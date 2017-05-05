var startList=0;//判断是不是应该显示列表

$(document).ready(function() {
	$("#cooperForShow").show();
	aist.sortWrapper({
		reloadGrid : searchMethod
	}); 
	
	var data = getParams(1);
    aist.wrap(data);
    startList=0;
	reloadGrid(data);
});


function reloadGrid(data) {
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
        	//console.log("Result==="+JSON.stringify(data));
      	  var caseForChangeList = template('template_caseForChangeList' , data);
      	    $("#caseForChangeList").empty();      	    
      	  
      	     if(startList == 1){      	      
   			  $("#caseForChangeList").html(caseForChangeList);
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
           		startList=0;
      	     }else{
			 
      	     }
              $.unblockUI(); 
             
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        } 
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
			 //console.log(page);
			searchMethod(page);
	    }
	});
}
/***
 *  回调刷新的方法
 */
/*function callback() {
	setTimeout('searchMethod()',1000); 
}*/
//search
function searchMethod(page){
	var data = getParams(page);
    aist.wrap(data);
	reloadGrid(data);
}
//查询
 $('#searchButton').click(function() {	
	searchMethod(1);
}); 

function getParams(page) {
	if(!page) {
		page = 1;
	}
	var propertyAddr =  $.trim($('#propertyAddr').val());
	var caseCode = $.trim($("#caseCodeForSelect").val());

	if(propertyAddr.length>0|| caseCode.length>0 ){
		startList=1;
	}else{
		startList=0;
	}
	var data = {
			search_propertyAddr: propertyAddr,
			search_caseCode:caseCode,			
			queryId : "queryCastForChangeList",
			rows : 10,
			page : page
		};
    return data; 
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

//清空
 $('#caseForChangeCleanButton').click(function() {	
		$("#caseCodeForSelect").val("");
		$("#propertyAddr").val("");
}); 

 //变更责任人弹框
function changeProfessor(caseCode){	
	cleanForm();
	$("#leadingProForChang").show();
	if(caseCode){
		$("#caseCodeForChange").val(caseCode);
		
		var url = "/case/selectLeandingPro";
		var ctx = $("#ctx").val();	
		url = ctx + url;
		
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
			data :[{
				name:'caseCode',
				value:caseCode
			}],
			success : function(data) {	
				$("#leadingProName").val(data.leadingProcessName);
				$("#leadingProId").val(data.leadingProcessId);				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}	
}
//变更责任人取消
$("#leadingProClose").click(function(){	
	$("#leadingProForChang").hide();
})

//变更责任人提交
$("#leadingProSubmit").click(function(){
	
	 var caseCode = $("#caseCodeForChange").val(); // 案件的caseCode
	 var leadingProId = $("#leadingProId").val();//新的责任人userId	
	 if(leadingProId == "" || leadingProId ==  null || leadingProId == undefined){
		 window.wxc.alert("若要变更项目责任人，请先选择新的案件责任人！");
		 return;
	 }
	 
	 window.wxc.confirm("您确定要进行责任人变更？",{"wxcOk":function(){
			var url = "/case/changeLeadingPro";
			var ctx = $("#ctx").val();
			url = ctx + url;
			var params = '&userId=' + leadingProId + '&caseCode=' + caseCode;

			$.ajax({
				cache : false,
				async : true,
				type : "POST",
				url : url,
				dataType : "json",
				timeout : 10000,
				data : params,
				
				success : function(data) {
					if(data.success){
						$("#leadingProForChang").hide();
						window.wxc.success("恭喜，责任人变更成功！");
						reloadGrid(getParams(1));						
					}else{
						window.wxc.error(data.message);
					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
	 }}); 
	 
})




//案件责任人
 function leadingProForChangeClick(){	
			userSelect({
				startOrgId : 'ff8080814f459a78014f45a73d820006',
				expandNodeId : 'ff8080814f459a78014f45a73d820006',
				nameType : 'long|short',
				orgType : '',
				departmentType : '',
				departmentHeriarchy : '',
				chkStyle : 'radio',
				//	jobCode : 'Manager,Senior_Manager',
				jobCode : '',
				callBack : selectLeadingPro
			});
	
 }

//选取责任人的回调函数
function selectLeadingPro(array) {	
	if (array && array.length > 0) {
		$("#leadingProName").val(array[0].username);
		$("#leadingProName").attr('hVal', array[0].userId);
		$("#leadingProId").val(array[0].userId);
		
	} else {
		$("#leadingProName").val("");
		$("#leadingProName").attr('hVal', "");
	}
}

//案件责任人
$('#materialRefundUser').click(function() {	
	leadingProForChangeClick();
});

//变更合作人弹框
function changeCooper(caseCode,tdId){
	cleanForm();
	if(caseCode){
		$("#cooperSubmit").attr("tdId",tdId);
		$("#cooperCaseCode").val(caseCode);		
		//查询机构交易顾问
		var url = "/case/changeCoopeForNew";
		var ctx = $("#ctx").val();	
		url = ctx + url;
		
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
			data :[{
				name:'cooperCaseCode',
				value:caseCode
			}],
			success : function(data) {					
				changeCooperForShow(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}else{
		window.wxc.error("变更请求参数有误,请核实！");
		return;
	}	
}

//变更合作对象
function changeCooperForShow(data) {
	var addHtml = '';	
	if(data.servitemList == null || data.servitemList=="" || data.servitemList==undefined){
		$("#cooperForShow").hide();
		addHtml += '<div class="form_content">';
		addHtml += '<label class="control-label"><span style="margin-left: 45px">抱歉，对应案件编号没有可以变更的合作项目！</span></label> ' ;
		addHtml +='<div>'		
	}else{		
		$("#cooperForShow").show();		
		$.each(data.servitemList, function(index, value){
			addHtml += '<div class="form_content">';
			addHtml += "<input type='hidden' name='caseCode' value='"+$('#cooperCaseCode').val()+"' />";
			addHtml += "<input type='hidden' name='preOrgId' id='preOrg"+index+"' value='"+value.orgId+"'/>";
			addHtml += "<input type='hidden' name='srvCode'  id='srvCode"+index+"' value='"+value.srvCode+"'/>";
			
			addHtml += '<label class="control-label sign_left_small">合作项目</label>';			
			addHtml += "<input type='text' class='teamcode input_type' placeholder='' id='srvName"+index+"' name='srvName'  readonly value='"+value.srvName+"'/>";
			addHtml += "<label class='control-label sign_left_small'>合作人</label>";			
			addHtml += "<input class='teamcode input_type' placeholder='' value='"+value.oldProcessorName+"'  name='cooperName' id='cooperName"+index+"' hVal='' onclick='cooperForChangeClick("+index+")' />";
			addHtml += "<div class='input-group float_icon organize_icon' id='cooperUser'><i class='icon iconfont'>&#xe627;</i></div>";
			addHtml += "<input type='hidden' name='preProcessorId' value='"+value.oldProcessorId+"' />";
			addHtml += "<input type='hidden' name='processorId' id='processorId"+index+"'  value='"+value.oldProcessorId+"' />";
			addHtml += "<input type='hidden' name='orgId' id='orgId"+index+"'  value='"+value.orgId+"' />";
			addHtml += "</div>";		
		})		
	}	
	
	$("#change-modal-data-show").html(addHtml);	
	$('#cooperForChang').show();
}

function cleanForm(){	
	$("input[name='leadingProName']").val();
	$("input[name='leadingProId']").val();
	$("input[name='cooperName']").val();
	$("input[name='preProcessorId']").val();
	$("input[name='processorId']").val();
}

//变更合作人提交
$("#cooperSubmit").click(function(){	
	 
	 if(!checkProId()){
		 window.wxc.alert("若要变更项目合作对象，请先选择合作人！");
		 return;
	 }
	 var  data = getParamForCooper();
	 var url = "/case/updateCoopeSubmit";
	 var ctx = $("#ctx").val();
	 url = ctx + url;			
	 
	 var tdId = $(this).attr("tdId");
/*	 alert(JSON.stringify(data));
	 alert("url==="+url);*/
	 
	 window.wxc.confirm("您确定要进行案件合作对象变更？",{"wxcOk":function(){
			$.ajax({
				cache : false,
				async : true,
				type : "POST",
				url : url,
				dataType : "json",
				timeout : 10000,
				contentType : 'application/json;charset=utf-8',
				data : JSON.stringify(data),			
				success : function(data) {
					if(data.success){		
						var $tdId = $("#PROCESSOR_NAME","#"+tdId);
						var hiddes = $("input[name='cooperName']","#change-modal-data-show");
						var names = "";
						for(var i=0;i<hiddes.size();i++){
							var name = hiddes.eq(i).val();
							if(names!=""){
								names+="/";
							}
							names+=name;
						}
						$tdId.html("合作顾问："+names);					
						$("#cooperForChang").hide();												
						window.wxc.success(data.message);																		
					}else{
						window.wxc.error(data.message);
					}	
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
	 }}); 
})

function getParamForCooper(){
 	 var caseCode = [];
 	 //修改之后的组织id
 	 var orgId = [];
 	 var preOrgId = [];//修改之前的组织id
 	 var srvCode = [];
 	 var processorId = [];//修改之后的userId
 	 var preProcessorId = [];
	 var caseCodeArray = $("input[name='caseCode']"); 
	 
	 var orgIdArray = $("input[name='preOrgId']");
	 var srvCodeArray = $("input[name='srvCode']");
	 var processorIdArray = $("input[name='processorId']");
	 
	 $.each(caseCodeArray, function(j, item) {
		 if(item.value != '' && item.value != null){
			 caseCode.push(item.value);
		 }	 
	 });
	 $.each(orgIdArray, function(i, item) {
		 if(item.value != '' && item.value != null){
			 preOrgId.push(item.value);
		 }	 
	 });
	 $.each(srvCodeArray, function(i, item) {
		 if(item.value != '' && item.value != null){
			 srvCode.push(item.value);
		 }	 
	 });
	 $.each(processorIdArray, function(i, item) {
		 if(item.value != '' && item.value != null){
			 processorId.push(item.value);
		 }	 
	 });

	 $.each($("input[name='preProcessorId']"), function(i, item) {
		 if(item.value != '' && item.value != null){
			 preProcessorId.push(item.value);
		 }	 
	 });
	 $.each($("input[name='orgId']"), function(i, item) {
		 if(item.value != '' && item.value != null){
			 orgId.push(item.value);
		 }	 
	 });
	 

	 var tgServItemAndProcessorVo = {};
	 tgServItemAndProcessorVo.caseCode = caseCode;
	 tgServItemAndProcessorVo.srvCode = srvCode;
	 tgServItemAndProcessorVo.processorId = processorId;
	 tgServItemAndProcessorVo.orgId = orgId;
	 tgServItemAndProcessorVo.preProcessorId = preProcessorId;
	 tgServItemAndProcessorVo.preOrgId = preOrgId;
	 
	 return tgServItemAndProcessorVo;
}



function checkProId(){
	 var processorIdArray = $("input[name='processorId']");
	 var proId = 0;	 
	 var proIdFlag = true;	 
	 $.each(processorIdArray, function(i, item) {		 
		 if(item.value == '' || item.value == null){
			 proId += 1;
		 }	 
	 });
	 
	 if(proId == processorIdArray.length){
		 proIdFlag = false;
	 }
	 return proIdFlag;
}
//变更合作人取消
$("#cooperClose").click(function(){	
	$("#cooperForChang").hide();
})


function cooperForChangeClick(index){
	
	userSelect({
		startOrgId : 'ff8080814f459a78014f45a73d820006',
		expandNodeId : 'ff8080814f459a78014f45a73d820006',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',		
		jobCode : '',
		callBack : function(array){
			if (array && array.length > 0) {
				$("#cooperName"+index).val(array[0].username);
				$("#cooperName"+index).attr('hVal', array[0].userId);
				$("#processorId"+index).val(array[0].userId);
				$("#orgId"+index).val(array[0].orgId);
				
			} else {
				$("#cooperName"+index).val("");
				$("#cooperName"+index).attr('hVal', "");
			}
		}
	});

}

//案件合作人
$('#cooperUser').click(function() {	
	cooperForChangeClick();
});