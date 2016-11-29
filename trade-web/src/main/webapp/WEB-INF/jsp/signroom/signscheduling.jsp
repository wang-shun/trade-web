<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
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
                                        <button type="button" class="btn btn-success" id="searchBtn">
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
        <script src="${ctx}/transjs/signing/signSchedule.js?v=1"></script>
        <script id="template_tradeCenterScheduleList" type="text/html">
 	{{each content as item index}}
	  <tr class="night_bg">
		<td></td>
       {{each item as subitem twoindex}}
		              {{if subitem.light == true}}
                         <td class="currmoth">{{subitem.day}}</td>
                      {{else if subitem.edit == true}}
                         <td>{{subitem.day}}</td>
                      {{else}}
                         <td class="nouser">{{subitem.day}}</td>
                      {{/if}}
         {{/each}}
      </tr>
	 <tr>
         <td><i class="iconfont icon-day">&#xe601;</i></td>
		{{each item as subitem twoindex}}
				
		   {{if subitem.tcs !=null && subitem.tcs.length>0}}
				{{if subitem.tcs.length==1}}
						{{if subitem.tcs[0].dutyType=='0'}}
							{{if subitem.light == true}}
				   				<td class="currday" onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a id="{{subitem.date+'0'}}" href='#'  class='underline big'>{{subitem.tcs[0].officerName}}</a></td>
              				{{else if subitem.edit == true}}
                   				<td  onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big" >{{subitem.tcs[0].officerName}}</a></td>
              				{{else}}
                   				<td class="nouser">{{subitem.tcs[0].officerName}}</td>
              				{{/if}}
					    {{else}}
							{{if subitem.light == true}}
				   				<td  class="currday" onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big">空置</a></td>
              				{{else if subitem.edit == true}}
                   				<td  onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big">空置</a></td>
              				{{else}}
                   				<td class="nouser">空置</td>
              				{{/if}}
					   {{/if}}	
                {{else if subitem.tcs.length==2}}
				  {{each subitem.tcs as tcsitem threeindex}}				
					{{if tcsitem.dutyType=='0'}}
							{{if subitem.light == true}}
				   				<td  class="currday" onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big">{{tcsitem.officerName}}</a></td>
              				{{else if subitem.edit == true}}
                   				<td  onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big" >{{tcsitem.officerName}}</a></td>
              				{{else}}
                   				<td class="nouser">{{tcsitem.officerName}}</td>
              				{{/if}}
					{{/if}}			
				  {{/each}}
				{{/if}}
				
		   {{else}}
				{{if subitem.light == true}}
				   				<td class="currday" onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big" >空置</a></td>
              	{{else if subitem.edit == true}}
                   				<td onclick="chooseDutyOfficer('{{subitem.date}}',0,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'0'}}" class="underline big">空置</a></td>
              	{{else}}
                   				<td class="nouser">空置</td>
              	{{/if}}

           {{/if}}
		      
         {{/each}}
      </tr>
      <tr>
         <td ><i class="iconfont icon-moon">&#xe6c1;</i></td>
         {{each item as subitem twoindex}}
				
		  {{if subitem.tcs !=null && subitem.tcs.length>0}}
				{{if subitem.tcs.length==1}}
						{{if subitem.tcs[0].dutyType=='1'}}
							{{if subitem.light == true}}
				   				<td class="currnight" onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big">{{subitem.tcs[0].officerName}}</a></td>
              				{{else if subitem.edit == true}}
                   				<td  onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big" >{{subitem.tcs[0].officerName}}</a></td>
              				{{else}}
                   				<td class="nouser">{{subitem.tcs[0].officerName}}</td>
              				{{/if}}
					    {{else}}
							{{if subitem.light == true}}
				   				<td class="currnight" onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big">空置</a></td>
              				{{else if subitem.edit == true}}
                   				<td onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big" >空置</a></td>
              				{{else}}
                   				<td class="nouser">空置</td>
              				{{/if}}
					   {{/if}}	
                {{else if subitem.tcs.length==2}}
				    {{each subitem.tcs as tcsitem threeindex}}				
					  {{if tcsitem.dutyType=='1'}}
							{{if subitem.light == true}}
				   				<td class="currnight" onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big" >{{tcsitem.officerName}}</a></td>
              				{{else if subitem.edit == true}}
                   				<td  onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big">{{tcsitem.officerName}}</a></td>
              				{{else}}
                   				<td class="nouser">{{tcsitem.officerName}}</td>
              				{{/if}}
					  {{/if}}			
				   {{/each}}
				{{/if}}
		   {{else}}
				{{if subitem.light == true}}
				   				<td class="currnight" onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big">空置</a></td>
              				{{else if subitem.edit == true}}
                   				<td  onclick="chooseDutyOfficer('{{subitem.date}}',1,'${sessionUser.serviceDepId}')"><a href="#" id="{{subitem.date+'1'}}" class="underline big">空置</a></td>
              				{{else}}
                   				<td class="nouser">空置</td>
              				{{/if}}

           {{/if}}

         {{/each}}
      </tr>
    {{/each}}
		</script>
        <script>
	        var ctx = "${ctx}";
	  		//初始化日期控件
	    	var monthSel=new DateSelect($('.month'),{max:new Date(2999,1,1),moveDone:reloadGrid});
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