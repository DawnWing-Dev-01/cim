<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>iphone预览</title>
		<jsp:include page="../../../comm/header.jsp" />
	</head>
	<body style="margin: 0px;">
		<div style="overflow: hidden;">
			<div class="device device-fixed" id="flexInDevice">
				<div class="status-bar"></div>
				<div class="winiphone" id="winiphone">
					<iframe id="showIphone" width="262" height="445" frameborder="0"></iframe>
				</div>
				<div id="qrcode-btn"></div>
			</div>
		</div>
	</body>
</html>