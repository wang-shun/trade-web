<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>评估无需结算案件列表</title>
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
		<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
		<link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
		<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
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
			.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
			.text-center{text-align:center;}
			#searchButton{margin-right:5px;}
			.table_content .big a{
				min-width: 140px;
				display: inline-block;
			}
		</style>
		<content tag="pagetitle">评估无需结算案件列表</content>
    </head>

    <body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h1 class="title">
				评估无需结算案件列表
			</h1>
			<!-- <form method="get" class="form-horizontal form_box"> -->
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label">评估公司</label>
						<div class="sign_right big_pad">
							<select  id="finOrgId" class="form-control select_control">
								</select>
						</div>
					</div>

					<div class="row date-info clearfix">
						<div class="form_content">
							<div id="dateDiv_0" style="padding-left:45px;">
								<label class="sign_left_two control-label">无需结算原因</label>
								<div class="sign_right big_pad">
									<select name="" class="form-control select_control" id="noEnd">
										<option value="" selected="selected">请选择</option>
										<option value="爆单">爆单</option>
										<option value="退报告">退报告</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label">贷款权证</label>
						<div class="sign_right big_pad">
							<select id="inTextType" data-placeholder="搜索条件设定" class="form-control select_control" onchange="intextTypeChange()">
									<option value="1" selected>产证地址</option>
									<option value="0">案件编号</option>
									<option value="2">评估单编号</option>
									
							 </select>

						</div>
					</div>
					<div class="form_content">
						<input id="inTextVal" type="text" class="form-control data_style">
					</div>
				</div>
				<div class="row m-t-sm">
					<div class="form_content">
						<div class="more_btn">
							<button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="myCaseListCleanButton" type="button" class="btn btn-grey">清空</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="renewbatEnd" onclick="javascript:reNewAccount()" type="button" class="btn btn-success" disabled="true">重新结算</button>&nbsp;
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
                                	<th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th>
                                    <th>
                                    	案件编号
                                    <!-- <p>案件编号</p>
                                    	<p>评估单编号</p> -->
                                    
                                    </th>
                                    <th>产证地址</th>
                                    <th>评估公司</th>
                                    <th>
                                    	<p class="aa">评估申请日期</p>
                                    	<p class="aa">出报告日期</p>
                                    </th> 
                                    <!-- <th>评估费实收</th>
                                    <th>评估值</th>
                                    <th>返利金额</th>
                                    <th>结算费用</th> -->
                                    <th>评估进程</th>
                                    <th>无需结算原因</th>
                                </tr>
						</thead>
						<tbody id="t_body_data_contents">

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
		<!-- <form action="#" accept-charset="utf-8" method="post" id="excelForm"></form> -->
	</div>

    <input type="hidden" id="ctx" value="${ctx}" />
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script">
       <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
        <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
         <!-- 日期控件 -->
    	<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
        <!-- Custom and plugin javascript -->
        <script src="<c:url value='/js/inspinia.js' />"></script>
        <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
        <!-- 弹出框插件 -->
	    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
        <!-- 分页控件  -->
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
        <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
        <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
        <script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
         <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        <!-- 评估无需结算  -->
        <script	src="<c:url value='/js/trunk/eval/settle/noEndEvalList.js' />"></script>
        
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalNoListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="{{item.caseCode}}" 
					 
					 caseCode="{{item.caseCode}}" cuNu="{{item.glCount}}"
					 pkId="{{item.pkid}}"/> 
					
					<input type='hidden' name='pkId' value="{{item.pkid}}"/>
					<input type='hidden' name='case_code' value="{{item.caseCode}}"/>
				</td>
									<td class="demo-left">{{item.caseCode}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.EVA_COMPANY}}</td>
                                    <td>
										<p>申：{{item.APPLY_DATE}}</p>
										<p>出：{{item.ISSUE_DATE}}</p>
									</td>
									<td></td>
                                    <td>{{item.SETTLE_NOT_REASON}}</td>
                                </tr>
						{{/each}}
	    </script>
	    </content>
          </body>
</html>