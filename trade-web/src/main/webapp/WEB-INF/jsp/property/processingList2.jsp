<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
	<!-- 图标 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />

    <script type="text/javascript">
		var idList = [ 1 ];
		var pkid ='';
		var taskitem = "";
		var caseCode = "";
		var prCode = '';
	</script>
</head>

<body>

<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">已受理产调</h2>
        <form id="myForm" action="${ctx }/quickGrid/findPage?xlsx" method="post" class="form_list">
			<input type="hidden" name="queryId" value="queryProcessingList">
			<input type="hidden" id="ctx" value="${ctx}"/>
			<input type="hidden" name="search_prDistrictId" value="${prDistrictId}">
			<input type="hidden" name="search_prStatus" value="1"/>
			<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
			<input type="hidden" id="prStatus" value="1"/>
			<input type="hidden" name="colomns" value="DIST_CODE,PROPERTY_ADDR,PR_CAT,orgName,applyOrgName,orgMgr,PR_APPLIANT,PR_APPLY_TIME,PR_ACCPET_TIME,PR_STATUS,IS_SUCCESS,UNSUCCESS_REASON">            
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left">行政区域</label>
                    <aist:dict id="distCode" clazz="select_control sign_right_one" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
                </div>
                <div class="form_content">
                    <label class="control-label sign_left">产证地址</label>
                    <input type="text" id="addr" name="search_propertyAddr" class="teamcode input_type" style="width:435px;" placeholder="请输入" value="">
                </div>
                <div class="form_content space_two">
                    <div class="add_btn">
                        <button id="addrSearchButton" type="button" class="btn btn-success"><i class="icon iconfont"></i>查询</button>
                        <button id="exportExcelButton" type="button" class="btn btn-success">导出至Excel</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="processingList" class="table_content"></div>
        </div>
    </div>
</div>
<!--*********************** HTML_main*********************** -->
</div>
</div>
</body>
<content tag="local_script">
    <!-- js模板引擎 -->
    <script src="${ctx}/static/js/template.js"></script>
    <!-- 分页控件  -->
    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <!-- 组织 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>    
    <!-- 自定义扩展jQuery库 -->
    <script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
    <script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
    <!-- owner -->
    <script src="${ctx}/static/trans/js/property/processingList.js"></script>
    <script id="template_processingList" type="text/html">
		{{each rows as item index}}
			<td>
				<p class="big deep_grey">{{item.DIST_CODE}}</p>
				<p class="big deep_grey" style="color:#808080;"></p>
			</td>
            <td>
                <p class="big tint_grey"></p>
            </td>
            <td>
                <p class="smll_sign"><i class="sign_normal">申</i></p>
                <p class="smll_sign"><i class="sign_normal">受</i></p>
            </td>
            <td>
                 <p class="smll_sign"><i class="sign_grey">否</i>78787878</p>
            </td>
            <td>
            	<span class="manager"><a href="#"><em>申请人：</em></a>　<i class="sign_red">经</i></span></span>
                <span class="manager"><a href="#"><em>执行人：</em></a></span>
            </td>
            <td class="text-center">
                                            <i class="iconfont icon_revise">
                                                &#xe603;
                                            </i>
            </td>
		{{/each}}
	</script>
	<script>
		  template.helper("rep", function(a){  
	          return a.replace(/[\r\n]/g,"");  
	      });
	</script>    
</content>
</html>
