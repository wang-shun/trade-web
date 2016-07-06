/**
 * 产调处理结果
 * liaohail
 * 
 */
var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prStatus = $("#prStatus").val();

$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var prDistrictId = $("#prDistrictId").val();
	var prStatus = $("#prStatus").val();
	//jqGrid 初始化
	$("#table_property_list").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 250,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 8,
		/*   rowList: [10, 20, 30], */
		colNames : [ 'PKID','行政区域','物业地址', '产调项目','所属分行','区董',
		             '产调申请人','申请人员工编号', '产调执行人', '产调申请时间',
		             '产调受理时间','产调完成时间','是否有效','无效原因','来源',
//		             '区董' ,
		             '操作'],
		colModel : [ {
			name : 'PKID',
			index : 'PKID',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		},{
			name : 'DIST_CODE',
			index : 'DIST_CODE',
			align : "center",
			width : 30
			
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 60
		}, {
			name : 'PR_CAT',
			index : 'PR_CAT',
			width : 40
		}, {
			name : 'applyOrgName',
			index : 'applyOrgName',
			width : 40
		},{
			name : 'QUDS',
			index : 'QUDS',
			width : 40
		},{
			name : 'PR_APPLIANT',
			index : 'PR_APPLIANT',
			width : 40
		},{
			name:"APPLIANT_EMPLOYEE_CODE",
			index:"APPLIANT_EMPLOYEE_CODE",
			width:40
		}, {
			name : 'PR_EXECUTOR',
			index : 'PR_EXECUTOR',
			width : 40
		}, {
			name : 'PR_APPLY_TIME',
			index : 'PR_APPLY_TIME',
			width : 50
		}, {
			name : 'PR_ACCPET_TIME',
			index : 'PR_ACCPET_TIME',
			width : 50
		}, {
			name : 'PR_COMPLETE_TIME',
			index : 'PR_COMPLETE_TIME',
			width : 50
		},{
			name : 'IS_SUCCESS',
			index : 'IS_SUCCESS',
			width : 30
		},{
			name : 'UNSUCCESS_REASON',
			index : 'UNSUCCESS_REASON',
			width : 30
		},
		{
			name : 'CHANNEL',
			index : 'CHANNEL',
			width : 30
		},
//		{
//			name : 'QUDS',
//			index : 'QUDS',
//			width : 30
//		},
		{
			name : 'QUDS',
			index : 'QUDS',
			width : 30,
			formatter:function(cellvalue, options, rawObject){
				var a = "<a href='../mobile/property/box/show?prCode="+rawObject.prCode+"' target='_blank'>查看附件</a>";
				var update = "<button type='button' onclick=\"showAttchBox('"+rawObject.CASE_CODE+"','"+rawObject.prCode+"','"+rawObject.PART_CODE+"','"+rawObject.PKID+"','"+rawObject.IS_SUCCESS+"','"+(rawObject.UNSUCCESS_REASON?rawObject.UNSUCCESS_REASON:'')+"');\" class='btn btn-warning btn-xs'>修改</button>";
				return a + update;
			}
		}
		],
		multiselect: false,
		pager : "#pager_property_list",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
	
	var data = {};
    data.search_prDistrictId = prDistrictId;
    data.search_prStatus = prStatus;
    data.optTransferRole = optTransferRole;











































































































		onSelectRow : function(rowid,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		}else{
	    		var ids=$("#table_property_list").jqGrid("getGridParam","selarrrow");
	    		if(ids.length==0){
	        		$("#caseDistributeButton").attr("disabled", true);
	    		}
    		}
		},
		onSelectAll :function(aRowids,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		}else{
	        	$("#caseDistributeButton").attr("disabled", true);
    		}
		},
		postData : {
			queryId : "querySuccessList",
			search_prDistrictId : prDistrictId,
			search_prStatus : prStatus
		}

    $("#successList").aistGrid({
		ctx : ctx,
		queryId : 'querySuccessList',
	    templeteId : 'template_successList',
	    data : data,
	    wrapperData : data,
	    columns : [{
			           colName :"行政区域"
			      },{
	    	           colName :"产证地址"
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

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_property_list').setGridWidth(width);

	});
	
	$('.contact-box').each(function() {
        animationHover(this, 'pulse');
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
	var data = {};
	data.queryId = "querySuccessList";
	data.search_prDistrictId = $("#prDistrictId").val();
	data.search_prStatus = $("#prStatus").val();
	$('#table_property_list').jqGrid('setGridParam',{
		datatype:'json',  
		mtype : 'POST',
		"postData": data
	}).trigger('reloadGrid');
	
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

function showAttchBox(cd, pr, pc, id, isS, uns) {

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
