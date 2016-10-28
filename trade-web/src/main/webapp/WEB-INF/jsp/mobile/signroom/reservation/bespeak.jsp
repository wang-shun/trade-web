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
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/validate.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/autocompleter.css" />
</head>
<body>
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="">取消</span>
        </a>
        <div class="aui-title">签约室预约</div>
    </header>
    <section class="aui-content aui-margin-b-15">
        <ul class="aui-list aui-form-list">

            <li class="aui-list-header newgrey">您还剩余<span class="num">3</span>次预约机会</li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        <i class="iconfont blue mr5">&#xe60d;</i>预约中心
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow">
                        <select>
                            <option>浦东贵宾中心</option>
                            <option>杨浦贵宾中心</option>
                            <option>长宁贵宾中心</option>
                        </select>
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        <i class="iconfont blue mr5">&#xe610;</i>到场人数
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow">
                        <input type="number" placeholder="请输入到场人数">
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        <i class="iconfont blue mr5">&#xe60e;</i>预约时段
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow">
                        <input type="text" id="dateseLect" placeholder="2016-11-31 12:00－13:30">
                    </div>
                </div>
            </li>
            <li class="aui-list-header newgrey">办理事项</li>
            <li class="aui-item">
                <div class="aui-list-item-inner aui-input-list">
                        <div class="aui-col-xs-3 aui-input-element choiceoption">
                            签合同
                        </div>
                        <div class="aui-col-xs-3 aui-input-element choiceoption">
                            办贷款
                        </div>
                        <div class="aui-col-xs-3 aui-input-element choiceoption">
                            e+贷款
                        </div>
                        <div class="aui-col-xs-3 aui-input-element choiceoption" id="choices4">
                            开列会
                        </div>
                </div>
            </li>
            <li class="aui-list-item item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        <i class="iconfont blue mr5">&#xe608;</i>交易地址
                    </div>
                    <div class="aui-list-item-input">
                        <input type="text" placeholder="">
                    </div>
                </div>
            </li>
            <li class="aui-list-item item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        <i class="iconfont blue mr5">&#xe60c;</i>贷款专员
                    </div>
                    <div class="aui-list-item-input">
                        <input type="text" placeholder="请输入名称">
                    </div>
                </div>
            </li>
            <li class="aui-list-header newgrey">备注信息</li>
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-input">
                        <textarea placeholder="请输入内容"></textarea>
                    </div>
                </div>
            </li>

        </ul>
        <div class="plr15 mt28"><input type="submit" class="aui-btn aui-btn-info aui-btn-block aui-btn-user" value="领取预约号"></div>
    </section>
    <div id="roomList" class="layui-m-layer layui-m-layer0" >
        <div class="layui-m-layershade"></div>
        <div class="layui-m-layermain">
            <div class="layui-m-layersection">
                <div class="layui-m-layerchild  layui-m-anim-scale noradius">
                    <div class="date-title">2016-10-02</div>
                    <section class="aui-grid">
                        <table class="table table-date">
                          <thead>
                            <tr>
                              <th>周一</th>
                              <th>周二</th>
                              <th>周三</th>
                              <th>周四</th>
                              <th>周五</th>
                              <th>周六</th>
                              <th>周末</th>
                            </tr>
                          </thead>
                          <tbody id="dayList">
                            <tr>
                              <td date="2016-10-24">24</td>
                              <td date="2016-10-25">25</td>
                              <td date="2016-10-26">26</td>
                              <td date="2016-10-27">27</td>
                              <td date="2016-10-28" class="warn">28</td>
                              <td date="2016-10-29" class="usable-date">29</td>
                              <td date="2016-10-30" class="usable-date">30</td>
                            </tr>
                            <tr>
                              <td date="2016-10-31" class="usable-date">31</td>
                              <td date="2016-11-01" class="usable-date">1</td>
                              <td date="2016-11-02" class="usable-date">2</td>
                              <td date="2016-11-03">3</td>
                              <td date="2016-11-04">4</td>
                              <td date="2016-11-05">5</td>
                              <td date="2016-11-06">6</td>
                            </tr>
                          </tbody>
                        </table>
                    </section>
                    <article class="aui-content">
                        <ul class="aui-list aui-date-list">
                            <li class="aui-list-item aui-padded-lr20">
                                <div class="aui-info-item">
                                    <i class="iconfont blue mr5">&#xe605;</i>
                                    <span class="time">19:30-21:00</span>
                                </div>
                                <div class="aui-info-item aui-text-grey">
                                   会议室剩余间数<span class="aui-text-red mlr5">0</span>间
                                </div>
                            </li>
                            <li class="aui-list-item aui-padded-lr20">
                                <div class="aui-info-item">
                                    <i class="iconfont blue mr5">&#xe605;</i>
                                    <span class="time">19:30-21:00</span>
                                </div>
                                <div class="aui-info-item aui-text-grey">
                                   会议室剩余间数<span class="aui-text-blue mlr5">3</span>间
                                </div>
                            </li>
                            <li class="aui-list-item aui-padded-lr20">
                                <div class="aui-info-item">
                                    <i class="iconfont blue mr5">&#xe605;</i>
                                    <span class="time">19:30-21:00</span>
                                </div>
                                <div class="aui-info-item aui-text-grey">
                                   会议室剩余间数<span class="aui-text-blue mlr5">3</span>间
                                </div>
                            </li>
                            <li class="aui-list-item aui-padded-lr20">
                                <div class="aui-info-item">
                                    <i class="iconfont blue mr5">&#xe605;</i>
                                    <span class="time">19:30-21:00</span>
                                </div>
                                <div class="aui-info-item aui-text-grey">
                                   会议室剩余间数<span class="aui-text-blue mlr5">12</span>间
                                </div>
                            </li>
                            <li class="aui-list-item aui-padded-lr20">
                                <div class="aui-info-item">
                                    <i class="iconfont blue mr5">&#xe605;</i>
                                    <span class="time">19:30-21:00</span>
                                </div>
                                <div class="aui-info-item aui-text-grey">
                                   会议室剩余间数<span class="aui-text-red mlr5">0</span>间
                                </div>
                            </li>
                        </ul>
                    </article>
                </div>
            </div>
        </div>
    </div>
    <div class="sign_bg">
        <img src="${ctx }/image/sigin_bg.png" alt="" />
    </div>
</body>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/api.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/layer.js"></script>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery.autocompleter.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/bespeak.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-mvalidate.js"></script>

</html>