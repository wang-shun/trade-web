<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>
<head>
<!-- 上传相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"	rel="stylesheet">
<!-- 展示相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"	rel="stylesheet" /> 

<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
	var finOrgCode = "${mortgage.lastLoanBank}";
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
	   <!-- 服务流程 -->
		<div class="row wrapper white-bg new-heading " id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                        贷款流失申请
                    </h2>
                <div class="mt20">
                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
                            <i class="iconfont icon">&#xe600;</i> 在途单列表
                        </button>
                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
                            <i class="iconfont icon">&#xe63f;</i> 案件视图
                        </button>
                    </div>
             </div>
        </div>
		<div class="ibox-content border-bottom clearfix space_box noborder">

            <div>
                <h2 class="newtitle title-mark">填写任务信息</h2>
                <div class="form_list">
                <form method="post" class="form-horizontal" id="loanlostApplyForm">
			<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode"	value="${taskitem}"> 
					<input type="hidden" id="custName"	name="custName" value=""> 
					<input type="hidden" id="finOrgCode" name="finOrgCode" value="${mortgage.finOrgCode }">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode"		value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" 	name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="pkid" name="pkid" value="${mortgage.pkid}">					
					<input type="hidden" id="h_custCode" value="${mortgage.custCode}">
					
					<input type="hidden" id="comAmount" name="comAmount" value="${mortgage.comAmount}">
					<input type="hidden" id="comYear" name="comYear" value="${mortgage.comYear}">
					<input type="hidden" id="comDiscount" name="comDiscount" value="${mortgage.comDiscount}">
					<input type="hidden" id="prfAmount" name="prfAmount" value="${mortgage.prfAmount}">
					<input type="hidden" id="prfYear" name="prfYear" value="${mortgage.prfYear}">
					<input type="hidden" id="houseNum" name="houseNum" value="${mortgage.houseNum}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType"	value="${approveType }"> 
					<input type="hidden" id="lapPkid"	name="lapPkid" value="${toApproveRecord.pkid }"> 
					<input type="hidden" id="operator" name="operator" value="${operator }">
                    <div class="marinfo">
                        <div class="line clearfix" style="overflow:visible">
                            <div class="form_content">
                                <label class="control-label sign_left_small">承办银行</label>
                                <select class=" select_control  " name="bank" id="bank">
								</select>
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">支行名称</label>
                                <select class=" select_control  " name="lastLoanBank" id="lastLoanBank">
								</select>
                            </div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">主贷人<font color=" red" class="mr5" >*</font></label>
                                <select class="yuanwid select_control " name="custCode" id="custCode">
								</select>
                            </div>
                            <div class="form_content ml20">
                                <label class="control-label sign_left_small">贷款流失金额<font color=" red" class="mr5" >*</font></label> 
                                <input type="text" class=" input_type yuanwid" id="mortTotalAmount" name="mortTotalAmount" value="${mortgage.mortTotalAmount/10000}">
                                <span class="date_icon">万元</span>
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">主贷人单位</label> 
                                <input type="text" name="custCompany" id="custCompany" class=" input_type" value="${custCompany }">
                            </div>
                        </div>


                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">客户自办贷款确认函编号</label>
                                <input type="text" class=" input_type yuanwid" id="loanLostConfirmCode" name="loanLostConfirmCode" value="${mortgage.loanLostConfirmCode}">
                                <span class="font12">备注：编号范例：ZY0000001ZB;若开具公司版本客户自办贷款确认函,则必须填写编号并上传附件,反之则无需上传</span>
                            </div>
                        </div>
                        <div class="line">
                            <label class="control-label sign_left_small" style="vertical-align: top;">贷款流失原因</label>
                            <div class="inline" style="width: 750px;" id="loanLostApply" style="display: block">
                             <div class="row">
							<c:forEach items="${loanLostApplyReasons}"	var="loanLostApplyReasonForShow">
								<div class="col-md-6 no-padding">
									<input type="checkbox"	   value="${loanLostApplyReasonForShow.name}"	id="loanLostApplyReasonShow" name="loanLostApplyReasonShow"
										class="input_type "	onChange="loanLostApplyReasonAppend(this.checked,'${loanLostApplyReasonForShow.name}');">
									<label>${loanLostApplyReasonForShow.name}</label>
								</div>
							</c:forEach>
							</div>
							</div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">已选择原因</label>
                                <input type="text" class="input_type yuanwid" style="width:820px;" id="loanLostApplyReason"
								name="loanLostApplyReason" value="${mortgage.loanLostApplyReason}" readonly="readonly">
                            </div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small" style="vertical-align: top;">贷款流失具体原因<font color=" red" class="mr5" >*</font></label>
                                <textarea class="input_type yuanwid" style="width:820px;height:75px;resize:none;" id="selfDelReason" name="selfDelReason">${mortgage.selfDelReason }</textarea>
                            </div>
                        </div>
                    </div>
                    </form>
                </div>
            </div>
            
            <!-- 案件跟进 -->
            <div class="view-content" id="caseCommentList"> </div>
        	<div class="mt30 clearfix" id="aboutInfo">
        		<c:choose>
				<c:when test="${accesoryList!=null}">
					<h2 class="newtitle title-mark">上传备件</h2>
					<div class="file-list">
					   <div class="file-content">
						<div class="table-box" id="loanlostApplyfileUploadContainer"></div>
						</div>
				</c:when>
				<c:otherwise>
					<h5>
						上传备件<br>无需上传备件
					</h5>
				</c:otherwise>
			</c:choose>
        </div>
        <div class="form-btn">
              <div class="text-center">
                  <a href="#" class="btn btn-success btn-space" onclick="save(false)">保存</a>
                   <a href="#" class="btn btn-success btn-space" onclick="submit()" readOnlydata="1">提交</a>
              </div>
       </div>
            
            </div>
	</div>
	<content tag="local_script"> <!-- Peity --> 
	<script	 src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<!-- Custom and plugin javascript -->
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
	<!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

	<!-- 上传附件相关 --> 
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script> 
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="<c:url value='/js/trunk/task/attachment3.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script	src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script> 
	<!-- bank select -->
	<script	src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
	<script	src="<c:url value='/js/trunk/comment/caseComment.js' />"></script> 
	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
		
	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>
	
	<script>
			var source = "${source}";
			function readOnlyForm() {
				$(".readOnly_date").removeClass('date');
				$(".readOnly_date input").attr('readOnly', true);
				$("select[readOnlydata=1]").closest('.row').hide();
				$("[readOnlydata=1]").attr('readonly', true);
				$("[readOnlydata=1]").each(function() {
					if ($(this).is('a')) {
						$(this).hide();
					}
				});
			}

			function loanLostApplyReasonAppend(isAppend, content) {
				if (isAppend) {
					var oldVal = $("#loanLostApplyReason").val();
					if (oldVal != '') {
						oldVal += ';';
					}
					$("#loanLostApplyReason").val(oldVal + content);
				} else {
					var oldVal = $("#loanLostApplyReason").val();
					var oldValArray = oldVal.split(";");
					var newVal = "";
					for (var i = 0; i < oldValArray.length; i++) {
						if (oldValArray[i] != content) {
							newVal += oldValArray[i] + ";";
						}
					}
					newVal = newVal.substr(0, newVal.length - 1);
					$("#loanLostApplyReason").val(newVal);
				}
			}

			//查询客户信息
			function getGuestInfo() {
				$.ajax({
					url : ctx + "/task/getGuestInfo",
					method : "post",
					dataType : "json",
					data : {
						caseCode : $("#caseCode").val()
					},
					success : function(data) {
						if (data != null && data.length > 0) {
							$("select[name='custCode']").html("");
							$("select[name='custCode']").append(
									"<option cName='' value='' >请选择</option>");
							for (var i = 0; i < data.length; i++) {
								var selected = '';
								if ($('#h_custCode').val() == data[i].pkid) {
									selected = 'selected';
								}
								$("select[name='custCode']").append(
										"<option value='"+data[i].pkid+"' "+ selected+">"
												+ data[i].guestName
												+ "</option>");
							}
						}
					}
				});
			}

			$(document).ready(function() {
				forLoanLostApplyReasonShow();

				if ('caseDetails' == source) {
					readOnlyForm();
				}


				getGuestInfo();

				/*案件备注信息*/
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : taskitem
				});
			});

			/**提交数据*/
			function submit() {
				if (checkAttachmentForLoanLost($("#loanLostConfirmCode").val())) {//验证是否上传附件
					//验证上传文件是否全部上传
					var isCompletedUpload = fileUpload.isCompletedUpload();
					
					if(!isCompletedUpload){
						window.wxc.alert("附件还未全部上传!");
						return false;
					}
					save(true);
				}
			}

			/**保存数据*/
			function save(b) {
				
				if(!b){
					if ( ($("#loan_lost_confirmation_pic_list li").length == undefined || $("#loan_lost_confirmation_pic_list li").length == 0) ) {
						if($("#loanLostConfirmCode").val().length>0){
							window.wxc.alert("请上传附件信息！");
							return false;
						}
					}
				}
				if (!checkForm()) {
					return;
				}

				if (!checkLoanLostApplyReasons()) {
					return;
				}
				$("#finOrgCode").val($("#lastLoanBank").val());
				var jsonData = $("#loanlostApplyForm").serializeArray();
				deleteAndModify();

				var url = "${ctx}/task/mortgage/saveLoanlostApply";
				if (b) {
					url = "${ctx}/task/mortgage/submitLoanlostApply";
				}			
				$.ajax({
					cache : true,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					data : jsonData,
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
					complete : function() {					
						if (status == 'timeout') {
							Modal.alert({
								msg : "抱歉，系统处理超时。"
							});
							$(".btn-primary").one("click", function() {
								parent.$.fancybox.close();
							});
						}
					},
					success : function(data) {					
						$.unblockUI();
						if (b) {
							caseTaskCheck();
							if (null != data.message) {
								window.wxc.alert(data.message);
							}
						} else {
							window.wxc.success("保存成功。",{"wxcOk":function(){
								window.close();
								window.opener.callback();
							}});
						}						
					},
					error : function(errors) {
						window.wxc.error("数据保存出错");
					}
				});				
			}

			//验证控件checkUI();
			function checkForm() {
				if ($("input[name='mortTotalAmount']").val() == '') {
					window.wxc.alert("贷款流失金额为必填项!");
					$("input[name='mortTotalAmount']").focus();
					return false;
				}
				if ($("select[name='custCode']").val() == '') {
					window.wxc.alert('请选择主贷人');
					return false;
				}
				$("input[name='custName']").val($("select[name='custCode']").find("option:selected").text());
				return true;
			}

			//验证贷款流失相应字段信息
			function checkLoanLostApplyReasons() {
				var loanLostApplyFlag = true
				if ($("input[name='loanLostApplyReason']").val() == '') {
					window.wxc.alert("贷款流失必须勾选相应原因!");
					$("input[name='loanLostApplyReason']").focus();
					loanLostApplyFlag = false;
					return loanLostApplyFlag;
				}
				//获取textarea文本域的值
				if ($("#selfDelReason").val() == '') {
					window.wxc.alert("贷款流失具体原因不能为空!");
					$("#selfDelReason").focus();
					loanLostApplyFlag = false;
					return loanLostApplyFlag;
				} else {
					if ($("#selfDelReason").val().length < 30) {
						window.wxc.alert("贷款流失具体原因不详细,具体原因不能少于30字！");
						$("#selfDelReason").focus();
						loanLostApplyFlag = false;
						return loanLostApplyFlag;
					}
				}
				return loanLostApplyFlag;
			}

		
			//页面初始化时  初始化复选框按钮
			function forLoanLostApplyReasonShow() {
				var oldVal = $("#loanLostApplyReason").val();
				var oldValArray = oldVal.split(";");
				var forReasonsArray = [];

				$("input[name='loanLostApplyReasonShow']").each(function() {
					forReasonsArray.push($(this).val());
				})

				for (var i = 0; i < oldValArray.length; i++) {
					for (var j = 0; j < forReasonsArray.length; j++) {
						if (forReasonsArray[j] == oldValArray[i]) {
							var obj = $("input[name='loanLostApplyReasonShow']")[j];
							$(obj).attr("checked", true)
							//$("input[name='loanLostApplyReasonShow'][j]:checkbox").attr("checked", true);

						}
					}
				}
			}
			
			
			function checkAttachmentForLoanLost(loanLostConfirmCode){	
				if(loanLostConfirmCode != '' && loanLostConfirmCode != null){
					
/* 				 	if (($("#loan_lost_letter_pic_list li").length == undefined || $("#loan_lost_letter_pic_list li").length == 0)
						|| ($("#loan_lost_confirmation_pic_list li").length == undefined || $("#loan_lost_confirmation_pic_list li").length == 0) ) { */
				 	if ( ($("#loan_lost_confirmation_pic_list li").length == undefined || $("#loan_lost_confirmation_pic_list li").length == 0) ) {
						window.wxc.alert("请上传附件信息！");
						checkAtt = false;
						return false;
					}else{
						checkAtt = true;
					} 
					//贷款流失申请中，附件部分“贷款流失申请书”不再作为必填项上传
					checkAtt = true;
					/* $.each(idList, function(index, value){//遍历所传附件的fileId
						var length = $("#picContainer"+value).find("img").length;
						if(length == 0) {
							window.wxc.alert("请上传附件信息！");
							checkAtt = false;
							return false;
						} else {
							checkAtt = true;
						}
					}); */
				}else{
					/* if ($("#loan_lost_letter_pic_list li").length == undefined || $("#loan_lost_letter_pic_list li").length == 0) {
						window.wxc.alert("请上传附件信息！");
						checkAtt = false;
						return false;
					}else{
						checkAtt = true;
					} */
					//贷款流失申请中，附件部分“贷款流失申请书”不再作为必填项上传
					checkAtt = true;
					
					if ($("#loan_lost_confirmation_pic_list li").length == undefined || $("#loan_lost_confirmation_pic_list li").length == 0) {
						checkAtt = true;
					}else{
						window.wxc.alert("[客户自办贷款确认函]附件须与[客户自办贷款确认函编号]同步！");
						checkAtt = false;
						return false;
					}
					/* $.each(idList, function(index, value){
						var length = $("#picContainer"+value).find("img").length;
						if($("#fileFlagCode"+value).val() == 'loan_lost_confirmation'){
							if(length != 0){
								window.wxc.alert("[客户自办贷款确认函]附件须与[客户自办贷款确认函编号]同步！");
								checkAtt = false;
								return false;
							}else{
								checkAtt = true;
							}
						}else{
							if(length == 0) {
								window.wxc.alert("请上传附件信息！");
								checkAtt = false;
								return false;
							} else {
								checkAtt = true;
							}
						}
						
					}); */
				}
				return checkAtt;
			}
			
			//渲染图片 <viewer.min.js中调用>
			function renderImg(){		
				$('.wrapper-content').viewer('destroy');
				$('.wrapper-content').viewer({zIndex:15001});
			}
			
		</script> 
	</content>
	<content tag="local_require">
    <script>
    	var fileUpload;
	    require(['main'], function() {
			requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination','chosen'],function($,aistFileUpload){
				fileUpload = aistFileUpload;
				fileUpload.init({
		    		caseCode : $('#caseCode').val(),
		    		partCode : "LoanlostApply",
		    		fileUploadContainer : "loanlostApplyfileUploadContainer"
		    	}); 
				$("#bank").change(function() {
					var selectValue = $("#bank").val();
					getBranchBankList(selectValue);
				});
				getBankList(finOrgCode);

				/*获取银行列表*/
				function getBankList(pcode) {
					var friend = $("#bank");
					friend.empty();
					$.ajax({
						url : ctx + "/manage/queryBankListByPcode",
						method : "post",
						dataType : "json",
						data : {
							"pcode" : pcode
						},
						success : function(data) {
							if (data.bankList != null) {
								for (var i = 0; i < data.bankList.length; i++) {
									if (data.bankCode == data.bankList[i].finOrgCode) {
										friend.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"
														+ data.bankList[i].finOrgName
														+ "</option>");
									} else {
										friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"
														+ data.bankList[i].finOrgName
														+ "</option>");
									}
								}
								
								if (pcode == null || pcode == ""
										|| pcode == undefined) {
									getBranchBankList(data.bankList[0].finOrgCode);
									$("#lastLoanBank").chosen({
										no_results_text : "未找到该选项",
										search_contains : true,
										disable_search_threshold : 10
									});
								} else {
									getBranchBankList(data.bankCode);
								}
							}
							friend.chosen({
								no_results_text : "未找到该选项",
								
								search_contains : true,
								disable_search_threshold : 10
							});
						}
					});
				}

				/*获取支行列表*/
				function getBranchBankList(pcode) {
					var friend = $("#lastLoanBank");
					friend.empty();
					$.ajax({
							url : ctx + "/manage/queryBankListByParentCode",
							method : "post",
							dataType : "json",
							data : {
								faFinOrgCode : pcode
							},
							async:false,
							success : function(data) {
								if (data != null) {
									for (var i = 0; i < data.length; i++) {
										if (finOrgCode == data[i].finOrgCode) {
											friend.append("<option value='"+data[i].finOrgCode+"' selected='selected'>"
															+ data[i].finOrgName
															+ "</option>");
										} else {
											friend.append("<option value='"+data[i].finOrgCode+"'>"
															+ data[i].finOrgName
															+ "</option>");
										}
									}
									friend.trigger('chosen:updated');
								}
							}
						});
				}
		    });
	    });
	</script>
	</content>
</body>
</html>