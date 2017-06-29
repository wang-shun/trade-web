<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet"> 
<link href="${ctx}/css/style.css" rel="stylesheet"> 
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/common/subscribe.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="${ctx}/css/transcss/task/myTaskList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  
	
 <!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />

<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
<style>
   .warn-red a,.warn-red i,.warn-red a:hover,.warn-red i:hover {
       color:red;
   }
   .warn-blue a,.warn-blue i,.warn-blue a:hover,.warn-blue i:hover {
       color:#337ab7;
   }
   .strong-black {
       color:#333;
   }
  .over-status span, .over-status em{
       margin:0 1px;
       padding: 1px 0 0px;
       display: inline-block;
       width: 22px;
       text-align: center;
       color:#666;
       border-radius: 0;
       border: 1px solid #ccc;
   }
   .over-status em.white {
       border: 1px solid #ccc;
   }
   .over-status em.red {
       border: 1px solid #ed5565;
       background-color: #ed5565;
       color:#fff;
   }
   .over-status i.red {
       color: #ed5565;
   }
</style>

</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                       	 时效管理
                    </h2>
                    <form class="form_list">
                    	<input type="hidden" id="jobCode" value="${currentUser.serviceJobCode }"/>
                    	<input type="hidden" id="userId" value="${currentUser.id }"/>
                    	<input type="hidden" id="orgId" value="${serviceDepId }" />
                    	
                        <div class="form-row  clearfix">
                             
                            <div class="form-group form-margin form-space-one pull-left">
                                <label class="lable-one pull-left" style="margin-top: 6px;" >
                                    	查询环节
                                </label>
                                <select class="form-control input-one pull-left mr5" id="selInProgress" style="width:200px;">
                                    <option value="">请选择</option>
                                    <option value="paidan">派单</option>
                                    <option value="firstFollow">首次跟进</option>
                                    <option value="sign">签约</option>
                                    <option value="guohu">过户审批</option>
                                    <option value="caseClose">结案归档</option>
                                </select>
                                <div id="datepicker_0" class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                    <input id="dtBegin_0" name="dtBegin" class="form-control data_style" type="text" value="" placeholder="起始日期"> <span class="input-group-addon" style="line-height:1;">到</span>
                                    <input id="dtEnd_0" name="dtEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
                                </div>
                            </div>
                            <div class="form-group form-margin form-space-one pull-left">
                                <label for="" class="lable-one">案件归属</label>
                                
                                <div class="sign_right teamcode" style="position:relative;display:inline-block;">
                                <input type="text" class="teamcode form-control" id="teamCode" name="teamCode" readonly="readonly"
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBackInCaseEff})"
										   value="${currentUser.serviceDepName}"></input>
								<div class="input-group float_icon organize_icon" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBackInCaseEff})"
										   value="${currentUser.serviceDepName}">
                                        <i class="icon iconfont"></i>
                                </div>
                            </div>
                            </div>
                        </div>
                        <div class="form-row  clearfix">                                                       
                            <div class="form-group form-margin form-space-one pull-left">
                                <label for="" class="lable-one">延期</label>
                                <select class="form-control input-one inline" id="selDelay" style="width:200px;margin-left:-4px;">
                                    <option value="">请选择</option>
                                   	<option value="curDelayed">当前延期</option>
                                    <option value="delayed">有延期</option>
                                    <option value="noDelayed">无延期</option>
                                </select>
                            </div>
                            <div class="form-group form-margin form-space-one pull-left" style="margin-left:38px;">
                                <label for="" class="lable-one">逾期</label>
                                <select class="form-control input-one inline" id="selOverdue">
                                    <option value="">请选择</option>
                                    <option value="curOverdued">当前逾期</option>
                                    <option value="overdued">有逾期</opstion>
                                    <option value="noOverdued">无逾期</option>
                                </select>
                            </div>
                            <div class="form-group form-margin form-space-one pull-left">
                                <label for="" class="lable-one">案件状态</label>
                                <aist:dict id="selStatus" name="case_status" display="select" dictType="30001" clazz="form-control input-one inline"/>
                            </div>
                        </div>
                        <div class="form-row  clearfix"> 
                        	<div class="form-group form-margin form-space-one pull-left">
                                <label for="" class="lable-one">案件编号</label>
                                <input type="text" name="caseCode" id="caseCode" class="form-control data_style inline" style="width:200px;margin-left:-4px;"/>
                            </div>
                        </div>
                        <div class="form-row form-rowbot clearfix">
                            <div class="btn-left btn-left-space" style="margin-left: 94px;">
                                <button type="button" class="btn btn-success btn-icon  mr5" id="btnSearch"><i class="icon iconfont">&#xe635;</i> 查询</button>
                                <button type="button" class="btn btn-success btn-icon  mr5" onClick="javascript:exportExcel();">导出到Excel</button>
                                <button type="reset" class="btn btn-grey mr5">
                                    清空
                                </button>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                        <p>首次跟进/签约过户结案累计数据分别由四项内容: <span class="strong-black">环节用时-逾期时间-时效标准-延期次数</span></p>
                            <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                                <thead>
                                    <tr>
                                        <th>案件编码</th>
                                        <th>主办人</th>
                                        <th>首次派单</th>
                                        <th>首次跟进</th>
                                        <th>签约</th>
                                        <th>过户审批</th>
                                        <th>结案归档</th>
                                        <th>累计</th>
                                        <shiro:hasPermission name="TradeMenu.Report.DELAY">
                                        <th>操作</th>
                                        </shiro:hasPermission>
                                    </tr>
                                </thead>
                                <tbody id="caseEfficientList"></tbody>
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
    </div>
    
    <div class="modal fade" id="subAchieve" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	  <input type="hidden" name="caseCode" />
    	  <input type="hidden" name="inProgress" />
    	  <input type="hidden" name="delayDays"/>
          <div class="modal-dialog" style="width: 500px;">
              <div class="modal-content">
                  <div class="modal-header" style="border:none;">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                      <h4 class="modal-title" id="myModalLabel">延期原因</h4>
                  </div>
                  <div class="modal-body">
                      <textarea style="width: 100%;height:85px;border:1px solid #ccc;resize: none;padding:7px;" name="comment" id=""></textarea>
                      <p style="color:red;" class="hide" id="tip">内容不能为空！</p>
                  </div>
                  <div class="modal-footer" style="text-align: center;margin: 0 0 40px;border:none;">
                      <button type="button" class="btn btn-success" id="btnConfirm">确定</button>
                      <button type="button" class="btn btn-grey" data-dismiss="modal" id="closeBtn">关闭</button>
                  </div>
              </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
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
			<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
			<!-- 分页控件  -->
			<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
			<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
			<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
			<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
			<!-- 必须JS -->
			<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
			<script src="${ctx}/js/workflow/myCaseList.js"></script>
			<script src="${ctx}/js/trunk/case/caseEfficientList.js?ver=1"></script> 
			
	<script id="template_caseEfficientList" type="text/html">

      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td>
                            <p class="big">
                                <a href="../case/caseDetail?caseId={{item.pkid}}" target="_blank" style="text-decoration:underline;">{{item.caseCode}}</a>
                            </p>
                        </td>

						<td>
                            {{item.leadingProcessName}}
                        </td>

						 <td>
							 <p>
                                 {{item.dispatchTime}}
                             </p>
                         </td>

						 <td>
							<p class="over-status">
								<a class="demo-top" title="{{if item.inProgress == 'firstFollow'}}进行中<br/>{{/if}}{{if item.firstFollowDateTime}}完成时间:{{item.firstFollowDateTime}}<br/>{{/if}}{{each item.firstFollowDelayList as firstFollowDelay index1}}延期原因{{index1 + 1}}：{{firstFollowDelay.comment}}<br/>{{/each}}" href="#">
                                       <span>{{item.firstFollowTime}}</span>
									   <em {{if item.firstFollowOverdueTime > 0}} class="red"{{else}}class="white"{{/if}}>{{item.firstFollowOverdueTime}}</em>
									   <span>{{item.firstFollowEff}}</span>
									   <em {{if item.firstFollowDly > 0}}class="red"{{else}}class="white"{{/if}}>{{item.firstFollowDly}}</em>
									   {{if item.inProgress == 'firstFollow'}} <i class="iconfont">&#xe60b;</i>  {{/if}}
                                </a> 
                            </p>
                         </td>

                         <td>
							<p class="over-status">
								<a class="demo-top" title="{{if item.inProgress == 'sign'}}进行中<br/>{{/if}}{{if item.signDateTime}}完成时间:{{item.signDateTime}}<br/>{{/if}}{{each item.signDelayList as signDelay index1}}延期原因{{index1 + 1}}：{{signDelay.comment}}<br/>{{/each}}" href="#">
                                       <span>{{item.signTime}}</span>
									   <em {{if item.signOverdueTime > 0}} class="red"{{else}}class="white"{{/if}}>{{item.signOverdueTime}}</em>
									   <span>{{item.signEff}}</span>
									   <em {{if item.signDly > 0}}class="red"{{else}}class="white"{{/if}}>{{item.signDly}}</em>
									   {{if item.inProgress == 'sign'}} <i class="iconfont">&#xe60b;</i>  {{/if}}
                                </a> 
                            </p>
                         </td>

                         <td>
							<p class="over-status">
								<a class="demo-top" title="{{if item.inProgress == 'guohu'}}进行中<br/>{{/if}}{{if item.guohuDateTime}}完成时间:{{item.guohuDateTime}}<br/>{{/if}}{{each item.guohuDelayList as guohuDelay index1}}延期原因{{index1 + 1}}：{{guohuDelay.comment}}<br/>{{/each}}" href="#">
                                       <span>{{item.guohuTime}}</span>
									   <em {{if item.guohuOverdueTime > 0}} class="red"{{else}}class="white"{{/if}}>{{item.guohuOverdueTime}}</em>
									   <span>{{item.guohuEff}}</span>
									   <em {{if item.guohuDly > 0}}class="red"{{else}}class="white"{{/if}}>{{item.guohuDly}}</em>
									   {{if item.inProgress == 'guohu'}} <i class="iconfont">&#xe60b;</i>  {{/if}}
                                </a> 
                            </p>
                         </td>
                                        
						 <td>
							<p class="over-status">
								<a class="demo-top" title="{{if item.inProgress == 'caseClose'}}进行中<br/>{{/if}}{{if item.caseCloseDateTime}}完成时间:{{item.caseCloseDateTime}}<br/>{{/if}}{{each item.caseCloseDelayList as caseCloseDelay index1}}延期原因{{index1 + 1}}：{{caseCloseDelay.comment}}<br/>{{/each}}" href="#">
                                       <span>{{item.caseCloseTime}}</span>
									   <em {{if item.caseCloseOverdueTime > 0}} class="red"{{else}}class="white"{{/if}}>{{item.caseCloseOverdueTime}}</em>
									   <span>{{item.caseCloseEff}}</span>
									   <em {{if item.caseCloseDly > 0}}class="red"{{else}}class="white"{{/if}}>{{item.caseCloseDly}}</em>
									   {{if item.inProgress == 'caseClose'}} <i class="iconfont">&#xe60b;</i>  {{/if}}
                                </a> 
                            </p>
                         </td>
						 <td>
                             <p>
                                {{item.totalEff}}
                             </p>
                         </td>
                         
						<shiro:hasPermission name="TradeMenu.Report.DELAY">     
						 <td class="text-center">
								<div class="btn-group">
                         			<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-expanded="false" {{if item.inProgress == 'completed' || item.inProgress == 'firstFollow'}}disabled="true"{{/if}}>操作
                             			<span class="caret"></span>
                         			</button>

                                 	<ul class="dropdown-menu" role="menu" style="left:-95px;">
                                      	{{if item.inProgress != 'completed' && item.inProgress != 'firstFollow'}}
											<li><a href="javascript:void(0)" onClick="showDelayPop('{{item.caseCode}}','{{item.inProgress}}');">延期</a></li>
										{{/if}}
                                	</ul>
                      			</div>
                         </td>
						</shiro:hasPermission>
				  </tr>
       {{/each}}
	</script>
</content>
</body>
</html>
