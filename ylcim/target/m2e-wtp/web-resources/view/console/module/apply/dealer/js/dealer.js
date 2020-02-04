$(function(){
	/**
	 * 经营者信息列表
	 */
	var dealerGrid = $('#dealerGrid').datagrid({
		pageSize: 20,
		pageList: [20, 50, 100, 500, 1000, 2000],
		url: './dealerAction!getDealerPage.action',
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
			id:'dealerSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#dealerSearchbox').searchbox('setValue', null);
			}
		},'-',{
			text: '条件检索',
			iconCls: 'icon-shaixuan',
			handler: search
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
			text: '生成',
			iconCls: 'icon-qrcode',
			handler: newQrCode
		},'-',{
			text: '打印',
			iconCls: 'icon-printqrcode',
			handler: printQrCode
		},'-',{
			text: '批量生成',
			iconCls: 'icon-qrcode',
			handler: newBatchQrCode
		},'-',{
			text: '批量下载',
			iconCls: 'czs-inbox-l',
			handler: downloadBatchQrCode
		},'-',{
			text: 'Excel导入',
			iconCls: 'icon-excel-2',
			handler: dealerImport
		},'-'],
		columns: [[{
			field: 'name',
			title: '名称',
			width: 35
		},{
			field: 'licenseNo',
			title: '营业执照',
			width: 20
		},{
			field: 'legalPerson',
			title: '法人姓名',
			align: 'center',
			width: 15
		},{
			field: 'jurisdictionName',
			title: '管辖单位',
			align: 'center',
			width: 15
		},{
			field: 'industryName',
			title: '所属行业',
			align: 'center',
			width: 15
		},/*{
			field: 'dealerTypeName',
			title: '经营类型',
			width: 15
		},*/{
			field: 'qrcode',
			title: '二维码',
			width: 10,
			align: 'center',
			formatter: function(value, data, index){
				if( value != null && value.length > 0 ){
					return '已生成';
				}
				return '待生成';
			}
		},{
			field: 'createDate',
			title: '创建日期',
			width: 10,
			align: 'center'
		}/*,{
	    	title: '说明',
	    	field: 'remark', 
	    	width: 80
	    }*/,{
			field: 'detail',
			title: '操作',
			width: 10,
			align: 'center',
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\')">查看</a>';
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	dealerGrid.datagrid('getPager').pagination({
		showPageList: true
	});
	
	$('#dealerSearchBar').replaceWith('<input id="dealerSearchbox"/>');
	$('#dealerSearchbox').searchbox({
		menu : '#dealerSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			dealerGrid.datagrid('load', filter);
		}
	});
});

/**
 * 
 */
