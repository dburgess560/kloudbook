/**
 * File map.js
 * Description: GET Kloudbook routes
 */

var routes = require('../handlers');
var api = require('../handlers/api');

module.exports = function(app) {
	app.get('/', routes.index);
	app.get('/partials/:name', routes.partials);
	app.get('*', routes.index);


	// API
	app.get('/api/user/:id', api.user);
	app.post('/api/user', api.addUser);
	app.put('/api/user/:id', api.editUser);
	app.delete('/api/user/:id', api.deleteUser);
};
