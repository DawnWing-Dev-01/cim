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
		<title>行业消费风险预警</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart class="earlywarning">
		
		<div class="weui-flex yujinghangye">
			<div class="weui-flex__item">
				<div class="row-item-indent1">
					<div class="weui-cells__title">
						<label class="title">预警行业：</label>
						<span class="industryshow">${industryInfo.name}</span>
					</div>
				</div>
			</div>
		</div>
		<s:if test="earlyWarningList.size()==0">
			<div class="weui-flex nowarning">
				<div class="weui-flex__item">
					<div class="row-item-indent1">
						<div class="weui-cells__title">
							<span class="industryshow">该行业无消费预警</span>
						</div>
					</div>
				</div>
			</div>
		</s:if>
		<s:iterator value="earlyWarningList" var="warning" status="status">
			<div class="weui-flex earlywarning-item">
				<div class="weui-flex__item">
					<div class="row-item-indent1">
						<div class="weui-cells__title">
							<div class="weui-flex title-row">
								<div class="weui-flex__item">
									<div class="earlywarning-title">${warning.ewTitle}</div>
								</div>
								<div class="weui-flex__item">
									<div class="earlywarning-time">
										<label>预警时间：</label>
										<span><s:date name="#warning.ewDate" format="yyyy年MM月"/></span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-item">
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd text-border earlywarning-content">
									<div class="description">
										<p>${warning.ewContent}</p>
									</div>
									<div class="signature">${warning.ewAuthor}</div>
									<div class="releasetime"><s:date name="#warning.ewDate" format="yyyy年MM月"/></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</s:iterator>
		<%--
		<div class="weui-flex earlywarning-item">
			<div class="weui-flex__item">
				<div class="row-item-indent1">
					<div class="weui-cells__title">
						<div class="weui-flex title-row">
							<div class="weui-flex__item">
								<div class="earlywarning-title">第二次预警</div>
							</div>
							<div class="weui-flex__item">
								<div class="earlywarning-time">
									<label>预警时间：</label>
									<span>2017年3月</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-item">
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border earlywarning-content">
								<div class="description">
									<p>自2017年1月以来，我局收到群众举报25次，举报内容为所销售西凤酒为假冒伪劣产品。经核查，目前市场上存在部分假冒西风十五年产品。请广大消费者在消费过程中注意辨别产品真伪，如发现假冒伪劣产品请及时通过本微信公众号进行举报。</p>
								</div>
								<div class="signature">杨凌示范区工商局</div>
								<div class="releasetime">2017年3月</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		--%>
		
		<div class="weui-flex dishonestyList-content">
			<div class="weui-flex__item">
				<div class="row-item">
					<a href="javascript:void(0);" class="weui-btn weui-btn_primary open-popup">点击查看本行业失信行为列表</a>
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