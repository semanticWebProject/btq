package swt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import swt.model.Question;
import swt.model.QuestionXML;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

public class SparqlInterface {

	private String WIKIDATA_ENDPOINT = "https://query.wikidata.org/sparql";

	public Question getQuestion(int categoryId) {
		IOOperations ioops = new IOOperations();
		QuestionXML questionXML = ioops.getQuestion(categoryId);

		return getQuestionFromWikiData(questionXML);
	}

	public Question getQuestionFromWikiData(QuestionXML questionXML) {
		Question question = new Question();

		// replace parameters in query
		System.out.println(questionXML.getQuestionText());
		System.out.println(questionXML.getParameter1());
		String query = questionXML.getSparqlQuery().replace("parameter1", questionXML.getParameter1());
		query = query.replace("parameter2", questionXML.getParameter2());

		// run query and get result
		ArrayList<HashMap<String, String>> records = runSparqlQuery(query);
		HashSet<Integer> options = createRandomOptions(records.size());

		// set attributes in question
		String questionParameter = records.get((int) options.toArray()[0]).get(questionXML.getParameter1());
		String correctAnswer = records.get((int) options.toArray()[0]).get(questionXML.getParameter2());
		String otherOption1 = records.get((int) options.toArray()[1]).get(questionXML.getParameter2());
		String otherOption2 = records.get((int) options.toArray()[2]).get(questionXML.getParameter2());
		String otherOption3 = records.get((int) options.toArray()[3]).get(questionXML.getParameter2());
		String[] answers = new String[] { otherOption1, otherOption2, otherOption3 };
		question = setOptionAnswer(question, answers, correctAnswer);

		// get parameter of question
		String regex = "[{](.)+[}]";
		Pattern regexPattern = Pattern.compile(regex);
		Matcher matcher = regexPattern.matcher(questionXML.getQuestionText());
		String parameter = matcher.group(0);
		question.getQuestionText().replace(parameter, questionParameter);

		return question;
	}

	public Question getQuestionFromWikiData() {
		Question question = new Question();

		String querySelect = "SELECT ?countryLabel ?cityLabel WHERE { " + "  ?country wdt:P31 wd:Q6256. "
				+ "  ?country wdt:P36 ?city. " + "  SERVICE wikibase:label { "
				+ "		bd:serviceParam wikibase:language \"en\" . " + "  } " + "} " + "LIMIT 10";

		ArrayList<HashMap<String, String>> records = runSparqlQuery(querySelect);
		HashSet<Integer> options = createRandomOptions(records.size());

		String countryName = records.get((int) options.toArray()[0]).get("countryLabel");
		String correctAnswer = records.get((int) options.toArray()[0]).get("cityLabel");
		String otherOption1 = records.get((int) options.toArray()[1]).get("cityLabel");
		String otherOption2 = records.get((int) options.toArray()[2]).get("cityLabel");
		String otherOption3 = records.get((int) options.toArray()[3]).get("cityLabel");

		System.out.println(records.get((int) options.toArray()[0]).get("countryLabel"));
		System.out.println(records.get((int) options.toArray()[0]).get("cityLabel"));
		System.out.println(records.get((int) options.toArray()[1]).get("cityLabel"));
		System.out.println(records.get((int) options.toArray()[2]).get("cityLabel"));
		System.out.println(records.get((int) options.toArray()[3]).get("cityLabel"));

		question.setQuestionText("What is the capital of " + countryName + " ?");

		// set position of correct option
		String[] answers = new String[] { otherOption1, otherOption2, otherOption3 };
		question = setOptionAnswer(question, answers, correctAnswer);

		return question;
	}

	private ArrayList<HashMap<String, String>> runSparqlQuery(String sparql) {

		Endpoint endpoint = new Endpoint(WIKIDATA_ENDPOINT, false);
		endpoint.setMethodHTTPRead("GET");
		HashMap<String, HashMap> result = new HashMap<>();

		try {
			result = endpoint.query(sparql);
		} catch (EndpointException e) {
			e.printStackTrace();
		}

		ArrayList<HashMap<String, String>> records = (ArrayList<HashMap<String, String>>) result.get("result")
				.get("rows");

		return records;
	}

	private HashSet<Integer> createRandomOptions(int maxNumber) {
		HashSet<Integer> options = new HashSet<Integer>();
		Random randomNumberGenerator = new Random();
		int randomNumber;

		while (options.size() != 4) {
			randomNumber = randomNumberGenerator.nextInt(maxNumber);
			options.add(randomNumber);
		}

		return options;
	}

	private Question setOptionAnswer(Question question, String[] answers, String correctAnswer) {
		Random randomNumberGenerator = new Random();
		int randomNumber;
		ArrayList<Integer> optionsPosition = new ArrayList<Integer>();

		optionsPosition.add(1);
		optionsPosition.add(2);
		optionsPosition.add(3);
		optionsPosition.add(4);

		// set other answer options
		while (optionsPosition.size() >= 1) {
			randomNumber = randomNumberGenerator.nextInt(optionsPosition.size());
			int index = optionsPosition.get(randomNumber);
			optionsPosition.remove(randomNumber);

			switch (index) {
			case 1:
				question.setOption1(answers[index]);
				break;
			case 2:
				question.setOption2(answers[index]);
				break;
			case 3:
				question.setOption3(answers[index]);
				break;
			case 4:
				question.setOption4(answers[index]);
				break;
			}
		}

		// set correct answer option
		switch (optionsPosition.get(0)) {
		case 1:
			question.setOption1(correctAnswer);
			question.setCorrectOptionIndex(1);
			break;
		case 2:
			question.setOption2(correctAnswer);
			question.setCorrectOptionIndex(2);
			break;
		case 3:
			question.setOption3(correctAnswer);
			question.setCorrectOptionIndex(3);
			break;
		case 4:
			question.setOption4(correctAnswer);
			question.setCorrectOptionIndex(4);
			break;
		}

		return question;
	}

}
