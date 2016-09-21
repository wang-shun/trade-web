<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/iconfont/iconfont.css" />

</head>
<body>
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">我的预约</div>
    </header>
    <article class="aui-content">
        <ul class="aui-list aui-media-list white">
            <li class="aui-list-header header_grey_bg">
                <i class="iconfont blue mr5">&#xe605;</i>13:00-15:00 <span class="color80">2016-09-16</span>
            </li>
            <li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title">浦东贵宾中心<span class="ml10">预约号：<em class="yellow">2016090</em></span></div>
                        <div class="aui-list-item-text font12">
                            房屋地址：上海市浦东而去祝桥片区航亭环路99弄71号0302室
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace">
                        <div class="aui-btn">取消预约</div>
                    </div>
                    <span class="shuxing"></span>
                </div>
            </li>
            <li class="aui-list-header header_grey_bg">
                <i class="iconfont blue mr5">&#xe605;</i>10:00-12:00 <span class="color80">2016-09-09</span>
            </li>
            <li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title">浦东贵宾中心<span class="ml10">预约号：<em class="yellow">2016090</em></span></div>
                        <div class="aui-list-item-text font12">
                            房屋地址：上海市浦东而去祝桥片区航亭环路99弄71号0302室
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace">
                        <div class="aui-btn">取消预约</div>
                    </div>
                </div>
            </li>
            <li class="aui-list-header header_grey_bg">
                <i class="iconfont blue mr5">&#xe605;</i>10:00-12:00 <span class="color80">2016-09-08</span>
            </li>
            <li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title">浦东贵宾中心<span class="ml10">预约号：<em class="yellow">2016090</em></span></div>
                        <div class="aui-list-item-text font12">
                            房屋地址：上海市浦东而去祝桥片区航亭环路99弄71号0302室
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace">
                        <div class="aui-btn trans_bg red">已取消</div>
                    </div>
                </div>
            </li>
            <li class="aui-list-header header_grey_bg">
                <i class="iconfont blue mr5">&#xe605;</i>10:00-12:00 <span class="color80">2016-09-09</span>
            </li>
            <li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title">浦东贵宾中心<span class="ml10">预约号：<em class="yellow">2016090</em></span></div>
                        <div class="aui-list-item-text font12">
                            房屋地址：上海市浦东而去祝桥片区航亭环路99弄71号0302室
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace">
                        <div class="aui-btn trans_bg">已过期</div>
                    </div>

                </div>
            </li>
            <li class="aui-list-header header_grey_bg">
                <i class="iconfont blue mr5">&#xe605;</i>10:00-12:00 <span class="color80">2016-09-09</span>
            </li>
            <li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title">浦东贵宾中心<span class="ml10">预约号：<em class="yellow">2016090</em></span></div>
                        <div class="aui-list-item-text font12">
                            房屋地址：上海市浦东而去祝桥片区航亭环路99弄71号0302室
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace">
                        <div class="aui-btn trans_bg">已过期</div>
                    </div>

                </div>
            </li>

        </ul>
    </article>




</body>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/api.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/layer.js"></script>
<script type="text/javascript">
    function myOpen(){
        layer.open({
            type: 0,
            title: '',
            content: '<div class="dialog-user"><i class="iconfont iconfont70 mt20 cyan">&#xe606;</i><h2 class="dialog-head mt20 font18">恭喜你预约成功</h2><div class="dialog-info"><p class="font14 mt20">房间号<span class="yellow font18">A-234</span>（最大容纳6人）</p><p class="mt5 font12">时间：2016-08-12&nbsp;&nbsp;10:00-12:00</p></div><div class="btn-box mt20"><a href="index.html"><div class="aui-btn aui-btn-primary aui-margin-r-10">我的预约</div></a><div class="aui-btn aui-btn-grey aui-margin-l-10">继续预约</div></div>'
        });
    };
</script>
</html>