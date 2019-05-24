import 'css/main.css';

import {inject} from 'aurelia-framework';
import {HttpService} from "./http-service";

@inject( HttpService )
export class PanelService {

  constructor( http ) {
    this.http = http;
    console.log('constructor PanelService ');
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
