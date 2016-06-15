<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />

<link href="${ctx}/css/transcss/loan/loanAgentList.css" rel="stylesheet" />

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
.chosen-container{float:left;margin-right:10px;margin-left:25px}
#addLine{line-height:35px;}
.product-type span{margin-right:5px}	
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
/* [id^="dateDiv_"]:not(#dateDiv_0) .chosen-container{margin-left:48px} */
#loanSrvCode .chosen-container{margin-left:15px}
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
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>金融产品筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
						   <div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-2 control-label m-l">人员</label>
									<div class="col-md-8"> 
										 <input type="text" id="realName" name="realName" class="form-control" value="${userInfo}"> 
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-2 control-label m-l">案件组织</label>
									<div class="col-md-8">
							<!-- 				<input type="text" class="span12 tbsporg org-label-control" id="orgName" name="orgName" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
										   expandNodeId:''})" />
										 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">  -->
										  <input type="text" class="form-control tbsporg" id="orgName" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})" value='${serOrgName }'>
                                          <input type="hidden" id="yuCuiOriGrpId" value="${serOrgId==null?serviceDepId:serOrgId}">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-2 control-label m-l">案件编号</label>
									<div class="col-md-8"> 	
										<input id="caseCode" name="caseCode" type="text" class="form-control pull-left">
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label m-l">产证地址</label>
									<div class="col-md-8">
									    <input id="propertyAddr" name="propertyAddr" type="text" class="form-control pull-left">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-2 control-label m-l">金融产品类型</label>
									<div class="col-md-8"> 	
										<aist:dict id="loanSrvCode" name="loanSrvCode"
											clazz="btn btn-white " display="select"
											defaultvalue="" dictType="yu_serv_cat_code_tree" tag="eplus"/>
									</div>
								</div>
							</div>
							<div class="col-md-6"  style="padding-left: 5.8% ">
									<div class="row date-info">
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-md-1 control-label"></label>
											<div id="dateDiv_0">
													<div id="select_div_0">
														<aist:dict id="case_date_0" name="case_date"
															clazz="btn btn-white chosen-select" display="select"
															defaultvalue="${isLoanAgentTimeType}" dictType="loan_agent_time_type" />
													</div>
													<div id="datepicker_0" 
														class="input-group input-medium date-picker input-daterange "
														data-date-format="yyyy-mm-dd">
														<input id="dtBegin_0" name="dtBegin" class="form-control"
															style="font-size: 13px;" type="text" value="${startTime}"
															placeholder="起始日期"> <span class="input-group-addon">到</span>
														<input id="dtEnd_0" name="dtEnd" class="form-control"
															style="font-size: 13px;" type="text" value="${endTime}"
															placeholder="结束日期" />
													</div>
													<div id="addLine" class="pull-left m-l"></div>
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
							</div>
						</div>
					
						<div class="row m-t-sm">
							<div class="col-md-6"></div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-4 control-label m-l-lg"></label>
									<div><button id="searchButton" type="button" class="btn btn-warning">查询</button></div>
								</div>
							</div>
							<div class="col-md-3"  style="text-align:right;">
								<a class="btn btn-primary" href="javascript:void(0)" onclick="javascript:exportToExcel()">金融产品导出</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<!-- <div class="data-wrap">
			<div class="data-wrap-in">
				<table border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th class="t-left pd-l">案件编号</th>
							<th class="t-left pd-l">产证地址</th>
							<th class="t-left pd-l">经办人</th>
							<th class="t-left pd-l">产品类型</th>
							<th class="t-left pd-l">合同价</th>
							<th class="t-left pd-l">申请时间</th>
							<th class="text-center">申请金额</th>
							<th class="t-left pd-l">面签时间</th>
							<th class="text-center">面签金额</th>
							<th class="t-left pd-l">放款时间</th>
							<th class="text-center">放款金额</th>
						</tr>
					</thead>
					<tbody id="loanAgentList">
						
					</tbody>
				</table>
			 </div>
		</div>  -->
		<div class="gcxz-wrap">
		<div class="gcxz-wrap-in">
			<table border="0" cellpadding="0" cellspacing="0" vertical="middle">
				<thead>
					<tr>
						<th><span class="sort" sortColumn="CASE_CODE" sord="desc">案件编号</span></th>
						<th>产证地址</th>
						<th><span class="sort" sortColumn="LOAN_AMOUNT" sord="desc">申请金额</span></th>
						<th><span class="sort" sortColumn="SIGN_AMOUNT" sord="desc">面签金额</span></th>
						<th class="last"><span class="sort" sortColumn="ACTUAL_AMOUNT" sord="desc">放款金额</span></th>
					</tr>
				</thead>
				<tbody id="loanAgentList">
						
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

	<input type="hidden" id="ctx" value="${ctx}" />
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
	 <script src="${ctx}/js/trunk/loan/loan_agent_list.js?v=1.2"></script>
	 <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	 <script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
	  <!-- 分页控件  -->
     <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	 <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	 <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	 <script id="template_loanAgentList" type= "text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td width="155"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
						<td><span class="cz-addr">{{item.PROPERTY_ADDR}}</span></td>
						<td class="money">{{item.LOAN_AMOUNT}}万</td>
						<td class="money">{{item.SIGN_AMOUNT}}万</td>
						<td class="money last">{{item.ACTUAL_AMOUNT}}万</td>
				  </tr>

				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="bg-12" width="155"><span class="sfd-tag">{{item.LOAN_SRV_CODE}}</span></td>
						<td><span class="ht-money">合同价：<i>{{item.CON_PRICE}}万</i></span><span class="jbr">经办人：{{item.EXECUTOR_ID}}</span></td>
						<td><span class="date">{{item.APPLY_TIME}}</span></td>
						<td><span class="date">{{item.SIGN_TIME}}</span></td>
						<td class="last"><span class="date">{{item.RELEASE_TIME}}</span></td>
                    </tr>
       {{/each}}
     </script>
     <script>
	     var argu_applyTime = "${argu_applyTime}";
	     var argu_signTime = "${argu_signTime}";
	     var argu_releaseTime = "${argu_releaseTime}";
	     var argu_yuCuiOriGrpId = "${orgId}";
	     
	 	 var orgName = "${sUserName}";
	 	 
	 	 aist.sortWrapper({
			reloadGrid : searchMethod
		 });
	 	 
	 	function exportToExcel() {
	 		var params = {};
	 		params.search_caseCode = $('#caseCode').val();
	 		params.search_propertyAddr = $('#propertyAddr').val();
	 		params.search_loanSrvCode = $('#loanSrvCode').val();
	 		// 日期查询
	 		var applyTimeStart = null, applyTimeEnd = null, signTimeStart = null, signTimeEnd = null, releaseTimeStart = null,releaseTimeEnd = null;
	 		for (var r = 0; r < divIndex; r++) {
	 			var val = $('#case_date_' + r + ' option:selected').val();
	 			if (val == undefined)
	 				continue;
	 			var start = $('#dtBegin_' + r).val();
	 			var end = $('#dtEnd_' + r).val();
	 			if(end&&end!=''){
	 				end=end+' 23:59:59';
	 			}
	 			if(start&&start!=''){
	 				start=start;
	 			}
	 			if (start != "") {
	 				if (val == '1') {
	 					applyTimeStart = start;
	 				} else if (val == '2') {
	 					signTimeStart = start;
	 				}else if (val == '3') {
	 					releaseTimeStart = start;
	 				}
	 			}
	 			if (end != "") {
	 				if (val == '1') {
	 					applyTimeEnd = end;
	 				} else if (val == '2') {
	 					signTimeEnd = end;
	 				}else if (val == '3') {
	 					releaseTimeEnd = end;
	 				}
	 			}
	 		}
	 		params.search_applyTimeStart = applyTimeStart;
	 		params.search_applyTimeEnd = applyTimeEnd;
	 		params.search_signTimeStart = signTimeStart;
	 		params.search_signTimeEnd = signTimeEnd;
	 		params.search_releaseTimeStart = releaseTimeStart;
	 		params.search_releaseTimeEnd = releaseTimeEnd;
	 		params.search_realName = 	$("#realName").val();
	 		params.argu_yuCuiOriGrpId = 	$("#yuCuiOriGrpId").val();
	 		
        	aist.exportExcel({
    	    	ctx : "${ctx}",
    	    	queryId : 'loanAgentListQuery',
    	    	colomns : ['CASE_CODE','PROPERTY_ADDR','LOAN_SRV_CODE','EXECUTOR_ID','PARENT_ORG_NAME','CON_PRICE','APPLY_TIME','SIGN_TIME','RELEASE_TIME','LOAN_AMOUNT','SIGN_AMOUNT','ACTUAL_AMOUNT'],
    	    	data : params
    	    }) 
        }
     </script>

	 </content>
</body>
</html>