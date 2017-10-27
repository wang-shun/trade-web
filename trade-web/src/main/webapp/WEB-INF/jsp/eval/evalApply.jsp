<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>评估申请</title>

<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet">

<!-- stickUp fixed css -->
<link href="<c:url value='/static/css/plugins/stickup/stickup.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/trans/css/common/stickDash.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/plugins/aist-steps/steps.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- index_css  -->

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/evalBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="wrapper border-bottom white-bg page-heading">
			<div class="pl10">
			<h2 class="newtitle-big">
				评估申请
			</h2>
			<div class="mt20">
				<button type="button" class="btn btn-icon btn-blue mr5" id="btnEvalView">
					<i class="iconfont icon">&#xe600;</i> 评估视图
				</button>
				<button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
					<i class="iconfont icon">&#xe63f;</i> 案件视图
				</button>
			</div>
		</div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form_list" id="evalApplyForm">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
						<ul class="form_lump">
						<div class="modal_title title-mark">
                                	填写任务信息
                        </div>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									style="color:red">* </i> 评估类型选择
								</label>
								<aist:dict id="reportType" name="reportType" clazz="select_control sign_right_two" display="select" defaultvalue="${toEvalReportProcessVo.reportType}" dictType="EVAL_TYPE"  ligerui='none' onchange="evaTypeChange()"></aist:dict>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two">原购入价</label>
								<input class="input_type sign_right_two"  name="ornginPrice" id="ornginPrice" value="${toEvalReportProcessVo.ornginPrice / 10000.00}" style="visibility:hidden" onkeyup="checkNum(this)">
								<div class="input-group date_icon">
									<span class="danwei">万</span>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 评估公司</label> 
								<select class="select_control sign_right_two" name="finOrgId" id="finOrgId" ></select>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two">评估公司联系人</label> 
								<input class="input_type sign_right_two"  name="evaComContact" id="evaComContact" value="${toEvalReportProcessVo.evaComContact}">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two">联系方式</label> 
								<input class="input_type sign_right_two"  name="contactWay" id="contactWay" value="${toEvalReportProcessVo.contactWay}">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 房龄</label>
								<input class="input_type sign_right_two"  name="houseAgeApply" id="houseAgeApply" value="${toEvalReportProcessVo.houseAgeApply}">
								<div class="input-group date_icon">
									<span class="danwei">年</span>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i>询价值</label> 
								<input class="input_type sign_right_two"  name="inquiryResult" id="inquiryResult" value="${toEvalReportProcessVo.inquiryResult / 10000.00}">
								<div class="input-group date_icon">
									<span class="danwei">万</span>
								</div>
							</div> 
						</li>
						
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i>评估报告份数</label>  							
								<input class="input_type sign_right_two"  name="reportNum" id="reportNum" value="${toEvalReportProcessVo.reportNum}">												
							</div>
							
							<!-- <div class="form_content">
								<label class="control-label sign_left_two"> <i  style="color:red">* </i>申请评估日期</label>
								<div id="datepicker" class="sign_right_two" id="data_1" data-date-format="yyyy-mm-dd">
	                            	 <input type="text" class="input_type data_style" id="realHtTime" name="applyDate">
	                            </div>
							</div> -->
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 申请评估日期</label> 
								<input class="input_type sign_right_two"   name="applyDate" id="applyDate" value="<fmt:formatDate value="${toEvalReportProcessVo.applyDate}" type="date" pattern="yyyy-MM-dd"/>"/>
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						
					</ul>
					<p class="text-center">
					        <c:if test="${source == null}">
							<input type="button" class="btn btn-success submit_From" value="提交">
							</c:if>
							<c:if test="${source == 'evalDetails'}">
							<input type="hidden" id="evaCode" name="evaCode" value="${evaCode}">
							<input type="button" class="btn btn-success submit_save" value="保存">
							</c:if>
						    <input type="button" id="closeButton" class="btn btn-grey ml5" value="关闭">
					</p>
				</form>
			</div>
		</div>
	</div>

	<!-- Mainly scripts -->
	<content tag="local_script">
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script> 
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="<c:url value='/js/common/textarea.js' />"></script>
    <%-- <script src="<c:url value='/js/eloan/eloancommon.js' />"></script>  --%>
    <script src="<c:url value='/js/common/common.js' />"></script>
		<script>	
		$(document).ready(function() {
				getEvaCompanyList();
				
				$('.input-daterange').datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});
				
				$('.submit_From').click(function() {
					if (!checkForm()) {
						return;
					}
					submitEvalApply('${ctx}/task/eval/submitApply','评估申请提交成功');
				});
				
				$('.submit_save').click(function() {
					if (!checkForm()) {
						return;
					}
					submitEvalApply('${ctx}/task/eval/saveApply','评估申请提交成功');
				});
				
		});
		
		//关闭
		$('#closeButton').click(function() {
			window.close();
		});
		
		/*获取评估公司列表*/
		function getEvaCompanyList(pcode){
			var friend = $("#finOrgId");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryEvaCompany",
			    method:"post",
			    dataType:"json",
			    data:{"pcode":pcode},
		    	success:function(evaComList){
		    		if(evaComList != null){
		    			for(var i = 0;i<evaComList.length;i++){
		    					friend.append("<option value='"+evaComList[i].finOrgCode+"'>"+evaComList[i].finOrgName+"</option>");
		    			}
		    		}
		    	}
			  });
		}
		
		function submitEvalApply(url,message){
			var jsonData = $("#evalApplyForm").serializeArray();
			//var url = "${ctx}/task/eval/submitApply";			
			
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : jsonData,
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '1900'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '1900'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					window.wxc.success(message,{"wxcOk":function(){
						window.close();
					}});
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		
		//询价类型非贷款时，显示原购入价
		function evaTypeChange(){
			var evaVal = $('#reportType').val();
			if(evaVal == '2' || evaVal == '3'){
				$('#ornginPrice').css('visibility','visible');
			}else{
				$('#ornginPrice').css('visibility','hidden');
			}
		}
		
		//必填项
		function checkForm() {
			if ($("#reportType").val() == '') {
				window.wxc.alert("请选择评估类型");
				return false;
			}
			if ($("#finOrgId").val() == '') {
				window.wxc.alert("请选择评估公司");
				return false;
			}
			if ($("#houseAgeApply").val() == '') {
				window.wxc.alert("请填写房龄");
				return false;
			}
			if ($("#inquiryResult").val() == '') {
				window.wxc.alert("请填写询价值");
				return false;
			}
			if ($("#reportNum").val() == '') {
				window.wxc.alert("请填写评估报告份数");
				return false;
			}
			if ($("#applyDate").val() == '') {
				window.wxc.alert("请填写申请评估日期");
				return false;
			}
			return true;
		}
		</script>
	</content>
</body>


</html>