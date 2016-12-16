//添加删除上下家  需要配置name 和id 
function getAtr(obj){
    var str='';
    str +=  '<div class="line">'
        +   '<div class="form_content">'
        +   '<input type="hidden" class="select_control sign_right_one" name="pkidUp" id="pkidUp" value="">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '上家姓名'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one" name="guestNameUp" id="guestNameUp" value="">'
        +   ' </div>'
        +   '<div class="form_content">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '上家电话'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one" name="guestPhoneUp" id="guestPhoneUp" value="">'
        +   '</div>'
        +   '<a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
        +   '</div>';
    $("#topHome").after(str);
}

function getNext(obj){
    var str='';
    str +=  '<div class="line">'
        +   '<div class="form_content">'
        +   ' <input type="hidden" class="select_control sign_right_one" name="pkidDown" id="pkidUp" value="">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '下家姓名'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one"  name="guestNameDown" id="guestNameDown" value="">'
        +   ' </div>'
        +   '<div class="form_content">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '下家电话'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one" name="guestPhoneDown"  id="guestPhoneDown" value="" >'
        +   '</div>'
        +   '<a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
        +   '</div>';
    $("#downHome").after(str);
}

function getDel(k){
    $(k).parents('.line').remove();
}

//案件经纪人
$('#newCaseAgent').click(function() {	
	addCaseFindAgent();
});
//案件经纪人
function addCaseFindAgent(){	
	userSelect({
		startOrgId : '1D29BB468F504774ACE653B946A393EE',
		expandNodeId : '1D29BB468F504774ACE653B946A393EE',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',				
		jobCode : '',
		callBack : addCaseAgent
	});	
}

//选取责任人的回调函数
function addCaseAgent(array) {	
	if (array && array.length > 0) {
		$("#agentName").val(array[0].username);
		$("#agentName").attr('hVal', array[0].userId);
		$("#agentCode").val(array[0].userId);
		
		$("#agentPhone").val(array[0].mobile);		
		$("#agentOrgName").val(array[0].orgName);
		$("#agentOrgId").val(array[0].orgId);
		$("#agentOrgCode").val(array[0].orgCode);
		
	} else {
		$("#agentName").val("");
		$("#agentName").attr('hVal', "");
	}
}





//定义全局变量
var ctx = $("#appCtx").val();
var trade_ctx = $("#ctx").val();
//页面初始化
$(document).ready(function() {
	select2DivClick(1);
	
});

$("#blocksSelect").select2({
	  ajax: {
		    url: ctx+'/api/house/bizblocksListAjax',
		    dataType: 'JSON',
		    delay: 400,
		    type: "POST",
		    /*params:{"Accept":"application/json, text/javascript, ; q=0.01","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.8",
			    "Cache-Control":"no-cache","Connection":"keep-alive","Content-Length":"71","Content-Type":"application/json;charset=UTF-8","X-Requested-With":"XMLHttpRequest",
			    "Access-Control-Allow-Origin":"*","Access-Control-Allow-Methods":"GET,POST"
			},*/
			   beforeSend : function(request){
			    	request.setRequestHeader("Accept","application/json,text/javascript,*/*;q=0.01");				    	    
			    	request.setRequestHeader("Content-Type","application/json;charset=UTF-8");			    	    	
			    },

		    data: function (params) {		    	
		    	var data={
			    	    "estateName": $.trim(params.term),
			    	    "cityCode": "",
			    	    "district": "",
			    	    "page": 1,
			    	    "pageSize": 10
	            };
		    	
		    	return $.toJSON(data);
/*		      return {
		    	    "estateName": $.trim(params.term),
		    	    "cityCode": "",
		    	    "district": "",
		    	    "page": params.page,
		    	    "pageSize": 10
		    	};*/
		    },
		    processResults: function (data, params) {
		      params.page = params.page || 1;
		      alert(JSON.stringify(params));
		      return {
		        results: data.items,
		        pagination: {
		          more: (params.page * 30) < data.total_count
		        }
		      };
		    },
		    cache: true
		  },
		  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
		  minimumInputLength: 2,
		  templateResult: formatRepo, // omitted for brevity, see the source of this page
		  templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
});


