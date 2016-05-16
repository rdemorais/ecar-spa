(function() {
    'use strict';
    angular
        .module('naut')
        .service('pemsService', pemsService)
        .service('pemsFilterService', pemsFilterService)
        .service('pemsServiceError', pemsServiceError);

    pemsService.$inject = ['$http', '$q', '$rootScope']
    function pemsService($http, $q, $rootScope) {

        var fixedArrays = {
            oes: []
        };

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

    	this.loadStatus = function(callback) {
            $http({
                method: 'POST',
                url: $rootScope.app.baseUrl + '/status',
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

    	this.loadOEs = function(callback) {
            if(fixedArrays.oes.length == 0) {
                $http({
                    method: 'POST',
                    url: $rootScope.app.baseUrl + '/lista-oes',
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
            }else {
                callback(fixedArrays.oes);
            }
    	};
    }

    //pemsServiceError.$inject = ['SwAlert']
    function pemsServiceError() {
        this.showInvalidGrantMsg = function() {
            
        }
    }

    function pemsFilterService() {
        var filtros = {
            ppa: false,
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

        var listaStatusFiltrosGerada = false;
        var listaStFiltrosSel;

        this.getSecretarias = function() {
            return filtros.secretarias;
        };
        
        this.setSecretarias = function(secs) {
            filtros.secretarias = secs;
        };

        this.clear = function() {
            filtros = {
                ppa: false,
                meta: false,
                iniciativa: false,
                status: [],
                oes: [],
                etiquetas: []
            };
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
                    {clazz: 'img-status-verde', clazzSel: 'img-status-verde-sel', codCor: 1, sel: false},
                    {clazz: 'img-status-amarelo', clazzSel: 'img-status-amarelo-sel', codCor: 2, sel: false},
                    {clazz: 'img-status-vermelho', clazzSel: 'img-status-vermelho-sel', codCor: 3, sel: false},
                    {clazz: 'img-status-azul', clazzSel: 'img-status-azul-sel', codCor: 10, sel: false},
                    {clazz: 'img-status-cinza', clazzSel: 'img-status-cinza-sel', codCor: 11, sel: false},
                    {clazz: 'img-status-branco', clazzSel: 'img-status-branco-sel', codCor: -1, sel: false}
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