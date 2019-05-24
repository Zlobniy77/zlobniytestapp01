export class WizardService {

  constructor() {
    console.log('constructor WizardService ');
  }

  setEditedModel( model ){
    console.log( 'start editing ' + model.name );
    if( this.editedModel ){
      console.log( 'already editing ' + this.editedModel );
      this.editedModel.finishEdit();
    }
    this.editedModel = model;
    console.log( 'editedModel = ' + this.editedModel );
  }

  unsetEditedModel(){
    if( this.editedModel ){
      this.editedModel.finishEdit();
      this.editedModel = undefined;
    }
  }

  isEditedModel(){
    return this.editedModel !== undefined;
  }

  attached() {
    console.log('attached WizardService ');
  }

  detached() {
    console.log('detached WizardService ');
  }

  activate(data) {
    console.log('activate WizardService ' + data);


  }


}
