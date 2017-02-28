<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>明细表</title>
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
							</select> <select id="fhId" class="select_control">
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
                                    <th rowspan="2">评估实收(万元)</th>
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
	        
	        var condition = "init";
	        if($("#dsId").val()!=0){
	        	condition = "qudong";
	        	data.condition_qudongId = $("#dsId").val();
	        	userId=$("#dsId").val();
	        }
	        
			if($("#zjId").val()!=0){
				 condition = "director";
				 data.condition_directorId = $("#zjId").val();
				 userId=$("#zjId").val();      	
			}
			 
			if($("#jlId").val()!=0){
				condition = "qyManager";
				data.condition_qyManagerId = $("#jlId").val();
				userId=$("#jlId").val();
			}
			
			if($("#fhId").val()!=0){
				 condition = "fhManager";
				 data.condition_fhManagerId = $("#fhId").val();
				 orgId=$("#fhId").val();
			 }
			 
			 data.condition = condition;
			 data.searchDateTime = year + "-" + month;
	         data.searchBelongMonth = year + month;
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
	        		$("#fhId").html("<option value='0'>请选择</option>");
	        	})
	        	$("#zjId").change(function(item){
	        		var parentId=$("#zjId option:selected").attr("hval");
	        		getGroup(parentId,"JQYJL","jlId");
	        		$("#fhId").html("<option value='0'>请选择</option>");
	        	})
	        	$("#jlId").change(function(item){
	        		var parentId=$("#jlId option:selected").attr("hval");
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
	  	              });
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
