package swt.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swt.model.Question;
import swt.model.QuestionXML;

public class WikidataSparqlEndpoint extends AbstractQueryEndpoint {
	
	private String WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql";
	
	protected Question getQuestionFromSparqlEndpoint(QuestionXML questionXML) {

		Question question = new Question();
		
		// replace parameters in query
		String query = questionXML.getSparqlQuery().replace("{parameter1}", questionXML.getParameter1());
		query = query.replace("{parameter2}", questionXML.getParameter2());
		query=query.replace("#lt", "<");
		query=query.replace("#gt", ">");

		Random randomNumberGenerator = new Random();
		int randomOffset = randomNumberGenerator.nextInt(questionXML.getOffsetMax());
		query = query.replace("{offset}", String.valueOf(randomOffset));

		System.out.println("Query: "+ query);
		
		// run query and get result
		ArrayList<HashMap<String, String>> records = runSparqlQuery(WIKIDATA_ENDPOINT, query);
		HashSet<Integer> options = createRandomOptions(records.size());

		// set attributes in question
		String questionParameter= records.get((int) options.toArray()[0]).get(questionXML.getParameter1());
		String correctAnswer 	= records.get((int) options.toArray()[0]).get(questionXML.getParameter2());
		String wrongAnswer1 	= records.get((int) options.toArray()[1]).get(questionXML.getParameter2());
		String wrongAnswer2 	= records.get((int) options.toArray()[2]).get(questionXML.getParameter2());
		String wrongAnswer3 	= records.get((int) options.toArray()[3]).get(questionXML.getParameter2());
		String[] wrongAnswers 	= new String[] { wrongAnswer1, wrongAnswer2, wrongAnswer3 };
		question = setOptionAnswer(question, wrongAnswers, correctAnswer);
		
		// get parameter of question
		String regex = "[{](.)+[}]";
		Pattern regexPattern = Pattern.compile(regex);
		question.setQuestion(questionXML.getQuestionText());
		Matcher matcher = regexPattern.matcher(question.getQuestion());
		matcher.find();
		String parameter = matcher.group(0);

		if (questionParameter.startsWith("http")) {
			question.setImage(questionParameter);
			question.setQuestion(question.getQuestion().replace(parameter, ""));
		} else {
			question.setQuestion(question.getQuestion().replace(parameter, questionParameter));
		}

		return question;
	}
}
