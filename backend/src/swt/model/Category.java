package swt.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Category {

		private int id;
		private String name;
		
		public Category() {
		}
		
		public Category(int id, String name) {
			this.id = id;
			this.name = name;
		}
	
		public JSONObject createJSONRepresentationofCategory() {
			JSONObject json = new JSONObject();
			try {
				json.put("id", this.id);
				json.put("name", this.name);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			return json;
		}

}
