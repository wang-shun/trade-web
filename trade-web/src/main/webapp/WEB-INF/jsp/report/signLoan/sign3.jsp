<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>明细表</title>
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
						<h3 class="content-title pull-left">明细表</h3>
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
							</select>
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
					<div class="table-scroll" style="height:500px;overflow: auto">
                        <!-- table -->
                        <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                            <thead>
                                <tr>
                                    <th rowspan="2">组织</th>
                                    <th rowspan="2">受理量</th>
                                    <th rowspan="2">签约量</th>
                                    <th rowspan="2">商贷办理量</th>
                                    <th rowspan="2">客户自办量</th>
                                    <th rowspan="2">公积金量</th>
                                    <th rowspan="2">过户量</th>
                                    <th rowspan="2">评估转化率</th>
                                    <th rowspan="2">评估实收</th>
                                    <th colspan="2">E+申请量（卡）</th>
                                    <th colspan="2">E+申请量（贷款）</th>
                                </tr>
                                <tr>
                                  <th>申请单数</th>
                                  <th>申请金额(万元)</th>
                                  <th>申请单数</th>
                                  <th>申请金额(万元)</th>
                                </tr>
                            </thead>
                            <tbody id="tableTemplate"></tbody>
					</table>
					
					</div>
					<em>备注：</em><br />
                    <em style="margin-left: 38px">①取派单时间、实际签约时间、签贷时间、自办贷款申请时间、公积金签约时间、实际过户时间；</em><br />
                    <em style="margin-left: 38px">②评估转化率 = 实收评估单数/商贷办理量；</em><br />
                    <em style="margin-left: 38px">③E+申请单数：是指当月申请的E+金融产品。</em>
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
              <td>{{item.groupName}}</td>
              <td>{{item.slCount}}</td>
              <td>{{item.qyCount}}</td>
              <td>{{item.sdCount}}</td>
              <td>{{item.zbCount}}</td>
              <td>{{item.gjjCount}}</td>
              <td>{{item.ghCount}}</td>
              <td>{{item.pgfRate}}%</td>
              <td>{{item.pgfAmount}}</td>
              <td>{{item.eCardCount}}</td>
              <td>{{item.eCardAmount}}</td>
              <td>{{item.eProCount}}</td>
              <td>{{item.eProAmount}}</td>
             </tr>
		{{/each}}
		{{if rows.length > 0}}
			<tr>
              <td>总计</td>
              <td>{{rows[rows.length-1].totalSlCount}}</td>
              <td>{{rows[rows.length-1].totalQyCount}}</td>
              <td>{{rows[rows.length-1].totalSdCount}}</td>
              <td>{{rows[rows.length-1].totalZbCount}}</td>
              <td>{{rows[rows.length-1].totalGjjCount}}</td>
              <td>{{rows[rows.length-1].totalGhCount}}</td>
              <td>{{rows[rows.length-1].totalPgfRate}}%</td>
              <td>{{rows[rows.length-1].totalPgfAmount}}</td>
              <td>{{rows[rows.length-1].totalECardCount}}</td>
              <td>{{rows[rows.length-1].totalECardAmount}}</td>
              <td>{{rows[rows.length-1].totalEProCount}}</td>
              <td>{{rows[rows.length-1].totalEProAmount}}</td>
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
              <td>0%</td>
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
	        var data = {};
	      	if(!page)page=1;
  	        data.rows=8;
  	        data.page=page;
  	        data.pagination = false;
	        var condition = "init";
	        data.parentOrgId = "1D29BB468F504774ACE653B946A393EE";
	        
	        if($("#dsId").val()!=0){
	        	condition = "qudong";
	        	data.parentOrgId = $("#dsId option:selected").attr("hval");
	        }
	        
			if($("#zjId").val()!=0){
				 condition = "director";
				 data.parentOrgId = $("#zjId option:selected").attr("hval"); 	
			}
			 
			if($("#jlId").val()!=0){
				condition = "qyManager";
				data.parentOrgId = $("#jlId option:selected").attr("hval");
			}
			
			 data.condition = condition;
			 data.searchDateTime = year + "-" + month;
	         data.searchBelongMonth = getBelongMonth(data.searchDateTime);
	         data.queryId = "signLoanDetailByQudongQuery";
	         
	  		 var url = ctx+"/quickGrid/findPage";
			 initData(url,data,"template_table","tableTemplate");
		}
		 $(function(){
	        	getGroup("1D29BB468F504774ACE653B946A393EE","JQYDS","dsId");
	        	$("#dsId").change(function(item){
	        		var parentId=$("#dsId option:selected").attr("hval");
	        		getGroup(parentId,"JQYZJ","zjId");
	        		$("#jlId").html(" <option value='0'>请选择</option>");
	        	/* 	$("#fhId").html("<option value='0'>请选择</option>"); */
	        	})
	        	$("#zjId").change(function(item){
	        		var parentId=$("#zjId option:selected").attr("hval");
	        		getGroup(parentId,"JQYJL","jlId");
	        	/* 	$("#fhId").html("<option value='0'>请选择</option>"); */
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
	</script>

</body>
</html>
