<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>过户数据</title>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet" />
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
<!-- index_css -->

<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/plugins/pager/centaline.pager.css' />" />
    </head>
    <body style="background-color:#fff;">
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row">
                    <div class="col-md-12">
                        <div class="clearfix mb30">
                            <h3 class="content-title pull-left">过户数据</h3>
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
                            <div class="form_content" style="height: 42px;overflow: hidden;">
                             	      <select  id ="districtId" class="select_control mr5">
                                    <option value="">
                                        请选择
                                    </option>
                                </select>
                                <select id="orgId" class="select_control">
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
                        <div class="table-scroll">
                            <!-- table -->
                            <table class="table table_blue  table-striped table-bordered table-hover customerinfo" >
                                <thead>
                                    <tr>
                                        <th>类型</th>
                                        <th>单数</th>
                                        <th>占比</th>
                                        <th>合同价</th>
                                        <th>商贷金额</th>
                                        <th>公积金金额</th>
                                        <th>贷款成数</th>
                                    </tr>
                                </thead>
                                <tbody id="tableTemplate">
                                    <tr>
                                        <td>总计</td>
                                        <td>12</td>
                                        <td>4%</td>
                                        <td>33</td>
                                        <td>38</td>
                                        <td>35</td>
                                        <td>19%</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div style="height: 23px;line-height: 23px;"><i class="icon iconfont icon40 yellow martop20" style="font-size: 30px;float: left;"></i>数据来源:
                    <span>当月过户审批通过案件</span>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

        <!-- Mainly scripts -->
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script src="<c:url value='/js/bootstrap.min.js' />"></script>
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

		<!-- 分页控件  -->
		<script src="<c:url value='/static/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
		<script src= "<c:url value='/static/js/template.js' />" type="text/javascript" ></script>
		<script src="<c:url value='/static/js/plugins/aist/aist.jquery.custom.js' />"></script>
		<!-- 排序插件 -->
		<script src="<c:url value='/static/js/plugins/jquery.custom.js' />"></script>	
			<!-- 个人js -->
		<script src="<c:url value='/js/trunk/report/getTemplateData.js' />"></script>
		<script id="template_table" type="text/html">
          {{each rows as item index}}
          <tr>
              <td>{{item.MORT_TYPE_CN}}</td>
              <td>{{item.CASE_COUNT}}</td>
              <td>{{(item.CASE_COUNT_PERCENT*100).toFixed()}}%</td>
              <td>{{(item.CASE_CON_PRICE/10000).toFixed()}}万元</td>
              <td>{{(item.MORT_COM_AMOUNT/10000).toFixed()}}万元</td>
              <td>{{(item.MORT_PRF_AMOUNT/10000).toFixed()}}万元</td>
              <td>{{(item.MORT_PERCENT*100).toFixed()}}%</td>
             </tr>
		{{/each}}
	    </script>
       	<script type="text/javascript">
		var ctx = $("#ctx").val();
		 $(function(){
	        	getGroup("ff8080814f459a78014f45a73d820006","true","districtId",null);
	        	$("#districtId").change(function(item){
	        		var parentId=$("#districtId").val();
	        		getGroup(parentId,"false","orgId",null);
	        	})
/* 	        	$("#orgId").change(function(item){
	        		var userId=$("#orgId").val();
	        		getGroup(userId,false,"userId","user");
	        	}) */
	        	
	        })
	        function getGroup(parentId,gb,id,type){
	        	  $.ajax({
	                  url : ctx+"/rapidQuery/findPage",
	                  method : "GET",
	                  data : {
	                	  parentId:parentId,
	                	  gb:gb,
	                	  queryId:'getGroup',
	                	  type:type,
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
				page : 1
			};
	        var orgId="";
  			if($("#districtId").val()!=0&&$("#orgId").val()==0){
  				orgId=$("#districtId").val();
                data.districtId=orgId;
  			}
            if($("#orgId").val()!=0&&$("#districtId").val()!=0){
  				orgId=$("#orgId").val();
                data.teamId=orgId;
  			}
            data.pagination=false
        	data.choiceMonth = year + "-" + month;
            data.belongMonth=getBelongMonth(data.choiceMonth);
            data.queryId='queryGuoHuForMortOrg';
/*         	data.belongMoth  = getBelongMonth(year + "-" + month); */
			var url = ctx+"/quickGrid/findPage"
			initData(url,data,"template_table","tableTemplate");
		}
	</script>
    </body>
</html>
