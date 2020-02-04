$(function(){
	$('#orgGrid').treegrid({    
	    url: './orgAction!getTreeGrid.action',
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
	    	title: '机构名称',
	    	field: 'name', 
	    	width: 30
	    },{
	    	title: '机构编码',
	    	field: 'code', 
	    	width: 15
	    },{
	    	title: '索引号码',
	    	field: 'indexNum', 
	    	width: 15
	    },{
	    	title: '状态',
	    	field: 'status', 
	    	width: 10,
	    	align: 'center',
	    	formatter: function(value){
				if(value == 1){
					return '正常';
				}
				return '异常';
			}
	    },{
	    	title: '排序',
	    	field: 'sort', 
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
	var checkNode = $('#orgGrid').treegrid('getSelected');
	var win = top.$wdawn.dialog({
		title: '添加机构',
		href: './orgAction!formView.action',
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
					url: './orgAction!saveOrgan.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#orgGrid').treegrid('reload');
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
			if(checkNode != null){
				$('input[name="orgInfo.fatherId"]', win).val(checkNode.id);
				$('#fatherName', win).val(checkNode.name).parents('tr').show();
			}
			
			// 选择图标
			$('.item-icon', win).dawnicon();
		}
	});
}

function modify(){
	var checkNode = $('#orgGrid').treegrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新机构',
		href: './orgAction!formView.action',
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
					url: './orgAction!updateOrgan.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#orgGrid').treegrid('reload');
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
			form.form('load','./orgAction!getDetails?object='+checkNode.id);
		}
	});
	
}

function del(){
	var checkNode = $('#orgGrid').treegrid('getSelected');
	
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
	    		url: './orgAction!deleteOrgan.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false)return;
	    			$('#orgGrid').treegrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 刷新
 */
function refresh(){
	$('#orgGrid').treegrid('reload').treegrid('unselectAll');
}