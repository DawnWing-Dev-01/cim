<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, user-scalable=no, maximum-scale=1, minimum-scale=1">
		<meta name="author" content="w·xL">
		<title>消费助手</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart class="helperbody">
		<div class="weui-flex xiaofeiyujing">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells" style="background: none !important;">
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<a href="./wechatAction!showArticleListView?articleInfo.columnId=402880e96364331f016364489ad80000" 
									class="weui-btn weui-btn_primary cus-btn-green" data-target="#industrylist-weui-popup">
									<i class="iconfont"></i>
									<label>放心消费创建</label>
								</a>
							</div>
						</div>
						
						<div class="weui-cell" style="margin-top: 0.267rem;">
							<div class="weui-cell__bd">
								<a id="industryEarlyWarning" href="javascript:void(0);" 
									class="weui-btn weui-btn_primary cus-btn-red open-popup" data-target="#industrylist-weui-popup">
									<i class="iconfont icon-warning"></i>
									<label>行业消费预警</label>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-flex xiaofeitishi-title">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">12315消费提示</div>
				</div>
			</div>
		</div>
		
		<div class="weui-flex xiaofeitishi">
			<div class="weui-flex__item">
				<div class="row-item">
					<s:iterator value="monthtipsList" var="tips" status="status">
						<s:if test="#status.index % 4 == 0">
							<c:out value="<div class='weui-flex'>" escapeXml="false"></c:out>
						</s:if>
						<div class="weui-flex__item">
							<div class="weui-month-item">
								<a class="weui-month-cilck" href="javascript:void(0);" articleId="${tips.articleId}"
										data-href="./wechatAction!showArticleView">
									<label>${tips.monthNum}月</label>
								</a>
							</div>
						</div>
						<s:if test="#status.index % 4 == 3 || #status.last">
							<c:out value="</div>" escapeXml="false"></c:out>
						</s:if>
					</s:iterator>
					<!-- 
					<div class="weui-flex">
						<div class="weui-flex__item">
							<div class="weui-month-item">
								<a class="weui-month-cilck" href="./wechatAction!showArticleView?columnId=3&month=1">
									<label>1月</label>
								</a>
							</div>
						</div>
						<div class="weui-flex__item">
							<div class="weui-month-item">
								<a class="weui-month-cilck" href="./wechatAction!showArticleView?columnId=3&month=2">
									<label>2月</label>
								</a>
							</div>
						</div>
						<div class="weui-flex__item">
							<div class="weui-month-item">
								<a class="weui-month-cilck" href="./wechatAction!showArticleView?columnId=3&month=3">
									<label>3月</label>
								</a>
							</div>
						</div>
						<div class="weui-flex__item">
							<div class="weui-month-item">
								<a class="weui-month-cilck" href="./wechatAction!showArticleView?columnId=3&month=4">
									<label>4月</label>
								</a>
							</div>
						</div>
					</div>
					 -->
				</div>
			</div>
		</div>
		<div class="weui-flex faguizhishi">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-flex">
						<div class="weui-flex__item">
							<a id="consumerlaw" href="./wechatAction!showArticleListView?articleInfo.columnId=402880e9624c96be01624ca627820006" 
									class="weui-btn weui-btn_primary open-popup">
								<i class="iconfont icon-falvfagui"></i>
								<label>消费法规</label>
							</a>
						</div>
						<div class="weui-flex__item">
							<a id="consumerKnowledge" href="./wechatAction!showArticleListView?articleInfo.columnId=402880e9624c96be01624ca655820007" 
									class="weui-btn weui-btn_primary open-popup">
								<i class="iconfont icon-zhishiku"></i>
								<label>消费知识</label>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="industrylist-weui-popup" class="weui-popup__container">
			<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal">
				<div class="weui-panel">
					<div class="weui-panel__hd">按行业查看消费预警</div>
					<div class="weui-panel__bd">
						<div class="weui-media-box weui-media-box_small-appmsg">
							<div class="weui-cells">
								<%-- <a class="weui-cell weui-cell_access" href="./wechatAction!earlywarning?industryId=123">
									<div class="weui-cell__hd">
										<i class="icon iconfont icon-hangye"></i>
									</div>
									<div class="weui-cell__bd weui-cell_primary">
										<p>食品制造业</p>
									</div>
									<span class="weui-cell__ft"></span> 
								</a> --%>
							</div>
							<div class="weui-loadmore hide">
							  	<i class="weui-loading"></i>
							  	<span class="weui-loadmore__tips">正在加载</span>
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
				debug : eval('${wxConfig.debug}'),
				appId : '${wxConfig.appId}', // 必填，公众号的唯一标识
				timestamp : '${wxConfig.timestamp}', // 必填，生成签名的时间戳
				nonceStr : '${wxConfig.nonceStr}', // 必填，生成签名的随机串
				signature : '${wxConfig.signature}',// 必填，签名
				jsApiList : eval("${wxConfig.jsApiList}"),
				hideMenuArray : eval("${wxConfig.hideMenuArray}")
			};
		</script>
		<script src="../static/js/webui/wechat/wxjsAuthorityVerify.js?v=${version}"></script> --%>
		<script type="text/javascript" src="../static/js/webui/wechat/helper.js?v=${version}"></script>
	</body>
</html>