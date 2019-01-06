import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';


@inject( Router )
export class NavigationService {

  NAV_DASHBOARD = "/dashboard";
  NAV_SURVEY = "/survey";
  NAV_START_PAGE = "/";
  NAV_SURVEY_VIEWER = "/survey-viewer";
  NAV_PANEL = "/panel";

  objectTitle;
  buttons = [];

  constructor( router ) {
    this.router = router;
    this.objectTitle = {};
    this.showClientInfo = true;
  }

  goTo( path ) {
    this.router.navigate( path );
  }

  setButtons( buttons ){
    this.buttons = buttons;
  }

  setTitle( title, editMode ){
    this.objectTitle = title;
    this.editMode = editMode;
  }

}