function search(){
	var win = top.$wdawn.dialog({
		title: '条件检索',
		href: './dealerAction!formView.action?viewType=searchform',
		width: 600,
		height: 200,
		buttons:[{
			text: '&nbsp;查询&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var qrcode = $('input[name="dealerInfo.qrcode"]:checked', win).val();
				var startDate = $('input[name="dealerInfo.startDate"]', win).val();
				var endDate = $('input[name="dealerInfo.endDate"]', win).val();
				$('#dealerGrid').datagrid('load', {
					'dealerInfo.qrcode': qrcode,
					'dealerInfo.startDate': startDate,
					'dealerInfo.endDate': endDate
				});
				win.dialog('close');
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
 * 添加
 */
function addTo(){
	var win = top.$wdawn.dialog({
		title: '添加经营者',
		href: './dealerAction!formView.action',
		width: 700,
		height: 400,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './dealerAction!saveDealer.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#dealerGrid').datagrid('reload');
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
			// 所属行业
			$('input[name="dealerInfo.industryId"]', win).combobox({    
			    url:'./industryAction!getIndustryCommBox.action',    
			    valueField: 'id',    
			    textField: 'name',
			    required: true
			});
			
			// 经营类型
			$('input[name="dealerInfo.dealerTypeId"]', win).combobox({    
			    url:'./dealerTypeAction!getTypeCommBox.action',    
			    valueField: 'id',    
			    textField: 'name',
			    required: true   
			});
			
			// 管辖单位
			$('input[name="dealerInfo.jurisdiction"]', win).combotree({    
			    url: './orgAction!getTree.action',
			    required: true,
			    lines: true,
			    checkbox: true,
			    animate: true
			});
			
			// 添加简称按钮点击事件
			$('#addsimple', win).click(function(){
				addsimple( this );
			});
		}
	});
}

/**
 * 修改
 */
function modify(){
	var checkNode = $('#dealerGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新经营者',
		href: './dealerAction!formView.action',
		width: 700,
		height: 400,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './dealerAction!updateDealer.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#dealerGrid').datagrid('reload');
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
					// 所属行业
					$('input[name="dealerInfo.industryId"]', win).combobox({    
					    url:'./industryAction!getIndustryCommBox.action',    
					    valueField: 'id',    
					    textField: 'name',
					    required: true   
					});
					
					// 经营类型
					$('input[name="dealerInfo.dealerTypeId"]', win).combobox({    
					    url:'./dealerTypeAction!getTypeCommBox.action',    
					    valueField: 'id',    
					    textField: 'name',
					    required: true   
					});
					
					// 管辖单位
					$('input[name="dealerInfo.jurisdiction"]', win).combotree({    
					    url: './orgAction!getTree.action',
					    required: true,
					    lines: true,
					    checkbox: true,
					    animate: true
					});
					
					// 添加简称按钮点击事件
					$('#addsimple', win).click(function(){
						addsimple( this );
					});
				}
			});
			form.form('load','./dealerAction!getDetails?object='+checkNode.id);
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#dealerGrid').datagrid('getSelected');
	
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
	    		url: './dealerAction!deleteDealer.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#dealerGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 详情
 * @param id
 */
function showDetail(dealerId){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './dealerAction!formView.action?viewType=readOnly',
		width: 700,
		height: 400,
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
			form.form('load','./dealerAction!getDetails?object='+dealerId);
		}
	});
}

/**
 * 生成二维码
 */
