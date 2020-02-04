// 注入权限验证配置
wx.config({
	debug : wechatConfig.debug,
	appId : wechatConfig.appId, // 必填，公众号的唯一标识
	timestamp : wechatConfig.timestamp, // 必填，生成签名的时间戳
	nonceStr : wechatConfig.nonceStr, // 必填，生成签名的随机串
	signature : wechatConfig.signature,// 必填，签名
	jsApiList : wechatConfig.jsApiList
});

wx.ready(function() {
	// 微信js-sdk初始化成功
	console.log('[WeChat js-sdk] config Successful verification...');
	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，
	// 所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
	// 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

	// 批量隐藏功能按钮接口
	wx.hideMenuItems({
		// 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		menuList : wechatConfig.hideMenuArray
	});

	wx.checkJsApi({
		// 需要检测的JS接口列表，所有JS接口列表见附录2,
		jsApiList : wechatConfig.jsApiList,
		success : function(res) {
			// 以键值对的形式返回，可用的api值true，不可用为false
			console.log(res);
			// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
			/*
			 * if( res.checkJsApi != 'ok' ){
			 *  }
			 */
		}
	});
});

wx.error(function(res) {
	// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，
	// 也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	console.log('wechat js_sdk exception...');
	console.log(res);
});