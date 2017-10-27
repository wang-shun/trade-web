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
    <input type="hidden" id = "ransomCode" data="赎楼编号" value="${ransomCode }" />
    <input type="hidden" id = "partCode" data="赎楼编号" value="${caseVo.taskProperty }" />
    <input type="hidden" id = "count" data="" value="${count}" />
    
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content border-bottom clearfix space_box">
            <h2 class="title text-center">如修改已经的录入时间计划，需录入变更理由</h2>
            <form method="get" class="form_list text-center" id="time-info">
            	<div id="dataBody"></div>
           		<%--  <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small select_style mend_select" id="partCode0">申请时间</label>
                        <div class="input-group sign-right dataleft input-daterange" >
                          <c:if test="${planMap['APPLY']==null}">
                          	<input name="estPartTime0"  class="form-control data_style" type="text" value="" placeholder="">
                          </c:if>
                          <c:if test="${planMap['APPLY']!=null}">
                          	<input name="estPartTime0"  class="form-control data_style" type="text"   
                          	value="<fmt:formatDate value='${planMap[\'APPLY\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
                          </c:if>
                        </div>
                     </div>
                     <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          <c:if test="${planMap['APPLY']==null}">
                         	 <input name="remark0"  class="teamcode input_type" placeholder="" value="" />
                          </c:if>
                          <c:if test="${planMap['APPLY']!=null}">
                         	 <input name="remark0"  class="teamcode input_type" placeholder="" value="${planMap['APPLY'].remark }" />
                          </c:if>
                     </div>
           		</div>	
           		<div class="line">
                   <div class="form_content">
                       <label class="control-label sign_left_small select_style mend_select" id="partCode1">面签时间</label>
                       <div class="input-group sign-right dataleft input-daterange"  >
                       	<c:if test="${planMap['SIGN']!=null}">	
                       		<input name="estPartTime1"  class="form-control data_style" type="text" value="<fmt:formatDate value='${planMap[\'SIGN\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">	
                       </c:if>
                       <c:if test="${planMap['SIGN']==null}">
                       		<input name="estPartTime1"  class="form-control data_style" type="text" 
                       		value="" placeholder="">	
                       </c:if>
                       </div>
                    </div>
                    <div class="form_content">
                         <label class="control-label sign_left_small">变更理由</label>
                          <c:if test="${planMap['SIGN']!=null}">
	                         <input name="remark1"  class="teamcode input_type" placeholder="" value="${planMap['SIGN'].remark }" />
                          </c:if>
                           <c:if test="${planMap['SIGN']==null}">
	                         <input name="remark1"  class="teamcode input_type" placeholder="" value="" />
                          </c:if>
                    </div>
           		</div>	
           		<div class="line">
                   <div class="form_content">
                       <label class="control-label sign_left_small select_style mend_select" id="partCode2">陪同还贷时间</label>
                       <div class="input-group sign-right dataleft input-daterange" >
                       	<c:if test="${count == 0 }">
                       		<c:if test="${planMap['PAYLOAN_ONE']==null}">
                         		<input name="estPartTime2"  class="form-control data_style" type="text" value="" placeholder="">
                         	</c:if>
                         	<c:if test="${planMap['PAYLOAN_ONE']!=null}">
                         		<input name="estPartTime2"  class="form-control data_style" type="text"  
                         		value="<fmt:formatDate value='${planMap[\'PAYLOAN_ONE\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
                         	</c:if>
                         </c:if>
                         <c:if test="${count != 0 }">
                         	<c:if test="${planMap['PAYLOAN_TWO']==null}">
                         		<input name="estPartTime2"  class="form-control data_style" type="text" value="" placeholder="">
                         	</c:if>
                         	<c:if test="${planMap['PAYLOAN_TWO']!=null}">
                         		<input name="estPartTime2"  class="form-control data_style" type="text"  
                         		value="<fmt:formatDate value='${planMap[\'PAYLOAN_TWO\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
                         	</c:if>
                         </c:if>
                       </div>
                    </div>
                    <div class="form_content">
                         <label class="control-label sign_left_small">变更理由</label>
                         <c:if test="${count == 0 }">
                         	<c:if test="${planMap['PAYLOAN_ONE']==null}">
                         		<input name="remark2"  class="teamcode input_type" placeholder="" value="" />
                         	</c:if>
                         	<c:if test="${planMap['PAYLOAN_ONE']!=null}">
                         		<input name="remark2"  class="teamcode input_type" placeholder="" value="${planMap['PAYLOAN_ONE'].remark }" />
                         	</c:if>
                         </c:if>
                         <c:if test="${count != 0 }">
                         	<c:if test="${planMap['PAYLOAN_TWO']==null}">
                         		<input name="remark2"  class="teamcode input_type" placeholder="" value="" />
                         	</c:if>
                         	<c:if test="${planMap['PAYLOAN_TWO']!=null}">
                         		<input name="remark2"  class="teamcode input_type" placeholder="" value="${planMap['PAYLOAN_TWO'].remark }" />
                         	</c:if>
                         </c:if>
                    </div>
           		</div>	
           		<div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small select_style mend_select" id="partCode3">注销抵押时间</label>
                        <div class="input-group sign-right dataleft input-daterange"  >
                        	<c:if test="${count == 0 }">
	                        	<c:if test="${planMap['CANCELDIYA_ONE']==null}">
	                         		<input name="estPartTime3"  class="form-control data_style" type="text" value="" placeholder="">
	                         	</c:if>
	                         	<c:if test="${planMap['CANCELDIYA_ONE']!=null}">
	                         		<input name="estPartTime3"  class="form-control data_style" type="text"  
	                         		value="<fmt:formatDate value='${planMap[\'CANCELDIYA_ONE\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
	                         	</c:if>
                         	</c:if>
                         	<c:if test="${count != 0 }">
	                         	<c:if test="${planMap['CANCELDIYA_TWO']==null}">
	                         		<input name="estPartTime3"  class="form-control data_style" type="text" value="" placeholder="">
	                         	</c:if>
	                         	<c:if test="${planMap['CANCELDIYA_TWO']!=null}">
	                         		<input name="estPartTime3"  class="form-control data_style" type="text"  
	                         		value="<fmt:formatDate value='${planMap[\'CANCELDIYA_ONE\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
	                         	</c:if>
	                         </c:if>
                        </div>
                     </div>
                     <div class="form_content">
                          <label class="control-label sign_left_small">变更理由</label>
                          	<c:if test="${count == 0 }">
	                          	<c:if test="${planMap['CANCELDIYA_ONE']==null}">
	                       		 	<input name="remark3"  class="teamcode input_type" placeholder="" value="" />
	                        	</c:if>
	                        	<c:if test="${planMap['CANCELDIYA_ONE']!=null}">
	                        		 <input name="remark3"  class="teamcode input_type" placeholder="" value="${planMap['CANCELDIYA_ONE'].remark }" />
	                        	</c:if>
                        	</c:if>
                        	<c:if test="${count != 0 }">
	                        	<c:if test="${planMap['CANCELDIYA_TWO']==null}">
	                        		 <input name="remark3"  class="teamcode input_type" placeholder="" value="" />
	                        	</c:if>
	                        	<c:if test="${planMap['CANCELDIYA_TWO']!=null}">
	                        	 <input name="remark3"  class="teamcode input_type" placeholder="" value="${planMap['CANCELDIYA_TWO'].remark }" />
	                        	</c:if>
                        	</c:if>
                     </div>
           		</div>	
           		<div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small select_style mend_select" id="partCode4">领取产证时间</label>
                        <div class="input-group sign-right dataleft input-daterange">
                        	<c:if test="${count == 0 }">
	                          	<c:if test="${planMap['RECEIVE_ONE']==null}">
	                         		<input name="estPartTime4"  class="form-control data_style" type="text" value="" placeholder="">
	                         	</c:if>
	                         	<c:if test="${planMap['RECEIVE_ONE']!=null}">
	                         		<input name="estPartTime4"  class="form-control data_style" type="text"  
	                         		value="<fmt:formatDate value='${planMap[\'RECEIVE_ONE\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
	                         	</c:if>
	                         </c:if>
	                         <c:if test="${count != 0 }">
	                         	<c:if test="${planMap['RECEIVE_TWO']==null}">
	                         		<input name="estPartTime4"  class="form-control data_style" type="text" value="" placeholder="">
	                         	</c:if>
	                         	<c:if test="${planMap['RECEIVE_TWO']!=null}">
	                         		<input name="estPartTime4"  class="form-control data_style" type="text"  
	                         		value="<fmt:formatDate value='${planMap[\'RECEIVE_TWO\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
	                         	</c:if>
                         	</c:if>
                        </div>
                     </div>
                     <div class="form_content">
                         <label class="control-label sign_left_small">变更理由</label>
                         <c:if test="${count == 0 }">
                         	<c:if test="${planMap['RECEIVE_ONE']==null}">
                        		 <input name="remark4"  class="teamcode input_type" placeholder="" value="" />
                        	</c:if>
                        	<c:if test="${planMap['RECEIVE_ONE']!=null}">
                        		 <input name="remark4"  class="teamcode input_type" placeholder="" value="${planMap['RECEIVE_ONE'].remark }" />
                        	</c:if>
                       	</c:if>
                       	<c:if test="${count != 0 }">
                        	<c:if test="${planMap['RECEIVE_TWO']==null}">
                        		 <input name="remark4"  class="teamcode input_type" placeholder="" value="" />
                        	</c:if>
                        	<c:if test="${planMap['RECEIVE_TWO']!=null}">
                        		 <input name="remark4"  class="teamcode input_type" placeholder="" value="${planMap['RECEIVE_ONE'].remark }" />
                        	</c:if>
                        </c:if>
                     </div>
           		</div>	
	              		<div class="line">
		                    <div class="form_content">
		                        <label class="control-label sign_left_small select_style mend_select" id="partCode5">回款结清时间</label>
		                        <div class="input-group sign-right dataleft input-daterange"  >
		                          <c:if test="${planMap['PAYCLEAR']==null}">
	                         		<input name="estPartTime5"  class="form-control data_style" type="text" value="" placeholder="">
	                         	</c:if>
	                         	<c:if test="${planMap['PAYCLEAR']!=null}">
	                         		<input name="estPartTime5"  class="form-control data_style" type="text"  
	                         		value="<fmt:formatDate value='${planMap[\'PAYCLEAR\'].estPartTime}' pattern='yyyy-MM-dd' />" placeholder="">
	                         	</c:if>
		                        </div>
		                     </div>
		                     <div class="form_content">
		                          <label class="control-label sign_left_small">变更理由</label>
		                           <c:if test="${planMap['PAYCLEAR']==null}">
	                         		 <input name="remark5"  class="teamcode input_type" placeholder="" value="" />
	                         	</c:if>
	                         	<c:if test="${planMap['PAYCLEAR']!=null}">
	                         		 <input name="remark5"  class="teamcode input_type" placeholder="" value="${planMap['PAYCLEAR'].remark }" />
	                         	</c:if>
		                     </div>
	              		</div> --%>	
             		</form>
               		<div> 
						<div class="text-center">
							<a class="btn btn-success btn-space" href="javascript:submitChangeRecord()" style="width: 110px;">保存</a>
							<a class="btn btn-success btn-space" href="javascript:checkChangeRecord()" >变更记录查看</a>
						</div>
					</div>
                
        </div>
    </div>
        
        <content tag="local_script">
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
		<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
		<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
		
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
		<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
		<!-- 提示 -->
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        <script src="<c:url value='/js/poshytitle/src/jquery.poshytipuser.js' />"></script>
        <%-- <script src="<c:url value='/js/trunk/report/dealChangeList.js' />"></script>  --%>
        
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
        <script src="<c:url value='/js/ransom/ransomPlanTime.js'/>" type="text/javascript"></script>

		</content>
    </body>
</html>
