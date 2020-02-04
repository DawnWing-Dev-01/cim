<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, user-scalable=no, maximum-scale=1, minimum-scale=1">
		<meta name="author" content="w·xL">
		<title>失信行为查询</title>
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
					<div class="weui-flex title-row">
						<div class="weui-flex__item">
							<div class="weui-cells__title">经营者名称：</div>
						</div>
						<div class="weui-flex__item scan-right">
							<div class="weui-cells__title">
								<a id="scanQRCode" class="scanQRCode" href="javascript:void(0);">扫一扫</a>
							</div>
						</div>
					</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border">
								<form method="get" onsubmit="return fuzzyQueryDealer()">
									<input type="text" class="weui-input" id="dealerName" name="dealerInfo.name" autocomplete="off"/>
									<input type="hidden" id="dealerId"/>
								</form>
								<a id="showListBtn" href="javascript:void(0);" class="open-popup hide" data-target="#showDealerList"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-flex query-content">
			<div class="weui-flex__item">
				<div class="row-item">
					<a id="queryDealer" href="javascript:void(0);" class="weui-btn weui-btn_primary open-popup">查询</a>
				</div>
			</div>
		</div>

	<div id="showDealerList" class="weui-popup__container popup-bottom" style="display: none;">
		<div class="weui-popup__overlay"></div>
		<div class="weui-popup__modal" style="height: inherit;">
			<div class="toolbar">
				<div class="toolbar-inner">
					<a href="javascript:void(0);" class="picker-button close-popup">关闭</a>
					<h1 class="title">请选择经营者</h1>
				</div>
			</div>
			<div class="modal-content" style="padding-top: 1.2rem;">
				<!-- <div class="weui-flex">
					<div class="weui-flex__item">
						<div class="row-item">
							<div class="dealer-item">
								<i class="iconfont icon-gongshangdengji"></i>
								<span>杨凌百信商贸有限公司</span>
							</div>
						</div>
					</div>
				</div>
				<div class="weui-flex">
					<div class="weui-flex__item">
						<div class="row-item">
							<div class="dealer-item">
								<i class="iconfont icon-gongshangdengji"></i>
								<label>杨凌特步特约专卖店</label>
							</div>
						</div>
					</div>
				</div> -->
			</div>
		</div>
	</div>

	<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script src="../static/library/fastclick/fastclick.js?v=${version}"></script>
		<script type="text/javascript">
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
		<script src="../static/js/webui/wechat/wxjsAuthorityVerify.js?v=${version}"></script>
		<script src="../static/js/webui/wechat/wechatquery.js?v=${version}"></script>
	</body>
</html>
