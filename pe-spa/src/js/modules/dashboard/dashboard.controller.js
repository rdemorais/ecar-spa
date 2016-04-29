/**=========================================================
 * Module: DashboardController.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .controller('DashboardController', DashboardController);
    
    DashboardController.$inject = ['$rootScope', '$scope', 'colors', '$timeout', '$state'];
    function DashboardController($rootScope, $scope, colors , $timeout, $state) {
      $scope.pesquisar = function() {
        console.log('pesquisar');
        $state.go('app.lista-itens');
      }

    }

})();