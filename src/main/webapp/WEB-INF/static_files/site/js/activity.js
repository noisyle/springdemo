var mainApp = angular.module("mainApp", ['ngRoute']);
mainApp.config(['$routeProvider', function($routeProvider) {
  $routeProvider
	.when('/', {templateUrl: 'main.html', controller: 'MainController'})
	.when('/list', {templateUrl: 'list.html', controller: 'ListController'})
	.when('/add', {templateUrl: 'add.html', controller: 'AddController'})
	.when('/act:id', {templateUrl: 'detail.html', controller: 'DetailController'})
	.otherwise({redirectTo: '/'});
}]);
mainApp.controller('MainController', function($scope, $http) {
  $http.get("list").success(function(r){
    $scope.activities = r.data;
  });
});
mainApp.controller('ListController', function($scope, $http, $location) {
  $http.get("list").success(function(r){
    $scope.activities = r.data;
  });
  $scope.del = function(id){
    $http.delete("activity", {params:{"id":id}}).success(function(r){
      if(r.status=='SUCCESS') {
        $http.get("list").success(function(r){
          $scope.activities = r.data;
        });
      } else alert(r.message);
    });
  }
});
mainApp.controller('AddController', function($scope, $http, $location) {
  $scope.save = function(){
    $http.post("activity", $scope.activity).success(function(r){
      if(r.status=='SUCCESS') $location.path('/list');
      else alert(r.message);
    });
  }
});
mainApp.controller('DetailController', function($scope, $http, $routeParams, $location) {
  $http.get("activity", {params:{"id":$routeParams.id}}).success(function(r){
    $scope.activity = r.data
  });
});
mainApp.controller('LoginController', function($scope, $http) {
  $scope.logined = false;
  $scope.username_invalid = false;
  $scope.password_invalid = false;
  $scope.login = function(){
    $scope.username_invalid = $scope.loginform.username.$invalid;
    $scope.password_invalid = $scope.loginform.password.$invalid;
    if(!$scope.username_invalid && !$scope.password_invalid){
      $scope.logined = true;
    }
  };
  $scope.logout = function(){
    $scope.username = '';
    $scope.password = '';
    $scope.logined = false;
  };
});

mainApp.config(function($httpProvider) {
  //让angular的post请求格式和jQuery一致
  $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
  $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  // Override $http service's default transformRequest
  $httpProvider.defaults.transformRequest = [function(data) {
    /**
     * The workhorse; converts an object to x-www-form-urlencoded serialization.
     * @param {Object} obj
     * @return {String}
     */
    var param = function(obj) {
      var query = '';
      var name, value, fullSubName, subName, subValue, innerObj, i;

      for (name in obj) {
        value = obj[name];

        if (value instanceof Array) {
          for (i = 0; i < value.length; ++i) {
            subValue = value[i];
            fullSubName = name + '[' + i + ']';
            innerObj = {};
            innerObj[fullSubName] = subValue;
            query += param(innerObj) + '&';
          }
        } else if (value instanceof Object) {
          for (subName in value) {
            subValue = value[subName];
            fullSubName = name + '[' + subName + ']';
            innerObj = {};
            innerObj[fullSubName] = subValue;
            query += param(innerObj) + '&';
          }
        } else if (value !== undefined && value !== null) {
          query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
      }
      return query.length ? query.substr(0, query.length - 1) : query;
    };

    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
  }];
});