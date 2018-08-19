import {inject} from 'aurelia-framework';
import {EventAggregator} from 'aurelia-event-aggregator';

@inject( EventAggregator )
export class SurveyHelper {

  constructor( eventAggregator ) {
    this.eventAggregator = eventAggregator;
  }

  deleteItem( listOfItems, index ){
    listOfItems.splice( index, 1 );
    this.updateIndex( listOfItems );
  }

  deleteQuestion( surveyModel, index ){
    let deletedQuestion = surveyModel.questionnaire.elements[index];
    surveyModel.questionnaire.elements.splice( index, 1 );
    this.updateIndex( surveyModel.questionnaire.elements );
    if( deletedQuestion.selected ){
      if( surveyModel.questionnaire.elements.length > 0 ){
        if( surveyModel.questionnaire.elements.length === index ){
          index = index - 1;
        }
        let question = surveyModel.questionnaire.elements[index];
        this.selectQuestion( question, surveyModel, true );
      }else{
        this.eventAggregator.publish( 'show-settings', undefined );
      }
    }
  }

  selectQuestion( question, surveyModel, isNew ){
    let that = this;
    if( isNew || !question.selected ){
      that.doSelect( question, surveyModel );
    }else if( isNew ){
      that.doSelect( question, surveyModel );
    }
  }

  doSelect( question, surveyModel ){
    // deselect all question
    for ( let quest of surveyModel.questionnaire.elements ) {
      quest.selected = false;
    }
    // select current question, on clicked
    question.selected = true;
    // update settings, set question settings from selected question, isToggle false = not hide settings
    // window if it has opened, and open if closed.
    this.eventAggregator.publish( 'show-settings', {settings: question.settings, isToggle: false} );
  }

  addQuestion( id, questionType, title, index, options, scales, surveyModel ){
    let questionNumber = surveyModel.questionnaire.elements.length;

    let question = this.createQuestion( id, questionType, title, questionNumber, options, scales, false, true, undefined );
    // select new question
    this.selectQuestion( question, surveyModel, true );

    this.insertElement( surveyModel.questionnaire.elements, question, index );
  }

  createQuestion( id, questionType, title, qIndex, options, scales, isSelected, isNew, settings ){

    let question = {};
    let questionNumber = parseInt( qIndex ) + 1;
    question.title = isNew ? this.createNewTitle( title, questionNumber ) : title;
    question.id =  id !== undefined ? id : questionNumber;
    question.number = questionNumber;
    question.settings = this.createQuestionSettings( questionType, settings );
    question.selected = isSelected;

    switch ( questionType ){
      case 'closed':
        this.createClosedQuestion( question, options, scales );
        break;
      case 'matrix':
        this.createMatrixQuestion( question, options, scales );
        break;
      case 'test':
        this.createTestQuestion( question, options, scales );
        break;
      default:
        console.log( 'unsupported question type ' + questionType );
        break;
    }

    return question;
  }

  createNewTitle( title, questionNumber ){
    return title + " " + questionNumber;
  }

  createTestQuestion( question, options, scales ){
    if( !options ){
      options = this.createDefaultOptions();
    }

    if( !scales ){
      scales = this.createDefaultScales();
    }

    question.settings.view = './../questions/subView/test-question.html';

    question.options = {};
    question.options.id = "options_"+question.id;
    question.options.type = "options";
    question.options.elements = [];
    let optionIndex = 0;
    for ( let option of options ) {

      let newOption = this.createOption( option.id, option.title, 'closed-option', question.id, optionIndex, false,
        'common-option', question.options.id, scales, "./../common/opts/option" );

      question.options.elements.push( newOption );
      optionIndex++;
    }

    question.scales = {};
    question.scales.id = "scales_"+question.id;
    question.scales.type = "scales";
    question.scales.cssClass = "scales-view";
    question.scales.elements = [];

    let scaleIndex = 0;
    for ( let scale of scales ) {
      question.scales.elements.push( this.createScale( scale.id, scale.title, 'scale-option', question.id, scaleIndex, false, scale.options.elements, question.scales.id ) );
      scaleIndex++;
    }
  }

  createMatrixQuestion( question, options, scales ){
    if( !options ){
      options = this.createDefaultOptions();
    }

    if( !scales ){
      scales = this.createDefaultScales();
    }

    question.settings.view = './../questions/subView/matrix.html';

    question.options = {};
    question.options.id = "options_"+question.id;
    question.options.type = "options";
    question.options.freeTextOption = "";
    question.options.elements = [];
    let optionIndex = 0;
    for ( let option of options ) {

      let newOption = this.createOption( option.id, option.title, 'closed-option', question.id, optionIndex, false,
        'common-option', question.options.id, scales, "./../common/opts/option" );

      question.options.elements.push( newOption );
      optionIndex++;
    }

    question.scales = {};
    question.scales.id = "scales_"+question.id;
    question.scales.type = "scales";
    question.scales.cssClass = "scales-view";
    question.scales.elements = [];

    let scaleIndex = 0;
    for ( let scale of scales ) {
      question.scales.elements.push( this.createScale( scale.id, scale.title, 'scale-option', question.id, scaleIndex, false, scale.options.elements, question.scales.id ) );
      scaleIndex++;
    }
  }

