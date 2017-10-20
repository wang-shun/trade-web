<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>赎楼时间计划变更</title>
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
        <style>
    .add {
        border: 1px solid red;
    }
    .line{padding-left:27%;}
    .title{margin-top:0}
    #record-detail tr{height: 25px;}
</style>
    </head>
    <body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <input type="hidden" id = "ransomCode" data="赎楼编号" value="${caseVo.ransomCode }" />
    <input type="hidden" id = "caseCode" data="案件编号" value="${caseVo.caseCode }" />
    <input type="hidden" id = "taskCode" data="任务名称" value="${caseVo.taskProperty }" />
    
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content border-bottom clearfix space_box">
            <h2 class="title text-center">如修改已经的录入时间计划，需录入变更理由</h2>
            <form method="get" class="form_list text-center">
            	<c:forEach items="${planVo }" var="planVo" varStatus="status" begin="0" end="5">
	                   	<div class="line">
		                    <div class="form_content">
		                    	<c:choose>
		                    		<c:when test="${planVo.partCode == 'SIGN' }">
				                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">申请时间</label>
		                    		</c:when>
		                    		<c:when test="${planVo.partCode == 'APPLY' }">
				                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">面签时间</label>
		                    		</c:when>
		                    		<c:when test="${planVo.partCode == 'PAYLOAN_ONE' || planVo.partCode == 'PAYLOAN_TWO'  }">
				                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">陪同还贷时间</label>
		                    		</c:when>
		                    		<c:when test="${planVo.partCode == 'CANCELDIYA_ONE' || planVo.partCode == 'CANCELDIYA_TWO'  }">
				                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">注销抵押时间</label>
		                    		</c:when>
		                    		<c:when test="${planVo.partCode == 'RECEIVE_ONE' || planVo.partCode == 'RECEIVE_TWO'  }">
				                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">领取产证时间</label>
		                    		</c:when>
		                    		<c:when test="${planVo.partCode == 'PAYCLEAR'  }">
				                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">回款结清时间</label>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<label class="control-label sign_left_small select_style mend_select" style="display:none"></label>
		                    		</c:otherwise>
		                    	</c:choose>
		                        <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
		                          <input name="estPartTime${status.index }"  class="form-control data_style" type="text"  value="<fmt:formatDate value='${planVo.estPartTime }' pattern='yyyy-MM-dd'/>" placeholder="">
		                        </div>
		                     </div>
		                     <div class="form_content">
		                          <label class="control-label sign_left_small">变更理由</label>
		                          <input name="remark${status.index }"  class="teamcode input_type" placeholder="" value="${planVo.remark }" />
		                     </div>
	              		</div>
	              		</c:forEach>	
               		<div>
						<div class="text-center">
							<a class='btn btn-primary ' href="javascript:void(0)" onclick="submitChangeRecord(1)" style="width: 110px;">保存</a>
							<a class='btn btn-primary ' onclick = "submitChangeRecord(2)" href="javascript:void(0)" data-toggle="modal" data-target="#myModal">变更记录查看</a>
						</div>
					</div>
                </form>
        </div>
    </div>
        
        <content tag="local_script">
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
		<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
		<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
      	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
		<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
		<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
		<!-- 提示 -->
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytipuser.js' />"></script>
        <script src="<c:url value='/js/trunk/report/dealChangeList.js' />"></script> 
        
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
        <script src="<c:url value='/js/ransom/ransomPlanTime.js'/>" type="text/javascript"></script>
		</content>
    </body>
</html>
