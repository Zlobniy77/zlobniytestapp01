import "css/common/custom-table.css";

import {bindable, inject} from "aurelia-framework";
import {DialogController} from "aurelia-dialog";
import $ from "jquery";

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
    this.height = "height: "+ this.settings.height + "px;";
    this.width  = "width: "+ this.settings.width + "px;";
    this.headerWidth =  "width: "+ (this.settings.width - 17) + "px;";


    this.data.rows.forEach(function( row ) {
      row.checkboxColumn = that.settings.checkboxColumn;
    });


  }


  initScroll(){
    $('.custom-table-body').scroll(function() {
      let pos = $('.custom-table-body').scrollLeft();
      $('.custom-table-header').animate({
        scrollLeft: pos,
      }, 0);
    });
  }

  attached(argument) {
    this.initScroll();
    // console.log('attached table');
  }



  activate( data ) {
    // console.log(data);
  }

}
