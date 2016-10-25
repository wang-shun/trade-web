
<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=utf-8" 	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>扣押物品列表</title>

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- 提示 -->
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" /> 
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" /> 

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"	rel="stylesheet" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
                    
 <div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
             	扣押物品列表
         </h2>
         <form class="form-inline">
             <div class="form-row form-rowbot">
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one" >案件编号</label>
                     <input type="text" class="form-control input-one" placeholder="" id="caseCode" name="caseCode">
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">提交人</label>
                     <input type="text" class="form-control input-one" placeholder="" id="createBy" name="createBy">
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">保管人</label>
                     <input type="text" class="form-control input-one" placeholder="" id="itemManager" name="itemManager">
                 </div>
             </div>
             <div class="form-row form-rowbot">
                  <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">物品类型</label>
                     <select class="form-control input-one" id="itemCategory">
                     	 <option value="" selected="selected">请选择</option>
                         <option value="carded">身份证</option>
                         <option value="bankCard">银行卡</option>
                         <option value="propertyCard">产权证</option>
                         <option value="mortgageContract">抵押合同</option>
                         <option value="otherCard">他证</option>
                     </select>
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">物品状态</label>
                     <select class="form-control input-one" id="itemStatus">
                     	 <option value="" selected="selected">请选择</option>
                         <option value="1">完好无损</option>
                         <option value="2">已损坏</option>
                         <option value="3">部分破损</option>
                     </select>
                 </div>
                 <div class="form-group form-margin" style="margin-left:15px;">
                     <select class="form-control select-one" id="timeSelect">
                         <option value="ITEM_INPUT_TIME">入库时间</option>
                         <option value="ITEM_OUTPUT_TIME">借出时间</option>                        
                         <option value="ITEM_BACK_TIME">退还时间</option>
                     </select>
                     <div class="input-daterange input-group" id="datepicker_0">
                         <input id="dtBegin_0"  type="text" class="form-control date-width" name="start" value="">
                         <span class="input-group-addon">到</span>
                         <input id="dtEnd_0"  type="text" class="form-control date-width" name="end" value="">
                     </div>
                 </div>
             </div>
             
             <div class="form-row">
                 <div class="form-group form-margin pull-left">
                     <label for="" class="lable-one">物业地址</label>
                     <input type="text" class="form-control" style="width:355px;" placeholder="" id="propertyAddr" name="propertyAddr">
                 </div>
                 <div class="btn-left btn-left-space ml40">
                     <button type="button" class="btn btn-success btn-icon  mr5" id="searchButton"><i class="icon iconfont">&#xe635;</i> 查询</button>
                     <button type="reset" class="btn btn-grey mr5">清空</button>
                     <a href="#" class="btn btn-toggle mr5" id="materialStorage">入库</a>
                     <a href="#" class="btn btn-toggle mr5" id="materialBorrow">借用</a>
                     <a href="#" class="btn btn-toggle mr5" id="materialReturn">归还</a>
                     <a href="#" class="btn btn-toggle mr5" id="materialRefund">退还</a>
                     <a href="#" class="btn btn-toggle mr5" id="materialDelete">删除</a>
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
                             <th class="">
                                 <input type="checkbox" class="i-checks" name="input"  id="allOrNotChoose" onclick="mycheck(this)">
                             </th>
                             <th>案件编码</th>
                             <th>保管物品</th>
                             <th>提交人</th>
                             <th>保管人</th>
                             <th>文件位置</th>
                             <th>状态</th>
                             <th>时间记录</th>
                             <th>操作</th>
                         </tr>
                     </thead>
                     <tbody id="materialInfoList"></tbody> 
                 </table>
              </div>
	          <div class="text-center">
				<span id="currentTotalPage"><strong class="bold"></strong></span> <span
					class="ml15">共<strong class="bold" id="totalP"></strong>
				</span>&nbsp;
				<div id="pageBar" class="pagination my-pagination text-center m0"></div>
			</div>
            </div>
         </div>
         
         
     <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
       <div class="modal-dialog" style="width: 800px;">
             <div class="modal-content animated fadeIn popup-box">
                   <div class="modal_title">借用申请</div>
                   <form  class="form_list" id="materialBorrowForm">
                       <div class="line">
                           <div class="form_content">
                               <label class="control-label sign_left_small">借用人</label>
                               <input class="teamcode input_type" placeholder="" value="" name="actionUser" id="actionUser">
                               <div class="input-group float_icon organize_icon">
                               <i class="icon iconfont"></i>
                           </div>
                           </div>
                           <div class="form_content input-daterange" id="datepicker_1">
                               <label class="control-label sign_left_small">预计归还日期</label>
                               <input class="teamcode input_type  " placeholder="" value="" name="actionPreDate" id="actionPreDate">
                           </div>
                       </div>
                       <div class="line">
                           <div class="form_content">
                               <label class="control-label sign_left_small">用途</label>
                               <input class="teamcode input_type" style="width:590px;" placeholder="" value="" name="actionReason" id="actionReason">
                           </div>
                       </div>
                       <div class="line clearfix">
                           <div class="form_content">
                               <label class="control-label sign_left_small pull-left">备注</label>
                               <textarea style="width:590px;max-width:590px;height:100px;display:inline;margin-left:5px;" class="pull-left textarea" 
                               name="actionRemark" id="actionRemark" cols="30" rows="10"></textarea>
                           </div>
                       </div>
                       <div class="line">
                           <div class="add_btn text-center" style="margin:15px 126px;">
                               <button type="button" class="btn btn-success" id="materialBorrowSubmit">提交</button>
                               <button type="button" class="btn btn-grey" id="materialBorrowClose">关闭</button>
                           </div>
                       </div>
                   </form>

               </div>
           </div>
   </div>
   <div class="modal inmodal in" id="Return" tabindex="-1" role="dialog" aria-hidden="true">
       <div class="modal-dialog" style="width: 800px;">
           <div class="modal-content animated fadeIn popup-box">
               <div class="modal_title">归还</div>
               <form method="post" class="form_list">
                   <div class="line">
                       <div class="form_content">
                           <label class="control-label sign_left_small">归还人</label>
                           <input class="teamcode input_type" placeholder="" value=""  name="returnActionUser" id="returnActionUser">
                       </div>
                   </div>
                   <div class="line clearfix">
                       <div class="form_content">
                           <label class="control-label sign_left_small pull-left">备注</label>
                           <textarea style="width:590px;max-width:590px;height:100px;display:inline;margin-left:5px;" class="pull-left textarea" 
                           name="returnActionRemark" id="returnActionRemark" cols="30" rows="10"></textarea>
                       </div>
                   </div>
                   <div class="line">
                       <div class="add_btn text-center" style="margin:15px 126px;">
                           <button type="button" class="btn btn-success" id="materialReturnSubmit">提交</button>
                           <button type="button" class="btn btn-grey" id="materialReturnClose">关闭</button>
                       </div>
                   </div>
               </form>

           </div>
       </div>
   </div>
   <div class="modal inmodal in" id="GiveBack" tabindex="-1" role="dialog" aria-hidden="true">
       <div class="modal-dialog" style="width: 800px;">
           <div class="modal-content animated fadeIn popup-box">
               <div class="modal_title">退还</div>
               <form method="get" class="form_list">
                   <div class="line">
                       <div class="form_content">
                           <label class="control-label sign_left_small">退还人</label>
                           <input class="teamcode input_type" placeholder="" value=""  name="refundActionUser" id="refundActionUser">
                       </div>
                   </div>
                   <div class="line clearfix">
                       <div class="form_content">
                           <label class="control-label sign_left_small pull-left">备注</label>
                           <textarea style="width:590px;max-width:590px;height:100px;display:inline;margin-left:5px;" class="pull-left textarea" 
                           name="refundActionRemark" id="refundActionRemark" cols="30" rows="10"></textarea>
                       </div>
                   </div>
                   <div class="line">
                       <div class="add_btn text-center" style="margin:15px 126px;">
                           <button type="button" class="btn btn-success" id="materialRefundSubmit" >提交</button>
                           <button type="reset" class="btn btn-grey" id="materialRefundClose">关闭</button>
                       </div>
                   </div>
               </form>
           </div>
       </div>
   </div>
         
    </div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="signTimeStart" value="${signTimeStart}" />
	<input type="hidden" id="signTimeEnd" value="${signTimeEnd}" />

	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="userJobCode" value="${userJobCode}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
  	<form action="${ctx}/material/materialStorgae" accept-charset="utf-8" method="post" id="materialStorgaeForm">
  		<input type="hidden" id="pkids" name="pkids" value="" />  		
  	</form>
