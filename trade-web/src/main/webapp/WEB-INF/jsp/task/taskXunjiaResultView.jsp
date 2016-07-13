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
                            								  <div class="form-group"><label class="col-sm-2 control-label">单价：2.8万</label>
																<div class="col-sm-10"></div>														
                                							  </div>     
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">总价：280万</label>
																<div class="col-sm-10"></div>														
                                							  </div>                          							  
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">评估编号：09003871</label>
																 <div class="col-sm-10"></div>
                                							  </div>
                                							  
                                							   
                                							
								                                	<div class="form-group"><label class="col-sm-2 control-label">是否接受</label>									
									                                    <div class="col-sm-10">
									                                    <select class="form-control m-b" name="account">
									                                        <option>接受</option>
									                                        <option>不接受</option>
									                                    </select>								                                        
									                                    </div>
									                                </div>	                               
                            							</form>
                            						 	 <a href="#" class="btn" >保存</a>
														 <a href="#" class="btn btn-primary" >提交</a>  
                            						</div>
                        					  </div> 
<!--                         					  <div id="caseCommentList" class="add_form">
</div> -->
                        					               
</div>
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
        $(document).ready(function () {
            // Examle data for jqGrid

            var reminderdata = [
                          {reminder_item: "产证原件", comment: ""},
                          {reminder_item: "居间协议", comment: ""},
                          {reminder_item: "房屋买卖合同", comment: "中原版"},
                          {reminder_item: "买卖双方证件", comment: ""},
                          {reminder_item: "公证委托书原件、委托人证件复印件、代理人证件原件", comment: "主体无法到场时"},
                          {reminder_item: "授权委托书原件、代理人证件原件、公章", comment: "主体无法到场时,且一方主体为公司时"},
                          {reminder_item: "营业执照、税务登记证、组织机构代码证、法人代表身份证", comment: "主体为公司时"}
                         ];
            
            var prepareData = [
                                {prepare_item: "产调", attachment: "xxx的产调.jpg"},
                               ];   

		$("#reminder_list").jqGrid({
			
			 data: reminderdata,
             datatype: "local",
             height: 100,
             multiselect: true,
             autowidth: true,
             shrinkToFit: true,
             colNames: ['提醒事项', '备注'],
             colModel: [
				 {name: 'reminder_item', index: 'reminder_item',  width: 500},
                 {name: 'comment', index: 'comment',  width:500},
                
             ],
			
			
		});
		
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
            
/* 	  	$("#caseCommentList").caseCommentGrid({
			caseCode : null,
			srvCode : null
	}); */
        });

        

 
        
        
        
        
    </script>
</content>
</body>


</html>