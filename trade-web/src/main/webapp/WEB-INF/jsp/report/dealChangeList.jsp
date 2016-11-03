<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>
            交易变更列表
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
		<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
        <!-- 提示 -->
        <link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" />
        <style>
    .add {
        border: 1px solid red;
    }
</style>
    </head>
    <body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
                <!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                            交易变更列表
                        </h2>
                        <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        案件编号
                                    </label>
                                    <input name="caseCode" id="caseCode" class="teamcode input_type" placeholder="" value="" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        所在组
                                    </label>
                                    <input type="text" readonly="readonly" class="teamcode input_type" id="teamId" name="teamId" hVal="" serviceDepIdOld="${serviceDepId}" serviceDepId="${serviceDepId}"　
                                          onClick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',startOrgId:'ff8080814f459a78014f45a73d820006', orgType:'',departmentType:'',departmentHeriarchy:'yucui_headquarter',chkStyle:'radio',chkLast:'true',callBack:radioYuCuiOrgSelectCallBack})";>
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        回访标记
                                    </label>
                                    <select name="visitRemark" id="visitRemark" class="select_control selwidth ">
                                    	<option value="">请选择</option>
                                        <option value="0">异常</option>
                                        <option value="1">正常</option>
                                        <option value="2">未处理</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        变更人
                                    </label>
                                    <input class="teamcode input_type" name="changeId" id="changeId" hVal="${changeId }" placeholder=""  readonly="readonly" placeholder="" value="" onclick="chooseChanger('${serviceDepId}')">
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        变更时间
                                    </label>
                                    <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                                        <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="起始时间"> <span class="input-group-addon">到</span>
                                        <input  name="changeTimeEnd" id="changeTimeEnd" class="form-control data_style" type="text" value="${curMonthEnd }" placeholder="结束日期">
                                    </div>
                                </div>
                            </div>

                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        环节名称
                                    </label>
                                    <aist:dict id="partCode" name="partCode" clazz="select_control selwidth " display="select" dictType="part_code" defaultvalue="" />
                                </div>

                                 <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产证地址
                                    </label>
                                    <input name="propertyAddr" id="propertyAddr" class="teamcode input_type" style="width:435px;" placeholder="" value="" />
                                </div>
                            </div>

                            <div class="line">
                                <div class="add_btn" style="margin-left:126px;">
                                    <button type="button" class="btn btn-success" id="searchBtn">
                                        <i class="icon iconfont">&#xe635;</i>
                                        查询
                                    </button>
                                    <button type="button" class="btn btn-success" onclick="javascript:exportToExcel()">
                                        导出列表
                                    </button>
                                    <button type=button class="btn btn-grey" id="clearBtn">
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
                                            <th>
                                                <span class="sort" sortColumn="T.CASE_CODE" sord="desc" onclick="changeStyle();">案件编码</span><i id="flag" class="fa fa-sort-desc fa_down"></i>
                                            </th>

                                            <th>
                                                产证地址
                                            </th>
                                            <th>
                                                客户姓名
                                            </th>
                                            <th>
                                                预计时间
                                            </th>
                                            <th>
                                                变更
                                            </th>
                                            <th>
                                                所在组
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody id="dealChangeList">
                                        <tr>
                                            <td>
                                                <p class="big">
                                                    <a href="javascript:;">
                                                        ZY-AJ-201605-0952
                                                    </a>
                                                </p>
                                                <span class="red_color">异常</span><a href="#"><i class="icon iconfont" style="font-size: 20px;color:#808080"></i></a>
                                            </td>
                                            <td>
                                             <p>
                                                    <i class="sign_blue">
                                                        总监审批
                                                    </i>
                                                </p>
                                                <p class="big">
                                                    上海杨浦区平路街道（内环）鞍山八村29号060室
                                                </p>
                                            </td>
                                            <td>
                                                <p class="manager"><span>上家:</span><a href="#" class="mr5">金娇娇</a></p>
                                                <p class="manager"><span>上家:</span><a href="#" class="mr5">金娇娇</a></p>
                                            </td>
                                            <td>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">原预计</i>
                                                    2016-08-21
                                                </p>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">新预计</i>
                                                    2016-08-22
                                                </p>
                                            </td>
                                            <td>

                                                <p class="manager"><i class="sign_normal">原因</i><span class="demo-top" title="原因有许多，需要哪一条？来看看，原因有许多，需要哪一条？来看看">原因有许多，需要</span></p>
                                                <p class="smll_sign">
                                                    2016-08-22
                                                </p>
                                            </td>
                                            <td>
                                                 <p class="manager"><span>变更人:</span><a href="#" class="mr5">金娇娇</a></p>
                                                <p>虹口杨浦贵宾服务部A组</p>
                                            </td>
                                            <td class="text-center">
                                                <button class="btn btn-success" data-toggle="modal" data-target="#myModal">处理</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="text-center page_box">
                                    <span id="currentTotalPage">
                                        <strong class="bold">
                                        </strong>
                                    </span>
                                    <span class="ml15">共
                                        <strong class="bold" id="totalP">
                                        </strong>条
                                    </span>
                                    &nbsp;
                                    <div id="pageBar" class="pagination text-center">
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" >
                    <div class="modal-dialog" style="width: 980px;">
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title">
                                案件回访
                            </div>
                            <div class="mt15">
                                    <div class="info_content">
                                        <div class="line">
                                            <p>
                                                <label>
                                                   案件编号
                                                </label>
                                                <span class="info_one" id="case_code" style="width: 140px"></span>
                                            </p>
                                            <p>
                                                <label>
                                                    案件地址
                                                </label>
                                                <span id="property_addr"></span>
                                            </p>
                                        </div>
                                        <div class="line">
                                            <p>
                                                <label>
                                                    变更人
                                                </label>
                                                <span class="info_one" style="width: 140px"><span id="change_name"></span><em class="ml5 blue-text" id="change_mobile"></em></span>
                                            </p>
                                            <p>
                                                <label>
                                                    环节
                                                </label>
                                                <span id="part_code"></span>
                                            </p>
                                            <p>
                                                <label>
                                                    贵宾服务部
                                                </label>
                                                <span class="info" id="team_name"></span>
                                            </p>

                                        </div>
                                        <div class="line">
                                            <p>
                                                <label>
                                                    变更原因
                                                </label>
                                                <span id="change_reason"></span>
                                            </p>
                                        </div>
                                        <div class="line">
                                            <p id="seller">
                                                <label>
                                                    上家
                                                </label>
                                            </p>
                                        </div>
                                        <div class="line">
                                            <p id="buyer">
                                                <label>
                                                    下家
                                                </label>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            <form method="get" class="form_list">
                                <div class="line">
                                    <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        回访标记
                                    </label>
                                    <div class="checkbox i-checks radio-inline sign sign_right">
                                        <label>
                                            <input type="radio" value="1" name="remark_visit" checked="">
                                                正常
                                        </label>
                                        <label>
                                            <input type="radio" value="0" name="remark_visit">
                                                异常
                                        </label>
                                    </div>
                                </div>

                                </div>
                                <div class="line clearfix">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small pull-left">
                                            回访跟进
                                        </label>
                                        <textarea style="width:590px;max-width:590px;height:100px;display:inline;margin-left:5px;" class="pull-left textarea" name="content" id="content" cols="30" rows="10"></textarea>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="add_btn text-center" style="margin:15px 126px;">
                                        <button type="button" class="btn btn-success" id="submitBtn">
                                            提交
                                        </button>
                                        <button type="button" class="btn btn-grey" data-dismiss="modal" id="close">
                                            关闭
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
                <input type="hidden" id="ctx" value="${ctx }"> 
                <input type="hidden" id="historyId" value="" />
                <!--*********************** HTML_main*********************** -->
        <content tag="local_script">
        <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="${ctx}/js/plugins/jquery.custom.js"></script>
		<script src="${ctx}/js/jquery.blockui.min.js"></script> 
      	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<!-- 提示 -->
        <script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
        <script src="${ctx}/js/poshytitle/src/jquery.poshytipuser.js"></script>
        
        <script src="${ctx}/js/trunk/report/dealChangeList.js?v=1.1"></script>
        <script id="template_dealChangeList" type="text/html">
      {{each rows as item index}}
  		   {{if index%2 == 0}}
 				<tr class="tr-1">
           {{else}}
                <tr class="tr-2">
           {{/if}}
				<td>
                    <p class="big">
                       <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}"  target="_blank"">
                           {{item.CASE_CODE}}
                       </a>
                    </p>
					{{if visitRemark==''}}
						{{each item.returnVisitList as returnVisit index1}}
 					  
						  {{if index1==0 && returnVisit.visitRemark=='0'}}
							<span class="red_color">异常</span>
						  {{/if}}
						  {{if index1==0 && returnVisit.visitRemark=='1'}}
							<span class="yes_color">正常</span>
						  {{/if}}
						{{/each}}
						{{if item.returnVisitList.length==0}}
							<span class="no_color">未处理</span>
						{{/if}}
					{{else if visitRemark=='0'}}
						{{if item.returnVisitList.length>0}}
							<span class="red_color">异常</span>
						{{/if}}
					{{else if visitRemark=='1'}}
						{{if item.returnVisitList.length>0}}
							<span class="yes_color">正常</span>
						{{/if}}
					{{else if visitRemark=='2'}}
						{{if item.returnVisitList.length==0}}
							<span class="no_color">未处理</span>
						{{/if}}
					{{/if}}
                    <a href="#">
						<i class="icon iconfont demo-top" style="font-size: 20px;color:#808080" title="{{each item.returnVisitList as returnVisit index1}}{{index1+1}}. {{ returnVisit.visitRemark=='0' ? '异常 ':'正常'}}&nbsp;{{returnVisit.content}}&nbsp;{{returnVisit.createTime}}<br/> {{/each}}"></i>
					</a>
                </td>
				<td>
                    <p>
                        <i class="sign_blue">{{ item.PART_CODE }}</i>
                    </p>
					{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
                      <p class="big demo-top" title="{{item.PROPERTY_ADDR}}">
                      {{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
                    {{else}}
                      <p class="big">
                      {{item.PROPERTY_ADDR}}
                     {{/if}}					 
						</p>
                </td>
				<td>
                    <p class="manager"><span>上家:</span>
					 {{ if item.SELLER !="" && item.SELLER !=null }}
						   {{if item.SELLER.split("/").length >1}}
								<a href="#" class="mr5 demo-top" title="{{item.SELLER}}">{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}</a>
							{{else}}
								<a href="#" class="mr5" >{{item.SELLER}}</a>
							{{/if}}
					{{/if}}
					</p>
                    <p class="manager"><span>下家:</span>
					{{ if  item.BUYER !=null && item.BUYER !="" }}
						   {{if item.BUYER.split("/").length >1}}
								<a href="#" class="mr5 demo-top" title="{{item.BUYER}}">{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}</a>
							{{else}}
								<a href="#" class="mr5" >{{item.BUYER}}</a>
							{{/if}}
					{{/if}}
					</p>
                </td>
				<td>
                    <p class="smll_sign">
                         <i class="sign_normal">原预计</i>
                         {{item.OLD_EST_PART_TIME}}
                    </p>
                    <p class="smll_sign">
                         <i class="sign_normal">新预计</i>
                         {{item.NEW_EST_PART_TIME}}
                    </p>
                 </td>
                 <td>
                     <p class="manager"><i class="sign_normal">原因</i>
						<span class="demo-top" title="{{item.CHANGE_REASON}}">
							{{if item.CHANGE_REASON !=null && item.CHANGE_REASON.length>10 }}
									{{item.CHANGE_REASON.substring(1,10)}}...
							{{else}}
									{{item.CHANGE_REASON}}
							{{/if}}
						</span>
					 </p>
                     <p class="smll_sign">
                      {{item.CHANGE_TIME}}
                     </p>
                 </td>
				<td>
                     <p class="manager"><span>变更人:</span><a href="#" class="mr5">{{item.REAL_NAME}}</a></p>
                     <p>{{item.TEAM_NAME}}</p>
                </td>
                <td class="text-center">
                     <button class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="doDeal('{{item.CASE_CODE}}','{{item.PROPERTY_ADDR}}','{{item.REAL_NAME+','+item.MOBILE}}','{{item.PART_CODE}}','{{item.TEAM_NAME}}','{{item.CHANGE_REASON}}','{{item.SELLERANDPHONE}}','{{item.BUYERANDPHONE}}','{{item.historyId}}')">处理</button>
                </td>
		   </tr>
       {{/each}}
	</script>  
    <script>
            $(document).ready(function () {

                $('.input-daterange').datepicker({
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true
                });
            });

        </script>
		</content>
    </body>
</html>
