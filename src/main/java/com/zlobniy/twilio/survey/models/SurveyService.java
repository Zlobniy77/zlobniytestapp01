package com.zlobniy.twilio.survey.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SurveyService {
  // MongoClient and Morphia instances must be accessible to the entire object, so a Datastore can
  // be built.
//  MongoClient mongoClient;
//  Morphia morphia;

  // An instance of Datastore must be accessible to the entire object, so all instance methods can
  // persist to and read from the datastore.

  private Map<String, Survey> database = new HashMap<>(  );

  // Constructor
  public SurveyService() {
    try {
      // Create MongoDB drivers
      //mongoClient = new MongoClient(mongoURI);
//      morphia = new Morphia();

      // Ask the Morphia driver to scan the Models package for models.
//      morphia.mapPackage("com.twilio.survey.models");

    } catch (Exception e) {
      // Catch any MongoDB configuration errors, and pass them back to STDERR.
      System.err.println(e.getMessage());
    } finally {
      // Create a datastore with the database name provided.
      //datastore = morphia.createDatastore(mongoClient, Server.config.getMongoDBName());
    }
  }


  // Find, Update, and Create -- database operations.
  public Survey getSurvey(String phone) {
    return database.getOrDefault( phone, null );
    //return datastore.find(Survey.class).field("phone").equal(phone).get();
  }

  public void updateSurvey(Survey survey) {

    Survey survey1 = database.get( survey.getPhone() );
    survey1.setDone( survey.isDone() );
    survey1.setId( survey.getId() );
    survey1.setIndex( survey.getIndex() );
    survey1.setResponses( survey.getResponses() );
//
//    UpdateOperations<Survey> updates = datastore.createUpdateOperations(Survey.class);
//    updates.set("index", survey.getIndex());
//    updates.set("responses", survey.getResponses());
//    updates.set("done", survey.isDone());
//    datastore.update(survey, updates);
  }

  public Survey createSurvey(String phone) {

    Survey survey = database.get( phone );
    if (survey == null) {
      survey = new Survey(phone);
      database.put( phone, survey );
      return survey;
    } else {
      return survey;
    }

//    Survey existingSurvey = getSurvey(phone);
//    if (existingSurvey == null) {
//      Survey survey = new Survey(phone);
//      datastore.save(survey);
//      return survey;
//    } else {
//      return existingSurvey;
//    }
  }

  public List<Survey> findAllFinishedSurveys() {

    List<Survey> surveys = database.values().stream().filter( Survey::isDone ).collect( Collectors.toList());
    return surveys;
//    return datastore.find(Survey.class).field("done").equal(true).asList();
  }
}
