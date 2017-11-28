(function() {
  'use strict';

  angular
    .module('guan')
    .controller('StatsController', StatsController);

  /** @ngInject */
  function StatsController() {
    var vm = this;
    var keyQuestionCounter        = "questionCounter";
    var keyQuestionCounterCorrect = "questionCounterCorrect";
    var keyQuestionCounterWrong   = "questionCounterWrong";
    var keyHighScore = "highScore";
    var keyQuestionCounterCorrectEasy = "questionCounterCorrectEasy";
    var keyQuestionCounterWrongEasy   = "questionCounterWrongEasy";
    var keyQuestionCounterCorrectMedium = "questionCounterCorrectMedium";
    var keyQuestionCounterWrongMedium   = "questionCounterWrongMedium";
    var keyQuestionCounterCorrectHard = "questionCounterCorrectHard";
    var keyQuestionCounterWrongHard   = "questionCounterWrongHard";


    vm.resetStats = (function() {
      window.location.reload();
      console.log('reset stats');
      localStorage.removeItem(keyQuestionCounter);
      localStorage.removeItem(keyQuestionCounterCorrect);
      localStorage.removeItem(keyQuestionCounterWrong);
      localStorage.removeItem(keyHighScore);
      localStorage.setItem(keyQuestionCounter, 0);
      localStorage.setItem(keyQuestionCounterCorrect, 0);
      localStorage.setItem(keyQuestionCounterWrong, 0);
      localStorage.setItem(keyHighScore, 0);
      localStorage.setItem(keyQuestionCounterCorrectEasy, 0);
      localStorage.setItem(keyQuestionCounterCorrectMedium, 0);
      localStorage.setItem(keyQuestionCounterCorrectHard, 0);
      localStorage.setItem(keyQuestionCounterWrongEasy, 0);
      localStorage.setItem(keyQuestionCounterWrongMedium, 0);
      localStorage.setItem(keyQuestionCounterWrongHard, 0);

      vm.answeredQuestions        = 0;
      vm.answeredQuestionsCorrect = 0;
      vm.answeredQuestionsWrong   = 0;
      vm.highScore				        = 0;

    });

    vm.answeredQuestions        = localStorage.getItem(keyQuestionCounter);
    vm.answeredQuestionsCorrect = localStorage.getItem(keyQuestionCounterCorrect);
    vm.answeredQuestionsWrong   = localStorage.getItem(keyQuestionCounterWrong);
    vm.highScore				        = localStorage.getItem(keyHighScore);

    //bar chart
    var ctxBEasy = document.getElementById("barChartEasy").getContext('2d');
    var easyChart = new Chart(ctxBEasy, {
        type: 'doughnut',
        data: {
            labels: ["Correct", "Wrong"],
            datasets: [{
                data: [ localStorage.getItem(keyQuestionCounterCorrectEasy),
                  localStorage.getItem(keyQuestionCounterWrongEasy)
                      ],
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(255, 99, 132, 0.2)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(255,99,132,1)'
                ]
            }]
        }
    });

    var ctxBMedium = document.getElementById("barChartMedium").getContext('2d');
    var mediumChart = new Chart(ctxBMedium, {
      type: 'doughnut',
      data: {
        labels: ["Correct", "Wrong"],
        datasets: [{
          data: [ localStorage.getItem(keyQuestionCounterCorrectMedium),
            localStorage.getItem(keyQuestionCounterWrongMedium)
          ],
          backgroundColor: [
            'rgba(75, 192, 192, 0.2)',
            'rgba(255, 99, 132, 0.2)'
          ],
          borderColor: [
            'rgba(75, 192, 192, 1)',
            'rgba(255,99,132,1)'
          ]
        }]
      }
    });

    var ctxBHard = document.getElementById("barChartHard").getContext('2d');
    var hardChart = new Chart(ctxBHard, {
      type: 'doughnut',
      data: {
        labels: ["Correct", "Wrong"],
        datasets: [{
          data: [ localStorage.getItem(keyQuestionCounterCorrectHard),
            localStorage.getItem(keyQuestionCounterWrongHard)
          ],
          backgroundColor: [
            'rgba(75, 192, 192, 0.2)',
            'rgba(255, 99, 132, 0.2)'
          ],
          borderColor: [
            'rgba(75, 192, 192, 1)',
            'rgba(255,99,132,1)'
          ]
        }]
      }
    });
  }
})();
