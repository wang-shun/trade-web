<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>签贷款数据</title>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet" />
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
<!-- 分页控件 -->
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/pager/centaline.pager.css' />" />
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
						<h3 class="content-title pull-left">签贷款数据</h3>
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
                        <!-- table -->
                        <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                            <thead>
                                <tr>
                                    <th>类型</th>
                                    <th>单数</th>
                                    <th>占比</th>
                                    <th>合同价(万元)</th>
                                    <th>商贷金额(万元)</th>
                                    <th>公积金金额(万元)</th>
                                    <th>商贷金额占比</th>
                                    <th>公积金金额占比</th>
                                </tr>
                            </thead>
                            <tbody id="tableTemplate">
                            </tbody>
                        </table>
                        <em>取数规则：</em><br/>
                        <em style="margin-left: 65px;">①当月签贷时间、公积金签约时间；</em><br />
                        <em style="margin-left: 65px;">②商贷金额占比 = 商贷金额/合同价。</em>
				    </div>
                    </div>
                </div>
            </div>
        </div>

	<!-- Mainly scripts -->
	<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script src="<c:url value='/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

	<!-- 分页控件  -->
	<script
		src="<c:url value='/static/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/static/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<!-- 排序插件 -->
	<script src="<c:url value='/static/js/plugins/jquery.custom.js' />"></script>
	<!-- 个人js -->
	<script src="<c:url value='/js/trunk/report/getTemplateData.js' />"></script>
	<script id="template_table" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>
				{{if item.groupName == '30016001'}} 纯商  {{/if}}
				{{if item.groupName == '30016002'}} 组合 {{/if}}
				{{if item.groupName == '30016003'}} 纯公积 {{/if}}
			  </td>
              <td>{{item.caseCount}}</td>
              <td>{{item.caseCountRate}}%</td>
              <td>{{item.htAmount}}</td>
              <td>{{item.sdAmount}}</td>
              <td>{{item.gjjAmount}}</td>
              <td>{{item.sdAmountRate}}%</td>
              <td>{{item.gjjAmountRate}}%</td>
             </tr>
		{{/each}}
		{{if rows.length > 0}}
			<tr>
              <td>总计</td>
              <td>{{rows[rows.length-1].tatalCaseCount}}</td>
              <td>{{rows[rows.length-1].totalCaseCountRate}}%</td>
              <td>{{rows[rows.length-1].totalHtAmount}}</td>
              <td>{{rows[rows.length-1].totalSdAmount}}</td>
              <td>{{rows[rows.length-1].totalGjjAmount}}</td>
              <td>{{rows[rows.length-1].totalSdAmountRate}}%</td>
              <td>{{rows[rows.length-1].totalGjjAmountRate}}%</td>
             </tr>
		{{else}}
			 <tr>
              <td>总计</td>
              <td>0</td>
              <td>0%</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>0%</td>
              <td>0%</td>
             </tr>
		{{/if}}
	    </script>     
       	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid(page) {
		   	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_)
	        var data={};		
	    	if(!page)page=1;
  	        data.rows=10;
  	        data.page=page;
	        data.searchDateTime = year + "-" + month;
          	data.searchBelongMonth = getBelongMonth(data.searchDateTime);
          	data.pagination = false; 
          	data.queryId = "signLoanQuery";
  			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_table","tableTemplate");
		}
	</script>
</body>
</html>
