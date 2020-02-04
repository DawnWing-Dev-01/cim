$(function(){
	// fastclick 解决移动端click事件300ms延迟
	FastClick.attach(document.body);
	
	// 调用微信扫一扫功能
	$('#scanQRCode').click(function() {
		wx.scanQRCode({
	        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
	        needResult : 1,
	        // 可以指定扫二维码还是一维码，默认二者都有
	        scanType: ['qrCode'],
	        desc : 'scanQRCode desc',
	        success : function(res) {
	        	console.log('------------------------------------------');
	            console.log(res);
	            var url = res.resultStr;
	            var errMsg = res.errMsg;
	            if( errMsg != 'scanQRCode:ok' ){
	            	$.toptip('扫码异常, 请重试', 2000, 'warning');
	            }
	            window.location.href = url;
	        },
			cancel: function(res){
				$.toptip('您取消了操作!', 2000, 'warning');
			}
	    });
	});

	// 底部展示经营者列表
	$('#showListBtn').click(function() {
		$('#showListBtn').popup();
	});
	
	// 监听input事件
	$('#dealerName').on('change', function(e){
		var $that = $(this);
		var val = $that.val();
		if( val == '' ){
			console.log($('#dealerId').val());
			$('#dealerId').val('');
		}
	});
	/*$('#dealerName').on('input propertychange', function(e){
		var that = this;
		if (!( e.type === "input" 
				|| e.orignalEvent.propertyName === "value") ) {
	        return;
	    }
		// 触发前先关闭popup层
		$('#showDealerList .close-popup').click();
		var newVal = that.value;
        var data = {
        	keyworks: newVal
    	};
        $.ajax({
        	type: 'post',
        	url: './wechatAction!fuzzyQueryDealerList.action',
        	data: data,
        	dataType: 'json',
        	beforeSend: function(){
        		$(that).blur();
        	}, 
        	success: function(data){
        		if(data.length == 0){
        			$('#showDealerList .close-popup').click();
        			return;
        		}
        		var listhtml = builderDealerList(data);
        		$('#showDealerList .modal-content').html(listhtml);
        		$('#showListBtn').click();
        	}
        });
	});*/
	
	// 点击经营者选中
	$('#showDealerList').on('click', '.dealer-item', function(){
		var $that = $(this);
		var dealerId = $that.attr('data-id');
		var showText = $('label', $that).html();
		if(dealerId != '-1'){
			$('#dealerName').val(showText);
			$('#dealerId').val(dealerId);
			// 点击后直接跳转
			showDealer();
		}
		// 关闭Popup层
		$('#showDealerList .close-popup').click();
	});
	
	// 查询事件
	$('#queryDealer').click(function() {
		showDealer();
	});
});

/**
 * 模糊查找经营者
 */
function fuzzyQueryDealer(){
	// 触发前先关闭popup层
	$('#showDealerList .close-popup').click();
	var newVal = $('#dealerName').val();
    var data = {
    	keyworks: newVal
	};
    $.ajax({
    	type: 'post',
    	url: './wechatAction!fuzzyQueryDealerList.action',
    	data: data,
    	dataType: 'json',
    	beforeSend: function(){
    		$('#dealerName').blur();
    	}, 
    	success: function(data){
    		if(data.length == 0){
    			$('#showDealerList .close-popup').click();
    			return false;
    		}
    		
    		if(data.length == 1){
    			var dealerId = data[0].id;
    			if(dealerId != '-1'){
    				$('#dealerName').val(data[0].name);
        			$('#dealerId').val(dealerId);
        			showDealer();
        			return false;
    			}
    		}
    		
    		var listhtml = builderDealerList(data);
    		$('#showDealerList .modal-content').html(listhtml);
    		$('#showListBtn').click();
    	}
    });
	return false;
}

/**
 * 展示经营者详细信息
 */
function showDealer(){
	var dealerName = $('#dealerName').val();
	if(dealerName == null || dealerName == ''){
		$.alert('请输入需要查询的经营者');
		return;
	}
	$.showLoading();
	var dealerId = $('#dealerId').val();
	if(dealerId == null || dealerId == ''){
		$.hideLoading();
		return fuzzyQueryDealer();
		
		$.toptip('请先选择经营者', 2000, 'warning');
		return;
	}
	$('#dealerId').val('');
	window.location.href = './wechatAction!dealerCreditQuery.action?dealerId='+dealerId;
}

/**
 * 构建经营者列表, 从Ajax异步查询
 * @param data
 * @returns {String}
 */
function builderDealerList( data ){
	var listhtml = '';
	$.each(data, function(index, obj){
		var border = 'border-bottom';
		if( index == (data.length-1) ){
			border = '';
		}
		listhtml += '<div class="weui-flex">';
			listhtml += '<div class="weui-flex__item">';
				listhtml += '<div class="row-item">';
					listhtml += '<div class="dealer-item '+border+'" data-id="'+obj.id+'">';
						listhtml += '<i class="iconfont icon-gongshangdengji"></i>';
						listhtml += '<label>'+obj.name+'</label>';
					listhtml += '</div>';
				listhtml += '</div>';
			listhtml += '</div>';
		listhtml += '</div>';
	});
	return listhtml;
}