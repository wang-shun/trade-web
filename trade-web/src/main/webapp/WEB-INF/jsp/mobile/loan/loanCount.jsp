<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/iconfont/iconfont.css" />
    <!--date css-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/plugins/mobiscroll/mobiscroll.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/plugins/mobiscroll/mobiscroll_date.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/autocompleter.css" />
</head>
<body>
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
        </a>
        <div class="aui-title">贷款计算</div>
    </header>

    <section class="total-tax">
        <div class="aui-bar aui-bar-btn aui-bar-btn-round aui-loan-btn" style="width:50%;">
            <div class="aui-bar-btn-item loan-active aui-loan-item" id="Principal" tapmode="" data-item-order="0">等额本息</div>
            <div class="aui-bar-btn-item tab-loan aui-loan-item" id="Corpus" tapmode="" data-item-order="1">等额本金</div>
        </div>
        <!-- 本息信息 -->
        <div class="principal">
            <div class="sum-loan clear mt15">
                <p class="font17">参考月供</p>
                <p class="sum-number"><span class="payments">0</span><em> 元</em></p>
            </div>
            <div class="aui-row  aui-row-padded aui-loan">
                <div class="aui-col-xs-6">
                    <p class="font15 medium-blue">还款总额</p>
                    <p class="loan-sum deep-blue"><em class="totalMoney">0</em> 万元</p>
                </div>
                <div class="aui-col-xs-6 loan-interest ">
                    <p class="font15 medium-blue">支付利息</p>
                    <p class="loan-sum deep-blue"><em class="interest">0</em> 万元</p>
                </div>
            </div>
        </div>
        <!-- 本金信息 -->
        <div class="corpus">
            <div class="sum-loan clear mt15 aui-row">
                <div class="aui-col-xs-6">
                    <p class="font17">首月月供</p>
                    <p class="sum-number"><span class="firstPayments">0</span><em> 元</em></p>
                </div>
                <div class="aui-col-xs-6 pl15">
                    <p class="font17">每月递减</p>
                    <p class="sum-number"><span class="monthlyDecline">0</span><em> 元</em></p>
                </div>
            </div>
            <div class="aui-row  aui-row-padded aui-loan">
                <div class="aui-col-xs-6">
                    <p class="font15 medium-blue">还款总额</p>
                    <p class="loan-sum deep-blue"><em class="totalMoney2">0</em> 万元</p>
                </div>
                <div class="aui-col-xs-6 loan-interest ">
                    <p class="font15 medium-blue">支付利息</p>
                    <p class="loan-sum deep-blue"><em class="interest2">0</em> 万元</p>
                </div>
            </div>
        </div>
    </section>
    <section class="aui-content aui-margin-b-15">
        <ul class="aui-list aui-form-list">
            <li class="aui-list-item mt10">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        公积金贷款
                    </div>
                    <div class="aui-list-item-input unit-seat">
                        <input type="number" id="accumulationMoney" value="" class="text-right blue" placeholder="请输入金额" onfocus="this.placeholder=''" onblur="this.placeholder='请输入金额'">
                    </div>
                    <div class="aui-unit">
                        万
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        贷款年限
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15">
                        <select class="rtl" id="accumulationYears" onchange="computationOf()">
                            <option value="1">1年(12期)</option>
                            <option value="2">2年(24期)</option>
                            <option value="3">3年(36期)</option>
                            <option value="4">4年(48期)</option>
                            <option value="5">5年(60期)</option>
                            <option value="6">6年(72期)</option>
                            <option value="7">7年(84期)</option>
                            <option value="8">8年(96期)</option>
                            <option value="9">9年(108期)</option>
                            <option value="10">10年(120期)</option>
                            <option value="11">11年(132期)</option>
                            <option value="12">12年(144期)</option>
                            <option value="13">13年(156期)</option>
                            <option value="14">14年(168期)</option>
                            <option value="15">15年(180期)</option>
                            <option value="16">16年(192期)</option>
                            <option value="17">17年(204期)</option>
                            <option value="18">18年(216期)</option>
                            <option value="19">19年(228期)</option>
                            <option value="20" >20年(240期)</option>
                            <option value="21">21年(252期)</option>
                            <option value="22">22年(264期)</option>
                            <option value="23">23年(276期)</option>
                            <option value="24">24年(288期)</option>
                            <option value="25">25年(300期)</option>
                            <option value="26">26年(312期)</option>
                            <option value="27">27年(324期)</option>
                            <option value="28">28年(336期)</option>
                            <option value="29">29年(348期)</option>
                            <option value="30" selected="selected">30年(360期)</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        公积金利率
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15" >
                        <div class="text-right loan-value">
							<select class="rtl" id="accumulationMoneyRate" onchange="computationOf()">
                            <option value="3.25">基准利率</option>
                            <option value="3.575">1.1倍利率</option>
                           </select>
                        </div>
                    </div>
                </div>
            </li>
            <li class="aui-list-item mt10">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        商业贷款
                    </div>
                    <div class="aui-list-item-input unit-seat">
                        <input type="number" id="loanMoney" value="" class="text-right blue" placeholder="请输入金额" onfocus="this.placeholder=''" onblur="this.placeholder='请输入金额'">
                    </div>
                    <div class="aui-unit">
                        万
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        贷款年限
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15" >
                        <select class="rtl" id="loanYears" onchange="computationOf()" >
                            <option value="1">1年(12期)</option>
                            <option value="2">2年(24期)</option>
                            <option value="3">3年(36期)</option>
                            <option value="4">4年(48期)</option>
                            <option value="5">5年(60期)</option>
                            <option value="6">6年(72期)</option>
                            <option value="7">7年(84期)</option>
                            <option value="8">8年(96期)</option>
                            <option value="9">9年(108期)</option>
                            <option value="10">10年(120期)</option>
                            <option value="11">11年(132期)</option>
                            <option value="12">12年(144期)</option>
                            <option value="13">13年(156期)</option>
                            <option value="14">14年(168期)</option>
                            <option value="15">15年(180期)</option>
                            <option value="16">16年(192期)</option>
                            <option value="17">17年(204期)</option>
                            <option value="18">18年(216期)</option>
                            <option value="19">19年(228期)</option>
                            <option value="20" >20年(240期)</option>
                            <option value="21">21年(252期)</option>
                            <option value="22">22年(264期)</option>
                            <option value="23">23年(276期)</option>
                            <option value="24">24年(288期)</option>
                            <option value="25">25年(300期)</option>
                            <option value="26">26年(312期)</option>
                            <option value="27">27年(324期)</option>
                            <option value="28">28年(336期)</option>
                            <option value="29">29年(348期)</option>
                            <option value="30" selected="selected">30年(360期)</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-item" onClick="selectMoneyRate('loanMoneyRate')">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        商贷利率
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15">
                        <div class="text-right loan-value">
                            <em id="loanMoneyRate">4.90</em>%
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <p class="mt15 text-center">
            以上结果仅供参考
        </p>
    </section>

    <!-- 自定义利率 -->
    <div class="layui-m-layer layui-m-layer0 white" >
        <div class="layui-loanshade">
        <header class="aui-bar aui-bar-nav">
            <a class="aui-pull-left aui-btn close-loan-popup">
                <span style="font-size:0.7rem; margin-top:15px;color:#81d8d0">后退</span>
            </a>
            <div class="aui-title">商贷利率</div>
			<a class="aui-pull-right aui-btn" id="ok">
            <span style="font-size:0.7rem ;margin-top:15px;color:#81d8d0">确定</span>
            </a>
        </header>
        <section class="aui-content aui-margin-b-15">
        <ul class="aui-list aui-form-list">
            <li class="aui-list-item mt10">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                       自定义利率
                    </div>
                    <div class="aui-list-item-input unit-seat">
                        <input type="number" class="text-right blue MoneyRate" value="4.90">
                    </div>
                    <div class="loan-unit">
                        %
                    </div>
                </div>
            </li>
            <li class="aui-list-header newgrey ml10">常用利率</li>
            <li class="aui-item">
                <div class="aui-content aui-margin-b-15">
                    <ul class="aui-list aui-loan-list white" id="manua-list">

                        <li class="manua manuali">
                             7折
                        </li>
                        <li class="manua manuali">
                             7.5折
                        </li>
                        <li class="manua manuali">
                            8折
                        </li>
                        <li class="manua manuali">
                            8.5折
                        </li>
                        <li class="manua manuali">
                            9折
                        </li>
                        <li class="manua manuali">
                            9.5折
                        </li>
                        <li class="manua manuali loan-checked nomal">
                            基本利率（4.90%）
                        </li>
                        <li class="manua manuali">
                            1.1倍
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </section>





        </div>

    </div>
