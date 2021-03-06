/**=========================================================
 * Module: RoutesRun
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .run(appRun);
    /* @ngInject */   
    function appRun($rootScope, $window, OAuth, pemsServiceError, $state, SwAlert) {
      
      // Hook not found
      $rootScope.$on('$stateNotFound',
        function(event, unfoundState, fromState, fromParams) {
            console.log(unfoundState.to); // "lazy.state"
            console.log(unfoundState.toParams); // {a:1, b:2}
            console.log(unfoundState.options); // {inherit:false} + default options
        });

      // Hook success
      $rootScope.$on('$stateChangeSuccess',
        function(event, toState, toParams, fromState, fromParams) {
          // success here
          // display new view from top
          if(!OAuth.isAuthenticated() && toState.name != 'app.troca') {
            console.log('nao autenticado... redirecionando para login');
            $state.go('app.login');
          }
          $window.scrollTo(0, 0);
        });


      $rootScope.$on('oauth:error', function(event, rejection) {
        if(rejection !== undefined) {
          // Ignore invalid_grant error - should be catched on LoginController.
          if ('invalid_grant' === rejection.data.error) {
            SwAlert.error('', 'Usuário ou senha inválidos');
            return;
          }
          /*
          // Refresh token when a invalid_token error occurs.
          if ('invalid_token' === rejection.data.error) {
            return OAuth.getRefreshToken();
          }*/

        }
        
        // Redirect to /login with the error_reason.
        
        $state.go('app.login');
      });
    }
    appRun.$inject = ['$rootScope', '$window', 'OAuth', 'pemsServiceError', '$state', 'SwAlert'];

})();

