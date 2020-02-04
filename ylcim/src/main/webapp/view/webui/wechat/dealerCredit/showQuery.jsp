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
		<title>经营者信用查询</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart>
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">您查询的经营者信息</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border">
								<div class="dealerinfo-item">
									<label>名称：</label>
									<span>${dealerCredit.dealerInfo.name}</span>
								</div>
								<div class="dealerinfo-item">
									<label>主营项目：</label>
									<span>${dealerCredit.dealerInfo.mainProject}</span>
								</div>
								<div class="dealerinfo-item">
									<label>经营地址：</label>
									<span>${dealerCredit.dealerInfo.dealerAddress}</span>
								</div>
								<div class="dealerinfo-item">
									<label>法人姓名：</label>
									<span>${dealerCredit.dealerInfo.legalPerson}</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<s:if test="dealerCredit.creditArray.size > 0 || dealerCredit.complaintResultArray.size >0">
			<div class="weui-flex">
				<div class="weui-flex__item">
					<div class="row-item">
						<div class="weui-cells__title">经营者诚信经营记录如下</div>
						<div class="weui-cells text-border">
							<!-- 信用公示平台的数据 -->
							<s:iterator value="dealerCredit.creditArray" var="dealerCreditVo">
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<div class="chengxinjilu-item">
											<div class="weui-flex">
												<div class="key-left">
													<label>被举报问题：</label>
												</div>
												<div class="weui-flex__item">
													<span>${dealerCreditVo.issueReported}</span>
												</div>
											</div>
										</div>
										<div class="chengxinjilu-item">
											<div class="weui-flex">
												<div class="key-left">
													<label>举报时间：</label>
												</div>
												<div class="weui-flex__item">
													<span><s:date name="#dealerCreditVo.reportDate" format="yyyy年MM月dd日"/></span>
												</div>
											</div>
										</div>
										<div class="chengxinjilu-item">
											<div class="weui-flex">
												<div class="key-left">
													<label>处罚结果：</label>
												</div>
												<div class="weui-flex__item">
													<span>${dealerCreditVo.penaltyResult}</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</s:iterator>
							<!-- 其他系统导入的投诉结果 -->
							<s:iterator value="dealerCredit.complaintResultArray" var="complaintResult">
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<div class="chengxinjilu-item">
											<div class="weui-flex">
												<div class="key-left">
													<label>被举报问题：</label>
												</div>
												<div class="weui-flex__item">
													<span>${complaintResult.complaints}</span>
												</div>
											</div>
										</div>
										<div class="chengxinjilu-item">
											<div class="weui-flex">
												<div class="key-left">
													<label>举报时间：</label>
												</div>
												<div class="weui-flex__item">
													<span><s:date name="#complaintResult.enterDate" format="yyyy年MM月dd日"/></span>
												</div>
											</div>
										</div>
										<div class="chengxinjilu-item">
											<div class="weui-flex">
												<div class="key-left">
													<label>处罚结果：</label>
												</div>
												<div class="weui-flex__item">
													<span>${complaintResult.finishKnot}</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</s:iterator>
						</div>
					</div>
				</div>
			</div>
		</s:if>
		<s:else>
			<div class="weui-flex">
				<div class="weui-flex__item">
					<div class="row-item">
						<div class="weui-cells__title">经营者诚信经营记录如下</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd text-border creditGood">
									<div class="description">该经营者信用记录良好，截止<s:date name="dealerCredit.nowDate" format="yyyy年MM月dd日"/>，我局暂未收到该经营者的举报信息。</div>
									<div class="signature">杨凌示范区工商局</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</s:else>
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<a id="iwantreport" href="./wechatAction!dishonestyEnterView.action?dealerId=${dealerCredit.dealerInfo.id}" 
						class="weui-btn weui-btn_primary open-popup">我要举报</a>
				</div>
			</div>
		</div>
		<div class="weui-flex industry-content">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">该经营者所属行业：${dealerCredit.industry.name}</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border">
								<s:if test="dealerCredit.warningArray.size > 0">
									<a id="consumerisk" class="weui-btn weui-btn_primary"
										href="./wechatAction!earlywarning?industryId=${dealerCredit.industry.id}">
										该行业存在消费风险，请点击查看
									</a>
								</s:if>
								<s:else>
									<a id="consumerisk" href="javascript:void(0);" 
										class="weui-btn weui-btn_primary">该行业近期无消费风险</a>
								</s:else>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script src="../static/library/fastclick/fastclick.js?v=${version}"></script>
		<%-- <script type="text/javascript">
			//$.showLoading();
			var wechatConfig = {
				// 要用户确认授权并获取code的url
				authorizeCodeUrl : '${viewDeftData.authorizeCodeUrl}'
			};
		</script> --%>
		<script src="../static/js/webui/wechat/showQuery.js?v=${version}"></script>
	</body>
</html>