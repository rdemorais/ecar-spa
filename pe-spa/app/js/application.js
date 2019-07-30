/*!
 * 
 * PEMS - MINISTERIO DA SAUDE
 * 
 */

angular
  .module('naut', [
    'ngRoute',
    'ngAnimate',
    'ngStorage',
    'ngCookies',
    'ngSanitize',
    'ngResource',
    'ui.bootstrap',
    'ui.router',
    'ui.utils',
    'oc.lazyLoad',
    'cfp.loadingBar',
    'pascalprecht.translate'
]);

(function() {
    'use strict';
    angular
        .module('naut')
        .config(oauthConfig)
        .factory('oauthInterceptor', oauthInterceptor)
        .provider('OAuth', OAuthProvider)
        .provider('OAuthToken', OAuthTokenProvider);


    var defaults = {
		  baseUrl: null,
		  clientId: null,
		  clientSecret: null,
		  grantPath: '/oauth2/token',
		  revokePath: '/oauth2/revoke'
		};

		var requiredKeys = [
		  'baseUrl',
		  'clientId',
		  'grantPath',
		  'revokePath'
		];

		/**
		 * OAuth provider.
		 */

		function OAuthProvider() {
		  var config;

		  /**
		   * Configure.
		   *
		   * @param {object} params - An object of params to extend.
		   */

		  this.configure = function(params) {
		    // Can only be configured once.
		    if (config) {
		      throw new Error('Already configured.');
		    }

		    // Check if is an object.
		    if (!(params instanceof Object)) {
		      throw new TypeError('Invalid argument: config must be an Object.');
		    }

		    // Extend default configuration.
		    config = angular.extend({}, defaults, params);

		    // Check if all required keys are set.
		    /*
		    angular.forEach(requiredKeys, (key) => {
		      if (!config[key]) {
		        throw new Error('Missing parameter: ' + key);
		      }
		    });*/

		    // Remove baseUrl trailing slash.
		    if('/' === config.baseUrl.substr(-1)) {
		      config.baseUrl = config.baseUrl.slice(0, -1);
		    }

		    // Add grantPath facing slash.
		    if('/' !== config.grantPath[0]) {
		      config.grantPath = "/" + config.grantPath;
		    }

		    // Add revokePath facing slash.
		    if('/' !== config.revokePath[0]) {
		      config.revokePath = "/" + config.revokePath;
		    }

		    return config;
		  };

		  /**
		   * OAuth service.
		   */

		  this.$get = function($http, OAuthToken) {
		    var OAuth = function() {

		      /**
		       * Check if OAuthProvider is configured.
		       */

		      this.constructor = function() {
		        if (!config) {
		          throw new Error('OAuthProvider must be configured first.');
		        }
		      }

		      /**
		       * Verifies if the user is authenticated or not based on the token
		       * cookie.
		       *
		       * @return {boolean}
		       */

		      this.isAuthenticated = function() {
		        return !!OAuthToken.getToken();
		      }

		      /**
		       * Retrieves the access_token and stores the response.data on cookies
		       * using the OAuthToken.
		       *
		       * @param {object} data - Request content, e.g., username and password.
		       * @param {object} options - Optional configuration.
		       * @return {promise} A response promise.
		       */

		      this.getAccessToken = function(data, options) {
		        data = angular.extend({
		          client_id: config.clientId,
		          grant_type: 'password'
		        }, data);

		        if (null !== config.clientSecret) {
		          data.client_secret = config.clientSecret;
		        }

		        data = queryString.stringify(data);

		        options = angular.extend({
		          headers: {
		            'Authorization': undefined,
		            'Content-Type': 'application/x-www-form-urlencoded'
		          }
		        }, options);

		        return $http.post(config.baseUrl + config.grantPath, data, options).then(function(response) {
		          OAuthToken.setToken(response.data);

		          return response;
		        });
		      }

		      /**
		       * Retrieves the refresh_token and stores the response.data on cookies
		       * using the OAuthToken.
		       *
		       * @param {object} data - Request content.
		       * @param {object} options - Optional configuration.
		       * @return {promise} A response promise.
		       */

		      this.getRefreshToken = function(data, options) {
		        data = angular.extend({
		          client_id: config.clientId,
		          grant_type: 'refresh_token',
		          refresh_token: OAuthToken.getRefreshToken(),
		        }, data);

		        if (null !== config.clientSecret) {
		          data.client_secret = config.clientSecret;
		        }

		        data = queryString.stringify(data);

		        options = angular.extend({
		          headers: {
		            'Authorization': undefined,
		            'Content-Type': 'application/x-www-form-urlencoded'
		          }
		        }, options);

		        return $http.post(config.baseUrl + config.grantPath, data, options).then(function(response) {
		          OAuthToken.setToken(response.data);

		          return response;
		        });
		      }

		      /**
		       * Revokes the token and removes the stored token from cookies
		       * using the OAuthToken.
		       *
		       * @param {object} data - Request content.
		       * @param {object} options - Optional configuration.
		       * @return {promise} A response promise.
		       */

		      this.revokeToken = function(data, options) {
		        var refreshToken = OAuthToken.getRefreshToken();

		        data = angular.extend({
		          client_id: config.clientId,
		          token: refreshToken ? refreshToken : OAuthToken.getAccessToken(),
		          token_type_hint: refreshToken ? 'refresh_token' : 'access_token'
		        }, data);

		        if (null !== config.clientSecret) {
		          data.client_secret = config.clientSecret;
		        }

		        data = queryString.stringify(data);

		        options = angular.extend({
		          headers: {
		            'Content-Type': 'application/x-www-form-urlencoded'
		          }
		        }, options);

		        return $http.post(config.baseUrl + config.revokePath, data, options).then(function(response) {
		          OAuthToken.removeToken();

		          return response;
		        });
		      }

					this.removeToken = function() {
		        return OAuthToken.removeToken();
		      }
		    }

		    return new OAuth();
		  };

		  this.$get.$inject = ['$http', 'OAuthToken'];
		}    

    function OAuthTokenProvider() {
		  var config = {
		    name: 'token',
		    options: {
		      secure: true
		    }
		  };

		  /**
		   * Configure.
		   *
		   * @param {object} params - An object of params to extend.
		   */

		  this.configure = function(params) {
		    // Check if is an object.
		    if (!(params instanceof Object)) {
		      throw new TypeError('Invalid argument: config must be an Object.');
		    }

		    // Extend default configuration.
		    angular.extend(config, params);

		    return config;
		  };

		  /**
		   * OAuthToken service.
		   */

		  this.$get = function($rootScope) {
		    var OAuthToken = function() {

		      /**
		       * Set token.
		       */

		      this.setToken = function(data) {
		      	$rootScope.$storage.authToken = data;
		        //return $cookies.put(config.name, data, config.options);
		        return $rootScope.$storage.authToken;
		      }

		      /**
		       * Get token.
		       */

		      this.getToken = function() {
		        //return $cookies.get(config.name);
		        return $rootScope.$storage.authToken
		      }

		      /**
		       * Get accessToken.
		       */

		      this.getAccessToken = function() {
		        return this.getToken() ? this.getToken().access_token : undefined;
		      }

		      /**
		       * Get authorizationHeader.
		       */

		      this.getAuthorizationHeader = function() {
		        if (!(this.getTokenType() && this.getAccessToken())) {
		          return;
		        }

		        return this.getTokenType().charAt(0).toUpperCase() + this.getTokenType().substr(1) + this.getAccessToken();
		      }

		      /**
		       * Get refreshToken.
		       */

		      this.getRefreshToken = function() {
		        return this.getToken() ? this.getToken().refresh_token : undefined;
		      }

		      /**
		       * Get tokenType.
		       */

		      this.getTokenType = function() {
		        return this.getToken() ? this.getToken().token_type : undefined;
		      }

		      /**
		       * Remove token.
		       */

		      this.removeToken = function() {
		        //return $cookies.remove(config.name, config.options);
		        $rootScope.$storage.authToken = undefined;
		        return $rootScope.$storage.authToken;
		      }
		    }

		    return new OAuthToken();
		  };

		  this.$get.$inject = ['$rootScope'];
		}    

    oauthConfig.$inject = ['$httpProvider'];
    function oauthConfig($httpProvider) {
		  $httpProvider.interceptors.push('oauthInterceptor');
		}

		oauthInterceptor.$inject = ['$q', '$rootScope', 'OAuthToken'];
		function oauthInterceptor($q, $rootScope, OAuthToken) {
		  return {
		    request: function(config) {
		      config.headers = config.headers || {};

		      // Inject Authorization header.
		      if (!config.headers.hasOwnProperty('Authorization') && OAuthToken.getAuthorizationHeader()) {
		        config.headers.Authorization = OAuthToken.getAuthorizationHeader();
		      }

		      return config;
		    },
		    responseError: function(rejection) {
		      // Catch invalid_request and invalid_grant errors and ensure that the token is removed.
		      if (400 === rejection.status && rejection.data &&
		        ('invalid_request' === rejection.data.error || 'invalid_grant' === rejection.data.error)
		      ) {
		        OAuthToken.removeToken();

		        $rootScope.$emit('oauth:error', rejection);
		      }

		      // Catch invalid_token and unauthorized errors.
		      // The token isn't removed here so it can be refreshed when the invalid_token error occurs.
		      if (401 === rejection.status &&
		        (rejection.data && 'invalid_token' === rejection.data.error) ||
		        (rejection.headers('www-authenticate') && 0 === rejection.headers('www-authenticate').indexOf('Bearer'))
		      ) {
		        $rootScope.$emit('oauth:error', rejection);
		      }

		      return $q.reject(rejection);
		    }
		  };
	} 
})();
(function() {
    'use strict';
    angular
    	.module('naut')
    	.directive('froalaNg', froala);

    	froala.$inject = ['$timeout']
    	function froala($timeout) {
    		return {
	        restrict: 'A',
	        require: '?ngModel',
	        scope: {
	        	language: '@',
	        	toolbarButtons: '@',
	        	ngModel: '='
	        },
	        controller: controller,
	        link: link
	      };

	      controller.$inject = ['$scope', '$element', '$attrs'];
	      function controller($scope, $element, $attrs) {
					
	      };

	      link.$inject = ['$scope', '$element', '$attrs', 'ngModel'];
	      function link($scope, $element, $attrs, ngModel) {

					$element.froalaEditor({
						toolbarButtons: $scope.toolbarButtons,
						language: $scope.language,
						quickInsertButtons: ['table', 'ul', 'ol', 'hr'],
						heightMin: 350,
						key: 'Etf1ktA3zkB-21hhD6hD-8gF1qf=='
					});

	      	$element.on('froalaEditor.contentChanged', function (e, editor) {
				var value = $element.froalaEditor('html.get', true);
				var cleredHtml = $element.froalaEditor('clean.html', value, ['font', 'fontFamily', 'font-family', 'style']);
				ngModel.$setViewValue(cleredHtml);
			});

	      	$timeout(function() {
	      		var cleredHtml = $element.froalaEditor('clean.html', ngModel.$viewValue, ['font', 'fontFamily', 'font-family']);
	      		if(cleredHtml == '') {
	      			cleredHtml = '<strong>Situa&ccedil;&atilde;o Atual</strong><br><i>[escreva aqui]</i><br><br><strong>Pontos Cr&iacute;ticos</strong><br><i>[escreva aqui]</i><br><br><strong>Recomenda&ccedil;&otilde;es</strong><br><i>[escreva aqui]</i>';
	      		}
	      		$element.froalaEditor('html.set', cleredHtml);
	      	});
	      }
    	}
})();
/**=========================================================
 * Module: pemsController.js
 =========================================================*/

