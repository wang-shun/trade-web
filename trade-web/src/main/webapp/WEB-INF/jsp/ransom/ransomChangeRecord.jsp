<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <input type="hidden" id = "caseCode" value="${ransomTailinsVo.caseCode }" />
    <input type="hidden" id = "ransomCode" value="${ransomTailinsVo.ransomCode }" />
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content border-bottom clearfix space_box">
                <h2 class="title text-center">变更记录查看详情</h2>
	        	<div  style="width: 1070px;height: 500px;top: 88px;">
					<table class="table table_blue table-striped table-bordered table-hover ">
						<!-- <thead>
							<tr><th></th><th></th></tr>
						</thead> -->
						<tbody id="record-detail"></tbody>
					</table>
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
		
		<script id="template_timeRecord" type= "text/html">
			{{each rows as item index}}
				{{if item.PART_CODE == "APPLY" }}
					<tr>
						<td>申请时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.EST_PART_TIME}}</td>
						<td>{{item.REMARK}}</td>
					</tr>
				{{/if}}
				{{if item.PART_CODE == "SIGN" }}
					<tr>
						<td>面签时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.EST_PART_TIME}}</td>
						<td>{{item.REMARK}}</td>
					</tr>
				{{/if}}
				{{if item.PART_CODE == "PAYLOAN_ONE" || item.EST_PART_TIME == "PAYLOAN_TWO"}}
					<tr>
						<td>陪同还贷时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.EST_PART_TIME}}</td>
						<td>{{item.REMARK}}</td>
					</tr>
				{{/if}}
				{{if item.PART_CODE == "CANCELDIYA_ONE" || item.PART_CODE == "CANCELDIYA_TWO" }}
					<tr>
						<td>注销抵押时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.EST_PART_TIME}}</td>
						<td>{{item.REMARK}}</td>
					</tr>
				{{/if}}
				{{if item.PART_CODE == "RECEIVE_ONE" || item.PART_CODE == "RECEIVE_TWO" }}
					<tr>
						<td>领取产证时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.EST_PART_TIME}}</td>
						<td>{{item.REMARK}}</td>
					</tr>
				{{/if}}
				{{if item.PART_CODE == "PAYCLEAR" }}
					<tr>
						<td>回款结清时间</td>
						<td>变更理由</td>
					</tr>
					<tr>
						<td>{{item.EST_PART_TIME}}</td>
						<td>{{item.REMARK}}</td>
					</tr>
				{{/if}}
			{{/each}}
		</script>
		</content>
    </body>
</html>
