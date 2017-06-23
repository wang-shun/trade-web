<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css'/>" rel="stylesheet">
<link href="<c:url value='/css/style.css'/>" rel="stylesheet">

<link href="<c:url value='/css/plugins/chosen/chosen.css'/>" rel="stylesheet">

<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}
.select>div {
	margin-left: 15px;
}
.select>input {
	margin-left: 20px;
}
.modal-text{
margin-left: 15px;
}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>前后台组别关系配置 </h5>
					</div>						
			
					<div class="ibox-content">
					    
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	 <!-- 组别配置 -->
	<div id="modal-form" class="modal fade" role="dialog"
		aria-labelledby="modal-title" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="modal-title">前后台组别关系配置</h4>
				</div>
				<form method="post" id="editForm" class="form-horizontal" action="saveEvalItem">
				<div id="modal-data-show" class="modal-body">
					<input type="hidden" id="pkId" value=""/>
						<div id="teamDiv" class="form-group ">
						</div>
						<div id="teamBackDiv" class="form-group">
						</div>
						<div class="form-group ">
							<label class="col-sm-2 control-label">是否可用</label>
							<div class="radio i-radios radio-inline">
								<label> <input type="radio" value="1" id="isAvailable"
									name="available"> 是
								</label> <label> <input type="radio" value="0" id="notAvailable"
									name="available"> 否
								</label>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:saveTeamOrgRelation()">保存</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
<content tag="local_script"> 
	<script	src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
    <script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script	src="<c:url value='/js/trunk/system/teamOrgRelation_list.js' />"></script> 
 </content>
</body>
</html>
