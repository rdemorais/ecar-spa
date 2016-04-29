/**=========================================================
 * Module: pemsController.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .controller('dashboardController', dashboardController)
        .controller('listaItensController', listaItensController)
        .controller('itemDashController', itemDashController)
        .controller('loginController', loginController);
    
    loginController.$inject = ['$scope', '$state', 'OAuth'];
    function loginController($scope, $state, OAuth) {

        $scope.login = function() {

            OAuth.getAccessToken({
                username: 'angular-oauth',
                password: 'passwd'
            }).then(function() {
                if(OAuth.isAuthenticated()) {
                    $state.go('app.dashboard');
                }
            });
        }
    }

    dashboardController.$inject = ['$scope', '$state', 'pemsFilterService'];
    function dashboardController($scope, $state, pemsFilterService) {
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa
        };

        $scope.pesquisar = function() {
            pemsFilterService.setPpa($scope.filtro.ppa);
            pemsFilterService.setMeta($scope.filtro.meta);
            pemsFilterService.setIniciativa($scope.filtro.iniciativa);

            $state.go('app.lista-itens');
        };

        $scope.limparFiltros = function() {
            pemsFilterService.clear();
            console.log('sds');
        };
    }

    listaItensController.$inject = ['$scope', '$state', 'pemsFilterService'];
    function listaItensController($scope, $state, pemsFilterService) {
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa
        };

        $scope.pesquisar = function() {
            
        }
    }

    itemDashController.$inject = ['$scope', '$state', 'pemsService'];
    function itemDashController($scope, $state, pemsService) {
        var vm = this;

        pemsService.loadItem(1, function(item) {
            vm.item = item;
        });
    }

})();