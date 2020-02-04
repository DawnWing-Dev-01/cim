$(function(){
	/**
	 * 失信行为列表
	 */
	var complaintGrid = $('#complaintGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './complaintSheetAction!getComplaintSheetPage.action',
		fit: true,
		border: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		queryParams: {
			'complaintInfo.reporterId': top.onlineId,
			'complaintInfo.flowStatus': null
		},
		frozenColumns:[[{
			field: 'ck',
			checkbox:true
		}]],
		toolbar:['-',{
			id:'complaintSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#complaintSearchbox').searchbox('setValue', null);
			}
		},'-',{
			id: 'addbtn',
			text: '添加',
			iconCls: 'czs-add',
			handler: addTo
		},'-',{
			id: 'modifybtn',
			text: '修改',
			iconCls: 'czs-pen-write',
			disabled: true,
			handler: modify
		},'-',{
			id: 'delbtn',
			text: '删除',
			iconCls: 'czs-trash-l',
			disabled: true,
			handler: del
		},'-'],
		columns: [[{
			field: 'complaintCode',
			title: '投诉编号',
			width: 15
		},{
			field: 'dealerName',
			title: '被投诉者',
			width: 20
		},{
			field: 'registerUnit',
			title: '登记单位',
			align: 'center',
			width: 10
		},{
			field: 'complaintSource',
			title: '投诉来源',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
				return value != 'weChatEntry' ? '平台录入' : '微信举报';
			}
		},{
			field: 'createDate',
			title: '登记时间',
			align: 'center',
			width: 10
		},{
			field: 'reportClassify',
			title: '举报分类',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
	    		var classify = null;
				switch (value) {
					case 1:
						classify = '举报';
						break;
					case 2:
						classify = '投诉';
						break;
					case 3:
						classify = '咨询';
						break;
					default:
						classify = '未分类';
						break;
				}
				return classify;
	    	}
		},{
			field: 'flowStatus', 
	    	title: '流程进度',
	    	align: 'center',
	    	width: 10,
	    	formatter: function(value, data, index){
	    		return showFlowText( value );
	    	}
	    },{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
				var btnhtml = '';
				if(data.flowStatus == 'draft'){
					btnhtml += '<a href="javascript: void(0);" onclick="submit(\''+data.id+'\')">提交</a> / ';
				}
				btnhtml += '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\', \''+data.flowStatus+'\')">查看</a>';
				return btnhtml;
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			if(rowData.flowStatus == 'draft'){
				$('#modifybtn').linkbutton('enable');
				$('#delbtn').linkbutton('enable');
			}else{
				$('#modifybtn').linkbutton('disable');
				$('#delbtn').linkbutton('disable');
			}
		}
	});
	complaintGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#complaintSearchBar').replaceWith('<input id="complaintSearchbox"/>');
	$('#complaintSearchbox').searchbox({
		menu : '#complaintSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			filter['complaintInfo.reporterId'] = top.onlineId;
			filter['complaintInfo.flowStatus'] = null;
			complaintGrid.datagrid('load', filter);
		}
	});
});

/**
 * 添加
 */
function addTo(){
	var win = top.$wdawn.dialog({
		title: '消费者投诉登记',
		href: './complaintSheetAction!formView.action',
		width: 900,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './complaintSheetAction!saveComplaintSheet.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#complaintGrid').datagrid('reload');
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
				selector: $('textarea[name="complaintInfo.complaintReason"]', win)
			});
			
			// 选择被投诉人
			$('#selectDealer', win).click(function(){
				$(this).selectorDealer({
					rowClick: function(rowIndex, rowData){
						$('input[name="complaintInfo.dealerId"]', win).val(rowData.id);
						$('input[name="complaintInfo.dealerName"]', win).val(rowData.name)
							.validatebox('isValid');
						$('input[name="complaintInfo.dealerLinkman"]', win).val(rowData.legalPerson)
							.validatebox('isValid');
						$('input[name="complaintInfo.dealeriPhone"]', win).val(rowData.linkTel)
							.validatebox('isValid');
						$('input[name="complaintInfo.dealerAddress"]', win).val(rowData.dealerAddress)
							.validatebox('isValid');
						$('input[name="complaintInfo.dealerJurisdiction"]', win).val(rowData.jurisdictionName);
					}
				});
			});
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintInfo.complaintReason"]', win)
			});
		}
	});
}

/**
 * 更新
 */
