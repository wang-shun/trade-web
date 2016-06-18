/**
 * 已受理产调
 * liaohail
 * 
 */
var ctx = $("#ctx").val();
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var prDistrictId = $("#prDistrictId").val();
	var prStatus = $("#prStatus").val();
	url = ctx + url;
	$("#btn_save").click(function() {
		save(false);

	});
	$("#btn_done").click(
			function() {
				save(true);
			});
	$("input[name='isScuess']").on('click',function(){
		if(!!~~$(this).val()){
			/*$("#div_s").css('display','initial');*/
			$("#div_s").show();
			$("#div_f").hide();
			$('#unSuccessReason').val('');
		}else{
			/*$("#div_f").css('display','initial');*/
			$("#div_f").show();
			$("#div_s").hide();
		}
	});
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
		colNames : [ 'PKID','行政区域','交易单编号','产调编号','物业地址', '产调项目','所属分行',
		             '产调申请人', '产调申请时间',
		             '产调受理时间','状态','附件','是否有效','无效原因','操作'],
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
			
		},{
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			align : "center",
			width : 0,
			hidden : true
		},{
			name : 'PR_CODE',
			index : 'PR_CODE',
			align : "center",
			width : 0,
			hidden : true
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 60
		}, {
			name : 'PR_CAT',
			index : 'PR_CAT',
			width : 40
		},  {
			name : 'orgName',
			index : 'orgName',
			width : 40
		},{
			name : 'PR_APPLIANT',
			index : 'PR_APPLIANT',
			width : 40
		}, {
			name : 'PR_APPLY_TIME',
			index : 'PR_APPLY_TIME',
			width : 50
		}, {
			name : 'PR_ACCPET_TIME',
			index : 'PR_ACCPET_TIME',
			width : 50
		},{
			name : 'PR_STATUS',
			index : 'PR_STATUS',
			width : 20
		},{
			name : 'act',
			index : 'act',
			align : "center",
			width : 40,
			resizable : false,
			sortable : false,
			title : false
		},{
			name : 'IS_SUCCESS',
			index : 'IS_SUCCESS',
			width : 30
		},{
			name : 'UNSUCCESS_REASON',
			index : 'UNSUCCESS_REASON',
			width : 30
		},{
			name : 'nullityTag',
			index : 'nullityTag',
			align : "center",
			width : 60,
			formatter : nullityTag
			
		},
		
		],
		pager : "#pager_property_list",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
		postData : {
			queryId : "queryProcessingList",
			search_prDistrictId : prDistrictId,
			search_prStatus : prStatus
		}
	});
	function nullityTag(cellvalue, options, item){
		var outHtml=
			"&nbsp;<button type=\"button\" class=\"btn btn-warning btn-xs\" id=\"teamCode\" name=\"teamCode\" readonly=\"readonly\" "
				   +"onclick=\"showOrgSelect("+item.PKID+")\" value='' >转组</button>";
			
			
		var btn2="<button type='button' onclick=\"showAttchBox('"+item.CASE_CODE+"','"+item.PR_CODE+"','"+item.PART_CODE+"','"+item.PKID+"','"+item.IS_SUCCESS+"','"+(item.UNSUCCESS_REASON?item.UNSUCCESS_REASON:'')+"');\" class='btn btn-warning btn-xs'>处理</button>";
		if(optTransferRole){return btn2=btn2+outHtml; }
			return btn2;
	}
	
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_property_list').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

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
	function reloadGrid(){
		$('#table_property_list').jqGrid('setGridParam',{
			datatype:'json',  
			mtype : 'POST',
			postData: packData()
		}).trigger('reloadGrid');
	}
	
	function showAttchBox(cd, pr, pc, id,isS,uns) {

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
		
		$("input[name='isScuess'][value='"+isS+"']").attr('checked',true).click();
		if(uns){
			$('#unSuccessReason').val(uns);
		}
		$("#modal-form").modal("show");
	}
	
	function getAttchInfo() {
	
		$
				.ajax({
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