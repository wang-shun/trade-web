<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/style.css" rel="stylesheet" />
<!-- Data Tables -->
<link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"
	rel="stylesheet" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">贷款信息报表</h2>
			<form method="get" class="form_list">
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 案件编号 </label> <input
							type="text" class="teamcode input_type" id="caseCode"
							placeholder="" name="caseCode" value="" />
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 产证地址 </label> <input
							type="text" class="teamcode input_type" style="width: 435px;"
							id="propertyAddr" placeholder="" name="propertyAddr" value="" />
					</div>
				</div>

				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 贷款专员 </label> <input
							class="teamcode input_type" placeholder="" value=""
							id="REAL_NAME" name="REAL_NAME"
							onclick="chooseCaseOperator('${serviceDepId}')"  hVal="" />

						<div class="input-group float_icon organize_icon"
							id="MortgageLostListOnclick">
							<i class="icon iconfont"></i>
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 组织 </label> <input
							class="teamcode input_type" placeholder="" value="" id="orgName"
							name="orgName"
							onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', 
						orgType:'',departmentType:'',departmentHeriarchy:'',expandNodeId:'${serviceDepId}',						
						chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})">
						<input type="hidden" id="yuCuiOriGrpId" value="">
						<div class="input-group float_icon organize_icon"  id="MortgageLostListOrganizeOnclick">
							<i class="icon iconfont"></i>
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 主贷人 </label> <input
							class="input_type" placeholder=""id="custName"
												name="custName" value=""  style="width: 74px;" />
					</div>
				</div>

				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 贷款银行 </label>

							 <select	name="loanLostFinOrgName" id="loanLostFinOrgName" class="teamcode select_control ">
<%-- 							  <option value="" selected="selected">请选择</option> 
								<c:forEach items="${FinOrgNameList}"  var="var">									
									<option value="${var.FinOrgCode}">${var.FinOrgName}</option>							
								</c:forEach> --%>
						</select>
					</div>
					<div class="form_content" style="margin-left: 126px;">
						<select class="form-control mend_select sign_left_small"  id="loanLostCaseListTimeSelect"
							style="width: 105px;">
							<option value="SIGN_DATE" selected="selected">签约时间</option>
							<option value="LEND_DATE">放款时间</option>
							<option value="APPR_DATE">批款时间</option>
						</select>
						<div class="input-group sign-right dataleft input-daterange"
							data-date-format="yyyy-mm-dd" id="datepicker_0">
							<input id="dtBegin_0" name="dtBegin" class="form-control data_style" type="text"
								value="${startTime}" placeholder="起始时间"> <span
								class="input-group-addon">到</span> <input id="dtEnd_0" name="dtEnd"
								class="form-control data_style" type="text" value="${endTime}"
								placeholder="结束日期">
						</div>
					</div>
				</div>

				<div class="line">

					<div class="form_content">
						<label class="control-label sign_left_small"> 贷款支行 </label> <select
							name="loanLostFinOrgNameYc" id="loanLostFinOrgNameYc" class="teamcode select_control ">
