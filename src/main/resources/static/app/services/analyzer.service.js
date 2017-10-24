(function () {
    angular.module('app')
        .factory('analyzerApi', service);

    service.$inject = ['$resource'];
    function service($resource) {
        return $resource('analyser/:destination', null, {
            'findTargets': {
                method: 'POST',
                isArray: true,
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity,
                params: {
                    destination: 'findTargets'
                }
            }
        });
    }
})();