</body>

<script type="text/javascript" src="${ctx}/static/momedia/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/api.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/layer.js"></script>

<!--date-->
<script type="text/javascript" src="${ctx}/static/momedia/js/plugins/mobiscroll/mobiscroll_date.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/plugins/mobiscroll/mobiscroll.js"></script>

<script type="text/javascript" src="${ctx}/static/momedia/js/jquery.autocompleter.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/calculation/count-loan.js"></script>

<script>
$(function () {
    //等额本息、等额本金点击切换
    var $principal = $("#Principal");
    var $corpus = $("#Corpus");

    $principal.on("click",function() {
       $(this).addClass('loan-active');
       $corpus.removeClass('loan-active');
       $(".principal").show();
       $(".corpus").hide();
	   computationOf();
    });

    $corpus.on("click",function() {
       $(this).addClass('loan-active');
       $principal.removeClass('loan-active');
       $(".principal").hide();
       $(".corpus").show();
	   computationOf();
    });
    var $layer = $(".layui-m-layer");
    $layer.hide();
    $(".close-loan-popup").on("click",function() {
        $layer.hide();
    })
   computationOf();
   $("#accumulationMoney").bind("input propertychange", function() {
     computationOf();
  });
     $("#loanMoney").bind("input propertychange", function() {
	 computationOf();
   });
});
//计算利率
	function computationRate(zhekou,id){
	 var baifenbi=parseFloat(zhekou);
	 var newComputationRate=4.90;

	 if(isNaN(baifenbi)){
        return newComputationRate;
      }
	  else if(baifenbi==1.1){
	   newComputationRate=newComputationRate*baifenbi;
	  }else{
	  newComputationRate=newComputationRate*(baifenbi/10);
	  }
	  return newComputationRate;
	}
	 var manualist = $("#manua-list li");
	function removeli(){
	$("#manua-list li").each(function(){
	if($(this).hasClass("loan-checked")){
	$(this).removeClass("loan-checked");
	}
   });

	}
    	//选择利率
	function selectMoneyRate(id){
	 removeli();
	$(this).toggleClass('loan-checked').siblings().removeClass('loan-checked');
	 var MoneyRate=$("#"+id).text();
	 $(".MoneyRate").val(MoneyRate);
	 $(".layui-m-layer").show();
	 $(".MoneyRate").unbind("keyup").keyup(function(event){
	 var e=(event) ? event : ((window.event) ? window.event : "");
	 var key = e.keyCode?e.keyCode:e.which;
	 if(e && key==13){
	    var MoneyRate2=$(".MoneyRate").val();
		$("#"+id).text(MoneyRate2);
        $(".layui-m-layer").hide();
	    computationOf();
      }
	 });
	 $("#ok").unbind("click").click(function() {
	   var MoneyRate2=$(".MoneyRate").val();
		$("#"+id).text(MoneyRate2);
        $(".layui-m-layer").hide();
		computationOf();
    });
	$(".manuali").unbind("click").click(function() {
	$(this).toggleClass('loan-checked').siblings().removeClass('loan-checked');
		var MoneyRate=$(".loan-checked").text();
		var newComputationRate= computationRate(MoneyRate,id);
		$("#"+id).text(newComputationRate.toFixed(2));
		$(".layui-m-layer").hide();
		computationOf();
    });
	computationOf();
	}

