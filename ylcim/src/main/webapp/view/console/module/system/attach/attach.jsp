<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
		<title>附件管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/system/attach/js/attach.js?v=${version}"></script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="attachGrid"></table>
		</div>
		<div id="attachSearchMenu" style="width:120px">
		    <div data-options="name:'attachInfo.attachName',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div>
		</div>
	</body>
</html>