(function () {
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

    $scope.verificar = function () {
      pemsService.verificarUsuario({ email: $scope.data.email, cpf: $scope.data.cpf }, function (user) {
        if (user != null) {
          $scope.data.verificado = true;
          $scope.data.nomeUsuario = user.nomeUsuario;
        } else {
          console.log('null - apresentar erro');
        }
      });
    }

    $scope.trocar = function () {
      pemsService.trocaSenha({ email: $scope.data.email, cpf: $scope.data.cpf, novaSenha: $scope.data.senha2 }, function (ret) {
        var indexOfArroba = $scope.data.email.indexOf('@');
        var login = $scope.data.email.substring(0, indexOfArroba);

        OAuth.getAccessToken({
          username: login,
          password: $scope.data.senha2
        }).then(function () {
          if (OAuth.isAuthenticated()) {
            pemsService.loadOEs(function (oes) { });
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

    $scope.logout = function () {
      pemsService.logout(function (ret) {
        if (ret) {
          OAuth.removeToken();
          $state.go('app.login');
          $scope.isAuth = false;
          $scope.nomeUsuario = '';
          $state.go('app.login');
        }
      });
    };

    $rootScope.$on('oauth:login', function () {
      pemsService.loadNomeUsuario(function (nome) {
        $scope.nomeUsuario = nome;
        $scope.isAuth = true;
      });
    });
  }

  loginController.$inject = ['$scope', '$state', 'OAuth', 'pemsService', '$rootScope'];
  function loginController($scope, $state, OAuth, pemsService, $rootScope) {
    $scope.login = '';
    $scope.senha = '';

    $scope.loginEcar = function () {
      OAuth.getAccessToken({
        username: $scope.login,
        password: $scope.senha
      }).then(function () {
        if (OAuth.isAuthenticated()) {
          pemsService.loadOEs(function (oes) { });
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
      minhaVisao: pemsFilterService.getFiltros().minhaVisao,
      codExe: pemsFilterService.getFiltros().codExe
    };

    $scope.mudarExercicio = function () {
      pemsFilterService.mudarExercicio($scope.filtro.codExe);
    }

    $scope.persp = pemsFilterService.getFiltros().pns ? 'pns' : 'ppa';

    $scope.pesquisar = function () {
      pemsFilterService.setPpa($scope.filtro.ppa);
      pemsFilterService.setMeta($scope.filtro.meta);
      pemsFilterService.setIniciativa($scope.filtro.iniciativa);
      pemsFilterService.setMinhaVisao($scope.filtro.minhaVisao);

      $state.go('app.lista-itens');
    };

    $scope.limparFiltros = function () {
      $scope.filtro.ppa = false;
      $scope.filtro.meta = false;
      $scope.filtro.iniciativa = false;
      $scope.filtro.minhaVisao = false;

      $rootScope.$emit('pems:limparFiltros');
    };
  }

  listaItensController.$inject = ['$scope', '$state', 'pemsFilterService', 'pemsService', '$rootScope'];
  function listaItensController($scope, $state, pemsFilterService, pemsService, $rootScope) {
    $scope.filtro = {
      ppa: pemsFilterService.getFiltros().ppa,
      meta: pemsFilterService.getFiltros().meta,
      iniciativa: pemsFilterService.getFiltros().iniciativa,
      minhaVisao: pemsFilterService.getFiltros().minhaVisao,
      codExe: pemsFilterService.getFiltros().codExe
    };

    $scope.mudarExercicio = function () {
      pemsFilterService.mudarExercicio($scope.filtro.codExe);
    }

    $scope.persp = pemsFilterService.getFiltros().pns ? 'pns' : 'ppa';

    $scope.gerarRelatorioGerencial = function () {
      pemsService.gerarRelatorioGerencial(pemsFilterService.getFiltros());
    }

    $scope.gerarRelatorioExecutivoPareceres = function () {
      pemsService.gerarRelatorioExecutivoPareceres(pemsFilterService.getFiltros());
    }

    $scope.pesquisar = function () {
      pemsFilterService.setPpa($scope.filtro.ppa);
      pemsFilterService.setMeta($scope.filtro.meta);
      pemsFilterService.setIniciativa($scope.filtro.iniciativa);
      pemsFilterService.setMinhaVisao($scope.filtro.minhaVisao);

      $scope.$broadcast('pems:pesquisar-lista');
    }

    $scope.limparFiltros = function () {
      $scope.filtro.ppa = false;
      $scope.filtro.meta = false;
      $scope.filtro.iniciativa = false;
      $scope.filtro.minhaVisao = false;

      $rootScope.$emit('pems:limparFiltros');
    };
  }

  itemDashController.$inject = ['$scope', '$state', '$stateParams', '$sce', 'pemsService', 'pemsFilterService', 'truncate', 'SwAlert'];
  function itemDashController($scope, $state, $stateParams, $sce, pemsService, pemsFilterService, truncate, SwAlert) {
    var vm = this;
    vm.parecer = '';
    $scope.obj ={
      descProduto: '',
      descEspecificacaoProduto: '',
      linhaBase: '',
      anoLinhaBase: '',
      dataApuracao: '',
      metodoApuracao: '',
      polaridadeIndicador: '',
      periodicidade: '',
    }
    $scope.anexos = [];

    $scope.edita = false;

    pemsService.loadAnexos(pemsFilterService.getFiltros(), function (anexos) {
      $scope.anexos = anexos;
    });

    $scope.downloadAnexo = function (anexo) {
      pemsService.downloadAnexo(anexo);
    }

    $scope.excluirAnexoServer = function (anexo) {
      SwAlert.confirm(
        'Exclusão de Anexo',
        'Esta ação excluirá permanentemente o anexo da base de dados!',
        function () {
          pemsService.excluirAnexoServer(anexo, function (resp) {
            if (resp) {
              pemsService.loadAnexos(pemsFilterService.getFiltros(), function (anexos) {
                $scope.anexos = anexos;
              });
              SwAlert.success('Sucesso', 'Anexo excluído da base de dados');
            }
          });
        }
      );
    }

    $scope.itemSelecionadoDash = function (_itemId, _nivel) {
      $state.go("app.dash-item", { itemId: _itemId, nivel: _nivel });
    }

    $scope.retornarListaItens = function () {
      $state.go("app.lista-itens");
    }

    $scope.gerarRelatorioExecutivo = function () {
      pemsService.gerarRelatorioExecutivo(pemsFilterService.getFiltros());
    }

    $scope.gerarRelatorioExecutivoPareceresAnteriores = function () {
      pemsService.gerarRelatorioExecutivoPareceresAnteriores(pemsFilterService.getFiltros());
    }
    $scope.botaoEditar = function (){
      $scope.edita = !$scope.edita;
    }
    $scope.botaoSalvar = function(){
      $scope.edita = !$scope.edita;
      pemsService.loadCamposIndicador($scope.obj, $stateParams.itemId);
      pemsFilterService.mudarExercicio($scope.filtro.codExe);     
    }

    pemsFilterService.getFiltros().codIett = $stateParams.itemId;
    pemsFilterService.getFiltros().nivel = $stateParams.nivel;
    pemsService.loadItem(pemsFilterService.getFiltros(), function (item) {
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
        .replace(tags, function ($0, $1) {
          return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
        });
    }
  }

})();
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
(function() {
	'use strict';
	angular
		.module('naut')
		.provider('SwAlert', SwAlertProvider)
		.provider('NumbroFormater', NumbroFormaterProvider);

		function SwAlertProvider() {
			var swal = window.swal;
			
			this.$get = function($rootScope) {
				var SwAlert = function() {
					this.error = function(title, text) {
						$rootScope.$evalAsync(function(){
							swal( title, text, 'error' );
						});
					}

					this.success = function(title, text) {
						$rootScope.$evalAsync(function(){
							swal( title, text, 'success' );
						});
					}

					this.confirm = function(title, text, callback) {
						$rootScope.$evalAsync(function() {
							swal({
								title: title,
								text: text, 
								type: 'warning',
								showCancelButton: true,
							  closeOnConfirm: false,
							  showLoaderOnConfirm: true,
							  confirmButtonText: 'Ok',
							  cancelButtonText: 'Cancelar'
							},
							function() {
								callback();
							});
						});
					}
				}
				return new SwAlert();
			}
			this.$get.$inject = ['$rootScope']
		};

		function NumbroFormaterProvider() {
			var numbro = window.numbro;

			this.$get = function() {
				var NumbroFormater = function() {
					numbro.culture('pt-BR');
					this.fmt = function(text, _format) {
						return numbro(text).format(_format);
					}
				}

				return new NumbroFormater();
			}
		};
})();
(function() {
    'use strict';
    angular
        .module('naut')
        .service('pemsService', pemsService)
        .service('pemsFilterService', pemsFilterService)
        .service('pemsServiceError', pemsServiceError);

    pemsService.$inject = ['$http', '$q', '$rootScope', 'pemsFilterService']
    function pemsService($http, $q, $rootScope, pemsFilterService) {

        var fixedArrays = {
            oes: [],
            oesPns: []
        };

        $rootScope.$on('pems:limparFiltros', function() {
            for (var i = 0; i < fixedArrays.oes.length; i++) {
                fixedArrays.oes[i].sel = false;
            };

            for (var i = 0; i < fixedArrays.oesPns.length; i++) {
                fixedArrays.oesPns[i].sel = false;
            };
        });

        this.gerarRelatorioExecutivo = function(filtro) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/download-rel-executivo',
                headers: {
                    accept: 'application/pdf'
                },
                responseType: 'arraybuffer',
                data: filtro
            }).then(function successCallBack(response) {
                
                var file = new Blob([response.data], {type: 'application/pdf'});
                
                var fileURL = URL.createObjectURL(file);

                var a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";
                a.href = fileURL;
                a.download = 'relatorio-executivo.pdf';
                a.click();
                //window.URL.revokeObjectURL(url);

            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        }

        this.gerarRelatorioExecutivoPareceresAnteriores = function(filtro) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/download-rel-executivo-pareceres-anteriores',
                headers: {
                    accept: 'application/pdf'
                },
                responseType: 'arraybuffer',
                data: filtro
            }).then(function successCallBack(response) {
                
                var file = new Blob([response.data], {type: 'application/pdf'});
                
                var fileURL = URL.createObjectURL(file);

                var a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";
                a.href = fileURL;
                a.download = 'relatorio-executivo-pareceres-anteriores.pdf';
                a.click();
                //window.URL.revokeObjectURL(url);

            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        }

        this.gerarRelatorioExecutivoPareceres = function(filtro) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/download-rel-executivo-pareceres',
                headers: {
                    accept: 'application/pdf'
                },
                responseType: 'arraybuffer',
                data: filtro
            }).then(function successCallBack(response) {
                
                var file = new Blob([response.data], {type: 'application/pdf'});
                
                var fileURL = URL.createObjectURL(file);

                var a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";
                a.href = fileURL;
                a.download = 'relatorio-executivo-pareceres.pdf';
                a.click();
                //window.URL.revokeObjectURL(url);

            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        }

        this.gerarRelatorioGerencial = function(filtro) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/download-rel-gerencial',
                headers: {
                    accept: 'application/pdf'
                },
                responseType: 'arraybuffer',
                data: filtro
            }).then(function successCallBack(response) {
                
                var file = new Blob([response.data], {type: 'application/pdf'});
                
                var fileURL = URL.createObjectURL(file);

                var a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";
                a.href = fileURL;
                a.download = 'relatorio-gerencial.pdf';
                a.click();
                //window.URL.revokeObjectURL(url);

            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        }

        this.loadEtiquetas = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/etiquetas',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var etiquetas = ecarResponse.obj;
                    callback(etiquetas);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

    	this.loadItem = function(filtro, callback) {

            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/load-item',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: filtro
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var item = ecarResponse.obj;
                    callback(item);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });            
    	};

        this.loadAnexos = function(filtro, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/anexos',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: filtro
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var anexos = ecarResponse.obj;
                    callback(anexos);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            }); 
        };

        this.downloadAnexo = function(anexo) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/download-anexo',
                responseType: 'arraybuffer',
                data: anexo.id
            }).then(function successCallBack(response) {
                
                var file = new Blob([response.data], {type: 'application/octet-stream'});
                
                var fileURL = window.URL.createObjectURL(file);

                var a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";
                a.href = fileURL;
                a.download = anexo.nomeOriginal;
                a.click();
                window.URL.revokeObjectURL(url);

            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };
        

        this.excluirAnexoServer = function(anexo, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/excluir-anexo',
                data: anexo,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    callback(true);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

    	this.loadAtividades = function(filtro, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/lista-itens-atv',
                data: filtro,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var listaItens = ecarResponse.obj;
                    callback(listaItens);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
    	};

    	this.loadProdutos = function(filtro, callback) {
    		$http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/lista-itens-pi',
                data: filtro,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var listaItens = ecarResponse.obj;
                    callback(listaItens);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
    	};

    	this.loadListaItens = function(filtro, callback) {
    		$http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/lista-itens',
                data: filtro,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var listaItens = ecarResponse.obj;
                    callback(listaItens);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
    	};

    	this.loadStatus = function(filtro, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/status',
                data: filtro,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var statusBar = ecarResponse.obj;
                    callback(statusBar);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
    	};

        this.loadSecretarias = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/lista-secretarias',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var secs = ecarResponse.obj;
                    callback(secs);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

        this.listSituacoes = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/lista-situacoes',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var sits = ecarResponse.obj;
                    callback(sits);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

        this.listCores = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/lista-cores',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var cores = ecarResponse.obj;
                    callback(cores);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

    	this.loadOEs = function(callback) {
            var url = '';
            if(pemsFilterService.getFiltros().pns == true) {
                url = '/lista-oes-pns';
            }else if(pemsFilterService.getFiltros().pns == false) {
                url = '/lista-oes';
            }
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + url,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    fixedArrays.oes = ecarResponse.obj;
                    for (var i = 0; i < fixedArrays.oes.length; i++) {
                        fixedArrays.oes[i].sel = false;
                        fixedArrays.oes[i].ord = i+1;
                    };
                    callback(fixedArrays.oes);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
    	};

        this.gravarParecer = function(parecer, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/gravar-parecer',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: parecer
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    callback(true);
                }else {
                    console.log(ecarResponse.msg);
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

        this.verificarUsuario = function(user, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrlTrocaSenha + '/verificar-usuario',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: user
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    callback(ecarResponse.obj);
                }else {
                    console.log(ecarResponse.msg);
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

        this.trocaSenha = function(user, callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrlTrocaSenha + '/troca-senha',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: user
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    callback(ecarResponse.obj);
                }else {
                    console.log(ecarResponse.msg);
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };

        this.logout = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/logout',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    callback(true);
                }else {
                    callback(false);
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        }

        this.loadNomeUsuario = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/nome-usuario',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    callback(ecarResponse.obj);
                }else {
                    console.log(ecarResponse.msg);
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });
        };
        this.loadCampos = function(filtro, callback) {

            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/campos-indicador',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: filtro
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var item = ecarResponse.obj;
                    callback(item);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });            
        };
            	this.loadItem = function(filtro, callback) {

            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/load-item',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: filtro
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var item = ecarResponse.obj;
                    callback(item);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });            
        }

        this.loadCamposIndicador = function(filtro, id, callback) {
            // console.log(filtro)
            $http({
                method: 'PUT',
                url: $rootScope.app.baseUrl + `/campos-indicador/${id}`,
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
                data: filtro
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var item = ecarResponse.obj;
                    callback(item);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                $rootScope.$emit('oauth:error', response);
            });            
    	} 
    }

    //pemsServiceError.$inject = ['SwAlert']
    function pemsServiceError() {
        this.showInvalidGrantMsg = function() {
            
        }
    }

    pemsFilterService.$inject = ['$rootScope']
    function pemsFilterService($rootScope) {
        var filtros = {
            pns: false,
            ppa: false,
            meta: false,
            minhaVisao: false,
            iniciativa: false,
            status: [],
            oes: [],
            etiquetas: [],
            secretarias: [],
            codExe: 2,
            codIett: -1,
            nivel: null
        };

        var listaStatusFiltrosGerada = false;
        var listaStFiltrosSel;

        $rootScope.$on('pems:limparFiltros', function() {

            filtros.pns = false;
            filtros.meta = false;
            filtros.iniciativa = false;
            filtros.status = [];
            filtros.oes = [];
            filtros.etiquetas = [];
            filtros.secretarias = [];
            filtros.codIett = -1;
            filtros.nivel = null;
            // filtros.codExe = 2;
        });

        this.mudarExercicio = function(exe) {
            filtros.codExe = exe;
            $rootScope.$emit('pems:exercicioAlterado');
        }

        this.mudarPerspectiva = function(p) {
            //this.clear();

            if(p == 'ppa') {
                filtros.pns = false;
            }else if(p == 'pns') {
                filtros.pns = true;
            }

            filtros.oes = [];

            $rootScope.$emit('pems:perspectivaAlterada');
        };

        this.getSecretarias = function() {
            return filtros.secretarias;
        };
        
        this.setSecretarias = function(secs) {
            filtros.secretarias = secs;
        };

        this.setPpa = function(ppa) {
            filtros.ppa = ppa;
        };

        this.setMeta = function(meta) {
            filtros.meta = meta;
        };

        this.setIniciativa = function(iniciativa) {
            filtros.iniciativa = iniciativa;
        };

        this.setMinhaVisao = function(mv) {
            filtros.minhaVisao = mv;
        };

        this.setStatusFilter = function(status) {
            filtros.status = status;
        };

        this.addRemoveOe = function(oe) {
            var indexOf = filtros.oes.indexOf(oe);
            if(indexOf != -1) {
                filtros.oes.splice(indexOf, 1);
            }else {
                filtros.oes.push(oe);
            }
        };

        this.addRemoveEtiqueta = function(etiqueta) {
            var indexOf = filtros.etiquetas.indexOf(etiqueta);
            if(indexOf != -1) {
                filtros.etiquetas.splice(indexOf, 1);
            }else {
                filtros.etiquetas.push(etiqueta);
            }
        };

        this.addRemoveStatus = function(nomeCor) {
            var indexOf = filtros.status.indexOf(nomeCor);
            if(indexOf != -1) {
                filtros.status.splice(indexOf, 1);
            }else {
                filtros.status.push(nomeCor);
            }
        };

        this.listaStatusFiltros = function() {
            var listaStFiltros = [
                    {clazz: 'img-status-verde', clazzSel: 'img-status-verde-sel', desc: 'Satisfatório', codCor: 1, sel: false},
                    {clazz: 'img-status-amarelo', clazzSel: 'img-status-amarelo-sel', desc: 'Alerta', codCor: 2, sel: false},
                    {clazz: 'img-status-vermelho', clazzSel: 'img-status-vermelho-sel', desc: 'Crítico', codCor: 3, sel: false},
                    {clazz: 'img-status-azul', clazzSel: 'img-status-azul-sel', desc: 'Alcançado', codCor: 10, sel: false},
                    {clazz: 'img-status-cinza', clazzSel: 'img-status-cinza-sel', desc: 'Cancelado', codCor: 11, sel: false},
                    {clazz: 'img-status-branco', clazzSel: 'img-status-branco-sel', desc: 'Não Monitorado', codCor: -1, sel: false}
                ];
            
            if(!listaStatusFiltrosGerada) {
                listaStFiltrosSel = listaStFiltros;
                listaStatusFiltrosGerada = true;
            }
            
            return listaStFiltrosSel;
        };

        this.getFiltros = function() {
            return filtros;
        };
    }
})();
/**=========================================================
 * Module: ColorsConstant.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .constant('COLORS', {
            // Same values from CSS
            'primary':                '#3F51B5',
            'success':                '#4caf50',
            'info':                   '#2095f2',
            'warning':                '#fe9700',
            'danger':                 '#f34235',
            'inverse':                '#363f45',
            'amber':                  '#FFC107',
            'pink':                   '#e91e63',
            'purple':                 '#6639b6',
            'orange':                 '#fe5621',
            'noir':                   '#212121',
            'white':                  '#fff',
            'gray-darker':            '#2b3d51',
            'gray-dark':              '#515d6e',
            'gray':                   '#A0AAB2',
            'gray-light':             '#e6e9ee',
            'gray-lighter':           '#f4f5f5',
            'Verde':                  '#4caf50',
            'Amarelo':                '#fe9700',
            'Vermelho':               '#f34235',
            'Azul':                   '#2095f2',
            'Cinza':                  '#A0AAB2',
            'Branco':                 '#000000'
        });
})();

/**=========================================================
 * Module: ColorsRun
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .run(appRun);

    appRun.$inject = ['$rootScope', 'colors'];
    function appRun($rootScope, colors) {
      // Request brand colors from templates e.g {{colorByName('info')}}
      $rootScope.colorByName = colors.byName;
    }

})();

/**=========================================================
 * Module: ColorsService.js
 * Services to retrieve global colors
 =========================================================*/
 
