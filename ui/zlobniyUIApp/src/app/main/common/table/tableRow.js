import "css/common/custom-table.css";

import {bindable, computedFrom, inject} from "aurelia-framework";
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class TableRow {

  @bindable checkboxColumn;
  @bindable headers;
  @bindable selectedRows;

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;

  }

  bind(bindingContext, overrideContext) {
    // console.log(bindingContext);
    this.row = bindingContext.row;
    let head = this.headers;

    this.row.cells.forEach(function( cell ) {
      cell.headerLink = head[cell.index];

    });

    // this.headers.forEach(function( column ) {
    //   let index = column.index;
    //   _row.cells[index].width
    // });

  }

  checkboxChanged( value ){
    console.log('changed' + value);
  }

  @computedFrom( 'row.checkboxValue' )
  get rowStyle(){
    let style = this.row.checkboxValue ? "background-color: aliceblue;" : " ";


    return style;
  }

  selectedChanged( newValue, oldValue ){
    console.log('selected changed');
  }

  attached(argument) {
    // console.log('attache rows');
  }

  activate( data ) {

  }

}