function modify(){
	var checkNode = $('#complaintGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '消费者投诉更新',
		href: './complaintSheetAction!formView.action?object='+checkNode.id,
		width: 900,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './complaintSheetAction!updateComplaintSheet.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#complaintGrid').datagrid('reload');
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
						selector: $('textarea[name="complaintInfo.complaintReason"]', win)
					});
					
					// 选择被投诉人
					$('#selectDealer', win).click(function(){
						$(this).selectorDealer({
							rowClick: function(rowIndex, rowData){
								$('input[name="complaintInfo.dealerId"]', win).val(rowData.id);
								$('input[name="complaintInfo.dealerName"]', win).val(rowData.name)
									.validatebox('isValid');
								$('input[name="complaintInfo.dealerLinkman"]', win).val(rowData.legalPerson)
									.validatebox('isValid');
								$('input[name="complaintInfo.dealeriPhone"]', win).val(rowData.linkTel)
									.validatebox('isValid');
								$('input[name="complaintInfo.dealerAddress"]', win).val(rowData.dealerAddress)
									.validatebox('isValid');
								$('input[name="complaintInfo.dealerJurisdiction"]', win).val(rowData.jurisdictionName);
							}
						});
					});
				}
			});
			form.form('load','./complaintSheetAction!getDetails?object='+checkNode.id);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintInfo.complaintReason"]', win)
			});
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#complaintGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './complaintSheetAction!deleteComplaintSheet.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#complaintGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 详情
 * @param id
 */
function showDetail( complaintId, flowStatus ){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './complaintSheetAction!formView.action?viewType=readOnly&object='+complaintId,
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
						selector: $('textarea[name="complaintInfo.complaintReason"]', win)
					});
					// 开启只读模式
					kindEditor.readonly(true);
				}
			});
			form.form('load','./complaintSheetAction!getDetails?object='+complaintId);
			
			// 点击查看处理记录按钮
			$('#showHandleGrid', win).click(function(){
				showHandleGrid( complaintId );
			});
			
			if(flowStatus == 'draft'){
				// 草稿状态不能看进度日志
				$('.easyui-tabs', win).tabs('disableTab', 2);
			}
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintInfo.complaintReason"]', win)
			});
		}
	});
}

/**
 * 提交投诉表
 * @param complaintId
 */
function submit( complaintId ){
	top.$.messager.confirm('操作确认','您确认想要提交吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './complaintSheetAction!submitWorkFlow.action',
	    		data: {object: complaintId},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#complaintGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 显示流程当前节点
 * @param flowstatus
 * @returns
 */
function showFlowText( flowstatus ){
	var text = '';
	switch ( flowstatus ) {
		case 'draft':
			text = '已起草';
			break;
		case 'submitted':
			text = '已提交';
			break;
		case 'accepted':
			text = '已受理';
			break;
		case 'audited':
			text = '已审核';
			break;
		case 'shunted':
			text = '已分流';
			break;
		case 'alreadyHandled':
			text = '已办理';
			break;
		case 'supervisorAudited':
			text = '已审核';
			break;
		case 'confirmed':
			text = '已结束';
			break;
		default:
			break;
	}
	return text;
}

/**
 * 展示投诉处理记录表格
 * @param complaintId
 */
function showHandleGrid( complaintId ){
	var win = top.$wdawn.dialog({
		title: '投诉处理记录',
		href: './handleAction!formView.action?viewType=handleGrid',
		width: 800,
		height: 350,
		collapsible: true,
		onLoad: function(){
			/**
			 * 投诉处理列表
			 */
			var handleGrid = $('#handleGrid', win).datagrid({
				pageSize: 20,
				pageList: [20],
				url: './handleAction!getComplaintHandlePage.action',
				fit: true,
				border: false,
				rownumbers: true,
				fitColumns: true,
				singleSelect: true,
				queryParams: {
					'complaintHandleInfo.complaintId': complaintId
				},
				columns: [[{
					field: 'handleUserName',
					title: '处理人',
					width: 15
				},{
					field: 'handleType',
					title: '处理类型',
					align: 'center',
					width: 10,
					formatter: function(value, data, index){
						return value != 'finally' ? '过程记录' : '处罚结果';
					}
				},{
					field: 'handleDate',
					title: '处理时间',
					align: 'center',
					width: 20
				},{
					field: 'handleSay',
					title: '处理结果',
					width: 80
				}]],
				pagination: true
			});
			handleGrid.datagrid('getPager').pagination({
				showPageList: false
			});
		}
	});
}