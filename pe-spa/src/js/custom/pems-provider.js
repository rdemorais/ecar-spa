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