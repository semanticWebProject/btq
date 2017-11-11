(function() {
  'use strict';

  angular
    .module('guan')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
