import 'css/panel.css';

import {inject} from 'aurelia-framework';
import {EventAggregator} from 'aurelia-event-aggregator';
import {NavigationService} from "../../services/navigation-service";
import {PanelService} from "../../services/panel-service";
import {ClientService} from "../../services/client-service";
import {WizardService} from "../../services/wizard-service";

@inject( EventAggregator, NavigationService, PanelService, ClientService, WizardService )
export class Panel {

  isOpenedColumns = false;

  constructor( eventAggregator, navigationService, panelService, clientService, wizardService ) {
    console.log('constructor panel ');
    this.eventAggregator = eventAggregator;
    this.navigation = navigationService;
    this.panelService = panelService;
    this.clientService = clientService;
    this.wizardService = wizardService;

    this.initPanelMouseHandler();

    this.header = '';
    this.body = '';

    this.data = {
      standardColumn: false,
    };
  }

  initPanelMouseHandler(){
    let that = this;
    this.panelMouseHandler = e => {
      let type = e.target.dataset.type;
      console.log( type );
      if ( type === 'editable' || type === 'selectable' ) {
        //console.log( that.name + " " + e.target );
      } else {
        if ( that.wizardService.isEditedModel() ) {
          that.wizardService.unsetEditedModel();
        } else {
          //console.log( 'nothing to edit' );
        }
        // hide opened selectable block
        that.isOpenedColumns = false;
      }
    };
  }

  switchOpenColumns( event ){
    this.isOpenedColumns = !this.isOpenedColumns;
  }

  addColumn( event, optionNumber ){
    //event.stopPropagation();
    console.log( optionNumber );

  }

  attached() {
    console.log('attached panel ');
    document.addEventListener( 'click', this.panelMouseHandler );
    this.changeTitleSub = this.eventAggregator.subscribe('change.title', event => {

      let value = event.value;
      this.panel.title = value;

    });

  }

  detached() {
    document.removeEventListener( 'click', this.panelMouseHandler );
    this.changeTitleSub.dispose();
    this.navigation.setTitle( {} );
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