//计算
function computationOf(){
	//公积金
	var A=parseFloat($("#accumulationMoney").val(),6);
	if(isNaN(A)){
	A=0;
	}
	var B=parseFloat(($("#accumulationMoneyRate").val().replace(/(^\s*)|(\s*$)/g, "")/100),3);
	var C=parseInt($("#accumulationYears").val());
	//商代
	var D=parseFloat($("#loanMoney").val(),6);
	if(isNaN(D)){
	D=0;
	}
	var E=parseFloat(($("#loanMoneyRate").text().replace(/(^\s*)|(\s*$)/g, "")/100),3);
	var F=parseInt($("#loanYears").val());
	//公积金中间参数
	var G=parseFloat((B/12),3);
	var H=C*12;
	//商代中间参数
	var I=parseFloat((E/12),3);
	var J=F*12;

if (!$(".principal").is(":hidden")) {
	//每月月供
	var payments = A*10000*G*Math.pow((1+G),H)/(Math.pow((1+G),H)-1)+D*10000*I*Math.pow((1+I),J)/(Math.pow((1+I),J)-1)
	//总利息
	var Interest=parseFloat((A*H*G*Math.pow((1+G),H)/(Math.pow((1+G),H)-1)-A)+(D*J*I*Math.pow((1+I),J)/(Math.pow((1+I),J)-1)-D),3)
	//totalMoney
	var totalMoney=(A+D+Interest);
	if(isNaN(payments)){
	payments=0;
	}
	if(isNaN(Interest)){
	Interest=0;
	}
	if(isNaN(totalMoney)){
	totalMoney=0;
	}
	$(".payments").text(payments.toFixed(0));
	$(".totalMoney").text(totalMoney.toFixed(2));
	$(".interest").text(Interest.toFixed(2));
	}else{
	//首月月供
	var firstPayments=A*10000/H+A*10000*G+D*10000/J+D*10000*I;
	//每月递减
	var monthlyDecline=A*10000*G/H+D*10000*I/J;
	//总利息
	var Interest=(H+1)*A*G/2+(J+1)*D*I/2
	//totalMoney
	var totalMoney=(A+D+Interest);
	if(isNaN(firstPayments)){
	firstPayments=0;
	}
	if(isNaN(monthlyDecline)){
	monthlyDecline=0;
	}
	if(isNaN(Interest)){
	Interest=0;
	}
	if(isNaN(totalMoney)){
	totalMoney=0;
	}
	$(".firstPayments").text(firstPayments.toFixed(0));
	$(".monthlyDecline").text(monthlyDecline.toFixed(0));
	$(".totalMoney2").text(totalMoney.toFixed(2));
	$(".interest2").text(Interest.toFixed(2));
  }

}
</script>



</html>