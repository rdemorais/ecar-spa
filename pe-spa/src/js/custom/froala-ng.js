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
					
	      }

	      link.$inject = ['$scope', '$element', '$attrs', 'ngModel'];
	      function link($scope, $element, $attrs, ngModel) {
					$element.froalaEditor({
	      		toolbarButtons: $scope.toolbarButtons,
	      		language: $scope.language,
	      		quickInsertButtons: ['table', 'ul', 'ol', 'hr'],
	      		heightMin: 350
	      	});

	      	$element.on('froalaEditor.contentChanged', function (e, editor) {
						var value = $element.froalaEditor('html.get', true);
						var cleredHtml = $element.froalaEditor('clean.html', value, ['font', 'fontFamily', 'font-family', 'style']);
						ngModel.$setViewValue(cleredHtml);
					});

	      	$timeout(function() {
	      		//TODO Inserir texto padrao: Sit, Pontos, Reco
	      		var cleredHtml = $element.froalaEditor('clean.html', ngModel.$viewValue, ['font', 'fontFamily', 'font-family']);
	      		$element.froalaEditor('html.set', ngModel.$viewValue);
	      	});
	      }
    	}
})();