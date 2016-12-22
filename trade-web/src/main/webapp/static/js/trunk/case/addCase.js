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
	//$('#blocksSelect').prepend('<option></option>').select2({placeholder: "请输入地址"});

});


//select2 自动补全控件
 $("#blocksSelect").select2({
	  language: "zh-CN",
	  placeholder: {id:'',placeholder:'请输入地址'},	
	  allowClear: true,
	  "language": {
		      "errorLoading" : function () {
		        return  '加载房屋信息出错';
		      },
			  "inputTooShort": function (args) {
			        var remainingChars = args.minimum - args.input.length;
			        var message = '请至少输入 ' + remainingChars + ' 个字符。';
			        return message;
		      },
			 "searching": function () {				 
				  return '信息查询中…';
			 },
			 "noResults": function () {
			    return '未找到符合条件的信息';
			 }

	  },
	  minimumInputLength: 1,	
	  //select2   4.0以上的版本  点选需要设置id(源码中通过id 来判断)
	  id : function(data){ 
	    	return data.id; 
	  },
	  ajax: {	    

		    url: ctx+'/api/house/bizblocksListAjax',
		    dataType: 'json',
		    delay: 300,
		    type: "POST",
		    params:{"contentType": "application/json;charset=utf-8"},
		    data: function (params) {
		    	//alert(JSON.stringify(params));		    	
		    	var data={
			    	    "estateName": $.trim(params.term),
			    	    "cityCode": "",
			    	    "district": "",
			    	    "page": 1,
			    	    "pageSize": 30
	            };
		    	return    data;
		    },
		   
		   //processResults 函数的results的接收返回的值，具体以json为主
		   processResults: function (data, page) {	
			   var converResults = [];			   
               $.each(data, function (i, v) {
                   var o = {};
                   o.id = v.pkid;
                   o.name = v.name;  
                   o.districtCode = v.districtCode;
                   converResults.push(o);
               })      	 
			    //results: data.items;results: data.res以后端返回的json为主
			    return {results: converResults};
			  },
		  cache: true,
		  },
		  
		  escapeMarkup: function (markup) { return markup; }, //字符转义处理
		  templateSelection: formatRepoSelection,  //获取输入框选择的值		  
		  templateResult: formatRepo  //查询返回值渲染
		
});


//显示 选取的值
function formatRepoSelection(results) {	
    if (results != null && results != undefined) {
	 var districts = {
		 		"310104":"徐汇区"
		 		,"310105":"长宁区"
		 		,"310117":"松江区"
		 		,"310116":"金山区"
		 		,"310115":"浦东新区"
		 		,"310114":"嘉定区"
		 		,"310113":"宝山区"
		 		,"310112":"闵行区"
		 		,"310110":"杨浦区"
		 		,"310108":"闸北区"
		 		,"310107":"普陀区"
		 		,"310106":"静安区"
		 		,"310101":"黄浦区"
		 		,"310118":"青浦区"
		 		,"310109":"虹口区"
		 	};
    	//拼接产证地址
    	$("#blockId").val(results.id);
    	$("#blockName").val(results.name);	
    	if(results.districtCode){
	    	$('#distCode').val(results.districtCode);  //设置区域
	    	$('#distName').val(districts[results.districtCode]);  //设置区域
    	}
        select2DivClick(results);   
        return results.name;
    }
};



//搜索结束后在页面 直接显示结果
function formatRepo(results) {	
	 loadedTimes = 0;
	 return '<div class="select2-user-result">' + results.name + '</div>';
	 //return  results.name; 
};

var loadedTimes = 0;

