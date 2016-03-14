<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>屏幕首页</title>
	<link rel="stylesheet" href="${ctx}/momedia/css/carousel/style.css"> 
	<link rel="stylesheet" href="${ctx}/momedia/css/fullpage/jquery.fullPage.css">
	<link rel="stylesheet" href="${ctx}/momedia/css/workload/css/startPage.css">
	<style>
		
	</style>
</head>
  <body>
        <div id="dowebok" >
		    <div class="section">
		        <div class="slide"><iframe src="workload?orgId=${orgId }" scrolling=”no” name="firstSlide"></iframe></div>
		        <div class="slide"><iframe src="showRLightList?orgId=${orgId }" scrolling=”no” name="secondSlide"></iframe></div>
		        <div class="slide"><iframe src="showRank?orgId=${orgId }" scrolling=”no”  name="thirdSlide"></iframe></div>
		        
		        
		        <%-- <div class="slide"><jsp:include page="workload2.jsp?orgId=${orgId }"></jsp:include> </div>
		        <div class="slide section2">222</iframe></div>
		        <div class="slide">333</div> --%>
		    </div>
		</div> 
		<div id="play_mv">
			<div class="firework1"><img alt="1" src="../../../momedia/images/fireworks.gif" class="fireworks"></div>
			<div class="mask"></div>
			<div class="cont"><p id="play_content"> 浦东贵宾服务部A组顾问 王小毛 攻榜成功！目前在E+金融周榜中排名第一，金融产品申请金额总计1000万 </p></div>
			<div  class="firework2"><img alt="1" src="../../../momedia/images/fireworks.gif"class="fireworks"></div>
		</div>
		<!-- <div id="div_none"></div> -->
		<%--<div id="dowebok">
		    <div class="section">
		        <iframe src="workload?orgId=${orgId }" scrolling=”no” ></iframe>
		    </div>
		     <div class="section">
		       <iframe src="showRLightList?orgId=${orgId }" scrolling=”no” ></iframe>
		    </div>
		    <div class="section">
		       <iframe src="showRank?orgId=${orgId }" scrolling=”no” ></iframe>
		    </div> 
		</div>
		--%>
		<audio id="aaa" src="../../../momedia/mp3/fireworks.mp3"  controls style="
    display: none;"></audio>
		
	    <script src="${ctx}/momedia/js/jquery-2.1.1.js"></script>
	    <script src= "${ctx}/momedia/js/plugins/carousel/script.js" type="text/javascript" ></script>
	    <script src= "${ctx}/momedia/js/plugins/fullpage/jquery.fullPage.min.js" type="text/javascript" ></script>
	     
	    <script>
	    var orgId = "${orgId}";
		$(function(){
			var ctx = "${ctx}";
		    $('#dowebok').fullpage({
		        sectionsColor : ['#1bbc9b', '#4BBFC3', '#7BAABE', '#f90'],
		        continuousVertical: true,
		        slidesNavigation:true,
		        controlArrowColor:"transparent"
		    });
		
		    var i = 0;
		    setInterval(function(){
		         $.fn.fullpage.moveSlideRight();
		         i = (i+1)%3;
		         if(i==0) {
		        	 thirdSlide.window.loadData(); 
		         } else if(i==1) {
		        	 firstSlide.location.reload(true);
		         } else {
		        	 secondSlide.location.reload(true); 
		         } 
		    }, 15000);
		});
		var playQueue=new Array();
		function doPlay(e){
			if($("#play_mv").attr("play")=="play"){
				playQueue.unshift(e);
				return true;
			}
			$("#play_mv").attr("play","play");
			aaa.src="../../../momedia/mp3/fireworks.mp3";
			aaa.play();
			$("#play_mv").fadeIn();
			$("#div_none").show();
			$("#play_content").html(e);
			$(".fireworks").attr("src","../../../momedia/images/fireworks.gif?"+new Date());
			setTimeout(playEnd,5000);
			setTimeout(closeMp3,2500);
			
		}
		function closeMp3(){
			aaa.pause();
		}
		function playEnd(){
			$("#play_mv").fadeOut();
			$("#div_none").hide();
			
			if(playQueue.length!=0){
				setTimeout(function(){
					 $("#play_mv").attr("play","");
					  
				    }, 2000);doPlay(playQueue.pop());
			}else{
				$("#play_mv").attr("play","");
			}
		}
		$("body").click(function(){
			//doPlay("<em>浦东贵宾服务部A组</em> 顾问 <em>王小毛</em> 攻榜成功！目前在E+金融周榜中排名第一，金融产品申请金额总计 <strong>1000万</strong> ");
			//doPlay("<em>浦东贵宾服务部A组</em> 顾问 <em>王小毛</em> 签约一单 <em>E+首付贷/换房贷</em>，金额<strong>50万</strong>，为E+金融产品推波助澜");
		});
		
		setInterval(function(){
			$.ajax({
				url :"${ctx}/mobile/dashboard/box/getNewAgent",
				method:"post",
				dataType:"json",
				data: {orgId: orgId}, 
				success : function(data) {
					  if(data.newLoanAgent&&data.newLoanAgent.length>0){
						  var nlas=data.newLoanAgent;
						  for(var i in nlas){
							  var nla=nlas[i];
							  var content="<em>"+nla.orgName+"</em> 顾问 <em>"+nla.realName+"</em> 签约一单 <em>"+nla.loanSevCode+"<em> 金额<strong>"+(nla.loanAmount/10000)+"万</strong>";
							  doPlay(content);
						  }
					  }
	                  
				}
			});
		    }, 60000);

		</script> 
		
</body>
</html>