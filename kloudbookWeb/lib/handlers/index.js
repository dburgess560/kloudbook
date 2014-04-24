/**
 * File index.js
 * Description: Render Kloudbook routes
 */

exports.index = function(req, res){
  res.render('index', {title:'Kloudbook | Welcome'});
};

exports.partials = function (req, res) {
  var name = req.params.name;
  res.render('partials/' + name);
};
