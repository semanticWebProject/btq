package swt.controller;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

import swt.model.Answer;
import swt.model.Question;
import swt.model.QuestionXML;

public abstract class AbstractQueryEndpoint {


    /**
     * Generic function to run sparql query against a specified endpoint
     *
     * @param sparqlEndpoint specified endpoint to run the query on
     * @param sparql         query string
     * @return ArrayList of results
     */
    protected ArrayList<HashMap<String, String>> runSparqlQuery(String sparqlEndpoint, String sparql) {
        Endpoint endpoint = new Endpoint(sparqlEndpoint, false);
        endpoint.setMethodHTTPRead("GET");
        HashMap<String, HashMap> result = new HashMap<>();

        try {
            sparql = sparql + " LIMIT 10";
            result = endpoint.query(sparql);
        } catch (EndpointException e) {
            e.printStackTrace();
        }

        return (ArrayList<HashMap<String, String>>) result.get("result").get("rows");
    }


    /**
     * Randomizing the answer options
     *
     * @param maxNumber max random number
     * @return Hashset of random numbers for the options
     */
    protected HashSet<Integer> createRandomOptions(int maxNumber) {
        HashSet<Integer> options = new HashSet<Integer>();
        Random randomNumberGenerator = new Random();
        int randomNumber;

        while (options.size() != maxNumber) {
            randomNumber = randomNumberGenerator.nextInt(maxNumber);
            options.add(randomNumber);
        }

        return options;
    }


    /**
     * Sets all answer options for the query randomly
     *
     * @param question      Question object to fill with the answer options
     * @param wrongAnswers  Array of wrong answers
     * @param correctAnswer Correct answer
     * @return enriched Question object including the answer options
     */
    protected Question setOptionAnswer(Question question, HashMap<Integer, ArrayList<String>> wrongAnswers, String correctAnswer, String description) {
        ArrayList<Answer> answers = new ArrayList<>();
        Random randomNumberGenerator = new Random();
        int randomNumber;
        ArrayList<Integer> optionsPosition = new ArrayList<Integer>();
        int count = 0;

        optionsPosition.add(0);
        optionsPosition.add(1);
        optionsPosition.add(2);
        optionsPosition.add(3);

        // set wrong answer options
        while (optionsPosition.size() > 1) {
            randomNumber = randomNumberGenerator.nextInt(optionsPosition.size());
            int index = optionsPosition.get(randomNumber);
            optionsPosition.remove(randomNumber);

            String answerDescription = description.replace("{parameter2}", wrongAnswers.get(count).get(0));
            answerDescription = answerDescription.replace("{parameter1}", wrongAnswers.get(count).get(1));
            Answer answer = new Answer(index, wrongAnswers.get(count).get(0), answerDescription);
            count++;
            answers.add(answer);
        }

        // set correct answer option
        Answer answer = new Answer(optionsPosition.get(0), correctAnswer, "");
        answers.add(answer);
        question.setCorrect(optionsPosition.get(0));

        // set all answers
        Collections.sort(answers, Comparator.comparing(Answer::getId));
        question.setAnswers(answers);

        return question;
    }

    /**
     * Get question from specific endpoint
     *
     * @param questionXML QuestionXML object which contains all information stored in the xml files
     * @return Question object
     */
    protected Question getQuestionFromSparqlEndpoint(QuestionXML questionXML, String endpoint) {

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
        ArrayList<HashMap<String, String>> records = runSparqlQuery(endpoint, query);
        HashSet<Integer> options = createRandomOptions(records.size());

        // set attributes in question
        String questionParameter= records.get((int) options.toArray()[0]).get(questionXML.getParameter1());
        String correctAnswer 	= records.get((int) options.toArray()[0]).get(questionXML.getParameter2());
        HashMap<Integer,ArrayList<String>> wrongAnswers = new HashMap<>();

        int index = 1;
        int position = 0;
        while (wrongAnswers.size() < 3) {
            HashMap<String, String> record = records.get((int) options.toArray()[index++]);
            if (! wrongAnswers.containsKey(record.get(questionXML.getParameter2())) && ! record.get(questionXML.getParameter2()).equalsIgnoreCase(correctAnswer)) {
                ArrayList<String> answerRecord = new ArrayList<>();
                answerRecord.add(record.get(questionXML.getParameter2()));
                answerRecord.add(record.get(questionXML.getParameter1()));
                wrongAnswers.put(position++, answerRecord);
            }
        }

        question = setOptionAnswer(question, wrongAnswers, correctAnswer, questionXML.getDescription());

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

