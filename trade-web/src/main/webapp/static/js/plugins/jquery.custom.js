(function($){
	var settings;
	
	$.fn.changeSelect = function(params){
		if(this.hasClass("selected")) {
			this.removeClass("selected");
		} else {
			this.addClass("selected");
		}
	};
	
	$.extend({
		exportExcel : function(options) {
			var settings = $.extend({
				url : "/quickGrid/findPage?xlsx&"
			},options||{});
			
			this.ctx = settings.ctx;
			this.url = settings.url;
			this.queryId = settings.queryId;
			this.colomns = settings.colomns;
			this.data =  settings.data;

    		this.url = this.ctx + this.url + $.param(this.data) + '&queryId='+ this.queryId + '&colomns=' + this.colomns;
            $.autoCommit(this.url);
		},
	
		// 自动提交form表单
	    autoCommit : function(url) {
	    	var form = $("<form action=\"#\" accept-charset=\"utf-8\" method=\"post\" id=\"excelForm\"></form>");
	    	if(url) {
	    		form.attr('action', url);
	    	}
	    	form.submit();
	    },
		
		// 判断该dom元素是否存在
		isExistDom : function(obj) {
			if(obj.length>0){
				return true;
			}else{
				return false;
			}
		}
	});
	
})(jQuery);