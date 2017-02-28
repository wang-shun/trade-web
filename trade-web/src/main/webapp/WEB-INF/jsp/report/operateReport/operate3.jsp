<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签贷款数据</title>
        <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
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
                            <h3 class="content-title pull-left">签贷款数据</h3>
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
                                    <th>签约贷款数据</th>
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
       	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
			var data = {
				rows : 8,
				page : 1
				
			};
			data.choiceYear = year;
			var url = ctx + "/js/eachartdata/loanloss.json"
			$.ajax({
				async : true,
				url : url,
				method : "get",
				dataType : "json",
				data : data,
				success : function(data) {
					if (data == null || data == undefined) {
						window.parent.wxc.alert("数据加载失败！");
						return;
					}
					var title = [
					             "派单量",
					             "签约量（买卖）",
					             "过户量",
					            " 商贷签约量",
					            " 公积金签约量",
					            " 商贷案件占比",
					             "纯公积金案件占比",
					             "签贷合同价",
					             "商贷金额",
					             "公积金金额",
					             "商贷金额占比",
					             "公积金金额占比"
 ];
					var trHtml = "";
					$.each(data.rows, function(i, item) {
						trHtml += "<tr><td>" + title[i] + "</td>"
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.lossCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "<td>" + item.successCount + "</td>";
						trHtml += "</tr>";

					})
					$("#tableTemplate").html(trHtml);
				}
			})
		}
	</script>
    </body>
</html>