function formatRepoSelection (bond) {			
	//alert(JSON.stringify(bond));
	//select2DivClick(bond); 
	//alert(JSON.stringify(bond));
	return bond.name;
};

//函数用来渲染结果
function formatRepo(data) {	
	// alert(JSON.stringify(data));
    return    '<option value='+ data.pkid +'> '+ data.name +'</option>';
};


//房屋搜索结果 houseAddrSearchResult 为条件查询房屋栋数并填充
function select2DivClick( data ){
	$('#buildingsSelect').empty();
  	var v = 5121;//data.pkid;	
	$.ajax({
		type: "GET",
		url: ctx+'/api/house/buildingsListAjax',
		dataType:"json",    
		data:"resblockId="+v,
		success: function(data) {
			//console.log(data);
			var option="";
			if(data.length===0){
				 option="<option value=''>无栋座</option>";
				 $('#buildingsSelect').append(option);
			}else{
				 option="<option value=''>请选择</option>";
				 $('#buildingsSelect').append(option);
				 $.each(data, function(i) {
			         	 option="<option value='"+data[i].pkid+"'>";
			          	 option+=data[i].name+"</option>";
			             $('#buildingsSelect').append(option);
			     });
				 
				 $('#floorSelect').empty();
				 option="<option value=''>请选择</option>";
			     $('#floorSelect').append(option);
			     
			     $('#roomSelect').empty();
			     option="<option value=''>请选择</option>";
			     $('#roomSelect').append(option);			   
			}	
			//栋座改变
			buildingChange(); 
		},
		error: function(errors) {
		   	alert("获得楼盘栋座出错!");   //弹出失败提示框
		}
	});  	
}


$("#buildingsSelect").change(function(){
	buildingChange();
});
/*
 * 栋座改变事件
 */
function buildingChange(){
/*	var authData=$("#authData");
	authData.html("");*/
	$("#floorSelect").empty();
	var resblock_id =  $("#blocksSelect").val();
	var building_id = 27527 ; //$("#buildingsSelect").val();      //TODO
	
	if(building_id==""){
		 option="<option value=''>请选择</option>";
	     $('#floorSelect').append(option);
	     
	     $('#roomSelect').empty();
	     option="<option value=''>请选择</option>";
	     $('#roomSelect').append(option);
	     
	     pageflag = false;
	     $('#pageOneSAlert').hide();
		 $("#pageOneEAlert").html("请至少填写楼盘地址.选择栋座,楼层,房号,和委托类型。")
		 $('#pageOneEAlert').show();
	     
	     return;	     
	}
	//房屋栋数不为空  根据变动的id查询新的层数
	var params="id="+building_id;
	$.ajax({
		type: "GET",
		url: ctx+'/api/house/getFloorTotal',
		dataType:"json",    
		data:params,
		success: function(data) {
		    var option="";
		    var floorSelect =$('#floorSelect');
			if(data===null){
				 option="<option value=''>无层</option>";
							floorSelect.append(option);

			}else{
				floorSelect.empty();
				//总层数
				var floorTotal=data;
				if(floorTotal){
					option="<option value=''>请选择</option>";
					floorSelect.append(option);
					for (var i = 1; i <= floorTotal; i++) {
						option="<option value='"+i+"'>"+i+"层</option>";
							floorSelect.append(option);
					}
				
				}
			
			}
			floorChange();	
						     
		},
	error: function(errors) {
	   	alert("获得楼盘层数出错!");   //弹出失败提示框	  
		}
	});
}	


/*
 * 楼层改变
 */
$("#floorSelect").change(function(){
	floorChange();
});	 

/*
 * 楼层改变
 */
