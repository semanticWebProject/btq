package swt.application;

import swt.controller.SparqlInterface;

public class Application {
	
	public static void main(String[] args) {
		SparqlInterface sparql=new SparqlInterface();
		
		System.out.println(sparql.getQuestion("geography"));
		
		
	}

}
