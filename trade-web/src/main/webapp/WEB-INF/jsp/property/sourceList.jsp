<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>产调来源清单</title>

<!-- General Style -->
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/static/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" />
<link rel="stylesheet" href="${ctx}/static/css/style.css" />

<!-- Data Tables -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- datapicker -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/datapicker/datepicker3.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />

<!-- jQuery UI -->
<link rel="stylesheet"
	href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" />

<!-- 分页控件 -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
<link rel="stylesheet"
	href="${ctx}/static/trans/css/property/popmac.css" />

<link rel="stylesheet"
	href="${ctx}/css/trans/css/propertyresearch/successList.css" />
	
	<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />

</head>

<body>
	<div class="row">
		<div class="col-md-12">
			<!--*********************** HTML_main*********************** -->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content border-bottom clearfix space_box">
					<!-- 					<h4>已完成产调</h4> -->
					<h2 class="title">产调来源清单</h2>
					<form method="post" class="form_list" id="sourceForm" action="${ctx}/quickGrid/findPage?xlsx">
						<div class="line">
							<input type="hidden" id="xlsx" name="xlsx" value="xlsx" /> 
							<input type="hidden" id="queryId" name="queryId" value="querySourceList" />
							<input type="hidden" id="ctx" value="${ctx}" />
							<input type="hidden" id="prDistrictId" name="search_prDistrictId" value="${prDistrictId}" />
							<input type="hidden" id="prDep" name="search_prDep" value="${prDep}" />
							<input type="hidden" id="xlsx" name="xlsx" value="xlsx"/>
		    				<input type="hidden" name="colomns" value="PR_CODE,IS_SUCCESS,UNSUCCESS_REASON,DIST_CODE,
		    					PR_ADDRESS,APP_RNAME,PR_APPLY_ORG_NAME,PR_APPLY_DEP_NAME,ORG_NAME,EXE_RNAME,PR_COST_ORG_MGR,
		    					PR_COST_ORG_NAME,PR_STATUS,PR_APPLY_TIME,PR_ACCPET_TIME,PR_COMPLETE_TIME" />
							<!-- <input type="hidden" id="prStatus" name="search_prStatus"
								value="2" /> -->
							<div class="form_content">
								<label class="control-label sign_left_small"> 申请人查询 </label>
								<!-- <input class="teamcode input_type" type="text" id="prApp"
									name="searchPrApp" placeholder="" value="" /> -->
								<input type="text" id="prApp" name="searchPrApp"
									class="teamcode input_type" readonly="readonly"
									onclick="chooseApplicant('105DB2C289397D50E0532429030A3DE0')"
									hVal="" />
								<div class="input-group float_icon organize_icon"
									id="propertySourceListOnclick">
									<i class="icon iconfont"></i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small"> 成本归属 </label> <input
									class="teamcode input_type" type="text" id="prCostMgr"
									name="searchPrCostMgr" placeholder="" value="" />
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small"> 目前状态 </label> <select
									name="searchPrStatus" id="prStatus" class="select_control ">
									<option value="" selected="selected">请选择</option>
									<option value="0">已申请</option>
									<option value="1">已受理</option>
									<option value="2">已完成</option>
								</select>
							</div>
						</div>
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small"> 贵宾服务部 </label> <input
									class="teamcode input_type" type="text" id="prDistName"
									name="searchPrDistName" readonly="readonly"
										   onclick="chooseDist('ff8080814f459a78014f45a73d820006')"
										   hVal="" placeholder="" value="" />
								<div class="input-group float_icon organize_icon">
									<i class="icon iconfont"></i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small"> 产调来源 </label> <select
									name="searchPrAppDep" id="prAppDep" class="select_control teamcode">
									<option value="" selected="selected">请选择</option>
									<option value="工商铺部">工商铺部</option>
									<option value="直带区住宅部">直带区住宅部</option>
									<option value="北区住宅部">北区住宅部</option>
									<option value="浦西住宅部">浦西住宅部</option>
									<option value="浦东住宅部">浦东住宅部</option>
									<option value="誉萃投资">誉萃投资</option>
									<option value="法律事务部">法律事务部</option>
								</select>
							</div>
						</div>
						<div class="line">
							<div class="form_content">
								<select name="searchTimeType" id="timeType" class="form-control mend_select sign_left_small"
									style="width: 105px; margin-right: 17px;">
									<option value="" selected="selected">请选择</option>
									<option value="0">申请时间</option>
									<option value="1">受理时间</option>
									<option value="2">完成时间</option>
								</select>
								<div class="input-group sign-right dataleft input-daterange"
									data-date-format="yyyy-mm-dd">
									<input name="searchStartTime" id="startTime" 
									    class="form-control data_style" type="text" value="" placeholder="起始时间">
										<span class="input-group-addon">到</span>
									<input name="searchEndTime" id="endTime"
										class="form-control data_style" type="text" value="" placeholder="结束日期">
								</div>
							</div>

							<div class="form_content">
								<label class="control-label sign_left_small"> 是否有效 </label>
								<div class="controls">
									<label class="radio inline"> <input type="radio"
										value="" checked="checked" name="successGroup" /> 全部
									</label> <label class="radio inline"> <input type="radio"
										value="1" name="successGroup" /> 是
									</label> <label class="radio inline"> <input type="radio"
										value="0" name="successGroup" /> 否
									</label>
								</div>
							</div>

						</div>

						<div class="line">
							<div class="add_btn" style="margin-left: 130px;">
								<button id="searchBtn" type="button" class="btn btn-success">
									<i class="icon iconfont"></i> 查询
								</button>
								<!-- <button type="button" class="btn btn-success" onclick="submitForm()">导出列表</button> -->
								<button id="exportBtn" type="button" class="btn btn-success">导出列表</button> 
								<button id="cleanBtn" type="reset" class="btn btn-grey">清空</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div id="sourceList" class="table_content"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--*********************** HTML_main*********************** -->

	<content tag="local_script"> 
	
	<!-- js模板引擎 -->
	<script src="${ctx}/static/js/template.js"></script> 
	<!-- 分页控件  --> 
	<script	src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<!-- 自定义扩展jQuery库 --> 
	<script src="${ctx}/js/plugins/jquery.custom.js"></script> 
