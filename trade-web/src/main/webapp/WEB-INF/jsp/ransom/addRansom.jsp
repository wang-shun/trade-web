<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewpoart" content="width=device-width, initial-scale=1.0">

<title>新增赎楼单</title>
<!-- 上传相关 -->
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"
	rel="stylesheet">
<!-- 展示相关 -->
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/static/css/bootstrap.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- index_css  -->
<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/input.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/table.css' />"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<link href="<c:url value='/static/css/plugins/stickup/stickup.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/aist-steps/steps.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/table.css' />" />
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/input.css' />" />
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/see.css' />" />
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/spv.css' />" />
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/js/viewer/viewer.min.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />"
	rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />"
	rel="stylesheet">
<!-- stickUp fixed css -->
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "SpvApplyApprove";
	var source = "${source}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
	var accTypeOptions = '';
	var accSum = 1;
</script>
<style>
.borderClass {
	border: 1px solid red !important;
	resize: none;
}

.borderClass:focus {
	border: 1px solid red !important;
	resize: none;
}

table thead tr {
	border-bottom: 1px solid;
	text-align: center;
	line-height: 30px;
}

table tbody select, input {
	margin: 10px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	
	<div id="wrapper">

		<!-- main Start -->
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<div class="ibox-content" >
					<h5>关联案件
						<button type="button" id="link_btn" class="btn btn-success btn-blue" data-toggle="modal" data-target="#myModal" onClick="showPop();">关联案件</button>
					</h5>
				</div>
				<div class="modal inmodal" id="myModal" tabindex="-1"role="dialog" aria-hidden="true">
					<div class="modal-dialog" style="width: 1070px;">
						<div
							class="modal-content animated fadeIn apply_box ibox-content">
							<form action="" class="form_list clearfix">
								<div class="modal_title">监管合约关联案件</div>
								<div class="line">
									<div class="form_content">
										<label class="control-label mr10"> 案件编码 </label> 
										<input class="teamcode input_type" value="" placeholder="请输入" id="caseCodet" name="caseCodet">
									</div>
									<div class="form_content">
										<label class="control-label sign_left"> 产证地址 </label> 
										<input class="input_type" value="" placeholder="请输入" style="width: 435px;" id="propertyAddr" name="propertyAddr">
									</div>
								</div>
								<div class="line">
									<div class="form_content">
										<label class="control-label mr10"> 上家姓名 </label> 
										<input class="teamcode input_type" value="" placeholder="请输入" id="sellerName" name="sellerName">
									</div>
									<div class="form_content space">
										<div class="add_btn">
											<button type="button" class="btn btn-success" id="searchButton">
												<i class="icon iconfont"></i> 查询
											</button>
											<button type="button" class="btn btn-success"id="addNewCase">新增案件</button>
										</div>
									</div>
								</div>
							</form>
							<button type="button" class="close close_blue"data-dismiss="modal">
								<i class="iconfont icon_rong"> &#xe60a; </i>
							</button>
							<div class="eloanApply-table">
								<table class= "table table_blue table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>案件编号</th>
											<th>产证地址</th>
											<th>工作人员</th>
											<th>上家</th>
											<th>下家</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="case-link"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="ibox-content" id="case_info">
					<div class="case_content" >
						<div class="case_row">
							<div class="case_lump">
								<p>
									<em>案件编号</em><span class="span_one" id="content_caseCode">${ransomLinkVo.caseCode}</span>
								</p>
							</div>
							<div class="case_lump">
								<p>
									<em>产证地址</em><span class="span_two" id="content_propertyAddr">${ransomLinkVo.propertyAddr}</span>
								</p>
							</div>
						</div>
						<div class="case_row">
							<div class="case_lump">
								<p>
									<em>交易顾问</em><span class="span_one" id="content_processorId">${ransomLinkVo.warrent}</span>
								</p>
							</div>
							<div class="case_lump">
								<p>
									<em>上家姓名</em><span class="span_two" id="content_seller">${ransomLinkVo.seller}</span>
								</p>
							</div>
						</div>
						<div class="case_row">
							<div class="case_lump">
								<p>
									<em>经纪人</em><span class="span_one" id="content_agentName">${ransomLinkVo.agentName}</span>
								</p>
							</div>
							<div class="case_lump">
								<p>
									<em>下家姓名</em><span class="span_two" id="content_buyer">${ransomLinkVo.buyer}</span>
								</p>
							</div>
						</div>
					</div>
				</div>
				</div>
				<!-- value待修改  此处只是copy   saveSpvCase.jsp  样式 -->
				<div class="ibox-content" id="base_info">
					<form class="form-inline" id="guestInfo">
						<input type="hidden" id="handle" name="handle" value="${handle }">
						<div class="title">客户信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								
								<label for="" class="lable-one"><i style="color: red;">*</i>主贷人姓名</label>
								<input type="hidden" id="sellName" value="${ransomLinkVo.seller }"/>
								<input type="hidden" id="buyName" value="${ransomLinkVo.buyer }"/>
								<select class="form-control input-one" id="custName" onchange="choseRoleChange()">
									<option value="">请选择</option>
									<option value="${ransomLinkVo.seller }">${ransomLinkVo.seller }</option>
									<option value="${ransomLinkVo.buyer }">${ransomLinkVo.buyer }</option>
								</select>	
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>手机号码</label> 
								<input type="hidden" value="${ransomLinkVo.sellPhone }" id="sellTel" />	
								<input type="hidden" value="${ransomLinkVo.buyPhone }" id="buyTel" />	
								<input type="text" id="phone" class="form-control input-one" disabled value="" placeholder="手机号码"/>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>受理时间</label> 
								<input id="date-picker2" name="toSpv.signTime" class="form-control input-one date-picker"
									style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime }" pattern="yyyy-MM-dd"/>" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>计划申请时间</label> 
								<input id="date-picker1" name="toSpv.signTime" class="form-control input-one date-picker"
									style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime }" pattern="yyyy-MM-dd"/>"placeholder="">
							</div>
							<div class="hr-line-dashed"></div>

							<table>
								<thead>
									<tr>
										<td>尾款机构</td><td>尾款类型</td><td>抵押类型</td><td>贷款金额</td><td>剩余金额</td>
									</tr>
								</thead>
								<tbody id="tableInfo">
									<tr id="trId0">
										<td>
											<aist:dict clazz="select_control yuanwid" id="finalOrg" name="finalOrg" display="select" 
												defaultvalue="${toMortgage.mortType }" dictType="FINAL_ORG" />
										</td>
										<td>
											<aist:dict id="finalType" name="finalType" clazz=" select_control yuanwid " display="select"
												dictType="RETAINAGE_TYPE" defaultvalue="${toMortgage.mortType }" />
										</td>
										<td>
											<select id="diyaType" name="diyaType" class=" select_control yuanwid ">
												<option>请选择</option><option value="710150001">一抵</option></select></td>
											</select>
										</td>
										<td>
											<input name="loanMoney" value="" type="text" class="form-control input-one" placeholder="贷款金额(单位：万元)" id="loanMoney">
										</td>
										<td>
											<input name="restMoney" value="0.00" onchange="changeRestMoney()" type="text" class="form-control input-one" placeholder="剩余金额(单位：万元)" id="restMoney">
										</td>
									</tr>
								
								<input type="hidden" id="addInput" />
							</table>
							<div id="addMoneyLine" class="input-group" style="">
								<a class="" href="javascript:addOrg();"><font>添加机构</font></a>
							</div>

							<div class="hr-line-dashed"></div>

							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>借款总金额</label> 
								<input name="" value="" type="text" readOnly="readonly" class="form-control input-one" placeholder="" id="allLoanMoney" > 
								<span>万</span>
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content">
					<div class="view-content" id="caseCommentList"> </div>
				</div>

				<div class="ibox-content" id="spvfour_info">
					<div class="form-btn">
						<div>
							<a id="submitBtn" class="btn btn-success">提交</a>
							<a onclick="rescCallback()" class="btn btn-default">关闭</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- main End -->
	</div>
	<!-- Mainly scripts -->
	<content tag="local_script"> 
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script>
	<script src="<c:url value='/static/js/morris/morris.js' />"></script> 
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> 
	<!-- index_js -->
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<!-- 上传附件相关 --> 
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script> 
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
	<script src="<c:url value='/js/ransom/addRansom.js' />"></script>
	<script src="<c:url value='/js/trunk/task/loanerProcessList.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script>
	<!-- 上传附件 结束 --> <!-- 附件保存修改相关 --> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<!-- stickup plugin --> 
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script>
	<%-- <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> --%>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/static/tbsp/js/userorg/userOrgSelect.js' />" type="text/javascript"></script> 
	<!-- 引入弹出框js文件 --> 
	<script src="<c:url value='/js/common/xcConfirm.js' />"></script> 
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script> 
	
	
	
	<script id="template_ransomCaseLink" type="text/html">
        {{each rows as item index}}
    		<tr id="tr-num{{index}}">
        		<td>
					<input type="hidden" value="{{item.pkid}}" id="casePkid{{index}}"/>
					<a href="${ctx}/case/caseDetail?caseId={{item.pkid}}" id="td{{index}}" class="case-num" target="_blank">{{item.caseCode}}</a>
				</td>
        		<td>
					<p class="big">{{item.propertyAddr}}</p>
					<p class="big">{{item.agentName}} / {{item.grpName}}</p>
				</td>
        		<td>交易顾问:{{item.realName}}</td>
        		<td>{{item.seller}}</td>
        		<td>{{item.buyer}}</td>
        		<td class="text-left"><button type="button" onclick = "checkLink('{{item.caseCode}}')" class="btn btn-success linkCase" name="linkCase">关联 </button></td>
    		</tr>
    	{{/each}}		
	</script>
		 <script>

			/*******************************************************控件相关*********************************************************************/
			// 日期控件
			$('#date-picker2').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			});
			$('#date-picker1').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			});

			//跟进信息
			//$("#caseCommentList").caseCommentGrid({
			//	caseCode : $('#caseCode').val()
				//srvCode : srvCode
			//});

		</script> </content>

</body>

</html>