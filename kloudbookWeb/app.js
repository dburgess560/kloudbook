/**
 * Module dependencies.
 */

var express = require('express'),
    http = require('http'),
    app = module.exports = express();

// Configuration
app.configure(function(){
  app.set('port', process.env.PORT || 3000);
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.set('view options', {
    layout: false
  });
  app.use(express.favicon(__dirname + '/public/imgs/favicon.png'));
  app.use(express.logger('dev'));
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(express.static(__dirname + '/public'));
  app.use(app.router);
});

// Development Only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

// Routes
require('./lib/routes/map')(app); 

// Start server
http.createServer(app).listen(app.get('port'), function() {
  console.log('Express server listening on port ' + app.get('port'));
});
