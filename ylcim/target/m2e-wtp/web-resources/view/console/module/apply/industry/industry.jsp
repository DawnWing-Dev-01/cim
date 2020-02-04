<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>行业管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/industry/js/industry.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="industryGrid"></table>
		</div>
		
		<div id="industrySearchMenu" style="width:120px"> 
			<div data-options="name:'industryInfo.name',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div> 
		</div>
	</body>
</html>