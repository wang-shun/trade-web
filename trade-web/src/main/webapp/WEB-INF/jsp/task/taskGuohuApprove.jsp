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

<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;
}
.radio.radio-inline > input{
	margin-left:10px;
}
.checkbox.checkbox-inline > div{
	margin-left:25px;
}
.checkbox.checkbox-inline > input{
	margin-left:20px;
}

</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="row">
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
		<%-- <div class="ibox-title">
		<c:choose>  
	    <c:when test="${accesoryList!=null}">  
		<h5>上传备件<br>${accesoryList[0].accessoryName }</h5>
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
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
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
							                <img src="http://img.sh.centaline.com.cn/salesweb/image/{%=file.id%}/80_80_f.jpg" style="width:80px;height:80px;margin-left:10px;">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-130px;">
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
		<h5>上传备件<br>无需上传备件</h5>
	    </c:otherwise>  
		</c:choose> 
		</div> --%>
		
		<div class="ibox-title">
			   <h5>成交信息</h5> 
               <div class="panel-body ibox-content">
                 <div class="row ">
                     <label class="col-sm-3 control-label">合同价：
                      	<c:if test="${!empty caseInfo.conPrice}">
                     		${caseInfo.conPrice/10000}  &nbsp&nbsp万元
                 		</c:if>
                     </label> 
                   <%--   <label class="col-sm-3 control-label">成交价：
                        <c:if test="${!empty caseInfo.realPrice}">
                     		${caseInfo.realPrice/10000}&nbsp&nbsp万元
                 		</c:if>
                     </label> --%>
                     <label class="col-sm-3 control-label">核定价格：
                          <c:if test="${!empty caseInfo.taxPricing}">
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
                     <label class="col-sm-3 control-label">商贷金额：
                         <c:if test="${!empty toMortgage.comAmount}">
                    ${toMortgage.comAmount}&nbsp&nbsp万元
                </c:if>
                     </label>
                     <label class="col-sm-3 control-label">商贷年限：${toMortgage.comYear}</label>
                	    <label class="col-sm-3 control-label">商贷利率：${toMortgage.comDiscount}</label>
                 </div>
                	<div class="row ">
                     <label class="col-sm-3 control-label">是否自办：<c:choose>
                     <c:when test="${toMortgage.isDelegateYucui=='1'}">是</c:when>
                     <c:when test="${toMortgage.isDelegateYucui=='0'}">否</c:when>
                      <c:otherwise>
                      ${toMortgage.isDelegateYucui}
                      </c:otherwise>
                     </c:choose></label>
                     <label class="col-sm-3 control-label">公积金贷款金额：
                         <c:if test="${!empty toMortgage.prfAmount}">
                    ${toMortgage.prfAmount}&nbsp&nbsp万元
                </c:if>
                     </label>
                     <label class="col-sm-3 control-label">公积金贷款年限：${toMortgage.prfYear}</label>
                     <label class="col-sm-3 control-label">放款方式：${caseDetailVO.lendWay}</label>
                 </div>
                	<div class="row ">
                     <label class="col-sm-3 control-label">主贷人：${caseDetailVO.mortBuyer}</label>
                     <label class="col-sm-3 control-label">主贷人单位：${caseDetailVO.buyerWork}</label>
                     <label class="col-sm-3 control-label">房贷套数：${toMortgage.houseNum}</label>
                     <label class="col-sm-3 control-label">申请时间：${caseDetailVO.prfApplyDate}</label>
                 </div>
                 <div class="row ">
                      <label class="col-sm-6 control-label">贷款银行：${caseDetailVO.parentBankName}</label>
                      <label class="col-sm-6 control-label">支       行：${caseDetailVO.bankName}</label>
                 </div>
                	<div class="row ">
                     <label class="col-sm-3 control-label">信贷员：${toMortgage.loanerName}</label>
                     <label class="col-sm-3 control-label">信贷员电话：${toMortgage.loanerPhone}</label>
                     <label class="col-sm-3 control-label">评估公司：${caseDetailVO.evaName}</label>
                     <label class="col-sm-3 control-label">评估费金额：
                         <c:if test="${!empty caseDetailVO.evaFee}">
                    ${caseDetailVO.evaFee}&nbsp&nbsp元
                </c:if>
                     </label>
                 </div>
                 <div class="row ">
                      <label class="col-sm-12 control-label">备注：${toMortgage.remark}</label>
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
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form class="form-horizontal" id="guohuApproveForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType" value="${approveType }">
					<%-- <input type="hidden" id="lapPkid" name="lapPkid" value="${toApproveRecord.pkid }"> --%>
					<input type="hidden" id="operator" name="operator" value="${operator }">
					<div class="form-group">
						<label class="col-sm-2 control-label">审批结果</label>
						<div class="radio i-checks radio-inline">
							<label> 
								<input type="radio" checked="checked" value="true" id="optionsRadios1" name="GuohuApprove">审批通过
							</label>
						</div>
						<div class="radio i-checks radio-inline">
							<label> 
								<input type="radio" value="false" id="optionsRadios2" name="GuohuApprove">审批未通过
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">审批意见</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="GuohuApprove_response" name="GuohuApprove_response">
						</div>
					</div>
				</form>
			</div>
		</div>	
		
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


	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

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
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<!-- 显示上传的附件 -->
	<script src="${ctx}/js/trunk/case/showCaseAttachment.js"></script>
	
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<script>
	    $(function() {
	    	getShowAttachment();
	    })
	    
		/**提交数据*/
		function submit() {
			if(!checkAttachment()) {
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
    		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  

                     if(status=='timeout'){//超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  });
    		  		 $(".btn-primary").one("click",function(){
    		  				parent.$.fancybox.close();
    		  			});	 
    		                } 
    		            } ,  
				success : function(data) {
				//	alert("数据已保存。");
					window.location.href = "${ctx }/task/myTaskList";
				},
				error : function(errors) {
					alert("数据保存出错");
				} 
			});
		}
	</script> 
	</content>
</body>
</html>
