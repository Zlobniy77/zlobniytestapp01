import "css/common/custom-table.css";

import {inject} from "aurelia-framework";
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class HeaderTableCell {

  isOpen = false;
  changedValue;

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;
  }

  bind(bindingContext, overrideContext) {
    // console.log(bindingContext);
    this.cell = bindingContext.cell;

    if( this.cell.valueType === 1 ){
      this.icon = 'fas fa-mail-bulk';
      this.description = 'email';
    }else{
      this.icon = 'as fa-align-justify';
      this.description = 'standard';
    }

  }

  attached(argument) {

  }

  change( index ){
    console.log( index );
  }

  changeType( event, type ){
    this.cell.valueType = type;
  }

  activate( data ) {

  }

}
