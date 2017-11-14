package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	public String getCategories() {
		IOOperations io = new IOOperations();
		
		Collection<Category> categories = io.getCategories();
		
		JSONArray response = new JSONArray();
		for (Category category : categories) {
			response.put(category.createJSONRepresentationofCategory());
		}
		
		return response.toString();
	}
}
