<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<title>赎楼修改</title>
<link rel="stylesheet"
	href="<c:url value='/static/css/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<!-- index_css  -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/uplodydome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_guaranty.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css' />"
	type="text/css" />
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet"/>
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet"/>
<link href="<c:url value='/css/animate.css' />" rel="stylesheet"/>
<link href="<c:url value='/css/style.css' />" rel="stylesheet"/>
<!-- Data Tables -->
<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" rel="stylesheet"/>
<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" rel="stylesheet"/>
<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" rel="stylesheet"/>
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" ">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 提示 -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" />
<style type="text/css">
.restMoney {
	
}
.diyaType{
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" value="${caseVo.caseCode }" id="caseCode">
<input type="hidden" value="${caseVo.ransomCode }" id="ransomCode">
<input type="hidden" value="${count }" id="count">
<div class="wrapper wrapper-content animated fadeInUp">
	<form method="post" class="form-horizontal" id="dataForm">
		<div class="ibox-content" id="reportOne">
			<h4 class="title">客户需求修改</h4>
			<div class="form_list">
				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">
							<font color=" red" class="mr5">*</font>主贷人姓名</label> 
							<select class="input_type yuanwid" id="borrowerName">
								<!-- <option value="">请选择</option> -->
								<option value="${guestInfo.get(0).guestName }">${guestInfo.get(0).guestName }</option>
								<option value="${guestInfo.get(1).guestName }">${guestInfo.get(1).guestName }</option>
							</select>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"><font color=" red" class="mr5">*</font>电话</label> 
							<input type="text" class="input_type yuanwid" id="borrowerPhone" name="borrowerPhone" value="${guestInfo.get(1).guestPhone }"/>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small select_style mend_select">
								<font color=" red" class="mr5">*</font>受理时间 
							</label> 
							<div class="input-group sign-right dataleft input-daterange" >
								<input type="text" class="form-control data_style" id="signTime" name="signTime" 
								value="<fmt:formatDate value='${tailinsVoList[0].signTime }' pattern='yyyy-MM-dd'/>" />
							</div>
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<table class="table table_blue table-striped table-bordered table-hover ">
								<thead>
									<tr>
										<td>尾款机构</td><td>尾款类型</td><td>抵押类型</td><td>贷款金额</td><td>剩余金额</td>
									</tr>
								</thead>
								<tbody id="bank-org">
									<c:forEach items="${tailinsVoList }" var="tailinsVo" varStatus="status">
										<tr id="tr${status.index }">
											<td>
											<select id="finOrgCode${status.index }" name="finOrgCode" class= "select_control yuanwid " ></select>
											<aist:dict id="finOrgCode${status.index }" name="finOrgCode" clazz=" select_control yuanwid " display="select" 
											dictType="RETAINAGE_TYPE" defaultvalue="${tailinsVo.finOrgCode }" />
											<%-- <select id="finOrgCode${status.index }" name="finOrgCode" class= "select_control yuanwid " ></select> --%>
											</td>
											<td>
												<aist:dict id="mortgageType${status.index }" name="mortgageType" clazz=" select_control yuanwid " 
												display="select" dictType="30016" defaultvalue="${tailinsVo.mortgageType }" />
											</td>
											<td>
												<input type="hidden" id="diyaType${status.index }" name="partCode" value="${tailinsVo.diyaType}">
												<aist:dict id="${tailinsVo.diyaType }" name="diyaType" clazz="select_control data_style diyaType" 
												display="label" dictType="71015" dictCode="${tailinsVo.diyaType }" defaultvalue="${tailinsVo.diyaType}" />
											</td>
											<td>
												<input id="loanMoney${status.index }" name="loanMoney" type="text" 
												class="input_type yuanwid" placeholder="单位：万元"  
												value="<fmt:formatNumber value='${ tailinsVo.loanMoney/10000 }' type='number' pattern='#0.00' />" 
												onkeyup="checkNum(this)">万
										    </td>
											<td>
												<input id="restMoney${status.index }" name="restMoney" type="text" 
												class="input_type yuanwid" placeholder="单位：万元" 
												value="<fmt:formatNumber value='${tailinsVo.restMoney/10000 }' type='number' pattern='#0.00' />" 
												onkeyup="checkNum(this)">万
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<input type="hidden" id="addInput" />
							</table>
							<div class="form_content">
								<label class="control-label sign_left_small">借款金额总计</label> 
								<input type="text" class="input_type yuanwid" id="borrowerMoney"name="borrowerMoney" 
								value="<fmt:formatNumber value='${ caseVo.borroMoney /10000}' type='number' pattern='#0.00' />" onkeyup="checkNum(this)">
								<span>万</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
			<h4 class="title">作业信息修改</h4>
			<hr>
			<table class="table table_blue table-striped table-bordered table-hover ">
				<thead>
					<tr class="center">
						<td>已完成环节</td>
						<td>完成日期</td>
						<td>执行人</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="work-info">
					<c:if test="${taskVo.applyCode != null }">
						<tr>
							<td>申请</td>
							<td><fmt:formatDate value='${taskVo.applyTime }' pattern='yyyy-MM-dd'/></td>
							<td>${taskVo.doUser }</td>
							<td><a href="${ctx}/ransomChange/applyView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.applyCode }">前往修改</a></td>
						</tr>
					</c:if>
					<c:if test="${taskVo.signCode != null }">
						<tr>
							<td>面签</td>
							<td><fmt:formatDate value='${taskVo.signTime }' pattern='yyyy-MM-dd'/></td>
							<td>${taskVo.doUser }</td>
							<td><a href="${ctx}/ransomChange/signView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }">前往修改</a></td>
						</tr>
					</c:if>
					<c:if test="${count == 0 }">
						<c:if test="${taskVo.payOneCode != null }">
							<tr>
								<td>陪同还贷</td>
								<td><fmt:formatDate value='${taskVo.payOneTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/payloanView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=${count}">前往修改</a></td>
							</tr>
						</c:if>
						<c:if test="${taskVo.cancelOneCode != null }">
							<tr>
								<td>注销抵押</td>
								<td><fmt:formatDate value='${taskVo.cancelOneTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/cancelView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=${count}">前往修改</a></td>
							</tr>
						</c:if>
						<c:if test="${taskVo.receiveOneCode != null }">
							<tr>
								<td>领取产证</td>
								<td><fmt:formatDate value='${taskVo.receiveOneTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/permitView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=${count}">前往修改</a></td>
							</tr>
						</c:if>	
					</c:if>
					<c:if test="${count == 1 }">
						<c:if test="${taskVo.payOneCode != null }">
							<tr>
								<td>陪同还贷(一抵)</td>
								<td><fmt:formatDate value='${taskVo.payOneTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/payloanView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=0">前往修改</a></td>
							</tr>
						</c:if>
						<c:if test="${taskVo.payTwoCode != null }">
							<tr>
								<td>陪同还贷(二抵)</td>
								<td><fmt:formatDate value='${taskVo.payTwoTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/payloanView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=${count}">前往修改</a></td>
							</tr>
						</c:if>
						<c:if test="${taskVo.cancelOneCode != null }">
							<tr>
								<td>注销抵押(一抵)</td>
								<td><fmt:formatDate value='${taskVo.cancelOneTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/cancelView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=0">前往修改</a></td>
							</tr>
						</c:if>
						<c:if test="${taskVo.cancelTwoCode != null }">
							<tr>
								<td>注销抵押(二抵)</td>
								<td><fmt:formatDate value='${taskVo.cancelTwoeTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/cancelView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=${count}">前往修改</a></td>
							</tr>
						</c:if>
						<c:if test="${taskVo.receiveOneCode != null }">
							<tr>
								<td>领取产证(一抵)</td>
								<td><fmt:formatDate value='${taskVo.receiveOneTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/permitView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=0">前往修改</a></td>
							</tr>
						</c:if>	
						<c:if test="${taskVo.receiveTwoCode != null }">
							<tr>
								<td>领取产证(二抵)</td>
								<td><fmt:formatDate value='${taskVo.receiveTwoTime }' pattern='yyyy-MM-dd'/></td>
								<td>${taskVo.doUser }</td>
								<td><a href="${ctx}/ransomChange/permitView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.signCode }&count=${count}">前往修改</a></td>
							</tr>
						</c:if>	
					</c:if>
					<c:if test="${taskVo.paymentCode != null }">
						<tr>
							<td>回款结清</td>
							<td><fmt:formatDate value='${taskVo.paymentTime }' pattern='yyyy-MM-dd'/></td>
							<td>${taskVo.doUser }</td>
							<td><a href="${ctx}/ransomChange/paymentView?ransomCode=${caseVo.ransomCode}&partCode=${taskVo.applyCode }">前往修改</a></td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<div>
				<div class="text-center">
					<input type="button" class="btn btn-primary" onclick="submitUpdateRansom()" value="保存" />
					<a class='btn btn-primary ' id="close" onclick="window.close()" >关闭</a>
				</div>
			</div>
		</div>
    <content tag="local_script">
    
    <script src="<c:url value='/js/trunk/report/dealChangeList.js' />"></script> 
	<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/js/ransom/ransomDetailUpdate.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
    <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
    <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<!-- 提示 -->
    <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
    <script src="<c:url value='/js/poshytitle/src/jquery.poshytipuser.js' />"></script>
    
    <script src="<c:url value='/js/ransom/ransomPlanTime.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
		<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
		<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
      	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
		<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
		<!-- 提示 -->
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytipuser.js' />"></script>
        <script src="<c:url value='/js/trunk/report/dealChangeList.js' />"></script> 
        
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
        <script src="<c:url value='/js/ransom/ransomPlanTime.js'/>" type="text/javascript"></script>
       	<script src="<c:url value='/js/common/common.js' />"></script> 
		</content>
	<script>
	    $(document).ready(function () {
	        $('.input-daterange').datepicker({
	            keyboardNavigation: false,
	            forceParse: false,
	            autoclose: true
	        });
	        getEvaFinOrg('finOrgCode');
	    });
	    
	    function getEvaFinOrg(name){
			var url = "/manage/queryTailins";
			$.ajax({
				async: true,
				type:'POST',
				url:ctx+url,
				dataType:'json',
				success:function(data){
					var html = '';
					if(data != null){
						$.each(data,function(i,item){
							html += '<option value="'+item.finOrgCode+'">'+item.finOrgName+'</option>';
						});
					}					
					$('[name='+name+']').empty();
					$('[name='+name+']').append(html);
				},
				error : function(errors) {
				}
			});
		}
</script>
</body>
</html>