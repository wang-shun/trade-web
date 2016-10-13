<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
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
                            								  <div class="form-group"><label class="col-sm-2 control-label">贷款签约时间</label>
																<div class="col-sm-10"><input type="text" class="form-control"></div>														
                                							  </div>                             							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">付款方式</label>
								
								                                    <div class="col-sm-10">
								                                        <div class="row">
								                                            <div class="col-md-2"><input type="text" placeholder="首期" class="form-control"></div>								                                           
								                                            <div class="col-md-3"><input type="text" placeholder="二期" class="form-control"></div>
								                                            <div class="col-md-3"><input type="text" placeholder="尾款" class="form-control"></div>
								                                        </div>
								                                    </div>
								                                </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">贷款类型</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							   <div class="form-group"><label class="col-sm-2 control-label">贷款总额</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">商贷部分金额</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">商贷部分年限</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">商贷部分利率折扣</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">公积金贷款金额</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">公积金贷款利率</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">主贷人姓名</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  <div class="form-group"><label class="col-sm-2 control-label">主贷人单位</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							    <div class="form-group"><label class="col-sm-2 control-label">放款方式</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							   <div class="form-group"><label class="col-sm-2 control-label">认定套数</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							   <div class="form-group"><label class="col-sm-2 control-label">备注</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>
                                							  
                                							  
								                              <div class="form-group"><label class="col-sm-2 control-label">信贷员信息</label>
								
								                                    <div class="col-sm-10">
								                                        <div class="row">
								                                            <div class="col-md-2"><input type="text" placeholder="姓名" class="form-control"></div>								                                           
								                                            <div class="col-md-3"><input type="text" placeholder="联系方式" class="form-control"></div>
								                                        </div>
								                                    </div>
								                                </div>
								                                
								                                	<div class="form-group"><label class="col-sm-2 control-label">贷款银行</label>									
									                                    <div class="col-sm-10">
									                                    <select class="form-control m-b" name="account">
									                                        <option>中国银行</option>
									                                        <option>工商银行</option>
									                                        <option>北京银行</option>
									                                        <option>农业银行</option>
									                                    </select>								                                        
									                                    </div>
									                                </div>
								                                
																	<div class="form-group"><label class="col-sm-2 control-label">支行</label>									
									                                    <div class="col-sm-10">
									                                    <select class="form-control m-b" name="account">
									                                        <option>龙岩路支行</option>
									                                        <option>张江路支行</option>
									                                        <option>水城路支行</option>
									                                    </select>								                                        
									                                    </div>
									                                </div>
									                                
									                                <div class="form-group"><label class="col-sm-2 control-label">是否发起过E估</label>									
									                                    <div class="col-sm-10">
									                                    <select class="form-control m-b" name="account">
									                                        <option>是：桌面端</option>
									                                        <option>是：手机端（输入评估编号）－最终去数据库中取</option>
									                                        <option>否</option>
									                                    </select>								                                        
									                                    </div>
									                                </div>
									                                
									                                
									                                <div class="form-group"><label class="col-sm-2 control-label">选择评估公司</label>									
									                                <div class="col-sm-10">
									                                    <select class="form-control m-b" name="account">
									                                        <option>E估</option>
									                                        <option>立信</option>
									                                        <option>世联</option>
									                                    </select>								                                        
									                                    </div>
									                                </div>
									                                
									                                
          
          									                        <div class="form-group"><label class="col-sm-2 control-label">需要的报告</label>									
									                                 
									                                
															    
								                                    <div class="col-sm-10">
								                                    	<label class="checkbox-inline"> <input type="checkbox" value="option1" id="inlineCheckbox1"> 仅询价</label>
								                                      
								                                   
								                                    	<label class="checkbox-inline"> <input type="checkbox" value="option1" id="inlineCheckbox1"> 评估报告 </label>
								                                      	
								                                     <label class="checkbox-inline"> <input type="checkbox" value="option1" id="inlineCheckbox1"> 预估单 </label>
								                                      
								                                    	<label class="checkbox-inline"> <input type="checkbox" value="option1" id="inlineCheckbox1"> 询价单 </label>
								                                      
								                                     </div>
								                                     </div>
								                                     
								                                      
								                              <div class="form-group"><label class="col-sm-2 control-label">备注</label>
																 <div class="col-sm-10"><input type="text" class="form-control"></div>
                                							  </div>								                               
                            							</form>
                            						
                            						</div>
                        					  </div> 
                        					  
                        					  <!-- 填写备注信息 -->
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
                                {prepare_item: "贷款材料确认书", attachment: "xxx的确认书.jpg"},
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