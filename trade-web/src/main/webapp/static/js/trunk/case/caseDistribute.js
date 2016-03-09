/**
 * 待分配案件
 * wanggh
 */
$(document).ready(function() {
	// Examle data for jqGrid
	// Configuration for jqGrid Example 1
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	var queryUserId = $("#queryUserId").val();
	var queryOrgId = $("#queryOrgId").val();
	url = ctx + url;
	//jqGrid 初始化
	$("#table_list_1").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 600,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 20,
		/*   rowList: [10, 20, 30], */
		colNames : [ '案件编号', '案件编号', '产证地址', '经纪人', '组别', '区经/区总', '案件状态', '派单时间' ],
		colModel : [ {
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
		postData : {
			queryId : "queryCastListItemListUnDistribute",
			argu_queryuserid :queryUserId,
			argu_queryorgid :queryOrgId
		}

	});

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list_1').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

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
	var url = "/case/getOrgTeamList";
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
		addHtml += '<p>联系电话：'+n.mobile+'</p>';
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
	var inHtml = '';
	inHtml+='<div class="form-group"><label class="col-lg-3 control-label">';
	inHtml+= '请选择组别：';
	inHtml+='</label><div class="col-lg-9" style="text-align:left; margin-top:-10px;" >';
	$.each(data,function(i, n){
		inHtml+='<div class="checkbox i-checks"><label>';
		inHtml+='<input type="radio" name="teamRadio" value="'+n.id+'"/>  '+n.orgName+' </label></div>';
	})
	inHtml+='</div></div>';
	$("#team-form").html(inHtml);
	$('#team-modal-form').modal("show");
}
/**
 * 案件分配
 * @param index
 */
function distributeCase(index){
	var userName =$("#userName_"+index).val();
	if(confirm("您是否确认分配给"+userName+"?")){
		var ids=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		var userId =$("#user_"+index).val();
		
		var url = "/case/bindCaseDist";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var params='&userId='+userId+'&caseCodes='+ids;
		
		$.ajax({
			cache : false,
			async:true,
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
/**
 * 案件转组
 * @param index
 */
function changeCaseTeam(){
	var orgName =$('input[name="teamRadio"]:checked').parent().text();
	if(confirm("您是否确认分配给"+orgName+"?")){

    	var orgId =$('input[name="teamRadio"]:checked').val();
		var url = "/case/bindCaseTeam";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCodes=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		var params='&orgId='+orgId+'&caseCodes='+caseCodes;
		
		$.ajax({
			cache : false,
			async:true,
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