<content tag="local_script"> 
<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/trans/js/spv/spvRecordShow.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<!-- stickup plugin -->
<script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<script src="${ctx}/js/trunk/material/materialList.js"></script> 

<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>	
<script	id="template_materialInfoList" type="text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td> 	
							<input type="checkbox" class="i-checks" name="materialCheck" value="{{item.PKIDFORACTION}}" kkk="{{item.CASE_CODE}}" statusFlag="{{item.ITEM_STATUS}}">						
						
						</td>
						<td><p class="big"><a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" target="_blank"  class="caseCodeForshow">{{item.CASE_CODE}}</a></p>
                            <p class="big">						
							{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
								<p class = "demo-top"  title = "{{item.PROPERTY_ADDR}}">
							{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
							{{else}}
							</p>
							<p>
								{{item.PROPERTY_ADDR}}
							{{/if}}	
							</p>
						</td>

						<td>
                            <p><i class="sign_blue">{{item.ITEM_CATEGORY}}</i></p>
                            <p>中国建设银行储蓄卡</p>
                        </td>
						<td>
							<p><a class="demo-top" title="手机号： {{item.CREATE_BY_MOBILE}}<br/>{{item.CREATE_BY_ORG_NAME}}" href="#">{{item.CREATE_BY_REAL_NAME}}</a> </p>
                        </td>

						<td>
							<p><a class="demo-top" title="手机号： {{item.ITEM_MANAGER_MOBILE}}<br/>{{item.ITEM_MANAGER_ORG_NAME}}" href="#">{{item.ITEM_MANAGER_REAL_NAME}}</a> </p>
                        </td>
                        <td>
                            <p class="big">{{item.ITEM_ADDR_CODE}}</p>
                        </td>

                        <td>
						{{if item.ITEM_STATUS == "back"}}
                         	<p class="big">退还</p>
						{{else if item.ITEM_STATUS == "instock"}}
							<p class="big">在库</p>
						{{else if item.ITEM_STATUS == "borrow"}}
								{{if item.TODAY  > item.ACTION_PRE_DATE}}
                            		<p><i class="sign_brown">外借</i></p>
								{{else}}
									<p class="big">外借</p>
								{{/if}}   	
						{{else if item.ITEM_STATUS == "stay"}}
                            <p><i class="sign_brown"> 待入库</i></p>
						{{/if}}                           
                        </td>

					    <td>
						{{if item.ITEM_STATUS == "back"}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">入库</i>{{item.ITEM_INPUT_TIME}}
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">退还</i>{{item.ITEM_BACK_TIME}}
                                            </p>
						{{else if item.ITEM_STATUS == "instock"}}
                                           <p class="smll_sign">
                                                <i class="sign_normal">入库</i>{{item.ITEM_INPUT_TIME}}
                                            </p>
						{{else if item.ITEM_STATUS == "borrow"}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">入库</i>{{item.ITEM_INPUT_TIME}}
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">借用</i>{{item.ITEM_OUTPUT_TIME}}
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">归还</i>{{item.ACTION_PRE_DATE}}
                                            </p>
						{{else if item.ITEM_STATUS == "stay"}}
                                            <p class="smll_sign">                                                
                                            </p>
						{{/if}}
                        </td>
						 <td class="text-center">
                            <a href="${ctx}/material/materialDetail?pkid={{item.PKIDFORACTION}}">
                               <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i></button>
                            </a>
                        </td>
				  </tr>
       {{/each}}
</script> 
<script type="text/javascript">

</script>
</content>
</body>
</html>