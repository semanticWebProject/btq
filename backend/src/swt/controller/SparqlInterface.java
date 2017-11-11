package swt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import swt.model.Question;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

public class SparqlInterface {
	
	
	public String getQuestion(String category) {
		String questionContents="";
		IOOperations ioops=new IOOperations();
		HashMap<String,String> questionParameters=new HashMap<String,String>();
		String question=ioops.getQuestion(category);
		String regex="[{](.)+[}]";
		
		Pattern regexPattern=Pattern.compile(regex);
		Matcher matcher=regexPattern.matcher(question);
		int i=0;
		
		
		while(matcher.find()) {
			String parameter=matcher.group(i);
			
			if(questionParameters.get(parameter)==null) {
				//String indices=questionParameters.get(parameter);
				
				questionParameters.put(parameter, String.valueOf(matcher.start()));
			}
			
			i++;
		}
		
		
		//System.out.println(questionParameters);
		questionContents=getQuestionFromWikiData();
		
		return questionContents;
		
	}
	
	public String getQuestionFromWikiData() {
		String questionContents="";
		
		try {
			Random randomNumberGenerator = new Random();
			int randomNumber;
			HashSet<Integer> options=new HashSet<Integer>();
			Question question=new Question();
			ArrayList<Integer> optionsPosition=new ArrayList<Integer>();
			optionsPosition.add(1);
			optionsPosition.add(2);
			optionsPosition.add(3);
			optionsPosition.add(4);
			
			 
            Endpoint sp = new Endpoint("https://query.wikidata.org/sparql", false);

            String querySelect = "SELECT ?countryLabel ?cityLabel WHERE { " + 
            		"  ?country wdt:P31 wd:Q6256. " + 
            		"  ?country wdt:P36 ?city. "+ 
            		"  SERVICE wikibase:label { " + 
            		"		bd:serviceParam wikibase:language \"en\" . " + 
            		"  } " + 
            		"} " + 
            		"LIMIT 10";
            
            sp.setMethodHTTPRead("GET");

            HashMap<String,HashMap> rs = sp.query(querySelect);
             
            ArrayList<HashMap<String, String>> records=(ArrayList<HashMap<String, String>>)rs.get("result").get("rows");
            
            
            while(options.size()!=4) {
            	randomNumber=randomNumberGenerator.nextInt(records.size());
            	options.add(randomNumber);
            }
            

            
            String countryName=records.get((int)options.toArray()[0]).get("countryLabel");
            String correctAnswer=records.get((int)options.toArray()[0]).get("cityLabel");
            String otherOption1=records.get((int)options.toArray()[1]).get("cityLabel");
            String otherOption2=records.get((int)options.toArray()[2]).get("cityLabel");
            String otherOption3=records.get((int)options.toArray()[3]).get("cityLabel");
            
            
            
            
            System.out.println(records.get((int)options.toArray()[0]).get("countryLabel"));
            System.out.println(records.get((int)options.toArray()[0]).get("cityLabel"));
            
            System.out.println(records.get((int)options.toArray()[1]).get("cityLabel"));
            System.out.println(records.get((int)options.toArray()[2]).get("cityLabel"));
            System.out.println(records.get((int)options.toArray()[3]).get("cityLabel"));
            

            question.setQuestionText("What is the capital of " + countryName+" ?");
            
            //set position of correct option
            randomNumber=randomNumberGenerator.nextInt(optionsPosition.size());
            int correctOptionIndex=optionsPosition.get(randomNumber);
            optionsPosition.remove(randomNumber);
            
            if(correctOptionIndex==1) {
            	question.setOption1(correctAnswer);
            	question.setCorrectOptionIndex(1);
            }
            else if(correctOptionIndex==2) {
            	question.setOption2(correctAnswer);
            	question.setCorrectOptionIndex(2);
            }
            else if(correctOptionIndex==3) {
            	question.setOption3(correctAnswer);
            	question.setCorrectOptionIndex(3);
            }
            else if (correctOptionIndex==4) {
            	question.setOption4(correctAnswer);
            	question.setCorrectOptionIndex(4);
            }
            
            
            randomNumber=randomNumberGenerator.nextInt(optionsPosition.size());
            int optionIndex=optionsPosition.get(randomNumber);
            optionsPosition.remove(randomNumber);
            if(optionIndex==1) {
            	question.setOption1(otherOption1);
            }
            else if(optionIndex==2) {
            	question.setOption2(otherOption1);
            }
            else if(optionIndex==3) {
            	question.setOption3(otherOption1);
            }
            else if (optionIndex==4) {
            	question.setOption4(otherOption1);
            }
            
            
            randomNumber=randomNumberGenerator.nextInt(optionsPosition.size());
            optionIndex=optionsPosition.get(randomNumber);
            optionsPosition.remove(randomNumber);
            if(optionIndex==1) {
            	question.setOption1(otherOption2);
            }
            else if(optionIndex==2) {
            	question.setOption2(otherOption2);
            }
            else if(optionIndex==3) {
            	question.setOption3(otherOption2);
            }
            else if (optionIndex==4) {
            	question.setOption4(otherOption2);
            }
            
            randomNumber=randomNumberGenerator.nextInt(optionsPosition.size());
            optionIndex=optionsPosition.get(randomNumber);
            optionsPosition.remove(randomNumber);
            if(optionIndex==1) {
            	question.setOption1(otherOption3);
            }
            else if(optionIndex==2) {
            	question.setOption2(otherOption3);
            }
            else if(optionIndex==3) {
            	question.setOption3(otherOption3);
            }
            else if (optionIndex==4) {
            	question.setOption4(otherOption3);
            }
            
            
            
            
           // System.out.println(question.createJSONRepresentationofQuestion());
            questionContents=question.createJSONRepresentationofQuestion();
            
            /*
            int i=1;
            
            
            for(HashMap<String, String> value : (ArrayList<HashMap<String, String>>)rs.get("result").get("rows")) {
            	
            	System.out.println("********************"+"Record "+i +"********************");
            	
            	String countryName=value.get("countryLabel");
            	String cityName=value.get("cityLabel");
            
            	System.out.println(countryName);
            	System.out.println(cityName);
            	
            		i=i+1;
            }
            */
        
            
            

        }catch(EndpointException eex) {
            System.out.println(eex);
            eex.printStackTrace();
        }
		
		return questionContents;
	
	}

}
