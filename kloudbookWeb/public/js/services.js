/**
 * File services.js
 * Description: Kloudbook Services
 */

'use strict';

var kloudbookService = angular.module('kloudbookServices', []);

kloudbookService.factory('SignupService', [function() {
  var user = {
    pub : {},
    priv : {}
  };
   
  var setUser = function(u) {
    user = u;
    user.id = "444"; // Backend would supply the userID.
    console.log("New User " + JSON.stringify(user, null, 4));
  };

  var getUser = function() {
    return user;
  };

  var setUserPublic = function(pub) {
    user.pub = pub;
    console.log("Pub " + JSON.stringify(user, null, 4));
  };

  var getUserPublic = function(pub) {
    return user.pub;
  };

  var setUserPrivate = function(priv) {
    user.priv = priv;
    console.log("Priv " + JSON.stringify(user, null, 4));
  }

  var getUserPrivate = function(priv) {
    return user.priv;
  };

  var getUserId = function() {
    return user.id;
  };

  return {
    getUser: getUser,
    setUser: setUser,
    getUserId: getUserId,
    getUserPublic : getUserPublic,
    setUserPublic : setUserPublic,
    getUserPrivate : getUserPrivate,
    setUserPrivate : setUserPrivate
  };
}]);
