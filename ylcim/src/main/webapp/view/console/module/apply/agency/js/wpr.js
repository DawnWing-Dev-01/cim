$(function(){
	var warningPublishGrid = $('#warningPublishGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './consumerWarningAction!warningPublishRemind.action',
		fit: true,
		border: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		columns: [[{
			field: 'name',
			title: '提示内容',
			width: 35
		},{
			field: 'industryName',
			title: '行业',
			align: 'center',
			width: 15
		},{
			field: 'threshold',
			title: '预警阀值',
			align: 'center',
			width: 10
		},{
			field: 'complaintTotal',
			title: '月有效投诉',
			align: 'center',
			width: 10
		},{
			field: 'yearNum',
			title: '年份',
			align: 'center',
			width: 10
		},{
			field: 'monthNum',
			title: '月份',
			align: 'center',
			width: 10
		},{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 15,
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="warningPublish(\''+data.id+'\',\''+data.industryId+'\');">【发布】</a>'
				+' / <a href="javascript: void(0);" onclick="neglectRemind(\''+data.id+'\');">【忽略】</a>';
			}
		}]],
		pagination: true
	});
	warningPublishGrid.datagrid('getPager').pagination({
		showPageList: false
	});
});

/**
 * 忽略提示
 * @param wprId
 */
function neglectRemind( wprId ){
	$.ajax({
		url: './consumerWarningAction!updateWprShowType.action',
		data: {
			'wprInfo.id': wprId,
			'wprInfo.showType': 0
		},
		success: function(){
			$('#warningPublishGrid').datagrid('reload');
		}
	});
}

/**
 * 发布预警
 */
function warningPublish( wprId, industryId ){
	var win = top.$wdawn.dialog({
		title: '发布预警',
		href: './consumerWarningAction!formView.action',
		width: 1000,
		height: 560,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './consumerWarningAction!saveConsumerWarning.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#warningGrid').datagrid('reload');
						
						// 发布成功的话, 将预警发布提示隐藏
						neglectRemind(wprId);
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
			// 加载成功执行的操作...
			// 初始化编辑器
			var kindEditor = top.$wdawn.kindEditor({
				selector: $('textarea[name="warningInfo.ewContent"]', win)
			});
			
			$('input[name="warningInfo.industryId"]', win).val(industryId);
			
			var ewDate = $('input[name="warningInfo.ewDate"]', win).datebox({    
				required:true
			});
			// 显示当前日期
			ewDate.datebox('setValue', new Date().format('yyyy-MM-dd'));
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="warningInfo.ewContent"]', win)
			});
		}
	});
}