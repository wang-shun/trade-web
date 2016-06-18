/**
 * 待分配案件
 * wanggh
 */
$(document).ready(function() {
	
	var queryUserId = $("#queryUserId").val();
	var queryOrgId = $("#queryOrgId").val();
	var postData={};
	postData.queryId='queryCastListItemListUnDistribute';
	postData.argu_queryuserid=queryUserId;
	postData.argu_queryorgid=queryOrgId;
	
	_query_case(postData);
	
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list_1').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

/*条件查询*/
function _query_case_selective(){
	var queryUserId = $("#queryUserId").val();
	var queryOrgId = $("#queryOrgId").val();
	var ctmNo = $('#ctmNo').val();
	if(ctmNo==""){
		ctmNo=null;
	}
	var caseNo = $('#caseNo').val();
	if(caseNo==""){
		caseNo=null;
	}
	var caseAddr = $('#caseAddr').val();
	if(caseAddr==""){
		caseAddr==null;
	}
	
	var post_data = {};
	post_data.argu_queryuserid=queryUserId;
	post_data.argu_queryorgid=queryOrgId;
	post_data.search_ctmNo=ctmNo;
	post_data.search_caseNo=caseNo;
	post_data.search_caseAddr=caseAddr;
	postData.queryId='queryCastListItemListUnDistribute';
	
	 _query_case(post_data);
}

/*案件查询*/
function _query_case(post_data){
	// Examle data for jqGrid
	// Configuration for jqGrid Example 1
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	//jqGrid 初始化
	$("#table_list_1").jqGrid({
		url : url,
		mtype : 'GET',
		page : 1,
		datatype : "json",
		height : 600,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 20,
		/*   rowList: [10, 20, 30], */
		colNames : [ 'YU_TEAM_CODE','案件编号', '案件编号', '产证地址', '经纪人', '组别', '区经/区总', '案件状态', '派单时间','GRP_CODE' ],
		colModel : [ {
			name : 'YU_TEAM_CODE',
			index : 'YU_TEAM_CODE',
			align : "center",
			width : 0,
			resizable : false,
			hidden : true
		},{
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			width : 60
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 230
		}, {
			name : 'AGENT_NAME',
			index : 'AGENT_NAME',
			width : 30
		}, {
			name : 'ORG_NAME',
			index : 'ORG_NAME',
			width : 80
		}, {
			name : 'LEADER',
			index : 'LEADER',
			width : 30
		}, {
			name : 'STATUS',
			index : 'STATUS',
			width : 30
		}, {
			name : 'CREATE_TIME',
			index : 'CREATE_TIME',
			width : 40
		},
		{
			name : 'AGENT_ORG_CODE',
			index : 'AGENT_ORG_CODE',
			width : 0,
			hidden : true
		}
		],
		multiselect: true,
		pager : "#pager_list_1",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		onSelectRow : function(rowid,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		    $("#caseChangeTeamButton").attr("disabled", false);
    		}else{
	    		var ids=$("#table_list_1").jqGrid("getGridParam","selarrrow");
	    		if(ids.length==0){
	        		$("#caseDistributeButton").attr("disabled", true);
	        		$("#caseChangeTeamButton").attr("disabled", true);
	    		}
    		}
		},
		onSelectAll :function(aRowids,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		    $("#caseChangeTeamButton").attr("disabled", false);
    		}else{
	        	$("#caseDistributeButton").attr("disabled", true);
	        	$("#caseChangeTeamButton").attr("disabled", true);
    		}
		},
		postData : post_data
	});
}


/**
 * 案件分配初始化
 */
