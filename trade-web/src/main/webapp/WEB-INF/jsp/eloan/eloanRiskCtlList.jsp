<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>风控措施清单</title>

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
<link rel="stylesheet"
	href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css"
	type="text/css" />

</head>

<body>
	<div class="row">
		<div class="col-md-12">
			<!--*********************** HTML_main*********************** -->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content border-bottom clearfix space_box">
					<!-- 					<h4>已完成产调</h4> -->
					<h2 class="title">风控措施清单</h2>
					<input type="hidden" id="ctx" value="${ctx}" />
					<input type="hidden" id="userId" value="${userId}" />
					<form method="post" class="form_list" id="sourceForm">
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small"> 贷款合约编号 </label> 
								<input type="text" id="eloanCode" name="searchEloanCode"
									class="teamcode input_type" />
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small"> 风控措施 </label>
								<select name="searchEloanRiskType" id="eloanRiskType"
									class="select_control sign_right_one">
									<option value="" selected="selected">请选择</option>
									<option value="card">押卡</option>
									<option value="mortgage">抵押</option>
									<option value="forceRegister">强制公正</option>
								</select>
							</div>
						</div>
						<div class="line">
							<div class="form_content">
								<div class="sign_left pull-left" style="width: 105px;">
									<select name="searchEloanChooseRole" id="eloanChooseRole" 
									class="form-control">
										<option value="" selected="selected">请选择</option>
										<option value="0">贷款专员</option>
										<option value="1">执行人</option>
										<option value="2">借款人</option>
									</select>
								</div>
								<div class="teamcode pull-left">
									<input type="text" id="eloanName" name="searchEloanName" 
									class="form-control pull-left">
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small select_style mend_select">
									风控执行时间 </label>
								<div class="input-group sign-right dataleft input-daterange"
									data-date-format="yyyy-mm-dd">
									<input name="searchStartTime" id="startTime" 
									    class="form-control data_style" type="text" value="" placeholder="起始日期">
										<span class="input-group-addon">到</span>
									<input name="searchEndTime" id="endTime"
										class="form-control data_style" type="text" value="" placeholder="结束日期">
								</div>
							</div>
						</div>
						

						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small"> 贷款归属组织 </label> <input
									class="teamcode input_type" type="text" id="exeTeam"
									name="searchExeTeam" readonly="readonly"
										   onclick="chooseDist('ff8080814f459a78014f45a73d820006')"
										   hVal="" placeholder="" value="" />
								<div class="input-group float_icon organize_icon">
									<i class="icon iconfont">&#xe61b;</i>
								</div>
							</div>
							<div class="add_btn" style="float:left;margin-left:126px;">
								<button id="searchBtn" type="button" class="btn btn-success">
									<i class="icon iconfont">&#xe635;</i> 查询
								</button>
								<button id="exportBtn" type="button" class="btn btn-success">导出列表</button>
								<button id="cleanBtn" type="reset" class="btn btn-grey">清空</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div id="riskCtlList" class="table_content"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
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
	<script src="${ctx}/js/trunk/eloan/eloanRiskCtlList.js"></script> 
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	
	<script id="template_eloanRiskCtlList" type="text/html">
		{{each rows as item index}}
			<tr>
				<td>
                    <p class="big">
						<a href="${ctx}/eloan/getEloanCaseDetails?pkid={{item.ELOANPKID}}">
						{{item.ELOANCODE}}
                    </p>
				</td>
				<td>
                    <p>
                        <i class="sign_blue">
                            {{item.SERVICECODE}}
                        </i>
                    </p>
                    <p class="big">
                        {{item.FINORGCODE}}
                    </p>
                </td>
				<td>
                    <p class="big">
                        {{item.RISKTYPE}}
                    </p>
                </td>
                <td class="center">
                    <span class="manager">{{item.CUSTOMERNAME}}</span>
                </td>
                <td>
                    <p class="big">
                        贷款专员：{{item.EXECUTORID}}
                    </p>
                    <p class="big">
                        {{item.EXECUTORDISTID}}
                    </p>
                </td>
               	<td>
                    <p class="big">
                        执行人：{{item.RCEXEID}}
                    </p>
                    <p class="smll_sign">
                        <i class="sign_normal">执</i>
                            {{item.EXETIME}}
                    </p>
                </td>
                <td class="text-center">
					{{if item.RCEXEIDSTR == '${userId}'}}
                        <div class="btn-group">
							{{if item.RISKTYPE == '押卡'}}
								<a href = "${ctx}/riskControl/guarantycards?pkid={{item.ELOANPKID}}">
                            	<button type="button" class="btn btn-success">
                                	查看/编辑
                            	</button>
								</a>
							{{else if item.RISKTYPE == '抵押'}}
                                <a href = "${ctx}/riskControl/guarantymortgage?pkid={{item.ELOANPKID}}">
                            	<button type="button" class="btn btn-success">
                                	查看/编辑
                            	</button>
								</a>
                            {{else if item.RISKTYPE == '强制公证'}}
                                <a href = "${ctx}/riskControl/guarantyfair?pkid={{item.ELOANPKID}}">
                            	<button type="button" class="btn btn-success">
                                	查看/编辑
                            	</button>
								</a>
                            {{else}}
								<button type="button" class="btn btn-success">
                                	查看/编辑
                            	</button>
							{{/if}}
                        </div>                    
					{{else}}
                        <div class="btn-group">
							{{if item.RISKTYPE == '押卡'}}
								<a href = "${ctx}/riskControl/guarantycardsvonly?pkid={{item.ELOANPKID}}">
                            	<button type="button" class="btn btn-success">
                                	查看
                            	</button>
								</a>
							{{else if item.RISKTYPE == '抵押'}}
                                <a href = "${ctx}/riskControl/guarantymortgagevonly?pkid={{item.ELOANPKID}}">
                            	<button type="button" class="btn btn-success">
                                	查看
                            	</button>
								</a>
                            {{else if item.RISKTYPE == '强制公证'}}
                                <a href = "${ctx}/riskControl/guarantyfairvonly?pkid={{item.ELOANPKID}}">
                            	<button type="button" class="btn btn-success">
                                	查看
                            	</button>
								</a>
                            {{else}}
								<button type="button" class="btn btn-success">
                                	查看
                            	</button>
							{{/if}}
                        </div>
					{{/if}}
                </td>
			</tr>
		{{/each}}
	</script>
	    
	<script>
		
		 // 清空搜索内容
		$('#cleanBtn').click(function() {
			$("input[name='searchEloanCode']").val('');
			$("select[name='searchEloanRiskType']").val('');
			$("select[name='searchEloanChooseRole']").val('');
			$("input[name='searchEloanName']").val('');
			$('input[name="searchStartTime"]').val('');
			$("input[name='searchEndTime']").val('');
			$("input[name='searchExeTeam']").val('');
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
	    
    </content>

</body>