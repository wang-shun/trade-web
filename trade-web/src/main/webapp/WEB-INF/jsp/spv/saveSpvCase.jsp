<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewpoart" content="width=device-width, initial-scale=1.0">

    <title>E+贷款</title>

    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
        <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
    
    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
    <link href="${ctx}/static/css/plugins/stickup/stickup.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/spv/input.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
    <!-- stickUp fixed css -->
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
            <!-- main Start -->
            <div class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
               <ul class="nav navbar-nav">
                    <li class="menuItem active"><a href="#base_info">客户信息</a></li>
                    <li class="menuItem"><a href="#spvone_info">房产及交易信息</a></li>
                    <li class="menuItem"><a href="#spvtwo_info">监管资金及账户信息</a></li>
                    <li class="menuItem"><a href="#spvthree_info">资金方案填写</a></li>
                </ul>
                <div class="menu_btn" style="float: right;
    margin-right: 250px;margin-top: 6px">
                    <button class="btn btn-warning">保存</button>
                </div>
            </div>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->

                    <div class="ibox-content" id="base_info">
                        <form class="form-inline">
                            <div class="title">
                                买方客户信息
                            </div>

                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >买方姓名</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one ">
                                    <label for="" class="lable-one" >买方性别</label>
                                    <span class="sex-char">
                                        <label class="radio-inline">
                                          <input type="radio" name="sex" id="sex1" value="option1" checked=""> 男
                                        </label>
                                        <label class="radio-inline">
                                          <input type="radio" name="sex" id="sex2" value="option2"> 女
                                        </label>
                                    </span>
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >买方手机号码</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >买方证件类型</label>
                                    <select name="" id="" class="form-control input-one">
                                        <option value="">证件类型</option>
                                    </select>
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >证件有效期</label>
                                    <input type="text" class="form-control input-one" placeholder="如：2010-08">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >证件编号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>

                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >发证机关</label>
                                    <input type="text" class="form-control input-four" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >买方家庭地址</label>
                                    <input type="text" class="form-control input-five" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >是否委托他人办理</label>
                                    <label class="radio-inline">
                                      <input type="radio" name="RadioOptions" id="BuyRadio1" value="option1"> 是
                                    </label>
                                    <label class="radio-inline">
                                      <input type="radio" name="RadioOptions" id="BuyRadio2" value="option2"> 否
                                    </label>
                                </div>

                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent buyinfo">
                                    <label for="" class="lable-one" >委托人姓名</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one left-extent buyinfo">
                                    <label for="" class="lable-one" >委托人证件类型</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one buyinfo">
                                    <label for="" class="lable-one" >证件编号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>
                            </div>
                        </form>
                        <form class="form-inline">
                            <div class="title">
                                卖方客户信息
                            </div>

                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >卖方姓名</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one ">
                                    <label for="" class="lable-one" >卖方性别</label>
                                    <span class="sex-char">
                                        <label class="radio-inline">
                                          <input type="radio" name="sex" id="sex3" value="option1" checked=""> 男
                                        </label>
                                        <label class="radio-inline">
                                          <input type="radio" name="sex" id="sex4" value="option2"> 女
                                        </label>
                                    </span>
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >卖方手机号码</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >卖方证件类型</label>
                                    <select name="" id="" class="form-control input-one">
                                        <option value="">证件类型</option>
                                    </select>
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >证件有效期</label>
                                    <input type="text" class="form-control input-one" placeholder="如：2010-08">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >证件编号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>

                            </div>
                            <div class="form-row form-rowbot">

                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >发证机关</label>
                                    <input type="text" class="form-control input-four" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >卖方家庭地址</label>
                                    <input type="text" class="form-control input-five" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >是否委托他人办理</label>
                                    <label class="radio-inline">
                                      <input type="radio" name="RadioOptions" id="SellRadio1" value="option1"> 是
                                    </label>
                                    <label class="radio-inline">
                                      <input type="radio" name="RadioOptions" id="SellRadio2" value="option2"> 否
                                    </label>
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent sellinfo">
                                    <label for="" class="lable-one" >委托人姓名</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one left-extent sellinfo">
                                    <label for="" class="lable-one" >委托人证件类型</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one sellinfo">
                                    <label for="" class="lable-one" >证件编号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="ibox-content" id="spvone_info">
                        <form class="form-inline">
                            <div class="title">
                                房产及交易信息
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >房产证号</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >房产权利人</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >委托人姓名</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >面积</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                    <span class="date_icon">平方米</span>
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >房屋地址</label>
                                    <input type="text" class="form-control input-five" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">

                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-two" >是否偿清</label>
                                    <label class="radio-inline">
                                      <input type="radio" name="RadioOptions" id="Pledge1" value="option1"> 是
                                    </label>
                                    <label class="radio-inline">
                                      <input type="radio" name="RadioOptions" id="Pledge2" value="option2"> 否
                                    </label>
                                    <span class="span-tag pledgeinfo">交易房屋抵押贷信息</span>
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent pledgeinfo">
                                    <label for="" class="lable-one" >抵押方</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one pledgeinfo">
                                    <label for="" class="lable-one" >开户行</label>
                                    <input type="text" class="form-control input-four" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent pledgeinfo">
                                    <label for="" class="lable-one" >未偿还金额</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one pledgeinfo">
                                    <label for="" class="lable-one" >金额大写</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >网签合同号</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >下家付款方式</label>
                                    <select class="form-control input-one">
                                        <option value="">全数</option>
                                    </select>
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >网签金额</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                    <span class="date_icon">元</span>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="ibox-content" id="spvtwo_info">
                        <form class="form-inline">
                            <div class="title">
                                监管资金及账户信息
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >监管总金额</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                    <span class="date_icon">元</span>
                                </div>
                            </div>
                            <div class="title">
                                监管资金的支付
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >自筹资金</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                    <span class="date_icon">元</span>
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >贷款资金</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="rate" >其中： 商业贷款</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                    <span class="date_icon">元</span>
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="rate" >公积金贷款</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                    <span class="date_icon">元</span>
                                </div>
                            </div>
                            <div class="form-row form-rowbot">

                            </div>
                            <div class="title">
                                资金监管账号信息
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >卖方收款账号名称</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >电话</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >开户行</label>
                                    <input type="text" class="form-control input-four" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >账号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >买方退款账号名称</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >电话</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >开户行</label>
                                    <input type="text" class="form-control input-four" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >账号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >托管账户名称</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >账号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>
                            </div>
                            <div class="form-row form-rowbot">
                                <div class="form-group form-margin form-space-one left-extent">
                                    <label for="" class="lable-one" >资金方账户名称</label>
                                    <input type="text" class="form-control input-one" placeholder="">
                                </div>
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one" >账号</label>
                                    <input type="text" class="form-control input-two" placeholder="">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="ibox-content" id="spvthree_info">
                            <div class="agree-tile">
                                资金出款约定
                            </div>
                        <form class="form-inline table-capital">
                            <p>监管资金次数合计<span id="sum"> 1</span> 次</p>
                            <div class="table-box" >
                                <table class="table table-bordered customerinfo">
                                    <thead>
                                        <th>划转条件</th>
                                        <th>账户</th>
                                        <th style="width: 16%;">金额</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </thead>
                                    <tbody id="addTr">
                                        <tr align="center">
                                            <td class="text-left">
                                                <select class="table-select" >
                                                    <option value="">买方贷款审批完成</option>
                                                </select>
                                            </td>
                                            <td class="text-left">
                                                <select class="table-select">
                                                    <option value="">资金方</option>
                                                    <option value="">卖方</option>
                                                </select>
                                            </td>
                                            <td><input class="table-input-one" type="text" placeholder="请输入金额" />元</td>
                                            <td class="text-left" ><input class="table-input" type="text" placeholder="" /></td>
                                            <td align="center"><a href="javascript:void(0)" onClick="getAtr(this)">添加</span></a>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="form-btn">
                            <div >
                                <button type="submit" class="btn btn-success">提交申请</button>
                                <button type="submit" class="btn btn-default">取消</button>
                            </div>
                        </div>
                            </div>
                        </form>

                    </div>

                </div>
            </div>
            <!-- main End -->

    </div>

    <!-- Mainly scripts -->
     <content tag="local_script">
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    <!-- Toastr script -->
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/static/js/morris/morris.js"></script>
    <script src="${ctx}/static/js/morris/raphael-min.js"></script>

    <!-- index_js -->
    <script src="${ctx}/static/trans/js/eloan/eloan.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
    <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
   	<script id="addMoneyRelease" type= "text/html">
                           <li id="releaseDiv{{divIndex}}">
                                <div class="form_content">
                                    <label class="control-label sign_left_two">放款金额</label>
                                    <input class="input_type sign_right_two" value="" id="releaseAmount" name="releaseAmount"/>
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                                <div class="form_content form_nomargin input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_two">放款时间</label>
                                    <input class="input_type sign_right_two" value="" id="releaseTime" name="releaseTime"/>
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                                <button class="btn btn-success margin_tagl15" id="remove{{divIndex}}" onclick="removeDiv('{{divIndex}}');">删除</button>
                            </li>
	</script>
	 <script>
        $(".buyinfo, .sellinfo, .pledgeinfo").hide();
        $("#BuyRadio1").on("click",function() {
            $(".buyinfo").show();
        });
        $("#BuyRadio2").on("click",function() {
            $(".buyinfo").hide();
        });
        $("#SellRadio1").on("click",function() {
            $(".sellinfo").show();
        });
        $("#SellRadio2").on("click",function() {
            $(".sellinfo").hide();
        });
        $("#Pledge1").on("click",function() {
            $(".pledgeinfo").show();
        });
        $("#Pledge2").on("click",function() {
            $(".pledgeinfo").hide();
        });
    </script>
