<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>明细表</title>
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
            <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
                <div class="row chartwo">
                    <div class="col-md-12">
                        <div class="clearfix mb30">
                            <h3 class="content-title pull-left">明细表</h3>
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
                        <div class="form_list">
                            <div class="form_content">
				      <select  id ="districtId" class="select_control mr5">
                                    <option value="">
                                        请选择
                                    </option>
                                </select>
                                <select id="orgId" class="select_control mr5">
                                    <option value="">
                                        请选择
                                    </option>
                                </select>
                                <select id="userId" class="select_control">
                                    <option value="">
                                        请选择
                                    </option>
                                </select>
                            </div>
                            <div class="form_content space">
                                <div class="add_btn mr5">
                                    <button type="button" onclick="reloadGrid()" class="btn btn-success" style="padding: 5px 12px;">
                                    <i class="icon iconfont"></i>
                                        查询
                                    </button>
                                </div>
                            </div>
                        </div>

                        <!-- table -->
                        <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                            <thead>
                                <tr>
                                    <th rowspan="2">组别</th>
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
                                  <th>申请金额</th>
                                  <th>申请单数</th>
                                  <th>申请金额</th>
                                </tr>
                            </thead>
                            <tbody id="tableTemplate" >
                                <tr>
                                    <td>A</td>
                                    <td>12</td>
                                    <td>4</td>
                                    <td>33</td>
                                    <td>38</td>
                                    <td>35</td>
                                    <td>63</td>
                                    <td>19%</td>
                                    <td>270</td>
                                    <td>19</td>
                                    <td>27</td>
                                    <td>19</td>
                                    <td>27</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

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
              <td>{{item.lossRate}}%</td>
              <td>{{item.oldMonthLossRate}}%</td>
              <td>{{item.monthLossRate}}%</td>
              <td>{{item.lossAmount}}</td>
              <td>{{item.successAmount}}</td>
              <td>{{item.totalAmount}}</td>
              <td>{{item.AmountLossRate}}%</td>
              <td>{{item.MonthAmountLossRate}}%</td>
              <td>{{item.MonthAmountLossRate}}%</td>
             </tr>
		{{/each}}
	    </script>     
        <script type="text/javascript">
        var ctx=$("#ctx").val();
        $(function(){
        	getGroup("ff8080814f459a78014f45a73d820006","true","districtId","group");
        	$("#districtId").change(function(item){
        		var parentId=$("#districtId").val();
        		getGroup(parentId,false,"orgId","group");
        		$("#userId").html("<option value='0'>请选择</option>");
        	})
        	$("#orgId").change(function(item){
        		var userId=$("#orgId").val();
        		getGroup(userId,false,"userId","user");
        	})
        	
        })
        function getGroup(parentId,gb,id,type){
        	  $.ajax({
                  url : ctx+"/rapidQuery/findPage",
                  method : "GET",
                  data : {
                	  parentId:parentId,
                	  gb:gb,
                	  queryId:'getGroup',
                	  searchType:type,
                	  pagination : false
                  },
                  dataType : "json",
                  async:true,
                  success : function(data) {
                	  var optionHtml="";
                	  optionHtml+="<option value='0'>请选择</option>"
                      $.each(data.rows,function(i,item){
                    	  optionHtml+="<option value="+item.id+">"+item.name+"</option>"
                      })
                      $("#"+id).html(optionHtml);
                  },
                  error:function(){}
              });

        }
        function reloadGrid() {
  		   	var year = window.parent.yearDisplay;
  	        var month_ = parseInt(window.parent.monthDisplay)+1;
  	        var month = month_ > 9 ? month_:("0"+month_)
  			var data = {
  				rows : 8,
  				page : 1,
  			};
  			var orgId="";
  			var userId="";
  			if($("#districtId").val()!=0){
  				orgId=$("#districtId").val();
  			}else if($("#orgId").val()!=0){
  				orgId=$("#orgId").val();
  			}else if($("#userId").val()!=0){
  				orgId="";
  				userId=$("#userId").val();
  			}
  			data.orgId=orgId;
  			data.userId=userId;
          	data.choiceMonth = year + "-" + month;
/*               	data.belongMoth  = getBelongMonth(year + "-" + month); */
  			var url = ctx+"/js/eachartdata/loanloss.json"
  			initData(url,data,"template_table","tableTemplate");
  		}
        </script>
    </body>
</html>
