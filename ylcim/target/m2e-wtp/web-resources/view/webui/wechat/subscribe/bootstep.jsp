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
		<title>关注引导</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/library/icon-library/caomei/1.2.8/style.css?v=${version}">
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/layui/css/layui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script type="text/javascript" src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart>
		<div class="weui-flex guided-view">
			<div class="weui-flex__item">
				<div class="row-item">
					<p>1、长按二维码关注“杨凌示范区工商行政管理局”公众号；</p>
					<p><img src="../static/images/guide/ylQrCode.jpg" alt="公众号" class="officialAccountsQRcode"/></p>
					<p>2、关注后，进入公众号，点击“消费维权”栏目，然后点击“失信行为查询”菜单；</p>
					<p><img src="../static/images/guide/0.png"/></p>
					<p><img src="../static/images/guide/1.png"/></p>
					<p>3、点击“扫一扫”按钮，打开相机扫描二维码，扫码后即可查询失信行为。</p>
					<p><img src="../static/images/guide/2.png"/></p>
					<p><img src="../static/images/guide/3.png"/></p>
				</div>
			</div>
		</div>
	
		<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script type="text/javascript" src="../static/library/fastclick/fastclick.js?v=${version}"></script>
	</body>
</html>