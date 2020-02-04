<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>12315消费提示</title>
		<jsp:include page="../../../comm/header.jsp" />
		<script type="text/javascript">
			var columnId = '${param.columnId}';
		</script>
		<link rel="stylesheet" type="text/css" href="./static/style/console/12315.css?v=${version}">
		<script type="text/javascript" src="./view/console/module/apply/12315/js/12315.js?v=${version}"></script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'center',border: false">
			<div class="month-container">
				<div class="month-item" data-month="1">
					<label>一月</label>
					<div class="opt-area">
						<a class="fabu" href="javascript:void(0);">
							<i class="icon czs-telegram"></i>
							<label>发布</label>
						</a>
						<a class="yulan" href="javascript:void(0);">
							<i class="icon icon-fenxi"></i>
							<label>预览</label>
						</a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>