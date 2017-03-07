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

				<div class="table-scroll" style="height:500px;overflow: auto">
					<table
							class="table table_blue  table-striped table-bordered table-hover customerinfo " >
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
       
		<td>{{item.org_name}} {{if item.JOB_CODE=='JQYDS'}}({{item.EMPLOYEE_NAME}}){{/if}}</td>
		<td>{{item.CASE_COUNT}}</td>
		<td>{{item.CASE_COUNT_PERCENT}}</td>
		<td>{{item.CASE_CON_PRICE/10000}}万元</td>
		<td>{{item.MORT_COM_COUNT}}</td>
		<td>{{item.MORT_COM_AMOUNT/10000}}万元</td>
		<td>{{item.IS_DELEGATE_YUCUI_COUNT}}</td>
		<td>{{item.IS_DELEGATE_CUSTOMER_COUNT}}</td>
		<td>{{item.LOST_AMOUNT/10000}}万元</td>
		<td>{{item.CASE_LOST_AMOUNT_PERCENT}}</td>
		<td>{{item.PING_GU_COUNT_PERCENT}}</td>
		<td>{{item.CASE_CON_PRICE/1000}}元</td>
		<td>{{item.EVA_FEE}}元</td>
		<td>{{item.E_COUNT_PERCENT}}</td>
		<td>{{item.E_AMOUNT_PERCENT}}</td>
	</tr>
	{{/each}}
	{{if rows.length > 0}}
	<tr>
		<td>{{rows[rows.length-1].TOTAL_CASE_PART}}</td>
		<td>{{rows[rows.length-1].TOTAL_CASE_COUNT}}</td>
		<td>1</td>
		<td>{{rows[rows.length-1].TOTAL_CASE_CON_PRICE/10000}}万元</td>
		<td>{{rows[rows.length-1].TOTAL_MORT_COM_COUNT}}</td>
		<td>{{rows[rows.length-1].TOTAL_MORT_COM_AMOUNT/10000}}万元</td>
		<td>{{rows[rows.length-1].TOTAL_IS_DELEGATE_YUCUI_COUNT}}</td>
		<td>{{rows[rows.length-1].TOTAL_IS_DELEGATE_CUSTOMER_COUNT}}</td>
		<td>{{rows[rows.length-1].TOTAL_LOST_AMOUNT/10000}}万元</td>
		<td>{{rows[rows.length-1].TOTAL_CASE_LOST_AMOUNT_PERCENT}}</td>
		<td>{{rows[rows.length-1].TOTAL_PING_GU_COUNT_PERCENT}}</td>
		<td>{{rows[rows.length-1].TOTAL_CASE_CON_PRICE/1000}}元</td>
		<td>{{rows[rows.length-1].TOTAL_EVA_FEE}}元</td>

		<td>{{rows[rows.length-1].TOTAL_E_COUNT_PERCENT}}</td>
		<td>{{rows[rows.length-1].TOTAL_E_AMOUNT_PERCENT}}</td>
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
	var nullData;
	function reloadGrid() {
		var year = window.parent.yearDisplay;
		var month_ = parseInt(window.parent.monthDisplay)+1;
		var month = month_ > 9 ? month_:("0"+month_)
		var data = {
			rows : 8,
			page : 1
		};
		var condition = "JQYDS";

		if($("#dsId").val()!=0){
			condition = "JQYZJ";
			data.condition_qudongId = $("#dsId").val();
			getGroup1($("#dsId").val(),"JQYZJ")
			/* userId=$("#dsId").val() */;
		}
		 if($("#zjId").val()!=0){
			condition = "JQYJL";
			data.condition_directorId = $("#zjId").val();
			/*  userId=$("#zjId").val();   */
			getGroup1($("#zjId").val(),"JQYJL")
		}
		 if($("#jlId").val()!=0){
			condition = "JFHJL";
			data.condition_qyManagerId = $("#jlId").val();
			/* userId=$("#jlId").val(); */
			getGroup1($("#jlId").val(),"JFHJL")
		}
		 if($("#dsId").val()==0&&$("#zjId").val()==0&&$("#jlId").val()==0){
			 getGroup1("1D29BB468F504774ACE653B946A393EE","JQYDS");
		 }

		data.pagination=false;
		data.produceType=condition;
		data.queryId='queryGuoHuForMortTeam';
		data.choiceMonth = year + "-" + month;
		data.belongMonth =getBelongMonth(data.choiceMonth);
		var url = ctx+"/quickGrid/findPage";
		initData(url,data,"template_table","tableTemplate");
	}
	$(function(){
		getGroup("1D29BB468F504774ACE653B946A393EE","JQYDS","dsId");
		$("#dsId").change(function(item){
			var parentId=$("#dsId").val();
			getGroup(parentId,"JQYZJ","zjId");
			$("#jlId").html(" <option value='0'>请选择</option>");
		})
		$("#zjId").change(function(item){
			var parentId=$("#zjId").val();
			getGroup(parentId,"JQYJL","jlId");
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
						optionHtml+="<option hval="+item.user_id+" value="+item.org_id+">"+item.org_name+"</option>";
					})
				}else{
					$.each(data.rows,function(i,item){
						optionHtml+="<option hval="+item.user_id+" value="+item.org_id+">"+item.org_name+"("+item.real_name+")</option>";
					})
				}
				$("#"+id).html(optionHtml);
			},
			error:function(){}
		});

	}
	function getGroup1(parentId,jobCode){
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
				nullData=data;
			},
			error:function(){}
		});

	}
	function initData(url,data,templateId,tbodyId) {
		/*aist.wrap(data);*/
		var sortColumn=$('span.active').attr("sortColumn");
		var sortgz=$('span.active').attr("sord");
		data.sidx=sortColumn;
		data.sord=sortgz;
		
		var pagination = data.pagination;
		
		$.ajax({
			async : true,
			url : url,
			method : "post",
			dataType : "json",
			data : data,
			success : function(data) {
				if(data==null||data==undefined ){
                    window.parent.wxc.alert("数据加载失败！");
					return;			
				}
			
				$.each(nullData.rows,function(i,item){
					item.E_AMOUNT_PERCENT = 0.0;
					item.LOST_AMOUNT = 0.00;
					item.PING_GU_COUNT_PERCENT = 0.0;
					item.ORGANIZATION_ID="";
					item.TOTAL_CASE_PART= "合计",
					item.JOB_CODE = item.job_code;

					item.CASE_COUNT_PERCENT = 0.0;
					item.MORT_COM_COUNT = 0;
					item.EVA_FEE = 0.00;
					item.EVA_COUNT= 0;

					item.CASE_LOST_AMOUNT_PERCENT = 0.0;
					item.E_CARD_COUNT = 0;
					item.EMPLOYEE_NAME = item.real_name;
					item.E_COUNT_PERCENT = 0.0;
					item.MORT_COM_AMOUNT =0.00,
					item.CASE_CON_PRICE = 0.00;
					item.CASE_COUNT = 0;
					item.IS_DELEGATE_YUCUI_COUNT = 0;
					item.E_AMOUNT = 0.00;
					item.IS_DELEGATE_CUSTOMER_COUNT = 0;
                    if(data.rows.length<=0){
                	   return;
                   }
					if(data.rows[data.rows.length-1].TOTAL_CASE_COUNT){
						item.TOTAL_CASE_COUNT = data.rows[data.rows.length-1].TOTAL_CASE_COUNT;
					}else{
						item.TOTAL_CASE_COUNT=0;
					}
					if(data.rows[data.rows.length-1].TOTAL_E_AMOUNT_PERCENT){
						item.TOTAL_E_AMOUNT_PERCENT = data.rows[data.rows.length-1].TOTAL_E_AMOUNT_PERCENT;
					}else{
						item.TOTAL_E_AMOUNT_PERCENT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_IS_DELEGATE_YUCUI_COUNT){
						item.TOTAL_IS_DELEGATE_YUCUI_COUNT = data.rows[data.rows.length-1].TOTAL_IS_DELEGATE_YUCUI_COUNT;
					}else{
						item.TOTAL_IS_DELEGATE_YUCUI_COUNT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_EVA_FEE){
						item.TOTAL_EVA_FEE = data.rows[data.rows.length-1].TOTAL_EVA_FEE;
					}else{
						item.TOTAL_EVA_FEE = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_CARD_COUNT_PERCENT){
						item.TOTAL_CARD_COUNT_PERCENT = data.rows[data.rows.length-1].TOTAL_CARD_COUNT_PERCENT;
					}else{
						item.TOTAL_CARD_COUNT_PERCENT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_MORT_COM_AMOUNT){
						item.TOTAL_MORT_COM_AMOUNT = data.rows[data.rows.length-1].TOTAL_MORT_COM_AMOUNT;
					}else{
						item.TOTAL_MORT_COM_AMOUNT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_CASE_CON_PRICE){
						item.TOTAL_CASE_CON_PRICE = data.rows[data.rows.length-1].TOTAL_CASE_CON_PRICE;
					}else{
						item.TOTAL_CASE_CON_PRICE = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_E_COUNT_PERCENT){
						item.TOTAL_E_COUNT_PERCENT = data.rows[data.rows.length-1].TOTAL_E_COUNT_PERCENT;
					}else{
						item.TOTAL_E_COUNT_PERCENT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_CASE_LOST_AMOUNT_PERCENT){
						item.TOTAL_CASE_LOST_AMOUNT_PERCENT = data.rows[data.rows.length-1].TOTAL_CASE_LOST_AMOUNT_PERCENT;
					}else{
						item.TOTAL_CASE_LOST_AMOUNT_PERCENT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_IS_DELEGATE_CUSTOMER_COUNT){
						item.TOTAL_IS_DELEGATE_CUSTOMER_COUNT = data.rows[data.rows.length-1].TOTAL_IS_DELEGATE_CUSTOMER_COUNT;
					}else{
						item.TOTAL_IS_DELEGATE_CUSTOMER_COUNT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_PING_GU_COUNT_PERCENT){
						item.TOTAL_PING_GU_COUNT_PERCENT = data.rows[data.rows.length-1].TOTAL_PING_GU_COUNT_PERCENT;
					}else{
						item.TOTAL_PING_GU_COUNT_PERCENT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_MORT_COM_COUNT){
						item.TOTAL_MORT_COM_COUNT = data.rows[data.rows.length-1].TOTAL_MORT_COM_COUNT;
					}else{
						item.TOTAL_MORT_COM_COUNT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_LOST_COUNT){
						item.TOTAL_LOST_COUNT = data.rows[data.rows.length-1].TOTAL_LOST_COUNT;
					}else{
						item.TOTAL_LOST_COUNT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_CASE_LOST_COUNT_PERCENT){
						item.TOTAL_CASE_LOST_COUNT_PERCENT = data.rows[data.rows.length-1].TOTAL_CASE_LOST_COUNT_PERCENT;
					}else{
						item.TOTAL_CASE_LOST_COUNT_PERCENT = 0;
					}
					if(data.rows[data.rows.length-1].TOTAL_LOST_AMOUNT){
						item.TOTAL_LOST_AMOUNT = data.rows[data.rows.length-1].TOTAL_LOST_AMOUNT;
					}else{
						item.TOTAL_LOST_AMOUNT = 0;
					} 

					   /*  var savadata=item; */
					$.each(data.rows,function(j,item1){
						if(item1.ORGANIZATION_ID==item.org_id){
							alert(item.org_name);
							item1.org_name=item.org_name;
							nullData.rows[i]=item1;
						    }
					})
					//console.log(item);
				})
				data.ctx = ctx;
				var templateData = template(templateId, nullData);
				$("#"+tbodyId).empty();
				$("#"+tbodyId).html(templateData);
				
		/* 		if(pagination == undefined || pagination){
			        initpage(nullData.total,data.pagesize,data.page, data.records,tbodyId);
				}  */
			},
			error : function(e, jqxhr, settings, exception) {
				//$.unblockUI();
			}
		});
	}
</script>
</body>
</html>
