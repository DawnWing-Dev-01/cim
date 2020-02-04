<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>来源类型</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/originType/js/originType.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="originTypeGrid"></table>
		</div>
		
		<div id="originTypeSearchMenu" style="width:120px"> 
			<div data-options="name:'constant.scKey',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div> 
		</div>
	</body>
</html>