<script>
var sum=1; //定义sum为全局变量
/*$(function(){
    $("#getAtr").click(function(){
        $str='';
        $str+="<tr align='center'>";
        $str+="<td>2</td>";
        $str+="<td>2</td>";
        $str+="<td>2</td>";
        $str+="<td>2</td>";
        $str+="<td onClick='getDel(this)'><a href='javascript:void(0)'>删除</a></td>";
        $str+="</tr>";
        $("#addTr").append($str);
        sum ++;
        $("#sum").html(sum);
    });
});*/



function getAtr(i){
    $str='';
    $str+="<tr align='center'>";
    $str+="<td class='text-left'><select class='table-select'><option value=''>买方贷款审批完成</option></select></td>";
    $str+="<td class='text-left'><select class='table-select'><option value=''>资金方</option><option value=''>卖方</option></select></td>";

    $str+="<td><input class='table-input-one' type='text' placeholder='请输入金额'>元</td>";
    $str+="<td class='text-left' ><input class='table-input' type='text' placeholder='' /></td>";
    $str+="<td class='btn-height'><a href='javascript:void(0)'  onClick='getAtr(this)'>添加</a><a onClick='getDel(this)' class='grey' href='javascript:void(0)'>删除</a></td>";
    $str+="</tr>";
    $("#addTr").append($str);
    sum ++;
    $("#sum").html(sum);
}
function getDel(k){
    $(k).parents('tr').remove();
    sum --;
    $("#sum").html(sum);
}


