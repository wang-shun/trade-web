<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的工作台（业务员权限）</title>

    <link rel="stylesheet" href="../static/css/bootstrap.min.css" >
    <link rel="stylesheet" href="../static/font-awesome/css/font-awesome.css" >

    <link rel="stylesheet" href="../static/css/animate.css" >
    <link rel="stylesheet" href="../static/css/style.css" ></head>

    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="../static/css/plugins/stickup/stickup.css" >
    <link rel="stylesheet" href="../static/trans/css/common/stickDash.css" >

    <link rel="stylesheet" href="../static/css/plugins/aist-steps/steps.css" ></head>
    <link rel="stylesheet" href="../static/css/plugins/toastr/toastr.min.css" >

    <!-- add_css  -->
    <link href="../static/trans/css/demo/workbench/dashboard/dashboard.css" rel="stylesheet">

<body>

    <div id="wrapper">
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span>
                                <img alt="image" class="img-circle" src="../static/img/profile_small.jpg">
                            </span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                    <span class="block m-t-xs">
                                        <strong class="font-bold">金姣姣</strong>
                                    </span>
                                    <span class="text-muted text-xs block">交易顾问</span>
                                    <span class="text-muted text-xs block">
                                        虹口杨浦贵宾服务部A组
                                        <b class="caret"></b>
                                    </span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li>
                                    <a href="#">我的信息</a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="javascript:void(0)" onclick="LogoutUtils.logout();return false;">
                                        注销
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">
                            YUCUI+
                        </div>
                    </li>
                    <li class="active">
                        <a href="#">
                            <i class="fa fa-home">
                            </i>
                            <span class="nav-label">
                                个人工作台
                            </span>
                        </a>
                        <iframe id="tmp_downloadhelper_iframe" style="display: none;">
                        </iframe>
                    </li>
                    <li class="">
                        <a href="#">
                            <i class="fa fa-th-large">
                            </i>
                            <span class="nav-label">我的案件</span>
                            <span class="fa arrow">
                            </span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    案件总览
                                </a>
                            </li>
                            <li class="">
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    待办任务
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    无主任务
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    案件追踪
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa ">
                                    </i>
                                    流失案件
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="">
                        <a href="#">
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
                                <a href="#">
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
                        <ul class="dropdown-menu dropdown-messages" id="messageul"><li><div class="dropdown-messages-box"><a class="pull-left"><span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);"><img class="img-circle himg" src="../static/http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg"></span></a><div class="media-body"><strong>跟进提醒</strong><br> 编号为ZY-NAJ-201607-0863，地址为上海杨浦区五角场街道国权路43号独栋号2411室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）<br><small class="text-muted">2016-07-22</small></div></div></li><div class="divider"></div><li><div class="dropdown-messages-box"><a class="pull-left"><span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);"><img class="img-circle himg" src="../static/http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg"></span></a><div class="media-body"><strong>跟进提醒</strong><br> 编号为ZY-NAJ-201607-0866，地址为上海杨浦区五角场镇市京一村13号0502室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）<br><small class="text-muted">2016-07-22</small></div></div></li><div class="divider"></div><li><div class="dropdown-messages-box"><a class="pull-left"><span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);"><img class="img-circle himg" src="../static/http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg"></span></a><div class="media-body"><strong>临时银行处理提醒</strong><br> 编号为ZY-AJ-201605-1527，地址为上海黄浦区西藏南路片区西藏南路1374弄21号0403室的案件临时银行已经处理. 临时银行为顾村公园支行(其他), 请及时跟进.<br><small class="text-muted">2016-07-22</small></div></div></li><div class="divider"></div><li><div class="dropdown-messages-box"><a class="pull-left"><span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);"><img class="img-circle himg" src="../static/http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg"></span></a><div class="media-body"><strong>跟进提醒</strong><br> 编号为ZY-NAJ-201607-0862，地址为上海杨浦区五角场街道国权路43号独栋号2410室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）<br><small class="text-muted">2016-07-22</small></div></div></li><div class="divider"></div><li><div class="dropdown-messages-box"><a class="pull-left"><span class="shead" style="background-image: url(&quot;http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg&quot;);"><img class="img-circle himg" src="../static/http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/A1820.jpg"></span></a><div class="media-body"><strong>跟进提醒</strong><br> 编号为ZY-NAJ-201607-0865，地址为上海杨浦区五角场镇浣纱六村(浣沙六村)31号0603室的案件需要您立即跟进！经纪人”杨先芝“电话为（15800504374）<br><small class="text-muted">2016-07-22</small></div></div></li><div class="divider"></div><li class="text-center link-block"><div></div><a href="http://trade.centaline.com:8083/trade-web/message/box/siteList.html" target="messageContent"><i class="fa fa-envelope"></i><strong>去消息中心</strong></a></li></ul>
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
            <div class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
               <ul class="nav navbar-nav">
                    <li class="menuItem active"><a href="#base_info">基本信息</a></li>
                    <li class="menuItem"><a href="#zj_info">业务提醒</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="base_info">
                        <div class="row" style="position: relative;">
                            <h5 class="main_titile" style="position:absolute;top:0;
                            left:25px;font-size: 14px;">半年案件分布统计</h5>
                            <div class="col-md-9">

                                <div id="main" style="width:100%;height:250px;"></div>
                            </div>
                            <div class="col-md-3">
                                <div class="task_light text-center">
                                    <p class="fa_red"><i class="fa fa-bell "></i>红灯任务<small>0</small></p>
                                    <p class="fa_orange"><i class="fa fa-bell "></i>黄灯任务<small>1</small></p>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="ibox-content" id="zj_info">
                     	<h5 class="main_titile">业务提醒</h5>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="widget-box">
                                    <div class="widget-header widget-header-flat">
                                        <h4 class="smaller">反馈提醒</h4>
                                        <div class="widget-toolbar">
                                            <label>
                                               <span class="label label-blue">0</span>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <img src="../static/img/no_idea.jpg" alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="widget-box">
                                    <div class="widget-header widget-header-flat">
                                        <h4 class="smaller">作业提醒</h4>
                                        <div class="widget-toolbar">
                                            <label>
                                               <span class="label label-blue">1</span>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <img src="../static/img/no_idea.jpg" alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="widget-box">
                                    <div class="widget-header widget-header-flat">
                                        <h4 class="smaller">止损提醒</h4>
                                        <div class="widget-toolbar">
                                            <label>
                                               <span class="label label-blue">2</span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <img src="../static/img/no_idea.jpg" alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

     <!-- Mainly scripts -->
    <script src="../static/js/jquery-2.1.1.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
    <script src="../static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="../static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <!-- ECharts.js -->
    <script src="../static/js/echarts.min.js"></script>



    <!-- Custom and plugin javascript -->
    <script src="../static/js/inspinia.js"></script>
    <script src="../static/js/plugins/pace/pace.min.js"></script>

    <!-- stickup plugin -->
    <script src="../static/js/plugins/stickup/stickUp.js"></script>
    <script src="../static/trans/js/demo/workbench/stickDash.js"></script>

    <!-- Toastr script -->
    <script src="../static/js/plugins/toastr/toastr.min.js"></script>
    <script src="../static/js/morris/morris.js"></script>
    <script src="../static/js/morris/raphael-min.js"></script>

    <!-- index_js -->
    <script src="../static/trans/js/demo/workbench/morrisDash.js"></script>


</body>

</html>