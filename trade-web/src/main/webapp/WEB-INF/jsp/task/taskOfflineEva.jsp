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
	var index = 0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var attachmentCode = "ToEvaReport";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<div class="row">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<h2>线下评估报告发起</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="ibox-title">
			<h5>评估信息</h5>

			<div class="panel-body ibox-content">
				<div class="row" style="margin-left: 90px;">
					<label class="col-sm-3 control-label">评估公司：${OfflineEva.finOrgName}</label>
					<label class="col-sm-3 control-label">支行：${OfflineEva.lastLoanBank}</label>
					<label class="col-sm-3 control-label">报告类型：
					<aist:dict display="label" id="reportType" name="reportType" dictCode="${OfflineEva.reportType}" dictType="report_type" />
					</label>
				</div>
				<div class="row" style="margin-left: 90px;">
					<label class="col-sm-3 control-label">物业地址：${OfflineEva.propertyAddr}</label>
					<label class="col-sm-3 control-label">楼层：${OfflineEva.locateFloor}/${OfflineEva.totalFloor}</label>
					<label class="col-sm-3 control-label">面积：${OfflineEva.square}平方</label>
				</div>
				<div class="row" style="margin-left: 90px;">
					<label class="col-sm-3 control-label">期望评估价：${OfflineEva.expectedPrice}</label>
					<label class="col-sm-3 control-label">信贷员姓名：${OfflineEva.loanerName}</label>
					<label class="col-sm-3 control-label">信贷员电话：${OfflineEva.loanerPhone}</label>
				</div>
				<div class="row" style="margin-left: 90px;">
					<label class="col-sm-9 control-label">备注：${OfflineEva.comment}</label>
				</div>
			</div>
			<%-- <div class="ibox-content">
				<img onClick="downLoad('ff8080814faae259014fabb432c1002b')" src=">
			</div> --%>
            <h5>附件下载<br></h5>
			<div class="ibox-content">
                <div id="imgShow" class="lightBoxGallery">
                    <!-- <a href="#" onClick="downLoad('ff8080814faae259014fabb432c1002b')" data-gallery="" style="padding-bottom: 5px;padding-top: 5px;">
                    	<img src="http://img.sh.centaline.com.cn/salesweb/image/ff8080814faae259014fabb432c1002b/80_80_f.jpg">
                    </a> -->
                </div>

            </div>
            <h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="loanlostApplyForm">
					<%--环节编码 --%>
					<%-- <input type="hidden" id="partCode" name="partCode" value="${taskitem}"> --%>
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="pkid" name="pkid" value="${OfflineEva.pkid}">

					<div class="form-group">
						<label class="col-sm-2 control-label">当前状态</label>
						<div class="col-sm-2">
							<aist:dict clazz="form-control m-b" id="status" name="status" display="select" defaultvalue="${OfflineEva.status}" dictType="report_status_code" />
						</div>
						<input type="hidden" name="feedback" id="feedback" />
					</div>
					<div class="form-group" id="data_1">
						<label class="col-sm-2 control-label">时间</label>
						<div class="input-group date" style="margin-left: 197px;">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" id="reportResponseTime" style="width:127px;"
									name="reportResponseTime" value="<fmt:formatDate  value='${OfflineEva.reportResponseTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
						</div>
					</div>
				</form>

			</div>
		</div>

		<div class="ibox-title">
			<a href="#" class="btn" onclick="save(false)">保存</a> <a
				href="myTaskList" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
	</div>
	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery-1.8.3.min.js"></script> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> 
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/transjs/task/showAttachment.js"></script>
	<script src="${ctx}/transjs/common/caseTaskCheck.js"></script> 

	<script>
		$(document).ready(function() {

			/**日期组件*/
			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : false,//显示年日历周
				autoclose : true
			});
			 getExplPicByhouseCode();
		});

		/**提交数据*/
		function submit() {
			save(true);
		}

		/**保存数据*/
		function save(b) {
			$("#feedback").val($("#status :selected").text());

			if (!checkForm()) {
				return;
			}
			var jsonData = $("#loanlostApplyForm").serializeArray();

			var url = "${ctx}/task/offlineEva/saveToEvaReport";
			if (b) {
				url = "${ctx}/task/offlineEva/submitToEvaReport";
			}
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
	   		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  
                	$.unblockUI();  
                	if(b){ 
                        $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
    				    $(".blockOverlay").css({'z-index':'1900'});
                	}   
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
					if(b) {
						caseTaskCheck();
						//window.location.href = "${ctx }/task/myTaskList";
					} else {
						alert("保存成功。");
					}
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}

		//验证控件checkUI();
		function checkForm() {
			if ($('input[name=]').val() == '') {
				alert("实际审税时间为必填项!");
				$('input[name=]').focus();
				return false;
			}
			/* if($('input[name=commet]').val()=='') {
			    alert("备注为必填项!");
			    $('input[name=commet]').focus();
			    return false;
			} */
			return true;
		}
		
	</script> </content>
</body>


</html>