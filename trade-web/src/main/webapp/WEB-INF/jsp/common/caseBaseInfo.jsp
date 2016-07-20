<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>		
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>
<style>
[class^=mark]{position:absolute;top:8px;left:130px;width:56px;height:37px;z-index:0; background-position:left center;background-repeat:no-repeat}
.mark-baodan{background-image:url(../img/mark-baodan.png);}
.mark-guaqi{background-image:url(../img/mark-guaqi.png);}
.mark-jiean{background-image:url(../img/mark-jiean.png);}
.mark-wuxiao{background-image:url(../img/mark-wuxiao.png);}
.mark-zaitu{background-image:url(../img/mark-zaitu.png);}
.bb {
	white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width:178.15px;
}
.hint {position: relative; display: inline-block;}

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}
/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}
</style>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox">
            <div class="ibox-title">
        		<h5>案件基本信息 </h5><small class="pull-right">誉萃编号：${caseBaseVO.toCase.caseCode}｜中原编号：${caseBaseVO.toCase.ctmCode}</small>
        	</div>
            <div class="ibox-content">
                <div  id="infoDiv" class="row">
                    <div class="col-lg-3">
                        <div  class="panel panel-success">
                            <div class="panel-heading">
                                物业信息                                            
                            </div>
                            <div class="panel-body" style="height:184px;">
                            	<p>CTM地址：${caseBaseVO.toPropertyInfo.ctmAddr}</p>
                                <p>产证地址：${caseBaseVO.toPropertyInfo.propertyAddr}</p>
                                <p>层高：${caseBaseVO.toPropertyInfo.locateFloor}／${caseBaseVO.toPropertyInfo.totalFloor}</p>
                                <p>产证面积：${caseBaseVO.toPropertyInfo.square}平方</p>
                                <p>房屋类型：<aist:dict id="propertyType" name="propertyType" display="label"  dictType = "30014" dictCode="${caseBaseVO.toPropertyInfo.propertyType}"/></p>
                                <p></p>
                                <p></p>
                            </div>

                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="panel panel-success bg-red">
                            <div class="panel-heading">
                                买卖双方
                            </div>
                            <div class="panel-body" style="height:184px;">
                            	<a class="hint hint-top" data-hint="${caseBaseVO.buyerSellerInfo.sellerName}">
                                	<p class="bb">上家姓名：${caseBaseVO.buyerSellerInfo.sellerName}</p>
                            	</a><br/>
                            	<a class="hint hint-top" data-hint="${caseBaseVO.buyerSellerInfo.sellerMobile}">
                                	<p class="bb">电话：${caseBaseVO.buyerSellerInfo.sellerMobile}</p>
                            	</a><br/>
                            	<a class="hint hint-top" data-hint="${caseBaseVO.buyerSellerInfo.buyerName}">
                                	<p class="bb">下家姓名：${caseBaseVO.buyerSellerInfo.buyerName}</p>
                            	</a><br/>
                            	<a class="hint hint-top" data-hint="${caseBaseVO.buyerSellerInfo.buyerMobile}">
                               		<p class="bb">电话：${caseBaseVO.buyerSellerInfo.buyerMobile}</p>
                            	</a><br/>
                                <p></p>
                                <p></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="panel panel-success bg-green">
                            <div class="panel-heading">
                               经纪人信息
                            </div>
                            <div class="panel-body" style="height:184px;">
                                <%-- <p>姓名：${caseDetailVO.agentName}</p> --%>
                                <p>姓名：${caseBaseVO.agentManagerInfo.agentName }</p>
                                <p>电话：${caseBaseVO.agentManagerInfo.agentPhone}</p>
                                <%-- <p>所属分行：${caseDetailVO.agentOrgName}</p> --%>
                                <p>所属分行：${caseBaseVO.agentManagerInfo.grpName }</p>
                                <p>直管经理：${caseBaseVO.agentManagerInfo.mcName}</p>
                                <p>经理电话：${caseBaseVO.agentManagerInfo.mcMobile}</p>
                                <p></p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3">
                        <div class="panel panel-success bg-blue">
                            <div class="panel-heading">
                                经办人信息
                            </div>
                            <div id="cpDiv" class="panel-body" style="height:184px;">
                                <p>交易顾问：${caseBaseVO.agentManagerInfo.cpName}</p>
                                <p>电话：${caseBaseVO.agentManagerInfo.cpMobile}</p>
                                 <c:if test="${empty caseBaseVO.agentManagerInfo.proList}">
                                <p>合作顾问：</p>
                                <p>电话：</p>
                                </c:if>
                                <c:if test="${!empty caseBaseVO.agentManagerInfo.proList}">
                                <c:forEach items="${caseBaseVO.agentManagerInfo.proList}" var="pro"> 
                                <p>合作顾问：${pro.processorName}</p>
                                <p>电话：${pro.processorMobile}</p>
                                </c:forEach>
                                </c:if>
                                <p>助理：${caseBaseVO.agentManagerInfo.asName}</p>
                                <p>电话：${caseBaseVO.agentManagerInfo.asMobile}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                       
		   <c:if test="${caseBaseVO.toCase.caseProperty == 30003001}">
           	<div class="mark-wuxiao"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003002}">
           	<div class="mark-jiean"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003003}">
           	<div class="mark-zaitu"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003004}">
           	<div class="mark-guaqi"></div>
           </c:if>
           <c:if test="${caseBaseVO.toCase.caseProperty == 30003005}">
           	<div class="mark-baodan"></div>
           </c:if>
        </div>
    </div>
</div>
