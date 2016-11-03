<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>详情</title>
	<%-- <link rel="stylesheet" href="${ctx}/momedia/css/caseDetailsStyle.css"> --%>
	<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
    <link href="${ctx}/static/css/mobile-yc-style4.css" rel="stylesheet">
</head>
<body>
    <div id="wrapper">
        <div id="page-wrapper" class="gray-bg">

            <div class="wrapper wrapper-content">
                <div class="row animated fadeInRight">
                    <div class="col-lg-12">

                        <div class="ibox">
                            <div class="ibox-content case-title-content" >
                                <div id="case-title" class="row">
                                    <div class="col-lg-12">
                                        <dl class="dl-horizontal">
                                            <dt><strong><h4>案件编号</h4></strong></dt>
                                            <dd>
                                                <span class="label label-success">${toCase.caseCode }</span>
                                            </dd>
                                            <dt>地址</dt>
                                            <dd>${toPropertyInfo.propertyAddr }</dd>

                                            <dt>上家</dt>
                                            <dd>${seller }</dd>
                                            <dt>下家</dt>
                                            <dd>${buyer }</dd>

                                            <dt>经纪人</dt>
                                            <dd>${user.realName } | ${user.orgName }</dd>
                                            <dt>客户经理</dt>
                                            <dd>${userManager.realName } | ${userManager.orgName }</dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>

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
                                                    <img alt="image" class="img-circle m-t-xs img-responsive" src="{{imgApp}}/{{item.EMPLOYEE_CODE}}.jpg"> 
                                                    <button type="button" class="btn btn-grey" data-toggle="modal" data-target="#myModal2">
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