<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html lang="zh">
	<head>
		<meta charset="utf-8">
		<title>图标管理</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<jsp:include page="../../../comm/header.jsp" />
		<link rel="stylesheet" type="text/css" href="./view/console/module/system/iconlibrary/css/choosel.css?v=${version}">
		<script type="text/javascript" src="./view/console/module/system/iconlibrary/js/iconlibrary.js?v=${version}"></script>
	</head>
	 
	<body>
		<div id="iconTab" class="easyui-tabs" data-options="fit:true,pill:true">   
    		<div id="caomei" title="草莓图标">   
        		<div class="markdown choosebody">
					<ul class="icon_lists clear">
						<s:iterator value="caomeiIconlibs" var="iconlib">
							<li data-cls="${iconlib.iconCls}">
								<i class="icon ${iconlib.iconCls}"></i>
								<div class="fontclass">${iconlib.name}</div>
							</li>
						</s:iterator>
					</ul>
				</div>
    		</div>   
		    <div id="iconfont" title="阿里矢量">   
		        <div class="markdown choosebody">
					<ul class="icon_lists clear">
						<s:iterator value="iconfontIconlibs" var="iconlib">
							<li data-cls="${iconlib.iconCls}">
								<i class="icon iconfont ${iconlib.iconCls}"></i>
								<div class="fontclass">${iconlib.name}</div>
							</li>
						</s:iterator>
					</ul>
				</div>
		    </div>   
		</div>
		<input type="hidden" id="iconCls"/>
	</body>
</html>