</script>
	
    <script>
    
        $(document).ready(function () {	
        	getBankList();
        	
        	$('.input-daterange').datepicker({
              	format : 'yyyy-mm-dd',
          		weekStart : 1,
          		autoclose : true,
          		todayBtn : 'linked',
          		language : 'zh-CN'
            });
            
            $('.submit_btn').click(function(){
            	var eloanRelList = new Array();
            	var eloanCode =  $('#eloanCode').val();
            	var isRelFinish = $('#isRelFinish').val();
            	if(isRelFinish==""){
            		alert("请选择房款是否完成");
            		return;
            	}
            	var sumAmount = 0;
            	$(".loan_ul li").each(function(){
            		var releaseAmount = $(this).find("#releaseAmount").val();
            		sumAmount+=Number(releaseAmount);
            		var releaseTime = $(this).find("#releaseTime").val();
            		
            		var eloanRel = {
            			releaseAmount : releaseAmount,
            			releaseTime : releaseTime,
            			eloanCode : eloanCode
            		}
            		eloanRelList.push(eloanRel);
            	})
            	var eloanRelListVO = {
            		eloanRelList : eloanRelList,
            		isRelFinish : isRelFinish,
            		taskId : $('#taskId').val()
            	}
            	//console.log(eloanRelListVO);
            	var msg = validateIsFinishRelease(eloanCode,sumAmount);
            	if(($.trim(msg) === '请选择放款完成!' && $('#isRelFinish').val()==1) || $.trim(msg) === '操作成功') {
            	}else {
            		alert(msg);
            		return false;
            	}
            	var url = "${ctx}/eloan/saveEloanRelease";
    			$.ajax({
    				cache : true,
    				async : false,//false同步，true异步
    				type : "POST",
    				url : url,
    				dataType : "json",
    				contentType:"application/json",  
    				data : JSON.stringify(eloanRelListVO),
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
    					window.close();
    					window.opener.callback();
    				},
    				error : function(errors) {
    					alert("数据保存出错");
    				}
    			});
            })
        });
        
        function validateIsFinishRelease(eloanCode,sumAmount) {
        	var flag = true;
        	var msg = '';
			var url = "${ctx}/eloan/validateIsFinishRelease";
			$.ajax({
				cache : false,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : {eloanCode:eloanCode,sumAmount:sumAmount},
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '1900'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '1900'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					flag = data.content;
					msg = data.message;
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
			return msg;
        }
        
        /*获取银行列表*/
		function getBankList(){
			var finOrgCode = $("#finOrgCode").attr('value');
			var friend = $("#finOrgCode");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryFin",
			    method:"post",
			    dataType:"json",
			    data:{"pcode":finOrgCode},
		    	success:function(data){
		    		if(data.bankList != null){
		    			for(var i = 0;i<data.bankList.length;i++){
		    				if(finOrgCode == data.bankList[i].finOrgCode) {
		    					friend.html(data.bankList[i].finOrgName);
		    				} 
		    			}
		    		}
		    	}
			  });
		}
        // 添加日期查询条件
        var divIndex = 1;
        function add_money() {
        	var addMoneyReleaseHtml = template('addMoneyRelease' , {divIndex:divIndex});
        	$(".loan_ul").append(addMoneyReleaseHtml);
        	// 日期控件
        	$("input[name='releaseTime']").datepicker({
        		format : 'yyyy-mm-dd',
        		weekStart : 1,
        		autoclose : true,
        		todayBtn : 'linked',
        		language : 'zh-CN'
        	});
        	divIndex++;
        }
        
        //删除日期控件
        function removeDiv(index) {
        	$("#releaseDiv" + index).remove();
        }
    </script>
   </content>


</body>

</html>