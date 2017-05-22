//import angular from 'angular';

import angular from 'angular';
import mocks from 'angular-mocks';
import simpleModule from './index.js';
describe('simpleModule', () => {
  describe('simpleController', () => {

    let $scope = {};
    beforeEach(angular.mock.module('simpleModule'));
    beforeEach(angular.mock.inject(function($rootScope, $controller){
      $scope = $rootScope.$new();
      $controller("simpleController", {$scope: $scope});
    }));
    it("sumData 测试", function(){
      console.log("test drive");
      expect($scope.sumData(2, 3)).toBe(5);
    });
  });
});
