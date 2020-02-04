/**
 * dawn.selector.js 业务选择器插件, 包含：角色选择、用户选择、经营者选择
 * author: w.xL
 * date: 2018-4-2
 * version: 1.0
 */

(function(){
	
	/**
	 * role selector
	 */
	$.fn.selectorRole = function( options ){
		var defaults = {
			title: 'Dawn Selector Role',
			width: 600,
			height: 400,
			href: './roleAction!formView.action?viewType=choose',
			rowClick: function(rowIndex, rowData){
				// 此处只定义接口, 由调用者本身来实现
				throw new Error('[selector] selectorRole not impl rowClick function...');
			}
		};
		
		// 属性继承
		var setting = $.extend(defaults, options);
		var $that = $(this);
		
		var win = top.$wdawn.dialog({
			title: setting.title,
			href: setting.href,
			width: setting.width,
			height: setting.height,
			collapsible: true,
			onLoad: function(){
				initGrid(win);
			}
		});
		
		// 初始化表格
		var initGrid = function( win ){
			var roleGrid = $('#roleGrid', win).datagrid({
				pageSize: 20,
				pageList: [20],
				url: './roleAction!getRolePage.action',
				fit: true,
				border: false,
				rownumbers: true,
				fitColumns: true,
				singleSelect: true,
				toolbar:['-',{
					id:'roleSearchBar'
				},'-',{
					text : '重置',
					iconCls : 'icon-cachu',
					handler : function() {
						$('#roleSearchbox', win).searchbox('setValue', null);
					}
				},'-'],
				columns: [[{
					field: 'name',
					title: '角色名称',
					width: 20
				},{
					field: 'code',
					title: '角色编码',
					align: 'center',
					width: 10
				}]],
				pagination: true,
				onClickRow: function(rowIndex, rowData){
					if( typeof setting.rowClick === 'function' ){
						setting.rowClick.apply(this, [rowIndex, rowData]);
						win.dialog('close');
					}
				}
			});
			roleGrid.datagrid('getPager').pagination({
				showPageList: false
			});
			
			$('#roleSearchBar', win).replaceWith('<input id="roleSearchbox"/>');
			$('#roleSearchbox', win).searchbox({
				menu : '#roleSearchMenu',
				prompt: '请输入查询条件',
				width: 250,
				searcher : function(value, name) {
					var filter = new Object();
					filter[name] = value;
					roleGrid.datagrid('load', filter);
				}
			});
		};
	};
	
	/**
	 * user selector
	 */
	$.fn.selectorUser = function( options ){
		var defaults = {
			title: 'Dawn Selector User',
			width: 750,
			height: 400,
			href: './userAction!formView.action?viewType=choose',
			rowClick: function(rowIndex, rowData){
				// 此处只定义接口, 由调用者本身来实现
				throw new Error('[selector] selectorUser not impl rowClick function...');
			}
		};
		
		// 属性继承
		var setting = $.extend(defaults, options);
		var $that = $(this);
		
		var win = top.$wdawn.dialog({
			title: setting.title,
			href: setting.href,
			width: setting.width,
			height: setting.height,
			collapsible: true,
			onLoad: function(){
				initGrid(win);
			}
		});
		
		// 初始化表格
		var initGrid = function( win ){
			/**
			 * 组织机构树
			 */
			var organizationTree = $('#organizationTree', win).tree({
		        url : './orgAction!getTree.action',
		        parentField : 'id',
		        treeField: 'text',
		        lines : true,
		        animate: true,
		        onClick : function(node) {
		            $('#userGrid', win).datagrid('load', {
		                object: node.indexNum
		            });
		        }
		    });
			
			var userGrid = $('#userGrid', win).datagrid({
				pageSize: 20,
				pageList: [20],
				url: './userAction!getUserPage.action',
				fit: true,
				border: false,
				rownumbers: true,
				fitColumns: true,
				singleSelect: true,
				toolbar:['-',{
					id:'userSearchBar'
				},'-',{
					text : '重置',
					iconCls : 'icon-cachu',
					handler : function() {
						$('#userSearchbox', win).searchbox('setValue', null);
					}
				},'-'],
				columns: [[{
					field: 'name',
					title: '姓名',
					width: 25
				},{
					field: 'birthday',
					title: '生日',
					align: 'center',
					width: 10
				},{
					field: 'gender',
					title: '性别',
					align: 'center',
					width: 10,
					formatter: function(value){
						if(value == 1){
							return '男';
						}
						return '女';
					}
				},{
					field: 'mobile',
					title: '手机号码',
					width: 15
				}]],
				pagination: true,
				onClickRow: function(rowIndex, rowData){
					if( typeof setting.rowClick === 'function' ){
						setting.rowClick.apply(this, [rowIndex, rowData]);
						win.dialog('close');
					}
				}
			});
			userGrid.datagrid('getPager').pagination({
				showPageList: false
			});
			
			$('#userSearchBar', win).replaceWith('<input id="userSearchbox"/>');
			$('#userSearchbox', win).searchbox({
				menu : '#userSearchMenu',
				prompt: '请输入查询条件',
				width: 250,
				searcher : function(value, name) {
					var filter = new Object();
					filter[name] = value;
					var checkNode = organizationTree.tree('getSelected');
					if( checkNode != null ){
						filter['object'] = checkNode.indexNum;
					}
					userGrid.datagrid('load', filter);
				}
			});
		};
	};
	
	/**
	 * dealer selector 选择被投诉人（经营者）
	 */
	$.fn.selectorDealer = function( options ){
		var defaults = {
			title: 'Dawn Selector Dealer',
			width: 700,
			height: 450,
			href: './dealerAction!formView.action?viewType=choose',
			rowClick: function(rowIndex, rowData){
				// 此处只定义接口, 由调用者本身来实现
				throw new Error('[selector] selectorUser not impl rowClick function...');
			}
		};
		
		// 属性继承
		var setting = $.extend(defaults, options);
		var $that = $(this);
		
		var win = top.$wdawn.dialog({
			title: setting.title,
			href: setting.href,
			width: setting.width,
			height: setting.height,
			collapsible: true,
			onLoad: function(){
				initGrid(win);
				
				// 筛选按钮添加点击事件
				$('#searchbtn', win).click(function(){
					$('#dealerGrid', win).datagrid('load', {
						'dealerInfo.name': $('#name', win).val(),
						'dealerInfo.simpleName': $('#simpleName', win).val(),
						'dealerInfo.legalPerson': $('#legalPerson', win).val(),
						'dealerInfo.licenseNo': $('#licenseNo', win).val(),
						'dealerInfo.dealerAddress': $('#dealerAddress', win).val()
					});
				});
				
				$('#addbtn', win).click(function(){
					addDealer(win);
				});
			}
		});
		
		// 初始化表格
		var initGrid = function( win ){
			var dealerGrid = $('#dealerGrid', win).datagrid({
				pageSize: 50,
				pageList: [50],
				url: './dealerAction!getDealerPage.action',
				fit: true,
				border: false,
				rownumbers: true,
				fitColumns: true,
				singleSelect: true,
				/*toolbar:['-',{
					id:'dealerSearchBar'
				},'-',{
					text : '重置',
					iconCls : 'icon-cachu',
					handler : function() {
						$('#dealerSearchbox', win).searchbox('setValue', null);
					}
				},'-'],*/
				columns: [[{
					field: 'name',
					title: '名称',
					width: 25
				},{
					field: 'licenseNo',
					title: '营业执照',
					width: 15
				},{
					field: 'legalPerson',
					title: '法人姓名',
					width: 10
				},{
					field: 'industryName',
					title: '所属行业',
					width: 10
				},{
					field: 'jurisdictionName',
					title: '管辖单位',
					width: 10
				}]],
				pagination: true,
				onClickRow: function(rowIndex, rowData){
					if( typeof setting.rowClick === 'function' ){
						setting.rowClick.apply(this, [rowIndex, rowData]);
						win.dialog('close');
					}
				}
			});
			dealerGrid.datagrid('getPager').pagination({
				showPageList: false
			});
			
			/*$('#dealerSearchBar', win).replaceWith('<input id="dealerSearchbox"/>');
			$('#dealerSearchbox', win).searchbox({
				menu : '#dealerSearchMenu',
				prompt: '请输入查询条件',
				width: 250,
				searcher : function(value, name) {
					var filter = new Object();
					filter[name] = value;
					dealerGrid.datagrid('load', filter);
				}
			});*/
		};
	};
	
	/**
	 * dealer selector 选择被投诉人（经营者）
	 */
	$.fn.selectorWeChatOpenId = function( options ){
		var defaults = {
			title: 'Dawn Selector WechatOpenId',
			width: 600,
			height: 400,
			href: './userAction!formView.action?viewType=choose',
			rowClick: function(rowIndex, rowData){
				// 此处只定义接口, 由调用者本身来实现
				throw new Error('[selector] selectorUser not impl rowClick function...');
			}
		};
		
		// 属性继承
		var setting = $.extend(defaults, options);
		var $that = $(this);
		
		var win = top.$wdawn.dialog({
			title: setting.title,
			href: setting.href,
			width: setting.width,
			height: setting.height,
			collapsible: true,
			onLoad: function(){
				// 这块不需要组织机构
				$('#userlayout', win).layout('remove', 'west');
				initGrid(win);
			}
		});
		
		// 初始化表格
		var initGrid = function( win ){
			var wechatUserGrid = $('#userGrid', win).datagrid({
				pageSize: 10000,
				pageList: [10000],
				url: './wechat/wechatAction!getWeChatUserList.action',
				fit: true,
				border: false,
				rownumbers: true,
				fitColumns: true,
				singleSelect: true,
				columns: [[{
					field: 'headImgUrl',
					title: '头像',
					width: 5,
					align: 'center',
					formatter: function(value){
						return '<img src="'+value+'" alt="头像" style="width:20px;height:20px;"/>';
					}
				},{
					field: 'nickName',
					title: '昵称',
					width: 15
				},{
					field: 'openId',
					title: '微信号',
					width: 30
				},{
					field: 'sex',
					title: '性别',
					width: 5,
					align: 'center',
					formatter: function(value){
						if(value == 1){
							return '男';
						}
						return '女';
					}
				},{
					field: 'city',
					title: '城市',
					width: 15
				}]],
				pagination: true,
				onClickRow: function(rowIndex, rowData){
					if( typeof setting.rowClick === 'function' ){
						setting.rowClick.apply(this, [rowIndex, rowData]);
						win.dialog('close');
					}
				},
				onLoadSuccess: function(data){
					var nextOpenId = data.nextOpenId;
				}
			});
			wechatUserGrid.datagrid('getPager').pagination({
				showPageList: false
			});
		};
	};
	
	/**
	 * 添加经营者
	 */
	function addDealer( fatherWin ){
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
							$('#dealerGrid', fatherWin).datagrid('reload');
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
					var that = this;
					top.layer.prompt({
						formType: 0,
						title: '输入经营者简称，并确认',
					}, function(value, index, elem){
						var $simple = $(that).prev('input[name="dealerInfo.simpleName"]');
						var simplename = $simple.val();
						if( simplename != null && simplename != '' ){
							simplename += ', ';
						}
						simplename += value;
						$simple.val(simplename);
						top.layer.close(index);
					});
				});
			}
		});
	}
	
})(jQuery, window);