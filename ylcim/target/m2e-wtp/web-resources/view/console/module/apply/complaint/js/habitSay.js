/*
 *常用意见工具类 
 */
var habitSay = new Object();

/**
 * 初始化常用意见
 * @param successfun
 */
habitSay.init = function(fatherWin, callback){
	$.ajax({
		type: 'post',
		url: './habitSayAction!getHabitSayList.action',
		dataType: 'json',
		success: function(resData){
			var hsHtml = '';
			$.each(resData, function(){
				hsHtml += '<a class="habitSay-item" href="javascript:void(0);" habitSayId="'+this.id+'">';
					hsHtml += '<label>'+this.sayDetail+'</label>';
					hsHtml += '<span class="l-btn-icon icon-close delete"></span>';
				hsHtml += '</a>';
			});
			
			if(hsHtml != ''){
				$('.habitSay-container', fatherWin).append(hsHtml);
			}else{
				$('.habitSay-container', fatherWin).children().removeClass('default');
			}
			
			// 点击常用意见绑定事件
			$('.habitSay-item', fatherWin).click(function(){
				var habitSay = $('label', this).text();
				// 业务操作
				if( typeof callback === 'function' ){
					callback.apply(this, [habitSay]);
				}
			});
			
			// 删除常用意见事件绑定
			$('.delete', fatherWin).click(function(event){
				event.stopPropagation();
				var $habitSay = $(this).parents('.habitSay-item');
				var habitSayId = $habitSay.attr('habitSayId');
				if(habitSayId != null && habitSayId != '-1'){
					habitSay.del(habitSayId);
					$habitSay.remove();
					
					// 若全部把自己的常用意见删除了, 则显示默认的常用意见
					var $habitSays = $('.habitSay-container', fatherWin).children();
					if($habitSays.length == 2){
						$habitSays.removeClass('default');
					}
				}else{
					top.$wdawn.showMsg('默认意见不能被删除!');
				}
			});
		}
	});
};

/**
 * 保存常用意见
 * @param habitSay
 */
habitSay.save = function ( habitSay ){
	if(habitSay != null && habitSay != ''){
		$.ajax({
			url: './habitSayAction!saveHabitSay.action',
			data: {object: habitSay},
		});
	}
};

/**
 * 删除常用意见
 * @param habitSayId
 */
habitSay.del = function ( habitSayId ){
	$.ajax({
		url: './habitSayAction!deleteHabitSay.action',
		data: {object: habitSayId},
	});
};