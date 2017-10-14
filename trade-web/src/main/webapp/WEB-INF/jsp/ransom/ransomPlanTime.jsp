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
    <input type="hidden" id = "ransomCode" value="${ransomVo.ransomCode }" />
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content border-bottom clearfix space_box">
                <h2 class="title text-center">如修改已经的录入时间计划，需录入变更理由</h2>
                <form method="get" class="form_list text-center">
                   <div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">申请时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="applyTime" id="applyTime" class="form-control data_style" type="text" value="<fmt:formatDate value='${ransomVo.applyTime }' pattern='yyyy-MM-dd'/>" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="applyRemake" id="applyRemake" class="teamcode input_type" placeholder="" value="${ransomVo.applyRemake }" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">面签时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="interviewTime" id="interviewTime" class="form-control data_style" type="text" value="<fmt:formatDate value='${ransomVo.interviewTime }' pattern='yyyy-MM-dd'/>" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="interviewRemake" id="interviewRemake" class="teamcode input_type" placeholder="" value="${ransomVo.interviewRemake }" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">陪同还贷时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="repayTime" id="repayTime" class="form-control data_style" type="text" value="<fmt:formatDate value='${ransomVo.repayTime }' pattern='yyyy-MM-dd'/>" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="repayRemake" id="repayRemake" class="teamcode input_type" placeholder="" value="${ransomVo.repayRemake }" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">注销抵押时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="cancelTime" id="cancelTime" class="form-control data_style" type="text" value="<fmt:formatDate value='${ransomVo.cancelTime }' pattern='yyyy-MM-dd'/>" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="cancelRemake" id="cancelRemake" class="teamcode input_type" placeholder="" value="${ransomVo.cancelRemake }" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">领取产证时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="redeemTime" id="redeemTime" class="form-control data_style" type="text" value="<fmt:formatDate value='${ransomVo.redeemTime }' pattern='yyyy-MM-dd'/>" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="redeemRemake" id="redeemRemake" class="teamcode input_type" placeholder="" value="${ransomVo.redeemRemake }" />
                      </div>
               		</div>
               		<div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small select_style mend_select">回款结清时间</label>
                          <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                              <input name="paymentTime" id="paymentTime" class="form-control data_style" type="text" value="<fmt:formatDate value='${ransomVo.paymentTime }' pattern='yyyy-MM-dd'/>" placeholder="">
                          </div>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <input name="paymentRemake" id="paymentRemake" class="teamcode input_type" placeholder="" value="${ransomVo.paymentRemake }" />
                      </div>
               		</div>
               		<div>
						<div class="text-center">
							<a class='btn btn-primary ' href="javascript:void(0)" id="save" style="width: 110px;">保存</a>
							<a class='btn btn-primary ' onclick = "reloadTimeRecord()" href="javascript:void(0)" data-toggle="modal" data-target="#myModal">变更记录查看</a>
						</div>
					</div>
                </form>
            </div>
        </div>
        
        <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
        	<div class="modal-dialog" style="width: 1070px;height: 500px;top: 88px;background: #ccc;">
				<div class="modal-header"><button class="close" type="button" data-dismiss="modal">×</button>
					<h2 class="title">变更明细记录查看</h2>
				</div>
				<div class="modal-body">
					<table class="table table_blue table-striped table-bordered table-hover ">
						<thead>
							<tr>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="record-detail"></tbody>
					</table>
				</div>
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
        
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
        <script src="<c:url value='/js/ransom/ransomPlanTime.js'/>" type="text/javascript"></script>
		</content>
		<script id="template_timeRecord" type= "text/html">
			{{each rows as item index}}
				{{if item.applyTime != null }}
					<tr>
						<td>申请时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.applyTime}}</td>
						<td>{{item.applyRemake}}</td>
					</tr>
				{{/if}}
				{{if item.interviewTime != null }}
					<tr>
						<td>面签时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.interviewTime}}</td>
						<td>{{item.interviewRemake}}</td>
					</tr>
				{{/if}}
				{{if item.repayTime != null }}
					<tr>
						<td>陪同还贷时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.repayTime}}</td>
						<td>{{item.repayRemake}}</td>
					</tr>
				{{/if}}
				{{if item.cancelTime != null }}
					<tr>
						<td>注销抵押时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.cancelTime}}</td>
						<td>{{item.cancelRemake}}</td>
					</tr>
				{{/if}}
				{{if item.redeemTime != null }}
					<tr>
						<td>领取产证时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.redeemTime}}</td>
						<td>{{item.redeemRemake}}</td>
					</tr>
				{{/if}}
				{{if item.paymentTime != null }}
					<tr>
						<td>回款结清时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.paymentTime}}</td>
						<td>{{item.paymentRemake}}</td>
					</tr>
				{{/if}}
			{{/each}}
		</script>
    </body>
</html>
