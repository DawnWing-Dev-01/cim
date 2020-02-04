$(function(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('disk_echarts'));

	option = {
	    title: {
	        text: '服务器磁盘占用率（单位:百分比）'
	        //subtext: 'From ExcelHome',
	        //sublink: 'http://e.weibo.com/1341556070/AjQH99che'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {
	            type : 'shadow'
	        },
	        formatter: function (params) {
	            var tar = params[0];
	            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type : 'category',
	        splitLine: {show:false},
	        data : ['C盘','D盘']
	    },
	    yAxis: {
	        type : 'value'
	    },
	    series: [
	        {
	            name: '占用率',
	            type: 'bar',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'inside'
	                }
	            },
	            data:[30, 2]
	        },
	        {
	            name: '使用率',
	            type: 'line',
	            data:[30, 2]
	        }
	    ]
	};
	
	 myChart.setOption(option, true);
});