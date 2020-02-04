<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>历史预警</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/warning/js/history.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false"  style="overflow: hidden; border-left: 1px #C3D9E0 solid;">
			<table id="warningGrid"></table>
		</div>
		
		<div data-options="region:'west',split: true, border: false" style="width:360px; border-right: 1px #C3D9E0 solid;">
			<table id="industryGrid"></table>
		</div>
		
		<div id="warningSearchMenu" style="width:120px"> 
			<div data-options="name:'warningInfo.ewTitle',iconCls:'icon-shaixuan'">标&nbsp;&nbsp;题</div> 
		</div>
	</body>
</html>