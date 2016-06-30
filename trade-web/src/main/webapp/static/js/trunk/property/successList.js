/**
 * 产调处理结果
 * liaohail
 * 
 */
var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prStatus = $("#prStatus").val();

$(document).ready(function() {
	
	var data = {};
    data.search_prDistrictId = prDistrictId;
    data.search_prStatus = prStatus;
    data.optTransferRole = optTransferRole;

    $("#successList").aistGrid({
		ctx : ctx,
		queryId : 'querySuccessList',
	    templeteId : 'template_successList',
	    data : data,
	    wrapperData : data,
	    columns : [{
	    	           colName :"物业地址"
	    	      },{
	    	           colName :"区域分行"
	    	      },{
	    	           colName :"产调申请时间"
    	          },{
	    	           colName :"是否有效"
    	          },{
		    	       colName :"产调申请"
	    	      },{
	    	           colName :"操作"
	    	      }]
	
	});
	
	$("input[name='isScuess']").on('click',function(){
		if(!!~~$(this).val()){
			$("#div_s").show();
			$("#div_f").hide();
			$('#unSuccessReason').val('');
		}else{
			$("#div_f").show();
			$("#div_s").hide();
		}
	});
	
	$("#btn_save").click(function() {
		save(false);
	});
	
	$("#btn_done").click(function() {
		save(true);
	});
	
	$('#addrSearchButton').click(function(){
		reloadGrid();
	});
	
	$('#successList table').addClass("apply-table");
	
	reloadGrid();
});

function reloadGrid(){
	
	var data = getParams();
	
	$("#successList").reloadGrid({
    	ctx : ctx,
		queryId : 'querySuccessList',
	    templeteId : 'template_successList',
	    data : data,
	    wrapperData : data
    });
}

function getParams() {
	
	var prDistrictId = $("#prDistrictId").val();
	var prStatus = $("#prStatus").val();
	var distCode = $("#distCode").val();
	var prCat = $("#prCat").val();
	var grpOrgName = $("#grpOrgName").val();
	var propertyAddr =  $("#addr").val();
	var auUser = $("#auUser").val();
	var acuUser = $("#acuUser").val();
	var isSuccess = $("#isSuccess").val();
	var prChannel = $("#prChannel").val();
	var quds = $("#quds").val();
	var completeTimeStart = $("#completeTimeStart").val();
	var completeTimeEnd = $("#completeTimeEnd").val();
	
	var data = {};
	data.search_prDistrictId = prDistrictId;
	data.search_prStatus = prStatus;
	data.search_distCode = distCode;
	data.search_prCat = prCat;
	data.search_grpOrgName = grpOrgName;
	data.search_propertyAddr = propertyAddr;
	data.search_auUser = auUser;
	data.search_acuUser = acuUser;
	data.search_isSuccess = isSuccess;
	data.search_prChannel = prChannel;
	data.search_quds = quds;
	data.search_completeTimeStart = completeTimeStart;
	data.search_completeTimeEnd = completeTimeEnd?(completeTimeEnd+' 23:59:59'):completeTimeEnd;
	data.optTransferRole = optTransferRole;
	
	return data;
} 


function showAttchBox(cd, pr, pc, id, isS, uns, addr, prcat, applyOrgName, orgMgr) {
	
	if(cd == null || cd == "") {
		$("#caseCode").val(pr);
	}else{
		$("#caseCode").val(cd);
	}
	$("#caseCode").val(pr);
	
	caseCode = pr;
	prCode = pr;
	pkid = id;
	taskitem = pc;
	
	getAttchInfo();
	
	if(isS=='否'){
		isS='0';
	}else{
		isS='1';
	}
	
	$('#address').text(addr);
	$('#prcat').text(prcat);
	$('#applyOrgName').text(applyOrgName);
	$('#orgMgr').text(orgMgr);
	
	$("input[name='isScuess'][value='"+isS+"']").attr('checked',true).click();
	if(uns){
		$('#unSuccessReason').val(uns);
	}
	$("#modal-form").modal("show");
};

function checkForm(){
	if(!~~$('input[name="isScuess"]:checked ').val()){
		if($('#unSuccessReason').val()==''){
			alert('请输入无效原因！');
			return false;
		}
	}
	return true;
}

function save(isSubmit){
	if(isSubmit&&!!~~$('input[name="isScuess"]:checked ').val()){
		if(!checkAttachment()){
			return false;
		}
	}
	if(!checkForm()){
		return false;
	}
	if(!!~~$('input[name="isScuess"]:checked ').val()){
		if(!deleteAndModify()){
			return false;
		}
	}
	if(isSubmit && !!~~$('input[name="isScuess"]:checked ').val()){
		checkIsExistFile(isSubmit);
	}else{
		commitDispose(isSubmit);
	}
}

function getAttchInfo() {
	$.ajax({
		url : ctx + "/attachment/quereyAttachments",
		method : "post",
		dataType : "json",
		data : {
			caseCode : caseCode
		},
		success : function(data) {
			//将返回的数据进行包装
			$("#picContainer1").html("");
			var trStr = "";
			dataLength = 0;
			//实勘描述
			$.each(
				data.attList,
				function(index, value) {
					dataLength++;
					
					trStr += "<div id='picContainers"+value.pkid+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;text-align:center;border-radius:4px;float:left;\">";
					trStr += "<div class=\"preview span12\">";
					trStr += "<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+value.pkid+"\" />";
	
					trStr += "<img src='"+appCtx['img-centanet'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' style='width:80px;height:80px;' alt=''>";
					trStr += "</div>";
					trStr += "<div class=\"delete span2\" style=\"margin-left: 75%; margin-top: -93px;line-height:0;\">";
					trStr += "<button onclick=\"romoveDiv('picContainers',"
							+ value.pkid
							+ ");\" class=\"btn red\"";
					trStr += "style=\"line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;\">";
					trStr += "<i class=\"icon-remove\"></i>";
					trStr += "</button>";
					trStr += "</div>";
					trStr += "</div>";
			});
			$("#picContainer1").append(trStr);
		}
	});
};

function commitDispose(isSubmit){
	var isScuess=$('input[name="isScuess"]:checked ').val();
	$.ajax({
		cache : false,
		type : "POST",
		url : ctx + '/property/saveProcessingList',
		dataType : "json",
		data :  {
			pkid:pkid,
			isScuess :isScuess ,
			unSuccessReason:$("#unSuccessReason").val(),
			isSubmit:!!isSubmit
		} ,
		success : function(data) {
			alert(data.message)
			if (data.success) {
				$("#modal-form").modal("hide");
				reloadGrid();
			}
		},
		error : function(errors) {
			alert("处理出错,请刷新后再次尝试！");
		}
	});
}

function checkIsExistFile(isSubmit){
	$.ajax({
		cache : false,
		type : "GET",
		url : ctx + '/property/isExistFile?prCodeArray='
				+ caseCode,
		dataType : "json",
		data : "",
		success : function(data) {
			if (data.success == false) {
				alert(data.message);
			} else {
				commitDispose(isSubmit);
			}
		},
		error : function(errors) {
			alert("处理出错,请刷新后再次尝试！");
		}
	});
}
