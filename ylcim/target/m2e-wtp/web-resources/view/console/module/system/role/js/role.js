$(function(){
	/**
	 * 角色列表
	 */
	var roleGrid = $('#roleGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './roleAction!getRolePage',
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
			id:'roleSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#roleSearchbox').searchbox('setValue', null);
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
		},'-',{
			text: '权限',
			iconCls: 'icon-quanxian3',
			handler: authorize
		},'-'],
		columns: [[{
			field: 'name',
			title: '角色名称',
			width: 150
		},{
			field: 'code',
			title: '角色编码',
			align: 'center',
			width: 150
		},{
			field: 'sort',
			title: '排序',
			align: 'center',
			width: 100
		},{
			field: 'status',
			title: '状态',
			align: 'center',
			width: 100,
			formatter: function(value){
				if(value == 1){
					return '正常';
				}
				return '异常';
			}
		},{
			field: 'remark',
			title: '描述',
			width: 300
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	roleGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#roleSearchBar').replaceWith('<input id="roleSearchbox"/>');
	$('#roleSearchbox').searchbox({
		menu : '#roleSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			roleGrid.datagrid('load', filter);
		}
	});
});

function addTo(){
	var win = top.$wdawn.dialog({
		title: '添加角色',
		href: './roleAction!formView.action',
		width: 450,
		height: 310,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var form = win.find('form');
				form.form('submit', {
					url: './roleAction!saveRole',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#roleGrid').datagrid('reload');
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
		}]
	});
}

function modify(){
	var checkNode = $('#roleGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新角色',
		href: './roleAction!formView.action',
		width: 450,
		height: 310,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var form = win.find('form');
				form.form('submit', {
					url: './roleAction!updateRole',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#roleGrid').datagrid('reload');
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
			var form = win.find('form');
			form.form({
				onLoadSuccess: function(obj){
					if(obj.success==false){
						top.$wdawn.showMsg(obj.message);
						win.dialog('close');
						return;
					}
				}
			});
			form.form('load','./roleAction!getDetails?object='+checkNode.id);
		}
	});
}

function del(){
	var checkNode = $('#roleGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	if(checkNode.isInit == 1){
		top.$wdawn.showMsg('禁止删除此记录!');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './roleAction!deleteRole',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false)return;
	    			$('#roleGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

function authorize(){
	var checkNode = $('#roleGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '资源授权',
		href: './roleAction!formView.action?viewType=authorize',
		width: 260,
		height: 500,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var treeChecked = $('#menuTree', win).tree('getChecked', ['checked', 'indeterminate']);
				if( !(treeChecked != null && treeChecked.length > 0) ){
					top.$wdawn.showMsg('请选择需要授权的资源!');
					return;
				}
				
				var menuIds = new Array();
				$.each(treeChecked, function(index, treeNode){
					menuIds.push(treeNode.id);
				});
				
				$.ajax({
		    		type: 'post',
		    		url: './roleMenuAction!saveRoleMenu',
		    		data: {object: checkNode.id, menuIds: menuIds},
		    		dataType: 'json',
		    		success: function(obj){
		    			top.$wdawn.showMsg(obj.message);
		    			if(obj.success==false)return;
		    			win.dialog('close');
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
			$('#menuTree', win).tree({
		        url : './menuAction!getTree',
		        lines : true,
		        animate: true,
		        checkbox: true,
		        onLoadSuccess: function(){
		        	$.ajax({
			    		type: 'post',
			    		url: './roleMenuAction!findMenuIdByRoleId',
			    		data: {object: checkNode.id},
			    		dataType: 'json',
			    		success: function(obj){
			    			$.each(obj, function(index, menuInfo){
			    				if( menuInfo.isLeaf != 1 ){
			    					return;
			    				}
			    				var node = $('#menuTree', win).tree('find', menuInfo.id);
					        	$('#menuTree', win).tree('check', node.target);
			    			});
			    		}
			    	});
		        }
		    });
		}
	});
}