import 'css/main.css';

import {inject} from 'aurelia-framework';
import 'jquery.fancytree/dist/skin-lion/ui.fancytree.css'; // CSS or LESS
import {createTree, getTree} from 'jquery.fancytree';
import 'jquery.fancytree/dist/modules/jquery.fancytree.edit';
import 'jquery.fancytree/dist/modules/jquery.fancytree.filter';
import 'jquery.fancytree/dist/modules/jquery.fancytree.dnd5';
import {FolderService} from "../../services/folder-service";
import {SurveyService} from "../../services/survey-service";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ClientService} from "../../services/client-service";

@inject( FolderService, SurveyService, EventAggregator, ClientService )
export class FolderTree {

  constructor( folderService, surveyService, eventAggregator, clientService ) {
    this.folderService = folderService;
    this.surveyService = surveyService;
    this.eventAggregator = eventAggregator;
    this.clientService = clientService;
  }


  attached() {
    console.log( 'attached FolderTree ' );
    let that = this;

    let data = [];

    this.tree = createTree( '#tree', {
      extensions: ['dnd5', 'edit', 'filter'],
      source: data,
      dnd5: {
        // autoExpandMS: 400,
        // preventForeignNodes: true,
        // preventNonNodes: true,
        // preventRecursiveMoves: true, // Prevent dropping nodes on own descendants
        // preventVoidMoves: true, // Prevent dropping nodes 'before self', etc.
        // scroll: true,
        // scrollSpeed: 7,
        // scrollSensitivity: 10,

        // --- Drag-support:

        dragStart: function ( node, data ) {
          /* This function MUST be defined to enable dragging for the tree.
           *
           * Return false to cancel dragging of node.
           * data.dataTransfer.setData() and .setDragImage() is available
           * here.
           */
//          data.dataTransfer.setDragImage($("<div>hurz</div>").appendTo("body")[0], -10, -10);
          return true;
        },
        dragDrag: function ( node, data ) {
          data.dataTransfer.dropEffect = "move";
        },
        dragEnd: function ( node, data ) {
        },

        // --- Drop-support:

        dragEnter: function ( node, data ) {
          // node.debug("dragEnter", data);
          data.dataTransfer.dropEffect = "move";
          // data.dataTransfer.effectAllowed = "copy";
          return true;
        },
        dragOver: function ( node, data ) {
          data.dataTransfer.dropEffect = "move";
          // data.dataTransfer.effectAllowed = "copy";
        },
        dragLeave: function ( node, data ) {
        },
        dragDrop: function ( node, data ) {
          /* This function MUST be defined to enable dropping of items on
           * the tree.
           */
          var transfer = data.dataTransfer;

          node.debug( "drop", data );

          // this folder will be moved.
          let objectId = data.otherNode.data.id;
          // in this folder
          let destination = node.data.id;

          that.folderService.moveFolder( objectId, destination );

          // alert("Drop on " + node + ":\n"
          //   + "source:" + JSON.stringify(data.otherNodeData) + "\n"
          //   + "hitMode:" + data.hitMode
          //   + ", dropEffect:" + transfer.dropEffect
          //   + ", effectAllowed:" + transfer.effectAllowed);

          if ( data.otherNode ) {
            // Drop another Fancytree node from same frame
            // (maybe from another tree however)
            var sameTree = (data.otherNode.tree === data.tree);

            data.otherNode.moveTo( node, data.hitMode );
          } else if ( data.otherNodeData ) {
            // Drop Fancytree node from different frame or window, so we only have
            // JSON representation available
            node.addChild( data.otherNodeData, data.hitMode );
          } else {
            // Drop a non-node
            node.addNode( {
              title: transfer.getData( "text" )
            }, data.hitMode );
          }
          node.setExpanded();
        }
      },
      activate: function( event, data ){

        for (const selectedNode of data.tree.getSelectedNodes()) {
          selectedNode.setActive( false );
          selectedNode.setSelected( false );
          selectedNode.selected = false;
        }

        let id = data.node.data.id;
        console.log( 'activate node ' + id );
        that.folderService.updateFolderStatus( id, 'select', true );
        that.clientService.setCurrentFolder( id );
      },
      deactivate: function(event, data) {
        let id = data.node.data.id;
        data.node.selected = false;
        console.log('deactivate node ' + id );
        that.folderService.updateFolderStatus( id, 'select', false );
      },
      expand: function(event, data) {
        let id = data.node.data.id;
        console.log( 'expand node ' + id );
        that.folderService.updateFolderStatus( id, 'expand', true );
      },
      collapse: function(event, data) {
        let id = data.node.data.id;
        console.log( 'collapse node ' + id );
        that.folderService.updateFolderStatus( id, 'expand', false );
      },
      lazyLoad: function ( event, data ) {
        data.result = [{"title": "Sub item", "lazy": false}, {"title": "Sub folder", "folder": true, "lazy": true}];
      },
      click: function ( event, data ) {
        // let id = data.node.data.id;
        // if( event.toElement.className === 'fancytree-expander' ){
        //   that.folderService.expandFolder( id, !data.node.expanded );
        //   return true;
        // }
        //
        // that.eventAggregator.publish( 'overview.update', id );
        // return false to prevent default behavior (i.e. activation, ...)
        return true;
      },

    } );

    let rootNode = this.tree.rootNode;
    this.folderService.loadData( data, rootNode );

  }

  addNode( node ){
    let tree = getTree("#tree");
    let root = tree.getActiveNode();
    root.addNode( node );
  }

  detached() {
    console.log( 'detached FolderTree ' );
  }

  activate( data ) {
    console.log( 'activate folder tree ' + data );
  }

}
