import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {HttpService} from '../../services/http-service';
import {ClientService} from "../../services/client-service";
import {FolderTree} from "../leftbar/folder-tree";


@inject( HttpService, ClientService, FolderTree )
export class Folder {

  constructor( httpService, clientService, folderTree ) {

    this.http = httpService;
    this.clientService = clientService;
    this.folderTree = folderTree;
    this.title = 'New folder';
  }



  attached() {

  }

  detached() {

  }

  activate( data ){
    this.closeAction = data.closeAction;
  }

  cancelButton(){
    this.closeAction.call();
  }

  okButton(){
    let url = '/api/folder/create';
    let data = {};
    data.title = this.title;
    data.parentId = this.clientService.getCurrentFolder();

    let promise = this.http.post( url, data );

    promise.then( (response) => {
      return response.json()
    }).then( (response) => {
      this.closeAction.call();
      this.folderTree.addNode( response );
    } );

  }


}
