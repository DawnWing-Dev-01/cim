$(function(){
	/**
	 * 文章信息列表
	 */
	var articleGrid = $('#articleGrid').datagrid({
		pageSize: 20,
		pageList: [20],
		url: './articleAction!getArticlePage.action',
		queryParams: {
			'articleInfo.columnId': columnId
		},
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
			id:'articleSearchBar'
		},'-',{
			text : '重置',
			iconCls : 'icon-cachu',
			handler : function() {
				$('#articleSearchbox').searchbox('setValue', null);
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
			field: 'name',
			title: '名称',
			width: 25
		},{
			field: 'columnName',
			title: '所属栏目',
			align: 'center',
			width: 10
		},{
			field: 'articleFrom',
			title: '文章来源',
			align: 'center',
			width: 10
		},{
			field: 'articleType',
			title: '文章类型',
			align: 'center',
			width: 10
		},{
			field: 'author',
			title: '发布者',
			align: 'center',
			width: 10
		},{
			field: 'deliveryDate',
			title: '发布日期',
			align: 'center',
			width: 10
		},{
	    	title: '摘要',
	    	field: 'summary', 
	    	width: 80
	    },{
			field: 'createDate',
			title: '创建日期',
			align: 'center',
			width: 10,
		},{
			field: 'id',
			title: '操作',
			align: 'center',
			width: 15,
			formatter: function(value, data, index){
				var opthtml = '<a href="javascript: void(0);" onclick="showDetail(\''+value+'\')">查看</a>';
				opthtml += '&nbsp;/&nbsp;';
				opthtml += '<a href="javascript: void(0);" onclick="yulanArticle(\''+value+'\')">预览</a>';
				return opthtml;
			}
		}]],
		pagination: true,
		onClickRow: function(rowIndex, rowData){
			
		}
	});
	articleGrid.datagrid('getPager').pagination({
		showPageList: false
	});
	
	$('#articleSearchBar').replaceWith('<input id="articleSearchbox"/>');
	$('#articleSearchbox').searchbox({
		menu : '#articleSearchMenu',
		prompt: '请输入查询条件',
		width: 250,
		searcher : function(value, name) {
			var filter = new Object();
			filter[name] = value;
			if( columnId != null && columnId != '' ){
				filter['articleInfo.columnId'] = columnId;
			}
			articleGrid.datagrid('load', filter);
		}
	});
});

/**
 * 添加
 */
function addTo(){
	var win = top.$wdawn.dialog({
		title: '发布文章',
		href: './articleAction!formView.action',
		width: 1000,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				if( !(columnId != null && columnId != '') ){
					top.$wdawn.showMsg('请到对应的栏目下发布文章!');
					return;
				}
				
				$('form', win).form('submit', {
					url: './articleAction!saveArticle.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#articleGrid').datagrid('reload');
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
				selector: $('textarea[name="articleInfo.content"]', win)
			});
			
			
			var deliveryDate = $('input[name="articleInfo.deliveryDate"]', win).datebox({    
				required:true   
			});
			// 显示当前日期
			deliveryDate.datebox('setValue', new Date().format('yyyy-MM-dd'));
			
			// 设置栏目信息
			$('input[name="articleInfo.columnId"]', win).val(columnId);
			
			// 消费法规、消费知识增加检索关键字
			if( columnId != '402880e9624c96be01624cacc8a40009' ){
				$('#articleSearchIndex', win).show();
				// 添加检索关键字
				$('#addIndex', win).click(function(){
					addsearchkey( this );
				});
			}
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="articleInfo.content"]', win)
			});
		}
	});
}

/**
 * 修改
 */
function modify(){
	var checkNode = $('#articleGrid').datagrid('getSelected');
	
	if(checkNode == null){
		top.$wdawn.showMsg('请先选择一条记录!');
		return;
	}
	
	var win = top.$wdawn.dialog({
		title: '更新文章',
		href: './articleAction!formView.action',
		width: 1000,
		height: 600,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				$('form', win).form('submit', {
					url: './articleAction!updateArticle.action',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false){
							return;
						}
						win.dialog('close');
						$('#articleGrid').datagrid('reload');
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
					
					// 初始化编辑器
					var editor = top.$wdawn.kindEditor({
						selector: $('textarea[name="articleInfo.content"]', win)
					});
					
					var deliveryDate = $('input[name="articleInfo.deliveryDate"]', win).datebox({    
						required:true   
					});
					
					// 消费法规、消费知识增加检索关键字
					if( columnId != '402880e9624c96be01624cacc8a40009' ){
						$('#articleSearchIndex', win).show();
						// 添加检索关键字
						$('#addIndex', win).click(function(){
							addsearchkey( this );
						});
					}
				}
			});
			form.form('load','./articleAction!getDetails?object='+checkNode.id);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="articleInfo.content"]', win)
			});
		}
	});
}

/**
 * 删除
 */
function del(){
	var checkNode = $('#articleGrid').datagrid('getSelected');
	
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
	    		url: './articleAction!deleteArticle.action',
	    		data: {object: checkNode.id},
	    		dataType: 'json',
	    		success: function(obj){
	    			top.$wdawn.showMsg(obj.message);
	    			if(obj.success==false){
						return;
					}
	    			$('#articleGrid').datagrid('reload');
	    		}
	    	});
	    }
	});
}

/**
 * 详情
 * @param id
 */
function showDetail( articleId ){
	var win = top.$wdawn.dialog({
		title: '查看详情',
		href: './articleAction!formView.action?viewType=readOnly',
		width: 1000,
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
					
					// 初始化编辑器
					var editor = top.$wdawn.kindEditor({
						selector: $('textarea[name="articleInfo.content"]', win)
					});
					// 开启只读模式
					editor.readonly(true);
					
					// 消费法规、消费知识增加检索关键字
					if( columnId != '402880e9624c96be01624cacc8a40009' ){
						$('#articleSearchIndex', win).show();
					}
				}
			});
			form.form('load','./articleAction!getDetails?object='+articleId);
		},
		onBeforeClose: function(){
			top.$wdawn.closeEditor({
				selector: $('textarea[name="articleInfo.content"]', win)
			});
		}
	});
}

/**
 * 消费提示预览
 */
function yulanArticle(articleId){
	top.layer.open({
		type : 2,
		title : false,
		content : './articleAction!formView.action?viewType=iphone',
		shadeClose : true,
		closeBtn: 0,
		shade : 0.5,
		area : [ '310px', '658px' ],
		success : function(layero, index) {
			var showArticle = './wechat/wechatAction!showArticleView?columnId='+columnId+'&articleId='+articleId;
			var $iframe = $('#layui-layer-iframe'+index, layero);
			$('#showIphone', $iframe.contents()).attr('src', showArticle);
		}
	}); 
}

/**
 * 检索关键字（索引）
 * @param addbtn
 */
function addsearchkey( addbtn ){
	top.layer.prompt({
		formType: 0,
		title: '输入检索关键字，并确认',
	}, function(value, index, elem){
		var $search = $(addbtn).prev('input[name="articleInfo.searchIndex"]');
		var searchkey = $search.val();
		if( searchkey != null && searchkey != '' ){
			searchkey += ', ';
		}
		searchkey += value;
		$search.val(searchkey);
		top.layer.close(index);
	});
}