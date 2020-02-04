$(function(){
	/**
	 * 历史失信行为列表
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
		queryParams:{
			'complaintInfo.flowStatus': 'confirmed'
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
			filter['complaintInfo.flowStatus'] = 'confirmed';
			complaintGrid.datagrid('load', filter);
		}
	});
});

/**
 * 详情
 * @param id
 */
function showDetail( complaintId ){
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
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="complaintInfo.complaintReason"]', win)
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