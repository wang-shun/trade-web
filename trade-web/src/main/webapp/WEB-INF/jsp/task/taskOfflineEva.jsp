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
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />


<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">

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
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div>
		<div class="wrapper border-bottom white-bg page-heading">
			<div class="row wrapper white-bg new-heading ">
				<div class="pl10">
					<h2 class="newtitle-big">
						线下评估发起
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
		</div>
		<div class="ibox-content border-bottom clearfix space_box noborder">
			<h2 class="newtitle title-mark">评估信息</h2>
			<div class="text_list">
				<ul class="textinfo">
					<li>
						<em>报告类型</em><span class="yuanwid"><aist:dict display="label" id="reportType" name="reportType" dictCode="${OfflineEva.reportType}" dictType="report_type" /></span>
					</li>
					<li>
						<em>期望评估价</em><span class="yuanwid">${OfflineEva.expectedPrice}</span>
					</li>
					<li>
						<em>面积</em><span class="yuanwid">${OfflineEva.square}平方</span>
					</li>
					<li>
						<em>楼层</em><span class="yuanwid">${OfflineEva.locateFloor}/${OfflineEva.totalFloor}</span>
					</li>

					<li>
						<em>信贷员姓名</em><span class="yuanwid">${OfflineEva.loanerName}</span>
					</li>
					<li>
						<em>信贷员电话</em><span class="yuanwid">${OfflineEva.loanerPhone}</span>
					</li>
					<li>
						<em>评估公司</em><span class="yuanwid">${OfflineEva.finOrgName}</span>
					</li>
					<li>
						<em class="pull-left">支行</em><span class="infolong pull-left">${OfflineEva.lastLoanBank}</span>
					</li>
					<li>
						<em class="pull-left">物业地址</em><span class="infolong pull-left">${OfflineEva.propertyAddr}</span>
					</li>
					<li>
						<em class="pull-left">备注</em><span class="infolong pull-left">${OfflineEva.comment}</span>
					</li>
				</ul>
			</div>

			<h2 class="newtitle title-mark">附件下载</h2>
			<div class="ibox-content">
                <div id="imgShow" class="lightBoxGallery">
                    <!-- <a href="#" onClick="downLoad('ff8080814faae259014fabb432c1002b')" data-gallery="" style="padding-bottom: 5px;padding-top: 5px;">
                    	<img src="http://img.sh.centaline.com.cn/salesweb/image/ff8080814faae259014fabb432c1002b/80_80_f.jpg">
                    </a> -->
                </div>

            </div>
			<h2 class="newtitle title-mark">填写任务信息</h2>
			<div class="form_list">
				<div class="marinfo">
					<div class="line">
						<div class="form_content">
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
							<label class="control-label sign_left_small">当前状态</label>
							<aist:dict clazz="select_control data_style" id="status" name="status" display="select" defaultvalue="${OfflineEva.status}" dictType="report_status_code" />
							<input type="hidden" name="feedback" id="feedback" />
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small select_style mend_select">
								时间
							</label>
							<div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
								<input type="text" class="input_type yuanwid datatime" id="reportResponseTime" style="width:127px;"
									   name="reportResponseTime" value="<fmt:formatDate  value='${OfflineEva.reportResponseTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 案件备注信息 -->
			<div id="caseCommentList" class="add_form"></div>

		</div>

		<div class="form-btn">
			<div class="text-center">
				<button  class="btn btn-success btn-space"  onclick="save(false)">保存</button>
				<button class="btn btn-success btn-space"  onclick="submit()">提交</button>
			</div>
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
	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 

	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>

	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
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
			 
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : taskitem
			});
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
						if(null!=data.message){
							window.wxc.alert(data.message);
						}
					} else {
						 window.wxc.success("保存成功。",{"wxcOk":function(){
							 window.close();
							 window.opener.callback();
						 }});
					}
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}

		//验证控件checkUI();
		function checkForm() {
			if ($('input[name=]').val() == '') {
				window.wxc.alert("实际审税时间为必填项!");
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