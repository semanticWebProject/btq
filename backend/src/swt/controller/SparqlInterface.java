package swt.controller;

import swt.model.Question;
import swt.model.QuestionXML;

public class SparqlInterface {

	private String WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql";
	private String DBPEDIA_ENDPOINT  = "http://dbpedia.org/sparql";


	// just for testing
	public static void main(String [] args) {
		SparqlInterface sq = new SparqlInterface();
		sq.getQuestion(0,0);
	}

	public Question getQuestion(int categoryId,int level) {
		IOOperations ioops = new IOOperations();
		QuestionXML questionXML = ioops.getQuestion(categoryId);
		Question question = new Question();
		int queryEndpointId = questionXML.getQueryEndpoint();
		
		if (queryEndpointId == 0) {
			//DBPedia
			DbpediaSparqlEndpoint de = new DbpediaSparqlEndpoint();
			question = de.getQuestionFromSparqlEndpoint(questionXML, DBPEDIA_ENDPOINT,level);
		}
		else if(queryEndpointId == 1) {
			// wikidata
			WikidataSparqlEndpoint we = new WikidataSparqlEndpoint();
			question = we.getQuestionFromSparqlEndpoint(questionXML, WIKIDATA_ENDPOINT,level);
		}
		else {
			
			System.err.println("No question endpoint specified");
		}
		
		return question;
	}
}
