/*加载待分配案件*/
$(document).ready(function(){
	reloadGrid(1);
});

/*条件查询*/
$('#searchButton').click(function(){
	reloadGrid(1);
});

/*获取查询参数*/
function getQueryParams(page){
	
	if(!page){
		page=1;
	}
	
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
		caseAddr=null;
	}
	
	var params = {
		argu_queryuserid : queryUserId,
		argu_queryorgid : queryOrgId,
		search_ctmNo : ctmNo,
		search_caseNo : caseNo,
		search_caseAddr : caseAddr,
		queryId : 'queryCastListItemListUnDistribute',
		rows : 12,
	    page : page
	};
	
	return params;	
}

/*获取未分配案件列表*/
function reloadGrid(page) {

	var data = getQueryParams(page);
	var ctx = $("#ctx").val();
	
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
        	
        	$.unblockUI();   	 
        	var myCaseList = template('template_myCaseList' , data);
        	$("#myCaseList").empty();
        	$("#myCaseList").html(myCaseList);
			// 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}

/*分页栏*/
function initpage(totalCount,pageSize,currentPage,records) {
	
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1){
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalP').text(records);
	
	
	$("#pageBar").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			reloadGrid(page);
	    }
	});
}

/*全选框绑定全选/全不选属性*/
$('#checkAllNot').click(function(){
	var my_checkboxes = $('input[name="my_checkbox"]');
	if($(this).prop('checked')){
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',true);
		}
		$("#caseDistributeButton").attr("disabled", false);
		$("#caseChangeTeamButton").attr("disabled", false);
	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
		}
		$("#caseDistributeButton").attr("disabled", true);
		$("#caseChangeTeamButton").attr("disabled", true);
	}
});

/*单选框*/
function _checkbox(){
	var my_checkboxes = $('input[name="my_checkbox"]');
	var flag =false;
	var count=0;
	$.each(my_checkboxes, function(j, item){
		if($('input[name="my_checkbox"]:eq('+j+')').prop('checked')){
			flag=true;
			++count;
		}
	});
	if(flag){
		$("#caseDistributeButton").attr("disabled", false);
		$("#caseChangeTeamButton").attr("disabled", false);
		if(count==my_checkboxes.length){
			$('#checkAllNot').prop('checked', true);
		}else if(count<my_checkboxes.length){
			$('#checkAllNot').prop('checked', false);
		}
	}else{
		$("#caseDistributeButton").attr("disabled", true);
		$("#caseChangeTeamButton").attr("disabled", true);
		$('#checkAllNot').prop('checked', false);
	}
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
		var checkeds=$('input[name="my_checkbox"]:checked');
		var ids = new Array();
		$.each(checkeds, function(i, items){
			var id = $('input[name="my_checkbox"]:checked:eq('+i+')').next('input[name="case_code"]').val();
			ids.push(id);
		});
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
		            $("#myCaseList").html("");
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
									reloadGrid(1);
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
		var checkeds=$('input[name="my_checkbox"]:checked');
		$.each(checkeds, function(i, items){
			var $myCheckbox = $('input[name="my_checkbox"]:checked:eq('+i+')');
			var $caseCode = $myCheckbox.next('input[name="case_code"]');
			var $grpCode  = $caseCode.next('input[name="yu_team_code"]');
			var $leadingProcessId  = $grpCode.next('input[name="leading_process_id"]');
			
			var toCaseInfo = {
				caseCode : $caseCode.val(),
				grpCode : $grpCode.val(),
				requireProcessorId:$leadingProcessId.val()
			}
			caseInfoList.push(toCaseInfo);
			
		});
		//var ids = $("#table_list_1").jqGrid('getGridParam',"selarrrow");
		//var ids = jQuery("#table_list_1").jqGrid('getDataIDs');
//		for (var i = 0; i < ids.length; i++) {
//		   var row = $("#table_list_1").getRowData(ids[i]);
//		   var toCaseInfo = {
//			   caseCode	: row.CASE_CODE  ,
//			   grpCode :  row.YU_TEAM_CODE
//		   }
//		   caseInfoList.push(toCaseInfo);
//		}
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
            	$("#myCaseList").html("");
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
					reloadGrid(1);
				}else{
					alert(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
	}
}