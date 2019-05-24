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

  saveSurvey( surveyModel, folderId ){
    return this.http.post( 'api/saveSurvey/' + folderId, this.surveyTransformer.serialize( surveyModel ) );
  }

  loadSurveysInFolder( folderId, surveyInfoList ){
    let promis = this.http.get( 'api/folder/load/' + folderId );

    promis.then(function(response) {
      // console.log('response', response);
      return response.json();
    }).then(function(json) {
      // console.log('parsed json', json);
      surveyInfoList.push.apply( surveyInfoList, json );

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
