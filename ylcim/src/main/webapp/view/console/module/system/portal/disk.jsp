<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>磁盘使用率</title>
    <jsp:include page="../../../comm/header.jsp" />
    <script type="text/javascript" src="${ctx}/static/library/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="./js/disk.js"></script>
  </head>
  
  <body style="margin: 0;padding: 0;">
    <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="disk_echarts" style="width: 360px;height:290px;margin: auto;"></div>
  </body>
</html>
