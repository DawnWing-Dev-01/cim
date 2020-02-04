<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, user-scalable=no, maximum-scale=1, minimum-scale=1">
		<meta name="author" content="w·xL">
		<title>进度查看</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/library/icon-library/caomei/1.2.8/style.css?v=${version}">
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/layui/css/layui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart>
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<ul id="workflowSchedule" class="layui-timeline" style="width: 65%; margin: 0px auto; margin-top: 30px;">
						<li class="layui-timeline-item">
							<i class="layui-icon layui-timeline-axis" style="color: red;"></i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">开始</div>
							</div>
						</li>
						<s:set var="runIndex" value="-1"></s:set>
						<s:iterator value="viewDeftData.workflowList" var="workflow" status="status">
							<s:if test="viewDeftData.workflowExample.flowNodeCode == #workflow.flowNodeCode">
								<s:set var="runIndex" value="#status.index + 1"></s:set>
							</s:if>
							
							<li class="layui-timeline-item">
								<s:if test="#status.index != #runIndex">
									<i class="layui-icon layui-timeline-axis" 
										<s:if test="#runIndex == -1 || #status.index < #runIndex">style="color: red;"</s:if>></i>
								</s:if>
								<s:else>
									<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis"></i>
								</s:else>
								<div class="layui-timeline-content layui-text">
									<div class="layui-timeline-title">
										<span>${workflow.flowNodeText}【${workflow.flowNodeCode}】</span>
										<s:if test="#status.index == #runIndex">
											<label class="during">进行中<span class="dotting"></span></label>
										</s:if>
									</div>
								</div>
							</li>
						</s:iterator>
						<li class="layui-timeline-item">
							<i class="layui-timeline-axis czs-circle-o" style="font-size: 18px;"></i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">结束</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	
		<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script src="../static/library/fastclick/fastclick.js?v=${version}"></script>
	</body>
</html>