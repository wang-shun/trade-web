<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<!-- 图标 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <!-- 分页控件 -->
    <link href="${ctx}/static/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<!-- aist列表样式 -->
<%--     <link href="${ctx}/static/css/common/aist.grid.css" rel="stylesheet"> --%>
    <!-- jQuery UI -->
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
	
	<script>
		var optTransferRole=false;
		<shiro:hasPermission name="TRADE.PRSEARCH.TRANSFER">
			optTransferRole=true;
		 </shiro:hasPermission>
	</script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<form action="#" method="post" id="excelForm"></form>
<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
<input type="hidden" id="prStatus" value="0"/>

<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">待处理产调</h2>
        <form method="get" class="form_list">
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left">行政区域</label>
                    <aist:dict id="distCode" clazz="select_control sign_right_one" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
                </div>
                <div class="form_content space">
                    <div class="add_btn">
                        <button type="button" id="searchButton" class="btn btn-success"><i class="icon iconfont"></i>查询</button>
                        <button type="button" id="expToexcel" class="btn btn-success" onclick="exportToExcel()">导出至Excel</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="processWaitList" class="table_content"></div>
        </div>
    </div>
</div>
<!--*********************** HTML_main*********************** -->
</div>
</div>
<content tag="local_script"> 
    <!-- js模板引擎 -->
    <script src="${ctx}/static/js/template.js"></script>
    <!-- 分页控件  -->
    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <!-- jQuery UI -->
    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <!-- blockUI -->
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
    <!-- 组织 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>    
    <!-- 自定义扩展jQuery库 -->
    <script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
    <script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
    <!-- owner -->    
    <script src="${ctx}/static/trans/js/property/processWaitList.js"></script>
	<script id="template_processWaitList" type= "text/html">
    	{{each rows as item index}}
 			<tr>
				<td>
					
					<p class="big deep_grey" style="color:#808080;">{{item.applyOrgName}}</p>
				</td>
				<td>
					<p class="big deep_grey">{{item.DIST_CODE}}</p>
					<p class="big tint_grey">{{item.PROPERTY_ADDR}}</p>
				</td>
				<td><p class="smll_sign"><i class="sign_normal">申</i>{{item.PR_APPLY_TIME}}</p></td>
                <td>
                	<span class="manager">
						<a href="#"><em>申请人：</em>{{item.PR_APPLIANT}}</a>
						{{if item.CHANNEL == '经纪人'}}
							<i class="sign_red">经</i>
						{{else}}
                 			<i class="sign_normal">誉</i>
						{{/if}}
					</span>
                    <span class="manager"><a href="#"><em>执行人：</em></a></span>
					<span class="manager"><a href="#"><em>区董：</em>{{item.orgMgr}}</a></span>
                </td>
				<td class="text-center">
					{{if wrapperData.optTransferRole}}
						<button type="button" id="teamCode" name="teamCode" class="btn btn-success" readonly="readonly" onclick="showOrgSelect({{item.PKID}})" value="{{item.prDistrictId}}">转组</button>
                	{{else}}

                 	{{/if}}
				</td>
			</tr>
		{{/each}}
	</script>
</content>
</body>
</html>
