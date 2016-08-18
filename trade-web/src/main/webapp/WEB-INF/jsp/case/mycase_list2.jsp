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

<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/case/case_filter.css" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<link rel="stylesheet" href="${ctx}/css/workflow/myCaseList.css" />

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
#exportExcel{
	width:84px;
	float:right;
}


.hint { position: relative; display: inline-block; }

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}

/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}

/* top */
.hint-top1:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top1:after {
    bottom: 100%;
	margin-bottom: 2px;
	width:80px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
.hint-top1:hover:before {
	margin-bottom: -10px;
}
.hint-top1:hover:after {
	margin-bottom: 2px;
	width:80px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
/* top */
.hint-top2:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top2:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top2:hover:before {
	margin-bottom: -10px;
}
.hint-top2:hover:after {
	margin-bottom: 2px;
	width:280px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">
                        案件总览
        </h2>
	    <form method="get" class="form-horizontal form_box">
			<div class="row clearfix">
                  <div class="form_content">
						<label class="sign_left control-label">案件类型</label>
						<div class="sign_right big_pad">
							<aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one"  />
						</div>
					</div>
					<div class="form_content">
                        <label class="sign_left_one control-label">
                                                                             案件状态
                        </label>
                        <div class="sign_right big_pad">
                                <aist:dict id="status" name="case_status" display="select" dictType="30001" clazz="select_control sign_right_one"  />
                         </div>
                    </div>
                    <div class="form_content">
                          <label class="sign_left_two control-label">
                                                                              案件组织
                          </label>
                          <div class="sign_right teamcode">
                                <input type="text" class="teamcode form-control" id="teamCode" name="teamCode" readonly="readonly"
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})"
										   value="${serviceDepName}"></input>
								<input class="teamcode form-control" type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">
                            </div>
                      </div>
             </div>
             <div class="row clearfix">
             		 <div id="select_div_1" class="form_content">
		           			<div class="sign_left">
                                 <select id="inTextType" data-placeholder="搜索条件设定" class="form-control" onchange="intextTypeChange()">
										<option value="1" selected>产证地址</option>
										<option value="0">客户姓名</option>
										<option value="2">经纪人姓名</option>
										<option value="4">交易顾问</option>
										<option value="3">所属分行</option>
										<option value="5">案件编号</option>
										<option value="6">CTM编号</option>
								 </select>
		                     </div>
		                     <div id="select_div_1" class="sign_right intextval">
									<input id="inTextVal" type="text" class="form-control pull-left">
							 </div>
					 </div>
                    <div class="form_content">
                    		 <label class="sign_left_two control-label">贷款需求选择</label>
							 <div class="sign_right">
									<aist:dict clazz="select_control sign_right_one" id="mortageService" name="mortageService" display="select" defaultvalue="" dictType="mortage_service" />
							 </div>
                    </div>            
			</div>
			<div class="row date-info clearfix">
                    <div class="form_content">
						<div id="dateDiv_0">
							<div id="select_div_0" class="sign_left">
								<aist:dict id="case_date_0" name="case_date" clazz="form-control" display="select" defaultvalue="30005001" dictType="30005" />
							</div>
							<div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left"  data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
									<span class="input-group-addon">到</span> 
								<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
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
							<button id="more" type="button" class="btn  btn-default btn_more"> 更多搜索条件<i class="fa fa-caret-up"></i> </button>
							<button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>
							<button id="cleanButton" type="button" class="btn btn-success">清空</button>&nbsp;&nbsp;&nbsp;
							<!-- <button href="javascript:void(0)" onclick="javascript:showExcelIn()" class="btn btn-success" >案件导出</button> -->
							<div id="exportExcel">
                            	<shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
								<a data-toggle="modal" class="btn btn-success" href="javascript:void(0)" onclick="javascript:showExcelIn()">案件导出</a>
								</shiro:hasPermission>
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
							    <th class="text-center light_icon"> <i class="iconfont icon_light">  &#xe604; </i> </th>
								<th ><span class="sort" sortColumn="B.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th >产证地址</th>
								<th >上家</th>
								<th >下家</th>
								<th >经办人</th>
								<th >案件状态</th>
							</tr>
						</thead>
						<tbody id="myCaseList">

						</tbody>
					</table>
				</div>
			</div>
			<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span> 
				<span class="ml15">共<strong class="bold" id="totalP"></strong>条 </span>&nbsp;
				<div id="pageBar" class="pagination text-center"></div>
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
										dictType="30010" />
								</div>
							</div>

							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">贷款服务信息</label>
								<div class="col-sm-9 checkbox i-checks checkbox-inline">
									<aist:dict id="mortage_info_item" name="mortage_info_item"
										display="checkbox" clazz="excel_in"
										defaultvalue="30010006,30010007" dictType="30011" />
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">交易服务信息</label>
								<div class="checkbox i-checks checkbox-inline">
									<aist:dict id="trade_info_item" name="trade_info_item"
										display="checkbox" clazz="excel_in" defaultvalue=""
										dictType="30012" />
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">资金监管信息</label>
								<div class="checkbox i-checks checkbox-inline">
									<aist:dict id="fund_info_item" name="fund_info_item"
										display="checkbox" clazz="excel_in" defaultvalue=""
										dictType="30013" />
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
						<button type="button" class="btn btn-success"
							onclick="javascript:exportToExcel()">导出至Excel</button>
					</div>
				</div>
			</div>
		</div>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
<content tag="local_script"> 
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
<script src="${ctx}/js/jquery.blockui.min.js"></script> 
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
<script src="${ctx}/js/plugins/jquery.custom.js"></script> 
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${ctx}/js/trunk/case/mycase_list2.js?v=1.1"></script> 

<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 

<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 

<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/plugins/jquery.custom.js"></script>


<script id="template_myCaseList" type="text/html">

      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td> 
							{{if item.RED_COUNT!=null}}
                                <span class="red-num">{{item.RED_COUNT}}</span>
							 {{/if}}
						</td>
						<td >
 							<p class="big">
								<a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}"  target="_blank">{{item.CASE_CODE}}</a>
							</p>
 							<p >
								<i class="tag_sign">c</i>{{item.ctmCode}}
							</p>
						</td>
						<td >
 							<p class="big">
								{{item.PROPERTY_ADDR}}
							</p>
 							<span >
								 <i class="salesman-icon"> </i>
								 <a class="hint  hint-top2" data-hint="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}" >
                           			{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}
								 </a>
                        	</span>
							</p>
						</td>
						
						<td class="center">
                          <span  >
						{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
							<a class="hint  hint-top1" data-hint="{{item.SELLER}}" >{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}...</a>
						{{else}}
							<a class="hint  hint-top1" data-hint="{{item.SELLER}}" >{{item.SELLER}}</a>
						{{/if}}
						</span>
                         
                       </td>
                       <td class="center">
                          <span  >
						{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
							<a class="hint  hint-top1" data-hint="{{item.BUYER}}" >{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}...</a>
						{{else}}
							<a class="hint  hint-top1" data-hint="{{item.BUYER}}" >{{item.BUYER}}</a>
						{{/if}}
                          </span>
                          
                        </td>
						<td class="center">
							<span class="manager"><a href="#"><em>交易顾问：</em>{{item.PROCESSOR_ID}}</a></span>
							<span class="manager"><a href="#"><em>合作顾问：</em></a></span>
						</td>
						<td class="center">
 							<p >
								  <i class="sign_blue">{{item.STATUS}}</i>
							</p>
						</td>
				  </tr>
       {{/each}}
</script> 
<script>
	$(function() {
		$("#productType").hide();
		$("#more").click(function() {
			$("#productType").toggle();
		});
	})
</script> 
</content>
</body>
</html>
