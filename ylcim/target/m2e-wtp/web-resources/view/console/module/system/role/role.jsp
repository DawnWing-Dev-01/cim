<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>角色管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/system/role/js/role.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="roleGrid"></table>
		</div>
		
		<div id="roleSearchMenu" style="width:120px"> 
			<div data-options="name:'roleInfo.name',iconCls:'icon-shaixuan'">角&nbsp;&nbsp;色</div> 
		</div>
	</body>
</html>