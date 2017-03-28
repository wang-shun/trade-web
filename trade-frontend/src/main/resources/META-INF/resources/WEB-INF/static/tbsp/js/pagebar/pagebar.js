/* 
 * ===AJAX分页控件===
 * author:zhangHuiBo
 * date:2014-11-18
 */  
;(function($, window, document, undefined){  
	var name = "pagebar";
    var instance = null;
    var settings;  
    var page;  
    defaults = {
		 currPageNum:1,      //currPageNum：当前页码
         pageRowSize:10,     //pageRowSize:每页记录数(不配置，则默认为12) 
         totalRowSize:0,     //totalRowSize:总记录数
         params:null,        //json格式参数
         queryFormId:null,   //查询参数  指定form表单的id将自动获取表单数据
         pageCallback:null   //自定义构建html回掉函数
    };
    function Plugin(element, options){
        this.settings = $.extend({},defaults,options);  
        this.settings.pageWidget = $(element.get(0));  
        page = new Page(this.settings.currPageNum,this.settings.totalRowSize,this.settings.pageRowSize);
        var paramStr = ""; 
        if(this.settings.queryFormId){
            paramStr = $("#"+this.settings.queryFormId).serialize();
        }
        if(this.settings.params){
        	if(paramStr){
        		paramStr += "&" + makeParamStr(this.settings.params);
        	}else{
        		paramStr = makeParamStr(this.settings.params);
        	}
        }
        this.settings.pageWidget.data("paramStr",paramStr);
        //开始获取数据  
        fetchData(page.getCurrPageNum(),this.settings);  
        return this;  
    }; 
    Plugin.prototype = {
    		reload:function(firstPage){
    			if(firstPage && firstPage == true){
    				fetchData(1,this.settings); 
    			}else{
    				fetchData(page.getCurrPageNum(),this.settings); 
    			}
    		}
    };
    /* 将json转换为请求参数字符串 */  
    var trunParamConfig2RequestParamStr = function(json){  
        var str = "";  
        for(key in json){  
            if(str==""){  
                str += key+"="+json[key];  
            }else{  
                str += "&"+key+"="+json[key];  
            }  
        }  
        return str;  
    };  
      
    /* 将配置参数拼接为请求字符串 */  
    var makeParamStr = function(param_json){  
        if(param_json==null){  
            return "";  
        }else{  
            return trunParamConfig2RequestParamStr(param_json);  
        }  
    };  
    /* 负责获取后台数据，获取完毕后会触发构建分页控件 */  
    var fetchData = function(currPageNum,options){  
        page.setCurrPageNum(currPageNum);  
        var firstResult = page.getFirstResult();  
        var maxResult = page.getMaxResult();  
        var pageRowSize = page.getPageRowSize(); 
        
        var data = null; 
        var paramStr = options.pageWidget.data("paramStr"); 
        if(paramStr){  
            data = paramStr + "&page.size="+pageRowSize+"&page.pn="+currPageNum;  
        }else{  
            data = "page.size="+pageRowSize+"&page.pn="+currPageNum;  
        } 
        var url = options.url;
        
        if(url.indexOf("?")>0){
    		url = url + "&ajax_need_parma_date_rurr="+new Date();
    	}else{
    		url = url + "?ajax_need_parma_date_rurr="+new Date();
    	}
        $.ajax({  
            type :"POST",  
            url : url,  
            data :data,
            success :function(datas){  
            	if(datas && !datas.success && datas.msg){
            		options.pageCallback(null,0); //执行回调函数
            		$.notice(datas.msg,"error");
            	}else{
            		page = new Page(Number(datas.number)+1,datas.totalElements,datas.size);  
            		buildPage(page,options);//触发构建分页控件 
            		options.pageCallback(datas.content,page.getFirstResult()); //执行回调函数 
            	}
            },  
            error:function(xmlHttpRequest,textStatus,errorThrown){  
                if(textStatus == "error"){  
                    var error = $.parseJSON(xmlHttpRequest.responseText);  
                    alert("Sorry："+error.errorCode+"，"+error.message+"！");  
                }  
            }  
        });  
    };  
      
    var trunTargetPage = function(pageNum,options){  
        fetchData(pageNum,options);  
    };  
      
    /* 为分页控件绑定事件 */  
    var bindEvent = function(options){  
        var links = options.pageWidget.find("a"); 
        $.each(links,function(i,link){  
            var link = $(link);  
            var pageNum = link.attr("pageNum"); 
            link.click(function(){  
                trunTargetPage(pageNum,options);  
            });  
        });
        options.pageWidget.find("input[type='button']").click(function(){
            gotoPage(options);
        });  
    }; 
     /*跳转到输入页码页*/
     var gotoPage = function(options){
         var gotoPageInput = options.pageWidget.find("input[class='gotoPageInput']").val();
         if(!gotoPageInput || !Number(gotoPageInput)){return ;}
         if(Number(gotoPageInput) <= Number(page.getTotalPageNum()) && Number(page.getTotalPageNum()) >= 1){
             trunTargetPage(Number(gotoPageInput),options);
         }
     };  
      
    /* 构建分页控件的具体算法实现 */  
    var buildPage = function(page,options){  
        //构建分页控件前，先清理现有分页 
    	options.pageWidget.empty();
    	options.pageWidget.append("<div class='digg4 metpager_8'>");  
        var currPageNum = Number(page.getCurrPageNum());  
        var totalPageNum = Number(page.getTotalPageNum());  
        var totalRowSize = Number(page.getTotalRowSize());
        if(totalPageNum < 1){
        	return ;
        }
        if(currPageNum == 1){ 
        	options.pageWidget.find("div[class='digg4 metpager_8']").append("<span class='disabled disabledfy'>上一页</span>"); 
        }else{  
        	options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+(currPageNum-1)+"' href='javascript:void(0);' title='上一页'>上一页</a>");  
        }  
        
        if(totalPageNum <= 7){  
            for(var i=1;i <= totalPageNum;i++){  
                if(i==currPageNum){  
                	options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);' class='current'>"+i+"</a>");  
                }else{  
                	options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);'>"+i+"</a>");  
                }  
            }  
        }else{  
            if(currPageNum < 5){  
                for(var i = 1;i < 7 ;i++){  
                    if(i==currPageNum){  
                    	options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);' class='current'>"+i+"</a>"); 
                    }else{  
                    	options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);'>"+i+"</a>");  
                    }  
                }  
             //如果当前页>5，且总页数与当前页的差<2  
            }else if(totalPageNum-currPageNum > 3){
            	 //显示1页
                for(var i = 1;i <= 1 ;i++){
                     options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);'>"+i+"</a>");  
                }
                //第一页后面加省略号  
                options.pageWidget.find("div[class='digg4 metpager_8']").append("<span>...</span>"); 
                for(var i=currPageNum-2;i<=currPageNum+2;i++){  
                     if(i==currPageNum){  
                         options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);' class='current'>"+i+"</a>"); 
                     }else{  
                         options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);'>"+i+"</a>");  
                     } 
                }  
            }else{
               //显示1,2页
               for(var i = 1;i <= 1 ;i++){
                    options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);'>"+i+"</a>");  
               }
               //第二页后面加省略号  
               options.pageWidget.find("div[class='digg4 metpager_8']").append("<span>...</span>"); 
               for(var i=totalPageNum-5;i<=totalPageNum;i++){  
                    if(i==currPageNum){  
                        options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);' class='current'>"+i+"</a>"); 
                    }else{  
                        options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+i+"' href='javascript:void(0);'>"+i+"</a>");  
                    } 
               }  
            }
            if(totalPageNum > 7 && totalPageNum-currPageNum > 3){
            	options.pageWidget.find("div[class='digg4 metpager_8']").append("<span> ... </span>"); 
                //最后一页
                options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+totalPageNum+"' href='javascript:void(0);'>"+totalPageNum+"</a>");  
            }
        }  
        
        if(totalPageNum == currPageNum || totalPageNum == 0){  
            options.pageWidget.find("div[class='digg4 metpager_8']").append("<span class='disabled disabledfy'>下一页</span>");  
        }else{ 
        	options.pageWidget.find("div[class='digg4 metpager_8']").append("<a pageNum='"+(currPageNum+1)+"' href='javascript:void(0);'>下一页</a>");  
        }  
        options.pageWidget.find("div[class='digg4 metpager_8']").append("<span class='' style='margin-left:10px;'>共"+totalPageNum+"页"+totalRowSize+"条记录</span>");  
        if(totalPageNum == 0){
        	options.pageWidget.find("div[class='digg4 metpager_8']").append("<span>&nbsp;&nbsp;到第<input type='text' class='gotoPageInput' style='width:30px;text-align:center;' value='"+currPageNum+"' onkeyup='javascript:RepNumber(this)' disabled='disabled'/>页<input type='button' class='btndisable' disabled='disabled' value='确定'/></span>");  
        }else{
        	options.pageWidget.find("div[class='digg4 metpager_8']").append("<span>&nbsp;&nbsp;到第<input type='text' class='gotoPageInput' style='width:30px;' value='"+currPageNum+"' onkeyup='javascript:RepNumber(this)'/>页&nbsp;<input type='button' class='btn' value='确定'/></span>");  
        }
        options.pageWidget.append("</div>"); 
        
        /*绑定点击事件 */ 
        bindEvent(options);  
    }; 
    /* Page类  */  
    function Page(currPageNum,totalRowSize,pageRowSize){
    	if(currPageNum==null){
    		currPageNum=1;
    	}
    	if(totalRowSize==null){
    		totalRowSize=0;
    	}
    	if(pageRowSize==null){
    		pageRowSize=0;
    	}
        this.currPageNum = currPageNum;  
        this.totalRowSize = totalRowSize;  
        this.pageRowSize = pageRowSize;  
    }  
    Page.prototype.getCurrPageNum = function(){  
        return this.currPageNum;  
    };  
    Page.prototype.setCurrPageNum = function(currPageNum){  
        this.currPageNum = currPageNum;  
    };  
    Page.prototype.getTotalPageNum = function(){
        return (this.totalRowSize%this.pageRowSize==0)?(this.totalRowSize/this.pageRowSize):(Math.floor(this.totalRowSize/this.pageRowSize)+1);  
    };  
    Page.prototype.getTotalRowSize = function(){  
        return this.totalRowSize;  
    };  
    Page.prototype.setTotalRowSize = function(totalRowSize){  
        this.totalRowSize = totalRowSize;  
    };  
    Page.prototype.getPageRowSize = function(){  
        return this.pageRowSize;  
    };  
    Page.prototype.setPageRowSize = function(pageRowSize){  
        this.pageRowSize = pageRowSize;  
    };  
    Page.prototype.getFirstResult = function(){  
        if(this.getCurrPageNum()<=0) return 0;  
        return this.getPageRowSize() * (this.getCurrPageNum() - 1);  
    };  
    Page.prototype.getMaxResult = function(){  
        return this.getFirstResult() + this.getPageRowSize();  
    }; 
	$.fn[name] = function(arg) {
		instance = new Plugin(this, arg);
		if(null != instance){return instance;}
	    return this;
	};
      
})(jQuery, window, document);
/* 过滤非数字 */
var RepNumber = function(obj) {
    var reg = /^[\d]+$/g;
     if (!reg.test(obj.value)) {
         var txt = obj.value;
         txt.replace(/[^0-9]+/, function (char, index, val) {//匹配第一次非数字字符
            obj.value = val.replace(/\D/g, "");//将非数字字符替换成""
            var rtextRange = null;
            if (obj.setSelectionRange) {
                obj.setSelectionRange(index, index);
            } else {//支持ie
                rtextRange = obj.createTextRange();
                rtextRange.moveStart('character', index);
                rtextRange.collapse(true);
                rtextRange.select();
            }
        });
     }
 };

