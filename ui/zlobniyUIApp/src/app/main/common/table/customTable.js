import 'css/common/custom-table.css';

import {bindable,inject} from 'aurelia-framework';
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class CustomTable {

  @bindable data;

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;


  }

  bind(bindingContext, overrideContext) {
    // console.log(bindingContext);


  }

  attached(argument) {
    // console.log('attached table');
  }



  activate( data ) {
    // console.log(data);
  }

}