<%-- 							<option value="">请选择</option>
							<c:forEach items="${FinOrgNameList}"  var="var">									
								<option value="${var.FinOrgCodeYc}">${var.FinOrgNameYc}</option>							
							</c:forEach> --%>
						</select>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 是否临时银行 </label>
						<div class="controls">
							<label class="radio inline"> <input type="radio"
								value="2" checked="checked" name="isTempBank"  id="isTempBankAll"> 全部
							</label> <label class="radio inline"> <input type="radio"
								value="1" name="isTempBank"> 是
							</label> <label class="radio inline"> <input type="radio"
								value="0" name="isTempBank"> 否
							</label>
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 是否流失 </label>
						<div class="controls">
							<label class="radio inline"> 
									<input type="radio" value="2" checked="checked" name="isLoseLoan" id="isLoseAll"> 全部
							</label> 
							<label class="radio inline"> 
									<input type="radio" value="1" name="isLoseLoan"> 是
							</label> 
							<label class="radio inline"> 
									<input type="radio" value="0" name="isLoseLoan"> 否
							</label>
						</div>
					</div>

				</div>

				<div class="line">
					<div class="form_content">
						<select class="form-control mend_select sign_left_small"  id="loanLostCaseListAmountSelect"
							style="width: 105px;">
							<option value="COM_AMOUNT" selected="selected">商贷</option>
							<option value="MORT_TOTAL_AMOUNT">总额</option>
							<option value="PRF_AMOUNT">公积金</option>
						</select>
						<div class="input-group sign-right dataleft "
							data-date-format="yyyy-mm-dd">
							<input  id="amountBegin_0" name="amountBegin_0" class="form-control data_style" type="text"
								value="" placeholder="万元"> <span
								class="input-group-addon">到</span> <input id="amountEnd_0"  name="amountEnd_0"
								class="form-control data_style" type="text" value=""
								placeholder="万元">
						</div>
					</div>
					<div class="add_btn" style="margin-left: 488px;">
						<button type="button" class="btn btn-success"
							id="mortgageInfoSearchButton">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<button type="button" id="mortgageInfoToExcel" class="btn btn-success"
						onclick="javascript:mortgageInfoToExcel()">导出列表</button>
						<button type="button" class="btn btn-grey"
							id="mortgageInfoCleanButton">清空</button>
					</div>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table border="0" cellpadding="0" cellspacing="0"
						class="table table_blue table-striped table-bordered table-hover "
						id="editable">
						<thead>
							<tr>
								<th><span class="sort" sortColumn="CASE_CODE" sord="desc"
									onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti"
									class="fa fa-sort-desc fa_down"></i></th>
								<th>案件地址</th>
								<th>时间</th>
								<th>主贷人</th>
								<th>贷款金额</th>
								<th>案件类型</th>
								<th>归属组</th>
							</tr>
						</thead>
						<tbody id="mortgageInfoList"></tbody>
					</table>
				</div>

				<div class="text-center page_box">
					<span id="currentTotalPage"><strong></strong></span> <span
						class="ml15">共<strong id="totalP"></strong>条
					</span>&nbsp;
					<div id="pageBar" class="pagergoto"></div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="signTimeStart" value="${signTimeStart}" />
	<input type="hidden" id="signTimeEnd" value="${signTimeEnd}" />

	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<script src="${ctx}/js/trunk/task/mortgageInfo_list2.js"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 排序插件 -->
	<script src="${ctx}/js/plugins/jquery.custom.js"></script> <!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> <script
		id="template_mortgageInfoList" type="text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="big"> <a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" target="_blank">{{item.CASE_CODE}}</a></td>
						<td>
                             <p class="big">
                                 {{item.PROPERTY_ADDR}}
                        	</p>
                             <p>
                                <a class="salesman-info" href="#" >
                                    {{item.FENHANG}}/{{item.FIN_ORG_NAME_YC}}                                               
                                </a>
                            </p>
                        </td>

					    <td>
                            <p class="smll_sign">
                                 <i class="sign_normal">贷</i>
                                      {{item.SIGN_DATE}}
                           </p>
                           <p class="smll_sign">
                                  <i class="sign_normal">审</i>
                                 {{item.APPR_DATE}}
                            </p>
                           <p class="smll_sign">
                                  <i class="sign_normal">放</i>
                                   {{item.LEND_DATE}}
                           </p>
                         </td>
						<td class="center">   
                                    {{item.CUST_NAME}}
                        </td>
						<td class="center">
                          	<span class="manager">总额：{{item.MORT_TOTAL_AMOUNT}}万</span>
                          	<span class="manager">商贷：{{item.COM_AMOUNT}}万</span>
                          	<span class="manager">公积金：{{item.PRF_AMOUNT}}万</span>
                       </td>

						<td class="center">

						{{if  item.IS_TMP_BANK == '是'}}
                      	 <p> <i class="sign_blue">临时银行</i></p>
						{{/if}}

						{{if  item.SDSTATUS=='是'}}
                      	 <p>流失</p>
						{{/if}}
                        
						
                       	</td>
                        <td class="center">
                                    {{item.ORG_NAME}}
                           <span class="manager">贷款专员：<a href="#">{{item.REAL_NAME}}</a></span>
                        </td>
				  </tr>
       {{/each}}
     </script> <script></script> </content>



	<script type="text/javascript">

	</script>
</body>
</html>