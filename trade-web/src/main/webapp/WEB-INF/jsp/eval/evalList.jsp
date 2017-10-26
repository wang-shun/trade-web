<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">

<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/case/myCaseList.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/transcss/case/case_filter.css' />" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

<!-- index_css -->

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/workflow/myCaseList.css' />" />
<link rel="stylesheet" href="<c:url value='/css/workflow/newRecordpop.css' />" />
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />

<style type="text/css">
.radio label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}
.ui-state-hover{
	cursor:pointer;
}
.org-label-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 0px;
    color: inherit;
    display: block;
    width: 100%;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    font-size: 14px;
}
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
/* #inTextVal{width:50%} */
.chosen-container{float:left;margin-right:10px;}
#addLine{line-height:35px;}
.product-type span{margin-right:5px}	
.product-type .selected,.product-type span:hover{border-color:#f8ac59}

.product-type  .selected , .product-type span:hover{
	border-color: #1c84c6;
}
.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
.text-center{text-align:center;}
.slash{font-weight:bold !important;}
.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
#searchButton{margin-right:5px;}
.table_content .big a{
	min-width: 140px;
	display: inline-block;
}

.sign_right_one_case{
	width:130px;
}
</style>
</head>
<body>
<input type="hidden" id="userId" value="${userId}" />
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<%-- <jsp:include page="/WEB-INF/jsp/case/glCaseDiv.jsp"></jsp:include> 合流jsp/cyx--%>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h1 class="title">
                       评估总览列表
        </h1>
	    <form method="get" class="form-horizontal form_box">
			<div class="row clearfix">
        		<div class="form_content">
					<label class="sign_left_two control-label">评估状态</label>
					<div class="sign_right big_pad">
						<aist:dict id="eval_status" name="eval_status"  display="select" dictType="eval_status" clazz="select_control sign_right_one_case"/>
					</div>
				</div>
				
				<div class="form_content">
		            <label class="sign_left_two control-label">评估公司</label>
		            <div class="sign_right big_pad">
                    	<select class="form-control" name="finOrgId" id="finOrgId"></select>
		        	</div>
		        </div>
            </div>
			<div class="row clearfix">
				<div class="form_content" >
					<label class="sign_left_two control-label">是否关注</label>
					<div class="sign_right big_pad">
						<select name="" class="form-control" id="isSubscribeFilter">
							<option value="" selected="selected">请选择</option>
							<option value="0">已关注</option>
							<option value="1">未关注</option>
						</select>
					</div>
				</div>
				<div id="select_div_1" class="form_content">
           			<div class="sign_left_two">
                   	 	<select id="inTextType" data-placeholder="搜索条件设定" class="form-control" onchange="intextTypeChange()">
								<option value="1" selected>产权地址</option>
								<option value="2">案件编号</option>
								<option value="3">买方</option>
								<option value="4">卖方</option>
								<option value="5">经纪人姓名</option>
								<option value="6">贷款申请人</option>
								<option value="7">办理人</option>
					 	</select>
                 	</div> 
                    <div id="select_div_1" class="sign_right" style="width:300px;">
				 		<input id="inTextVal" type="text" class="form-control pull-left">
				 	</div>
	 		 	</div>	
             </div>
			 <div class="row date-info clearfix">
				<div class="form_content">
						<div id="dateDiv_0" style="padding-left:45px;">
							<div id="select_div_0" class="sign_left_two">
								<aist:dict id="eval_date" name="eval_date" clazz="form-control" display="select" defaultvalue="600001001" dictType="600001" />
							</div>
							<div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
								<input id="dtBegin" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
									<span class="input-group-addon">到</span> 
								<input id="dtEnd" name="dtEnd" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
							</div>
						</div>
				</div>
			</div>
			
	 		<div class="row m-t-sm">
				<div class="form_content">
				    <div class="more_btn">
						<button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>
						<button id="myEvalListCleanButton" type="button" class="btn btn-grey">清空</button>&nbsp;
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table class="table table_blue table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th></th>
								<th ><span class="sort" sortColumn="B.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th >产权地址</th>
								<th >评估公司</th>
								<th >买房</th>
								<th >卖方</th>
								<th >经纪人</th>
								<th >申请人</th>
								<th >办理人</th>
								<th >
								      上报时间
								      <p>
								          完成时间
								      </p>
								
								</th>
								<th >评估状态</th>
							</tr>
						</thead>
						<tbody id="evalList">

						</tbody>
					</table>
				</div>
			</div>
			
			<div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
		    </div> 	
	</div>
</div>
		
<input type="hidden" id="ctx" value="${ctx}" />
<%-- <input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
<input type="hidden" id="userId" value="${userId}" /> --%>


<!-- <form action="#" accept-charset="utf-8" method="post" id="excelForm"></form> cyx-->
<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
<script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script>
<script src="<c:url value='/js/trunk/eval/evalList.js' />"></script>  

<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 

<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

<script id="template_evalCaseList" type="text/html">

      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}	
                       <tr class="tr-2">
                   {{/if}}
						
						<td class="center">
							{{if item.aaa>0}}
								<img src="">
							{{/if}}
						</td>

						<td >
 							<p class="big">
								<a href="{{ctx}}/eval/detail?caseCode={{item.CASE_CODE}}&evaCode={{item.EVA_CODE}}"  target="_blank">{{item.EVA_CODE}}</a>
								{{if item.SUBSCRIBE_COUNT == 0}}
									<span style="cursor: pointer;" class="starmack subscribe"  moduleCode="{{item.EVA_CODE}}" isSubscribe="true">
										<i class="iconfont_s  markstar star_subscribe" status="1">&#xe644;</i>
									</span>
								{{else}}
									<span style="cursor: pointer;" class="starmack subscribe active"  moduleCode="{{item.EVA_CODE}}" isSubscribe="false">
										<i class="iconfont_s  markstar star_subscribe" status="1">&#xe63e;</i>
									</span>
								{{/if}}
                                
							</p>
                            <p>
								<i class="tag_sign">案</i>{{item.CASE_CODE}}
							</p>

						</td>

						<td class="center">
 							<p >
								<i class="sign_blue">{{item.PROPERTY_ADDR}}</i>
							</p>
						</td>
                          
                        <td class="center">
								{{item.FIN_ORG_NAME}}
						</td>
                        <td class="center">
                        	{{item.BUYER}}
                        </td>
                         <td class="center">
                        	{{item.SELLER}}
                       	</td>

                         <td class="center">
								{{item.AGENT_NAME}}
						</td>
                         <td class="center">
								{{item.LOANNAME}}
						</td>
                         <td class="center">
                                {{item.ASSITNAME}}
						</td>
                         <td class="center">
                                
                                             	{{if item.SYS_CREATE_TIME!=null}}
					                             <p>  
                    	                             <i class="sign_normal">上</i>
                                                      {{item.SYS_CREATE_TIME}}          
                	                             </p>
				                               {{else}}
                	                               <p>  
                    	                              <i class="sign_grey">上</i>
                    	                               {{item.SYS_CREATE_TIME}}          
                	                               </p>
				                              {{/if}}

					                          {{if item.SYS_FINSH_TIME!=null}}
						                            <p>  
                        	                            <i class="sign_normal">完</i>
                        	                             {{item.SYS_FINSH_TIME}}          
                    	                            </p>
					                        {{else}}
                    	                            <p>  
                        	                            <i class="sign_grey">完</i>
                        	                            {{item.SYS_FINSH_TIME}}          
                    	                            </p>
					                         {{/if}}                                
				
                          </td>

						<td class="center">
                               {{item.STATUS}}
						</td>
						
				  </tr>
       {{/each}}
</script> 

</content>
</body>
</html>