function floorChange(){
	 $('#roomSelect').empty();
	var roomChanged = false;  //todo
	var estate_id=  $("#blocksSelect").val();//楼房地址
	var building_id=$("#buildingsSelect").val();//楼栋	
	var floor=$("#floorSelect").val();//楼层
	if(floor==""){
		 option="<option value=''>请选择</option>";
	     $('#roomSelect').append(option);
	     return; 
	}
	//TODO
	var params={
		estateId: 5121, //estate_id,
		buildingId:27527,//building_id,
		floor:4//floor
	};
	$.ajax({
		type: "GET",
		url: ctx+'/api/house/getRoomNos',
		dataType:"json",    
		data:params,
		async: false,
		cache:false,
		success: function(data) {			
				var data=data.data;
				var option="";
				if(data==null||data.length===0){
					 option="<option value=''>无房屋</option>";
				     $('#roomSelect').append(option);
				}else{
					 option="<option value=''>请选择</option>";
				     $('#roomSelect').append(option);
					 $.each(data, function(i) {
			         	 option="<option value='"+data[i].pkid+"'>";
			          	 option+=data[i].roomNo+"</option>";
			             $('#roomSelect').append(option);
			     	});
				}
				roomChanged = true;
						     
		},
	error: function(errors) {
		 alert("获得房屋号出错!");	   
		}
	});
	if(roomChanged){
		roomSelectChange();
	}
}  

//房屋层数改变
$("#roomSelect").change(function (){
		roomSelectChange();
});

function roomSelectChange(){		
	//var houseId = $('#roomSelect').val(); //房屋编号      TODO
	var houseId = 7781504;
	var addHtml = "";	
	//选定具体的房屋号之后 确定houseId，到后端请求数据是否有CTM推送的案件
	if(houseId !="" && houseId !=null && houseId != undefined){	
			var data = {
					propertyCode: houseId,						
					queryId : "isRepeatCaseList",
					rows : 4,
					page : 1
			};
			reloadGrid(data);
	}
}

