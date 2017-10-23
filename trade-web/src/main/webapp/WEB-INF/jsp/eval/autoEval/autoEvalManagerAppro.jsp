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
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ctm" value="${toCaseInfo.ctmCode}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="activityFlag" value="${toCase.caseProperty}" />
	<input type="hidden" id="caseCode" value="${toCase.caseCode}" />
	<input type="hidden" id="instCode" value="${toWorkFlow.instCode}" />
	<input type="hidden" id="srvCodes" value="${caseDetailVO.srvCodes}" />
	<input type="hidden" id="processDefinitionId"
		value="${toWorkFlow.processDefinitionId}" />
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

	<%-- <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include> --%>
	<!-- 主要内容页面 -->
	<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"><a href="#serviceFlow"> 服务流程 </a></li>
				<li class="menuItem"><a href="#aboutInfo"> 相关信息 </a></li>

			</ul>
		</div>
	</nav>
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInDown">
			<div class="scroll_box fadeInDown animated">
				<div class="top12 panel" id="basicInfo">
                 	<c:if test="${toCase.caseProperty=='30003001'}">
                   		<div class="sign sign-red" ><span
                   		<c:if test="${toApproveRecord!=''}">
                  		class="hint hint-top" data-hint="${toApproveRecord}"
                  		</c:if> >无效</span></div>

                  	</c:if>
                   	<c:if test="${toCase.caseProperty=='30003002'}">
                   			<div class="sign sign-red">结案</div>
                    </c:if>
                   	<c:if test="${toCase.caseProperty=='30003009'}">
                   			<div class="sign sign-out"> 外单 </div>
                    </c:if>
                 	<c:if test="${toCase.caseProperty=='30003005'}">
                  		<div class="sign sign-red ">
                  		<span
                   		<c:if test="${toApproveRecord!=''}">
                  		class="hint hint-top" data-hint="${toApproveRecord}"
                  		</c:if> >爆单</span>

                  		</div>
                  	</c:if>
                   	<c:if test="${toCase.caseProperty=='30003003' || toCase.caseProperty=='30003007' || toCase.caseProperty=='30003008'}">
                   		<div class="sign sign-red">在途</div>
	                   <div class="sign sign-blue">
	                   	<c:if test="${toCase.status=='30001001'}">
	                   		未分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001002'}">
	                   		已分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001003'}">
	                   		已签约
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001004'}">
	                   		已过户
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001005'}">
	                   		已领证
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001006'}">
	                   		未指定
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001007'}">
	                   		被合流
	                   	</c:if>
	                   </div>
                    </c:if>
                   	<c:if test="${toCase.caseProperty=='30003004'}">
                   		<div class="sign sign-red">挂起</div>
	                   <div class="sign sign-blue">
	                   	<c:if test="${toCase.status=='30001001'}">
	                   		未分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001002'}">
	                   		已分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001003'}">
	                   		已签约
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001004'}">
	                   		已过户
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001005'}">
	                   		已领证
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001006'}">
	                   		未指定
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001007'}">
	                   		被合流
	                   	</c:if>
	                   </div>
                    </c:if>
                  	<c:if test="${toCase.caseProperty=='30003006'}">
                  		<div class="sign sign-red">全部</div>
	                   <div class="sign sign-blue">
	                   	<c:if test="${toCase.status=='30001001'}">
	                   		未分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001002'}">
	                   		已分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001003'}">
	                   		已签约
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001004'}">
	                   		已过户
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001005'}">
	                   		已领证
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001006'}">
	                   		未指定
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001007'}">
	                   		被合流
	                   	</c:if>
	                   </div>
                   	</c:if>
                   	<c:if test="${caseDetailVO.loanType=='30004005'}">
                  		<div class="sign sign-yellow">税费卡</div>
                  	</c:if>
					<div class="panel-body">
						<div class="ibox-content-head lh24">
							<h5>案件基本信息</h5>
							<p class="star-position" id="subscribe">

								<c:if test="${isSubscribe}">
									<span style="cursor: pointer;" class="starmack subscribe subscribe_detail active"  moduleCode="${toCase.caseCode}" isSubscribe="false">
										<i class="iconfont_s  markstar star_subscribe" status="1">&#xe63e;</i>
										<span class="star_text_1">未关注</span>
										<span class="star_text_2">已关注</span>
									</span>
								</c:if>
								<c:if test="${isSubscribe==false}">
									<span style="cursor: pointer;" class="starmack subscribe subscribe_detail"  moduleCode="${toCase.caseCode}" isSubscribe="true">
									<i class="iconfont_s  markstar star_subscribe" status="1">&#xe644;</i>
									<span class="star_text_1">未关注</span>
									<span class="star_text_2">已关注</span>
									</span>
								</c:if>

							</p>
							<small class="pull-right">誉萃编号：${toCase.caseCode}｜中原编号：${toCase.ctmCode}</small>
						</div>
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>CTM地址</dt>
											<dd>${toPropertyInfo.ctmAddr}</dd>
											<dt>产证地址</dt>
											<dd>${toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${toPropertyInfo.square}平方</dd>
											<dt>房屋类型</dt>
											<dd>
												<aist:dict id="propertyType" name="propertyType"
													display="label" dictType="30014"
													dictCode="${toPropertyInfo.propertyType}" />
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_two col-sm-5">
									<span>买卖双方</span>
									<div class="ibox-conn else_conn">
										<dl class="dl-horizontal col-sm-6">
											<dt>上家姓名</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>下家姓名</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									</div>
									<c:choose>  
										    <c:when test="${toCase.caseProperty=='30003009'}">
										        <span>推荐人信息</span>
												<div class="ibox-conn else_conn_two ">
													<dl class="dl-horizontal">
														<dt>姓名</dt>
														<dd>
															<a data-toggle="popover" data-placement="right" data-content="${toCaseInfo.recommendPhone}"> ${toCaseInfo.recommendUsername}</a>
														</dd>
														<dt>手机号</dt>
														<dd>${toCaseInfo.recommendPhone }</dd>
													</dl>
												</div>
										   </c:when>  
										   <c:otherwise> 
											    <span>经纪人信息</span>
												<div class="ibox-conn else_conn_two ">
													<dl class="dl-horizontal">
														<dt>姓名</dt>
														<dd>
															<a data-toggle="popover" data-placement="right"
																data-content="${toCaseInfo.agentPhone}">
																${caseDetailVO.agentName}</a>
														</dd>
														<dt>所属分行</dt>
														<dd>${toCaseInfo.grpName }</dd>
														<dt>直管经理</dt>
														<dd>
															<a data-toggle="popover" data-placement="right"
																data-content="${caseDetailVO.mcMobile}">
																${caseDetailVO.mcName} </a>
														</dd>
													</dl>
												</div>
									       </c:otherwise> 
										</c:choose>	
								</div>
								<div class="info_box info_box_three col-sm-3">
									<span>经办人信息</span>
									<div class="ibox-conn  ibox-text">
										<dl class="dl-horizontal">
											<dt>交易顾问</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.cpMobile}">
													${caseDetailVO.cpName} </a>
											</dd>
											<c:if test="${empty caseDetailVO.proList}">
												<dt>合作顾问</dt>
												<dd></dd>
											</c:if>
											<c:if test="${!empty caseDetailVO.proList}">
												<c:forEach items="${caseDetailVO.proList}" var="pro">
													<dt>合作顾问</dt>
													<dd>
														<a data-toggle="popover" data-placement="right"
															data-content="${pro.processorMobile}">
															${pro.processorName} </a>
													</dd>
												</c:forEach>
											</c:if>
											<dt>交易助理</dt>
											<dd>
												<input type="hidden" id="assistantUserName" value="${caseDetailVO.asName}" />
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.asMobile}">
													${caseDetailVO.asName} </a>
											</dd>
										</dl>
									</div>
								</div>

							</div>
							<div  >
									<!-- <span class="table table_blue table-striped table-bordered table-hover ">自办评估信息</span> -->
									<span >自办评估信息</span>
									<div >
										<dl class="col-sm-3 control-label" >
											<dt>流程状态：待审批</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>申请人姓名：</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>申请人分行信息：</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									
										<dl class="col-sm-3 control-label" >
											<dt>申请时间：</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>评估公司：</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>自办原因：</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									
										<dl class="col-sm-3 control-label" >
											<dt>自办评估公司：</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="col-sm-3 control-label">
											<dt>贷款银行：</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 服务流程 -->
				<div class="panel " id="serviceFlow">
				<form action="">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#settings" data-toggle="tab">填写审批任务</a>
							</li>
							
						</ul>

						<div class="tab-content">
							
							<div class="tab-pane active fade in" id="settings">
							<table border="1" class="table table_blue table-striped table-bordered table-hover">
									  <tr>
									    <th width="100px" align="center">审批结果</th>
									    <td width="500px">
									    <label> 
							   			 <input type="radio" value="1" id="lamp1" name="lampRadios"> <span >通过</span>
										</label>
										<label> 
							   			 <input type="radio" value="1" id="lamp1" name="lampRadios"> <span >驳回</span>
										</label>
									</td>
									  </tr>
									  <tr>
									    <th>审批意见</th>
									    <td><textarea rows="2" cols="100"></textarea></td>
									    
									  </tr>
									  <tr>
									    <th>回访结果</th>
									    <td><textarea rows="2" cols="100"></textarea></td>
									  </tr>
									  <tr>
									    <th>回访时间</th>
									    <td><textarea rows="2" cols="100"></textarea></td>
									  </tr>
							</table>
								
								
					<div><span>审批记录</span></div>				
					<div class="form_content">
						
							<textarea class="teamcode input_type" rows="5" cols="150"></textarea>
						 <!-- <input
							class="teamcode input_type"  style="width: 435px;"
							placeholder="" value="" id="updateCause" name="updateCause" /> -->
					
					</div>
					 <div class="modal-footer">
						<!-- <button type="button" class="btn btn-primary" 
						>关闭</button> -->
						<a href="javascript:window.opener=null;window.close();">关闭</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	<button class="btn btn-primary" id="btnSave">提交</button>
					</div>
								</div>
							</div>
							
						</div>
					</div>
				</div>


				
			</div>
		</div>
	</div>
	</div>
	

	

	<content tag="local_script"> <!-- Peity -->
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
	<script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script>
	<script src="<c:url value='/js/trunk/case/caseDetail.js' />"></script> 
	<%-- <script src="<c:url value='js/trunk/case/showCaseAttachment.js' />"></script> --%>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<script	src="<c:url value='/js/plugins/toastr/toastr.min.js' />"></script>
	<!-- 放款监管信息  -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script	src="<c:url value='/transjs/task/caseflowlist.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script	src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<!-- 各个环节的备注信息  -->
	<script src="<c:url value='/js/trunk/case/caseRemark.js' />"></script>
	<jsp:include	page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script>

		var caseCode = $("#caseCode").val();
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
			/*   rowList: [10, 20, 30], */
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
			//sortname:'UPLOAD_DATE',
	        //sortorder:'desc',
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
			/*var url=ctx+"/api/imageshow/imgShow?img="+attachPath;
			window.open(encodeURI(encodeURI(url)));*/
		}

		//加载页面获取屏幕高度
 		$(function(){

			var caseCode = $('#caseCode').val();

			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});
/* 	        var h= window.screen.availHeight;
			$("#scroll").css("height",h-h*0.32); */
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
	        	/* 	$(".wrapper").css("margin-top","px"); */
	        	}else{
	        		$("#isFixed").css("position","relative");
	        		$("#isFixed").removeClass("istauk");

	        	}
	        }

		});

		/*动态生成上下家*/
		function generateSellerAndBuyer(name, phone){
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
        });
        /**
         * 新建外单案件
         */
        $('#addLiushui').click(function() {	
        	window.location.href = ctx+"/caseMerge/addLiushui?caseCode="+$('#caseCode').val();
        });



	</script>
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

