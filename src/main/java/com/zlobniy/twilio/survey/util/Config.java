package com.zlobniy.twilio.survey.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


public class Config {
  private int port;
  // Holds the name of the collection (helpful in the event that authentication is enforced)
  private String mongoDBName;
  private Question[] questions;

  public Config() {
    Map<String, String> env = System.getenv();

    // Set defaults, and override with environment variables, if present.

    // default to port 4567, the idiomatic Spark port.
    port = 4567;
    // default to localhost, the idiomatic MongoDB host.
    // Check the environment for the presence of configured variables.
    if (env.containsKey("PORT")) {
      port = Integer.valueOf(env.get("PORT"));
    }

    questions = parseQuestionFile();
  }

  private Question[] parseQuestionFile() {
    String questionFileAsJson = importQuestionFile();

    ObjectMapper mapper = new ObjectMapper();

//    Gson gson = new Gson();
//    Question[] q = gson.fromJson(questionFileAsJson, Question[].class);
    Question[] q = new Question[0];
    try{
      q = mapper.readValue( questionFileAsJson, Question[].class );
    } catch( IOException e ){
      e.printStackTrace();
    }
    return q;
  }

  private String importQuestionFile() {
    String questionFileAsString = "";
    try {
      Scanner questionFile = new Scanner(new File("questions.json"));
      while (questionFile.hasNextLine()) {
        questionFileAsString += questionFile.nextLine();
      }
      questionFile.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return questionFileAsString;
  }

  public int getPort() {
    return port;
  }

  public String getMongoDBName() {
    return mongoDBName;
  }

  public Question[] getQuestions() {
    return questions;
  }

}
