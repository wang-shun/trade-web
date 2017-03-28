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
#partCode_chosen {
  width : 415px!important;
}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="row">
		<!-- <div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>任务项天数配置</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="form-group ">
							<label class="col-sm-2 control-label">环节编码</label>
							<div class="input-group-btn">
								<aist:dict id="partCode" name="partCode"
									clazz="btn btn-white chosen-select part-style" display="select" defaultvalue="FirstFollow" dictType="part_code" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div> -->


		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>任务项配置列表 </h5>
				</div>						
		
				<div class="ibox-content">
				    
					<div class="jqGrid_wrapper">
						<table id="table_list_1"></table>
						<div id="pager_list_1"></div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>红灯规则配置列表 </h5>
				</div>						
		
				<div class="ibox-content">
				    
					<div class="jqGrid_wrapper">
						<table id="table_list_2"></table>
						<div id="pager_list_2"></div>
					</div>
				</div>
			</div>
		</div>


		
		
	</div>
	 <!-- 提醒项配置 -->
	<div id="modal-form" class="modal fade" role="dialog"
		aria-labelledby="modal-title" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="modal-title">任务项配置</h4>
				</div>
				<form method="post" id="editForm" class="form-horizontal">
				<div id="modal-data-show" class="modal-body">
					<input type="hidden" id="pkid" name="pkid" value=""/>
					<div class="form-group ">
						<label class="col-sm-3 control-label"><font color='red'>*</font>环节编码:</label>
						<div class="col-sm-8">
							<!-- <div class="input-group-btn"> -->
								<aist:dict id="partCode" name="partCode"
									clazz="chosen-select" display="select" defaultvalue="FirstFollow" dictType="part_code" />
							<!-- </div> -->
						</div>
					</div>
					<div class="form-group">
					    <label class="col-sm-3 control-label"><font color='red'>*</font>计划天数:</label>
					    <div class="col-sm-8">
					        <input id="planDays" name="planDays" type="number" class="form-control" value="1" size="1"/>
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-sm-3 control-label"><font color='red'>*</font>人工设置:</label>
					    <div class="col-sm-8">
					       <select id="isManualSet" name="isManualSet" class="form-control">
					       		<option value="0">否</option>
					       		<option value="1">是</option>
					       </select>
					    </div>
					</div>
				</div>
				</form>
				<div class="modal-footer">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:saveTaskPlanSet()">保存</button>
				</div>
			</div>
		</div>
	</div>
	 <!-- 红灯提醒项配置 -->
	<div id="modal-form-lampRule" class="modal fade" role="dialog"
		aria-labelledby="modal-title" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="modal-title">亮灯规则配置</h4>
				</div>
				<form method="post" id="editLampRuleForm" class="form-horizontal">
				<div id="modal-data-show" class="modal-body">
					<div class="form-group">
					    <label class="col-sm-3 control-label"><font color='red'>*</font>提醒灯:</label>
					    <div class="col-sm-8">
					       <select id="color" name="color" class="form-control">
					       		<option value="0">红灯</option>
					       		<option value="1">黄灯</option>
					       		<option value="2">绿灯</option>
					       </select>
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-sm-3 control-label"><font color='red'>*</font>延迟天数:</label>
					    <div class="col-sm-8">
					         <input id="delaytime" name="delaytime" type="number" class="form-control" value="1" size="1"/>
					    </div>
					</div>
				</div>
				</form>
				<div class="modal-footer">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:saveLampRule()">保存</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
<content tag="local_script"> 
	<script	src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script	src="${ctx}/js/trunk/system/taskPlanSet_list.js"></script> 
 </content>
</body>
</html>
