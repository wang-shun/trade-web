<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>e+产品</title>

<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<link href="${ctx}/static/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/style.css" rel="stylesheet">

<!-- stickUp fixed css -->
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/trans/css/common/stickDash.css"
	rel="stylesheet">

<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- index_css  -->

<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css"
	rel="stylesheet" />
<link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet" />
<link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<link href="${ctx}/css/jquery.editable-select.min.css" rel="stylesheet">
</head>


<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

		<div class="row">
			<div class="ibox-content" id="zj_info">
				<form method="get" class="form_list" id="mortgageForm">
					<%-- <input type="hidden" name="caseCode" id="caseCode"
						value="${caseCode}" /> <input type="hidden" id="excutorId"
						name="excutorId" value="${excutorId}" />

					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId"
						name="processInstanceId" value="${processInstanceId}"> <input
						type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!--  -->
					<input type="hidden" id="eloanCode" name="eloanCode"
						value="${eloanCase.eloanCode}">
						<input type="hidden" id="createTime" name="createTime"
						value="<fmt:formatDate value="${eloanCase.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" >
						<input type="hidden" id="createBy" name="createBy"
						value="${eloanCase.createBy}"> --%>
					<ul class="form_lump">
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"> </i> PKID
								</label>
							 <input class="input_type sign_right_two" value="${mortgage.pkid}"
									name="pkid" id="pkid">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"> </i>案件编号
								</label>  <input class="input_type sign_right_two" value="${mortgage.caseCode}"
									name="caseCode" id="caseCode">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"> </i>按揭贷款类型
								</label>  <input class="input_type sign_right_two" value="${mortgage.mortType}"
									name="mortType" id="mortType">
							</div>

						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>贷款总额
								</label>
								<input class="input_type sign_right_two" value="${mortgage.mortTotalAmount}"
									name="mortTotalAmount" id="mortTotalAmount">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>贷款机构
								</label>
								<input class="input_type sign_right_two" value="${mortgage.finOrgCode}"
									name="finOrgCode" id="finOrgCode">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>商贷金额
								</label>
								<input class="input_type sign_right_two" value="${mortgage.comAmount}"
									name="comAmount" id="comAmount">
							</div>
						</li>
					<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>商贷年限
								</label>
								<input class="input_type sign_right_two" value="${mortgage.comYear}"
									name="comYear" id="comYear">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>商贷利率折扣
								</label>
								<input class="input_type sign_right_two" value="${mortgage.comDiscount}"
									name="comDiscount" id="comDiscount">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>公积金金额
								</label>
								<input class="input_type sign_right_two" value="${mortgage.prfAmount}"
									name="prfAmount" id="prfAmount">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>公积金贷款年限
								</label>
								<input class="input_type sign_right_two" value="${mortgage.prfYear}"
									name="prfYear" id="prfYear">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>放款方式
								</label>
								<input class="input_type sign_right_two" value="${mortgage.lendWay}"
									name="lendWay" id="lendWay">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>认定套数
								</label>
								<input class="input_type sign_right_two" value="${mortgage.houseNum}"
									name="houseNum" id="houseNum">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>备注
								</label>
								<input class="input_type sign_right_two" value="${mortgage.remark}"
									name="remark" id="remark">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>信贷员是否到场签约
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isLoanerArrive}"
									name="isLoanerArrive" id="isLoanerArrive">
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 签约时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.signDate}" pattern="yyyy-MM-dd" />"
									name="signDate" id="signDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 批贷时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.apprDate}" pattern="yyyy-MM-dd" />"
									name="apprDate" id="apprDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 他证送达时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.tazhengArrDate}" pattern="yyyy-MM-dd" />"
									name="tazhengArrDate" id="tazhengArrDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 放款时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.lendDate}" pattern="yyyy-MM-dd" />"
									name="lendDate" id="lendDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>客户编号
								</label>
								<input class="input_type sign_right_two" value="${mortgage.custCode}"
									name="custCode" id="custCode">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>客户单位
								</label>
								<input class="input_type sign_right_two" value="${mortgage.custCompany}"
									name="custCompany" id="custCompany">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>客户名称
								</label>
								<input class="input_type sign_right_two" value="${mortgage.custName}"
									name="custName" id="custName">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否需要评估报告
								</label>
								<input class="input_type sign_right_two" value="${mortgage.ifReportBeforeLend}"
									name="ifReportBeforeLend" id="ifReportBeforeLend">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>信贷员姓名
								</label>
								<input class="input_type sign_right_two" value="${mortgage.loanerName}"
									name="loanerName" id="loanerName">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>信贷员电话
								</label>
								<input class="input_type sign_right_two" value="${mortgage.loanerPhone}"
									name="loanerPhone" id="loanerPhone">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>最终贷款银行
								</label>
								<input class="input_type sign_right_two" value="${mortgage.lastLoanBank}"
									name="lastLoanBank" id="lastLoanBank">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>办理方式
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isDelegateYucui}"
									name="isDelegateYucui" id="isDelegateYucui">
							</div>
							<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i>公积金申请时间
								</label>
								<input class="input_type sign_right_three" value="<fmt:formatDate value="${mortgage.prfApplyDate}" pattern="yyyy-MM-dd" />"
									name="prfApplyDate" id="prfApplyDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>

						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否需要复议
								</label>
								<input class="input_type sign_right_two" value="${mortgage.ifRequireReconsider}"
									name="ifRequireReconsider" id="ifRequireReconsider">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否主贷银行
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isMainLoanBank}"
									name="isMainLoanBank" id="isMainLoanBank">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否有效
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isActive}"
									name="isActive" id="isActive">
							</div>
						</li>
						<li>
							<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i>创建时间
								</label>
								<input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.createTime}" pattern="yyyy-MM-dd" />"
									name="createTime" id="createTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>创建人
								</label>
						        <input type="hidden" id="createBy" name="createBy" value='${mortgage.createBy}'>
						        <input type="text" id="realName"  class="input_type sign_right_two" style="background-color:#FFFFFF;" readonly="readonly"   onclick="userSelect({startOrgId:'ff8080814f459a78014f45a73d820006',expandNodeId:'ff8080814f459a78014f45a73d820006',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectCreateBack})" value='${mortgage.createBy}'>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
								<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i>更新时间
								</label>
								<input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.updateTime}" pattern="yyyy-MM-dd" />"
									name="updateTime" id="updateTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>更新人
								</label>
								<input type="hidden" id="updateBy" name="updateBy" value='${mortgage.updateBy}'>
						        <input type="text" id="realName2"  class="input_type sign_right_two" style="background-color:#FFFFFF;" readonly="readonly"   onclick="userSelect({startOrgId:'ff8080814f459a78014f45a73d820006',expandNodeId:'ff8080814f459a78014f45a73d820006',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUpdateBack})" value='${mortgage.updateBy}'>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>贷款推荐函编号
								</label>
								<input class="input_type sign_right_two" value="${mortgage.recLetterNo}"
									name="recLetterNo" id="recLetterNo">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否临时银行
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isTmpBank}"
									name="isTmpBank" id="isTmpBank">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>临时银行处理人
								</label>
								<input type="hidden" id="tmpBankUpdateBy" name="tmpBankUpdateBy" value='${mortgage.tmpBankUpdateBy}'>
						        <input type="text" id="realName2"  class="input_type sign_right_two" style="background-color:#FFFFFF;" readonly="readonly"   onclick="userSelect({startOrgId:'ff8080814f459a78014f45a73d820006',expandNodeId:'ff8080814f459a78014f45a73d820006',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectTmpBankBack})" value='${mortgage.tmpBankUpdateBy}'>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
							<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_three"> <i
									class="red"></i>临时银行处理时间
								</label>
								<input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.tmpBankUpdateTime}" pattern="yyyy-MM-dd" />"
									name="tmpBankUpdateTime" id="tmpBankUpdateTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
