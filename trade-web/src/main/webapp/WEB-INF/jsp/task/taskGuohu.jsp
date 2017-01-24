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

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />

<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
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
                        过户
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
            <div class="">
                <h2 class="newtitle title-mark">完成提醒</h2>
                <div class="jqGrid_wrapper">
                    <table id="remind_list"></table>
                    <div id="pager_list_1"></div>
                    <button type="button" class="btn btn-icon btn-grey-border mt20" id="sendSMS">
                        <i class="iconfont icon">&#xe62a;</i> 发送短信提醒
                    </button>
                </div>
            </div>
        <div>
            <h2 class="newtitle title-mark">填写任务信息</h2>
            <div class="form_list">
	            <form method="get" class="form-horizontal" id="houseTransferForm">
	            <%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode"
					value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode"
					value="${caseCode}">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId"
					name="processInstanceId" value="${processInstanceId}">
				<%-- 原有数据对应id --%>
				<input type="hidden" id="pkid" name="pkid"
					value="${houseTransfer.pkid}">
				<%-- 设置审批类型 --%>
				<input type="hidden" id="approveType" name="approveType"
					value="${approveType }"> <input type="hidden"
					id="operator" name="operator" value="${operator }">
	                <div class="marinfo">
	                    <div class="line">
	                        <div class="form_content">
	                            <label class="control-label sign_left_small select_style mend_select">
	                               	 实际过户时间<font color=" red" class="mr5" >*</font>
	                            </label>
	                            <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                            	<input type="text" class="input_type yuanwid datatime" id="realHtTime"
										name="realHtTime"
										value="<fmt:formatDate  value='${houseTransfer.realHtTime}' type='both' pattern='yyyy-MM-dd'/>"
										onfocus="this.blur()">
	                            </div>
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">房产税<font color=" red" class="mr5" >*</font></label> 
								<input type="text" class=" input_type yuanwid" id="houseHodingTax"
								name="houseHodingTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.houseHodingTax}' type='number' pattern='#0.00' />">
	                            <span class="date_icon">万元</span>
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">个人所得税<font color=" red" class="mr5" >*</font></label> 
	                            <input type="text" class=" input_type yuanwid" id="personalIncomeTax"
									name="personalIncomeTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.personalIncomeTax}' type='number' pattern='#0.00' />">
	                           <span class="date_icon">万元</span>
	                        </div>
	                    </div>
	                    <div class="line">
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">上家营业税<font color=" red" class="mr5" >*</font></label> 
	                            <input type="text" class=" input_type yuanwid" id="businessTax"
									name="businessTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ houseTransfer.businessTax}' type='number' pattern='#0.00' />">
	                           <span class="date_icon">万元</span>
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">下家契税<font color=" red" class="mr5" >*</font></label> 
	                            <input type="text" class=" input_type yuanwid" id="contractTax"
								name="contractTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.contractTax}' type='number' pattern='#0.00' />">
	                           <span class="date_icon">万元</span>
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">土地增值税<font color=" red" class="mr5" >*</font></label> 
	                            <input type="text" class=" input_type yuanwid" id="landIncrementTax"
								name="landIncrementTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.landIncrementTax}' type='number' pattern='#0.00' />">
	                           <span class="date_icon">万元</span>
	                        </div>
	                    </div>
	                    	                     <div class="line">
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">是否刷卡<font color=" red" class="mr5" >*</font></label> 
	                           <select  class="select_control yuanwid" name="useCardPay" id="useCardPay" onchange="showcardPayAmount()">
	                           <option value="0" ${houseTransfer.useCardPay eq '0'?'selected="selected"':'' }" >否</option>
	                           <option value="1" ${houseTransfer.useCardPay eq '1'?'selected="selected"':'' }">是（佣金卡/税费卡）</option>
	                           </select>
	                        </div>
	                        <div class="form_content" id="showcardPayAmount" style="display:${houseTransfer.useCardPay eq '1'?'block':'none' }">
	                            <label class="control-label sign_left_small">刷卡金额<font color=" red" class="mr5" >*</font></label> 
	                            <input type="text" class=" input_type yuanwid" id="cardPayAmount"
								name=cardPayAmount onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.cardPayAmount}' type='number' pattern='#0.00' />">
	                           <span class="date_icon">元</span>
	                        </div>
	                    </div>
	                    <c:if test="${toMortgage != null }">
	                    <div class="line">
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">贷款类型</label>
	                            <aist:dict id="mortType" name="mortType"
									clazz=" select_control yuanwid " display="select" dictType="30016"
									defaultvalue="${toMortgage.mortType }" />
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">贷款总额</label> 
	                            <input type="text" name="mortTotalAmount"
									value="${toMortgage.mortTotalAmount }" id="mortTotalAmount"
									class=" input_type yuanwid" onkeyup="checknum(this)" placeholder="万元">
	                           <span class="date_icon">万元</span>
	                        </div>
	                    </div>
	                    <div class="line">
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">商贷金额</label> 
	                            <input type="text" name="comAmount" id="comAmount"
									value="${toMortgage.comAmount }" class=" input_type yuanwid"
									onkeyup="checknum(this)" placeholder="万元">
	                           <span class="date_icon">万元</span>
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">商贷年限</label> 
	                            <input type="text" name="comYear" id="comYear"
									value="${toMortgage.comYear }" class=" input_type yuanwid"
									onkeyup="checknum(this)">
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">商贷利率折扣</label> 
	                            <input type="text" name="comDiscount" id="comDiscount"
									value="${toMortgage.comDiscount }" placeholder="0.50~1.50之间"
									class=" input_type yuanwid" onkeyup="autoCompleteComDiscount(this)">
	                        </div>
	
	                    </div>
	                    <div class="line">
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">公积金贷款金额</label> 
	                            <input type="text" name="prfAmount" id="prfAmount"
									value="${toMortgage.prfAmount }" class=" input_type yuanwid"
									onkeyup="checknum(this)" placeholder="万元">
	                           <span class="date_icon">万元</span>
	                        </div>
	                        <div class="form_content">
	                            <label class="control-label sign_left_small">公积金贷款年限</label> 
	                            <input type="text" name="prfYear" id="prfYear"
									value="${toMortgage.prfYear }" class=" input_type yuanwid"
									onkeyup="checknum(this)">
	                           <span class="date_icon">万元</span>
	                        </div>
	                    </div>
	                    </c:if>
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
					<div class="ibox-content" style="height: 410px; overflow-y: scroll;">
						<div class="table-box" id="guohufileUploadContainer"></div>
					</div>
				</c:when>
				<c:otherwise>
					<h5>
						上传备件<br>无需上传备件
					</h5>
				</c:otherwise>
			</c:choose>
        </div>
        
	        <div class="">
	            <h2 class="newtitle title-mark">审批记录</h2>
	            <div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>
				</div>
	        </div>
	
	        <div class="form-btn">
	            <div class="text-center">
	                <a href="#" class="btn btn-success btn-space" onclick="save(false)">保存</a>
	                 <a href="#" class="btn btn-success btn-space" onclick="submit()" readOnlydata="1">提交</a>
	            </div>
	        </div>
        
        </div>
	</div>

	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<!-- 上传附件相关 --> 
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	<script src="${ctx}/js/stickUp.js"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="${ctx}/js/trunk/task/attachment3.js?v=1.1.0"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script> 
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<script src="${ctx}/transjs/sms/sms.js"></script> 
	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
	<!-- 审批记录 -->
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script> 
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/viewer/viewer.min.js"></script>	
		
	<!-- 改版引入的新的js文件 -->
	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
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
        function showcardPayAmount(){
        	var  isCardPay=$("#useCardPay").val();
        	if(isCardPay==1){
               $("#showcardPayAmount").show();
        	}else{
        		$("#showcardPayAmount").hide();
        	}
        	
        }
		$(document).ready(
				function() {
					var isDelegateYucui = '${toMortgage.isDelegateYucui}';
					var initMortType = '${toMortgage.mortType}';
					if (isDelegateYucui == '1') {
						if ('30016003' == initMortType) {
							$("select[name='mortType']").prop('disabled',
									true);
						} else {
							$("select[name='mortType']").each(
									function() {
										$(this).find(
												"option[value='30016003']")
												.remove();
									});
						}
					}

					if ('caseDetails' == source) {
						readOnlyForm();
					}

					$("#sendSMS").click(
							function() {
								var t = '';
								var s = '/';
								$("#remind_list").find(
										"input:checkbox:checked").closest(
										'td').next().each(function() {
									t += ($(this).text() + s);
								});
								if (t != '') {
									t = t.substring(0, t.length - 1);
								}
								$("#smsPlatFrom").smsPlatFrom({
									ctx : '${ctx}',
									caseCode : $('#caseCode').val(),
									serviceItem : t
								});
							});

					$("#remind_list").jqGrid({
						url : "${ctx}/quickGrid/findPage",
						datatype : "json",
						height : 210,
						multiselect : true,
						autowidth : true,
						shrinkToFit : true,
						// rowNum:8,
						viewrecords : true,
						colNames : [ '提醒事项', '备注' ],
						colModel : [ {
							name : 'REMIND_ITEM',
							index : 'REMIND_ITEM',
							width : 500
						}, {
							name : 'COMMENT',
							index : 'COMMENT',
							width : 500
						}

						],
						// pager : "#pager_list_1",
						viewrecords : false,
						pagebuttions : false,
						hidegrid : false,
						// recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						// pgtext : " {0} 共 {1} 页",
						postData : {
							queryId : "queryToReminderList",
							search_partCode : taskitem
						},

					});
					/**日期组件*/
					$('#data_1 .input-group.date').datepicker({
						todayBtn : "linked",
						keyboardNavigation : false,
						forceParse : false,
						calendarWeeks : false,
						autoclose : true
					});

					//GuoHuApproveList.init('${ctx}','/quickGrid/findPage','approve_list','approve_pager');
					var ctx = "${ctx}";
					var taskitem = "${taskitem}";
					var attachmentCode = "LoanlostApply";
					var caseCode = "${caseCode}";
					var processInstanceId = "${processInstanceId}";
					var approveType = "${approveType }";
					$("#reminder_list").jqGrid({
						//data : reminderdata,
						url : "${ctx}/quickGrid/findPage",
						datatype : "json",
						height : 120,
						width : 1059,
						shrinkToFit : true,
						rowNum : 4,
						sortname : 'OPERATOR_TIME',
						viewrecords : true,
						sortorder : "desc",
						viewrecords : true,
						colNames : [ '操作时间', '操作人', '环节编码', '内容' ],
						colModel : [ {
							name : 'OPERATOR_TIME',
							index : 'OPERATOR_TIME',
							width : '15%'
						}, {
							name : 'OPERATOR',
							index : 'OPERATOR',
							width : '15%'
						}, {
							name : 'PART_CODE',
							index : 'PART_CODE',
							width : '20%'
						}, {
							name : 'CONTENT',
							index : 'CONTENT',
							width : '50%'
						}

						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",
						postData : {
							queryId : "queryLoanlostApproveList",
							search_caseCode : caseCode,
							search_approveType : approveType,
							search_processInstanceId : processInstanceId
						}
					});

					$("#caseCommentList").caseCommentGrid({
						caseCode : caseCode,
						srvCode : taskitem
					});
				});

		/*贷款折扣自动补全*/
		function autoCompleteComDiscount(obj) {

			obj.value = obj.value.replace(/[^\d.]/g, ""); //清除“数字”和“.”以外的字符  
			obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字而不是. 
			obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.   
			obj.value = obj.value.replace(".", "$#$").replace(/\./g, "")
					.replace("$#$", ".");

			var inputVal = obj.value;
			if (inputVal >= 0.5 && inputVal <= 1.5) {
				var reg = /^[01]{1}\.{1}\d{3,}$/;
				if (reg.test(inputVal)) {
					obj.value = inputVal.substring(0, 4);
				}
			}
		}

		/**提交数据*/
		function submit() {
			if (checkAttachments()) {
				save(true);
			}
		}
		function checkAttachments() {
			$.each($("#guohufileUploadContainer ul"), function(index, value){
				
				var length = $(this).find("li").length;
				if(length == 0) {
					alert("请上传备件！");
					checkAtt = false;
					return false;
				} else {
					checkAtt = true;
				}
			});
			return checkAtt;
		}

		/**保存数据*/
		function save(b) {
			if(b){
				var caseCode = $("#caseCode").val();
				if(caseCode != "" && caseCode != null  && caseCode != undefined ){

					$.ajax({					
						url: ctx+"/caseMerge/mergeSearch",
						method:"post",
						dataType:"json",
						data:{"caseCode" : caseCode},		
						success: function(data) {	
							//alert("Result=====" +JSON.stringify(data));
							console.log("Result=====" +JSON.stringify(data));
							if(data != null ){
								if(data.success){
									var ctmCode  = data.content;
									var caseOrigin  = data.message;													
									if(caseOrigin != null && caseOrigin != "" && caseOrigin == "INPUT"){
											if(ctmCode != null && ctmCode != "" && ctmCode != undefined){
												goProcess(b);
											}else{											
												window.wxc.error("自建案件必须完成案件合流才能提交过户申请");
												return;
											}
									}else{
										//非自录案件走正常流程
										goProcess(b);
									}							
								}
							}
						},
						error: function(errors) {
							window.wxc.error("获取案件合流信息出错！");   //弹出失败提示框
						}
					}); 				
				}
			}else{
				goProcess(false);
			}			
		}
		
		function  goProcess(b){	
			if (!checkForm()) {
				return;
			}
/* 			if (!checkForm() || !deleteAndModify()) {				
				return;
			} */
			
			deleteAndModify();
			var jsonData = $("#houseTransferForm").serializeArray();

			var url = "${ctx}/task/ToHouseTransfer/saveToHouseTransfer";
			if (b) {
				url = "${ctx}/task/ToHouseTransfer/submitToHouseTransfer";
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

					$.unblockUI();
					if (b) {
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
					}

					if (status == 'timeout') {//超时,status还有success,error等值的情况
						Modal.alert({
							msg : "抱歉，系统处理超时。"
						});
						$(".btn-primary").one("click", function() {
							parent.$.fancybox.close();
						});
					}
				},
				success : function(data) {						
					if (b) {
						caseTaskCheck();						
						if (null != data.message) {
							window.wxc.alert(data.message);
						}
						//window.location.href = "${ctx }/task/myTaskList";
					} else {
						window.wxc.success("保存成功。");
						if (window.opener != null) {
							window.close();
							window.opener.callback();
						}

					}
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		/*double 验证*/
		function checkNum(obj) {
			//先把非数字的都替换掉，除了数字和.
			obj.value = obj.value.replace(/[^\d.]/g, "");
			//必须保证第一个为数字而不是.
			obj.value = obj.value.replace(/^\./g, "");
			//保证只有出现一个.而没有多个.
			obj.value = obj.value.replace(/\.{2,}/g, ".");
			//保证.只出现一次，而不能出现两次以上
			obj.value = obj.value.replace(".", "$#$").replace(/\./g, "")
					.replace("$#$", ".");
		}

		//验证控件checkUI();
		function checkForm() {
			if ($('input[name=realHtTime]').val() == '') {
				window.wxc.alert("实际过户时间为必填项!");
				$('input[name=realHtTime]').focus();
				return false;
			}
			if ($('input[name=houseHodingTax]').val() == '') {
				window.wxc.alert("房产税为必填项!");
				$('input[name=houseHodingTax]').focus();
				return false;
			}
			/* if($('input[name=commet]').val()=='') {
			    alert("备注为必填项!");
			    $('input[name=commet]').focus();
			    return false;
			} */
			if ($('input[name=personalIncomeTax]').val() == '') {
				window.wxc.alert("个人所得税为必填项!");
				$('input[name=personalIncomeTax]').focus();
				return false;
			}
			if ($('input[name=businessTax]').val() == '') {
				window.wxc.alert("上家营业税为必填项!");
				$('input[name=businessTax]').focus();
				return false;
			}
			if ($('input[name=contractTax]').val() == '') {
				window.wxc.alert("下家契税为必填项!");
				$('input[name=contractTax]').focus();
				return false;
			}
			if ($('input[name=landIncrementTax]').val() == '') {
				window.wxc.alert("土地增值税为必填项!");
				$('input[name=landIncrementTax]').focus();
				return false;
			}
			if ($('select[name=useCardPay]').val() ==1&& $('input[name=cardPayAmount]').val() == '') {
				window.wxc.alert("刷卡总金额为必填项!");
				$('input[name=cardPayAmount]').focus();
				return false;
			}
			var _mortType = $('#mortType').find(':selected').val();
			var _comDiscount = $('input[name=comDiscount]').val();
			if ((_mortType == '30016001' && _comDiscount == '')
					|| (_mortType == '30016002' && _comDiscount == '')) {
				window.wxc.alert('纯商贷和组合贷款必须填写商贷部分利率折扣, 不能为空');
				$('input[name=comDiscount]').focus();
				return false;
			}

			if ((_mortType == '30016001' && _comDiscount != '')
					|| (_mortType == '30016002' && _comDiscount != '')) {
				if (isNaN(_comDiscount)) {
					window.wxc.alert("请输入0.50~1.50之间的合法数字,小数位不超过两位");
					$('input[name=comDiscount]').focus();
					return false;
				} else if (_comDiscount > 1.5 || _comDiscount <= 0.5) {
					window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
					$('input[name=comDiscount]').focus();
					return false;
				} else if (_comDiscount<=1.5 || _comDiscount>= 0.5) {
					var reg = /^[01]{1}\.{1}\d{3,}$/;
					if (reg.test(_comDiscount)) {
						window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
						$('input[name=comDiscount]').focus();
						return false;
					}
				}
			}

			return true;
		}
				
		//渲染图片 
		function renderImg(){		
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer({zIndex:15001});
		}
	</script> </content>
	<content tag="local_require">
    <script>
	    require(['main'], function() {
	    	requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
			    aistFileUpload.init({
		    		caseCode : $('#caseCode').val(),
		    		partCode : "Guohu",
		    		fileUploadContainer : "guohufileUploadContainer"
		    	}); 
		    });
	    });
	</script>
	</content>
	
</body>


</html>