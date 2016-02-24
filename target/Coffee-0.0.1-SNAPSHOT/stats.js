// Last 7 weekdays
function makeHighChart(coffeeCategories, coffeeDataSeries){
/*
var coffeeCategories = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']
// name: name , data: [1,1,1,1]
var coffeeDataSeries = [
    {name: 'Sigg', data: [1,2,3,4,5,6,7]},
    {name: 'Mike', data: [2,2,3,4,5,6,7]},
    {name: 'Eirik', data: [2,2,2,4,5,6,7]}
]*/



var chart = new Highcharts.Chart({
    
        chart: {
            type: 'column',
            renderTo : 'weeklyView'
        },
        title: {
            text: 'Stacked column chart'
        },
        xAxis: {
            categories: coffeeCategories
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total coffee consumption'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
            headerFormat: '<b>{point.x}</b><br/>',
            pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        series: coffeeDataSeries
    });
}