$(function() {
	// fastclick 解决移动端click事件300ms延迟
	FastClick.attach(document.body);
	
	layui.use('laypage', function() {
		var laypage = layui.laypage;

		// 执行一个laypage实例
		laypage.render({
			elem : 'footerPage',
			limit : 10,
			groups : 2,
			curr: curr,
			count : total,
			jump : function(obj, first) {
				// 首次不执行
				if (!first) {
					// obj.curr得到当前页, obj.limit得到每页显示的条数
					var $form = $('#formArticleList');
					$('#page', $form).val(obj.curr);
					
					// 提交表单
					$form.submit();
				}
			}
		});
	});
});