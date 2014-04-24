/**
 * File api.js
 * Description: Serve JSON to our AngularJS client
 */

// For a real app, you'd make database requests here.

// For this example, "data" acts like an in-memory "database"
var data = {
  "users": [
    {
      "id": "123",
      "first'": "James",
      "last": "Brown",
      "email": "nonya@hotme.com"
    },
    {
      "id": "113",
      "first": "Elvis",
      "last": "Presley",
      "email": "nonya2@hotme.com"
    },
    {
      "id": "213",
      "first": "Michael",
      "last": "Jackson",
      "email": "nonya3@hotme.com"
    }
  ]
};

// GET

exports.users = function (req, res) {
  var users = [];
  data.users.forEach(function (user, i) {
    users.push({
      id: i,
      first: user.first,
      lname: user.last,
      email: user.email
    });
  });
  res.json({
    users: users
  });
};


exports.user = function (req, res) {
  var id = req.params.id;
  for (var i = 0; i < data.users.length; i++) {
    if (data.users[i].id == id) {
      res.json({
        user: data.users[id]
      });
    } else {
      res.json(false);
    }
  }
};

// POST

exports.addUser = function (req, res) {
  data.users.push(req.body);
  res.json(req.body);
};

// PUT

exports.editUser = function (req, res) {
  var id = req.params.id;
  for (var i = 0; i < data.users.length; i++) {
    if (data.users[i].id == id) {
      data.users[i] = req.body;
      res.json(true);
    } else {
      res.json(false);
    }
  }
};

// DELETE

exports.deleteUser = function (req, res) {
  var id = req.params.id;
  for (var i = 0; i < data.users.length; i++) {
    if (data.users[i].id == id) {
      data.users.splice(i, 1);
      res.json(true);
    } else {
      res.json(false);
    }
  }
};
