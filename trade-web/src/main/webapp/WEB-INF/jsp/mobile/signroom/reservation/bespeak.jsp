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
            <span class="">返回</span>
        </a>
        <div class="aui-title">签约室预约</div>
    </header>
    <section class="aui-content aui-margin-b-15">
    	<form id="bespeakForm">
    		<input type="hidden" id="ctx" value="${ctx }"/>
    		<input type="hidden" id="defaultTradeCenterId" name="defaultTradeCenterId" value="${defaultTradeCenterId }"/>
    		<input type="hidden" name="selTradeCenterId" id="selTradeCenterId" value="${defaultTradeCenterId }"/>
    		<input type="hidden" name="agentCode" id="agentCode" value="${agentCode }" />
    		<input type="hidden" name="selBespeakTime" id="selBespeakTime" />
    		<input type="hidden" name="selResDate" id="selResDate" />
    		
	        <ul class="aui-list aui-form-list">
	            <li class="aui-list-header newgrey">您还剩余<span class="num">${remainBespeakNumber }</span>次预约机会</li>
	            <li class="aui-list-item">
	                <div class="aui-list-item-inner">
	                    <div class="aui-list-item-label">
	                        <i class="iconfont blue mr5">&#xe60d;</i>预约中心
	                    </div>
	                    <div class="aui-list-item-input aui-list-item-arrow">
	                        <select id="selTradeCenter">
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
	                        <input type="number" id="numberOfPeople" name="numberOfPeople" placeholder="请输入到场人数" min="1" >
	                    </div>
	                </div>
	            </li>
	            <li class="aui-list-item">
	                <div class="aui-list-item-inner">
	                    <div class="aui-list-item-label">
	                        <i class="iconfont blue mr5">&#xe60e;</i>预约时段
	                    </div>
	                    <div class="aui-list-item-input aui-list-item-arrow">
	                        <input type="text" id="dateseLect" placeholder="请选择预约时段">
	                    </div>
	                </div>
	            </li>
	            <li class="aui-list-header newgrey">办理事项</li>
	            <li class="aui-item">
	                <div class="aui-list-item-inner aui-input-list">
	                	<c:forEach items="${transactItemVoList}" var="transactItemVo">
	                        <div id="${transactItemVo.code }" class="aui-col-xs-3 aui-input-element choiceoption transactItemCode">
	                            	${transactItemVo.name }
	                        </div>
	                     </c:forEach>
	                </div>
	            </li>
	            <li class="aui-list-item item">
	                <div class="aui-list-item-inner">
	                    <div class="aui-list-item-label">
	                        <i class="iconfont blue mr5">&#xe608;</i>交易地址
	                    </div>
	                    <div class="aui-list-item-input" style="margin-right: 15px;">
	                    	<input type="text" id="propertyAddress" name="propertyAddress" class="font-small" data-required="true" data-descriptions="address">
	                    </div>
	                </div>
	            </li>
	            <li class="aui-list-item item">
	                <div class="aui-list-item-inner">
	                    <div class="aui-list-item-label">
	                        <i class="iconfont blue mr5">&#xe60c;</i>交易顾问
	                    </div>
	                    <div class="aui-list-item-input">
	                    	 <input type="text" id="serviceSpecialist" placeholder="请输入名称">
	                    </div>
	                </div>
	            </li>
	            <li class="aui-list-header newgrey">备注信息</li>
	            <li class="aui-list-item">
	                <div class="aui-list-item-inner">
	                    <div class="aui-list-item-input">
	                        <textarea placeholder="请输入内容" id="specialRequirement"></textarea>
	                    </div>
	                </div>
	            </li>
	        </ul>
	        
	        <div class="field-tooltipWrap" style="display:none;">
            	<div class="field-tooltipInner">
            		<div class="field-tooltip fieldTipBounceIn"><div class="zvalid-resultformat">请输入交易单地址</div>
            	</div>
            </div>
            </div>
	        
        	<div class="plr15 mt28"><input type="button" id="btnBespoke" class="aui-btn aui-btn-info aui-btn-block aui-btn-user" value="领取预约号"></div>
    	</form>
    </section>
    
    <div id="roomList" class="layui-m-layer layui-m-layer0" style="display:none;">
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
                              <th>周日</th>
                            </tr>
                          </thead>
                          <tbody id="dayList">
                            <!-- <tr>
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
                            </tr> -->
                          </tbody>
                        </table>
                    </section>
                    <section id="signroomList" style="min-height: 300px;">
                    	<div class="nodata">
	                    	<img src="${ctx}/image/nodata.png" width="100%" alt="" >
	                        <p class="text-center font16">对不起，暂无数据！</p>
	                        <p class="text-center">(请选择你所要的预约时间)</p>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
    <div class="sign_bg">
        <img src="${ctx }/image/sigin_bg.png" alt="" />
    </div>
    
    <!-- 预约成功提示框 -->
    <div id="layuiSuccess" class="layui-m-layer layui-m-layer0" style="display:none;">
        <div class="layui-m-layershade"></div>
        <div class="layui-m-layermain">
            <div class="layui-m-layersection">
                <div class="layui-m-layerchild  layui-m-anim-scale">
                    <div class="layui-m-layercont layui-padding">
                        <div class="dialog-user">
                            <i class="iconfont iconfont70 mt20 cyan">&#xe606;</i>
                            <h2 class="dialog-head mt30 font22">恭喜你预约成功</h2>
                            <div class="dialog-info"><p class="font18 mt20">预约编号: <span class="yellow ">A-FJ234s448</span></p><p class="mt5 font12">时间：<span id="spnResDate">2016-08-12</span>&nbsp;&nbsp;<span id="spnResTime">10:00-12:00</span></p></div>
                            <div class="mt20">
                                <div class="aui-btn aui-btn-info aui-btn-block aui-btn-mt10" onClick="goToMyReservationList();">确定</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 预约失败弹出框 -->
    <div id="layuiFalse" class="layui-m-layer layui-m-layer0" style="display:none;">
        <div class="layui-m-layershade"></div>
        <div class="layui-m-layermain">
            <div class="layui-m-layersection">
                <div class="layui-m-layerchild  layui-m-anim-scale">
                    <div class="layui-m-layercont layui-padding">
                        <div class="dialog-user">
                            <i class="iconfont iconfont70 mt20 pink">&#xe60b;</i>
                            <h2 class="dialog-head mt30 font22">预约失败</h2>
                            <div class="dialog-info">
                                <p class="font16 mt10" id="message"></p>
                            </div>
                            <div class="mt20" id="operation">
                                <div class="aui-btn aui-btn-info aui-btn-block aui-btn-mt10">换个时间试试</div>
                                <div class="aui-btn aui-btn-info aui-btn-block aui-btn-mt10">我愿意接受小房间</div>
                                <div class="aui-btn aui-btn-grey aui-btn-block aui-btn-mt10">取消</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
</body>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/api.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/layer.js"></script>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery.autocompleter.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/bespeak.js?v=1"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-mvalidate.js"></script>

</html>