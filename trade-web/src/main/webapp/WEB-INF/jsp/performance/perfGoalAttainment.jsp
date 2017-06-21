<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/trans/css/award/baseAward.css">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<!-- 图标 -->
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" rel="stylesheet">

<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />

<script type="text/javascript">
	var ctx = "${ctx}";
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	
	<div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <div class="clearfix"> 
                        <p class="month pull-left">
                            <button type="button" name="dateButton" class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow"></i></button>
                            <span id="month">2016/10月</span>
                            <button name="dateButton" class="btn btn-success btn_small pull-left";><i class="icon iconfont icon_arrow"></i></button>
                        </p>
                        <h2 class="title pull-left ml15">
                            业绩达成情况
                        </h2>
                    </div>
	                    <div class="form_list">
	                        <div class="form_content">
	                        <label class="control-label sign_left_small">案件组织</label>
	                        <input id="teamCode" name="teamCode" class="teamcode input_type" placeholder="请选择"  readonly="readonly" value="" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
								startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})" />
							<input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">
	                        <div class="input-group float_icon organize_icon" style="cursor:pointer;" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
								startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})">
	                            <i class="icon iconfont">&#xe61b;</i>
	                        </div>
	                    </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">
                                    查看单位
                                </label>
                                <input type="hidden" id="dataView" value="${dataView }">
                                <select class="select_control sign_right_one" id="sel_team">
                                	<option value="0" id ="0">
                                    	 请选择  
                                    </option>
                                </select>
                            </div>
                            <div class="form_content"  style="margin-left: 127px;">
                                <div class="add_btn">
                                    <button type="searchButton" class="btn btn-success mr5 btn-icon" onclick="javascript:searchButton()">
                                        <i class="icon iconfont"></i>
                                        查询
                                     </button>
                                    <button type="button" class="btn btn-success" id="exportBtn">
                                        导出为Excel
                                    </button>
                                    <button type="button" class="btn btn-success" onclick="javascript:add()">
                                        新增业绩目标
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            
                        </div>
                    </div>
                </div>
            </div>

	<content tag="local_script"> 
	<!-- datepicker -->
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <!-- js模板引擎 -->
    <script src="${ctx}/static/js/template.js"></script>
    <!-- 分页控件  -->
    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <!-- dateSelect -->
    <script	src="${ctx}/static_res/js/plugins/dateSelect/dateSelect.js"></script>  
    
    <!-- 自定义扩展jQuery库 -->
    <script src="${ctx}/static_res/js/plugins/aist/aist.jquery.custom.js"></script>
    <script src="${ctx}/static_res/js/plugins/jquery.custom.js" ></script>
    <!-- owner -->
    <script src="${ctx}/static/trans/js/award/baseAwardReport.js"></script>
	<!-- 日期控件 -->
    <script src="${ctx}/js/inspinia.js"></script>
    <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
	
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script> </script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/static/js/template.js" type="text/javascript"></script>
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script src="${ctx}/static_res/trans/js/performance/perfGoalAttainment.js"></script>  
	
	
	</script>
	<script
		id="template_successList" type="text/html">
	{{each rows as item index}}
    	<tr>
			<td>{{item.viewObject}}</td>
			<td>{{item.belongMonth}}</td>
			<td>
				{{if item.goalPerf != null}}
					{{item.goalPerf/10000}}万
				{{/if}}
			</td>
			<td>
				{{if item.shareAmount != null}}
					{{item.shareAmount/10000}}万
				{{/if}}
			</td>
			<td>
				{{if item.completionRate != null}}
					{{item.completionRate*100}}%
				{{/if}}
			</td>
			<td>{{item.countPerf}}</td>
			<td>
				{{if item.ry == null}}
					<a href="{{ctx}}/trade-web/perf/receivablePerfDetail?viewObjectId={{item.viewObjectId}}&viewObject={{item.viewObject}}&time={{item.belongMonth}}">业绩明细</a>
				{{/if}}
				{{if item.ry!=null && item.ry == '1'}}
					<a href="{{ctx}}/trade-web/perf/receivablePerfDetail">业绩明细</a>
				{{/if}}
				
			</td>
		</tr>
	{{/each}}
     </script>
     <script>
    
    
     </script>
    <!-- <a href=href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}">业绩明细</a> -->
        </content>
</body>