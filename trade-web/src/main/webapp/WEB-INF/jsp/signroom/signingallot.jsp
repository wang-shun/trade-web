<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<html>
<head>
<meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>
            签约室分配
        </title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet"/>
        <link href="${ctx}/css/animate.css" rel="stylesheet"/>
        <link href="${ctx}/css/style.css" rel="stylesheet"/>
        <!-- Data Tables -->
        <link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

         <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/css/common/base.css" />
        <link rel="stylesheet" href="${ctx}/css/common/table.css" />
        <link rel="stylesheet" href="${ctx}/css/common/input.css" />
        <link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" ">
        
        <link rel="stylesheet" href="${ctx}/js/plugins/autocomplete/autocompleter.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                            	签约室分配
                        </h2>
                        <form method="get" class="form_list">
                        	<div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        预约日期
                                    </label>
                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                        <input name="curDate" id="curDate" class="input_type data_style datatime" type="text" value="${curDate }" placeholder="">
                                    </div>
                                    <div class="seldata">
                                        <span id="today" class="todaycolor date-time">今</span>
                                        <span id="tommrow" class="tommrow date-time">明</span>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房间类型
                                    </label>
                                    <select class="select_control sign_right_one" name="roomTypeSlot" id="roomTypeSlot">
                                        <option value="">请选择</option>
                                        <option value="0">普通房间</option>
                                        <option value="1">机动房间</option>
                                    </select>
                                </div>
                                <div class="add_btn" style="float:left;margin-left:26px;">
                                    <button type="button" class="btn btn-success" id="searchBtn">
                                        <i class="icon iconfont"></i>
                                        查询
                                    </button>
                                    <button type="button" class="btn btn-grey" id="clearBtn">
                                        清空
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table_content">
                                <table class="table table_blue table-striped table-bordered table-hover " id="signRoomTable" >
                                    <thead>
                                        <tr>
                                            <th>
                                                	房间号
                                            </th>
                                            <th>
                                                	归属贵宾服务部
                                            </th>
                                            <th>
                                                	人数
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width: 790px;">
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title">
                                	临时分配
                            </div>
                            <form action="" class="form_list clearfix">
                            	<input type="hidden" id="caseCode" value=""/>
                            	<input type="hidden" id="scheduleId" value=""/>
                            	<input type="hidden" id="tradeCenter" value=""/>
                            	<input type="hidden" id="tradeCenterId" value=""/>
                            	<input type="hidden" id="roomId" value=""/>
                            	<input type="hidden" id="resPersonOrgId" value=""/>
                            	<input type="hidden" id="numberOfPeople" value=""/>
                            	<input type="hidden" id="startDate" value=""/>
                                <div class="trade">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                          	  房间号
                                        </label>
                                        <div class="pull-left popup-text">
                                                <span id="roomNo" name="roomNo"></span><em class="yellow_bg ml5" id="roomType" name="roomType"></em>
                                        </div>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	预约日期
                                        </label>
                                        <div class="pull-left popup-text" id="signDate">
                                            
                                        </div>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	预约时间
                                        </label>
                                        <div class="pull-left popup-text">
                                            
                                            <span id="slotTime" name="slotTime">08:00~10:00</span>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="trade">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                           	 经纪人用户名
                                        </label>
                                        <input class="teamcode input_type" id="jjrName" name="jjrName" readonly="readonly" placeholder="" value="" onclick="chooseManager('${serviceDepId}')">
                                        <div class="input-group float_icon organize_icon">
                                            <i class="icon iconfont"></i>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="trade" id="tradeAddr">
                                    <div class="form_content" >
            							<label class="control-label sign_left_small">
                                          	  交易地址
                                        </label>
                                        <div style="position: relative;display: inline;">
                                        <input type="text" id="propertyAddress" name="propertyAddress" placeholder="交易单地址" data-required="true"  maxlength="40" class=" input_type width400" />
                                        </div>
                                    </div>
                                </div>
                                <div class="trade">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            	参与人数
                                        </label>
                                        <input class="pop-name input_type" type="text" placeholder="" id="numberOfParticipants" name="numberOfParticipants"  value="" />
                                    </div>
                                </div>
                                <div class="trade">
                                    <div class="form_content choices">
                                        <label class="control-label sign_left_small">
                                            	办理事项
                                        </label>
                                        <c:forEach items="${transactItemVoList}" var="transactItemVo">
                                             <span name="srvcode" class="text-white "  id="${transactItemVo.code }">
                                            	${transactItemVo.name }
                                        </span>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="trade">
                                    <div class="add_btn" style="float:left;margin:15px 126px;">
                                        <button type="button" class="btn btn-success" id="saveBtn">
                                           	 分配
                                        </button>
                                        <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                            	关闭
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe></div>
                </div>
                <!--*********************** HTML_main*********************** -->
                
 <!-- Mainly scripts -->
 <content tag="local_script"> 
 
 		<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
 		<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
 		<script src="${ctx}/js/jquery.blockui.min.js"></script> 
 		<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	    <script src="${ctx}/js/plugins/jquery.custom.js"></script> 
	    <script src="${ctx}/js/plugins/autocomplete/jquery.autocompleter.js"></script>
	    <%-- <script src="${ctx}/js/plugins/autocomplete/autocompleter.js"></script> --%>
		<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
		<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 分页控件  -->
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src="${ctx}/js/template.js" type="text/javascript"></script> 
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		
        <script src="${ctx}/transjs/signing/siging.js"></script>
        <script id="template_signRoomList" type="text/html">
 			
		</script>
        <script>
            $(document).ready(function () {

                $('.input-daterange').datepicker({
                	startDate:new Date(),
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true
                });
            });
        </script>
</content>
</body>
</html>