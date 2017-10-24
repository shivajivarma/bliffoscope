(function(){
    angular.module('app')
        .directive('app', function(){
            return {
                scope: {},
                templateUrl: 'app/app.html',
                controller: 'AppController',
                controllerAs: 'vm'
            }
        });
})();