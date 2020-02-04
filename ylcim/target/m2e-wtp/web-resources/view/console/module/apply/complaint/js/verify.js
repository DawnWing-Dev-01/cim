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
				if(data.flowStatus == 'shunted'){
					return '<a href="javascript: void(0);" onclick="toHandle();">【失信行为处理】</a>';
				}
				return '<a href="javascript: void(0);" onclick="verify(\''+data.id+'\', \''+data.flowStatus+'\')">审核</a>';
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
			complaintGrid.datagrid('load', filter);
		}
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
				if('submitted' == flowStatus){
					var handleResult = $('input[name="flowLogInfo.handleResult"]:checked', win).val();
					var dealerId = $('input[name="complaintInfo.dealerId"]', win).val();
					// 已提交, 进入受理状态&&审核结果不是直接结束的, 需要关联经营者信息
					if( !(dealerId != null && dealerId.length > 0) 
							&& handleResult != -1 ){
						top.$wdawn.showMsg('请关联经营者信息!');
						$('#verifyformTabs', win).tabs('select', 1);
						return;
					}
					
					// 先提交失信行为表单
					$('form#complaint', win).form('submit', {
						url: './complaintSheetAction!updateComplaintSheet.action',
						success: function(message){
							var obj = $.parseJSON(message);
							if(obj.success==false){
								return;
							}
							top.$wdawn.showMsg('关联成功!');
						}
					});
				}
				
				// 提交审核表单, 并且推进流程
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
						
						// 最后一个节点确认时&&审核结果是同意时, 更新投诉单的公示状态
						var handleResult = $('input[name="flowLogInfo.handleResult"]:checked', win).val();
						if( 'supervisorAudited' == flowStatus && handleResult == 1 ){
							top.layer.confirm('<small>默认为不公示，选【否】或直接关闭<br>则该投诉不予公示...</small>', {
								title:'是否公示?', 
								btn: ['是', '否']
							}, function(index){
								$.ajax({
									type: 'post',
									url: './complaintSheetAction!updateIsPublicity.action',
									data: {
										'complaintInfo.id': complaintId,
										'complaintInfo.isPublicity': 1
									},
									dataType: 'json',
									success: function(obj){
										console.log(obj);
									}
								});
							  top.layer.close(index);
							});
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
								
								// 关联完被投诉人信息后, 直接提交
								$('form#complaint', win).form('submit', {
									url: './complaintSheetAction!updateComplaintSheet.action',
									success: function(message){
										var obj = $.parseJSON(message);
										if(obj.success==false){
											return;
										}
										top.$wdawn.showMsg('关联成功!');
									}
								});
							}
						});
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
				
				// 选择举报分类, 只有受理时才可以选择
				$('#reportClassifyTr', win).remove();
			}else{
				// 初审时不能驳回
				$('input[name="flowLogInfo.handleResult"][value="0"]', win).attr('disabled', 'disabled');
				
				// 举报分, 选择时给ComplaintSheetInfo赋值
				$('#reportClassify', win).combobox({
					data: [{
						label: '举报', value: '1'
					},{
						label: '投诉', value: '2'
					},{
						label: '咨询', value: '3'
					}],
					valueField: 'value',
					textField: 'label',
					required: true,
					onSelect: function(record){
						$('input[name="complaintInfo.reportClassify"]', win).val(record.value);
					}
				});
			}
			
			// 分流只有在科长审核&&分流完成后才出现, 办理时走单需选择分局主管
			if( 'audited' != flowStatus && 'shunted' != flowStatus ){
				$('#audited', win).remove();
			}else{
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
			
			// 点击查看处理记录按钮
			$('#showHandleGrid', win).click(function(){
				showHandleGrid( complaintId );
			});
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
 * 跳转至失信行为处理菜单
 */
function toHandle(){
	var menuli = $('#402880fe61a9142e0161a91f36f40004', top.document).children();
	$.each(menuli, function(index, li){
		var title = $('.tree-title', li).text();
		if( $.trim(title) == '失信行为处理' ){
			$('.tree-node', li).click();
			return false;
		}
	});
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
						return value != 'finally' ? '过程记录' : '最终处理';
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