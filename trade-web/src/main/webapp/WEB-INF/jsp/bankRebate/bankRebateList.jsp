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
							<label class="sign_left_two control-label">申请时间</label>
							<div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control data_style" type="text" value="" placeholder="起始日期">
								<span class="input-group-addon">到</span>
								<input id="dtEnd_0" name="dtEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
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
									<aist:dict id="finOrgId" name="" display="select" dictType="bank_rebate_guarantee" clazz="form-control"/>
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
							<%--
							<button id="importButton" type="button" class="btn btn-success" onclick="javascript:showExcelModal()">返利批量导入</button>
							<a data-toggle="modal" class="btn btn-success" href="javascript:void(0)" onclick="javascript:showExcelIn()">下载导入模板</a>
							--%>
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
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                        </button>
                        <h4 class="modal-title">
							返利单批量导入
                        </h4>
                    </div>
                    <input type="hidden" id="ex_message" value="${ex_message}" />
                    <form id="excelInForm"  method="post" enctype="multipart/form-data">
                        <div class="modal-body">
							<div class="form_list">
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small">担保公司</label>
										<aist:dict id="guaranteeCompany" name="toBankRebate.guaranteeCompany" display="select"
												   dictType="bank_rebate_guarantee" clazz="select_control"/>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small">返利总金额</label>
										<input type="text" class="select_control sign_right_one" id="rebateMoney" name="toBankRebate.rebateTotal"
											   value="">
									</div>
								</div>
							</div>
							<div class="form_list">
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small">申请人</label>
										<input type="text" class="select_control sign_right_one" id="applyer" name="toBankRebate.applyPerson"
											   readonly="readonly" value="${user.realName}"/>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small">录入人所在部门</label>
										<input type="text" class="select_control sign_right_one" id="applyDepartment"
											   readonly="readonly" value="${user.serviceDepName }"/>
										<input type="hidden" name="toBankRebate.deptId" value="${user.serviceDepId}" />
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small">录入时间：</label>
										<c:set var="now" value="<%=new java.util.Date()%>"/>
										<input type="text" class="select_control sign_right_one" id="applyTime" name="applyTime"
											   readonly="readonly"
											   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
									</div>
								</div>
							</div>
							<div class="form_list">
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small">备注：</label>
										<textarea class="select_control sign_right_one" style="width: 400px;" name="toBankRebate.comment" cols="100"></textarea>
									</div>
								</div>
							</div>
							<div class="form_list">
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small">附件：</label>
										<input id="file"  class="btn btn-default"  type="file" name="fileupload"  />
									</div>
								</div>
							</div>
                        </div>
                    </form>
                    <div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="javascript:excelIn()">
							提交
						</button>
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
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
										{{item.STATUS}}
									</td>
									<td class="center">
										{{if item.STATUS_OLD=='0'}}
											<a href="${ctx}/bankRebate/bankRebateUpdate?guaranteeCompId={{item.GUARANTEE_COMP_ID}}" target="_blank">修改</a>
											<%-- <a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.GUARANTEE_COMP_ID}}" target="_blank">提交</a> --%>
										{{/if}}
                        			</td>
                                </tr>
						{{/each}}
	    </script>
		<script>
			$(function(){
                $('#datepicker_0').datepicker({
                    format : 'yyyy-mm-dd',
                    weekStart : 1,
                    autoclose : true,
                    todayBtn : 'linked',
                    language : 'zh-CN'
                });
                aist.sortWrapper({
                    reloadGrid : searchMethod
                });
                var data = getQueryParams(1);
                data.queryId = "bankRebate";
                data.rows = 10;
                data.page = 1;
                aist.wrap(data);
                reloadGrid(data);
			})
			function showExcelIn(){
				window.location.href = ctx + "/bankRebate/exportMatrixLeaderSheet"
			}
            function callback() {
                setTimeout('searchMethod(1)',1000);
            }
		</script>
	    </content>
          </body>
</html>