<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金自动化流程</title>     

		<!-- Data Tables -->
		<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />	
		<link href="<c:url value='/static/css/animate.css'/>"  rel="stylesheet"/> 
		<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
		<!-- 分页控件 -->
		<link href="<c:url value='/css/plugins/pager/centaline.pager.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css'/>"  rel="stylesheet" />
		
		<!-- index_css -->
 		<link href="<c:url value='/static/trans/css/common/base.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/award/baseAward.css' />" rel="stylesheet" />	 	
		<link href="<c:url value='/static/trans/css/manager/managerIframe.css' />" rel="stylesheet" />	 

		<!-- 必须CSS -->
		<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" /> 
    </head>
    
    <body class="pace-done">
			<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
           <div class="wrapper wrapper-content animated fadeInRight">
            
        <div class="ibox-content clearfix space_box">
            <h2 class="title">
                可计件环节奖金列表
            </h2>
        <div class="row">
            <div class="col-md-12">
                <div class="table_content">
                    <table class="table table_blue table-hover table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>
                                    职位
                                </th>
                                <th>
                                    对应组别
                                </th>
                                <th>
                                    具体环节
                                </th>
                                <th>
                                    基础奖金（元）
                                </th>
                                <th style="width: 135px;">
                                    操作
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    交易顾问
                                </td>
                                <td>
                                    前台组
                                </td>
                                <td>
                                    签约
                                </td>
                                <td>
                                    80
                                </td>
                                <td>
                                    <button class="btn btn-success">修改</button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    交易顾问
                                </td>
                                <td>
                                    前台组
                                </td>
                                <td>
                                    商业贷款
                                </td>
                                <td>
                                    80
                                </td>
                                <td>
                                    <button class="btn btn-success">修改</button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    交易顾问
                                </td>
                                <td>
                                    前台组
                                </td>
                                <td>
                                    陪同还贷
                                </td>
                                <td>
                                    80
                                </td>
                                <td>
                                    <button class="btn btn-success">修改</button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    交易顾问
                                </td>
                                <td>
                                    前台组
                                </td>
                                <td>
                                    过户
                                </td>
                                <td>
                                    80
                                </td>
                                <td>
                                    <button class="btn btn-success">修改</button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    交易顾问
                                </td>
                                <td>
                                    后台组
                                </td>
                                <td>
                                    纯公积金
                                </td>
                                <td>
                                    70
                                </td>
                                <td>
                                    <button class="btn btn-success">修改</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    
                </div>
            </div>
        
            <div class="clearfix"> 
                <h2 class="title pull-left ml15">
                    管理人员基础奖金配置
                </h2>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">
                            姓名
                        </label>
                        <input class=" input_type" placeholder="请输入" value="">
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">
                            所在组别
                        </label>
                        <select class="select_control sign_right_one">
                            <option value="">
                                浦东贵宾服务部
                            </option>
                        </select>
                    </div>
                    <div class="form_content ml5" >
                        <div class="add_btn">
                            <button type="button" class="btn btn-success mr5 btn-icon">
                                <i class="icon iconfont"></i>
                                查询
                             </button>
                            <button type="button" class="btn btn-success" id="AddBtn">
                                新增
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="table_content">
                    <table class="table table_blue table-hover table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>
                                    组别
                                </th>
                                <th>
                                    姓名
                                </th>
                                <th>
                                    员工编号
                                    <a href="javascript:void(0);"><i class="fa fa-sort-desc fa_down"></i></a>
                                </th>
                                <th>
                                    职位
                                </th>
                                <th>
                                    基础奖金（元）
                                </th>
                                <th>
                                    计件年月
                                </th>
                                <th style="width: 135px;">
                                    操作
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td >
                                    宝山贵宾服务部B组
                                </td>
                                <td>
                                    姚年萍
                                </td>
                                <td>
                                    AA2541
                                </td>
                                <td>
                                    主管
                                </td>
                                <td>
                                    40
                                </td>
                                <td>
                                    201705
                                </td>
                                <td>
                                    <button class="btn btn-success mr5">
                                        修改
                                    </button>
                                    <button class="btn btn-grey">
                                        删除
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td >
                                    宝山贵宾服务部B组
                                </td>
                                <td>
                                    姚年萍
                                </td>
                                <td>
                                    AA2541
                                </td>
                                <td>
                                    主管
                                </td>
                                <td>
                                    40
                                </td>
                                <td>
                                    201705
                                </td>
                                <td>
                                    <button class="btn btn-success mr5">
                                        修改
                                    </button>
                                    <button class="btn btn-grey">
                                        删除
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td >
                                    宝山贵宾服务部B组
                                </td>
                                <td>
                                    姚年萍
                                </td>
                                <td>
                                    AA2541
                                </td>
                                <td>
                                    主管
                                </td>
                                <td>
                                    40
                                </td>
                                <td>
                                    201705
                                </td>
                                <td>
                                    <button class="btn btn-success mr5">
                                        修改
                                    </button>
                                    <button class="btn btn-grey">
                                        删除
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td >
                                    宝山贵宾服务部B组
                                </td>
                                <td>
                                    姚年萍
                                </td>
                                <td>
                                    AA2541
                                </td>
                                <td>
                                    主管
                                </td>
                                <td>
                                    40
                                </td>
                                <td>
                                    201705
                                </td>
                                <td>
                                    <button class="btn btn-success mr5">
                                        修改
                                    </button>
                                    <button class="btn btn-grey">
                                        删除
                                    </button>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!--*********************** HTML_main*********************** -->


    <!-- 模态框（Modal） -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog" style="width: 720px;">
            <div class="modal-content" style="padding-bottom: 30px;">
                <div class="modal-header" style="border:0">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">新增管理人员信息</h4>
                </div>
                <div class="modal-body">
                    <div class="form_list">
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">
                                    姓名
                                </label>
                                <input class="input_type sign_right_one" placeholder="请输入" value="">
                                <div class="input-group float_icon organize_icon">
                                    <i class="icon iconfont"></i>
                                </div>
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">
                                    基础奖金
                                </label>
                                <input class="input_type sign_right_one" placeholder="请输入" value="">
                                <span class="date_icon">元</span>
                            </div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">
                                    组别
                                </label>
                                <input class=" input_type sign_right_one" placeholder="请输入" value="">
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">
                                    职位
                                </label>
                                <input class=" input_type sign_right_one" placeholder="请输入" value="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="text-align:center;border:0">
                    <button type="button" class="btn btn-success" >保存</button>
                    <button type="button" class="btn btn-grey" data-dismiss="modal">取消</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>

	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="userJobCode" value="${userJobCode}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" /> 
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>        
    <!-- Mainly scripts -->
    <content tag="local_script">    
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
     <!-- 日期控件 -->
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>     
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/inspinia.js' />"></script>
    <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>       
 	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
    <!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src= "<c:url value='/js/template.js" type="text/javascript' />"></script>
    <script src= "<c:url value='/transjs/award/newMethodAwardCollect.js' />"></script>
   	<!-- 必须JS --> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>  
	<script>
        $(function() {
            $('#AddBtn').click(function(event) {
                $('#addModal').modal('show');
            });
        })
    </script>    
	</content>             
   </body>
</html>