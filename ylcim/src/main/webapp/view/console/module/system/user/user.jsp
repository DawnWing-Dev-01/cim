<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript" src="./view/console/module/system/user/js/user.js?v=${version}"></script>
	</head>
	 
	<body class="easyui-layout">
		<div data-options="region:'center',border: false" style="overflow: hidden; border-left: 1px #C3D9E0 solid;">
			<table id="userGrid"></table>
		</div>
		
		<div data-options="region:'west',split: true, border: false" style="width:260px; border-right: 1px #C3D9E0 solid;">
			<div class="easyui-layout" data-options="fit:true, border: false">
				<div data-options="region:'north',title:'所属组织',border: false, collapsible:false" style="height:260px; border-bottom: 1px #C3D9E0 solid;">
					<ul id="organizationTree"></ul>
				</div>
				<div data-options="region:'center',title:'角色信息',border: false,collapsible:false">
					<table id="roleGrid"></table>
				</div>
			</div>
		</div>   
		
		<div id="userSearchMenu" style="width:120px"> 
			<div data-options="name:'userInfo.name',iconCls:'czs-people'">姓&nbsp;&nbsp;名</div> 
			<div data-options="name:'userInfo.username',iconCls:'czs-people'">账&nbsp;&nbsp;号</div> 
		</div>
		
		<%-- <shiro:hasPermission name="./userAction!index">
	        <a onclick="addUserFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
	    </shiro:hasPermission> --%>
	</body>
</html>