(function(){
    angular.module('app')
        .controller('AppController', controller);

    controller.$inject = ['analyzerApi'];
    function controller(analyzerApi){
        var vm = this;

        vm.threshold = 70;

        vm.findResult = function(){
            delete vm.result;
            delete vm.error;
            var formData = new FormData();
            formData.append('testDataFile', $('#testDataFile')[0].files[0]);

            angular.forEach($('#targetFile')[0].files, function(file){
                formData.append('targetFiles', file);
            });

            formData.append('threshold', vm.threshold);
            analyzerApi.findTargets(formData, function(res){
                vm.result = res;
            }, function(e){
                vm.error = e.data.message;
            });
        };
    }
})();