<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />

	
 <!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/datapicker/datepicker3.css"" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/trans/css/award/baseAward.css" />

<style type="text/css">

</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	 

	<div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">可计件奖金案件筛选</h2>
                    <form method="get" class="form_list">
				    	 <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left">
                                      	  案件编号
                                    </label>
                                    <input type="text" class="select_control sign_right_one" id="caseCode" name="caseCode" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left">
                                        	产证地址
                                    </label>
                                    <input class="teamcode input_type" style="width:435px;" placeholder="请输入" value="" id="prAddress" name="prAddress">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left select_style mend_select">
                                       	 过户时间
                                    </label>
                                    <div id="datepicker_1" class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd">
                                        <input id="guohuStart" name="guohuStart"  class="form-control data_style" type="text" value=""  placeholder="起始日期"> <span class="input-group-addon">到</span>
                                        <input id="guohuEnd" name="guohuEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
                                    </div>
                                </div>
                                 <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                       	过户审核时间
                                    </label>
                                    <div id="datepicker_0" class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd">
                                        <input id="guohuApproveStart" name="guohuApproveStart"  class="form-control data_style" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
                                        <input id="guohuApproveEnd" name="guohuApproveEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
                                    </div>
                                </div>
                            </div>
                             <div class="form_content" style="margin-left:176px;">
                                <div class="add_btn">
                                    <button id="searchButton" type="button" class="btn btn-success">
                                        <i class="icon iconfont">&#xe635;</i>
                                       	 查询
                                    </button>
                                    <button type="button" class="btn btn-success" onclick="javascript:exportToExcel()">
                                       	 详细导出
                                    </button>
                                    <button id="cleanButton" type="reset" class="btn btn-grey">
                                        	清空
                                    </button>
                                </div>
                            </div>
                        </form>
					</div>	
				 <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            <table class="table table_blue table-hover table-striped table-bordered">
                                <thead>
									<tr>
										<th ><span class="sort" sortColumn="c.CASE_CODE" sord="desc" onclick="caseCodeSort();">案件编码</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
										<th >产证地址</th>
										<th ><span class="sort" sortColumn="hf.real_HT_TIME" sord="desc" onclick="createTimeSort();">过户时间</span> <i id="createTimeSorti" class="fa fa-sort-asc fa_up"></i></th>
										<th >经办人</th>
										<th >所属组织</th>
										<th >案件状态</th>
									</tr>
								</thead>
								<tbody id="chandiaoTransferList">
									
								</tbody>
							</table>
							<div class="text-center page_box">
								<span id="currentTotalPage"><strong ></strong></span>
								<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
								<div id="pageBar" class="pagination text-center"></div>  
						    </div>
						</div>
					</div>
				</div>
		</div>
	
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
<input type="hidden" id="queryOrgs" value="${queryOrgs}" />

<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

<content tag="local_script"> 

<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${ctx}/js/jquery.blockui.min.js"></script>
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/plugins/jquery.custom.js"></script>
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${ctx}/transjs/award/kjjianCase2.js?v=1.1"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
								
 

<script id="template_chandiaoTransferList" type= "text/html">
      {{each rows as item index}}
  				   {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td >
<p class="big">
<a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a>
</p>
						</td>
						<td >
<p>
{{item.PROPERTY_ADDRESS}}
</p>
						</td>
						<td >
								<p class="smll_sign">
                                    <i class="sign_normal">过</i>
                                               {{item.GUOHU_TIME}}
                                </p>
                                <p class="smll_sign">
                                    <i class="sign_normal">审</i>
                                                {{item.GUOHU_APPROVE_TIME}}
                                </p>
                                <p class="smll_sign">
{{ if item.CLOSE_TIME !=null && item.CLOSE_TIME !=''}}
                                    <i class="sign_normal">完</i>
{{else}}
									<i class="sign_grey">完</i>
{{/if}}
												{{item.CLOSE_TIME}}
                                </p>
						</td>
						<td class="center">
								<span class="manager"><a href="#"><em>经纪人：</em>{{item.AGENT_NAME}}</a></span>
                                <span class="manager"><a href="#"><em>主办人：</em>{{item.ZHU_BAN_NAME}}</a></span>
 						</td>
						<td  >
								 <p><i class="sign_red sign-left">片区</i>{{item.AR_NAME}}</p>
                                 <p><i class="sign_normal sign-left">组织</i>{{item.YC_ORG}}</p>
 						</td>
						<td  >
								 <p>{{item.CASE_STATUS}}</p>
 						</td>
				  </tr>
       {{/each}}
</script>

<script>
   aist.sortWrapper({
	reloadGrid : searchMethod
	});
</script>

</content>
</body>
</html>