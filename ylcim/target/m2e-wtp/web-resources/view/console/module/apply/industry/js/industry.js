$(function(){
	/**
	 * 行业信息列表
	 */
	var industryGrid = $('#industryGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './industryAction!getIndustryPage.action',
		fit: true,
		border: false,
		rownumbers: true,
		//fitColumns: true,
		singleSelect: true,
		frozenColumns:[[{
			field: 'ck',
			checkbox:true
		}]],
		toolbar:['-',{
			id:'industrySearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#industrySearchbox').searchbox('setValue', null);
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
			field: 'name',
			title: '名称',
			width: 300
		},{
			field: 'warningThreshold',
			title: '预警阀值',
			align: 'center',
			width: 100
		},{
			field: 'sort',
			title: '排序',
			align: 'center',
			width: 100
		},{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 100,
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\')">查看</a>';
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	industryGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#industrySearchBar').replaceWith('<input id="industrySearchbox"/>');
	$('#industrySearchbox').searchbox({
		menu : '#industrySearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			industryGrid.datagrid('load', filter);
		}
	});
});

/**
 * 添加
 */
function addTo(){
	var win = top.$wdawn.dialog({
		title: '添加行业',
		href: './industryAction!formView.action',
		width: 450,
		height: 260,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './industryAction!saveIndustry.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#industryGrid').datagrid('reload');
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
		}
	});
}

/**
 * 修改
 */
function modify(){
	var checkNode = $('#industryGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新行业',
		href: './industryAction!formView.action',
		width: 450,
		height: 260,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './industryAction!updateIndustry.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#industryGrid').datagrid('reload');
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
				}
			});
			form.form('load','./industryAction!getDetails?object='+checkNode.id);
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#industryGrid').datagrid('getSelected');
	
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
	    		url: './industryAction!deleteIndustry.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#industryGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 详情
 * @param id
 */
function showDetail(industryId){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './industryAction!formView.action?viewType=readOnly',
		width: 450,
		height: 280,
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
				}
			});
			form.form('load','./industryAction!getDetails?object='+industryId);
		}
	});
}