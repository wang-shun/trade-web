/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
/**
 * li 中id 必须是如 li_1 or li_2 
 * li 对应的 div中的 id  也是  tab_1 or tab_2 原因方便截取查找
 */
var TabPage = function () {
	//ul 的id
	var tab_ul="tab_ul";//默认tab_ul
	
	//显示内容div id  
	var tab_content="tab-content-1"; //默认tab-content-1
	
	//需要拷贝复制的内容 div id
	var divclone="custDelDemand";
	
	var addTabPage= function () {
		var last_ul=$("#"+tab_ul);
		//倒数第1个li
		var last_li=$("#"+tab_ul+">li:nth-last-child(1)");
		var last_li_id=last_li.attr("id");
		var idNo=last_li_id.split("_");
		//当前编号
		var fag=Number(idNo[1]);
		
		//下一个
		var count=fag+1;
		var idStr="li_"+count;
		var tabId="tab_"+count;
		var hrefStr="#"+tabId;
		
		//删除按钮 
		var del_icon="<i onclick='TabPage.removeTabPage(this)' class='icon-trash m-icon-white'></i>";
		
		last_ul.append("<li class='active' id='"+idStr+"' ><a href='"+hrefStr+"' data-toggle='tab'>需求"+count+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+del_icon+"</a></li>  ");
		
		//新clonne一个DIV
		var divStr="<div class='tab-pane active' id='tab_"+count+"'>"
		+"</div>";
		
		$("#"+tab_content).append(divStr);
		
		
		//clone from 
		$("#tab_"+count).append($("#customer_from").clone());
		//不clone div
		//$("#tab_"+count).append($("#"+divclone).clone());
		
		CustomerDelAdd.init('${ctx}/customerdel/save','customer_add','customer_from');
	    CustomerDelAddValidator.init('customer_from','error');
		
		
		
		
		
		//不显示当前
		$("#"+tab_ul).find("li").each(function(){
			//console.log($(this));
			$(this).attr("class","");
		}); 
		//显示新增加的层
		
		$('#'+tab_ul+' a[href="#'+tabId+'"]').tab('show'); 
		
		//$("#tab_"+fag).attr("class","tab-pane");
		
		
		//时间空间初始化
		Datetimepicker.init("form_datetime","yyyy-mm-dd");
		
		
		//$(hrefStr).load("toCustDelDemandAdd"); // ajax加载页面
		
	};
	
	
	return {
		
		/**
		 * ul   tab页对应  ul的id
		 * div  tab页对应  div id
		 * divc 新增tab页  需要拷贝复制的内容 div
		 */
		init:function(ul,div,divc){
			tab_ul=ul;
			tab_content=div; 
			divclone=divc;
			
			//把对于的addTabPage 加载到对应的btn 或者a 中
				$("#addTabPage").click(
					function (){
						addTabPage();
					}	
					
				);
			
		},
		/**
		 * 移除一个 tab页  =>移除一个  li和对应的 div
		 * 参数 i 是this 当前 移除的 li
		 */
		removeTabPage:function(i){
			//console.log(i.parentNode.parentNode);
			//移除tab
			var li_id=i.parentNode.parentNode.id;
			var count=li_id.split("_")[1];
			i.parentNode.parentNode.parentNode.removeChild(i.parentNode.parentNode);
			//console.log(count);
			$("#tab_"+count).remove();
			//显示上一个div
			//var  fag=Number(count)-1;
			//显示第一个
			var fag=1;
			
			$("#"+tab_ul).find("li").each(function(){
				//console.log($(this));
				$(this).attr("class","");
			}); 
			
			$('#'+tab_ul+' a[href="#tab_'+fag+'"]').tab('show'); 
			
			//$("#tab_"+fag).attr("class","tab-pane");
			
		}
		
	};
}();


   