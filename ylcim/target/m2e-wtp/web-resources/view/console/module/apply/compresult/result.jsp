<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>投诉结果管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/compresult/js/result.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'west',border:false,title:'来源类型'" style="width:360px; 
				border-right: 1px #C3D9E0 solid;">
			<table id="originTypeGrid"></table>
		</div>
		
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="resultGrid"></table>
		</div>
		
		<div id="resultSearchMenu" style="width:120px"> 
			<div data-options="name:'complaintResult.enterCode',iconCls:'icon-shaixuan'">登记编号</div> 
		</div>
	</body>
</html>