(function() {
    'use strict';

    angular
        .module('naut')
        .factory('colors', colors);
    
    colors.$inject = ['COLORS'];
    function colors(COLORS) {

      return {
        byName: function(name) {
          return (COLORS[name] || '#fff');
        }
      };
    }

})();
/**=========================================================
 * Module: AnimateEnabledDirective.js
 * Enable or disables ngAnimate for element with directive
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('animateEnabled', animateEnabled);

    animateEnabled.$inject = ['$animate'];
    function animateEnabled($animate) {

      return {
        restrict: 'A',
        link: function (scope, element, attrs) {
          scope.$watch(function () {
            return scope.$eval(attrs.animateEnabled, scope);
          }, function (newValue) {
            $animate.enabled(!!newValue, element);
          });
        }
      };
    }
})();


/**=========================================================
 * chained-animation.directive.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('chainedAnimation', chainedAnimation);
    
    function chainedAnimation() {

      return {
        restrict: 'A',
        link: link
      };

      function link(scope, element, attrs) {
          
        if ( element.parents('[chained-animation]').length ) return;

        // inspired by Kupletsky Sergey http://codepen.io/zavoloklom/pen/wtApI
        var speed = 2000;
        var childs = element.find('[chained-animation]').add(element);

        childs.each(function() {
            var child = angular.element(this);
            var elementOffset = child.offset();
            var offset = elementOffset.left*0.8 + elementOffset.top;
            var delay = parseFloat(offset/speed).toFixed(2);

            child
              .css('-webkit-animation-delay', delay+'s')
              .css('-o-animation-delay', delay+'s')
              .css('animation-delay', delay+'s')
              .addClass('animated')
              .addClass(attrs.chainedAnimation);

          });

      }
    }

})();

/**=========================================================
 * Module: heckAllTableDirective
 * Allows to use a checkbox to check all the rest in the same
 * columns in a Bootstrap table
 =========================================================*/
