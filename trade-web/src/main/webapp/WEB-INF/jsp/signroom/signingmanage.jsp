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
<!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                          	  签约室配置管理
                        </h2>
                        <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        	签约中心
                                    </label>
                                    <select class="select_control " id="centerId" name="centerId">
                                        <option value="">请选择</option>
                                        <c:forEach items="${tradeCenters }" var="tradeCenter">
                                        	<option value="${tradeCenter.pkid }">${tradeCenter.centerName }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        	房间类型
                                    </label>
                                    <select class="select_control sign_right_one" name="roomTypeSlot" id="roomTypeSlot">
                                        <option value="">请选择</option>
                                        <option value="0">普通房间</option>
                                        <option value="1">机动房间</option>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        	状态
                                    </label>
                                    <select class="select_control sign_right_one" id="roomStatus" id="roomStatus">
                                        <option value="">请选择</option>
                                        <option value="0">关闭</option>
                                        <option value="1">开放</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line">
                                <div class="add_btn" style="float:left;margin-left:126px;">
                                    <button type="button" class="btn btn-success" id="searchBtn">
                                        	查询
                                    </button>
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal" id="addSignRoom">
                                       	 添加
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table_content">
                                <table class="table table_blue table-striped table-bordered table-hover " id="schedualable" >
                                    <thead>
                                    <tr>
                                        <th>房间号</th>
                                        <th>房间类型</th>
                                        <th>签约中心名称</th>
                                        <th>人数</th>
                                        <th>开放策略</th>
                                        <th>状态</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false">
                    <div class="modal-dialog" style="width: 930px;">
                        <div class="modal-content animated fadeIn popup-box">
                            <div class="modal_title" id="title">
                            </div>
                            <form method="post" class="form_list" name="addForm">
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            签约中心选择
                                        </label>
                                        <select class="select_control " name="rmSignRoom.centerId" id="rmSignRoom.centerId">
                                            <option value="">请选择</option>
                                            <c:forEach items="${tradeCenters }" var="tradeCenter">
                                        	<option value="${tradeCenter.pkid }">${tradeCenter.centerName }</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房间编号
                                        </label>
                                        <input class="pop-name input_type" placeholder="" value="" name="rmSignRoom.roomNo" id="rmSignRoom.roomNo" />
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            可容纳人数
                                        </label>
                                        <input class="pop-name input_type" placeholder="" value="" name="rmSignRoom.numbeOfAccommodatePeople" id="rmSignRoom.numbeOfAccommodatePeople"/>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房间类型
                                        </label>
                                        <div class="checkbox i-checks radio-inline sign sign_right">
                                            <label>
                                                <input type="radio" value="0"  name="rmSignRoom.roomType" checked="checked">
                                                普通房间
                                            </label>
                                            <label>
                                                <input type="radio" value="1"  name="rmSignRoom.roomType">
                                                机动房间
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small pull-left">
                                            房间备注
                                        </label>
                                            <textarea name="rmSignRoom.remark" id="rmSignRoom.remark" class="pull-left textarearoom" placeholder="请输入2~20字"></textarea>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small pull-left">
                                            开放策略
                                        </label>
                                        <div class="pull-left week">
                                            <label>
                                                <input type="checkbox"  name="items" id="CheckedAll">
                                                全选
                                            </label>
                                            <label>
                                                <input type="checkbox" value="工作日"  name="items1" id="work" >
                                                工作日
                                            </label><br/>
                                            <label >
                                                <input type="checkbox" class="work1" value="1"  name="items">
                                                周一
                                            </label>
                                            <label >
                                                <input type="checkbox" class="work1" value="2"   name="items">
                                                周二
                                            </label>
                                            <label >
                                                <input type="checkbox" class="work1" value="3"  name="items">
                                                周三
                                            </label>
                                            <label >
                                                <input type="checkbox" class="work1" value="4"  name="items">
                                                周四
                                            </label>
                                            <label >
                                                <input type="checkbox" class="work1" value="5"  name="items">
                                                周五
                                            </label>
                                            <label>
                                                <input type="checkbox" value="6"  name="items" class="zhoumo">
                                                周六
                                            </label>
                                            <label>
                                                <input type="checkbox" value="7"  name="items" class="zhoumo">
                                                周日
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            房间状态
                                        </label>
                                        <div class="checkbox i-checks radio-inline sign sign_right">
                                            <label>
                                                <input type="radio" value="1"  name="rmSignRoom.roomStatus" checked="checked">
                                                开放
                                            </label>
                                            <label>
                                                <input type="radio" value="0"  name="rmSignRoom.roomStatus">
                                                关闭
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="add_btn" style="float:left;margin:15px 126px;">
                                        <button type="button" class="btn btn-success" id="saveBtn">
                                           <span id="btnName"></span>
                                        </button>
                                        <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                            取消
                                        </button>
                                    </div>
                                </div>
                                <input type="hidden" name="pkid" id="pkid" value=""> 
                            </form>

                        </div>
                </div>
                
 <!-- Mainly scripts -->
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
       
         <script src="${ctx}/transjs/signing/sigingManager.js"></script>
        <script>
            $(document).ready(function () {
                //全选
                $("#CheckedAll").click(function(){
                    var isChecked = $(this).prop("checked");
                    $('input[name=items]').prop("checked", isChecked );
                    $("#work").prop("checked", false );

                });
                $('input[type=checkbox][name=items]').click(function(){
                    var flag=true;
                    $('input[type=checkbox][name=items]').each(function(){
                        if(!$(this).prop("checked") == true){
                            flag = false;
                        }
                    });
                    if( flag ){
                        $('#CheckedAll').prop('checked', true );
                    }else{
                        $('#CheckedAll').prop('checked', false );
                    }
                });
                //工作日选择
                $("#work").click(function() {
                    var isChecked = $(this).prop("checked");
                    if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
                        $('.work1').prop("checked", isChecked );
                        $(".zhoumo").prop("checked", false );
                        $("#CheckedAll").prop("checked", false );
                    }else{
                        $('.work1').prop("checked", false );
                    }
                });

                $('.work1').click(function(){
                    var flag=true;
                    $('.work1').each(function(){
                        if(!$(this).prop("checked") == true){
                            flag = false;
                        }
                    });
                    if( !flag ){
                        $("#work").prop('checked', false );
                    }
                });
                $(".zhoumo").click(function(){
                    if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
                        $('#work').prop("checked", false );
                    }
                });



                //日历控件
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