package service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import swt.controller.SparqlInterface;
import java.util.Random;

@Path("/category/{categoryId}/question")
public class QuestionService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestion(@PathParam("categoryId") int categoryId, @QueryParam("level") int level, @Context HttpServletResponse response) {
		response.setHeader("access-control-allow-origin", "*");
		response.setContentType("application/json;charset=UTF-8");

		ObjectMapper mapper = new ObjectMapper();
		SparqlInterface sparql = new SparqlInterface();
		int questionLevel=level; //level of question 0:easy , 1:medium , 2:hard, default: easy

		String jsonResponse = "";
		try {
			jsonResponse = mapper.writeValueAsString(sparql.getQuestion(categoryId,questionLevel));
		} catch (JsonProcessingException e) {
			throw new NotFoundException();
		}

		return jsonResponse;
	}
	
}
