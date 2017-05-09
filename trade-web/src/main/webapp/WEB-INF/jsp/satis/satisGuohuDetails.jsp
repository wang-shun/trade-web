<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>过户回访
    </title>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="${ctx}/static/css/animate.css" />
    <link rel="stylesheet" href="${ctx}/static/css/style.css" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" >


    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/caseDetail.css" >
    <link rel="stylesheet" href="${ctx}/static/trans/css/workflow/details.css" >



</head>

<body class="pace-done"><div class="pace  pace-inactive"><div class="pace-progress" data-progress-text="100%" data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
  <div class="pace-progress-inner"></div>
</div>
<div class="pace-activity"></div></div>
    <div id="wrapper">
        <!-- 左侧菜单栏 01-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span>
                                <img alt="image" class="img-circle" src="${ctx}/static/img/profile_small.jpg"/>
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
                        <a href="${ctx}/workbench/dashboard（manager）.html">
                            <i class="fa fa-home">
                            </i>
                            <span class="nav-label">
                                个人工作台
                            </span>
                        </a>
                        <iframe id="tmp_downloadhelper_iframe" style="display: none;">
                        </iframe>
                    </li>
                    <li class="active">
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
                                <a href="myCaseList.html">
                                    <i class="fa ">
                                    </i>
                                    案件总览
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/caseDistribute.html">
                                    <i class="fa ">
                                    </i>
                                    待分配案件
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/myTaskList.html">
                                    <i class="fa ">
                                    </i>
                                    待办任务
                                </a>
                            </li>
                            <li>
                                <a href="${ctx}/workflow/unlocatedTasks.html">
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
                                <a href="${ctx}/workflow/toTaskOfGroupList.html">
                                    <i class="fa ">
                                    </i>
                                    本组待办列表
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/detailsFollow.html">
                                    <i class="fa ">
                                    </i>
                                    首次跟进详情页
                                </a>
                            </li>
                            <li class="active">
                                <a href="${ctx}/workflow/detailsSign.html">
                                    <i class="fa ">
                                    </i>
                                    签约详情页
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/detailsLoansettle.html">
                                    <i class="fa ">
                                    </i>
                                    贷款结清详情页
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/detailsSelfloan.html">
                                    <i class="fa ">
                                    </i>
                                    自办贷款审批
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/detailsCheckpurchase.html">
                                    <i class="fa ">
                                    </i>
                                    查限购
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/detailsFillplan.html">
                                    <i class="fa ">
                                    </i>
                                    填写交易计划详情页
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/workflow/detailsLoss.html">
                                    <i class="fa">
                                    </i>
                                    贷款流失审批（主管）详情页
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
                            <li>
                                <a href="${ctx}/eloan/eloan_index.html">
                                    <i class="fa ">
                                    </i>
                                    金融产品信息填写
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
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    佣金查看
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    我的计件奖金
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    我的基础计件奖金
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
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
                                <a href="${ctx}/spv/spvCaseList.html">
                                    <i class="fa ">
                                    </i>
                                    监管合约列表
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/spv/waterDetail.html">
                                    <i class="fa ">
                                    </i>
                                    监管资金出入账流水
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

        <!-- 右侧内容 -->
        <div id="page-wrapper" class="gray-bg dashbard-1" style="min-height: 778px;">
            <!-- 右侧页面抬头 -->
            <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-warning " href="http://trade.centaline.com:8083/trade-web/case/caseDetail?caseId=16396#"><i class="fa fa-bars"></i> </a>
                </div>
                <div class="navbar-header" style="margin-left: 30%;"><h2 class="welcome">欢迎使用誉萃交易平台！</h2></div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">    </span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="http://trade.centaline.com:8083/trade-web/case/caseDetail?caseId=16396#">
                            <i class="fa fa-envelope"></i>  <span class="label label-warning" id="sp_badge">0</span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages" id="messageul">
                        </ul>
                    </li>
                    <li>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="http://trade.centaline.com:8083/trade-web/case/caseDetail?caseId=16396#">
                        切换岗位
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs" id="portalOrgJob">
                    <li><a href="javascript:void(0);" onclick="orgJob.changeOrgJob(&quot;8a8493d45184b1450151a4f13c7516aa&quot;,&quot;ff8080814f459a78014f45abd1020010&quot;)">交易主管@虹口杨浦贵宾服务部后台1组</a></li><li><a href="javascript:void(0);" onclick="orgJob.changeOrgJob(&quot;6114AB949B4445828D4383977C4FAC71&quot;,&quot;ff8080814f68d45f014f6e99db850071&quot;)">誉萃总监@虹口杨浦贵宾服务部</a></li><li><a href="javascript:void(0);" onclick="orgJob.changeOrgJob(&quot;8dc9766035cc47819e7eb28ad2c16bc6&quot;,&quot;ff8080814f459a78014f45abd1020010&quot;)">交易主管@虹口杨浦贵宾服务部A组</a></li></ul>
                </li></ul>
            </nav>
            </div>
            <!-- 右侧页面主体内容 -->
    <input type="hidden" id="ctx" value="http://trade.centaline.com:8083/trade-web">
    <input type="hidden" id="ctm" value="CBC-1-201608-0014">
    <input type="hidden" id="Lamp1" value="-1">
    <input type="hidden" id="Lamp2" value="1">
    <input type="hidden" id="Lamp3" value="3">
    <input type="hidden" id="Lamp3" value="3">
    <input type="hidden" id="activityFlag" value="30003003">
    <input type="hidden" id="caseCode" value="ZY-SH-201608-0095">
    <input type="hidden" id="instCode" value="">
    <input type="hidden" id="srvCodes" value="30004009,30004015">
    <input type="hidden" id="processDefinitionId" value="">
    <div id="salesLoading" style="display: none">
        <div id="loading-center">
            <div id="loading-center-absolute">
                <div class="object" id="object_one"></div>
                <div class="object" id="object_two" style="left: 20px;"></div>
                <div class="object" id="object_three" style="left: 40px;"></div>
                <div class="object" id="object_four" style="left: 60px;"></div>
                <!--<div class="object" id="object_five" style="left:80px;"></div>-->
            </div>
        </div>
        <div id="loading-center-absolute-second">系统正在处理，请稍后${ctx}.</div>
    </div>
    <!-- 主要内容页面 -->
    <nav id="navbar-example" class="navbar navbar-default navbar-static" role="navigation">
        <div id="isFixed" style="position: relative; top: 0px;" class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
            <ul class="nav navbar-nav scroll_content">
                <li class="menuItem active"><a href="#"> 基本信息 </a></li>
                <li class="menuItem"><a href="#"> 服务流程 </a></li>
                <li class="menuItem"><a href="#"> 相关信息 </a></li>
            </ul>
        </div>
    </nav>
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
                <div class="top12 panel" id="basicInfo">
                    <div class="sign sign-red">
                        结案
                    </div>
                    <div class="sign sign-blue">
                        已过户
                    </div>
                    <div class="sign sign-yellow">
                        税费卡
                    </div>
                    <div class="panel-body">
                        <div class="ibox-content-head">
                            <h2 class="title">
                                案件基本信息
                            </h2>
                            <small class="pull-right">誉萃编号：ZY-SH-201608-0095｜中原编号：CBC-1-201608-0014</small>
                        </div>
                        <div id="infoDiv infos" class="row">
                            <div class="ibox white_bg">
                                <div class="info_box info_box_one col-sm-4 ">
                                    <span>物业信息</span>
                                    <div class="ibox-conn ibox-text">
                                        <dl class="dl-horizontal">
                                            <dt>CTM地址</dt>
                                            <dd>上海浦东新区洋泾片区羽山路100弄5号0301室</dd>
                                            <dt>产证地址</dt>
                                            <dd>上海浦东新区洋泾片区羽山路100弄5号0301室</dd>
                                            <dt>层高</dt>
                                            <dd>3／18</dd>
                                            <dt>产证面积</dt>
                                            <dd>103.72平方</dd>
                                            <dt>房屋类型</dt>
                                            <dd>

                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                                <div class="info_box info_box_two col-sm-5">
                                    <span>买卖双方</span>
                                    <div class="ibox-conn else_conn">
                                        <dl class="dl-horizontal col-sm-6">
                                            <dt>上家姓名</dt>
                                            <dd>
                                                <div id="seller"><a data-toggle="popover" data-placement="right" data-content="" data-original-title="" title="">赵晓明</a>&nbsp;</div>
                                            </dd>
                                        </dl>
                                        <dl class="dl-horizontal col-sm-6">
                                            <dt>下家姓名</dt>
                                            <dd>
                                                <div id="buyer"><a data-toggle="popover" data-placement="right" data-content="13921981251" data-original-title="" title="">贾先生</a>&nbsp;</div>
                                            </dd>
                                        </dl>
                                    </div>
                                    <span>经纪人信息</span>
                                    <div class="ibox-conn else_conn_two ">
                                        <dl class="dl-horizontal">
                                            <dt>姓名</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="13701867479" data-original-title="" title="">
                                                    阮俊</a>
                                            </dd>
                                            <dt>所属分行</dt>
                                            <dd>ACCBCA.南丹分行一组全体</dd>
                                            <dt>直管经理</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="13817585505" data-original-title="" title="">
                                                    邵雷 </a>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                                <div class="info_box info_box_three col-sm-3">
                                    <span>经办人信息</span>
                                    <div class="ibox-conn ibox-text">
                                        <dl class="dl-horizontal">
                                            <dt>交易顾问</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="13918843797" data-original-title="" title="">
                                                    陶晨 </a>
                                            </dd>
                                                    <dt>合作顾问</dt>
                                                    <dd>
                                                        <a data-toggle="popover" data-placement="right" data-content="13916540926" data-original-title="" title="">
                                                            顾新峰 </a>
                                                    </dd>

                                                    <dt>合作顾问</dt>
                                                    <dd>
                                                        <a data-toggle="popover" data-placement="right" data-content="13817789952" data-original-title="" title="">
                                                            许庆祺 </a>
                                                    </dd>


                                            <dt>助理</dt>
                                            <dd>
                                                <a data-toggle="popover" data-placement="right" data-content="" data-original-title="" title="">
                                                     </a>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            <div class="row wrapper white-bg new-heading ">
            <div class="pl10">
            <h2 class="newtitle-big">
                过户回访
            </h2>
            </div>
        </div>

    <div class="ibox-content border-bottom clearfix space_box noborder">
        <div>
            <h2 class="newtitle title-mark">上家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">赵晓明—189898989898</span>
                            <span >赵晓明2—189898989898</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                            <select class="select_control yuanwid">
                                <option value="">是</option>
                                <option value="">否</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label> 
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label> 
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪还贷评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">陪同还贷意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">过户评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">过户意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">下家回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">贾先生—13555555555</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid">
                                    <option value="">是</option>
                                    <option value="">否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">过户评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">过户意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h2 class="newtitle title-mark">经纪人回访</h2>
            <div class="form_list">
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">客户信息</label>
                            <span class="mr10">阮俊—13666666666</span>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">电话是否正确</label>
                                 <select class="select_control yuanwid">
                                    <option value="">是</option>
                                    <option value="">否</option>
                                </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small select_style mend_select">
                                电话结果
                            </label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">签约意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">贷款意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">公积金意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">过户评分</label>
                            <select class="select_control yuanwid">
                                <option value="">0</option>
                                <option value="">1</option>
                                <option value="">2</option>
                                <option value="">3</option>
                                <option value="">4</option>
                                <option value="">5</option>
                                <option value="">6</option>
                                <option value="">7</option>
                                <option value="">8</option>
                                <option value="">9</option>
                                <option value="">10</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">过户意见</label>
                            <input class=" input_type yuanwid" style="width: 435px;" placeholder="" value="" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <div class="title title-mark">
                <strong>附件信息</strong>
            </div>
            <div class="view-content">
            </div>
        </div>
        <div>
            <div class="title title-mark mb15">
            <strong>案件跟进</strong>
            </div>
            <div class="view-content">
                <div class="view-box">
                    <p class="text-center">
                        <img src="${ctx}/static/trans/img/false.png" height="100" alt="" />
                    </p>
                </div>
                <div class="form_list clearfix">
                   <input class="input_type pull-left" placeholder="" value="" style="width:93%;">
                   <button class="btn btn_more pull-right" style="width:60px;">跟进</button>
                </div>
            </div>
        </div>

                     <div class="form-btn">
                            <div class="text-center">
                                <button  class="btn btn-success btn-space">保存</button>
                                <button class="btn btn-success btn-space" data-toggle="modal" data-target="#myModal">回访打回</button>
                                <button class="btn btn-success btn-space">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--********** 弹窗页面 **********-->
                <div class="modal inmodal in" id="myModal" tabindex="-1" role="dialog" aria-hidden="false" >
                    <div class="modal-dialog" style="width:760px;">
                        <div class="animated fadeIn popup-box" style="background-color: #fff;padding:30px 30px 60px 30px;">
                            <div class="modal_title">
                                回访打回
                            </div>
                            <textarea name="" id="" class="textarearoom mt10" style="width:100%;max-width: 760px;margin-left:0;max-height: 150px;height: 150px;" >电话多次打不通，提示已关机！需要经纪人配合。。。
                            </textarea>
                            <div class="add_btn text-center mt20">
                                <button type="button" class="btn btn-success">
                                    打回
                                </button>
                                <button type="reset" class="btn btn-grey" data-dismiss="modal">
                                    关闭
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <!--********** 弹窗页面 **********-->
    </div>

<style>

</style>
    <!-- Mainly scripts -->
    <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>

        <!-- jqGrid -->
    <script src="${ctx}/static/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/static/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    </body>
    </html>