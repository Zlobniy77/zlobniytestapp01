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

  @computedFrom( 'cell.headerLink.width', 'cell.valid' )
  get style(){
    if( this.cell ){
      let style = "max-width:" + this.cell.headerLink.width + "px; width:"+ this.cell.headerLink.width +"px;";
      if( this.cell.valid === false ){
        style = style + " border-color: red;";
      }
      return style;
    }else{
      return "";
    }

  }

  @computedFrom( 'cell.title' )
  get title(){

    if( this.cell ){

      if( this.cell.title.trim().length > 0 ){
        this.cell.valid = true;
      }

      return this.cell.title;
    }else{
      return "";
    }

  }

  activate( data ) {

  }

}
