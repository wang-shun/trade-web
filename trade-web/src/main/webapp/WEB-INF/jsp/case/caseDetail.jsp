<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
	<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"	rel="stylesheet">
    <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"	rel="stylesheet">
    <style type="text/css">
	.checkbox.checkbox-inline>div {
		margin-left: 28px;
	}
	
	.checkbox.checkbox-inline>input {
		margin-left: 23px;
	}
	.modal-body{padding:10px!important}
	.wrapper-content{padding:0!important}
			.userHead{
	width: 80px;
		  height: 80px;
		  display: inline-block;
		  border-radius: 50%;
		  background-size: 80px 108px;
		  vertical-align: middle;
		  background-image:url(../img/a5.png);
	
}
[class^=mark]{position:absolute;top:8px;left:130px;width:56px;height:37px;z-index:0; background-position:left center;background-repeat:no-repeat}
.mark-baodan{background-image:url(../img/mark-baodan.png);}
.mark-guaqi{background-image:url(../img/mark-guaqi.png);}
.mark-jiean{background-image:url(../img/mark-jiean.png);}
.mark-wuxiao{background-image:url(../img/mark-wuxiao.png);}
.mark-zaitu{background-image:url(../img/mark-zaitu.png);}
</style>
</head>

<body>

	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ctm" value="${toCaseInfo.ctmCode}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="activityFlag" value="${toCase.caseProperty}"/>  
	<input type="hidden" id="caseCode" value="${toCase.caseCode}"/>  			
	<input type="hidden" id="instCode" value="${toWorkFlow.instCode}"/>
	<input type="hidden" id="srvCodes" value="${caseDetailVO.srvCodes}"/>
	<input type="hidden" id="processDefinitionId" value="${toWorkFlow.processDefinitionId}"/>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
			
			<script>
				<%if(request.getAttribute("msg") == null || request.getAttribute("msg") == ""){%>
				<%}
				else{%>
					alert("<%=request.getAttribute("msg") %>");
				<%}%>
			</script>
			
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                    	
                        <div class="ibox-title">
                    		<h5>案件基本信息 </h5><small class="pull-right">誉萃编号：${toCase.caseCode}｜中原编号：${toCase.ctmCode}</small>
                    	</div>
                        <div class="ibox-content">
                            <div  id="infoDiv" class="row">
                                <div class="col-lg-3">
                                    <div  class="panel panel-success">
                                        <div class="panel-heading">
                                            物业信息                                            
                                        </div>
                                        <div class="panel-body">
                                            <p>产证地址：${toPropertyInfo.propertyAddr}</p>
                                            <p>层高：${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</p>
                                            <p>产证面积：${toPropertyInfo.square}平方</p>
                                            <p>房屋类型：<aist:dict id="propertyType" name="propertyType" display="label"  dictType = "30014" dictCode="${toPropertyInfo.propertyType}"/></p>
                                            <p></p>
                                            <p></p>
                                        </div>

                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                            买卖双方
                                        </div>
                                        <div class="panel-body">
                                            <p>上家姓名：${caseDetailVO.sellerName}</p>
                                            <p>电话：${caseDetailVO.sellerMobile}</p>
                                            <p>下家姓名：${caseDetailVO.buyerName}</p>
                                            <p>电话：${caseDetailVO.buyerMobile}</p>
                                            <p></p>
                                            <p></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                           经纪人信息
                                        </div>
                                        <div class="panel-body">
                                            <p>姓名：${caseDetailVO.agentName}</p>
                                            <p>电话：${caseDetailVO.agentMobile}</p>
                                            <p>所属分行：${caseDetailVO.agentOrgName}</p>
                                            <p>直管经理：${caseDetailVO.mcName}</p>
                                            <p>经理电话：${caseDetailVO.mcMobile}</p>
                                            <p></p>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-lg-3">
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                            经办人信息
                                        </div>
                                        <div id="cpDiv" class="panel-body">
                                            <p>交易顾问：${caseDetailVO.cpName}</p>
                                            <p>电话：${caseDetailVO.cpMobile}</p>
                                             <c:if test="${empty caseDetailVO.proList}">
                                            <p>合作顾问：</p>
                                            <p>电话：</p>
                                            </c:if>
                                            <c:if test="${!empty caseDetailVO.proList}">
                                            <c:forEach items="${caseDetailVO.proList}" var="pro"> 
                                            <p>合作顾问：${pro.processorName}</p>
                                            <p>电话：${pro.processorMobile}</p>
                                            </c:forEach>
                                            </c:if>
                                            <p>助理：${caseDetailVO.asName}</p>
                                            <p>电话：${caseDetailVO.asMobile}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <c:if test="${caseDetailVO.caseProperty == 30003001}">
                        	<div class="mark-wuxiao"></div>
                        </c:if>
                        <c:if test="${caseDetailVO.caseProperty == 30003002}">
                        	<div class="mark-jiean"></div>
                        </c:if>
                        <c:if test="${caseDetailVO.caseProperty == 30003003}">
                        	<div class="mark-zaitu"></div>
                        </c:if>
                        <c:if test="${caseDetailVO.caseProperty == 30003004}">
                        	<div class="mark-guaqi"></div>
                        </c:if>
                        <c:if test="${caseDetailVO.caseProperty == 30003005}">
                        	<div class="mark-baodan"></div>
                        </c:if>
                    </div>
                </div>
            </div>
         	
            <%-- start 各个流程备注信息  --%>
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                        <div class="ibox-title">
                   			 <h5>流程备注</h5>
                   			 <div class="ibox-tools">
                                 <a class="collapse-link"><i class="fa fa-chevron-down"></i></a>
                             </div>
                    	</div>
                        <div class="ibox-content" style="display: none;">
                             <table id="caseCommenTable"></table>
                             <div id="caseCommenPager"></div>
            			</div>
            		</div>
            	</div>
            </div>
            <%-- end 各个流程备注信息  --%>
            
            
             <div  id="buttonDiv" class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                        <div class="ibox-title">
                    		<h5>案件基本操作</h5>
                    	</div>
                        <div class="ibox-content">
                            <div  class="row">
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUSPEND">  
                             <a role="button" id="casePause" class="btn btn-primary btn-xm" href="javascript:casePause()">案件挂起/恢复 </a>
                   			 </shiro:hasPermission>
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.PRARISE">  
                   			 <a role="button"data-toggle="modal" class="btn btn-primary btn-xm btn-activity" href="#pr-modal-form">产调发起 </a> 
                   			 </shiro:hasPermission>
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUPSTART">  
                             <a role="button" class="btn btn-primary btn-xm btn-activity" href="${ctx }/spv/toSaveSpv?caseCode=${toCase.caseCode}">房款监管</a>
                   			 </shiro:hasPermission>
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUPEND">  
                   			 <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:startSpvOutApplyProcess('${toCase.caseCode}')">监管解除</a> 
                   			 </shiro:hasPermission>
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.DAISHOU">  
                   			 <a role="button" class="btn btn-primary btn-xm btn-activity" href="http://shs-ctm01/centalineoa/portal/frmIndex.aspx" target="_blank">代收代付</a> 
                   			 </shiro:hasPermission>
                   			 <c:if test="${not empty toWorkFlow.processDefinitionId}" >
                        		<c:if test="${not empty toWorkFlow.instCode}" >
		                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.LEADCHANGE">  
		                   			 <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showOrgCp()">责任人变更</a>
		                   			 </shiro:hasPermission>
	                   			 </c:if>
                   			 </c:if>
                   			 <shiro:hasPermission name="TRADE.CASE.ORGC.CHANGE">  
                   			 <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:caseChangeTeam()">案件转组</a>
                   			 </shiro:hasPermission>
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.PLANCHANGE">  
                   			 <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showPlanModal()">交易计划变更</a>
                   			 </shiro:hasPermission>  
                            <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SERVCHANGE">  
                   			 <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showSrvModal()">服务项变更</a>  
                   			 </shiro:hasPermission>
                            <shiro:hasPermission name="TRADE.CASE.COWORKCHANGE">  
                   			 	<a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showChangeModal()">变更合作对象</a>  
                   			 </shiro:hasPermission>
                   			 <shiro:hasPermission name="TRADE.CASE.RESTART">  
                   			 	<a role="button" id="processRestart" class="btn btn-primary btn-xm btn-activity" href="javascript:serviceRestart()">流程重启</a>  
                   			 </shiro:hasPermission>
                   			 <shiro:hasPermission name="TRADE.CASE.RESET">
		                   	 	<a role="button" id="caseResetes" class="btn btn-primary btn-xm btn-activity" href="javascript:caseReset()">案件重置</a>
		                   	 </shiro:hasPermission>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- 案件转组 -->
            <div id="team-modal-form" class="modal fade" role="dialog" aria-labelledby="team-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:700px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="team-modal-title">
						      案件转组
						   </h4>
					   </div>
                       <div class="modal-body">
                       <div class="row">
                       <form  id="team-form" class="form-horizontal">
                       
                       
			            </form>
			            </div>
                     </div> 
                     <div class="modal-footer">
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="button" class="btn btn-primary" onclick="javascript:changeCaseTeam()">
			                                提交
			            </button>
                     </div>
                     </div>
                 </div>
             </div>  
            <!-- 案件转组 end -->
                  
            <!-- 责任人变更 -->
            <div id="leading-modal-form" class="modal fade" role="dialog" aria-labelledby="leading-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:1200px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="leading-modal-title">
						      	请选择交易顾问
						   </h4>
						</div>
                        <div class="modal-body">
                			 <div class="row"  style="height: 450px;overflow:auto; "> 
                                <div class="col-lg-12 ">
                                	<h3 class="m-t-none m-b"></h3>
									<div class="wrapper wrapper-content animated fadeInRight">
									    <div id="leading-modal-data-show" class="row">
	      								</div>
						            </div>															
						       </div>
						    </div>
	                    </div> 
                     </div>
                 </div>
             </div>  
             <!-- 产调发起 -->
            <div id="pr-modal-form" class="modal fade" role="dialog" aria-labelledby="pr-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:1200px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="pr-modal-title">
						      请选择产调项目
						   </h4>
						</div>
                        <div class="modal-body">
                			 <div class="row"> 
                                <div class="col-lg-12 checkbox i-checks checkbox-inline">
                                	<h3 class="m-t-none m-b"></h3>	
                                	<aist:dict id="pr_item" name="pr_item" display="checkbox"
									defaultvalue="" dictType="30009" />													
						       </div>
						    </div>
	                    </div> 
	                     <div class="modal-footer">
				            <button type="button" class="btn btn-default"
				               onclick="javascript:checkAllPRItem()">全选
				            </button>
				            <button type="button" class="btn btn-default"
				               onclick="javascript:cleanPRItem()">清空
				            </button>
				            <button type="button" class="btn btn-default"
				               data-dismiss="modal">关闭
				            </button>
				            <button type="button" class="btn btn-primary" onclick="javascript:startCasePrairses()">
				                                提交
				            </button>
				         </div>
                     </div>
                 </div>
             </div>  
             <!-- 交易计划变更 -->
            <div id="plan-modal-form" class="modal fade" role="dialog" aria-labelledby="plan-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:1000px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="plan-modal-title">
						      交易计划变更
						   </h4>
					   </div>
                       <div class="modal-body">
                       <div class="row">
                       <form  id="plan-form" class="form-horizontal">
                       
                       
			            </form>
			            </div>
                     </div> 
                     <div class="modal-footer">
                     
			            <button type="button" class="btn btn-primary" onclick="javascript:openTransHistory()">
			                              变更记录
			            </button>
			            <button type="button" class="btn btn-primary" onclick="javascript:resetPlanModal()">
			                              重置
			            </button>
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="button" class="btn btn-primary" onclick="return savePlanItems();">
			                                提交
			            </button>
                     </div>
                     </div>
                 </div>
             </div>  
            <!-- 服务项变更 -->
            <div id="srv-modal-form" class="modal fade" role="dialog" aria-labelledby="srv-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:700px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="srv-modal-title">
						      添加删除服务项
						   </h4>
					   </div>
                       <div class="modal-body">
                       <div class="row">
                       <form  class="form-horizontal">
                       <div class="form-group">
			                <div class="col-lg-3 control-label">
                                                                       原始服务项：					
						    </div>
						    <div class="col-lg-9 control-label" style="text-align:left" id="oldSrvs">					
						    </div>
			            </div>
								<div class="hr-line-dashed"></div>
			            <div class="form-group">
			                <div class="col-lg-3 control-label" >
                                                                       已选择服务项：						
						    </div>
			                <div class="col-lg-9 control-label" style="text-align:left" id="selectedSrvs">					
						    </div>
			            </div>
						<div class="hr-line-dashed"></div>
               			 <div class="form-group">
			                <div class="col-lg-3 control-label">
                                                                       服务项：						
						    </div>
			                <div class="col-lg-9 checkbox i-checks checkbox-inline">
                                	<aist:dict id="srvCode" name="srvCode" display="checkbox"
									defaultvalue="" dictType="yu_serv_cat_code_tree" level='2' />											
						    </div>
			            </div>
			            </form>
			            </div>
                     </div> 
                     <div class="modal-footer">
                     
			            <button type="button" class="btn btn-primary" onclick="javascript:resetSrvModal()">
			                              重置
			            </button>
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="button" class="btn btn-primary" onclick="javascript:saveSrvItems()">
			                                提交
			            </button>
                     </div>
                     </div>
                 </div>
             </div>  
             
             <!-- start 变更合作对象   -->	         
	         <div id="change-modal-form" class="modal fade" role="dialog" aria-labelledby="leading-modal-title" aria-hidden="true">
				<form action="${ctx}/case/updateCoope" method="post" class="form-horizontal">   
				<input type="hidden" name="instCode" value="${toWorkFlow.instCode}"/>  
				<input type="hidden" name="caseId" value="${toCase.pkid}"/>
	             <div class="modal-dialog" style="width:800px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="leading-modal-title">
						      	请选择服务项目
						   </h4>
						</div>
                        <div class="modal-body">
                			 <div class="row"  style="max-height: 400px;overflow-y:auto;overflow-x:hidden"> 
                                <div class="col-lg-12 ">
									<div class="wrapper wrapper-content animated fadeInRight">
									    <div id="change-modal-data-show" class="row">
	      								</div>
						            </div>															
						       </div>
						    </div>
	                    </div> 
	                    
	                    <div class="modal-footer">
				            <input type="submit" class="btn btn-primary" value="提交" />
				            <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
		                 </div>
                     </div>
                 
                 </div>
                 </form>
             </div>    
            <!-- end 变更合作对象   --> 
             
             <div class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                        <div class="ibox-title">
                   			 <h5>案件进程总览</h5>    
                   			     
                    	</div>
                        <div class="ibox-content">
                        	<!-- to do -->
                        	<c:if test="${not empty toWorkFlow.processDefinitionId}" >
                        		<c:if test="${not empty toWorkFlow.instCode}" >	
                         	<iframe  frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes” style="height: 750px;width: 100%;
                         	" src="<aist:appCtx appName='aist-activiti-web'/>/diagram-viewer/index.html?processDefinitionId=${toWorkFlow.processDefinitionId}&processInstanceId=${toWorkFlow.instCode}"></iframe>
                         		</c:if>
                         	</c:if>
            			</div>
            		</div>
            	</div>
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                        <div class="ibox-title">
                   			 <h5>操作纪录</h5>
                    	</div>
                        <div class="ibox-content">
                        	<!-- to do -->
                        	<div class="jqGrid_wrapper">
                                <table id="operation_history_table"></table>
                                <div id="operation_history_pager"></div>
                           </div>   
            			</div>
            		</div>
            	</div>
            </div>
            
              <div class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                        <div class="ibox-title">
                   			 <h5>案件信息全视图</h5>
                    	</div>
                        <div class="ibox-content">
                        	<!-- to do -->
								                            <div class="panel-body">
                                <div class="panel-group" id="accordion">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h5 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">交易相关信息</a>
                                            </h5>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse in">
                                            <div class="panel-body ibox-content">
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">派单时间：${caseDetailVO.createTime}</label>
                                                    <label class="col-sm-3 control-label">分单时间：${caseDetailVO.resDate}</label>
                                                    <label class="col-sm-3 control-label">签约时间：${caseDetailVO.realConTime}</label>
                                                </div>
                                                <div class="row ">
                                                	<label class="col-sm-3 control-label">核价时间：${caseDetailVO.pricingTime}</label>
                                                    <label class="col-sm-3 control-label">审税时间：${caseDetailVO.taxTime}</label>
                                                    <label class="col-sm-3 control-label">查限购时间：${caseDetailVO.realPlsTime}</label>
                                                    <label class="col-sm-3 control-label">过户时间：${caseDetailVO.realHtTime}</label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">领证时间：${caseDetailVO.realPropertyGetTime}</label>
                                                    <label class="col-sm-3 control-label">结案时间：${caseDetailVO.closeTime}</label>
                                                    <label class="col-sm-3 control-label">户口情况：${caseDetailVO.isHukou}</label>
                                                    <label class="col-sm-3 control-label">合同公证：${caseDetailVO.isConCert}</label>
                                                </div>
                                                <div class="row ">
                                                
                                                    <label class="col-sm-3 control-label">合同价：
                                                    	<c:if test="${!empty caseInfo.conPrice}">
			                                                ${caseInfo.conPrice/10000}  &nbsp&nbsp万元
			                                            </c:if>
                                                    </label> 
                                                    <label class="col-sm-3 control-label">成交价：
                                                        <c:if test="${!empty caseInfo.realPrice}">
			                                                ${caseInfo.realPrice/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">核定价格：
                                                        <c:if test="${!empty caseInfo.taxPricing}">
			                                                ${caseInfo.taxPricing/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">房屋性质：${caseDetailVO.houseProperty}</label>
                                                </div>
                                                <div class="row "> 
                                                    <label class="col-sm-3 control-label">付款金额(首付)：
                                                        <c:if test="${!empty caseInfo.amount1}">
			                                                ${caseInfo.amount1/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">付款金额(二期)：
                                                        <c:if test="${!empty caseInfo.amount2}">
			                                                ${caseInfo.amount2/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">付款金额(尾款)：
                                                        <c:if test="${!empty caseInfo.amount3}">
			                                                ${caseInfo.amount3/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">付款金额(装修)：
                                                        <c:if test="${!empty caseInfo.amount4}">
			                                                ${caseInfo.amount4/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                </div>
                                                <div class="row "> 
                                                    <label class="col-sm-3 control-label">付款方式(首付)：${caseDetailVO.payType1}</label>
                                                    <label class="col-sm-3 control-label">付款方式(二期)：${caseDetailVO.payType2}</label>
                                                    <label class="col-sm-3 control-label">付款方式(尾款)：${caseDetailVO.payType3}</label>
                                                    <label class="col-sm-3 control-label">付款方式(装修)：${caseDetailVO.payType4}</label>
                                                </div>
                                                <div class="row "> 
                                                    <label class="col-sm-3 control-label">付款时间(首付)：${caseDetailVO.payTime1}</label>
                                                    <label class="col-sm-3 control-label">付款时间(二期)：${caseDetailVO.payTime2}</label>
                                                    <label class="col-sm-3 control-label">付款时间(尾款)：${caseDetailVO.payTime3}</label>
                                                    <label class="col-sm-3 control-label">付款时间(装修)：${caseDetailVO.payTime4}</label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">购房年数：${caseDetailVO.holdYear}</label>
                                                    <label class="col-sm-3 control-label">唯一住房： ${caseDetailVO.isUniqueHome}</label>
                                                    <label class="col-sm-3 control-label">房产税：
                                                        <c:if test="${!empty caseInfo.houseHodingTax}">
			                                                ${caseInfo.houseHodingTax/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">契税：
                                                        <c:if test="${!empty caseInfo.contractTax}">
			                                                ${caseInfo.contractTax/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">个人所得税：
                                                        <c:if test="${!empty caseInfo.personalIncomeTax}">
			                                                ${caseInfo.personalIncomeTax/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">营业税：
                                                        <c:if test="${!empty caseInfo.businessTax}">
			                                                ${caseInfo.businessTax/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">土地增值税：
                                                        <c:if test="${!empty caseInfo.landIncrementTax}">
			                                                ${caseInfo.landIncrementTax/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                </div>
                                                <div class="row ">                                                    
                                                    <label class="col-sm-3 control-label">上家剩余贷款：
                                                        <c:if test="${!empty caseInfo.uncloseMoney}">
			                                                ${caseInfo.uncloseMoney/10000}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">还款方式： ${caseDetailVO.closeType}</label>
                                                    <label class="col-sm-3 control-label">还款时间：${caseDetailVO.loanCloseCode}</label>
                                                </div>
                                                <div class="row ">                                                    
                                                    <label class="col-sm-6 control-label">上家贷款银行：${caseInfo.upBank}</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">贷款相关信息</a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse">
                                            <div class="panel-body ibox-content">
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">签约时间：${caseDetailVO.signDate}</label>
                                                    <label class="col-sm-3 control-label">批贷时间：${caseDetailVO.apprDate}</label>
                                                    <label class="col-sm-3 control-label">他证送达时间：${caseDetailVO.tazhengArrDate}</label>
                                                   
                                                    <label class="col-sm-3 control-label">放款时间：${caseDetailVO.lendDate}</label>
                                                </div>
                                               	<div class="row ">
                                                    <label class="col-sm-3 control-label">贷款类型：${caseDetailVO.mortTypeName}</label>
                                                    <label class="col-sm-3 control-label">商贷金额：
                                                        <c:if test="${!empty toMortgage.comAmount}">
			                                                ${toMortgage.comAmount}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">商贷年限：${toMortgage.comYear}</label>
                                               	    <label class="col-sm-3 control-label">商贷利率：${toMortgage.comDiscount}</label>
                                                </div>
                                               	<div class="row ">
                                                    <label class="col-sm-3 control-label">是否自办：<c:choose>
                                                    <c:when test="${toMortgage.isDelegateYucui=='1'}">是</c:when>
                                                    <c:when test="${toMortgage.isDelegateYucui=='0'}">否</c:when>
                                                     <c:otherwise>
                                                     ${toMortgage.isDelegateYucui}
                                                     </c:otherwise>
                                                    </c:choose></label>
                                                    <label class="col-sm-3 control-label">公积金贷款金额：
                                                        <c:if test="${!empty toMortgage.prfAmount}">
			                                                ${toMortgage.prfAmount}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                    <label class="col-sm-3 control-label">公积金贷款年限：${toMortgage.prfYear}</label>
                                                    <label class="col-sm-3 control-label">放款方式：${caseDetailVO.lendWay}</label>
                                                </div>
                                               	<div class="row ">
                                                    <label class="col-sm-3 control-label">主贷人：${caseDetailVO.mortBuyer}</label>
                                                    <label class="col-sm-3 control-label">主贷人单位：${caseDetailVO.buyerWork}</label>
                                                    <label class="col-sm-3 control-label">房贷套数：${toMortgage.houseNum}</label>
                                                    <label class="col-sm-3 control-label">申请时间：${caseDetailVO.prfApplyDate}</label>
                                                </div>
                                                <div class="row ">
                                                     <label class="col-sm-6 control-label">贷款银行：${caseDetailVO.parentBankName}</label>
                                                     <label class="col-sm-6 control-label">支       行：${caseDetailVO.bankName}</label>
                                                </div>
                                               	<div class="row ">
                                                    <label class="col-sm-3 control-label">信贷员：${toMortgage.loanerName}</label>
                                                    <label class="col-sm-3 control-label">信贷员电话：${toMortgage.loanerPhone}</label>
                                                    <label class="col-sm-3 control-label">评估公司：${caseDetailVO.evaName}</label>
                                                    <label class="col-sm-3 control-label">评估费金额：
                                                        <c:if test="${!empty caseDetailVO.evaFee}">
			                                                ${caseDetailVO.evaFee}&nbsp&nbsp万元
			                                            </c:if>
                                                    </label>
                                                </div>
                                                <div class="row ">
                                                     <label class="col-sm-12 control-label">备注：${toMortgage.remark}</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">房款监管信息</a>
                                            </h4>
                                        </div>
                                        <div id="collapseThree" class="panel-collapse collapse">
                                            <div class="panel-body ibox-content">
									            <table id="gridTable1"></table>
									    		<div id="gridPager1"></div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive">金融服务信息</a>
                                            </h4>
                                        </div>
                                        <div id="collapseFive" class="panel-collapse collapse">
                                            <div class="panel-body ibox-content">
                                                <c:forEach var="toLoanAgent" items="${toLoanAgents}" varStatus="status">
                                                <c:forEach var="toLoanAgentVO" items="${toLoanAgentVOs}" begin="${status.index}" end="${status.index}">
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">客户姓名：${toLoanAgent.custName}</label>
                                                    <label class="col-sm-3 control-label">贷款服务：${toLoanAgentVO.loanSrvName}</label>
                                                    <label class="col-sm-6 control-label">贷款机构：${toLoanAgentVO.finOrgName}</label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">客户电话：${toLoanAgent.custPhone}</label>
                                                    <label class="col-sm-3 control-label">贷款金额：
                                                        <c:if test="${!empty toLoanAgent.loanAmount}">
			                                                ${toLoanAgent.loanAmount/10000}&nbsp&nbsp万元
			                                            </c:if></label>
                                                    <label class="col-sm-3 control-label">放款金额：
                                                        <c:if test="${!empty toLoanAgent.actualAmount}">
			                                                ${toLoanAgent.actualAmount/10000}&nbsp&nbsp万元
			                                            </c:if></label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">申请状态：${toLoanAgentVO.applyStatusName}</label>
                                                    <label class="col-sm-3 control-label">申请期数：
                                                        <c:if test="${!empty toLoanAgent.month}">
			                                                ${toLoanAgent.month}&nbsp&nbsp月
			                                            </c:if></label>
                                                    <label class="col-sm-3 control-label">转介人：${toLoanAgent.zjName}</label>
                                                    <label class="col-sm-3 control-label">转介人编号：${toLoanAgent.zjCode}</label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">确认状态：${toLoanAgentVO.confirmStatusName}</label>
                                                    <label class="col-sm-3 control-label">合作人：${toLoanAgent.coName}</label>
                                                    <label class="col-sm-3 control-label">合作人编号：${toLoanAgent.coCode}</label>
                                                    <label class="col-sm-3 control-label">分配比例：
                                                        <c:if test="${!empty toLoanAgent.awardPer}">
			                                                ${toLoanAgent.awardPer}&nbsp&nbsp%
			                                            </c:if></label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">申请时间：${toLoanAgentVO.applyTime}</label>
                                                    <label class="col-sm-3 control-label">确认时间：${toLoanAgentVO.confirmTime}</label>
                                                    <label class="col-sm-3 control-label">面签时间：${toLoanAgentVO.signTime}</label>
                                                    <label class="col-sm-3 control-label">放款时间：${toLoanAgentVO.releaseTime}</label>
                                                </div>
                                                <div class="row ">
                                                    <label class="col-sm-3 control-label">对账时间：${toLoanAgentVO.incomeConfirmTime}</label>
                                                    <label class="col-sm-3 control-label">结账时间：${toLoanAgentVO.incomeArriveTime}</label>
                                                    <label class="col-sm-3 control-label">超期导出时间：${toLoanAgentVO.lastExceedExportTime}</label>
                                                </div>
                                                <c:if test="${!status.last}">
			                                       <div class="hr-line-dashed"></div>
			                                    </c:if>
                                                </c:forEach>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">附件信息</a>
                                            </h4>
                                        </div>
                                        <div id="collapseFour" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div id="imgShow" class="">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseSix">CTM附件</a>
                                            </h4>
                                        </div>
                                        <div id="collapseSix" class="panel-collapse collapse">
                                            <div class="panel-body ibox-content">
										            <table id="gridTable"></table>
										    		<div id="gridPager"></div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        	
            			</div>
            		</div>
            	</div>
            </div>
        

<content tag="local_script">

    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>


    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
    <script	src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
    <script	src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	 <script src="${ctx}/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	 <script src="${ctx}/js/jquery.blockui.min.js"></script>
	 
	<script src="${ctx}/transjs/task/follow.pic.list.js"></script>
	<script src="${ctx}/js/trunk/case/caseDetail.js"></script>
	<script src="${ctx}/js/trunk/case/showCaseAttachment.js"></script>
	
	<!-- 放款监管信息  -->
	<script src="${ctx}/transjs/task/caseflowlist.js"></script>
	
	<!-- 各个环节的备注信息  -->
	<script src="${ctx}/js/trunk/case/caseRemark.js"></script>
	
</content>
</body>
</html>
