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
                                                          'bower_components/ui-select/dist/select.css']}
            ]

        });

})();

