const jshint = require('gulp-jshint');
const gulp   = require('gulp');

gulp.task('lint', function() {
  return gulp.src('app/*.ts')
    .pipe(jshint())
    .pipe(jshint.reporter('default', { verbose: true }));
});

gulp.task('default',["lint"],function(){
  gulp.src('js/angular.js')
      .pipe(gulp.dest('js/dist'));

    console.log('hello world');
});
