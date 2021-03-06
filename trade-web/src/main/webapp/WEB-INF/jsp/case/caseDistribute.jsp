<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
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
	<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
	<link href="<c:url value='/css/style.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
	<link href="<c:url value='/css/transcss/case/myCaseList.css' />" rel="stylesheet">
    <!-- 分页控件 -->
	<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
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
	
	.bianhao{width:221px;padding-left:0;}
	.dizhi{width:542px}
	
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
					<h5>我的待分配案件列表</h5>
				</div>

				<div class="ibox-content">
					<form method="get" class="form-horizontal">
					
					<div class="row">
						<div class="col-md-12">
							<div class="form-group ">
								<label class="col-md-1 control-label m-l">案件编号</label>
								<div class="col-md-10">
									<div class="col-md-2 bianhao">
										<input type="text" class="form-control" id="caseNo" name="caseNo" value=""/>
									</div>
								<label class="col-md-1 control-label">CTM编号</label>
								<div class="col-md-2 bianhao">
									<input type="text" class="form-control" id="ctmNo" name="ctmNo" value=""/>
								</div>
								</div>
							</div>
						</div>
					</div>
						
					<div class="row">
						<div class="col-md-12">
							<div class="form-group ">
								<label class="col-md-1 control-label m-l">产证地址</label>
								<div class="col-md-10 dizhi">
									<input type="text" class="form-control" id="caseAddr" name="caseAddr" value=""/>
								</div>
							</div>
						</div>
					</div>						
						
					<div class="row m-t-sm">
						<div class="col-md-12">
							<div class="form-group">
							<label class="col-md-1 control-label m-l-lg"></label>
							<div>
								<button id="searchButton" type="button" class="btn btn-warning" ">查询</button>
							</div>
							</div>
						</div>
					</div>
						
					</form>

					<div class="data-wrap">
						<div class="data-wrap-in">
							<table border="0" cellpadding="0" cellspacing="0">
							<thead>
							<tr>
								<th class="t-left pd-l"><input type="checkbox" id="checkAllNot" class="cbox"/></th>
								<th class="t-left pd-l">案件编号</th>
								<th class="t-left pd-l">产证地址</th>
								<th class="t-left pd-l">区经/区总</th>
								<th class="t-left pd-l">案件状态</th>
								<th class="t-left pd-l">派单时间</th>
							</tr>
							</thead>
							<tbody id="myCaseList">
					
							</tbody>							
							</table>
						</div>				
					</div>
					
					<div class="text-center">
						<span id="currentTotalPage"><strong class="bold"></strong></span>
						<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
						<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
    				</div>	
					
					<div class="ibox-button text-center">
						<a class="btn btn-primary" href="javascript:caseDistribute()" disabled="true" id="caseDistributeButton">案件分配</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="btn btn-primary" href="javascript:caseChangeTeam()" disabled="true" id="caseChangeTeamButton">案件转组</a>
					</div>
					
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

    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
    <script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
	<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
	<!-- 排序插件 -->
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	<!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>

    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
    <script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>

	<script src="<c:url value='/js/trunk/case/caseDistribute.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script id="yuCuiFontTeamList" type="text/html">
		 <select class="form-control" name="yuTeamCode">
                {{each data as item}}
                      <option value ="{{item.id}}">{{item.orgName}}</option>
                {{/each}}
		</select>
	</script>	

	<script id="template_myCaseList" type= "text/html">
    	{{each rows as item index}}
  			{{if index%2 == 0}}
 		    	<tr class="tr-1">
            {{else}}
            	<tr class="tr-2">
            {{/if}}
				<td>
					<input type="checkbox" name="my_checkbox" class="cbox" onclick="_checkbox()" /> 
					<input type="hidden" name="case_code" value="{{item.CASE_CODE}}"/>
					<input type="hidden" name="yu_team_code" value="{{item.YU_TEAM_CODE}}"/>
					<input type="hidden" name="leading_process_id" value="{{item.LEADING_PROCESS_ID}}"/>

				</td>
				<td class="t-left pd-l">{{item.CASE_CODE}}</td>
				<td class="t-left pd-l"><span class="case-addr">{{item.PROPERTY_ADDR}}</span></td>
				<td class="t-left pd-l">{{item.LEADER}}</td>
				<td class="t-left pd-l">{{item.STATUS}}</td>
				<td class="t-left pd-l">{{item.CREATE_TIME}}</td>
				  </tr>

			{{if index%2 == 0}}
 				<tr class="tr-1">
            {{else}}
                <tr class="tr-2">
            {{/if}}
				<td></td>
				<td class="t-left pd-l"><span class="ctm-tag">C</span><span class="case-ctm">{{item.CTM_CODE}}</span></td>
				<td class="t-left pd-l">
				<i class="salesman-icon"></i>
					<span class="salesman-info">
						{{item.AGENT_NAME}}<span class="slash">/</span>{{item.ORG_NAME}}
					</span>
				</td>
				<td colspan="3" class="t-left pd-l">
				</td>
				</tr>
       {{/each}}
	</script>
</content>
</body>
</html>
