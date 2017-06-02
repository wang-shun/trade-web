<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@page import="com.aist.common.utils.SpringUtils"%>
<%@page import="com.aist.uam.permission.remote.UamPermissionService"%>
<%@page import="com.aist.uam.permission.remote.vo.App"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	UamPermissionService uamPermissionService = SpringUtils.getBean("uamPermissionServiceClient");
	String appName = pageContext.getServletContext().getInitParameter("appName");
	App app = uamPermissionService.getAppByAppName(appName);
	pageContext.getSession().setAttribute("ctx", app.genAbsoluteUrl());
	request.setAttribute("domain", app.getDomain());
	List<App> appList = uamPermissionService.getAllApp();
	pageContext.getSession().setAttribute("appCtxList", appList);
	Map<String, String> appCtx = new HashMap<String,String>(appList.size());
	for(App appitem : appList){
		appCtx.put(appitem.getAppName(), appitem.genAbsoluteUrl());
	}
	pageContext.getSession().setAttribute("appCtx", appCtx);
 %>
<HTML>
<HEAD>
<TITLE>SOA</TITLE>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
</head>
<body>

	<iframe id="siteIframe" name="siteIframe" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%">
	</iframe>

 <content tag="local_script">
 	<link href="${ctx}/js/ligerui/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
 	<script src="${ctx}/js/ligerui/ligerui.all.js" type="text/javascript"></script>
 <script type="text/javascript">
	var ctx="${ctx}";
	 document.domain='${domain}';
    window.ctx=ctx;
         var appCtx={};
	  <c:forEach items="${appCtxList}" var="app">
	     appCtx['${app.appName}'] = '${app.genAbsoluteUrl()}';
	  </c:forEach>

function openDialog(option){
   	var query = $; var dailogOption = option.dialog;
   	if(!dailogOption.data){dailogOption.data = {};}
   	if (self != top) { query = parent.$; dailogOption.data.frameId = window.frameElement.id; }
   	if(option.callBack){ var callBack = option.callBack; dailogOption.data.callBack = callBack; }
   	query.ligerDialog.open(dailogOption); 
}
function closeDialog(options){
  	var dialog = frameElement.dialog; 
  	var dialogData = dialog.get("data");
 	if(dialogData.callBack){ 
 		var userDefinedCallback ;
        if(options && options.data){ userDefinedCallback = dialogData.callBack+'(\''+JSON.stringify(options.data)+'\')'; }else{ userDefinedCallback = dialogData.callBack+"()"; }
	   	if(dialogData.frameId){ parent.document.getElementById(dialogData.frameId).contentWindow.eval(userDefinedCallback); }else{ parent.eval(userDefinedCallback); }
    }
  	dialog.close();
} 
 
 function myiFrameHeight() {
	
	     var ifm= document.getElementById("siteIframe");
	
	     var subWeb = document.frames ? document.frames["siteIframe"].document :ifm.contentDocument;
	
	         if(ifm != null && subWeb != null) {
	
	         ifm.height = subWeb.body.scrollHeight;
	
	         }
	
	 }   
	 function iFrameHeight(h){
		 var ifm= document.getElementById("siteIframe");
		 ifm.height=h;
	 }
 	$(function(){
        	$("#siteIframe").attr("src",appCtx['aist-message-web']+"/site/siteList.html?source=TRADE");/* .bind("onload",myiFrameHeight); */
        	iFrameHeight(600);
    });
         /*========================加载消息::开始=========================*/
var message = {
	getMessage:function(){
	    $.ajax({  
            type : "get",  
            //async : false,  
            url:appCtx['aist-message-web']+"/site/siteIndex.json?page.size=5",  
            dataType : "jsonp",  
            jsonp : "callback", //传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)  
            jsonpCallback : "handler", //自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据  
            success : function(data) {  
            	 if(data){
            		$("#messageul").empty();
		        	message.createMsgTip(data,$("#messageul"));
	        	 }
            },  
            error : function() {  
                console.error('An error occurred getMessage()');  
            }  
        });  
	} ,
	createMsgTip:function(data,e){
		e.empty();

		$("#sp_badge").text(data.totalElements);
		if(data.content && data.content.length > 0){
			for(var i=0;i<data.content.length;i++){
				var obj = data.content[i];
				var li =this.createMsgLi(obj); 
				e.append(li);
				e.append($("<div>").addClass("divider"));
				}
		}

		e.append($("<li>").addClass("text-center link-block").append($("<div>")).append($("<a>").attr({href:ctx+"/message/box/siteList.html",target :"messageContent"}).append($("<i>").addClass("fa fa-envelope")).append($("<strong>").text("去消息中心"))));
		return e;
	},
	createMsgLi:function(obj){
		
		var li=$("<li>");
		var messageBox=$("<div>").addClass("dropdown-messages-box");
		var mediaBody=$("<div>");
		var t=$("<strong>").text(obj.title);
//		var c=$("<strong>").text(obj.centent);
		var small=$("<small>").addClass("text-muted").text(obj.senderTime);
		messageBox.append(mediaBody.append(t).append($("<br>")).append(obj.content).append($("<br>")).append(small));
		li.append(messageBox);
		return li;
	},
	readSiteMsg:function(title,id){
		var options = {
		        dialogId : "readSiteMsg", 
		        dialog : {
		            title : title,
		            height : 390,
		            width : 700,
		            url : appCtx['aist-message-web']+"/site/siteDetail.html?id="+id,
		            buttons: [
		               { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
		            ]
		        }
		    };
		    openDialog(options);
	}
};																										
    </script>
    </content>
</body>
</html>