//房屋搜索结果 houseAddrSearchResult 为条件查询房屋栋数并填充
function select2DivClick( data ){	
	if(loadedTimes++ > 0){
		return ;
	}
	var v = data.id;		
	if(v !="" && v != null){
		$.ajax({
			type: "GET",
			url: ctx+'/api/house/buildingsListAjax',
			dataType:"json",    
			data:"resblockId="+v,
			success: function(data) {	
				$('#buildingsSelect').empty();  
				var option="";
				if(data.length==0){
					 option="<option value=''>无可选栋座</option>";
					 $('#buildingsSelect').append(option);
				}else{
					 option="<option value=''>请选择楼栋</option>";
					 $('#buildingsSelect').append(option);
					 $.each(data, function(i) {
				         	 option="<option value='"+data[i].pkid+"'>";
				          	 option+=data[i].name+"</option>";
				             $('#buildingsSelect').append(option);
				     });
					 
					 $('#floorSelect').empty();
					 option="<option value=''>请选择楼层</option>";
				     $('#floorSelect').append(option);
				     
				     $('#roomSelect').empty();
				     option="<option value=''>请选择房屋</option>";
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
	
}


$("#buildingsSelect").change(function(){
	buildingChange();
});
/*
 * 栋座改变事件
 */
function buildingChange(){
	$("#floorSelect").empty();
	var resblock_id =  $("#blockId").val();
	var building_id =  $("#buildingsSelect").val();     
	$("#buildingName").val( $("#buildingsSelect option:selected").text());     
	
	
	
	if(building_id==""){
		 option="<option value=''>请选择楼层</option>";
	     $('#floorSelect').append(option);
	     
	     $('#roomSelect').empty();
	     option="<option value=''>请选择房屋</option>";
	     $('#roomSelect').append(option);
	     
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
			if(data==null || data==0){
				 option="<option value=''>无可选楼层</option>";
							floorSelect.append(option);

			}else{
				floorSelect.empty();
				//总层数
				var floorTotal=data;
				if(floorTotal){
					option="<option value=''>请选择房屋</option>";
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
	$("#floorName").val($("#floorSelect option:selected").text());    
	
	var estate_id=  $("#blockId").val();//楼房地址
	var building_id=$("#buildingsSelect").val();//楼栋	
	var floor=$("#floorSelect").val();//楼层
	if(floor==""){
		 option="<option value=''>请选择房屋</option>";
	     $('#roomSelect').append(option);
	     return; 
	}
	//TODO
	var params={
		estateId: estate_id,
		buildingId:building_id,
		floor:floor
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
				if(data==null ||  data.length==0){
					 option="<option value=''>无可选房屋</option>";
				     $('#roomSelect').append(option);
				}else{
					 option="<option value=''>请选择房屋</option>";
				     $('#roomSelect').append(option);
					 $.each(data, function(i) {
			         	 option="<option value='"+data[i].pkid+"'>";
			          	 option+=data[i].roomNo+"</option>";
			             $('#roomSelect').append(option);
			     	});
				}
				roomChanged = true;//说明房屋号 变更了
						     
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
	var houseId = $('#roomSelect').val(); //房屋编号      TODO
	$("#roomName").val($("#roomSelect option:selected").text());   
	$("#propertyCode").val(houseId); //房屋code
	
	var addHtml = "";	
	//选定具体的房屋号之后 确定houseId，到后端请求数据是否有CTM推送的案件
	if(houseId !="" && houseId !=null && houseId != undefined){	
	var addr = "上海市"+ $('#distName').val() + $('#blockName').val() + $('#buildingName').val()+"栋" + $('#floorName').val() + $('#roomName').val()+"室";
	$("#propertyAddr").val(addr);  
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
			//alert(JSON.stringify(data));
			$.unblockUI();			
			if(data != null && data.rows.length > 0){	//&& data.page > 0
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
	if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13 || mobile.length ==14)){				
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

function getDictName(dictCode){
	var dictName = "";
	if(dictCode !="" && dictCode != null){
		if(dictCode == "310101"){
			dictName = "黄浦区";
		}else if(dictCode == "310102"){
			dictName = "徐汇区";
		}else if(dictCode == "310105"){
			dictName = "长宁区";
		}else if(dictCode == "310117"){
			dictName = "松江区";
		}else if(dictCode == "310116"){
			dictName = "金山区";
		}else if(dictCode == "310115"){
			dictName = "浦东新区";
		}else if(dictCode == "310114"){
			dictName = "嘉定区";
		}else if(dictCode == "310113"){
			dictName = "宝山区";
		}else if(dictCode == "310112"){
			dictName = "闵行区";
		}else if(dictCode == "310110"){
			dictName = "杨浦区";
		}else if(dictCode == "310108"){
			dictName = "闸北区";
		}else if(dictCode == "310107"){
			dictName = "普陀区";
		}else if(dictCode == "310106"){
			dictName = "静安区";
		}else if(dictCode == "310118"){
			dictName = "青浦区";
		}else if(dictCode == "310109"){
			dictName = "虹口区";
		}
	}	
	return dictName;	
}

//拼接地址
function setPropertyAddr(){
	$("#propertyAddr").val("");
	var addr = "上海市" + $('#distName').val();
	
	if($("#blockName").val() != "" && $("#blockName").val() != null){
		addr += $("#blockName").val();
	}
	if($("#buildingName").val() != "" && $("#buildingName").val() != null){
		if($("#buildingName").val().indexOf("栋") > 0 ){
			addr += $("#buildingName").val();
		}else{
			addr += $("#buildingName").val();
			addr += "栋";
		}
	}
	if($("#floorName").val() != "" && $("#floorName").val() != null){
		addr += $("#floorName").val();
	}
	if($("#roomName").val() != "" && $("#roomName").val() != null){
		addr += $("#roomName").val();
		addr += "室";
	}
	
	$("#propertyAddr").val(addr);  
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