(function() {
    'use strict';

    angular
        .module('naut')
        .directive('checkAll', checkAll);
    
    function checkAll() {
      
      controller.$inject = ['$scope', '$element'];
      return {
        restrict: 'A',
        controller: controller
      };

      function controller($scope, $element){
        
        $element.on('change', function() {

          var th = angular.element(this);
          var index = indexInParent(this);
          var checkbox = th.find('input'); // assumes checkbox
          var table = th.parent().parent().parent(); // table > thead > tr > th

          angular.forEach( table.find('tbody').find('tr'),
            function(tr, key) {
              var tds = angular.element(tr).find('td');
              var chk = tds.eq(index).find('input'); // assumes checkbox
              if(chk && chk.length)
                chk[0].checked = checkbox[0].checked
            });

        });
      }

      function indexInParent(node) {
        var children = node.parentNode.childNodes;
        var num = 0;
        for (var i=0; i<children.length; i++) {
           if (children[i]==node) return num;
           if (children[i].nodeType==1) num++;
        }
        return -1;
      }

    }

})();

/**=========================================================
 * Module: FullscreenDirective
 * Toggle the fullscreen mode on/off
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('toggleFullscreen', toggleFullscreen);
    
    function toggleFullscreen() {

      return {
        restrict: 'A',
        link: link
      };

      function link(scope, element, attrs) {

        if (screenfull.enabled) {

          element.on('click', function (e) {
            e.preventDefault();

            screenfull.toggle();

            var icon = angular.element(this).find('em');
            // Switch icon indicator
            if(screenfull.isFullscreen)
              icon.removeClass('fa-expand').addClass('fa-compress');
            else
              icon.removeClass('fa-compress').addClass('fa-expand');
          
          });

        } else {
          element.remove();
        }
      }
    }

})();

/**=========================================================
 * Module: ResetKeyDirective.js
 * Removes a key from the browser storage via element click
 =========================================================*/
