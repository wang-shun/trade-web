<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>案件总览</title>
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
#exportExcel{
	width:84px;
	float:right;
}
.sign_right_one_case{
	width:130px;
}

</style>
<content tag="pagetitle">案件总览</content>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<%-- <jsp:include page="/WEB-INF/jsp/case/glCaseDiv.jsp"></jsp:include> 合流jsp/cyx--%>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h1 class="title">
                        案件总览列表
        </h1>
	    <form method="get" class="form-horizontal form_box">
			<div class="row clearfix">
        		<div class="form_content">
					<label class="sign_left_two control-label">案件类型</label>
					<div class="sign_right big_pad">
						<aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/>
					</div>
				</div>
				
				<div class="form_content">
		            <label class="sign_left_two control-label">案件状态</label>
		            <div class="sign_right big_pad">
                    	<aist:dict id="status" name="case_status" display="select" dictType="30001" clazz="select_control sign_right_one_case"/>
		        	</div>
		        </div>
		        
		        <div class="form_content">
              		<label class="sign_left_two control-label">案件组织</label>
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
				<div class="form_content" >
					<label class="sign_left_two control-label">是否关注</label>
					<div class="sign_right big_pad">
						<select name="" class="form-control" id="isSubscribeFilter">
							<option value="" selected="selected">请选择</option>
							<option value="0">已关注</option>
							<option value="1">未关注</option>
						</select>
					</div>
				</div>
				
				<div class="form_content">
					<label class="sign_left_two control-label">付款方式</label>
					<div class="sign_right big_pad">
						<aist:dict clazz="select_control sign_right_one_case" id="mortageService" name="mortageService" display="select" defaultvalue="" dictType="mortage_service" />		
					</div>
				</div>
				
				<div id="select_div_1" class="form_content">
           			<div class="sign_left_two">
                   	 	<select id="inTextType" data-placeholder="搜索条件设定" class="form-control" onchange="intextTypeChange()">
								<option value="1" selected>产权地址</option>
								<option value="0">客户姓名</option>
								<option value="2">经纪人姓名</option>
								<option value="4">权证专员</option>
								<option value="3">所属分行</option>
								<option value="5">案件编号</option>
								<option value="6">成交报告编号</option>
					 	</select>
                 	</div> 
                    <div id="select_div_1" class="sign_right" style="width:300px;">
				 		<input id="inTextVal" type="text" class="form-control pull-left">
				 	</div>
	 		 	</div>
             </div>
             
			 <div class="row date-info clearfix">
				<div class="form_content">
						<div id="dateDiv_0" style="padding-left:45px;">
							<div id="select_div_0" class="sign_left_one " >
								<aist:dict id="case_date_0" name="case_date" clazz="form-control" display="select" defaultvalue="30007001" dictType="30007" />
							</div>
							<div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
									<span class="input-group-addon">到</span> 
								<input id="dtEnd_0" name="dtEnd" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
							</div>
							<div id="addLine" class="pull-left m-l"></div>
						</div>
				</div>
			</div>
			
			<div class="row clearfix">
			       <div class="form_content">
						<a class="add_btn" href="javascript:addDateDiv();"><span class="retrieve">添加日期检索</span></a>
				  </div>
			</div>
			
			<div  class="row product-type" id="productType">
				<div class="form_content">
					<label class="sign_left control-label">产品类型</label>
					<div class="sign_right goods_type">
						<aist:dict id="srvCode_0" name="srvCode" clazz="btn btn-white" display="checkboxcustom" dictType="yu_serv_cat_code_tree" level='2' onclick=""/>
					</div>
				</div>
		  	</div>
		  	
	 		<div class="row m-t-sm">
				<div class="form_content">
					<div class="more_btn">
						<button id="more" type="button" class="btn  btn-default btn_more">更多搜索条件<i class="fa fa-caret-up"></i> </button>&nbsp;&nbsp;&nbsp;&nbsp;
						<button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- <button id="addNewCase"  type="button" class="btn btn-success">新增案件</button> -->									
						<button id="myCaseListCleanButton" type="button" class="btn btn-grey">清空</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<div id="exportExcel">   
							<a data-toggle="modal" class="btn btn-primary" href="javascript:void(0)" onclick="javascript:showExcelIn()">案件导出</a>							
						</div>
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
								<th ><span class="sort" sortColumn="B.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th >案件状态</th>
								<th >产权地址</th>
								<th >买房姓名</th>
								<th >卖方姓名</th>
								<th >经办人</th>
								<!-- <th class="text-center light_icon">转入时间<i class="icon iconfont clock_icon"> &#xe60b; </i> </th> -->
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
	
	<div id="modal-form" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" style="width: 1200px">
			<div class="modal-content ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="leading-modal-title">请选择导出项</h4>
				</div>
				<div class="modal-body ">
					<form class="form-horizontal">

						<div class="form-group">
							<label class="col-sm-2 control-label">案件基本信息</label>
							<div class="col-sm-9 checkbox i-checks checkbox-inline">
								<aist:dict id="basic_info_item" name="basic_info_item"
									display="checkbox" clazz="excel_in" defaultvalue=""
									dictType="60001" />
							</div>
						</div>

						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">贷款基本信息</label>
							<div class="col-sm-9 checkbox i-checks checkbox-inline">
								<aist:dict id="mortage_info_item" name="mortage_info_item"
									display="checkbox" clazz="excel_in"
									defaultvalue="" dictType="60002" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">交易基本信息</label>
							<div class="checkbox i-checks checkbox-inline">
								<aist:dict id="trade_info_item" name="trade_info_item"
									display="checkbox" clazz="excel_in" defaultvalue=""
									dictType="60003" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
					</form>
				</div>
				<div class="modal-footer">
					<button id="checkAll" type="button" class="btn btn-default"
						onclick="javascript:checkAllItem()">全选</button>
					<button id="unCheckAll" type="button" class="btn btn-default"
						onclick="javascript:unCheckAllItem()">全不选</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:exportToExcel()">导出至Excel</button>
				</div>
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
<script src="<c:url value='/js/trunk/case/mycase_list_new.js' />"></script>  

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
						
						<td class="center">
							{{if item.aaa>0}}
								<img src="">
							{{/if}}
						</td>

						<td >
 							<p class="big">
								<a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}"  target="_blank">{{item.CASE_CODE}}</a>
								{{if item.SUBSCRIBE_COUNT == 0}}
									<span style="cursor: pointer;" class="starmack subscribe"  moduleCode="{{item.CASE_CODE}}" isSubscribe="true">
										<i class="iconfont_s  markstar star_subscribe" status="1">&#xe644;</i>
									</span>
								{{else}}
									<span style="cursor: pointer;" class="starmack subscribe active"  moduleCode="{{item.CASE_CODE}}" isSubscribe="false">
										<i class="iconfont_s  markstar star_subscribe" status="1">&#xe63e;</i>
									</span>
								{{/if}}
							</p>
						</td>

						<td class="center">
 							<p >
								<i class="sign_blue">{{item.STATUS}}</i>
							</p>
 							<p >
								<i class="sign_blue">{{item.CASE_PROPERTY_SHOW}}</i>
							</p>
						</td>

						<td >
							<p class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}">			
								{{item.PROPERTY_ADDR}}				 
							</p>
						</td>
						
						<td class="center">
                        	<p>
							{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
								{{if item.BUYER.split("/").length-1 >1}}
									{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
									{{
									(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).indexOf("/"))+1)
									}}</br>...
								{{else}}
									{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
									{{
									(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).length)+1)
									}}</br>
								{{/if}}
							{{else}}
								{{ if item.BUYER.length>5}}
									{{item.BUYER.substring(0,5)}}...
								{{else}}
									{{item.BUYER}}
								{{/if}}
							{{/if}}
                         	</p>
                        </td>

						<td class="center">
                        	<p>
							{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
								{{if item.SELLER.split("/").length-1 >1}}
									{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
									{{
									(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).indexOf("/"))+1)
									}}</br>...
								{{else}}
									{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
									{{
									(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).length)+1)
									}}</br>
								{{/if}}
							{{else}}
								{{ if item.SELLER.length>5}}
									{{item.SELLER.substring(0,5)}}...
								{{else}}
									{{item.SELLER}}
								{{/if}}
							{{/if}}
							</p>
                       	</td>

						<td class="center">
							<p >{{item.FONT_NAME}}</p>
							<p >{{item.PROCESSOR_ID}}</p>	
						</td>
						
				  </tr>
       {{/each}}
</script> 

</content>
</body>
</html>
