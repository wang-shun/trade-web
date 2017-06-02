<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />

<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
<style type="text/css">

</style>
<script language="javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>

	<%--环节编码 --%>
	<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
	<!-- 交易单编号 -->
	<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
	<!-- 流程引擎需要字段 -->
	<input type="hidden" id="taskId" name="taskId" value="${taskId }"> 
	<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
	<input type="hidden" id="eva_code" name="eva_code" value="${toEguPricing.evaCode }">
	<div class="modal inmodal in" id="modal-form-report" tabindex="-1" role="dialog" aria-hidden="false" >
               <div class="modal-dialog " style="width: 1000px; height: 800px">
					<div class="modal-content">
					<div class="modal-body" style="height: 700px">
					<div class="col-lg-12">
					<div class="ibox ">
						<div class="ibox-title">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h5>评估报告发起</h5>
						</div>
						<form id="reportForm" >
						<div class="ibox-content">
							<div class="form-group">
								<label class="col-sm-2 control-label">选择评估公司<span class="star">*</span>：</label>
								<div class="col-sm-10">
									<select class="form-control m-b" name="finOrgCode" id="orgPricing">

									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">选择需要的报告<span class="star">*</span>：</label>
								<div class="col-sm-10">
									<aist:dict id="reportType" name="reportType"
									clazz="form-control m-b" display="select"
									dictType="report_type" defaultvalue="" />
								</div>
							</div>
						</div>
						</form> 
						
						<div class="form-group">
							<div class="table-box" id="offlineEvafileUploadContainer" style="text-align:center;"></div>
						</div>
							<input type="button" class="btn btn-success" id="reportSubBtn" value="保存">
						</div>
					</div>
					</div>
				</div>
				</div>
            </div>
	<div class="scroll_box fadeInDown animated marginbot">
		<div class="row wrapper white-bg new-heading " id="serviceFlow">
			<div class="pl10">
				<h2 class="newtitle-big">
					发起报告类评估
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

		<div class="ibox-content border-bottom clearfix space_box noborder" id="aboutInfo">
			<div class="clearfix">
				<h2 class="newtitle title-mark">报告查看</h2>
				<div class="jqGrid_wrapper">
					<table id="table_list_1"></table>
					<div id="pager_list_1"></div>
				</div>
			</div>

			<!-- 案件备注信息 -->
			<div class="view-content" id="caseCommentList"> </div>

			<div class="form-btn">
				<div class="text-center">
					<button id="sub" class="btn btn-success btn-space">提交</button>
				</div>
			</div>
		</div>

</div>	


<content tag="local_script"> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> 
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/staps/jquery.steps.min.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
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
	<script src="${ctx}/js/stickUp.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/transjs/task/taskEvaReportArise.js"></script> 


	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script>
	
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script	src="${ctx}/js/trunk/task/attachment.js"></script>
	<script src="${ctx}/js/viewer/viewer.min.js"></script>

	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	<script>
	//渲染图片 
	function renderImg(){
		$('.wrapper-content').viewer('destroy');
		$('.wrapper-content').viewer();
	}
	</script>
</content>
<content tag="local_require">
    <script>
        var fileUpload;
    	require(['main'], function() {
        	requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
        		fileUpload = aistFileUpload;
    	    });
        });
    
	</script>
	</content>
</body>

</html>