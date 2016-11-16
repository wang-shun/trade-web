<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>修改case info</title>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet" />
<!-- IonRangeSlider -->
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet" />
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<%-- <link href="${ctx}/css/style.min.css" rel="stylesheet">  --%>
<link href="${ctx}/css/transcss/award/bonus.css" rel="stylesheet" />
<!-- Gritter -->
<link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet" />
<!-- 分页控件 -->
        <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
        <!-- aist列表样式 -->
        <%-- <link href="${ctx}/css/common/aist.grid.css" rel="stylesheet"> --%>
</head>
<body class="pace-done">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper" class="Index">
		<!-- Main view -->
		<div class="main-bonus">
			<div class="bonus-wrap">
				<div class="bonus-header">
					<div class="ibox-content bonus-m-con">
						<div class="row m-t">
							<div class="col-lg-4 col-md-4">
								<div class="form-group">
									<label class="col-lg-3 col-md-3 control-label font_w">ctm编号</label>
									<div class="col-lg-9 col-md-9">
										<input type="text" class="form-control" id="ctmCode"
											name="ctmCode">
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4">
								<div class="form-group">
									<label class="col-lg-3 col-md-3 control-label font_w">目标组别</label>
									<div class="col-lg-9 col-md-9">
										<select class="form-control" name="targetCode" id="targetCode">
										</select>
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4">
								<button class="btn btn-warning" id="modifTagetCode">
									<span class="bold">修改</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="bonus-wrap">
				<div class="bonus-header">
					<div class="ibox-content bonus-m-con">
						<div class="row m-t">
							<div class="col-lg-4 col-md-4">
								<div class="form-group">
									<label class="col-lg-3 col-md-3 control-label font_w">CTM编号</label>
									<div class="col-lg-9 col-md-9">
										<input type="text" class="form-control" id="expCtmCode"
											name="expCtmCode">
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4">
								<button class="btn btn-warning" id="exportCase">
									<span class="bold">导入案件</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="bonus-wrap">
				<div class="bonus-header">
					<div class="ibox-content bonus-m-con">
						<div class="row m-t">
							<div class="col-lg-4 col-md-4">
								<div class="form-group">
									<label class="col-lg-3 col-md-3 control-label font_w">组织</label>
									<div class="col-md-9">
											<input type="text" class="form-control tbsporg"
												id="salesName" name="salesName" readonly="readonly"
												onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
											   startOrgId:'1D29BB468F504774ACE653B946A393EE', orgType:'',departmentType:'',departmentHeriarchy:'',
											   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
											   expandNodeId:'',chkType:'BUSIGRP,BUSIAR'})" />
											<input class="m-wrap " type="hidden" id="salesCode"
												name="salesCode">
									</div>
								</div>
							</div>

							<div class="col-lg-4 col-md-4">
								<div class="form-group">
									<label class="col-lg-3 col-md-3 control-label font_w">誉萃组别</label>
									<div class="col-lg-9 col-md-9">
										<select class="form-control" name="yuTeamCode" id="yuTeamCode">
										</select>
									</div>
								</div>
							</div>

							<div class="col-lg-4 col-md-4">
								<button class="btn btn-warning" id="addCaseMapping">
									<span class="bold">添加</span>
								</button>
								<button class="btn btn-warning" id="checkCaseMapping">
									<span class="bold">检查映射</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-md-12">
					<div class="col-md-12">
						<div class="row m-t" style="padding: 30px 0;">
							<div class="col-lg-4 col-md-4">
								<div class="form-group">
									<label class="col-lg-3 col-md-3 control-label font_w" style="margin-top: 7px;text-align: right;padding-right: 0;font-weight: normal;">ctm编号</label>
									<div class="col-lg-9 col-md-9">
										<input type="text" class="form-control" id="invalidCtmCode" name="invalidCtmCode" style="margin-left: 5px;">
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4">
								<button class="btn btn-warning" id="searchIvalidMapingCase" style="height: 35px;padding: 6px 22px;margin-left: 20px;">
									<span class="bold">查询没有对应关系案件</span>
								</button>
							</div>
							
						</div>
					</div>
					<div class="col-lg-4 col-md-4">
								<h5 class="col-lg-7 col-md-7">没有对应关系案件列表</h5>
							</div>
					<div class="bonus-table "></div>
				</div>
			</div>
		</div>
		<!-- /Main view -->
	</div>

	<!-- End page wrapper-->
	<!-- Mainly scripts -->
	<content tag="local_script">
	<!-- 分页控件  -->
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
       	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
       	  <script src="${ctx}/js/plugins/jquery.custom.js"></script>
	 
	<!-- 组织控件 --> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script
		id="noMappingCase" type="text/html">
    	{{each rows as item index}}
  			{{if index%2 == 0}}
 		    	<tr class="tr-1">
            {{else}}
            	<tr class="tr-2">
            {{/if}}
					<td class="center">{{item.CASE_CODE}} </td>
                    <td class="center">{{item.CTM_CODE}} </td>
					<td class="center">{{item.GRP_CODE}} </td>
					<td class="center">{{item.GRP_NAME}} </td>
					<td class="center">{{item.TARGET_CODE}} </td>
					<td class="center">{{item.TARGET_NAME}} </td>
				</tr>
       {{/each}}
	</script> <script src="${ctx}/js/inspinia.js" /> <script
		src="${ctx}/js/plugins/pace/pace.min.js"></script> <script>
			$(document).ready(function() {
				$('#modifTagetCode').click(function() {
					modifTagetCode();
				});

				$('#exportCase').click(function() {
					exportCase();
				});

				$('#addCaseMapping').click(function() {
					addCaseMapping();
				});

				$('#checkCaseMapping').click(function() {
					checkCaseMapping();
				});
				
				$('#searchIvalidMapingCase').click(function() {
					initData();
				});
				

				getAllTeamList();

				initData();
			});

			function initData() {
				var ctmCode = $("#invalidCtmCode").val();
				var params = {
					rows : 10,
					page : 1,
					ctmCode : ctmCode
				};
				$(".bonus-table")
						.aistGrid(
								{
									ctx : ctx,
									url : "/rapidQuery/findPage",
									queryId : 'noMappingCase',
									templeteId : 'noMappingCase',
									gridClass : 'table table_blue table-striped table-bordered table-hover',
									data : params,
									columns : [ {
										colName : "案件编号"
									}, {
										colName : "ctm编号"
									}, {
										colName : "店组编号"
									}, {
										colName : "店组名称"
									}, {
										colName : "目标店组编号"
									}, {
										colName : "目标店组名称"
									} ]
								});
			}

			function exportCase() {
				var ctmCode = $("#expCtmCode").val().trim();
				if (ctmCode.length == 0) {
					alert("ctm编号为空!");
					return;
				}
				if (!confirm("请确认是否是否要导入案件：" + ctmCode)) {
					return;
				}
				var data = {};
				data.ctmCode = ctmCode;
				$.ajax({
					async : false,
					url : ctx + "/caseModify/exportCTMCase.json",
					method : "post",
					dataType : "json",
					data : data,
					success : function(data) {
						if (data.success) {
							alert("修改成功!");
						} else {
							alert("修改失败!");
						}
					}
				});

			}

			function modifTagetCode() {
				var ctmCode = $("#ctmCode").val().trim();
				var targetCode = $("#targetCode").val().trim();
				if (ctmCode.length == 0) {
					alert("ctm编号为空!");
					return;
				}
				if (targetCode.length == 0) {
					alert("tagetCode为空!");
					return;
				}
				if (!confirm("请确认是否是否要修改案件：" + ctmCode)) {
					return;
				}
				var data = {};
				data.ctmCode = ctmCode;
				data.targetCode = targetCode;

				$.ajax({
					async : false,
					url : ctx + "/caseModify/updateTagetCode.json",
					method : "post",
					dataType : "json",
					data : data,
					success : function(data) {
						if (data.success) {
							alert("修改成功!");
						} else {
							alert("修改失败!");
						}
					}
				});
			}

			function getAllTeamList() {
				var url = "/case/getAllTeamList";
				$.ajax({
					cache : false,
					async : true,
					type : "POST",
					url : ctx + url,
					dataType : "json",
					timeout : 10000,
					success : function(data) {
						var targetCode = $("#targetCode");
						var yuTeamCode = $("#yuTeamCode");

						$("#targetCode option").remove();
						$("#yuTeamCode option").remove();

						targetCode.append("<option>请选择组别</option>");
						yuTeamCode.append("<option>请选择组别</option>");
						$.each(data, function(i, item) {
							targetCode
									.append("<option value='"+item.orgCode+"'>"
											+ item.orgName + "</option>");
							yuTeamCode
									.append("<option value='"+item.orgCode+"'>"
											+ item.orgName + "</option>");
						});
					}
				});
			}

			function radioYuCuiOrgSelectCallBack(array) {
				if (array && array.length > 0) {
					$("#salesName").val(array[0].name);
					$("#salesCode").val(array[0].id);
				} else {
					$("#salesName").val("");
					$("#salesCode").val("");
				}
			}

			function addCaseMapping() {
				var salesOrgId = $("#salesCode").val().trim();
				var yuTeamCode = $("#yuTeamCode").val().trim();
				if (salesOrgId.length == 0) {
					alert("组织为空!");
					return;
				}
				if (yuTeamCode.length == 0) {
					alert("誉萃组别为空!");
					return;
				}
				if (!confirm("请确认是否是否要添加配置?")) {
					return;
				}
				var data = {};
				data.salesOrgId = salesOrgId;
				data.yuTeamCode = yuTeamCode;

				$.ajax({
					async : false,
					url : ctx + "/caseModify/addCaseMapping.json",
					method : "post",
					dataType : "json",
					data : data,
					success : function(data) {
						if (data.success) {
							alert("修改成功!");
						} else {
							alert(data.message);
						}
					}
				});
			}

			function checkCaseMapping() {
				var salesOrgId = $("#salesCode").val().trim();
				if (salesOrgId.length == 0) {
					alert("组织为空!");
					return;
				}
				var data = {};
				data.salesOrgId = salesOrgId;

				$.ajax({
					async : false,
					url : ctx + "/caseModify/checkCaseMapping.json",
					method : "post",
					dataType : "json",
					data : data,
					success : function(data) {
						if (data.success) {
							alert("配置已经存在！");
						} else {
							alert("配置不存在！");
						}
					}
				});
			}
		</script> </content>
</body>
</html>