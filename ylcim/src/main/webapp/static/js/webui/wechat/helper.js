// 消费助手视图Js代码
$(function(){
	// fastclick 解决移动端click事件300ms延迟
	FastClick.attach(document.body);
	
	initIndustryList();
	
	$('#industryEarlyWarning').click(function() {
		$("#industryEarlyWarning").popup();
	});
	
	// 12315消费提示点击月份
	$('.weui-month-cilck').click(function(){
		var that = $(this);
		var articleId = that.attr('articleId');
		if( articleId == null || articleId == '' ){
			$.alert('暂未发布消费提示。');
			return;
		}
		var href = that.attr('data-href');
		window.location.href = href + '?articleId=' + articleId;
	});
});

/**
 * 初始化行业列表
 */
function initIndustryList(){
	$.ajax({
		type: 'get',
		url: './wechatAction!industrylistView',
		dataType: 'json',
		beforeSend: function(){
			$('#industrylist-weui-popup .weui-loadmore').removeClass('hide');
		},
		success: function(data){
			$('#industrylist-weui-popup .weui-loadmore').addClass('hide');
			var cellhtml = ''; 
			$.each(data, function(index, industry){
				cellhtml += '<a class="weui-cell weui-cell_access" href="./wechatAction!earlywarning?industryId='+industry.id+'">';
					cellhtml += '<div class="weui-cell__hd">';
						cellhtml += '<i class="icon iconfont icon-hangye"></i>';
					cellhtml += '</div>';
					cellhtml += '<div class="weui-cell__bd weui-cell_primary">';
						cellhtml += '<p>'+industry.name+'</p>';
					cellhtml += '</div>';
					cellhtml += '<span class="weui-cell__ft"></span> ';
				cellhtml += '</>';
			});
			// 没有行业信息的提示
			if( cellhtml == '' ){
				cellhtml += '<a class="weui-cell weui-cell_access" href="javascript:void(0);';
					cellhtml += '<div class="weui-cell__hd">';
						cellhtml += '<i class="icon iconfont icon-hangye"></i>';
					cellhtml += '</div>';
					cellhtml += '<div class="weui-cell__bd weui-cell_primary">';
						cellhtml += '<p>暂未维护行业信息</p>';
					cellhtml += '</div>';
					cellhtml += '<span class="weui-cell__ft"></span> ';
				cellhtml += '</>';
			}
			var $cells = $('#industrylist-weui-popup').find('.weui-cells');
			$cells.html(cellhtml);
		}
	});
}