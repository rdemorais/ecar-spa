module.exports = function($, usehtml) {

  // distribution folder
  var dist = 'app/'; 
  var source = 'src/'; // for abs path construction

  var markupEngine = usehtml ? 'html' : 'jade';
  var markupExt = '.' + markupEngine;

  // main source folders
  var srcLESS = source + 'less/';
  var srcHTML = source + markupEngine + '/';
  var srcJS   = source + 'js/';


  // Shared config object
  var config = {
    // ---
    // Paths
    // ---

    dist:    dist,
    distCSS: dist + 'css',
    distJS:  dist + 'js',
    source:  source,
    srcLESS: srcLESS,
    srcHTML: srcHTML,
    srcJS:   srcJS,
    html: {
      index: [srcHTML + 'index' + markupExt],
      views: [srcHTML + '**/*' + markupExt, '!'+srcHTML + 'index' + markupExt],
      all: [srcHTML + '**/*' + markupExt]
    },
    less: {
      styles: [srcLESS + 'styles.less'],
      watch: [srcLESS + 'app/**/*.less']
    },
    bootstrap: [srcLESS + 'bootstrap/bootstrap.less'],
    js: [srcJS + 'app.module.js', srcJS + 'modules/**/*.js', srcJS + 'custom/**/*.js'],

    // ---
    // Plugins
    // --- 
    
    plato: {
      js: srcJS + '**/*.js'
    },
    report: './report/',
    tplcache: {
      file: 'templates.js',
      opts: {
        standalone: false,
        root: 'templates',
        module: 'naut'
      }
    },
    webserver: {
      webroot:          '.',
      host:             'localhost',
      port:             '5000',
      livereload:       true,
      directoryListing: false
    },
    prettify: {
      indent_char: ' ',
      indent_size: 3,
      unformatted: ['a', 'sub', 'sup', 'b', 'i', 'u']
    },
    usemin: {
      path: '.',
      css: [$.minifyCss({ processImport: false }), 'concat', $.rev()],
      // html: [$.minifyHtml({empty: true})],
      vendor: [$.uglify( {preserveComments:'some'} ).on('error', function(){}), $.rev()]
      //js: [$.ngAnnotate(), $.uglify( {preserveComments:'some'} ).on('error', function(){console.log("ERRORR")}), $.rev()]
    }
  };

  // scripts to check with jshint
  config.lintJs =  [].concat(config.js, config.distJS);

  return config;

};

