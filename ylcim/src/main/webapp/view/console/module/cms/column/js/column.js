$(function(){
	/**
	 * 栏目信息列表
	 */
	var columnGrid = $('#columnGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './columnAction!getColumnPage.action',
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
			id:'columnSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#columnSearchbox').searchbox('setValue', null);
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
			width: 25
		},{
			field: 'sort',
			title: '排序',
			width: 10,
			align: 'center'
		},{
			field: 'createDate',
			title: '创建日期',
			width: 10,
			align: 'center'
		},{
	    	title: '说明',
	    	field: 'remark', 
	    	width: 80
	    }]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	columnGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#columnSearchBar').replaceWith('<input id="columnSearchbox"/>');
	$('#columnSearchbox').searchbox({
		menu : '#columnSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			columnGrid.datagrid('load', filter);
		}
	});
});

/**
 * 添加
 */
function addTo(){
	var win = top.$wdawn.dialog({
		title: '添加栏目',
		href: './columnAction!formView.action',
		width: 450,
		height: 310,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './columnAction!saveColumn.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#columnGrid').datagrid('reload');
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
	var checkNode = $('#columnGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新栏目',
		href: './columnAction!formView.action',
		width: 450,
		height: 310,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './columnAction!updateColumn.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#columnGrid').datagrid('reload');
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
			form.form('load','./columnAction!getDetails?object='+checkNode.id);
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#columnGrid').datagrid('getSelected');
	
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
	    		url: './columnAction!deleteColumn.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#columnGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}