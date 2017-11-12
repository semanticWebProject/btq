package swt.model;

import org.json.*;

public class Question {
	
	String questionText = "";
	String option1 = "";
	String option2 = "";
	String option3 = "";
	String option4 = "";
	int correctOptionIndex;
	
	public Question() {
		
	}
	
	public Question(String questionText,String option1,
			String option2,String option3,String option4,
			int correctOptionIndex) {
		
		this.questionText = questionText;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctOptionIndex = correctOptionIndex;
		
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public void setOption1(String option) {
		this.option1 = option;
	}
	
	public void setOption2(String option) {
		this.option2 = option;
	}
	
	public void setOption3(String option) {
		this.option3 = option;
	}
	
	public void setOption4(String option) {
		this.option4 = option;
	}
	public void setCorrectOptionIndex(int index) {
		this.correctOptionIndex = index;
	}
	
	public String getQuestionText() {
		return this.questionText;
	}
	
	public String getOption1() {
		return this.option1;
	}
	
	public String getOption2() {
		return this.option2;
	}
	
	public String getOption3() {
		return this.option3;
	}
	
	public String getOption4() {
		return this.option4;
	}
	public int getCorrectOptionIndex() {
		return this.correctOptionIndex;
	}
	
	public String createJSONRepresentationofQuestion() {
		JSONObject json = new JSONObject();
		try {
			json.put("question", this.questionText);
			
			JSONArray optionsArray = new JSONArray();
			JSONObject option1 = new JSONObject();
			JSONObject option2 = new JSONObject();
			JSONObject option3 = new JSONObject();
			JSONObject option4 = new JSONObject();
			
			option1.put("id", 1);
			option1.put("text", this.option1);
			optionsArray.put(option1);
			
			option2.put("id", 2);
			option2.put("text", this.option2);
			optionsArray.put(option2);
			
			option3.put("id", 3);
			option3.put("text", this.option3);
			optionsArray.put(option3);
			
			option4.put("id", 4);
			option4.put("text", this.option4);
			optionsArray.put(option4);
			
			json.put("answers", optionsArray);

			json.put("correct", this.correctOptionIndex);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return json.toString();
	}

}
