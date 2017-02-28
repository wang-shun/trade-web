<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>商业贷款统计表二</title>
<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/static/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link href="${ctx}/static/css/animate.css" rel="stylesheet" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" />
<!-- 分页控件 -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
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
						<h3 class="content-title pull-left">商业贷款统计表二</h3>
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
					<div class="form_list">
						<div class="form_content">
							<select id="dsId" class="select_control mr5">
								<option value="">请选择</option>
							</select> <select id="zjId" class="select_control mr5">
								<option value="">请选择</option>
							</select> <select id="jlId" class="select_control mr5">
								<option value="">请选择</option>
							</select><!-- <select id="fhId" class="select_control">
								<option value="">请选择</option>
							</select> -->
						</div>
						<div class="form_content space">
							<div class="add_btn mr5">
								<button type="button" onclick="reloadGrid()"
									class="btn btn-success" style="padding: 5px 12px;">
									<i class="icon iconfont"></i> 查询
								</button>
							</div>
						</div>
					</div>

					<div class="table-scroll">
						<table
							class="table table_blue  table-striped table-bordered table-hover customerinfo ">
							<thead>
								<tr>
									<th>组织</th>
									<th>过户总单数</th>
									<th>占比</th>
									<th>合同价</th>
									<th>商贷单数</th>
									<th>商贷金额</th>
									<th>公司办理</th>
									<th>客户自办</th>
									<th>流失金额</th>
									<th>金额流失率</th>
									<th>评估转化率</th>
									<th>应收评估费</th>
									<th>实收评估费</th>
									<th>E+卡转化率（单数）</th>
									<th>E+贷款转化率（金额）</th>
								</tr>
							</thead>
							<tbody id="tableTemplate">
								<tr>
									<td>金磊</td>
									<td>12</td>
									<td>4%</td>
									<td>33</td>
									<td>38</td>
									<td>35</td>
									<td>63</td>
									<td>19%</td>
									<td>38</td>
									<td>35</td>
									<td>35</td>
									<td>63</td>
									<td>19%</td>
									<td>35</td>
									<td>63</td>
								</tr>
							</tbody>
						</table>
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
	<script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
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
              <td>{{item.lossCount}}</td>
              <td>{{item.successCount}}</td>
              <td>{{item.totalCount}}</td>
              <td>{{item.lossRate}}%</td>
              <td>{{item.oldMonthLossRate}}%</td>
              <td>{{item.monthLossRate}}%</td>
              <td>{{item.lossAmount}}</td>
              <td>{{item.successAmount}}</td>
              <td>{{item.totalAmount}}</td>
              <td>{{item.AmountLossRate}}%</td>
              <td>{{item.MonthAmountLossRate}}%</td>
              <td>{{item.MonthAmountLossRate}}%</td>
               <td>{{item.totalAmount}}</td>
              <td>{{item.AmountLossRate}}%</td>
             </tr>
		{{/each}}
	    </script>
	<script type="text/javascript">
		var ctx = $("#ctx").val();
		function reloadGrid() {
		   	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_)
			var data = {
				rows : 8,
				page : 1
			};
	        var userId="";
	        var orgId="";
	        if($("#dsId").val()!=0){
	        	userId=$("#dsId").val();
	        }
			 if($("#zjId").val()!=0){
				 userId=$("#zjId").val();      	
				        }
			 if($("#jlId").val()!=0){
				 userId=$("#jlId").val();
			 }
			 if($("#fhId").val()!=0){
				 userId="";
				 orgId=$("#fhId").val();
			 }
			 data.userId=userId;
			 data.orgId=orgId;
        	data.choiceMonth = year + "-" + month;
/*         	data.belongMoth  = getBelongMonth(year + "-" + month); */
			var url = ctx+"/js/eachartdata/loanloss.json"
			initData(url,data,"template_table","tableTemplate");
		}
		 $(function(){
	        	getGroup("1D29BB468F504774ACE653B946A393EE","JQYDS","dsId");
	        	$("#dsId").change(function(item){
	        		var parentId=$("#dsId option:selected").attr("hval");
	        		getGroup(parentId,"JQYZJ","zjId");
	        		$("#jlId").html(" <option value='0'>请选择</option>");
	        		/* $("#fhId").html("<option value='0'>请选择</option>"); */
	        	})
	        	$("#zjId").change(function(item){
	        		var parentId=$("#zjId option:selected").attr("hval");
	        		getGroup(parentId,"JQYJL","jlId");
	        		/* $("#fhId").html("<option value='0'>请选择</option>"); */
	        	})
	        	$("#jlId").change(function(item){
	       /*  		var parentId=$("#jlId option:selected").attr("hval");
	  	        	  $.ajax({
	  	                  url : ctx+"/rapidQuery/findPage",
	  	                  method : "GET",
	  	                  data : {
	  	                	  parentId:parentId,
	  	                	  queryId:'getFH',
	  	                	  pagination : false
	  	                  },
	  	                  dataType : "json",
	  	                  async:true,
	  	                  success : function(data) {
	  	                	  var optionHtml="";
	  	                	  optionHtml+="<option value='0'>请选择</option>"
	  	                	  {
	  	                		  $.each(data.rows,function(i,item){
	  		                    	  optionHtml+="<option  value="+item.org_id+">"+item.org_name+"</option>";
	  		                      })
	  	                	  }
	  	                      $("#fhId").html(optionHtml);
	  	                  },
	  	                  error:function(){}
	  	              }); */
	  	              
	  	        	})
	        })
	        function getGroup(parentId,jobCode,id){
	        	  $.ajax({
	                  url : ctx+"/rapidQuery/findPage",
	                  method : "GET",
	                  data : {
	                	  orgId:parentId,
	                	  queryId:'getQD',
	                	  jobCode:jobCode,
	                	  pagination : false
	                  },
	                  dataType : "json",
	                  async:true,
	                  success : function(data) {
	                	  var optionHtml="";
	                	  optionHtml+="<option value='0'>请选择</option>"
	                	  if(id!="dsId"){
	                      $.each(data.rows,function(i,item){
	                    	  optionHtml+="<option hval="+item.org_id+" value="+item.user_id+">"+item.org_name+"</option>";
	                      })
	                	  }else{
	                		  $.each(data.rows,function(i,item){
		                    	  optionHtml+="<option hval="+item.org_id+" value="+item.user_id+">"+item.org_name+"("+item.real_name+")</option>";
		                      })
	                	  }
	                      $("#"+id).html(optionHtml);
	                  },
	                  error:function(){}
	              });

	        }
	</script
</body>
</html>
