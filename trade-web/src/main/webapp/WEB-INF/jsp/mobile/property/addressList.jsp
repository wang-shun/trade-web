<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/common/aui.2.0.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/common/style.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/iconfont/iconfont.css' />" />
    <!--date css-->
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/plugins/mobiscroll/mobiscroll.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/plugins/mobiscroll/mobiscroll_date.css' />" />
    
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/addresslist.css' />" />
</head>
<body>
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
        </a>
        <div class="aui-title">通讯录</div>
    </header>
    <div class="aui-searchbar aui-clearfix  search-grey-bg" id="search" style="position:relative;">
        <div class="aui-searchbar-input aui-border-radius" tapmode>
            <i class="aui-iconfont aui-icon-search"></i>
            <form action="javascript:search();">
                <input type="search" placeholder="查询交易顾问" id="filter">
            </form>
        </div>
        <div class="aui-searchbar-cancel black" tapmod>查询</div>
    </div>

    <div class="aui-content aui-margin-b-15">
        <div class="list_group" data-index="show">
            <ul class="list">
                <li class="jcorgFilterTextParent" id="area1">

                    <p class="title">市区贵宾服务部</p>

                    <a href="tel:13918843797">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陶晨</div>
                            <div class="aui-col-xs-6 job">副总监</div>
                        </div>
                    </a>
                    <a href="tel:13816338907">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">孙一菲</div>
                            <div class="aui-col-xs-6 job">资深内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:13816559074">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">丁曙颖</div>
                            <div class="aui-col-xs-6 job">首席交易专员</div>
                        </div>
                    </a>
                    <a href="tel:13501621959">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陈琦</div>
                            <div class="aui-col-xs-6 job">首席交易专员</div>
                        </div>
                    </a>
                    <a href="tel:13671649985">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">翁佳祥</div>
                            <div class="aui-col-xs-6 job">资深交易服务专员</div>
                        </div>
                    </a>
                    <a href="tel:15800544555">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘尧晨</div>
                            <div class="aui-col-xs-6 job">首席交易专员</div>
                        </div>
                    </a>
                    <a href="tel:13801852299">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">胡元</div>
                            <div class="aui-col-xs-6 job">首席交易专员</div>
                        </div>
                    </a>
                    <a href="tel:13512138339">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">张晓俊</div>
                            <div class="aui-col-xs-6 job">资深交易服务专员</div>
                        </div>
                    </a>
                    <a href="tel:13671601060">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">顾凤云</div>
                            <div class="aui-col-xs-6 job">首席交易专员</div>
                        </div>
                    </a>
                    <a href="tel:13585842368">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">赵慧</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18016280702">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">靳礼军</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18521036828">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">姚沁慧</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13816125688">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">林莉</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13817075270">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">吴芳</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13262688386">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">董艳华</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:13901834833">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陈洁茹</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18601668837">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">郑捷</div>
                            <div class="aui-col-xs-6 job">资深贷款管理专员</div>
                        </div>
                    </a>
                </li>
                <li class="jcorgFilterTextParent" id="area2">
                    <p class="title">闵行贵宾服务部</p>

                    <a href="tel:18964863223">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">徐盈盈</div>
                            <div class="aui-col-xs-6 job">副总监</div>
                        </div>
                    </a>
                    <a href="tel:18721827966">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">包晓娟</div>
                            <div class="aui-col-xs-6 job">资深内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:13621731869">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">彭美虹</div>
                            <div class="aui-col-xs-6 job">高级总务专员</div>
                        </div>
                    </a>
                    <a href="tel:13524525087">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">倪华丽</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:13371996090">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">何宏</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13321989502">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陈奇麟</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13761414984">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">周沛文</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18116105767">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">蒋建国</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13817021427">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">何燕</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:15000617615">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">辛颖</div>
                            <div class="aui-col-xs-6 job">首席交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13818111347">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">周林祥</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18917990196">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">乔志刚</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13524241013">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">张振红</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:18016325138">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">杨文晔</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13501745387">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">佘高骏</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:17701727778">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">周文清</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:17321071812">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">岳彪</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                </li>
                <li class="jcorgFilterTextParent" id="area3">
                    <p class="title">松江贵宾服务部</p>

                    <a href="tel:18017150850">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">李晓靖</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:13621920086">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陈洁</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18017019518">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">丁宇</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13681664717">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">潘浩</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13761138229">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">杨小叶</div>
                            <div class="aui-col-xs-6 job">高级内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:17749709617">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">彭可云</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18021018552">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王军杰</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13671965070">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘伟珍</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15601622730">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">徐晓栋</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                </li>
                <li class="jcorgFilterTextParent" id="area4">
                    <p class="title">普陀贵宾服务部</p>

                    <a href="tel:13901901731">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘华</div>
                            <div class="aui-col-xs-6 job">总监</div>
                        </div>
                    </a>
                    <a href="tel:13022183722">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">蔡宏</div>
                            <div class="aui-col-xs-6 job">高级交易服务专员</div>
                        </div>
                    </a>
                    <a href="tel:13621624562">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">宋莉</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13061616202">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">郭丽君</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13916900624">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">凌文俊</div>
                            <div class="aui-col-xs-6 job">首席交易服务专员</div>
                        </div>
                    </a>
                    <a href="tel:18621779926">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">钱丽莉</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18621635605">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">杨秀萍</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15800738790">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">徐姣娇</div>
                            <div class="aui-col-xs-6 job">资深贷款管理专员</div>
                        </div>
                    </a>
                    <a href="tel:18918046467">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">戴倩</div>
                            <div class="aui-col-xs-6 job">首席交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15900508400">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">黄轶列</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13761610562">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">朱文宸</div>
                            <div class="aui-col-xs-6 job">首席贷款管理专员</div>
                        </div>
                    </a>
                    <a href="tel:13636607127">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">华军</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:15000126854">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">尹鸣敏</div>
                            <div class="aui-col-xs-6 job">资深内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:18621837016">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">唐歆梅</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15601683241">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">姚晓春</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent" id="area5">
                    <p class="title">嘉定贵宾服务部</p>

                    <a href="tel:13901952376">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">傅峻青</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:13052098602">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">任正英</div>
                            <div class="aui-col-xs-6 job">内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:18516130787">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">葛瑶</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13501651435">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">章小丽</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13621773218">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">姚尧</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18121426568">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王飞飞</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18217419670">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">林美容</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13816838639">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">夏云</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13402175342">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘悦纳</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent" id="area6">
                    <p class="title">宝山贵宾服务部</p>

                    <a href="tel:13816872593">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">袁赛</div>
                            <div class="aui-col-xs-6 job">副总监</div>
                        </div>
                    </a>
                    <a href="tel:13818202503">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">沈怡薇</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:15000439375">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">金全</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13916734000">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">施立</div>
                            <div class="aui-col-xs-6 job">首席交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13816939447">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">赵海红</div>
                            <div class="aui-col-xs-6 job">首席交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13482371637">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">江燕华</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13916813463">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">魏丽萍</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15001778148">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">唐俊卿</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13816980954">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">秦伟</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13661829861">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">赵晓骏</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13917729022">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">姚年萍</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:15821731031">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王美静</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13816953281">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王智款</div>
                            <div class="aui-col-xs-6 job">首席交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13564037287">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">俞立</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15000932711">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">李菊兰</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:18801922710">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">朱胜兰</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13916909228">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">侯善礼</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13917435834">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">施燕青</div>
                            <div class="aui-col-xs-6 job">内勤助理</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent" id="area7">
                    <p class="title">虹杨贵宾服务部</p>

                    <a href="tel:13817789952">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">许庆祺</div>
                            <div class="aui-col-xs-6 job">总监</div>
                        </div>
                    </a>
                    <a href="tel:15921996244">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">贝莉</div>
                            <div class="aui-col-xs-6 job">高级内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:13817043888">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">浦雅萍</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:13818058858">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">邱华</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:15026888437">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王鼎元</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13916540926">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">顾新峰</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18616313163">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">黄频</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13761047387">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">周庆峰</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13761223024">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">苏爱娣</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13701611165">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">徐彦青</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18939914096">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">蔡炜俊</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13761839055">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">金姣姣</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18017224347">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">史大林</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13774222304">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘昶胜</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13524912112">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">朱杰</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13918165593">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">姜思宇</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13818421789">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">戴哲</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:13764475519">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">李良</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13611884843">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陈辰</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13601739246">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">时伟冰</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15800346281">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王武臣</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13818237428">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">蔡静</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15821095790">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">张晟洋</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13917398207">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">唐优丽</div>
                            <div class="aui-col-xs-6 job">高级总务专员</div>
                        </div>
                    </a>

                </li>

                <li class="jcorgFilterTextParent" id="area8">
                    <p class="title">浦东贵宾服务部</p>
                    <a href="tel:13817705188">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">胡江海</div>
                            <div class="aui-col-xs-6 job">高级总监</div>
                        </div>
                    </a>

                </li>
                <li class="jcorgFilterTextParent">
                    <p class="subtitle">浦东A组贵宾服务部</p>

                    <a href="tel:13472782508">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘烨</div>
                            <div class="aui-col-xs-6 job">高级区域经理</div>
                        </div>
                    </a>
                    <a href="tel:13472821478">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">张锦雯</div>
                            <div class="aui-col-xs-6 job">高级内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:13311698952">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">黄晓伟</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13601647917">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">赵静</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13917475149">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王艳</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15900892519">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王锐</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13621684952">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘侃良</div>
                            <div class="aui-col-xs-6 job">首席交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13482420382">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">蒋振伟</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13764223723">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">左晟</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>

                </li>

                <li class="jcorgFilterTextParent">
                    <p class="subtitle">浦东B组贵宾服务部</p>

                    <a href="tel:18116220716">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陈宏辉</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:13764097949">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">张静媛</div>
                            <div class="aui-col-xs-6 job">资深内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:15901615675">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">李梦娜</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18621953863">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">尹崇光</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15921539394">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">蔡再权</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13524212832">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">侯广</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13166178513">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">武俊平</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15026669729">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">曹苗苗</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent">
                    <p class="subtitle">浦东C组贵宾服务部</p>

                    <a href="tel:15801977980">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">葛骑兵</div>
                            <div class="aui-col-xs-6 job">区域经理</div>
                        </div>
                    </a>
                    <a href="tel:13524139980">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">何超</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15001834907">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">胡伟伟</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13764405065">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">徐春华</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15021101996">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">宋子文</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15921484067">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">朱丽</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:17717415573">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">唐佳颖</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13661941773">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">葛清清</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15502114156">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">李圆</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18019188699">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">吴卫杰</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13917230854">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">赵志勇</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15720823239">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">何花</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15921054336">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陆天虹</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18217607540">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘红玉</div>
                            <div class="aui-col-xs-6 job">高级内勤助理</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent">
                    <p class="subtitle">浦东D组贵宾服务部</p>

                    <a href="tel:13472782508">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘烨</div>
                            <div class="aui-col-xs-6 job">高级区域经理</div>
                        </div>
                    </a>
                    <a href="tel:15202141198">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">周群</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:18516639056">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">马元元</div>
                            <div class="aui-col-xs-6 job">资深内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:13816976148">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">尤振华</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13764480805">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">屠婴</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13641996574">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">顾振华</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13621789816">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">殷洁</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13817258004">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">曹玉梅</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15221731477">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">张鑫</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13482693579">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">秦晓杰</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent">
                    <p class="subtitle">浦东E组贵宾服务部</p>

                    <a href="tel:13585824208">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">马惠财</div>
                            <div class="aui-col-xs-6 job">高级区域经理</div>
                        </div>
                    </a>
                    <a href="tel:13816805217">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">李玉东</div>
                            <div class="aui-col-xs-6 job">高级交易管理主任</div>
                        </div>
                    </a>
                    <a href="tel:13818133553">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">徐瑾</div>
                            <div class="aui-col-xs-6 job">资深内勤助理</div>
                        </div>
                    </a>
                    <a href="tel:13818000142">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">施云</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13636385647">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">倪凯乐</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13501743473">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">林成思</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15026869128">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">王俊秀</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15221042831">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">夏玲波</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18621664570">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">廖容</div>
                            <div class="aui-col-xs-6 job">高级交易顾问</div>
                        </div>
                    </a>
                </li>

                <li class="jcorgFilterTextParent">
                    <p class="subtitle">浦东交易组</p>

                    <a href="tel:13817879294">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">董剑文</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18917193215">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">江艳娜</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15900519947">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">岑曙汛</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15003323252">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">杨凤南</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18621707189">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">刘招芹</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13381615907">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">陆俊华</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13681886721">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">郑曙华</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:18916938057">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">邓琳</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:13621719193">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">吕嘉庆</div>
                            <div class="aui-col-xs-6 job">资深交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:17717041850">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">孙劼欽</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15921402327">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">汤小辉</div>
                            <div class="aui-col-xs-6 job">交易顾问</div>
                        </div>
                    </a>
                    <a href="tel:15921919067">
                        <div class="item aui-row">
                            <div class="aui-col-xs-6 jcorgFilterTextChild">薛俊雯</div>
                            <div class="aui-col-xs-6 job">高级总务专员</div>
                        </div>
                    </a>
                </li>

            </ul>
        </div>
    </div>

    <!-- <div class="initials">
        <ul>
            <li>市区</li>
            <li>闵行</li>
            <li>松江</li>
            <li>普陀</li>
            <li>嘉定</li>
            <li>宝山</li>
            <li>虹杨</li>
            <li>浦东</li>
            <li><img src="img/068.png"></li>
        </ul>
    </div> -->
    <div class="index">
        <a href="#area1">市区</a>
        <a href="#area2">闵行</a>
        <a href="#area3">松江</a>
        <a href="#area4">普陀</a>
        <a href="#area5">嘉定</a>
        <a href="#area6">宝山</a>
        <a href="#area7">虹杨</a>
        <a href="#area8">浦东</a>
    </div>



