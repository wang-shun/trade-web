<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">


<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<style type="text/css">
.form-group label {
	text-align: right;
}
.form-control {
	margin-bottom: 5px;
	height:32px;
}
.col-sm-10{
	height:37px;
}
.col-md-2{
	width:12%
}

</style>
</head>

<body>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>考勤</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">事由<span class="star">*</span>：</label>
							<div class="col-sm-9" style="line-height:32px">
							<aist:dict id="attendReason" name="attendReason"
												clazz="form-control m-b" display="select" dictType="yu_attend_reason"
												defaultvalue="" />
							</div>
						</div>
						<div class="form-group" id="otherReason" style="display:none">
							<label class="col-sm-2 control-label">其他事由<span class="star">*</span>：</label>
							<div class="col-sm-9" style="line-height:32px">
								<input type="text" name="reason"
									id="reason" placeholder=""
									class="form-control" >
									</div>
							
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">地址<span class="star">*</span>：</label>
							<div class="col-sm-9">
								<input type="text" name="addr"
									id="addr" placeholder="输入当前地址进行查询"
									class="form-control" >
							</div>
							
						</div>
						<div class="form-group">
							
							<div class="col-sm-11">
								<button id="searchButton" type="button" style="width:98%;" class="btn btn-primary pull-right">查询</button>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">选择地址<span class="star">*</span>：</label>
						
							<div class="col-sm-9">
								<div  id="addrList">
																			
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">位置信息<span class="star">*</span>：</label>
						
							<div class="col-sm-9">
									<input type="text" name="address"
									id="address" placeholder=""
									class="form-control" readonly>
							</div>
							<input type="hidden" name="longitude"
									id="longitude" placeholder=""
									class="form-control" >
							<input type="hidden" name="latitude"
									id="latitude" placeholder=""
									class="form-control" >
						</div>
						<div class="form-group">
							<div class="col-sm-11" >
								<button id="attendanceButton" type="button" style="width:98%;" class="btn btn-primary pull-right">考  勤</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>

    <script>
    var ctx = "${ctx}";
    var lng = null;
    var lat = null;
    
    var position_option = {
            enableHighAccuracy: true,
            maximumAge: 30000,
            timeout: 20000
        };
    getLocation();
    function getLocation(){
        if (window.navigator.geolocation){
            window.navigator.geolocation.getCurrentPosition(showPosition,function(){/*alert("位置信息获取失败，请前往地图设置！");*/},position_option);
       //获取不稳定，可以设置超时时间
        }else{
            main.alert_warning("Geolocation is not supported by this browser.");
        }
    }
    function showPosition(position){
    	lng = position.coords.longitude;
    	lat = position.coords.latitude;
    } 
  
    $(function(){
   
    	$("#attendReason").change(function(){
    		if($("#attendReason").val()=="20"){
    			$("#otherReason").css("display","block");
    		}else{
    			$("#otherReason").css("display","none");
    		}
    	});
    	
    	$("#attendanceButton").click(function(){
    		if($("#attendReason").val()==""){
    			alert("请选择事由！");
    			return ;
    		}else if($("#attendReason").val()=="20" && $("#reason").val()==""){
    			alert("请输入事由！");
    			return;
    		}else if($("#address").val()==""){
    			alert("位置不能为空！");
    			return;
    		}
    		var reason = "";
    		if($("#attendReason").val()=="20"){
    			reason = $("#reason").val();
    		}else{
    			reason = $("#attendReason :selected").text();
    		}
    		
        	$.ajax({
        		url:ctx+"/attendCheck/saveAttendance",
        		method:"post",
        		dataType:"json",
        		data:{reason:reason,address:$("#address").val(),longitude:$("#longitude").val(),latitude:$("#latitude").val()},
        		success:function(data){
        		
        			alert(data.message);
        			if(data.success){
        				location.href=ctx+'/attendCheck/mobile/attendList';
        			}
        		}
        	});
    	});
    
    	$("#searchButton").click(function(){

    		 if($("#addr").val() == ""){
    			alert("请输入查询地址！");
    			return;
    		} 

         	$.ajax({
        		url:"http://api.map.baidu.com/place/v2/search",
        		method:"post",
        		dataType:"jsonp",
        		data:{ak:"0Bc5e24bf7f0dbaec526294346eaa87b",location:lat+","+lng,output:"json",query:$("#addr").val(),scope:"1",page_size:10,page_num:0,radius:1000},
        		success:function(data){
        			if(data!=null && data.results.length >0){
        				var html = "";
        				for(var i=0;i<data.results.length;i++){
        			
        					html += "<div style='line-height:24px'>"+data.results[i].name+"("+data.results[i].address+")"+"</div>";
        				}
        				$("#addrList").html(html);
        				$("#addrList div").each(function(){
        					$(this).click(function(){
        						$("#address").val($.trim($(this).html()));
        				    	$("#longitude").val(lng);
        				    	$("#latitude").val(lat);
        					});
        				});
        			} 
        		}
        	}); 
    	});
    });
 
    </script>
    
    </content>
</body>
</html>
