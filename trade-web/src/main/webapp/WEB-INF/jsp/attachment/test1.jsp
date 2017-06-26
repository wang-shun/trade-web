<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
	<meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>过户
    </title>
    <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/style.css' />" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.bootstrap.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.responsive.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
    <link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">


    <link href="<c:url value='/static/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" >
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" >
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" >


    <link rel="stylesheet" href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" >
    <link rel="stylesheet" href="<c:url value='/static/trans/css/workflow/details.css' />" >
    <link rel="stylesheet" href="<c:url value='/static/trans/css/workflow/guohu.css' />" >

</head>
<body class="">


	<h1>ddd</h1>
       
    <!-- 右侧页面主体内容 -->
    <input type="hidden" id="ctx" value="http://trade.centaline.com:8083/trade-web">
    <div class="mt30">
        <h2 class="newtitle title-mark">上传附件</h2>
        
        <div class="table-box" id="fileUploadContainer">
            
        </div>
    </div>


    <%--  <script src="http://trade.centaline.com:8083/trade-web/static/js/jquery-2.1.1.js"></script> --%>
    <script>
   /*  require(['jquery','aistFileUpload','aistMath'], function($,aistFileUpload) {
        console.log($().jquery);
        console.log(aistMath.add(3,5));
        aistFileUpload.init({
    		caseCode : "ZY-SH-201609-0035",
    		partCode : "SpvApplyApprove",
    		fileUploadContainer : "fileUploadContainer"
    	}); 
    }); */
    require(['main'], function() {
    	requirejs(['aistFileUpload'],function(aistFileUpload){
		    aistFileUpload.init({
	    		caseCode : "ZY-SH-201609-0035",
	    		partCode : "SpvApplyApprove",
	    		fileUploadContainer : "fileUploadContainer"
	    	}); 
	    });
    });
    </script>

</body>
</html>