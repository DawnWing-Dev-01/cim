var baiduMap = null;
$(function(){
	// fastclick 解决移动端click事件300ms延迟
	FastClick.attach(document.body);
	
	// 用户授权
	isAuthorize();
	
	// 创建Map实例
	baiduMap = new BMap.Map('showmap-container');
});

function isAuthorize(){
	if(wechatConfig.needToAuthorize == 1){
		$.confirm('平台可将举报进度实时推送给实名举报的用户，是否继续？', '实名举报？', function() {
			//点击确认后的回调函数
			$('#isRealName').val(1);
			window.location.href = wechatConfig.authorizeCodeUrl;
		}, function() {
			//点击取消后的回调函数
			console.log('user select anonymously...');
			$('#isRealName').val(0);
			$('#wechatOpenId').val('');
		});
	}
}

// 选择图片
$('.weui-photo-item').click(function() {
	var $clickElem = $(this);
	wx.chooseImage({
		count : 1, // 默认9
		// 可以指定是原图还是压缩图，默认二者都有
		sizeType : [ 'original', 'compressed' ],
		// 可以指定来源是相册还是相机，默认二者都有
		sourceType : [ 'album', 'camera' ],
		success : function(res) {
			// 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			var localIds = res.localIds;
			// 因一次只设置选择一张照片, 故取第一张就可以
			var localId = localIds[0];
			// 获取本地图片的base64数据, 浏览选择的图片
			/*wx.getLocalImgData({
				localId : localId, // 图片的localID
				success : function(res) {
					// localData是图片的base64数据，可以用img标签显示
					var localData = res.localData;
					
					$clickElem.removeClass('icon-streak-xiangpian');
					$('img', $clickElem).attr('src', localData).show();
				}
			});*/
			$clickElem.removeClass('icon-streak-xiangpian');
			$('img', $clickElem).attr('src', localId).show();
			
			// 将图片上传到服务器上, 用于后台下载到自己服务器上
			wx.uploadImage({
				localId : localId, // 需要上传的图片的本地ID，由chooseImage接口获得
				isShowProgressTips : 1, // 默认为1，显示进度提示
				success : function(res) {
					var serverId = res.serverId; // 返回图片的服务器端ID
					// 将上传的服务id设置到元素中, 到最后一并传递给后台
					$clickElem.attr('upload-serverId', serverId);
				}
			});
		},
		cancel: function(res){
			$.toptip('您取消了操作!', 2000, 'warning');
		}
	});
});

/**
 * 展示地图
 */
$('#showMap').click(function(){
	baiduMap.clearOverlays();
	wx.getLocation({
		// 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		type : 'gcj02',
		success : function(res) {
			// 微信获取的经纬度转换成百度的经纬度
			var wechatPoint = new BMap.Point(res.longitude, res.latitude);
			var convertor = new BMap.Convertor();
	        var pointArr = [];
	        pointArr.push(wechatPoint);
			convertor.translate(pointArr, 3, 5, function(data) {
				if (data.status === 0) {
					var baiduPoint = data.points[0];
					// 初始化地图,设置中心点坐标和地图级别
					baiduMap.centerAndZoom(baiduPoint, 15);
					var marker = new BMap.Marker(baiduPoint);
					baiduMap.addOverlay(marker);
					// 创建地理编码实例      
					var myGeo = new BMap.Geocoder();
					myGeo.getLocation(baiduPoint, function(result) {
						if (result) {
							$('#showmap-weui-popup .show-address span')
									.html(result.address);
						}
					});
				}
			});
		},
		cancel : function(res) {
			console.log('[js-sdk] Get Location error, res : '+res);
		}
	});
	$("#showMap").popup();
});

/**
 * 在地图上选择当前地址
 */
$('#selectAddressBtn').click(function(){
	var address = $(this).siblings('span').html();
	$('.address input').val(address);
	$.closePopup();
});

/**
 * 提交表单
 */
