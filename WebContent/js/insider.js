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
					$location.path('/user').replace();
				}, 
			function(error){
					if(error.status == 401){
						$scope.message = "username doesn't exist or password incorrect";
					}
					
			});

	};
});


myApp.controller('user', function($scope,$resource, $location, $routeParams){
	var User;
	if($routeParams.user_id){
		User = $resource('/insider/request/user/:user_id', {user_id: $routeParams.user_id});
	} else {
		User = $resource('/insider/request/user/');
	}
	
	User.get(function(res){
		$scope.res= res;
			if(res.status == "success") {
				$scope.user = res.user.user;
				$scope.userDetails = res.user.userDetails;
				
				getRanks(res.user.user.user_id);
				if(res.user.userDetails.isMyAccount){
					$scope.showEdit = true;
					
				} else {
					$scope.showFollow = true;
					if(res.user.userDetails.isFollowing) {
						$scope.followingLabel = "Following";
						$('#follow').addClass("follow");
					} else {
						$scope.followingLabel = "Follow";
						$('#follow').addClass("unfollow");
					}
				}
			} else {
				$location.path('/').replace();
			}
			
	});	

	function getRanks(user_id){
		var Ranks = $resource('/insider/request/rank/:user_id', {user_id: user_id});
		$scope.ranksArr =  Ranks.query();
		//console.log($scope.ranksArr);
	}
	
	$scope.getReview = function(index, review_id){
		var str = "#review-"+review_id;
		var str2 = "review"+review_id;
		if ($scope.ranksArr[index].isOpen){
			$scope[str2] = false;
			$scope.ranksArr[index].isOpen = false;
		} else {
			if ($scope.ranksArr[index].hasReview) {
			} else {
				var Review = $resource('/insider/request/review/:review_id', {review_id: review_id});
				Review.get(function(res){
					$scope.ranksArr[index].reviewText = res.review_text;
					console.log($scope.ranksArr[index].reviewText);
					$scope.ranksArr[index].hasReview = true;			
				});
			}
			$scope[str2] = true;
			$scope.ranksArr[index].isOpen = true;
		}	
	};
	
	$scope.startFollowing = function(user_id){
		if(!$scope.res.user.userDetails.isFollowing){
			var Follow = $resource('/insider/request/user/follow/:user_id', {user_id: user_id});
			Follow.save(function(res){
				
			});
		}
	}
});