(function() {
    'use strict';
    angular
        .module('naut')
        .service('pemsService', pemsService)
        .service('pemsFilterService', pemsFilterService);

    pemsService.$inject = ['$http']
    function pemsService($http, pemsFilterService) {

        var fixedArrays = {
            oes: []
        };

        this.loadEtiquetas = function(callback) {
            var etiquetas = [
                {nome: 'Saúde mais perto de você', id: 10},
                {nome: 'Rede Cegonha', id: 2},
                {nome: 'SOS Emergências', id: 4},
                {nome: 'SOS', id: 5},
                {nome: 'UPA', id: 6}
            ];

            callback(etiquetas);
        };

    	this.loadItem = function(itemId, callback) {
    		var item = {
            oe: 'OE 2',
            oeShortName: 'oe2',
            responsavel: 'Rafael de Morais',
            orgaoResp: 'SE',
            situacao: 'Em andamento',
            nomeCor: 'vermelho',
            tipo: 'Meta',
            sigla: '01',
            descricao: 'Ampliar o número das equipes de saúde de referência no atendimento a adolescentes em conflito com a lei, passando de 65 para 110 equipes implantadas. (04G6).',
            parecer: '<strong>Tabacaria</strong> <p>Não sou nada. Nunca serei nada. Não posso querer ser nada. À parte isso, tenho em mim todos os sonhos do mundo. Janelas do meu quarto, Do meu quarto de um dos milhões do mundo. que ninguém sabe quem é ( E se soubessem quem é, o que saberiam?), Dais para o mistério de uma rua cruzada constantemente por gente, Para uma rua inacessível a todos os pensamentos, Real, impossivelmente real, certa, desconhecidamente certa, Com o mistério das coisas por baixo das pedras e dos seres, Com a morte a por umidade nas paredes e cabelos brancos nos homens, Com o Destino a conduzir a carroça de tudo pela estrada de nada. Estou hoje vencido, como se soubesse a verdade. Estou hoje lúcido, como se estivesse para morrer, E não tivesse mais irmandade com as coisas Senão uma despedida, tornando-se esta casa e este lado da rua A fileira de carruagens de um comboio, e uma partida apitada De dentro da minha cabeça, E uma sacudidela dos meus nervos e um ranger de ossos na ida. Estou hoje perplexo, como quem pensou e achou e esqueceu. Estou hoje dividido entre a lealdade que devo À Tabacaria do outro lado da rua, como coisa real por fora, E à sensação de que tudo é sonho, como coisa real por dentro. Falhei em tudo. Como não fiz propósito nenhum, talvez tudo fosse nada. A aprendizagem que me deram, Desci dela pela janela das traseiras da casa.</p>  <p><strong>Poema em linha reta</strong> <p>Nunca conheci quem tivesse levado porrada. Todos os meus conhecidos têm sido campeões em tudo. E eu, tantas vezes reles, tantas vezes porco, tantas vezes vil, Eu tantas vezes irrespondivelmente parasita, Indesculpavelmente sujo. Eu, que tantas vezes não tenho tido paciência para tomar banho, Eu, que tantas vezes tenho sido ridículo, absurdo, Que tenho enrolado os pés publicamente nos tapetes das etiquetas, Que tenho sido grotesco, mesquinho, submisso e arrogante, Que tenho sofrido enxovalhos e calado, Que quando não tenho calado, tenho sido mais ridículo ainda; Eu, que tenho sido cômico às criadas de hotel, Eu, que tenho sentido o piscar de olhos dos moços de fretes, Eu, que tenho feito vergonhas financeiras, pedido emprestado [sem pagar, Eu, que, quando a hora do soco surgiu, me tenho agachado Para fora da possibilidade do soco; Eu, que tenho sofrido a angústia das pequenas coisas ridículas, Eu verifico que não tenho par nisto tudo neste mundo. Toda a gente que eu conheço e que fala comigo Nunca teve um ato ridículo, nunca sofreu enxovalho, Nunca foi senão príncipe — todos eles príncipes — na vida… Quem me dera ouvir de alguém a voz humana Que confessasse não um pecado, mas uma infâmia; Que contasse, não uma violência, mas uma cobardia! Não, são todos o Ideal, se os oiço e me falam. Quem há neste largo mundo que me confesse que uma vez foi vil? Ó príncipes, meus irmãos, Arre, estou farto de semideuses! Onde é que há gente no mundo? Então sou só eu que é vil e errôneo nesta terra? Poderão as mulheres não os terem amado, Podem ter sido traídos — mas ridículos nunca! E eu, que tenho sido ridículo sem ter sido traído, Como posso eu falar com os meus superiores sem titubear? Eu, que venho sido vil, literalmente vil, Vil no sentido mesquinho e infame da vileza.</p></p></br>',
    		indicadores: [
    			{
    				id: 1, 
    				nome: 'Ampliar o número de equipes da Estratégia Saúde da Família para 46 mil',
    				ano: 2016,
    				linhaBase: 39308,
    				meta: 43465,
    				metaQuadrienal: 46465,
    				meses: [
    					{nome: 'Jan', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Fev', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Mar', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Abr', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Mai', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Jun', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Jul', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Ago', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Set', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Out', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Nov', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654},
    					{nome: 'Dez', realizado: 346, aRealizar: 4157, resultadoAnual: 0.083, aRealizar19: 7157, resultadoQuadrienal: 0.048, qtdAcumulada: 39654}
    				]
    			}
    		],
            anexos: [
                {nome: 'Lista de itens para apresentação', link: 'http://www.googgle.com'},
                {nome: 'Tabela de upas', link: 'http://www.googgle.com'},
                {nome: 'Nomes dos participantes', link: 'http://www.googgle.com'},
                {nome: 'Apresentação', link: 'http://www.googgle.com'}
            ]
        }

        callback(item);
    	}

    	this.loadAtividades = function() {

    	};
    	this.loadProdutos = function(callback) {
    		var listaProdutos = {
    			total: 6,
    			lista: [
    				{oe: 'OE 1', oeShortName: 'oe1', desc: 'Texto do produto', nivel: 'produto', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'verde', ciclo: '03/2016', id: 345},
    				{oe: 'OE 2', oeShortName: 'oe2', desc: 'Texto do produto', nivel: 'produto', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'amarelo', ciclo: '03/2016', id: 33},
    				{oe: 'OE 3', oeShortName: 'oe3', desc: 'Texto do produto', nivel: 'produto', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'cinza', ciclo: '03/2016', id: 12},
    				{oe: 'OE 4', oeShortName: 'oe4', desc: 'Texto do produto', nivel: 'produto', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'vermelho', ciclo: '03/2016', id: 45},
    				{oe: 'OE 5', oeShortName: 'oe5', desc: 'Texto do produto', nivel: 'produto', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'azul', ciclo: '03/2016', id: 78},
    				{oe: 'OE 6', oeShortName: 'oe6', desc: 'Texto do produto', nivel: 'produto', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'branco', ciclo: '03/2016', id: 32}

    			]
    		};

    		callback(listaProdutos);
    	};

    	this.loadListaItens = function(callback) {
            //pemsFilterService.getFiltros());
    		var listaItens = {
    			total: 6,
    			lista: [
    				{oe: 'OE 1', oeShortName: 'oe1', situacao: 'Em andamento', desc: 'Alcançar 20.335 médicos brasileiros e estrangeiros atuando em regiões prioritárias para o SUS, por meio do Programa Mais Médicos e do Programa de Valorização dos Profissionais da Atenção Básica - PROVAB. (03Z5)', nivel: 'meta', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'verde', ciclo: '03/2016', id: 345},
    				{oe: 'OE 2', oeShortName: 'oe2', situacao: 'Não iniciado',desc: 'Fortalecimento do Centro Colaborador para a Qualidade do Cuidado e para a Segurança do Paciente - Proqualis, de forma a contribuir para a melhoria continuada da qualidade dos cuidados de saúde no Brasil e outros países de língua portuguesa, ampliando de 1.200 para 2.400 as publicações e o conteúdo técnico científico disponibilizado no Portal Proqualis. (06B0)', nivel: 'iniciativa', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'amarelo', ciclo: '03/2016', id: 33},
    				{oe: 'OE 3', oeShortName: 'oe3', situacao: 'Atrasado',desc: 'Implantação do Cadastro Nacional de Especialistas, com prazo de implantação até 2016. (05NE)', nivel: 'iniciativa', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'cinza', ciclo: '03/2016', id: 12},
    				{oe: 'OE 4', oeShortName: 'oe4', situacao: 'Em andamento',desc: 'Ampliar o número de equipes da Estratégia Saúde da Família para 46 mil. (0265)', nivel: 'meta', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'vermelho', ciclo: '03/2016', id: 45},
    				{oe: 'OE 5', oeShortName: 'oe5', situacao: 'Alcançado',desc: 'Ampliação do número de equipes de atenção básica prisional - EABP. (02PV)', nivel: 'iniciativa', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'azul', ciclo: '03/2016', id: 78},
    				{oe: 'OE 6', oeShortName: 'oe6', situacao: 'Em andamento',desc: 'Implantar 684 novas equipes de atenção domiciliar, sendo 432 novas Equipes Multiprofissionais de Atenção Domiciliar (EMAD) e 252 novas Equipes Multiprofissionais de Apoio (EMAP). (026O)', nivel: 'meta', responsavel: 'Rafael de Morais', orgaoResp: 'SE', nomeCor: 'branco', ciclo: '03/2016', id: 32}

    			]
    		};

    		callback(listaItens);
    	};

    	this.loadStatus = function(callback) {
    		var statusBar = {
    			iniciativa: {
    				totalInicitiva: 200,
    				status: [
    					{desc: 'Satisfatório', nomeCor: 'Verde', count: 15},
	    				{desc: 'Alerta', nomeCor: 'Amarelo', count: 23},
	    				{desc: 'Crítico', nomeCor: 'Vermelho', count: 43},
	    				{desc: 'Alcançado', nomeCor: 'Azul', count: 30},
	    				{desc: 'Cancelado', nomeCor: 'Cinza', count: 66},
	    				{desc: 'Não Monitorado', nomeCor: 'Branco', count: 23}
    				]
    			},
    			meta: {
    				totalMeta: 239,
    				status: [
    					{desc: 'Satisfatório', nomeCor: 'Verde', count: 25},
	    				{desc: 'Alerta', nomeCor: 'Amarelo', count: 56},
	    				{desc: 'Crítico', nomeCor: 'Vermelho', count: 90},
	    				{desc: 'Alcançado', nomeCor: 'Azul', count: 22},
	    				{desc: 'Cancelado', nomeCor: 'Cinza', count: 43},
	    				{desc: 'Não Monitorado', nomeCor: 'Branco', count: 3}
    				]
    			},
    			produto: {
    				totalProduto: 319,
    				status: [
    					{desc: 'Satisfatório', nomeCor: 'Verde', count: 67},
	    				{desc: 'Alerta', nomeCor: 'Amarelo', count: 32},
	    				{desc: 'Crítico', nomeCor: 'Vermelho', count: 77},
	    				{desc: 'Alcançado', nomeCor: 'Azul', count: 11},
	    				{desc: 'Cancelado', nomeCor: 'Cinza', count: 43},
	    				{desc: 'Não Monitorado', nomeCor: 'Branco', count: 89}
    				]
    			},
    			atividade: {
    				totalAtividade: 268,
    				status: [
    					{desc: 'Satisfatório', nomeCor: 'Verde', count: 99},
	    				{desc: 'Alerta', nomeCor: 'Amarelo', count: 12},
	    				{desc: 'Crítico', nomeCor: 'Vermelho', count: 56},
	    				{desc: 'Alcançado', nomeCor: 'Azul', count: 22},
	    				{desc: 'Cancelado', nomeCor: 'Cinza', count: 67},
	    				{desc: 'Não Monitorado', nomeCor: 'Branco', count: 12}
    				]
    			}
    		};

    		callback(statusBar);
    	};

    	this.loadOEs = function(callback) {
    		var oes = [
    			{id: 1, sigla: 1, desc: 'OE 1', sel: false},
				{id: 2, sigla: 2, desc: 'OE 2', sel: false},
				{id: 3, sigla: 3, desc: 'OE 3', sel: false},
				{id: 4, sigla: 4, desc: 'OE 4', sel: false},
				{id: 5, sigla: 5, desc: 'OE 5', sel: false},
				{id: 6, sigla: 6, desc: 'OE 6', sel: false},
				{id: 7, sigla: 7, desc: 'OE 7', sel: false},
				{id: 8, sigla: 8, desc: 'OE 8', sel: false},
				{id: 9, sigla: 9, desc: 'OE 9', sel: false},
				{id: 10, sigla: 10, desc: 'OE 10', sel: false},
				{id: 11, sigla: 11, desc: 'OE 11', sel: false},
				{id: 12, sigla: 12, desc: 'OE 12', sel: false},
				{id: 13, sigla: 13, desc: 'OE 13', sel: false},
				{id: 14, sigla: 14, desc: 'OE 14', sel: false},
				{id: 15, sigla: 15, desc: 'OE 15', sel: false},
				{id: 16, sigla: 16, desc: 'OE 16', sel: false},
				{id: 17, sigla: 17, desc: 'OE 17', sel: false},
				{id: 18, sigla: 18, desc: 'OE 18', sel: false},
				{id: 19, sigla: 19, desc: 'OE 19', sel: false},
				{id: 20, sigla: 20, desc: 'OE 20', sel: false},
				{id: 21, sigla: 21, desc: 'OE 21', sel: false},
				{id: 22, sigla: 22, desc: 'OE 22', sel: false},
				{id: 23, sigla: 23, desc: 'OE 23', sel: false},
				{id: 24, sigla: 24, desc: 'OE 24', sel: false}
    		];

            $http({
                method: 'POST',
                url: 'http://localhost:8080/pe-server/ecar/api/lista-oes',
                headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
            }).then(function successCallBack(response) {
                var ecarResponse = response.data;
                if(ecarResponse.status == 'success') {
                    var _oes = ecarResponse.obj;
                    callback(_oes);
                }else {
                    //tratar erro
                }
            }, function errorCallBack(response) {
                console.log(response);
            });
            
            //callback(oes);
    	};
    }

    function pemsFilterService(pemsService) {
        var filtros = {
            ppa: false,
            meta: false,
            iniciativa: false,
            status: [],
            oes: [],
            etiquetas: [{nome: 'Saúde mais perto de você', id: 10}]
        };

        this.listaOEs = function(callback) {
            pemsService.loadOEs(function(oes) {

                for (var i = 0; i < oes.length; i++) {
                    oes[i].sel = false;
                    oes[i].ord = i+1;
                };

                filtros.oes = oes;

                callback(filtros.oes);
            });
        };

        this.clear = function() {
            filtros = {
                ppa: false,
                meta: false,
                iniciativa: false,
                status: [],
                oes: [],
                etiquetas: [{nome: 'Saúde mais perto de você', id: 10}]
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

        this.getFiltros = function() {
            return filtros;
        };
    }
})();