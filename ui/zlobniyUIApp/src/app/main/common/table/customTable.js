import "css/common/custom-table.css";

import {bindable, inject} from "aurelia-framework";
import {DialogController} from "aurelia-dialog";

@inject( DialogController, Element )
export class CustomTable {

  @bindable data;
  @bindable settings;

  constructor( dialogController, element ) {
    this.dialogController = dialogController;
    this.element = element;


  }

  bind(bindingContext, overrideContext) {
    let that = this;
    this.style = "height: "+ this.settings.height + "px";


    this.data.rows.forEach(function( row ) {
      row.checkboxColumn = that.settings.checkboxColumn;
    });
  }

  attached(argument) {
    // console.log('attached table');
  }



  activate( data ) {
    // console.log(data);
  }

}
