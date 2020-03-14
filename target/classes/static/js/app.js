angular.module('app', []).controller('TouristController', function($http) {
	var vm = this;
	function refreshData() {
		$http({
			method : 'GET',
			url : 'api/tourists'
		}).then(function success(response) {
			vm.tourists = response.data;
			console.log(vm.tourists );
		}, function error(response) {
			console.log('API error ' + response.status);
		});
	}

	vm.addProduct = function(vm.tourists ) {
		$http({
			method : 'POST',
			url : 'api/tourists',
			data : tourists
		}).then(function success(response) {
			refreshData();
			vm.tourists = {};
		}, function error(response) {
			console.log('Data not saved ' + tourists);
		});
	}

	vm.appName = 'Tourist Manager';
	refreshData();
});