(function() {
  'use strict';

  angular
    .module('guan')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController(toastr, $http) {
    var vm = this;

    vm.chosenCategoryID = '';
    vm.showToastr = showToastr;

    function showToastr() {
      toastr.info('Fork <a href="https://github.com/Swiip/generator-gulp-angular" target="_blank"><b>generator-gulp-angular</b></a>');
      vm.classAnimation = '';
    }


    var keyQuestionCounter = "questionCounter";
    var keyQuestionCounterCorrect = "questionCounterCorrect";
    var keyQuestionCounterWrong   = "questionCounterWrong";
    var keyHighScore = "highScore";

    vm.question = '';
    vm.answers  = '';
    vm.correct  = '';
    vm.askQuestion = false;
    vm.selectCategory = true;
    vm.correctAnswer = false;
    vm.wrongAnswer = false;
    vm.score = 0;
    vm.newHighscore = false;

    // Called when category is chosen, loads first question
    vm.chooseCategory = (function cC(catID) {
      console.log('chosen category: ' + catID);
      vm.chosenCategoryID = catID;
      vm.selectCategory = false;
      vm.newHighscore = false;
      vm.score = 0;
      vm.loadQuestion();
    });

    // Load a question
    vm.loadQuestion = (function loadQuestion() {
      /* uncomment to reset statistics
      localStorage.removeItem(keyQuestionCounterCorrect);
      localStorage.removeItem(keyQuestionCounter);
      localStorage.removeItem(keyQuestionCounterWrong);
      localStorage.removeItem(keyHighScore);
      */
      console.log('load new question');
      vm.askQuestion   = true;
      vm.correctAnswer = false;
      vm.wrongAnswer   = false;

      // Simple GET request example:
      $http({
        method: 'GET',
        url: 'app/main/' + vm.chosenCategoryID + '_samplequestion.json'
       // url: 'http://134.155.234.95:8080/SWTBeatTheQuiz/service'
      }).then(function successCallback(response) {

          // this callback will be called asynchronously
          // when the response is available
          console.log(response.data['question']);
          vm.question = response.data['question'];
          vm.answers  = response.data['answers'];
          vm.correct  = response.data['correct'];
        }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
          console.log(response);
        });

    });

    vm.reset = (function reset() {
      console.log('reset');
       vm.askQuestion = false;
       vm.selectCategory = true;
       vm.correctAnswer = false;
       vm.wrongAnswer = false;
       vm.chosenCategoryID = 'None';
       vm.selectedAnswer = 'None';
       vm.selectCategory = true;
    });


    // Called when an answer is selected
    vm.validate = (function validate(id) {
      vm.selectedAnswer = id;

      // As soon as answer is clicked, buttons are disabled and can't be clicked anymore
      jQuery(".answers button").attr("disabled","disabled");

      // Correct answer is given
      if (vm.correct == id) {
        console.log('correct answer selected');
        vm.correctAnswer = true;
        vm.wrongAnswer = false;
        vm.askQuestion = true;
        jQuery('.answers button.option-'+id).addClass('btn-success');

        // Update correct question counter
        var questionCount = localStorage.getItem(keyQuestionCounterCorrect);
        questionCount++;
        console.log("Correct questions: " + questionCount);
        localStorage.removeItem(keyQuestionCounterCorrect);
        localStorage.setItem(keyQuestionCounterCorrect, questionCount);

        // Update score
        vm.score++;
        console.log("Current score: " + vm.score);
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

      var questionCount = localStorage.getItem(keyQuestionCounter);
      questionCount++;
      console.log(questionCount);
      localStorage.removeItem(keyQuestionCounter);
      localStorage.setItem(keyQuestionCounter, questionCount);
    });

    /* New */ //TODO get this from the server
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

  }
})();
