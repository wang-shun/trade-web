<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<title>屏幕首页</title>
	<link rel="stylesheet" href="../../../momedia/css/carousel/style.css">
	<link rel="stylesheet" href="../../../momedia/css/dashbord/style.css">
	
</head>
<body>
	<div class="container">
		<div class="col col-1 pull-left">
			<div class="ibox ibox-red">
				<p class="pull-right">红灯任务<br><span id="redCount"></span></p>
			</div>
			<div class="ibox ibox-yellow">
				<p class="pull-right">黄灯任务<br><span id="yellowCount"></span></p>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">组别排名</div>
				<div class="panel-body orgRedColorRemainList">
					<ul>
						<li><span class="name"><span class="badge badge-danger">1</span>浦东贵宾服务A组</span><span class="pull-right"><i class="icon-light"></i>201</span></li>
						<li><span class="name"><span class="badge badge-warning">2</span>浦东贵宾服务A组</span><span class="pull-right"><i class="icon-light"></i>201</span></li>
						<li><span class="name"><span class="badge badge-yellow">3</span>浦东贵宾服务A组</span><span class="pull-right"><i class="icon-light"></i>201</span></li>
						<li><span class="name"><span class="badge">4</span>浦东贵宾服务A组</span><span class="pull-right"><i class="icon-light"></i>201</span></li>
						<li><span class="name"><span class="badge">5</span>浦东贵宾服务A组</span><span class="pull-right"><i class="icon-light"></i>201</span></li>
						<li><span class="name"><span class="badge">6</span>浦东贵宾服务A组</span><span class="pull-right"><i class="icon-light"></i>201</span></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col col-2 pull-left">
			<div class="panel panel-danger">
				<div class="panel-heading">红灯员工通报</div>
				<div class="panel-body " id="userRlist">
					<ul>
						<li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="triangle red"></span>
								<span class="warning">!</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="triangle orange"></span>
								<span class="warning">!</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="triangle yellow"></span>
								<span class="warning">!</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="badge">4</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="badge">5</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="badge">6</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="badge">7</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
						<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/E1560.jpg" alt="img">
								<span class="badge">8</span>
							</div>
							<div class="media-body">
								<p class="name pull-left">张三萨</p>
								<span class="pull-right"><i class="icon-light"></i>21</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col col-3 pull-left">
			<div class="panel">
				<div class="panel-heading">红灯任务项提醒</div>
				<div class="panel-body redColorRemain">
				         <marquee direction="up" behavior="scroll" scrollamount="6" scrolldelay="0" loop="-1" width="" height="" bgcolor="" hspace="10" vspace="10">
							<ul>
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
									 
				                    <li>
										<span class="tips"><i class="icon-light"></i>审税</span>
										<span class="person"><i class="icon-person"></i>美七七</span>
									    <span class="date-box"><i class="icon-date"></i>2016-02-17</span>
									 </li>
							</ul>
							</marquee>
					<!-- <ul>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
						<li>
							<span class="tips icon-light">审税</span>
							<span class="icon-person">美七七</span>
							<span class="icon-date">2016-02-17</span>
						</li>
					</ul> -->
					<!-- <a href="" class="btn">滚动处理</a> -->
				</div>
			</div>
		</div>
		<div class="logo pull-right"></div>
	</div>
	<content tag="local_script"> 
		<script id="redColorRemainList" type= "text/html">
          {{if content.length>0}}
				<marquee direction="up" behavior="scroll" scrollamount="6" scrolldelay="0" loop="-1" width="" height="" bgcolor="" hspace="10" vspace="10">
				<ul>
          		{{each content as item}}
	                    <li>
							<span class="tips"><i class="icon-light"></i>{{item.taskName}}</span>
							<span class="person "><i class="icon-person"></i>{{item.realName}}</span>
						    <span class="date-box "><i class="icon-date"></i>{{item.estPartTime}}</span>
						</li>
           		{{/each}}
				</ul>
				</marquee>
           {{/if}}
    	   {{if content.length=0}}
				无任何提醒!
           {{/if}}
	   </script>
	   <script id="userRedColorRemainList" type= "text/html">
	   {{if redLight.length>0}}
		<ul>
	  	 {{each redLight as item index}}
         <li class="{{if index < 3}}danger{{/if}}">  
			<div class="feed-element pull-left">
					<img class="img-circle" src="{{fileSer}}{{item.eCode}}.jpg" onerror="showDefImg();" alt="img">		
				</span>	
				<span class="triangle orange"></span>
				<span class="warning">!</span>
			
			</div>
			<div class="media-body">
				<p class="name pull-left">{{item.realName}}</p>
      
				<span class="pull-right"><i class="icon-light"></i>{{item.count}}</span>
			</div>
		</li>
		{{/each}}
			</ul>
		{{/if}}
		{{if redLight.length<=0}}
			<img class='nodata' src='../../../momedia/img/nodata.png'>
		{{/if}}
	   </script>
	   <script id="orgRedColorRemainList" type= "text/html">
          {{if lightList.length>0}}
				<ul>
          		{{each lightList as item index}}
						<li><span class="name"><span class="badge badge-danger">{{index+1}}</span>{{item.orgName}}</span><span class="pull-right"><i class="icon-light"></i>{{item.count}}</span></li>
           		{{/each}}
				</ul>
           {{/if}}
    	   {{if lightList.length=0}}
				无任何提醒!
           {{/if}}
	   </script>
	    
	    <script src="../../..//momedia/js/jquery-2.1.1.js"></script>
	    <script src= "../../../momedia/js/template.js" type="text/javascript" ></script>
       	<script>
		function showDefImg(){
			event.target.src="../../../momedia/img/a5.png";
		}
		var ctx = "${ctx}";
		var orgId = "${orgId}";
			jQuery(document).ready(function() {
				loadData();
			});
			function loadData(){
				//红灯任务项提醒列表
				$.ajax({
					url : "../../../mobile/dashboard/box/queryRyLightList",
					method:"post",
					dataType:"json",
					data: {orgId: orgId},  
					success : function(data) {
						  var redColorRemainList= template('redColorRemainList' , data);
		                  $( ".redColorRemain").empty();
		                  $( ".redColorRemain").html(redColorRemainList);

					}
				});
				//红灯与黄灯数量
				$.ajax({
					url : "../../../mobile/dashboard/box/firstPage",
					method:"post",
					dataType:"json",
					data: {orgId: orgId},  
					success : function(data) {
						  var orgRedColorRemainList= template('orgRedColorRemainList' , data);
		                  $( ".orgRedColorRemainList").empty();
		                  $( ".orgRedColorRemainList").html(orgRedColorRemainList);
		                  
		                  $( "#redCount").empty();
		                  $( "#redCount").html(data.redLight);
		                  
		                  $( "#yellowCount").empty();
		                  $( "#yellowCount").html(data.yeLight);
					}
				});
				$.ajax({
					url : "../../../mobile/dashboard/box/rLightList",
					method:"post",
					dataType:"json",
					data: {orgId: orgId},  
					success : function(data) {
						data.fileSer="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/";
						  var html= template('userRedColorRemainList' , data);
		                  $( "#userRlist").empty();
		                  $( "#userRlist").html(html);
					}
				});
			}
		</script>
	</content>
</body>
</html>