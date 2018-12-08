import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';
import {EventAggregator} from 'aurelia-event-aggregator';
import {SurveyModelTransformer} from '../transformer/survey-model-transformer';
import {HttpService} from './http-service';

@inject( HttpService, Router, SurveyModelTransformer, EventAggregator )
export class SurveyService {

  editedModel;

  constructor( httpService, router, surveyTransformer, eventAggregator ) {
    this.http = httpService;
    this.router = router;
    this.surveyTransformer = surveyTransformer;
    this.eventAggregator = eventAggregator;
  }

  setEditedModel( model ){
    console.log( 'start editing ' + model.name );
    if( this.editedModel ){
      console.log( 'already editing ' + this.editedModel );
      this.editedModel.finishEdit();
    }
    this.editedModel = model;
    console.log( 'editedModel = ' + this.editedModel );
  }

  unsetEditedModel(){
    if( this.editedModel ){
      this.editedModel.finishEdit();
      this.editedModel = undefined;
    }
  }

  isEditedModel(){
    return this.editedModel !== undefined;
  }

  loadSurvey( id ){
    return this.http.get( 'api/survey/' + id );
  }

  loadRespondentSurvey( id ){
    return this.http.get( 'api/respondentSurvey/' + id );
  }

  loadRealRespondentSurvey( checksum ){
    return this.http.get( 'realRespondentSurvey/' + checksum );
  }

  getSurveyLink( id ){
    return this.http.get( 'api/getSurveyLink/' + id );
  }

  loadAnswers( surveyId ){
    return this.http.get( '/api/answers/' + surveyId );
  }

  saveAnswers( data ){
    return this.http.post( 'saveAnswers', data );
  }

  unSetSurveyModel(){
    this.surveyModel = {};
  }

  saveSurvey( surveyModel ){
    return this.http.post( 'api/saveSurvey', this.surveyTransformer.serialize( surveyModel ) );
  }

  loadSurveys( surveyInfoList ){
    let promis = this.http.get( 'api/surveys' );
    promis.then(function(response) {
      console.log('response', response)
      return response.json()
    }).then(function(json) {
      console.log('parsed json', json)
      for ( let obj of json ) {
        surveyInfoList.push( obj );
      }
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });
  }

  initNewSurveyModel(){
    let surveyModel = {};
    surveyModel.title = "New survey";
    surveyModel.type = "SurveyModel";
    surveyModel.questionnaire = {};
    surveyModel.questionnaire.type = 'questionnaire';
    surveyModel.questionnaire.elements = [];
    surveyModel.surveySettings = {};
    surveyModel.surveySettings.showQuestionNumber = true;

    return surveyModel;
  }

}
