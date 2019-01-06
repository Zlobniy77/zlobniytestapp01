import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {ClientService} from "../services/client-service";
import {NavigationService} from "../services/navigation-service";
import {Ui} from "../ui";


@inject( ClientService, NavigationService, Ui )
export class Dashboard extends Ui {

  title = 'Dashboard';
  info = "";

  constructor( clientService, navigationService, ...rest ){
    super(...rest);
    this.clientService = clientService;
    this.navigationService = navigationService;
  }

  activate(){
    let that = this;
    let buttons = [
      {
        title: 'Create survey', action: function () {
          that.navigationService.goTo( that.navigationService.NAV_SURVEY );
        }, css: 'fa fa-file-invoice'
      },
      // {
      //   title: 'Create panel', action: function () {
      //     that.navigationService.goTo( that.navigationService.NAV_PANEL );
      //   }, css: 'fa fa-file-invoice'
      // }
    ];

    this.navigationService.setButtons( buttons );
  }

}
