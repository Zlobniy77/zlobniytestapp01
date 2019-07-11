import {BindingEngine, inject} from 'aurelia-framework';
import {NavigationService} from "./navigation-service";
import {HttpService} from "./http-service";
import {EventAggregator} from 'aurelia-event-aggregator';
import environment from './../../environment';

@inject( NavigationService, HttpService, BindingEngine, EventAggregator )
export class ClientService {

  test = false;
  clientInfo = {};
  currentFolder;
  hasLogged;
  subscription;

  constructor(  navigationService, httpService, bindingEngine, eventAggregator ) {
    console.log('constructor client service: ');
    this.bindingEngine = bindingEngine;
    this.currentFolder = 0;
    this.navigationService = navigationService;
    this.eventAggregator = eventAggregator;
    this.http = httpService;
    this.loginAction();

    this.subscription = this.bindingEngine
      .propertyObserver(this, 'currentFolder')
      .subscribe((newValue, oldValue) => {
        this.objectValueChanged(newValue, oldValue)
      });

  }

  bind() {
    console.log( 'activate client service' );
  }


  attached(){
    console.log('attached client service');

  }

  detached() {
    this.subscription.dispose();
  }

  objectValueChanged(newValue, oldValue) {
    console.log(`bindingEngine value changed from: ${oldValue} to:${newValue}`);
    this.eventAggregator.publish( 'overview.update', newValue );
  }

  logOff(){
    let that = this;
    let clientData = this.clientInfo;

    let promis = this.http.post( 'logout', clientData );
    promis.then(function(response) {
      console.log('response', response);
      return response.json()
    }).then(function(json) {
      console.log('parsed json', json);
      if( json.message === 'logout' ){
        that.clientInfo = {};
        that.clientInfo.hasLogged = false;
        window.localStorage.removeItem( 'token' );
        that.navigationService.setButtons( [] );
        that.navigationService.setTitle( {} );
        that.navigationService.goTo( that.navigationService.NAV_START_PAGE );
      }
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });

  }

  loadProperty(){

    let id = this.clientInfo.id;

    let promis = this.http.get( 'api/client/' + id + '/property' );

    return promis;
  }

  saveProperty( property ){

    let id = this.clientInfo.id;

    let promis = this.http.post( 'api/client/' + id + '/property', property );
    promis.then(function(response) {
      console.log('response', response);
      return response.json()
    }).then(function(json) {
      console.log('parsed json', json);

    }).catch(function(ex) {
      console.log('parsing failed', ex)
    });

  }

  loginAction( clientData ) {

    if( clientData === undefined ){
      clientData = {};
    }

    if( this.ifTest() ) return;

    let that = this;


    console.log('Login action: ' + clientData );
    let promise = this.http.postLogin( clientData );
    if( promise ){
      promise.then(function( response ) {
        return response.json()
      }).then(function(json){
        console.log('json', json);
        if( json.token ){
          console.log('login action success');
          that.clientInfo = json;
          that.clientInfo.hasLogged = true;
          that.currentFolder = json.rootFolderId;
          that.hasLogged = true;
          window.localStorage.setItem( 'token', json.token );
          if( that.navigationService.router.currentInstruction.config.name === 'welcome' ){
            that.navigationService.goTo( that.navigationService.NAV_DASHBOARD );
          }
        }
      }).catch(function(ex) {
        that.navigationService.setButtons( [] );
        that.navigationService.setTitle( {} );
        if( that.navigationService.router.currentInstruction.config.name === 'survey-viewer' ){
          that.navigationService.goTo( that.navigationService.NAV_SURVEY_VIEWER );
        }else{
          that.navigationService.goTo( that.navigationService.NAV_START_PAGE );
        }
        console.log('failed', ex)
      });
    }

  }

  registrationAction( data ){
    let that = this;
    this.currentFolder = 0;
    let promise = this.http.registrationPost( data );
    promise.then(function( response ) {
      return response.json()
    }).then(function(json){
      console.log('json', json);
      if( json.token ){
        that.clientInfo = json;
        that.clientInfo.hasLogged = true;
        that.currentFolder = json.rootFolderId;
        that.hasLogged = true;
        window.localStorage.setItem( 'token', json.token );
        if( that.navigationService.router.currentInstruction.config.name === 'regForm' ){
          that.navigationService.goTo( that.navigationService.NAV_DASHBOARD );
        }
      }
    }).catch(function(ex) {
      that.navigationService.setButtons( [] );
      that.navigationService.setTitle( {} );
      that.navigationService.goTo( that.navigationService.NAV_START_PAGE );
      console.log('failed', ex)
    });
  }

  ifTest(){

    let inv_test = environment.testing;

    if( inv_test ){
      this.clientInfo = { id:'0', username:'test', hasLogged: true, name: 'Test'};
      this.hasLogged = true;
      return true;
    }
    return false;
  }

  setCurrentFolder( id ){
    this.currentFolder = id;
  }

  getCurrentFolder(){
    return this.currentFolder;
  }

}
