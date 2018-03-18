//const angular = require('angular');//引入angular
var simpleModule = angular.module('simpleModule',[]);//定义一个angular模块
simpleModule.controller("simpleController", function($scope){
  $scope.sumData = function(str1, str2){
    return str1 + str2;
  }
});
export default simpleModule;

// const $ = require("jquery");
// require ("bootstrap/dist/css/bootstrap.css");
// require ("bootstrap/dist/js/bootstrap.js");
