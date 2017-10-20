<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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

<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/case/myCaseList.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/transcss/case/case_filter.css' />" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

<!-- index_css -->

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/workflow/myCaseList.css' />" />
<link rel="stylesheet" href="<c:url value='/css/workflow/newRecordpop.css' />" />
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />

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
    border-radius: 0px;
    color: inherit;
    display: block;
    width: 100%;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    font-size: 14px;
}
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
/* #inTextVal{width:50%} */
.chosen-container{float:left;margin-right:10px;}
#addLine{line-height:35px;}
.product-type span{margin-right:5px}	
.product-type .selected,.product-type span:hover{border-color:#f8ac59}

.product-type  .selected , .product-type span:hover{
	border-color: #1c84c6;
}
.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
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
#searchButton{margin-right:5px;}
.table_content .big a{
	min-width: 140px;
	display: inline-block;
}

.sign_right_one_case{
	width:130px;
}
/**开发完修改或删除**/
.table thead tr th{
    color: #1d1b1b;
}









</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/case/glCaseDiv.jsp"></jsp:include>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">
                        赎楼单总览 
        </h2>
	    <form method="get" class="form-horizontal form_box">
			<div class="row clearfix">
                  <div class="form_content">
						<label class="sign_left_one control-label">赎楼状态</label>
						<div class="sign_right big_pad">
                               <aist:dict id="ransomStatus" name="ransom_Status" display="select" dictType="RANSOM_STATUS" clazz="select_control sign_right_one_case"/>
                         </div>
					</div>
					<div class="form_content">
                        <label class="sign_left_one control-label">当前环节</label>
                        <div class="sign_right big_pad">
                               <aist:dict id="ransomProperty" name="ransom_Property" display="select" dictType="RANSOM_PART_CODE" clazz="select_control sign_right_one_case"/>
                         </div>
                    </div>
                    <div class="form_content">
                          <label class="sign_left_two control-label">案件归属</label>
                          <div class="sign_right teamcode" style="position:relative;">
	                    	<input type="text" style="width:300px;" class=" form-control" id="teamCode" name="teamCode" readonly="readonly"
		   						onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
		   						chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})" value="${serviceDepName}"></input>
							<input class="teamcode form-control" type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">
							<div class="input-group float_icon organize_icon" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
		   						chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})" value="${serviceDepName}">
	                        	<i class="icon iconfont"></i>
	                   		</div>
	                   	</div>
                      </div>
             </div>
			
             <div class="row clearfix">
             		 <div id="select_div_1" class="form_content">
             		 <label class="sign_left_one control-label">信息搜索</label>
		           			<div class="sign_left">
                                 <select id="inTextType"  class="form-control" name="searchInfo" onchange="intextTypeChange()">
                                 		<option >请选择</option>
										<option value="0" >房屋地址</option>
										<option value="1">归属人姓名</option>
										<option value="2">客户姓名</option>
								 </select>
		                     </div>
		                     <div id="select_div_1" class="sign_right intextval">
									<input id="inTextVal" type="text" name="inTextVal" class="form-control pull-left" placeholder="请输入搜索内容">
							 </div>
					 </div>
			</div>
			<div class="row date-info clearfix">
                    <div class="form_content">
						<div id="dateDiv_0">
						<label class="sign_left_one control-label">时间搜索</label>
							<div id="select_div_0" class="sign_left">
								<aist:dict id="ransomSearchTime" name="ransomSearchTime" clazz="form-control" display="select"  dictType="RANSOM_SEARCH_TIME" />
							</div>
							<div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="startTime" class="form-control data_style" onchange="checkDate('dtBegin_0')" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
									<span class="input-group-addon">到</span> 
								<input id="dtEnd_0" name="endTime" class="form-control data_style" onchange="checkDate('dtEnd_0')" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
							</div>
							<div id="addLine" class="pull-left m-l"></div>
						</div>
					</div>
			</div>
				 <div class="row m-t-sm">
					<div class="form_content">
						<div class="more_btn">
							<button id="searchButton"  type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>
							<shiro:hasPermission name="TRADE.CASE.RANSOMINFOLIST.ADDCASE">
								<button id="addNewRansomCase"   type="button" class="btn btn-success">新增案件</button>
							</shiro:hasPermission>
							<!-- <button id="exportCase"  type="button" class="btn btn-success" onclick="realShowExcelIn()">实时导出</button> -->
							<a data-toggle="modal" class="btn btn-success" href="javascript:void(0)" onclick="javascript:realShowExcelIn()">实时导出</a>
						</div>
					</div>
				</div>
		</form>
	</div>
	<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table class="table table_blue table-striped table-bordered table-hover " >
						<thead>
							<tr>
								<th></th>
								<th ><span class="sort" sortColumn="B.CASE_CODE" sord="desc" onclick="caseCodeSort();" >合约编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th >房屋地址</th>
								<th >案件归属</th>
								<th >客户</th>
								<th >合作机构</th>
								<th >最新状态</th>
								<th >借款金额</th>
								<th >操作</th>
								<!-- <th class="text-center light_icon"> <i class="icon iconfont clock_icon">  &#xe60b; </i> </th> -->
							</tr>
						</thead>
						<tbody id="myCaseList">
							
						</tbody>
					</table>
				</div>
			</div>
			<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
		    </div> 	
	</div>
