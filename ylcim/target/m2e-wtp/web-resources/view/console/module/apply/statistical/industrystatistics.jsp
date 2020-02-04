<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>行业统计</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/statistical/js/industry.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<div style="width: 160px;margin: 100px auto;margin-bottom: 0px;">
				<label>选择年份</label>
				<input id="yearCommbox">
			</div>
			<!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
		    <div id="industry_echarts" style="width: 800px;height:350px;margin: 25px auto;"></div>
		</div>
	</body>
</html>