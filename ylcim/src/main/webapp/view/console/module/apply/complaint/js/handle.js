$(function(){
	/**
	 * 失信行为列表
	 */
	var complaintGrid = $('#complaintGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './complaintSheetAction!getTodoListPage.action',
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
			id:'complaintSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#complaintSearchbox').searchbox('setValue', null);
			}
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
				return '<a href="javascript: void(0);" onclick="verify(\''+data.id+'\', \''+data.flowStatus+'\')">审核</a>';
			}
		}]],
		pagination: true,
		onLoadSuccess: function(){
			$('#handleGrid').datagrid('load', {
				'complaintHandleInfo': null
			});
		},
		onClickRow: function(rowIndex, rowData){
			$('#handleGrid').datagrid('load', {
				'complaintHandleInfo.complaintId': rowData.id
			});
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
			complaintGrid.datagrid('load', filter);
		}
	});
	
	/**
	 * 投诉处理列表
	 */
	var handleGrid = $('#handleGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './handleAction!getComplaintHandlePage.action',
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
			id: 'addbtn',
			text: '添加',
			iconCls: 'czs-add',
			handler: addTo
		},'-',{
			id: 'delbtn',
			text: '删除',
			iconCls: 'czs-trash-l',
			handler: del
		},'-'],
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
				return value != 'finally' ? '过程记录' : '最终处理';
			}
		},{
			field: 'handleDate',
			title: '处理时间',
			align: 'center',
			width: 10
		},{
			field: 'handleSay',
			title: '处理结果',
			width: 80
		},{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\', \''+data.id+'\')">查看</a>';
			}
		}]],
		pagination: true
	});
	handleGrid.datagrid('getPager').pagination({
		showPageList: false
	});
});

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
 * 审核
 * @param complaintId
 */
function verify( complaintId, flowStatus ){
	var win = top.$wdawn.dialog({
		title: '审核',
		href: './complaintSheetAction!formView.action?viewType=verifyForm&flowNode='+flowStatus+'&object='+complaintId,
		width: 900,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var dealerId = $('input[name="complaintInfo.dealerId"]', win).val();
				if( dealerId != null && dealerId.length > 0 ){
					if(!isFinally(complaintId)){
						top.$wdawn.showMsg('请下最终处罚结果!');
						return;
					}
					
					// 办理提交时, 需更新流程实例类型(正常受理、非正常受理)
					if( 'shunted' == flowStatus ){
						var exampleType = $('input[name="workflowExample.exampleType"]:checked', win).val();
						$.ajax({
							type: 'post',
							url: './complaintSheetAction!updateExampleType.action',
							data: {
								'workflowExample.businessId': complaintId,
								'workflowExample.exampleType': exampleType
							},
							dataType: 'json',
							success: function(obj){
								console.log(obj);
							}
						});
					}
					
					$('form#flowLog', win).form('submit', {
						url: './complaintSheetAction!verifyComplaint.action',
						success: function(message){
							var obj = $.parseJSON(message);
							top.$wdawn.showMsg(obj.message);
							if(obj.success==false){
								return;
							}
							
							// 将审核意见保存为常用意见
							var handleSay = $('textarea[name="flowLogInfo.handleSay"]', win).blur().val();
							habitSay.save(handleSay);
							
							win.dialog('close');
							$('#complaintGrid').datagrid('reload');
						}
					});
				}else{
					top.$wdawn.showMsg('请关联经营者信息!');
					$('#verifyformTabs', win).tabs('select', 1);
				}
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
			var form = $('form#complaint', win);
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
					if( 'submitted' != flowStatus){
						// 开启只读模式
						kindEditor.readonly(true);
					}
					
					// 选择被投诉人
					$('#selectDealer', win).click(function(){
						chooseDealer(win);
					});
				}
			});
			form.form('load','./complaintSheetAction!getDetails?object='+complaintId);
			
			// 审核表单初始化区域
			$('input[name="object"]', win).val(complaintId);
			
			// 初始化编辑器
			var kindEditor = top.$wdawn.kindEditor({
				selector: $('textarea[name="flowLogInfo.handleSay"]', win)
			});
			
			// 常用意见加载
			habitSay.init(win, function(data){
				kindEditor.html(data);
				kindEditor.sync();
			});
			
			// 只有在初审时才可以结束流程
			if( 'submitted' != flowStatus ){
				var $stopradio = $('input[name="flowLogInfo.handleResult"][value="-1"]', win);
				var radioId = $stopradio.attr('id');
				$('label[for="'+radioId+'"]', win).remove();
				$stopradio.remove();
			}
			
			// 分流只有在科长审核&&分流完成后才出现, 办理时走单需选择分局主管
			if( 'audited' != flowStatus && 'shunted' != flowStatus ){
				$('#audited', win).remove();
			}else{
				if( 'shunted' == flowStatus ){
					$('#audited th', win).text('主管领导：');
				}
				// 分流时选择用户
				$('#selectSubject', win).click(function(){
					$(this).selectorUser({
						rowClick: function(rowIndex, rowData){
							var showText = $('#showText', win);
							showText.val(rowData.name).validatebox('validate');
							$('input[name="flowLogInfo.subjectId"]', win).val(rowData.id);
						}
					});
				});
			}
			
			// 受理类型只有在分流完, 正在办理才会出现, 可以选择
			if( 'shunted' != flowStatus ){
				$('#shunted', win).remove();
			}
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintInfo.complaintReason"]', win)
			});
			top.$wdawn.closeEditor({
				selector: $('textarea[name="flowLogInfo.handleSay"]', win)
			});
		}
	});
}

/**
 * 判断是否已经下过最终定论
 * @param complaintId
 * @returns
 */
function isFinally( complaintId ){
	var isFinally = null;
	$.ajax({
		type: 'post',
		url: './handleAction!isFinally.action',
		async: false,
		data: {object: complaintId},
		dataType: 'json',
		success: function(obj){
			isFinally = obj.isFinally;
		}
	});
	return isFinally;
}

/**
 * 记录处理结果
 */
function addTo(){
	var checkNode = $('#complaintGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	if(isFinally(checkNode.complaintId)){
		top.$wdawn.showMsg('已处罚, 请不要重复操作!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '投诉处理记录',
		href: './handleAction!formView.action',
		width: 900,
		height: 500,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './handleAction!saveComplaintHandle.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#handleGrid').datagrid('reload');
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
				selector: $('textarea[name="complaintHandleInfo.handleSay"]', win)
			});
			
			var handleDate = $('input[name="complaintHandleInfo.handleDate"]', win).datetimebox({    
				required:true   
			});
			// 显示当前日期
			handleDate.datetimebox('setValue', new Date().format('yyyy-MM-dd hh:MM:ss'));
			
			$('input[name="complaintHandleInfo.complaintId"]', win).val(checkNode.id);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="flowLogInfo.handleSay"]', win)
			});
		}
	});
}

/**
 * 删除处理结果
 */
function del(){
	var checkNode = $('#handleGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	if(isFinally(checkNode.complaintId)){
		top.$wdawn.showMsg('禁止删除!');
		return;
	}
	
	top.$.messager.confirm('操作确认','您确认想要删除记录吗？', function(yn){    
	    if (yn){    
	    	$.ajax({
	    		type: 'post',
	    		url: './handleAction!deleteComplaintHandle.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#handleGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}