(function() {
    'use strict';

    angular
        .module('naut')
        .directive('resetKey', resetKey);
    
    resetKey.$inject = ['$state', '$rootScope'];
    function resetKey($state, $rootScope) {

      return {
        restrict: 'EA',
        link: link
      };
      
      function link(scope, element, attrs) {
        
        var resetKey = attrs.resetKey;

        element.on('click', function (e) {
            e.preventDefault();

            if(resetKey) {
              delete $rootScope.$storage[resetKey];
              $state.go($state.current, {}, {reload: true});
            }
            else {
              $.error('No storage key specified for reset.');
            }
        });
      }
    }

})();

/**=========================================================
 * Module: TitleCaseFilter.js
 * Convert any case to title
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .filter('titlecase', titlecase);

    function titlecase() {
        return filterFilter;

        ////////////////
        function filterFilter(params) {
          params = ( params === undefined || params === null ) ? '' : params;
          return params.toString().toLowerCase().replace( /\b([a-z])/g, function(ch) {
              return ch.toUpperCase();
          });
        }
    }

})();

/**=========================================================
 * Module: BrowserDetectionService.js
 * Browser detection service
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .service('browser', service);

    function service() {
      /*jshint validthis:true*/
      var matched, browser = this;

      var uaMatch = function( ua ) {
        ua = ua.toLowerCase();

        var match = /(opr)[\/]([\w.]+)/.exec( ua ) ||
          /(chrome)[ \/]([\w.]+)/.exec( ua ) ||
          /(version)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec( ua ) ||
          /(webkit)[ \/]([\w.]+)/.exec( ua ) ||
          /(opera)(?:.*version|)[ \/]([\w.]+)/.exec( ua ) ||
          /(msie) ([\w.]+)/.exec( ua ) ||
          ua.indexOf('trident') >= 0 && /(rv)(?::| )([\w.]+)/.exec( ua ) ||
          ua.indexOf('compatible') < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( ua ) ||
          [];

        var platform_match = /(ipad)/.exec( ua ) ||
          /(iphone)/.exec( ua ) ||
          /(android)/.exec( ua ) ||
          /(windows phone)/.exec( ua ) ||
          /(win)/.exec( ua ) ||
          /(mac)/.exec( ua ) ||
          /(linux)/.exec( ua ) ||
          /(cros)/i.exec( ua ) ||
          [];

        return {
          browser: match[ 3 ] || match[ 1 ] || '',
          version: match[ 2 ] || '0',
          platform: platform_match[ 0 ] || ''
        };
      };

      matched = uaMatch( window.navigator.userAgent );

      if ( matched.browser ) {
        browser[ matched.browser ] = true;
        browser.version = matched.version;
        browser.versionNumber = parseInt(matched.version);
      }

      if ( matched.platform ) {
        browser[ matched.platform ] = true;
      }

      // These are all considered mobile platforms, meaning they run a mobile browser
      if ( browser.android || browser.ipad || browser.iphone || browser[ 'windows phone' ] ) {
        browser.mobile = true;
      }

      // These are all considered desktop platforms, meaning they run a desktop browser
      if ( browser.cros || browser.mac || browser.linux || browser.win ) {
        browser.desktop = true;
      }

      // Chrome, Opera 15+ and Safari are webkit based browsers
      if ( browser.chrome || browser.opr || browser.safari ) {
        browser.webkit = true;
      }

      // IE11 has a new token so we will assign it msie to avoid breaking changes
      if ( browser.rv )
      {
        var ie = 'msie';

        matched.browser = ie;
        browser[ie] = true;
      }

      // Opera 15+ are identified as opr
      if ( browser.opr )
      {
        var opera = 'opera';

        matched.browser = opera;
        browser[opera] = true;
      }

      // Stock Android browsers are marked as Safari on Android.
      if ( browser.safari && browser.android )
      {
        var android = 'android';

        matched.browser = android;
        browser[android] = true;
      }

      // Assign the name and platform variable
      browser.name = matched.browser;
      browser.platform = matched.platform;


      return browser;
    }

})();

/**=========================================================
 * Module: SupportService.js
 * Checks for features supports on browser
 =========================================================*/
/*jshint -W069*/
(function() {
    'use strict';

    angular
        .module('naut')
        .service('support', service);
    
    service.$inject = ['$document', '$window'];
    function service($document, $window) {
      /*jshint validthis:true*/
      var support = this;
      var doc = $document[0];

      // Check for transition support
      // ----------------------------------- 
      support.transition = (function() {

        function transitionEnd() {
            var el = document.createElement('bootstrap');

            var transEndEventNames = {
              WebkitTransition : 'webkitTransitionEnd',
              MozTransition    : 'transitionend',
              OTransition      : 'oTransitionEnd otransitionend',
              transition       : 'transitionend'
            };

            for (var name in transEndEventNames) {
              if (el.style[name] !== undefined) {
                return { end: transEndEventNames[name] };
              }
            }
            return false;
          }

          return transitionEnd();
      })();

      // Check for animation support
      // ----------------------------------- 
      support.animation = (function() {

          var animationEnd = (function() {

              var element = doc.body || doc.documentElement,
                  animEndEventNames = {
                      WebkitAnimation: 'webkitAnimationEnd',
                      MozAnimation: 'animationend',
                      OAnimation: 'oAnimationEnd oanimationend',
                      animation: 'animationend'
                  }, name;

              for (name in animEndEventNames) {
                  if (element.style[name] !== undefined) return animEndEventNames[name];
              }
          }());

          return animationEnd && { end: animationEnd };
      })();

      // Check touch device
      // ----------------------------------- 
      support.touch                 = (
          ('ontouchstart' in window && navigator.userAgent.toLowerCase().match(/mobile|tablet/)) ||
          ($window.DocumentTouch && document instanceof $window.DocumentTouch)  ||
          ($window.navigator['msPointerEnabled'] && $window.navigator['msMaxTouchPoints'] > 0) || //IE 10
          ($window.navigator['pointerEnabled'] && $window.navigator['maxTouchPoints'] > 0) || //IE >=11
          false
      );

      return support;

    }
})();

/**=========================================================
 * Module: TouchDragService.js
 * Services to add touch drag to a dom element
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .service('touchDrag', touchDrag);

    touchDrag.$inject = ['$document', 'browser'];
    function touchDrag($document, browser) {
      /*jshint validthis:true*/
      var self = this;

      self.touchHandler = function (event) {
          var touch = event.changedTouches[0];

          var simulatedEvent = document.createEvent('MouseEvent');
              simulatedEvent.initMouseEvent({
              touchstart: 'mousedown',
              touchmove: 'mousemove',
              touchend: 'mouseup'
          }[event.type], true, true, window, 1,
              touch.screenX, touch.screenY,
              touch.clientX, touch.clientY, false,
              false, false, false, 0, null);

          touch.target.dispatchEvent(simulatedEvent);
          event.preventDefault();
      };

      self.addTo = function (element) {
        element = element || $document;
        if(browser.mobile) {
          element.addEventListener('touchstart', this.touchHandler, true);
          element.addEventListener('touchmove', this.touchHandler, true);
          element.addEventListener('touchend', this.touchHandler, true);
          element.addEventListener('touchcancel', this.touchHandler, true);
        }
      };
    }

})();

