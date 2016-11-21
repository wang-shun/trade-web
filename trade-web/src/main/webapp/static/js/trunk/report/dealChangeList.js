var ctx = $("#ctx").val();
$(function(){
	//页面初始化
	reloadGrid(1);
	
	aist.sortWrapper({
		reloadGrid : reloadGrid
	});
	
});

function reloadGrid(page){
	if(!page) {
		page = 1;
	}
	var data = getParams();
	/*$("#dealChangeList").reloadGrid({
    	ctx : ctx,
		queryId : "queryTradeChangedCaseList",
	    templeteId : 'template_dealChangeList',
	    data : data,
	    wrapperData : data
    });*/
	var visitRemark = $("#visitRemark").val();
	data.queryId = "queryTradeChangedCaseList";
	data.rows = 10;
	data.page = page;
	aist.wrap(data);
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
      	  data.visitRemark = visitRemark;
      	  var dealChangeList = template('template_dealChangeList' , data);
		  $("#editable tbody").empty();
		  $("#editable tbody").html(dealChangeList);
		  
		  // 显示分页 
          initpage(data.total,data.pagesize,data.page, data.records);
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
        
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
}

function initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalP').text(records);
	$(function(){
			//left
			$('.demo-left').poshytip({
				className: 'tip-twitter',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'left',
				alignY: 'center',
				offsetX: 8,
				offsetY: 5,
			});

			//right
			$('.demo-right').poshytip({
				className: 'tip-twitter',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'right',
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

			//bottom
			$('.demo-bottom').poshytip({
				className: 'tip-twitter',
				showTimeout: 1,
				alignTo: 'target',
				alignX: 'center',
				alignY: 'bottom',
				offsetX: 8,
				offsetY: 5,
			});
		});
	
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
//查询条件
function getParams() {
	var caseCode = $.trim($("input[name='caseCode']").val());
	var teamId = $("input[name='teamId']").attr("hVal");
	var visitRemark = $("#visitRemark").val();
	var changeId = $("input[name='changeId']").attr("hVal");
	var changeTimeStart = $.trim($("input[name='changeTimeStart']").val());
	if(changeTimeStart!=''){
		changeTimeStart+=" 00:00:00";
	}
	var changeTimeEnd = $.trim($("input[name='changeTimeEnd']").val());
	if(changeTimeEnd!=''){
		changeTimeEnd+=' 23:59:59';
	}
	var partCode = $("#partCode option:selected").val();
	var propertyAddr = $.trim($("#propertyAddr").val());
	
	var data = {};
	data.caseCode = caseCode;
	data.teamId = teamId;
	data.visitRemark = visitRemark;
	data.changeId = changeId;
	data.changeTimeStart = changeTimeStart;
	data.changeTimeEnd = changeTimeEnd;
	data.partCode = partCode;
	data.propertyAddr = propertyAddr;
	
	return data;
}
//选择变更人
function chooseChanger(id) {
	userSelect({
		startOrgId : 'ff8080814f459a78014f45a73d820006',//非营业部
		expandNodeId : 'ff8080814f459a78014f45a73d820006',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : '',
		callBack : dealChangeSelectUserBack
	});
}
//选择变更人回调函数
function dealChangeSelectUserBack(array){
	if (array && array.length > 0) {
		$("#changeId").val(array[0].username);
		$("#changeId").attr('hVal', array[0].userId);

	} else {
		$("#changeId").val("");
		$("#changeId").attr('hVal', "");
	}
}
//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array){
	if(array && array.length >0){
	    $("#teamId").val(array[0].name);
	    $("#teamId").attr('hVal', array[0].id);
	}else{
		 $("#teamId").val("");
		 $("#teamId").attr('hVal', '');
	}
}
//点击查询按钮
$("#searchBtn").click(function(){
	reloadGrid();
});
//点击处理按钮
function doDeal(caseCode,propertyAddr,changeNameAndMobile,teamName,sellerandphone,buyerandphone,batchId,fontName,fontMobile,agentName,agentMobile){
	$("#transplanHistory tr").remove();
	$.ajax({
		async: true,
        url:ctx+ "/transplan/queryTtsTransPlanHistorys" ,
        method: "post",
        dataType: "json",
        data: {batchId : batchId,caseCode : caseCode},
        success: function(data){
        	if(data.success){
        		var th='';
        		for(var i=0;i<data.content.length;i++){
        			var oldEstPartTime = data.content[i].oldEstPartTime==null?'':data.content[i].oldEstPartTime;
        			var newEstPartTime = data.content[i].newEstPartTime==null?'':data.content[i].newEstPartTime;
        			th+="<tr><td><p><i class='sign_blue'>"+data.content[i].partCode+"</i></p></td>" +
        					"<td><p class='smll_sign'><i class='sign_normal'>原</i>"+ oldEstPartTime +"</p>" +
        							"<p class='smll_sign'><i class='sign_normal'>新</i>"+newEstPartTime+"</p></td>" +
        									"<td><span>"+data.content[i].changeReason+"</span></td></tr>";
        		}
        		$("#transplanHistory").append(th);
        	}
        }
    });
	$("#returnVisitHistory tr").remove();
	$.ajax({
		async: true,
        url:ctx+ "/transplan/queryReturnVisitHistorys" ,
        method: "post",
        dataType: "json",
        data: {batchId : batchId},
        success: function(data){
        	console.log(data);
        	if(data.success){
        		var th='';
        		for(var i=0;i<data.content.length;i++){
        			var vit = data.content[i].visitRemark;
        			var visitRemark = vit=='0'?'异常': vit=='1'?'正常': vit=='2'?'下次处理':'';
        			th+="<tr><td>"+data.content[i].createTime+"</td>" +
        					"<td>"+ data.content[i].content +"</td>" +
        					"<td>"+visitRemark+"</td></tr>";
        		}
        		$("#returnVisitHistory").append(th);
        	}
        }
    });
	$("#fontName").html(fontName);
	$("#fontMobile").html(fontMobile);
	$("#agentName").html(agentName);
	$("#agentMobile").html(agentMobile);
	
	$("#content").val("")
	$("input[name='remark_visit']").each(function(){
       if($(this).prop("value")==1){
            $(this).prop("checked",true);
        }
    });
	$(".mr10").remove();
	$("#historyId").val(historyId);
	$("#case_code").html(caseCode);
	$("#property_addr").html(propertyAddr);
	if(changeNameAndMobile && changeNameAndMobile.length>0){
		$("#change_name").html(changeNameAndMobile.split(',')[0]);
		$("#change_mobile").html(changeNameAndMobile.split(',')[1]);
	}
	$("#part_code").html(partCode);
	$("#team_name").html(teamName);
	$("#change_reason").html(changeReason);
	var htm='';
	if(sellerandphone && sellerandphone.length>0){
		var srps = sellerandphone.split('/');
		for(var i=0;i<srps.length;i++){
			htm+= "<span class='mr10'>"+srps[i].split(',')[0]+"<em class='ml5 blue-text'>"+srps[i].split(',')[1]+"</em></span>";
		}
		$("#seller").append($(htm));
	}
	htm='';
	if(buyerandphone && buyerandphone.length>0){
		var srps = buyerandphone.split('/');
		for(var i=0;i<srps.length;i++){
			htm+= "<span class='mr10'>"+srps[i].split(',')[0]+"<em class='ml5 blue-text'>"+srps[i].split(',')[1]+"</em></span>";
		}
		$("#buyer").append($(htm));
	}
	
	
}
//添加跟进内容
$("#submitBtn").click(function(){
	var historyId = $("#historyId").val();
	var content = $.trim($("#content").val());
	var remarkVisit = 1;
  	$("input[name='remark_visit']").each(function(){
  		if($(this).prop("checked")){
  			remarkVisit = $(this).prop("value");
  		}
     });
	if(content==''){
		alert("请输入回访跟进内容！");
		return;
	}
	var params = {};
	params.planHistoryId = historyId;
	params.visitRemark = remarkVisit;
	params.content = content;
	
	$.ajax({
		url:ctx+"/report/addReturnVisit",
		method:"post",
		dataType:"json",
		data : params,
		success : function(data) {
			if(data.success){
				alert(data.message);
				$("#close").click();
				reloadGrid(1);
				
				//window.location.href = ctx+"/report/dealChangeCaseList";
				var newTitle = '';
				if(remarkVisit=='1'){
					$('#span'+batchId).html('<span class="yes_color">正常</span>');
					newTitle+= "1. "+"正常 "+data.content.content+" "+data.content.createTime+"<br>";
				}else if(remarkVisit=='0'){
					$('#span'+batchId).html('<span class="red_color">异常</span>');
					newTitle+= "1. "+"异常 "+data.content.content+" "+data.content.createTime+"<br>";
				}
				var title = $('#i'+batchId).attr('title');
				var titles = title.split("<br>");
				for(var i=0;i<titles.length-1;i++){
					var cont = titles[i].split(".")[1];
					newTitle+= (i+2)+"."+cont+"<br>";
				}
				$('#i'+batchId).attr("title",newTitle);
				$('.demo-top').poshytip({
					className: 'tip-twitter',
					showTimeout: 1,
					alignTo: 'target',
					alignX: 'center',
					alignY: 'top',
					offsetX: 8,
					offsetY: 5,
				});
			}else{
				alert(data.message);
			}
		},		
		error : function(errors) {
			alert("数据保存出错:"+JSON.stringify(errors));
		}	 
    });
	
});

function changeStyle(){
	  if ($("#flag").hasClass('fa fa-sort-desc fa_down')){
		  $("#flag").removeClass('fa fa-sort-desc fa_down');
	     $("#flag").addClass('fa fa-sort-asc fa_up');
	  } else {
		 $("#flag").removeClass('fa fa-sort-asc fa_up');
		 $("#flag").addClass('fa fa-sort-desc fa_down');
	  }
		
	
}
//导出数据
function exportToExcel() {
	var queryId = "queryTradeChangedCaseList";
	var data = getParams();
	console.log(data);
	$.exportExcel({
		ctx : "..",
		queryId : queryId,
		colomns : ['CASE_CODE','PROPERTY_ADDR','PART_CODE','OLD_EST_PART_TIME','NEW_EST_PART_TIME','REAL_NAME','CHANGE_TIME','CHANGE_REASON','DISTRICT_NAME','TEAM_NAME','SELLERANDPHONE','BUYERANDPHONE','visitRemark','CONTENT','CREATE_TIME'],
		data:data
	});
}

//点击清除按钮
$("#clearBtn").click(function(){
	
	$("input[name='caseCode']").val("");
	$("input[name='teamId']").attr("hVal","");
	$("input[name='teamId']").val("");
	$("input[name='changeId']").attr("hVal","");
	$("input[name='changeId']").val("");
	$("input[name='changeTimeStart']").datepicker('update', '');
	$("input[name='changeTimeEnd']").datepicker('update', '');
	$("#propertyAddr").val("");
	$("#partCode option").each(function(){
		if($(this).text()=='请选择'){
			$(this).prop("selected",true);
		}
	});
	$("#visitRemark option").each(function(){
		if($(this).text()=='请选择'){
			$(this).prop("selected",true);
		}
	});
	
	
});
