import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {SurveyService} from "../../services/survey-service";
import {NavigationService} from "../../services/navigation-service";
import {ContextMenu} from "../common/context-menu";
import {Popup} from "../common/popup";
import {DialogService} from "aurelia-dialog";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ClientService} from "../../services/client-service";
import {Ui} from "../../ui";

@inject( SurveyService, NavigationService, DialogService, EventAggregator, ClientService, Ui )
export class Overview extends Ui {

  title = "-";

  constructor( surveyService, navigationService, dialogService, eventAggregator, clientService, ...rest ) {
    super(...rest);
    this.surveyService = surveyService;
    this.navigationService = navigationService;
    this.dialogService = dialogService;
    this.eventAggregator = eventAggregator;
    this.clientService = clientService;

    this.surveyInfoList = [];

    // this.surveyInfoList.push({title:'test', id:2, creationDate:'09/08/2018 17:46'});
    // this.surveyInfoList.push({title:'test 2', id:3, creationDate:'09/08/2018 17:46'});
  }



  contextMenu( id, event ){
    let contextMenu = this.getContextMenu( id, event );
    // show context menu
    this.dialogService.open({
      viewModel: ContextMenu,
      model: contextMenu,
      lock: false
    })
    .whenClosed( data => {}  );
  }

  attached() {
    let that = this;

    this.updateOverview = this.eventAggregator.subscribe('overview.update', id => {
      if (that.clientService.getCurrentFolder() === id) return;

      console.log('call update overview ' + id);
      that.surveyInfoList = [];// clear array, remove previous data from array

      that.surveyService.loadSurveysInFolder(id, that.surveyInfoList);
      that.clientService.setCurrentFolder( id );

    });

  }

  detached() {
    this.updateOverview.dispose();
  }

  activate(){
    this.surveyService.loadSurveysInFolder( this.clientService.getCurrentFolder(), this.surveyInfoList );
  }

  getContextMenu( id, event ){

    // get mouse position
    let position = {x:(event.clientX-30)+"px", y:(event.clientY-30)+"px"};

    let edit = this.editItem( id );
    let open = this.openItem( id );
    let showLinks = this.showLinksItem( id );

    let contextMenu = {id:'contextMenu', position: position, elements:[
      edit, open, showLinks,
    ]};

    return contextMenu;
  }

  editItem( id ){
    let that = this;
    return {index:0, title:'edit', action: function () {
      that.navigationService.goTo( that.navigationService.NAV_SURVEY + "/" + id )
    } };
  }

  openItem( id ){
    let that = this;
    return {index:1, title:'open', action: function () {
      let data = {};
      data.id = id;
      data.width = "70%";
      data.view = "app/main/respondent/survey-viewer";
      that.dialogService.open({
        viewModel: Popup,
        model: data,
        lock: false
      })
        .whenClosed( resp => {}  );
    } };
  }

  showLinksItem( id ){
    let that = this;
    return {index:2, title:'Get link', action: function () {

      that.surveyService.getSurveyLink( id )
        .then( function ( response ) {
          return response.json()
        } ).then( function ( response ) {
        let links = response.links;

        let data = {};
        data.links = links;

        data.width = "70%";
        data.view = "app/main/common/info-message";
        that.dialogService.open({
          viewModel: Popup,
          model: data,
          lock: false
        })
          .whenClosed( resp => {}  );

      } ).catch( function ( ex ) {
        console.log( 'parsing failed', ex )
      } );

    } };
  }

}