/**=========================================================
 * Module: CoreConfig
 =========================================================*/
(function () {
  'use strict';

  angular
    .module('naut')
    .config(commonConfig)
    .config(lazyLoadConfig);

  // Common object accessibility
  commonConfig.$inject = ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide'];
  function commonConfig($controllerProvider, $compileProvider, $filterProvider, $provide) {

    var app = angular.module('naut');
    app.controller = $controllerProvider.register;
    app.directive  = $compileProvider.directive;
    app.filter     = $filterProvider.register;
    app.factory    = $provide.factory;
    app.service    = $provide.service;
    app.constant   = $provide.constant;
    app.value      = $provide.value;

  }

  // Lazy load configuration
  lazyLoadConfig.$inject = ['$ocLazyLoadProvider', 'VENDOR_ASSETS'];
  function lazyLoadConfig($ocLazyLoadProvider, VENDOR_ASSETS) {

    $ocLazyLoadProvider.config({
      debug: false,
      events: true,
      modules: VENDOR_ASSETS.modules
    });

  }

})();



/**=========================================================
 * Module: ColorsConstant.js
 =========================================================*/

(function() {
  'use strict';

  // Same MQ as defined in the css
  angular
    .module('naut')
    .constant('MEDIA_QUERY', {
      'desktopLG': 1200,
      'desktop':   992,
      'tablet':    768,
      'mobile':    480
    });

})();

/**=========================================================
 * Module: CoreController.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .controller('CoreController', CoreController);

    /* @ngInject */
    function CoreController($rootScope) {
      // Get title for each page
      $rootScope.pageTitle = function() {
        return $rootScope.app.name + ' - ' + $rootScope.app.description;
      };

      // Cancel events from templates
      // ----------------------------------- 
      $rootScope.cancel = function($event){
        $event.preventDefault();
        $event.stopPropagation();
      };
    }
    CoreController.$inject = ['$rootScope'];

})();

/**=========================================================
 * Module: ApplicationRun.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .run(appRun);


    appRun.$inject = ['$rootScope', '$state', '$stateParams', '$localStorage', 'translator', 'settings', 'browser'];
    function appRun($rootScope, $state, $stateParams, $localStorage, translator, settings, browser) {

      // Set reference to access them from any scope
      $rootScope.$state = $state;
      $rootScope.$stateParams = $stateParams;
      $rootScope.$storage = $localStorage;

      translator.init();
      settings.init();
      
      // add a classname to target different platforms form css
      var root = document.querySelector('html');
      root.className += ' ' + browser.platform;

    }

})();


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
/**=========================================================
 * Module: climacon.js
 * Include any animated weather icon from Climacon
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('climacon', climacon);
    
    function climacon() {

      var SVG_PATH = 'vendor/animated-climacons/svgs/',
          SVG_EXT = '.svg';

      return {
        restrict: 'EA',
        link: link
      };
      
      function link(scope, element, attrs) {
        
        var color  = attrs.color  || '#000',
            name   = attrs.name   || 'sun',
            width  = attrs.width  || 20,
            height = attrs.height || 20;

        // Request the svg indicated
        $.get(SVG_PATH + name + SVG_EXT).then(svgLoaded, svgError);

        // if request success put it as online svg so we can style it
        function svgLoaded(xml) {
          var svg = angular.element(xml).find('svg');

          svg.css({
            'width':  width,
            'height': height
          });
          svg.find('.climacon_component-stroke').css('fill', color);

          element.append(svg);
        }
        // If fails write a message
        function svgError() {
          element.text('Error loading SVG: ' + name);
        }

      }
    }
})();

/**=========================================================
 * Module: LayerMorphDirective.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('btnLayerMorph', btnLayerMorph)
        .directive('layerMorphOverlay', layerMorphOverlay)
        .directive('layerMorphClose', layerMorphClose);
    /* @ngInject */
    function btnLayerMorph(LayerMorph) {

      return {
        restrict: 'A',
        link: function(scope, element, attrs) {
          var queryResult = document.querySelector(attrs.target);
          var target = angular.element(queryResult);
          
          if(!target.length) {
            console.log('LayerMorph: Wrong target ' + attrs.target);
            return;
          }

          element.on('click', function() {
            LayerMorph.open( element, target );
          });

        }
      };
    }
    btnLayerMorph.$inject = ['LayerMorph'];
    /* @ngInject */
    function layerMorphOverlay(LayerMorph, $document) {

      return {
        restrict: 'C',
        link: function(scope, element, attrs) {
          $document.ready(function(){
            LayerMorph.init();
          });
        }
      };
    }
    layerMorphOverlay.$inject = ['LayerMorph', '$document'];

    /* @ngInject */
    function layerMorphClose(LayerMorph) {

      return {
        restrict: 'A',
        link: function(scope, element, attrs) {
          element.on('click', function(){
            LayerMorph.close();
          });
        }
      };
    }
    layerMorphClose.$inject = ['LayerMorph'];

})();

/**=========================================================
 * Module: LayerMorphService.js
 =========================================================*/


(function() {
    'use strict';

    angular
        .module('naut')
        .service('LayerMorph', LayerMorph);
    /* @ngInject */
    function LayerMorph($window, support) {
        var $win  = angular.element($window),
            $body = angular.element( document.querySelector('body') ),
            self  = this;
        
        /* jshint -W004*/
        self.init         = init;
        self.ready        = ready;
        self.open         = open;
        self.close        = close;
        self.isActive     = isActive;
        self.isReady      = isReady;
        self.placeLayer   = placeLayer;
        self.attachResize = attachResize;

        ////////////////
        
        function init(){
          // find main elements
          self.container = angular.element( document.querySelector('.layer-morph-container') );
          self.inner     = angular.element( document.querySelector('.layer-morph-inner') );
          // attach event to re-position when screen resolution changes
          self.attachResize();
        }

        function ready(callback) {
          if ( support.transition.end ) {
            self.inner.on(support.transition.end, runEndTransition);
          }
          else {
            runEndTransition();
          }

          function runEndTransition() {
            // activate layers container
            self.container.addClass('active');
            // detach event
            self.inner.off(support.transition.end);
            // run user callback
            if(callback) callback();
          }

        }

        function open(element, target) {

          self.currentElement = element;
          // activate current element
          self.currentElement.addClass('active');

          if ( ! self.isActive() ) {
            // place layer coordinate
            self.placeLayer();

            self.ready(function(){
              // activate target
              target.addClass('active');
              self.isReady(true);
            });

            self.isActive(true);
          }
        }

        function close() {
          angular.element(document.querySelector('.layer-morph.active')).removeClass('active');
          self.container.removeClass('active');
          self.currentElement.removeClass('active');
          self.isReady(false);
          self.isActive(false);
        }

        function isActive(newState){
          // if no param return current state, else set new state
          $body[ (typeof newState === 'undefined') ? 'hasClass' :
            newState ? 'addClass' : 'removeClass']('layer-morph-active');
          $body[0].offsetWidth;
        }

        function isReady(newState){
          // if no param return current state, else set new state
          $body[ (typeof newState === 'undefined') ? 'hasClass' :
            newState ? 'addClass' : 'removeClass']('layer-morph-ready');
          $body[0].offsetWidth;
        }

        function placeLayer(){

          var element = self.currentElement;
          if(!element) return;
          
          // element offset
          var elOffset = offset(element[0]);
          // screen dimension
          var $winHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0),
              $winWidth  = Math.max(document.documentElement.clientWidth, window.innerWidth || 0),
              diameter   = (Math.sqrt( Math.pow($winHeight, 2) + Math.pow($winWidth, 2) ) * 2);

          self.inner
              .css('height', diameter+'px')
              .css('width', diameter+'px');

          elOffset.top = elOffset.top + ( outerHeight(element[0]) /2 ) - $window.pageYOffset;
          elOffset.left = elOffset.left + ( outerWidth(element[0]) /2 ) ;

          self.inner
              .css('top', (elOffset.top - self.inner[0].offsetHeight/2) +'px')
              .css('left', (elOffset.left - self.inner[0].offsetWidth/2) +'px');
        }

        function attachResize(){

          $win.on('resize', function(){
            self.placeLayer();
          });
        }
        // helpers
        function outerHeight(el) {
          var style = getComputedStyle(el);
          return el.offsetHeight + parseInt(style.paddingTop) + parseInt(style.paddingBottom);
        }
        function outerWidth(el) {
          var style = getComputedStyle(el);
          return el.offsetWidth + parseInt(style.paddingLeft) + parseInt(style.paddingRight);
        }
        function offset(el) {
          var rect = el.getBoundingClientRect();
          return {
            top: rect.top + document.body.scrollTop,
            left: rect.left + document.body.scrollLeft
          };
        }
    }
    LayerMorph.$inject = ['$window', 'support'];

})();
/**=========================================================
 * Module: HeaderNavController
 * Controls the header navigation
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .controller('HeaderNavController', HeaderNavController);
    /* @ngInject */    
    function HeaderNavController($rootScope) {
      var vm = this;
      vm.headerMenuCollapsed = true;

      vm.toggleHeaderMenu = function() {
        vm.headerMenuCollapsed = !vm.headerMenuCollapsed;
      };

      // Adjustment on route changes
      $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        vm.headerMenuCollapsed = true;
      });

    }
    HeaderNavController.$inject = ['$rootScope'];

})();

