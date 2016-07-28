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
                window.URL.revokeObjectURL(url);

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
                window.URL.revokeObjectURL(url);

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
            codExe: 1,
            codIett: -1,
            nivel: null
        };

        var listaStatusFiltrosGerada = false;
        var listaStFiltrosSel;

        $rootScope.$on('pems:limparFiltros', function() {
            filtros = {
                pns: false,
                meta: false,
                iniciativa: false,
                status: [],
                oes: [],
                etiquetas: [],
                secretarias: [],
                codExe: 1,
                codIett: -1,
                nivel: null
            };
        });

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