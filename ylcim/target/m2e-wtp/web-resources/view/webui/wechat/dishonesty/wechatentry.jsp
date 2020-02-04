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
		<title>失信行为举报</title>
		<link rel="stylesheet" href="../static/library/icon-library/iconfont/iconfont.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/lib/weui.min.css?v=${version}">
		<link rel="stylesheet" href="../static/library/jQuery-WeUI/1.2.0/css/jquery-weui.css?v=${version}">
		<link rel="stylesheet" href="../static/style/webui/wechat/wechatstyle.css?v=${version}">
		<script src="../static/library/flexible/2.0/index.js?v=${version}"></script>
	</head>

	<body ontouchstart>
		<input id="dealerId" name="dealeriPhone" type="hidden" value="${viewDeftData.dealerInfo.id}">
		<input id="dealerLinkman" name="dealerLinkman" type="hidden" value="${viewDeftData.dealerInfo.legalPerson}">
		<input id="dealeriPhone" name="dealeriPhone" type="hidden" value="${viewDeftData.dealerInfo.linkTel}">
		<input id="dealerJurisdiction" name="dealerJurisdiction" type="hidden" value="${viewDeftData.jurisdiction}">
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">经营者名称：</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border">
								<input id="dealerName" name="dealerName" type="text" class="weui-input" 
									value="${viewDeftData.dealerInfo.name}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-flex address">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">事件发生位置：</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border">
								<input id="dealerAddress" name="dealerAddress" type="text" class="weui-input" 
									value="${viewDeftData.dealerInfo.dealerAddress}"/>
								<a id="showMap" href="javascript:void(0);" class="open-popup" 
										data-target="#showmap-weui-popup">
									<i class="iconfont icon-ditu"></i>
								</a>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">事件简要描述：</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border shortdesc">
								<textarea id="complaintReason" name="complaintReason" class="weui-textarea" rows="4"></textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-flex uploadphoto">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">上传相片：</div>
					<div class="weui-flex">
						<div class="weui-flex__item">
							<div class="weui-photo-item icon-streak-xiangpian">
								<img alt="相片" />
							</div>
						</div>
						<div class="weui-flex__item">
							<div class="weui-photo-item icon-streak-xiangpian">
								<img alt="相片" />
							</div>
						</div>
						<div class="weui-flex__item">
							<div class="weui-photo-item icon-streak-xiangpian">
								<img alt="相片" />
							</div>
						</div>
						<div class="weui-flex__item">
							<div class="weui-photo-item icon-streak-xiangpian">
								<img alt="相片" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<div class="weui-cells__title">联系电话：</div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__bd text-border">
								<input id="informeriPhone" name="informeriPhone" type="number" class="weui-input" 
									pattern="^1[3-9]\d{9}$" max="11" placeholder="请输入您的手机号码" required />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="row-item">
					<a id="submit" href="javascript:void(0);" class="weui-btn weui-btn_primary" data-isLock="0">提交</a>
					<a id="submit-success" href="javascript:void(0);" class="open-popup"
						data-target="#success-weui-popup"></a>
				</div>
			</div>
		</div>
	
		<div id="success-weui-popup" class="weui-popup__container">
			<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal">
				<div class="weui-msg">
					<div class="weui-msg__icon-area">
						<i class="weui-icon-success weui-icon_msg"></i>
					</div>
					<div class="weui-msg__text-area">
						<h2 class="weui-msg__title">操作成功</h2>
						<p class="weui-msg__desc">举报内容已推送给经营者信用公示平台，正在确认中，请耐心等待。<br>谢谢~</p>
						<!-- <p class="weui-msg__desc" style="color: red;">小提示：长按二维码可查询进度呦</p> -->
					</div>
					<div class="weui-msg__opr-area">
						<!-- <img alt="" src="../static/images/testQRcode.jpg" style="width: 50%; margin: 5px auto;"> -->
						<p class="weui-btn-area">
							<!-- <a href="javascript:;" class="weui-btn weui-btn_primary">查看二维码</a> -->
							<!-- <a href="javascript:;" class="weui-btn weui-btn_default">辅助操作</a> -->
						</p>
					</div>
	
	
	
					<div class="weui-msg__extra-area">
						<div class="weui-footer">
							<p class="weui-footer__links">
								<a href="javascript:void(0);" class="weui-footer__link">经营者信用公示平台</a>
							</p>
							<p class="weui-footer__text">Copyright © 2018-2019 y.sxweb.top</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="showmap-weui-popup" class="weui-popup__container">
			<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal">
				<div id="showmap-container"></div>
				<div class="">
				</div>
				<div class="show-address">
					<i class="iconfont icon-renyuandingwei"></i>
					<label>当前位置：</label>
					<br>
					<span></span>
					<br>
					<a id="selectAddressBtn" href="javascript:void(0);" class="weui-btn weui-btn_primary select-address">
						<i class="iconfont icon-complete"></i>
						<label>选择</label>
					</a>
				</div>
			</div>
		</div>
		
		<!-- 隐藏域 -->
		<!-- 实名举报微信号(openId) -->
		<input id="wechatOpenId" name="wechatOpenId" type="hidden" value="${authorizeConfig.wxUserInfo.openId}">
		<input id="formToken" name="formToken" type="hidden" value="${formToken}">
	
		<!-- body 最后 js Area-->
		<script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js?v=${version}"></script>
		<script src="../static/library/jQuery-WeUI/1.2.0/js/jquery-weui.min.js?v=${version}"></script>
	
		<!-- 调用js-sdk 网页开发工具包 -->
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${version}"></script>
	
		<script src="../static/library/fastclick/fastclick.js?v=${version}"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${wxConfig.baiduMapKey}"></script>
		<script type="text/javascript">
			//$.showLoading();
			var wechatConfig = {
				debug : eval('${wxConfig.debug}'),
				appId : '${wxConfig.appId}', // 必填，公众号的唯一标识
				timestamp : '${wxConfig.timestamp}', // 必填，生成签名的时间戳
				nonceStr : '${wxConfig.nonceStr}', // 必填，生成签名的随机串
				signature : '${wxConfig.signature}',// 必填，签名
				jsApiList : eval("${wxConfig.jsApiList}"),
				hideMenuArray : eval("${wxConfig.hideMenuArray}"),
				// 是否需要用户确认授权: 1-需要, 2不需要;
				needToAuthorize : '${authorizeConfig.needToAuthorize}',
				// 要用户确认授权并获取code的url
				authorizeCodeUrl : '${authorizeConfig.authorizeCodeUrl}'
			};
		</script>
		<script src="../static/js/webui/wechat/wxjsAuthorityVerify.js?v=${version}"></script>
		<script src="../static/js/webui/wechat/wechatentry.js?v=${version}"></script>
	</body>
</html>
