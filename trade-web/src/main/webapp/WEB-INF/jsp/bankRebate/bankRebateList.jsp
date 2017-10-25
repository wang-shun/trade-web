<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>银行返利列表</title>
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
			.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
			.text-center{text-align:center;}
			#searchButton{margin-right:5px;}
			.table_content .big a{
				min-width: 140px;
				display: inline-block;
			}
			
		</style>
		<content tag="pagetitle">银行返利列表</content>
    </head>

    <body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h1 class="title">
				银行返利列表
			</h1>
			<!-- <form method="get" class="form-horizontal form_box"> -->
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label">申请单状态</label>
						<div class="sign_right big_pad">
							<aist:dict id="caseStatus" name="" display="select" dictType="bank_rebate_status" clazz="select_control sign_right_one_case"/>
						</div>
					</div>

					<div class="row date-info clearfix">
						<div class="form_content">
							<div id="dateDiv_0" style="padding-left:45px;">
								<label class="sign_left_two control-label">申请时间</label>
								<div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
									<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期">
									<span class="input-group-addon">到</span>
									<input id="dtEnd_0" name="dtEnd" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label">申请人</label>
						<div class="sign_right big_pad">
							<input type="text" class="form-control" id="applyer" name="applyer">
						</div>
					</div>

					<div class="row date-info clearfix">
						<div class="form_content">
							<div id="dateDiv_0" style="padding-left:45px;">
								<label class="sign_left_two control-label">担保公司</label>
								<div class="sign_right big_pad">
									<select name="" class="form-control" id="finOrgId">
										<option value="" selected="selected">请选择</option>
										<option value="0">评估公司</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row m-t-sm">
					<div class="form_content">
						<div class="more_btn">
							<button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="myCaseListCleanButton" type="button" class="btn btn-grey">清空</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="addNewCase"  type="button" class="btn btn-success">新增</button>
							<button id="importButton" type="button" class="btn btn-success" onclick="javascript:showExcelModal()">返利批量导入</button>
							<a data-toggle="modal" class="btn btn-success" href="javascript:void(0)" onclick="javascript:showExcelIn()">下载Excel模板</a>
							<button id="deleteButton" onclick="javascript:deleteButton()" type="button" class="btn btn-success" disabled="true">删除</button>&nbsp;
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
							<th>申请时间</th>
							<th>担保公司</th>
							<th>返利总额</th>
							<th>申请人</th>
							<th>申请单状态</th>
							<th>操作</th>
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

        <!-- 返利导入 -->
        <div id="excel-modal-form" class="modal fade" role="dialog" aria-labelledby="excel-modal-title" aria-hidden="true">
            <div class="modal-dialog" style="width:1200px">
                <div class="modal-content">
                    <span>返利单批量导入》》</span>
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">×
                        </button>
                        <h4 class="modal-title" id="excel-modal-title">
                            请上传附件
                        </h4>
                    </div>
                    <input type="hidden" id="ex_message" value="${ex_message}" />
                    <form id="excelInForm"  method="post" enctype="multipart/form-data">
                        <div class="modal-body">
                            <div id="wrapper" class="Index">
                                <div class="main-bonus">
                                    <div class="bonus-wrap">
                                        <div class="ibox-content bonus-m-con">
                                            <div class="row">
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">担保公司</label>
                                                    <div class="col-lg-9 col-md-9">
                                                        <select name="guaranteeCompany" class="form-control" id="finOrgId">
                                                            <option value="" selected="selected">请选择</option>
                                                            <option value="评估公司">评估公司</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">返利总金额</label>
                                                    <div class="col-lg-9 col-md-9">
                                                        <input type="text" class="form-control" id="rebateMoney" name="rebateTotal">
                                                    </div>
                                                </div>
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">公司账户</label>
                                                    <div class="col-lg-9 col-md-9">
                                                        <select name="companyAccount" class="form-control" id="companyAccount"  readonly="readonly">
                                                            <option value="" selected="selected">请选择</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">申请人</label>
                                                    <div class="col-lg-9 col-md-9">
                                                        <input type="text" class="form-control" id="applyer" name="applyPerson"  value="${user.realName}" readonly="readonly">
                                                    </div>
                                                </div>
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">录入时间</label>
                                                    <!--   <div id="datepicker_1" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd hh:mm:ss">
                                                         <input id="dtBegin_0" name="applyTime" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="录入时间">
                                                     </div> -->
                                                    <div class="col-lg-9 col-md-9">
                                                        <input type="text" class="form-control" id="applyTime" name="applyTime"  value="" readonly="readonly">
                                                    </div>
                                                </div>
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">录入人所在部门</label>
                                                    <div class="col-lg-9 col-md-9">
                                                        <input type="text" class="form-control" id="applyOrg" name="applyOrg" value="${user.serviceDepName }" readonly="readonly">
                                                    </div>
                                                </div>
                                                <div class="col-lg-5 col-md-5">
                                                    <label class="col-lg-3 col-md-3 control-label font_w">备注</label>
                                                    <textarea rows="2" cols="100" name="comment"></textarea>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" id="inType" value="" />
                            <label class="col-sm-7 control-label">
                                <input id="file"  class="btn btn-default"  type="file" name="fileupload"  />
                            </label>
                            <div class="col-sm-3"></div>
                        </div>

                    </form>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" onclick="javascript:excelIn()">
                            提交
                        </button>
                    </div>

                </div>
            </div>
        </div>
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
        <script	src="<c:url value='/js/trunk/bankRebate/bankRebateList.js' />"></script>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="bankRebate" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="{{item.GUARANTEE_COMP_ID}}"

					 guaranteeCompId="{{item.GUARANTEE_COMP_ID}}" />
                    <input id='guaranteeCompId' type='hidden' name='guaranteeCompId' value="{{item.GUARANTEE_COMP_ID}}"/>
					<input type='hidden' name='pkId' value="{{item.pkId}}" >

				</td>
                                    <td>{{item.APPLY_TIME}}</td>
                                    <td>{{item.GUARANTEE_COMPANY}}</td>
									<td>{{item.REBATE_TOTAL}}</td>
                                    <td>{{item.APPLY_PERSON}}</td>
									<td>
										{{if item.STATUS=='0'}}未提交{{/if}}
									</td>
									<td class="center">
										{{if item.STATUS=='0'}}
											<a href="${ctx}/bankRebate/bankRebateUpdate?pkid={{item.pkId}}&&guaranteeCompId={{item.GUARANTEE_COMP_ID}}" target="_blank">修改</a>
											<a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.GUARANTEE_COMP_ID}}" target="_blank">提交</a>
										{{/if}}
                        			</td>
                                </tr>
						{{/each}}
	    </script>
		<script>
			function showExcelIn(){
				window.location.href = ctx + "/bankRebate/exportMatrixLeaderSheet"
			}
		</script>
	    </content>
          </body>
</html>