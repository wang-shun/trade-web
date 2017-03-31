<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">

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
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>誉萃组别配置</h5>
				</div>
				<div class="ibox-content">
					<form id="searchForm" method="get" class="form-horizontal">
						<div class="form-group ">
							<label class="col-sm-2 control-label">组别编码</label>
							
							<div class="col-sm-5">
							<input id="search_yuTeamCode" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group ">
							<label class="col-sm-2 control-label">组别名称</label>
							
							<div class="col-sm-5">
							<input id="search_orgName" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group ">
							<label class="col-sm-2 control-label">主组别</label>
							<div class="radio i-radios radio-inline">
								<label> <input type="radio" value="-1" id="search_isResponseTeam0"
									name="search_isResponseTeam" selected> 全部
								</label> <label> <input type="radio" value="1" id="search_isResponseTeam1"
									name="search_isResponseTeam"> 是
								</label> <label> <input type="radio" value="0" id="search_lisResponseTeam2"
									name="search_isResponseTeam"> 否
								</label>
							</div>
						</div>
						<div class="form-group ">
							<label class="col-sm-2 control-label">自行选择</label>
							<div class="radio i-radios radio-inline">
								<label> <input type="radio" value="-1" id="search_freeSelect0"
									name="search_freeSelect" selected> 全部
								</label> <label> <input type="radio" value="1" id="search_freeSelect1"
									name="search_freeSelect"> 是
								</label> <label> <input type="radio" value="0" id="search_freeSelect2"
									name="search_freeSelect"> 否
								</label>
							</div>
						</div>
						<div class="form-group ">
							<label class="col-sm-2 control-label">组别类型</label>
							<div class="select">
								<aist:dict id="search_teamProperty" name="search_teamProperty"
									clazz="btn btn-white chosen-select" display="select" defaultvalue="" dictType="yucui_team_cat" />
							</div>
						</div>
						<span class="input-group-btn ">
							<button id="searchButton" type="button"
								class="btn btn-primary pull-right">查询</button>
						</span>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>组别配置列表 </h5>
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
					<h4 class="modal-title" id="modal-title">组别配置</h4>
				</div>
				<form method="post" id="editForm" class="form-horizontal" action="saveEvalItem">
				<div id="modal-data-show" class="modal-body">
					<input type="hidden" id="pkId" value=""/>
					
						<div id="teamDiv" class="form-group ">
							
						</div>
						
						<div class="form-group ">
							<label class="col-sm-2 control-label">主组别</label>
							<div class="radio i-radios radio-inline">
								<label> <input type="radio" value="1" id="isResponseTeam1"
									name="isResponseTeam"> 是
								</label> <label> <input type="radio" value="0" id="lisResponseTeam2"
									name="isResponseTeam"> 否
								</label>
							</div>
						</div>
						<div class="form-group ">
							<label class="col-sm-2 control-label">自行选择</label>
							<div class="radio i-radios radio-inline">
								<label> <input type="radio" value="1" id="freeSelect1"
									name="freeSelect"> 是
								</label> <label> <input type="radio" value="0" id="freeSelect2"
									name="freeSelect"> 否
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">组别类型</label>
							<div class="col-sm-4 select" >
								<aist:dict id="teamProperty" name="teamProperty"
									clazz="btn btn-white chosen-select" display="select" defaultvalue="yu_all" dictType="yucui_team_cat" />
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:saveTeamPropertyItem()">保存</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="saveMsg" value="${saveMsg}" />
<content tag="local_script"> 
	<script	src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script	src="${ctx}/js/trunk/system/teamProperty_list.js"></script> 
 </content>
</body>
</html>
