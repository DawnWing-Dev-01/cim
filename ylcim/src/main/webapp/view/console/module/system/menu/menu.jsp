<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>菜单管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/system/menu/js/menu.js?v=${version}"></script>
	</head>
	 
	<body>
		
		<div class="easyui-panel" data-options="fit:true,border: false" style="overflow: hidden;">
			<table id="menuGrid"></table>
		</div>
		
	</body>
</html>
