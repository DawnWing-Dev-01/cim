$(function(){
	var attachGrid=$('#attachGrid').datagrid({
		pageSize:20,
		pageList:[20],
		url:'./attachAction!getAttachPage',
		fit:true,
		border : false,
		nowrap : false,
		rownumbers : true,
		fitColumns : true,
		singleSelect : true,
		frozenColumns : [[{
			field : 'ck',
			checkbox : true
		}]],
		toolbar :[{
			id : 'attachSearchBar'
		}, '-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#attachSearchbox').searchbox('setValue', null);
			}
		},'-',{
			text:'删除',
			iconCls:'czs-trash-l',
			handler:del
		},'-'],
		columns : [[{
			field : 'attachName',
			title : '附件名称',
			width : 30
		},{
			field : 'attachSize',
			title : '附件大小(KB)',
			align : 'center',
			width : 10,
			formatter:function(value, row, index){
				var kb = value / 1024;
				return kb.toFixed(1);
			}
		},{
			field : 'fileType',
			title : '文件类型',
			width : 10
		},{
			field : 'attachType',
			title : '附件类型',
			align : 'center',
			width : 10,
			formatter:function(value, row, index){
				switch (value) {
				case 'image':
					return '图片';
				case 'doc':
					return '文档';
				case 'media' || 'flash':
					return '媒体';
				case 'file':
					return '文件';
				default:
					return '其他';
				}
			}
		},{
			field : 'state',
			title : '附件状态',
			align : 'center',
			width : 10,
			formatter:function(value, row, index){
				return value != '0' ? '正常' : '销毁';
			}
		},{
			field : 'createDate',
			title : '上传时间',
			align : 'center',
			width : 10
		},{
			field : 'id',
			title : '操作',
			align : 'center',
			width : 10,
			formatter:function(value, row, index){
				//return '<a href="#" onclick="detail(\''+value+'\')">删除</a>';
			}
		}]],
		pagination : true,
		onClickRow : function(rowIndex, rowData) {
			attachGrid.datagrid('clearSelections');
			attachGrid.datagrid('selectRow', rowIndex);
		}
	});
	attachGrid.datagrid('getPager').pagination({
		showPageList : false
	});
	$('#attachSearchBar').replaceWith('<input id="attachSearchbox"/>');
	$('#attachSearchbox').searchbox({
		menu : '#attachSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			attachGrid.datagrid('load', filter);
		}
	});
});

function del() {
	var meetingTypeIds = new Array();
	$.each($('#attachGrid').datagrid('getSelections'), function(index, value) {
		meetingTypeIds.push(value.id);
	});
	if (meetingTypeIds.length <= 0) {
		top.phenix.tip('warning', '请选择所要删除的信息!');
		return;
	}
	top.$.messager.confirm('提示', '是否删除选择的信息?', function(isTrue) {
		if (isTrue) {
			$.ajaxSettings.traditional = true;
			$.ajax({
				type : 'post',
				url : './attachAction!deleteAttach',
				data : {
					'objects' : meetingTypeIds
				},
				dataType : 'json',
				success : function(obj) {
					top.phenix.tip(obj.type, obj.message);
					$('#attachGrid').datagrid('reload');
				}
			});
		}
	});
}
