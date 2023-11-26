package org.project;


import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;


@RestController
@Path("/api")
@RequestMapping("/api")
public class dataDiver {
    String filepath;
    Logger logger = LogManager.getLogger(dataDiver.class);
    Input in = new Input("");

    @PostConstruct
    public void init() {
        long t = System.currentTimeMillis();
        logger.info("Ping me if u see this, I'll buy you a beer - Matt");
        filepath = "src\\main\\resources\\input.txt";;
        getMap();
        long total = System.currentTimeMillis() - t;
        logger.info("Time taken to do startup logic: " + total);
    }

    private void getMap() {
        long begin = System.currentTimeMillis();
        in.setFilepath(filepath);
        in.processInputFile();
        logger.info("Time taken to get input map: " + (System.currentTimeMillis() - begin));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/get_entries")
    @GetMapping("/get_entries")
    public Set<String> get_entries(

    ) {
        if (in.dataMap == null || in.dataMap.isEmpty())
            getMap();
        return in.dataMap.keySet();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/get_entries_map")
    @GetMapping("/get_entries_map")
    public Map<String, descriptionObject> get_entries_map(

    ) {
        if (in.dataMap == null || in.dataMap.isEmpty())
            getMap();
        return in.dataMap;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/get_entries")
    @GetMapping("/reindex")
    public Set<String> reindex(

    ) {
        getMap();
        return in.dataMap.keySet();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/remove_entry")
    @DeleteMapping("/remove_entry/{name}")
    public Set<String> remove_entry(
            @Parameter(description = "Name of entry to remove", required = true)
            @PathVariable("name") String name
    ) {
        logger.info("Removing entry " + name);
        in.removeInputItem(name);
        return in.dataMap.keySet();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add_entry")
    @PostMapping("/add_entry")
    public String add_entry(
            @Parameter(description = "Json representation", required = true)
            @RequestBody Map<String, String> body
    ) throws Exception {
        System.out.println("a");
        logger.info("Adding entry for "+ body.get("name"));
        logger.info("yippee");
        in.addInputItem(body.get("url"), body.get("name"));
        //do we want this to be persistent (add to the input file?)
        if (in.dataMap.containsKey(body.get("name")))
            return "Successfully added entry.";
        return "There was an error adding this entry.";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add_entries_json")
    @PostMapping("/add_entries_json")
    public String add_entries_json(
            @Parameter(description = "Json representation", required = true)
            @RequestBody Map<String, Map<String, String>> body
    ) throws Exception {
        System.out.println("a");
        logger.info("Adding entries from JSON");
        for (Map.Entry<String, Map<String, String>> entry : body.entrySet())
        {
            String name = entry.getValue().get("name");
            String url = entry.getValue().get("url");
            if (name == null || name.isEmpty()) {
                return "Entry " + entry.getKey() + " does not have a name parameter.";
            }
            if (url == null || name.isEmpty()) {
                return "Entry " + entry.getKey() + " does not have a url parameter.";
            }
            in.addInputItem(entry.getValue().get("name"), entry.getValue().get("url"));
        }

        //do we want this to be persistent (add to the input file?)
        if (in.dataMap.containsKey(body.get("name")))
            return "Successfully added entry.";
        return "There was an error adding this entry.";
    }

//TODO file upload add entries (i hope they don't use this, it's dumb.)
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("/add_entry")
//    @PostMapping("/add_entry")
//    public String add_entries_file(
//            @Parameter(description = "Json representation", required = true)
//            @RequestPart MultipartFile file
//    ) throws Exception {
//        System.out.println("a");
//        logger.info("Adding entry for "+ body.get("name"));
//        logger.info("yippee");
//        in.addInputItem(body.get("name"), body.get("url"));
//        //do we want this to be persistent (add to the input file?)
//        if (in.dataMap.containsKey(body.get("name")))
//            return "Successfully added entry.";
//        return "There was an error adding this entry.";
//    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/autofill/set_threshold/{threshold}")
    @PutMapping("/autofill/set_threshold/{threshold}")
    public String set_threshold(
            @Parameter(description = "threshold value, out of 100. This is for url syntax requirements.", required = true)
            @PathVariable int threshold
    ) {
        if (threshold > 100 || threshold <= 0)
            return "Please enter a valid threshold value, between 1 and 100";

        double rate = (double) threshold / 100;
        return in.getAutofiller().setAutofillThreshold(rate);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/autofill/{partialEntry}")
    @GetMapping("/autofill/{partialEntry}")
    public Set<String> autofill(
            @Parameter(description = "partial input to search against", required = true)
            @PathVariable String partialEntry
    ) {
        return in.getAutofiller().getPotentialMatches(partialEntry);
    }

    //TODO get matches for input string
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/search/{entry}")
    @GetMapping("/search/{entry}")
    public List<descriptionObject> search(
            @Parameter(description = "Search string", required = true)
            @PathVariable String entry,
            @Parameter(description = "Result ordering types, options are ALPHA, FREQ, PAY")
            @RequestParam(name = "order") OrderEnum ordering,
            @Parameter(description = "Search type, options are AND, OR, NOT")
            @RequestParam(name = "searchType") SearchType type
    ) {
        return Search.search(in, entry, ordering, type);
    }



}