$('#submit').click(function() {
	var $submit = $(this);
	// 判断按钮是否被点, 防止重复点击按钮
	var isLock = $submit.attr('data-isLock');
	if(isLock == 1){
		return;
	}
	// 给按钮加锁
	$submit.attr('data-isLock', '1');
	
	// 获取页面用户输入的值, 对相应的值做校验
	var dealerName = $('#dealerName').val();
	if(dealerName == null || dealerName == ''){
		$.alert('请输入经营者名称。', function(){
			// 执行完, 释放按钮锁
			$submit.attr('data-isLock', '0');
		});
		return false;
	}
	var dealerAddress = $('#dealerAddress').val();
	if(dealerAddress == null || dealerAddress == ''){
		$.alert('请确定下事件发生位置。', function(){
			// 执行完, 释放按钮锁
			$submit.attr('data-isLock', '0');
		});
		return false;
	}
	var complaintReason = $('#complaintReason').val();
	if(complaintReason == null || complaintReason == ''){
		$.alert('请告诉我们发生了什么事情。', function(){
			// 执行完, 释放按钮锁
			$submit.attr('data-isLock', '0');
		});
		return false;
	}
	var informeriPhone = $('#informeriPhone').val();
	if(informeriPhone == null || informeriPhone == ''){
		$.alert('请告诉我们您的联系方式。', function(){
			// 执行完, 释放按钮锁
			$submit.attr('data-isLock', '0');
		});
		return false;
	}else{
		var regexp = eval('/'+$('#informeriPhone').attr('pattern')+'/');
		if(!regexp.test(informeriPhone)){
			$.alert('请输入正确的手机号码。', function(){
				// 执行完, 释放按钮锁
				$submit.attr('data-isLock', '0');
			});
			return false;
		}
	}
	
	// 建议上传照片
	var uploaditem = $('.uploadphoto .weui-photo-item[upload-serverId]');
	if(uploaditem.length == 0){
		$.alert('为了执法人员方便核查举报内容，建议上传照片', function(){
			// 执行完, 释放按钮锁
			$submit.attr('data-isLock', '0');
		});
		$.closePopup();
		return false;
	}
	// 获取已经上传的相片serverId
	var imageServerIds = new Array();
	$.each(uploaditem, function(index, uploadphoto){
		var upload_serverId = $(uploadphoto).attr('upload-serverId');
		imageServerIds.push(upload_serverId);
	});
	
	// 微信号OpenId
	var wechatOpenId = $('#wechatOpenId').val();
	var formToken = $('#formToken').val();
	
	// 被举报人相关信息
	var dealerId = $('#dealerId').val();
	var dealerLinkman = $('#dealerLinkman').val();
	var dealeriPhone = $('#dealeriPhone').val();
	var dealerJurisdiction = $('#dealerJurisdiction').val();
	
	// 构造参数
	var data = {
			'formToken': formToken,
			'complaintSheet.dealerId': dealerId,
			'complaintSheet.dealerName': dealerName,
			'complaintSheet.dealerAddress': dealerAddress,
			'complaintSheet.dealerLinkman': dealerLinkman,
			'complaintSheet.dealeriPhone': dealeriPhone,
			'complaintSheet.complaintReason': complaintReason,
			'complaintSheet.informeriPhone': informeriPhone,
			'complaintSheet.dealerJurisdiction': dealerJurisdiction,
			'complaintSheet.informerWeChatOpenId': wechatOpenId,
			'complaintSheet.imageServerIds': imageServerIds.join(';')
	};
	// 请求后台保存投诉表单
	$.ajax({
		type: 'post',
		url: './wechatAction!saveComplaintSheet',
		data: data,
		dataType: 'json',
		beforeSend: function(){
			$.showLoading();
		}, 
		success: function(resp){
			// 执行完, 释放按钮锁
			$submit.attr('data-isLock', '0');
			$.hideLoading();
			if(resp.success == false){
				$.alert(resp.message);
				return;
			}
			$("#submit-success").click();
		}
	});
});