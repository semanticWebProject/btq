package swt.model;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public final class QuestionXML {

	@JacksonXmlProperty(localName = "endpoint")
	private int queryEndpoint;
	@JacksonXmlProperty(localName = "text")
	private String questionText;
	@JacksonXmlProperty(localName = "sparql")
	private String sparqlQuery;
	@JacksonXmlProperty(localName = "parameter1")
	private String parameter1;
	@JacksonXmlProperty(localName = "parameter2")
	private String parameter2;
	@JacksonXmlProperty(localName = "offsetMax")
	private int offsetMax;
    @JacksonXmlProperty(localName = "description")
	private String description;
    
    @JacksonXmlElementWrapper(localName = "levels")
    @JacksonXmlProperty(localName = "level")
    private List<Level> levels;
    
    
    HashMap<Integer,Level> levelDetails;

	public QuestionXML() {
		
		
	}

	public QuestionXML(int queryEndpoint, String questionText, String sparqlQuery, String parameter1,
					   String parameter2, int offsetMax, String description,List<Level> levels) {
		this.queryEndpoint = queryEndpoint;
		this.questionText = questionText;
		this.sparqlQuery = sparqlQuery;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.offsetMax = offsetMax;
		this.description = description;
		
	}

	public int getQueryEndpoint() {
		return queryEndpoint;
	}

	public void setQueryEndpoint(int queryEndpoint) {
		this.queryEndpoint = queryEndpoint;
	}

	
	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getSparqlQuery() {
		return sparqlQuery;
	}

	public void setSparqlQuery(String sparqlQuery) {
		this.sparqlQuery = sparqlQuery;
	}

	public String getParameter1() {
		return parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	public String getParameter2() {
		return parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}
	

	public int getOffsetMax() {
		return offsetMax;
	}

	public void setOffsetMax(int offsetMax) {
		this.offsetMax = offsetMax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public void setLevels(List<Level> levels) {
		this.levels=levels;
	}
	
	public List<Level> getLevels(){
		return this.levels;
	}
	
	
	public Level getLevelDetails(int levelId) {
		
		//convert list into hashmap if it is not converted
		if(this.levelDetails==null) {
			this.levelDetails=new HashMap<Integer,Level>();
			for(Level level :levels) {
				
				levelDetails.put(level.getId(),level);
				
			}
		}
		
		
		Level levelDetails=new Level();
		
		if(this.levelDetails.get(levelId)!=null)
			levelDetails= this.levelDetails.get(levelId);
		else
			System.out.println("No details present");
		
		return levelDetails;
	} 
}
