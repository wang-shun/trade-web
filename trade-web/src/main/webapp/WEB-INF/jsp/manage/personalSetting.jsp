<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
</head>
<body>
									
        <div class="row">                           
             <div class="ibox-title">
                   <h5>完成提醒 </h5>
                       <div class="ibox-content">
                            <div class="jqGrid_wrapper">
                               	<table id="reminder_list"></table>                              			    
                           	</div>
                           				   	  <div class="ibox-title">
                        					  <button type="submit" class="btn btn-primary pull-right">发送短信提醒</button>                        				
                        					  </div>     
                            						</div>
                        					  </div>
                                              <div class="ibox-title">
                           							 <h5>填写任务信息 </h5>
                            						<div class="ibox-content">
                            							<form method="get" class="form-horizontal">
                            								  <div class="form-group"><label class="col-sm-2 control-label">实际过户时间</label>
																<div class="col-sm-10"><input type="text" class="form-control"></div>														
                                							  </div>    
                                							  <div class="form-group"><label class="col-sm-2 control-label">房产税</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							   <div class="form-group"><label class="col-sm-2 control-label">个人所得税</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">上家营业税</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">下家契税</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">土地增值税</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
								                              <div class="form-group"><label class="col-sm-2 control-label">备注</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>								                               
                            							</form>
                            						
                            						</div>
                        					  </div> 
                        					  
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
														 <a href="myTaskList" class="btn btn-primary" >提交</a>  
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
                                {prepare_item: "收件收据", attachment: "收件收据.jpg"},
                                {prepare_item: "抵押登记申请表", attachment: "抵押登记申请表.jpg"},
                                {prepare_item: "商业贷款利率页（有商业贷款）", attachment: ""},
                                {prepare_item: "过户确认书", attachment: ""},
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
        });

        

 
        
        
        
        
    </script>
</content>
</body>


</html>