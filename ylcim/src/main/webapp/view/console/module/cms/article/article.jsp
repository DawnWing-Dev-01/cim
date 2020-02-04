<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>文章管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript">
			var columnId = '${param.columnId}';
		</script>
		<script type="text/javascript" src="./view/console/module/cms/article/js/article.js?v=${version}"></script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<table id="articleGrid"></table>
		</div>
		
		<div id="articleSearchMenu" style="width:120px"> 
			<div data-options="name:'articleInfo.name',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div>
			<div data-options="name:'articleInfo.articleFrom',iconCls:'icon-shaixuan'">来&nbsp;&nbsp;源</div>
			<div data-options="name:'articleInfo.articleType',iconCls:'icon-shaixuan'">类&nbsp;&nbsp;型</div>
			<div data-options="name:'articleInfo.searchIndex',iconCls:'icon-shaixuan'">关键字</div>
		</div>
	</body>
</html>