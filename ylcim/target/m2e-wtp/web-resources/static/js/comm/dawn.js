/**
 * JQuery 扩展
 */
var $wdawn = $.extend({

}, $wdawn);

/**
 * @requires jQuery
 * 
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
	type : 'POST',
	cache : false,
	traditional : true,
	complete : function(XMLHttpRequest, textStatus) {
		// ajax session超时处理:通过XMLHttpRequest取得响应头,shiro-status
		var shiro_status = XMLHttpRequest.getResponseHeader("shiro-status");
		if (shiro_status == '401') {
			var basePath = $wdawn.getProjectPath();
			top.location.href = basePath + "/loginAction!index";
			return;
		}
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		try {
			top.$.messager.progress('close');
			top.$.messager.alert('错误', XMLHttpRequest.responseText);
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
	}
});

/**
 * 获得工程路径
 */
$wdawn.getProjectPath = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName + '/');
};

/**
 * 替换主题
 */
$wdawn.theme = function(theme) {
	var links = $('link');
	for ( var cl = 0; cl < links.length; cl++) {
		var url = $(links[cl]).attr('href');
		if (url.indexOf('themes/') != -1) {
			var href = url.substring(0, url.indexOf('themes/')) + 'themes/';
			var tail = url.substring(url.indexOf('themes/') + 7, url.length);
			href = href + theme
					+ tail.substring(tail.indexOf('/'), tail.length);
			$(links[cl]).attr('href', href);
		}
	}
	var iframe = $('iframe');
	iframeTheme(iframe);

	/**
	 * 递归处理iframe主题
	 */
	function iframeTheme(obj) {
		for ( var ci = 0; ci < obj.length; ci++) {
			var links = $(obj[ci]).contents().find('link');
			for ( var ccl = 0; ccl < links.length; ccl++) {
				var url = $(links[ccl]).attr('href');
				if (url.indexOf('themes/') != -1) {
					var href = url.substring(0, url.indexOf('themes/'))
							+ 'themes/';
					var tail = url.substring(url.indexOf('themes/') + 7,
							url.length);
					href = href + theme
							+ tail.substring(tail.indexOf('/'), tail.length);
					$(links[ccl]).attr('href', href);
				}
			}
			var children = $(obj[ci]).contents().find('iframe');
			iframeTheme(children);
		}
	}
};

/**
 * UUID
 * 
 * @return
 */
$wdawn.uuid = function() {
	var S4 = function() {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	};
	return (S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4());
};

/**
 * EasyUI 扩展 进度条提示信息
 */
$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候....';

/**
 * EasyUI 扩展 加载数据失败时关闭进度条并提示异常信息
 */
$.fn.datagrid.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
};

/**
 * EasyUI 扩展 panel关闭时处理,释放资源
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	if (frame.length > 0) {
		// frame[0].contentWindow.document.write('');
		frame[0].contentWindow.close();
		frame.remove();
		/*
		 * if ($.browser.msie) { CollectGarbage(); }
		 */
	}
};
/**
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox
 */
$.extend($.fn.validatebox.defaults.rules, {
	equals : {
		// 添加验证两次密码功能
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '两次密码不一致！'
	},
	phoneNum: {
		//验证手机号 
		validator: function(value, param){
			var regular = /^1[3-8]+\d{9}$/;
			return regular.test(value);
        },
        message: '请输入正确的手机号码。'
    },
    telNum:{
    	//既验证手机号，又验证座机号
    	validator: function(value, param){
    		var regular = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3\d3)|(\d{3}\-))?(1[3456789]\d{9})$)/;
    		return regular.test(value);
        },
        message: '请输入正确的电话号码。'
    }
});

/**
 * 删除进度条
 */
/*$.parser.onComplete = function(){
	setTimeout(function(){
		if(self==top){
			$('#loading').remove();
		}else{
			top.$.messager.progress('close');
		}
	}, 500);
};*/

/**
 * dialog弹出框
 */
$wdawn.dialog = function(options) {

	if (options.dialogType == 'iframe') {
		var num = new Date().getTime();
		options.content = '<iframe id="iframeDialog" src="'
				+ options.link
				+ '" width="100%" height="100%" frameborder="0" scrolling="auto">';
		// options.modal = false;
	}

	var _options = $.extend({
		modal : true,
		iconCls : 'czs-chrome',
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);

	return $('<div/>').dialog(_options);
};

$wdawn.showMsg = function(message) {
	$.messager.show({
		title : '提示信息',
		msg : message,
		timeout : 1000,
		iconCls : 'czs-volume-l',
		showType : 'slide',
		style : {
			left : '50%',
			top : '50%',
			'margin-left' : '-125px',
			'margin-top' : '-50px'
		}
	});
	
	var buildMsgInfo = function(){
		var msg = '<div class="message-info">'
		 		 	+'<div class="message-icon success"><i class="iconfont icon-chenggong"></i></div>'
		 		 	+'<div class="message-text">' + message || "消息内容！" + '</div>'
		 		 +'</div>';
		return msg;
	};
};

$wdawn.confirm = function(options) {
	var defaults = {
		message : '',
		title : '操作确认',
		yCallback : null
	};
	// 设置参数
	var settings = $.extend(defaults, options);
	$.messager.confirm(settings.title, settings.message, function(yn) {
		if (yn) {
			if (typeof settings.yCallback === 'function') {
				settings.yCallback.apply();
			}
		}
	});
};

/**
 * 创建KindEditor在线HTML编辑器
 * @param options selector 选择器/jQ Obj
 */
$wdawn.kindEditor = function(options){
	var defaults = {
			selector : ''
	};
	var settings = $.extend(defaults, options);
	
	var editor = KindEditor.create(settings.selector, {
		resizeType : 0,  //0不允许编辑,1高度改变,2可改变大小
		wellFormatMode :true, //初始化美化HTML
		uploadJson : './attachAction!uploadAttach4Editor.action',
        fileManagerJson : './attachAction!showAttachList.action',
        allowFileManager : true,
        /*items : [
            'source', '|', 'undo', 'redo', '|', 
            'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', '|',  
            'insertorderedlist', 'insertunorderedlist', '|',   
            'indent', 'outdent', '|',   
            'clearhtml', 'quickformat', 'selectall', '|',
            'bold', 'italic', 'underline', 'strikethrough', '|', 'hr'
        ],*/  //工具组
        //编辑器创建成功执行函数
        afterCreate: function(){
        	//同步数据
        	this.sync();
        },
        //当失去焦点时执行 同步数据this.sync();
        afterBlur: function(){
        	//将编辑器的数据同步至文本域里
        	this.sync();
        }
	});
	return editor;
};

/**
 * 关闭编辑器
 */
$wdawn.closeEditor = function(options){
	var defaults = {
			selector : ''
	};
	var settings = $.extend(defaults, options);
	
	KindEditor.remove(settings.selector);
};

/**
 * @requires jQuery
 * 
 * 防止退格键导致页面回退
 */
$(document).keydown(function (e) { 
    var doPrevent; 
    if (e.keyCode == 8) { 
        var d = e.srcElement || e.target; 
        if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') { 
            doPrevent = d.readOnly || d.disabled; 
        }else{
            doPrevent = true; 
        }
    }else{ 
        doPrevent = false; 
    }
    if (doPrevent) 
    e.preventDefault(); 
});

/**
 * 屏蔽JS错误
 */
/*
 * window.onerror = function(){ return true; };
 */