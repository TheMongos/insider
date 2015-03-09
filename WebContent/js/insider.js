var myApp = angular.module('insider', ['ngRoute', 'ngResource']);

myApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){

		$routeProvider
			.when('/item/:item_id', {
				templateUrl: 'partials/item.html',
				controller:'item'
			})
			.when('/user/:user_id', {
				templateUrl: 'partials/user.html',
				controller:'user'
			})
			.when('/user/', {
				templateUrl: 'partials/user.html',
				controller:'user'
			})
			.when('/login', {
				templateUrl: 'partials/login.html',
				controller:'login'
			})
			.when('/signup', {
				templateUrl: 'partials/signup.html',
				controller:'signup'
			})
			.when('/', {
				templateUrl: 'partials/user.html',
				controller:'user'
			});
	}]);

myApp.controller('login', function($scope,$resource, $location){
	$('#navigator').css("display","none");
	var Login = $resource('/insider/request/login');
	$scope.login = function (){
		$scope.message ="";
		var obj = {username: $scope.username, password: $scope.password};
		Login.save(obj, function(res){
					$('#navigator').css("display","block");
					$location.path('/user').replace();
				}, 
			function(error){
					console.log(error);
					if(error.status == 401){
						$scope.message = error.data.message;
					}
					
			});

	};
});

myApp.controller('signup', function($scope,$resource, $location){
	$('#navigator').css("display","none");
	var Login = $resource('/insider/request/signup');
	$scope.signup = function (){
		$scope.message ="";
		var obj = {username: $scope.username, password: $scope.password, firstName: $scope.firstName, lastName: $scope.lastName, email: $scope.email};
		Login.save(obj, function(res){
					$('#navigator').css("display","block");
					$location.path('/login').replace();
				}, 
			function(error){
					if(error.status == 409){
						$scope.message = error.data.message;
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
				$('#follow').addClass("notfollow");
			}
		}
		
	}, function (error){
		$location.path('/login').replace();
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
				if(res.status == "success"){
					$scope.followingLabel = "Following";
					$('#follow').removeClass("notfollow").addClass("follow");
					$scope.userDetails.userFollowersCount++;
				}
			}, function (error){
				$location.path('/login').replace();
			});
		}
	}
	
});

myApp.controller('item', function($scope,$resource, $location, $routeParams){
	var Item = $resource('/insider/request/item/:item_id', {item_id: $routeParams.item_id});
	Item.get(function(res){
		$scope.item = res;
		console.log(res);
		if (res.itemRanks.userRank) {
			$scope.userRank = JSON.parse(res.itemRanks.userRank);
			$scope.rank = $scope.userRank.rank;
		} else {
			$scope.rank = 0;
		}
	}, function (error){
		$location.path('/login').replace();
	});	
	
	$scope.showDescription = function() {
		if ($scope.description) {
			$scope.description = false;
		} else {
			$scope.description = true;
		}
	}
	
	$scope.addRank = function(){
		$scope.message = "";
		//TODO send rank to server 
	}
	
	$scope.addReview = function(){
		$scope.showReview =true;
		$scope.hideItem = true;
	}
	
	$scope.sendReview = function(){
		if($scope.rank){
			//TODO  send review to server
			console.log($scope.reviewText.length);
			$scope.showReview =false;
			$scope.hideItem = false;
		} else {
			$scope.message = "please rank the title";
		}
	}
});