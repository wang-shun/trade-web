<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
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
    
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/taxation/taxationCalculate.css" />
</head>
<body>
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">税费计算</div>
    </header>
    <section class="total-tax">
        <div class="aui-row  aui-row-padded aui-taxation">
            <div class="aui-col-xs-4">
                <p class="font15 medium-blue">契税</p>
                <p class="font12 light-blue tab-tax"><span class="deep-blue">个人</span>/<span>公司</span></p>
                <p class="money">12,345 元</p>
            </div>
            <div class="aui-col-xs-4 text-center">
                <p class="font15 medium-blue">增值税</p>
                <p class="font12 light-blue tab-tax"><span class="deep-blue">市区</span>/<span>郊区</span></p>
                <p class="money">+0 元</p>
            </div>
            <div class="aui-col-xs-4 text-right">
                <p class="font15 medium-blue">个人所得税</p>
                <p><i class="iconfont font14 popup-one">&#xe615;</i></p>
                <p class="money">+0 元</p>
            </div>
        </div>
        <div class="sum-tax">
            <p class="font17">税金总额<i class="iconfont font14 popup-two">&#xe615;</i></p>
            <p class="sum-number">12,345 <span>元</span></p>
        </div>
    </section>
    <section class="aui-content aui-margin-b-15">
        <ul class="aui-list aui-form-list">
            <li class="aui-list-header newgrey"></li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        建筑面积
                    </div>
                    <div class="aui-list-item-input unit-seat">
                        <input type="number" class="text-right blue" placeholder="请输入建筑面积">
                    </div>
                    <div class="aui-unit">
                        平
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        房价
                    </div>
                    <div class="aui-list-item-input unit-seat">
                        <input type="number" class="text-right blue" placeholder="请输入房价">
                    </div>
                    <div class="aui-unit">
                        万
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        原价
                    </div>
                    <div class="aui-list-item-input unit-seat">
                        <input type="number" class="text-right blue" placeholder="请输入原价">
                    </div>
                    <div class="aui-unit">
                        万
                    </div>
                </div>
            </li>
            <li class="aui-list-header newgrey"></li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        房屋性质<i class="iconfont font14 popup-three">&#xe615;</i>
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15">
                        <select class="rtl">
                            <option>普通住宅</option>
                            <option>办公楼</option>
                            <option>商铺</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        房产证年限
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15">
                        <select class="rtl">
                            <option>10年以上</option>
                            <option>20年以上</option>
                            <option>30年以上</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        买方是否首次购房
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15">
                        <select class="rtl">
                            <option>首次</option>
                            <option>二次</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label taxation-left">
                        卖方是否唯一住房
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow mr15">
                        <select class="rtl">
                            <option>唯一</option>
                            <option>不唯一</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-header newgrey"></li>
            <section class="other-box white">
                <div class="other-charges">
                    其它购房费用<i class="iconfont ml5">&#xe680;</i>
                </div>
                <ul id="otherList">
                    <li class="othernape">
                        <div class="aui-list-item-inner">
                            <div class="aui-list-item-label">
                                应税面积<i class="iconfont font14 popup-four">&#xe615;</i>
                            </div>
                            <div class="aui-list-item-input unit-seat">
                                <input type="number" class="text-right blue" placeholder="请输入建筑面积">
                            </div>
                            <div class="aui-unit">
                                平
                            </div>
                        </div>
                    </li>
                    <li class="othernape">
                        <div class="aui-list-item-inner  ">
                            <div class="aui-list-item-label">
                                房产税
                            </div>
                            <div class="aui-list-item-input mr15">
                                <p class="text-right"><span>9000</span>元</p>
                            </div>
                        </div>
                    </li>
                    <li class="othernape">
                        <div class="aui-list-item-inner  ">
                            <div class="aui-list-item-label">
                                交易手续费
                            </div>
                            <div class="aui-list-item-input mr15">
                                <p class="text-right">2.5元/平方米</p>
                            </div>
                        </div>
                    </li>
                    <li class="othernape">
                        <div class="aui-list-item-inner  ">
                            <div class="aui-list-item-label">
                                委托公证费
                            </div>
                            <div class="aui-list-item-input mr15">
                                <p class="text-right">200元/平方米</p>
                            </div>
                        </div>
                    </li>
                    <li class="othernape">
                        <div class="aui-list-item-inner  ">
                            <div class="aui-list-item-label">
                                权证登记费
                            </div>
                            <div class="aui-list-item-input mr15">
                                <p class="text-right">80元</p>
                            </div>
                        </div>
                    </li>
                </ul>
            </section>
        </ul>
        <p class="mt15 text-center">
            以上结果仅供参考，实际费用以审税结果为准
        </p>
    </section>
    <div id="Popup" class="layui-m-layer layui-m-layer0" >
        <div class="layui-m-layershade"></div>
        <div class="layui-m-layermain">
            <div class="layui-m-layersection">
                <div class="layui-m-layerchild  layui-m-anim-scale noradius">
                    <div class="date-title mt5">房屋性质说明</div>
                    <article class="aui-content popup-text">
                    </article>
                    <p class="know">我知道了</p>
                </div>
            </div>
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

