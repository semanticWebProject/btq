package service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import swt.controller.IOOperations;
import swt.model.Category;

@Path("/category")
public class CategoryService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories(@Context HttpServletResponse response) {
        response.setHeader("access-control-allow-origin", "*");

        IOOperations io = new IOOperations();
		Collection<Category> categories = io.getCategories();
		ObjectMapper mapper = new ObjectMapper();

		String jsonResponse = "";
		try {
			jsonResponse = mapper.writeValueAsString(categories);
		} catch (JsonProcessingException e) {
			throw new NotFoundException();
		}

		return jsonResponse;
	}
}
