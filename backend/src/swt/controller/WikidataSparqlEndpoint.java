package swt.controller;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

import java.util.ArrayList;
import java.util.HashMap;

public class WikidataSparqlEndpoint extends AbstractQueryEndpoint {

    /**
     * function to run sparql query against wikidata endpoint
     *
     * @param sparqlEndpoint specified endpoint to run the query on
     * @param sparql         query string
     * @return ArrayList of results
     */
    protected ArrayList<HashMap<String, String>> runSparqlQuery(String sparqlEndpoint, String sparql) {
        System.out.println("Wikidata endpoint");
        Endpoint endpoint = new Endpoint(sparqlEndpoint, true);

        endpoint.setMethodHTTPRead("GET");
        HashMap<String, HashMap> result = new HashMap<>();

        try {
            System.out.println("-- Wikidata: runSparqlQuery: ");
            result = endpoint.query(sparql);
        } catch(Exception ex) {
            System.out.println("Exception: run again");
            runSparqlQuery(sparqlEndpoint, sparql);
        }

//        for (HashMap<String, String> r : (ArrayList<HashMap<String, String>>) result.get("result").get("rows")) {
//            System.out.println(r.toString());
//        }

        return (ArrayList<HashMap<String, String>>) result.get("result").get("rows");
    }
}
