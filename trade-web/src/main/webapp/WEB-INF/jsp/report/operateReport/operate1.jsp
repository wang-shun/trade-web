<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>贷款签约与过户对比</title>
        <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
		<!-- 分页控件 -->
		<!-- jQuery UI -->
			<link rel="stylesheet" href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" />
		<link rel="stylesheet" href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
		<link rel="stylesheet" href="${ctx}/static/trans/css/property/popmac.css" />
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
                <div class="row chartwo">
                    <div class="col-md-12">
                        <div class="clearfix mb30">
                            <h3 class="content-title pull-left">贷款签约与过户对比</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <input type="hidden" value="${ctx}" id="ctx">
                            </div>
                        </div>

                        <!-- table -->
                        <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                            <thead>
                                <tr>
                                    <th>类型</th>
                                    <th>贷款类型</th>
                                    <th>1月</th>
                                    <th>2月</th>
                                    <th>3月</th>
                                    <th>4月</th>
                                    <th>5月</th>
                                    <th>6月</th>
                                    <th>7月</th>
                                    <th>8月</th>
                                    <th>9月</th>
                                    <th>10月</th>
                                    <th>11月</th>
                                    <th>12月</th>
                                </tr>
                            </thead>
                            <tbody id="tableTemplate">
                                <tr>
                                    <td>纯商</td>
                                    <td>12</td>
                                    <td>4%</td>
                                    <td>33</td>
                                    <td>38</td>
                                    <td>35</td>
                                    <td>63</td>
                                    <td>19%</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

        <!-- Mainly scripts -->
        <script src="${ctx}/js/jquery-2.1.1.js"></script>
        <script src="${ctx}/js/bootstrap.min.js"></script>
        <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        		<!-- block UI -->
			<script src="${ctx}/js/jquery.blockui.min.js"></script>
			<!-- 分页控件  -->
			   <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
			<script src= "${ctx}/static/js/template.js" type="text/javascript" ></script>
			<script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
			<!-- 排序插件 -->
			<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>	
				<!-- 个人js -->
			<script src="${ctx}/js/trunk/report/getTemplateData.js"></script>
			<script id="template_table" type="text/html">
          {{each rows as item index}}
		    <tr>
              <td>{{item.groupName}}</td>
              <td>{{item.lossCount}}</td>
              <td>{{item.successCount}}</td>
              <td>{{item.totalCount}}</td>
              <td>{{item.groupName}}</td>
              <td>{{item.lossCount}}</td>
              <td>{{item.successCount}}</td>
              <td>{{item.totalCount}}</td>
             </tr>
		{{/each}}
	    </script>     
       	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1
				
			};
        	data.choiceYear = year;
/*         	data.belongMoth  = getBelongMonth(year + "-" + month); */
			var url = ctx+"/js/eachartdata/loanloss.json"
			initData(url,data,"template_table","tableTemplate");
		}
	</script>
    </body>
</html>
