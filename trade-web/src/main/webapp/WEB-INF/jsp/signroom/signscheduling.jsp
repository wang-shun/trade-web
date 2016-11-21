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
        <link rel="stylesheet" href="${ctx}/css/common/input.css" />
        <link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css">
        
        <link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css">

        <link rel="stylesheet" href="${ctx}/css/trans/css/signingroom/signingroom.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                        签约室值班表
                    </h2>
                    <div class="scheduling-box">
                        <p class="month pull-left">
                            <button type="button" name="dateButton" class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow"></i></button>
                                    <span id="month">2016/10月</span>
                                    <button name="dateButton" class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow"></i></button>
                        </p>
                        <div class="pull-left">
                            <form method="get" class="form_list scheduling-nomargin">
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            签约中心
                                        </label>
                                    <select class="select_control sign_right_one" id="centerId" name="centerId">
                                    <!-- <option value="">请选择</option> -->
                                    <c:forEach items="${tradeCenters }" var="tradeCenter">
                                    	<option value="${tradeCenter.pkid }">${tradeCenter.centerName }</option>
                                    </c:forEach>
                                    </select>
                                    </div>
                                    <div class="add_btn" style="float:left;margin-left:26px;">
                                        <button type="button" class="btn btn-success">
                                            <i class="icon iconfont">&#xe635;</i>
                                            查询
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table class="table table-bordered scheduling-table text-center clearfix " >
                            <thead>
                            <tr>
                                <th class="text-center">早晚班</th>
                                <th class="text-center">周日</th>
                                <th class="text-center">周一</th>
                                <th class="text-center">周二</th>
                                <th class="text-center">周三</th>
                                <th class="text-center">周四</th>
                                <th class="text-center">周五</th>
                                <th class="text-center">周六</th>
                            </thead>
                            <tbody id="tradeCenterSchedule">
                                <tr class="night_bg">
                                    <td >
                                    </td>
                                    <td class="nouser">
                                        25
                                    </td>
                                    <td class="nouser">
                                        26
                                    </td>
                                    <td class="nouser">
                                        27
                                    </td>
                                    <td class="nouser">
                                       28
                                    </td>
                                    <td class="nouser">
                                        29
                                    </td>
                                    <td class="nouser">
                                        30
                                    </td>
                                    <td>
                                        1
                                    </td>
                                </tr>
                                <tr>
                                    <td><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">陈辰
                                    </td>
                                    <td>
                                       陈辰
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td class="nouser">徐彦青
                                    </td>
                                    <td class="nouser">王鼎元
                                    </td>
                                    <td class="nouser">王鼎元
                                    </td>
                                    <td class="nouser">徐彦青
                                    </td>
                                    <td class="nouser">王鼎元
                                    </td>
                                    <td class="nouser">陈辰
                                    </td>
                                    <td>
                                       张晟洋
                                    </td>
                                </tr>
                                <tr class="night_bg">
                                    <td >
                                    </td>
                                    <td>
                                        2
                                    </td>
                                    <td>
                                        3
                                    </td>
                                    <td>
                                        4
                                    </td>
                                    <td>
                                       5
                                    </td>
                                    <td>
                                        6
                                    </td>
                                    <td>
                                        7
                                    </td>
                                    <td>
                                        8
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>
                                       陈辰
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td>王鼎元
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>王鼎元
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>王鼎元
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>
                                       张晟洋
                                    </td>
                                </tr>
                                <tr class="night_bg">
                                    <td >
                                    </td>
                                    <td>
                                        9
                                    </td>
                                    <td>
                                        10
                                    </td>
                                    <td>
                                        11
                                    </td>
                                    <td>
                                       12
                                    </td>
                                    <td>
                                        13
                                    </td>
                                    <td>
                                       14
                                    </td>
                                    <td>
                                        15
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>
                                       陈辰
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>
                                       张晟洋
                                    </td>
                                </tr>
                                <tr class="night_bg">
                                    <td >
                                    </td>
                                    <td>
                                        16
                                    </td>
                                    <td>
                                        17
                                    </td>
                                    <td>
                                        18
                                    </td>
                                    <td>
                                       19
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td>
                                       21
                                    </td>
                                    <td>
                                        22
                                    </td>
                                </tr>
                                <tr>
                                    <td><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>赵明
                                    </td>
                                    <td>赵明
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>
                                       陈辰
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td>赵明
                                    </td>
                                    <td>赵明
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td>赵明
                                    </td>
                                    <td>赵明
                                    </td>
                                    <td>
                                       张晟洋
                                    </td>
                                </tr>
                                <tr class="night_bg">
                                    <td>
                                    </td>
                                    <td>
                                        23
                                    </td>
                                    <td>
                                        24
                                    </td>
                                    <td>
                                        25
                                    </td>
                                    <td class="currmoth">
                                       26
                                    </td>
                                    <td>
                                       27
                                    </td>
                                    <td>
                                       28
                                    </td>
                                    <td>
                                       29
                                    </td>
                                </tr>
                                <tr>
                                    <td><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>李志
                                    </td>
                                    <td>张晟洋
                                    </td>
                                    <td class="currday">张晟洋
                                    </td>
                                    <td>李志
                                    </td>
                                    <td>李志
                                    </td>
                                    <td>
                                       陈辰
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td>苏爱娣
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td>苏爱娣
                                    </td>
                                    <td class="currnight">陈辰
                                    </td>
                                    <td >陈辰
                                    </td>
                                    <td>苏爱娣
                                    </td>
                                    <td>
                                       张晟洋
                                    </td>
                                </tr>
                                <tr class="night_bg">
                                    <td >
                                    </td>
                                    <td>
                                        30
                                    </td>
                                    <td>
                                        31
                                    </td>
                                    <td class="nouser">
                                        1
                                    </td>
                                    <td class="nouser">
                                       2
                                    </td>
                                    <td class="nouser">
                                       3
                                    </td>
                                    <td class="nouser">
                                       4
                                    </td>
                                    <td class="nouser">
                                       5
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td>徐彦青
                                    </td>
                                    <td>
                                    徐彦青
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td>苏爱娣
                                    </td>
                                    <td>陈辰
                                    </td>
                                    <td class="nouser">苏爱娣
                                    </td>
                                    <td class="nouser">苏爱娣
                                    </td>
                                    <td class="nouser">苏爱娣
                                    </td>
                                    <td class="nouser">苏爱娣
                                    </td>
                                    <td class="nouser">苏爱娣
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </div>

                </div>



                    <div class="row">
                        <div class="col-md-12 clearfix">


                        </div>
                    </div>
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
		<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
		<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 分页控件  -->
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src="${ctx}/js/template.js" type="text/javascript"></script> 
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		
		<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
        <script src="${ctx}/js/poshytitle/src/jquery.poshytipuser.js"></script>
		<script	src="${ctx}/static/js/plugins/dateSelect/dateSelect.js"></script>
        <script src="${ctx}/transjs/signing/signSchedule.js"></script>
        <script id="template_tradeCenterScheduleList" type="text/html">
 			{{each content as item index}}
  				   {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<tr class="night_bg">
                                    <td >
                                    </td>
                                    <td class="nouser">
                                        25
                                    </td>
                                    <td class="nouser">
                                        26
                                    </td>
                                    <td class="nouser">
                                        27
                                    </td>
                                    <td class="nouser">
                                       28
                                    </td>
                                    <td class="nouser">
                                        29
                                    </td>
                                    <td class="nouser">
                                        30
                                    </td>
                                    <td>
                                        1
                                    </td>
                                </tr>
                                <tr>
                                    <td><i class="iconfont icon-day">&#xe601;</i>
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">张晟洋
                                    </td>
                                    <td class="nouser">陈辰
                                    </td>
                                    <td>
                                       陈辰
                                    </td>
                                </tr>
                                <tr>
                                    <td ><i class="iconfont icon-moon">&#xe6c1;</i>
                                    </td>
                                    <td class="nouser">徐彦青
                                    </td>
                                    <td class="nouser">王鼎元
                                    </td>
                                    <td class="nouser">王鼎元
                                    </td>
                                    <td class="nouser">徐彦青
                                    </td>
                                    <td class="nouser">王鼎元
                                    </td>
                                    <td class="nouser">陈辰
                                    </td>
                                    <td>
                                       张晟洋
                                    </td>
                                </tr>
            {{/each}}
		
		</script>
        <script>
	        var ctx = "${ctx}";
	  		//初始化日期控件
	    	var monthSel=new DateSelect($('.month'),{max:new Date(),moveDone:reloadGrid});
            $(document).ready(function () {
                $('.input-daterange').datepicker({
                	startDate:new Date(),
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true
                });
              //初始化数据
        	    reloadGrid();
            });
        </script>
</content>
</body>
</html>