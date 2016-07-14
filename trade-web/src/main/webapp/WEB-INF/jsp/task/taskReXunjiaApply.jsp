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
									
        <div class="row">                           
                                              <div class="ibox-title">
                           							 <h5>填写任务信息 </h5>
                            						<div class="ibox-content">
                            							<form method="get" class="form-horizontal">
                            								  <div class="form-group"><label class="col-sm-2 control-label">期望价格</label>
																<div class="col-sm-10"><input type="text" class="form-control"></div>														
                                							  </div>                             							  
                      
                                							  <div class="form-group"><label class="col-sm-2 control-label">异议申请备注</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                										                               
                            							</form>
                            						
                            						</div>
                        					  </div> 
                        					                                    
<!-- 	</div> -->
<!-- 	<div id="caseCommentList" class="add_form">
</div> -->
<content tag="local_script">
    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
   <!-- Data picker -->
   <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script>
      $(function(){
/*     		$("#caseCommentList").caseCommentGrid({
				caseCode : null,
				srvCode : null
		}); */
      });
</script>
</content>
</body>


</html>