<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>誉萃交易进度查询</title>
	<%-- <link rel="stylesheet" href="${ctx}/momedia/css/caseDetailsStyle.css"> --%>
	<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
    <link href="${ctx}/static/css/mobile-yc-style4.css" rel="stylesheet">
    <link href="${ctx}/static/iconfontmobile/iconfont.css" rel="stylesheet">
</head>
<body>
    <div id="wrapper">
        <div  class="white-bg">
            <header class="aui-bar aui-bar-nav">
                <a class="aui-pull-left" href="${ctx}/weixin/case/progressQueryList">
                    <span class="aui-iconfont aui-icon-left"><i class="iconfont font-20">&#xe628;</i></span>
                </a>
                <div class="aui-title font-18">进度查看</div>
            </header>
            <section class="case-info">
                <header class="font-16">案件信息</header>
                <div class="miuu-info">
                    <div class="mt5"><i class="iconfont wathet mr5">&#xe60f;</i>${toPropertyInfo.propertyAddr }</div>
                    <div class="clearfix mt5">
                        <span class="info-minute"><i class="iconfont wathet mr5">&#xe677;</i>${toCase.caseCode }</span>
                        <span class="info-minute"><i class="iconfont wathet mr5">&#xe60e;</i><fmt:formatDate value='${toCase.createTime }' type='both' pattern='yyyy-MM-dd'/></span>
                    </div>
                </div>
            </section>
            <section class="miuu-home clearfix">
                <p>上家：${seller }</p>
                <p>下家：${buyer }</p>
            </section>
            <section class="data-list">
               <div class="col-sm-12 miu-data">
                    <p class="clearfix">
                        <span class="info-minute"><i class="iconfont lightgrey mr5">&#xe670;</i>${leading.realName }(主办)</span>
                        <span class="info-minute"><i class="iconfont lightgrey mr5">&#xe614;</i>${leading.mobile }</span>
                    </p>
                    <p><i class="iconfont lightgrey mr5">&#xe612;</i>${leading.orgName }</p>
               </div>
               <c:if test="${!empty proList}">
                       <c:forEach items="${proList}" var="pro"> 
                      		<div class="col-sm-12 miu-data">
			                    <p class="clearfix">
			                        <span class="info-minute"><i class="iconfont lightgrey mr5">&#xe61a;</i>${pro.processorName}(合作)</span>
			                        <span class="info-minute"><i class="iconfont lightgrey mr5">&#xe614;</i>${pro.processorMobile}</span>
			                    </p>
			                    <p><i class="iconfont lightgrey mr5">&#xe612;</i>${pro.processorOrgName}</p>
		                	</div>
                       </c:forEach>
                </c:if>
            </section>
   				<div class="wrapper wrapper-content">
                <div class="row animated fadeInRight">
                    <div class="col-lg-12">
                        <div class="ibox">
                             <div class="ibox-content timeline-content" id="">
                                <div id="vertical-timeline" class="vertical-container dark-timeline content">

                                   <!--  <div class="vertical-timeline-block">
                                        <div class="vertical-timeline-icon timeline-qz-icon" >
                                        </div>


                                        <div class="vertical-timeline-content">

                                            <div class="col-sm-4 user-pic" >

                                                <div class="text-center">
                                                    <img alt="image" class="img-circle m-t-xs img-responsive" src="../static/img/u1.jpg"  data-toggle="modal" data-target="#myModal2">
                                                    <button type="button" class="btn btn-grey" data-toggle="modal" data-target="#myModal2">
                                                        <i class="fa fa-phone-square"></i>夏诚
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="col-sm-8 act-info">

                                                <p>
                                                    <span class="label label-danger" style="float:right">办理中</span>
                                                    <span style="float:left">
                                                        <h4> <strong>过户</strong>
                                                        </h4>
                                                    </span>
                                                </p>

                                                <p>服务描述：办理过户手续，缴纳交易税费</p>

                                                <p>计划完成时间：6月25日 </p>

                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                <div class="pull-right">
                   powered by
                    <strong>AIST</strong>
                    .
                </div>
                <div>
                    <strong>誉萃投资</strong>
                    真诚为您服务
                </div>
            </div>
            <!-- 弹框1 -->
            <div class="modal inmodal" id="myModal1" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content animated flipInY">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true"></span></button>
                        </div>
                        <div class="modal-body">
                            <div class="pic">
                                <div class="pic-in">
                                    <img src="../static/img/u1.jpg" alt="">
                                </div>
                                <div class="pic-info">
                                    <span class="jbr">经办人：金娇娇</span>
                                    <span>电　话：18655666655</span>
                                </div>
                            </div>
                            <div class="score">
                                <div class="score-star">
                                    <span>服务评分</span>
                                    <span><i class="star active"></i><i class="star active"></i><i class="star active"></i><i class="star"></i><i class="star"></i></span>
                                </div>
                                <div class="score-comm">
                                    <span>写点什么</span>
                                    <textarea placeholder="请您对我们的服务进行100字内的评价..."></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary btn-comm">发表评论</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 弹框1 -->
            <!-- 弹框2 -->
            <div class="modal inmodal" id="myModal2" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content animated flipInY">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true"></span></button>
                            <h5 class="modal-title">您可以电话联系</h5>
                        </div>
                        <div class="modal-body">
                            <ul>
                                <li><span>客户经理：${userManager.realName }</span><i class="phone-square"></i></li>
                                <!-- <li><span>环节经办人：夏诚</span><i class="phone-square"></i></li> -->
                                <li><span>经纪人：${user.realName }</span><i class="phone-square"></i></li>
                            </ul>
                        </div>
                        <div class="modal-footer1">
                            <span class="help"><i class="ques"></i></span>
                            <div class="help-info">
                                <span>帮助：<br></span>
                                <span>点击电话图标，拨打相关工作人员联系电话</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 弹框2 -->
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/momedia/js/template.js"></script>
	<script type="text/javascript" src="${ctx}/momedia/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Peity -->
    <script src="${ctx}/static/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

    <!-- Peity -->
    <script src="${ctx}/static/js/demo/peity-demo.js"></script>
     <!-- headroom -->
    <script src="https://npmcdn.com/headroom.js@0.9.3"></script>
    <script src="${ctx}/static/js/plugins/headroom/jQuery.headroom.js"></script>
    <script type="text/html" id="timeLine">
	{{if rows.length>0}}
		{{ each rows as item }}
				<div class="vertical-timeline-block">
                                        <div class="vertical-timeline-icon timeline-ej-icon" >
                                        </div>

                                        <div class="vertical-timeline-content">
                                            <div class="col-sm-4 user-pic">
                                                <div class="text-center">
                                                    <img alt="image" class="img-circle m-t-xs img-responsive" src="{{imgApp}}/{{item.EMPLOYEE_CODE}}.jpg" onerror="this.src='{{ctx}}/static/img/touxiang.png'"> 
                                                    <button type="button" class="btn btn-grey">
                                                        <i class="fa fa-phone-square"></i>{{item.ASSIGNEE}}
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="col-sm-8 act-info">
                                                <p>
                                                    <span style="float:left">
                                                        <h4> <strong>{{item.NAME}}</strong>
                                                        </h4>
                                                    </span>
                                                </p>

                                                
                                                <p>完成时间：{{item.real_time}}</p>

                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
             </div>
			{{/each}}
			{{/if}}
	</script>

    <script>
    	var ctx="${ctx}";
    	var imgApp = "${imgApp}";
    	var postData={queryId:"queryTaskHistoryItemListForMobile",argu_casecode:'${toCase.caseCode }',rows:999,page:1};
        $(document).ready(function(){
            $('#act-filter-content').headroom({
              "offset": 45,
              "tolerance": {
              down : 0,
              up : 0
            },
              "classes": {
                "initial": "animated",
                "pinned": "bounceInUp",
                "unpinned": "bounceOutDown"
              }
            });
            
           $.ajax({
                type: 'POST',
                url: ctx+'/quickGrid/findPage',
                data: postData,
                success: function(data){
                   data.imgApp = imgApp;
                   data.ctx = ctx;
             	   var html=template('timeLine', data); 
        				$(".content").append(html);
                },
                dataType: 'json',
                complete : function(){
                }
           });

        });
    </script>
</body>
</html>