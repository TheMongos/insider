var myApp = angular.module('insider', ['ngRoute', 'ngResource']);

myApp.config(['$routeProvider', function($routeProvider){

		$routeProvider.
			when('/', {
				templateUrl: 'partials/login.html',
				controller:'login'
			});
	}]);

myApp.controller('login', function($scope,$resource){
	
	$scope.click = function (){
		var obj = {username: $scope.username, password: $scope.password};
		var Contact = $resource('/insider/request/login', {callback:'JSON_CALLBACK'},{'bbb':{method:'POST'}});
		var response = Contact.bbb(obj ,function(res){
			console.log(res);
			});

	};
});