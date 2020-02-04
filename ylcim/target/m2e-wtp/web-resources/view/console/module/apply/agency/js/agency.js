$(function(){
	var complaintGrid = $('#complaintGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './complaintSheetAction!getTodoListPage.action',
		fit: true,
		border: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		columns: [[{
			field: 'dealerName',
			title: '被投诉者',
			width: 20
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
			field: 'detail',
			title: '操作',
			align: 'center',
			width: 10,
			formatter: function(value, data, index){
				if(data.flowStatus == 'shunted'){
					return '<a href="javascript: void(0);" onclick="toApply(\'handle\');">【处理】</a>';
				}
				return '<a href="javascript: void(0);" onclick="toApply(\'verify\');">【审核】</a>';
			}
		}]],
		pagination: true
	});
	complaintGrid.datagrid('getPager').pagination({
		showPageList: false
	});
});

/**
 * 跳转至失信行为处理菜单
 */
function toApply( apply ){
	var menuTitle = apply != 'verify' ? '失信行为处理':'失信行为审核';
	var menuli = $('#402880fe61a9142e0161a91f36f40004', top.document).children();
	$.each(menuli, function(index, li){
		var title = $('.tree-title', li).text();
		if( $.trim(title) == menuTitle ){
			$('.tree-node', li).click();
			return false;
		}
	});
}