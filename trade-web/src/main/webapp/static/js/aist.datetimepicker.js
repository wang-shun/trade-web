/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */

var Datetimepicker = function () {
	
	return {
		/**
		 * target  div中class 的名称
		 * f format格式 "yyyy-mm-dd"
		 */
		init: function (target,f) {
			$("."+target).datetimepicker({
					language:  'zh-CN',  
				    format: f,
			        autoclose: true,
			        todayBtn: true,
			        todayHighlight:true,
			        minView:4,
			        minuteStep: 5
				   
			   });
			
		}
		
	};
}();


   