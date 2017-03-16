const path = require('path');
const Vue = require("vue");

module.exports = {
  entry: './app/index.js',
  output: {
    filename: 'bundle.js',
     path: path.resolve(__dirname, 'dist')
  }
}
