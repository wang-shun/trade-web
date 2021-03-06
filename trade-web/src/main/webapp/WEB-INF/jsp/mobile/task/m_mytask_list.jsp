<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/list.css' />" rel="stylesheet">

<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<%-- <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet"> --%>
<%-- <link href="<c:url value='/css/animate.css' />" rel="stylesheet"> --%>
<link href="<c:url value='/css/plugins/dropzone/basic.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/dropzone.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/scrollpagination/scrollpagination.css' />" rel="stylesheet"/>

<!-- Morris -->
<link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />"
	rel="stylesheet">
<style type="text/css">
#selectDiv {
	width: 480px;
}

#inTextType_chosen {
	margin-left: 40px;
}
.ui-jqgrid tr.jqgrow td{
    font-weight:normal;
    // overflow:hidden;
    // white-space:pre;
    white-space: normal !important;
    vertical-align:text-top;
    
    height:auto;
    padding:0 2px 0 2px;
    border-bottom-width:1px;
    border-bottom-color:inherit;
    border-bottom-style:solid;
}
a {
    color: #333;
    text-decoration: none;
    outline: 0;
}
a:hover {
    color: #333;
    text-decoration: none;
}

</style>
</head>

<body style="height:800px;">
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />

<!-- 	<div class="row">
			<form method="get" class="form-horizontal"></form>

						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
						</div>

	</div> -->
	
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
						<div class="form-group">
							
								<div class="col-sm-2" style="float: left;">
								   <select id="inTextType" data-placeholder= "搜索条件设定"
		                                        class= "btn btn-white chosen-select" style="float :right;" onchange="intextTypeChange()">
											<option value="1" selected>产证地址</option>
											<option value="4">案件编号</option>
										</select>
								</div>
								<div class="col-sm-9" style="line-height:32px">
							      <input id="inTextVal" type="text" class="form-control">
							</div>
						</div>
						
						<div class="form-group">
							
							<div class="col-sm-11">
								<button id="searchButton" type="button" style="width:98%;" class="btn btn-primary pull-right">查询</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<a href="javascript:;" class="top"><i class="pic-top"></i></a>
        <article class="list" style="background-color:#fff;">
            <ul id="content" >
                    <li>
                        <div>
                            <span class="text-ellipsis">商业贷款办理</span>
                            <span><i class="icon-person"></i>周琴</span><span><i class="icon-calendar"></i>2016-01-25</span>
                        </div>
                        <p class="text-ellipsis"><i class="icon-address"></i>高科西路3030弄2号0201室高科西路3030弄2号0201室</p>
                    </li>
                    <li>
                        <div>
                            <span>商业贷款办理</span>
                            <span><i class="icon-person"></i>周琴</span><span><i class="icon-calendar"></i>2016-01-25</span>
                        </div>
                        <p><i class="icon-address"></i>高科西路3030弄2号0201室</p>
                    </li><li>
                        <div>
                            <span>商业贷款办理</span>
                            <span><i class="icon-person"></i>周琴</span><span><i class="icon-calendar"></i>2016-01-25</span>
                        </div>
                        <p><i class="icon-address"></i>高科西路3030弄2号0201室</p>
                    </li><li>
                        <div>
                            <span>商业贷款办理</span>
                            <span><i class="icon-person"></i>周琴</span><span><i class="icon-calendar"></i>2016-01-25</span>
                        </div>
                        <p><i class="icon-address"></i>高科西路3030弄2号0201室</p>
                    </li><li>
                        <div>
                            <span>商业贷款办理</span>
                            <span><i class="icon-person"></i>周琴</span><span><i class="icon-calendar"></i>2016-01-25</span>
                        </div>
                        <p><i class="icon-address"></i>高科西路3030弄2号0201室</p>
                    </li>
                </ul>
              <!--   <div class="loading" id="loading"><span class="pullUpIcon">&nbsp;</span><span class="pullUpLabel">正在加载数据...</span></div> -->
              <div class="loading" id="loading">
	                <div class="spinner" style="float:left;">
					  <div class="spinner-container container1">
					    <div class="circle1"></div>
					    <div class="circle2"></div>
					    <div class="circle3"></div>
					    <div class="circle4"></div>
					  </div>
					  <div class="spinner-container container2">
					    <div class="circle1"></div>
					    <div class="circle2"></div>
					    <div class="circle3"></div>
					    <div class="circle4"></div>
					  </div>
					  <div class="spinner-container container3">
					    <div class="circle1"></div>
					    <div class="circle2"></div>
					    <div class="circle3"></div>
					    <div class="circle4"></div>
					  </div>
					</div>
					<span class="" style="float:left;">正在加载数据...</span>
				</div>
    			<div class="loading" id="nomoreresults">没有更多数据</div>
        </article>

	<content tag="local_script"> <!-- Peity --> 
		<script type="text/javascript" src="<c:url value='/js/plugins/scrollpagination/scrollpagination.js' />"></script> 
	   <script
		src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> <!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> <!-- Custom and plugin javascript -->
	<script
		src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script> <script
		src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> <script
		src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
		<!-- iCheck -->
		<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
		<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/mobilejs/task/m_mytask_list.js' />"></script> 
	 	<!--ace-template demo-->        
         <script id="tpl" type="text/html">
            {{if rows.length>0}}
				{{ each rows as item }}
                   <a href="/trade-web/task/mobile/{{item.PART_CODE}}?&taskId={{item.ID}}&caseCode={{item.CASE_CODE}}&instCode={{item.INST_CODE}}">
					<li class="liHref">
                        <div>
                            <span>{{item.NAME}}</span>
                            <span><i class="pic-person"></i>{{item.AGENT_NAME}}</span>
                            <span><i class="pic-calendar"></i>{{item.EST_PART_TIME}}</span>
                        </div>
                        <p><i class="pic-address"></i>{{item.PROPERTY_ADDR}}</p>
                    </li>
                   </a>
             	{{/each}}
			{{/if}}
         </script>
	    <script>
		    $(document).ready(function() {
		    	//back to top
		        $('.top').hide();
		        $(window).scroll(function(){
		            if($(window).scrollTop() > 100){
		                $('.top').fadeIn();
		            }else{
		                $('.top').fadeOut();
		            }
		        });
		        $('.top').click(function(){
		            $('html,body').animate({scrollTop:0},500);
		        });
		        
		        initScrollPaggination();
		        
		        $('.liHref').click(function(){
		        	
		        });
		    });
		    
		    var postData={queryId:"queryMobileTaskListItemList",rows:10,page:1};
		    function initScrollPaggination(){
		    	postData.propertyAddr = '';
		    	postData.caseCode = '';
		    	postData.page = 1;
		    	var inTextVal = $('#inTextVal').val();
		    	var hVal = $('#inTextVal').attr('hVal');
		    	if (inTextVal != null && inTextVal.trim() != "") {
		    		var inTextType = $('#inTextType').val();
		    		if (inTextType == '1') {
		    			postData.propertyAddr = inTextVal.trim();
		    		} else if (inTextType == '4') {
		    			postData.caseCode = inTextVal.trim();
		    		}
		    	}
		    	$('#content').empty();
		       	$('#content').scrollPagination({
		       		'contentPage': ctx+'/quickGrid/findPage',
		       		'contentData': postData, // these are the variables you can pass to the request, for example: children().size() to know which page you are
		       		'scrollTarget': $(window), // who gonna scroll? in this example, the full window
		       		'heightOffset': 10, // it gonna request when scroll is 10 pixels before the page ends
		       		'beforeLoad': function(){ // before load function, you can display a preloader div
		       			$('#loading').fadeIn();	
		       		},
		       		'dataType':'json',
		       		'afterLoad': function(elementsLoaded){ // after loading content, you can use this function to animate your new elements
		       			 
		       			$('#loading').fadeOut();
						if ($.isEmptyObject(elementsLoaded)
								|| elementsLoaded.length == 0) {
							$('#nomoreresults').show();
							setTimeout(nomoreresultsOut, 2000);
						} else {
							postData.page += 1;
						}
						$(elementsLoaded).fadeInWithDelay();

		       		},
		       		'render':function(data){
		       			var html=template('tpl', data); 
		       			return html;
		       		}
		       	});
		   	}
		    function nomoreresultsOut() {
				$('#nomoreresults').fadeOut()
			}
		    
		    $("#searchButton").click(function() {
				initScrollPaggination();
			});
	    
	    </script>
	    
	</content>
</body>
</html>
