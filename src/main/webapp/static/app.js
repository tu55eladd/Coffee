var app = angular.module("CoffeeApp",['ngRoute']);

app.controller("CoffeeCtrl", ['$scope', '$http', function($scope,$http) {

	$scope.selected = {person:undefined};
	$scope.views = [
	{
		name: "Register cup",
		href: "home"
	},
	{
		name: "Total",
		href: "total"
	},
	{
		name: "Weekly",
		href: "weekly"
	}
	];
	$scope.addButtonDisabled = {value : false}
	$scope.addCup = function (){
		var person = getSelectedPerson()
		console.log(person)
		if(!person){return}
		
		$http({
				method: "POST",
				url:"/consumption/add",
				data: person})
		.then(addCupResponse,errorTrace);
	}

	function addCupResponse(data){
		$scope.addButtonDisabled.value = true;
	}

	function getSelectedPerson(){
		return $scope.selected.person
	}

	changeToPersonView = function(){
		$http.get("/consumtion/all")
		.then(function(response){
			
			personChart($scope.persons, response.data)
			
		},errorTrace)
	}

	function setPersons(response){
		$scope.persons = response.data;
	}

	function errorTrace(response){
		console.log(response);
	}

	function getPersons(){
		$http.get("/person/all").
		then(setPersons,errorTrace);
	}
	
	changeToWeekView = function(){
		var days = 7
		$http.get("/consumtion/daysback?days="+days)
		.then(function(response){
			weekChart(response.data)
		},errorTrace)
	}

	getPersons()

}]);

app.config(['$routeProvider',
	function($routeProvider) {

		$routeProvider
			.when('/home',{
				templateUrl : function(params) {
					//changeSelected()
					return 'static/partials/home.html'
				}
			})
			.when('/total',{
				templateUrl : function(params) {
					//changeSelected()
					setTimeout(changeToPersonView,10)
					return 'static/partials/total.html'
				}
			})
			.when('/weekly',{
				templateUrl : function(params) {
					setTimeout(changeToWeekView,10);
					return 'static/partials/weekly.html'
				}
			})
			.otherwise({
				redirectTo: '/home'
			})
	}
])

function weekChart(coffeeDataSeries){
	var coffeeCategories = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']
	// name: name , data: [1,1,1,1]
	makeHighChart(coffeeCategories,coffeeDataSeries)
}

function personChart(persons,consumptions){
	var personCategories = extractNamesFromPersonObjects(persons)
	var dataSeries = countPerPerson(persons,consumptions)
	console.log(dataSeries);
	makeHighChart(personCategories,dataSeries)
}

function countPerPerson(persons,consumptions){
	var coffeesPerPerson = [0,0,0,0]
	for(i=0; i<persons.length; i++){
		for(u=0; u<consumptions.length;u++){
			if(consumptions[u].person.id == persons[i].id){
				coffeesPerPerson[i] += 1;
			}
		}
	}
	return [{ name: "coffees", data: coffeesPerPerson }]
	
}

function extractNamesFromPersonObjects(persons){
	var names = []
	for(i=0;i<persons.length;i++){
		names.push(persons[i].name)
	}
	return names;
}