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
			    			$('span.sort').not($(this)).css("cursor","pointer");
			    			
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
			    			} else {
			    				reloadGrid = 'reloadGrid';
			    				// 如果页面定义了reloadGrid方法,则使用reloadGrid方法，否则使用默认的方法
			    				if(typeof window[reloadGrid]=='function'){
				    				window[reloadGrid]();
				    			} else {
			    					var _self = settings._self;
			    					var cacheData = settings.cacheData;
			    					_self.reloadGrid(cacheData);
				    			}
			    			}
			    		});
					});
			 },
			 
			 initSort : function(settings) {
				 // 增加小手样式
				 $('span.sort').css("cursor","pointer");
				 // 如果存在默认值,按照默认的排序
				 if($('span.sort').hasClass(settings.active)) {
					 var activing =  $('span.sort.'+ settings.active);
					 var sord = activing.attr("sord");
					 // 设置样式
					 activing.css('color','#ed4e2a');
					 if(sord=='asc') {
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
					 var sord = $(this).attr("sord");
					 if(sord=='asc') {
						 $(this).append("<i class='"+ settings.upIcon +"'></i>");
					 } else {
						 $(this).append("<i class='"+ settings.downIcon +"'></i>");
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
		    	$(document.body).append(form);
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
			
			initPage : function(elements,options,pageData) {
				var _self = $(elements);
				
				var total = pageData.total;
	        	var pagesize = pageData.pagesize;
	        	var page = pageData.page;
	        	var records = pageData.records;
	        	
				var currentTotalstrong=$('#currentTotalPage').find('strong');
				if (total<1 || pagesize<1 || page<1) {
					$(currentTotalstrong).empty();
					$('#totalP').text(0);
					$("#pageBar").empty();
					return;
				}
				$(currentTotalstrong).empty();
				$(currentTotalstrong).text(page+'/'+total);
				
/*				var currentRecords = $("#currentRecords").find('strong');
				var lastRecords = "";
				if(page*pagesize>records) {
					lastRecords = records;
				} else {
					lastRecords = page*pagesize
				}
				currentRecords.empty();*/
				//currentRecords.text((page-1)*pagesize+1+"-"+lastRecords);
				$('#totalP').text(records);
				
				
				$("#pageBar").twbsPagination({
					totalPages:total,
					visiblePages:9,
					startPage:page,
					first:'<i class="fa fa-step-backward"></i>',
					prev:'<i class="fa fa-chevron-left"></i>',
					next:'<i class="fa fa-chevron-right"></i>',
					last:'<i class="fa fa-step-forward"></i>',
					showGoto:true,
					onPageClick: function (event, page) {
						 //console.log(page);
						//setStyle();
						options.page = page;
	      				_self.reloadGrid(options);
				    }
				});
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
	
	jQuery.isBlank = function(obj){
	   	return(!obj || $.trim(obj) === "");
	};
	
	jQuery.fn.aistGrid = function(options) {
		var settings = $.extend({
			url : "/quickGrid/findPage",
			page : 1,
			rows : 12
		},options||{});
		
		var ctx = settings.ctx;
		var url = settings.url;
		var queryId = settings.queryId;
		var page =  settings.page;
		var rows =  settings.rows;
		var templeteId = settings.templeteId;
		var templeteSource = settings.templeteSource;
		var columns = settings.columns;
		var gridClass = settings.gridClass;
		
		var data = ($.isBlank(settings.data))?{}:settings.data;
		data.queryId = queryId;
		data.page = page;
		data.rows = rows;

		if(typeof(columns) == "undefined") {
		
		} else {
			if (typeof(gridClass) == "undefined"){
				var table = $("<table></table");
			} else {
				var table = $("<table class=\""+gridClass+"\"></table");
			}
			
			var tbody = $("<tbody></tbody");
			var thead = $("<thead></thead>");
		    var tr = $("<tr></tr>");
		    $.each(columns, function(index,callback){
		    	if (typeof(columns[index].sortColumn) == "undefined")
		    	{
		    		if (typeof(columns[index].colName) == "undefined") {
		    			tr.append($("<th></th>"));
		    		} else {
		    			tr.append($("<th>" + columns[index].colName + "</th>"));
		    		}
		    	}else{
		    		var sortActive = 'active';
		    		if (typeof(columns[index].sortActive) == "undefined" || !columns[index].sortActive==true) {
		    			tr.append($("<th><span class=\"sort\" sortColumn=\"" + columns[index].sortColumn + "\" sord=\""+columns[index].sord+"\">"+columns[index].colName+"</span></th>"));
		    		} else {
		    			tr.append($("<th><span class=\"sort " +sortActive+"\" sortColumn=\"" + columns[index].sortColumn + "\" sord=\""+columns[index].sord+"\">"+columns[index].colName+"</span></th>"));
		    		}
		    	}
		    });

		    thead.append(tr);
		    table.append(thead).append(tbody);
		    
		    $(this).empty().append(table);
		}
	    
	    var pageBar = '<div class="text-center page_box">'+
            '<span id="currentTotalPage"><strong></strong></span>'+
            '<span class="ml15"> 共 <strong id="totalP"></strong> 条 </span>&nbsp;'+
            '<div id="pageBar" class="pagination text-center"></div>'+
            '</div>';
	    if($("#pageBar").length == 0) {
	    	$(this).after(pageBar);
	    }
	    var _self = $(this);

	    aist.sortWrapper({
			//reloadGrid : reloadGrid,
			cacheData : settings,
			_self : _self
		});
	    
	    _self.reloadGrid(settings);
		
	};
	
	jQuery.fn.reloadGrid = function(options) {
		var settings = $.extend({
			url : "/quickGrid/findPage",
			page : 1,
			rows : 12
		},options||{});
		
		var ctx = settings.ctx;
		var url = settings.url;
		var data = ($.isBlank(settings.data))?{}:settings.data;
		data.queryId = settings.queryId;
		data.page = settings.page;
		data.rows = settings.rows;
		aist.wrap(data);
		var templeteId = settings.templeteId;
		var templeteSource = settings.templeteSource;
		var wrapperData = settings.wrapperData;
		var _self = $(this);
		
		$.ajax({
			  async: true,
	          url:ctx+url ,
	          method: "post",
	          dataType: "json",
	          data: data,
	          beforeSend: function () {  
	          	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	  			$(".blockOverlay").css({'z-index':'9998'});
	          }, 
	          success: function(data){
	        	  $.unblockUI(); 
	        	  if(!$.isBlank(wrapperData)) {
	        		  data.wrapperData = wrapperData;
	        	  } 
	        	  var templateHtml ='';
	        	  if(typeof(templeteId) == "undefined") {
	        		  var render = template.compile(templeteSource);
	        		  templateHtml = render(data);
	        	  } else {
	        		  templateHtml = template(templeteId , data);
	        	  }
	        	  if (_self.has("tbody").length>0)         
	        	  {   
	        		  _self.find("tbody").empty();
		        	  _self.find("tbody").html(templateHtml);
	        	  }   
	        	  else    
	        	  {   
	        		  _self.empty();
	        		  _self.html(templateHtml);
	        	  }   
	        	 
	               // 显示分页 
	        	  var pageData = {};
	        	  pageData.total = data.total;
	        	  pageData.pagesize = data.pagesize;
	        	  pageData.page = data.page;
	        	  pageData.records = data.records;
	        	  aist.initPage(_self,settings,pageData);
	          }
	    });
	};
}(jQuery, window);