/*加载结算评估案件*/
$(document).ready(function(){
	aist.sortWrapper({
		reloadGrid : searchMethod
	});
	
	var data = getQueryParams(1);
	
    aist.wrap(data);
	//添加排序------------
	reloadGrid(data);
	
	$('#datepicker_0').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
	//获取评估公司
	getEvaFinOrg('finOrgId');
});

/**
 * 获取评估公司 格式化
 * @param finOrgId
 */
function getEvaFinOrg(finOrgId){
	var url = "/manage/queryEvaCompany";
	$.ajax({
		async: true,
		type:'POST',
		url:ctx+url,
		dataType:'json',
		success:function(data){
			var html = '<option value="" selected>请选择</option>';
			if(data != null){
				$.each(data,function(i,item){
					html += '<option value="'+item.pkid+'">'+item.finOrgName+'</option>';
				});
			}					
			$('#'+finOrgId).empty();
			$('#'+finOrgId).append(html);
		},
		error : function(errors) {
		}
	});
}

/*条件查询*/
$('#searchButton').click(function(){
	searchMethod(1)
});

//search
function searchMethod(page){
	var data = getQueryParams(page);
    aist.wrap(data);
	reloadGrid(data);
}

/*获取查询参数*/
function getQueryParams(page){
	
	if(!page){
		page=1;
	}
	//案件状态
	var status = $("#caseStatus  option:selected").val();
	//结算时间
	var settleTime = $("#dtBegin_0").val();
	//产证地址
	var propertyAddr = "";
	var caseCode =  "";
	var evalCode =  "";
	//产证地址,案件编号,评估单编号
	var inTextVal = $("#inTextVal").val();
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			caseCode = inTextVal.trim();
		} else if (inTextType == '1') {
			//产证地址
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			//评估单编号
			evalCode = inTextVal.trim();
		}
	}
	//评估公司
	var finOrgID = $('#finOrgId').val();
	var params = {
		search_status : status,
		search_propertyAddr : propertyAddr,
		search_caseCode : caseCode,
		search_settleTime : settleTime,
		search_evalCode : evalCode,
		search_finOrgID : finOrgID,
		queryId : 'evalAccount',
		rows : 10,
	    page : page
	};
	
	return params;	
}

/*获取评估待结算的案件列表*/
function reloadGrid(data) {

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
        	var myCaseEvalList = template('evalListTemp' , data);
        	$("#t_body_data_contents").empty();
        	$("#t_body_data_contents").html(myCaseEvalList);
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
		first:'<i class="icon-step-backward"></i>',
		prev:'<i class="icon-chevron-left"></i>',
		next:'<i class="icon-chevron-right"></i>',
		last:'<i class="icon-step-forward"></i>',
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
		$("#batEnd").attr("disabled", false);
	}else{
		for(var i=0; i<my_checkboxes.length; i++){
			$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
			
		}
		$("#batEnd").attr("disabled", true);
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
		$("#batEnd").attr("disabled", false);
		if(count==my_checkboxes.length){
			$('#checkAllNot').prop('checked', true);
		}else if(count<my_checkboxes.length){
			$('#checkAllNot').prop('checked', false);
		}
	}else{
		$("#batEnd").attr("disabled", true);
		$('#checkAllNot').prop('checked', false);
	}
}
/**
 * 导入EXCEL表格
 * @returns
 */

function exportToExcel() {
	//案件状态
	var status = $("#caseStatus  option:selected").val();
	//结算时间
	var settleTime = $("#dtBegin_0").val();
	//产证地址
	var propertyAddr = "";
	var caseCode =  "";
	var evalCode =  "";
	//产证地址,案件编号,评估单编号
	var inTextVal = $("#inTextVal").val();
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			caseCode = inTextVal.trim();
		} else if (inTextType == '1') {
			//产证地址
			propertyAddr = inTextVal.trim();
		} else if (inTextType == '2') {
			//评估单编号
			evalCode = inTextVal.trim();
		}
	}
	//评估公司
		var finOrgID = $('#finOrgId').val();
		var params = {};
		params.search_status = status;
		params.search_propertyAddr = propertyAddr;
		params.search_caseCode = caseCode;
		params.search_settleTime = settleTime;
		params.search_evalCode = evalCode;
		params.search_finOrgID = finOrgID;

		var displayColomn = new Array;
		displayColomn.push('caseCode');
		displayColomn.push('PROPERTY_ADDR');
		displayColomn.push('FIN_ORG_ID');
		displayColomn.push('APPLY_DATE');
		displayColomn.push('ISSUE_DATE');
		displayColomn.push('EVAL_REAL_CHARGES');
		displayColomn.push('EVA_PRICE');
		displayColomn.push('SETTLE_FEE');
		displayColomn.push('STATUS');
		displayColomn.push('SETTLE_TIME');
		
		var colomns = '&colomns=' + displayColomn;
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		var queryId = '&queryId=evalAccount';
		url = ctx + url + jQuery.param(params) + queryId  + colomns;
		$('#excelForm').attr('action', url);
		$('#excelForm').method="post" ;
		$('#excelForm').submit();  
}


/**
 * 批量结算
 */
$('#batEnd').click(function(){
	var ids = new Array();
	var checkeds=$('input[name="my_checkbox"]:checked');
	$.each(checkeds, function(i, items){
		var $td = $(items).parent();
		var id = $('input[name="case_code"]',$td).val();
		ids.push(id);
	});
	var ctx = $("#ctx").val();
	
	window.wxc.confirm("确定批量结算吗？",{"wxcOk":function(){
		window.location.href = ctx + "/eval/settle/batEnd?caseCodes="+ids;
	}});
});


/**所搜条件的设定*/
function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();
//	if(inTextType=='4'){
//		initAutocomplete(ctx+"/labelVal/queryUserInfo");
//	}else if (inTextType=='3'){
//		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
//	}
}





/**
 * 批量
 */
/*
function distributeCase(index){
//	var userName =$("#userName_"+index).val();
//	var mobile=$("#mobile_"+index).val();
	var checkeds=$('input[name="my_checkbox"]:checked');
	var ids = new Array();
	$.each(checkeds, function(i, items){
		var id = $('input[name="my_checkbox"]:checked:eq('+i+')').next('input[name="case_code"]').val();
		ids.push(id);
	});
//	var userId =$("#user_"+index).val();
	
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
									reloadGrid(1);
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
}*/
/**
 * 案件转组初始化
 */
/*function caseChangeTeam(){
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
}*/
/**
 * 选择交易顾问modal
 * @param data
 */
/*function showModal(data){
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
}*/


/**
 * 案件分配
 * @param index
 */
/*function distributeCase(index){
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
										reloadGrid(1);
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
}*/