function newQrCode(){
	var checkNode = $('#dealerGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var qrcode = checkNode.qrcode;
	if( qrcode != null && qrcode.length > 0 ){
		top.$.messager.confirm('操作确认','确定要重新生成二维码？', function(yn){    
		    if (yn){
		    	newQrCodeAjax(checkNode.id);
		    }
		});
	}else{
		newQrCodeAjax(checkNode.id);
	}
}

/**
 * @param dealerId
 */
function newQrCodeAjax( dealerId ){
	$.ajax({
		type: 'post',
		url: './dealerAction!generateQrCode.action',
		data: {object: dealerId},
		dataType: 'json',
		beforeSend: function(){
			// loading
			top.$.messager.progress({
				msg:'二维码生成中...',
				interval: 100
			});
		},
		success: function(obj){
			top.$.messager.progress('close');
			top.$wdawn.showMsg(obj.message);
			if(obj.success==false){
				return;
			}
			$('#dealerGrid').datagrid('reload');
		}
	});
}

/**
 * 批量生成二维码
 */
function newBatchQrCode(){
	var rows = $('#dealerGrid').datagrid('getRows');
	var dealerIds = new Array();
	$.each(rows, function(index, row){
		dealerIds.push(row.id);
	});
	
	if(dealerIds.length == 0){
		return;
	}
	
	$.ajax({
		type: 'post',
		url: './dealerAction!generateBatchQrCode.action',
		data: {'strArray': dealerIds},
		dataType: 'json',
		beforeSend: function(){
			// loading
			top.$.messager.progress({
				msg:'批量二维码生成中...',
				interval: 100
			});
		},
		success: function(obj){
			top.$.messager.progress('close');
			top.$wdawn.showMsg(obj.message);
			if(obj.success==false){
				return;
			}
			$('#dealerGrid').datagrid('reload');
		}
	});
}

/**
 * 批量下载二维码
 */
function downloadBatchQrCode(){
	var rows = $('#dealerGrid').datagrid('getRows');
	var dealerIds = new Array();
	$.each(rows, function(index, row){
		dealerIds.push(row.id);
	});
	
	if(dealerIds.length == 0){
		return;
	}
	
	$.ajax({
		type: 'post',
		url: './dealerAction!baleTempZip.action',
		data: {'strArray': dealerIds},
		dataType: 'text',
		beforeSend: function(){
			// loading
			top.$.messager.progress({
				msg:'打包批量下载中...',
				interval: 100
			});
		},
		success: function(obj){
			top.$.messager.progress('close');
			if(obj != ''){
				window.location.href = './dealerAction!downloadBatch.action?object='+obj;
			}
		}
	});
}

/**
 * 添加简称
 * @param addbtn
 */
function addsimple( addbtn ){
	
	top.layer.prompt({
		formType: 0,
		title: '输入经营者简称，并确认',
	}, function(value, index, elem){
		var $simple = $(addbtn).prev('input[name="dealerInfo.simpleName"]');
		var simplename = $simple.val();
		if( simplename != null && simplename != '' ){
			simplename += ', ';
		}
		simplename += value;
		$simple.val(simplename);
		top.layer.close(index);
	});
}

/**
 * 打印二维码
 */
function printQrCode(){
	var checkNode = $('#dealerGrid').datagrid('getSelected');
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var qrcode = checkNode.qrcode;
	if( !(qrcode != null && qrcode.length > 0) ){
		top.$wdawn.showMsg('请先生成二维码!');
	}
	
	var win = top.$wdawn.dialog({
		title: '打印预览',
		href: './dealerAction!formView.action?viewType=showQrCode&object='+checkNode.id,
		width: 300,
		height: 479,
		collapsible: true,
		buttons:[{
			text: '&nbsp;打印&nbsp;',
			plain: true,
			iconCls: 'icon-printqrcode',
			handler: function(){
				$('#viewPrint', win).attr('src', './dealerAction!formView.action?viewType=showQrCode&object='+checkNode.id);
				$('#viewPrint', win).load(function(){
					var $iframe = $(this).contents();
					var clone = win.clone();
					clone.css({
						width: '100%',
						heigth: '100%'
					});
					$('body', $iframe).html(clone);
					var childwindow = $(this)[0].contentWindow;
					childwindow.print();
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
			/*var bgImgQrcode = $wdawn.getProjectPath()+'static/other/QrCodeBGI/QrCode_Template.jpg';
			$('#showQrCode_container', win).css({
				'background-image': 'url("'+bgImgQrcode+'")'
			});
			$('#showTitle', win).html(checkNode.name);
			var showQrCodeUrl = './wechat/attachAction!showImage?object=' + qrcode;
			$('#showQrCode', win).attr('src', showQrCodeUrl);*/
		}
	});
}

/**
 * Excel导入
 */
function dealerImport(){
	var win = top.$wdawn.dialog({
		title: '导入：第1步',
		href: './dealerAction!formView.action?viewType=import',
		width: 400,
		height: 260,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				// loading
				top.$.messager.progress({
					msg:'导入中, 请稍后...',
					interval: 100
				});
				$('form', win).form('submit', {
					url: './dealerAction!dealerImport.action',
					success: function(message){
						top.$.messager.progress('close');
						var obj = $.parseJSON(message);
						if(obj.success==false){
							top.$wdawn.showMsg(obj.message);
							return;
						}
						win.dialog('close');
						$('#dealerGrid').datagrid('reload');
						
						// 打开下载二维码窗口
						var downloadwin = top.$wdawn.dialog({
							title: '导入：第2步',
							content: '<a href="javascript:void(0);" class="downloadQrCode">下载二维码</a>',
							width: 300,
							height: 100,
							closable: false,
							onOpen: function(){
								// 加载成功执行的操作...
								$('.downloadQrCode', top.document).click(function(){
									window.location.href = './dealerAction!downloadBatch.action?object='+obj.attachId;
									downloadwin.dialog('close');
								});
							}
						});
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
		}
	});
}