package swt.controller;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

import java.util.ArrayList;
import java.util.HashMap;

public class WikidataSparqlEndpoint extends AbstractQueryEndpoint {

    /**
     * Generic function to run sparql query against a specified endpoint
     *
     * @param sparqlEndpoint specified endpoint to run the query on
     * @param sparql         query string
     * @return ArrayList of results
     */
    protected ArrayList<HashMap<String, String>> runSparqlQuery(String sparqlEndpoint, String sparql) {
        System.out.println("Wikidata endpoint");
        // Old endpoint used for wikidata
        Endpoint endpoint = new Endpoint(sparqlEndpoint, true);

        endpoint.setMethodHTTPRead("GET");
        HashMap<String, HashMap> result = new HashMap<>();

        try {
            sparql = sparql;//+ " LIMIT 10";
            result = endpoint.query(sparql);
        } catch (EndpointException e) {
            e.printStackTrace();
        } catch(Exception ex) {
            System.out.println("Exception: run again");
            runSparqlQuery(sparqlEndpoint, sparql);
        }

        System.out.println("-- Abstract: runSparqlQuery: ");
        for (HashMap<String, String> r : (ArrayList<HashMap<String, String>>) result.get("result").get("rows")) {
            System.out.println(r.toString());
        }

        return (ArrayList<HashMap<String, String>>) result.get("result").get("rows");
    }
}
