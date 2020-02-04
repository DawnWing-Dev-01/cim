$(function(){
	
	/**
	 * 组织机构树
	 */
	var organizationTree = $('#organizationTree').tree({
        url : './orgAction!getTree.action',
        parentField : 'id',
        treeField: 'text',
        lines : true,
        animate: true,
        onClick : function(node) {
            $('#userGrid').datagrid('load', {
                object: node.indexNum
            });
        }
    });
	
	/**
	 * 角色列表
	 */
	var roleGrid = $('#roleGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './roleAction!getRolePage',
		fit: true,
		idField: 'id',
		border: false,
		rownumbers: true,
		fitColumns: true,
		frozenColumns:[[{
			field: 'ck',
			checkbox:true
		}]],
		columns: [[{
			field: 'name',
			title: '角色名称',
			width: 150
		}/*,{
			field: 'code',
			title: '角色编码',
			align: 'center',
			width: 150
		}*/]],
		pagination: false
	});
	
	/**
	 * 用户列表
	 */
	var userGrid = $('#userGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './userAction!getUserPage.action',
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
			id:'userSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#userSearchbox').searchbox('setValue', null);
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
			text : '重置密码',
			iconCls : 'icon-resetpwd',
			handler : resetPwd
		},'-',{
			text : '授权',
			iconCls : 'icon-authorize',
			handler : authorize
		},'-',{
			text : '解除',
			iconCls : 'icon-unauthorize',
			handler : dissolve
		},'-'],
		columns: [[{
			field: 'name',
			title: '姓名',
			width: 25
		},{
			field: 'username',
			title: '登录账号',
			width: 20
		},{
			field: 'birthday',
			title: '生日',
			align: 'center',
			width: 20
		},{
			field: 'gender',
			title: '性别',
			align: 'center',
			width: 15,
			formatter: function(value){
				if(value == 1){
					return '男';
				}
				return '女';
			}
		},{
			field: 'mobile',
			title: '手机号码',
			width: 20
		},{
			field: 'status',
			title: '状态',
			width: 10,
			align: 'center',
			formatter: function(value){
				if(value == 1){
					return '正常';
				}
				return '停用';
			}
		},{
			field: 'createDate',
			title: '开通日期',
			align: 'center',
			width: 20
		},{
	    	title: '说明',
	    	field: 'remark', 
	    	width: 80
	    },{
			field: 'detail',
			title: '操作',
			width: 15,
			align: 'center',
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\')">查看</a>';
			}
		}]],
		pagination: true,
		onLoadSuccess: function(data){
			$('#roleGrid').datagrid('clearSelections');
		},
		onClickRow: function(rowIndex, rowData){
			$.ajax({
	    		type: 'post',
	    		url: './userRoleAction!findRoleIdByUserId',
	    		data: {object: rowData.id},
	    		dataType: 'json',
	    		beforeSend: function(){
	    			$('#roleGrid').datagrid('clearSelections');
	    		}, 
	    		success: function(obj){
	    			$.each(obj, function(index, roleId){
	    				$('#roleGrid').datagrid('selectRecord', roleId);
	    			});
	    		}
	    	});
		}
	});
	userGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#userSearchBar').replaceWith('<input id="userSearchbox"/>');
	$('#userSearchbox').searchbox({
		menu : '#userSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			var checkNode = organizationTree.tree('getSelected');
			if( checkNode != null ){
				filter['object'] = checkNode.indexNum;
			}
			userGrid.datagrid('load', filter);
		}
	});
});

function addTo(){
	var checkNode = $('#organizationTree').tree('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择所属组织机构！');
		return;
	}
	var win = top.$wdawn.dialog({
		title: '添加用户',
		href: './userAction!formView.action',
		width: 600,
		height: 360,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var form = win.find('form');
				form.form('submit', {
					url: './userAction!saveUser',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#userGrid').datagrid('reload');
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
			$('#orgInfo', win).hide();
			$('input[name="userInfo.orgId"]', win).val(checkNode.id);
			
			// 选择微信号
			$('#chooseOpenId', win).click(function(){
				$(this).selectorWeChatOpenId({
					rowClick: function(rowIndex, rowData){
						$('input[name="userInfo.wechatOpenId"]', win).val(rowData.openId);
					}
				});
			});
		}
	});
}

