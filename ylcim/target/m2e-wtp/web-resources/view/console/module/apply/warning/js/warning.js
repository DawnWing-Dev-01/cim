$(function(){
	
	/**
	 * 行业信息列表
	 */
	var industryGrid = $('#industryGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './industryAction!getIndustryPage.action',
		title: '行业列表',
		fit: true,
		border: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		columns: [[{
			field: 'name',
			title: '名称',
			width: 20
		},{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="yulanArticle(\''+data.id+'\')">预览</a>';
			}
		}]],
		pagination: true,
		onLoadSuccess: function(rowData){
			$('#warningGrid').datagrid('reload');
		},
		onClickRow: function(rowIndex, rowData){
			$('#warningGrid').datagrid('load', {
                'warningInfo.industryId': rowData.id
            });
		}
	});
	industryGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	/**
	 * 消费预警列表
	 */
	var warningGrid = $('#warningGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './consumerWarningAction!getConsumerWarningPage.action',
		fit: true,
		border: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		frozenColumns:[[{
			field: 'ck',
			checkbox:true
		}]],
		toolbar:['-',{
			id:'warningSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#warningSearchbox').searchbox('setValue', null);
			}
		},'-',{
			text: '添加',
			iconCls: 'czs-add',
			handler: addTo
		},'-',{
			text: '修改',
			iconCls: 'czs-pen-write',
			handler: modify
		},'-',{
			text: '删除',
			iconCls: 'czs-trash-l',
			handler: del
		},'-'],
		columns: [[{
			field: 'ewTitle',
			title: '预警标题',
			width: 25
		},{
			field: 'ewDate',
			title: '预警时间',
			align: 'center',
			width: 10
		},{
			field: 'ewAuthor',
			title: '发布单位',
			align: 'center',
			width: 15
		},{
			field: 'starShowDate',
			title: '显示开始时间',
			align: 'center',
			width: 10
		},{
			field: 'endShowDate',
			title: '显示结束时间',
			align: 'center',
			width: 10
		},{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\')">查看</a>';
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	warningGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#warningSearchBar').replaceWith('<input id="warningSearchbox"/>');
	$('#warningSearchbox').searchbox({
		menu : '#warningSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			var checkNode = $('#industryGrid').datagrid('getSelected');
			if( checkNode != null ){
				filter['warningInfo.industryId'] = checkNode.id;
			}
			warningGrid.datagrid('load', filter);
		}
	});
});

/**
 * 添加
 */
function addTo(){
	var checkNode = $('#industryGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择行业信息!');
		return;
	}
	
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
			
			$('input[name="warningInfo.industryId"]', win).val(checkNode.id);
			
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

/**
 * 修改
 */
function modify(){
	var checkNode = $('#warningGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新预警',
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
					url: './consumerWarningAction!updateConsumerWarning.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#warningGrid').datagrid('reload');
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
			var form = $('form', win);
			form.form({
				onLoadSuccess: function(obj){
					if(obj.success==false){
						top.$wdawn.showMsg(obj.message);
						win.dialog('close');
						return;
					}
					
					// 初始化编辑器
					var kindEditor = top.$wdawn.kindEditor({
						selector: $('textarea[name="warningInfo.ewContent"]', win)
					});
					
					var ewDate = $('input[name="warningInfo.ewDate"]', win).datebox({    
						required:true
					});
				}
			});
			form.form('load','./consumerWarningAction!getDetails?object='+checkNode.id);
		},
		onBeforeClose: function(){
			/*top.$wdawn.closeEditor({
				selector: $('textarea[name="warningInfo.ewContent"]', win)
			});*/
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#warningGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	if(checkNode.isInit == 1){
		top.$wdawn.showMsg('为保证系统安全, 此记录不可删除!');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './consumerWarningAction!deleteConsumerWarning.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#warningGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 详情
 * @param id
 */
function showDetail( warningId ){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './consumerWarningAction!formView.action?viewType=readOnly',
		width: 1000,
		height: 560,
		collapsible: true,
		buttons:[{
			text: '&nbsp;取消&nbsp;',
			plain: true,
			iconCls: 'icon-close',
			handler: function(){
				win.dialog('close');
			}
		}],
		onLoad: function(){
			var form = $('form', win);
			form.form({
				onLoadSuccess: function(obj){
					if(obj.success==false){
						top.$wdawn.showMsg(obj.message);
						win.dialog('close');
						return;
					}
					
					// 初始化编辑器
					var kindEditor = top.$wdawn.kindEditor({
						selector: $('textarea[name="warningInfo.ewContent"]', win)
					});
					// 开启只读模式
					kindEditor.readonly(true);
				}
			});
			form.form('load','./consumerWarningAction!getDetails?object='+warningId);
		}
	});
}

/**
 * 消费预警预览
 */
function yulanArticle( industryId ){
	top.layer.open({
		type : 2,
		title : false,
		content : './articleAction!formView.action?viewType=iphone',
		shadeClose : true,
		closeBtn: 0,
		shade : 0.5,
		area : [ '310px', '658px' ],
		success : function(layero, index) {
			var showArticle = './wechat/wechatAction!earlywarning?industryId='+industryId;
			var $iframe = $('#layui-layer-iframe'+index, layero);
			$('#showIphone', $iframe.contents()).attr('src', showArticle);
		}
	}); 
}