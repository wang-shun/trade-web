<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>后台交易顾问工作量统计</title>
        <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
		<!-- 分页控件 -->
		<link rel="stylesheet" href="${ctx}/static/css/plugins/pager/centaline.pager.css" />

        <!-- index_css -->
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
                            <h3 class="content-title pull-left">后台交易顾问工作量统计</h3>
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
                        <div class="sign-content clearfix">
                            <div>
                                <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                                    <thead>
                                        <tr>
                                            <th>交易顾问</th>
                                            <th>查限购</th>
                                            <th>核价</th>
                                            <th>审税</th>
                                            <th>公积办理</th>
                                            <th>过户</th>
                                            <th>领证</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableTemplate">
                                        
                                    </tbody>
                                </table>
                                 <div class="text-center">
								<span id="currentTotalPage"><strong class="bold"></strong></span>
								<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
								<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
						        </div>
                            </div>
                        </div>
				</div>
			</div>
		</div>
	</div>
	<!--*********************** HTML_main*********************** -->
        <script src="${ctx}/js/jquery-2.1.1.js"></script>
        <script src="${ctx}/js/bootstrap.min.js"></script>
        <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/static/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
		<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>	
		<script src="${ctx}/js/trunk/report/getTemplateData.js"></script>
		<script id="template_table" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.groupName}}</td>
              <td>{{item.cxgCount}}</td>
              <td>{{item.hjCount}}</td>
              <td>{{item.ssCount}}</td>
              <td>{{item.gjjCount}}</td>
 			  <td>{{item.ghCount}}</td>
              <td>{{item.lzCount}}</td>
             </tr>
		{{/each}}
		{{if rows.length > 0}}
			<tr>
              <td>总计</td>
              <td>{{rows[rows.length-1].totalCxgCount}}</td>
              <td>{{rows[rows.length-1].totalHjCount}}</td>
              <td>{{rows[rows.length-1].totalSsCount}}</td>
              <td>{{rows[rows.length-1].totalGjjCount}}</td>
 			  <td>{{rows[rows.length-1].totalGhCount}}</td>
              <td>{{rows[rows.length-1].totalLzCount}}</td>
             </tr>
		{{else}}
			<tr>
              <td>总计</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
 			  <td>0</td>
              <td>0</td>
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
          	data.queryId = "backConsultantWorkQuery";
          	
  			var url = ctx+"/quickGrid/findPage";
			initData(url,data,"template_table","tableTemplate");
		}
	</script>
</body>
</html>
