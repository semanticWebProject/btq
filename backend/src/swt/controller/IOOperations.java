package swt.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import swt.model.Category;
import swt.model.Question;
import swt.model.QuestionXML;


public class IOOperations {
	
	public QuestionXML getQuestion(int categoryId) {
		
		QuestionXML questionXML = new QuestionXML();
				
		try {
			// read in xml for categoryId
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(this.getClass().getClassLoader().getResource("categories").getFile() + "/" + getCategoryFileName(categoryId));
			
			// get all questions
			NodeList nodeList = document.getElementsByTagName("question");
			
			// get a random number and select question
			Random randomNumberGenerator = new Random();
			int totalResults = nodeList.getLength();
			System.out.println("Total Questions: " + totalResults);
			
			if (totalResults > 0) {
				int randomNumber = randomNumberGenerator.nextInt(totalResults);
				
				System.out.println("Random Number: " + randomNumber);
				
				// select random question from questions
				Node item = nodeList.item(randomNumber);
				System.out.println("Current Element: " + item.getNodeName());

				// set attributes for question from xml context
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					questionXML.setQuestionText(element.getElementsByTagName("text").item(0).getTextContent());
					questionXML.setSparqlQuery(element.getElementsByTagName("sparql").item(0).getTextContent());
					questionXML.setParameter1(element.getElementsByTagName("parameter1").item(0).getTextContent());
					questionXML.setParameter2(element.getElementsByTagName("parameter2").item(0).getTextContent());
				}
			}
			
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("Text: " + questionXML.getQuestionText());
//		System.out.println("Sparql: " + questionXML.getSparqlQuery());
		System.out.println("Parameter1: " + questionXML.getParameter1());

		return questionXML;
		
	}
	
	private String getCategoryFileName(int categoryId) {
				
		File folder = new File(this.getClass().getClassLoader().getResource("categories").getFile());
		File category = folder.listFiles()[categoryId];
		
		return category.getName();
		
	}
	
	public Collection<Category> getCategories() {
		Collection<Category> categories = new ArrayList<>();
		
		File folder = new File(this.getClass().getClassLoader().getResource("categories").getFile());
		File[] fileList = folder.listFiles();
		
		for (int i = 0; i < fileList.length; i++) {
			String categoryName = fileList[i].getName().substring(0, fileList[i].getName().length() - 4);
			Category category = new Category(i, categoryName);
			categories.add(category);
		}
		
		return categories;
	}

}