<!-- 	<script src="${ctx}/static/trans/js/property/aist.jquery.custom.ps.js"></script> -->
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>

	<!-- datapicker -->
	<script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
	<!-- owner --> 
	<script src="${ctx}/js/trunk/property/propertySourceList.js"></script> 
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	
	
	<script id="template_sourceList" type="text/html">
		{{each rows as item index}}
			<tr>
				<td>
                    <p class="big">
					    <a href='../mobile/property/box/show?prCode={{item.PR_CODE}}' style="text-decoration: none;" target='_blank'>
						    {{item.PR_CODE}}
                        </a>
                    </p>
                    {{if item.IS_SUCCESS == '是'}}
						<span class="yes_color">有效</span>
					{{else if item.IS_SUCCESS == '否'}}
						<span class="no_color">无效</span><a class="demo-right" title="{{item.UNSUCCESS_REASON}}"><i class="icon iconfont" style="font-size: 20px;color:#808080">&#xe609;</i></a>
					{{else}}
					{{/if}}
				</td>	
                <td>
                    <p class="big">
                         {{item.DIST_CODE}}
                    </p>
                    <p class="big tint_grey">
                         {{item.PR_ADDRESS}}
                    </p>
                </td>

				<td>
                    <span class="manager"><i class="sign_red">来源</i><em>{{item.PR_APPLY_DEP_NAME}}</em></span>
                    <span class="manager"><i class="sign_normal">处理</i><em>{{item.ORG_NAME}}</em></span>
                </td>	
                <td>
                    <span class="manager">{{item.PR_COST_ORG_MGR}}</a></span>
                    <span class="manager">{{item.PR_COST_ORG_NAME}}</span>
                </td>
                <td>
                    <span class="manager">{{item.APP_RNAME}}</span>
                    <span class="manager">{{item.PR_APPLY_ORG_NAME}}</span>
                </td>
                <td>
                    <p class="smll_sign">
						{{if item.PR_STATUS_OLD >= 0}}
                        	<i class="sign_normal">申</i>
						{{else}}
							<i class="sign_grey">申</i>
						{{/if}}
                        {{item.PR_APPLY_TIME}}
                    </p>
                    <p class="smll_sign">
						{{if item.PR_STATUS_OLD >= 1}}
                        	<i class="sign_normal">受</i>
						{{else}}
							<i class="sign_grey">受</i>
						{{/if}}
                        {{item.PR_ACCPET_TIME}}
                    </p>
                    <p class="smll_sign">
						{{if item.PR_STATUS_OLD >= 2}}
                        	<i class="sign_normal">完</i>
						{{else}}
							<i class="sign_grey">完</i>
						{{/if}}
                        {{item.PR_COMPLETE_TIME}}
                    </p>
                </td>
			</tr>
		{{/each}}
	</script>
	    
	<script>
	
		/* function submitForm(){
			$('#sourceForm').submit();
		} */
		
	    // 清空搜索内容
		$('#cleanBtn').click(function() {
			$("input[name='searchPrApp']").val('');
			$("input[name='searchPrCostMgr']").val('');
			$("select[name='searchPrStatus']").val('');
			$("input[name='searchPrDistName']").val('');
			$("select[name='searchPrAppDep']").val('');
			$("select[name='searchTimeType']").val('');
			$("input[name='searchStartTime']").val('');
			$("input[name='searchEndTime']").val('');
			$('input[name="successGroup"]').val('');
		});
	    
		$(document).ready(function () {

            $('.input-daterange').datepicker({
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                todayBtn : 'linked',
            	language : 'zh-CN'
            });
        });
		
	</script> 
	
    <script type="text/javascript">
		$(function() {
			setStyle();
		});
	</script>
	    
    </content>
	
</body>