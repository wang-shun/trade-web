var messageGrid = function () {
    return {
    	_config:{e:{},c:"",autoGetMsg:true,sendTime:1000*60*5},
    	_tagElement:{},
    	_messageServer:"",
    	_timer:null,
		_msgCatagory:"",
    	/**t：目标元素，s:消息类型下拉id,u:message服务地址*/
        init: function (config) {
        	 this._config=$.extend(this._config,config);
             this._tagElement=this._config.e;
             this._messageServer=this._config.u;
             this._msgCatagory=this._config.c;

			this.getMessage();
			var THIS=this;
			if(this._config.autoGetMsg){
				this._timer=setInterval(function(){THIS.getMessage(THIS); },this._config.sendTime);
			}
        },stopTimer:function(){
        	if(this._timer){
        		clearInterval(this._timer);
        		this._timer=null;
        	}
        },startTimer:function(){
        	if(!this._timer&&this.autoGetMsg){
        		this.init({});
        	}else{
        		console.log('Timer has already started or autoGetMsg is not true');
        	}
        },getMessage:function(THIS){
        	if(!THIS)THIS=this;
			THIS._getMessage(THIS._tagElement,THIS._msgCatagory);
        },_getMessage:function(te,c){
        	var _this=this;
        	/*
        	 * c:msgGatagory
        	 * **/
        	    $.ajax({  
                    type : "get",  
                    //async : false,  
                    url:_this._messageServer+"/site/siteIndex.json?callback=?&page.size=10&msgCatagory="+c,  
                    dataType : "jsonp",  
                    jsonp : "callback", //传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)    
                    success : function(data) {  
                    	 if(data){
                    		 _this.buildMsg(te,data.content);
        	        	 }
                    },  
                    error : function() {  
                        //console.error('An error occurred getMessage()');  
                    }  
                });
        },buildMsg:function(e,d){
        	/**c:msg catagory d:return date*/
        	e.empty();
        	scroller=$("<div>").addClass("scroller");
        	ul=$("<ul>").addClass("feeds");
        	var booleans = false;
        	$(d).each(function(i,e){
        		li=$("<li>");
            	col1=$("<div>").addClass("col1");
            	
            	col1.click(function(){
            		message.readSiteMsg(e.title,e.id);
            	});
            	cont= $("<div>").addClass("cont");
            	if(booleans){
            		cont.append($("<div>").addClass("cont-col1").append($("<div>").addClass("label ").append($("<i>").addClass("icon-bell"))));
            		booleans = false;
            	}else{
            		cont.append($("<div>").addClass("cont-col1").append($("<div>").addClass("label label-success").append($("<i>").addClass("icon-bell"))));
            		booleans = true;
            	}
            	
            	cont.append($("<div>").addClass("cont-col2").append($("<div>").addClass("desc").append(e.title)));
            	col1.append(cont);
            	col2=$("<div>").addClass("col2").append($("<div>").addClass("date").text(e.relativeDate));
            	ul.append(li.append(col1).append(col2));
        	});
//        	div.append(scroller.append(ul));
        	scroller.append(ul);
        	e.append(scroller);
        	scroller.slimScroll({
                size: '7px',
                color: '#a1b2bd',
                //position: isRTL ? 'left' : 'right',
                height: $(this).attr("data-height"),
                alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
                railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
                disableFadeOut: true
            });
        }
    };
};

