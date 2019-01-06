import 'css/main.css';

import {inject} from 'aurelia-framework';
import {EventAggregator} from 'aurelia-event-aggregator';
import {NavigationService} from "../../services/navigation-service";
import {PanelService} from "../../services/panel-service";
import {ClientService} from "../../services/client-service";

@inject( EventAggregator, NavigationService, PanelService, ClientService )
export class Panel {

  constructor( eventAggregator, navigationService, panelService, clientService ) {
    console.log('constructor panel ');
    this.eventAggregator = eventAggregator;
    this.navigation = navigationService;
    this.panelService = panelService;
    this.clientService = clientService;

    this.initSurveyMouseHandler();

    this.header = '';
    this.body = '';
  }

  initSurveyMouseHandler(){
    let that = this;
    this.surveyMouseHandler = e => {
      console.log( e.target.dataset.type );
      if ( e.target.dataset.type === 'editable' ) {
        //console.log( that.name + " " + e.target );
      } else {
        if ( that.panelService.isEditedModel() ) {
          that.panelService.unsetEditedModel();
        } else {
          //console.log( 'nothing to edit' );
        }
      }

    };
  }

  attached() {
    console.log('attached panel ');
    document.addEventListener( 'click', this.surveyMouseHandler );
    this.changeTitleSub = this.eventAggregator.subscribe('change.title', event => {

      let value = event.value;
      this.panel.title = value;

    });

  }

  detached() {
    document.removeEventListener( 'click', this.surveyMouseHandler );
    this.changeTitleSub.dispose();
    console.log('detached panel ');
  }

  activate(data) {
    console.log('activate panel ' + data);

    this.initPanelModel();

    let buttons = [
      {
        title: 'Exit', action: () => {
          this.navigation.goTo( this.navigation.NAV_DASHBOARD );
        }, css: 'fa fa-arrow-circle-left'
      },
      {
        title: 'Save', action: () => {

          let data = this.parseData();
          this.panel.header = data.header;
          this.panel.body = data.body;

          this.panelService.savePanel( this.panel, this.clientService.getCurrentFolder() );
        }, css: 'fas fa-save'
      }
    ];

    this.navigation.setButtons(buttons);
    this.navigation.setTitle( this.panel, true );

  }

  initPanelModel(){
    this.panel = {};
    this.panel.title = 'new panel';
    this.panel.header = {};
    this.panel.body = {};
  }

  parseData(){

    let header = this.parseHeader();
    let body = this.parseBody();

    let data = {
      header: header,
      body: body,
    };

    return data;
  }

  parseHeader(){

    let data = this.header.split(';');

    return data;

  }

  parseBody(){

    let data = [];

    let body = this.body.split('\n');

    for (const rowElement of body) {
      let row = rowElement.split(';');
      data.push( {row} );
    }

    return data;
  }

}
