<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">

<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<style type="text/css" >

</style>
<script type="text/javascript">
	var ctx = "${ctx}";
	var index=0;
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

	<div class="row">
		<div class="ibox-title">
			<h5>填写监管签约信息</h5>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="spvForm">
					<input type="hidden" name="pkid" id="pkid" value="${toSpvVo.toSpv.pkid }" />
					<input type="hidden" id="spvType" name="spvType" value="${toSpvVo.toSpv.spvType}">

					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<div class="form-group">
						<label class="col-sm-2 control-label">监管协议号<span class="star">*</span></label>
						<div class="col-sm-10 input-group">
							<input type="text" class="form-control" id="spvCode" name="spvCode" value="${toSpvVo.toSpv.spvCode }">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">监管金额<span class="star">*</span></label>
						<div class="col-sm-10 input-group">
							<input type="text" class="form-control" id="amount" name="amount" value="${toSpvVo.toSpv.amount }" onkeyup="checknum(this)">
							<span class="input-group-addon">万</span>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">监管机构<span class="star">*</span></label>
						<div class="col-sm-10 input-group">
						<select name="spvInsti" id="spvInsti" class="form-control">
						
						</select>
						</div>
					</div>
					<div class="form-group" >
						<label class="col-sm-2 control-label">签约时间<span class="star">*</span></label>
						<div class="input-group date" id="date_1">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" id="signTime" readonly
									name="signTime" value="<fmt:formatDate  value='${toSpvVo.toSpv.signTime}' type='both' pattern='yyyy-MM-dd'/>">
						</div>
					</div>
					
					<div class="form-group" id="inCashFlow">
						<input type="hidden" name="pkid" value="${toSpvVo.toCashFlow.pkid}" />
					
						<label class="col-sm-2 control-label">进账金额<span class="star">*</span></label>
						<div class="col-sm-2 " style="padding-left:0px">
							<input type="text" class="form-control" id="flowAmount" name="in_flowAmount" value="${toSpvVo.toCashFlow.flowAmount}" onkeyup="checknum(this)">
						</div>
						<label class="col-sm-1 control-label" style="width:11%">进账时间<span class="star">*</span></label>
						<div class="col-md-3 input-group" style="padding-left:15px">
						<div class="input-group date" id="inDate_0" >
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="form-control" id="in_flowTime" name="in_flowTime" value="<fmt:formatDate  value='${toSpvVo.toCashFlow.flowTime}' type='both' pattern='yyyy-MM-dd'/>">						
						</div>
						</div>
					</div>
					<c:choose>
					<c:when test="${toSpvVo.toSpvDeCondList!=null }">	
						<c:forEach items="${toSpvVo.toSpvDeCondList }" var="spvDeCond" varStatus="status">
						<div class="form-group"  id="div_${status.index }" name="div_de">
							<input type="hidden" name="pkid" id="pkid_${status.index }" value="${spvDeCond.pkid }" />
							<label class="col-sm-2 control-label" >解除条件${status.index+1 }</label>
							<div class="col-md-2" style="padding-left:0px;">
								<input type="text" class="form-control" id="deCondition_${status.index }" name="deCondition" value="${spvDeCond.deCondition }">
							</div>
		
							<label class="col-sm-1 control-label" style="width:11%">解除金额</label>
							<div class="col-md-2">
								<input type="text" class="form-control" id="deAmount_${status.index }" name="deAmount" value="${spvDeCond.deAmount }" onkeyup="checknum(this)">
							</div>
							<label class="col-sm-2 control-label" style="width:12%">预计解除日期</label>
							<div class="col-sm-3 input-group" >
							<div class="input-group date" id="preTime_${status.index }" >
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" id="dePreTime_${status.index }" name="dePreTime" value="<fmt:formatDate  value='${spvDeCond.dePreTime }' type='both' pattern='yyyy-MM-dd'/>">							
								<span class="input-group-addon"><a href="javascript:removeLine('${status.index }');">删除</a></span>
							
							</div>
							</div>
						
						</div>
						</c:forEach>

					</c:when>
					<c:otherwise>
					<div class="form-group"  id="div_0"  name="div_de">
						<label class="col-sm-2 control-label" >解除条件1</label>
						<div class="col-sm-2" style="padding-left:0px">
							<input type="text" class="form-control" id="deCondition_0" name="deCondition" value="">
						</div>
	
						<label class="col-sm-1 control-label" style="width:11%">解除金额</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="deAmount_0" name="deAmount" value="" onkeyup="checknum(this)">
						</div>
					  	<label class="col-sm-2 control-label" style="width:12%">预计解除日期</label>
						<div class="col-sm-3 input-group" >
							<div class="input-group date" id="preTime_0" >
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" id="dePreTime_0" name="dePreTime" value="">						
								<span class="input-group-addon"><a href="javascript:removeLine('0');">删除</a></span>
						</div>
						</div>
					</div>
					
					</c:otherwise>
					</c:choose>
					<div class="form-group" >
					<div  id="addLine" class="col-sm-10" style="margin-left:150px">
							<a href="javascript:addLine();">增加</a>
						</div>
					</div>
					<c:choose>
						<c:when test="${toSpvVo.toCashFlowList!=null && fn:length(toSpvVo.toCashFlowList) > 0 }">
						<c:forEach items="${toSpvVo.toCashFlowList }" var="toCashFlow" varStatus="status">
							<div class="form-group"  id="deflowdiv_${status.index }" name="div_deflow">
							<input type="hidden" name="pkid" value="${toCashFlow.pkid }" />
							<label class="col-sm-2 control-label" >出账金额</label>
							<div class="col-sm-2" style="padding-left:0px">
								<input type="text" class="form-control" id="flowAmount_${status.index }" name="flowAmount" value="${toCashFlow.flowAmount }" onkeyup="checknum(this)">
							</div>
							<label class="col-sm-1 control-label" style="width:11%">实际出账时间</label>
							<div class="col-sm-6 input-group"  style="padding-left:15px;width:53.66666%">
							<div class="input-group date" id="deflowDate_${status.index }" >
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" id="flowTime_${status.index }" name="flowTime" value="<fmt:formatDate  value='${toCashFlow.flowTime }' type='both' pattern='yyyy-MM-dd'/>">
								<span class="input-group-addon"><a href="javascript:removeFlowLine('${status.index }');">删除</a></span>
							</div>
						</div>
					</div>
						</c:forEach>
						
						</c:when>
						<c:otherwise>
						<div class="form-group"  id="deflowdiv_0" name="div_deflow">
							<input type="hidden" name="pkid"  value="" />
	
							<label class="col-sm-2 control-label" >出账金额</label>
							<div class="col-sm-2" style="padding-left:0px">
								<input type="text" class="form-control" id="flowAmount_0" name="flowAmount" value="" onkeyup="checknum(this)">
							</div>
							<label class="col-sm-1 control-label" style="width:11%">实际出账时间</label>
							<div class="col-sm-6 input-group"  style="padding-left:15px;width:53.66666%">
							<div class="input-group date" id="deflowDate_0" >
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" id="flowTime_0" name="flowTime" value="">
								<span class="input-group-addon"><a href="javascript:removeFlowLine('0');">删除</a></span>
							</div>
						</div>
					</div>
						
						</c:otherwise>
	
					</c:choose>
	
					<div class="form-group" >
					<div  id="addFlowLine" class="col-sm-10" style="margin-left:150px">
							<a href="javascript:addFlowLine();">增加</a>
						</div>
					</div>
					<div style="clear:both" ></div>

					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10 input-group">
							<input type="text" class="form-control" id="remark" name="remark" value="${toSpvVo.toSpv.remark }">
						</div>
					</div>	
								
				</form>
			</div>
		</div>
		<div class="ibox-title" style="height:auto;">
		<c:choose>  
	    <c:when test="${accesoryList!=null}">  
		<h5>上传备件<br><br><br>${accesoryList[0].accessoryName }</h5>
		<c:forEach var="accesory" items="${accesoryList}" varStatus="status">
               <div class="" id="fileupload_div_pic"> 
               <form id="fileupload"
				action="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"
				method="POST" enctype="multipart/form-data">
				<noscript>
					<input type="hidden" name="redirect" value="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload">
					<input type="hidden" id="preFileCode" name="preFileCode" value="${accesory.accessoryCode }">
				</noscript>
				<c:if test="${status.index != 0}">
					<h5 align="left"><br>${accesory.accessoryName }</h5>
				</c:if>
				<div class="row-fluid fileupload-buttonbar">
					<div class="" style="height: auto">
						<div role="presentation" class="table table-striped "
							style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
							<div id="picContainer${accesory.pkid }" class="files" data-toggle="modal-gallery"
								data-target="#modal-gallery"></div>
								<span class=" fileinput-button " style="margin-left:10px!important;width:80px;">
								<div id="chandiaotuBtn" class=""
									style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
									<i class="fa fa-plus"></i>
								</div> 
								<input id="picFileupload${accesory.pkid }" type="file" name="files[]" multiple
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
			<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-left:10px;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
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
							        <div class="cancel" style="margin-top:-125px;margin-left:85%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
						<script id="templateDownload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.thumbnail_url) { %}
							                <img src="http://img.sh.centaline.com.cn/salesweb/image/{%=file.id%}/80_80_f.jpg" alt="">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button data-url="<aist:appCtx appName='aist-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
				</div> 
			</div>
			</c:forEach>
			
			<div class="row-fluid" style="display:none;">
				<div class="span4">
					<div class="control-group">
						<a class="btn blue start" id="startUpload" style="height: 30px; width: 50px"> 
							<i class="icon-upload icon-white"></i> <span>上传</span>
						</a>
					</div>
				</div>
			</div>
	    </c:when>  
	    <c:otherwise> 
		<h5>上传备件<br></h5>
	    </c:otherwise>  
		</c:choose> 
		</div>
		
		<div class="ibox-title">
			<a href="#" class="btn btn-primary" id="saveBtn">保存</a>
		<!--  	<a href="myTaskList" class="btn btn-primary" onclick="submit()">提交</a>-->
		</div>
	</div>

	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> 
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
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
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
	<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
	
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/transjs/spv/saveSpv.js"></script>
    
	</content>
</body>


</html>