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
</head>
<body class="pace-done">
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
									<label class="col-lg-3 col-md-3 control-label font_w">tagertCode</label>
									<div class="col-lg-9 col-md-9">
										<input type="text" class="form-control" id="targetCode"
											name="targetCode">
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
									<label class="col-lg-3 col-md-3 control-label font_w">导入编号</label>
									<div class="col-lg-9 col-md-9">
										<input type="text" class="form-control" id="expCtmCode"
											name="expCtmCode">
									</div>
								</div>
							</div>
							<div class="col-lg-4 col-md-4">
								<button class="btn btn-warning" id="exportCase">
									<span class="bold">导入</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /Main view -->
	</div>

	<!-- End page wrapper-->
	<!-- Mainly scripts -->
	<content tag="local_script"> <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
	<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

	<!-- Custom and plugin javascript --> <script
		src="${ctx}/js/inspinia.js"></script> <script
		src="${ctx}/js/plugins/pace/pace.min.js"></script> <script>
			$(document).ready(function() {
				$('#modifTagetCode').click(function() {
					modifTagetCode();
				});
				
				$('#exportCase').click(function() {
					exportCase();
				});
				
			});

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
						if(data.success){
							alert("修改成功!");
						}else{
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
						if(data.success){
							alert("修改成功!");
						}else{
							alert("修改失败!");
						}
					}
				});

			}
		</script> </content>
</body>
</html>