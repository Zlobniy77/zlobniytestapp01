import environment from './environment';
import {PLATFORM} from 'aurelia-pal';
import * as Bluebird from 'bluebird';
import {fetch} from 'whatwg-fetch';
import 'bootstrap';

// remove out if you don't want a Promise polyfill (remove also from webpack.config.js)
Bluebird.config({ warnings: { wForgottenReturn: false }, longStackTraces: false });

export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .developmentLogging()
    .feature(PLATFORM.moduleName('resources/index'))
    .plugin(PLATFORM.moduleName('aurelia-dialog'))
    .plugin(PLATFORM.moduleName('aurelia-validation'))
    .plugin(PLATFORM.moduleName('aurelia-animator-css'))
    .plugin(PLATFORM.moduleName('hammerjs'));

  configureContainer(aurelia.container);

  // Uncomment the line below to enable animation.
  // aurelia.use.plugin(PLATFORM.moduleName('aurelia-animator-css'));
  // if the css animator is enabled, add swap-order="after" to all router-view elements

  // Anyone wanting to use HTMLImports to load views, will need to install the following plugin.
  // aurelia.use.plugin(PLATFORM.moduleName('aurelia-html-import-template-loader'));

  if (environment.debug) {
    aurelia.use.developmentLogging();
  }

  if (environment.testing) {
    aurelia.use.plugin(PLATFORM.moduleName('aurelia-testing'));
  }

  aurelia.start().then(() => aurelia.setRoot(PLATFORM.moduleName('app')));
}

function configureContainer(container) {
  // let http = new fetch();
  // http.configure(config => {
  //   config
  //     .useStandardConfiguration()
  //     .withBaseUrl('/')
  //     .withInterceptor({
  //       request(request) {
  //         console.log(`Requesting ${request.method} ${request.url}`);
  //         return request;
  //       },
  //       response(response) {
  //         console.log(`Received ${response.status} ${response.url}`);
  //         return response;
  //       }
  //     });
  // });
  // container.registerInstance(fetch, http); // <---- this line ensures everyone that `@inject`s a `HttpClient` instance will get the instance we configured above.
}
