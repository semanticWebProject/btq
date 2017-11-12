package swt.controller;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class IOOperations {
	

	
	public String getQuestion(String category) {
		
		String question = "";
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("config/" + getCategoryFileName(category));
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expression = xpath.compile("/questions/question");
			
			NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
			
			Random randomNumberGenerator = new Random();
			
			int totalResults = nodeList.getLength();
			
			System.out.println("Total Questions: " + totalResults);
			
			if (totalResults > 0) {
				int randomNumber = randomNumberGenerator.nextInt(totalResults);
				
				System.out.println("Random Number: " + randomNumber);
				
				Node item = nodeList.item(randomNumber);
				NodeList childNodes = item.getChildNodes();
				
				for (int i = 0; i < childNodes.getLength(); i++) {
					
					if (childNodes.item(i).getNodeType() == 1)
						question = childNodes.item(i).getTextContent().trim();
						//System.out.println(i+" " +childNodes.item(i).getTextContent().trim());
				}
				
			}
			
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return question;
		
	}
	
	public String getCategoryFileName(String category) {
		
		String fileName = "";
		
		switch(category) {
						case "geography": fileName = "geography.xml";
						break;
						default: fileName = "geography.xml";
						break;
		}
		return fileName;
		
	}

}