</body>

<script type="text/javascript" src="<c:url value='/static/momedia/js/jquery-2.1.1.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/momedia/js/api.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/momedia/js/aui-dialog.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/momedia/js/aui-main.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/momedia/js/layer.js' />"></script>

<!--date-->
<script type="text/javascript" src="<c:url value='/static/momedia/js/plugins/mobiscroll/mobiscroll_date.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/momedia/js/plugins/mobiscroll/mobiscroll.js' />"></script>

<!-- 过滤插件 -->
<script type="text/javascript" src="<c:url value='/static/momedia/js/jcfilter.js' />"></script>
<script>
        $(function () {
            $("#filter").jcOnPageFilter({
                         animateHideNShow: true,
                         focusOnLoad:false,
                         highlightColor:'#FFFF00',
                 textColorForHighlights:'#000000',
                         caseSensitive:false,
                         hideNegatives:true,
                         parentLookupClass:'jcorgFilterTextParent',
                         childBlockClass:'jcorgFilterTextChild',
                         indexhideClass : 'index'
              });
        });



        $(function() {
            $("a[href^='#area']").click(function() {
                console.log($(this));
                var id = $(this).attr("href");
                $("html,body").animate({scrollTop:($(id).offset().top + 1)},50);
            });
        });
        $(window).scroll(function() {
            $("#filter").blur();
            // 当滚动到最底部以上100像素时， 加载新内容
            if ($(this).scrollTop() >= 25) {
                 $(".index").css("position","fixed");
                    $(".index").css("top","20px");
            } else {
                $(".index").css("position","fixed");
                    $(".index").css("top","130px");
            }
        });
</script>



</html>