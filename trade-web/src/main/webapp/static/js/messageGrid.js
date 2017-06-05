var messageGrid = function () {
    return {
    	_config:{e:{},c:"",autoGetMsg:true,sendTime:1000*60*5},
    	_tagElement:{},
    	_messageServer:"",
    	_timer:null,
		_msgCatagory:"",
		_ctx:"",
    	/**t：目标元素，s:消息类型下拉id,u:message服务地址*/
        init: function (config) {
        	 this._config=$.extend(this._config,config);
             this._tagElement=this._config.e;
             this._messageServer=this._config.u;
             this._msgCatagory=this._config.c;
			 this._ctx=this._config.ctx;
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
                    		 _this.buildMsg(te,data.content,data.totalElements);
        	        	 }
                    },  
                    error : function() {  
                        console.error('An error occurred getMessage()');  
                    }  
                });
        },buildMsg:function(e,d,a){
        	/**c:msg catagory d:return date*/
        	e.empty();
        	E=e;
        	if($.isEmptyObject(d)){
        		/*feedElement=$("<div>").addClass("feed-element");
        		feedDiv=$("<div>").addClass("media-body");
            	title=$("<strong>").text("");
            	contentDiv=$("<div>").text("暂无未读消息");
            	sendTime=$("<small>").addClass("text-muted").text("");
 
            	feedElement.append(feedDiv.append(title).append(contentDiv).append(sendTime));
 
            	E.append(feedElement);*/
        	}else{
        		$(E).closest(".msGrid").removeClass("no-records");
	        	$(d).each(function(i,e){
	        		imgUrl = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"
					+ e.employeeCode + ".jpg";
	        		feedElement=$("<div>").addClass("feed-element");
	        		feedDiv=$("<div>").addClass("media-body");
	            	title=$("<strong>").text(e.title);
	            	 var urlRegu = /([a-zA-z]+:\/\/[^\s]*)/gi;
	                 var urlTemplate = "<a href=\"$1\" target=\"_blank\">$1</a>";
	                
	            	contentDiv=$("<div>").html(e.content);
	            	contentDiv.html().replace(urlRegu, urlTemplate);
	            	sendTime=$("<small>").addClass("text-muted").text(e.senderTime);
	            	title.dblclick(function(){
	            		message.readSiteMsg(e.title,e.id);
	            	});
	            	imgSpan=$("<span>").addClass("shead");
	            	var img = $('<img>').addClass('img-circle himg').attr('src', imgUrl).load(function() {
	            		this.parentNode.style.backgroundImage="url("+this.src+")";
					});
	            	imgSpan.append(img);
	            	
					feedElement.append($('<a>').addClass('pull-left').append(imgSpan));
	            	feedElement.append(feedDiv.append(title).append(contentDiv).append(sendTime));
	 
	            	E.append(feedElement);
	        	});
	        	$(e).closest(".ibox").find(".label-success").text(a);
        	}
			handleScrollers();
        }
    };
};