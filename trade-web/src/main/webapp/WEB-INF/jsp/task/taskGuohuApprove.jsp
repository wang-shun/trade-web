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
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<link href="${ctx}/css/transcss/comment/caseComment.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}

#notApproves label {
	font-weight: normal;
	margin: 0;
}

#notApproves {
	padding: 20px 0px;
}

#notApproves .col-sm-4 {
	margin: 5px 0px;
}

#notApproves input[type=checkbox], input[type=radio] {
	margin: 0px 0 0;
	line-height: normal;
}

.form_sign .sign {
	margin-top: 3px;
	margin-bottom: 3px;
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="ibox-title">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<h2>过户审批</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
	</div>


	<div class="ibox-title">
		<h5>成交信息</h5>
		<div class="panel-body ibox-content">
			<div class="row ">
				<label class="col-sm-12 control-label">案件地址：${caseDetailVO.propertyAddress }

				</label>
			</div>
			<div class="row ">
				<label class="col-sm-3 control-label">合同价： <c:if
						test="${!empty caseInfo.conPrice}">
                     		${caseInfo.conPrice/10000}  &nbsp&nbsp万元
                 		</c:if>
				</label>
				<%--   <label class="col-sm-3 control-label">成交价：
                        <c:if test="${!empty caseInfo.realPrice}">
                     		${caseInfo.realPrice/10000}&nbsp&nbsp万元
                 		</c:if>
                     </label> --%>
				<label class="col-sm-3 control-label">核定价格： <c:if
						test="${!empty caseInfo.taxPricing}">
                     		${caseInfo.taxPricing/10000}&nbsp&nbsp万元
                 		  </c:if>
				</label>
			</div>
		</div>
	</div>

	<div class="ibox-title">
		<c:if test="${!empty toMortgage}">
			<h5>案件贷款情况 : 有贷款</h5>
		</c:if>
		<c:if test="${empty toMortgage}">
			<h5>案件贷款情况 : 无贷款</h5>
		</c:if>

		<div class="panel-body ibox-content">
			<div class="row ">
				<label class="col-sm-3 control-label">签约时间：${caseDetailVO.signDate}</label>
				<label class="col-sm-3 control-label">批贷时间：${caseDetailVO.apprDate}</label>
				<label class="col-sm-3 control-label">他证送达时间：${caseDetailVO.tazhengArrDate}</label>

				<label class="col-sm-3 control-label">放款时间：${caseDetailVO.lendDate}</label>
			</div>
			<div class="row ">
				<label class="col-sm-3 control-label">贷款类型：${caseDetailVO.mortTypeName}</label>
				<label class="col-sm-3 control-label">商贷金额： <c:if
						test="${!empty toMortgage.comAmount}">
                    ${toMortgage.comAmount}&nbsp&nbsp万元
                </c:if>
				</label> <label class="col-sm-3 control-label">商贷年限：${toMortgage.comYear}</label>
				<label class="col-sm-3 control-label">商贷利率：${toMortgage.comDiscount}</label>
			</div>
			<div class="row ">
				<label class="col-sm-3 control-label">是否自办：<c:choose>
						<c:when test="${toMortgage.isDelegateYucui=='0'}">是</c:when>
						<c:when test="${toMortgage.isDelegateYucui=='1'}">否</c:when>
						<c:otherwise>
                      ${toMortgage.isDelegateYucui}
                      </c:otherwise>
					</c:choose></label> <label class="col-sm-3 control-label">公积金贷款金额： <c:if
						test="${!empty toMortgage.prfAmount}">
                    ${toMortgage.prfAmount}&nbsp&nbsp万元
                </c:if>
				</label> <label class="col-sm-3 control-label">公积金贷款年限：${toMortgage.prfYear}</label>
				<label class="col-sm-3 control-label">放款方式：${caseDetailVO.lendWay}</label>
			</div>
			<div class="row ">
				<label class="col-sm-3 control-label">主贷人：${caseDetailVO.mortBuyer}</label>
				<label class="col-sm-3 control-label">主贷人单位：${caseDetailVO.buyerWork}</label>
				<label class="col-sm-3 control-label">房贷套数：${toMortgage.houseNum}</label>
				<label class="col-sm-3 control-label">申请时间：${caseDetailVO.prfApplyDate}</label>
			</div>
			<c:choose>
				<c:when
					test="${toMortgage.isDelegateYucui=='1' && (toMortgage.mortType=='30016001' or toMortgage.mortType=='30016002')}">
					<div class="row ">
						<label class="col-sm-3 control-label">贷款银行：${caseDetailVO.parentBankName}</label>
						<label class="col-sm-3 control-label">支
							行：${caseDetailVO.bankName}</label> <label class="col-sm-3 control-label">是否为临时银行：<c:choose>
								<c:when test="${toMortgage.isTmpBank==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose></label> <label class="col-sm-3 control-label">推荐函编号：${toMortgage.recLetterNo}</label>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row ">
						<label class="col-sm-6 control-label">贷款银行：${caseDetailVO.parentBankName}</label>
						<label class="col-sm-6 control-label">支
							行：${caseDetailVO.bankName}</label>
					</div>
				</c:otherwise>
			</c:choose>

			<div class="row ">
				<label class="col-sm-3 control-label">信贷员：${toMortgage.loanerName}</label>
				<label class="col-sm-3 control-label">信贷员电话：${toMortgage.loanerPhone}</label>
				<label class="col-sm-3 control-label">评估公司：${caseDetailVO.evaName}</label>
				<label class="col-sm-3 control-label">评估费金额： <c:if
						test="${!empty caseDetailVO.evaFee}">
                    ${caseDetailVO.evaFee}&nbsp&nbsp元
                </c:if>
				</label>
			</div>
			<div class="row ">
				<label class="col-sm-3 control-label">备注：${toMortgage.remark}</label>
				<label class="col-sm-9 control-label">贷款流失类型：${caseDetailVO.loanLostType}</label>
			</div>
		</div>
	</div>

	<div class="ibox-title">
		<h5>附件信息</h5>
		<div class="">
			<div id="imgShow" class="lightBoxGallery"></div>
		</div>
	</div>
	<div class="ibox-title">
		<h5>ctm附件</h5>
		<div class="ibox-content">
			<div class="jqGrid_wrapper">
				<table id="gridTable"></table>
				<div id="gridPager"></div>
			</div>
		</div>
	</div>

	<div class="ibox-title">
		<h5>备注信息 :</h5>
		<div class="">${houseTransfer.comment}</div>
	</div>

	<div class="ibox-title">
		<h5>填写任务信息</h5>
		<div class="ibox-content">
			<form class="form-horizontal" id="guohuApproveForm">
				<%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode"
					value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode"
					value="${caseCode}">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId"
					value="${processInstanceId}">
				<%-- 设置审批类型 --%>
				<input type="hidden" id="approveType" name="approveType"
					value="${approveType }">
				<%-- <input type="hidden" id="lapPkid" name="lapPkid" value="${toApproveRecord.pkid }"> --%>
				<input type="hidden" id="operator" name="operator"
					value="${operator }">
				<div class="form-group">
					<label class="col-sm-2 control-label">审批结果</label>
					<div class="radio i-checks radio-inline">
						<label> <input type="radio" checked="checked" value="true"
							id="optionsRadios1" name="GuohuApprove"
							onClick="$('#notApproves').hide();">审批通过
						</label>
					</div>
					<div class="radio i-checks radio-inline">
						<label> <input type="radio" value="false"
							id="optionsRadios2" name="GuohuApprove"
							onClick="$('#notApproves').show();getNotApproves();">审批未通过
						</label>
					</div>
					<div class="form_sign col-sm-12 clearfix" id="notApproves"
						style="display: none">
						<c:forEach items="${notApproves}" var="notApprove">
							<div class="col-sm-6 sign">
								<input type="checkbox" value="${notApprove.code}"
									name="notApprove" class="btn btn-white"
									onClick="appendNotApprove(this.checked,'${notApprove.name}');">
								<label>${notApprove.name}</label>
							</div>
						</c:forEach>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">审批意见</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="GuohuApprove_response"
							name="GuohuApprove_response">
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- 案件备注信息 -->
	<div id="caseCommentList" class="add_form"></div>

	<div class="ibox-title">
		<h5>审批记录</h5>
		<div class="ibox-content">
			<div class="jqGrid_wrapper">
				<table id="reminder_list"></table>
				<div id="pager_list_1"></div>
			</div>
		</div>
	</div>
	<div class="ibox-title">
		<!-- <a href="#" class="btn" onclick="save()">保存</a> -->
		<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
	</div>
	</div>


	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <script
		src="${ctx}/transjs/task/loanlostApprove.js"></script> <script
		src="${ctx}/transjs/task/showAttachment.js"></script> <!-- Custom and plugin javascript -->
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<!-- 上传附件相关 --> <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> <!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> <script src="${ctx}/js/trunk/task/attachment.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script> <!-- 显示上传的附件 -->
	<script src="${ctx}/js/trunk/case/showCaseAttachment.js"></script> <script
		src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> <script
		src="${ctx}/js/trunk/comment/caseComment.js"></script> <script
		src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script>
			function appendNotApprove(isAppend, content) {
				if (isAppend) {
					var oldVal = $("#GuohuApprove_response").val();
					if (oldVal != '') {
						oldVal += '；';
					}
					$("#GuohuApprove_response").val(oldVal + content);
				}
			}
			var url = "/quickGrid/findPage";
			var ctx = "${ctx}";
			var ctmCode = "${toCaseInfo.ctmCode}";
			var r1 = false;
			var changeTaskRole = false;
			var serivceDepId = '${serivceDefId}';
			var loanReqType = "${loanReqType}";
			<shiro:hasPermission name="TRADE.CASE.DEALPRICE:SHOW">
			r1 = true;
			</shiro:hasPermission>
			<shiro:hasPermission name="TRADE.CASE.TASK:ASSIGN">
			changeTaskRole = true;
			</shiro:hasPermission>
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
			
			$("#gridTable")
					.jqGrid(
							{
								url : ctx + url,
								mtype : 'GET',
								datatype : "json",
								height : 250,
								autowidth : true,
								shrinkToFit : true,
								rowNum : 20,
								/*   rowList: [10, 20, 30], */
								colNames : [ '附件类型', '附件名称', '附件路径', '上传时间',
										'操作' ],
								colModel : [ {
									name : 'ATT_TYPE',
									index : 'ATT_TYPE',
									align : "center",
									width : 20,
									resizable : false
								}, {
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
								}, {
									name : 'READ',
									index : 'READ',
									align : "center",
									width : 20,
									resizable : false
								} ],
								multiselect : true,
								pager : "#gridPager",
								//sortname:'UPLOAD_DATE',
								//sortorder:'desc',
								viewrecords : true,
								pagebuttions : true,
								multiselect : false,
								hidegrid : false,
								recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
								pgtext : " {0} 共 {1} 页",
								gridComplete : function() {
									var ids = jQuery("#gridTable").jqGrid(
											'getDataIDs');
									for (var i = 0; i < ids.length; i++) {
										var id = ids[i];
										var rowDatas = jQuery("#gridTable")
												.jqGrid('getRowData', ids[i]); // 获取当前行

										var link = "<button  class='btn red' onclick='addAttachmentReadLog(\""
												+ ctx
												+ "\",\""
												+ ctmCode
												+ "\",\""
												+ caseCode
												+ "\",\""
												+ rowDatas['ATT_NAME']
												+ "\",\""
												+ rowDatas['ATT_PATH']
												+ "\")'>查看附件</a>";

										//var detailBtn = "<button  class='btn red' id='alertOper' onclick='openLoan(\""+ctx+"\",\""+rowDatas['pkId']+"\")' style='width:90px;'>详细</button>";

										jQuery("#gridTable").jqGrid(
												'setRowData', ids[i], {
													READ : link
												});

										var attType = rowDatas["ATT_TYPE"];
										if (!r1 && attType == '买卖居间协议') {
											$("#gridTable").jqGrid(
													"delRowData", id);
										}
									}
								},
								postData : {
									queryId : "followPicListQuery",
									argu_ctmCode : ctmCode
								}

							});

			$(function() {
				getShowAttachment();

				//jqGrid 初始化

			})

			/**提交数据*/
			function submit() {
				if (!checkAttachment()) {
					return;
				}
				save();
			}

			/**保存数据*/
			function save() {
				var jsonData = $("#guohuApproveForm").serializeArray();
				/**deleteAndModify();*/
				$.ajax({
					cache : true,
					async : false,//false同步，true异步
					type : "POST",
					url : "${ctx}/task/guohuApprove/guohuApprove",
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
						//	alert("数据已保存。");
						if (window.opener) {
							window.close();
							window.opener.callback();
						} else {
							window.location.href = "${ctx }/task/myTaskList";
						}
					},
					error : function(errors) {
						alert("数据保存出错");
					}
				});
			}
		</script> </content>
</body>
</html>
