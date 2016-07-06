/**
 * 已受理产调
 * liaohail
 *
 */
var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prStatus = $("#prStatus").val();

$(document).ready(function() {

	$("#btn_save").click(function() {
		save(false);
	});

	$("#btn_done").click(function() {
		save(true);
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

	// 初始化列表
	var data = {};
    data.search_prDistrictId = prDistrictId;
    data.search_prStatus = prStatus;
    data.optTransferRole = optTransferRole;

	$("#processingList").aistGrid({
		ctx : ctx,
		queryId : 'queryProcessingList',
	    templeteId : 'template_processingList',
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

	// 查询
	$('#addrSearchButton').click(function() {
		reloadGrid();
	});

	$('#processingList table').addClass("apply-table");

});

function reloadGrid() {
	var data = getParams();
    $("#processingList").reloadGrid({
    	ctx : ctx,
		queryId : 'queryProcessingList',
	    templeteId : 'template_processingList',
	    data : data,
	    wrapperData : data
    });
};

function getParams() {

	var prDistrictId = $("#prDistrictId").val();
	var prStatus = $("#prStatus").val();
	var propertyAddr =  $("#addr").val();
	var distCode = $("#distCode").val();

	var data = {};

	data.search_propertyAddr = propertyAddr;
	data.search_prDistrictId = prDistrictId;
	data.search_prStatus = prStatus;
	data.optTransferRole = optTransferRole;
	data.search_distCode = distCode;

	return data;
}

/**
 * 处理已受理产调
 */
var pkidLsit ;
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
	//处理产调
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

//	function reloadGrid(){
//		$('#table_property_list').jqGrid('setGridParam',{
//			datatype:'json',
//			mtype : 'POST',
//			postData: packData()
//		}).trigger('reloadGrid');
//	}

	function showAttchBox(cd, pr, pc, id, isS, uns, addr, prcat, applyOrgName, orgMgr) {

		if (cd == null || cd == "") {
			$("#caseCode").val(pr);
		} else {
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
						$
								.each(
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
	}
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
	var optPkid='';

	function checkOrg(o){
		if(o.extendField!='yucui_district'){
			alert('请选择一个贵宾服务总进行转组');
			return false;
		}
		return true;
	}
	function showOrgSelect(id){
		optPkid=id;
		orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
			   startOrgId:'ff8080814f459a78014f45a73d820006',
			   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,departmentHeriarchy:'yucui_team'});
	}
	function radioYuCuiOrgSelectCallBack(array){
		if(array && array.length >0){
			if(checkOrg(array[0])){
				if(confirm('是否确认转组')){

					$("#yuCuiOriGrpId").val(array[0].id);
					doTransfer(optPkid,array[0].id,array[0].name);
				}
			}else{
				return false;
			}
		}
	}
	function doTransfer(pkid,districtId,orgName){
		var transferData={pkid:pkid,districtId:districtId};
		$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
		$(".blockOverlay").css({'z-index':'9998'});
		$.ajax({
			cache : false,
			type : "POST",
			url : ctx+'/property/doChangePrDistrictId',
			dataType : "json",
			data :transferData,
			success : function(data) {
				alert(data.message)
				$.unblockUI();
				if(data.success){
					reloadGrid();
				}
			},
			error : function(errors) {
				alert("处理出错,请刷新后再次尝试！");
				  $.unblockUI();
			}
		});
	}
