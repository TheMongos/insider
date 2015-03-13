var myApp = angular.module('insider', ['ngRoute', 'ngResource']);

myApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){

	$routeProvider
	.when('/top', {
		templateUrl: 'partials/top.html',
		controller: 'top'
	})
	.when('/search', {
		templateUrl: 'partials/search.html',
		controller: 'search'
	})
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
	.when('/following/:user_id', {
		templateUrl: 'partials/following.html',
		controller:'following'
	})
	.when('/followers/:user_id', {
		templateUrl: 'partials/followers.html',
		controller:'followers'
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
		$scope.messageShow = false;
		var obj = {username: $scope.username, password: $scope.password};
		Login.save(obj, function(res){
			$('#navigator').css("display","block");
			$location.path('/user').replace();
		}, 
		function(error){
			console.log(error);
			if(error.status == 401){
				$scope.message = error.data.message;
				$scope.messageShow = true;
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
				$('#follow').addClass("btn-success");
			} else {
				$scope.followingLabel = "Follow";
				$('#follow').addClass("notfollow");
				$('#follow').addClass("btn-primary");
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
		//var str = "#review-"+review_id;
		var str2 = "review"+review_id;
		var reviewBtn = "#reviewBtn"+review_id;
		$(".reviewBtn").css("background-color","white");
		if ($scope.ranksArr[index].isOpen){
			$scope[str2] = false;
			$scope.ranksArr[index].isOpen = false;
			$(reviewBtn).html("read review");
		} else {
			$(reviewBtn).html("close review");
			if ($scope.ranksArr[index].hasReview) {
			} else {
				var Review = $resource('/insider/request/review/:review_id', {review_id: review_id});
				Review.get(function(res){
					$scope.ranksArr[index].reviewText = res.review_text;
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
					$('#follow').removeClass("notfollow btn-primary").addClass("follow btn-success");
					$scope.userDetails.userFollowersCount++;
				}
			}, function (error){
				$location.path('/login').replace();
			});
		}
	}

	$scope.logout = function(){
		var Logout = $resource('/insider/request/logout');

		Logout.save(function(res){
			console.log(res)
			$location.path('/login').replace();
		}, function (error){
			$location.path('/login').replace();
		})
	}

});

myApp.controller('item', function($scope,$resource, $location, $routeParams){
	var Item = $resource('/insider/request/item/:item_id', {item_id: $routeParams.item_id});
	Item.get(function(res){
		$scope.item = res;
		console.log(res);
		//
		$scope.reviewButton = "write review";

		if (res.itemRanks.userRank) {
			$scope.userRank = JSON.parse(res.itemRanks.userRank);
			$scope.myRank = $scope.userRank.rank;
			if($scope.userRank.review_id){
				$scope.getReview($scope.userRank.review_id);
			}
		} else {
			$scope.myRank = 0;
		}

		$scope.getFollowingRanks();
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
		console.log($scope.myRank);
		if($scope.myRank != 0){
			var Rank = $resource('/insider/request/rank/:category_id/:item_id/:rank', {item_id: $scope.item.itemDetails.item_id, category_id: $scope.item.itemDetails.category_id , rank: $scope.myRank});
			Rank.save(function(res){
				console.log(res);
			},
			function (error){
				$location.path('/login').replace();
			});
		}
	}

	$scope.addReview = function(){
		if($scope.showReview){
			$scope.reviewButton = "write review";
			$scope.showReview =false;
			$scope.hideItem = false;
		} else {
			$scope.reviewButton = "close review";
			$scope.showReview =true;
			$scope.hideItem = true;
		}	
	}

	$scope.sendReview = function(){
		if($scope.myRank != 0){
			var obj = {item_id: $scope.item.itemDetails.item_id, category_id: $scope.item.itemDetails.category_id , rank: $scope.myRank, review_text: $scope.reviewText};
			var Rank = $resource('/insider/request/review');
			Rank.save(obj, function(res){
				$scope.getReview(res.review_id);
				$scope.userRank.review_id = res.review_id;
			},
			function (error){
				$location.path('/login').replace();
			});
			$scope.addReview();
		} else {
			$scope.message = "please rank the title";
		}
	}

	$scope.getFollowingRanks = function(){
		var Rank = $resource('/insider/request/rank/following/:item_id', {item_id : $scope.item.itemDetails.item_id});
		$scope.ranksArr = Rank.query(function(res){
			console.log(res);
		},
		function (error){
			$location.path('/login').replace();
		});
	}

	$scope.getReview = function(review_id){
		var Review = $resource('/insider/request/review/:review_id', {review_id: review_id});
		Review.get(function(res){
			console.log(res);
			$scope.reviewText = res.review_text;
		},
		function (error){
			$location.path('/login').replace();
		});
	}

	$scope.deleteReview = function() {
		var Delete = $resource('/insider/request/review/:review_id/:item_id', { review_id: $scope.userRank.review_id , item_id: $scope.item.itemDetails.item_id });

		Delete.remove(function(res){
			$scope.reviewText = "";
			$scope.userRank.review_id = "";
		},
		function (error){
			$location.path('/login').replace();
		});
	}
});

myApp.controller('top', function($scope,$resource, $location, $routeParams){
	$scope.categories = [
	                     {name:'Movies', id: 1},
	                     {name:'TV', id: 2}
	                     ];

	$scope.selectedCat = $scope.categories[0];
	$scope.showFollowing = true;


	$scope.changeCategory = function() {
		if ($scope.showFollowing) {
			$scope.getTopFollowing();
		} else {
			$scope.getTopAll();
		}
	}

	$scope.getTopAll = function() {
		$scope.showFollowing = false;

		//activation check 
		var All = $resource('/insider/request/best/:category_id', { category_id : $scope.selectedCat.id });
		$scope.itemsArr = All.query(function(res) {
			console.log(res);
		}, function(error) {
			$location.path('/login').replace();
		});	
	}

	$scope.getTopFollowing = function() {
		$scope.showFollowing = true;

		var Following = $resource('/insider/request/best/following/:category_id', { category_id : $scope.selectedCat.id });
		$scope.itemsArr = Following.query(function(res) {
			console.log(res);
		}, function(error) {
			$location.path('/login').replace();
		});	
	}

	//initial
	$scope.getTopFollowing();

});

myApp.controller('search', function($scope,$resource, $location, $routeParams){
	$scope.isMovie = true;

	$scope.changeSearch = function(clicked){
		var isUserTemp;
		if (clicked == "tv"){
			isUserTemp = $scope.isUser;
			$scope.isTv = true; $scope.isMovie = false; $scope.isUser = false;
		} else if (clicked == "movie"){
			isUserTemp = $scope.isUser;
			$scope.isMovie = true; $scope.isTv = false; $scope.isUser = false;
		} else if (clicked == "user"){
			$scope.isUser = true; $scope.isMovie = false; $scope.isTv = false;
			$scope.search();
		} 
		
		if(isUserTemp){
			$scope.search();
		}
	}

	$scope.search = function(){
		if($scope.searchText) {
			if ($scope.isUser) { 
				var SearchUser = $resource('/insider/request/search/user/:query', { query : $scope.searchText });

				$scope.userArr = SearchUser.query(function(res) {
					console.log(res);
					// do something
				}, function(error) {
					$location.path('/login').replace();
				});
			} else { 
				var SearchItem = $resource('/insider/request/search/item/:query', { query : $scope.searchText });

				$scope.itemArr = SearchItem.query(function(res) {
					console.log(res);
					// do something
				}, function(error) {
					$location.path('/login').replace();
				});
			}
		}
	}
});

myApp.controller('following', function($scope,$resource, $location, $routeParams){
	var Following = $resource('/insider/request/user/following/:user_id', {user_id: $routeParams.user_id});

	$scope.followArr = Following.query(function(res){
		console.log(res);
	},
	function (error){
		$location.path('/login').replace();
	});
});

myApp.controller('followers', function($scope,$resource, $location, $routeParams){
	var Followers = $resource('/insider/request/user/followers/:user_id', {user_id: $routeParams.user_id});

	$scope.followArr = Followers.query(function(res){
		console.log(res);
	},
	function (error){
		$location.path('/login').replace();
	});
});