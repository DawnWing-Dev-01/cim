$(function(){
	if(eval(showCaptcha)){
		// 请求验证码的Action
		var url = './captcha.jpg';
		$('#verifiedCode').attr('src', unCache(url));
		
		// 刷新验证码的实现
		$('#verifiedCode').click(function(){
			$(this).attr('src', unCache(url));
		});
	}
	
	// 禁用缓存, 给url追加时间戳
	function unCache(url){
		url += '?v=';
		url += new Date().getTime();
		return url;
	}
}); 

function beforeLogin(){
	var username = $('#username').val();
	if(username == '' || username == null){
		showError( '请输入账号!', $('#username') );
		return false;
	}
	
	var password = $('#pwd').val();
	if(password == '' || password == null){
		showError( '请输入密码!', $('#pwd') );
		return false;
	}
	
	if(eval(showCaptcha)){
		var verifiedCode = $('#verifiedCodeVal').val();
		if(verifiedCode == '' || verifiedCode == null){
			showError( '请输入验证码!', $('#verifiedCodeVal') );
			return false;
		}
	}
}

/**
 * 显示错误提示
 * @param errmsg
 */
function showError( errmsg, obj ){
	$(obj).css({
		'border-color':'red'
	});
	layer.tips(errmsg, obj, {
		end: function(){
			$(obj).css({
				'border-color':'#cdcdcd'
			}).focus();
		}
	});
}