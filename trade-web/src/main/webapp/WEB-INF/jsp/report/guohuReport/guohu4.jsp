<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>贷款银行流向</title>
<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/static/css/animate.css" rel="stylesheet" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" />
<!-- 分页控件 -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css">
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
							<div class="text-center">
								<span id="currentTotalPage"><strong class="bold"></strong></span>
								<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
								<div id="pageBar" class="pagination my-pagination text-center m0"></div>
							</div>
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
	<script src="${ctx}/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<!-- 分页控件  -->
	<script
		src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/static/js/template.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 排序插件 -->
	<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
	<!-- 个人js -->
	<script src="${ctx}/js/trunk/report/getTemplateData.js"></script>
	<script id="template_table" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.MORT_FIN_BRANCH_NAME}}</td>
              <td>{{item.CASE_COUNT}}</td>
              <td>{{item.MORT_COM_AMOUNT/10000}}万元</td>
             </tr>
		{{/each}}
	    </script>
	<script id="template_table1" type="text/html">
          {{each rows as item index}}
		  {{if item.IS_RUWEI_BANK=='cl'}}
		  <tr style="background: #f23c3c;color: white;">
			  <td>{{item.MORT_FIN_BRANCH_NAME}}</td>
			  <td>{{item.MORT_FIN_SUB_BRANCH_NAME}}</td>
			  <td>{{item.CASE_COUNT}}</td>
			  <td>{{item.MORT_COM_AMOUNT/10000}}万元</td>
		  </tr>
		  {{else}}
		  <tr>
			  <td>{{item.MORT_FIN_BRANCH_NAME}}</td>
			  <td>{{item.MORT_FIN_SUB_BRANCH_NAME}}</td>
			  <td>{{item.CASE_COUNT}}</td>
			  <td>{{item.MORT_COM_AMOUNT/10000}}万元</td>
		  </tr>
		  {{/if}}
		{{/each}}
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
			data.pagination=true;
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