<%-- 							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>临时银行原因
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isActive}"
									name="isActive" id="isActive">
							</div> --%>
						</li>
					</ul>
					<p class="text-center">
						<input type="button" class="btn btn-success submit_From" onclick="saveEloanApply()"
							value="提交"> <a type="button"
							href="${ctx}/test/mortgageList" class="btn btn-grey ml5">关闭</a>
					</p>

				</form>
			</div>

		</div>
	</div>
	<!-- main End -->
	</div>

	<!-- Mainly scripts -->
	<content tag="local_script"> <!-- stickup plugin --> <script
	<%@include
		file="/WEB-INF/jsp/tbsp/common/userorg.jsp"%>
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script> 
		src="${ctx}/static/js/morris/morris.js"></script> <script
		src="${ctx}/static/js/morris/raphael-min.js"></script> <!-- index_js -->
	   <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- aist --> <script src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/jquery.editable-select.min.js"></script> 

 <script>
		$(document)
				.ready(
						function() {

							$('.input-daterange').datepicker({
								format : 'yyyy-mm-dd',
								weekStart : 1,
								autoclose : true,
								todayBtn : 'linked',
								language : 'zh-CN'
							});});
		function saveEloanApply() {		
			var jsonData = $("#mortgageForm").serializeArray();
			console.log(jsonData);
			console.log("===Result==="+JSON.stringify(jsonData));			
			var url = "${ctx}/test/saveMortgage";
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
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
					alert(data.message);
					debugger;
					var bohui = $("#processInstanceId").val();
					if (bohui != null && bohui != '') {
						window.close();
						window.opener.callback();
					} else {
						window.close();
						window.opener.callback();
					}

				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}
		function selectCreateBack(array) {
			if (array && array.length > 0) {
				$("#realName").val(array[0].username);
				$("#createBy").val(array[0].userId);

			} else {
				$("#realName").val("");
				$("#createBy").val("");
			}
		}
		function selectUpdateBack(array) {
			if (array && array.length > 0) {
				$("#realName2").val(array[0].username);
				$("#updateBy").val(array[0].userId);

			} else {
				$("#realName2").val("");
				$("#updateBy").val("");
			}
		}
		function selectTmpBankBack(array) {
			if (array && array.length > 0) {
				$("#realName3").val(array[0].username);
				$("#tmpBankUpdateBy").val(array[0].userId);

			} else {
				$("#realName3").val("");
				$("#tmpBankUpdateBy").val("");
			}
		}
	</script> </content>


</body>

</html>