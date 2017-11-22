package swt.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import swt.model.Category;
import swt.model.QuestionXML;
import swt.model.CategoryXML;
import swt.model.Level;
import swt.model.*;

import javax.ws.rs.NotFoundException;


public class IOOperations {

    public QuestionXML getQuestion(int categoryId) {

        QuestionXML question = new QuestionXML();

        // read in xml for categoryId
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);

        String fileName = getCategoryFileName(categoryId);
        File file = new File(this.getClass().getClassLoader().getResource("categories/" + fileName).getFile());

        QuestionsXML questionsXML = null;
        try {
            questionsXML = mapper.readValue(file, QuestionsXML.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get a random number and select question
        Random randomNumberGenerator = new Random();
        int totalResults = questionsXML.getQuestion().length;
        System.out.println("Total Questions: " + totalResults);
//        totalResults = 2; // for testing
        if (totalResults > 0) {
            int randomNumber = randomNumberGenerator.nextInt(totalResults);
        		
            System.out.println("Random Number: " + randomNumber);

            // select random question from questions
            question = questionsXML.getQuestion()[randomNumber];

        }

        // just for testing
        System.out.println("Query endpoint: " + question.getQueryEndpoint());
        System.out.println("Text: " + question.getQuestionText());
        System.out.println("Sparql: " + question.getSparqlQuery());
        System.out.println("Parameter1: " + question.getParameter1());
        System.out.println("Parameter2: " + question.getParameter2());
        System.out.println("Levels: " + question.getLevels());

        System.out.println("Easy : "+question.getLevelDetails(1).getOffsetMax());
        System.out.println("Medium : "+question.getLevelDetails(2).getOffsetMax());
        System.out.println("Hard : "+question.getLevelDetails(3).getOffsetMax());

        //System.out.println("Medium: " + question.getMediumFilter());
        //System.out.println("Hard: " + question.getHardFilter());

        return question;

    }


    public Collection<Category> getCategories() {
        Collection<Category> categories = new ArrayList<>();
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);

        // get category including all questions
        CategoriesXML categoriesXML = null;
        try {
            File file = new File(this.getClass().getClassLoader().getResource("categories/Categories.xml").getFile());
            categoriesXML = mapper.readValue(file, CategoriesXML.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (CategoryXML categoryXML : categoriesXML.getCategory()) {
            Category category = new Category(categoryXML.getId(), categoryXML.getName(), categoryXML.getImageURL());
            categories.add(category);
        }

        return categories;
    }

    private String getCategoryFileName(int categoryId) {

        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);

        // get category including all questions
        CategoriesXML categoriesXML = null;
        try {
            File file = new File(this.getClass().getClassLoader().getResource("categories/Categories.xml").getFile());
            categoriesXML = mapper.readValue(file, CategoriesXML.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (CategoryXML categoryXML : categoriesXML.getCategory()) {
            if (categoryXML.getId() == categoryId)
                return categoryXML.getFileName();
        }

        return null;
    }
}