function caseDistribute(){
	var url = "/case/getUserOrgCpUserList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
	    data : "", 
		success : function(data) {
			showModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}

/**
 * 案件转组初始化
 */
function caseChangeTeam(){
	//var url = "/case/getOrgTeamList";
	//var url = "/setting/getYuCuiTeamList";
	var url = "/case/getAllTeamList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
	    data : "", 
		success : function(data) {
			showTeamModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}
function imgLoad(img){
  	 img.parentNode.style.backgroundImage="url("+img.src+")";
}
/**
 * 选择交易顾问modal
 * @param data
 */
function showModal(data){
	var addHtml = '';
	var ctx = $("#ctx").val();
	$.each(data,function(i, n){
		addHtml += '<div class="col-lg-4"><div class="contact-box">';
		addHtml += '<a href="javascript:distributeCase('+i+')">';
		addHtml += '<div class="col-sm-4"><div class="text-center">';
		
		addHtml+='<span class="userHead">';
		if(n.imgUrl!=null){
			addHtml += '<img onload="javascript:imgLoad(this)" alt="image" class="himg" src="'+n.imgUrl+'">';
		}
		addHtml+='</span>';
		
		addHtml += '<div class="m-t-xs font-bold">交易顾问</div></div></div>';
		addHtml += '<div class="col-sm-8">';
		addHtml += '<input id="user_'+i+'" type="hidden" value="'+n.id+'">';
		addHtml += '<input id="userName_'+i+'" type="hidden" value="'+n.realName+'">';
		addHtml += '<h3><strong>'+n.realName+'</strong></h3>';
		addHtml += '<input id="mobile_'+i+'" type="hidden" value="联系电话：'+n.mobile+'">'+'联系电话：'+n.mobile;
		addHtml += '<p>当前单数：'+n.userCaseCount+'</p>';
		addHtml += '<p>本月接单：'+n.userCaseMonthCount+'</p>';
		addHtml += '<p>未过户单：'+n.userCaseUnTransCount+'</p>';
		addHtml += '</div><div class="clearfix"></div></a>';
		addHtml += '</div></div>';
	})
	$("#modal-data-show").html(addHtml);

    $('.contact-box').each(function() {
        animationHover(this, 'pulse');
    });
	$('#modal-form').modal("show");
}
/**
 * 选择组别modal
 * @param data
 */
function showTeamModal(data){
/*	var inHtml = '';
	inHtml+='<div class="form-group"><label class="col-lg-3 control-label">';
	inHtml+= '请选择组别：';
	inHtml+='</label><div class="col-lg-9" style="text-align:left; margin-top:-10px;" >';
	$.each(data,function(i, n){
		inHtml+='<div class="checkbox i-checks"><label>';
		inHtml+='<input type="radio" name="teamRadio" value="'+n.id+'"/>  '+n.orgName+' </label></div>';
	})
	inHtml+='</div></div>';
	$("#team-form").html(inHtml);*/
	 var d = {
		data : data	 
	 }
	 var fontTeam = template('yuCuiFontTeamList', d); 
     $("#fontTeam").empty();
     $("#fontTeam").html(fontTeam);
	 $('#team-modal-form').modal("show");
}
/**
 * 案件分配
 * @param index
 */
function distributeCase(index){
		var userName =$("#userName_"+index).val();
		var mobile=$("#mobile_"+index).val();
		var ids=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		var userId =$("#user_"+index).val();
		
		var url = "/case/isTransferOtherDistrict";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var params='&userId='+userId+'&caseCodes='+ids;
		
		$.ajax({	
			cache : false,
			async: false,
			type : "POST",
			url : url,
			dataType : "json",
			timeout: 10000,
		    data : params, 
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },  
            complete: function() {  
                $.unblockUI();   
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
            
			success : function(data) {
				 var confrimMsg = '';
				 if(data.content == false){
					 confrimMsg = "您是否确认分配给"+userName+"?"+"业务员"+mobile;
				 } else {
					 confrimMsg = "案件所属区域与主办或合作对象不匹配,您是否确认分配给"+userName+"?";
				 }
				 if(confirm(confrimMsg)){
					var url = "/case/bindCaseDist";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var params='&userId='+userId+'&caseCodes='+ids;
					 $.ajax({
							cache : false,
							async: true,
							type : "POST",
							url : url,
							dataType : "json",
							timeout: 10000,
						    data : params, 
						    beforeSend:function(){  
								$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
								$(".blockOverlay").css({'z-index':'9998'});
				            },  
				          complete: function() {  
				                $.unblockUI();   
				                if(status=='timeout'){//超时,status还有success,error等值的情况
					          	  Modal.alert(
								  {
								    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
								  });
						  		 $(".btn-primary").one("click",function(){
						  				parent.$.fancybox.close();
						  			});	 
						                }
						            } , 
				            
							success : function(data) {
								if(data.success){
									alert("分配成功");
									$('#modal-form').modal("hide");
									//jqGrid reload
									$("#table_list_1").trigger('reloadGrid');
								}else{
									alert(data.message);
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
							}
						});
				 }
			}
		}); 
}
/**
 * 案件转组
 * @param index
 */
function changeCaseTeam(){
	//var orgName =$('input[name="teamRadio"]:checked').parent().text();
	var orgName =$('select[name="yuTeamCode"]').find("option:selected").text();
	if(confirm("您是否确认分配给"+orgName+"?")){

    	//var orgId =$('input[name="teamRadio"]:checked').val();
		var orgId =$('select[name="yuTeamCode"]').val();
		var url = "/case/bindCaseTeam";
		var ctx = $("#ctx").val();
		url = ctx + url;
		//var caseCodes=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		//var params='&orgId='+orgId+'&caseCodes='+caseCodes;
		
		var caseInfoList = new Array();
		var ids = $("#table_list_1").jqGrid('getGridParam',"selarrrow");
		//var ids = jQuery("#table_list_1").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
		   var row = $("#table_list_1").getRowData(ids[i]);
		   var toCaseInfo = {
			   caseCode	: row.CASE_CODE  ,
			   grpCode :  row.YU_TEAM_CODE
		   }
		   caseInfoList.push(toCaseInfo);
		}
		var teamTransferVO = {
		   caseInfoList	: caseInfoList,
		   orgId : orgId
		}
		teamTransferVO = $.toJSON(teamTransferVO);
		$.ajax({
			cache : false,
			async:true,
			type : "POST",
			url : url,
			dataType : "json",
			contentType: "application/json; charset=utf-8" ,
			timeout: 10000,
		    data : teamTransferVO, 
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },  
            complete: function() {  
                $.unblockUI();   
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
			success : function(data) {
				if(data.success){
					alert("分配成功");
					$('#team-modal-form').modal("hide");
					//jqGrid reload
					$("#table_list_1").trigger('reloadGrid');
				}else{
					alert(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
	}
}