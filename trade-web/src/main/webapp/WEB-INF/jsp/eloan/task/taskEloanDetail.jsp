<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title>E+贷款详情</title>

<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet" />

<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css">
<link rel="stylesheet" href="${ctx}/static/css/style.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
<!-- stickUp fixed css -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_detail.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_guaranty.css">
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- aist列表样式 -->
<%-- <link href="${ctx}/css/common/aist.grid.css" rel="stylesheet"> --%>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
   <div id="wrapper">
            <!-- main Start -->
            <div class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
                <ul class="nav navbar-nav">
                    <li class="menuItem active"><a href="#reportOne">关联交易单</a></li>
                    <li class="menuItem"><a href="#reportTwo">贷款申请信息</a></li>
                    <li class="menuItem"><a href="#reportThree">借款人信息填写</a></li>
                    <li class="menuItem"><a href="#reportFour">担保人信息</a></li>
                    <li class="menuItem"><a href="#reportFive">申请材料</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                         <div class="row">
                            <div class="col-lg-9">
                                <div class="row" id="">
                                    <div class="col-lg-12">
                                        <div class="m-b-md">
                                            <h4>
                                            				<aist:dict id="loanSrvCode" name="loanSrvCode"
											clazz="select_control sign_right_two" display="onlyLabel"
											dictType="yu_serv_cat_code_tree" tag="Eloan"
											dictCode="${eloanCase.loanSrvCode}" ligerui='none'></aist:dict>
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="">
							<div class="col-lg-5" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>E+编号</dt>
									<dd>
										<a herf="#">${eloanCase.eloanCode }</a>
									</dd>
									<dt>产权地址</dt>
									<dd>${info.propertyAddr}</dd>
									<dt>合作机构</dt>
									<dd>${info.finOrgName}</dd>
									
									<c:if test="${eloanCase.finOrgCode == 'W0003' && eloanCase.month <= 12}">
										<dt>手续费</dt>
										<dd>${eloanCase.chargeAmount}</dd>
									</c:if>
									
									<c:if test="${!empty eloanCase.remark}">
										<dt>情况说明</dt>
										<dd>${eloanCase.remark}</dd>
									</c:if>
								</dl>
							</div>
							<div class="col-lg-4" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>申请时间</dt>
									<dd>${info.applyTime}</dd>
									<dt>申请期数</dt>
									<dd>${eloanCase.month}</dd>
								</dl>
							</div>
							<div class="col-lg-3">
								<dl class="dl-horizontal">
									<dt>借款人</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right"
											data-content="手机：${eloanCase.custPhone}">${eloanCase.custName}</a>
									</dd>
									<dt>贷款专员</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right" data-content="手机：${info.excutorPhone}">${info.excutorName}</a>
									</dd>
									<dt>贷款转介人</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.zjPhone}" --%>>${eloanCase.zjName}</span>
									</dd>
									<dt>合作信贷员</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.coPhone}" --%>>${eloanCase.coName}</span>
									</dd>
									<dt>产品部合作人</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.coPhone}" --%>>${eloanCase.pdName}</span>
									</dd>
								</dl>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="row bs-wizard"
									style="border-bottom: 0; margin-left: 15px">
									<div
										class="col-lg-4 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	
										">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>申请</strong>
													${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万
												</dd>
											</dl>
										</div>
									</div>
									<div
										class="col-lg-5 bs-wizard-step 
											   <c:choose>  
										    <c:when test="${info.status=='sign'}"> active
										   </c:when>  
										    <c:when test="${info.status=='confirmSign'||info.status=='release'}"> complete
										   </c:when>   
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	
										">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>签约</strong>
													${eloanCase.signAmount>0?eloanCase.signAmount:0}万
												</dd>
											</dl>
										</div>
									</div>
									

									<div
										class="col-lg-2 bs-wizard-step 
										<c:choose>  
  
										   <c:when test="${info.status=='release'}"> complete
										　　complete
										   </c:when>  
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>				
										">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>放款</strong>
													${info.releaseAmount>0?info.releaseAmount:0} 万
												</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div>
                            </div>
                            <div class="col-lg-3">
                                <div class="row">
									<div class="col-lg-12">
										<div class="m-b-md">
											<h4>关联案件</h4>
										</div>
									</div>
								</div>
								<div class="feed-activity-list">
									<div class="feed-element">
										<div class="pull-lef contract-icon-block">
											<div class="icon icon_blue iconfont">&#xe607;</div>
										</div>
										<div class="media-body">
											<strong><a>${eloanCase.caseCode }</a></strong><br />经办人 <strong>${info.jingbanName }</strong>
											<br />
											<!-- <small class="text-muted">2016-06-14 15:30 网签</small> -->
										</div>
									</div>
								</div>
                            </div>
                        </div>

                    </div>
                    
                    <div class="ibox-content">
			<div class="row" style="margin-top:12px">
					<div class="col-lg-12">
						<div class="m-b-md">
							<h4>放款记录</h4>
						</div>

					</div>
					<div class="main_titile">
						<div class="case_content">
							<c:forEach items="${eloanRelList}" var="item">
								<div class="case_row">
									<div class="case_lump">
										<p>
											<em>放款金额</em><span class="span_one" id="content_caseCode">${item.releaseAmount}万</span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>放款时间</em><span class="span_one" id="content_propertyAddr"><fmt:formatDate
													value="${item.releaseTime}" pattern="yyyy-MM-dd" /></span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>放款状态</em>
											<span class="" id="content_caseCode"> 
												<c:if	test="${item.confirmStatus==1}">	审批通过     </c:if> 
												<c:if 	test="${item.confirmStatus==2}">	审批拒绝     </c:if> 
												<c:if 	test="${item.confirmStatus==0}">	待确认	</c:if>
											</span>
										</p>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>

                    <div class="ibox-content" id="reportTwo">

                        <div class="row">
                                  <div class="fk-block col-lg-12">
                                        <div class="panel blank-panel">
                                            <div class="panel-heading">
                                                <div class="panel-options relative">
                                                    <ul class="nav nav-tabs">
                                                        <li class="active">
                                                            <a href="#tab-fk" data-toggle="tab">风控准备</a>
                                                        </li>
                                                        <!-- <li class="">
                                                            <a href="#tab-hk" data-toggle="tab">回款方案</a>
                                                        </li> -->
                                                       <!--  <li class="">
                                                            <a href="#tab-hz" data-toggle="tab">合作信息</a>
                                                        </li> -->
                                                    </ul>

                                                </div>
                                            </div>
                                            <div class="panel-body">

                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab-fk">
                                                       <div class="guaranty_btn">
                                                        <%-- <a href="${ctx}/riskControl/guarantycards?pkid=${pkId}"> --%>
                                                        <shiro:hasPermission name="TRADE.RISKCONTROL.ADD">
                                                        <button class="btn btn-success btn-space ykqing cardButton">押卡</button>
                                                        <%-- <a href="${ctx}/riskControl/guarantymortgage?pkid=${pkId}"> --%>
                                                        <button class="btn btn-success btn-space ykqing mortgageButton">抵押</button>
                                                        <%-- <a href="${ctx}/riskControl/guarantyfair?pkid=${pkId}"> --%>
                                                        <button class="btn btn-success btn-space ykqing forceFairButton">强制公正</button>
                                                        </shiro:hasPermission>
                                                        </div>
                                                        <div class="riskControl-table">
        												</div>
                                                        <!-- <table class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th>风控项目</th>
                                                                    <th>执行时间</th>
                                                                    <th>执行人</th>
                                                                    <th>备注</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                      <a href="#">押卡</a>
                                                                    </td>
                                                                    <td>2016-06-21 15:25</td>
                                                                    <td>杨忠梁</td>
                                                                    <td>客户按揭银行不能放到光大</td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                        <a href="#">抵押</a>
                                                                    </td>
                                                                    <td>2016-06-20 13:44</td>
                                                                    <td>安家贷</td>
                                                                    <td></td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                        <a href="#">强制公证</a>
                                                                    </td>
                                                                    <td>2016-06-20 09:00</td>
                                                                    <td>安家贷</td>
                                                                    <td></td>
                                                                </tr>

                                                            </tbody>
                                                        </table> -->
                                                    </div>
                                                    <div class="tab-pane" id="tab-hk">
                                                        <table class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th>版本号</th>
                                                                    <th>贷款金额</th>
                                                                    <th>需回款金额</th>
                                                                    <th>资金方</th>
                                                                    <th>资方账户</th>
                                                                    <th>生效时间</th>
                                                                    <th>提交人</th>
                                                                    <th>备注</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                       v2
                                                                    </td>
                                                                    <td>70万</td>
                                                                    <td>30万</td>
                                                                    <td>及时雨</td>
                                                                    <td>5021 2345 1222 5689 543</td>
                                                                    <td>2016-06-14 18:44</td>
                                                                    <td>杨忠梁</td>
                                                                    <td>提前还款50万元</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                       v1
                                                                    </td>
                                                                    <td>70万</td>
                                                                    <td>70万</td>
                                                                    <td>及时雨</td>
                                                                    <td>5021 2345 1222 5689 543</td>
                                                                    <td>2016-06-14 18:44</td>
                                                                    <td>杨忠梁</td>
                                                                    <td></td>
                                                                </tr>

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="tab-pane" id="tab-hz">
                                                        <table class="table table-striped">
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                       转介人
                                                                    </td>
                                                                    <td>夏诚</td>
                                                                    <td>200元</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                       金融贷款专员
                                                                    </td>
                                                                    <td>廖荣</td>
                                                                    <td>40%</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                       合作人
                                                                    </td>
                                                                    <td>马惠财</td>
                                                                    <td>10%</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                       产品部配合人
                                                                    </td>
                                                                    <td>杨忠梁</td>
                                                                    <td>10%</td>
                                                                </tr>

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                  </div>

                             </div>
                    </div>
                    <div class="ibox-content" id="reportThree" style="display:none;">
                    </div>
                    <div class="ibox-content" id="reportFour" style="display:none;">

                    </div>
                    <div class="ibox-content" id="reportFive" style="display:none;">

                    </div>

                </div>
            </div>
    </div>
	<!-- main End -->
	<content tag="local_script"> 
	   <script src="${ctx}/js/inspinia.js"></script> 
	   <script src="${ctx}/js/plugins/pace/pace.min.js"></script> 
	   <!-- 开关按钮js -->
       <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
       <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
       <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
       <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
       <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
       <script id="queryRiskControlList" type= "text/html">
                        {{each rows as item index}}
 				                 <tr>
                                    <td>{{item.RISK_TYPE}}</td>
                                    <td>{{item.CREATE_TIME}}</td>
                                    <td>{{item.CREATE_BY}}</td>
                                    <td>{{item.RISK_COMMENT}}</td>
									<td>
	                                    {{if item.CREATE_BY == wrapperData.userName}}
                                            <shiro:hasPermission name="TRADE.RISKCONTROL.ADD">
 											{{if item.RISK_TYPE == '押卡'}}
                                                <a href="${ctx}/riskControl/guarantycards?pkid={{wrapperData.pkId}}"> <button type="button" class="btn btn-success"> 修改  </button></a>
                                            {{else if item.RISK_TYPE == '抵押'}}
                                                <a href="${ctx}/riskControl/guarantymortgage?pkid={{wrapperData.pkId}}"><button type="button" class="btn btn-success"> 修改  </button> </a>
                                            {{else}}
                                                <a href="${ctx}/riskControl/guarantyfair?pkid={{wrapperData.pkId}}"> <button type="button" class="btn btn-success"> 修改  </button> </a>
                                            {{/if}}
                                            </shiro:hasPermission>
											<shiro:hasPermission name="TRADE.RISKCONTROL.DELETE">
                                            	<button type="reset" class="btn btn-grey" onclick="deleteRiskControl('{{item.PKID}}','{{item.RISK_TYPE}}','{{wrapperData.pkId}}')">删除</button>
											</shiro:hasPermission>
                                         {{/if}}
                                     </td>
                                </tr>
						{{/each}}
	    </script>
	   <script>
		   jQuery(document).ready(function() {
			   var eloanCode = "${eloanCase.eloanCode }";
			   var pkId = "${pkId}";
			   var userName = "${userName}";
			   $(".riskControl-table").aistGrid({
					ctx : "${ctx}",
					queryId : 'riskControlListQuery',
				    templeteId : 'queryRiskControlList',
				    gridClass : 'table table-striped',
				    data : {eloanCode : eloanCode},
				    wrapperData : {pkId : pkId,userName:userName},
				    columns : [{
				    	           colName :"风控项目"
				    	      },{
				    	           colName :"执行时间"
				    	      },{
				    	           colName :"执行人"
			    	          },{
		  		    	           colName :"备注"
				    	      },{
		  		    	           colName :"操作"
				    	      }]
				
				}); 
			   
			   $(".cardButton").click(function() {
				   var type="card";
				   if(isExistsType(type,eloanCode)) {
					   alert('已经存在该风控类型，你只能修改!');
				   } else {
					   window.location.href = "${ctx}/riskControl/guarantycards?pkid=${pkId}";
				   }
			   });
			   
 			   $(".mortgageButton").click(function() {
 				   var type="mortgage";
 				   if(isExistsType(type,eloanCode)) {
 					   alert('已经存在该风控类型，你只能修改!');
				   } else {
					   window.location.href = "${ctx}/riskControl/guarantymortgage?pkid=${pkId}";
				   }
			   });
 
 			   $(".forceFairButton").click(function() {
 				  var type="forceRegister";
 				  if(isExistsType(type,eloanCode)) {
 					 alert('已经存在该风控类型，你只能修改!');
				  } else {
					 window.location.href = "${ctx}/riskControl/guarantyfair?pkid=${pkId}";
				  }
 			   });
		   })
	  
			//点击浏览器任何位置隐藏提示信息
			$("body").bind("click", function(evt) {
				if ($(evt.target).attr("data-toggle") != 'popover') {
					$('a[data-toggle="popover"]').popover('hide');
				}
			});
		   // 验证押卡是否已经存在
		   function isExistsType(type,eloanCode) {
			    var isExist = false;
				var url = "${ctx}/riskControl/validateRiskControlType";
				$.ajax({
					cache : false,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					//contentType:"application/json",  
					data : {'type': type,'eloanCode':eloanCode},
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '1900'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '1900'
						});
					},
					complete : function() {
						$.unblockUI();
					},
					success : function(data) {
						isExist = data.ajaxResponse.success;
					},
					error : function(errors) {
						alert("数据保存出错");
					}
				});
				return isExist;
			}
		   
		   function deleteRiskControl(pkid,riskType,eloanPkId){
			   var url = "${ctx}/riskControl/deleteRiskControl?pkid="+pkid+"&riskType="+riskType+"&eloanPkId="+eloanPkId;
				$.ajax({
					cache : false,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					//contentType:"application/json",  
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '1900'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '1900'
						});
					},
					complete : function() {
						$.unblockUI();
					},
					success : function(data) {
						window.location.reload();
					},
					error : function(errors) {
						alert("数据保存出错");
					}
				});
		   }
	   </script> 
	</content>
</body>
</html>



