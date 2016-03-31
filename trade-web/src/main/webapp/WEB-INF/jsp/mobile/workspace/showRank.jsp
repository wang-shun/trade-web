<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<title>屏幕首页</title>
	<link rel="stylesheet" href="../../..//momedia/css/rank/css/style.css">
</head>
<body id="rank">
	<div class="container">
		<div class="part pull-left">
			<h2>各组金融产品转化率排名</h2>
			<div class="monthly-rank">
				<h3><i class="icon-month"></i>月排名</h3>
				<ul id="rateRankM">
					<!-- <li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-red">50.20%</p>
							</div>
					</li>
					<li class="danger second">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-2"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-orange">50.20%</p>
							</div>
					</li>
					<li class="danger third">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-3"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-yellow">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">4</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">5</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">6</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li> -->
				</ul>
			</div>
			<div class="weekly-rank">
				<h3><i class="icon-week"></i>周排名</h3>
				<ul id="rateRankW">
					<!-- <li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-red">50.20%</p>
							</div>
					</li>
					<li class="danger second">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-2"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-orange">50.20%</p>
							</div>
					</li>
					<li class="danger third">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-3"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-yellow">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">4</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">5</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">6</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li> -->
				</ul>
			</div>
		</div>
		<div class="part pull-right">
			<h2>金融产品个人申请额度龙虎榜</h2>
			<div class="monthly-rank">
				<h3><i class="icon-month"></i>月排名</h3>
				<ul id="amountRankM">
					<!-- <li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-red">50.20%</p>
							</div>
					</li>
					<li class="danger second">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-2"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-orange">50.20%</p>
							</div>
					</li>
					<li class="danger third">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-3"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-yellow">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">4</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">5</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">6</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li> -->
				</ul>
			</div>
			<div class="weekly-rank">
				<h3><i class="icon-week"></i>周排名</h3>
				<ul id="amountRankW">
					<!-- <li class="danger">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-red">50.20%</p>
							</div>
					</li>
					<li class="danger second">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-2"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-orange">50.20%</p>
							</div>
					</li>
					<li class="danger third">
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-big.jpg" alt="img">
								<span class="medal medal-3"></span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text-yellow">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">4</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">5</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li>
					<li>
							<div class="feed-element pull-left">
								<img class="img-circle" src="images/pic-small.jpg" alt="img">
								<span class="badge">6</span>
							</div>
							<div class="media-body">
								<p class="mt">浦东贵宾服务A组</p>
								<p class="text">50.20%</p>
							</div>
					</li> -->
				</ul>
			</div>
		</div>
		<div class="logo pull-right"></div>
	</div>
	<script src="../../..//momedia/js/jquery-2.1.1.js"></script>
	    <script src= "../../../momedia/js/template.js" type="text/javascript" ></script>
	    <script id="rankDetils" type= "text/html">
 			{{if data!=null}}
				{{each data as item index}}
				<li class="{{if index<3}}danger rank-{{index+1}}{{/if}}">
							<div class="feed-element pull-left">
								<img class="img-circle" src="{{fileSev}}{{item.empCode}}.jpg" alt="img" onerror="showDefImg();">
								{{if index<3}}
									<span class="medal {{if index != 0}}medal-{{index+1}} {{/if}}"></span>
								{{/if}}
								{{if index>=3}}
									<span class="badge">{{index+1}}</span>
								{{/if}}
							</div>
							<div class="media-body">
								<p class="">{{item.belongOrgName}}</p>
								<p class="text{{if index==0}}-red{{/if}}{{if index==1}}-orange{{/if}}{{if index==2}}-yellow{{/if}}">{{!item.rankValue?'0.00':item.rankValue}}%</p>
							</div>
					</li>
				{{/each}}
			{{/if}}
		</script>
		<script id="rankDetils1" type= "text/html">
 			{{if data.length>0}}
				{{each data as item index}}
				<li class="{{if index<3}}danger rank-{{index+1}}{{/if}}">
							<div class="feed-element pull-left">
								<img class="img-circle" src="{{fileSev}}{{item.empCode}}.jpg" alt="img" onerror="showDefImg();">
								{{if index<3}}
									<span class="medal {{if index != 0}}medal-{{index+1}} {{/if}}"></span>
								{{/if}}
								{{if index>=3}}
									<span class="badge">{{index+1}}</span>
								{{/if}}
							</div>
							<div class="media-body">
								<p class="">{{item.realName}}</p>
								<p class="text{{if index==0}}-red{{/if}}{{if index==1}}-orange{{/if}}{{if index==2}}-yellow{{/if}}">{{!item.rankValue?'0':item.rankValue}}</p>
							</div>
					</li>
				{{/each}}
			{{/if}}
		</script>
		<script>
		var ctx="${ctx}";
		var wWinner='';
		var mWinner='';
		function showDefImg(){
			event.target.src="../../../momedia/img/a5.png";
		}
		function loadData(){
			$.ajax({
				url : "../../../mobile/dashboard/box/getRank",
				method:"post",
				dataType:"json",
				success : function(data) {
					  var fs="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/";
					  var rateRankM= template('rankDetils' , {data:data.rateRankM,fileSev:fs});
	                  $( "#rateRankM").empty();
	                  $( "#rateRankM").html(rateRankM);
	                  
					  var rateRankW= template('rankDetils' , {data:data.rateRankW,fileSev:fs});
	                  $( "#rateRankW").empty();
	                  $( "#rateRankW").html(rateRankW);
	                  
					  var amountRankM= template('rankDetils1' ,{data:data.amountRankM,fileSev:fs});
	                  $( "#amountRankM").empty();
	                  $( "#amountRankM").html(amountRankM);
	                  
					  var amountRankW= template('rankDetils1' , {data:data.amountRankW,fileSev:fs});
	                  $( "#amountRankW").empty();
	                  $( "#amountRankW").html(amountRankW);
	                  chekcWinner(data);
				}
			});
		}
		loadData();
		function chekcWinner(data){
    		  if(data.amountRankM[0]){
    			  var mw=data.amountRankM[0];
    			  
    			  if(mWinner!=mw.empCode&&mWinner!=''){
    				  if(parent.doPlay){
    					  var content="<em>"+mw.belongOrgName+"</em> 顾问 <em>"+mw.realName+"</em> 攻榜成功！目前在E+金融周榜中排名第一，金融产品申请金额总计 <strong>"+(mw.rankValue/10000)+"万</strong>"; 
	    				  parent.doPlay(content);
    				  }
    			  }
    			  mWinner=mw.empCode;
    		  }
    		  if(data.amountRankW[0]){
    			  var ww=data.amountRankW[0];
    			  if(wWinner!=ww.empCode &&wWinner!=''){
    				  if(parent.doPlay){
    					  var content="<em>"+ww.belongOrgName+"</em> 顾问 <em>"+ww.realName+"</em> 攻榜成功！目前在E+金融周榜中排名第一，金融产品申请金额总计 <strong>"+(ww.rankValue/10000)+"万</strong>";
	    				  parent.doPlay(content);
    				  }
    			  }
    			  wWinner=ww.empCode;
    		  }
		}
		</script>
</body>
</html>