package swt.application;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import swt.controller.SparqlInterface;

@Path("/Service")
public class Service {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getQuestion() {
		System.out.println("I am here");
		SparqlInterface sparql=new SparqlInterface();
		return sparql.getQuestion("geography");
	}
}
