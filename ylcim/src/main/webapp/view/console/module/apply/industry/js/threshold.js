$(function(){
	/**
	 * 行业信息列表
	 */
	var industryGrid = $('#industryGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './industryAction!getIndustryPage.action',
		fit: true,
		border: false,
		rownumbers: true,
		//fitColumns: true,
		singleSelect: true,
		frozenColumns:[[{
			field: 'ck',
			checkbox:true
		}]],
		toolbar:['-',{
			id:'industrySearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#industrySearchbox').searchbox('setValue', null);
			}
		},'-',{
			text: '设置阀值',
			iconCls: 'icon-warning',
			handler: threshold
		},'-'],
		columns: [[{
			field: 'name',
			title: '名称',
			width: 300
		},{
			field: 'warningThreshold',
			title: '预警阀值',
			align: 'center',
			width: 100
		},{
			field: 'sort',
			title: '排序',
			align: 'center',
			width: 100
		},{
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 100,
			formatter: function(value, data, index){
				return '<a href="javascript: void(0);" onclick="showDetail(\''+data.id+'\')">查看</a>';
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	industryGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#industrySearchBar').replaceWith('<input id="industrySearchbox"/>');
	$('#industrySearchbox').searchbox({
		menu : '#industrySearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			industryGrid.datagrid('load', filter);
		}
	});
});

/**
 * 详情
 * @param id
 */
function showDetail(industryId){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './industryAction!formView.action?viewType=readOnly',
		width: 450,
		height: 280,
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
			form.form('load','./industryAction!getDetails?object='+industryId);
		}
	});
}

/**
 * 阀值设置
 */
function threshold(){
	var checkNode = $('#industryGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	top.layer.prompt({
		formType: 0,
		title: '输入预警阀值，并确认',
		value: checkNode.warningThreshold,
		success: function(layero, index){
			var $layerContent = $('.layui-layer-content', layero);
			$layerContent.css({'padding-bottom':'5px'});
			var thresholdTitle = '<div style="width: 242px; color: #FF4A4A;">'
					+'预警阀值是指一个月内行业被投诉的有效次数，大于该值就需要提示发布预警了</div>';
			$layerContent.append(thresholdTitle);
		}
	}, function(value, index, elem){
		var threshold = parseInt(value);
		if( isNaN(threshold) ){
			top.layer.alert('请输入数字!');
			return;
		}
		var loadIndex = null;
		$.ajax({
    		type: 'post',
    		url: './industryAction!updateThreshold.action',
    		data: {
    			'industryInfo.id': checkNode.id,
    			'industryInfo.warningThreshold': threshold
    		},
    		dataType: 'json',
    		beforeSend: function(){
    			loadIndex = top.layer.load();
    		}, 
    		success: function(obj){
    			top.layer.close(loadIndex);
    			top.layer.close(index);
    			top.$wdawn.showMsg(obj.message);
    			if(obj.success==false){
					return;
				}
    			$('#industryGrid').datagrid('reload');
    		}
    	});
	});
}