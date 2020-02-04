$(function(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('cpu_echarts'));

	// 指定图表的配置项和数据
	option = {
	    tooltip : {
	        formatter: "{a} <br/>{b} : {c}%"
	    },
	    toolbox: {
	        feature: {
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    series: [
	        {
	            name: 'CPU 使用率',
	            type: 'gauge',
	            detail: {formatter:'{value}%'},
	            data: [{value: 50, name: 'CPU 使用率'}]
	        }
	    ]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option, true);
	
	var timeTicket = setInterval(function () {
		option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
	    myChart.setOption(option, true);
	},2000);
	
/*	var timeTicket = setInterval(function () {
	    $.ajax({
	    	type: 'post',
	    	url: '../serverAction!getMonitor',
	    	dataType: 'json',
	    	success: function(data){
	    		var cpuRatio = parseFloat(data.cpuRatio);
	    		option.series[0].data[0].value = cpuRatio.toFixed(2) - 0;
	    	    myChart.setOption(option, true);
	    	}
	    });
	},5000);*/
});