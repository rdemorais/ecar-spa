(function() {
    'use strict';

    angular
        .module('naut')
        .directive('pemsOes', pemsOes)
        .directive('pemsStatusBar', pemsStatusBar)
        .directive('pemsEtiquetas', pemsEtiquetas)
        .directive('pemsStatusFilter', pemsStatusFilter)
        .directive('pemsListaItens', pemsListaItens)
        .directive('pemsIndicadores', pemsIndicadores)
        .filter('propsFilter', propsFilter)
        .filter('format', format);

    function pemsStatusBar() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'colors'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/pems-status-bar.html',
        controller: controller
      };
      function controller($scope, $element, pemsService, colors) {
        pemsService.loadStatus(function(statusBar) {
          $scope.statusBar = statusBar;
        });

        var pieOptionsCor = function(nomeCor) {
          var pieOptions = {
            animate:{
              duration: 700,
              enabled: true
            },
            barColor: colors.byName(nomeCor),
            // trackColor: colors.byName('inverse'),
            scaleColor: false,
            lineWidth: 5,
            lineCap: 'circle',
            size: 50
          };

          return pieOptions;
        }

        $scope.statusClick = function(item) {
          item.sel = !item.sel;
        }

        //Calculo de percentual e cor
        var iniciativa = $scope.statusBar.iniciativa;
        angular.forEach(iniciativa.status, function(value, key) {
          var st = value;
          st.pieOptions = pieOptionsCor(st.nomeCor);

          st.percentual = (st.count/iniciativa.totalInicitiva) * 100;
        });

        var meta = $scope.statusBar.meta;
        angular.forEach(meta.status, function(value, key) {
          var st = value;
          value.pieOptions = pieOptionsCor(value.nomeCor);

          st.pieOptions = pieOptionsCor(st.nomeCor);

          st.percentual = (st.count/meta.totalMeta) * 100;
        });

        var produto = $scope.statusBar.produto;
        angular.forEach(produto.status, function(value, key) {
          var st = value;
          value.pieOptions = pieOptionsCor(value.nomeCor);

          st.pieOptions = pieOptionsCor(st.nomeCor);

          st.percentual = (st.count/produto.totalProduto) * 100;
        });

        var atividade = $scope.statusBar.atividade;
        angular.forEach(atividade.status, function(value, key) {
          var st = value;
          value.pieOptions = pieOptionsCor(value.nomeCor);

          st.pieOptions = pieOptionsCor(st.nomeCor);

          st.percentual = (st.count/atividade.totalAtividade) * 100;
        });
      }
    };

    //pemsOes.$inject = ['pemsService'];
    function pemsOes() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService'];
      return {
        restrict: 'E',
        scope: {
          mod: '@'
        },
        templateUrl: 'app/views/cached/oes.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService) {
        pemsFilterService.listaOEs(function(oes) {
          $scope.oes = oes;
        });

        $scope.oeClick = function(oe) {
          //pemsFilterService.addRemoveOe(oe.id);
          oe.sel = !oe.sel;
        }
      }
    }

    function pemsEtiquetas() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/etiquetas.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService) {
        $scope.uiselectEt = {
          etiquetas: [],
          etSelectionadas: []
        };
        
        pemsService.loadEtiquetas(function(ets) {
          $scope.uiselectEt.etiquetas = ets;
        });  

        $scope.uiselectEt.etSelectionadas = [$scope.uiselectEt.etiquetas[0]];

        //$scope.uiselectEt.etSelectionadas = pemsFilterService.getFiltros().etiquetas;
        //console.log($scope.uiselectEt.etSelectionadas);

        $scope.onSelectEt = function(et) {
          pemsFilterService.addRemoveEtiqueta(et);
        };

        $scope.onRemoveEt = function(et) {
          pemsFilterService.addRemoveEtiqueta(et);
        };
      }
    }

    function pemsStatusFilter() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/status-filter.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService) {
        $scope.selStatus = function(nomeCor) {
          pemsFilterService.addRemoveStatus(nomeCor);
        }
      }
    }

    function pemsListaItens() {
      controller.$inject = ['$scope', '$element', 'pemsService', '$state'];
      return {
        restrict: 'E',
        scope: {
          nivel: '@'
        },
        templateUrl: 'app/views/cached/lista-itens-direc.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, $state) {
        if($scope.nivel == 1) {
          pemsService.loadListaItens(function(listaItens) {
            $scope.listaItens = listaItens;
          });
        }else if($scope.nivel == 2) {
          pemsService.loadProdutos(function(listaItens) {
            $scope.listaItens = listaItens;
          });
        }

        $scope.itemSelecionado = function(itemId) {
          console.log(itemId);
          $state.go("app.dash-item");
        }
      }
    }

    function pemsIndicadores() {
      controller.$inject = ['$scope', '$element', 'pemsService'];
      return {
        restrict: 'E',
        scope: {
          indicadores: '='
        },
        templateUrl: 'app/views/cached/indicadores-direc.html',
        controller: controller
      };

      function controller($scope, $element, pemsService) {

      }
    }

    function format() {
      return function(text, format) {
        var ret = ''
        numbro.culture('pt-BR');
        switch(format) {
          case 'num':
            ret = numbro(text).format('0,0');
            break;
          case 'perc':
            ret = numbro(text).format('0.00%');
            break;
        }

        return ret;
      }
    }

    function propsFilter() {
      return function(items, props) {
        var out = [];

        if (angular.isArray(items)) {
          items.forEach(function(item) {
            var itemMatches = false;

            var keys = Object.keys(props);
            for (var i = 0; i < keys.length; i++) {
              var prop = keys[i];
              var text = props[prop].toLowerCase();
              if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                itemMatches = true;
                break;
              }
            }

            if (itemMatches) {
              out.push(item);
            }
          });
        } else {
          // Let the output be the input untouched
          out = items;
        }

        return out;
      };
    }
})();

