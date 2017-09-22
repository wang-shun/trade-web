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
.table thead tr th {
    background-color: #4bccec;
    font-size: 14px;
    font-weight: normal;
    color: #fff;
}
</style>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="caseCode" value="${caseCode}" />
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
										<button id="associEval" onclick="javascript:associEval()"><span>关联评估单列表</span></button>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#settings" data-toggle="tab">结算信息</a>
							</li>
						</ul>
						<div class="form_content"">
										<dl class="col-sm-3 control-label" >
													<dt>案件编号：${evalVO.caseCode}</dt>
													<dd>
														<div id="seller"></div>
													</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>评估单编号：${evalVO.evaCode}</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>评估公司：${evalVO.finOrgId}</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									
										<dl class="col-sm-3 control-label" >
											<dt>评估费实收金额：${evalVO.evalRealCharges}元</dt>
											<dd>
											
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>出具评估报告的日期：${evalVO.issueDate}</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
										<%-- <dl class="col-sm-3 control-label">
											<dt>返利金额：${evalVO.caseCode}元</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl> --%>
										<dl class="col-sm-3 control-label">
											<dt>结算费用：${evalVO.settleFee}元</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									
										<dl class="col-sm-3 control-label" >
											<dt>评估值：${evalVO.evaPrice}万元</dt>
											<dd>
											
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>贷款权证：</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
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
											
												<label class="control-label sign_left_small">* 新增事由 </label>
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
	<div class="portlet-body" style="display: block;">
			<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
	</div>
	<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
		<span>关联评估单列表</span>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                
                    <div class="bonus-wrap">
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
                            <div >
	             		 		<div id="select_div_1" >
				           			<div >
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
                </div>
                 
                    <div class="bonus-table">
                        <table>
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
                    
                	<div class="text-center">
						<span id="currentTotalPage"><strong class="bold"></strong></span>
						<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
						<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
				    </div>
        </div>
	</div>
	<content tag="local_script"> <!-- Peity -->
	<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox.js' />"></script>
			
		<!-- Add Button helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-buttons.js' />"></script>
	
		<!-- Add Thumbnail helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-thumbs.js' />"></script>
	
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-media.js' />"></script>
	<script	src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script	src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jasny/jasny-bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<%-- <script src="<c:url value='/transjs/task/follow.pic.list.js' />"></script> --%>
	<%-- <script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script> --%>
	<%-- <script src="<c:url value='/js/trunk/case/caseDetail.js' />"></script>  --%>
	<%-- <script src="<c:url value='js/trunk/case/showCaseAttachment.js' />"></script> --%>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<%-- <script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script> --%>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<%-- <script src="<c:url value='/js/stickUp.js' />"></script> --%>
	<script	src="<c:url value='/js/plugins/toastr/toastr.min.js' />"></script>
	<!-- 放款监管信息  -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script	src="<c:url value='/transjs/task/caseflowlist.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<%-- <script	src="<c:url value='/js/trunk/comment/caseComment.js' />"></script> --%>
	<!-- 各个环节的备注信息  -->
	<%-- <script src="<c:url value='/js/trunk/case/caseRemark.js' />"></script> --%>
	<jsp:include	page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script>
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
	function associEval(){
		/* $('#alertOper').attr("href",ctx+"/eval/settle/associatedEvalList");
		$("#alertOper").trigger('click'); */
		window.location.href = ctx+"/eval/settle/associatedEvalList";
	}
	
	function closeEval(){
		window.location.href = ctx+"/eval/settle/evalWaitEndList";
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
	<script>

	/* var caseCode = $("#caseCode").val();
		var ctmCode = $("#ctm").val();
		var url = "/quickGrid/findPage";
		var ctx = $("#ctx").val();
	    var r1 =false;
	    var changeTaskRole=false;
	    var serivceDepId='${serivceDefId}';
	    var loanReqType="${loanReqType}";
	    <shiro:hasPermission name="TRADE.CASE.DEALPRICE:SHOW">
			r1 = true;
		</shiro:hasPermission>
		<shiro:hasPermission name="TRADE.CASE.TASK:ASSIGN">
			changeTaskRole=true;
		</shiro:hasPermission>
		var isNewFlow =${isNewFlow};
		var isCaseManager=${isCaseManager};
	      $('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
 	      $('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));

 	     function getCurrentDate(){
 	    	var d = new Date()
 	    	var vYear = d.getFullYear();
 	    	var vMon = d.getMonth() + 1;
 	    	var vDay = d.getDate();

 	    	var str = vYear + "-" + (vMon<10 ? "0" + vMon : vMon) + "-" + (vDay<10 ? "0"+ vDay : vDay);
	    	return str;
    	  }

 	      $("#btnSave").click(function(){
 	    	  var content = $("#bizwarnForm-modal-form input[name=content]").val();
 	    	  var caseCode = $("#bizwarnForm-modal-form input[name=caseCode]").val();
 	    	  var caseId = $("#bizwarnForm-modal-form input[name=caseId]").val();

 	    	 $.ajax({
					cache:false,
					async:true,
					type:"POST",
					dataType:"json",
					url:ctx+"/bizwarn/addBizWarnInfo",
					data:{caseCode:caseCode,content:content},
					success:function(data){
						if(data.success){
							location.href = "../case/caseDetail?caseId=" + caseId;
						}else{
							window.wxc.error('添加失败');
						}
					}
				});

 	      });

 	      $("#add").click(function(){
 	    	  $("#bizwarnForm-modal-form").modal("show");
 	      });

 	     $("#edit").click(function(){
 	    	 var caseCode = $("#editBizwarnForm-modal-form input[name=caseCode]").val();


 	    	 var status;
 	    	 var content;
 	    	 $.ajax({
					cache:false,
					async:false,
					type:"POST",
					dataType:"json",
					url:ctx+"/bizwarn/getBizWarnInfo",
					data:{caseCode:caseCode},
					success:function(data){
						status = data.status;
						content = data.content
					}
				});

 	    	 	if(status == "1"){
 	    	 		window.wxc.alert("解除状态不能修改商贷预警信息！");
 	    	 		return false;
 	    	 	}

 	    	  $("#editBizwarnForm-modal-form input[name=content]").val(content);
	    	  $("#editBizwarnForm-modal-form").modal("show");
	      });

 	     $("#btnEdit").click(function(){
 	    	 var content = $("#editBizwarnForm-modal-form input[name=content]").val();
	    	 var caseCode = $("#editBizwarnForm-modal-form input[name=caseCode]").val();
	    	 var caseId = $("#editBizwarnForm-modal-form input[name=caseId]").val();

	    	 $.ajax({
					cache:false,
					async:true,
					type:"POST",
					dataType:"json",
					url:ctx+"/bizwarn/editBizWarnInfo",
					data:{caseCode:caseCode,content:content},
					success:function(data){
						if(data.success){
							location.href = "../case/caseDetail?caseId=" + caseId;
						}else{
							window.wxc.error('修改失败');
						}
					}
				});
 	     });

 	      $("#relieve").click(function(){
 	    	  var status = $("input[name=status]").val();

 	    	 window.wxc.confirm("是否确定解除？",{"wxcOk":function(){
  				$.ajax({
  					cache:false,
  					async:true,
  					type:"POST",
  					dataType:"json",
  					url:ctx+"/bizwarn/relieve",
  					data:{caseCode:caseCode},
  					success:function(data){
  						if(data.success){
  							$("#spnStatus").html("解除");
  							$("#spnRelieveTime").html(getCurrentDate());
  							$("#relieve").hide();
  						}else{
  							window.wxc.error('解除失败');
  						}
  					}
  				});
 	    	}});
 	      });

		//jqGrid 初始化
		$("#gridTable").jqGrid({
			url : ctx+url,
			mtype : 'GET',
			datatype : "json",
			height : 250,
			autowidth : true,
			shrinkToFit : true,
			rowNum : 50,
			/*   rowList: [10, 20, 30], 
			colNames : [ '附件类型','附件名称','附件路径','上传时间','操作'],
			colModel : [ {
				name : 'ATT_TYPE',
				index : 'ATT_TYPE',
				align : "center",
				width : 20,
				resizable : false
			},{
				name : 'ATT_NAME',
				index : 'ATT_NAME',
				align : "center",
				width : 20,
				resizable : false
			}, {
				name : 'ATT_PATH',
				index : 'ATT_PATH',
				align : "center",
				width : 20,
				resizable : false
				//formatter : linkhouseInfo
			}, {
				name : 'UPLOAD_DATE',
				index : 'UPLOAD_DATE',
				align : "center",
				width : 20,
				resizable : false
			},{
				name : 'READ',
				index : 'READ',
				align : "center",
				width : 20,
				resizable : false
			}],
			multiselect: true,
			pager : "#gridPager",
			viewrecords : true,
			pagebuttions : true,
			multiselect:false,
			hidegrid : false,
			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
			pgtext : " {0} 共 {1} 页",
			gridComplete:function(){
				var ids = jQuery("#gridTable").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
    				var id = ids[i];
    				var rowDatas = jQuery("#gridTable").jqGrid('getRowData', ids[i]); // 获取当前行

    				var link = "<button  class='btn red' onclick='addAttachmentReadLog(\""+ctx+"\",\""+ctmCode+"\",\""+caseCode+"\",\""+rowDatas['ATT_NAME']+"\",\""+rowDatas['ATT_PATH']+"\")'>查看附件</a>";

    				//var detailBtn = "<button  class='btn red' id='alertOper' onclick='openLoan(\""+ctx+"\",\""+rowDatas['pkId']+"\")' style='width:90px;'>详细</button>";

    				jQuery("#gridTable").jqGrid('setRowData', ids[i], { READ: link});

    				var attType = rowDatas["ATT_TYPE"];
    				if(!r1 && attType =='买卖居间协议') {
   					   $("#gridTable").jqGrid("delRowData", id);
    				}
				}
			},
			postData : {
				queryId : "followPicListQuery",
				argu_ctmCode : ctmCode
			}

		});

		//附件连接
		function linkhouseInfo(cellvalue, options, rowObject){
			 var link = '<a href="" target="_black" onclick="addAttachmentReadLog('+cellvalue+')">'+cellvalue+'</a>';
			 return link;
		}

		function addAttachmentReadLog(ctx,ctmCode,caseCode,attachName,attachPath) {
			var tsAttachmentReadLog = {
				 	'caseCode':caseCode,
				 	'ctmCode':ctmCode,
				 	'attachmentName':attachName,
				 	'attachmentAddress':attachPath
			};
			//tsAttachmentReadLog=$.toJSON(tsAttachmentReadLog);

			$.ajax({
				type : 'post',
				cache : false,
				async : true,
				url : ctx+'/log/addAttachmentReadLog',
				data : tsAttachmentReadLog,
				dataType : "json",
				success : function(data) {
					//alert("记录日志成功");
				},
				error : function(errors) {
					//alert("记录日志失败");
					return false;
				}
			});

			window.open(attachPath);
		}

		//加载页面获取屏幕高度
 		$(function(){

			var caseCode = $('#caseCode').val();

			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});

			  //点击浏览器任何位置隐藏提示信息
		      $("body").bind("click",function(evt){
	              if($(evt.target).attr("data-toggle")!='popover'){
	             	$('a[data-toggle="popover"]').popover('hide');
	              }
	          });
	    	//隐藏头部信息
 	        window.onscroll = function(){
	        	if(document.body.scrollTop>62){
	        		$("#isFixed").css("position","fixed");
	        		$("#isFixed").addClass("istauk");
	        	 
	        	}else{
	        		$("#isFixed").css("position","relative");
	        		$("#isFixed").removeClass("istauk");

	        	}
	        }

		});*/

		/*动态生成上下家*/
		/* function generateSellerAndBuyer(name, phone){
 			var nameArr = name.split('/');
 			var phoneArr = phone.split('/');
 			var str='';
 			for (var i=0; i<nameArr.length; i++) {
 				if(i%2==0){
 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a>&nbsp;';
 				}else{
 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a><br/>';
 				}
 			}
 			return str;
 		}
        jQuery(function($) {
            $(document).ready( function() {
               $('.stickup-nav-bar').stickUp({
                // $('.col-lg-9').stickUp({
                                    parts: {
                                      0:'basicInfo',
                                      1:'serviceFlow',
                                      2:'aboutInfo'
                                    },
                                    itemClass: 'menuItem',
                                    itemHover: 'active',
                                    marginTop: 'active'
                                  });
            });
        }); */
        /**
         * 新建外单案件
         */
       /*  $('#addLiushui').click(function() {	
        	window.location.href = ctx+"/caseMerge/addLiushui?caseCode="+$('#caseCode').val();
        });  */



	</script>*/
	<script
		id="template_successList" type="text/html">
	{{each rows as item index}}
    	<tr>
       		 <td>
                                                {{item.shareTime}}
                                                    
                                                </td>
                                                
                                                <td>
                                                    {{item.bizCode}}
                                                </td>
                                                <td>
                                                    {{item.shareBase}}
                                                </td>
                                                <td>
                                                   {{item.sharesRate}}
                                                </td>
                                                <td>
                                                    {{item.shareAmount}}
                                                </td>
                                                <td>
                                                    {{item.createBy}}
                                                </td>
                                                <td>
                                                	{{item.treamId}}
                                                </td>
                                                <td>
				{{if item.status !=null && item.status == 'GENERATED'}}有效
				{{/if}}
				{{if item.status !=null && item.status == 'CANCELED'}}
				无效<a href="#" class="demo-left" title="案件重启<br/>" style="margin-left:2px;"><i class="icon iconfont" style="font-size: 20px;color:#808080;">&#xe609;</i></a>
				{{/if}}
                                                </td>
                                            </tr>                                        
	{{/each}}
	</script>
	
</content>

</body>
</html>

