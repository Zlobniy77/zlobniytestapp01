import "css/panel.css";

import {BindingEngine, computedFrom, inject} from "aurelia-framework";
import {EventAggregator} from "aurelia-event-aggregator";
import {NavigationService} from "../../services/navigation-service";
import {PanelService} from "../../services/panel-service";
import {ClientService} from "../../services/client-service";
import {WizardService} from "../../services/wizard-service";
import $ from "jquery";

@inject( EventAggregator, NavigationService, PanelService, ClientService, WizardService, BindingEngine )
export class Panel {

  isOpenedColumns = false;

  constructor( eventAggregator, navigationService, panelService, clientService, wizardService, bindingEngine ) {
    this.eventAggregator = eventAggregator;
    this.navigation = navigationService;
    this.panelService = panelService;
    this.clientService = clientService;
    this.wizardService = wizardService;
    this.bindingEngine = bindingEngine;

    this.data = {
      headers: [],
      rows:[],
      selectedRows: [],
    };

    let subscription = this.bindingEngine.collectionObserver( this.data.rows )
      .subscribe( this.collectionChanged.bind(this) );

    this.initPanelMouseHandler();

    this.tableSettings = {
      height: 200,
      // width: 300,
      checkboxColumn: true,
    };


    // this.data = {
    //   headers: [
    //     {title: 'val 1', index: 0, subType: 'headers', type: 1},
    //     {title: 'val 2', index: 1, subType: 'headers', type: 0},
    //     {title: 'val 3', index: 2, subType: 'headers', type: 0},
    //     {title: 'val 4', index: 3, subType: 'headers', type: 0},
    //     {title: 'val 5', index: 4, subType: 'headers', type: 0},
    //   ],
    //
    //   rows: [
    //     {
    //       index: 0, cells: [
    //         {title: 'cell 1', index: 0, rowIndex: 0, subType: 'rows', valueType: 1},
    //         {title: 'cell 2', index: 1, rowIndex: 0, subType: 'rows', valueType: 0},
    //         {title: 'cell 3', index: 2, rowIndex: 0, subType: 'rows', valueType: 0},
    //         {title: 'cell 4', index: 3, rowIndex: 0, subType: 'rows', valueType: 0},
    //         {title: 'cell 5', index: 4, rowIndex: 0, subType: 'rows', valueType: 0},
    //       ],
    //       checkboxColumn: false,
    //       checkboxValue: false,
    //     },
    //     {
    //       index: 1, cells: [
    //         {title: 'cell 11', index: 0, rowIndex: 1, subType: 'rows', valueType: 1},
    //         {title: 'cell 12', index: 1, rowIndex: 1, subType: 'rows', valueType: 0},
    //         {title: 'cell 13', index: 2, rowIndex: 1, subType: 'rows', valueType: 0},
    //         {title: 'cell 14', index: 3, rowIndex: 1, subType: 'rows', valueType: 0},
    //         {title: 'cell 15', index: 4, rowIndex: 1, subType: 'rows', valueType: 0},
    //       ],
    //       checkboxColumn: false,
    //       checkboxValue: true,
    //     },
    //     {
    //       index: 2, cells: [
    //       {title: 'cell 11', index: 0, rowIndex: 1, subType: 'rows', valueType: 1},
    //       {title: 'cell 12', index: 1, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 13', index: 2, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 14', index: 3, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 15', index: 4, rowIndex: 1, subType: 'rows', valueType: 0},
    //     ],
    //       checkboxColumn: false,
    //       checkboxValue: true,
    //     },
    //     {
    //       index: 3, cells: [
    //       {title: 'cell 11', index: 0, rowIndex: 1, subType: 'rows', valueType: 1},
    //       {title: 'cell 12', index: 1, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 13', index: 2, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 14', index: 3, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 15', index: 4, rowIndex: 1, subType: 'rows', valueType: 0},
    //     ],
    //       checkboxColumn: false,
    //       checkboxValue: true,
    //     },
    //     {
    //       index: 4, cells: [
    //       {title: 'cell 11', index: 0, rowIndex: 1, subType: 'rows', valueType: 1},
    //       {title: 'cell 12', index: 1, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 13', index: 2, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 14', index: 3, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 15', index: 4, rowIndex: 1, subType: 'rows', valueType: 0},
    //     ],
    //       checkboxColumn: false,
    //       checkboxValue: true,
    //     },
    //     {
    //       index: 5, cells: [
    //       {title: 'cell 11', index: 0, rowIndex: 1, subType: 'rows', valueType: 1},
    //       {title: 'cell 12', index: 1, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 13', index: 2, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 14', index: 3, rowIndex: 1, subType: 'rows', valueType: 0},
    //       {title: 'cell 15', index: 4, rowIndex: 1, subType: 'rows', valueType: 0},
    //     ],
    //       checkboxColumn: false,
    //       checkboxValue: true,
    //     },
    //   ],
    //
    // }



  }

