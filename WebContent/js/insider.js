var myApp = angular.module('insider', ['ngRoute', 'ngResource']);

myApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){

		$routeProvider
			.when('/user/:user_id', {
				templateUrl: 'partials/user.html',
				controller:'user'
			})
			.when('/user/', {
				templateUrl: 'partials/user.html',
				controller:'user'
			})
			.when('/', {
				templateUrl: 'partials/login.html',
				controller:'login'
			});
	}]);

myApp.controller('login', function($scope,$resource, $location){
	var Login = $resource('/insider/request/login');
	$scope.click = function (){
		$scope.message ="";
		var obj = {username: $scope.username, password: $scope.password};
		Login.save(obj, function(res){
			//console.log(res);
			if(res.status == "success")
				$location.path('/user').replace();
			else
				$scope.message = res.message;
			});

	};
});

myApp.controller('user', function($scope,$resource, $location){
	console.log("in user controller");
	var User = $resource('/insider/request/user/:user_id');
	User.get(function(res){
		$scope.res = res;
	});
	
	/*$scope.click = function (){
		$scope.message ="";
		var obj = {username: $scope.username, password: $scope.password};
		Login.save(obj, function(res){
			//console.log(res);
			if(res.status == "success")
				$location.path('/user/' +  res.id).replace();
			else
				$scope.message = res.message;
			});

	};*/
});