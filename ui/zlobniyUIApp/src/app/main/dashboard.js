import 'css/dashboard.css';

import {inject} from 'aurelia-framework';
import {ClientService} from "../services/client-service";
import {NavigationService} from "../services/navigation-service";
import {Ui} from "../ui";
import {Popup} from "./common/popup";
import {DialogService} from 'aurelia-dialog';


@inject( ClientService, NavigationService, DialogService, Ui )
export class Dashboard extends Ui {

  title = 'Dashboard';
  info = "";

  constructor( clientService, navigationService, dialogService, ...rest ){
    super(...rest);
    this.clientService = clientService;
    this.navigationService = navigationService;
    this.dialogService = dialogService;
  }

  activate(){
    let that = this;
    let buttons = [
      {
        title: 'Create survey', action: function () {
          that.navigationService.goTo( that.navigationService.NAV_SURVEY );
        }, css: 'fa fa-file-invoice'
      },
      {
        title: 'Create panel', action: function () {
          that.navigationService.goTo( that.navigationService.NAV_PANEL );
        }, css: 'fas fa-clipboard-list'
      },
      {
        title: 'Create folder', action: function () {

          let data = {};

          data.width = "40%";
          data.view = "app/main/folders/folder";
          that.dialogService.open({
            viewModel: Popup,
            model: data,
            lock: false
          })
            .whenClosed( resp => {}  );

        }, css: 'fas fa-folder'
      }
    ];

    this.navigationService.setButtons( buttons );
  }

}
