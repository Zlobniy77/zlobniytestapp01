import 'css/survey.css';

import {inject} from 'aurelia-framework';
import {HttpService} from "./http-service";

@inject( HttpService )
export class FolderService {


  constructor( httpService ) {
    this.http = httpService;
  }

  loadData( data, rootNode ){

    //rootNode.addChildren( {title:'test folder', id: 123} );

    // let prepared =
    //   {"id": 1,"title": "/", "expanded": true, "folder": true, "children": [
    //     {"id": 2,"title": "dev", "folder": true},
    //     {"id": 3,"title": "etc", "folder": true, "children": [
    //       {"id": 4,"title": "cups"},
    //       {"id": 5,"title": "httpd"},
    //       {"id": 6,"title": "init.d"}
    //     ]},
    //     {"id": 7,"title": "sbin", "folder": true},
    //     {"id": 8,"title": "tmp", "folder": true},
    //     {"id": 9,"title": "Users", "expanded": true, "folder": true, "children": [
    //       {"title": "jdoe", "folder": true},
    //       {"title": "jmiller", "folder": true},
    //       {"title": "mysql", "folder": true}
    //     ]},
    //     {"title": "usr", "expanded": true, "folder": true, "children": [
    //       {"title": "bin", "folder": true},
    //       {"title": "lib", "folder": true},
    //       {"title": "local", "folder": true}
    //     ]},
    //     {"title": "var", "expanded": true, "folder": true, "children": [
    //       {"title": "log", "folder": true},
    //       {"title": "spool", "folder": true},
    //       {"title": "yp", "folder": true}
    //     ]}
    //   ]};
    //
    // rootNode.addChildren( prepared );

    let promis = this.http.get( 'api/folder/loadAll' );
    promis.then(function(response) {
      console.log('response', response);
      return response.json()
    }).then(function(json) {
      console.log('parsed json', json);

      for ( let obj of json ) {
        rootNode.addChildren( obj );
      }

    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });
  }

  attached() {
    console.log( 'attached FolderService ' );
  }

  detached() {
    console.log( 'detached FolderService ' );
  }

  updateFolderStatus( id, param, status ){

    this.http.put( 'api/folder/' + id + "/" + param + "/" + status );
  }

  moveFolder( objectId, destination ){

    let data = {
      destination: destination,
      object: objectId,
    };

    this.http.post( 'api/folder/move', data );

  }


}
