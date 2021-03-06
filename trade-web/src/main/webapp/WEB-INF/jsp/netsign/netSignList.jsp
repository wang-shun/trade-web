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
            	预约网签列表
        </title>
        
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/animate.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/style.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
        <!-- Data Tables -->
        <link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
        <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
		<link href="<c:url value='/css/transcss/case/case_filter.css' />" rel="stylesheet">

         <!-- index_css -->
        <link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" />
        <link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                            预约网签列表
                        </h2>
                        <form class="form_list" id="searchForm">
                        	<input type="hidden" id="ctx" value="${ctx}"/>
                        	<input type="hidden" name="resPeopleId" id="resPeopleId" value="${resPeopleId }"/>
                        	<input type="hidden" name="resTime" value="${resTime }"/>
                        	<input type="hidden" name="resStatus" value="${resStatus }"/>
                        	<input type="hidden" name="distinctId" value="${distinctId }" />
                        	<input type="hidden" name="flag" value="search"/>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                      案件编号：
                                    </label>
                                    <input class="pop-name input_type" name="resPersonId" id="resPersonId" hVal="${resPeopleId }" placeholder=""  readonly="readonly" placeholder="" value="${resPersonId }" onclick="chooseManager('${serviceDepId}')">
                                	
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                       成交报告编号
                                    </label>
                                    <input class="pop-name input_type" placeholder="" value="${resNo }" name="resNo">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                       楼盘
                                    </label>
                                    <input class="input_type width165" placeholder="" value="${mobile }" name="mobile">
                                    <label class="control-label sign_left_small">
                                    栋座
                                    </label>
                                    <input class="input_type width165" placeholder="" value="${mobile }" name="mobile">
                                    <label class="control-label sign_left_small">
                                     房号
                                    </label>
                                    <input class="input_type width165" placeholder="" value="${mobile }" name="mobile">
                                </div>
                            </div>
                            <div class="line">
                           		 <div class="form_content">
                            		<select class="select_control sign_right_one" id="selResStatus">
                                        <option value=""  <c:if test="${resStatus == '' }">selected="selected"</c:if> >请选择</option>
                                        <option value="0" <c:if test="${resStatus == '0' }">selected="selected"</c:if> >业绩上报日期</option>
                                        <option value="1" <c:if test="${resStatus == '1' }">selected="selected"</c:if> >预约网签日期</option>
                                        <option value="2" <c:if test="${resStatus == '2' }">selected="selected"</c:if> >登记日期时间</option>
                                        <option value="3" <c:if test="${resStatus == '3' }">selected="selected"</c:if> >取消预约日期</option>
                                    </select>
                                 </div>
                                <div class="form_content">
                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                        <input name="startDateTime" class="form-control data_style" type="text" value="${startDateTime }" placeholder="起始日期"> <span class="input-group-addon">到</span>
                                        <input name="endDateTime" class="form-control data_style" type="text" value="${endDateTime }" placeholder="结束日期">
                                    </div>
                                    <div class="seldata">
                                        <span id="today" class="todaycolor date-time">今</span>
                                        <span id="tommrow" class="tommrow date-time">明</span>
                                    </div>
                                </div>

                            </div>
                            <div class="line">

                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                       	 状态
                                    </label>
                                    
                                    <select class="select_control sign_right_one" id="selResStatus">
                                        <option value=""  <c:if test="${resStatus == '' }">selected="selected"</c:if> >请选择</option>
                                        <option value="0" <c:if test="${resStatus == '0' }">selected="selected"</c:if> >取消预约</option>
                                        <option value="1" <c:if test="${resStatus == '1' }">selected="selected"</c:if> >预约成功</option>
                                    </select>
                                </div>
                                <div class="add_btn" style="float:left;margin-left:26px;">
                                    <button type="button" class="btn btn-success" id="searchButton">
                                        <i class="icon iconfont">&#xe635;</i>&nbsp;查询
                                    </button>
                                    <button type="button" class="btn btn-grey" id="clear">
                                        	清空
                                    </button>
                                     <button type="button" class="btn btn-grey" id="import">
                                        	导出
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
                                            	案件编号
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	成交报告编号
                                        </th>
                                        <th style="background-color:#52cdec;">
                                           	 	业绩上报日期
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	事业部
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	产权地址
                                        </th>
                                        <th style="background-color:#52cdec;">
                                           	 	买方姓名
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	卖方姓名
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	成交人
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	预约网签日期
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	预约网签时间
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	取消预约时间
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	过户权证
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	贷款权证
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	预约人
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	预约电话
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	登记时间
                                        </th>
                                        <th style="background-color:#52cdec;">
                                            	预约状态
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
										class="ml15"><strong class="bold" id="totalP"></strong>
									</span>&nbsp;
									<div id="pageBar" class="pagination my-pagination text-center m0"></div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- 取消预约弹出框 -->
				<div class="modal inmodal in" id="canceModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width: 790px;">
                    <input type="hidden" id="resId" name="resId" />
                    <input type="hidden" id="resPersonId" name="resPersonId" />
                    <input type="hidden" id="actStartTime" name="actStartTime" />
                    <input type="hidden" id="actEndTime" name="actEndTime" />
                    <input type="hidden" id="resDateTime" name="resDateTime" />
                    <input type="hidden" id="signingCenter" name="signingCenter" />
                    
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title">
                               	 取消预约
                            </div>
                            <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	已与经纪人确认
                                        </label>
                                        <div class="checkbox i-checks radio-inline sign sign_right" style="margin-top: 5px;margin-left: 10px;">
                                            <label>
                                                <input type="radio" value="0"  name="isCanceConfirm" checked="checked">
                                                否
                                            </label>
                                            <label>
                                                <input type="radio" value="1"  name="isCanceConfirm">
                                                是
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small pull-left" style="margin-left: 38px;margin-right: 8px;">
                                            	取消原因
                                        </label>
                                        <textarea name="comment" id="" class="pull-left textareasmall" placeholder=""></textarea>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="add_btn" style="float:left;margin:15px 117px;">
                                        <button type="button" class="btn btn-success" id="canceConfirmBtn">
                                            	提交
                                        </button>
                                        <button type="button" class="btn btn-grey" data-dismiss="modal" id="closeBtn">
                                            	关闭
                                        </button>
                                    </div>
                                </div>
                        </div>
                        <iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe></div>
                </div>
                
                <!-- 最新更进弹出框 -->
				<div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width: 790px;">
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title">
                               	 最新跟进
                            </div>

                            <form action="${ctx }/reservation/saveResFlowup" class="form_list clearfix" id="flowupForm" method="post">
                            	<input type="hidden" name="resId"/>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	房间号
                                        </label>
                                        <div class="pull-left popup-text" id="roomNo">
                                            SQ-01
                                         <em class="yellow_bg ml5">机动</em>
                                        </div>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                           	 预约日期
                                        </label>
                                        <div class="pull-left popup-text" id="resDateTime">
                                            09-04
                                        </div>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	预约时间
                                        </label>
                                        <div class="pull-left popup-text" id="resTime">
                                            08:00-10:00
                                        </div>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	预约人
                                        </label>
                                        <div class="pull-left popup-text">
                                            <span id="realname">小陈</span>
                                            <span class="grey95" id="mobile">13027425487</span>
                                        </div>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small select_style mend_select">
                                            	跟进日期
                                        </label>
                                        <div class="pull-left popup-text" id="followUpDate">
                                            2016-10-11&nbsp;10:00
                                        </div>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small pull-left">
                                            	跟进内容
                                        </label>
                                        <textarea name="comment" id="" class="pull-left textareasmall" placeholder=""></textarea>
                                        <iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe></div>
                                </div>
                                <div class="line">
                                    <div class="add_btn" style="float:left;margin:15px 126px;">
                                        <button type="button" class="btn btn-success" id="btnAddFlowUpInfo">
                                            	提交
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
                
               	<!-- 更换签约室弹出框 -->
                <div class="modal inmodal in" id="changeRoom" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width: 790px;">
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title">
                                	更换签约室
                            </div>
                            <div class="apply_table">
                                <table class="table table_blue mt20 table-striped table-bordered table-hover customerinfo" id="signRoom">
                                	<input type="hidden" id="resId" name="resId" />
                                	<input type="hidden" id="resStartTime" id="resStartTime" />
                                	<input type="hidden" id="resEndTime" id="resEndTime" />
                                	
                                    <thead>
                                        <tr>
                                            <th style="background-color:#52cdec;">
                                                	房间类型
                                            </th>
                                            <th style="background-color:#52cdec;">
                                                	剩余房间编号
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <p>
                                                    5人间
                                                </p>
                                            </td>
                                            <td>
                                                <button class="btn btn-transparent margin5">soi-1</button>
                                                <button class="btn btn-transparent margin5">soi-2</button>
                                                <button class="btn btn-transparent margin5">soi-3</button>
                                                <button class="btn btn-transparent margin5">soi-4</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    9人间
                                                </p>
                                            </td>
                                            <td>
                                                <button class="btn btn-transparent margin5">soi-1</button>
                                                <button class="btn btn-transparent margin5">soi-2</button>
                                                <button class="btn btn-transparent margin5">soi-3</button>
                                                <button class="btn btn-transparent margin5 btn-lightyellow">soi-4（机动）</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    12人间
                                                </p>
                                            </td>
                                            <td>
                                                <button class="btn btn-transparent margin5">soi-1</button>
                                                <button class="btn btn-transparent margin5">soi-2</button>
                                                <button class="btn btn-transparent margin5">soi-3</button>
                                                <button class="btn btn-transparent margin5">soi-4</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    15人间
                                                </p>
                                            </td>
                                            <td>
                                                <button class="btn btn-transparent margin5">soi-1</button>
                                                <button class="btn btn-transparent margin5">soi-2</button>
                                                <button class="btn btn-transparent margin5">soi-3</button>
                                                <button class="btn btn-transparent margin5">soi-4</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="change-btn" style="text-align:center;margin-top:30px">
                                <button type="button" class="btn btn-success mr5" id="btnChangeRoom">
                                    	更换签约室
                                </button>
                                <button type="submit" class="btn btn-success mr5" id="btnChangeAndEnableRoom">
                                    	更换签约室及启用
                                </button>
                                <button type="reset" class="btn btn-grey mr5" data-dismiss="modal">
                                    	取消
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                
<content tag="local_script"> 
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
    <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
    <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
	<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> <!-- 分页控件  -->
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
    <script src="<c:url value='/js/trunk/signroom/signinglist.js' />"></script>
    
    <script id="template_signingList" type="text/html">

      {{each rows as item index}}
  		   {{if index%2 == 0}}
 				<tr class="tr-1" id="{{item.resId}}">
           {{else}}
                <tr class="tr-2" id="{{item.resId}}">
           {{/if}}
				<td>
                   <p class="smll_sign big">
                        {{item.resNo}}
                   </p>
                   <p class="smll_sign">
                        {{if item.currentDate == item.resDateTime}}<i class="sign_normal today">今</i>{{/if}}<span class="big tint_grey">{{item.resDateTime}}</span>
                   </p>
                </td>
				<td class="tdRoomInfo">
					<p class="smll_sign big pRoomInfo">{{item.roomNO}}（<span class="big">{{item.numberOfPeople}}人间</span>） </p>
                    <p class="smll_sign">参加人数：{{item.numberOfParticipants}}人</p>
                </td>
				<td>
                    <p class="smll_sign">
                        <i class="sign_normal">起</i><span class="big tint_grey">{{item.actStartTime}}</span>
                    </p>
                    <p class="smll_sign">
                        <i class="sign_normal">止</i><span class="big tint_grey">{{item.actEndTime}}</span>
                    </p>
                </td>
				<td class="tdCheckInAndOutTime">
                    <p class="smll_sign">
                        <i class="sign_normal sign_blue_bg">起</i><span class="big tint_grey checkInTime">{{item.actCheckInTime}}</span>
                    </p>
                    <p class="smll_sign">
                        <i class="sign_normal sign_blue_bg">止</i><span class="big tint_grey checkOutTime">{{item.actCheckOutTime}}</span>
                    </p>
                 </td>
				 <td class="tdResStatus" lang="{{item.timeDifference}}">
							{{if item.resStatus == '0'}}
								预约中
							{{/if}}

							{{if item.resStatus == '1'}}
								{{if item.timeDifference <= 0}}
									<span class="big orange-hint text-center">使用中</span>
								{{else if item.timeDifference > 0}}
									<span class="big red-hint text-center">使用中</span>
								{{else}}
									<span class="big text-center">使用中</span>
								{{/if}}
							{{/if}}

							{{if item.resStatus == '2'}}
								已使用
							{{/if}}

							{{if item.resStatus == '3'}}
								已过期
							{{/if}}

							{{if item.resStatus == '4'}}
								已取消
							{{/if}}

							{{if item.resStatus == '5'}}
								提前使用
							{{/if}}
                 </td>
				 <td>
					  <span class="manager"><a href="#" title="{{item.mobile}}" class="demo-top"><em>预约人:</em>{{item.realName}}</a></span>
                      <span class="manager"><em>交易顾问:</em>{{item.serviceSpecialist}}</span>
                 </td>
				 <td>
					  <p class="smll_sign">
						  {{each item.transactItemCodeList as transactItemCode index}}
								{{if transactItemCode == 'contract'}}<i class="sign_blue">签合同</i>{{/if}}
								{{if transactItemCode == 'doLoan'}}<i class="sign_blue">办贷款</i>{{/if}}
								{{if transactItemCode == 'Eloan'}}<i class="sign_blue">e+贷款</i>{{/if}}
								{{if transactItemCode == 'OpenRegularMeeting'}}<i class="sign_blue">开例会</i>{{/if}}
						  {{/each}}
                      </p>
					  <p>
						  <a href="#"  class="demo-right" title="{{item.specialReq}}">
					  			{{if item.specialReq != null && item.specialReq!="" && item.specialReq.length > 18}}
									{{item.specialReq.substring(0,18)}}....
					  			{{else}}
									{{item.specialReq}}
					  			{{/if}}
						  </a>
					  </p>
                 </td>
				 <td>
                      <p class="smll_sign big latestFollupDateTime">{{item.followDateTime}}</p>
                      <p class="demo-right" title="{{each item.flowupInfoList as flowupInfo index1}}{{index1 + 1}}.{{flowupInfo.realName}}&nbsp;&nbsp;{{flowupInfo.createDateTime}}&nbsp;&nbsp;{{flowupInfo.comment}}</br>{{/each}}">
                         <a href="#"  class="latestComment">
							{{if item.latestComment != null && item.latestComment!="" && item.latestComment.length > 8}}
								{{item.latestComment.substring(0,8)}}....
					  		{{else}}
								{{item.latestComment}}
					  		{{/if}}
						</a>
                      </p>
                 </td>
				 <td class="tdOperation">
                      <div class="btn-group">
						  {{if item.resStatus != '5'}}
                          <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">操作
                                <span class="caret"></span>
                          </button>
						  {{/if}}
                          <ul class="dropdown-menu" role="menu" style="left:-95px;">
								{{if item.resStatus == '0'}}
									<c:if test="${isCurrenDayDuty == true }">
                                    	  <li class="liStartUse"><a href="#" data-toggle="modal" data-target="#canceModal" onClick="canceReservation('{{item.resId}}','{{item.resPersonId}}','{{item.actStartTime}}','{{item.actEndTime}}','{{item.resDateTime}}','{{item.signingCenter}}')">取消</a></li>
                                    </c:if>
									<shiro:hasPermission name="TRADE.SIGNROOM.SIGN">
                                    	<c:if test="${isCurrenDayDuty == true }">
                                    	  <li class="liStartUse"><a href="javascript:void(0);" onClick="startUse(this,'{{item.resDateTime}}','{{item.actStartTime}}','{{item.actEndTime}}','{{item.roomId}}','{{item.resId}}','{{item.scheduleId}}');">开始使用</a></li>
                                    	</c:if>
                                    </shiro:hasPermission>

									<shiro:hasPermission name="TRADE.SIGNROOM.CHANGEROOM">
                                    	<c:if test="${isCurrenDayDuty == true }">
                                    	  <li class="liChangeRoom"><a href="#" onClick="changeRoom(this,'{{item.resId}}','{{item.tradeCenterId}}','{{item.resDateTime}} {{item.actStartTime}}','{{item.resDateTime}} {{item.actEndTime}}');" data-toggle="modal" data-target="#changeRoom">变更签约室</a></li>
                                    	</c:if>
                                    </shiro:hasPermission>
								{{/if}}
                                
								{{if item.resStatus == '1'}}
                                	<shiro:hasPermission name="TRADE.SIGNROOM.SIGNOUT">
                                    	<c:if test="${isCurrenDayDuty }">
                                    	  	<li class="liEndUse"><a href="javascript:void(0);" onClick="endUse(this)">结束使用</a></li>
                                    	</c:if>
                                    </shiro:hasPermission>
								{{/if}}

								{{if item.resStatus == '3' && item.timeDifference <= 0}}
									<shiro:hasPermission name="TRADE.SIGNROOM.SIGN">
										<c:if test="${isCurrenDayDuty == true }">
                                    	  <li class="liStartUse"><a href="javascript:void(0);" onClick="startUse(this,'{{item.resDateTime}}','{{item.actStartTime}}','{{item.actEndTime}}','{{item.roomId}}','{{item.resId}}','{{item.scheduleId}}');">开始使用</a></li>
                                    	</c:if>
									</shiro:hasPermission>
								{{/if}}

                                <li>
									<input type="hidden" name="resId" value="{{item.resId}}"/>
									<input type="hidden" name="roomType" value="{{item.roomType}}"/>
									<input type="hidden" name="roomNo" value="{{item.roomNO}}"/>
									<input type="hidden" name="resDateTime" value="{{item.resDateTime}}"/>
									
									<input type="hidden" name="startResDateTime" value="{{item.actStartTime}}"/>
									<input type="hidden" name="endResDateTime" value="{{item.actEndTime}}"/>
									<input type="hidden" name="realName" value="{{item.realName}}"/>
									<input type="hidden" name="mobile" value="{{item.mobile}}"/>
									<a href="#" data-toggle="modal" data-target="#myModal" onClick="followup(this);">最新跟进</a>
								</li>
                          </ul>
					   </div>
                 </td>	
		   </tr>
       {{/each}}
	</script>  
	
</content>
</body>
</html>
