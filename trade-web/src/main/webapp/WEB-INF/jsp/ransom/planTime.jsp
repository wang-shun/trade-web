<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>交易变更列表</title>
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/animate.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/style.css' />" rel="stylesheet"/>
        <!-- Data Tables -->
        <link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

         <!-- index_css -->
        <link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" ">
		<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
        <!-- 提示 -->
        <link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" />
        <style>
    .add {
        border: 1px solid red;
    }
    .line{padding-left:27%;}
</style>
    </head>
    <body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content border-bottom clearfix space_box">
                <h2 class="title text-center">如修改已经的录入时间计划，需录入变更理由</h2>
                <form method="get" class="form_list text-center">
                   <div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">申请时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">面签时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">陪同还贷时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">注销抵押时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">领取产证时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">回款结清时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                      </div>
               		</div>
               		<div>
						<div class="text-center">
							<a class='btn btn-primary ' href="javascript:void(0)" id="save" style="width: 110px;">保存</a>
							<a class='btn btn-primary ' href="javascript:void(0)" id="close">变更记录查看</a>
						</div>
					</div>
                </form>
            </div>
        </div>
        <content tag="local_script">
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
		<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
		<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
      	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
		<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
		<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
		<!-- 提示 -->
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytipuser.js' />"></script>
        <script src="<c:url value='/js/trunk/report/dealChangeList.js' />"></script>
    <script>
            $(document).ready(function () {
                $('.input-daterange').datepicker({
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true
                });
                var ctx = "${ctx}";
        		$('#save').click(function() {
        			window.location.href = ctx + "/ransomList/ransom/ransomDetail";
        		});
            });

        </script>
		</content>
    </body>
</html>
