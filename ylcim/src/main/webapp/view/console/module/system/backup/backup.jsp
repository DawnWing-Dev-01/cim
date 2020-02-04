<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>数据备份</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/system/backup/js/backup.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="backupGrid"></table>
		</div>
		
		<div id="backupSearchMenu" style="width:120px"> 
			<div data-options="name:'backupInfo.name',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div> 
		</div>
	</body>
</html>