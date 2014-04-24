'use strict';

/* Directives */

var kloudbookDirectives = angular.module('kloudbookDirectives', [])

kloudbookDirectives.directive('appVersion', ['version', function(version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
}]);

kloudbookDirectives.directive('AddOption');