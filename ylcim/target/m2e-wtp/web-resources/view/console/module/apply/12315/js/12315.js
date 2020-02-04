$(function(){
	
	// 显示月份卡片
	showMonthCard();
	
});

/**
 * 显示月份卡片
 */
function showMonthCard(){
	$.ajax({
		type: 'post',
		url: './consumerTipsAction!getConsumerTipsPage.action',
		dataType: 'json',
		success: function( result ){
			var itemhtml = '';
			$.each(result, function(index, tips){
				var articleId = tips.articleId != undefined ? tips.articleId : '-1';
				itemhtml += '<div class="month-item" month="'+tips.monthNum+'" tipsId="'+tips.id+'" articleId="'+articleId+'">';
					itemhtml += '<label>'+tips.monthTxt+'</label>';
					itemhtml += '<div class="opt-area">';
						itemhtml += '<a class="fabu" href="javascript:void(0);" onclick="fabuTips(this)">';
							itemhtml += '<i class="icon czs-telegram"></i>';
							itemhtml += '<label>发布</label>';
						itemhtml += '</a>';
						itemhtml += '<span>&nbsp;/&nbsp;<span>';
						itemhtml += '<a class="fabu" href="javascript:void(0);" onclick="yulanTips(this)">';
							itemhtml += '<i class="icon czs-apple"></i>';
							itemhtml += '<label>预览</label>';
						itemhtml += '</a>';
					itemhtml += '</div>';
				itemhtml += '</div>';
			});
			$('.month-container').html(itemhtml);
		}
	});
};

/**
 * 发布消费提示
 */
function fabuTips(elem){
	var $monthItem = $(elem).parents('.month-item');
	var tipsId = $monthItem.attr('tipsId');
	var month = $monthItem.attr('month');
	var articleId = $monthItem.attr('articleId');
	
	var win = top.$wdawn.dialog({
		title: '12315消费提示',
		href: './articleAction!formView.action',
		width: 1000,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './consumerTipsAction!addConsumerTips.action?object='+tipsId,
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						showMonthCard();
					}
				});
			}
		},{
			text: '&nbsp;取消&nbsp;',
			plain: true,
			iconCls: 'icon-close',
			handler: function(){
				win.dialog('close');
			}
		}],
		onLoad: function(){
			var deliveryDate = $('input[name="articleInfo.deliveryDate"]', win).datebox({    
				required:true
			});
			
			if(articleId != null && articleId != '-1'){
				var form = $('form', win);
				form.form({
					onLoadSuccess: function(obj){
						// 初始化编辑器
						var editor = top.$wdawn.kindEditor({
							selector: $('textarea[name="articleInfo.content"]', win)
						});
					}
				});
				form.form('load','./articleAction!getDetails?object='+articleId);
			}else{
				// 设置栏目信息
				$('input[name="articleInfo.columnId"]', win).val(columnId);
				
				// 初始化编辑器
				var editor = top.$wdawn.kindEditor({
					selector: $('textarea[name="articleInfo.content"]', win)
				});
				
				// 显示当前日期
				deliveryDate.datebox('setValue', new Date().format('yyyy-MM-dd'));
				
				// 消费提示标题
				var tipsName = '【'+new Date().format('yyyy年')+month+'月'+'】'+'消费提示';
				$('input[name="articleInfo.name"]', win).val(tipsName).validatebox('isValid');
			}
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="articleInfo.content"]', win)
			});
		}
	});
}

/**
 * 消费提示预览
 */
function yulanTips(elem){
	var $monthItem = $(elem).parents('.month-item');
	var articleId = $monthItem.attr('articleId');
	if( articleId == '-1' || articleId == '' ){
		$wdawn.showMsg('未发布消费提示!');
		return;
	}
	
	top.layer.open({
		type : 2,
		title : false,
		content : './articleAction!formView.action?viewType=iphone',
		shadeClose : true,
		closeBtn: 0,
		shade : 0.5,
		area : [ '310px', '658px' ],
		success : function(layero, index) {
			var showArticle = './wechat/wechatAction!showArticleView?columnId='+columnId+'&articleId='+articleId;
			var $iframe = $('#layui-layer-iframe'+index, layero);
			$('#showIphone', $iframe.contents()).attr('src', showArticle);
		}
	}); 
}