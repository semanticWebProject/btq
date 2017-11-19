package swt.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import swt.model.Category;
import swt.model.QuestionXML;
import swt.model.CategoryXML;


public class IOOperations {

    public QuestionXML getQuestion(int categoryId) {

        QuestionXML question = new QuestionXML();

        // read in xml for categoryId
        File file = new File(this.getClass().getClassLoader().getResource("categories").getFile() + "/" + getCategoryFileName(categoryId));
        CategoryXML categoryXML = getCategory(file);

        // get a random number and select question
        Random randomNumberGenerator = new Random();
        int totalResults = categoryXML.getQuestion().length;
        System.out.println("Total Questions: " + totalResults);
        totalResults = 2; // for testing
        if (totalResults > 0) {
            int randomNumber = randomNumberGenerator.nextInt(totalResults);

            System.out.println("Random Number: " + randomNumber);

            // select random question from questions
            question = categoryXML.getQuestion()[randomNumber];

        }

        // just for testing
        System.out.println("Query endpoint: " + question.getQueryEndpoint());
        System.out.println("Text: " + question.getQuestionText());
        System.out.println("Sparql: " + question.getSparqlQuery());
        System.out.println("Parameter1: " + question.getParameter1());
        System.out.println("Parameter2: " + question.getParameter2());
        System.out.println("Easy: " + question.getEasyFilter());
        System.out.println("Medium: " + question.getMediumFilter());
        System.out.println("Hard: " + question.getHardFilter());

        return question;

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
            File file = new File(fileList[i].getAbsolutePath());
            CategoryXML categoryXML = getCategory(file);
            Category category = new Category(categoryXML.getId(), categoryXML.getName(), categoryXML.getImageURL());
            categories.add(category);
        }

        return categories;
    }

    private CategoryXML getCategory(File file) {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);

        // get category including all questions
        CategoryXML category = null;
        try {
            category = mapper.readValue(file, CategoryXML.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return category;
    }
}
