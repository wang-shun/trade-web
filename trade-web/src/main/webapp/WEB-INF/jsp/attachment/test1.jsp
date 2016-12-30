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
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="${ctx}/static/css/animate.css" />
    <link rel="stylesheet" href="${ctx}/static/css/style.css" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">


    <link href="${ctx}/static/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" >


    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/caseDetail.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/details.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/guohu.css" >

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


   <%--  <script src="http://trade.centaline.com:8083/trade-web/static/js/jquery-2.1.1.js"></script>
        <script type="text/javascript">
	    var ctx = '${ctx}';
	    window.ctx=ctx;
    </script>--%>
    <script src="${ctx}/js/plugins/required/require.js" data-main="${ctx}/js/plugins/required/config.js"></script> 
    <script>
      /*    $(function() {
				    	
    	 }); */
    </script>

</body>
</html>