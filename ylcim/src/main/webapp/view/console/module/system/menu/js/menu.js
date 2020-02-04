$(function(){
	
	/**
	 * 菜单树表格
	 */
	$('#menuGrid').treegrid({    
	    url: './menuAction!getTreeGrid',
	    fit: true,
		border: false,
		rownumbers: true,
		animate: true,
		collapsible: true,
		fitColumns: true,
	    idField: 'id',    
	    treeField: 'name',
	    toolbar: [{
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
			text: '刷新',
			iconCls: 'czs-brush-l',
			handler: refresh
		},'-'],
	    columns:[[{
	    	title: '菜单名称',
	    	field: 'name', 
	    	width: 30
	    },{
	    	title: '链接地址',
	    	field: 'action', 
	    	width: 60
	    },{
	    	title: '类型',
	    	field: 'type', 
	    	width: 20,
	    	align: 'center',
	    	formatter: function(value){
				if(value == 'navigate'){
					return '导航栏目';
				}
				return '操作菜单';
			}
	    },{
	    	title: '排序',
	    	field: 'sort', 
	    	align: 'center',
	    	width: 10
	    },{
	    	title: '状态',
	    	field: 'status', 
	    	align: 'center',
	    	width: 10
	    },{
	    	title: '说明',
	    	field: 'remark', 
	    	width: 80
	    }]]
	});
});

function addTo(){
	var checkNode = $('#menuGrid').treegrid('getSelected');
	var win = top.$wdawn.dialog({
		title: '添加菜单',
		href: './menuAction!formView.action',
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
					url: './menuAction!saveMenu',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#menuGrid').treegrid('reload');
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
			var $menuAction = $('input[name="menuInfo.action"]', win);
			$('input[name="menuInfo.type"]', win).change(function(){
				if($(this).val() == 'operate'){
					$menuAction.validatebox({    
					    required: true,
					    validType:'length[0,100]',
					    tipPosition: 'top'
					});
				}else{
					$menuAction.validatebox({    
					    required: false
					});
				}
			});
			
			if(checkNode != null){
				$('input[name="menuInfo.fatherId"]', win).val(checkNode.id);
				$('#fatherName', win).val(checkNode.name).parents('tr').show();
			}
			
			// 选择图标
			$('.item-icon', win).dawnicon();
		}
	});
}

function modify(){
	var checkNode = $('#menuGrid').treegrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新菜单',
		href: './menuAction!formView.action',
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
					url: './menuAction!updateMenu',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#menuGrid').treegrid('reload');
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
					
					// 选择图标
					$('.item-icon', win).dawnicon();
				}
			});
			form.form('load','./menuAction!getDetails?object='+checkNode.id);
		}
	});
	
}

function del(){
	var checkNode = $('#menuGrid').treegrid('getSelected');
	
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
	    		url: './menuAction!deleteMenu',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false)return;
	    			$('#menuGrid').treegrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 刷新
 */
function refresh(){
	$('#menuGrid').treegrid('reload').treegrid('unselectAll');
}