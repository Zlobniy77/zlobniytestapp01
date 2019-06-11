import "css/panel.css";

import {inject} from "aurelia-framework";
import {EventAggregator} from "aurelia-event-aggregator";
import {NavigationService} from "../../services/navigation-service";
import {PanelService} from "../../services/panel-service";
import {ClientService} from "../../services/client-service";
import {WizardService} from "../../services/wizard-service";

@inject( EventAggregator, NavigationService, PanelService, ClientService, WizardService )
export class Panel {

  isOpenedColumns = false;

  constructor( eventAggregator, navigationService, panelService, clientService, wizardService ) {
    this.eventAggregator = eventAggregator;
    this.navigation = navigationService;
    this.panelService = panelService;
    this.clientService = clientService;
    this.wizardService = wizardService;

    this.initPanelMouseHandler();

    this.tableSettings = {
      height: 200,
      // width: 300,
      checkboxColumn: true,
    };

    this.header = '';
    this.body = '';

    this.data = {
      headers: [
        {title: 'val 1', index: 0, type: 'headers', valueType: 1},
        {title: 'val 2', index: 1, type: 'headers', valueType: 0},
        {title: 'val 3', index: 2, type: 'headers', valueType: 0},
        {title: 'val 4', index: 3, type: 'headers', valueType: 0},
        {title: 'val 5', index: 4, type: 'headers', valueType: 0},
      ],

      rows: [
        {
          index: 0, cells: [
            {title: 'cell 1', index: 0, rowIndex: 0, type: 'rows', valueType: 1},
            {title: 'cell 2', index: 1, rowIndex: 0, type: 'rows', valueType: 0},
            {title: 'cell 3', index: 2, rowIndex: 0, type: 'rows', valueType: 0},
            {title: 'cell 4', index: 3, rowIndex: 0, type: 'rows', valueType: 0},
            {title: 'cell 5', index: 4, rowIndex: 0, type: 'rows', valueType: 0},
          ],
          checkboxColumn: false,
          checkboxValue: false,
        },
        {
          index: 1, cells: [
            {title: 'cell 11', index: 0, rowIndex: 1, type: 'rows', valueType: 1},
            {title: 'cell 12', index: 1, rowIndex: 1, type: 'rows', valueType: 0},
            {title: 'cell 13', index: 2, rowIndex: 1, type: 'rows', valueType: 0},
            {title: 'cell 14', index: 3, rowIndex: 1, type: 'rows', valueType: 0},
            {title: 'cell 15', index: 4, rowIndex: 1, type: 'rows', valueType: 0},
          ],
          checkboxColumn: false,
          checkboxValue: true,
        },
        {
          index: 2, cells: [
          {title: 'cell 11', index: 0, rowIndex: 1, type: 'rows', valueType: 1},
          {title: 'cell 12', index: 1, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 13', index: 2, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 14', index: 3, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 15', index: 4, rowIndex: 1, type: 'rows', valueType: 0},
        ],
          checkboxColumn: false,
          checkboxValue: true,
        },
        {
          index: 3, cells: [
          {title: 'cell 11', index: 0, rowIndex: 1, type: 'rows', valueType: 1},
          {title: 'cell 12', index: 1, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 13', index: 2, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 14', index: 3, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 15', index: 4, rowIndex: 1, type: 'rows', valueType: 0},
        ],
          checkboxColumn: false,
          checkboxValue: true,
        },
        {
          index: 4, cells: [
          {title: 'cell 11', index: 0, rowIndex: 1, type: 'rows', valueType: 1},
          {title: 'cell 12', index: 1, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 13', index: 2, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 14', index: 3, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 15', index: 4, rowIndex: 1, type: 'rows', valueType: 0},
        ],
          checkboxColumn: false,
          checkboxValue: true,
        },
        {
          index: 5, cells: [
          {title: 'cell 11', index: 0, rowIndex: 1, type: 'rows', valueType: 1},
          {title: 'cell 12', index: 1, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 13', index: 2, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 14', index: 3, rowIndex: 1, type: 'rows', valueType: 0},
          {title: 'cell 15', index: 4, rowIndex: 1, type: 'rows', valueType: 0},
        ],
          checkboxColumn: false,
          checkboxValue: true,
        },
      ],

    }



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
    this.createRow();
  }

  createColumn( type ){
    let index = this.data.headers.length;
    return {
      title: 'val ',
      index: index,
      type: 'headers',
      isNew: true,
      valueType: type,
    };
  }

  createRow(){
    let index = this.data.rows.length;
    let row = {
      index: index,
      cells: [],
      checkboxColumn: this.tableSettings.checkboxColumn,
      checkboxValue: false,
    };

    this.data.headers.forEach(function( column ) {
      let cell = {
        title: ' ',
        index: column.index,
        rowIndex: row.index,
        type: 'rows',
        isNew: false,
        valueType: column.valueType,
      };

      row.cells.push( cell );
    });

    row.cells[0].isNew = true;
    this.data.rows.push( row );
  }

  fillRowsWithEmptyValues( columnIndex, type ){

    this.data.rows.forEach(function( row ) {
      row.cells.push( {title: ' ', index: columnIndex, rowIndex: row.index, type: 'rows', valueType: type}, );
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
    }else if( item.type === 'headers' ){
      array = data[item.type];
    }else{
      array = data[item.type][item.rowIndex].cells;
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
    this.panel.type = 'panel';
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
