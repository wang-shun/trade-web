<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>贷款银行流向</title>
        <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
		<!-- 分页控件 -->
		<!-- jQuery UI -->
			<link rel="stylesheet" href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" />
		<link rel="stylesheet" href="${ctx}/static/css/plugins/pager/centaline.pager.css" />        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
        <link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row">
                    <div class="col-md-12">
                        <div class="clearfix mb30">
                            <h3 class="content-title pull-left">贷款银行流向</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                   <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <input type="hidden" value="${ctx}" id="ctx">
                                <p class="calendar-month">
                                    <span value="1">1月</span>
		                            <span value="2">2月</span>
		                            <span value="3">3月</span>
		                            <span value="4">4月</span>
		                            <span value="5">5月</span>
		                            <span value="6">6月</span>
		                            <span value="7">7月</span>
		                            <span value="8">8月</span>
		                            <span value="9">9月</span>
		                            <span value="10">10月</span>
		                            <span value="11">11月</span>
		                            <span value="12">12月</span>
		                        </p>
                            </div>
                        </div>
                        <div class="sign-content row  clearfix">
                            <div class="col-md-6">
                                <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                                    <thead>
                                        <tr>
                                            <th>银行</th>
                                            <th>单数</th>
                                            <th>金额(万元)</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableTemplate1">
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-6" style="height:565px;overflow-y:auto;">
                                <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                                    <thead>
                                        <tr>
                                            <th>银行</th>
                                            <th>支行</th>
                                            <th>单数</th>
                                            <th>金额(万元)</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableTemplate2">
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
              <td>{{item.groupName}}</td>
              <td>{{item.caseCount}}</td>
              <td>{{item.sdAmount}}</td>
             </tr>
		  {{/each}}

		  {{if rows.length > 0}}
			<tr>
             <td>其他</td>
             <td>{{rows[rows.length-1].sumCaseCount - rows[rows.length-1].totalCaseCount}}</td>
             <td>{{rows[rows.length-1].sumSdAmount - rows[rows.length-1].totalSdAmount}}</td>
          </tr>
		  <tr>
             <td>总计</td>
             <td>{{rows[rows.length-1].sumCaseCount}}</td>
             <td>{{rows[rows.length-1].sumSdAmount}}</td>
          </tr>
		  {{else}}
				<tr>
             		<td>总计</td>
             		<td>0</td>
             		<td>0</td>
          		</tr>
		  {{/if}}
	    </script>
	<script id="template_table1" type="text/html">
          {{each rows as item index}}
		    	<tr {{if item.isRuweiBank != 'cl'}} style="background-color:red;color:#fff;" {{/if}}>
              		<td>{{item.branchBankName}}</td>
              		<td>
						{{item.subBranchBankName}}
			  		</td>
              		<td>{{item.caseCount}}</td>
              		<td>{{item.sdAmount}}</td>
             	</tr>
		{{/each}}
		{{if rows.length > 0}}
			<tr>
             	<td>总计</td>
             	<td>&nbsp;</td>
             	<td>{{rows[rows.length-1].totalCaseCount}}</td>
             	<td>{{rows[rows.length-1].totalSdAmount}}</td>
         	</tr>
		{{else}}
			<tr>
             	<td>总计</td>
             	<td>&nbsp;</td>
             	<td>0</td>
             	<td>0</td>
         	</tr>
		{{/if}}
	    </script>     
       	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid(){
			reloadGrid1(1);
			reloadGrid2(1);
		}
		function reloadGrid1(page) {
		   	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_)
	        if(!page) page=1;
			var data = {};
	        data.page=page;
	        data.rows=10;
	        //统计分行数据
	        data.searchDateTime = year + "-" + month;
          	data.searchBelongMonth = getBelongMonth(data.searchDateTime);
          	data.queryId = "branchBankQuery";
          	data.pagination = true;
          	data.hidePagination = true;
  			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_table","tableTemplate1");
		}
		function reloadGrid2(page) {
		   	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_)
	        if(!page) page=1;
			var data = {};
	        data.page=page;
	        data.rows=20;
	        data.searchDateTime = year + "-" + month;
          	data.searchBelongMonth = getBelongMonth(data.searchDateTime);
			//初始化支行数据
			data.queryId = "subBranchBankQuery";
			data.pagination = false;
  			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_table1","tableTemplate2");
		}
	</script>
</body>
</html>
