import 'css/main.css';

import {inject} from 'aurelia-framework';
import {HttpService} from "./http-service";

@inject( HttpService )
export class PanelService {

  constructor( http ) {
    this.http = http;
    console.log('constructor PanelService ');
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

  attached() {
    console.log('attached PanelService ');
  }

  detached() {
    console.log('detached PanelService ');
  }

  activate(data) {
    console.log('activate PanelService ' + data);


  }

  savePanel( panel, folderId ){
    console.log( 'save panel ' + panel );
    return this.http.post( 'api/panel/save/' + folderId, panel );
  }

  loadPanels( clientId ){

    let url = 'api/panel/all/' + clientId;
    return this.http.get( url );
  }

}