  collectionChanged(splices) {
    console.log('rows changed');
    // This will fire any time the collection is modified.
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

  addColumn( event, type ){
    //event.stopPropagation();
    console.log( type );

    let column = this.createColumn( type );


    this.data.headers.push( column );
    this.fillRowsWithEmptyValues( column.index, type );
    this.isOpenedColumns = false;

  }

  addRow(){

    let lastRow = this.data.rows[this.data.rows.length - 1];
    let valid = this.data.rows.length > 0 ? this.validateRow( lastRow ) : true;
    if( valid ){
      this.createRow();
    }else{
      lastRow.cells.forEach(function( cell ) {
        cell.valid = false;
      });
    }
  }

  /**
   * Check that row contains data (not empty)
   * */
  validateRow( row ){
    let hasNotEmptyCell = false;
    row.cells.forEach(function( cell ) {
      if( !hasNotEmptyCell && ( cell.title.trim().length > 0 ) ){
        hasNotEmptyCell = true;
      }
      if( cell.valid === false ){
        cell.valid = true;
      }
    });
    return hasNotEmptyCell;
  }

  createColumn( type ){
    let index = this.data.headers.length;
    return {
      title: 'val ',
      index: index,
      subType: 'headers',
      isNew: true,
      type: type,
      width: 150,
    };
  }

  createRow(){
    let index = this.data.rows.length;
    let row = {
      index: index,
      cells: [],
    };

    this.data.headers.forEach(function( column ) {
      let cell = {
        title: ' ',
        index: column.index,
        rowIndex: row.index,
        subType: 'rows',
        isNew: false,
        type: column.type,
        width: column.width,
      };

      row.cells.push( cell );
    });

    row.cells[0].isNew = true;
    this.data.rows.push( row );
  }

  fillRowsWithEmptyValues( columnIndex, type ){
    let head = this.data.headers;

    this.data.rows.forEach(function( row ) {
      row.cells.push(
        {title: ' ',
          index: columnIndex,
          rowIndex: row.index,
          subType: 'rows',
          type: type,
          headerLink: head[columnIndex],
        },
      );
    });

  }

  attached() {
    console.log('attached panel ');
    document.addEventListener( 'click', this.panelMouseHandler );

    this.initEventSource();
  }

  initEventSource(){

    let that = this;

    // change title in table and panel title
    this.changeTitle = this.eventAggregator.subscribe('change.title', event => {

      let item = event.item;
      let value = event.value;

      let currentItem = that.find( that.data, item );
      if( currentItem ){
        currentItem.title = value;
      }

      that.wizardService.unsetEditedModel();
    });

  }

  find( data, item ){
    let value;
    let array = [];
    if( item.type === 'panel' ){
      value = item;
    }else if( item.subType === 'headers' ){
      array = data[item.subType];
    }else{
      array = data[item.subType][item.rowIndex].cells;
    }

    array.forEach(function( element ) {
      if( element.index === item.index ){
        value = element;
      }
    });
    return value;
  }

  destroyEventSource(){
    this.changeTitle.dispose();
  }

  detached() {
    document.removeEventListener( 'click', this.panelMouseHandler );
    this.navigation.setTitle( {} );
    console.log('detached panel ');

    this.destroyEventSource();
  }

  activate(data) {
    console.log('activate panel ' + data);
    let that = this;

    if( data && data.id ){
      this.fetchPanel( data.id );
    }else{
      this.initPanelModel();
    }


    let buttons = [
      {
        title: 'Exit', action: () => {
          this.navigation.goTo( this.navigation.NAV_DASHBOARD );
        }, css: 'fa fa-arrow-circle-left'
      },
      {
        title: 'Save', action: () => {

          this.panel.headers = that.data.headers;
          this.panel.rows = that.data.rows;

          this.panelService.savePanel( this.panel, this.clientService.getCurrentFolder() );
        }, css: 'fas fa-save'
      }
    ];

    this.navigation.setButtons(buttons);
    this.navigation.setTitle( this.panel, true );

  }

  fetchPanel( id ){
    this.panel = {};
    let that = this;

    this.panelService.loadPanel( id )
      .then( function ( response ) {
        return response.json()
      } ).then( function ( panelModel ) {
      console.log( 'parsed json', panelModel );
      that.panel = $.extend(true, {}, panelModel);
      that.data.headers = that.panel.headers;

      that.data.headers.forEach(function( header ) {
        header.subType = 'headers';
      });

      that.data.rows = that.panel.rows;
      that.data.rows.forEach(function( row ) {
        row.cells.forEach(function( cell ) {
          cell.subType = 'rows';
          cell.rowIndex = row.index;
        });
      });

      that.navigation.setTitle( that.panel, true );
    } ).catch( function ( ex ) {
      console.log( 'parsing failed', ex )
    } );

  }

  initPanelModel(){
    this.panel = {};
    this.panel.title = 'new panel';
    this.panel.type = 'panel';
    this.panel.header = {};
    this.panel.body = {};
  }

  @computedFrom( 'rows.checkboxValue' )
  get hasDataSelected(){

    let rowsSelected = [];
    this.data.rows.forEach(function( row ) {
      if( row.checkboxValue ){
        rowsSelected.push( row );
      }
    });

    return rowsSelected.length > 0;
  }

  deleteSelectedRow(event){
    console.log('delete rows');
  }

}
