$(function(){
	/**
	 * 数据库备份列表
	 */
	var backupGrid = $('#backupGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './backupAction!getBackupPage',
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
			id:'backupSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#backupSearchbox').searchbox('setValue', null);
			}
		},'-',{
			id: 'backup',
			text: '备份',
			iconCls: 'czs-add',
			handler: backup
		},'-',{
			text: '删除',
			iconCls: 'czs-trash-l',
			handler: del
		},'-'],
		columns: [[{
			field: 'name',
			title: '备份名称',
			width: 150
		},{
			field: 'backupPath',
			title: '备份地址',
			width: 400
		},{
			field: 'remark',
			title: '备注',
			width: 500
		},{
			field: 'id',
			title: '操作',
			width: 100,
			align: 'center',
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="restore(\''+value+'\')">还原</a>';
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	backupGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#backupSearchBar').replaceWith('<input id="backupSearchbox"/>');
	$('#backupSearchbox').searchbox({
		menu : '#backupSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			backupGrid.datagrid('load', filter);
		}
	});
});

function backup(){
	$('#backup').linkbutton('disable');
	$.ajax({
		type: 'post',
		url: './backupAction!backup',
		dataType: 'json',
		beforeSend: function(){
			top.$.messager.progress({
				msg:'数据库备份中....',
				interval: 100
			});
		},
		success: function(obj){
			$('#backup').linkbutton('enable');
			top.$.messager.progress('close');
			top.$wdawn.showMsg(obj.message);
			if(obj.success==false)return;
			$('#backupGrid').datagrid('reload');
		}
	});
}

function restore( backupId ){
	$.ajax({
		type: 'post',
		url: './backupAction!restore',
		data: {object: backupId},
		dataType: 'json',
		beforeSend: function(){
			top.$.messager.progress({
				msg:'数据库还原中....',
				interval: 100
			});
		},
		success: function(obj){
			top.$.messager.progress('close');
			top.$wdawn.showMsg(obj.message);
			if(obj.success==false)return;
			$('#backupGrid').datagrid('reload');
		}
	});
}

function del(){
	var checkNode = $('#backupGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './backupAction!deleteBackup',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false)return;
	    			$('#backupGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}