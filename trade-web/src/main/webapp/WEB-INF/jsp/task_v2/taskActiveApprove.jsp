<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
</head>
<body>
									
        <div class="ibox-title">
           <h5>填写表单，完成任务 </h5>
             <div class="ibox-content">
             <div class="radio">
             	<label> <input type="radio" checked="" value="option1" id="approve_yes" name="optionsRadios"> 同意 </label>
             </div>
             <div class="radio">
             	<label> <input type="radio" value="option2" id="approve_no" name="optionsRadios"> 不同意服务激活 </label>
             </div>
				 <a href="#" class="btn" >保存</a>
				 <a href="#" class="btn btn-primary" >提交</a>  
				 </div>                 							
        </div>      

<!-- <div id="caseCommentList" class="add_form">
</div> -->
<content tag="local_script">
    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script>
 
</script>
</content>
</body>


</html>