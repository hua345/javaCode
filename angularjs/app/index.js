const angular = require('angular');//引入angular
const ngModule = angular.module('app',[]);//定义一个angular模块
const _ = require('lodash');
const $ = require("jquery");
require ("bootstrap/dist/css/bootstrap.css");
require ("bootstrap/dist/js/bootstrap.js");
function component () {
  var element = document.createElement('div');

  /* lodash is required for the next line to work */
  element.innerHTML = _.join(['Hello','webpack'], ' ');

  return element;
}

document.body.appendChild(component());
