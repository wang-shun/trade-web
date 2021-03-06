$(function(){
	//页面初始化
	reloadGridList();
	//条件查询
	$("#searchButton").click(function(){
		reloadGridList();
	});
	
	aist.sortWrapper({
		reloadGrid : reloadGridList
	});
});

function reloadGridList(){
	var data = getParams();
	data.pagination = false; 
	$("#managerRewardList").reloadGrid({
    	ctx : ctx,
		queryId : "managerRewardList",
	    templeteId : 'template_managerRewardList',
	    data : data,
	    wrapperData : data
    });
}
//新增高层信息
$("#save").click(function(){
	var userNm = $("#managerForm #userName").val();
	var srvFee = $.trim($("#managerForm #srvFee").val());
	if(userNm==null || userNm==''){
		window.wxc.alert("姓名不能为空！");
		return;
	}
	if(srvFee==null || srvFee==''){
		window.wxc.alert("基础奖金不能为空！");
		return;
	}
	if(!/^[1-9][0-9]*$/.test(srvFee)){  
		window.wxc.alert("请输入正确的基础奖金!");  
		return;
    }  
	
	var jsonData = $("#managerForm").serializeArray();
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+"/award/saveManagerReward",
		dataType : "json",
		data : jsonData,
		beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
         },
		success : function(data) {
			if(data.success){
				window.wxc.success("保存成功",{"wxcOk": function(){
					location.reload();
				}});
			}else{
				window.wxc.error(data.message);
			}
		},
		complete: function() { 
			 $.unblockUI(); 
		},
		error : function(errors) {
			console.log(errors);
			window.wxc.error("数据保存出错");
			 $.unblockUI();
		}
	});
	
});

//删除高层信息
function delManagerInfo(pkid){
	window.wxc.confirm("是否确认删除！",{"wxcOk":function(){
		$.ajax({
			cache : false,
			async : false,//false同步，true异步
			type : "POST",
			url : ctx+"/award/delManagerReward",
			dataType : "json",
			data : { 'pkid':pkid} ,
			beforeSend:function(){  
					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
					$(".blockOverlay").css({'z-index':'9998'});
	         },
			success : function(data) {
				if(data.success){
					window.wxc.success("删除成功",{"wxcOk": function(){
						location.reload();
					}});
				}else{
					window.wxc.error(data.message);
				}
			},
			complete: function() { 
				 $.unblockUI(); 
			},
			error : function(errors) {
				window.wxc.error("数据保存出错");
				 $.unblockUI();
			}
		});
	}});
}

//修改基础奖金信息
function modifyBaseInfo(PKID,JOB_NAME,SRV_ITEM_NAME,SRV_FEE,SRV_ITEM_CODE){
	$("#modifyBaseForm #pkid").val(PKID);
	$("#modifyBaseForm #jobName").html(JOB_NAME);
	$("#modifyBaseForm #srvFee").val(SRV_FEE);
	$("#modifyBaseForm #srvItemCode").html(SRV_ITEM_NAME);
	$("#modifyBaseForm #srvItemCodeByName").val(SRV_ITEM_CODE);
	$('#modifyBaseModal').modal('show');
}
//修改高层基础奖金信息
function modifyManagerInfo(PKID,USER_NAME,SRV_FEE,ORG_NAME,JOB_NAME){
	$("#pkid").val(PKID);
	$("#name").html(USER_NAME);
	$("#fee").val(SRV_FEE);
	$("#org").html(ORG_NAME);
	$("#job").html(JOB_NAME);
	$('#modifyModal').modal('show');
}
$("#modify").click(function(){
	var pkid = $("#pkid").val();
	var srvFee = $.trim($("#fee").val());
	if(srvFee==null || srvFee==''){
		window.wxc.alert("基础奖金不能为空！");
		return;
	}
	if(!/^[1-9][0-9]*$/.test(srvFee)){  
		window.wxc.alert("请输入正确的基础奖金!");  
		return;
    }  
	
	$.ajax({
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+"/award/modifyManagerReward",
		dataType : "json",
		data : {'pkid':pkid,'srvFee':srvFee},
		beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
         },
		success : function(data) {
			if(data.success){
				window.wxc.success("更新成功",{"wxcOk": function(){
					location.reload();
				}});
			}else{
				window.wxc.error(data.message);
			}
		},
		complete: function() { 
			 $.unblockUI(); 
		},
		error : function(errors) {
			window.wxc.error("数据保存出错");
			 $.unblockUI();
		}
	});
	
});

$("#modifyBase").click(function(){
	var pkid = $("#modifyBaseForm #pkid").val();
	var srvFee = $.trim($("#modifyBaseForm #srvFee").val());
	var srvItemCode = $("#modifyBaseForm #srvItemCodeByName").val();	
	var belongMonth = getBlongMonth();
	
	if(srvFee==null || srvFee==''){
		window.wxc.alert("基础奖金不能为空！");
		return;
	}
	if(!/^[1-9][0-9]*$/.test(srvFee)){  
		window.wxc.alert("请输入正确的基础奖金!");  
		return;
    }  
	$.ajax({
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+"/award/modifyBaseReward",
		dataType : "json",
		data : {'pkid':pkid,'srvFee':srvFee,'srvItemCode':srvItemCode,'belongMonth':belongMonth},
		beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
         },
		success : function(data) {
			if(data.success){
				window.wxc.success("更新成功",{"wxcOk": function(){
					location.reload();
				}});
			}else{
				window.wxc.error(data.message);
			}
		},
		complete: function() { 
			 $.unblockUI(); 
		},
		error : function(errors) {
			window.wxc.error("数据保存出错");
			 $.unblockUI();
		}
	});
	
});


function getParams() {
	//var belongMonth = getBlongMonth();//$("#belongMonth").val();
	var belongMonth = $.trim($("#belongMonth",window.parent.document).val());	
	var realName = $.trim($("#realName").val());
	var orgName = $.trim($("#organName").val());
	var data = {};
	
	var bmTime = getTimeInt(belongMonth);	
	
	data.belongMonth = bmTime;
	data.realName = realName;
	data.orgName = orgName;
	return data;
}
function changeStyle(){
	  if ($("#flag").hasClass('fa fa-sort-desc fa_down')){
		  $("#flag").removeClass('fa fa-sort-desc fa_down');
	     $("#flag").addClass('fa fa-sort-asc fa_up');
	  } else {
		 $("#flag").removeClass('fa fa-sort-asc fa_up');
		 $("#flag").addClass('fa fa-sort-desc fa_down');
	  }
}


function getTimeInt(belongMonthTime){	
	var standard = '2017-05';
	var dateTimeFlag = new Date(standard).getTime();

	var bmDate = new Date(belongMonthTime).getTime();

	if(bmDate < dateTimeFlag){		
		return standard;
	}
	return belongMonthTime;
	
}

//获取计件年月信息
function getBlongMonth(){
	var bm = "";	
	//方式一
	var belongMonth =  $.trim($("#belongMonth",window.parent.document).val());
    //方式二
	//var belongMonth1 = parent.document.getElementById("belongMonth").value;
    if(belongMonth =="" || belongMonth == null || belongMonth == undefined){
    	bm == null;
    }else{
    	bm = belongMonth + "-01";
    }
    return bm;
}