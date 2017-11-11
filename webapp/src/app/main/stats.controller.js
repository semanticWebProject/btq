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
    
    vm.answeredQuestions        = localStorage.getItem(keyQuestionCounter);
    vm.answeredQuestionsCorrect = localStorage.getItem(keyQuestionCounterCorrect);
    vm.answeredQuestionsWrong   = localStorage.getItem(keyQuestionCounterWrong);
    vm.highScore				= localStorage.getItem(keyHighScore);

    //bar chart
    var ctxB = document.getElementById("barChart").getContext('2d');
    var myBarChart = new Chart(ctxB, {
        type: 'bar',
        data: {
            labels: ["All", "Correct", "Wrong"],
            datasets: [{
                label: '# of answered questions',
                data: [ vm.answeredQuestions,
                        vm.answeredQuestionsCorrect,
                        vm.answeredQuestionsWrong
                      ],
                backgroundColor: [
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(255, 99, 132, 0.2)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(255,99,132,1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });

  }
})();
