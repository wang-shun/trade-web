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
    <title>回访
    </title>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="${ctx}/static/css/animate.css" />
    <link rel="stylesheet" href="${ctx}/static/css/style.css" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/caseDetail.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/details.css" >
    <link rel="stylesheet" href="${ctx}/css/transcss/comment/caseComment.css">
</head>

<body class="pace-done">
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
    <!-- 右侧页面主体内容 -->
    <input type="hidden" id="taskId" name="taskId" value="${taskId}">
    <input type="hidden" id="instCode" name="instCode" value="${instCode}">
    <!-- 主要内容页面 -->
    <nav id="navbar-example" class="navbar navbar-default navbar-static" role="navigation">
        <div id="isFixed" style="position: relative; top: 0px;" class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
            <ul class="nav navbar-nav scroll_content">
                <li class="menuItem active"><a href="#"> 基本信息 </a></li>
                <li class="menuItem"><a href="#"> 服务流程 </a></li>
                <li class="menuItem"><a href="#"> 相关信息 </a></li>
            </ul>
        </div>
    </nav>
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
                <div class="top12 panel" id="basicInfo">
                    <div class="sign sign-red">
                        结案
                    </div>
                    <div class="sign sign-blue">
                        已过户
                    </div>
                    <div class="sign sign-yellow">
                        税费卡
                    </div>
                    <div class="panel-body">
                        <div class="ibox-content-head">
                            <h2 class="title">
                                案件基本信息
                            </h2>
                            <small class="pull-right">誉萃编号：${toCaseInfo.caseCode}｜中原编号：${toCaseInfo.ctmCode}</small>
                        </div>
                        <div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>CTM地址</dt>
											<dd>${toPropertyInfo.ctmAddr}</dd>
											<dt>产证地址</dt>
											<dd>${toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${toPropertyInfo.square}平方</dd>
											<dt>房屋类型</dt>
											<dd>
												<aist:dict id="propertyType" name="propertyType"
													display="label" dictType="30014"
													dictCode="${toPropertyInfo.propertyType}" />
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_two col-sm-5">
									<span>买卖双方</span>
									<div class="ibox-conn else_conn">
										<dl class="dl-horizontal col-sm-6">
											<dt>上家姓名</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>下家姓名</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									</div>
									<span>经纪人信息</span>
									<div class="ibox-conn else_conn_two ">
										<dl class="dl-horizontal">
											<dt>姓名</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${toCaseInfo.agentPhone}">
													${caseDetailVO.agentName}</a>
											</dd>
											<dt>所属分行</dt>
											<dd>${toCaseInfo.grpName }</dd>
											<dt>直管经理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.mcMobile}">
													${caseDetailVO.mcName} </a>
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_three col-sm-3">
									<span>经办人信息</span>
									<div class="ibox-conn  ibox-text">
										<dl class="dl-horizontal">
											<dt>交易顾问</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.cpMobile}">
													${caseDetailVO.cpName} </a>
											</dd>
											<c:if test="${empty caseDetailVO.proList}">
												<dt>合作顾问</dt>
												<dd></dd>
											</c:if>
											<c:if test="${!empty caseDetailVO.proList}">
												<c:forEach items="${caseDetailVO.proList}" var="pro">
													<dt>合作顾问</dt>
													<dd>
														<a data-toggle="popover" data-placement="right"
															data-content="${pro.processorMobile}">
															${pro.processorName} </a>
													</dd>
												</c:forEach>
											</c:if>
											<dt>助理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.asMobile}">
													${caseDetailVO.asName} </a>
											</dd>
										</dl>
									</div>
								</div>

							</div>
						</div>
                    </div>
                </div>

            <div class="row wrapper white-bg new-heading ">
            <div class="pl10">
            <h2 class="newtitle-big">
                签约回访
            </h2>
            </div>
        </div>

    <div class="ibox-content border-bottom clearfix space_box noborder">
        <div>
            <h2 class="newtitle title-mark">上家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">赵晓明—189898989898</span>
                            <span >赵晓明2—189898989898</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                            <select class="select_control yuanwid">
                                <option value="">是</option>
                                <option value="">否</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label> 
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label> 
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪还贷评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪同还贷意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">下家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">贾先生—13555555555</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid">
                                    <option value="">是</option>
                                    <option value="">否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">经纪人回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">阮俊—13666666666</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid">
                                    <option value="">是</option>
                                    <option value="">否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>

        
        <div style="height: auto;">
					    <div class="mb15">
			            	<h2 class="newtitle title-mark">上传附件</h2>
			            	<div class="table-box" id="fileUploadContainer"></div>
       					</div>	
		</div>		
        <div>
           				<div id="caseCommentList" class="add_form"></div>
        </div>
        
                        <div class="form-btn">
                            <div class="text-center">
                                <button  class="btn btn-success btn-space">保存</button>
                                <button class="btn btn-success btn-space" data-toggle="modal" data-target="#myModal">回访打回</button>
                                <button class="btn btn-success btn-space">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--********** 弹窗页面 **********-->
                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width:760px;">
                        <div class="animated fadeIn popup-box" style="background-color: #fff;padding:30px 30px 60px 30px;">
                            <div class="modal_title">
                                回访打回
                            </div>
                            <textarea name="" id="" class="textarearoom mt10" style="width:100%;max-width: 760px;margin-left:0;max-height: 150px;height: 150px;" >
                            </textarea>
                            <div class="add_btn text-center mt20">
                                <button type="button" class="btn btn-success">
                                    打回
                                </button>
                                <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                    关闭
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <!--********** 弹窗页面 **********-->
                
          <content tag="local_script">
        	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
        	<script	src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        	<script	src="${ctx}/js/template.js" type="text/javascript"></script>
       		<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
        	<script	src="${ctx}/js/trunk/comment/caseComment.js"></script>
        	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script>
        	<script src="${ctx}/js/viewer/viewer.min.js"></script>
	        <script type="text/javascript">
	        $(function(){
				$("#caseCommentList").caseCommentGrid({
					caseCode : '${toCaseInfo.caseCode}',
					srvCode : null
				});
				
			    $('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
		 	    $('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));
	        })

	 	     /*动态生成上下家*/
 			function generateSellerAndBuyer(name, phone){
 	 			var nameArr = name.split('/');
 	 			var phoneArr = phone.split('/');
 	 			var str='';
 	 			for (var i=0; i<nameArr.length; i++) {
 	 				if(i%2==0){
 	 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a>&nbsp;';
 	 				}else{
 	 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a><br/>';
 	 				}
 	 			}
 	 			return str;
 	 		}
	        </script>
        </content>     
        <content tag="local_require">
	       <script>
       		var fileUpload;
		    require(['main'], function() {
				requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','blockUI','steps','ligerui','aistJquery','poshytip','twbsPagination','bootstrapModal','modalmanager','eselect'],function($,aistFileUpload){
					fileUpload = aistFileUpload;
						fileUpload.init({
				    		caseCode : 'ZY-SH-201612-0115',
				    		partCode : "SpvApplyApprove",
				    		fileUploadContainer : "fileUploadContainer",
				    		readonly : true,
				    	});
					})
			    });
			</script>
	    </content>      
    </body>
    </html>