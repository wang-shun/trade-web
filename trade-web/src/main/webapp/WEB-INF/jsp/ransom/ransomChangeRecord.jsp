<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>交易变更记录查看</title>
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
    <input type="hidden" id = "ransomCode" value="${ransomCode }" />
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content border-bottom clearfix space_box">
                <h2 class="title text-center">变更记录查看详情</h2>
	        	<div  style="width: 1070px;height: 100%;top: 88px;">
					<table class="table table_blue table-striped table-bordered table-hover ">
						<thead>
							<tr>
								<th >赎楼编号</th>
								<th >环节</th>
								<th >变更前计划时间</th>
								<th >变更后计划时间</th>
								<th >变更人</th>
								<th >变更时间</th>
								<th >变更理由</th>							
							</tr>
						</thead>
						<tbody id="record-detail">
							<c:forEach items="${plans }" var="record">
								<tr>
									<td>${ransomCode }</td>
									<td>
										<aist:dict id="ransomPart" name ="ransomPart" display="label" dictType="RANSOM_PART_CODE"
													dictCode="${record.partCode }" />
									<td>
										<fmt:formatDate value='${record.oldEstPartTime }' pattern='yyyy-MM-dd'/>
									</td>
									<td>
										<fmt:formatDate value='${record.newEstPartTime }' pattern='yyyy-MM-dd'/>
									</td>
									<td>${record.changeName }</td>
									<td>
										<fmt:formatDate value='${record.changeTime }' pattern='yyyy-MM-dd'/>
									</td>
									<td>${record.reason }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
          			</div>
            </div>
            
           <div class="add_btn text-center mt20">
	   		<div class="more_btn">
   	    		<button id="closeButton" type="button" class="btn btn_blue" onclick="window.close()">关闭</button>
			</div>
		</div>
        </div>
        <content tag="local_script">
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
		<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
		<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
		<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
		<!-- 提示 -->
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
        <script src="<c:url value='/js/ransom/ransomChangeRecord.js'/>" type="text/javascript"></script>
		</content>
    </body>
</html>
