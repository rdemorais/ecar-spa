/**=========================================================
 * Module: RoutesConfig.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .config(routesConfig);

    routesConfig.$inject = ['$locationProvider', '$stateProvider', '$urlRouterProvider', 'RouteProvider', 'OAuthProvider'];
    function routesConfig($locationProvider, $stateProvider, $urlRouterProvider, Route, OAuthProvider) {

      OAuthProvider.configure({
        baseUrl: '${pems-url}/',
        // baseUrl: 'http://localhost:8080/pems',
        clientId: 'restapp',
        clientSecret: 'secret',
        grantPath: '/oauth/token',
        revokePath: '/oauth/revoke'
      });

      // use the HTML5 History API
      $locationProvider.html5Mode(false);

      // Default route
      $urlRouterProvider.otherwise('/app/login');

      // Application Routes States
      $stateProvider
        .state('app', {
          url: '/app',
          abstract: true,
          templateUrl: Route.base('app.html'),
          resolve: {
            _assets: Route.require('icons', 'toaster', 'animate', 'swAlert', 'froala')
          }
        })
        .state('app.login', {
          url: '/login',
          templateUrl: Route.base('login.html'),
          resolve: {}
        })
        .state('app.troca', {
          url: '/troca',
          templateUrl: Route.base('troca-senha.html'),
          controller: 'trocaSenhaController',
          resolve: {}
        })
        .state('app.dashboard', {
          url: '/dashboard',
          templateUrl: Route.base('dashboard.html'),
          controller: 'dashboardController',
          resolve: {
            assets: Route.require('easypiechart', 'ui.select', 'slimscroll')
          }
        })
        .state('app.lista-itens', {
          url: '/lista-itens',
          templateUrl: Route.base('lista-itens.html'),
          controller: 'listaItensController',
          resolve: {
            assets: Route.require('slimscroll')
          }
        })
        .state('app.dash-item', {
          params: {
              'itemId': null,
              'nivel': null
            },
          url: '/item-dash',
          templateUrl: Route.base('item-dash.html'),
          resolve: {
            assets: Route.require('slimscroll', 'ngFileUpload')
          }
        })
    }
})();