package swt.controller;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

import swt.model.Answer;
import swt.model.Question;
import swt.model.QuestionXML;

public abstract class AbstractQueryEndpoint {

    /**
     * Get question from specific endpoint
     *
     * @param questionXML QuestionXML object which contains all information stored in the xml files
     * @return Question object
     */
    abstract protected Question getQuestionFromSparqlEndpoint(QuestionXML questionXML);


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

        while (options.size() != 4) {
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
    protected Question setOptionAnswer(Question question, String[] wrongAnswers, String correctAnswer) {
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

            Answer answer = new Answer(index, wrongAnswers[count++]);
            answers.add(answer);
        }

        // set correct answer option
        Answer answer = new Answer(optionsPosition.get(0), correctAnswer);
        answers.add(answer);
        question.setCorrect(optionsPosition.get(0));

        // set all answers
        question.setAnswers(answers);

        return question;
    }

}

