import 'css/common/custom-table.css';

import {computedFrom, inject} from 'aurelia-framework';
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class TableCell {

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;
  }

  bind(bindingContext, overrideContext) {
    // console.log(bindingContext);
    this.cell = bindingContext.cell;

  }

  attached(argument) {

  }

  @computedFrom( 'cell.width' )
  get style(){
    if( this.cell ){
      return "width: " + this.cell.width + "px";
    }else{
      return "";
    }

  }

  activate( data ) {

  }

}
