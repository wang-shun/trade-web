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
.renyuan{width:400px;}
.bianhao{width:221px}
.dizhi{width:430px}
.hideDiv{display: none;}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>贷款信息列表</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
					
					<div class="row ${isConsultant ? 'hideDiv' : ''}">
					
					            <div class="row">
									<div class="col-md-12 col-sm-12">
										<div class="form-group ">
											<label class="col-md-1 control-label m-l">案件编号</label>
											<div class="col-md-10 dizhi">
												<input type="text" class="form-control" id="caseCode"
													name="caseCode" value="" />
											</div>
										</div>
									</div>
								</div>

								<div class="row product-type">
									<div class="col-md-12">
										<div class="form-group ">
											<label class="col-md-1 control-label m-l">贷款类型</label>
											<div class="col-md-10">
												<aist:dict id="finCode" name="finCode"
													clazz="btn btn-white" display="checkboxcustom"
													dictType="30016" level='2' onclick="" />
											</div>
										</div>
									</div>
								</div>
								
                                <div class="row product-type">
									<div class="col-md-12">
										<div class="form-group ">
											<label class="col-md-1 control-label m-l">客户姓名</label>
											<div class="col-md-10">
												<input type="text" class="form-control" id="custName"
													name="custName" value="" />
											</div>
										</div>
									</div>
								</div>
								
                          </div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group ">
											<label class="col-md-1 control-label m-l">产证地址</label>
											<div class="col-md-8 dizhi">
												<input type="text" class="form-control" id="propertyAddr"
													name="propertyAddr" value="" />
											</div>
										</div>
									</div>
						  </div>

					<div class="row date-info">
						<div class="col-md-12">
							<div class="form-group">
							<label class="col-md-1 control-label m-l">签约时间</label>
							<div id="dateDiv_0">
							<div id="datepicker_0"
								class="input-group input-medium date-picker input-daterange pull-left"
								data-date-format="yyyy-mm-dd">
								<input id="dtBegin" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="${signTimeStart}" placeholder="起始日期">
								<span class="input-group-addon">到</span>
								<input id="dtEnd" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="${signTimeEnd}" placeholder="结束日期" />
							</div>
							<div id="addLine" class="pull-left m-l">
							
						</div>
						</div>
						</div>
							</div>
						</div>	
						
						<div class="row m-t-sm">
							<div class="col-md-6">
								<div class="form-group">
							<label class="col-md-2 control-label m-l-lg"></label>
							<div>
							<button id="searchButton" type="button" class="btn btn-warning">查询</button>
							<button id="cleanButton" type="button" class="btn btn-primary">清空</button>
							</div>
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
						<th class="t-left pd-l"><span class='sort' sortColumn='m.CASE_CODE' sord='desc'>案件编号</span></th>
						<th class="t-left pd-l"><span>案件地址</span></th>
						<th class="t-left pd-l"><span>贷款类型</span></th>
						<th class="t-left pd-l"><span>贷款总额(万元)</span></th>
						<th class="t-left pd-l"><span>贷款机构</span></th>
						<th class="t-left pd-l"><span>客户姓名</span></th>
						<th class="t-left pd-l"><span>商贷金额(万元)</span></th>
					    <th class="t-left pd-l"><span>商贷年限</span></th>
					    <th class="t-left pd-l"><span>商贷利率折扣</span></th>
					    <th class="t-left pd-l"><span class='sort' sortColumn='m.SIGN_DATE' sord='desc'>签约时间</span></th>
					    <th class="t-left pd-l"><span class='sort' sortColumn='m.LEND_DATE' sord='desc'>放款时间</span></th>
					</tr>
				</thead>
				<tbody id="mortgageInfoList">
					
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
	<input type="hidden" id="signTimeStart" value="${signTimeStart}" />
	<input type="hidden" id="signTimeEnd" value="${signTimeEnd}" />
	<input type="hidden" id="org" value="${org}" />
	<input type="hidden" id="status" value="${status}" />
	<input type="hidden" id="userId" value="${userId}" />
	<input type="hidden" id="tempUser" value="${tempUser}" />
	<input type="hidden" id="personalId" value="${personalId}" />
	<input type="hidden" id="isConsultant" value="${isConsultant}" />

	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	
	<content tag="local_script"> 
    <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
    <script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<script src="${ctx}/js/trunk/task/mortgageInfo_list.js?version=1.1.1"></script> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
	<!-- 排序插件 -->
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<!-- 分页控件  -->
    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script id="template_mortgageInfoList" type= "text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="t-left"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
						<td class="t-left pd-l"><span class="case-addr">{{item.PROPERTY_ADDR}}</span></td>
                        <td class="t-left pd-l">{{item.MORT_TYPE}}</td>
						<td class="t-left pd-l">{{item.MORT_TOTAL_AMOUNT}}</td>
                        <td class="t-left pd-l">{{item.FIN_ORG_CODE}}</td>
                        <td class="t-left pd-l">{{item.CUST_NAME}}</td>
                        <td class="t-left pd-l">{{item.MORT_TOTAL_AMOUNT}}</td>
                        <td class="t-left pd-l">{{item.COM_YEAR}}</td>
                        <td class="t-left pd-l">{{item.COM_DISCOUNT}}</td>
                        <td class="t-left pd-l">{{item.SIGN_DATE}}</td>
                        <td class="t-left pd-l">{{item.LEND_DATE}}</td>
				  </tr>
       {{/each}}
     </script>
     <script></script>
	 </content>	
</body>
</html>
