package swt.model;

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
	@JacksonXmlProperty(localName = "easy")
	private String easyFilter;
	@JacksonXmlProperty(localName = "medium")
	private String mediumFilter;
	@JacksonXmlProperty(localName = "hard")
	private String hardFilter;
	

	public QuestionXML() {
	}

	public QuestionXML(int queryEndpoint, String questionText, String sparqlQuery, String parameter1,
					   String parameter2, int offsetMax, String easyFilter, String mediumFilter, String hardFilter) {
		this.queryEndpoint = queryEndpoint;
		this.questionText = questionText;
		this.sparqlQuery = sparqlQuery;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.offsetMax = offsetMax;
		this.easyFilter = easyFilter;
		this.mediumFilter = mediumFilter;
		this.hardFilter = hardFilter;
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
	
	public String getEasyFilter() {
		return easyFilter;
	}

	public void setEasyFilter(String easyFilter) {
		this.easyFilter = easyFilter;
	}
	
	public String getMediumFilter() {
		return mediumFilter;
	}

	public void setMediumFilter(String mediumFilter) {
		this.mediumFilter = mediumFilter;
	}
	
	public String getHardFilter() {
		return hardFilter;
	}

	public void setHardFilter(String hardFilter) {
		this.hardFilter = hardFilter;
	}

	public int getOffsetMax() {
		return offsetMax;
	}

	public void setOffsetMax(int offsetMax) {
		this.offsetMax = offsetMax;
	}
}
