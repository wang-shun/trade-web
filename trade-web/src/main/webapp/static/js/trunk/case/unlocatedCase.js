/**
 * 佣金管理 wanggh
 */

$(document).ready(function() {
	// Examle data for jqGrid
	// Configuration for jqGrid Example 1
	loadGrid();

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list_1').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
					
});

// select控件
var config = {
	'.chosen-select' : {},
	'.chosen-select-deselect' : {
		allow_single_deselect : true
	},
	'.chosen-select-no-single' : {
		disable_search_threshold : 10
	},
	'.chosen-select-no-results' : {
		no_results_text : 'Oops, nothing found!'
	},
	'.chosen-select-width' : {
		width : "95%"
	}
};

for ( var selector in config) {
	$(selector).chosen(config[selector]);
};

// 日期控件
$('#datepicker').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

function loadGrid(){
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	//jqGrid 初始化
	$("#table_list_1").jqGrid("GridUnload");
	$("#table_list_1").jqGrid({
		url : url,
		mtype : 'POST',
		datatype : "json",
		height : 600,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 20,
		/*   rowList: [10, 20, 30], */
		colNames : [ '案件编号', '案件编号', '产证地址', '经纪人', '组别', '区经/区总','导入时间'],
		colModel : [ {
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			width : 60
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 250
		}, {
			name : 'AGENT_NAME',
			index : 'AGENT_NAME',
			width : 30
		}, {
			name : 'ORG_NAME',
			index : 'ORG_NAME',
			width : 90
		}, {
			name : 'LEADER',
			index : 'LEADER',
			width : 30
		}, {
			name : 'IMPORT_TIME',
			index : 'IMPORT_TIME',
			width : 40
		},
		],
		multiselect: true,
		pager : "#pager_list_1",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		onSelectRow : function(rowid,status) {
			if(status){
				$("#caseChangeTeamButton").attr("disabled", false);
    		}else{
	    		var ids=$("#table_list_1").jqGrid("getGridParam","selarrrow");
	    		if(ids.length==0){
	        		$("#caseChangeTeamButton").attr("disabled", true);
	    		}
    		}
		},
		onSelectAll :function(aRowids,status) {
			if(status){
    		    $("#caseChangeTeamButton").attr("disabled", false);
    		}else{
	        	$("#caseChangeTeamButton").attr("disabled", true);
    		}
		},
		postData : {
			queryId : "queryUnlocatedCase",
			search_proAddr:$("#txt_prd_addr").val(),
			distCode:$("#distCode").val(),
			search_dtBegin:$("#dtBegin_0").val(),
			search_dtEnd:$("#dtEnd_0").val()?($("#dtEnd_0").val()+' 23:59:59'):$("#dtEnd_0").val(),
			argu_oriGrpId:$("#oriGrpId").val()
		}

	});
}

function clean(){
	$("input[name='txt_prd_addr']").val('');
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("input[name='radioOrgName']").val('');
	$("input[name='oriGrpId']").val('');
	$("#distCode ").get(0).selectedIndex=0;
}
/**
 * 案件转组初始化
 */
function caseChangeTeam(){
	showTeamModal();
	/*var url = "/case/getAllTeamList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
	    data : "", 
		success : function(data) {
			showTeamModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
*/}

/**
 * 选择组别modal
 * @param data
 */
function showTeamModal(data){
	/*var inHtml = '';
	inHtml+='<div class="form-group"><label class="col-lg-3 control-label">';
	inHtml+= '请选择组别：';
	inHtml+='</label><div class="col-lg-9" style="text-align:left; margin-top:-10px;" >';
	$.each(data,function(i, n){
		inHtml+='<div class="checkbox i-checks"><label>';
		inHtml+='<input type="radio" name="teamRadio" value="'+n.id+'"/>  '+n.orgName+' </label></div>';
	})
	inHtml+='</div></div>';
	$("#team-form").html(inHtml);*/
	$('#team-modal-form').modal("show");
}

/**
 * 案件转组
 * @param index
 */
/*function changeCaseTeam(){
	

		var orgName=$("#radioOrgName1").val();
		var orgId=$("#oriGrpId1").val();
		if(orgName==''||orgId==''){
			alert('请选择一个片区');
			return false;
		}
		var url = "/case/bindUnLocatedCaseTeam";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCodes=$("#table_list_1").jqGrid("getGridParam","selarrrow");
		var params='&orgId='+orgId+'&caseCodes='+caseCodes;
		
		$.ajax({
			cache : false,
			async:true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout: 10000,
		    data : params, 
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },  
            complete: function() {  
                $.unblockUI();   
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
			success : function(data) {
				if(data.success){
					alert("分配成功");
					$('#team-modal-form').modal("hide");
					//jqGrid reload
					$("#table_list_1").trigger('reloadGrid');
				}else{
					alert(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
}*/

//选组织的回调函数
function radioOrgSelectCallBack1(array){
    if(array && array.length >0){
        $("#radioOrgName1").val(array[0].name);
		$("#oriGrpId1").val(array[0].id);
		
/*		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
		$("#oldactiveName").attr("onclick",userSelect);*/
	}else{
		$("#radioOrgName1").val("");
		$("#oriGrpId1").val("");
	}
}
//选组织的回调函数
function radioOrgSelectCallBack(array){
    if(array && array.length >0){
        $("#radioOrgName").val(array[0].name);
		$("#oriGrpId").val(array[0].id);
		
/*		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
		$("#oldactiveName").attr("onclick",userSelect);*/
	}else{
		$("#radioOrgName").val("");
		$("#oriGrpId").val("");
	}
}

