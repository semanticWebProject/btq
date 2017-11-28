package swt.controller;


import org.apache.jena.graph.Node;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbpediaSparqlEndpoint extends AbstractQueryEndpoint {


    /**
     * function to run sparql query with Jena framework
     *
     * @param sparqlEndpoint specified endpoint to run the query on
     * @param sparql         query string
     * @return ArrayList of results
     */
    protected ArrayList<HashMap<String, String>> runSparqlQuery(String sparqlEndpoint, String sparql) {

        Query query = QueryFactory.create(sparql);
        QueryExecution qExe = QueryExecutionFactory.sparqlService( sparqlEndpoint, query );
        qExe.setTimeout(5000, 20000);

        System.out.println("Run new sparql query on : " + sparqlEndpoint);
        ResultSet results = qExe.execSelect();
//      ResultSetFormatter.out(System.out, results, query) ; // for pretty printing, ATTENTION: hasNext() is null if called

        // Read results
        if (results == null) System.out.println("results are null");

        System.out.println("runSparql Query with jena");
        System.out.println(results.hasNext());

        ArrayList<HashMap<String, String>> endresult = new ArrayList<HashMap<String, String>>();

        while(results.hasNext()) {

            QuerySolution qs = results.next();
            List<String> list = results.getResultVars();
            HashMap<String, String> answerOption = new HashMap<String, String>();
            for(String s : list) {
                RDFNode node = qs.get(s);
                Node n = node.asNode();


                System.out.println("Is literal: " + node.isLiteral());
                System.out.println("Is ressource: " + node.isResource());
                System.out.println("Is uri resource: " + node.isURIResource());
                System.out.println("node: " + node.toString());

                if (node.isResource()) {
                    Resource res = node.asResource();
                    System.out.println("localname: " + res.getLocalName());
                    System.out.println("uri: " + res.getURI());
                    System.out.println("string: " + res.toString());
                    System.out.println("ns: " + res.getNameSpace());

                    answerOption.put(s, res.getURI());
                }
                if (node.isLiteral()) {
                    Literal l = node.asLiteral();
                    System.out.println("as literal: " + node.asLiteral());
                    System.out.println("Node literal datatype: " + n.getLiteralDatatype());
                    System.out.println("Node l datatype: " + l.getDatatype());
                    System.out.println("Node literal datatype uri: " + n.getLiteralDatatypeURI());
                    System.out.println("Node language: " + n.getLiteralLanguage());
                    System.out.println("Node literalvalue: " + n.getLiteralValue());
                    System.out.println("Node literal: " + n.getLiteral());

                    if (n.getLiteralDatatypeURI().equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#decimal")) {
                        System.out.println("Decimal");
                        String formattedNumber = formatNumber(n.getLiteralValue().toString());
                        System.out.println(formattedNumber);
                        answerOption.put(s, formattedNumber);
                    }
                    else {
                        System.out.println("Non Decimal");
                        answerOption.put(s, n.getLiteralValue().toString());
                    }

                }

                System.out.print("Label: " + s + " ") ;
//              System.out.println("Ressource: " + qs.getResource(s));

            }
            endresult.add(answerOption);
        }

        System.out.println("-- Abstract: Jena Result: ");
        for (HashMap<String, String> r : endresult) {
            System.out.println(r.toString());
        }
        return endresult;
    }
}
