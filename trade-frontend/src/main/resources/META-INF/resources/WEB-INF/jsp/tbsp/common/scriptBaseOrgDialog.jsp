<%@page import="java.util.List"%>
<%@page import="com.aist.uam.permission.remote.vo.App"%>
<%@page import="com.aist.common.utils.SpringUtils"%>
<%@page import="com.aist.uam.permission.remote.UamPermissionService"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script> var ctx = '${ctx}';
         var appCtx={};
	  <c:forEach items="${appCtxList}" var="app">
	     appCtx['${app.appName}'] = '${app.genAbsoluteUrl()}';
	  </c:forEach>
</script>
<%-- <link href="${ctx}/static/tbsp/js/ligerui/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />  --%>
<link href="${ctx}/static/tbsp/js/ligerui/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />  
<link href="${ctx}/static/tbsp/js/ligerui/skins/Aqua/css/gzl-style.css" rel="stylesheet" type="text/css" />  
<link href="${ctx}/static/tbsp/js/ligerui/skins/Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" /> 
<link href="${ctx}/static/tbsp/css/fonts/font-awesome.css" rel="stylesheet" type="text/css" />  
<link href="${ctx}/media/css/midifiedStyle.css" rel="stylesheet" type="text/css">
<link href="${ctx}/media/css/custom-mika.css" rel="stylesheet" type="text/css">
<%-- <script src="${ctx}/static/tbsp/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script> --%>
<%-- <script src="${ctx}/static/tbsp/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>    
 --%><script src="${ctx}/static/tbsp/js/jquery-blockUI/jquery.blockUI.js" type="text/javascript"></script>
<script src="${ctx}/static/tbsp/js/jquery/jquery.components.js" type="text/javascript"></script>    
<script src="${ctx}/static/tbsp/js/ligerui/ligerui.all.js" type="text/javascript"></script> 
<script src="${ctx}/static/tbsp/js/json/json2.js" type="text/javascript"></script>
<script src="${ctx}/static/tbsp/js/pagebar/pagebar.js" type="text/javascript"></script>

<script type="text/javascript">
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
//操作提示
$(function(){ $.extend({notice : function(msg, type) {if (type == null) {type = "success";}if ($("#msgNotice").length > 0) {$("#msgNotice").removeClass("alert-success alert-info alert-warning alert-error");} else {$('<div class="alert notice" id="msgNotice"/>').appendTo($("body"));}$("#msgNotice").addClass("alert-" + type);$("#msgNotice").text(msg).fadeIn('slow').fadeOut(3000);}});});
$(function(){ try{$("body").bind("click",function(){ if(parent.menu){parent.menu.closeMenu();} });}catch(e){}});
</script>