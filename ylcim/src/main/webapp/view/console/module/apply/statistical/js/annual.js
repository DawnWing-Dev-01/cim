$(function(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('annual_echarts'));

	option = {
		title: {
	        text: '年度投诉量统计'
	    },
	    xAxis: {
	        type: 'category',
	        data: ['0', '0', '0', '0', '0']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [{
	        data: [0, 0, 0, 0, 0],
	        type: 'line'
	    }]
	};
	
	yearDynamicAjax(option, myChart);
	
	//myChart.setOption(option, true);
});

/**
 * 动态获取统计数据
 * @param option
 * @param myChart
 */
function yearDynamicAjax(option, myChart){
	$.ajax({
		url: './statisticalAnalysisAction!statisticsByYear.action',
		dataType: 'json',
		success: function(data){
			option.xAxis.data = data.xAxis;
			option.series[0].data = data.series;
		    myChart.setOption(option, true);
		}
	});
}