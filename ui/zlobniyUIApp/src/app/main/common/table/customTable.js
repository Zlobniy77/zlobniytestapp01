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

    if( this.settings.width ){
      this.width  = "width: "+ this.settings.width + "px;";
      this.headerWidth =  "width: "+ (this.settings.width - 17) + "px;";
    }else{
      this.width  = "width: 100%;";
      this.headerWidth =  "width: 99%";
    }
    // this.headerWidth =  "width: "+ (this.settings.width - 17) + "px;";

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