var messageTable = function () {
    return {
    	_config:{e:{},c:"",autoGetMsg:true,sendTime:1000*60*5},
    	_tagElement:{},
    	_messageServer:"",
    	_timer:null,
		_msgCatagory:"",
    	/**t：目标元素，s:消息类型下拉id,u:message服务地址*/
        init: function (config) {
        	 this._config=$.extend(this._config,config);
             this._tagElement=this._config.e;
             this._messageServer=this._config.u;
             this._msgCatagory=this._config.c;

			this.getMessage();
			var THIS=this;
			if(this._config.autoGetMsg){
				this._timer=setInterval(function(){THIS.getMessage(THIS); },this._config.sendTime);
			}
        },stopTimer:function(){
        	if(this._timer){
        		clearInterval(this._timer);
        		this._timer=null;
        	}
        },startTimer:function(){
        	if(!this._timer&&this.autoGetMsg){
        		this.init({});
        	}else{
        		console.log('Timer has already started or autoGetMsg is not true');
        	}
        },getMessage:function(THIS){
        	if(!THIS)THIS=this;
			THIS._getMessage(THIS._tagElement,THIS._msgCatagory);
        },_getMessage:function(te,c){
        	var _this=this;
        	/*
        	 * c:msgGatagory
        	 * **/
        	var scope = "20070001";
        	if(c==1){
        		scope = "20070001";
        	}else if(c==2){
        		scope = "20070002";
        	}
        	$.ajax({
        	    type:'post',
        	    cache : false,
        	    async: false,//false同步，true异步
        	    dataType: 'json',
        	    url:_this._messageServer+"/workspace/selectWorkNum",
        		data:[
        				 {name : 'typeId',value : c},
        				 {name : 'scope',value : scope}
        	     ], 
        		success: function(data) {
        			if(typeof(data.message) != "undefined"){
        				//alert(data.message);
        			}
        			//大于等于75绿色    大于50% 小于75% 黄色    小于50%红色
        			//登盘
        			$("#addHous").html(data.content.newHous);
        			$("#addHousPlan").html(data.content.newHousPlan);
        			$("#divaddHous").attr("data-percent",data.content.newHousPlanPercent);
        			//新增客源
        			$("#newCust").html(data.content.newCust);
        			$("#newCustPlan").html(data.content.newCustPlan);
        			$("#divnewCust").attr("data-percent",data.content.newCustPlanPercent);
        			//新增钥匙
        			$("#newKeys").html(data.content.newKeys);
        			$("#newKeysPlan").html(data.content.newKeysPlan);
        			$("#divnewKeys").attr("data-percent",data.content.newKeysPlanPercent);
        			//新增实勘
        			$("#newPic").html(data.content.newPic);
        			$("#newPicPlan").html(data.content.newPicPlan);
        			$("#divnewPic").attr("data-percent",data.content.newPicPlanPercent);
        			//新增带看
        			$("#newLook").html(data.content.newLook);
        			$("#newLookPlan").html(data.content.newLookPlan);
        			$("#divnewLook").attr("data-percent",data.content.newLookPlanPercent);
        			//潜房新增 s
        			$("#hroppnew").html(data.content.hroppnew);
        			$("#hroppnewPlan").html(data.content.hroppnewPlan);
        			$("#divhroppnew").attr("data-percent",data.content.hroppnewPlanPercent);
        			//房源跟进
        			$("#htrackValid").html(data.content.htrackValid);
        			$("#htrackValidPlan").html(data.content.htrackValidPlan);
        			$("#divhtrackValid").attr("data-percent",data.content.htrackValidPlanPercent);
        			//独家委托 s
        			$("#hexcl").html(data.content.hexcl);
        			$("#hexclPlan").html(data.content.hexclPlan);
        			$("#divhexcl").attr("data-percent",data.content.hexclPlanPercent);
        			//客户激活
        			$("#custactive").html(data.content.custactive);
        			$("#custactivePlan").html(data.content.custactivePlan);
        			$("#divcustactive").attr("data-percent",data.content.custactivePlanPercent);
        			//客户跟进
        			$("#ctrack").html(data.content.ctrack);
        			$("#ctrackPlan").html(data.content.ctrackPlan);
        			$("#divctrack").attr("data-percent",data.content.ctrackPlanPercent);
        			//公转私 s
        			$("#cpoolRcv").html(data.content.cpoolRcv);
        			$("#cpoolRcvPlan").html(data.content.cpoolRcvPlan);
        			$("#divcpoolRcv").attr("data-percent",data.content.cpoolRcvPlanPercent);
        			//备案 s
        			$("#hreg").html(data.content.hreg);
        			$("#hregPlan").html(data.content.hregPlan);
        			$("#divhreg").attr("data-percent",data.content.hregPlanPercent);
        			
        			$('.easy-pie-chart .number').each(function() {
                        var newValue = Math.floor($(this).attr("data-percent"));
                        $(this).data('easyPieChart').update(newValue);  
                        
                        if(newValue >= 75){
                        	//绿色
                        	$(this).addClass("visits");
                			$(this).removeClass("transactions").removeClass("bounce");
                			$(this).data('easyPieChart').options.barColor= 'green';
                        }else if(newValue < 50){
                        	//红色
                        	$(this).addClass("bounce");
                			$(this).removeClass("transactions").removeClass("visits");
                			$(this).data('easyPieChart').options.barColor= 'red';
                        }else {
                        	//大于50% 小于75% 黄色
                        	//黄色
                        	$(this).addClass("transactions");
                			$(this).removeClass("bounce").removeClass("visits");
                			$(this).data('easyPieChart').options.barColor= 'purple';
                        }
                        //$('span', this).text(newValue);
                    });
        		},
        		error: function(errors) {
        			//alert("获取工作统计数据失败！");
        			return false;
        		}
        	});	
        },buildMsg:function(e,d){
        	/**c:msg catagory d:return date*/
        	e.empty();
        	console.info(d);
        	console.info(e);
        	scroller=$("<div>").addClass("scroller");
        	ul=$("<ul>").addClass("feeds");
        	var booleans = false;
        	$(d).each(function(i,e){
        		li=$("<li>");
            	col1=$("<div>").addClass("col1");
            	
            	col1.dblclick(function(){
            		message.readSiteMsg(e.title,e.id);
            	});
            	cont= $("<div>").addClass("cont");
            	if(booleans){
            		cont.append($("<div>").addClass("cont-col1").append($("<div>").addClass("label ").append($("<i>").addClass("icon-bell"))));
            		booleans = false;
            	}else{
            		cont.append($("<div>").addClass("cont-col1").append($("<div>").addClass("label label-success").append($("<i>").addClass("icon-bell"))));
            		booleans = true;
            	}
            	
            	cont.append($("<div>").addClass("cont-col2").append($("<div>").addClass("desc").append(e.title)));
            	col1.append(cont);
            	col2=$("<div>").addClass("col2").append($("<div>").addClass("date").text(e.relativeDate));
            	ul.append(li.append(col1).append(col2));
        	});
//        	div.append(scroller.append(ul));
        	scroller.append(ul);
        	e.append(scroller);
        	scroller.slimScroll({
                size: '7px',
                color: '#a1b2bd',
                //position: isRTL ? 'left' : 'right',
                height: $(this).attr("data-height"),
                alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
                railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
                disableFadeOut: true
            });
        }
    };
};