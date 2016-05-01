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
                username: 'admin',
                password: 'serenaya'
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

    itemDashController.$inject = ['$scope', '$state', '$stateParams', 'pemsService', 'pemsFilterService'];
    function itemDashController($scope, $state, $stateParams, pemsService, pemsFilterService) {
        var vm = this;

        pemsFilterService.getFiltros().codIett = $stateParams.itemId;
        pemsFilterService.getFiltros().nivel = $stateParams.nivel;
        pemsService.loadItem(pemsFilterService.getFiltros(), function(item) {
            vm.item = item;
        });
    }

})();