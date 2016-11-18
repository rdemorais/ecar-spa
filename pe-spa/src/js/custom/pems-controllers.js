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
        .controller('trocaSenhaController', trocaSenhaController)
        .controller('appController', appController)
        .factory('truncate', stripTags);

    trocaSenhaController.$inject = ['$scope', '$state', 'pemsService', 'OAuth', '$rootScope'];
    function trocaSenhaController($scope, $state, pemsService, OAuth, $rootScope) {
        $scope.data = {
            verificado: false,
            email: '',
            cpf: '',
            senha1: '',
            senha2: '',
            nomeUsuario: ''
        }

        $scope.verificar = function() {
            pemsService.verificarUsuario({email: $scope.data.email, cpf: $scope.data.cpf}, function(user) {
                if(user != null) {
                    $scope.data.verificado = true;
                    $scope.data.nomeUsuario = user.nomeUsuario;
                }else {
                    console.log('null - apresentar erro');
                }
            });
        }

        $scope.trocar = function() {
            pemsService.trocaSenha({email: $scope.data.email, cpf: $scope.data.cpf, novaSenha: $scope.data.senha2}, function(ret) {
                var indexOfArroba = $scope.data.email.indexOf('@');
                var login = $scope.data.email.substring(0, indexOfArroba);
                
                OAuth.getAccessToken({
                    username: login,
                    password: $scope.data.senha2
                }).then(function() {
                    if(OAuth.isAuthenticated()) {
                        pemsService.loadOEs(function(oes){});
                        $rootScope.$emit('oauth:login');
                        $state.go('app.dashboard');
                    }
                });
            });
        }
    }

    appController.$inject = ['$scope', 'OAuth', '$state', 'pemsService', '$rootScope'];
    function appController($scope, OAuth, $state, pemsService, $rootScope) {
        $scope.isAuth = false;
        $scope.nomeUsuario = '';

        $scope.logout = function() {
            pemsService.logout(function(ret) {
                if(ret) {
                    OAuth.removeToken();
                    $state.go('app.login');
                    $scope.isAuth = false;
                    $scope.nomeUsuario = '';
                    $state.go('app.login');
                }
            });
        };

        $rootScope.$on('oauth:login', function() {
            pemsService.loadNomeUsuario(function(nome) {
                $scope.nomeUsuario = nome;
                $scope.isAuth = true;
            });
        });
    }

    loginController.$inject = ['$scope', '$state', 'OAuth', 'pemsService', '$rootScope'];
    function loginController($scope, $state, OAuth, pemsService, $rootScope) {
        $scope.login = '';
        $scope.senha = '';

        $scope.loginEcar = function() {
            OAuth.getAccessToken({
                username: $scope.login,
                password: $scope.senha
            }).then(function() {
                if(OAuth.isAuthenticated()) {
                    pemsService.loadOEs(function(oes){});
                    $rootScope.$emit('oauth:login');         
                    $state.go('app.dashboard');
                }
            });
        }
    }

    dashboardController.$inject = ['$scope', '$state', 'pemsFilterService', 'pemsService', '$rootScope'];
    function dashboardController($scope, $state, pemsFilterService, pemsService, $rootScope) {
        
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa,
            minhaVisao: pemsFilterService.getFiltros().minhaVisao
        };

        $scope.persp = pemsFilterService.getFiltros().pns ? 'pns' : 'ppa';

        $scope.pesquisar = function() {
            pemsFilterService.setPpa($scope.filtro.ppa);
            pemsFilterService.setMeta($scope.filtro.meta);
            pemsFilterService.setIniciativa($scope.filtro.iniciativa);
            pemsFilterService.setMinhaVisao($scope.filtro.minhaVisao);

            $state.go('app.lista-itens');
        };

        $scope.limparFiltros = function() {
            $scope.filtro = {
                ppa: false,
                meta: false,
                iniciativa: false,
                minhaVisao: false
            };
            $rootScope.$emit('pems:limparFiltros');
        };
    }

    listaItensController.$inject = ['$scope', '$state', 'pemsFilterService', 'pemsService', '$rootScope'];
    function listaItensController($scope, $state, pemsFilterService, pemsService, $rootScope) {
        $scope.filtro = {
            ppa: pemsFilterService.getFiltros().ppa,
            meta: pemsFilterService.getFiltros().meta,
            iniciativa: pemsFilterService.getFiltros().iniciativa,
            minhaVisao: pemsFilterService.getFiltros().minhaVisao
        };

        $scope.persp = pemsFilterService.getFiltros().pns ? 'pns' : 'ppa';

        $scope.gerarRelatorioGerencial = function() {
          pemsService.gerarRelatorioGerencial(pemsFilterService.getFiltros());
        }

        $scope.gerarRelatorioExecutivoPareceres = function() {
            pemsService.gerarRelatorioExecutivoPareceres(pemsFilterService.getFiltros());
        }

        $scope.pesquisar = function() {
            pemsFilterService.setPpa($scope.filtro.ppa);
            pemsFilterService.setMeta($scope.filtro.meta);
            pemsFilterService.setIniciativa($scope.filtro.iniciativa);
            pemsFilterService.setMinhaVisao($scope.filtro.minhaVisao);

            $scope.$broadcast('pems:pesquisar-lista');
        }

        $scope.limparFiltros = function() {
            $scope.filtro = {
                ppa: false,
                meta: false,
                iniciativa: false,
                minhaVisao: false
            };
            $rootScope.$emit('pems:limparFiltros');
        };
    }

    itemDashController.$inject = ['$scope', '$state', '$stateParams', '$sce', 'pemsService', 'pemsFilterService', 'truncate', 'SwAlert'];
    function itemDashController($scope, $state, $stateParams, $sce, pemsService, pemsFilterService, truncate, SwAlert) {
        var vm = this;
        vm.parecer = '';

        $scope.anexos = [];
        pemsService.loadAnexos(pemsFilterService.getFiltros(), function(anexos) {
            $scope.anexos = anexos;
        });

        $scope.downloadAnexo = function(anexo) {
            pemsService.downloadAnexo(anexo);
        }

        $scope.excluirAnexoServer = function(anexo) {
            SwAlert.confirm(
                'Exclusão de Anexo', 
                'Esta ação excluirá permanentemente o anexo da base de dados!',
                function() {
                    pemsService.excluirAnexoServer(anexo, function(resp) {
                        if(resp) {
                            pemsService.loadAnexos(pemsFilterService.getFiltros(), function(anexos) {
                                $scope.anexos = anexos;
                            });
                            SwAlert.success('Sucesso', 'Anexo excluído da base de dados');
                        }
                    });
                }
            );
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

        $scope.gerarRelatorioExecutivoPareceresAnteriores = function() {
            pemsService.gerarRelatorioExecutivoPareceresAnteriores(pemsFilterService.getFiltros());
        }

        pemsFilterService.getFiltros().codIett = $stateParams.itemId;
        pemsFilterService.getFiltros().nivel = $stateParams.nivel;
        pemsService.loadItem(pemsFilterService.getFiltros(), function(item) {
            vm.item = item;
            vm.item.parecer = truncate(vm.item.parecer, '<a><br><ul><li><strong><b><table><p><i><ol><td><tr><h1><h2><h3>');
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