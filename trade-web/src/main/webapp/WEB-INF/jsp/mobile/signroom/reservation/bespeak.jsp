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
<body class="white">
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">预约取号</div>
    </header>
    <form action="" id="form1">
    	<input type="hidden" id="ctx" value="${ctx }"/>
    	<input type="hidden" id="tradeCenterId" value="${tradeCenterId }"/>
		<input type="hidden" id="selDate" value="${selDate }"/>
		<input type="hidden" id="bespeakTime" value="${bespeakTime }"/>
		<input type="hidden" id="agentCode" value="${agentCode }"/>
		<input type="hidden" id="caseCode"/>
		
        <div class="aui-content aui-padded-l-10 aui-padded-r-10 margin10 border_grey radius3 linehgl30 relative">
            <input type="text" id="propertyAddress" name="propertyAddress" placeholder="交易单地址" class="excend10 font-small" data-required="true" data-descriptions="address">
            <i class="dingwei iconfont orange">&#xe60a;</i>
        </div>
        <section class="aui-content">
            <div class="aui-row pad10">
                <div class="aui-col-xs-3 linehgl30">
                    参与人数
                </div>
                <div class="aui-col-xs-5">
                    <input id="numberOfPeople" name="numberOfPeople" type="number" min="1" class="border_grey input30 radius3 font-small" placeholder="请输入实际人数" data-validate="phone" data-describedby="phone-description">
                </div>
            </div>
            <div class="aui-row pad10">
                <div class="aui-col-xs-3 linehgl30">
                    办理事项
                </div>
                <div class="aui-col-xs-9 add-select">
                	<c:forEach items="${transactItemVoList}" var="transactItemVo">
	                    <div class="choiceoption">
	                          <input type="button" id="${transactItemVo.code }" class="aui-border-btn" name="transactItemCode" value="${transactItemVo.name }" data-required="true" data-descriptions="choices" />
	                    </div>
                    </c:forEach>
                </div>
            </div>
            
            <div class="field-tooltipWrap" style="display:none;">
            	<div class="field-tooltipInner">
            		<div class="field-tooltip fieldTipBounceIn"><div class="zvalid-resultformat">请输入交易单地址</div>
            	</div>
            </div>
            </div>
            
            
            <div class="aui-row pad10">
                <div class="">
                    备注信息：
                </div>
                <div class="textarea">
                    <textarea name="" id="specialRequirement" cols="30" rows="3" class="" placeholder="如有其他特殊要求可填入此栏" ></textarea>
                </div>
            </div>
            <div class="plr30"><input type="button" class="aui-btn aui-btn-info aui-btn-block aui-btn-user" value="领取预约号" id="btnBespoke"></div>
        </section>
    </form>
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