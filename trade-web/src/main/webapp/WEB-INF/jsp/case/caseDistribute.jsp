<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <style type="text/css">
    .ibox-button {
		margin-top: 10px;
	}
	.userHead{
	width: 80px;
		  height: 80px;
		  display: inline-block;
		  border-radius: 50%;
		  background-size: 80px 108px;
		  vertical-align: middle;
		  background-image:url(../img/a5.png);
	
}
	
    </style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="queryUserId" value="${queryUserId}"/>
<input type="hidden" id="queryOrgId" value="${queryOrgId}"/>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>我的案件列表</h5>
				</div>

				<div class="ibox-content">
					<div class="jqGrid_wrapper">
						<table id="table_list_1"></table>
						<div id="pager_list_1"></div>
						
			
				
					</div><div class="ibox-button text-center"><a class="btn btn-primary" href="javascript:caseDistribute()"
							disabled="true" id="caseDistributeButton">案件分配</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-primary" href="javascript:caseChangeTeam()"
							disabled="true" id="caseChangeTeamButton">案件转组</a></div>
				</div>
			</div>
		</div>
	</div>
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div id="modal-form" class="modal fade" aria-labelledby="modal-title"
			aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="modal-title">请选择交易顾问</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="height: 450px;overflow:auto; ">
							<div class="col-lg-12 ">
								<h3 class="m-t-none m-b"></h3>
								<div class="wrapper wrapper-content animated fadeInRight">
									<div id="modal-data-show" class="row"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 <!-- 案件转组 -->
            <div id="team-modal-form" class="modal fade" role="dialog" aria-labelledby="team-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:700px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="team-modal-title">
						      案件转组
						   </h4>
					   </div>
                       <div class="modal-body">
                       <div class="row">
                       <form  id="team-form">
		                       <div class="form-group">
		                            <label class="col-lg-2 control-label">请选择组别:</label>
		                            <div class="col-lg-8" id="fontTeam">
										
									</div>
		                       </div>
			            </form>
			            </div>
                     </div> 
                     <div class="modal-footer">
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="button" class="btn btn-primary" onclick="javascript:changeCaseTeam()">
			                                提交
			            </button>
                     </div>
                     </div>
                 </div>
             </div>  
	<content tag="local_script">


    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>

	<script src="${ctx}/js/trunk/case/caseDistribute.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
	<script id="yuCuiFontTeamList" type="text/html">
		 <select class="form-control" name="yuTeamCode">
                {{each data as item}}
                      <option value ="{{item.id}}">{{item.orgName}}</option>
                {{/each}}
		</select>
	</script>	



</content>
</body>
</html>
