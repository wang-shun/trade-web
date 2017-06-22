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
                    <h2 class="title">
                     	   业绩明细
                    </h2>
                    <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_list">
			                        <div class="form_content">
				                        <label class="control-label sign_left_small">案件组织</label>
				                        <input id="teamCode" name="teamCode" class="teamcode input_type" placeholder="请选择"  readonly="readonly" value="${viewObject }" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
											startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})" />
										<input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId" value="${viewObjectId }">
			                        <div class="input-group float_icon organize_icon" style="cursor:pointer;" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})">
			                            <i class="icon iconfont">&#xe61b;</i>
			                        </div>
			                    </div>
                                
                                
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        业绩状态
                                    </label>
                                    <div class="input_type" style="border:0 none;padding-left:0">
                                        <label class="radio-inline">
                                          <input type="radio" name="statusOptions"  value="" checked="checked" id="status">全部
                                        </label> 
                                        <label class="radio-inline">
                                          <input type="radio" name="statusOptions"  value="GENERATED" id="status">有效
                                        </label>
                                        <label class="radio-inline">
                                          <input type="radio" name="statusOptions" value="CANCELED" id="status">无效
                                        </label>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        业绩类型
                                    </label>
                                    <div class="input_type" style="border:0 none;padding-left:0">
                                        <label class="radio-inline">
                                          <input type="radio" name="orderOptions"  value="0" checked="checked" id="originCode">全部
                                        </label>
                                        <label class="radio-inline">
                                          <input type="radio" name="orderOptions"  value="1" id="originCode">内单
                                        </label>
                                        <label class="radio-inline">
                                          <input type="radio" name="orderOptions" value="2" id="originCode">外单
                                        </label>
                                    </div>
                                </div>
                                
                                
                                
                                
                                
                            </div>
                            <div class="line">
                                
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                    		    应收科目
                                    </label>
                                    <select class="select_control sign_right_one" id="subjectCode">
                                    <option value="">请选择</option>
	                                    <c:forEach items="${subjectList }" var="subjectList">
	                                    	<option value="${subjectList.subjectCode }">${subjectList.subjectName }</option>
	                                    </c:forEach>
                                        
                                    </select>
                                </div>
                               
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        提交时间
                                    </label>
                                    <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="${startTime }" placeholder="起始日期"> 
										<span class="input-group-addon" style="line-height: 1.0000">到</span> 
											<input id="dtEnd_0" name="dtEnd" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="${endTime }" placeholder="结束日期">
									</div>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content" style="margin-left: 127px;">
                                    <div class="add_btn">
                                        <button type="button" class="btn btn-success" onclick="javascript:searchButton();">
                                            <i class="icon iconfont"></i>
                                            查询
                                        </button>
                                        <button type="button" class="btn btn-success" id="exportBtn">
                                            导出为Excel
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
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
	<script src="${ctx}/static_res/trans/js/performance/receivablePerf.js"></script>
    <script
		id="template_successList" type="text/html">
	{{each rows as item index}}
		<tr>
            <td><p><a href="javascript:void(0)">{{item.sharesCode}}</a></p></td>
            <td>
				{{if item.shareAmount != null}}{{item.shareAmount/10000}}万
				{{/if}}
			</td>
            <td><p>{{item.shareTime}}</p></td>
            <td>{{item.subject}}
				<i class="sign_blue" style="display: inline;">
					{{if item.caseOrigin !=null && item.caseOrigin == 'CTM'}}内单
					{{/if}}
					{{if item.caseOrigin !=null && item.caseOrigin != 'CTM'}}外单
					{{/if}}
				</i></td>
            <td>
				{{if item.status !=null && item.status == 'GENERATED'}}有效
				{{/if}}
				{{if item.status !=null && item.status == 'CANCELED'}}无效
				{{/if}}
			</td>
            <td>{{item.userId}}<p>{{item.treamId}}</p></td>
            <td><p><a href="javascript:void(0)">{{item.caseCode}}</a></p><p>{{item.treamId}}{{item.district}}</p></td>
            <td>{{item.SELLER}}</td>
            <td>{{item.BUYER}}</td>
            </tr>

	{{/each}}
     </script> 
    </content>       
</body>
</html>