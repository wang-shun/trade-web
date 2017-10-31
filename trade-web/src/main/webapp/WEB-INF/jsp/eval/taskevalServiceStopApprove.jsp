<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;}
.radio.radio-inline > input{
	margin-left:10px;}
.checkbox.checkbox-inline > div{
	margin-left:25px;}
.checkbox.checkbox-inline > input{
	margin-left:20px;}

</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<h2>评估爆单审批</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/eval/detail?evaCode=${evaCode}">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="lamform">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 评估单编号 -->
					<input type="hidden" id="evaCode" name="evaCode" value="${evaCode}">
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="instCode" value="${processInstanceId}">

					<div class="form-group">
						<label class="col-sm-2 control-label">审批结果</label>
						<div class="radio i-checks radio-inline">
							<label> 
								<input type="radio" checked="checked" value="true" id="optionsRadios1" name="isApproved">审批通过
							</label>
						</div>
						<div class="radio i-checks radio-inline">
							<label> 
								<input type="radio" value="false" id="optionsRadios2" name="isApproved">审批未通过
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">审批意见</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="content" name="content" value="">
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

	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>

	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 公共信息js -->	
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>
	<script>
	$(document).ready(function() {
		ApproveList.init(ctx,'/quickGrid/findPage', 'reminder_list','pager_list_1',caseCode,'16');
	});
		/**提交数据*/
		function submit() {
			save();
		}
	
		/**保存数据*/
		function save() {
			var jsonData = $("#lamform").serializeArray();
			
			var url = "${ctx}/eval/stop/approve/submit";
			
			$.ajax({
				cache : true,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
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
					if(data.success) {
						if(data) {
							window.wxc.success("操作成功",{"wxcOk":function(){
								window.close();
						 		//window.location.href = ctx + "/task/eval/evalTaskList";
						    }});
							//caseTaskCheck();
						} else {
							window.wxc.alert("操作失败。");
						}
					} else {
						window.wxc.alert("操作失败。");
					}
				},
				error : function(errors) {
					window.wxc.alert("操作失败。");
				}
			});
		}
	</script>
	<script>
	var ApproveList = (function() {
		return {
			init : function(ctx, url, gridTableId, gridPagerId,
					caseCode,approveType) {
				//jqGrid 初始化
				$("#" + gridTableId)
						.jqGrid(
								{
									url : ctx + url,
									mtype : 'GET',
									datatype : "json",
									height : 125,
									autowidth : true,
									shrinkToFit : true,
									rowNum : 3,
									/*   rowList: [10, 20, 30], */
									colNames : [ '审批人', '审批时间', '审批结果', '审批意见' ],
									colModel : [ {
										name : 'OPERATOR',
										index : 'OPERATOR',
										align : "center",
										width : 25	,
										resizable : false
									}, {
										name : 'OPERATOR_TIME',
										index : 'OPERATOR_TIME',
										align : "center",
										width : 25,
										resizable : false
									}, {
										name : 'NOT_APPROVE_OLD',
										index : 'NOT_APPROVE_OLD',
										align : "center",
										width : 25,
										resizable : false
									//formatter : linkhouseInfo
									}, {
										name : 'CONTENT',
										index : 'CONTENT',
										align : "center",
										width : 25,
										resizable : false
									} ],
									multiselect : true,
									pager : "#" + gridPagerId,
									//sortname:'UPLOAD_DATE',
									//sortorder:'desc',
									viewrecords : true,
									pagebuttions : true,
									multiselect : false,
									hidegrid : false,
									recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
									pgtext : " {0} 共 {1} 页",
									gridComplete : function() {
										var ids = jQuery("#" + gridTableId)
												.jqGrid('getDataIDs');
										for (var i = 0; i < ids.length; i++) {
											var id = ids[i];
											var rowDatas = jQuery(
													"#" + gridTableId).jqGrid(
													'getRowData', ids[i]); // 获取当前行

											var auditResult = rowDatas['NOT_APPROVE_OLD'];
											var auditResultDisplay = null;
											console.info(auditResult);
	                                        if (!auditResult || auditResult.length == 0) {
	                                            auditResultDisplay = "通过"
	                                            auditResult = rowDatas['CONTENT'];
	                                        } else {
	                                            auditResultDisplay = "不通过";
	                                            auditResult = rowDatas['NOT_APPROVE_OLD'];
	                                        }
											jQuery("#" + gridTableId)
													.jqGrid('setRowData',ids[i],{NOT_APPROVE_OLD: auditResultDisplay,CONTENT:auditResult});
										}
									},
									postData : {
										queryId : "queryLoanlostApproveList",
										caseCode : caseCode,
										approveType : approveType
									}

								});
			}
		};
	})();
	</script>
	</content>
</body>


</html>