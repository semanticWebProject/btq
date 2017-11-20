(function() {
  'use strict';

  angular
    .module('guan')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController(toastr, $http) {
    var vm = this;
    vm.firstLoad = 1;

    /* Local variables */
     var base_url = 'https://swt-btq.herokuapp.com/';
//    var base_url = 'http://134.155.210.159:8080/backend/';
    var keyQuestionCounter        = "questionCounter";
    var keyQuestionCounterCorrect = "questionCounterCorrect";
    var keyQuestionCounterWrong   = "questionCounterWrong";
    var keyHighScore              = "highScore";

    /* Model variables */
    vm.chosenCategoryID = '';
    vm.question = 'The question is being loaded. Please be patient!';
    vm.answers  = '';
    vm.correct  = '';
    vm.askQuestion = false;
    vm.selectCategory = true;
    vm.correctAnswer = false;
    vm.wrongAnswer = false;
    vm.score = 0;
    vm.newHighscore = false;

    /* Init Functions */
    getCategories(); //retrieves the categories from the server


    /* Model functions */
    //resets all model variables to get back to the home screen
    vm.reset = (function reset() {
      console.log('reset');
       vm.askQuestion = false;
       vm.selectCategory = true;
       vm.chosenCategory = 'None';
       vm.correctAnswer = false;
       vm.wrongAnswer = false;
       vm.selectedAnswer = 'None';
       vm.selectCategory = true;
       vm.firstLoad = 1;
       vm.question = 'The question is being loaded. Please be patient!';
       vm.answers  = '';
       vm.correct  = '';
     });

    // Called when category is chosen, loads first question
    vm.chooseCategory = (function cC(catID) {
      console.log('chosen category: ' + catID);
      for (var i = 0; i<vm.categories.length; i++) {
        if (vm.categories[i].id == catID) {
          console.log('category chosen: ' + vm.categories[i]);
          vm.chosenCategory = vm.categories[i];
        }
      }
      vm.selectCategory = false;
      vm.newHighscore = false;
      vm.score = 0;
      vm.loadQuestion();
    });

    // Load a question
    vm.loadQuestion = (function loadQuestion() {
      vm.askQuestion   = true;
      vm.correctAnswer = false;
      vm.wrongAnswer   = false;

      // Simple GET request example:
      $http({
        method: 'GET',
        url: base_url+'category/'+vm.chosenCategory.id+'/question'
      }).then(function successCallback(response) {

          // this callback will be called asynchronously
          // when the response is available
           console.log(response.data);
          vm.question = response.data['question'];
          vm.answers  = response.data['answers'];
          vm.correct  = response.data['correct'];
          vm.imageURL = response.data['image'];
          if (vm.imageURL == null) {
            console.log('no image'); vm.imageURL = false;
          }
          else {
            console.log('image is loaded: ');
            console.log(vm.imageURL);
          }

          if (vm.firstLoad == 1) {
            vm.firstLoad = 0;
            console.log('first load');
            vm.loadFields();
          }

        }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
          console.log(response);
        });

    });

    vm.loadFields = (function() {
      console.log('load fields after click');
      vm.imageURLField = 'loading ...';
      vm.questionField = vm.question;
      vm.imageURLField = vm.imageURL;
      vm.answersField  = vm.answers;
      if (vm.answers[0].text.includes('http')) {
        console.log('image answer')
        vm.imageAnswer = true;
        vm.textAnswer = false;
      }
      else {
      console.log('text answer')
        vm.textAnswer = true;
        vm.imageAnswer = false;
      }

    });

    // Called when an answer is selected
    vm.validate = (function validate(id) {
      vm.selectedAnswer = id;

      // As soon as answer is clicked, buttons are disabled and can't be clicked anymore
      jQuery(".answers button").attr("disabled","disabled");

      // Correct answer is given
      if (vm.correct == id) {
        // load question once question was answered correctly
        console.log(' ---- Loading new question already ---- ');
        vm.loadQuestion();

        vm.correctAnswer = true;
        vm.wrongAnswer = false;
        vm.askQuestion = true;
        jQuery('.answers button.option-'+id).addClass('btn-success');

        //Flashes score icon
        setTimeout(function() {
          jQuery('.scoreInformation').addClass('flash-icon');
        }, 800);
        setTimeout(function() {
          jQuery('.scoreInformation').removeClass('flash-icon');
        }, 3800);

        // Update correct question counter
        var questionCount = localStorage.getItem(keyQuestionCounterCorrect);
        questionCount++;
        localStorage.removeItem(keyQuestionCounterCorrect);
        localStorage.setItem(keyQuestionCounterCorrect, questionCount);

        // Update score
        vm.score++;
      }
      else {
        jQuery('.answers button.option-'+id).addClass('btn-danger');
        //Flashes the correct answer for 2s after 800ms
        setTimeout(function() {
          jQuery('.answers button.option-'+vm.correct).addClass('btn-success flash-button');
        }, 800);
        setTimeout(function() {
          jQuery('.answers button.option-'+vm.correct).removeClass('flash-button');
        }, 2000);

        vm.correctAnswer = false;
        vm.wrongAnswer = true;
        vm.askQuestion = true;

        // Update wrong question counter
        var questionCount = localStorage.getItem(keyQuestionCounterWrong);
        questionCount++;
        console.log("Wrong questions: " + questionCount);
        localStorage.removeItem(keyQuestionCounterWrong);
        localStorage.setItem(keyQuestionCounterWrong, questionCount);

        // Check for highscore & reset score
        if (vm.score > localStorage.getItem(keyHighScore)) {
        	vm.newHighscore = true;
        	vm.highScore = vm.score;
        	localStorage.removeItem(keyHighScore);
        	localStorage.setItem(keyHighScore, vm.score);
        }
      }

      // scroll down for validation
      $('html, body').animate({
         scrollTop: document.body.scrollHeight
      }, 'slow');

      //update question counter
      var questionCount = localStorage.getItem(keyQuestionCounter);
      questionCount++;
      console.log(questionCount);
      localStorage.removeItem(keyQuestionCounter);
      localStorage.setItem(keyQuestionCounter, questionCount);
    });


    /* Utility functions */
    // get the categories
    function getCategories() {
      $http({
        method: 'GET',
        url: base_url+'category'
      }).then(function successCallback(response) {
        vm.categories = response.data;
        console.log(response);
      });

    }


    // close menu on click
    $('.nav li').click(function() {
      //  alert('test');
        $('.nav li').removeClass('active');
        $(this).addClass('active');
        console.log('navbar should close')
        $('.navbar-collapse').removeClass('in');
    })

    // temporary local workaround for the categories
    /*
       vm.categories = [
      {
        'id': 0,
        'name': 'Actors & Movies',
        'thumbnail': 'http://www.enchantedmind.com/wp/wp-content/uploads/2013/01/Movies.jpg'
      },
      {
        'id': 1,
        'name': 'Geography',
        'thumbnail': 'http://allcomedyskits.com/wp-content/uploads/2016/10/Geography-1000x600.jpg'
      }
    ];
    */

  }
})();
