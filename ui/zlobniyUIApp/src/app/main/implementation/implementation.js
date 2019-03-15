import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {HttpService} from '../../services/http-service';
import {ClientService} from "../../services/client-service";
import {PanelService} from "../../services/panel-service";


@inject( HttpService, ClientService, PanelService )
export class Implementation {

  closeAction;
  panels;
  panelId;
  surveyId;

  constructor( httpService, clientService, panelService ) {
    this.http = httpService;
    this.clientService = clientService;
    this.panelService = panelService;
  }

  attached() {

  }

  detached() {

  }

  dropdownChanged( value ){
    this.panelId = value;
    console.log( value );
  }

  activate( data ){
    let that = this;
    this.surveyId = data.id;
    this.closeAction = data.closeAction;
    let clientId = this.clientService.clientInfo.id;

    let promis = this.panelService.loadPanels( clientId );

    promis.then(function(response) {
      return response.json()
    }).then(function(json) {
      that.panels = json;
      that.panelId = json[0].id;
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });

  }

  finish(){

    let data = {
      surveyId: this.surveyId,
      panelId: this.panelId,
      clientId: this.clientService.clientInfo.id,
    };

    let url = "api/startSurvey";

    this.http.post( url, data );

    this.closeAction.call();
  }

}
