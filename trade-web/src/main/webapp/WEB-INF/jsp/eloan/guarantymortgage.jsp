<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>抵押</title>

    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

    <link rel="stylesheet" href="${ctx}/static/css/animate.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">

    <link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
    <link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
    <!-- index_css  -->

        <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">

    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input2.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/eloan/eloan_detail.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css">

    <link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
    
    <!-- 上传相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
            <!-- main Start -->
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                         <jsp:include page="/WEB-INF/jsp/eloan/common/eloanBaseInfo.jsp"></jsp:include>
                    </div>

                    <div class="ibox-content ibox-space">
                        <form method="get" class="form_list" id="mortgageForm">
                         	<input type="hidden" id="riskControlId" name="riskControlId" value="${toRcMortgageVO.toRcMortgage.rcId }">
                            <div class="modal_title title-mark">
                                抵押信息登记
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                       <font color="red">*</font> 抵押合同编号
                                    </label>
                                    <input type="text" placeholder="" class="select_control sign_right_one" name="mortgageContractCode" id="mortgageContractCode" value="${toRcMortgageVO.toRcMortgage.mortgageContractCode }">
                                </div>
                                <div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>抵押登记时间
                                    </label>
                                    <input class="input_type sign_right_two" value="<fmt:formatDate value="${toRcMortgageVO.toRcMortgage.mortgageTime}" pattern="yyyy-MM-dd" />" name="mortgageTime" id="mortgageTime" />
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small" style="float:left">
                                                                                                      备注
                                    </label>
                                    <textarea class="select_control sign_right_two" id="riskComment" name="riskComment" style="width:400px;height:100px; display:block;margin-left:124px">${toRcMortgageVO.rcRiskControl.riskComment }</textarea>
                                </div>
                            </div>
                            
                           <div class="modal_title title-mark">
                                物品信息登记
                            </div>
                            <!-- <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        卡证管理员
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">小林</option>
                                        <option value="">小黄</option>
                                    </select>
                                </div>
                            </div> -->
                            <div id="mortgageList">
                            <c:forEach items="${toRcMortgageVO.toRcMortgageInfoList}" var="item" varStatus="status">
                            <div class="line" id="addTr">
                           		<input type="hidden" id="pkid" name="pkid" value="${item.pkid}">
                           		<input type="hidden" id="mortgageCode" name="mortgageCode" value="${item.mortgageCode}">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品类别
                                    </label>
                                    <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one mark"
							              display="select"  dictType="MORTGAGE_TYPE" tag="mortgage"
                                            ligerui='none' defaultvalue="${item.mortgageCategory}" onchange="change(this)"></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品名称
                                    </label>
                                    <input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="${item.mortgageName}">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>保管人
                                    </label>
	                                <input id="itemManager" name="itemManager" class="teamcode input_type" value="${item.itemManagerName}" hVal="${item.itemManager}" onclick="chooseItemManager(this)" />
	                                <div class="input-group float_icon organize_icon managerOnclick">
									 	<i class="icon iconfont">&#xe627;</i>
									</div>
                                </div>
                                <a href="javascript:void(0)" class="add_space" onclick="getAdd(this)">添加</a>
                                <c:if test="${status.first==false}"><a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a></c:if>
                                <c:if test="${item.mortgageCategory=='propertyCard'}">
                                <div class="entry">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            产权编号
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referCode" name="referCode" value="${item.referCode}">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            产权人姓名
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referName" name="referName" value="${item.referName}">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房屋地址
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referAddreass" name="referAddreass" value="${item.referAddreass}">
                                    </div>
                                </div>
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            他证编号
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referCode" name="referCode" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            他项权利人
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房屋地址
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referAddreass" name="referAddreass" value="">
                                    </div>
                                </div>
                                </c:if>
                                <c:if test="${item.mortgageCategory=='otherCard'}">
                                <div class="entry" style="display:none;">
                                     <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            产权编号
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referCode" name="referCode" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            产权人姓名
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房屋地址
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referAddreass" name="referAddreass" value="">
                                    </div>
                                </div>
                                <div class="entry">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            他证编号
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referCode" name="referCode" value="${item.referCode}">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            他项权利人
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referName" name="referName" value="${item.referName}">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房屋地址
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referAddreass" name="referAddreass" value="${item.referAddreass}">
                                    </div>
                                </div>
                                </c:if>
                            </div> 
                            </c:forEach>
                            <c:if test="${empty toRcMortgageVO.toRcMortgageInfoList}">
                            <div class="line" id="addTr">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品类别
                                    </label>
                                    <aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one mark"
							              display="select"  dictType="MORTGAGE_TYPE" tag="mortgage"
                                            ligerui='none' defaultvalue="${item.mortgageCategory}" onchange="change(this)"></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        物品名称
                                    </label>
                                    <input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        <font color="red">*</font>保管人
                                    </label>
	                                <input id="itemManager" name="itemManager" class="teamcode input_type" value="" hVal="" onclick="chooseItemManager(this)" />
	                                <div class="input-group float_icon organize_icon managerOnclick">
									 	<i class="icon iconfont">&#xe627;</i>
									</div>
                                </div>
                                <a href="javascript:void(0)" class="add_space" onclick="getAdd(this)">添加</a>
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            产权编号
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referCode" name="referCode" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            产权人姓名
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房屋地址
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referAddreass" name="referAddreass" value="">
                                    </div>
                                </div>
                                <div class="entry" style="display:none;">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            他证编号
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referCode" name="referCode" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            他项权利人
                                        </label>
                                        <input type="text" placeholder="" class="select_control sign_right_one" id="referName" name="referName" value="">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房屋地址
                                        </label>
                                        <input type="text" placeholder="" class="select_control teamcode" id="referAddreass" name="referAddreass" value="">
                                    </div>
                                </div>
                            </div>
                            </c:if>
                            </div>
                            
        <div class="ibox-title" style="height: auto;">
			<c:choose>
				<c:when test="${accesoryList!=null}">
					<h5>
						上传备件<br> <br> <br>${accesoryList[0].accessoryName }</h5>
					<c:forEach var="accesory" items="${accesoryList}"
						varStatus="status">
						<div class="" id="fileupload_div_pic">
							<form id="fileupload"
								action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
								method="POST" enctype="multipart/form-data">
								<noscript>
									<input type="hidden" name="redirect"
										value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
									<input type="hidden" id="preFileCode" name="preFileCode"
										value="${accesory.accessoryCode }">
								</noscript>
								<c:if test="${status.index != 0}">
									<h5 align="left">
										<br>${accesory.accessoryName }</h5>
								</c:if>
								<div class="row-fluid fileupload-buttonbar">
									<div class="" style="height: auto">
										<div role="presentation" class="table table-striped "
											style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
											<div id="picContainer${accesory.pkid }" class="files"
												data-toggle="modal-gallery" data-target="#modal-gallery"></div>
											<span class=" fileinput-button "
												style="margin-left: 10px !important; width: 80px;">
												<div id="chandiaotuBtn" class=""
													style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
													<i class="fa fa-plus"></i>
												</div> <input id="picFileupload${accesory.pkid }" type="file"
												name="files[]" multiple
												data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
												data-sequential-uploads="true">
											</span>
										</div>
									</div>
								</div>
							</form>
						</div>

						<div class="row-fluid">
							<div class="">
								<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-left:10px;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
									<!--图片缩图  -->
							        <div class="preview"><span class="fade"></span></div>
									<!--  错误信息 -->
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									<!-- 单个对应的按钮  -->
							            <div class="start span1" style="display: none">
										{% if (!o.options.autoUpload) { %}
							                <button class="btn">
							                    <i class="icon-upload icon-white"></i>
							                    <span>上传</span>
							                </button>
							            {% } %}
										</div>
							        {% } else { %}
							            <div class="span1" colspan="2"></div>
							        {% } %}
							        <div class="cancel" style="margin-top:-125px;margin-left:85%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
								<script id="templateDownload${accesory.pkid }"
									type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.id) { %}
                                              {% if (((file.name).substring((file.name).lastIndexOf(".")+1))=='tif') { %}
							               		<img src="${ctx }/img/tif.png" alt="" width="80px" height="80px">
                                              {% } else { %}
 												 <img src="${imgweb}/filesvr/downLoad?id={%=file.id%}" alt="" width="80px" height="80px">
  											  {% } %}
							            {% } %}</div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
							</div>
						</div>
					</c:forEach>

					<div class="row-fluid" style="display: none;">
						<div class="span4">
							<div class="control-group">
								<a class="btn blue start" id="startUpload"
									style="height: 30px; width: 50px"> <i
									class="icon-upload icon-white"></i> <span>上传</span>
								</a>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h5>
						上传备件<br>无需上传备件
					</h5>
				</c:otherwise>
			</c:choose>
		</div>
                        </form>
                        <div class="status_btn text-center mt15">
                            <button class="btn btn-success btn-space submit_btn">保存</button>
                            <button class="btn btn-default" data-dismiss="modal" data-toggle="modal" data-target="#myModal">关闭</button>
                        </div>
                        <button type="button" class="close close_blue" style="display:none;" data-dismiss="modal">
                            <i class="iconfont icon_rong">&#xe60a;
                            </i>
                        </button>
                    </div>


                </div>
            </div>

                        <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeIn">
                        <div class="modal-body" style="background:#fff;">
                          <!--   <p class="text-center" style="font-size: 20px;">选择保存按钮可保存本次填写信息！</p> -->
                          <p class="text-center" style="font-size: 20px;">请确认填写的信息是否保存，确认后将不保存信息！</p>
                        </div>
                        <div class="modal-footer" style="text-align:center;">
                        <!--     <button type="button" class="btn btn-success submit_btn" style="margin-left:0px;">确认保存</button> -->
                            <!-- <button type="button" class="btn btn-success">我要删除</button> -->
                            <button type="button" class="btn  btn-success close_btn" data-win="modal">确定</button>
                             <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- main End -->
    </div>
