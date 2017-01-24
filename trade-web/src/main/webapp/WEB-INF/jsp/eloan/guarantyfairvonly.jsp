<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>强制公证</title>

<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<link rel="stylesheet" href="${ctx}/static/css/animate.css">
<link rel="stylesheet" href="${ctx}/static/css/style.css">

<link rel="stylesheet"
	href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/toastr/toastr.min.css">
<!-- index_css  -->

<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet"
	href="${ctx}/static/trans/css/common/stickmenu.css">
<link href="${ctx}/static/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
<link rel="stylesheet"
	href="${ctx}/static/trans/css/common/uplodydome.css">
<link rel="stylesheet"
	href="${ctx}/static/trans/css/eloan/eloan_detail.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet"
	href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">

<link rel="stylesheet"
	href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css"
	type="text/css" />

<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
	rel="stylesheet">

</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
		<!-- main Start -->
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<div class="ibox-content" id="reportOne">
					<jsp:include page="/WEB-INF/jsp/eloan/common/eloanBaseInfo.jsp"></jsp:include>
				</div>

				<div class="ibox-content ibox-space">
					<form method="get" class="form_list">
						<input type="hidden" id="caseCode" value="${eloanCase.eloanCode }"/>
						<input type="hidden" id="riskControlId" name="riskControlId"
							value="${toRcForceRegister.rcId }">
						<div class="modal_title">强制公证信息登记</div>
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small"> <font
									color="red">*</font>公正处名称
								</label> <input disabled="disabled" type="text" placeholder=""
									class="select_control sign_right_one" id="notaryName"
									name="notaryName" value="${toRcForceRegister.notaryName }">
							</div>
							<div class="form_content input-daterange"
								data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_small"> <font
									color="red">*</font>执行时间
								</label> <input disabled="disabled" class="input_type sign_right_two"
									value="<fmt:formatDate value="${toRcForceRegister.executeTime}" pattern="yyyy-MM-dd" />"
									name="executeTime" id="executeTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small"> 备注 </label>
								<%-- <textarea disabled="disabled" class="sign_right_two"
									value="${rcRiskControl.riskComment }" id="riskComment"
									name="riskComment" style="width: 400px; height: 100px;"></textarea> --%>
								<textarea class="select_control sign_right_two" id="riskComment" name="riskComment" style="width:400px;height:100px; display:block;margin-left:124px">${rcRiskControl.riskComment }</textarea>
							</div>
						</div>

						<button type="button" class="close close_blue"
							style="display: none;" data-dismiss="modal">
							<i class="iconfont icon_rong">&#xe60a; </i>
						</button>
						<%--  <table class="table table_blue ">
                                    <thead>
                                        <tr>
                                            <th>
                                                公证书
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <ul class="filelist">
                                                    <li id="WU_FILE_0">
                                                        <p class="imgWrap">
                                                            <img src="${ctx}/static/trans/img/uplody01.png">
                                                        </p>
                                                        <div class="file-panel" >
                                                            <span class="file-name">公证书1</span>
                                                            <span class="cancel pull-right">删除</span>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <p class="imgWrap fileposition">
                                                            <img src="${ctx}/static/trans/img/uplody02.png">
                                                            <input type="file" name="file" class="webupload_file" multiple="multiple" accept="image/*">
                                                        </p>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table> --%>

						<div class="ibox-title" style="height: auto;">
							<c:choose>
								<c:when test="${accesoryList!=null}">
									<h5>附件</h5>
									<div class="table-box" id="fileUploadContainer"></div>
								</c:when>
							</c:choose>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- main End -->
	</div>
	<content tag="local_script"> <!-- Mainly scripts --> <!--<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> -->

	<!-- Custom and plugin javascript --> <script
		src="${ctx}/static/js/inspinia.js"></script> <script
		src="${ctx}/static/js/plugins/pace/pace.min.js"></script> <script
		type="text/javascript"
		src="${ctx}/static/trans/js/plugins/poshytip/jquery.poshytip.js"></script>
	<script
		src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- 开关按钮js --> <script
		src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
	<script src="${ctx}/static/trans/js/eloan/eloan_guarant.js"></script> <!-- 上传附件相关 -->
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> <script
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
	<!-- 附件保存修改相关 --> <script src="${ctx}/js/trunk/eloan/attachmentvonly.js"></script>

	<script>
	    if ("${idList}" != "") {
			var idList = eval("(" + "${idList}" + ")");
		} else {
			var idList = [];
		}
		var taskitem = "RiskControl";
		// 这里实际上是指eloanCode,为了兼容
		var caseCode = "${eloanCase.eloanCode }";
		
	    $(document).ready(function () {
	    	$('.input-daterange').datepicker({
	        	format : 'yyyy-mm-dd',
	    		weekStart : 1,
	    		autoclose : true,
	    		todayBtn : 'linked',
	    		language : 'zh-CN'
	        });	    	
	    
        	var pkid = "${pkid}";
        	var eloanCode =  "${eloanCase.eloanCode }";
        	
        	 $('.submit_btn').click(function(){
        		var notaryName = $("#notaryName").val();
 				var executeTime = $("#executeTime").val();
 				if(notaryName == ""){
 					 window.wxc.alert("请填写公正处名称！");
 					 return false;
 				}
 				if(executeTime == ""){
 					 window.wxc.alert("请填写执行时间！");
 					 return false;
 				}
             	var toRcForceRegister = {
             		rcId : $('#riskControlId').val(),
             		notaryName : $('#notaryName').val(),
             		executeTime : $('#executeTime').val()
             	}
            	var rcRiskControl = {
             	    pkid : $('#riskControlId').val(),
             		eloanCode : eloanCode,
             		riskComment : $('#riskComment').val(),
             		riskType : 'forceRegister'
                }
             	var toRcForceRegisteVO = {
             		toRcForceRegister : toRcForceRegister,
             		rcRiskControl : rcRiskControl
             	}
             	
             	var url = "${ctx}/riskControl/saveToRcForceRegister";
     			$.ajax({
     				cache : true,
     				async : false,//false同步，true异步
     				type : "POST",
     				url : url,
     				dataType : "json",
     				contentType:"application/json",  
     				data : JSON.stringify(toRcForceRegisteVO),
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
     				},
     				success : function(data) {
     					window.wxc.success(data.message);
     					window.close();
     					//window.opener.callback();
     					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
     				},
     				error : function(errors) {
     					window.wxc.error("数据保存出错");
     				}
     			});
     			
     			// 保存附件相关信息
     			deleteAndModify();
        	 });
        	 
             $(".close_btn").click(function(){
         		window.close();
					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
          		})
	    });
    
    </script> </content>
    
    <content tag="local_require">
    <script>
	    require(['main'], function() {
			requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','valid','bootstrapModal','modalmanager'],function($,aistFileUpload){
			    aistFileUpload.init({
		    		caseCode : $('.form_list #caseCode').val(),
		    		partCode : "RiskControl",
		    		fileUploadContainer : "fileUploadContainer"
		    	}); 
		    });
	    });
	</script>
</content>
    
</body>

</html>