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
<link href="${ctx}/css/style.css" rel="stylesheet">

<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">

<!-- 图片查看CSS -->
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
	
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
	<!-- 服务流程 -->
	<div class="row wrapper white-bg new-heading " id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                       过户审批
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
            <div class="clearfix">
                <h2 class="newtitle title-mark">成交信息</h2>
                <div class="text_list">
                    <ul class="textinfo">
                        <li>
                            <em>案件地址</em><span>${caseDetailVO.propertyAddress }</span>
                        </li>
                        <li>
                            <em>合同价</em>
                            <span>
                            <c:if test="${!empty caseInfo.conPrice}">
                     		${caseInfo.conPrice/10000}  &nbsp&nbsp万元
                 		    </c:if>
                 		    </span>
                        </li>
                        <li>
                            <em>核定价格</em>
                            <span class="yuanwid">
                            <c:if test="${!empty caseInfo.taxPricing}">
                     		${caseInfo.taxPricing/10000}&nbsp&nbsp万元
                 		    </c:if>
                 		    </span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="clearfix">
            	<c:if test="${!empty toMortgage}">
					<h2 class="newtitle title-mark">案件贷款情况 : 有贷款</h2>
				</c:if>
				<c:if test="${empty toMortgage}">
					<h2 class="newtitle title-mark">案件贷款情况 : 无贷款</h2>
				</c:if>
                <div class="text_list">
                    <ul class="textinfo">
                        <li>
                            <em>签约时间</em><span class="yuanwid">${caseDetailVO.signDate}</span>
                        </li>
                        <li>
                            <em>批货时间</em><span class="yuanwid">${caseDetailVO.apprDate}</span>
                        </li>
                        <li>
                            <em>他证送达时间</em><span class="yuanwid">${caseDetailVO.tazhengArrDate}</span>
                        </li>
                        <li>
                            <em>放款时间</em><span class="yuanwid">${caseDetailVO.lendDate}</span>
                        </li>
                        <li>
                            <em>贷款类型</em><span class="yuanwid">${caseDetailVO.mortTypeName}</span>
                        </li>
                        <li>
                            <em>商贷金额</em>
                            <span class="yuanwid">
                            <c:if test="${!empty toMortgage.comAmount}">
                               ${toMortgage.comAmount}&nbsp&nbsp万元
			                </c:if>
			                </span>
                        </li>
                        <li>
                            <em>商贷年限</em><span class="yuanwid">${toMortgage.comYear}</span>
                        </li>
                        <li>
                            <em>商贷利率</em><span class="yuanwid">${toMortgage.comDiscount}</span>
                        </li>
                        <li>
                            <em>是否自办</em>
                            <span class="yuanwid">
                            <c:choose>
                            <c:when test="${toMortgage.isDelegateYucui=='0'}">是</c:when>
							<c:when test="${toMortgage.isDelegateYucui=='1'}">否</c:when>
							<c:otherwise>
			                      ${toMortgage.isDelegateYucui}
	                        </c:otherwise>
							</c:choose>
                            </span>
                        </li>
                        <li>
                            <em>公积金贷款金额</em>
                            <span class="yuanwid">
                            	<c:if test="${!empty toMortgage.prfAmount}">
                    					${toMortgage.prfAmount}&nbsp&nbsp万元
                				</c:if>
                            </span>
                        </li>
                        <li>
                            <em>公积金贷款年限</em><span class="yuanwid">${toMortgage.prfYear}</span>
                        </li>
                        <li>
                            <em>放款方式</em><span class="yuanwid">${caseDetailVO.lendWay}</span>
                        </li>
                        <li>
                            <em>主贷人</em><span class="yuanwid">${caseDetailVO.mortBuyer}</span>
                        </li>

                        <li>
                            <em>房贷套数</em><span class="yuanwid">${toMortgage.houseNum}</span>
                        </li>
                        <li>
                            <em>申请时间</em><span class="yuanwid">${caseDetailVO.prfApplyDate}</span>
                        </li>
                        <li>
                            <em class="pull-left">主贷人单位</em><span class="infolong pull-left">${caseDetailVO.buyerWork}</span>
                        </li>
                        <li>
                            <em>信贷员</em><span class="yuanwid">${toMortgage.loanerName}</span>
                        </li>
                        <li>
                            <em>信贷员电话</em><span class="yuanwid">${toMortgage.loanerPhone}</span>
                        </li>
                        <li>
                            <em>评估金额</em>
                            <span class="yuanwid"> 
                            <c:if test="${!empty caseDetailVO.evaFee}">
                    			${caseDetailVO.evaFee}&nbsp&nbsp元
                			</c:if>
                			</span>
                        </li>
                        <li>
                            <em class="pull-left">评估公司</em><span class="infolong pull-left">${caseDetailVO.evaName}</span>
                        </li>
             <c:choose>
				<c:when test="${toMortgage.isDelegateYucui=='1' && (toMortgage.mortType=='30016001' or toMortgage.mortType=='30016002')}">
                        <li>
                            <em>贷款银行</em><span class="yuanwid">${caseDetailVO.parentBankName}</span>
                        </li>
                        <li>
                            <em>是否临时银行</em>
                            <span class="yuanwid">
                            	<c:choose>
								<c:when test="${toMortgage.isTmpBank==1}">是</c:when>
								<c:otherwise>否</c:otherwise>
								</c:choose>
                            </span>
                        </li>
                      
                        <li>
                            <em>推荐函编号</em><span class="yuanwid">${toMortgage.recLetterNo}</span>
                        </li>
                        <li>
                            <em class="pull-left">支行</em><span class="infolong pull-left">${caseDetailVO.bankName}</span>
                        </li>
                </c:when>
                <c:otherwise>
						<li>
                            <em>贷款银行</em><span class="yuanwid">${caseDetailVO.parentBankName}</span>
                        </li>
						<li>
                            <em class="pull-left">支行</em><span class="infolong pull-left">${caseDetailVO.bankName}</span>
                        </li>
				</c:otherwise>
			</c:choose>
                        <li>
                            <em class="pull-left">备注</em><span class="infolong pull-left">${toMortgage.remark}</span>
                        </li>
                        <li>
                            <em class="pull-left">贷款流失类型</em><span class="infolong pull-left">${caseDetailVO.loanLostType}</span>
                        </li>
                    </ul>
                </div>
            </div>
        <div class="clearfix">
            <h2 class="newtitle title-mark">附件信息</h2>
            <div class="mb20 mt20">
                <div id="imgShow" class="lightBoxGallery"></div>
            </div>
        </div>
        <div class="clearfix">
            <h2 class="newtitle title-mark">CTM附件</h2>
            <div class="jqGrid_wrapper">
				<table id="gridTable"></table>
				<div id="gridPager"></div>
			</div>
        </div>
        <div class="clearfix">
            <h2 class="newtitle title-mark">备注信息</h2>
			<div class="">${houseTransfer.comment}</div>
        </div>

        <div>
            <h2 class="newtitle title-mark">填写任务信息</h2>
            <div class="form_list">
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
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">审批结果</label>
                            <div class="controls ">
                               <label class="radio inline"> <input type="radio" checked="checked" value="true"
							id="optionsRadios1" name="GuohuApprove"
							onClick="$('#notApproves').hide();">审批通过
                                </label> <label class="radio inline"> <input type="radio" value="false"
							id="optionsRadios2" name="GuohuApprove"
							onClick="$('#notApproves').show();getNotApproves();">审批不通过
                                </label>
                            </div>
                        </div>
                        
						<div class="form_content" id="notApproves"
						style="display: none">
						<c:forEach items="${notApproves}" var="notApprove">
							<div class="col-sm-6">
								<input type="checkbox" value="${notApprove.code}"
									name="notApprove" class="btn btn-white"
									onClick="appendNotApprove(this.checked,'${notApprove.name}');">
								<label>${notApprove.name}</label>
							</div>
						</c:forEach>
						
						<c:if test="${not empty users }">	
						<div class="col-sm-6">&nbsp;</div>
						<div class="col-sm-6">&nbsp;</div>
                        <div class="col-sm-6">&nbsp;</div>
						<div class="col-sm-6">分配人员：</div>
						<div class="col-sm-6">&nbsp;</div>
						<c:forEach items="${users}" var="user" varStatus="var">
							<div class="col-sm-6">
								<input type="checkbox" value="${user.username }"
									name="members" class="btn btn-white" >                                                                                                                                                                                                                                                                               
								<label>${user.realName}(${var.index eq 0 ? '前台':'后台' })</label>
							</div>
						</c:forEach>
						</c:if>
						
					</div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">审批意见</label>
                            <input type="text" class="input_type optionwid" id="GuohuApprove_response"
							name="GuohuApprove_response">
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
         <!-- 相关信息 -->
        <!-- 案件备注信息 -->
		<div class="view-content" id="caseCommentList" id="aboutInfo"> </div>

         <div class="mt20">
             <h2 class="newtitle title-mark">审批记录</h2>
             <div class="jqGrid_wrapper">
				<table id="reminder_list"></table>
				<div id="pager_list_1"></div>
			</div>
         </div>
         <div class="form-btn">
                <div class="text-center">
                     <button class="btn btn-success btn-space" onclick="submit()">提交</button>
                     <!-- <button class="btn btn-grey btn-space">关闭</button> -->
                </div>
         </div>
     </div>
       
	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script> 
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<!-- 上传附件相关 --> <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
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
	<script src="${ctx}/js/trunk/task/attachment.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script> 
	<!-- 图片查看JS -->
	<script src="${ctx}/js/trunk/case/showCaseAttachmentGuohu.js"></script>
	
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script> 
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
	<script src="${ctx}/js/viewer/viewer.min.js"></script>
	<!-- 改版引入的新的js文件 -->
	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	<script>
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
			var ctmCode = "${caseBaseVO.toCaseInfo.ctmCode}";
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

			});

			/**提交数据*/
			function submit() {
				if (!checkAttachment()) {
					return;
				}

			if($("input[name='GuohuApprove']:checked").val() == 'false' && '${users}' != ''){
				var flag = false;
				$("input[name='members']").each(function(i,e){
					if(e.checked){
						flag = true;
					}
				});
				if(!flag){
					alert("请至少选择一个处理人！");
					return false;
				}
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
			
			//渲染图片 
			function renderImg(){
				$('.wrapper-content').viewer('destroy');
				$('.wrapper-content').viewer();
			}
		</script> </content>
</body>
</html>
