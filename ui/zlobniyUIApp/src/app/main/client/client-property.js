import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {HttpService} from '../../services/http-service';
import {ClientService} from "../../services/client-service";
import {FolderTree} from "../leftbar/folder-tree";


@inject( HttpService, ClientService, FolderTree )
export class ClientProperty {

  closeAction;
  property;

  constructor( httpService, clientService, folderTree ) {
    this.http = httpService;
    this.clientService = clientService;
    this.folderTree = folderTree;
  }

  attached() {

  }

  detached() {

  }

  activate( data ){
    let that = this;
    this.closeAction = data.closeAction;

    let promis = this.clientService.loadProperty();

    promis.then(function(response) {
      return response.json()
    }).then(function(json) {
      that.property = json;
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });

  }

  finish(){



    this.clientService.saveProperty( this.property );
    this.closeAction.call();
  }

}
