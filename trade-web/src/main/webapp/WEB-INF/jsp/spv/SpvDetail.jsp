<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>监管合约详情</title>
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="${ctx}/static/trans/css/common/stickmenu.css">
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />


</head>
<body>

 <!-- main Start -->
            <div class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
               <!-- <ul class="nav navbar-nav">
                    <li class="menuItem active"><a href="#base_info">基础信息</a></li>
                    <li class="menuItem"><a href="#spvone_info">附件</a></li>
                    <li class="menuItem"><a href="#spvtwo_info">出流水账单</a></li>
                    <li class="menuItem"><a href="#spvthree_info">相关合约状态</a></li>
                </ul>
                <div class="menu_btn" style="float: right;
    margin-right: 250px;margin-top: 6px">
                    <button class="btn btn-warning">保存</button>
                </div>
            </div> -->
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->
                    <div class="ibox-content elan_detail">
                        <div class="row">
                            <div class="col-lg-9">
                                <div class="row" id="">
                                    <div class="col-lg-12">
                                        <div class="m-b-md">
                                            <h4>光大银行资金监管</h4>
                                        </div>

                                    </div>
                                </div>
                                <div class="row" id="">
                                    <div class="col-lg-6" id="cluster_info">
                                            <dl class="dl-horizontal">
                                                    <dt>合约编号</dt>
                                                <dd><a herf="#">ZY-JR-201609-0001</a></dd>
                                                <dt>产权地址</dt>
                                                <dd>上海市普陀区蕰川路1613弄11号109室</dd>
                                                 <dt>合作机构</dt>
                                               <dd>光大银行市西支行</dd>
                                            </dl>
                                        </div>
                                    <div class="col-lg-3" id="cluster_info">
                                        <dl class="dl-horizontal" >
                                            <dt>监管金额</dt>
                                            <dd>200万元</dd>
                                            <dt>已转入金额</dt>
                                            <dd>200万元</dd>
                                            <dt>已转出金额</dt>
                                            <dd>200万元</dd>
                                        </dl>
                                    </div>
                                    <div class="col-lg-3">
                                        <dl class="dl-horizontal">
                                            <dt>申请人</dt>
                                            <dd><span data-container="body" data-toggle="popover" data-placement="right" data-content="手机：137 9541 0234"><a>王明</a></span> </dd>
                                            <dt>风控专员</dt>
                                            <dd><span data-container="body" data-toggle="popover" data-placement="right" data-content="手机：137 9541 0234"><a>廖容</a></span></dd>
                                            <dt>风控总监</dt>
                                            <dd>
                                                <span data-container="body" data-toggle="popover" data-placement="right" data-content="手机：137 9541 0234"><a>王嘉明</a></span>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row bs-wizard" style="border-bottom:0; margin-left:15px">
                                              <div class="col-lg-4 bs-wizard-step complete">
                                                  <div class="progress">
                                                      <div class="progress-bar"></div>
                                                  </div>
                                                  <a href="#" class="bs-wizard-dot"></a>
                                                  <div class="bs-wizard-info text-center">
                                                    <dl>
                                                        <dd><span>申请</span> 2016-8-18</dd>
                                                    </dl>
                                                  </div>
                                              </div>
                                              <div class="col-lg-5 bs-wizard-step active">
                                                  <div class="progress">
                                                      <div class="progress-bar"></div>
                                                  </div>
                                                  <a href="#" class="bs-wizard-dot"></a>
                                                  <div class="bs-wizard-info text-center">
                                                    <dl>
                                                        <dd><span>签约</span> 2016-8-20</dd>
                                                    </dl>
                                                  </div>
                                              </div>
                                              <div class="col-lg-2 bs-wizard-step disabled">
                                                  <div class="progress">
                                                      <div class="progress-bar"></div>
                                                  </div>
                                                  <a href="#" class="bs-wizard-dot"></a>
                                                  <div class="bs-wizard-info text-center">
                                                        <dl>
                                                         <dd><span>结束</span> 2016-8-21</dd>
                                                    </dl>
                                                  </div>
                                              </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="m-b-md">
                                            <h4>关联合约</h4>
                                        </div>
                                    </div>
                                </div>
                                <div class="feed-activity-list">
                                    <div class="feed-element">
                                        <div class="pull-lef contract-icon-block" >
                                            <div class="icon icon_blue iconfont">&#xe607;</div>
                                        </div>
                                        <div class="media-body">
                                            <strong><a>YC-AJ-201606142394</a></strong><br/>经办人  <strong>胡俊楠</strong>. <br/>
                                            <small class="text-muted">2016-06-14 15:30 网签</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="ibox-content clearfix" id="base_info">
                        <div class="panel blank-panel">
                            <div class="panel-heading">
                                <div class="panel-options">
                                    <ul class="nav nav-tabs">
                                        <li class="active">
                                            <a href="#tab-1" data-toggle="tab" aria-expanded="true">买卖双方</a>
                                        </li>
                                        <li class="">
                                            <a href="#tab-2" data-toggle="tab" aria-expanded="false">房产及交易</a>
                                        </li>
                                        <li class="">
                                            <a href="#tab-3" data-toggle="tab">
                                                监管资金的支付
                                            </a>
                                        </li>
                                        <li class="">
                                            <a href="#tab-4" data-toggle="tab">资金监管及账户</a>
                                        </li>
                                        <li class="">
                                            <a href="#tab-5" data-toggle="tab">资金放款方案</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                            <div class="panel-body">
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tab-1">
                                        <div class="panel-body">
                                            <div id="infoDiv infos" class="row">
                                                <div class="ibox white_bg row">
                                                    <div class="info_box info_box_one col-md-6 ">
                                                        <span>买家信息</span>
                                                        <div class="ibox-conn ibox-text">
                                                            <dl class="dl-horizontal">
                                                                <dt>客户姓名</dt>
                                                                <dd>王小胖</dd>
                                                                <dt>性别</dt>
                                                                <dd>男</dd>
                                                                <dt>家庭住址</dt>
                                                                <dd>上海市静安区华盛世纪广场12号楼房1791</dd>
                                                                <dt>身份证</dt>
                                                                <dd>039394049399044494</dd>
                                                                <dt>证件有效期</dt>
                                                                <dd>
                                                                        长期有效
                                                                </dd>
                                                            </dl>
                                                        </div>
                                                    </div>
                                                    <div class="info_box info_box_one col-md-6 ">
                                                        <span>卖家信息</span>
                                                        <div class="ibox-conn ibox-text">
                                                            <dl class="dl-horizontal">
                                                                <dt>客户姓名</dt>
                                                                <dd>张三</dd>
                                                                <dt>性别</dt>
                                                                <dd>男</dd>
                                                                <dt>家庭住址</dt>
                                                                <dd>上海市普陀区中心广场15号楼房254</dd>
                                                                <dt>身份证</dt>
                                                                <dd>039394049399044494</dd>
                                                                <dt>证件有效期</dt>
                                                                <dd>
                                                                        长期有效
                                                                </dd>
                                                            </dl>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="tab-pane" id="tab-2">
                                        <div class="row" id="">
                                            <div class="col-md-5">
                                                <ul class="real-estate">
                                                    <li>
                                                        <p>
                                                            <em>房产证号</em>
                                                            <span>沪私09090030号</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>房屋住址</em>
                                                            <span>上海市普陀区蕰川路1613弄11号109室</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>房产权利人</em>
                                                            <span>王明</span>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="col-md-4">
                                                <ul class="real-estate">
                                                    <li>
                                                        <p>
                                                            <em>面积</em>
                                                            <span>200平方米</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>网签合同号</em>
                                                            <span>wq3498232324</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>买方自有金额支付金额</em>
                                                            <span title="">200万元</span>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="col-md-3">
                                                <ul class="real-estate">
                                                    <li>
                                                        <p>
                                                            <em>下家付款方式</em>
                                                            <span>全款</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>金额大写</em>
                                                            <span title="">伍佰伍拾万元整</span>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="tab-3">
                                        <div class="row" id="">
                                            <div class="col-md-4">
                                                <ul class="real-estate">
                                                    <li>
                                                        <p>
                                                            <em>买方自有资金支付金额</em>
                                                            <span>100万</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>买方贷款资金支付金额</em>
                                                            <span>100万</span>
                                                        </p>
                                                    </li>

                                                </ul>
                                            </div>
                                            <div class="col-md-4">
                                                <ul class="real-estate">
                                                    <li>
                                                        <p>
                                                            <em>商业贷款金额</em>
                                                            <span>80万</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <em>公积金贷款金额</em>
                                                            <span>20万</span>
                                                        </p>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-pane" id="tab-4">
                                    <div class="row">
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="info_box info_box_one col-md-6 ">
                                                    <span>收款账户</span>
                                                    <div class="ibox-conn ibox-text">
                                                        <dl class="dl-horizontal account">
                                                            <dt>姓名</dt>
                                                            <dd>王小胖</dd>
                                                            <dt>归属地</dt>
                                                            <dd>上海市</dd>
                                                            <dt>开户行</dt>
                                                            <dd>上海市普陀区光大江苏路分行</dd>
                                                            <dt>账号</dt>
                                                            <dd>110548754562525252</dd>
                                                        </dl>
                                                    </div>
                                                </div>
                                                <div class="info_box info_box_one col-md-6 ">
                                                    <span>退款账户</span>
                                                    <div class="ibox-conn ibox-text">
                                                        <dl class="dl-horizontal account">
                                                            <dt>姓名</dt>
                                                            <dd>张三</dd>
                                                            <dt>归属地</dt>
                                                            <dd>上海市</dd>
                                                            <dt>开户行</dt>
                                                            <dd>上海市普陀区光大江苏路分行</dd>
                                                            <dt>账号</dt>
                                                            <dd>110548754562525252</dd>
                                                        </dl>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                        <table class="table table-bordered table-hover customerinfo" style="display:none;">
                                            <thead>
                                                <tr>
                                                    <th>
                                                        账户归属
                                                    </th>
                                                    <th>
                                                        姓名
                                                    </th>
                                                    <th>
                                                        归属地
                                                    </th>
                                                    <th>
                                                        开户行
                                                    </th>
                                                    <th>
                                                        账号
                                                    </th>
                                                    <th>
                                                        买方自有金额支付金额
                                                    </th>
                                                    <th>
                                                        金额大写
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        收款账户
                                                    </td>
                                                    <td>
                                                        <a href="javascript:void(0);" title="" >
                                                            胡飞
                                                        </a>
                                                    </td>
                                                    <td>
                                                        上海市
                                                    </td>
                                                    <td>
                                                        上海市普陀区光大江苏路分行
                                                    </td>
                                                    <td>
                                                        110548754562525252
                                                    </td>
                                                    <td>
                                                        5500000
                                                    </td>
                                                    <td>
                                                        伍佰伍拾万元整
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        退款账户
                                                    </td>
                                                    <td>
                                                        <a href="javascript:void(0);" title="" >
                                                            李磊
                                                        </a>
                                                    </td>
                                                    <td>
                                                        上海市
                                                    </td>
                                                    <td>
                                                        上海市中山公园龙之梦分行
                                                    </td>
                                                    <td>
                                                        232325256895856852
                                                    </td>
                                                    <td>
                                                    </td>
                                                    <td>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="tab-pane" id="tab-5">
                                        <table class="table table-bordered table-hover customerinfo col-md-6">
                                            <thead>
                                                <tr>
                                                    <th>
                                                        次数
                                                    </th>
                                                    <th>
                                                        划转条件
                                                    </th>
                                                    <th>
                                                        每次划转金额
                                                    </th>
                                                    <th>
                                                        卖方账户
                                                    </th>
                                                    <th>
                                                        资金方
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        2
                                                    </td>
                                                    <td>
                                                        申请交易过户登记后
                                                    </td>
                                                    <td>
                                                        240万
                                                    </td>
                                                    <td>
                                                        100万
                                                    </td>
                                                    <td>
                                                        140万
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                    </td>
                                                    <td>
                                                        标准交易过户申请后
                                                    </td>
                                                    <td>
                                                        180万
                                                    </td>
                                                    <td>
                                                        180万
                                                    </td>
                                                    <td>
                                                        0万
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                    </td>
                                                    <td>
                                                        合计
                                                    </td>
                                                    <td>
                                                        300万
                                                    </td>
                                                    <td>
                                                        250万
                                                    </td>
                                                    <td>
                                                        50万
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- main End -->


	<content tag="local_script"> <script
		src="${ctx}/js/inspinia.js"></script> <script
		src="${ctx}/js/plugins/pace/pace.min.js"></script> <script>
			//点击浏览器任何位置隐藏提示信息
			$("body").bind("click", function(evt) {
				if ($(evt.target).attr("data-toggle") != 'popover') {
					$('a[data-toggle="popover"]').popover('hide');
				}
			});
		</script> </content>
</body>
</html>



