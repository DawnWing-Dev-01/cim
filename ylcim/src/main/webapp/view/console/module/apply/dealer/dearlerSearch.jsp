<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>经营者信息查询</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/apply/dealer/js/dearlerSearch.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden;">
			<div class="easyui-layout" data-options="fit:true,border: false" style="border-left: 1px #C3D9E0 solid;">
				<div data-options="region:'north',split: true, border: false" 
						style="height:360px; border-bottom: 1px #C3D9E0 solid;">
					<table id="dealerGrid"></table>
				</div>
				
				<div data-options="region:'center',border: false" style="overflow: hidden; border-top: 1px #C3D9E0 solid;">
					<table id="complaintGrid"></table>
				</div>
			</div>
		</div>
		
		<div data-options="region:'west',split: true, border: false" style="width:360px; 
				border-right: 1px #C3D9E0 solid;">
			<table id="industryGrid"></table>
		</div>
		
		<div id="dealerSearchMenu" style="width:120px"> 
			<div data-options="name:'dealerInfo.name',iconCls:'icon-shaixuan'">名&nbsp;&nbsp;称</div> 
			<div data-options="name:'dealerInfo.simpleName',iconCls:'icon-shaixuan'">简&nbsp;&nbsp;称</div>
			<div data-options="name:'dealerInfo.legalPerson',iconCls:'icon-shaixuan'">法&nbsp;&nbsp;人</div> 
			<div data-options="name:'dealerInfo.licenseNo',iconCls:'icon-shaixuan'">营业执照</div> 
		</div>
		
		<div id="complaintSearchMenu" style="width:120px"> 
			<div data-options="name:'complaintInfo.dealerName',iconCls:'icon-shaixuan'">被投诉者</div> 
		</div>
	</body>
</html>