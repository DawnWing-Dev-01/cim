(function(){
	
	$.fn.dawnicon = function(options){
		/*var defaults = {
			iconCls: '',
			fatherDialog: ''
		};
		
		var setting = $.extend(defaults, options);*/
		var $that = $(this);
		
		$that.click(function(){
			var win = top.$wdawn.dialog({
				title: 'IconFont图标',
				link: './iconlibraryAction!chooseIcon',
				dialogType: 'iframe',
				width: 715,
				height: 450,
				buttons:[{
					text: '&nbsp;选择&nbsp;',
					plain: true,
					iconCls: 'icon-complete',
					handler: function(){
						var iframe = $('#iframeDialog', win).contents();
						var iconCls = $('#iconCls', iframe).val();
						if( !(iconCls != null && iconCls != '') ){
							return;
						}
						var oldcls = $that.attr('data-old');
						$that.removeClass(oldcls);
						
						$that.addClass(iconCls).attr('data-old', iconCls);
						$that.siblings('input').val(iconCls);
						
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
			});
		});
		
		var iconClsIn = $that.siblings('input').val();
		if( iconClsIn != null && iconClsIn != '' ){
			var oldcls = $that.attr('data-old');
			$that.removeClass(oldcls);
			
			$that.addClass(iconClsIn).attr('data-old', iconClsIn);
		}
	};
	
})(jQuery);