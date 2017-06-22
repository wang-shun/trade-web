<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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
   .unDelay {
   	color:#808080;
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
                    	<input type="hidden" id="orgId" value="${currentUser.serviceCompanyId }" />
                    	
                        <div class="form-row  clearfix">
                             
                            <div class="form-group form-margin form-space-one pull-left">
                                <label class="lable-one pull-left" style="margin-top: 6px;" >
                                    	查询环节
                                </label>
                                <select class="form-control input-one pull-left mr5" id="selInProgress">
                                    <option value="">所有环节</option>
                                    <option value="paidan">派单</option>
                                    <option value="firstFollow">首次跟进</option>
                                    <option value="sign">签约</option>
                                    <option value="guohu">过户</option>
                                    <option value="caseClose">结案</option>
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
                                <select class="form-control input-one inline" id="selDelay" style="margin-left:-4px;">
                                    <option value="">不限</option>
                                   	<option value="curDelayed">当前延期</option>
                                    <option value="delayed">有延期</option>
                                    <option value="noDelayed">无延期</option>
                                </select>
                            </div>
                            <div class="form-group form-margin form-space-one pull-left" style="margin-left:38px;">
                                <label for="" class="lable-one">逾期</label>
                                <select class="form-control input-one inline" id="selOverdue">
                                    <option value="">不限</option>
                                    <option value="curOverdued" selected>当前逾期</option>
                                    <option value="overdued">有逾期</option>
                                    <option value="noOverdued">无逾期</option>
                                </select>
                            </div>
                            <div class="form-group form-margin form-space-one pull-left">
                                <label for="" class="lable-one">案件状态</label>
                                <select class="form-control input-one inline" id="selStatus">
                                    <option value="">所有状态</option>
                                    <option value="progressing">进行中</option>
                                    <option value="completed">已完成</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-row form-rowbot clearfix">
                            <div class="btn-left btn-left-space" style="margin-left: 94px;">
                                <button type="button" class="btn btn-success btn-icon  mr5" id="btnSearch"><i class="icon iconfont">&#xe635;</i> 查询</button>
                                <button type="submit" class="btn btn-success btn-icon  mr5">导出到Excel</button>
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
                                        <th>派单</th>
                                        <th>首次跟进</th>
                                        <th>签约</th>
                                        <th>过户</th>
                                        <th>结案</th>
                                        <th>累计</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="caseEfficientList"></tbody>
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
                             <p {{if item.isFirstFollowOverdue }}class="warn-red"{{/if}} ><a class="demo-top" {{if item.inProgress == 'firstFollow'}} title="进行中" {{else}} title="完成时间:{{item.firstFollowDateTime}}" {{/if}} href="#">{{if item.inProgress == 'firstFollow'}}<i class="iconfont">&#xe60b;</i> {{/if}}{{item.firstFollowEffInfo}}</a></p>
                         </td>

                         <td>
                             <p {{if item.isSignOverdue }}class="warn-red"{{/if}}><a class="demo-top" {{if item.inProgress == 'sign'}} title="进行中" {{else}} title="完成时间:{{item.signDateTime}}" {{/if}} href="#">{{if item.inProgress == 'sign'}}<i class="iconfont">&#xe60b;</i> {{/if}}{{item.signEffInfo}}</a> </p>
                         </td>

                         <td>
                             <p {{if item.isGuohuOverdue }}class="warn-red"{{/if}} ><a class="demo-top"  {{if item.inProgress == 'guohu'}} title="进行中" {{else}} title="完成时间:{{item.guohuDateTime}}" {{/if}}>{{if item.inProgress == 'guohu'}}<i class="iconfont">&#xe60b;</i> {{/if}}<a  href="#">{{item.guohuEffInfo}}</a> </p>
                         </td>
                                        
						 <td>
							<p {{if item.isCaseCloseOverdue }}class="warn-red"{{/if}} ><a class="demo-top"  {{if item.inProgress == 'caseClose'}} title="进行中" {{else}} title="完成时间:{{item.caseCloseDateTime}}" {{/if}}>{{if item.inProgress == 'caseClose'}}<i class="iconfont">&#xe60b;</i> {{/if}}<a  href="#">{{item.caseCloseEffInfo}}</a> </p>            
                         </td>
						 <td>
                             <p>
                                {{item.totalEff}}
                             </p>
                         </td>
                              
						 <td class="text-center">
							 {{if item.inProgress != 'completed' && item.inProgress != 'firstFollow'}}
                             	<p><a class="sum_editor" href="javascript:void(0)" onClick="showDelayPop('{{item.caseCode}}','{{item.inProgress}}');">延期一次</a> </p>
							 {{else}}							 
								<p>延期一次</p>
							 {{/if}}
                         </td>
				  </tr>
       {{/each}}
	</script>
</content>
</body>
</html>
