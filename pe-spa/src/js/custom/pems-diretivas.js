(function() {
    'use strict';

    angular
        .module('naut')
        .directive('pemsOes', pemsOes)
        .directive('pemsSecretarias', pemsSecretarias)
        .directive('pemsStatusBar', pemsStatusBar)
        .directive('pemsEtiquetas', pemsEtiquetas)
        .directive('pemsStatusFilter', pemsStatusFilter)
        .directive('pemsListaItens', pemsListaItens)
        .directive('pemsIndicadores', pemsIndicadores)
        .filter('propsFilter', propsFilter)
        .filter('format', format);

    function pemsSecretarias() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/pems-secretarias.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService) {
        $scope.secretarias = [];
        $scope.secSelec = pemsFilterService.getSecretarias();

        $scope.toggleSelection = function(sec) {
          var idx = $scope.secSelec.indexOf(sec.id);

          if (idx > -1) {
            $scope.secSelec.splice(idx, 1);
          }else {
            $scope.secSelec.push(sec.id);
          }

          pemsFilterService.setSecretarias($scope.secSelec);
        }

        pemsService.loadSecretarias(function(secs) {
          $scope.secretarias = secs;
        });
      }
    }

    function pemsStatusBar() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'colors', 'pemsFilterService'];
      return {
        restrict: 'E',
        scope: {
          perspectiva: '&'
        },
        templateUrl: 'app/views/cached/pems-status-bar.html',
        controller: controller
      };
      function controller($scope, $element, pemsService, colors, pemsFilterService) {
        $scope.statusBar = {};
        
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
        };

        $scope.statusClick = function(item) {
          item.sel = !item.sel;
        }

        $scope.$watch($scope.perspectiva, function(newValue, oldValue) {
          loadStatus(pemsService, pemsFilterService, pieOptionsCor, $scope);          
        });

        loadStatus(pemsService, pemsFilterService, pieOptionsCor, $scope);
      }

      function loadStatus(pemsService, pemsFilterService, pieOptionsCor, $scope) {
        pemsService.loadStatus(pemsFilterService.getFiltros(), function(statusBar) {
          $scope.statusBar = statusBar;
        

          //Calculo de percentual e cor
          var iniciativa = $scope.statusBar.iniciativa;
          angular.forEach(iniciativa.status, function(value, key) {
            var st = value;
            st.pieOptions = pieOptionsCor(st.nomeCor);

            st.percentual = (st.count/iniciativa.total) * 100;
          });

          var meta = $scope.statusBar.meta;
          angular.forEach(meta.status, function(value, key) {
            var st = value;
            value.pieOptions = pieOptionsCor(value.nomeCor);

            st.pieOptions = pieOptionsCor(st.nomeCor);

            st.percentual = (st.count/meta.total) * 100;
          });

          var produto = $scope.statusBar.produto;
          angular.forEach(produto.status, function(value, key) {
            var st = value;
            value.pieOptions = pieOptionsCor(value.nomeCor);

            st.pieOptions = pieOptionsCor(st.nomeCor);

            st.percentual = (st.count/produto.total) * 100;
          });

          var atividade = $scope.statusBar.atividade;
          angular.forEach(atividade.status, function(value, key) {
            var st = value;
            value.pieOptions = pieOptionsCor(value.nomeCor);

            st.pieOptions = pieOptionsCor(st.nomeCor);

            st.percentual = (st.count/atividade.total) * 100;
          });
        });
      }
    };

    function pemsOes() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService'];
      return {
        restrict: 'E',
        scope: {
          mod: '@',
          perspectiva: '&'
        },
        templateUrl: 'app/views/cached/oes.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService) {
        pemsService.loadOEs(function(oes) {
          $scope.oes = oes;
        });

        $scope.oeClick = function(oe) {
          pemsFilterService.addRemoveOe(oe.id);
          oe.sel = !oe.sel;
        }

        $scope.$watch($scope.perspectiva, function(newValue, oldValue) {
          console.log(newValue);
          pemsFilterService.mudarPerspectiva(newValue);
          pemsService.loadOEs(function(oes) {
            $scope.oes = oes;
          });
        });
      }
    }

    function pemsEtiquetas() {
      controller.$inject = ['$scope', '$element', '$timeout', 'pemsService', 'pemsFilterService'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/etiquetas.html',
        controller: controller
      };

      function controller($scope, $element, $timeout, pemsService, pemsFilterService) {
        $scope.uiselectEt = {
          etiquetas: [],
          etSelectionadas: []
        };
        
        pemsService.loadEtiquetas(function(ets) {
          $scope.uiselectEt.etiquetas = ets;
          $scope.uiselectEt.etSelectionadas = pemsFilterService.getFiltros().etiquetas;
        });  

        $scope.onSelectEt = function(et) {
          pemsFilterService.addRemoveEtiqueta(et.id);
        };

        $scope.onRemoveEt = function(et) {
          pemsFilterService.addRemoveEtiqueta(et.id);
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
        $scope.listaStatus = pemsFilterService.listaStatusFiltros();

        $scope.selStatus = function(status) {
          pemsFilterService.addRemoveStatus(status.codCor);
          status.sel = !status.sel;
        }
      }
    }

    function pemsListaItens() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService', '$state'];
      return {
        restrict: 'E',
        scope: {
          nivel: '@'
        },
        templateUrl: 'app/views/cached/lista-itens-direc.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService, $state) {
        if($scope.nivel == 'META') {
          pemsService.loadListaItens(pemsFilterService.getFiltros(), function(listaItens) {
            $scope.listaItens = listaItens;
          });
        }else if($scope.nivel == 'PRODUTO_INTERMEDIARIO') {
          pemsService.loadProdutos(pemsFilterService.getFiltros(), function(listaItens) {
            $scope.listaItens = listaItens;
          });
        }else if($scope.nivel == 'ATIVIDADE') {
          pemsService.loadAtividades(pemsFilterService.getFiltros(), function(listaItens) {
            $scope.listaItens = listaItens;
          });
        }

        $scope.itemSelecionado = function(_itemId) {
          $state.go("app.dash-item", {itemId: _itemId, nivel: $scope.nivel});
        }

        $scope.$on('pems:pesquisar-lista', function(event) {
          if($scope.nivel == 'META') {
            pemsService.loadListaItens(pemsFilterService.getFiltros(), function(listaItens) {
              $scope.listaItens = listaItens;
            });
          } else if($scope.nivel == 'PRODUTO_INTERMEDIARIO') {
            pemsService.loadProdutos(pemsFilterService.getFiltros(), function(listaItens) {
              $scope.listaItens = listaItens;
            });
          } else if($scope.nivel == 'ATIVIDADE') {
            pemsService.loadAtividades(pemsFilterService.getFiltros(), function(listaItens) {
              $scope.listaItens = listaItens;
            });
          }
        });
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

    format.$inject = ['NumbroFormater']
    function format(NumbroFormater) {
      return function(text, format) {
        var ret = ''
        switch(format) {
          case 'num':
            ret = NumbroFormater.fmt(text, '0,0');
            break;
          case 'perc':
            ret = NumbroFormater.fmt(text, '0.00%');
            break;
          case 'perc-st':
            ret = NumbroFormater.fmt(text, '0');
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