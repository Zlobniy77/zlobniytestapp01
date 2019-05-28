import 'css/common/custom-table.css';

import {bindable,inject} from 'aurelia-framework';
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class TableRow {

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;

  }

  bind(bindingContext, overrideContext) {
    // console.log(bindingContext);
    this.row = bindingContext.row;
  }

  attached(argument) {
    // console.log('attache rows');
  }

  activate( data ) {

  }

}
