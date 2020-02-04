<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="userlayout" class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border: false" style="overflow: hidden;">
		<table id="userGrid"></table>
	</div>
	
	<div data-options="region:'west',border: false" title="组织机构"
			style="width:230px; padding: 10px; border-right: 1px #C3D9E0 solid;">
		<ul id="organizationTree"></ul>
	</div>
	
	<div id="userSearchMenu" style="width:120px"> 
		<div data-options="name:'userInfo.name',iconCls:'czs-people'">姓&nbsp;&nbsp;名</div> 
		<div data-options="name:'userInfo.username',iconCls:'czs-people'">账&nbsp;&nbsp;号</div> 
	</div>
</div>