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
															    <div class="form-group"><label class="col-sm-2 control-label">其他信息</label>
								
								                                    <div class="col-sm-10">
								                                    	<label class="checkbox-inline"> <input type="checkbox" value="option1" id="inlineCheckbox1"> 材料已上传 </label>
								                                     	 </div>
								                                </div>
								                                
								                              <div class="form-group"><label class="col-sm-2 control-label">备注</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>								                               
                            							</form>
                            						
                            						</div>
                        					  </div> 
                        					  
<!--                         					  <div id="caseCommentList" class="add_form">
</div> -->
                        					  <div class="ibox-title">
                           							 <h5>上传备件 </h5>
                            						<div class="ibox-content">                            			
<%--                             								<form id="my-awesome-dropzone" class="dropzone" action="#">
										                        <div class="dropzone-previews"></div>  
										                    	</form>     --%>     
										                <div class="jqGrid_wrapper">
                               						    <table id="prepare_list"></table>                              			    
                           				   		    	</div>          
                            						</div>
											             <a href="#" class="btn" >保存</a>
														 <a href="#" class="btn btn-primary" >提交</a>  
                        					  </div>                                     

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
        $(document).ready(function () {
            // Examle data for jqGrid

      
            
            var prepareData = [
                                {prepare_item: "产证", attachment: "xxx的产证.jpg"},
                                {prepare_item: "证件照", attachment: "小王.jpg"},
                                {prepare_item: "合同照片", attachment: "购房合同1.jpg"},
                               ];   

		
		$("#prepare_list").jqGrid({
			
			data: prepareData,
            datatype: "local",
            height: 100,
            autowidth: true,
            shrinkToFit: true,
            colNames: ['备件项', '附件'],
            colModel: [
				 {name: 'prepare_item', index: 'prepare_item',  width: 500},
                 {name: 'attachment', index: 'attachment',editable: true,edittype:'file', width:500}, 
            ],
			
			
		});
		
        Dropzone.options.myAwesomeDropzone = {

                autoProcessQueue: false,
                uploadMultiple: true,
                parallelUploads: 100,
                maxFiles: 100,

                // Dropzone settings
                init: function() {
                    var myDropzone = this;

                    this.element.querySelector("button[type=submit]").addEventListener("click", function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        myDropzone.processQueue();
                    });
                    this.on("sendingmultiple", function() {
                    });
                    this.on("successmultiple", function(files, response) {
                    });
                    this.on("errormultiple", function(files, response) {
                    });
                }

            }
		
        $('#data_1 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
		
            
            // Add responsive to jqGrid
	      $(window).bind('resize', function () {
                var width = $('.jqGrid_wrapper').width();
                $('#table_list_1').setGridWidth(width);
                $('#table_list_2').setGridWidth(width);
               
            }); 

        });

        
 
        
        
        
        
    </script>
</content>
</body>


</html>