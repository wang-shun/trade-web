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
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">

<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link
	href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"
	rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
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
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	 
	 <div class="row">
		

		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>案件筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
							<div class="col-md-7 col-sm-12">
								<div class="form-group ">
									<label class="col-md-2  col-sm-4 control-label">案件类型</label>
									<div class="col-md-8">
										<aist:dict id="caseProperty" name="case_property" display="select" dictType="30003" clazz="form-control" /> 
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-12">
								<div class="form-group">
									<label class="col-md-3  col-sm-4 control-label">服务阶段</label>
									<div class="col-md-8">
									    <aist:dict id="status" name="case_status" display="select" dictType="30001" clazz="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row product-type">
							<div class="col-md-12">
								<div class="form-group ">
									<label class="col-md-1 control-label m-l">产品类型</label>
									<div class="col-md-10">
										<aist:dict id="srvCode" name="srvCode" clazz="btn btn-white" display="checkboxcustom" dictType="yu_serv_cat_code_tree" level='2' onclick=""/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group ">
									<label class="col-md-1 control-label m-l">案件组织</label>
									<div class="col-md-10">
											<input type="text" class="span12 tbsporg org-label-control" id="teamCode" name="teamCode" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'ff8080814f459a78014f45a73d820006', orgType:'',departmentType:'',departmentHeriarchy:'yucui_headquarter',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
										   expandNodeId:''})" />
										 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId"> 
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l">请选择</label>
							<div id="select_div_1" class="">
								<select id="inTextType" data-placeholder="搜索条件设定"
									class="btn btn-white chosen-select form-control pull-left col-md-6" onchange="intextTypeChange()">
									<option value="1" selected>物业地址</option>
									<option value="0">客户姓名</option>
									<option value="2">经纪人姓名</option>
									<option value="4">经办人姓名</option>
									<option value="3">所属分行</option>
									<option value="5">案件编号</option>
									<option value="6">CTM编号</option>
								</select>
								<input id="inTextVal" type="text" class="form-control pull-left">
							</div>
						</div>
							</div>
						</div>
						<div class="row date-info">
							<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l">请选择</label>
							<div id="dateDiv_0">
							<div id="select_div_0">
								<aist:dict id="case_date_0" name="case_date"
									clazz="btn btn-white chosen-select" display="select"
									defaultvalue="30005001" dictType="30005" />
							</div>
							<div id="datepicker_0"
								class="input-group input-medium date-picker input-daterange pull-left"
								data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control"
									style="font-size: 13px;" type="text" value=""
									placeholder="起始日期"> <span class="input-group-addon">到</span>
								<input id="dtEnd_0" name="dtEnd" class="form-control"
									style="font-size: 13px;" type="text" value=""
									placeholder="结束日期" />
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
			<!-- <div class="col-lg-12 col-md-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>我的案件列表</h5>
					</div>

					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
							
                            <shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
							<a data-toggle="modal" class="btn btn-primary"
								href="javascript:void(0)" onclick="javascript:showExcelIn()">案件导出</a>
								</shiro:hasPermission>
						</div>
					</div>
				</div>
			</div> -->
			
			<div class="data-wrap">
		<div class="data-wrap-in">
			<table border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th class="t-left pd-l">案件编号</th>
						<th class="t-left pd-l">产证地址</th>
						<th class="t-left pd-l">上家</th>
						<th class="t-left pd-l">下家</th>
						<th class="text-center">红灯数</th>
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

    						 <shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
							<a data-toggle="modal" class="btn btn-primary"
								href="javascript:void(0)" onclick="javascript:showExcelIn()">案件导出</a>
								</shiro:hasPermission>
		</div>

		<div id="modal-form" class="modal fade" aria-hidden="true">
				<div class="modal-dialog" style="width: 1200px">
					<div class="modal-content ">
					    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="leading-modal-title">
						      请选择导出项
						   </h4>
						</div>
						<div class="modal-body ">
							<form  class="form-horizontal" >
						
								<div class="form-group">
									<label class="col-sm-2 control-label">案件基本信息</label>
									<div class="col-sm-9 checkbox i-checks checkbox-inline">
										<aist:dict id="basic_info_item" name="basic_info_item"
											display="checkbox" clazz="excel_in" defaultvalue="" dictType="30010" />
									</div>
								</div>

								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<label class="col-sm-2 control-label">贷款服务信息</label>
									<div class="col-sm-9 checkbox i-checks checkbox-inline">
										<aist:dict id="mortage_info_item" name="mortage_info_item"
											display="checkbox" clazz="excel_in" defaultvalue="30010006,30010007"
											dictType="30011" />
									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<label class="col-sm-2 control-label">交易服务信息</label>
									<div class="checkbox i-checks checkbox-inline">
										<aist:dict id="trade_info_item" name="trade_info_item"
											display="checkbox" clazz="excel_in" defaultvalue="" dictType="30012" />
									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<label class="col-sm-2 control-label">资金监管信息</label>
									<div class="checkbox i-checks checkbox-inline">
										<aist:dict id="fund_info_item" name="fund_info_item"
											display="checkbox" clazz="excel_in" defaultvalue="" dictType="30013" />
									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<a id="checkAll" data-toggle="modal" class="btn btn-success"
									href="javascript:void(0)" onclick="javascript:checkAllItem(true)">全选</a> 
									<a id="unCheckAll" data-toggle="modal" class="btn btn-success"
									href="javascript:void(0)" onclick="javascript:unCheckAllItem(true)">全不选</a><a
									data-toggle="modal" class="btn btn-primary"
									href="javascript:void(0)" onclick="javascript:exportToExcel()">导出至Excel</a>
									</form>
						</div>
						<div class="modal-footer">
				            <button id="checkAll" type="button" class="btn btn-default"
				               onclick="javascript:checkAllItem()">全选
				            </button>
				            <button id="unCheckAll" type="button" class="btn btn-default"
				               onclick="javascript:unCheckAllItem()">全不选
				            </button>
				            <button type="button" class="btn btn-default"
				               data-dismiss="modal">关闭
				            </button>
				            <button type="button" class="btn btn-primary" onclick="javascript:exportToExcel()">
				                                导出至Excel
				            </button>
				         </div>
					</div>
				</div>
		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	<content tag="local_script"> 
    <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		 <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	 <script src="${ctx}/js/trunk/case/mycase_list.js?v=1.0"></script>
	 <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	 <script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
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
						<td class="t-left pd-l"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a><span class="fd">{{item.STATUS}}</span></td>
						<td class="t-left pd-l"><span class="case-addr">{{item.PROPERTY_ADDR}}</span></td>
						<td class="t-left pd-l">{{item.SELLER}}</td>
						<td class="t-left pd-l">{{item.BUYER}}</td>
						<td>
                             {{if item.RED_COUNT!=null}}
                                <span class="red-num">{{item.RED_COUNT}}</span>
							 {{/if}}
                        </td>
				  </tr>

				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="t-left pd-l"><span class="ctm-tag">C</span><span class="case-ctm">{{item.ctmCode}}</span></td>
						<td class="t-left pd-l"><i class="salesman-icon"></i><span class="salesman-info">{{item.AGENT_NAME}}<span class="slash">/</span>{{item.AGENT_ORG_NAME}}</span></td>
						<td colspan="2" class="t-left pd-l"><span class="jbr">经办人：{{item.PROCESSOR_ID}}</span></td>
						<td></td>
					</tr>
       {{/each}}
     </script>
     <script></script>

	 </content>
</body>
</html>
