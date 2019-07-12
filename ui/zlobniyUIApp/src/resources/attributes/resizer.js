import {bindable, inject} from 'aurelia-framework';
import $ from 'jquery';

@inject( Element )
export class ResizerCustomAttribute {

  @bindable cell;

  constructor( element ) {
    this.element = element;
  }

  attached() {

    let _this = this;

    $( this.element ).append("<div class='resizer'></div>");

    let elem = $( this.element ).find('.resizer');

    elem.on("mousedown", function(e) {
      let LEFT_MOUSE_BUTTON = 1;
      let MIN_COLUMN_WIDTH = 20;

      if (e.which !== LEFT_MOUSE_BUTTON) {
        return;
      }

      let columnGrip = $(this);
      let column = $(columnGrip.parent()[0]);
      let columnIndex = column.index() - 1; // first column can't dragging!
      let columnOriginLeftX = column.position().left;

      let mouseStartX = e.clientX;
      let offsetX = e.offsetX;

      elem.addClass("columnDragging");

      let onMouseMove = function(e) {
        if (columnOriginLeftX + MIN_COLUMN_WIDTH >= e.clientX) {
          return;
        }
        columnGrip.offset({left: e.clientX - offsetX});
      };

      $("body").on("mousemove", onMouseMove);

      $("body").one("mouseup", function(e) {
        let deltaMouseX = e.clientX - mouseStartX;
        let oldColumnWidth = _this.cell.width;
        let newColumnWidth = oldColumnWidth + deltaMouseX;
        newColumnWidth = newColumnWidth >= MIN_COLUMN_WIDTH ? newColumnWidth : MIN_COLUMN_WIDTH;

        elem.removeClass("columnDragging");
        $("body").off("mousemove", onMouseMove);

        _this.cell.width = newColumnWidth;
        //_this.style.width = "width: " + newColumnWidth + "px;";
        // _this.model.setColumnWidth(columnIndex, newColumnWidth);
        // ClientActionService.saveColumns( _this.model.get("columns"), function() {} );
      });
    });

  }

  detached() {
    $( this.element ).remove(".resizer");
    // $( this.element ).tooltip('dispose');
  }

}
