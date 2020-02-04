$(function(){
	/**
	 * 来源类型列表
	 */
	var originTypeGrid = $('#originTypeGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './originTypeAction!getOriginTypePage.action',
		fit: true,
		border: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		columns: [[{
			field: 'scKey',
			title: '类型Key',
			width: 10
		},{
			field: 'scVal',
			title: '类型Value',
			width: 10
		}]],
		onClickRow: function(rowIndex, rowData){
			$('#resultGrid').datagrid('load', {
                'complaintResult.originTypeId': rowData.id
            });
			
			if(rowData.id != 'CST-08'){
				$('#addbtn').linkbutton('enable');
				$('#delbtn').linkbutton('enable');
				$('#importbtn').linkbutton('enable');
				$('#linkedbtn').linkbutton('enable');
			}else{
				$('#addbtn').linkbutton('disable');
				$('#delbtn').linkbutton('disable');
				$('#importbtn').linkbutton('disable');
				$('#linkedbtn').linkbutton('disable');
			}
		}
	});
	
	var resultGrid = $('#resultGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './complaintResultAction!getComplaintResultPage.action',
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
			id:'resultSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#resultSearchbox').searchbox('setValue', null);
			}
		},'-',{
			id: 'addbtn',
			text: '添加',
			iconCls: 'czs-add',
			disabled: true,
			handler: addTo
		},'-',{
			id: 'modifybtn',
			text: '修改',
			iconCls: 'czs-pen-write',
			handler: modify
		},'-',{
			id: 'delbtn',
			text: '删除',
			iconCls: 'czs-trash-l',
			disabled: true,
			handler: del
		},'-',{
			id: 'importbtn',
			text: '导入',
			iconCls: 'icon-excel-2',
			handler: resultImport
		},'-',{
			text: '导出', 
			iconCls: 'icon-excel-2',
			handler: resultExport
		},'-',{
			id: 'linkedbtn',
			text: '公示/关联',
			iconCls: 'czs-doc-edit',
			disabled: true,
			handler: linkedPublicity
		},'-'],
		columns: [[{
			field: 'enterCode',
			title: '登记编号',
			width: 20
		},{
			field: 'providerName',
			title: '提供方姓名',
			width: 10
		},{
			field: 'complaintType',
			title: '类型',
			align: 'center',
			width: 10
		},{
			field: 'handleStatus',
			title: '办理情况',
			align: 'center',
			width: 10
		},{
			field: 'enterDept',
			title: '登记部门',
			width: 20
		},{
			field: 'enterDate',
			title: '登记日期',
			align: 'center',
			width: 10
		},{
			field: 'isPublicity',
			title: '是否公示',
			align: 'center',
			width: 10,
	    	formatter: function(value, data, index){
	    		return value != 1 ? '不公示' : '公示';
	    	}
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
	
	resultGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#resultSearchBar').replaceWith('<input id="resultSearchbox"/>');
	$('#resultSearchbox').searchbox({
		menu : '#resultSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			var checkNode = $('#originTypeGrid').datagrid('getSelected');
			if( checkNode != null ){
				filter['complaintResult.originTypeId'] = checkNode.id;
			}
			resultGrid.datagrid('load', filter);
		}
	});
});


/**
 * 添加
 */
function addTo(){
	var checkNode = $('#originTypeGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择来源类型!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '添加投诉结果',
		href: './complaintResultAction!formView.action',
		width: 900,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './complaintResultAction!saveComplaintResult.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#resultGrid').datagrid('reload');
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
			// ---------------------------------------------------------
			// 初始化编辑器
			var kindEditor = top.$wdawn.kindEditor({
				selector: $('textarea[name="complaintResult.complaints"]', win)
			});
			
			$('input[name="complaintResult.originTypeId"]', win).val(checkNode.id);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintResult.complaints"]', win)
			});
		}
	});
}

/**
 * 修改
 */
