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
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"	rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"	rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css"	rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />

<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
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
						<h4>${accesoryList[0].accessoryName }</h4>
						<c:forEach var="accesory" items="${accesoryList}"	varStatus="status">
							<div class="" id="fileupload_div_pic">							
								<form id="fileupload"	action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"	method="POST" enctype="multipart/form-data">
									<noscript>
										<input type="hidden" name="redirect" value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
										<input type="hidden" id="preFileCode" name="preFileCode" value="${accesory.accessoryCode }">
									</noscript>
									<c:if test="${status.index != 0}">
										<h5 align="left"><br>${accesory.accessoryName }</h5>
									</c:if>
									
									<div class="row-fluid fileupload-buttonbar">
										<div class="" style="height: auto">
											<div role="presentation" class="table table-striped "	style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
												<div id="picContainer${accesory.pkid }" class="files"	data-toggle="modal-gallery" data-target="#modal-gallery"></div>
												<!-- 用于'客户确认书'验证 -->
												<input type="hidden" id="fileFlagCode${accesory.pkid }"   value="${accesory.accessoryCode }">
												
												<span class=" fileinput-button "	style="margin-left: 10px !important; width: 80px;">
													<div id="chandiaotuBtn" class="" style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
														<i class="fa fa-plus"></i>
													</div> 													
													<input id="picFileupload${accesory.pkid }" type="file"	name="files[]" multiple 	data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"	data-sequential-uploads="true">
												</span>
											</div>
										</div>
									</div>									
								</form>
							</div>

							<div class="row-fluid">
								<div class="">
									<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
									<!--图片缩图  -->
							        <div class="preview"><span class="fade"></span></div>
									<!--  错误信息 -->
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									<!-- 单个对应的按钮  -->
							            <div class="start span1" style="display: none">
										{% if (!o.options.autoUpload) { %}
							                <button class="btn">
							                    <i class="icon-upload icon-white"></i>
							                    <span>上传</span>
							                </button>
							            {% } %}
										</div>
							        {% } else { %}
							            <div class="span1" colspan="2"></div>
							        {% } %}
							        <div class="cancel" style="margin-top:-172px;margin-left:85%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
						<script id="templateDownload${accesory.pkid }"	type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.id) { %}
							                <img src="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/getfile?fileId={%=file.id%}" style="width:80px;height:80px;">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId={%=file.id%}" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
								</div>
							</div>
						</c:forEach>
						</div>
						<div class="row-fluid" style="display: none;">
							<div class="span4">
								<div class="control-group">
									<a class="btn blue start" id="startUpload"
										style="height: 30px; width: 50px"> <i
										class="icon-upload icon-white"></i> <span>上传</span>
									</a>
								</div>
							</div>
						</div>
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
	<script	 src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
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
	<script	src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	<script src="${ctx}/js/stickUp.js"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="${ctx}/js/trunk/task/attachment3.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script> 
	<script	src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script	src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
	<script	src="${ctx}/js/trunk/comment/caseComment.js"></script> 
	<script	src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
	<script	src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
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

				$("#bank").change(function() {
					var selectValue = $("#bank").val();
					getBranchBankList(selectValue)
				});

				getBankList(finOrgCode);
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
					save(true);
				}
			}

			/**保存数据*/
			function save(b) {
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
							window.wxc.success("保存成功。");
							window.close();
							window.opener.callback();
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

			/*获取银行列表*/
			function getBankList(pcode) {
				var friend = $("#bank");
				friend.empty();
				$
						.ajax({
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
									friend.chosen({
										no_results_text : "未找到该选项",
										
										search_contains : true,
										disable_search_threshold : 10
									});
									if (pcode == null || pcode == ""
											|| pcode == undefined) {
										getBranchBankList(data.bankList[0].finOrgCode);
									} else {
										getBranchBankList(data.bankCode);
									}
								}
							}
						});
			}

			/*获取支行列表*/
			function getBranchBankList(pcode) {
				var friend = $("#lastLoanBank");
				if (document.getElementById("lastLoanBank")["options"].length > 0) {
					friend.chosen('destroy');
				}
				friend.empty();
				$
						.ajax({
							url : ctx + "/manage/queryBankListByParentCode",
							method : "post",
							dataType : "json",
							data : {
								faFinOrgCode : pcode
							},
							success : function(data) {
								if (data != null) {
									for (var i = 0; i < data.length; i++) {
										if (finOrgCode == data[i].finOrgCode) {
											friend
													.append("<option value='"+data[i].finOrgCode+"' selected='selected'>"
															+ data[i].finOrgName
															+ "</option>");
										} else {
											friend
													.append("<option value='"+data[i].finOrgCode+"'>"
															+ data[i].finOrgName
															+ "</option>");
										}
									}
									friend.chosen({
										no_results_text : "未找到该选项",
										search_contains : true,
										disable_search_threshold : 10
									});
								}
							}
						});
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
					$.each(idList, function(index, value){//遍历所传附件的fileId
						var length = $("#picContainer"+value).find("img").length;
						if(length == 0) {
							window.wxc.alert("请上传附件信息！");
							checkAtt = false;
							return false;
						} else {
							checkAtt = true;
						}
					});
				}else{
					$.each(idList, function(index, value){
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
						
					});
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
</body>
</html>