/**=========================================================
 * Module: SidebarDirective
 * Wraps the sidebar. Handles collapsed state and slide
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('uiSidebar', uiSidebar);

    uiSidebar.$inject = ['$rootScope', '$window', '$timeout', 'MEDIA_QUERY'];
    function uiSidebar ($rootScope, $window, $timeout, MEDIA_QUERY) {

      return {
        restrict : 'A',
        link : link
      };

      function link(scope, element) {
        element.find('a').on('click', function (event) {
          var ele = angular.element(this),
              par = ele.parent()[0];

          // remove active class (ul > li > a)
          var lis = ele.parent().parent().children();
          angular.forEach(lis, function(li){
            if(li !== par)
              angular.element(li).removeClass('active');
          });

          var next = ele.next();
          if ( next.length && next[0].tagName === 'UL' ) {
            ele.parent().toggleClass('active');
            event.preventDefault();
          }
        });

        // on mobiles, sidebar starts off-screen
        if ( onMobile() ) $timeout(function(){
          $rootScope.app.sidebar.isOffscreen = true;
        });
        // hide sidebar on route change
        $rootScope.$on('$stateChangeStart', function() {
            if ( onMobile() )
              $rootScope.app.sidebar.isOffscreen = true;
        });

        $window.addEventListener('resize', function(){
            $timeout(function(){
                $rootScope.app.sidebar.isOffscreen = onMobile();
            });
        });

        function onMobile() {
          return $window.innerWidth < MEDIA_QUERY.tablet;
        }

      }
    }
})();

/**=========================================================
 * Module: LoadingBarConfig.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .config(loadingBarConfig);
    
    /* @ngInject */
    function loadingBarConfig(cfpLoadingBarProvider) {
      cfpLoadingBarProvider.includeBar = true;
      cfpLoadingBarProvider.includeSpinner = false;
      cfpLoadingBarProvider.latencyThreshold = 500;
      cfpLoadingBarProvider.parentSelector = '.app-container > header';
    }
    loadingBarConfig.$inject = ['cfpLoadingBarProvider'];

})();


/**=========================================================
 * Module: LoadingbarRun
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .run(appRun);
    /* @ngInject */
    function appRun($rootScope, $timeout, cfpLoadingBar) {
      // Loading bar transition

      var latency;
      $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
          if( document.querySelector('.app-container > section') ) // check if bar container exists
            latency = $timeout(function() {
              cfpLoadingBar.start();
            }, 50); // sets a latency Threshold
      });
      $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
          event.targetScope.$watch('$viewContentLoaded', function () {
            $timeout.cancel(latency);
            cfpLoadingBar.complete();
          });
      });

    }
    appRun.$inject = ['$rootScope', '$timeout', 'cfpLoadingBar'];

})();

/**=========================================================
 * Module: RippleDirective.js
 * Adapted to support bootstrap components
 * Based on: https://github.com/nelsoncash/angular-ripple
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('ripple', ripple);
    /* @ngInject */
    function ripple($timeout) {

      return {
        restrict: 'A',
        link: link
      };

      function link (scope, element, attrs) {
        var x, y, size, offsets;

        element.append('<span class="ripple"></span>');

        element.on('click touchstart', function(e) {
          var eventType = e.type;
          
          var rippleContainer = this.querySelector('.ripple');
          var ripple = rippleContainer.querySelector('.angular-ripple');

          // Ripple
          if (ripple === null) {
            // Create ripple
            ripple = document.createElement('span');
            ripple.className = 'angular-ripple';

            // Prepend ripple to element
            rippleContainer.insertBefore(ripple, rippleContainer.firstChild);

            // Set ripple size
            if (!ripple.offsetHeight && !ripple.offsetWidth) {
              size = Math.max(rippleContainer.offsetWidth, rippleContainer.offsetHeight);
              ripple.style.width = size + 'px';
              ripple.style.height = size + 'px';
            }
          }

          // create jqlite reference
          var $ripple = angular.element(ripple);
          // Remove animation effect
          $ripple.removeClass('animate');

          // get click coordinates by event type
          if (eventType === 'click') {
            x = e.pageX;
            y = e.pageY;
          } else if(eventType === 'touchstart') {
            x = e.changedTouches[0].pageX;
            y = e.changedTouches[0].pageY;
          }

          // set new ripple position by click or touch position
          function getPos(el) {
            for (var lx=0, ly=0; el !== null; lx += el.offsetLeft, ly += el.offsetTop, el = el.offsetParent);
            return {left: lx, top: ly};
          }

          offsets = offset( $ripple.parent()[0] );

          ripple.style.left = (x - offsets.left - size / 2) + 'px';
          ripple.style.top = (y - offsets.top - size / 2) + 'px';

          // Add animation effect
          $ripple.addClass('animate');
          
          $timeout(function(){
            $ripple.removeClass('animate');
          }, 500);

        });
      }

      // helpers
      function offset(el) {
        var rect = el.getBoundingClientRect();
        return {
          top: rect.top + document.body.scrollTop,
          left: rect.left + document.body.scrollLeft
        };
      }      
    }
    ripple.$inject = ['$timeout'];

})();

/**=========================================================
 * Module: RoutesConfig.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .config(routesConfig);

    routesConfig.$inject = ['$locationProvider', '$stateProvider', '$urlRouterProvider', 'RouteProvider', 'OAuthProvider'];
    function routesConfig($locationProvider, $stateProvider, $urlRouterProvider, Route, OAuthProvider) {

      OAuthProvider.configure({
        // baseUrl: '${pems-url}/',
        baseUrl: 'http://localhost:8080/pems',
        clientId: 'restapp',
        clientSecret: 'secret',
        grantPath: '/oauth/token',
        revokePath: '/oauth/revoke'
      });

      // use the HTML5 History API
      $locationProvider.html5Mode(false);

      // Default route
      $urlRouterProvider.otherwise('/app/login');

      // Application Routes States
      $stateProvider
        .state('app', {
          url: '/app',
          abstract: true,
          templateUrl: Route.base('app.html'),
          resolve: {
            _assets: Route.require('icons', 'toaster', 'animate', 'swAlert', 'froala')
          }
        })
        .state('app.login', {
          url: '/login',
          templateUrl: Route.base('login.html'),
          resolve: {}
        })
        .state('app.troca', {
          url: '/troca',
          templateUrl: Route.base('troca-senha.html'),
          controller: 'trocaSenhaController',
          resolve: {}
        })
        .state('app.dashboard', {
          url: '/dashboard',
          templateUrl: Route.base('dashboard.html'),
          controller: 'dashboardController',
          resolve: {
            assets: Route.require('easypiechart', 'ui.select', 'slimscroll')
          }
        })
        .state('app.lista-itens', {
          url: '/lista-itens',
          templateUrl: Route.base('lista-itens.html'),
          controller: 'listaItensController',
          resolve: {
            assets: Route.require('slimscroll')
          }
        })
        .state('app.dash-item', {
          params: {
              'itemId': null,
              'nivel': null
            },
          url: '/item-dash',
          templateUrl: Route.base('item-dash.html'),
          resolve: {
            assets: Route.require('slimscroll', 'ngFileUpload')
          }
        })
    }
})();
/**=========================================================
 * Module: RouteProvider.js
 * Provides helper functions for routes definition
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .provider('Route', RouteProvider)
        ;
    
    RouteProvider.$inject = ['VENDOR_ASSETS'];
    function RouteProvider (VENDOR_ASSETS) {

      // Set here the base of the relative path
      // for all app views
      this.base = function (uri) {
        return 'app/views/' + uri;
      };

      // Generates a resolve object by passing script names
      // previously configured in constant.VENDOR_ASSETS
      this.require = function () {
        var _args = arguments;
        return ['$ocLazyLoad','$q', function ($ocLL, $q) {
            // Creates a promise chain for each argument
            var promise = $q.when(1); // empty promise
            for(var i=0, len=_args.length; i < len; i ++){
              promise = andThen(_args[i]);
            }
            return promise;

            // creates promise to chain dynamically
            function andThen(_arg) {
              // also support a function that returns a promise
              if(typeof _arg === 'function')
                  return promise.then(_arg);
              else
                  return promise.then(function() {
                    // if is a module, pass the name. If not, pass the array
                    var whatToLoad = getRequired(_arg);
                    // simple error check
                    if(!whatToLoad) return $.error('Route resolve: Bad resource name [' + _arg + ']');
                    // finally, return a promise
                    return $ocLL.load( whatToLoad );
                  });
            }
            // check and returns required data
            // analyze module items with the form [name: '', files: []]
            // and also simple array of script files (for non angular scripts)
            function getRequired(name) {
              if (VENDOR_ASSETS.modules)
                  for(var m in VENDOR_ASSETS.modules)
                      if(VENDOR_ASSETS.modules[m].name && VENDOR_ASSETS.modules[m].name === name)
                          return VENDOR_ASSETS.modules[m];
              return VENDOR_ASSETS.scripts && VENDOR_ASSETS.scripts[name];
            }

          }];
      }; // require

      // not necessary, only used in config block for routes
      this.$get = function(){};

    }

})();

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


/**=========================================================
 * Module: VendorAssetsConstant.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .constant('VENDOR_ASSETS', {
            // jQuery based and standalone scripts
            scripts: {
              'animate':            ['bower_components/animate.css/animate.min.css'],
              'icons':              ['bower_components/font-awesome/css/font-awesome.min.css',
                                     'bower_components/weather-icons/css/weather-icons.min.css',
                                     'bower_components/feather/webfont/feather-webfont/feather.css'],
              'slimscroll':         ['bower_components/slimScroll/jquery.slimscroll.min.js'],
              'jquery-ui':          ['bower_components/jquery-ui/jquery-ui.min.js',
                                     'bower_components/jqueryui-touch-punch/jquery.ui.touch-punch.min.js'],
              'swAlert':            ['bower_components/sweetalert/dist/sweetalert.css'],
              'froala':             ['bower_components/froala/css/froala_editor.min.css',
                                     'bower_components/froala/css/froala_editor.pkgd.min.css',
                                     'bower_components/froala/css/froala_style.min.css']
            },
            // Angular modules scripts (name is module name to be injected)
            modules: [
              
              {name: 'toaster',           files: ['bower_components/angularjs-toaster/toaster.js',
                                                  'bower_components/angularjs-toaster/toaster.css']},
              {name: 'easypiechart',      files:  ['bower_components/jquery.easy-pie-chart/dist/angular.easypiechart.min.js']},
              {name: 'xeditable',                 files: ['bower_components/angular-xeditable/dist/js/xeditable.js',
                                                          'bower_components/angular-xeditable/dist/css/xeditable.css']},
              {name: 'ui.select',                 files: ['bower_components/ui-select/dist/select.js',
                                                          'bower_components/ui-select/dist/select.css']},
              {name: 'ngFileUpload',      files: ['bower_components/ng-file-upload-bower-12.0.4/ng-file-upload.min.js']}
            ]

        });

})();


/**=========================================================
 * Module: SettingsController.js
 * Handles app setting
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .controller('SettingsController', SettingsController);
    /* @ngInject */
    function SettingsController(settings) {
      var vm = this;
      // Restore/Save layout settings
      settings.loadAndWatch();

      // Set scope for panel settings
      vm.themes = settings.availableThemes();
      vm.setTheme = settings.setTheme;

    }
    SettingsController.$inject = ['settings'];
})();

