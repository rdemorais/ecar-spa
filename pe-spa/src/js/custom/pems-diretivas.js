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
        .directive('pemsParecer', pemsParecer)
        .filter('propsFilter', propsFilter)
        .filter('format', format);

    pemsParecer.$inject = ['$state', '$timeout', 'Upload', '$rootScope'];
    function pemsParecer($state, $timeout, Upload, $rootScope) {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService', 'SwAlert'];
      return {
        restrict: 'E',
        scope: {
          item: '='
        },
        templateUrl: 'app/views/cached/parecer-view.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService, SwAlert) {
        $scope.data = {
          situacoes: [],
          cores: [],
          corSelecionada: 'branco',
          corAlcancado: {id: 10, nome: 'azul', significado: 'Satisfatório'},
          sitAlcancado: {id: 2, descricao: 'Alcançado'},
          corCancelado: {id: 11, nome: 'cinza', significado: 'Cancelado'},
          sitCancelado: {id: 17, descricao: 'Cancelado'},
          disabled: false,
          files: new Array(),
          file: new Object()
        };

        $scope.$watch('data.file', function(newValue, oldValue) {
          var file = $scope.data.file[0];
          if(file !== undefined) {
            $scope.data.files.push(file);
          }
        });

        $scope.parecer = {
          situacao: {},
          cor: {},
          texto: '',
          codArel: $scope.item.codArel
        };

        if(($scope.item.mes === $scope.item.mesCicloParecer) && ($scope.item.ano === $scope.item.anoCicloParecer)) {
          $scope.parecer.cor = $scope.item.cor;
          $scope.parecer.situacao = $scope.item.situacao;
          $scope.parecer.texto = $scope.item.parecer;

          $scope.data.corSelecionada = $scope.parecer.cor.nome;
        }

        pemsService.listSituacoes(function(sits) {
          $scope.data.situacoes = sits;
        });

        pemsService.listCores(function(cores) {
          $scope.data.cores = cores;
        });

        $scope.excluirAnexo = function(f) {
          var indexOf = $scope.data.files.indexOf(f);
          if(indexOf != -1) {
            $scope.data.files.splice(indexOf, 1);
          }
        };

        $scope.gravarParecer = function() {
          var emptyCor = angular.equals({}, $scope.parecer.cor);
          var emptySituacao = angular.equals({}, $scope.parecer.situacao);
          var emptyParecer = angular.equals('', $scope.parecer.texto);

          if(emptyCor || emptySituacao || emptyParecer) {
            SwAlert.error('', 'Informe o Status, a Situação e o Parecer.');
          } else {
            $scope.data.disabled=true;

            $scope.item.situacao = $scope.parecer.situacao;
            $scope.item.cor = $scope.parecer.cor;
            $scope.item.parecer = $scope.parecer.texto;
            $scope.item.mes = $scope.item.mesCicloParecer;
            $scope.item.ano = $scope.item.anoCicloParecer;
            
            pemsService.gravarParecer($scope.parecer, function(ret) {
              if(ret) {
                var files = $scope.data.files;

                if(files && files.length) {

                }
                $scope.data.disabled=false;

                for (var i = 0; i < files.length; i++) {
                  var f = files[i];
                  Upload.upload({
                    url: $rootScope.app.baseUrl + '/upload',
                    data: {
                      file: f, 
                      nomeFile: f.name,
                      codIett: $scope.item.id,
                      codArel: $scope.parecer.codArel
                    }
                  }).then(function(response) {
                    console.log(response);
                  });
                  
                };

                SwAlert.success('', 'Parecer registrado com sucesso');
              }
            });
          }
        };  

        $scope.changeCor = function() {
          $scope.data.corSelecionada = $scope.parecer.cor.nome;

          if($scope.parecer.cor.id == 10) {
            $scope.parecer.situacao = $scope.data.sitAlcancado;
          }else if($scope.parecer.situacao.id == 2){
            $scope.parecer.situacao = {};
          }

          if($scope.parecer.cor.id == 11) {
            $scope.parecer.situacao = $scope.data.sitCancelado;
          }else if($scope.parecer.situacao.id == 17){
            $scope.parecer.situacao = {};
          }
        };

        $scope.changeSituacao = function() {
          if($scope.parecer.situacao.id == 2) {
            $scope.parecer.cor = $scope.data.corAlcancado;
            $scope.data.corSelecionada = $scope.parecer.cor.nome;
          }else if($scope.parecer.cor.id == 10) {
            $scope.parecer.cor = {};
            $scope.data.corSelecionada = 'branco';
          }

          if($scope.parecer.situacao.id == 17) {
            $scope.parecer.cor = $scope.data.corCancelado;
            $scope.data.corSelecionada = $scope.parecer.cor.nome;
          }else if($scope.parecer.cor.id == 11) {
            $scope.parecer.cor = {};
            $scope.data.corSelecionada = 'branco';
          }

        };
      }
    }

    function pemsSecretarias() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService', '$rootScope'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/pems-secretarias.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService, $rootScope) {
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

        $rootScope.$on('pems:limparFiltros', function() {
          $scope.secSelec = [];
        });
      }
    }

    function pemsStatusBar() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'colors', 'pemsFilterService', '$rootScope'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/pems-status-bar.html',
        controller: controller
      };
      function controller($scope, $element, pemsService, colors, pemsFilterService, $rootScope) {
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

        $rootScope.$on('pems:exercicioAlterado', function(event) {
          loadStatus(pemsService, pemsFilterService, pieOptionsCor, $scope);
        });

        $rootScope.$on('pems:perspectivaAlterada', function(event) {
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
          pemsFilterService.mudarPerspectiva(newValue);
          pemsService.loadOEs(function(oes) {
            $scope.oes = oes;
          });
        });
      }
    }

    function pemsEtiquetas() {
      controller.$inject = ['$scope', '$element', '$timeout', 'pemsService', 'pemsFilterService', '$rootScope'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/etiquetas.html',
        controller: controller
      };

      function controller($scope, $element, $timeout, pemsService, pemsFilterService, $rootScope) {
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

        $rootScope.$on('pems:limparFiltros', function() {
          $scope.uiselectEt.etSelectionadas = [];
        });
      }
    }

    function pemsStatusFilter() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService', '$rootScope'];
      return {
        restrict: 'E',
        templateUrl: 'app/views/cached/status-filter.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService, $rootScope) {
        $scope.listaStatus = pemsFilterService.listaStatusFiltros();

        $scope.selStatus = function(status) {
          pemsFilterService.addRemoveStatus(status.codCor);
          status.sel = !status.sel;
        }

        $rootScope.$on('pems:limparFiltros', function() {
          for (var i = 0; i < $scope.listaStatus.length; i++) {
            $scope.listaStatus[i].sel = false;
          }  
        });
      }
    }

    function pemsListaItens() {
      controller.$inject = ['$scope', '$element', 'pemsService', 'pemsFilterService', '$state', '$rootScope'];
      return {
        restrict: 'E',
        scope: {
          nivel: '@'
        },
        templateUrl: 'app/views/cached/lista-itens-direc.html',
        controller: controller
      };

      function controller($scope, $element, pemsService, pemsFilterService, $state, $rootScope) {
        $rootScope.$on('pems:exercicioAlterado', function(event) {
          pemsService.loadListaItens(pemsFilterService.getFiltros(), function(listaItens) {
            $scope.listaItens = listaItens;
          });
        });
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