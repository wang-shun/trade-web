<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  taglib  uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- Toastr style -->
    <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
	<!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox.css' />" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-buttons.css' />" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.fancybox-thumbs.css' />" />
<!-- stickUp fixed css -->
<link href="<c:url value='/static/trans/css/common/hint.css' />" rel="stylesheet" />
<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link href="<c:url value='/css/plugins/jasny/jasny-bootstrap.min.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"	rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
</head>
<body>

<style>

[v-cloak] {
  display: none;
}

.table thead tr th{
    color: #1d1b1b;
}
.table_content .big a{
	min-width: 140px;
	display: inline-block;
}
.bonus-m-con .bonus-search{margin-left:15px;}
		.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
.hideDiv{
display: none;}
.table thead tr th {
    background-color: #4bccec;
    font-size: 14px;
    font-weight: normal;
    color: #fff;
}
</style>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="caseCode" value="${caseCode}" />
	<input type="hidden" id="flag" value="${flag}" />
	<input type="hidden" id="ctx" value="${ctx}" />
	<%--<input type="hidden" id="ctm" value="${toCaseInfo.ctmCode}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="activityFlag" value="${toCase.caseProperty}" /> --%>
	
	<%-- <input type="hidden" id="instCode" value="${toWorkFlow.instCode}" />
	<input type="hidden" id="srvCodes" value="${caseDetailVO.srvCodes}" />
	<input type="hidden" id="processDefinitionId"
		value="${toWorkFlow.processDefinitionId}" /> --%>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	
	<script>
		var resourceDistributionBtn = false;
		<%if (request.getAttribute("msg") == null || request.getAttribute("msg") == "") {%>
		<%} else {%>
			window.wxc.alert("<%=request.getAttribute("msg")%>");
		<%}%>
		<shiro:hasPermission name="TRADE.CASE.DISTRIBUTION">
		 resourceDistributionBtn = true;
		</shiro:hasPermission>
	</script>
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInDown">
			<div class="scroll_box fadeInDown animated">
				<div class="top12 panel" id="basicInfo">
						<div class="panel-body">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#settings" data-toggle="tab">新增结算案件信息</a>
								</li>
								
							</ul>
	
							<div class="tab-content">
								<div class="tab-pane active fade in" id="settings">
									<div class="jqGrid_wrapper row">
										<button type="button" class="btn btn-primary" onclick="javascript:associEval()" >关联评估单列表</button>
										<button type="button" class="btn btn-primary" onclick="javascript:associEval2()" style="padding-right::100px;" >关联失误</button>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel-body ibox-content">
							<div class="ibox white_bg" id="content">
									<div class="info_box info_box_one col-sm-12 "  >
								  		<span>结算信息</span>
								  			<div class="height_line1" style=" margin-top:10px;"></div>
									  		<div class="row font-family" >
												<label v-cloak  class="col-sm-4 control-label">案件编号：<span  style="background-color:#87CEFA" v-html="evalVO.caseCode"></span><span id="content_caseCode"></span></label>
												<label v-cloak  class="col-sm-3 control-label">评估单编号：<span  style="background-color:#87CEFA" v-html="evalVO.evaCode"></span></label>
											</div>
											<div class="height_line1" ></div>
											<div class="row font-family" style=" margin-top:20px;">
												<label class="col-sm-4 control-label">评估公司：<span  style="background-color:#87CEFA" v-html="evalVO.finOrgId"></span></label>
												<label class="col-sm-3 control-label">评估费实收金额：<span  style="background-color:#87CEFA" v-html="evalVO.evalRealCharges+'元'"></span></label>
												<label class="col-sm-3 control-label">评估申请日期：<span  style="background-color:#87CEFA" v-html="evalVO.applyDate"></span></label>
											</div>
											<div class="height_line1"></div>
											<div class="row font-family" style=" margin-top:20px;">
												<label class="col-sm-4 control-label">出具评估报告的日期：<span  style="background-color:#87CEFA" v-html="evalVO.issueDate"></span></label>
												<!-- <label class="col-sm-3 control-label">返利金额：0元&nbsp;&nbsp;</label> -->
												<label class="col-sm-3 control-label">结算费用：<span  style="background-color:#87CEFA" v-html="evalVO.evalComAmount+'元'"></span></label>
											</div>
											<div class="height_line1"></div>
											<div class="row font-family" style=" margin-top:20px;">
												<label class="col-sm-4 control-label">评估值：<span  style="background-color:#87CEFA" v-html="evalVO.evaPrice+'元'"></span></label>
												<label class="col-sm-3 control-label">贷款权证：</label>
											</div>
									</div>
							</div>
						</div>
						
				</div>
			</div>
				<!-- 新增结算任务-->
					<div class="panel " id="serviceFlow">
							<div class="panel-body">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#settings" data-toggle="tab">新增结算任务</a>
										</li>
										
									</ul>
			
								<div class="tab-content">
										<div class="tab-pane active fade in" id="settings">
											<div class="jqGrid_wrapper row">
												<div class="form_content">
									<label class="control-label sign_left_small">* 新增结算费为</label> <input
										class="teamcode input_type" 
										placeholder="" value="" id="endCost" name="endCost" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</div>
											</div>
										</div>
									<div class="jqGrid_wrapper row">
											<div class="form_content">
											
												<label class="control-label sign_left_small" style=" margin-top:30px;">* 新增事由 </label>
													<!-- <textarea class="teamcode input_type" rows="5" cols="150"></textarea> -->
												 <input
													class="teamcode input_type"  style="width: 435px;"
													placeholder="" value="" id="newCause" name="newCause" />
											
											</div>
										<div class="tab-pane fade" id="messages">
											<c:if test="${not empty toWorkFlow.processDefinitionId}">
												<c:if test="${not empty toWorkFlow.instCode}">
													<iframe frameborder="no" border="0" marginwidth="0"
														marginheight="0" scrolling="auto" allowtransparency="yes"
														overflow:auto;
															style="height: 1068px; width: 100%;"
														src="<aist:appCtx appName='aist-activiti-web'/>/diagram-viewer/index.html?processDefinitionId=${toWorkFlow.processDefinitionId}&processInstanceId=${toWorkFlow.instCode}"></iframe>
												</c:if>
											</c:if>
										</div>
										<div class="tab-pane fade" id="home">
											<table id="caseCommenTable"></table>
											<div id="caseCommenPager"></div>
										</div>
									</div>
								</div>
							</div>
							<div>
								<button type="button" class="btn btn-primary" onclick="javascript:closeEval()"
									>关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn btn-primary" id="submit" onclick="javascript:submit()">提交</button>
							</div>
					</div>
		</div>
	</div>
	
		<div id="modal-form" class="modal inmodal in"  tabindex="-1"
						role="dialog" aria-hidden="true">
			<div class="modal-dialog" style="width: 1070px; margin-right:10px; ">
				<div class="modal-content animated fadeIn apply_box info_box">
					<form action="" class="form_list clearfix">
					<div >
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<p class="modal-title" id="modal-title" style="font-size:16px;">关联评估单列表</p>
					</div>
					<!-- <div > -->
						<div class="row" style="height: 450px;overflow-y: auto; overflow-x: hidden; ">
								<div class="wrapper wrapper-content animated fadeInRight" style="padding-top: 0;">
							                <div class="main-bonus">
							                    <div class="row">
							                        <div class="ibox-content bonus-m-con">
							                            <div class="row">
							                                <div class="col-lg-5 col-md-5">
							                                        <label class="col-lg-3 col-md-3 control-label font_w">评估公司</label>
							                                        <div class="col-lg-9 col-md-9">
							                                            <input type="text" class="form-control" id="evalCompany" name="evalCompany">
							                                        </div>
							                                </div>
							                                <div class="col-lg-5 col-md-5">
							                                    <div class="form-group">
							                                        <label class="col-lg-3 col-md-3 control-label font_w">评估申请时间</label>
							                                         <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
																		<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value=""> 
																		
																	</div>
							                                    </div>
							                                </div>
							                                 <div class="col-lg-5 col-md-5"> 
							                                        <label class="col-lg-3 col-md-3 control-label font_w">案件状态</label>
							                                        <div class="col-lg-9 col-md-9">
																			<select name="" class="form-control" id="caseStatus">
																				<option value="" selected="selected">请选择</option>
																				<option value="0">已结算</option>
																				<option value="1">未结算</option>
																				<option value="2">已核对</option>
																				<option value="3">未核对</option>
																				<option value="4">已审批</option>
																				<option value="5">未审批</option>
																				  <option value="6">已驳回</option>
																			</select>
							                                        </div>
							                                </div>
							                            </div>
							                            <div id="select_div_1" class="form_content" >
								             		 		<div  class="sign_left_two">
									                                 <select id="inTextType" data-placeholder="搜索条件设定" onchange="intextTypeChange()">
																			<option value="1" selected>产证地址</option>
																			<option value="0">案件编号</option>
																			<option value="2">评估单编号</option>
																			
																	 </select>
																	 <input id="inTextVal" type="text"  style="width:320px;overflow-x:visible;overflow-y:visible;">
																	 <button id="searchButton" type="button" class="btn btn-success">查询</button>
															 </div>
														</div>
							                        </div>
							                    </div>
							                </div>
						                <!--  <table id="table_evalCase_list"></table>
						                 <div id="pager_evalCase_list"></div> -->
							<!-- <span>关联评估单列表</span>
					        <div class="portlet-body" style="display: block;">
									<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
							</div>  -->
										  <div class="row">
							                    <div class="table_content">
							                        <table class="table table_blue table-striped table-bordered table-hover ">
							                            <thead>
							                                <tr>
							                                    <th>
							                                    	
							                                    <p>案件编号</p>
							                                    	<p>评估单编号</p>
							                                    
							                                    </th>
							                                    <th>产证地址</th>
							                                    <th>评估公司</th>
							                                    <th>贷款权证</th>
							                                      <th>经纪人</th>
							                                    <th>评估申请日期
							                                    </th> 
							                                   
							                                    <th>结算费用</th>
							                                    
							                                    <th>操作</th>
							                                    
							                                </tr>
							                            </thead>
							                            <tbody id="t_body_data_contents">
							                                                              
							                            </tbody>
							                        </table>                     
							                    </div>
							                </div>
							                    
							                	<div class="text-center">
													<span id="currentTotalPage"><strong class="bold"></strong></span>
													<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
													<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
											    </div>
								<!-- <button type="button" class="btn btn-primary" onclick="javascript:closeEval()">关闭</button> -->
									
							     
							</div>
							
					</div>
				<!-- </div> -->
			</div>
		</div>
	</div>
	<!-- <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true"> -->
	<content tag="local_script"> <!-- Peity -->
		<!-- vue.js-->
		<script	src="<c:url value='/js/vue.min.js' />" type="text/javascript"></script>
         <!-- 日期控件 -->
    	<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
        <!-- Custom and plugin javascript -->
        <script src="<c:url value='/js/inspinia.js' />"></script>
        <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
        <!-- 弹出框插件 -->
	    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
        <!-- 分页控件  -->
        <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
	<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox.js' />"></script>
			
		<!-- Add Button helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-buttons.js' />"></script>
	
		<!-- Add Thumbnail helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-thumbs.js' />"></script>
	
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-media.js' />"></script>
	<script	src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	 <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	 <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
     <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script	src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jasny/jasny-bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script	src="<c:url value='/js/plugins/toastr/toastr.min.js' />"></script>
	<!-- 放款监管信息  -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<!-- 关联评估案件 -->
	<%-- <script	src="<c:url value='/js/trunk/eval/settle/associatedEvalCase.js' />"></script> --%>
	<jsp:include	page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script id="evalListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                     <td>
										<p>案：{{item.caseCode}}</p>
										<p>评：{{item.evalCode}}</p>
									</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.FIN_ORG_ID}}</td>
									<td>贷款权证</td>
									<td>经纪人</td>
									<td>
										{{item.APPLY_DATE}}
									</td>
                                    <td>{{item.SETTLE_FEE}}</td>
									<td class="center">
										<a type="button" class="btn btn-success linkCase" name="linkCase" id="{{item.caseCode}}" target="_blank">关联案件</a>
                        			</td>
                                </tr>
						{{/each}}
	
    </script>
    <script>
  	//关联信息
    $('.table_content')
	.on(
			"click",
			'.linkCase',
			function() {
				var caseCode = $(this).attr(
						"id"); 
				//$('.modal-dialog').on("click",'.close');
				//$(".close").click();
				
				$('#modal-form').modal("hide");
				//window.wxc.confirm("确定关联吗？",{"wxcOk":function(){
					$.ajax({
						cache:false,
						async:true,
						type:"GET",
						url :ctx+"/eval/settle/associatedShowForm",
						dataType:"json",
						data : [{
								name :"caseCode",  
								value:caseCode
						}],
						
						
						success:function(data){
							
							/* var caseCode = data.evalVO.caseCode;
								var evaCode = data.evalVO.evaCode;
								var finOrgId = data.evalVO.finOrgId;
								var evalRealCharges = data.evalVO.evalRealCharges;
								var applyDate = data.evalVO.applyDate;
								var issueDate = data.evalVO.issueDate;
								var settleFee = data.evalVO.settleFee;
								var evaPrice = data.evalVO.evaPrice; */
								
								new Vue({
									el:'#content',
									data:{
										evalVO:data.evalVO
									}
								}) 
								//var applyDate = data.evalVO.applyDate;
								/* $("#content_caseCode").append('<p>'+caseCode+'</p>');
								 $("#content_evalCode").append('<p>'+evaCode+'</p>');
								$("#content_company").append('<p>'+finOrgId+'</p>');
								$("#content_evalRealCharges").append('<p>'+evalRealCharges+'元'+'</p>');
								$("#content_applyDate").append('<p>'+applyDate+'</p>');
								$("#content_issueDate").append('<p>'+issueDate+'</p>');
								$("#content_settleFee").append('<p>'+settleFee+'元'+'</p>');
								$("#content_evaPrice").append('<p>'+evaPrice+'万元'+'</p>'); */
								//$("#content_caseCode").append('<p>'+caseCode+'</p>');
								
						},
						error:function(data){
							window.wxc.error(data.message);
						}
					});
				//}};
					
			});
 	
    </script>
	<script>
	//<a type="button" class="btn btn-success linkCase" name="linkCase" id="{{index}}" target="_blank">关联案件</a>
	//<a onclick="associatedShow({{item.caseCode}})" target="_blank">关联案件</a>
	//<a href="${ctx}/eval/settle/associatedShowForm?caseCode={{item.caseCode}}" target="_blank">关联案件</a>
	var ctx = $("#ctx").val();
	jQuery(document).ready(function() {
		 $(".fancybox").fancybox({
				maxWidth	: 650,
				maxHeight	: 450,
				fitToView	: false,
				width		: '70%',
				height		: '55%',
				autoSize	: false,
				closeClick	: false,
				openEffect	: 'none',
				closeEffect	: 'none'
			});
		 
		 $('#alertOper').fancybox({
				type: 'iframe',
				padding : 0,
				margin:0,
				autoScale:false,
				fitToView	: false,
			 	width		: '70%', 
				height		: '55%',
				autoDimensions:true,
				showCloseButton:true,
				close:true,
				helpers: {
	                overlay: {
	                    closeClick: false
	                }
	            },
				iframe:{preload:false},
				openEffect	: 'none',
				closeEffect	: 'none',
				afterClose:function(){
					//jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
				}
			});
		

	});
	
	//关联
	function associEval(){
		$('#modal-form').modal("show");
		$('#datepicker_0').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			});
			
			aist.sortWrapper({
			reloadGrid : reloadGrid
		});

		//初始化数据
	    reloadGrid();
		 // 查询
			$('#searchButton').click(function() {
				reloadGrid();
			});
	}
	
	//关联错误
	function associEval2(){
		window.location.reload();
	}
	
	
	
	
	function packgeData(page){
		var data1 = {};
	    
	    data1.rows = 12;
	    data1.page = 1;
	    if(page){
	    	data1.page=page;
	    }
	    
	    var propertyAddr = "";
		var caseCode =  "";
		var evalCode =  "";
		//产证地址,案件编号,评估单编号
		var inTextVal = $("#inTextVal").val();
		if (inTextVal != null && inTextVal.trim() != "") {
			var inTextType = $('#inTextType').val();
			if (inTextType == '0') {
				caseCode = inTextVal.trim();
			} else if (inTextType == '1') {
				//产证地址
				propertyAddr = inTextVal.trim();
			} else if (inTextType == '2') {
				//评估单编号
				evalCode = inTextVal.trim();
			}
		}
		 data1.search_propertyAddr = propertyAddr;
		 data1.search_caseCode = caseCode;
		 data1.search_evalCode = evalCode;
	    data1.search_finOrgID = $("#evalCompany").val();
		data1.search_applyDate=$('#dtBegin_0').val();
		return data1;
	}
	function reloadGrid(page) {
		var data1=packgeData(page);
		data1.queryId = "queryEvalWaitEndList";
		aist.wrap(data1);
	    fetchData(data1);
	}
	function fetchData(p){
		  $.ajax({
  			  async: true,
  	          url:ctx+ "/quickGrid/findPage" ,
  	          method: "post",
  	          dataType: "json",
  	          data: p,
  	          beforeSend : function() {
				$.blockUI({
					message : $("#salesLoading"),
					css : {
						'border' : 'none',
						'z-index' : '9999'
					}
				});
				$(".blockOverlay").css({
					'z-index' : '9998'
				});
			},
  	          success: function(data){
  	        	  $.unblockUI();
  	        	  data.ctx = ctx;
  	        	  var tsAwardBaseList= template('evalListTemp' , data);
	                  $("#t_body_data_contents").empty();
	                  $("#t_body_data_contents").html(tsAwardBaseList);
	                  
	                 initpage(data.total,data.pagesize,data.page, data.records);
  	          }
  	     });
	} 
	function initpage(totalCount,pageSize,currentPage,records)
	{
		if(totalCount>1500){
			totalCount = 1500;
		}
		var currentTotalstrong=$('#currentTotalPage').find('strong');
		if (totalCount<1 || pageSize<1 || currentPage<1)
		{
			$(currentTotalstrong).empty();
			$('#totalP').text(0);
			$("#pageBar").empty();
			return;
		}
		$(currentTotalstrong).empty();
		$(currentTotalstrong).text(currentPage+'/'+totalCount);
		$('#totalP').text(records);
		
		$("#pageBar").twbsPagination({
			totalPages:totalCount,
			visiblePages:9,
			startPage:currentPage,
			first:'<i class="icon-step-backward"></i>',
			prev:'<i class="icon-chevron-left"></i>',
			next:'<i class="icon-chevron-right"></i>',
			last:'<i class="icon-step-forward"></i>',
			showGoto:true,
			onPageClick: function (event, page) {
				 //console.log(page);
				reloadGrid(page);
		    }
		});
	}
	
	
	/* function associEval(){
		$('#modal-form').modal("show");
		var url = "/quickGrid/findPage";
		url = ctx + url;
		
		//jqGrid 初始化
		$("#table_evalCase_list").jqGrid({
			url : url,
			mtype : 'POST',
			datatype : "json",
			height : 250,
			autowidth : true,
			shrinkToFit : true,
			rowNum : 8,
			colNames : [ '案件编号','评估单编号','产证地址', '评估公司', '贷款权证','经纪人', '评估申请时间','结算费用', '操作' ],
			colModel : [{
				name : 'caseCode',
				index : 'caseCode',
				align:'center',
				resizable : true,
				width : 20
			},{
				name : 'evalCode',
				index : 'evalCode',
				align:'center',
				resizable : true,
				width : 20
			}, {
				name : 'PROPERTY_ADDR',
				index : 'PROPERTY_ADDR',
				sortable : false,
				resizable : true,
				width : 30
			}, {
				name : 'FIN_ORG_ID',
				index : 'FIN_ORG_ID',
				align:'center',
				resizable : true,
				width : 20
			}, {
				name : '贷款权证',
				index : '贷款权证',
				align:'center',
				resizable : true,
				width : 20
			},{
				name : '经纪人',
				index : '经纪人',
				align:'center',
				resizable : true,
				width : 20
			},{
				name : 'APPLY_DATE',
				index : 'APPLY_DATE',
				//align:'center',
				resizable : true,
				width : 10
			},{
				name : 'SETTLE_FEE',
				index : 'SETTLE_FEE',
				//align:'center',
				resizable : true,
				width : 10
			},{
				name : 'act',
				index : 'act',
				align : "center",
				width : 20,
				resizable : false,
				sortable : false,
				title : false
			}],
			multiselect: true,
			pager : "#pager_evalCase_list",
		
			viewrecords : true,
			pagebuttions : true,
			multiselect:false,
			hidegrid : false,
			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
			pgtext : " {0} 共 {1} 页",

			gridComplete:function(){
				var ids = jQuery("#table_evalCase_list").jqGrid('getDataIDs');
				for ( var i = 0; i < ids.length; i++) { 
					var rowDatas = jQuery("#table_evalCase_list").jqGrid('getRowData',ids[i] ); //获取当前行
					var caseCode = rowDatas.caseCode;

					btn3="<button onclick='associatedCase(\""+ctx+"\","+ids[i]+",\""+caseCode+"\")' class='btn red' >关联案件</button>&nbsp;";

					jQuery("#table_evalCase_list").jqGrid('setRowData', ids[i], { act :  btn3 }); 
				} 
			},
			postData : {
				queryId : "queryEvalWaitEndList"
			}

		});
		$(".ui-jqgrid-bdiv").attr("style","height: 350px;");

		// Add responsive to jqGrid
		$(window).bind('resize', function() {
			var width = $('.jqGrid_wrapper').width();
			$('#table_evalCase_list').setGridWidth(width);
		});
		 $('.contact-box').each(function() {
	         animationHover(this, 'pulse');
	     }); */
		/* $('#alertOper').attr("href",ctx+"/eval/settle/associatedEvalList");
		$("#alertOper").trigger('click'); */
		//window.location.href = ctx+"/eval/settle/associatedEvalList";
	//}
	
	/**
	 * 关联按钮事件
	 * @param ctx  
	 * @param rowid  主键pkid
	 * @param caseCode 案件编码
	 */
	function associatedCase(ctx,rowid,caseCode){
		window.wxc.confirm("确定要关联吗？",{"wxcOk":function(){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				url :ctx+"/eval/settle/associatedShowForm",
				dataType:"json",
				data : [{
						name : "Pkid",
						value: rowid
					},{
						name :"caseCode",  
						value:caseCode
				}],
				success:function(data){
					window.wxc.success(data.message);
					window.location.href = ctx+"/eval/settle/newEndForm2&caseCode="+caseCode;
					//jQuery("#table_evalCase_list").trigger("reloadGrid");//刷新列表
				},
				error:function(data){
					window.wxc.error(data.message);
				}
			});
		}});
	}
	
	function closeEval(){
		window.close();
		//window.location.href = ctx+"/eval/settle/evalWaitEndList";
	}
	
	$("#submit").click(function(){
		var toEvalSettle = {
				caseCode : $("#caseCode").val(),
				settleFee : $("#endCost").val(),
				settleAddReason:$("#newCause").val()
			};
		toEvalSettle = $.toJSON(toEvalSettle);
	   	  $.ajax({
					cache:false,
					async:true,
					type:"POST",
					dataType:"json",
					contentType: "application/json; charset=utf-8" ,
					url:ctx+"/eval/settle/newEndFee",
					data:toEvalSettle,
					success:function(data){
						if(data.success){
							location.href = "../settle/evalWaitEndList"
						}else{
							window.wxc.error(data.message);
						}
					},
					
			}); 
   	  	
     });
	
	</script>
	
	</content>

</body>
</html>

