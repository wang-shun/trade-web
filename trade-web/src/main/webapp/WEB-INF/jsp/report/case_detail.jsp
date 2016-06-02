<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<style type="text/css">

<style type="text/css">
.radio label {
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
.ui-state-hover{
	cursor:pointer;
}
.org-label-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    width: 45%;
    font-size: 14px;
}
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
#inTextVal{width:50%}
.chosen-container{float:left;margin-right:10px;margin-left:15px}
#addLine{line-height:35px;}
.product-type span{margin-right:5px}	
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
[id^="dateDiv_"]:not(#dateDiv_0) .chosen-container{margin-left:33px}
.text-center{text-align:center;}
.slash{font-weight:bold !important;}
.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
.ml-15{margin-left:15px;}
.case-state{width:150px;}
.zuzhi{width:442px;}
.bianhao{width:221px}
.dizhi{width:430px}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>案件统计详情</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
					
						<!-- 组织范围 -->
						<div class="row">
							<div class="col-md-12">
								<div class="form-group ">
									<label class="col-md-1 control-label m-l">组织范围</label>
									<div class="col-md-10 zuzhi">
											<input type="text" class="span12 tbsporg org-label-control" id="teamCode" name="teamCode" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${depId}',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
										   expandNodeId:''})" value="${orgName}"/>
										 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId" value="${org}"/> 
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12">
								<div class="form-group ">
									<label class="col-md-1 control-label m-l">案件编号</label>
									<div class="col-md-10 bianhao">
											<input type="text" class="form-control" id="caseNo" name="caseNo" value=""/>
									</div>
								</div>
							</div>
						</div>
						
						
					<div class="row">
						<div class="col-md-12">
							<div class="form-group ">
								<label class="col-md-1 control-label m-l">案件地址</label>
								<div class="col-md-10 dizhi">
									<input type="text" class="form-control" id="caseAddr" name="caseAddr" value=""/>
								</div>
							</div>
						</div>
					</div>						
						
					<!--  	<div class="row date-info">
							<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l">时间范围</label>
							<div id="dateDiv_0">
							<div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left ml-15" data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="${createTimeStart}" placeholder="起始日期" /> 
								<span class="input-group-addon">到</span>
								<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="${createTimeEnd}" placeholder="结束日期" />
							</div>
						</div>
						</div>
							</div>
						</div>-->
						
					<div class="row date-info">
						<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l">请选择</label>
							<div id="dateDiv_0">
							<div id="select_div_0">
								<aist:dict id="case_date_0" name="case_date"
									clazz="btn btn-white chosen-select" display="select"
									defaultvalue="${statusVal}" dictType="caseTypeTime" />
							</div>
							<div id="datepicker_0"
								class="input-group input-medium date-picker input-daterange pull-left"
								data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="${createTimeStart}" placeholder="起始日期">
								<span class="input-group-addon">到</span>
								<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="${createTimeEnd}" placeholder="结束日期" />
							</div>
							<div id="addLine" class="pull-left m-l">
							
						</div>
						</div>
						</div>
							</div>
						</div>	
						
						
						<div class="row m-t-sm">
							<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l-lg"></label>
							<div><a class="" href="javascript:addDateDiv();"><font>添加日期检索</font></a></div>
						</div>
							</div>
						</div>		
				
						
						<div class="row m-t-sm">
							<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l-lg"></label>
							<div><button id="searchButton" type="button" class="btn btn-warning">查询</button></div>
						</div>
							</div>
						</div>
						
					</form>
				</div>
			</div>
		</div>
		
		<div class="data-wrap">
		<div class="data-wrap-in">
			<table border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th class="t-left pd-l"><span class='sort' sortColumn='a.CASE_CODE' sord='desc'>案件编号</span></th>
						<th class="t-left pd-l">产证地址</th>
						<th class="t-left pd-l"><span id="typeTime1" class='sort' sortColumn='a.RECEIVED_TIME' sord='desc'>接单时间</span></th>
						<th class="t-left pd-l"><span id="typeTime2" class='sort' sortColumn='a.SIGN_TIME' sord='desc'>签约时间</span></th>
						<th class="t-left pd-l"><span id="typeTime3" class='sort' sortColumn='a.HOUSE_TRANFER_TIME' sord='desc'>过户时间</span></th>
						<th class="t-left pd-l"><span id="typeTime4" class='sort' sortColumn='a.CLOSE_TIME' sord='desc'>结案时间</span></th>
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
		</div>
	</div>
	
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="createTimeStart" value="${createTimeStart}" />
	<input type="hidden" id="createTimeEnd" value="${createTimeEnd}" />
	<input type="hidden" id="org" value="${org}" />
	<input type="hidden" id="status" value="${status}" />
	<input type="hidden" id="userId" value="${userId}" />
	<input type="hidden" id="tempUser" value="${tempUser}" />
	
	<content tag="local_script"> 
    <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
    <script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<script src="${ctx}/js/trunk/report/case_detail.js"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
	<!-- 排序插件 -->
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<!-- 分页控件  -->
    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script id="template_myCaseList" type= "text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="t-left pd-l"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
						<td class="t-left pd-l"><span class="case-addr">{{item.PROPERTY_ADDR}}</span></td>
						<td class="t-left pd-l">{{item.RECEIVED_TIME}}</td>
						<td class="t-left pd-l">{{item.SIGN_TIME}}</td>
						<td class="t-left pd-l">{{item.HOUSE_TRANFER_TIME}}</td>
						<td class="t-left pd-l">{{item.CLOSE_TIME}}</td>
				  </tr>

				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="t-left pd-l"><span class="ctm-tag">C</span><span class="case-ctm">{{item.CTM_CODE}}</span></td>
						<td class="t-left pd-l">
							<i class="salesman-icon"></i>
							<span class="salesman-info">
								{{item.AGENT_NAME}}<span class="slash">/</span>{{item.AGENT_TEL}}<span class="slash">/</span>{{item.AGENT_ORG}}
							</span>
						</td>
						<td colspan="4" class="t-left pd-l">
							<span class="jbr">经办人：{{item.PROCESSOR}}</span>
							<span class="slash"> </span>{{item.TEAM_ID}}
						</td>
					</tr>
       {{/each}}
     </script>
     <script></script>
	 </content>	
</body>
</html>