/**=========================================================
 * Module: SettingsService
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .service('settings', settings);
    /* @ngInject */
    function settings($rootScope, $localStorage, $translate) {
      /*jshint validthis:true*/
      var self = this;

      self.init = init;
      self.loadAndWatch = loadAndWatch;
      self.availableThemes = availableThemes;
      self.setTheme = setTheme;

      /////////////////

      self.themes = [
        {name: 'primary',   sidebar: 'bg-white',   sidebarHeader: 'bg-primary bg-light',   brand: 'bg-primary',   topbar:  'bg-primary'},
        {name: 'purple',    sidebar: 'bg-white',   sidebarHeader: 'bg-purple bg-light',    brand: 'bg-purple',    topbar:  'bg-purple'},
        {name: 'success',   sidebar: 'bg-white',   sidebarHeader: 'bg-success bg-light',   brand: 'bg-success',   topbar:  'bg-success'},
        {name: 'warning',   sidebar: 'bg-white',   sidebarHeader: 'bg-warning bg-light',   brand: 'bg-warning',   topbar:  'bg-warning'},
        {name: 'info',      sidebar: 'bg-white',   sidebarHeader: 'bg-info bg-light',      brand: 'bg-info',      topbar:  'bg-info'},
        {name: 'danger',    sidebar: 'bg-white',   sidebarHeader: 'bg-danger bg-light',    brand: 'bg-danger',    topbar:  'bg-danger'},
        {name: 'pink',      sidebar: 'bg-white',   sidebarHeader: 'bg-pink bg-light',      brand: 'bg-pink',      topbar:  'bg-pink'},
        {name: 'amber',     sidebar: 'bg-white',   sidebarHeader: 'bg-amber bg-light',     brand: 'bg-amber',     topbar:  'bg-amber'},
      ];

      function init() {
        // Global settings
        $rootScope.app = {
          name:          'PEMS',
          description:   'Planejamento Estratégico do Ministério da Saúde',
          departamento:  'Departamento de Monitoramento e Avaliação do SUS - DEMAS/SE/MS',
          year:          new Date().getFullYear(),
          // baseUrl:       '${pems-url}/ecar/api',
          // baseUrlTrocaSenha: '${pems-url}/troca-senha/ecar/api',
          baseUrl:       'http://localhost:8080/pems/ecar/api',
          baseUrlTrocaSenha: 'http://localhost:8080/pems/troca-senha/ecar/api',
          views: {
            animation: 'ng-fadeInLeft2'
          },
          layout: {
            isFixed: false,
            isBoxed: false,
            isDocked: false
          },
          sidebar: {
            isOffscreen: true,
            isMini: false,
            isRight: false
          },
          footer: {
            hidden: false
          },
          themeId: 0,
          // default theme
          theme: {
            name:          'primary',
            sidebar:       'bg-white',
            sidebarHeader: 'bg-primary bg-light',
            brand:         'bg-primary',
            topbar:        'bg-primary'
          }
        };      
      }

      function loadAndWatch() {
        // Load current settings from local storage
        if( angular.isDefined($localStorage.settings) )
          $rootScope.app = $localStorage.settings;
        else
          $localStorage.settings = $rootScope.app;

        $rootScope.$watch('app.layout', function () {
          $localStorage.settings = $rootScope.app;
        }, true);
      }

      function availableThemes() {
        return self.themes;
      }

      function setTheme(idx) {
        $rootScope.app.theme = this.themes[idx];
      }

    }
    settings.$inject = ['$rootScope', '$localStorage', '$translate'];

})();

/**=========================================================
 * Module: TranslateConfig.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .config(translateConfig);
    /* @ngInject */
    function translateConfig($translateProvider) {

      $translateProvider.useStaticFilesLoader({
        prefix: 'app/langs/',
        suffix: '.json'
      });
      $translateProvider.preferredLanguage('en');
      $translateProvider.useLocalStorage();
    }
    translateConfig.$inject = ['$translateProvider'];

})();

/**=========================================================
 * Module: TranslatorService
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .service('translator', translator);
    /* @ngInject */
    function translator($rootScope, $translate) {
      /*jshint validthis:true*/
      var self = this;

      self.init = init;
      self.set  = set;
      self.data = {
        // Handles language dropdown
        listIsOpen: false,
        // list of available languages
        available: {
          'en':    'English',
          'es':    'Español',
          'pt':    'Português',
          'zh-cn': '中国简体',
        },
        selected: 'English'
      };

      /////////////////////

      // display always the current ui language
      function init() {
        var proposedLanguage = $translate.proposedLanguage() || $translate.use();
        var preferredLanguage = $translate.preferredLanguage(); // we know we have set a preferred one in App.config
        self.data.selected = self.data.available[ (proposedLanguage || preferredLanguage) ];
        
        // Init internationalization service
        $rootScope.language = self.data;
        $rootScope.language.set = angular.bind(self,self.set);

        return self.data;
      }

      function set(localeId, ev) {
        // Set the new idiom
        $translate.use(localeId);
        // save a reference for the current language
        self.data.selected = self.data.available[localeId];
        // finally toggle dropdown
        self.data.listIsOpen = ! self.data.listIsOpen;
      }

    }
    translator.$inject = ['$rootScope', '$translate'];

})();

/**=========================================================
 * Module: ScrollableDirective.js
 * Make a content box scrollable
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('naut')
        .directive('scrollable', scrollable);
    /* @ngInject */
    function scrollable() {
      return {
        restrict: 'EA',
        link: function(scope, elem, attrs) {
          var defaultHeight = 285;

          attrs.height = attrs.height || defaultHeight;

          elem.slimScroll(attrs);

        }
      };
    }

})();