  createClosedQuestion( question, options, scales ){
    if( !options ){
      options = this.createDefaultOptions();
    }

    if( !scales ){
      scales = this.createDefaultScales();
    }

    question.settings.view = './../questions/subView/closed-question.html';

    question.options = {};
    question.options.id = "options_"+question.id;
    question.options.type = "options";
    question.options.freeTextOption = "";
    question.options.elements = [];
    question.options.selected = -1;
    let optionIndex = 0;
    for ( let option of options ) {

      question.options.elements.push( this.createOption( option.id, option.title, 'closed-option', question.id,
        optionIndex, false,'common-option', question.options.id, scales, "./../common/opts/option" ) );

      optionIndex++;
    }

    question.scales = {};
    question.scales.id = "scales_"+question.id;
    question.scales.type = "scales";
    question.scales.cssClass = "scales-view";
    question.scales.elements = [];

    let scaleIndex = 0;
    for ( let scale of scales ) {
      question.scales.elements.push( this.createScale( scale.id, scale.title, 'scale-option', question.id, scaleIndex, false, scale.options.elements, question.scales.id ) );
      scaleIndex++;
    }

  }

  insertElement( elements, element, index ){
    elements.splice( index, 0, element );
    this.updateIndex( elements );
  }

  updateIndex( elements ){
    var i = 0;
    let lastIndex = elements.length - 1;
    elements.forEach(function( element ) {
      element.index = i;
      if( element.index === lastIndex ){
        element.isLast = true;
      }else{
        element.isLast = false;
      }
      i++;
    });

  }

  createDefaultOptions(){
    let options = [
      {title: 'Option 1', id:1},
      {title: 'Option 2', id:2},
      {title: 'Option 3', id:3}
    ];
    return options;
  }

  createDefaultScales(){

    let scales = [];
    scales.push( this.createDefaultScale( 'scale 1', 0, 10 ) );
    scales.push( this.createDefaultScale( 'scale 2', 1, 20 ) );
    scales.push( this.createDefaultScale( 'scale 3', 2, 30 ) );

    return scales;
  }

  createDefaultScale( title, index, id ){
    let scale = {};
    scale.id = id;
    scale.title = title;
    scale.name = "scale_" + index;
    scale.options = {};
    scale.options.elements = this.createDefaultScaleSteps( id );
    return scale;
  }

  createDefaultScaleSteps( scaleId ){
    let scaleSteps = [
      {title: 'step 1', id: scaleId + 1},
      {title: 'step 2', id: scaleId + 2},
      {title: 'step 3', id: scaleId + 3}
    ];
    return scaleSteps;
  }

  createGroup( scale, groupIndex, scaleName, optionName ){
    let group = {};
    group.index = groupIndex;
    group.name = scaleName;
    group.options = [];
    let groupOptionIndex = 0;
    let elements = scale.options.elements ? scale.options.elements : scale.options;
    for ( let scaleStep of elements ) {
      let option = {};
      option.id = '';
      option.index = groupOptionIndex;
      option.name = optionName + "_" + scaleName;
      option.selected = false;
      group.options.push( option );
      groupOptionIndex++;
    }
    return group;
  }

  createScaleGroup( scales, optionName ){
    let scaleGroup = [];
    let groupIndex = 0;
    for ( let scale of scales ) {
      let group = this.createGroup( scale, groupIndex, scale.name, optionName );
      scaleGroup.push( group );
      groupIndex++;
    }
    return scaleGroup;
  }

  // view = "./../common/opts/option"
  createOption( id, title, type, qId, index, isNew, cssClass, optionsId, scaleGroups, view ){

    let optionName = "option_" + index;

    let scaleGroup = this.createScaleGroup( scaleGroups, optionName );

    let option = {
      id: id,
      view: view,
      title: title,
      type: type,
      qId: qId,
      index: index,
      isNew: isNew,
      cssClass: cssClass ? cssClass : 'common-option',
      optionsId: optionsId,
      selected: false,
      name: optionName,
      scaleGroup: scaleGroup,
    };
    return option;
  }

  createScale( id, title, type, qId, index, isNew, scaleSteps, scaleId ){
    let scale = {
      id: id,
      view: "./../common/opts/scale",
      title: title,
      type: 'scale',
      qId: qId,
      index: index,
      isNew: isNew,
      scaleId: scaleId,
      name: "scale_"+index,
    };

    scale.options = [];
    scale.options.id = "steps_"+index+"_"+qId;
    scale.options.cssClass = "scale-steps";
    scale.options.type = "steps";
    scale.options.elements = [];
    let stepIndex = 0;
    for ( let step of scaleSteps ) {
      scale.options.elements.push( this.createOption( step.id, step.title, type, qId, stepIndex, false,
        'justify-content-center', scale.options.id, [], "./../common/opts/scale-option" ) );

      stepIndex++;
    }

    return scale;
  }

  createQuestionSettings( type, settings ){

    if( !settings ){
      settings = {};
      settings.layout = 'radio';
      settings.freeTextOption = false;
      settings.otherValue = 'Other';
      settings.lengthValue = 4000;
      settings.widthValue = '100%';
      settings.rowsValue = 1;
    }

    settings.availableQuestionTypes = this.getAvailableQuestionTypes();
    settings.questionType = type;

    return settings;
  }

  getAvailableQuestionTypes(){

    let closedQuestionLayouts = [
      {type:'radio', title:'Radio buttons'},
      {type:'checkbox', title:'Checkboxes'},
      {type:'list', title:'List'},
    ];

    let matrixQuestionLayouts = [
      {type:'radio', title:'Radio buttons'},
      {type:'checkbox', title:'Checkboxes'},
    ];

    let available = [
      {type:'closed', title:'Only one answer', view: './../questions/subView/closed-question.html', availableLayout: closedQuestionLayouts},
      {type:'matrix', title:'Matrix', view: './../questions/subView/matrix.html', availableLayout: matrixQuestionLayouts},
      // {type:'test', title:'Test', view: './../questions/subView/test-question.html'},
    ];

    return available;
  }

}
