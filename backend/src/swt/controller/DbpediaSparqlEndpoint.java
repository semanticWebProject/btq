package swt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swt.model.Question;
import swt.model.QuestionXML;

public class DbpediaSparqlEndpoint extends AbstractQueryEndpoint {
	
	private String DBPEDIA_ENDPOINT  = "http://dbpedia.org/sparql";
	
	public Question getQuestionFromSparqlEndpoint(QuestionXML questionXML) {
		Question question = new Question();
	
		String query = questionXML.getSparqlQuery().replace("{parameter1}", questionXML.getParameter1());
		query = query.replace("{parameter2}", questionXML.getParameter2());

		System.out.println("Query: "+query);
		
		ArrayList<HashMap<String, String>> records = runSparqlQuery(DBPEDIA_ENDPOINT, query);
		
		
		// set attributes in question
		HashSet<Integer> options = createRandomOptions(records.size());
		String questionParameter= records.get((int) options.toArray()[0]).get(questionXML.getParameter1());
		String correctAnswer 	= records.get((int) options.toArray()[0]).get(questionXML.getParameter2());
		String wrongAnswer1 	= records.get((int) options.toArray()[1]).get(questionXML.getParameter2());
		String wrongAnswer2 	= records.get((int) options.toArray()[2]).get(questionXML.getParameter2());
		String wrongAnswer3 	= records.get((int) options.toArray()[3]).get(questionXML.getParameter2());
		String[] wrongAnswers 	= new String[] { wrongAnswer1, wrongAnswer2, wrongAnswer3 };
		question = setOptionAnswer(question, wrongAnswers, correctAnswer);
		
		// set question text
		String regex = "[{](.)+[}]";
		Pattern regexPattern = Pattern.compile(regex);
		question.setQuestionText(questionXML.getQuestionText());
		Matcher matcher = regexPattern.matcher(question.getQuestionText());
		matcher.find();
		String parameter = matcher.group(0);
		question.setQuestionText(question.getQuestionText().replace(parameter, questionParameter));

		System.out.println("question text: " + question.getQuestionText().replace(parameter, questionParameter));
		System.out.println("questionParameter: " + questionParameter);
		System.out.println("Correct answers: " + correctAnswer);
		System.out.println("Wrong answers: " + Arrays.toString(wrongAnswers));
		
		return question;
	}
}
