package service;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import javax.ws.rs.POST;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import swt.controller.SparqlInterface;

@Path("/question")
public class QuestionService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestion(	@QueryParam("topic") String topic,
								@QueryParam("questionNo") int questionNo,
								@Context HttpHeaders header, 
								@Context HttpServletResponse response) {
		response.setHeader("access-control-allow-origin", "*");
		System.out.println("I am here " + System.getProperty("user.dir"));
		System.out.println("Topic : " +topic );
		System.out.println("Question Number : " +questionNo );
		SparqlInterface sparql=new SparqlInterface();
		return sparql.getQuestion("geography");
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String question(	@FormParam("topic") String topic,
							@FormParam("questionNo") int questionNo) {
		System.out.println("I am here " + System.getProperty("user.dir"));
		System.out.println("Topic : " +topic );
		System.out.println("Question Number : " +questionNo );
		SparqlInterface sparql=new SparqlInterface();
		return sparql.getQuestion("geography");
	}
}
