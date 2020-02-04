/**
 * JS网页标题提醒插件,附带消息音效提示
 * */
(function($) {
	$.extend({
		/**
		 * 调用方法： 
		 * var timerArr = $.blinkTitle.show();
		 * $.blinkTitle.playSound('xxx.mp3');
		 * $.blinkTitle.clear(timerArr);
		 */
		blinkTitle : {
			show : function() { //有新消息时在title处闪烁提示
				var step = 0, _title = document.title;
				var timer = setInterval(function() {
					step++;
					if (step == 3) {
						step = 1;
					}

					if (step == 1) {
						document.title = '【　　　】' + _title;
					}

					if (step == 2) {
						document.title = '【新消息】' + _title;
					}

				}, 500);
				return [ timer, _title ];
			},
			/**
			 * @param timerArr[0], timer标记
			 * @param timerArr[1], 初始的title文本内容
			 */
			clear : function(timerArr) { //去除闪烁提示，恢复初始title文本
				if (timerArr) {
					clearInterval(timerArr[0]);
					document.title = timerArr[1];
				}
			},
			/**
			 * @param media 媒体路径
			 */
			playSound : function(media){
				var embed = $('<embed src="'+media+'" loop="0" autostart="true" hidden="true" style="display: none;"></embed>');
				$('embed').remove();
				embed.appendTo('body');
			}
		}
	});
})(jQuery);