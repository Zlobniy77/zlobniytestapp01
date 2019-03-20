import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {HttpService} from '../../services/http-service';
import {ClientService} from "../../services/client-service";
import {PanelService} from "../../services/panel-service";


@inject( HttpService, ClientService, PanelService )
export class Exports {

  answers;

  constructor( httpService, clientService, panelService ) {
    this.http = httpService;
    this.clientService = clientService;
    this.panelService = panelService;
  }

  attached() {

  }

  detached() {

  }


  activate( data ){
    let that = this;
    this.surveyId = data.id;
    this.closeAction = data.closeAction;
    let clientId = this.clientService.clientInfo.id;

    let url = 'api/results/'+data.id;

    let promis = this.http.get( url );

    promis.then(function(response) {
      return response.json()
    }).then(function(json) {
      that.answers = json;
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });

  }

  finish(){

    console.log('export close');

    this.closeAction.call();
  }

}
