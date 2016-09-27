<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 
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
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                            签约室
                        </h2>
                        <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        预约人：
                                    </label>
                                    <input class="pop-name input_type" placeholder="" value="">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        预约号
                                    </label>
                                    <input class="pop-name input_type" placeholder="" value="">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        手机号码
                                    </label>
                                    <input class="input_type width165" placeholder="" value="">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        预约日期
                                    </label>
                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                        <input name="" class="form-control data_style datatime" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
                                        <input name="" class="form-control data_style datatime" type="text" value="" placeholder="结束日期">
                                       </div>
                                    <div class="seldata">
                                        <span id="today" class="today date-time">今</span>
                                        <span id="tommrow" class="tommrow date-time">明</span>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small width89">
                                        预约时间
                                    </label>
                                    <select class="select_control sign_right_one">
                                        <option value="">
                                            08:00-10:00
                                        </option>
                                        <option value="">
                                            10:00-12:00
                                        </option>
                                    </select>
                                </div>

                            </div>
                            <div class="line">

                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        状态
                                    </label>
                                    <select class="select_control sign_right_one">
                                        <option value="">
                                            请选择
                                        </option>
                                        <option value="">
                                            机动
                                        </option>
                                    </select>
                                </div>
                                <div class="add_btn" style="float:left;margin-left:26px;">
                                    <button type="button" class="btn btn-success">
                                        <i class="icon iconfont">&#xe635;</i>
                                        查询
                                    </button>
                                    <button type="button" class="btn btn-grey">
                                        清空
                                    </button>
                                </div>
                            </div>

                        </form>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table_content">
                                <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                                    <thead>
                                    <tr>
                                        <th style="background-color:#52cdec;">
                                            	预约号
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	房号
                                        </th>
                                        <th style="background-color:#52cdec;">
                                           	 	预约时间
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	实际时间
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	预约状态
                                        </th>
                                        <th style="background-color:#52cdec;">
                                           	 	预约人
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	特殊要求
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	最新跟进
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	操作
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody id="signinglist">
                                   
                                    </tbody>
                                </table>
                                
                                 <div class="text-center">
									<span id="currentTotalPage"><strong class="bold"></strong></span> <span
										class="ml15">共<strong class="bold" id="totalP"></strong>
									</span>&nbsp;
									<div id="pageBar" class="pagination my-pagination text-center m0"></div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
                
<content tag="local_script"> 
    <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script> 
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
    <script src="${ctx}/js/plugins/jquery.custom.js"></script> 
    <script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
    <script src="${ctx}/js/trunk/signroom/signinglist.js?v=1.1"></script>
    
    <script id="template_signingList" type="text/html">

      {{each rows as item index}}
  		   {{if index%2 == 0}}
 				<tr class="tr-1">
           {{else}}
                <tr class="tr-2">
           {{/if}}
				<td>
                   <p class="smll_sign big">
                        {{item.resNo}}
                   </p>
                   <p class="smll_sign">
                        {{if item.currentDate == item.resDateTime}}<i class="sign_normal today">今</i>{{/if}}<span class="big tint_grey">{{item.resDateTime}}</span>
                   </p>
                </td>
				<td>
                   <p class="smll_sign big">SJ01</p>
                   <p class="smll_sign"><span class="big tint_grey">{{item.numberOfPeople}}人间</span></p>
                </td>
				<td>
                    <p class="smll_sign">
                        <i class="sign_normal">起</i><span class="big tint_grey">{{item.actStartTime}}</span>
                    </p>
                    <p class="smll_sign">
                        <i class="sign_normal">止</i><span class="big tint_grey">{{item.actEndTime}}</span>
                    </p>
                </td>
				<td>
                    <p class="smll_sign">
                        <i class="sign_normal sign_blue_bg">起</i><span class="big tint_grey">{{item.actCheckInTime}}</span>
                    </p>
                    <p class="smll_sign">
                        <i class="sign_normal sign_blue_bg">止</i><span class="big tint_grey">{{item.actCheckOutTime}}</span>
                    </p>
                 </td>
				 <td>
                      <p class="big">
							{{if item.currentTime > item.endTime}}
								已过期
							{{else}}
								{{if item.resStatus == '0'}}
									预约中
								{{/if}}

								{{if item.resStatus == '4'}}
									已取消
								{{/if}}

								{{if item.resStatus == '1'}}
									使用中
								{{/if}}

								{{if item.resStatus == '2'}}
									已使用
								{{/if}}
							{{/if}}
					  </p>
                 </td>
				 <td>
                      <span class="manager">{{item.realName}}</span>
                      <span class="manager">{{item.mobile}}</span>
                 </td>
				 <td>
					  {{if item.specialReq != null && item.specialReq!="" && item.specialReq.length > 24}}
								<p class="big" title="{{item.specialReq}}">{{item.specialReq.substring(item.specialReq.length-24,item.specialReq.length)}}</p>
					  {{else}}
								<p class="big">{{item.specialReq}}</p>
					  {{/if}}
                 </td>
				 <td>
                      <p class="smll_sign big">2016-10-11 12:55</p>
                      <p>
                         <a href="#"  class="demo-right" title="1.预约浦东贵宾中心6人间 <br>2.预约时间为2016年10月11日10:00~12:00已经和客户电话确认客户已经到达客户已经结束">客户已经开始</a>
                      </p>
                 </td>
				 <td>
                      <div class="btn-group">
                          <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">操作
                                <span class="caret"></span>
                          </button>
                          <ul class="dropdown-menu" role="menu" style="left:-95px;">
								{{if item.resStatus == '0'}}
									<li><a href="#">开始使用</a></li>
								{{/if}}
                                
								{{if item.resStatus == '1'}}
                                	<li><a href="#">结束使用</a></li>
								{{/if}}

                                <li><a href="#" data-toggle="modal" data-target="#myModal">最新跟进</a></li>
                          </ul>
					   </div>
                 </td>	
		   </tr>
       {{/each}}
	</script>  
</content>
</body>
</html>