function modify(){
	var checkNode = $('#resultGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新投诉结果',
		href: './complaintResultAction!formView.action',
		width: 900,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './complaintResultAction!updateComplaintResult.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#resultGrid').datagrid('reload');
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
					
					// ---------------------------------------------------------
					// 初始化编辑器
					var kindEditor = top.$wdawn.kindEditor({
						selector: $('textarea[name="complaintResult.complaints"]', win)
					});
					// 登记编号不能修改
					$('input[name="complaintResult.enterCode"]', win).attr('readonly', 'readonly');
				}
			});
			form.form('load','./complaintResultAction!getDetails?object='+checkNode.id);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintResult.complaints"]', win)
			});
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#resultGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './complaintResultAction!deleteComplaintResult.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#resultGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 导入
 */
function resultImport(){
	var win = top.$wdawn.dialog({
		title: '导入投诉结果',
		href: './complaintResultAction!formView.action?viewType=import',
		width: 400,
		height: 280,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				// loading
				var originTypeId = $('input[name="object"]', win).val();
				if( originTypeId == null || originTypeId == '' ){
					return;
				}
				
				if(originTypeId == 'CST-08'){
					top.$wdawn.showMsg('此来源不允许导入，请重新选择!');
					return;
				}
				
				top.$.messager.progress({
					msg:'导入中, 请稍后...',
					interval: 100
				});
				$('form', win).form('submit', {
					url: './complaintResultAction!importResult.action',
					success: function(message){
						top.$.messager.progress('close');
						var obj = $.parseJSON(message);
						if(obj.success==false){
							top.$wdawn.showMsg(obj.message);
							return;
						}
						win.dialog('close');
						$('#resultGrid').datagrid('reload');
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
			// 来源类型
			var combox = $('input[name="object"]', win).combobox({    
			    url:'./originTypeAction!getOriginTypeCommbox.action',    
			    id: 'id',
			    valueField: 'id',    
			    textField: 'scVal',
			    limitToList: true,
			    required: true,
			    onLoadSuccess: function(){
			    	var checkNode = $('#originTypeGrid').datagrid('getSelected');
					if(checkNode != null){
						combox.combobox('select', checkNode.id);
					}
			    }
			});
		}
	});
}

/**
 * 导出
 */
function resultExport(){
	var win = top.$wdawn.dialog({
		title: '投诉结果导出',
		href: './complaintResultAction!formView.action?viewType=export',
		width: 600,
		height: 200,
		buttons:[{
			text: '&nbsp;导出&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var startDate = $('input[name="startDate"]', win).val();
				var endDate = $('input[name="endDate"]', win).val();
				var checkNode = $('#originTypeGrid').datagrid('getSelected');
				var originTypeId = checkNode != null ? '&object='+checkNode.id : '';
				win.dialog('close');
				window.location.href = './complaintResultAction!exportResult.action?startDate='+startDate+
					'&endDate='+endDate+originTypeId;
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
 * 公示/关联
 */
function linkedPublicity(){
	var checkNode = $('#resultGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '公示/关联',
		href: './complaintResultAction!formView.action?viewType=linkedPublicity',
		width: 500,
		height: 260,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './complaintResultAction!linkedPublicity.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#resultGrid').datagrid('reload');
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
			$('input[name="complaintResult.id"]', win).val(checkNode.id);
			// 选择被投诉者
			$('#selectDealer', win).click(function(){
				$(this).selectorDealer({
					rowClick: function(rowIndex, rowData){
						$('input[name="complaintResult.dealerId"]', win).val(rowData.id);
						$('input[name="complaintResult.dealerName"]', win).val(rowData.name)
							.validatebox('isValid');
					}
				});
			});
		}
	});
}

/**
 * @param compResultId
 */
function showDetail( compResultId ){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './complaintResultAction!formView.action?viewType=readOnly',
		width: 900,
		height: 600,
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
					
					// ---------------------------------------------------------
					// 初始化编辑器
					var kindEditor = top.$wdawn.kindEditor({
						selector: $('textarea[name="complaintResult.complaints"]', win)
					});
					// 开启只读模式
					kindEditor.readonly(true);
				}
			});
			form.form('load','./complaintResultAction!getDetails?object='+compResultId);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintResult.complaints"]', win)
			});
		}
	});
}