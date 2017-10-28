<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>首页</title>
       <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/fonts/font-awesome/css/font-awesome.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
        <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
        <%-- <link href="<c:url value='/css/style.min.css' />" rel="stylesheet">  --%>
        <link href="<c:url value='/css/transcss/award/bonus.css' />" rel="stylesheet">
        <!-- Gritter -->
        <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
        <!-- 分页控件 -->
        <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
        <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
	<link
	href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />"
	rel="stylesheet">
	<style>
		.bonus-m-con .bonus-search{margin-left:15px;}
		.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
.hideDiv{
display: none;}
	</style>
    </head>
    
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <span>评估结算案件列表</span>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="ibox-content bonus-m-con">
                            <div class="row">
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">评估公司</label>
                                        <div class="col-lg-9 col-md-9">
                                        	<select  id="finOrgId" class="form-control select_control">
											</select>
                                        </div>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">结算时间</label>
                                         <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value=""> 
											
										</div>
                                    </div>
                                </div>
                                 <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">案件状态</label>
                                        <div class="col-lg-9 col-md-9">
												<select name="" class="form-control" id="caseStatus">
													<option value="" selected="selected">请选择</option>
													<option value="4">已结算</option>
													<option value="3">未结算</option>
												</select>
                                          <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                </div>

                            </div>
                            <div >
	             		 		<div id="select_div_1" >
				           			<div >
		                                 <select id="inTextType"  class="form-control" data-placeholder="搜索条件设定" onchange="intextTypeChange()">
												<option value="1" selected>产证地址</option>
												<option value="0">案件编号</option>
												<option value="2">评估单编号</option>
												
										 </select>
										 <input id="inTextVal" type="text"  style="width:320px;overflow-x:visible;overflow-y:visible;">
										 <button id="searchButton" type="button" class="btn btn-success">查询</button>
											<a data-toggle="modal" class="btn btn-success" onclick="javascript:exportToExcel()"  href="" id="exportBtn">导出到excel</a>
										<button id="batEnd"  type="button" class="btn btn-success" disabled="true">批量结算</button> &nbsp;&nbsp;&nbsp;
				                     </div>
				                    
								 </div>
	                   			
							</div>
                          
                        </div>
                    </div>
                    </div>
                 
                    <div class="table_content">
                        <table class="table table_blue table-striped table-bordered table-hover ">
                            <thead>
                                <tr>
                                	<th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th>
                                    <th>
                                    	案件编号
                                    <!-- <p>案件编号</p>
                                    	<p>评估单编号</p> -->
                                    
                                    </th>
                                    <th>产证地址</th>
                                    <th>评估公司</th>
                                    <th>
                                    	<p class="aa">评估申请日期</p>
                                    	<p class="aa">&nbsp;&nbsp;&nbsp;&nbsp;出报告日期</p>
                                    </th> 
                                    <th>评估费实收</th>
                                    <th>评估值</th>
                                    <!-- <th>返利金额</th> -->
                                    <th>结算费用</th>
                                    <th>贷款权证</th>
                                    <th>案件状态</th>
                                    <th>结算时间</th>
                                </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                                              
                            </tbody>
                        </table>                     
                    </div>
                	<div class="text-center">
						<span id="currentTotalPage"><strong class="bold"></strong></span>
						<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
						<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
				    </div>
                </div>
            </div>
            <!-- /Main view -->
        </div>
         <form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="<c:url value='/js/bootstrap.min.js' />"></script> --%>
       <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
        <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
         <!-- 日期控件 -->
    	<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
        <!-- Custom and plugin javascript -->
        <script src="<c:url value='/js/inspinia.js' />"></script>
        <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
        <!-- 弹出框插件 -->
	    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
        <!-- 分页控件  -->
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
        <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
        <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
        <script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
         <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        <!-- 评估结算  -->
        <script	src="<c:url value='/js/trunk/eval/settle/evalEndList.js' />"></script>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="{{item.caseCode}}" 
					 
					 caseCode="{{item.caseCode}}" />
                    <input id='caseCode' type='hidden' name='case_code' value="{{item.caseCode}}"/>
					<input type='hidden' name='pkId' value="{{item.pkid}}" disabled>
					<input type='hidden' name='taskIds' value="{{item.ID}}" disabled>
					
				</td>
                                     <td class="demo-left">{{item.caseCode}}
									</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
									<td>{{item.EVA_COMPANY}}</td>
									<td>
										<p>评：{{item.APPLY_DATE}}</p>
										<p>出：{{item.ISSUE_DATE}}</p>
									</td>
                                    <td>{{item.EVAL_REAL_CHARGES}}</td>
									 <td>{{item.EVA_PRICE}}</td>
                                    <td>{{item.SETTLE_FEE}}</td>
                                    <td>小张</td>
									<td>
									{{if item.STATUS=='3'}}
						 				<span class="yes_color">未结算</span>
					    			{{/if}}
									{{if item.STATUS=='4'}}
						 				<span class="yes_color">已结算</span>
					    			{{/if}}
									</td>
									<td>{{item.SETTLE_TIME}}</td>
                                </tr>
						{{/each}}
	    </script>
	    
	    
	    </content> 
          </body>
</html>