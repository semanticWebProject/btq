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
     //  var base_url = 'http://localhost:8080/';
//    var base_url = 'http://134.155.210.159:8080/backend/';
    var keyQuestionCounter        = "questionCounter";
    var keyQuestionCounterCorrect = "questionCounterCorrect";
    var keyQuestionCounterWrong   = "questionCounterWrong";
    var keyHighScore              = "highScore";
    var keyQuestionCounterCorrectEasy = "questionCounterCorrectEasy";
    var keyQuestionCounterWrongEasy   = "questionCounterWrongEasy";
    var keyQuestionCounterCorrectMedium = "questionCounterCorrectMedium";
    var keyQuestionCounterWrongMedium   = "questionCounterWrongMedium";
    var keyQuestionCounterCorrectHard = "questionCounterCorrectHard";
    var keyQuestionCounterWrongHard   = "questionCounterWrongHard";


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
    vm.difficultyLevel = 0; // default: easy
    vm.gameLives = 5;      // default: easy
    vm.difficultyLevelDescription = 'Easy mode is selected. You have 5 lives'; //default: easy
    vm.questionLoaded = false;
    vm.difficultyLevelText = 'easy';

    /* Init Functions */
    getCategories(); //retrieves the categories from the server


    /* Model functions */
    //resets all value if game is over or home is clicked, otherwise load new question,
    vm.reset = (function reset(home) {
      console.log('-- Home: ' + home);

      if (vm.gameLives > 0 && !home) {
        console.log('lives are still there');
        vm.loadFields();
        return;
      }
      console.log('reset or lost' + home);

      window.location.reload();
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
       vm.difficultyLevel = 0; // default: easy
       vm.gameLives = 5;      // default: easy
       vm.nextButtonText = 'You lost all your lives. Try again!';
     });

    // Called when category is chosen, loads first question
    vm.chooseCategory = (function cC(catID) {
      console.log('chosen category: ' + catID);
      for (var i = 0; i<vm.categories.length; i++) {
        if (vm.categories[i].id == catID) {
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


      // Simple GET request example:
      var url = base_url+'category/'+vm.chosenCategory.id+'/question?level='+vm.difficultyLevel;
      $http({
        method: 'GET',
        url: url
      }).then(function successCallback(response) {
          console.log(response);

          vm.questionLoaded = true;
          jQuery("button.nextButton").removeAttr("disabled","disabled");
          if (vm.correctAnswer) {
            vm.nextButtonText = 'Next';
          }
          else {
            if (vm.gameLives > 0){
              vm.nextButtonText = 'You got it wrong, but you still have ' + vm.gameLives  + ((vm.gameLives >1) ? ' lives' : ' life') + '  left :)';
              vm.nextButtonWrong = 'btn-grey';
            }
          }

          // this callback will be called asynchronously
          // when the response is available
          vm.question = response.data['question'];
          vm.answers  = response.data['answers'];
          vm.correct  = response.data['correct'];
          vm.imageURL = response.data['image'];
          if (vm.imageURL == null) {
            vm.imageURL = false;
          }

          if (vm.firstLoad == 1) {
            vm.firstLoad = 0;
            console.log('first load');
            vm.loadFields();
          }

        }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
          console.log("request error");
          console.log(response);
          vm.loadQuestion();
        });
    });

    vm.loadFields = (function() {
      // reset selected answer
      if (vm.questionLoaded) { //might break

        vm.questionLoaded = false;

        vm.selectedAnswer = 'None';
        console.log('load fields after click');
        vm.imageURLField = 'loading ...';
        vm.questionField = vm.question;
        vm.imageURLField = vm.imageURL;
        vm.answersField  = vm.answers;
        vm.correctField  = vm.correct;
        vm.correctAnswer = false;
        vm.wrongAnswer   = false;
        vm.newHighscore  = false;
      }

    });

    // Called when an answer is selected
    vm.validate = (function validate(id) {
      vm.selectedAnswer = id;
      console.log('Selected ID: ' + id);

      // As soon as answer is clicked, buttons are disabled and can't be clicked anymore
      jQuery(".answers button").attr("disabled","disabled");

      // always load question once question was answered correctly (lives might be still there)
      if (vm.correct == id || vm.gameLives > 1) {
        vm.loadQuestion();
        jQuery("button.nextButton").attr("disabled","disabled");
        console.log(' ---- Loading new question already ---- ');
      }
      vm.nextButtonText = 'Loading ...';
      vm.nextButtonWrong = 'btn-grey';

      // Correct answer is given
      if (vm.correct == id) {

        jQuery('.answers button.option-'+id).addClass('btn-success');

        //Flashes score icon
        setTimeout(function() {
          jQuery('.scoreInformation').addClass('flash-icon');
        }, 500);
        setTimeout(function() {
          jQuery('.scoreInformation').removeClass('flash-icon');
        }, 1500);

        // Update correct question counter
        var questionCount = localStorage.getItem(keyQuestionCounterCorrect);
        questionCount++;
        localStorage.removeItem(keyQuestionCounterCorrect);
        localStorage.setItem(keyQuestionCounterCorrect, questionCount);

        // update difficulty level counter for correct answers
        switch(vm.difficultyLevel) {
          case 0:
            if (localStorage.getItem((keyQuestionCounterCorrectEasy))) {
              localStorage.setItem(keyQuestionCounterCorrectEasy, parseInt(localStorage.getItem(keyQuestionCounterCorrectEasy)) + 1);
            } else {
              localStorage.setItem(keyQuestionCounterCorrectEasy, 1);
            }
            break;
          case 1:
            if (localStorage.getItem((keyQuestionCounterCorrectMedium))) {
              localStorage.setItem(keyQuestionCounterCorrectMedium, parseInt(localStorage.getItem(keyQuestionCounterCorrectMedium)) + 1);
            } else {
              localStorage.setItem(keyQuestionCounterCorrectMedium, 1);
            }
            break;
          case 2:
            if (localStorage.getItem((keyQuestionCounterCorrectHard))) {
              localStorage.setItem(keyQuestionCounterCorrectHard, parseInt(localStorage.getItem(keyQuestionCounterCorrectHard)) + 1);
            } else {
              localStorage.setItem(keyQuestionCounterCorrectHard, 1);
            }
            break;
        }


        vm.correctAnswer = true;
        vm.wrongAnswer = false;
        vm.askQuestion = true;

        // Update score
        vm.score++;

        //update highscore
        console.log('currenct highscore: ' + localStorage.getItem(keyHighScore));
        if (vm.score > localStorage.getItem(keyHighScore)) {
          vm.newHighscore = true;
          vm.highScore = vm.score;
          localStorage.removeItem(keyHighScore);
          localStorage.setItem(keyHighScore, vm.score);
        }
      }

      // wrong answer
      else {
        jQuery('.answers button.option-'+id).addClass('btn-danger');
        //Flashes the correct answer for 2s after 800ms
        setTimeout(function() {
          jQuery('.answers button.option-'+vm.correctField).addClass('btn-success flash-button');
        }, 500);
        setTimeout(function() {
          jQuery('.answers button.option-'+vm.correctField).removeClass('flash-button');
        }, 1500);

        vm.gameLives--;
        console.log('game lives: '  + vm.gameLives);


        // Update wrong question counter
        var questionCount = localStorage.getItem(keyQuestionCounterWrong);
        questionCount++;
        localStorage.removeItem(keyQuestionCounterWrong);
        localStorage.setItem(keyQuestionCounterWrong, questionCount);

        // update difficulty level counter for wrong answers
        switch(vm.difficultyLevel) {
          case 0:
            if (localStorage.getItem((keyQuestionCounterWrongEasy))) {
              localStorage.setItem(keyQuestionCounterWrongEasy, parseInt(localStorage.getItem(keyQuestionCounterWrongEasy)) + 1);
            } else {
              localStorage.setItem(keyQuestionCounterWrongEasy, 1);
            }
            break;
          case 1:
            if (localStorage.getItem((keyQuestionCounterWrongMedium))) {
              localStorage.setItem(keyQuestionCounterWrongMedium, parseInt(localStorage.getItem(keyQuestionCounterWrongMedium)) + 1);
            } else {
              localStorage.setItem(keyQuestionCounterWrongMedium, 1);
            }
            break;
          case 2:
            if (localStorage.getItem((keyQuestionCounterWrongHard))) {
              localStorage.setItem(keyQuestionCounterWrongHard, parseInt(localStorage.getItem(keyQuestionCounterWrongHard)) + 1);
            } else {
              localStorage.setItem(keyQuestionCounterWrongHard, 1);
            }
            break;
        }

        vm.correctAnswer = false;
        vm.wrongAnswer = true;
        vm.askQuestion = true;

        //update highscore
        console.log('currenct highscore: ' + localStorage.getItem(keyHighScore));
        if (vm.score > localStorage.getItem(keyHighScore)) {
          vm.newHighscore = true;
          vm.highScore = vm.score;
          localStorage.removeItem(keyHighScore);
          localStorage.setItem(keyHighScore, vm.score);
        }

        if (vm.gameLives == 0){
          jQuery('.answers button.option-'+id).addClass('btn-danger');
          vm.nextButtonText = 'Game over. You got ' + vm.score + ' point' + ((vm.score==1) ? '' : 's') + '!';
          vm.nextButtonWrong = 'btn-danger';
        }
        else {
          jQuery('.answers button.option-'+id).addClass('btn-warning');
          vm.nextButtonWrong = 'btn-grey';
        }

      }

      // scroll down for validation
      $('html, body').animate({
         scrollTop: document.body.scrollHeight
      }, 'slow');

      //update question counter
      var questionCount = localStorage.getItem(keyQuestionCounter);
      questionCount++;
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


    vm.selectDifficultyLevel = (function(difficultyLevel) {
      console.log('selected level: '+ difficultyLevel);
      vm.difficultyLevel = difficultyLevel;
      switch(difficultyLevel) {
        case 0: vm.gameLives = 5;
                vm.difficultyLevelText = 'easy';
                vm.difficultyLevelDescription = 'You selected the easy mode. You have ' + vm.gameLives + ' lives';
                $('.difficultyLevel .easyOption').addClass('btn-success');
                $('.difficultyLevel .mediumOption').removeClass('btn-warning');
                $('.difficultyLevel .hardOption').removeClass('btn-danger');
          break;
        case 1:
                vm.gameLives = 4;
                vm.difficultyLevelText = 'medium';
                vm.difficultyLevelDescription = 'You selected the medium mode. You have ' + vm.gameLives + ' lives';
                $('.difficultyLevel .easyOption').removeClass('btn-success');
                $('.difficultyLevel .mediumOption').addClass('btn-warning');
                $('.difficultyLevel .hardOption').removeClass('btn-danger');
          break;
        case 2:
                vm.gameLives = 3;
                vm.difficultyLevelText = 'hard';
                vm.difficultyLevelDescription = 'You selected the hard mode.  You have ' + vm.gameLives + ' lives';
                $('.difficultyLevel .easyOption').removeClass('btn-success');
                $('.difficultyLevel .mediumOption').removeClass('btn-warning');
                $('.difficultyLevel .hardOption').addClass('btn-danger');
          break;
      }
    });

  }
})();
