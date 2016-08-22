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
	<!-- jQuery UI -->
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <!-- 分页控件 -->
    <link href="${ctx}/static/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
    <!-- owner -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/property/processingList.css" />
</head>

<body>
<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="userId" value="${userId}"/>
<input type="hidden" id="prStatus" value="2"/>

<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">我的产调结果</h2>
        <form method="get" class="form_list">
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left">产证地址</label>
                    <input id="addr" class="teamcode input_type" style="width:435px;" placeholder="请输入" value="">
                </div>
                <div class="form_content space">
                    <div class="add_btn">
                        <button type="button" id="addrSearchButton" class="btn btn-success"><i class="icon iconfont"></i>查询</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="resultGetList" class="table_content"></div>
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
    <!-- 组织 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>    
    <!-- 自定义扩展jQuery库 -->
    <script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
    <script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script>
    <!-- owner -->
    <script src="${ctx}/static/trans/js/property/resultGetList.js"></script>
    <script id="template_resultGetList" type= "text/html">
		{{each rows as item index}}
			<tr>
            	<td>
                	<p class="big deep_grey">{{item.DIST_CODE}}</p>
                	<p class="big deep_grey" style="color:#808080;">{{item.applyOrgName}}</p>
                </td>
                <td>
                	<p class="big tint_grey">
						<a href="../mobile/property/box/show?prCode={{item.PR_CODE}}" style="text-decoration: none;" target="_blank">{{item.PROPERTY_ADDR}}</a>
					</p>
                </td>
                <td>
                    <p class="smll_sign">
						{{if item.PR_APPLY_TIME == null}}
							<i class="sign_grey">申</i>
						{{else}}
							<i class="sign_normal">申</i>{{item.PR_APPLY_TIME}}
						{{/if}}
					</p>
                    <p class="smll_sign">
						{{if item.PR_ACCPET_TIME == null}}
							<i class="sign_grey">受</i>
						{{else}}
							<i class="sign_normal">受</i>{{item.PR_ACCPET_TIME}}
						{{/if}}
					</p>
                    <p class="smll_sign">
						{{if item.PR_COMPLETE_TIME == null}}
							<i class="sign_grey">完</i>
						{{else}}
							<i class="sign_normal">完</i>{{item.PR_COMPLETE_TIME}}
						{{/if}}
					</p>
                </td>
                <td>
                	<p class="smll_sign">
						{{if item.IS_SUCCESS == '是'}}
							<i class="sign_sharp52bdbd">是</i>
						{{else if item.IS_SUCCESS == '否'}}
							<i class="sign_grey">否</i><em style="word-break:break-all;font-size: 14px;font-style: normal;color: #808080;">{{item.UNSUCCESS_REASON}}</em>
						{{else}}
                 		{{/if}}
					</p>
                </td>
                <td class="center">
                    <span class="manager"><a href="#"><em>申请人：</em>{{item.PR_APPLIANT}}</a></span>
                    <span class="manager"><a href="#"><em>执行人：</em>{{item.PR_EXECUTOR}}</a></span>
                    <span class="manager"><a href="#"><em>区董：</em>{{item.orgMgr}}</a></span>
                </td>
                <td class="text-center">
					{{if item.PR_STATUS == '已完成'}}
						<a href="../mobile/property/box/show?prCode={{item.PR_CODE}}" class="btn btn-success" target="_blank">查看</a>
					{{else}}
                 	{{/if}}
                </td>				
			</tr>
		{{/each}}	
	</script>
</content>    
</body>
</html>
