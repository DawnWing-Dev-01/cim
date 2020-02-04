<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
	<head>
		<meta charset="utf-8">
		<title>图标管理</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<jsp:include page="../../../comm/header.jsp" />
		<link rel="stylesheet" type="text/css" href="./view/console/module/system/iconlibrary/css/caomei.css?v=${version}">
		<link rel="stylesheet" type="text/css" href="./view/console/module/system/iconlibrary/css/iconfont.css?v=${version}">
		<script type="text/javascript" src="./view/console/module/system/iconlibrary/js/iconlibrary.js?v=${version}"></script>
	</head>
	 
	<body>
		<div id="iconTab" class="easyui-tabs" data-options="tabPosition:'bottom',fit:true,pill:true">   
    		<div title="草莓图标" style="padding:20px;display:none;">   
        		<jsp:include page="../../../module/system/iconlibrary/iconshow/caomei.jsp" />
    		</div>   
		    <div title="阿里矢量">   
		        <jsp:include page="../../../module/system/iconlibrary/iconshow/iconfont.jsp" />
		    </div>   
		</div>
	</body>
</html>
