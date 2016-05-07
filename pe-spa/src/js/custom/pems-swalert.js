(function() {
	'use strict';
	angular
		.module('naut')
		.provider('SwAlert', SwAlertProvider);

		function SwAlertProvider() {
			var swal = window.swal;
			
			this.$get = function($rootScope) {
				var SwAlert = function() {
					this.error = function(title, text) {
						$rootScope.$evalAsync(function(){
							swal( title, text, 'error' );
						});
					}
				}
				return new SwAlert();
			}
			this.$get.$inject = ['$rootScope']
		}
})();