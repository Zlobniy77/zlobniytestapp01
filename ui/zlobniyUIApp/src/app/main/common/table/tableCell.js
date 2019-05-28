import 'css/common/custom-table.css';

import {bindable,inject} from 'aurelia-framework';
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class TableCell {

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;

    this.value = "test*"
  }

  bind(bindingContext, overrideContext) {
    // console.log(bindingContext);
    this.cell = bindingContext.cell;
    this.value = this.cell.value;

  }

  attached(argument) {

  }

  activate( data ) {

  }

}
