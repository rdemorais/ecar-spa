(function() {
    'use strict';
    angular
    	.module('naut')
    	.directive('froalaNg', froala);

    	function froala() {
    		return {
	        restrict: 'A',
	        require: '?ngModel',
	        scope: {
	        	language: '@',
	        	toolbarButtons: '@'
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
						ngModel.$setViewValue(value);
					});
	      }
    	}
})();