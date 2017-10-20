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
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
		<!-- 备件相关结束 -->
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet" />
	
    </head>
    
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <span>返利申请》》新增</span>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
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
                                        <div class="col-lg-9 col-md-9">
                                        <textarea rows="2" cols="100"></textarea>
                                        </div>
                                </div>

                            </div>
                        </div>
                    </div>
                 </div>
               </div>
               <span>返利分配情况列表</span>
               <div class="bonus-table">
                        <table>
                            <thead>
                                <tr>
                                    <th>成交报告编号
                                    </th>
                                    <th>按钮</th>
                                    <th>银行名称</th>
                                    <th>
                                    	返利金额
                                    </th> 
                                    <th>权证返利</th>
                                    
                                    <th>业务返利</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                                   <tbody>
									<tr>
										<td id="trId0">
										<input id="loanMoney" name="loanMoney" value=""  type="text" class="form-control input-one" placeholder="请在此输入成交编号" value=""> 
										</td>
										<td>
											<a href="">查看成交</a>
										</td>
										<td>
											<aist:dict id="diyaType" name="diyaType" clazz=" select_control yuanwid " display="select" dictType="71015" defaultvalue="" />
										</td>
										<td>
											<input id="rebateMoney" name="loanMoney" value="" type="text" class="form-control input-one" placeholder="" value="" /> 
									    </td>
										<td>
											<input id="warrantMoney" name="restMoney" value="" type="text" class="form-control input-one" placeholder="" value="" /> 
										</td>
										<td>
											<input id="businessMoney" name="" value="" type="text" class="form-control input-one" placeholder="" value="" /> 
										</td>
										<td>
										<a href="javascript:removeTr(0);">删除</a>
										</td>
									</tr>
									</tbody>
								<input type="hidden" id="addInput" /> 
                        </table>
                        <div id="addMoneyLine" class="input-group" style="">
								<a class="" href="javascript:addOrg();"><font>添加新记录</font></a>
						</div>
                        	<div class="form_content">
								<label class="control-label sign_left_small" >已录入金额</label> 
								<input type="text" class="input_type yuanwid" id="borrowerMoney"
									name="borrowerMoney" 
									value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
								<label class="control-label sign_left_small" >与总金额的差额</label> 
								<input type="text" class="input_type yuanwid" id="borrowerMoney"
									name="borrowerMoney" 
									value="" readonly="readonly" >
							</div>  
                   </div>
         <form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
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
        <script	src="<c:url value='/js/trunk/bankRebate/bankRebate.js' />"></script>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="bankRebate" type= "text/html">
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