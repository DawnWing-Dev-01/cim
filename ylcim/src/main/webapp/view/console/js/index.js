$(function(){
	$.ajax({
		type: 'post',
		url: './menuAction!getNav',
		success: function(html){
			$('#menuBox').append(html);
			$('#menuBox').accordion({
				fit: true,
				border: false
			});
		}
	});
	
	$('#header-date').html(new Date().format('yyyy年MM月dd日  / 星期w'));
});

function logOut(){
	$.ajax({
		type: 'post',
		url: './loginAction!logOut',
		success: function(){
			window.location.href = './loginAction!index';
		}
	});
}

function showUserInfo(){
	var win = top.$wdawn.dialog({
		title: '用户信息',
		href: './userAction!formView.action?viewType=showInfo',
		width: 630,
		height: 380,
		collapsible: true,
		buttons:[{
			text: '&nbsp;确定&nbsp;',
			plain: true,
			iconCls: 'czs-save',
			handler: function(){
				var form = win.find('form');
				form.form('submit', {
					url: './userAction!updateUser',
					success: function(message){
						var obj = $.parseJSON(message);
						top.$wdawn.showMsg(obj.message);
						if(obj.success==false)return;
						win.dialog('close');
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
			var form = win.find('form');
			form.form({
				onLoadSuccess: function(obj){
					if(obj.success==false){
						top.$wdawn.showMsg(obj.message);
						win.dialog('close');
						return;
					}
					
					// 更新账号只读
					$('input[name="userInfo.username"]', win).attr('readonly', 'readonly');
					
					// 选择微信号
					$('#chooseOpenId', win).click(function(){
						$(this).selectorWeChatOpenId({
							rowClick: function(rowIndex, rowData){
								$('input[name="userInfo.wechatOpenId"]', win)
									.val(rowData.openId).validatebox('isValid');
							}
						});
					});
				}
			});
			form.form('load','./userAction!getDetails?object='+onlineId);
			
			$('#submit', win).click(function(){
				var form = $('#updatePwd', win);
				form.form('submit', {
					url: './userAction!modifyPwd',
					success: function(message){
						var obj = $.parseJSON(message);
						if(obj.success==false)return;
						form.form('reset');
						// 修改密码后提示退出登录
						top.$wdawn.confirm({
							title: obj.message,
							message: '为了系统安全，请您重新登录！',
							yCallback: function(){
								// 注销登录
								logOut();
							}
						});
					}
				});
			});
		}
	});
}

function changeThemes(){
	alert('暂未开通，敬请期待！');
}