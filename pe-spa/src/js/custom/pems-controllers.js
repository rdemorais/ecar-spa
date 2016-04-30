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
    
    loginController.$inject = ['$scope', '$state', 'OAuth', 'pemsService'];
    function loginController($scope, $state, OAuth, pemsService) {

        $scope.login = function() {

            OAuth.getAccessToken({
                username: 'angular-oauth',
                password: 'passwd'
            }).then(function() {
                if(OAuth.isAuthenticated()) {
                    pemsService.loadOEs(function(oes){});

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
        };
    }

    listaItensController.$inject = ['$scope', '$state', 'pemsFilterService', 'pemsService'];
    function listaItensController($scope, $state, pemsFilterService, pemsService) {
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa
        };

        $scope.pesquisar = function() {
            pemsFilterService.setPpa($scope.filtro.ppa);
            pemsFilterService.setMeta($scope.filtro.meta);
            pemsFilterService.setIniciativa($scope.filtro.iniciativa);
            $scope.$broadcast('pems:pesquisar-lista');
        }
    }

    itemDashController.$inject = ['$scope', '$state', '$stateParams', 'pemsService'];
    function itemDashController($scope, $state, $stateParams, pemsService) {
        var vm = this;
        pemsService.loadItem($stateParams.itemId, function(item) {
            vm.item = item;
        });
    }

})();