<script type="text/javascript" src="${ctx}/static/momedia/js/calendar.js"></script>

<script>
$(function () {
    //获取jQuery 对象
    var $popup = $("#Popup");

    var $text1 = "<p>如提供房屋原始凭证，可另按（房价-买入价-增值税-契税-贷款利息-装修费用）*20%方式进行计算</p>"

    var $text2 = "<p>其中契税由买方缴纳，增值税及个人所得税由卖方缴纳，双方另有约定除外</p>";
    var $text3 = "<p>同时满足以下3个条件即为普通住宅，反之则为非普通住宅<br>"
                + "1、五层及以上的多高层住房，以及不足五层的老式公寓、新式里弄、旧式里弄等;<br>"
                + "2、单套建筑面积在140平方米以下；<br>"
                + "3、成交价格低于同级别土地上住房平均交易价格1.44倍以下。<br>"
                + "既：内环内<450万/套，内外环间<310万/套，外环外<230万/套</p>";

    var $text4 = "<p>应税面积是对本市居民家庭给予人均60平方米的免税住房面积扣除后的应缴纳房产税的面积。<br>"
                + "如购房者为上海户籍且首套房可免征。</p>";

    //遮罩弹窗层隐藏显示
    $popup.hide();
    $(".popup-one").on("click",function() {
        $popup.show();
        $(".date-title").html("个人所得税说明");
        $(".popup-text").html($text1);
    })
    $(".popup-two").on("click",function() {
        $popup.show();
        $(".date-title").html("税金总额说明");
        $(".popup-text").html($text2);
    });
    $(".popup-three").on("click",function() {
        $popup.show();
        $(".date-title").html("房屋性质说明");
        $(".popup-text").html($text3);
    })
    $(".popup-four").on("click",function() {
        $popup.show();
        $(".date-title").html("应税面积说明");
        $(".popup-text").html($text4);
    })
    $(".layui-m-layershade,.know").on("click",function() {
        $popup.hide();
        $(".popup-text").html("");
    });


    //点击其它费用列表显示隐藏切换
    var $otherList = $("#otherList");
    $otherList.hide();
    $(".other-charges").on("click",function() {
        if($otherList.is(":visible")){
                $otherList.hide();
                $(this).find("i").html("&#xe680;");                        //  隐藏$category
          }else{
                $otherList.show();                            //  显示$category
                $(this).find("i").html("&#xe67f;");
          }
    });

    //点击切换
    $(".tab-tax > span").on("click",function() {
        $(this).addClass('deep-blue').siblings().removeClass('deep-blue');
    });

});
</script>



</html>