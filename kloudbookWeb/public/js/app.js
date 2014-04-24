/**
 * File app.js
 * Description: Kloudbook Application: Declares app level module.
 */

'use strict';

var kloudbook = angular.module('kloudbook', ['ngRoute', 'kloudbookCtrl', 'kloudbookServices']);

// ToDo(Naj): Make signup urls inaccessible.
kloudbook.config(['$routeProvider', '$locationProvider',
  function($routeProvider, $locationProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/home',
        controller: 'HomeCtrl'
      }).
      when('/signup_two', {
        templateUrl: 'partials/supg2',
        controller: 'PublicCtrl'
      }).
      when('/signup_three', {
        templateUrl: 'partials/supg3',
        controller: 'PrivateCtrl'
      }).
      otherwise({
        redirectTo: '/'
      });
    $locationProvider.html5Mode(true);
  }]);
