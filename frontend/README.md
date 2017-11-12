# How to initialize the project with gulp-angular 


> Yeoman generator for AngularJS + Gulp.
> Lets you quickly set up a project with:
> * your favorite technologies
> * web best pratices.
> * guidelines powered by Google.
> * Gulp provide fast workspace with quick feedback.

A tutorial is present in the [Yeoman codelab](http://yeoman.io/codelab/). :kissing_heart:

## Usage

### Install

##### Download and install `nodejs` and  `git`
[Nodejs](https://nodejs.org/en/)
[Git](https://git-scm.com/download/win)
(All tools must be added to the PATH Variable)
Check if nodejs and npm are installed: 
```
npm -v
node -v
```

##### Install required tools `yo`, `gulp`, `bower` and `cordova`:
```
npm install -g yo gulp bower cordova
```

##### Checkout the code from Github
```
git clone https://...
```

##### Install required dependencies (navigate to folder of the cloned repository):
```
npm install
bower install
```


### Run

##### Run `gulp serve`, to open the app in the browser using live reload:
```
gulp serve
```


##### Build the project with simply typing `gulp`
The output of gulp will be written in the "www" directory which will be used by cordova to build the app. It includes the minified HTML, CSS, Javascript and other necessary files.
```
gulp
```


##### Deploy the app with `cordova`
Follow [this guide](https://cordova.apache.org/docs/en/latest/guide/platforms/android/index.html) to install cordova and the necessary build tools for Android.


Add Android
```
cordova platform add android --save
```
Check if all requirements are met
```
cordova requirements android
```
Build the app
```
cordova build android --verbose
```
Deploy on the phone
```
cordova run android
```
