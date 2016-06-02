/****
 *  
 *    排序自定义插件
 *    
 *    @author  : xin.hu
 *    @date : 2016-6-2
 * 
 */
; !
function($, window) {
	 "use strict";
	 
	 var aist = {
			 sortWrapper : function(options){
				 var settings = $.extend({
						active  :  "active",
						upIcon  :  "icon-chevron-up",
						downIcon : "icon-chevron-down"
					    //reloadGrid : "reloadGrid"
					},options||{});
					
					var active = settings.active;
					var upIcon = settings.upIcon;
					var downIcon =  settings.downIcon;
					var reloadGrid = settings.reloadGrid;
					var page = 1;
					
					var data = {};
					data.page = 1;
					
					aist.initSort(settings);
					
					$('span.sort').each(function(i){
			    		$(this).click(function(){
			    			var sidx = $(this).attr('sortColumn');
			    			data.sidx = sidx;
			    			
			    			//$('span.sort').not($(this)).children("i").remove();
			    			$('span.sort').not($(this)).removeClass(active);
			    			$('span.sort').not($(this)).removeAttr("style");
			    			
			    			$(this).addClass(active);
			    			$(this).css('color','#ed4e2a');
			    			// 如果上一次没有排序，则显示下箭头，否则上箭头
			    	/*		if(!$(this).has('i').length){
			    				$(this).append("<i class='"+ downIcon +"'></i>");
			    				data.sord = 'desc';
			    				$(this).attr("sord",'desc');
			    			} else {*/
			    				$(this).children("i").changeClass(downIcon,upIcon);
			    				
			    				if($(this).children("i").hasClass(downIcon)) {
			    					data.sord = 'desc';
			    					$(this).attr("sord",'desc');
			    				} else {
			    					data.sord = 'asc';
			    					$(this).attr("sord",'asc');
			    				}
			    			//}
			    			
			    			
			    			
			    			//$(this).data("sord",data.sord);
			    			
			    			/*var _reloadGrid = $(this).attr('reloadGrid');
			    			
			    			if(typeof window[_reloadGrid]=='function'){
			    				window[_reloadGrid]();
			    			}*/
			    			if(typeof reloadGrid =='function'){
			    				reloadGrid();
			    			}
			    			
			    		});
					});
			 },
			 
			 initSort : function(settings) {
				 // 如果存在默认值,按照默认的排序
				 if($('span.sort').hasClass(settings.active)) {
					 var activing =  $('span.sort.'+ settings.active);
					 var sord = activing.attr("sord");
					 // 设置样式
					 activing.css('color','#ed4e2a');
					 if(sord='asc') {
						 activing.append("<i class='"+ settings.upIcon +"'></i>");
					 } else {
						 activing.append("<i class='"+ settings.downIcon +"'></i>");
					 }
					 
					/* if(typeof settings.reloadGrid =='function'){
		    				reloadGrid();
	    			 }*/
				 }
				 
				 // 其他排序字段需要加上图标
				 var other = $('span.sort').not("."+settings.active);
				 other.each(function(i){
					 var sord = other.attr("sord");
					 if(sord='asc') {
						 other.append("<i class='"+ settings.upIcon +"'></i>");
					 } else {
						 other.append("<i class='"+ settings.downIcon +"'></i>");
					 }
				 })
			 },
			 
			 wrap : function(data) {
				 var activing =  $('span.sort.active');
				 var sortColumn = activing.attr("sortColumn");
				 var sord = activing.attr("sord");
				 data.sidx = sortColumn;
				 data.sord = sord;
			 },
			 
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
	            aist.autoCommit(this.url);
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
	 }
	 window.aist = aist;
	 jQuery.fn.changeSelect = function(params){
			if(this.hasClass("selected")) {
				this.removeClass("selected");
			} else {
				this.addClass("selected");
			}
	};
		
	jQuery.fn.changeClass = function(class1,class2){
		if(this.hasClass(class1)) {
			this.removeClass(class1);
			this.addClass(class2);
		} else {
			this.removeClass(class2);
			this.addClass(class1);
		}
	};
	
	
}(jQuery, window);