/*加载待分配案件*/
$(document).ready(function(){
	/*加载排序查询组件*/
	aist.sortWrapper({
		reloadGrid : searchMethod
	});
	
	var data = getQueryParams(1);
    aist.wrap(data);
	//添加排序------------
	reloadGrid(data);
});

/*条件查询*/
$('#searchButton').click(function(){
    searchMethod(1)
});
//search
function searchMethod(page){
	$("#caseDistributeButton").attr("disabled", true);
	$("#caseChangeTeamButton").attr("disabled", true);
	var data = getQueryParams(page);
    aist.wrap(data);
	reloadGrid(data);
}

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
	var caseNo = $('#caseNo').val().replace(/(^\s*)|(\s*$)/g, "");
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
		rows : 10,
	    page : page
	};
	return params;	
}

/*获取未分配案件列表*/
function reloadGrid(data) {

	//var data = getQueryParams(page);
	var ctx = $("#ctx").val();
	//添加排序-----
   // aist.wrap(data);
	
	var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data.sidx=sortcolumn;
	data.sord=sortgz;
	//添加排序----------
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
        	console.log(data);
        	
        	$.unblockUI();   	 
        	var myCaseList = template('template_myCaseList' , data);
        	$("#myCaseList").empty();
        	$("#myCaseList").html(myCaseList);
        	
			// 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
            $('.demo-left').poshytip({
    			className: 'tip-twitter',
    			showTimeout: 1,
    			alignTo: 'target',
    			alignX: 'left',
    			alignY: 'center',
    			offsetX: 8,
    			offsetY: 5,
    		});

    		//top
    		$('.demo-top').poshytip({
    			className: 'tip-twitter',
    			showTimeout: 1,
    			alignTo: 'target',
    			alignX: 'center',
    			alignY: 'top',
    			offsetX: 8,
    			offsetY: 5,
    		});
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
			//reloadGrid(page);
			searchMethod(page);
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
 * 案件是否有可合流
 */
function getMergeCount(){
	var str ="";
	var type = false;
	var cuNus = $('input[name="my_checkbox"]:checked');
	var cuNusLength = cuNus.length;
	var pkId;
	var caseCode;
	var propertyAddr;
	var agentName;
	var agentPhone;
	var agentOrgName;
	var seller;
	var buyer;
	var propertyCode;
	var inputType;
	if(null != cuNus )
	for ( var i = 0; i < cuNus.length; i++) {
		var cu=$(cuNus[i]).attr("cuNu");
		var caseCode_=$(cuNus[i]).attr("caseCode");
		if(parseInt(cu)>0){ str += "\n"+caseCode_+"\n"; type=true;}
		if(cuNusLength == 1){
			pkId 		= $(cuNus[i]).attr("pkId");
			caseCode	= $(cuNus[i]).attr("caseCode");
			propertyAddr= $(cuNus[i]).attr("propertyAddr");
			agentName   = $(cuNus[i]).attr("agentName");
			agentPhone  = $(cuNus[i]).attr("agentPhone");
			agentOrgName= $(cuNus[i]).attr("agentOrgName");
			seller      = $(cuNus[i]).attr("seller");
			buyer       = $(cuNus[i]).attr("buyer");
			propertyCode= $(cuNus[i]).attr("propertyCode");
			inputType	= $(cuNus[i]).attr("caseOrigin");
		}
	}
	if(cuNusLength==1 && type){
		showGlDiv('backCase1',pkId,caseCode,propertyAddr,agentName,agentPhone,agentOrgName,seller,buyer,propertyCode,inputType,'aseDistributeType()',true);
		return true;
	}
	if(cuNus.length==1 && !type){
		caseDistributeType();
		return true;
	}
	if(cuNus.length>1 && "" != str){
		window.wxc.alert("批量分配案件中有如下案件可以合流(请先合流案件)："+str+"");
		return true;
	}else{
		caseDistributeType();
	}
	
	return true;
	
}
/**
 * 案件是否有可合流
 */
function glCaseList(){
	caseDistribute();
}
/**
 * 案件是否有可合流
 */
function getMergeInfoList(){
	getMergeCount();
}
/**
 * 案件分配初始化
 */
function caseDistribute(){
	getMergeCount();

}
/**
 * 案件分配初始化
 */
function caseDistributeType(){
	
	var data = {};
	if($('input[name="my_checkbox"]:checked').length==1){data.caseCode=$('input[name="my_checkbox"]:checked').val();};
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
	    data : data, 
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
		if(n.type == "INPUT"){
			addHtml += '<div class="feed-activity-list row" style="margin-top: 0;  padding-top: 0; ">                                                       ';
			addHtml += '	<div class="col-md-4">                                                                 ';
			addHtml += '		<p class="title">交易单创建人</p>                                                  ';
			addHtml += '		<div class="feed-element  feed-element-grey">                                      ';
			addHtml += '			<span class="pull-left">                                                       ';
			addHtml += '				<img alt="image" class="img-circle" src="'+n.imgUrl+'"> ';
			addHtml += '				<div class="m-t-xs font-bold">'+n.jobName+'</div>';
			addHtml += '			</span>                                                                        ';
			addHtml += '			<div class="media-body ">                                                      ';
			addHtml += '				<strong>'+n.realName+'</strong>                                     ';
			addHtml += '				<br>                                                                       ';
			addHtml += '				<small class="text-muted">'+'联系电话：'+n.mobile+'</small>                            ';
			addHtml += '				<br>                                                                       ';
			addHtml += '				<small class="text-muted">'+n.orgName+'</small>                            ';
			addHtml += '				<p class="small"></p>                                            ';
			addHtml += '			</div>                                                                         ';
			addHtml += '		</div>                                                                             ';
			addHtml += '	</div>                                                                                 ';
			addHtml += '</div>                                                                                     ';
		}
		if(n.type == "CTM" || n.type == "MERGE"){
			addHtml += '<div class="feed-activity-list row" style="margin-top: 0;  padding-top: 0; ">                                                       ';
			addHtml += '	<div class="col-md-4">                                                                 ';
			addHtml += '		<p class="title">经纪人意向顾问</p>                                                ';
			addHtml += '		<div class="feed-element  feed-element-grey">                                      ';
			addHtml += '			<span class="pull-left">                                                       ';
			addHtml += '				<img alt="image" class="img-circle" src="'+n.imgUrl+'"> ';
			addHtml += '				<div class="m-t-xs font-bold">'+n.jobName+'</div>';
			addHtml += '			</span>                                                                        ';
			addHtml += '			<div class="media-body ">                                                      ';
			addHtml += '				<strong>'+n.realName+'</strong>                                       ';
			addHtml += '				<br>                                                                       ';
			addHtml += '				<small class="text-muted">'+'联系电话：'+n.mobile+'</small>                            ';
			addHtml += '				<br>                                                                       ';
			addHtml += '				<small class="text-muted">'+n.orgName+'</small>                            ';
			addHtml += '				<p class="small"></p>                                     ';
			addHtml += '			</div>                                                                         ';
			addHtml += '		</div>                                                                             ';
			addHtml += '	</div>                                                                                 ';
			addHtml += '</div>                                                                                     ';
			
		}
		if(n.type == "ALL"){
			addHtml += '<div class="mt30" >';
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
			addHtml += '</div></div></div>';
		}
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
				 
				 window.wxc.confirm(confrimMsg,{"wxcOk":function(){
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
									window.wxc.success("分配成功",{"wxcOk":function(){
										$('#modal-form').modal("hide");
										//jqGrid reload
										/*
										reloadGrid(1);*/
										$("#checkAllNot").attr('checked',false);
										searchMethod(1);
									}});
								}else{
									window.wxc.error(data.message);
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
							}
						});
				 }});
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
	
	window.wxc.confirm("您是否确认分配给"+orgName+"?",{"wxcOk":function(){
    	//var orgId =$('input[name="teamRadio"]:checked').val();
		var orgId =$('select[name="yuTeamCode"]').val();
		var url = "/case/bindCaseTeam";
		var ctx = $("#ctx").val();
		url = ctx + url;
		//var caseCodes=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		//var params='&orgId='+orgId+'&caseCodes='+caseCodes;
		
		var caseInfoList = new Array();
		var checkeds=$('input[name="my_checkbox"]:checked');
		$.each(checkeds, function(i, item){
			var $td = $(item).parent();
			var toCaseInfo = {
					caseCode :$('input[name="case_code"]',$td).val(),
					grpCode :$('input[name="yu_team_code"]',$td).val(),
					requireProcessorId:$('input[name="leading_process_id"]',$td).val()
				}
			caseInfoList.push(toCaseInfo);
		});

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
					window.wxc.success("分配成功",{"":function(){
						$('#team-modal-form').modal("hide");
						$("#checkAllNot").attr('checked',false);
						searchMethod(1);
					}});
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
	}});
}



function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

function createTimeSort(){
	if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#createTimeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

/***
 *  回调刷新的方法
 */
function callback() {
	setTimeout('searchMethod()',1000); 
}





























