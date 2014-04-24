/**
 * File controllers.js
 * Description: Kloudbook Controllers
 */

'use strict';

/*
** ToDo(Naj): Should check correct names for json user object for correct backend.            
*/

var kloudbookCtrl = angular.module('kloudbookCtrl', []);

//Home Controller.
kloudbookCtrl.controller('HomeCtrl', ['$scope', '$http', '$location', 'SignupService',
  function ($scope, $http, $location, SignupService) {
    $scope.submitted = false;
    $scope.agreement = "By joining Kloudbook you agree to Kloudbook's User Agreement, Privacy Policy, and Cookies Agreement"
  
    /*
    ** ToDo(Naj): Should grab the userId from the real user object from real db.
    */
    $scope.signupSubmit = function(user) {
      if ($scope.signup.$valid) {
        SignupService.setUser(user);
        $http.post('/api/user', JSON.stringify(SignupService.getUser(), null, 4)).
          success(function(data) { 
            alert('Added User : Need to connect to correct backend.');
            $location.path("/signup_two");
          });
      } else {
        $scope.signup.submitted = true;
      }
    };
  
    // ToDo(Naj): Should be in control of video play.
    $scope.videoPlay = function() {
    };
  }]);


//Signup Public Controller.
kloudbookCtrl.controller('PublicCtrl', ['$scope', '$http', '$location', 'SignupService',
  function ($scope, $http, $location, SignupService) {
    $scope.user = SignupService.getUser();
    $scope.profile_box = ["Lookup email addresses and cell phone numbers", "Stay in touch with colleagues and friends", 
                          "Organize contacts across various devices"];

    $scope.createProfileSubmit = function(pub) {
      SignupService.setUserPublic(pub);
      var user = SignupService.getUser();
      $http.put('/api/user/' + user.id, user).
        success(function(data) {
          alert('Edited Public : Needs to connect to correct backend.');
          $location.path("/signup_three");
        });
    };
  }]);

//Signup Private Controller.
kloudbookCtrl.controller('PrivateCtrl', ['$scope', '$http', '$location', 'SignupService',
  function ($scope, $http, $location, SignupService) {
    $scope.submitted = false;
    $scope.user = SignupService.getUser();
    $scope.phoneOptions =["Mobile", "Home", "Work", "Other"];
    $scope.addrOptions = ["Home", "Work", "Other"];
    $scope.countries = ["United States: +1"];
    //ToDo(): Add more country options in more sophisticated way, not for v.1.

    $scope.addContactsSubmit = function(priv) {
      if ($scope.signup.$valid) {
        SignupService.setUserPrivate(priv);
        var user = SignupService.getUser();
        $http.put('/api/user/' + user.id, user).
          success(function(data) {
            alert('Edited Private : Needs to connect to correct backend');
          });
      } else {
          $scope.signup.submitted = true;
      }
    };
  }]);
