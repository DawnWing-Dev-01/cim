$(function(){
	
	$('.icon_lists li').click(function(){
		$('.icon_lists li').removeClass('onclick');
		$(this).addClass('onclick');
		
		var iconCls = $(this).attr('data-cls');
		$('#iconCls').val(iconCls);
	});
	
});

function addCaoMei(){
	// 先删除
	deleteIcon('caomei');
	
	$.each($('.icon-container'), function(index, item){
		var $span = $(item).children('[class^="czs-"]');
		var iconCls = $span.attr('class');
		
		var iconlibrary = {
				'iconlibrary.name': '.'+iconCls,
				'iconlibrary.iconCls': iconCls,
				'iconlibrary.from': 'caomei',
				'iconlibrary.sort': index
		};
		
		$.ajax({
    		type: 'post',
    		url: './iconlibraryAction!saveIconlibrary',
    		data: iconlibrary,
    		dataType: 'json',
    		success: function(obj){
    			console.log(obj.message);
    		}
    	});
	});
}

function addIconFont(){
	// 先删除
	deleteIcon('iconfont');
	
	$.each($('.icon_lists li'), function(index, item){
		var $div = $(item).children('.fontclass');
		var iconCls = $div.text();
		
		var iconlibrary = {
				'iconlibrary.name': iconCls,
				'iconlibrary.iconCls': iconCls.substr(1),
				'iconlibrary.from': 'iconfont',
				'iconlibrary.sort': index
		};
		
		$.ajax({
    		type: 'post',
    		url: './iconlibraryAction!saveIconlibrary',
    		data: iconlibrary,
    		dataType: 'json',
    		success: function(obj){
    			console.log(obj.message);
    		}
    	});
	});
}

function deleteIcon(from){
	$.ajax({
		type: 'post',
		url: './iconlibraryAction!deleteIconlibrary',
		data: {object: from},
		async: false,
		dataType: 'json',
		success: function(obj){
			console.log(obj.message);
		}
	});
}