</div>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
<input type="hidden" id="userId" value="${userId}" />

<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
<script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script>
<script src="<c:url value='/js/ransom/myRansom_list.js' />"></script> 
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 

<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<!--<script src="<c:url value='/js/workflow/myCaseList.js' />"></script>2016.1021 注释人:caoy 原因:与mycase_list2.js方法冲突--%>
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

<script id="template_myCaseList" type="text/html">
{{each rows as item index}}
	
  {{if index%2 == 0}}
      <tr class="tr-1">
  {{else}}
       <tr class="tr-2">
   {{/if}}
	<td class="center"></td>
	<td class="center">
			<p>
				<a href = "${ctx}/ransomList/ransomDetail?caseCode={{item.CASE_CODE}}">{{item.RANSOM_CODE}}</a>
			</p>
			<p>
				{{if item.RANSOM_STATUS == 4}}
					<i class="demo-top sign_blue" title = "{{item.STOP_REASON}}">中止</i>
				{{else}}
					<i class="sign_gray" style="display:none;">{{item.RANSOM_STATUS}}</i>
				{{/if}}
			</p>
	</td>
	<td class="center">
		{{item.PROPERTY_ADDR}}
	</td>
	
	<td class="center">
      <p>
		{{item.AGENT_NAME}}
	  </p>
	  <p>
		{{item.GRP_NAME}}
	  </p>
   </td>
   <td class="center">
     	{{item.BORROWER_NAME}}
    </td>
	<td class="center">
		{{item.COM_ORG_CODE}}
	</td>
	<td class="center"> 
		{{if item.RANSOM_STATUS == 1}}
		 	受理 &nbsp;&nbsp;{{item.UPDATE_TIME}}
		{{/if}}
		{{if item.RANSOM_STATUS == 2}}
		 	在途 &nbsp;&nbsp;{{item.UPDATE_TIME}}
		{{/if}}
		{{if item.RANSOM_STATUS == 4}}
		 	中止 &nbsp;&nbsp;{{item.UPDATE_TIME}}
		{{/if}}
		{{if item.RANSOM_STATUS == 3}}
		 	结束 &nbsp;&nbsp;{{item.UPDATE_TIME}}
		{{/if}}
	</td>
	<td class="center"> 
		{{item.BORRO_MONEY}}万元
	</td>
	<td class="center"> 
		{{if item.ISAPPLY == 0}}
			<a href="${ctx}/task/ransom/ransomApply?caseCode={{item.CASE_CODE}}" target="_blank">申请</a>
			<input type="button" class="btn btn-success" value="中止" onclick="ransomSuspend('{{item.CASE_CODE}}')" />
	</td>
  </tr>
{{/each}}
</script> 
</content>
</body>
</html>
