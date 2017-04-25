/**
 * 外单案件添加页面js
 * 编写：hjf
 * 时间：2017年4月21日13:51:21
 * 版本：1.0
 */
/**
 * 上家信息  动态添加
 */
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
/**
 * 下家信息	动态添加
 * @param obj
 */
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
/*上下家信息 删除*/
function getDel(k){
    $(k).parents('.line').remove();
}

/*$("#loanLostCleanButton").click(function(){
	window.wxc.confirm("确定清空所有信息？",{"wxcOk":function(){
		
	}});
});*/

//案件经纪人
$('#newCaseAgent').click(function() {	
	addCaseFindAgent();
});
//案件经纪人函数
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
//var sale_ctx = "http://10.4.19.87:8081/sales-web";
var sale_ctx = $("#appCtx").val();
var trade_ctx = $("#ctx").val();

//初始化select
function initSelectYear(id, value) {
	var d = new Date();
	var endYear = d.getFullYear();
	var starYear = 1900;
	var friend = $("#" + id);
	friend.empty();
	for (var i = endYear; i >= starYear; i--) {
		if (value == null || value == '' || value == undefined
				|| value > endYear || value < starYear) {
			value = endYear;
		}
		if (value == i) {
			friend
					.append("<option value='"+i+"'  selected='selected'>"
							+ i + "</option>");
		} else {
			friend.append("<option value='"+i+"'>" + i
					+ "</option>");
		}

	}
}


//显示 选取的值
function formatRepoSelection(results) {	
    if (results != null && results != undefined) {
    	$("#blockId").val(results.id);
    	
    	results.selected = true; 
    	results.id = results.id
    	results.name = results.text
        if(results.id == null || results.id == ""){
        	results.text = '请输入房屋地址'
        	results.name = results.text
        }
        $("#blocksSelect").val(results.name);
       
        select2DivClick(results);   
        return results.name;
    }
};




$("#buildingsSelect").change(function(){
	buildingChange();
});


function reloadGrid(data){
	$.ajax({
		async : true,
		url : trade_ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		success : function(data) {
			//alert(JSON.stringify(data));
			$.unblockUI();			
			if(data != null && data.rows.length > 0){	//&& data.page > 0
				$("#isRepeatCase").show();
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
	var houseId = $('#roomSelect').val(); //房屋编号     	
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
	$("#blockId").val("");	
	$("#buildingsSelect").val("");
	$("#floorSelect").val("");
	$("#roomSelect").val("");	
	$("#blocksSelect").val("请输入房屋地址").trigger("change");//或者	
	
	$("#propertyType").val("");
	$("#distCode").val("");
	$("#finishYear").val("");
	$("input[name='propertyAddr']").val("");
	$("input[name='propertyCode']").val("");
	$("input[name='square']").val("");
	$("input[name='floor']").val("");	
	$("input[name='totalFloor']").val("");
	$("input[name='propertyAddr']").val("");
	$("#houseInfo").hide();
	
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

function getCheckBoxValues(name) {
	var commSubject = [];
	$("span[name='commSubject'].out-btn-select").each(function() {
		var val = $(this).attr('value');							
		commSubject.push(val);
	});							
	return commSubject;
}
/**
 * 页面提交
 */
$("#newCaseInfoSave,#newCaseInfoSubmit").click(function(){	
	//TODO  submit之前需要判断哪些字段为空的情况的下不能提交
	/*if(!checkForm()){
		return false;		
	}
	if(!phoneUpAndphoneDownCheck()){
		return false;
	}*/
	
	//$("#saveCaseInfo").submit();
	
	var commSubject = getCheckBoxValues("commSubject");
	var data = [];
	$("form").each(function(){
		var obj = $(this).serializeArray();
		for(var i in obj){
			if(obj[i].name=="commSubject"){
				obj[i].value=commSubject.toString();
			}
			data.push(obj[i]);
		}
	}); 

	var url = ctx+"/caseMerge/saveWdCaseInfo";
	$.ajax({
		cache : false,
		async : false,/**false同步，true异步**/
		type : "POST",
		url : url,
		dataType : "json",
		data : data,
		success : function(data) {			
			
			/*if(data.success){
				window.wxc.success("新建外单成功！",{"wxcOk":function(){
					window.location.href=ctx+"/case/myCaseList";
				}});
			}else{
				window.wxc.error("新建外单失败！"+data.message); 
			}*/
			
		}
	});
	
});

function checkForm(){
	var formSubmitFlag = true;	

	if ($("#blocksSelect").val() == '') {	
		window.wxc.alert("楼盘信息不能为空！");
		$("#blocksSelect").focus();		
		return false;
	}	
	if ($("#buildingsSelect option:selected").val() == '') {
		window.wxc.alert("楼栋信息不能为空！");
		$("#buildingsSelect").focus();		
		return false;
	}	
	if ($("#floorSelect option:selected").val() == '') {
		window.wxc.alert("楼层信息不能为空！");
		$("#floorSelect").focus();		
		return false;
	}	
	if ($("#roomSelect option:selected").val() == '') {
		window.wxc.alert("房屋号信息不能为空！");
		$("#roomSelect").focus();	
		return false;
	}	
	
	if ($('input[name=agentName]').val() == '') {
		window.wxc.alert("经纪人姓名不能为空!");
		$('input[name=agentName]').focus();
		return false;
	}
	if ($('input[name=agentPhone]').val() == '') {
		window.wxc.alert("经纪人电话不能为空!");
		$('input[name=agentPhone]').focus();
		return false;
	}
	if ($('input[name=guestNameUp]').val() == '') {
		window.wxc.alert("上家姓名不能为空!");
		$('input[name=guestNameUp]').focus();
		return false;
	}
	if ($('input[name=guestPhoneUp]').val() == '') {
		window.wxc.alert("上家电话不能为空!");
		$('input[name=guestPhoneUp]').focus();
		return false;
	}
	if ($('input[name=guestNameDown]').val() == '') {
		window.wxc.alert("下家姓名不能为空!");
		$('input[name=guestNameDown]').focus();
		return false;
	}
	if ($('input[name=guestPhoneDown]').val() == '') {
		window.wxc.alert("下家电话不能为空!");
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
			window.wxc.alert("上家电话为必填项!");
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
			window.wxc.alert("下家电话为必填项!");
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
							window.wxc.alert("上下家电话不能填写一样!");
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
		window.wxc.alert("电话号码只能由数字组成！");
		isValid = false;
		return isValid;
	}
	if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13 || mobile.length ==14)){				
		window.wxc.alert("电话号码只能由是8位、11位、13位或者14位的数字组成！");
		isValid = false;
		return isValid;
	}
	
	if(isUniqueChar(mobile)){
		window.wxc.alert("电话号码不能为全部相同的数字！");
		isValid = false;
		return isValid;
	}
	return isValid;
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
	
	return addr;	
}
