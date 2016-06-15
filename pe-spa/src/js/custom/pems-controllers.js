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
        .controller('loginController', loginController)
        .factory('truncate', stripTags);
    
    loginController.$inject = ['$scope', '$state', 'OAuth', 'pemsService'];
    function loginController($scope, $state, OAuth, pemsService) {
        $scope.login = '';
        $scope.senha = '';

        $scope.loginEcar = function() {

            OAuth.getAccessToken({
                username: $scope.login,
                password: $scope.senha
            }).then(function() {
                if(OAuth.isAuthenticated()) {
                    pemsService.loadOEs(function(oes){});

                    $state.go('app.dashboard');
                }
            });
        }
    }

    dashboardController.$inject = ['$scope', '$state', 'pemsFilterService', 'pemsService'];
    function dashboardController($scope, $state, pemsFilterService, pemsService) {
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa
        };

        $scope.persp = pemsFilterService.getFiltros().pns ? 'pns' : 'ppa';

        $scope.pesquisar = function() {
            pemsFilterService.setPpa($scope.filtro.ppa);
            pemsFilterService.setMeta($scope.filtro.meta);
            pemsFilterService.setIniciativa($scope.filtro.iniciativa);

            $state.go('app.lista-itens');
        };

        $scope.limparFiltros = function() {
            //pemsFilterService.clear();
        };
    }

    listaItensController.$inject = ['$scope', '$state', 'pemsFilterService', 'pemsService'];
    function listaItensController($scope, $state, pemsFilterService, pemsService) {
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa
        };

        $scope.persp = pemsFilterService.getFiltros().pns ? 'pns' : 'ppa';

        $scope.$watch($scope.persp, function(newValue, oldValue) {
            console.log(newValue);
        });

        $scope.gerarRelatorioGerencial = function() {
          pemsService.gerarRelatorioGerencial(pemsFilterService.getFiltros());
        }

        $scope.pesquisar = function() {
            pemsFilterService.setPpa($scope.filtro.ppa);
            pemsFilterService.setMeta($scope.filtro.meta);
            pemsFilterService.setIniciativa($scope.filtro.iniciativa);
            $scope.$broadcast('pems:pesquisar-lista');
        }
    }

    itemDashController.$inject = ['$scope', '$state', '$stateParams', '$sce', 'pemsService', 'pemsFilterService', 'truncate'];
    function itemDashController($scope, $state, $stateParams, $sce, pemsService, pemsFilterService, truncate) {
        var vm = this;
        vm.parecer = '';

        $scope.anexos = [];
        pemsService.loadAnexos(pemsFilterService.getFiltros(), function(anexos) {
            $scope.anexos = anexos;
        });

        $scope.downloadAnexo = function(anexo) {
            pemsService.downloadAnexo(anexo);
        }

        $scope.itemSelecionadoDash = function(_itemId, _nivel) {
            $state.go("app.dash-item", {itemId: _itemId, nivel: _nivel});
        }

        $scope.retornarListaItens = function() {
            $state.go("app.lista-itens");
        }

        $scope.gerarRelatorioExecutivo = function() {
            pemsService.gerarRelatorioExecutivo(pemsFilterService.getFiltros());
        }

        pemsFilterService.getFiltros().codIett = $stateParams.itemId;
        pemsFilterService.getFiltros().nivel = $stateParams.nivel;
        pemsService.loadItem(pemsFilterService.getFiltros(), function(item) {
            vm.item = item;
            vm.parecer = truncate(vm.item.parecer, '<a><br><ul><li><strong><b><table><p><i><ol><td><tr><h1><h2><h3>');
        });

    }

    function stripTags() {
      return function truncate(input, allowed) {
        //var allowed = '<a><b><strong><table><br>';
        allowed = (((allowed || '') + '')
          .toLowerCase()
          .match(/<[a-z][a-z0-9]*>/g) || [])
          .join(''); // making sure the allowed arg is a string containing only tags in lowercase (<a><b><c>)
        var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
          commentsAndXmlTags = /<!--[\s\S]*?-->|<\?(?:xml)?[\s\S]*?>/gi;
        return input.replace(commentsAndXmlTags, '')
          .replace(tags, function($0, $1) {
            return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
          });
      }
    }

})();