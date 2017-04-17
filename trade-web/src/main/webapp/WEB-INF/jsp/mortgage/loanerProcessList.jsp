<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/style.css" rel="stylesheet" />
<!-- Data Tables -->
<link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"	rel="stylesheet">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/css/btn.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
<style type="text/css">
    .moneydata {
        display: inline-block;
        width: 140px;
        font-size: 12px;
    }
    .ratedata  {
        display: inline-block;
        width: 103px;
        font-size: 12px;
    }
    .dataheight {
        height: 45px!important;
    }
    
    
    .sum_marb {
        margin-bottom: 0;
    }
    .loan_sum {
        display: inline-block;
        width: 115px;
    }
</style>
</head>

<body>
   <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
   <div class="wrapper wrapper-content animated fadeInRight">
       <div class="ibox-content border-bottom clearfix space_box">
           	  <h2 class="title">派单列表</h2>
              <form method="get" class="form_list">
                  <div class="line">
                      <div class="form_content">
                          <label class="control-label sign_left_small">案件编号</label>
                          <input class="teamcode input_type" placeholder="请输入"  id="caseCode" name="caseCode" value=""/>
                      </div>
                      <div class="form_content">
                          <label class="control-label sign_left_small">信贷员</label>
                          <input class="teamcode input_type" placeholder="请输入" value=""  name="loanerName" id="loanerName"  onclick="loanerClick()"/>                          
                          <div class="input-group float_icon organize_icon">
                              <i class="icon iconfont"  onclick="loanerClick()">&#xe627;</i>
                          </div>
                          
                   		 <input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
						 <input type="hidden" id="loanerOrgId" name ="loanerOrgId" />
						 <input type="hidden"  id="loanerId" name="loanerId" />
                      </div>
                  </div>
                  <div class="line">
                  		<div class="form_content">
                          <label class="control-label sign_left_small">银行</label>
                          <input class="teamcode input_type" placeholder="请输入" value="" />
	                    </div>
	                    <div class="form_content">
                          <label class="control-label sign_left_small">产证地址</label>
                          <input class="teamcode input_type" style="width:435px;" name="propertyAddr" id="propertyAddr" placeholder="请输入" value="" />
                      </div>
                  </div>
                  <div class="form_content space">
                          <div class="add_btn" style="margin-left:122px;">
                              <button type="button" class="btn btn-success mr5" id="loanerConditionSearch">
                                  <i class="icon iconfont">&#xe635;</i>查询</button>
                              <button type="button" class="btn btn-grey mr5"  id="loanerConditionClean">清空</button>
                          </div>
                   </div>
              </form>
          </div>
          <div class="row">
              <div class="col-md-12">
                  <div class="table_content">
                      <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                          <thead>
                              <tr>
								<th><span class="sort" sortColumn="CASE_CODE" sord="desc"
									onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti"
									class="fa fa-sort-desc fa_down"></i></th>
                                  <th>银行</th>
                                  <th>信贷员</th>
                                  <th>上家</th>
                                  <th>下家 </th>
                                  <th>操作</th>
                              </tr>
                          </thead>
                          <tbody id="loanerProcessList"></tbody>
                     </table>
				</div>			
				
				<div class="text-center page_box">
					<span id="currentTotalPage"><strong></strong></span> <span
						class="ml15">共<strong id="totalP"></strong>条
					</span>&nbsp;
					<div id="pageBar" class="pagergoto"></div>
				</div>    
              </div>
          </div>
    </div>


	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="userJobCode" value="${userJobCode}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
	

	<content tag="local_script"> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
	<script	src="${ctx}/js/plugins/chartJs/echarts.js" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script	src="${ctx}/js/jquery.blockui.min.js"></script> 
	<script	src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> 
	<script	src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
	<script	src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>	
	<script src="${ctx}/js/trunk/task/loanerProcessList.js"></script> 	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
	
	<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
	<!-- 排序插件 --> 
	<script	src="${ctx}/js/plugins/jquery.custom.js"></script> 

	<!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<!-- 日期拖拽 -->
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.js"></script>	
	<script src="${ctx}/js/plugins/moment/moment-with-locales.js"></script>
		
	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
	
	<script	id="template_loanerProcessList" type="text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}   
                       <td>
							<p class="big"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" target="_blank">{{item.CASE_CODE}}</a></p>
                                  
							
                            {{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
								<p class = "demo-top"  title = "{{item.PROPERTY_ADDR}}">{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
							{{else}}
								</p>
								<p class = "demo-top">{{item.PROPERTY_ADDR}}
							{{/if}}	
                                </p>
                       </td>

                        <td><p class="big">{{item.FIN_ORG_NAME}}-{{item.FIN_ORG_NAME_YC}}</p></td>

                        <td>{{item.LOANER_NAME}}</td>

                        <td class="center">{{item.SELLER}}</td>
                        <td class="center">{{item.BUYER}}</td>

                        <td><button class="btn btn-grey" onclick="finishLoanerProcess({{item.INST_CODE}},{{item.CASE_CODE}},{{item.ID_}})">取消</button></td>
                     </tr>

                     <tr class="dataheight">
                        <td colspan="6">
                        <i class="sign_blue mr5">{{item.MORT_TYPE_CN}}</i>
                        <span class="moneydata mr10">贷款总额： <em>{{item.MORT_TOTAL_AMOUNT}}万</em></span>
                        <span class="moneydata mr10">商贷金额： <em>{{item.COM_AMOUNT}}万</em></span>
                        <span class="ratedata mr10">商贷利率： <em>{{item.COM_DISCOUNT}}%</em></span>
                        <span class="ratedata mr10">商贷年限： <em>{{item.COM_YEAR}}年</em></span>
						</td>
					</tr>				
       {{/each}}
     </script> 
     <script type="text/javascript">
			$(function(){				
				//top
				$('.demo-top').poshytip({
					className: 'tip-twitter',
					showTimeout: 1,
					alignTo: 'target',
					alignX: 'center',
					alignY: 'top',
					offsetX: 8,
					offsetY: 5,
				});
				
			});
	</script> 
</content>
</body>
</html>