function modify(){
	var checkNode = $('#userGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录！');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新用户',
		href: './userAction!formView.action',
		width: 600,
		height: 360,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var form = win.find('form');
				form.form('submit', {
					url: './userAction!updateUser',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
						$('#userGrid').datagrid('reload');
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
					
					// 更新账号只读
					$('input[name="userInfo.username"]', win).attr('readonly', 'readonly');
					
					// 所属组织机构
					$('input[name="userInfo.orgId"]', win).combotree({    
					    url: './orgAction!getTree.action',
					    required: true,
					    lines: true,
					    checkbox: true,
					    animate: true
					});
					
					// 选择微信号
					$('#chooseOpenId', win).click(function(){
						$(this).selectorWeChatOpenId({
							rowClick: function(rowIndex, rowData){
								$('input[name="userInfo.wechatOpenId"]', win).val(rowData.openId);
							}
						});
					});
				}
			});
			form.form('load','./userAction!getDetails?object='+checkNode.id);
		}
	});
}

function del(){
	var checkNode = $('#userGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录！');
		return;
	}
	
	if(checkNode.isInit == 1){
		top.$wdawn.showMsg('禁止删除此记录！');
		$('#userGrid').datagrid('reload');
		return;
	}
	
	if(checkNode.status == 0){
		top.$wdawn.showMsg('该账号已停用，请勿重复操作！');
		$('#userGrid').datagrid('reload');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './userAction!deleteUser',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false)return;
	    			$('#userGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

function resetPwd(){
	var checkNode = $('#userGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录！');
		return;
	}
	
	$.ajax({
		type: 'post',
		url: './userAction!resetPwd.action',
		data: {object: checkNode.id},
		dataType: 'json',
		success: function(data){
			top.$wdawn.showMsg(data.message);
		}
	});
}

/**
 * 详情
 * @param id
 */
function showDetail(userId){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './userAction!formView.action?viewType=readOnly',
		width: 600,
		height: 360,
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
					
					// 所属组织机构
					$('input[name="userInfo.orgId"]', win).combotree({    
					    url: './orgAction!getTree.action',
					    required: true,
					    lines: true,
					    checkbox: true,
					    animate: true
					});
				}
			});
			form.form('load','./userAction!getDetails?object='+userId);
		}
	});
}

/**
 * 授权角色
 */
function authorize(){
	var checkNode = $('#userGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录！');
		return;
	}
	
	var selecteds = $('#roleGrid').datagrid('getSelections');
	if( !(selecteds != null && selecteds.length > 0) ){
		top.$wdawn.showMsg('请选择需要授权的角色!');
		return;
	}
	
	var roleIds = new Array();
	$.each(selecteds, function(index, roleInfo){
		roleIds.push(roleInfo.id);
	});
	
	$.ajax({
		type: 'post',
		url: './userRoleAction!saveUserRole',
		data: {object: checkNode.id, roleIds: roleIds},
		dataType: 'json',
		success: function(obj){
			top.$wdawn.showMsg(obj.message);
			if(obj.success==false)return;
			$('#userGrid').datagrid('reload');
		}
	});
}

/**
 * 解除角色关系
 */
function dissolve(){
	var checkNode = $('#userGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录！');
		return;
	}
	
	$.ajax({
		type: 'post',
		url: './userRoleAction!deleteUserRole',
		data: {object: checkNode.id},
		dataType: 'json',
		success: function(obj){
			top.$wdawn.showMsg(obj.message);
			if(obj.success==false)return;
			$('#userGrid').datagrid('reload');
		}
	});
}