<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>栏目管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/cms/column/js/column.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="columnGrid"></table>
		</div>
		
		<div id="columnSearchMenu" style="width:120px"> 
			<div data-options="name:'columnInfo.name',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div> 
		</div>
	</body>
</html>