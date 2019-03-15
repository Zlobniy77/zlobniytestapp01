import {inject} from 'aurelia-framework';
import {DialogService} from "aurelia-dialog";
import {ClientService} from "../services/client-service";
import {Ui} from "../ui";
import {Popup} from "../main/common/popup";

@inject( ClientService, DialogService, Ui )
export class UserInfo extends Ui {

  constructor( clientService, dialogService, ...rest ) {
    super(...rest);
    this.clientService = clientService;
    this.dialogService = dialogService;
  }

  doLogOff() {
    this.clientService.logOff();
    console.log('log off action: ');
  }

  property() {

    let data = {};
    data.id = 'clientProperty';
    data.width = "70%";
    data.view = "app/main/client/client-property";
    this.dialogService.open({
      viewModel: Popup,
      model: data,
      lock: false
    })
      .whenClosed( resp => {}  );

  }

}
