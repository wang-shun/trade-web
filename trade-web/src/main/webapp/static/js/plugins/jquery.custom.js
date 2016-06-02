/****
 *  自定义插件
 *  
 *  @param Jquery
 *  
 *  @author : child 
 *  @date : 2016-6-1
 */

(function($){
	var settings;
	
	$.fn.changeSelect = function(params){
		if(this.hasClass("selected")) {
			this.removeClass("selected");
		} else {
			this.addClass("selected");
		}
	};
	
	$.fn.changeClass = function(class1,class2){
		if(this.hasClass(class1)) {
			this.removeClass(class1);
			this.addClass(class2);
		} else {
			this.removeClass(class2);
			this.addClass(class1);
		}
	};
	
	$.fn.sort = function(options){
		var settings = $.extend({
			active  :  "active",
			upIcon  :  "icon-chevron-up",
			downIcon : "icon-chevron-down"
		},options||{});
		
		var active = settings.active;
		var upIcon = settings.upIcon;
		var downIcon =  settings.downIcon;
		var page = 1;
		
		var data = {};
		data.page = 1;
		
		this.addClass(active);
		// 如果上一次没有排序，则显示下箭头，否则上箭头
		if(!$(this).has('i').length){
			$(this).append("<i class='"+ downIcon +"'></i>");
			data.sord = 'desc';
		} else {
			$(this).children("i").changeClass(downIcon,upIcon);
			
			if(this.hasClass(downIcon)) {
				data.sord = 'desc';
			} else {
				data.sord = 'asc';
			}
		}
		
		this.siblings().children("i").remove();
		this.siblings().removeClass(
);
		
		
		var sidx = $(this).attr('sortColumn');
		data.sidx = sidx;
		
		var reloadGrid = $(this).attr('reloadGrid');
		this.reloadGrid(data);

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
		},
		
	  sort : function(options){
		var settings = $.extend({
			active  :  "active",
			upIcon  :  "icon-chevron-up",
			downIcon : "icon-chevron-down"
		    //reloadGrid : "reloadGrid"
		},options||{});
		
		var active = settings.active;
		var upIcon = settings.upIcon;
		var downIcon =  settings.downIcon;
		var _reloadGrid = settings.reloadGrid;
		var page = 1;
		
		var data = {};
		data.page = 1;
		
		$('span.sort').each(function(i){
    		$(this).click(function(){
    			var sidx = $(this).attr('sortColumn');
    			data.sidx = sidx;
    			
    			var sortcolumn = $('span.sort');
    			for(var i = 0; i < sortcolumn.length ;i ++){
    				if(sidx != $(sortcolumn[i]).attr('sortColumn')){
    					$(sortcolumn[i]).children("i").remove();
    					$(sortcolumn[i]).removeClass(active);
    				}
    			}
    			$(this).addClass(active);
    			// 如果上一次没有排序，则显示下箭头，否则上箭头
    			if(!$(this).has('i').length){
    				$(this).append("<i class='"+ downIcon +"'></i>");
    				data.sord = 'desc';
    				$(this).attr("sord",'desc');
    			} else {
    				$(this).children("i").changeClass(downIcon,upIcon);
    				
    				if($(this).children("i").hasClass(downIcon)) {
    					data.sord = 'desc';
    					$(this).attr("sord",'desc');
    				} else {
    					data.sord = 'asc';
    					$(this).attr("sord",'asc');
    				}
    			}
    			
    			
    			
    			//$(this).data("sord",data.sord);
    			
    			//var reloadGrid = $(this).attr('reloadGrid');
    			//reloadGrid(data);
    			if(typeof _reloadGrid=='function'){
    				_reloadGrid();
    			}
    		});
		});
	  }
	})
	
})(jQuery);