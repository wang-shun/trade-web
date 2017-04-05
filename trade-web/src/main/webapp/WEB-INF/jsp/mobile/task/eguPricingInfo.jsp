<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">

<style type="text/css">

.form-control {
	margin-bottom: 5px;
	height:32px;
}

.radio {
	margin-left: 25px;margin-top:-10px
}

.mouseover-color{
	background-color:#B4D5F5;
}

</style>
<script language="javascript">
 	var ctx = "${ctx}";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
</script>
</head>
<body>
	<input type="hidden" name="evaCode" id="evaCode" value="${toEguPricing.evaCode }" />	

	<div id="disagreeModal" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-body">
					<div class="col-lg-12">
						<div class="ibox ">
							<div class="ibox-title">
							    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>询价价格异议申请</h5>
							</div>

							<form id="disagreeForm">								
								<div class="ibox-content" >

									<div class="form-group" style="margin-left:-20px">
										<label class="col-sm-10 control-label" >期望价格<span class="star">*</span>：</label>
										<div class="col-sm-12 input-group" style="margin-left:15px">
											<input type="text" name="expected_price"
												id="expected_price" placeholder="期望价格"
												class="form-control" onkeyup="checknum(this)">
											<span class="input-group-addon">万元</span>
										</div>
									</div>
									<div class="form-group" style="margin-left:-20px">
										<label class="col-sm-10 control-label" >备注<span class="star">*</span>：</label>
										<div class="col-sm-12 ">
											<input type="text" name="remark" id="remark"
												placeholder="备注" class="form-control">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<input type="button" class="btn btn-success" id="subDisagreeBtn"
						value="提交">
				</div>
			</div>
		</div>
	</div>
	
	<div id="bindCaseCodeModal" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-body">
					<div class="col-lg-12">
						<div class="ibox ">
							<div class="ibox-title">
							    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>绑定案件编号</h5>
							</div>

							<form id="applyForm">
								<div class="ibox-content" >
									<div class="form-group" style="margin-left:-20px">
										<label class="col-sm-10 control-label" >案件编号<span class="star">*</span>：</label>
										<div class="col-sm-12">
											<input type="text" name="caseCode"
												id="caseCode" placeholder="案件编号"
												class="form-control" >
										</div>
									</div>
									<div class="form-group" style="margin-left:-20px">
										<label class="col-sm-10 control-label" >主选/候选<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="radio" checked value="1"
														id="mainBank1" name="mainBank">主选银行
											<input type="radio" value="0"
														id="mainBank0" name="mainBank">候选银行			
										</div>
									</div>
									<div class="form-group" style="margin-left:-20px">
										<label class="col-sm-10 control-label" >是否接受询价结果<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="radio" checked value="1"
														id="isFinal1" name="isFinal">是
											<input type="radio" value="0"
														id="isFinal0" name="isFinal">否			
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<input type="button" class="btn btn-success" id="subBindBtn"
						value="提交">
				</div>
			</div>
		</div>
	</div>
	
	<div id="reportModal" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content" >
				<div class="modal-body">
				<div class="col-lg-12">
					<div class="ibox " >
					<div class="ibox-title">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h5>评估报告发起</h5>
					</div>
					<form id="reportForm" >
					<input type="hidden" name="orgPricing" id="orgPricing" value="P00021" />
						<div class="ibox-content" >

						<div class="form-group" style="margin-left:-20px">
							<label class="col-sm-10 control-label" >选择需要的报告<span class="star">*</span>：</label>
							<div class="col-sm-12">
								<aist:dict id="reportType" name="reportType"
								clazz="form-control m-b" display="select"
								dictType="report_type" defaultvalue="" />
							</div>
						</div>
	
						<div class="form-group" style="margin-left:-20px">
							<label class="col-sm-10 control-label" >备注：</label>
							<div class="col-sm-12">
								<input type="text" name="comment" id="comment"
												placeholder="备注" class="form-control" >
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
														action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
														method="POST" enctype="multipart/form-data">
														<noscript>
															<input type="hidden" name="redirect"
																value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
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
																		data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
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
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
								
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
							           <button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
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
				</div>
			</div>
					<input type="button" class="btn btn-success" id="reportSubBtn"
						value="提交">

		</div>
	</div>
	</div>
	</div>
	
	<div class="panel-body">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox">
					<div class="ibox-title" id="firBank">
						<h5 id="firstBank">询价信息</h5>
					</div>
					<div class="ibox-content" id="first" >							
						<div class="row">
						<form id="addToEguPricingForm">
								<input type="hidden" name="pkid" value="pkid" value="" />
								
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">物业地址：</label><label class="col-sm-8 control-label">${toEguPropertyInfo.residenceName }&nbsp;</label>						
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">评估编号：</label><label class="col-sm-8 control-label">${toEguPricing.evaCode }&nbsp;</label>										
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">申请时间：</label><label class="col-sm-8 control-label"><fmt:formatDate  value='${toEguPricing.ariseTime }' type='both' pattern='yyyy-MM-dd'/>&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">询价状态：</label><label class="col-sm-8 control-label"><aist:dict id="ass_confirm_code" name="ass_confirm_code" display="label"
												clazz="form-control m-b" dictType="ass_confirm_code"
												dictCode="${toEguPricing.result }" />&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">单价：</label><label class="col-sm-8 control-label">${toEguPricing.unitPrice }元&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">总价：</label><label class="col-sm-8 control-label">${toEguPricing.totalPrice }万元&nbsp;</label>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">房屋类型：</label><label class="col-sm-8 control-label"><aist:dict id="prop_type" name="prop_type" display="label"
												clazz="form-control m-b" dictType="prop_type"
												dictCode="${toEguPropertyInfo.propType }" />&nbsp;</label>
		
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">楼栋室号：</label><label class="col-sm-8 control-label">${toEguPropertyInfo.buildingNo }-${toEguPropertyInfo.roomNo }&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">建筑面积：</label><label class="col-sm-8 control-label">${toEguPropertyInfo.area }&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">期望评估价：</label><label class="col-sm-8 control-label">${toEguPricing.expectRate }万元&nbsp;</label>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">房屋结构：</label><label class="col-sm-8 control-label">${toEguPropertyInfo.room }室${toEguPropertyInfo.hall }厅${toEguPropertyInfo.toilet }卫&nbsp;</label>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">楼层：</label><label class="col-sm-8 control-label">${toEguPropertyInfo.floor }/${toEguPropertyInfo.totalFloor }层&nbsp;</label>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">平面类型：</label><label class="col-sm-8 control-label"><aist:dict id="plane_type" name="plane_type" display="label"
												clazz="form-control m-b" dictType="flat_type"
												dictCode="${toEguPropertyInfo.planeType }" />&nbsp;</label>	
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">朝向：</label><label class="col-sm-8 control-label"><aist:dict id="towards" name="towards" display="label"
												clazz="form-control m-b" dictType="towards"
												dictCode="${toEguPropertyInfo.towards }" />&nbsp;</label>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">景观类型：</label><label class="col-sm-8 control-label"><aist:dict id="scape" name="scape" display="label"
												clazz="form-control m-b" dictType="scape"
												dictCode="${toEguPropertyInfo.scape }" />&nbsp;</label>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">临街类型：</label><label class="col-sm-8 control-label"><aist:dict id="near_street" name="near_street" display="label"
												clazz="form-control m-b" dictType="near_street"
												dictCode="${toEguPropertyInfo.nearStreet }" />&nbsp;</label>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label" style="text-align:right">贷款银行：</label><label class="col-sm-8 control-label">${toEguPricing.finOrgCode }&nbsp;</label>
									</div>
								
							</form>
							</div>						
						</div>
						<div class="form-group">
							<a href="#" class="btn btn-primary" style="width:98%;" id="disagreeApplyBtn" >发起异议</a>
						</div>
						<div class="form-group">
							<a href="#" class="btn btn-primary" style="width:98%;" id="bindBtn" >绑定案件</a>
						</div>

						<div class="form-group">
							<a href="#" class="btn btn-primary" style="width:98%;" id="subPricingBtn" >确认询价结果</a>
						</div>
						<div class="form-group">
							<a href="#" class="btn btn-primary" style="width:98%;" id="reportApplyBtn" >发起报告</a>
						</div>
				</div>
			</div>
		</div>
	</div>

<content tag="local_script"> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>

	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
   	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
		<!-- 上传附件相关 --> 
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
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
	
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/mobilejs/task/eguPricingInfo.js"></script> 	
	 </content>
</body>

</html>