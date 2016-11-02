/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 * 
 */

var JQAdvanceList = function () {

    return {
        //main function to initiate the module
        init: function (id,url,ctx,_onRowComplete,_getPageBar) {
           $.ajax({    
	        	//url : '${ctx}/api/hou/advHouListAjax',  
           	    url : url, 
	            dataType:"json",    
	            global:false,     
	            success: function(data){
	               var html = '';
	               var _cxt = ctx;
	               var curPage = data.page;
	               var totalPage = data.total;
	               //循环所有的rows,循环一次，创建一条数据
	               $.each(data.rows,function(index,item) {
	               	   // 该方法负责逻辑的处理
	               	   var ite = getNewItem(item,_cxt);
	               	   // 该方法只负责页面的展现
	               	   var div = createDiv(ite,_cxt);
	               	   
	               	   $("#"+id).append(div);
	               	   
	               	   eval(_onRowComplete+'(ite,index)'); 

	               });
	               
	               eval(_getPageBar+'(curPage,totalPage)'); 
	               //getPageBar(data.page,data.total); 
	            },
	             
	            error: function (data) {
                    alert("data:" + data.responseText);
                }
	       });
           
/*           $.ajax({    
	        	//url : '${ctx}/api/hou/advHouListAjax',  
          	    url : url, 
	            dataType:"json",    
	            global:false,     
	            success: function(data){
	               var html = '';
	               var _cxt = ctx;
                  var curPage = data.page;
	               var totalPage = data.total;
	               //循环所有的rows,循环一次，创建一条数据
	               $.each(data.rows,function(index,item) {
	               	   // 该方法负责逻辑的处理
	               	   var ite = getNewItem(item,_cxt);
	               	   // 该方法只负责页面的展现
	               	   var div = createDiv(ite,_cxt);
	               	   
	               	   $("#"+id).append(div);
	               	   
	               	   eval(_onRowComplete+'(ite,index)'); 

	               });
	               
	               eval(_getPageBar+'(curPage,totalPage)'); 
	               //getPageBar(data.page,data.total); 
	            },
	             
	            error: function (data) {
                   alert("data:" + data.responseText);
               }
	       });*/
	       
	       // 类似于oracle的decode函数
	       // added by xin.hu 2015-4-9
	       function getDecode(cellvalue, option1,value1,option2, value2, defauValue) {
            	var type = parseInt(cellvalue);
            	switch (type) {
            	case option1:
            		return value1;
            	case option2:
            		return value2;
            	default:
            		return defauValue;
            	}
           }
           
           /***
            *  处理取值的相关逻辑的判断
            */
           function getNewItem(item,cxt) {
/*            var houseDelId = item['houseDelId'];
        	  var favorClass = getDecode(item['isFavor'], 1,'glyphicons no-js heart rose_red',0, 'glyphicons no-js heart_empty rose_red', 'glyphicons no-js ');
              var keyClass = getDecode(item['isKey'], 1,'glyphicons no-js keys active various',0, 'glyphicons no-js keys', 'glyphicons no-js ');
              
              // 点击钥匙，需要弹出的钥匙信息的Iframe
              var keyHref = getDecode(item['isKey'], 1,cxt+'/hou/box/toShowHouKey',0, '#', '#');
              
              var cameraClass = getDecode(item['isCamera'], 1,'glyphicons no-js camera active',0, 'glyphicons no-js camera', 'glyphicons no-js ');
              var emailClass = getDecode(item['isEmail'], 1,'glyphicons no-js e-mail active',0, 'glyphicons no-js e-mail', 'glyphicons no-js ');
              var calendarClass = getDecode(item['isCalendar'], 1,'glyphicons no-js calendar active',0, 'glyphicons no-js calendar', 'glyphicons no-js ');
              var focusClass = getDecode(item['isFocus'], 1,'glyphicons no-js fire active',0,  'glyphicons no-js fire', 'glyphicons no-js ');
              var groupClass = getDecode(item['isGroup'], 1,'glyphicons no-js group active',0, 'glyphicons no-js group', 'glyphicons no-js ');*/
        	   
        	  var houseDelId = item.housedel.pkid;
         	  var favorClass = getDecode(item.housedel.isFavor, 1,'glyphicons no-js heart rose_red',0, 'glyphicons no-js heart_empty rose_red', 'glyphicons no-js ');
              var keyClass = getDecode(item.housedel.isKey, 1,'glyphicons no-js keys active various',0, 'glyphicons no-js keys', 'glyphicons no-js keys ');
               
              // 点击钥匙，需要弹出的钥匙信息的Iframe
              var keyHref = getDecode(item.housedel.isKey, 1,cxt+'/hou/box/toShowHouKey',0, '#', '#');
               
	          var cameraClass = getDecode(item.housedel.hasPicture, 1,'glyphicons no-js camera active',0, 'glyphicons no-js camera', 'glyphicons no-js camera');
	          var emailClass = getDecode(item.housedel.isEmail, 1,'glyphicons no-js e-mail active',0, 'glyphicons no-js e-mail', 'glyphicons no-js e-mail');
	          var calendarClass = getDecode(item.housedel.isCalendar, 1,'glyphicons no-js calendar active',0, 'glyphicons no-js calendar', 'glyphicons no-js calendar');
	          var focusClass = getDecode(item.housedel.isFocus, 1,'glyphicons no-js fire active',0,  'glyphicons no-js fire', 'glyphicons no-js fire');
	          var groupClass = getDecode(item.housedel.isGroup, 1,'glyphicons no-js group active',0, 'glyphicons no-js group', 'glyphicons no-js group');
        
        	  var labels = "<div class=\"span7\">";  // 循环房子的标签
/*        	  if(item.houseLabels !=null) {
        		  $.each(item.houseLabels,function(index,item){
            		  if(index == 0){
            			  labels = labels+"<a href=\"#\" class=\"btn red mini\">"+ item.houseLabel +"</a>&nbsp;";
            		  } else {
            			  labels = labels+"<span class=\"btn green mini\">"+ item.houseLabel +"</span>&nbsp;";
            		  }
            	  });
        	  }*/
        	  var subWayLabelClass = getDecode(item.house.isAnearSubway, 1,'btn green mini',0, 'btn red mini', '');
        	  var schoolDistrictLabelClass = getDecode(item.house.isSchoolDistrict, 1,'btn green mini',0, 'btn red mini', '');
        	  if(item.house.isAnearSubway !=null) {
        		  labels = labels+"<span class='"+ subWayLabelClass+"'>近地铁</span>&nbsp;";
        	  }
        	  if(item.house.isSchoolDistrict !=null) {
        		  labels = labels+"<span class='"+ schoolDistrictLabelClass+"'>学区房</span>&nbsp;";
        	  }
        	  labels = labels+"</div>";
        	  
        	  var houseImage = cxt+'/media/image/hou_none.jpg';
           	  // 若没有图片，则使用默认的图片
           	  if(item['houseImage'] !=null && item['houseImage'].length >0) {
           	   	  houseImage = cxt+item['houseImage'];
           	  }
           	  
        	  item.favorClass = favorClass;
        	  item.keyClass = keyClass;
        	  item.keyHref = keyHref;
        	  item.cameraClass = cameraClass;
        	  item.emailClass = emailClass;
        	  item.calendarClass = calendarClass;
        	  item.focusClass = focusClass;
        	  item.groupClass = groupClass;
        	  item.labels = labels;
        	  item.houseImage = houseImage;
        	  
        	  return item;
           }
           
           function createDiv(item,cxt){
           	   var div = '';
           	   /****
           	    *  对复杂的div的渲染需要按如下规则进行注释: 先按行分，再按列分 eg: 2.3.1 代表第二列,第三行,第一列中div的内容
           	    */
           	            div+= "<div class=\"row-fluid\">"
						div+="<div class=\"booking-blocks\">"
						// 1.1 : 图片
						 div+="<div class=\" span2 \">"
						 div+="<img src='"+ item.houseImage +"\' alt=\"\">"
						// 1.2 : 图片描述
						 div+="<ul class=\"unstyled\">"
						 div+="<li><i class=\"icon-map-marker\"></i>"+ item['houseMarker'] +"</li>"
						 div+="</ul>"	
						 div+="</div>"
						
						// 2
						 div+="<div class=\"span10\">"
						 div+="<div class=\"row-fluid\">"
						 div+="<div class=\"span3\">"
						// 2.1.1 : 名称 ; 2.1.2 : 房屋编号
						 div+="<label class='hou-title'><strong><a href=\""+ctx +"/hou/housDelInfo/yygg008\">"+ item.house.estateName +"</a>&nbsp;<a href='"+ctx+"/hou/houDelInfo/"+ item.housedel.houdelCode +"'>"+ item.housedel.houdelCode +"</a></strong></label>"
						 div+="</div>"
						// 2.1.3 : 标签列表
				         div+= item.labels
						 div+="<div class=\"span2\">"
						// 2.1.4 : 是否收藏
						 div+="<a href=\"#\" id=\"key\" class='"+ item.favorClass +"'><i>"+ item['favorLabel'] +"</i></a>"
						 div+="</div>"
						 div+="</div>"
						 div+="<div class=\"row-fluid\">"
						 div+="<div class=\"span6\">"
						// 2.2 
						 div+="<div class=\"row-fluid hou-list\">"
							// 2.2.1 : 房屋架构
							 div+="<label class=\"control-label span4 \"><p class=\"text-left\"><strong>"+ item.house.bedroomAmount +"室"+item.house.parlorAmount+"厅"+item.house.cookroomAmount+"厨"+item.house.toiletAmount+"卫</strong></p></label>"
							// 2.2.2 : 方向
							 div+="<label class=\"control-label span2\"><p class=\"text-left\"><strong>"+ item.house.orientation +"</strong></p></label>"
							// 2.2.3 : 房屋大小
							 div+="<label class=\"control-label span2\"><p class=\"text-left\"><strong>"+ item.house.buildSize +"平</strong></p></label>"
							// 2.2.4 : 楼层
							 div+="<label class=\"control-label span2\"><p class=\"text-left\"><strong>"+ item.house.floor +"/"+ item.house.totalFloor + "层</strong></p></label>"
							 div+="<label class=\"control-label span2\"><p class=\"text-left\"><strong></strong></p></label>"
						 div+="</div>"
						// 2.3
						    // 2.3.1 : 房屋描述
						 div+="<div class=\"row-fluid hou-list\">"
							 div+="<label class=\"control-label  \"><p class=\"text-left\">"+ item['description'] +" </p></label>"
						 div+="</div>"
						 div+="</div>"

						 div+="<div class=\"span2 hou-list\">"
						        // 2.2.5 : 总价格
								 div+="<label class=\"control-label span12\"><p class=\"text-left red\"><strong><a class=\"big-number \" href=\"#alertMessage\">"+ item.price.price +"万</a></strong></p></label>"
								// 弹出框
								 div+="<div id=\"alertMessage\" style=\"width:400px;overflow:auto;display: none;\">"
						         div+="<div><div class='label label-success'><i class=\"icon-bell\"></i></div>&nbsp;&nbsp;系统消息</div>"
						         div+="<div class=\"alert alert-info\" id=\"alertContent\">"
								 div+="<div>恭喜你，你现在已经身家数亿</div>"
								 div+="<div><strong>编号为 : </strong><span id=\"code\">110</span></div>"
								 div+="<div><strong>归属人 : </strong><span id=\"hoderName\">傻叉</span></div>"
								 div+="<div>你可在\"我的房源\"或\"角色房源\"列表中查看</div>"
								 div+="</div>"
								 div+="</div>"
							    // 2.3.2 : 单位价格
								 div+="<label class=\"control-label span12\"><p class=\"text-left red\">"+ (item.price.price/item.house.buildSize).toPrecision(2) +"万/平</label>"
						 div+="</div>"

						 div+="<div class=\"span2 hou-list\">"
						        // 2.2.6 : 跟进数量
								 div+="<label class=\"control-label row-fluid\"><strong><a class=\"fancybox fancybox.iframe\"  href=\"${ctx}/hou/toPicAdd\">"+ item['followCount'] +"</a>&nbsp;</strong>跟进</label>"
								// 2.3.3 : 带看数量
								 div+="<label class=\"control-label row-fluid\"><strong><a class=\"blue-number\">"+ item['showCount'] +"</a>&nbsp;</strong>带看</label>"
						 div+="</div>"
						 div+="<div class=\"span2 hou-list\">"
						        // 2.2.6 : 姓名
								 div+="<label class=\"control-label row-fluid\">"+ item['hdelHolderName'] +"</label>"
								// 2.3.4 : 地址
								 div+="<label class=\"control-label row-fluid\">"+ item['hdelHolderAddress'] +"</label>"
						 div+="</div>"
						 div+="</div>"
						// 2.4.1 : 钥匙; 2.4.2:相机; 2.4.3:Email; 2.4.4:日历; 2.4.5:组
						 div+="<div class=\"row-fluid hou-list-icon\">"
							 div+="<a href='"+ item.keyHref +"' id=\"key\" class='"+ item.keyClass +"'><i></i></a>"
							 div+="<a href=\"#\" id=\"pic\" class='"+ item.cameraClass +"'><i></i></a>"
							 div+="<a onclick=\"showQrcode(this\" id=\"post\" class='"+ item.emailClass +"'><i></i></a>"
							 div+="<a href=\"#\" id=\"callCalendar\" class='"+ item.calendarClass +"'><i></i></a>"
							 div+="<a href=\"#\" id=\"focus\" class='"+ item.focusClass +"'><i></i></a>"
							 div+="<a href=\"#\" id=\"groupSee\" class='"+ item.groupClass +"'><i></i></a>"
							 div+="<div class=\"qrcode\" id=\"qrcode\"  style=\"display:none\"></div>"
						 div+="</div>"
						 div+="</div>"
						 div+="</div>"
					 div+="</div>"
					 div+="<hr>";
					 
			    return div;
           }
        }
    };

    function onRowComplete(row, rowNum){
    	//点击总价格,弹出对话框
		$('.big-number').fancybox({
			'titlePosition': 'inside',
			'transitionIn': 'none',
			'transitionOut': 'none',
			afterClose:function(){
		    }
		});
    	
    	//点击钥匙，需要弹出iframe对话框
    	$('.various').fancybox({
	        type: 'iframe',
			padding : 0,
			margin:0,
			autoScale:false,
			fitToView	: false,
		 	width		: '80%', 
			height		: '50%',
			autoDimensions:true,
			closeClick	: false,
			iframe:{preload:false},
			openEffect	: 'none',
			closeEffect	: 'none',
			afterClose:function(){
				/*var  url=ctx+"/hou/toShowHouKey";
			    window.location.replace(url);*/
			}
	   });
    }
    
    /****
     *  点击查询按钮
     * 
     */
    function onClickBtnBlock() {
    	
    }
}();