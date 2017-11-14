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

@Path("/category/{categoryId}/question")
public class QuestionService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestion(@PathParam("categoryId") int categoryId, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("access-control-allow-origin", "*");
		SparqlInterface sparql = new SparqlInterface();
		return sparql.getQuestion(categoryId).createJSONRepresentationofQuestion();
	}
	
}
