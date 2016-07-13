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

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<%--环节编码 --%>
	<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
	<!-- 交易单编号 -->
	<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
	<!-- 流程引擎需要字段 -->
	<input type="hidden" id="taskId" name="taskId" value="${taskId }"> 
	<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
	<input type="hidden" id="eva_code" name="eva_code" value="${toEguPricing.evaCode }"> 
	
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<h2>发起报告单</h2>
			<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
			</ol>
                        
		</div>
	</div>
				<div class="wrapper wrapper-content  animated fadeInRight">
						<div class="col-lg-12">
							<div class="ibox">
								<div class="ibox-title">
									<h5>报告查看</h5>
								</div>
								<div class="ibox-content">
									<div class="jqGrid_wrapper">
										<table id="table_list_1"></table>
										<div id="pager_list_1"></div>
									</div>
								</div>
							</div>
						</div>
						<!-- 案件备注信息 -->
						<div id="caseCommentList" class="add_form">
						</div>
						
						<div class="ibox-title">
				            <button class="btn btn-primary" id="sub" type="button"><i class="fa fa-check"></i>&nbsp;提交</button>   
						</div>
						
				</div>							
						<div id="modal-form-report" class="modal fade" aria-hidden="true" style="height: 800px">
							<div class="modal-dialog" style="width: 1000px; height: 800px">
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
											<%for(int j=1;j<4;j++) {%>
											<div class="ibox-content">
												<%if(j==1){ %>
												<h5>身份证</h5>
												<%}else if(j==2) {%>
												<h5>房产证</h5>
												<%}else{ %>
												<h5>购房合同</h5>
												<%} %>
												<div class="" id="fileupload_div_pic<%=j%>">
													<form id="fileupload"
														action="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"
														method="POST" enctype="multipart/form-data">
														<noscript>
															<input type="hidden" name="redirect"
																value="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload">
															<input type="hidden" id="preFileCode" name="preFileCode"
																value="<%=j%>">
														</noscript>
														<h5 align="left"></h5>
														<div class="row-fluid fileupload-buttonbar">
															<div class="" style="height: auto">
																<div role="presentation" class="table table-striped "
																	style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
																	<div id="picContainer<%=j %>" class="files"
																		data-toggle="modal-gallery"
																		data-target="#modal-gallery"></div>
																	<span class=" fileinput-button "
																		style="margin-left: 0 !important; width: 80px;">
																		<div id="chandiaotuBtn" class=""
																			style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
																			<i class="fa fa-plus"></i>
																		</div> <input id="picFileupload<%=j %>" type="file"
																		name="files[]" multiple
																		data-url="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"
																		data-sequential-uploads="true">
																	</span>
																</div>
															</div>
														</div>
													</form>
												</div>

												<div class="row-fluid">
													<div class="">
														<script id="templateUpload<%=j %>" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
								
							        <div class="preview"><span class="fade"></span></div>
								
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									
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
						<script id="templateDownload<%=j %>" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="<%=j%>"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.thumbnail_url) { %}
							                <img src="http://img.sh.centaline.com.cn/salesweb/image/{%=file.id%}/80_80_f.jpg" style="height:80px;width:80px">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-179px;">
							           <button data-url="<aist:appCtx appName='aist-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
													</div>
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
											<%} %>
										</div>

										<input type="button" class="btn btn-success" id="reportSubBtn" value="保存">

									</div>
								</div>
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
</content></body>

</html>