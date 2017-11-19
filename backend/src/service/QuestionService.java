package service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import swt.controller.SparqlInterface;

@Path("/category/{categoryId}/question")
public class QuestionService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestion(@PathParam("categoryId") int categoryId, @Context HttpServletResponse response) {
		response.setHeader("access-control-allow-origin", "*");
		ObjectMapper mapper = new ObjectMapper();
		SparqlInterface sparql = new SparqlInterface();

		String jsonResponse = "";
		try {
			jsonResponse = mapper.writeValueAsString(sparql.getQuestion(categoryId));
		} catch (JsonProcessingException e) {
			throw new NotFoundException();
		}

		return jsonResponse;
	}
	
}
