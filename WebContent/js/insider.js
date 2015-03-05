var myApp = angular.module('insider', ['ngRoute', 'ngResource']);

myApp.config(['$routeProvider', function($routeProvider){
	//$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
		$routeProvider.
			when('/', {
				templateUrl: 'partials/login.html',
				controller:'login'
			});
	}]);
/*myApp.factory('Bug', function($resource){
    var resource = $resource('/insider/request/login',{},{
        post:{
            method:"POST",
            isArray:false,
            params:{username: $scope.username, password: $scope.password}
        },
    });
    return resource;
});*/
myApp.controller('login', function($scope,$resource){
	
	$scope.click = function (){
		//alert("hi");
		var obj = {username: $scope.username, password: $scope.password};
		//console.log($scope.username)
		var Contact = $resource('/insider/request/login', {callback:'JSON_CALLBACK'},{'bbb':{method:'POST'}});
		//params:{data:{username: $scope.username, password: $scope.password}}}
		//console.log(Contact);
		var response = Contact.bbb(obj ,function(res){
			console.log(res);
			});
		//console.log(response);
		//Bug.post();
		/*Bug.post({
			username: $scope.username,
			password: $scope.password
		});*/
	};
});