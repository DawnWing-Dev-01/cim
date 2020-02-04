$(function(){
	/**
	 * 简易工作流列表
	 */
	var workFlowGrid = $('#workFlowGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './workFlowAction!getWorkFlowPage.action',
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
			id:'workFlowSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#workFlowSearchbox').searchbox('setValue', null);
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
			field: 'flowNodeCode',
			title: '流节点编码',
			width: 25
		},{
			field: 'flowNodeText',
			title: '节点名称',
			width: 15
		},{
			field: 'handleMode',
			title: 'NextNode<br>处理方式',
			align: 'center',
			width: 15,
			formatter: function(value, data, index){
				switch (value) {
					case 1:
							return '对应角色';
						break;
					case 2:
							return '单一用户';
						break;
					default:
							return '不处理';
						break;
				}
			}
		},{
			field: 'handleSubject',
			title: 'NextNode<br>处理主体',
			align: 'center',
			width: 15
		},{
			field: 'sort',
			title: '排序',
			align: 'center',
			width: 10
		},{
			field: 'createDate',
			title: '创建日期',
			align: 'center',
			width: 10
		},{
	    	title: '说明',
	    	field: 'remark', 
	    	width: 50
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
	workFlowGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#workFlowSearchBar').replaceWith('<input id="workFlowSearchbox"/>');
	$('#workFlowSearchbox').searchbox({
		menu : '#workFlowSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			workFlowGrid.datagrid('load', filter);
		}
	});
});

/**
 * 添加
 */
function addTo(){
	var win = top.$wdawn.dialog({
		title: '添加工作流',
		href: './workFlowAction!formView.action',
		width: 500,
		height: 350,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './workFlowAction!saveWorkFlow.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#workFlowGrid').datagrid('reload');
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
			var $handleSubject = $('input[name="workFlowInfo.handleSubject"]', win);
			var $subjectId = $('input[name="workFlowInfo.subjectId"]', win);
			$('input[name="workFlowInfo.handleMode"]', win).change(function(){
				if($(this).val() != 0){
					$handleSubject.validatebox({    
					    required: true,
					    validType:'length[0,50]',
					    tipPosition: 'top'
					});
				}else{
					$handleSubject.validatebox({    
					    required: false
					});
				}
				$handleSubject.val('').validatebox('validate');
				$subjectId.val('');
			}).change();
			
			// 选择主体添加点击事件
			$('#selectSubject', win).click(function(){
				selectSubject( this, win );
			});
		}
	});
}

/**
 * 修改
 */
function modify(){
	var checkNode = $('#workFlowGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新工作流',
		href: './workFlowAction!formView.action',
		width: 500,
		height: 350,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './workFlowAction!updateWorkFlow.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#workFlowGrid').datagrid('reload');
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
					
					var $handleSubject = $('input[name="workFlowInfo.handleSubject"]', win);
					var $subjectId = $('input[name="workFlowInfo.subjectId"]', win);
					
					$('input[name="workFlowInfo.handleMode"]', win).change(function(){
						if($(this).val() != 0){
							$handleSubject.validatebox({    
							    required: true,
							    validType:'length[0,50]',
							    tipPosition: 'top'
							});
						}else{
							$handleSubject.validatebox({    
							    required: false
							});
						}
						$handleSubject.val('').validatebox('validate');
						$subjectId.val('');
					});
					
					// 选择主体添加点击事件
					$('#selectSubject', win).click(function(){
						selectSubject( this, win );
					});
					
					$('input[name="workFlowInfo.flowNodeCode"]', win).attr('readonly', 'readonly');
				}
			});
			form.form('load','./workFlowAction!getDetails?object='+checkNode.id);
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#workFlowGrid').datagrid('getSelected');
	
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
	    		url: './workFlowAction!deleteWorkFlow.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#workFlowGrid').datagrid('reload');
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
		href: './workFlowAction!formView.action?viewType=readOnly',
		width: 500,
		height: 350,
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
			form.form('load','./workFlowAction!getDetails?object='+industryId);
		}
	});
}

/**
 * 选择主体
 * @param handleMode
 */
function selectSubject( btnObj, fatherWin ){
	var handleMode = $('input[name="workFlowInfo.handleMode"]:checked', fatherWin).val();
	if( handleMode == 1  ){
		$(btnObj).selectorRole({
			//title: '选择角色',
			//href: './roleAction!formView.action?viewType=choose',
			rowClick: function(rowIndex, rowData){
				var handleSubjectObj = $('input[name="workFlowInfo.handleSubject"]', fatherWin);
				handleSubjectObj.val(rowData.name).validatebox('validate');
				$('input[name="workFlowInfo.subjectId"]', fatherWin).val(rowData.id);
			}
		});
	}
	
	if( handleMode == 2  ){
		$(btnObj).selectorUser({
			//title: '选择用户',
			//href: './userAction!formView.action?viewType=choose',
			showOrgTree: true,
			rowClick: function(rowIndex, rowData){
				var handleSubjectObj = $('input[name="workFlowInfo.handleSubject"]', fatherWin);
				handleSubjectObj.val(rowData.name).validatebox('validate');
				$('input[name="workFlowInfo.subjectId"]', fatherWin).val(rowData.id);
			}
		});
	}
};