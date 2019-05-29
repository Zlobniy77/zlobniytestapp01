import 'css/survey.css';

import {bindable, inject} from 'aurelia-framework';
import {WizardService} from "../../../services/wizard-service";
import {EventAggregator} from 'aurelia-event-aggregator';
import {EventSources} from "../../../services/event-sources";
import {Ui} from "../../../ui";

@inject( WizardService, EventAggregator, EventSources, Ui )
export class EditableItem extends Ui {

  @bindable item;
  @bindable editMode;
  edit;

  constructor( wizardService, eventAggregator, eventSources, ...rest ) {
    super(...rest);
    this.wizardService = wizardService;
    this.eventAggregator = eventAggregator;
    this.eventSources = eventSources;
    this.edit = false;
  }

  startEdit(){
    if( !this.edit ){
      this.wizardService.setEditedModel( this );
      this.edit = true;
    }
  }

  changeText( event ){
    let data = {
      item: this.item,
      value: event.target.value,
    };

    this.eventSources.addEvent( 'change.title', data );
  }

  finishEdit(){
    if ( this.edit ) {
      this.edit = false;
      if( this.item.title === '' ){
        let data = {
          questionNumber: this.item.qNumber,
          index: this.item.index,
        };
        this.eventSources.addEvent( 'option.remove', data );
        //this.removeOption( this.item.index );
      }
    }

  }

  // removeOption( index ){
  //   this.eventAggregator.publish( this.item.optionsId + '-remove', index );
  // }

  attached() {
    if( this.item && this.item.isNew ){
      this.startEdit();
      this.item.isNew = false;
    }
  }

  detached() {

  }

}
