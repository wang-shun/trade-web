<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>申请审批意见</title>

    <link rel="stylesheet" href="../static/css/bootstrap.min.css">
    <link rel="stylesheet" href="../static/font-awesome/css/font-awesome.css">

    <link rel="stylesheet" href="../static/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="../static/css/style.css" rel="stylesheet">

    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="../static/css/plugins/stickup/stickup.css">
    <link rel="stylesheet" href="../static/trans/css/common/stickmenu.css">

    <link rel="stylesheet" href="../static/css/plugins/aist-steps/steps.css">
    <link rel="stylesheet" href="../static/css/plugins/toastr/toastr.min.css">

    <!-- index_css  -->
    <link rel="stylesheet" href="../static/iconfont/iconfont.css" ">

    <link rel="stylesheet" href="../static/trans/css/spv/table.css" />
    <link rel="stylesheet" href="../static/trans/css/common/input.css" />
    <link rel="stylesheet" href="../static/trans/css/spv/see.css" />
    <link rel="stylesheet" href="../static/trans/css/spv/spv.css" />

    <link rel="stylesheet" href="../static/trans/js/response/css/jkresponsivegallery.css
" />



</head>

<body>

    <div id="wrapper">
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span>
                                <img alt="image" class="img-circle" src="../static/img/profile_small.jpg"/>
                            </span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                    <span class="block m-t-xs">
                                        <strong class="font-bold">
                                            金姣姣
                                        </strong>
                                    </span>
                                    <span class="text-muted text-xs block">
                                        交易顾问
                                    </span>
                                    <span class="text-muted text-xs block">
                                        虹口杨浦贵宾服务部A组
                                        <b class="caret">
                                        </b>
                                    </span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li>
                                    <a href="#">
                                        我的信息
                                    </a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="javascript:void(0)" onClick="LogoutUtils.logout();return false;">
                                        注销
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">
                            YUCUI+
                        </div>
                    </li>
                    <li class="">
                        <a href="../workbench/dashboard.html">
                            <i class="fa fa-home">
                            </i>
                            <span class="nav-label">
                                个人工作台
                            </span>
                        </a>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-th-large">
                            </i>
                            <span class="nav-label">
                                我的案件
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li class="">
                                <a href="../workflow/myCaseList.html">
                                    <i class="fa ">
                                    </i>
                                    案件总览
                                </a>
                            </li>
                            <li class="">
                                <a href="../workflow/caseDistribute.html">
                                    <i class="fa ">
                                    </i>
                                    待分配案件
                                </a>
                            </li>
                            <li class="">
                                <a href="../workflow/myTaskList.html">
                                    <i class="fa ">
                                    </i>
                                    待办任务
                                </a>
                            </li>
                            <li>
                                <a href="../workflow/unlocatedTasks.html">
                                    <i class="fa ">
                                    </i>
                                    无主任务
                                </a>
                            </li>
                            <li>
                                <a href="unlocatedCase.html">
                                    <i class="fa ">
                                    </i>
                                    无主案件
                                </a>
                            </li>
                            <li>
                                <a href="tracking.html">
                                    <i class="fa ">
                                    </i>
                                    案件追踪
                                </a>
                            </li>
                            <li>
                                <a href="mortgageApproveLost.html">
                                    <i class="fa ">
                                    </i>
                                    流失案件
                                </a>
                            </li>
                            <li>
                                <a href="../workflow/toTaskOfGroupList.html">
                                    <i class="fa ">
                                    </i>
                                    本组待办列表
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="">
                            <i class="fa fa-glass">
                            </i>
                            <span class="nav-label">
                                E+贷款
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li class="">
                                <a href="../eloan/eloan_index.html">
                                    <i class="fa ">
                                    </i>
                                    E+贷款主页列表
                                </a>
                            </li>
                            <li>
                                <a href="../eloan/eloan_detail.html">
                                    <i class="fa ">
                                    </i>
                                    E+贷款详情
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                佣金管理
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="../award/read.html">
                                    <i class="fa ">
                                    </i>
                                    佣金查看
                                </a>
                            </li>
                            <li class="">
                                <a href="../award/personBonus.html">
                                    <i class="fa ">
                                    </i>
                                    我的计件奖金
                                </a>
                            </li>
                            <li class="">
                                <a href="../award/baseAward.html">
                                    <i class="fa ">
                                    </i>
                                    我的基础计件奖金
                                </a>
                            </li>
                            <li class="">
                                <a href="../award/baseAwardReport.html">
                                    <i class="fa ">
                                    </i>
                                    计件奖金报表
                                </a>
                            </li>
                            <li class="">
                                <a href="../award/baseAwardReport.html">
                                    <i class="fa ">
                                    </i>
                                    可计件奖金案件筛选
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="active">
                        <a href="">
                            <i class="fa fa-glass">
                            </i>
                            <span class="nav-label">
                                资金监管
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li class="">
                                <a href="../spv/spvCaseList.html">
                                    <i class="fa ">
                                    </i>
                                    监管合约列表
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/see.html">
                                    <i class="fa ">
                                    </i>
                                    资金监管详情
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/spvDetails.html">
                                    <i class="fa ">
                                    </i>
                                    资金监管信息填写
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/waterDetail.html">
                                    <i class="fa ">
                                    </i>
                                    监管资金出入账流水
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/spvRecorded2.html">
                                    <i class="fa ">
                                    </i>
                                    入账审批驳回
                                </a>
                            </li>
                            <li class="active">
                                <a href="../spv/spvRecordShow.html">
                                    <i class="fa ">
                                    </i>
                                    入账申请审批意见
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/outAccount.html">
                                    <i class="fa ">
                                    </i>
                                    出账审批驳回
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/outAccountop.html">
                                    <i class="fa ">
                                    </i>
                                    出账申请审核意见
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/outAccountoption.html">
                                    <i class="fa ">
                                    </i>
                                    出账
                                </a>
                            </li>
                            <li class="">
                                <a href="../spv/spvRecorded.html">
                                    <i class="fa ">
                                    </i>
                                    入账
                                </a>
                            </li>
                        </ul>


                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-comment">
                            </i>
                            <span class="nav-label">
                                产调结果
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li class="">
                                <a href="../propertyresearch/processWaitList.html">
                                    <i class="fa ">
                                    </i>
                                    待处理产调
                                </a>
                            </li>
                            <li class="">
                                <a href="../propertyresearch/processingList.html">
                                    <i class="fa ">
                                    </i>
                                    已受理产调
                                </a>
                            </li>
                            <li class="">
                                <a href="../propertyresearch/successList.html">
                                    <i class="fa ">
                                    </i>
                                    已完成产调
                                </a>
                            </li>
                            <li class="">
                                <a href="../propertyresearch/resultGet.html">
                                    <i class="fa ">
                                    </i>
                                    产调结果
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                            <a href="#">
                                <i class="fa fa-th-large">
                                </i>
                                <span class="nav-label">
                                    报表
                                </span>
                                <span class="fa arrow">
                                </span>
                            </a>
                            <ul class="nav nav-second-level collapse">
                                <li class="">
                                    <a href="../report/district.html">
                                        <i class="fa ">
                                        </i>
                                        区域报表
                                    </a>
                                </li>
                                <li class="">
                                    <a href="../workflow/caseDistribute.html">
                                        <i class="fa ">
                                        </i>
                                        金融产品报表
                                    </a>
                                </li>
                                <li class="">
                                    <a href="../workflow/myTaskList.html">
                                        <i class="fa ">
                                        </i>
                                        评估费报表
                                    </a>
                                </li>
                                <li class="">
                                    <a href="../report/satisfactionDegree.html">
                                        <i class="fa ">
                                        </i>
                                        满意度
                                    </a>
                                </li>
                                <li>
                                    <a href="../workflow/unlocatedTasks.html">
                                        <i class="fa ">
                                        </i>
                                        贷款流失报表
                                    </a>
                                </li>
                                <li class="">
                                    <a href="../report/gageInfo.html">
                                        <i class="fa ">
                                        </i>
                                        临时银行案件列表
                                    </a>
                                </li>
                                <li class="">
                                    <a href="../report/sourceReport.html">
                                        <i class="fa ">
                                        </i>
                                        产调来源报表
                                    </a>
                                </li>
                                <li>
                                    <a href="tracking.html">
                                        <i class="fa ">
                                        </i>
                                        案件工作量详情
                                    </a>
                                </li>
                            </ul>
                        </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                贷款信息列表
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    我的贷款
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    本组贷款
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    本区贷款
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    贷款清单
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                临时银行模块
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    本组临时银行申请
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    本区临时银行申请
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    所有临时银行申请
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                贷款流失模块
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    流失单量分析
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    流失金额分析
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    流失区域分布分析
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    流失贵宾中心分析
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                资金监管合约管理
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    申请转合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    录入监管合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    保存监管合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    提交监管合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    检查交易异常
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    我的监管合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    本组监管合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    本区监管合约
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    监管合约清单
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                入款/放款方案管理
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    增加放款方案
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    查看放款方案
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    变更放款方案
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    审批放款方案变更
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    检查放款方案变更
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                销售管理
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    评估费收取单转化率
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    评估费打折系数
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                工作质量管理
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    客户满意度导入
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    客户满意度查看
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    客户满意度汇总
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-cny">
                            </i>
                            <span class="nav-label">
                                业绩/奖金模块
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    可计件案件池查看
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    可计件案件池导出
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    计件基础奖金计算
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    计件基础奖金归集
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    计件奖金计算
                                </a>
                            </li>
                        </ul>
                    </li>

                    <li class="">
                        <a href="#">
                            <i class="fa fa-calendar-o">
                            </i>
                            <span class="nav-label">
                                知识管理
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    知识库
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    我的知识
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-user">
                            </i>
                            <span class="nav-label">
                                个人设置
                            </span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    我的信息
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    授权代办
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>

        <div id="page-wrapper" class="gray-bg">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-warning " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <div class="navbar-header" style="margin-left: 30%;"><h2 class="welcome">欢迎使用誉萃交易平台！</h2><iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe></div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">    </span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i>  <span class="label label-warning" id="sp_badge">766</span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages" id="messageul">
                            <li>
                                <div class="dropdown-messages-box">
                                    <a class="pull-left">
                                        <span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);">
                                            <img class="img-circle himg" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg">
                                        </span>
                                    </a>
                                    <div class="media-body">
                                        <strong>
                                            跟进提醒
                                        </strong>
                                        <br>
                                        编号为ZY-NAJ-201607-0863，地址为上海杨浦区五角场街道国权路43号独栋号2411室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）
                                        <br>
                                        <small class="text-muted">
                                            2016-07-22
                                        </small>
                                    </div>
                                </div>
                            </li>
                            <div class="divider">
                            </div>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a class="pull-left">
                                        <span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);">
                                            <img class="img-circle himg" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg">
                                        </span>
                                    </a>
                                    <div class="media-body">
                                        <strong>
                                            跟进提醒
                                        </strong>
                                        <br>
                                        编号为ZY-NAJ-201607-0866，地址为上海杨浦区五角场镇市京一村13号0502室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）
                                        <br>
                                        <small class="text-muted">
                                            2016-07-22
                                        </small>
                                    </div>
                                </div>
                            </li>
                            <div class="divider">
                            </div>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a class="pull-left">
                                        <span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);">
                                            <img class="img-circle himg" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg">
                                        </span>
                                    </a>
                                    <div class="media-body">
                                        <strong>
                                            临时银行处理提醒
                                        </strong>
                                        <br>
                                        编号为ZY-AJ-201605-1527，地址为上海黄浦区西藏南路片区西藏南路1374弄21号0403室的案件临时银行已经处理. 临时银行为顾村公园支行(其他),
                                        请及时跟进.
                                        <br>
                                        <small class="text-muted">
                                            2016-07-22
                                        </small>
                                    </div>
                                </div>
                            </li>
                            <div class="divider">
                            </div>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a class="pull-left">
                                        <span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);">
                                            <img class="img-circle himg" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg">
                                        </span>
                                    </a>
                                    <div class="media-body">
                                        <strong>
                                            跟进提醒
                                        </strong>
                                        <br>
                                        编号为ZY-NAJ-201607-0862，地址为上海杨浦区五角场街道国权路43号独栋号2410室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）
                                        <br>
                                        <small class="text-muted">
                                            2016-07-22
                                        </small>
                                    </div>
                                </div>
                            </li>
                            <div class="divider">
                            </div>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a class="pull-left">
                                        <span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);">
                                            <img class="img-circle himg" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg">
                                        </span>
                                    </a>
                                    <div class="media-body">
                                        <strong>
                                            跟进提醒
                                        </strong>
                                        <br>
                                        编号为ZY-NAJ-201607-0865，地址为上海杨浦区五角场镇浣纱六村(浣沙六村)31号0603室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）
                                        <br>
                                        <small class="text-muted">
                                            2016-07-22
                                        </small>
                                    </div>
                                </div>
                            </li>
                            <div class="divider">
                            </div>
                            <li class="text-center link-block">
                                <div>
                                </div>
                                <a href="http://trade.centaline.com:8083/trade-web/message/box/siteList.html"
                                target="messageContent">
                                    <i class="fa fa-envelope">
                                    </i>
                                    <strong>
                                        去消息中心
                                    </strong>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        切换岗位
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs" id="portalOrgJob">
                    <li><a href="javascript:void(0);" onclick="orgJob.changeOrgJob(&quot;8dc9766035cc47819e7eb28ad2c16bc6&quot;,&quot;ff8080814f459a78014f45ab2c39000d&quot;)">交易顾问@虹口杨浦贵宾服务部A组</a></li></ul>
                    </li>
                </ul>
            </nav>
            </div>

            <!-- main Start -->

            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->


                    <div class="ibox-content space30" >
                        <div class="agree-tile">
                            资金入账申请
                        </div>
                        <div class="info_content">
                            <div class="line">
                                <p>
                                    <label>
                                       产品类型
                                    </label>
                                    <span class="info_one">光大四方资金监管</span>
                                </p>
                                <p>
                                    <label>
                                        监管金额
                                    </label>
                                    <span class="info_one">300万元人民币</span>
                                </p>

                                <p>
                                    <label>
                                        物业地址
                                    </label>
                                    <span class="info">上海市长宁区畅园3栋1702室</span>
                                </p>

                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                        收款人名称
                                    </label>
                                    <span class="info_one">中原地产监管账户</span>
                                </p>

                                <p>
                                    <label>
                                        收款人账户
                                    </label>
                                    <span class="info_one">62248757878587</span>
                                </p>

                                <p>
                                    <label>
                                        收款人开户行
                                    </label>
                                    <span class="info">中原地产监管账户开户行</span>
                                </p>
                            </div>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                入账申请信息
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered customerinfo">
                                        <thead>
                                            <th style="width: 100px;">付款人姓名</th>
                                            <th>付款人账户</th>
                                            <th style="width: 100px;">入账金额</th>
                                            <th style="width: 120px;">贷记凭证编号</th>
                                            <th>付款方式</th>
                                            <th>凭证附件</th>
                                        </thead>
                                        <tbody id="addTr">
                                            <tr>
                                                <td>
                                                    <div class="big">林小胖</div>
                                                </td>
                                                <td>
                                                    <div class="marspace">6224567890123456789</div>
                                                    <div class="marspace">中国农村合作信用社</div>
                                                </td>

                                                <td class="text-left" >
                                                    <div class="big">50万元</div>
                                                </td>
                                                <td>
                                                    <div class="big">DJZP-000001</div>
                                                </td>
                                                <td>
                                                    <div class="big">转账凭证</div>
                                                </td>
                                                <td>
                                                    <a class="response" href="../static/trans/img/uplody01.png" title="凭证1"><button type="button" class="btn btn-sm btn-default" ><i class="icon iconfont icon_y">&#xe635;</i> 凭证1</button></a>
                                                    <a class="response" href="../static/trans/img/uplody02.png" title="凭证2"><button type="button" class="btn btn-sm btn-default" data-toggle="modal" data-target="#showImg"  ><i class="icon iconfont icon_y">&#xe635;</i>凭证2</button></a>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </div>
                            </form>
                        </div>

                    </div>

                    <div class="ibox-content clearfix space30">
                        <div class="title">
                            <strong>审核意见</strong>
                        </div>
                        <div class="view-content">
                            <div class="view-box">
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>张小核(资金监管专员)</em></span>
                                   <span class="result pink_bg">未通过</span>
                                   <span class="time">审核日期:<em>2016-9-12</em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">账号与附件不一致，重新填写</em>
                                </p>
                            </div>
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>张小核(资金监管专员)</em></span>
                                   <span class="result pink_bg">未通过</span>
                                   <span class="time">审核日期:<em>2016-9-12</em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">账号与附件不一致，重新填写,账号与附件不一致，重新填写账号与附件不一致，重新填写账号与附件不一致，重新填写,账号与附件不一致，重新填写账号与附件不一致，重新填写账号与附件不一致，重新填写,账号与附件不一致，重新填写账号与附件不一致，重新填写</em>
                                </p>
                            </div>

                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>张小核(资金监管专员)</em></span>
                                   <span class="result pink_bg">未通过</span>
                                   <span class="time">审核日期:<em>2016-9-12</em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">账号与附件不一致，重新填写</em>
                                </p>
                            </div>
                        </div>
                        </div>


                        <div class="submitter">
                            提交人：<span>张三(业务员)</span>
                        </div>
                        <div class="excuse">
                            <form action="">
                                <textarea name="" id="" placeholder="请填写审核意见" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;"></textarea>
                            </form>
                            <div class="form-btn">
                            <div class="text-center">
                                <button type="submit" class="btn btn-success btn-space">审批通过</button>
                                <button type="submit" class="btn btn-pink btn-space">审批驳回</button>
                                <button type="submit" class="btn btn-default btn-space">取消</button>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- main End -->

        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="../static/js/jquery-2.1.1.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
    <script src="../static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="../static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="../static/js/inspinia.js"></script>
    <script src="../static/js/plugins/pace/pace.min.js"></script>

    <!-- stickup plugin -->
    <script src="../static/trans/js/spv/spvRecorded.js"></script>

    <script src="../static/trans/js/response/js/jkresponsivegallery.js"></script>


<script>
$(function() {
    $('.response').responsivegallery();
});
</script>



</body>

</html>