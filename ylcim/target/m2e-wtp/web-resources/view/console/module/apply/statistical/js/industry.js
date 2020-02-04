$(function(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('industry_echarts'));

	option = {
		title: {
	        text: '行业投诉量统计'
	    },
	    xAxis: {
	        type: 'category',
	        data: ['行业1', '行业2', '行业3', '行业4', '行业5']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [{
	        data: [0, 0, 0, 0, 0],
	        type: 'bar'
	    }]
	};
	
	 myChart.setOption(option, true);
	 
	 industryDynamicAjax(option, myChart, null);
	 
	 $('#yearCommbox').combobox({    
		 width: 100,
		 url:'./statisticalAnalysisAction!getYearCommbox.action',    
		 valueField:'year',    
		 textField:'text',
		 onClick: function(record){
			 industryDynamicAjax(option, myChart, record.year);
		 }
	 });
});

/**
 * 动态获取统计数据
 * @param option
 * @param myChart
 */
function industryDynamicAjax(option, myChart, year){
	var data = {
			object: year
	};
	$.ajax({
		url: './statisticalAnalysisAction!statisticsByIndustry.action',
		data: data,
		dataType: 'json',
		success: function(data){
			option.xAxis.data = data.xAxis;
			option.series[0].data = data.series;
		    myChart.setOption(option, true);
		}
	});
}