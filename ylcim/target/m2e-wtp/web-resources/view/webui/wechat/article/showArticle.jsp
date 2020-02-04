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
		<title>${columnInfo.name}</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body class="article-content" ontouchstart>

		<header class="article-header">
			<h2 class="weui-title">${articleInfo.name}</h2>
			<div class="weui-date-from">
	            <em class="weui-date"><s:date name="articleInfo.deliveryDate" format="yyyy-MM-dd"/></em>
	            <span class="weui-from">${articleInfo.articleFrom}</span>
	        </div>
		</header>
		<article class="weui-article">
			<section class="summary">
				<blockquote>
					<strong>
						${articleInfo.summary}
					</strong>
				</blockquote>
			</section>
			<section>
				${articleInfo.content}
			</section>
		</article>
		
		<div class="weui-loadmore weui-loadmore_line">
			<span class="weui-loadmore__tips">End</span>
		</div>
		
		<div class="weui-msg__extra-area" style="position: inherit;">
			<div class="weui-footer">
				<p class="weui-footer__links">
					<a href="javascript:void(0);" class="weui-footer__link">${viewDeftData.SystemName}</a>
				</p>
				<p class="weui-footer__text">${viewDeftData.Copyright}</p>
			</div>
		</div>
		
		<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script src="../static/library/fastclick/fastclick.js?v=${version}"></script>
		
		<script type="text/javascript">
			$(function(){
				// fastclick 解决移动端click事件300ms延迟
				FastClick.attach(document.body);
			});
		</script>
	</body>
</html>