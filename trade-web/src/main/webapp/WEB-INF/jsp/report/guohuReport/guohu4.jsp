<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>贷款银行流向</title>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet" />
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
<!-- 分页控件 -->
<link rel="stylesheet" href="<c:url value='/static/css/plugins/pager/centaline.pager.css' />" />
<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />">
</head>
<body style="background-color: #fff;">
	<!--*********************** HTML_main*********************** -->
	<div>
		<div class="ibox-content" id="base_info">
			<div class="row chartwo">
				<div class="col-md-12">
					<div class="clearfix mb30">
						<h3 class="content-title pull-left">贷款银行流向</h3>
						<div class="calendar-watch clearfix">
							<p class="calendar-year">
								<a href="#" id="subtract"><em>&lt;</em></a> <span>2016</span> <a
									href="#" id="add"><em>&gt;</em></a>
							</p>
							<input type="hidden" value="${ctx}" id="ctx">
							<p class="calendar-month">
								<span value="1">1月</span> <span value="2">2月</span> <span
									value="3">3月</span> <span value="4">4月</span> <span value="5">5月</span>
								<span value="6">6月</span> <span value="7">7月</span> <span
									value="8">8月</span> <span value="9">9月</span> <span value="10">10月</span>
								<span value="11">11月</span> <span value="12">12月</span>
							</p>
						</div>
					</div>

					<div class="sign-content row  clearfix" style="overflow: auto;height: 500px;">
						<div class="col-md-6">
							<table
								class="table table_blue  table-striped table-bordered table-hover customerinfo">
								<thead>
									<tr>
										<th>银行</th>
										<th>单数</th>
										<th>金额</th>
									</tr>
								</thead>
								<tbody id="tableTemplate1">
									<tr>
										<td>建设银行浦东分行</td>
										<td>10</td>
										<td>33500</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="col-md-6">
							<table
								class="table table_blue  table-striped table-bordered table-hover customerinfo">
								<thead>
									<tr>
										<th>银行</th>
										<th>支行</th>
										<th>单数</th>
										<th>金额</th>
									</tr>
								</thead>
								<tbody id="tableTemplate2">
									<tr>
										<td>建设银行</td>
										<td>建设银行浦东分行</td>
										<td>10</td>
										<td>33500</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->

	<!-- Mainly scripts -->
	<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script src="<c:url value='/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<!-- 分页控件  -->
	<script src="<c:url value='/static/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/static/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<!-- 排序插件 -->
	<script src="<c:url value='/static/js/plugins/jquery.custom.js' />"></script>
	<!-- 个人js -->
	<script src="<c:url value='/js/trunk/report/getTemplateData.js' />"></script>
	<script id="template_table" type="text/html">
		{{c=0}}{{m=0}}	{{tc=0}}{{tm=0}}
			{{each rows as item index}}
				{{if index < 10}}
		    <tr>
              <td>{{item.MORT_FIN_BRANCH_NAME}}</td>
              <td>{{item.CASE_COUNT}}</td>
              <td>{{(item.MORT_COM_AMOUNT/10000).toFixed()}}万元</td>
             </tr>
				{{else}}
					{{c=item.CASE_COUNT+c}}
					{{m=(item.MORT_COM_AMOUNT/10000)+m}}
				{{/if}}
			{{tc=tc+item.CASE_COUNT}}
			{{tm=tm+(item.MORT_COM_AMOUNT/10000)}}
		  {{/each}}
		<tr>
			<td>其他</td>
			<td>{{c}}</td>
			<td>{{m.toFixed()}}万元</td>
		</tr>
		<tr>
			<td>总计</td>
			<td>{{tc}}</td>
			<td>{{tm.toFixed()}}万元</td>
		</tr>
	    </script>
	<script id="template_table1" type="text/html">
		{{tc=0}}{{tm=0}}
          {{each rows as item index}}
		  {{if item.IS_RUWEI_BANK=='cl'}}
			<tr>
				<td>{{item.MORT_FIN_BRANCH_NAME}}</td>
				<td>{{item.MORT_FIN_SUB_BRANCH_NAME}}</td>
				<td>{{item.CASE_COUNT}}</td>
				<td>{{(item.MORT_COM_AMOUNT/10000).toFixed()}}万元</td>
			</tr>
		  {{else}}
			<tr style="background: #f23c3c;color: white;">
				<td>{{item.MORT_FIN_BRANCH_NAME}}</td>
				<td>{{item.MORT_FIN_SUB_BRANCH_NAME}}</td>
				<td>{{item.CASE_COUNT}}</td>
				<td>{{(item.MORT_COM_AMOUNT/10000).toFixed()}}万元</td>
			</tr>
		  {{/if}}
			{{tc=tc+item.CASE_COUNT}}
			{{tm=tm+(item.MORT_COM_AMOUNT/10000)}}
		{{/each}}
			<tr>
				<td>总计</td>
				<td></td>
				<td>{{tc}}</td>
				<td>{{tm.toFixed()}}万元</td>
			</tr>
	    </script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid(){
			reloadGrid1(1);
			loadT(1);
		}
		function reloadGrid1(page) {
		   	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_)
			if(!page) page=1;
			var data = {};
			data.page=page;
			data.rows=20;
        	data.choiceMonth = year + "-" + month;
			data.belongMonth =getBelongMonth(data.choiceMonth);
			data.pagination=false;
			data.queryId='queryGuoHuForMortBank';
			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_table","tableTemplate1");
		}
		function loadT(page){
			var year = window.parent.yearDisplay;
			var month_ = parseInt(window.parent.monthDisplay)+1;
			var month = month_ > 9 ? month_:("0"+month_)
			if(!page) page=1;
			var data = {};
			data.page=page;
			data.rows=10;
			data.choiceMonth = year + "-" + month;
			data.belongMonth =getBelongMonth(data.choiceMonth);
			data.pagination=false;
			data.queryId='queryGuoHuForMortBranchBank';
			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_table1","tableTemplate2");
		}
	</script>
</body>
</html>
