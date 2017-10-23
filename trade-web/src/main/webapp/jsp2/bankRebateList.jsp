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
    <span>返利提交列表</span>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="ibox-content bonus-m-con">
                            <div class="row">
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">申请单状态</label>
                                        <div class="col-lg-9 col-md-9">
                                            <select name="" class="form-control" id="caseStatus">
													<option value="" selected="selected">请选择</option>
													<option value="0">已提交</option>
													<option value="1">未提交</option>
												</select>
                                        </div>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">申请时间</label>
                                         <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
												<span class="input-group-addon">至</span> 
											<input id="dtEnd_0" name="dtEnd" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
										</div>
                                    </div>
                                </div>
                                 <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">申请人</label>
                                        <div class="col-lg-9 col-md-9">
                                        	<input type="text" class="form-control" id="applyer" name="applyer">
                                        </div>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">担保公司</label>
                                        <div class="col-lg-9 col-md-9">
                                        	   <select name="" class="form-control" id="finOrgId">
													<option value="" selected="selected">请选择</option>
													<option value="0">评估公司</option>
												</select>
                                        </div>
                                </div>
								
                            </div>
                <div class="row m-t-sm">
					<div class="form_content">
						<div class="more_btn">
							<button id="searchButton" type="button" class="btn btn-success">查询</button>
							<button id="myCaseListCleanButton" type="button" class="btn btn-grey">清空</button>
							<button id="addNewCase"  type="button" class="btn btn-success">新增</button>
							<button id="myCaseListCleanButton" type="button" class="btn btn-success" onclick="javascript:showExcelModal()">返利批量导入</button>
                           	<shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
								<a data-toggle="modal" class="btn btn-success" href="javascript:void(0)" onclick="javascript:showExcelIn()">下载Excel模板</a>
							</shiro:hasPermission>
							<button id="deleteButton" type="button" class="btn btn-success">删除</button>&nbsp;
						</div>
					</div>
				</div>
                           
                          
                        </div>
                    </div>
                 </div>
                 
                    <div class="bonus-table">
                        <table>
                            <thead>
                                <tr>
                                	<th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th>
                                    <th>申请时间
                                    </th>
                                    <th>担保公司</th>
                                    <th>返利总额</th>
                                    <th>
                                    	申请人
                                    </th> 
                                    <th>申请单状态</th>
                                    
                                    <th>操作</th>
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
         <!-- 返利导入 -->
        <div id="excel-modal-form" class="modal fade" role="dialog" aria-labelledby="excel-modal-title" aria-hidden="true">
          <div class="modal-dialog" style="width:1200px">
             <div class="modal-content">
             	<span>返利单批量导入》》</span>
                 <div class="modal-header">
				   <button type="button" class="close" data-dismiss="modal"
				      aria-hidden="true">×
				   </button>
				   <h4 class="modal-title" id="excel-modal-title">
				      	请上传附件
				   </h4>
				</div>
				
                <form id="excelInForm"  method="post" enctype="multipart/form-data"> 
                <div class="modal-body">
			               <div id="wrapper" class="Index">
			               		<div class="main-bonus">
				                   <div class="bonus-wrap">
				                       <div class="ibox-content bonus-m-con">
				                           <div class="row">
				                                <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">担保公司</label>
				                                       <div class="col-lg-9 col-md-9">
				                                       	   <select name="" class="form-control" id="finOrgId">
																<option value="" selected="selected">请选择</option>
																<option value="0">评估公司</option>
															</select>
				                                       </div>
				                               </div>
				                               <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">返利总金额</label>
				                                       <div class="col-lg-9 col-md-9">
				                                       	<input type="text" class="form-control" id="rebateMoney" name="rebateMoney">
				                                       </div>
				                               </div>
				                               <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">公司账户</label>
				                                       <div class="col-lg-9 col-md-9">
				                                       	   <select name="" class="form-control" id="finOrgId"  readonly="readonly">
																<option value="" selected="selected">请选择</option>
															</select>
				                                       </div>
				                               </div>
				                                <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">申请人</label>
				                                       <div class="col-lg-9 col-md-9">
				                                       	<input type="text" class="form-control" id="applyer" name="applyer" readonly="readonly" value="" >
				                                       </div>
				                               </div>
				                               <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">录入时间</label>
				                                       <div class="col-lg-9 col-md-9">
				                                       	<input type="text" class="form-control" id="applyTime" name="applyTime" readonly="readonly" value="" >
				                                       </div>
				                               </div>
				                               <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">录入人所在部门</label>
				                                       <div class="col-lg-9 col-md-9">
				                                       	<input type="text" class="form-control" id="applyTime" name="applyTime" readonly="readonly" value="" >
				                                       </div>
				                               </div>
				                               <div class="col-lg-5 col-md-5">
				                                       <label class="col-lg-3 col-md-3 control-label font_w">备注</label>
				                                       <textarea rows="2" cols="100"></textarea>
				                               </div>
				
				                           </div>
				                       </div>
				                   </div>
			                </div>
			              </div>
                <input type="hidden" id="inType" value="" />
           		    <label class="col-sm-7 control-label">
           		    <input id="file"  class="btn btn-default"  type="file" name="fileupload"  />
           		    </label>
         			 <div class="col-sm-3"></div>
                </div> 
                
              </form>
                <div class="modal-footer">
		            <button type="button" class="btn btn-default"
		               data-dismiss="modal">关闭
		            </button>
		            <button type="button" class="btn btn-primary" onclick="javascript:excelIn()">
		                                提交
		            </button>
                </div>
                
             </div>
          </div>
       </div> 
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
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
        <script	src="<c:url value='/js/trunk/bankRebate/bankRebateList.js' />"></script>
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
                                    <td>{{item.caseCode}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
									<td>{{item.FIN_ORG_ID}}</td>
                                    <td>{{item.EVAL_REAL_CHARGES}}</td>
									<td>{{item.EVA_PRICE}}</td>
									<td class="center">
										{{if item.STATUS=='4'}}
											<a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.caseCode}}" target="_blank">修改</a>
											<a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.caseCode}}" target="_blank">提交</a>
										{{/if}}
                        			</td>
                                </tr>
						{{/each}}
	    </script>
	    
	    </content> 
          </body>
</html>