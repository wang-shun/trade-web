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
.td_width{
	width:130px;
}
</style>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ctm" value="${toCaseInfo.ccaiCode}" />
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
<%-- 	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include> cyx--%>
	<input type="hidden" id="data" value="${data }"/>
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
							<small class="pull-right">交易编号：${toCase.caseCode}｜成家报告编号：${toCase.ctmCode}</small>
						</div>
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>产证地址</dt>
											<dd>${toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${toPropertyInfo.square}平方</dd>
											<dt>竣工年限</dt>
											<dd>${toPropertyInfo.finishYear}年</dd>
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
											<dt>买方姓名</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>卖方姓名</dt>
											<dd>
												<div id="seller"></div>
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
														<dt>直属经理</dt>
														<dd>
															<a data-toggle="popover" data-placement="right"
																data-content="${caseDetailVO.mcMobile}">
																${caseDetailVO.mcName} </a>
														</dd>
														<dt>分行秘书</dt>
														<dd>
															<a data-toggle="popover" data-placement="right"
																data-content="${caseDetailVO.msMobile}">${caseDetailVO.msName }</a>
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
											<dt>贷款权证</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.loanMobile}">
													${caseDetailVO.loanName} </a>
											</dd>
											<dt>过户权证</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.warMobile}">
													${caseDetailVO.warName} </a>
											</dd>
											<dt>内勤</dt>
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
						</div>
					</div>
				</div>
				<!-- 服务流程 -->
				<div class="panel" style="min-height:100px" id="serviceFlow">
					<div class="panel-body">
						<h5>案件基本操作</h5>
						<div class="row">
							<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUSPEND">
								<a role="button" id="casePause" class="btn btn-primary btn-xm"
												href="javascript:casePause()">案件挂起 </a>
							</shiro:hasPermission>
							<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.PLANCHANGE">
								<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:showPlanModal()">交易计划变更</a>
							</shiro:hasPermission>
							<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:caseBaodan()">爆单</a>
							<!-- 已过户&&已领他证 不可变更 -->
							<c:if test="${ !isBackTeam}">
								<c:if test="${not empty toWorkFlow.processDefinitionId}">
									<c:if test="${not empty toWorkFlow.instCode}">
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.LEADCHANGE">
											<a role="button"
												class="btn btn-primary btn-xm btn-activity"
												href="javascript:showOrgCp()">责任人变更</a>
										</shiro:hasPermission>
									</c:if>
								</c:if>
							</c:if>
							
							<c:if test="${toCase.caseProperty != 30003002}">
								<!-- 已经结案审批通过限制流程重启 -->
								<!-- 已经过户或者已经领证的案件限制流程重启 -->
								<c:if test="${toCase.status != '30001004' and toCase.status != '30001005' and toCase.status != '30001007' and toCase.caseProperty!='30003009'  || serviceJobType=='Y' }">
								<shiro:hasPermission name="TRADE.CASE.RESTART">
									<a role="button" id="processRestart"
										class="btn btn-primary btn-xm btn-activity"
										href="javascript:serviceRestart()">流程重启</a>
							    </shiro:hasPermission>
								</c:if>
							</c:if>	
							<c:if test="${isCaseOwner}">
								<a role="button" class="btn btn-primary btn-xm btn-activity"
									href="javascript:void(0)">贷款需求选择</a>
							</c:if>				
							<a role="button" class="btn btn-primary btn-xm btn-activity"
								href="javascript:evaPricingApply()">询价申请</a>
							<a role="button" class="btn btn-primary btn-xm btn-activity"
								href="javascript:void(0)">评估申请</a>
						</div>
						
	
						<!-- 交易计划变更 -->
						<div id="plan-modal-form" class="modal fade" role="dialog"
							aria-labelledby="plan-modal-title" aria-hidden="true">
							<div class="modal-dialog" style="width: 1000px">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">×</button>
										<h4 class="modal-title" id="plan-modal-title">交易计划变更</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<form id="plan-form" class="form-horizontal"></form>
										</div>
									</div>
									<div class="modal-footer">
	
										<button type="button" class="btn btn-primary"
											onclick="javascript:openTransHistory()">变更记录</button>
										<button type="button" class="btn btn-primary"
											onclick="javascript:resetPlanModal()">重置</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">关闭</button>
										<button type="button" class="btn btn-primary"
											onclick="return savePlanItems();">提交</button>
									</div>
								</div>
							</div>
						</div>
									
						<!-- 责任人变更 -->
						<div id="leading-modal-form" class="modal fade" role="dialog"
							aria-labelledby="leading-modal-title" aria-hidden="true">
							<div class="modal-dialog" style="width: 1200px">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">×</button>
										<h4 class="modal-title" id="leading-modal-title">
											请选择责任人</h4>
									</div>
									<div class="modal-body">
										<div class="row" style="height: 450px; overflow: auto;">
											<div class="col-lg-12 ">
												<h3 class="m-t-none m-b"></h3>
												<div class="wrapper wrapper-content animated fadeInRight">
													<div id="leading-modal-data-show" class="row"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>		
						<!-- loanRequirementChange -->
						<div id="loanReqmentChg-modal-form" class="modal fade"
							role="dialog" aria-labelledby="plan-modal-title"
							aria-hidden="true">
							<div class="modal-dialog" style="width: 1000px">
								<form method="get" class="form-horizontal"
									id="loan_reqment_chg_form">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="plan-modal-title">贷款需求选择</h4>
										</div>
	
										<!-- 交易单编号 -->
										<input type="hidden" name="caseCode"
											value="${toCase.caseCode}">
										<!-- 流程引擎需要字段 -->
										<input type="hidden" name="processInstanceId"
											value="${toWorkFlow.instCode}">
										<div class="modal-body">
											<div style="padding-left: 20px; padding-right: 20px;">
												<div class="row">
													<div class="col-md-7">
														<div class="form-group" id="data_1" name="isYouXiao">
															<label class="col-md-5 control-label"
																style='padding-left: 0px; text-align: left;'><font
																color="red">*</font>请选择客户贷款需求</label>
															<div class="col-md-7">
																<aist:dict clazz="form-control" id="mortageService"
																	name="mortageService" display="select"
																	defaultvalue="0" dictType="mortage_service" />
															</div>
														</div>
													</div>
												</div>
												<div class="row" id='div_releasePlan'>
													<div class="col-md-7">
														<div class="form-group">
															<label class="col-md-5 control-label"
																style='padding-left: 0px; text-align: left;'><font
																color="red">*</font>预计放款时间</label>
															<div class="col-md-7">
																<div class=" input-group date">
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span> <input type="text"
																		class="form-control" name="estPartTime"
																		id="estPartTime" disabled="disabled" value="">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													*请注意：当您选择纯公积金贷款时，您需要选择一位合作人；当您选择其它贷款时，默认的服务执行人为您自己。</div>
												<div class="divider">
													<hr>
												</div>
												<div id="hzxm"></div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											<button type="button" id="btn_loan_reqment_chg"
												class="btn btn-primary">变更</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


				<!-- 相关信息 -->
				<div class="panel " id="aboutInfo" style="min-height: 800px;">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#home_info" data-toggle="tab">交易相关信息</a>
							</li>
							<li class=""><a href="#profile_info" data-toggle="tab">贷款相关信息</a>
							</li>
							<li class=""><a href="#settings_info" data-toggle="tab">金融服务信息</a>
							</li>
							<li class=""><a href="#fujian_info" data-toggle="tab">附件信息</a>
							</li>
							<li class=""><a href="#ccai_info" data-toggle="tab">CCAI附件</a>
							</li>
							<li class=""><a href="#caseComment-info" data-toggle="tab">备注</a>
							</li>
							<li class=""><a href="#messages_info1" data-toggle="tab" >业绩记录</a>
							</li>
							<li cass=""><a href="#charge_info" data-toggle="tab" style="padding:10px;">收费情况</a>
							</li>
						</ul>

						<div class="tab-content">
							<div class="tab-pane fade active in" id="home_info">
								<div class="row ">
									<label class="col-sm-3 control-label">业务单创建时间：${caseDetailVO.createTime}</label>
									<label class="col-sm-3 control-label">接单时间：${caseDetailVO.resDate}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">卖方姓名：${caseDetailVO.sellerName}</label>
									<label class="col-sm-3 control-label">联系电话：${caseDetailVO.sellerMobile}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">买方姓名：${caseDetailVO.buyerName}</label>
									<label class="col-sm-3 control-label">联系电话：${caseDetailVO.buyerMobile}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">房屋性质：${caseDetailVO.houseProperty }</label>
									<label class="col-sm-3 control-label">购房年数：${caseDetailVO.holdYear }</label>
									<label class="col-sm-3 control-label">唯一住房：${caseDetailVO.isUniqueHome }</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">产证面积：${toPropertyInfo.square }</label>
									<label class="col-sm-3 control-label">产证地址：${toPropertyInfo.propertyAddr }</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">竣工年份：${toPropertyInfo.finishYear}</label>
									<label class="col-sm-3 control-label">所在楼层：${toPropertyInfo.locateFloor }</label>
									<label class="col-sm-3 control-label">总层高：${toPropertyInfo.totalFloor }</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">成交价：
										<c:if test="${!empty caseInfo.realPrice }">${caseInfo.realPrice/1000 }&nbsp&nbsp万元</c:if>
									</label>
									<label class="col-sm-3 control-label">合同价：
										<c:if test="${!empty caseInfo.conPrice }">${caseInfo.conPrice/10000 }&nbsp&nbsp万元</c:if>
									</label>
									<label class="col-sm-3 control-label">网签时间：${caseDetailVO.realConTime }</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">付款金额（一期）：
										<c:if test="${!empty caseInfo.amount1 }">${caseInfo.amount1/10000 }&nbsp&nbsp万元</c:if>
									</label>
									<label class="col-sm-3 control-label">付款金额（二期）：
										<c:if test="${!empty caseInfo.amount2 }">${caseInfo.amount2/10000 }&nbsp&nbsp万元</c:if>
									</label>
									<label class="col-sm-3 control-label">付款金额（三期）：
										<c:if test="${!empty caseInfo.amount3 }">${caseInfo.amount3/10000 }&nbsp&nbsp万元</c:if>
									</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">付款方式（一期）：${caseDetailVO.payType1}</label>
									<label class="col-sm-3 control-label">付款方式（二期）：${caseDetailVO.payType2}</label>
									<label class="col-sm-3 control-label">付款方式（三期）：${caseDetailVO.payType3}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">付款时间（一期）：${caseDetailVO.payTime1}</label>
									<label class="col-sm-3 control-label">付款时间（二期）：${caseDetailVO.payTime2}</label>
									<label class="col-sm-3 control-label">付款时间（三期）：${caseDetailVO.payTime3}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">缴税时间：${caseDetailVO.paymentTime }</label>
									<label class="col-sm-3 control-label">过户时间：${caseDetailVO.realHtTime }</label>
									<label class="col-sm-3 control-label">领证时间：${caseDetailVO.realPropertyGetTime}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">个人所得税：
										<c:if test="${!empty caseInfo.personalIncomeTax}"> ${caseInfo.personalIncomeTax/10000}&nbsp&nbsp万元 </c:if>
									</label>
									<label class="col-sm-3 control-label">增值税及附加税：
										<c:if test="${!empty caseInfo.businessTax}"> ${caseInfo.businessTax/10000}&nbsp&nbsp万元 </c:if>
									</label>
									<label class="col-sm-3 control-label">契税：
										<c:if test="${!empty caseInfo.contractTax}">  ${caseInfo.contractTax/10000}&nbsp&nbsp万元 </c:if>
									</label>
									<label class="col-sm-3 control-label">土地增值税：
										<c:if test="${!empty caseInfo.landIncrementTax}"> ${caseInfo.landIncrementTax/10000}&nbsp&nbsp万元 </c:if>
									</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">卖方剩余贷款：
										<c:if test="${!empty caseInfo.uncloseMoney}"> ${caseInfo.uncloseMoney/10000}&nbsp&nbsp万元 </c:if>
									</label>
									<label class="col-sm-3 control-label">还款方式：${caseDetailVO.closeType}</label>
									<label class="col-sm-3 control-label">还款时间：${caseDetailVO.loanCloseCode}</label>
									<label class="col-sm-3 control-label">还款银行：${caseInfo.upBank}</label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">结案时间：${caseDetailVO.closeTime}</label>
								</div>
							</div>
									
							<div class="tab-pane fade" id="profile_info">
								<div class="row ">
									<label class="col-sm-3 control-label">付款方式：</label>
									<label class="col-sm-3 control-label">贷款金额：${toMortgage.mortTotalAmount }&nbsp&nbsp万元</label>
									<label class="col-sm-3 control-label">是否自办：
										<c:choose>
											<c:when test="${toMortgage.isDelegateYucui=='1' }">否</c:when>
											<c:when test="${toMortgage.isDelegateYucui=='0' }">是</c:when>
											<c:otherwise>${toMortgage.isDelegateYucui }</c:otherwise>
										</c:choose>
									</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">面签时间：${caseDetailVO.signDate }</label>
									<label class="col-sm-3 control-label">递件时间：</label>
									<label class="col-sm-3 control-label">批贷时间：${caseDetailVO.apprDate }</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">公积金贷款金额：
										<c:if test="${!empty toMortgage.prfAmount}"> ${toMortgage.prfAmount}&nbsp&nbsp万元</c:if>
									</label>
									<label class="col-sm-3 control-label">公积金贷款年限：${toMortgage.prfYear}</label>
									<label class="col-sm-3 control-label">主贷人：${caseDetailVO.mortBuyer}</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">商贷贷款金额：
										<c:if test="${!empty toMortgage.comAmount}">${toMortgage.comAmount}&nbsp&nbsp万元</c:if>
									</label>
									<label class="col-sm-3 control-label">商贷贷款年限：${toMortgage.comYear}</label>
									<label class="col-sm-3 control-label">商贷利率折扣：${toMortgage.comDiscount}</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">放款方式：${caseDetailVO.lendWay}</label>
									<label class="col-sm-3 control-label">送他项证时间：${caseDetailVO.tazhengArrDate}</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">房贷套数：${toMortgage.houseNum}</label>
									<label class="col-sm-3 control-label">放款时间：${caseDetailVO.lendDate}</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">信贷员：${toMortgage.loanerName}</label>
									<label class="col-sm-3 control-label">信贷员联系电话：${toMortgage.loanerPhone}</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">评估公司：${caseDetailVO.evaName}</label>
									<label class="col-sm-3 control-label">评估价格：
										<c:if test="${!empty caseDetailVO.evaFee}">${caseDetailVO.evaFee}&nbsp&nbsp元</c:if>
									</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">承办银行：${caseDetailVO.parentBankName}</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">支行名称：${caseDetailVO.bankName}</label>
								</div>
							</div>
									
							<div class="tab-pane fade" id="settings_info">
								<div class="row ">
									<label class="col-sm-3 control-label">付款方式：</label>
									<label class="col-sm-3 control-label">贷款金额：</label>
									<label class="col-sm-3 control-label">是否自办：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">面签时间：</label>
									<label class="col-sm-3 control-label">递件时间：</label>
									<label class="col-sm-3 control-label">批贷时间：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">公积金贷款金额：</label>
									<label class="col-sm-3 control-label">公积金贷款年限：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">商贷贷款金额：</label>
									<label class="col-sm-3 control-label">商贷贷款年限：</label>
									<label class="col-sm-3 control-label">商贷利率折扣：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">放款方式：</label>
									<label class="col-sm-3 control-label">送房产证时间：</label>
									<label class="col-sm-3 control-label">送他项证时间：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">房贷套数：</label>
									<label class="col-sm-3 control-label">放款时间：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">信贷员：</label>
									<label class="col-sm-3 control-label">信贷员联系电话：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">评估公司：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">承办银行：</label>
								</div>
								<div class="row ">
									<label class="col-sm-3 control-label">支行名称：</label>
								</div>
							</div>
							
							<div class="tab-pane fade" id="fujian_info">
								<div class="panel-body">
									<div id="imgShow" class=""></div>
								</div>
							</div>
							
							<div class="tab-pane fade" id="ccai_info">
								<table id="gridTable"></table>
								<div id="gridPager"></div>
							</div>
							
							<div class="tab-pane fade" id="caseComment-info">
								<div id="caseCommentList" class="add_form"></div>
							</div>
													
							<div class="tab-pane fade" id="messages_info1">
                                <div class="table_content">
                                    <table class="table table_blue table-striped table-bordered table-hover" id="editable" >
                                        <tbody id="table_content_pre">                        			
                                        </tbody>
                                    </table> 
                                    <table class="table table_blue table-striped table-bordered table-hover" >
                                        <tbody id="table_content_pre_partner">            			
                                        </tbody>
                                    </table>
                                    <div>
	                                    <div class="row ">
	                                    	<label class="control-label" style="margin-left:20px;">合计 ：<label id="totalFee"/>
	                                   	</div>
	                                   	<div class="row ">
	                                    	<label class="control-label" style="margin-left:20px;">总业绩 ：<label id="totalPerformance"/>
	                                   	</div>
	                                   	<div class="row ">
                                    		<label class="control-label" style="margin-left:20px;">单数合计 ：<label id="totalTurnoverNum"/>
                                    	</div>
                                    </div>
                                </div>
                            </div>

							<div class="tab-pane fade" id="charge_info">
								<div class="row">
									<label class="col-sm-3 control-label">业主应收：<label id="ownerReceivableCommission"></label></label>
									<label class="col-sm-3 control-label">客户应收：<label id="guestReceivableCommission"></label></label>
									<label class="col-sm-3 control-label">收佣日期（业主）：<label id="ownerCommissionTime"></label></label>
									<label class="col-sm-3 control-label">收佣日期（客户）：<label id="guestCommissionTime"></label></label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">业主折佣：<label id="ownerCommissionDis"></label></label>
									<label class="col-sm-3 control-label">客户折佣：<label id="guestCommissionDis"></label></label>
									<label class="col-sm-3 control-label">业主佣金到期日：<label id="ownerCommissionMature"></label></label>
									<label class="col-sm-3 control-label">客户佣金到期日：<label id="guestCommissionMature"></label></label>
								</div>
								<div class="row">
									<label class="col-sm-3 control-label">评估值：<label id="assessmentFee"></label></label>
									<label class="col-sm-3 control-label">应收评估值：<label id="receivableAssessmentFee"></label></label>
									<label class="col-sm-3 control-label">实收评估值：<label id="receiptsAssessmentFee"></label></label>
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
	<script src="<c:url value='/js/trunk/case/caseDetail_new.js' />"></script>
	<%-- <script src="<c:url value='js/trunk/case/showCaseAttachment.js' />"></script> --%>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<%-- <script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script> --%>
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
			//备注,common.js 
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
               //固定导航头
            	$('.stickup-nav-bar').stickUp({
                // $('.col-lg-9').stickUp({
                                    parts: {
                                      0:'basicInfo',
                                      1:'serviceFlow',
                                      2:'aboutInfo'
                                    },
                                    itemClass: 'menuItem',
                                    itemHover: 'active',
                                    marginTop: 'auto'
                                  });
            });
        });



	</script>
	<script
		id="template_successList" type="text/html">
		<thead>
			<tr>
				<th></th>
				<th>部门</th>
				<th>员工</th>
				<th>分成金额</th>
				<th>分成比例</th>
				<th>分成说明</th>
				<th>成交单数</th>
			</tr>
		</thead>
	{{each sharingInfo as item index}}
    	<tr>
       		<td class="td_width">
				{{if item.type == 1}}
					分成人
				{{else}}
					权证
				{{/if}}
			</td>
			<td class="td_width">
				{{item.department}}
			</td>
			<td class="td_width">
				{{item.employee}}
			</td>
			<td class="td_width">
				{{item.sharingAmount}}
			</td>
			<td class="td_width">
				{{item.sharingProportion}}
			</td>
			<td class="td_width">
				{{item.sharingInstruction}}
			</td>
			<td class="td_width">
				{{item.turnoverNum}}
			</td>                                     
		</tr>                                        
	{{/each}}
	</script>
	<script
		id="template_successList2" type="text/html">
		<thead>
			<tr>
				<th></th>
				<th>合作费类型</th>
				<th>分成金额</th>
				<th>分成比例 </th>
				<th>合作人</th>
				<th>合作部门</th>
				<th>合作经理</th>
			</tr>
		</thead>
	{{each cooperateFeeInfo as item index}}
    	<tr>
       		<td class="td_width">
				合作人
			</td>
            <td class="td_width">
				{{item.cooperateFeeType}}
			</td>
			<td class="td_width">
				{{item.sharingAmount}}
			</td> 
			<td class="td_width">
				{{item.sharingProportion}}
			</td> 
			<td class="td_width">
				{{item.partner}}
			</td> 
			<td class="td_width">
				{{item.cooperateDepartment}}
			</td> 			
			<td class="td_width">
				{{item.cooperateManager}}
			</td>                                    
		</tr>                                        
	{{/each}}
	</script>
</content>

</body>
</html>

