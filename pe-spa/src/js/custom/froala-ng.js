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