<content tag="local_script"> 
    <!-- Mainly scripts -->
<!-- <script src="../static/js/jquery-2.1.1.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
    <script src="../static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="../static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> -->

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${ctx}/static/trans/js/plugins/poshytip/jquery.poshytip.js"></script>
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <!-- 开关按钮js -->
    <script src="${ctx}/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js"></script>
    <script src="${ctx}/static/trans/js/eloan/eloan_guarant.js"></script>
     <!-- 上传附件相关 --> <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
	<!-- 选择组织控件 --> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> <!-- 上传附件 结束 -->
    <!-- 附件保存修改相关 --> <script src="${ctx}/js/trunk/task/attachment2.js"></script>
    <script>
	    if ("${idList}" != "") {
			var idList = eval("(" + "${idList}" + ")");
		} else {
			var idList = [];
		}
		var taskitem = "RiskControl_Mortgage";
	    $(document).ready(function () {
	    	$('.input-daterange').datepicker({
	        	format : 'yyyy-mm-dd',
	    		weekStart : 1,
	    		autoclose : true,
	    		todayBtn : 'linked',
	    		language : 'zh-CN'
	        });
	    	
	    	var eloanCode =  "${eloanCase.eloanCode }";
	    	var caseCode = "${eloanCase.caseCode }";
        	var pkid = "${pkid}";
        	
        	
        	 $('.submit_btn').click(function(){
        		var mortgageContractCode = $("#mortgageContractCode").val();
				var mortgageTime = $("#mortgageTime").val();
				var mortgagePropertyAddress = $("#mortgagePropertyAddress").val();
				var propertyName = $("#propertyName").val();
				var propertyCode = $("#propertyCode").val();
				var otherCode = $("#otherCode").val();
				if(mortgageContractCode == ""){
					 alert("请填写抵押合同编号！");
					 return false;
				}
				if(mortgageTime == ""){
					 alert("请填写抵押登记时间！");
					 return false;
				}
				if(mortgagePropertyAddress == ""){
					 alert("请填写抵押物业地址！");
					 return false;
				}
				if(propertyName == ""){
					 alert("请填写 产权人姓名！");
					 return false;
				}
				if(propertyCode == ""){
					 alert("请填写产证编号！");
					 return false;
				}
				if(otherCode == ""){
					 alert("请填写他证编号！");
					 return false;
				}
				var validataItemManager = false;
				var toRcMortgageInfoList = new Array();
             	$("#mortgageList .line").each(function(i){
             		if($(this).is(":hidden")) { 
             			var isWillDeleted = 'Y';
             		} else {
             			var isWillDeleted = 'N';
             		}
             		var pkid = $(this).children("input[name='pkid']").val()==undefined?null:$(this).children("input[name='pkid']").val();
             		var mortgageCategory = $(this).find("#mortgageCategory").val();
             		var mortgageCode = $(this).find("#mortgageCode").val()==undefined?null:$(this).find("#mortgageCode").val();
             		var mortgageName = $(this).find("#mortgageName").val();
             		var itemManager = $(this).find("#itemManager").attr('hVal');
             		var referName = $(this).find(".entry:visible").find("#referName").val();
             		var referCode = $(this).find(".entry:visible").find("#referCode").val();
             		var referAddreass = $(this).find(".entry:visible").find("#referAddreass").val();
             		
             		// 校验保管人必选项
             		if(itemManager=='') {
             			validataItemManager = true;
             		}
             		
             		var toRcMortgageInfo = {
             			pkid : pkid,
             			mortgageCategory : mortgageCategory,
             			mortgageCode : mortgageCode,
             			mortgageName : mortgageName,
             			itemManager : itemManager,
             			referName : referName,
             			referCode : referCode,
             			referAddreass : referAddreass,
             			isWillDeleted : isWillDeleted
             		}
             		toRcMortgageInfoList.push(toRcMortgageInfo);
             	})
             	
            	if(validataItemManager) {
             		alert("请选择保管人！");
					return false;
             	}
             	
             	
             	var toRcMortgage = {
             		mortgageContractCode : $('#mortgageContractCode').val(),
             		mortgageTime : $('#mortgageTime').val(),
             		mortgagePropertyAddress : $('#mortgagePropertyAddress').val(),
             		propertyName : $('#propertyName').val(),
             		propertyCode : $('#propertyCode').val(),
             		otherCode :$('#otherCode').val()
             	}
            	var rcRiskControl = {
             		pkid : $('#riskControlId').val(),
             		eloanCode : eloanCode,
             		caseCode : caseCode,
             		riskComment : $('#riskComment').val(),
             		riskType : 'mortgage'
                }
             	var toRcMortgageVO = {
             		toRcMortgageInfoList : toRcMortgageInfoList,
             		toRcMortgage : toRcMortgage,
             		rcRiskControl : rcRiskControl
             	}
             	
             	var url = "${ctx}/riskControl/saveRcMortgage";
     			$.ajax({
     				cache : true,
     				async : false,//false同步，true异步
     				type : "POST",
     				url : url,
     				dataType : "json",
     				contentType:"application/json",  
     				data : JSON.stringify(toRcMortgageVO),
     				beforeSend : function() {
     					$.blockUI({
     						message : $("#salesLoading"),
     						css : {
     							'border' : 'none',
     							'z-index' : '9999'
     						}
     					});
     					$(".blockOverlay").css({
     						'z-index' : '9998'
     					});
     				},
     				complete : function() {
     					$.unblockUI();
     				},
     				success : function(data) {
     					alert(data.message);
     					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
     				},
     				error : function(errors) {
     					alert("数据保存出错");
     				}
     			});
             	
     			// 保存附件相关信息
     			deleteAndModify();
             });
             
             $(".close_btn").click(function(){
 					window.location.href = ctx+"/eloan/getEloanCaseDetails?pkid="+pkid;
             })
	    })
	    
	    var divIndex = 1;
		function getAtr(i){
		    var addTr=$('#hideAddTr').clone();
		    addTr.show();
		    $("#mortgageList").append(addTr);
		    divIndex++;
		}
		
		function getDel(k){
		    $(k).parents('.line').remove();
		    divIndex--;
		}
		//选择组织之后 级联选择主办人信息
		var o;
		function chooseItemManager(obj) {
			o = obj;
			userSelect({
				startOrgId : 'ff8080814f459a78014f45a73d820006',
				expandNodeId : '',
				nameType : 'long|short',
				orgType : '',
				departmentType : '',
				departmentHeriarchy : '',
				chkStyle : 'radio',
				//jobCode : 'consultant',
				callBack : selectUserBack
			});
		}
		//选取人员的回调函数
		function selectUserBack(array) {
			if (array && array.length > 0) {
				$(o).attr('value',array[0].username);
				$(o).attr('hVal', array[0].userId);
			} else {
				$(o).val("");
				$(o).attr('hVal', "");
			}
		}
    </script>
</content>
</body>
</html>