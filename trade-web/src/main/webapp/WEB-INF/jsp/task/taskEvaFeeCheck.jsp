<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>									
        <div class="row">                           

                                              <div class="ibox-title">
                           							 <h5>填写任务信息 </h5>
                            						<div class="ibox-content">
                            							<form method="get" class="form-horizontal">
                            								  <div class="form-group"><label class="col-sm-2 control-label">有助于助理进行判断的信息</label>
																<div class="col-sm-10"><input type="text" class="form-control"></div>														
                                							  </div>                             							  
                                	
															   <div class="form-group"><label class="col-sm-2 control-label">是否已经收费</label>
								
								                                    <div class="col-sm-10">
								                                   		<div class="radio"><label> <input type="radio" checked="" value="true" id="optionsRadios1" name="optionsRadios">已收费 </label></div>
                                       					   			    <div class="radio"><label> <input type="radio" value="false" id="optionsRadios2" name="optionsRadios">未收费</label></div>
                                							
								                                    </div>
								                                </div>
								                                
								                              <div class="form-group"><label class="col-sm-2 control-label">备注</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>								                               
                            							</form>
                            						 <a href="#" class="btn" >保存</a>
														 <a href="#" class="btn btn-primary" >提交</a>
                            						
                            						</div>
                        					  </div> 
                        					  
<!--            <div id="caseCommentList" class="add_form">
</div>       -->       					                                  

<content tag="local_script">
    <!-- Peity -->
    <script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>

    <!-- jqGrid -->
    <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
    <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>

    <script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
    <script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
   <!-- Data picker -->
   <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>


	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script>

        
    </script>
</content>
</body>


</html>