function reloadGrid(data){
	$.ajax({
		async : true,
		url : trade_ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
/*		beforeSend : function() {
			$.blockUI({
				message : $("#salesLoading"),
				css : {
					'border' : 'none',
					'z-index' : '9999'
				}
			});
			$(".blockOverlay").css({
				'z-index' : '9998'
			});
		},*/
		success : function(data) {
			$.unblockUI();		
			if(data != null){	
				$("#isRepeatCase").show();
				data.ctx = ctx;
				var addCaseList = template('template_addCaseList', data);
				$("#addCaseList").empty();
				$("#addCaseList").html(addCaseList);
				// 显示分页
				initpage(data.total, data.pagesize, data.page, data.records);
			}			
		},
		error : function(e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}
//分页
function initpage(totalCount, pageSize, currentPage, records) {
	if (totalCount > 1500) {
		totalCount = 1500;
	}
	var currentTotalstrong = $('#currentTotalPage').find('strong');
	if (totalCount < 1 || pageSize < 1 || currentPage < 1) {
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage + '/' + totalCount);
	$('#totalP').text(records);
	$(function() {
		// top
		$('.demo-top').poshytip({
			className : 'tip-twitter',
			showTimeout : 1,
			alignTo : 'target',
			alignX : 'center',
			alignY : 'top',
			offsetX : 8,
			offsetY : 5,
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
			 //console.log(page);
			searchMethod(page);
	    }
	});
}

function searchMethod(page){
	if (!page) {
		page = 1;
	}	
	var params = getParams();
	params.page = page;
	params.rows = 4;
	params.queryId = "isRepeatCaseList";
	reloadGrid(params);
}

function getParams() {
	//var houseId = $('#roomSelect').val(); //房屋编号      TODO
	var houseId = 7781504;
	var params = {};
	params.propertyCode = houseId;
	
	return params;
}


$("#caseInfoClean").click(function(){
	$("#myModal").show();	
});
$("#newCaseInfoCancel").click(function(){
	$("#myModal").hide();	
});
$("#newCaseInfoDelete").click(function(){
	$("#blocksSelect").val("");
	$("#buildingsSelect").val("");
	$("#floorSelect").val("");
	$("#roomSelect").val("");
	
	$("input[name='agentPhone']").val("");
	$("input[name='agentOrgName']").val("");
	$("input[name='agentName']").val("");
	$("input[name='agentOrgId']").val("");
	$("input[name='agentCode']").val("");
	
	$("input[name='guestNameUp']").val("");
	$("input[name='guestPhoneUp']").val("");
	$("input[name='guestNameDown']").val("");
	$("input[name='guestPhoneDown']").val("");	
	$("#myModal").hide();	
	
	$("#isRepeatCase").hide();
	
});



$("#newCaseInfoSave,#newCaseInfoSubmit").click(function(){	
	$("#myModal").hide();	
	//TODO  submit之前需要判断哪些字段为空的情况的下不能提交
	if(!checkForm()){
		return;		
	}
	if(!phoneUpAndphoneDownCheck()){
		return;
	}
	
	$("#saveCaseInfo").submit();
});

function checkForm(){
	var formSubmitFlag = true;	

	if ($("#blocksSelect").val("") == '') {	
		alert("楼盘信息不能为空！");
		$("#blocksSelect").focus();		
		return false;
	}	
	if ($("#buildingsSelect").val("") == '') {
		alert("楼栋信息不能为空！");
		$("#buildingsSelect").focus();		
		return false;
	}	
	if ($("#floorSelect").val("") == '') {
		alert("楼层信息不能为空！");
		$("#floorSelect").focus();		
		return false;
	}	
	if ($("#roomSelect").val("") == '') {
		alert("房屋号信息不能为空！");
		$("#roomSelect").focus();	
		return false;
	}	
	
	if ($('input[name=agentName]').val() == '') {
		alert("经纪人姓名不能为空!");
		$('input[name=agentName]').focus();
		return false;
	}
	if ($('input[name=agentPhone]').val() == '') {
		alert("经纪人电话不能为空!");
		$('input[name=agentPhone]').focus();
		return false;
	}
	if ($('input[name=guestNameUp]').val() == '') {
		alert("上家姓名不能为空!");
		$('input[name=guestNameUp]').focus();
		return false;
	}
	if ($('input[name=guestPhoneUp]').val() == '') {
		alert("上家电话不能为空!");
		$('input[name=guestPhoneUp]').focus();
		return false;
	}
	if ($('input[name=guestNameDown]').val() == '') {
		alert("下家姓名不能为空!");
		$('input[name=guestNameDown]').focus();
		return false;
	}
	if ($('input[name=guestPhoneDown]').val() == '') {
		alert("下家电话不能为空!");
		$('input[name=guestPhoneDown]').focus();
		return false;
	}
	
	return formSubmitFlag;
}


//上下家电话相同验证
function phoneUpAndphoneDownCheck() {
	var checkGuestPhone= true;
	var selectsPhoneDown = $("input[name='guestPhoneDown']");
	var selectsPhoneUp = $("input[name='guestPhoneUp']");				
	
	
	$.each(selectsPhoneUp, function(j, item) {
		if (item.value == '') {
			alert("上家电话为必填项!");
			selectsPhoneUp[j].focus();
			checkGuestPhone = false;						
		} else {						
			checkGuestPhone = checkContactNumber(item.value);						
			if (!checkGuestPhone) {								
				//alert("上家电话不符合手机号码或电话号码格式!");
				selectsPhoneUp[j].focus();
				return false;
			}
		}
	});
	if (!checkGuestPhone || selectsPhoneUp == null) {
		return false;
	}
	//验证下家电话号码
	$.each(selectsPhoneDown, function(j, item) {
		if (item.value == '') {
			alert("下家电话为必填项!");
			selectsPhoneDown[j].focus();
			checkGuestPhone = false;
		} else {
			checkGuestPhone = checkContactNumber(item.value);
			if (!checkGuestPhone) {
				//alert("下家电话不符合手机号码或电话号码格式!");
				selectsPhoneDown[j].focus();
				return false;
			}
		}
	});
	if (!checkGuestPhone || selectsPhoneDown == null) {
		return false;
	}				
	$.each(selectsPhoneUp,function(i, itemPhoneUp) {
			if (itemPhoneUp.value != '') {
				$.each(selectsPhoneDown,function(j,	itemPhoneDown) {
					if (itemPhoneDown.value != '') {
						if (itemPhoneUp.value.trim() == itemPhoneDown.value.trim()) {
									alert("上下家电话不能填写一样!");
									checkGuestPhone=false;
									return checkGuestPhone;
						}
					}
				})							
		    }
	if(checkGuestPhone==false) {return  false;}
	})				
	return checkGuestPhone;
}


//判断是否有重复字符
function isUniqueChar(value){
	if(!value){
		return false;
	}
	var uniqueMap   = {};
	for(i=0;i<value.length;i++){
		var val = value.charAt(i);
		uniqueMap[val]=val;
	}
	var result = ""
	for(var key in uniqueMap){
		result +=key;
	}
	return (result.length==1);
}
//验证手机和电话号码
function checkContactNumber(ContactNumber) {
	
	var mobile = $.trim(ContactNumber);	
	var number=/^[0-9]*$/;	//数字
	var isValid = true;
	
	if(!number.exec(mobile)){					
		alert("电话号码只能由数字组成！");
		isValid = false;
		return isValid;
	}
	if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13)){				
		alert("电话号码只能由是8位、11位或者13位的数字组成！");
		isValid = false;
		return isValid;
	}
	
	if(isUniqueChar(mobile)){
		alert("电话号码不能为全部相同的数字！");
		isValid = false;
		return isValid;
	}
	return isValid;
}
/*		
 * 
 * //选定具体的房屋号之后 确定houseId，到后端请求数据是否有CTM推送的案件   mybatis方法
 $.ajax({
		url:trade_ctx+"/caseMerge/inputCaseJudge",
		method:"post",
		dataType:"json",
		data:{"propertyCode" : houseId},//"mmIoBatch" : mmIoBatch,
		success:function(data){ 
		alert("Result=====" +JSON.stringify(data));
		console.log("Result=====" +JSON.stringify(data));
			if(data != null ){
				if(data.success){
					 var  resultList = data.content;
					 //alert("Result=====" +JSON.stringify(data));
					 
					 $.each(resultList, function(index, value) {
						 addHtml += "<tr>"; 
						 addHtml += "<td>"; 
						 addHtml += "<p class='big'>"; 							
						 addHtml += "<a href=trade_ctx/case/caseDetail?caseId=' "+ value.toCase.pkid+"'>"+value.toCase.caseCode+"</a>"; 
						 addHtml += "</p>" ;							 
						 addHtml +=	"<p>"+value.toPropertyInfo.propertyAddr+"</p></td>";
						 addHtml += "<td><p>";
						 addHtml += "<span class='yes_color'>"+value.toCase.caseProperty +"</span></p>";							 
						 addHtml += "<p><span class='no_color'>"+value.toCase.status+"</span></p></td>";
						 addHtml += "<td>";							 
						 addHtml += "<span class='manager'>上家："+value.upName+"</span>";
						 addHtml += "<span class='manager'>下家："+value.downName+"</span>";							 
						 addHtml += "</td>";
						 
						 addHtml += "<td>";							 
						 addHtml += "<span class='manager'><a href='#'><em>"+value.toCaseInfo.agentName+"</em>"+value.toCaseInfo.agentPhone+"</a></span>";
						 addHtml += "<span class='manager'>下家："+value.toCaseInfo.grpName+"</span>";							 
						 addHtml += "</td>";
		
						 addHtml += "<td>";							 
						 addHtml += "<p class='big'>"+value.toCase.leadingProcessId+"</p>";
						 addHtml += "<p class='big tint_grey'>"+value.toCaseInfo.orgId+"</p>";						 
						 addHtml += "</td>";
						 
						 addHtml += "</tr>";
						 $("#addCaseList").html(str);
					 })
					 $("#isRepeatCase").show();
				
				}else{
					alert("请求数据错误！");
				}
			}	
		},       
		error:function(e){
	    	 alert(e);
	   }
	});*/
