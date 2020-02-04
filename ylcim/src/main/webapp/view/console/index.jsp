<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>经营者信用公示平台</title>
		<link rel="shortcut icon" type="image/x-icon" href="./static/images/favicon.ico" />
		<jsp:include page="./comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/agency/js/agency.js?v=${version}"></script>
		<script type="text/javascript" src="./view/console/module/apply/agency/js/wpr.js?v=${version}"></script>
		<script type="text/javascript" src="./view/console/module/apply/statistical/js/annual.js?v=${version}"></script>
		<script type="text/javascript" src="./view/console/module/apply/statistical/js/industry.js?v=${version}"></script>
		<script type="text/javascript" src="./view/console/js/index.js?v=${version}"></script>
		<script type="text/javascript">history.go(1);</script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'north'" style="height:100px; border-top: none;">
			<jsp:include page="./layout/north.jsp"></jsp:include>
		</div>
		<div data-options="region:'west',split:true,iconCls:'czs-layout-grid',title:'系统菜单'" style="width:260px;">
			<jsp:include page="./layout/west.jsp"></jsp:include>
		</div>
		<div data-options="region:'center'">
			<jsp:include page="./layout/center.jsp"></jsp:include>
		</div>
		<div data-options="region:'south'" style="height:30px;line-height: 28px;text-align: center;">
			<jsp:include page="./layout/south.jsp"></jsp:include>
		</div>
	</body>
</html>