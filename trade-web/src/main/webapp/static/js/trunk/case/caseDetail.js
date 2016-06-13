/**
 * 案件详情
 * @wanggh
 */
Array.prototype.contains = function(obj){
	 var i = this.length;
     while (i--) {
         if (this[i] === obj) {
         return true;
         }
     }
     return false;
};
var changeTaskList=['TransSign','PurchaseLimit','Pricing','TaxReview','LoanClose','ComLoanProcess','PSFApply','PSFSign', 'PSFApprove',
                    'LoanlostApply','SelfLoanApprove','Guohu','HouseBookGet','LoanRelease'];
$(document).ready(
		function() {
			$("#sel_changeFrom option").each(function(){
				var _this=$(this);
				if(!changeTaskList.contains(_this.val())){
					_this.remove();
				}
			});
			$("#sel_changeFrom").change(function(){
				$("#changeForm-form").attr('action','../task/'+$("#sel_changeFrom").val());
			});
			$("#sel_changeFrom").change();
			$("#changeForm-form").submit(function(){
				$('#changeForm-modal-form').modal("hide");
			});
			$("#changeForm-form").submit(function(){
				if($("#sel_changeFrom").val()==null||$("#sel_changeFrom").val()==''){
					alert('请选择要修改的项目！');
					return false;
				}
			});
			
			
			//案件挂起
			buttonActivity();
			
			//基本信息等高
			var cpDivHeight = $("#cpDiv").height();
			$('#infoDiv .panel-body').css("height", cpDivHeight + 20);
		
			//jqGrid初始化
			var url = "/quickGrid/findPage";
			var ctx = $("#ctx").val();
			var caseCode = $("#caseCode").val();
			url = ctx + url;
			// Configuration for jqGrid Example 1
			$("#operation_history_table").jqGrid(
					{

						url : url,
						datatype : "json",
						mtype : "POST",
						height : 250,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						/* rowList: [10, 20, 30], */
						colNames : [ 'TASKID', 'CASECODE', 'PARTCODE',
								'INSTCODE', '红绿灯', '红灯记录', '任务名', '执行人', '预计执行时间',
								'执行时间','任务状态' ],
						colModel : [ {
							name : 'ID',
							index : 'ID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'CASE_CODE',
							index : 'CASE_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'PART_CODE',
							index : 'PART_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'INST_CODE',
							index : 'INST_CODE',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'DATELAMP',
							index : 'DATELAMP',
							width : 40,
							editable : true,
							formatter : dateLampFormatter
						}, {
							name : 'RED_LOCK',
							index : 'RED_LOCK',
							width : 30,
							editable : true,
							formatter : isRedFormatter
						}, {
							name : 'NAME',
							index : 'NAME',
							width : 75
						}, {
							name : 'ASSIGNEE',
							index : 'ASSIGNEE',
							width : 75
						}, {
							name : 'EST_PART_TIME',
							index : 'EST_PART_TIME',
							width : 90
						}, {
							name : 'END_TIME',
							index : 'END_TIME',
							width : 90
						},{
							name : 'status',
							index : 'status',
							width : 90
						},

						],
						pager : "#operation_history_pager",
						sortname:'ID',
		    	        sortorder:'desc',
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						// rowid为grid中的行顺序
						onSelectRow : function(rowid) {
						},
						postData : {
							queryId : "queryTaskHistoryItemList",
							argu_casecode : caseCode
						}

					});

			var ctm = $("#ctm").val();
			var caseCode=$("#caseCode").val();
			//FollowPicList.init(ctx,'/quickGrid/findPage','gridTable','gridPager',ctm,caseCode);
			caseflowListitem.init(ctx,'/quickGrid/findPage','gridTable1','gridPager1',caseCode);  // 显示房款监管信息列表
			caseremarkList.init(ctx,'/quickGrid/findPage','caseCommenTable','caseCommenPager',caseCode);  // 显示各个流程的备注信息列表
			
			var width = $('.jqGrid_wrapper').width();
			$('#operation_history_table').setGridWidth(width);
			$('#gridTable').setGridWidth(width-120);
			$('#gridTable1').setGridWidth(width-120);
		// Add responsive to jqGrid
			$(window).bind('resize', function() {
				var width1 = $('.jqGrid_wrapper').width();
				$('#operation_history_table').setGridWidth(width1);
				$('#gridTable').setGridWidth(width1-120);
				$('#gridTable1').setGridWidth(width1-120);

			});
			
			// 房款监督信息
			/*$(window).bind('resize', function() {
				// var width = $('.jqGrid_wrapper').width();
				$('#gridTable1').setGridWidth($(window).width()*0.6);
			});*/
			
			
			$("input:checkbox[name='srvCode']").change(function(){
				changeSrvs();
			});
			
			getShowAttachment();

		});


var lamp1 = $("#Lamp1").val();
var lamp2 = $("#Lamp2").val();
var lamp3 = $("#Lamp3").val();
/**
 * 红绿灯Formate
 * @param cellvalue
 * @returns {String}
 */
function dateLampFormatter(cellvalue) {

	var color='';
	if (cellvalue < lamp1||cellvalue==null)
		return "";
	if (cellvalue < lamp2) {
		color='green';
	} else if (cellvalue < lamp3) {
		color='yellow';
	} else {
		color='red';
	}
	var outDiv = '<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">';
	outDiv+='<div class="sk-double-bounce1" style="background-color:'+color+'"></div>';
	outDiv+='<div class="sk-double-bounce2" style="background-color:'+color+'"></div>';
	outDiv+='</div>';
	return outDiv;
}

function isRedFormatter(cellvalue) {

	var reStr='无';
	if (cellvalue =='1')
		reStr='有';
	return reStr;
}
/**
 * 责任人变更初始化
 */
function showOrgCp() {
	//查询机构交易顾问
	var url = "/case/getUserOrgCpUserList";
	var ctx = $("#ctx").val();
	url = ctx + url;

	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data : "",
		success : function(data) {
			console.info(data);
			showLeadingModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
function imgLoad(img){
 	 img.parentNode.style.backgroundImage="url("+img.src+")";
}
/**
 * 选择交易顾问modal
 * @param data
 */
function showLeadingModal(data) {
	var addHtml = '';
	var ctx = $("#ctx").val();
	$.each(data,function(i, n) {
						addHtml += '<div class="col-lg-4"><div class="contact-box">';
						addHtml += '<a href="javascript:changeLeadingUser(' + i
								+ ')">';
						addHtml += '<div class="col-sm-4"><div class="text-center">';
						addHtml+='<span class="userHead">';
						if(n.imgUrl!=null){
							addHtml += '<img onload="javascript:imgLoad(this)" alt="image" class="himg" src="'+n.imgUrl+'">';
						}
						addHtml+='</span>';
						addHtml += '<div class="m-t-xs font-bold">交易顾问</div></div></div>';
						addHtml += '<div class="col-sm-8">';
						addHtml += '<input id="user_' + i
								+ '" type="hidden" value="' + n.id + '">';
						addHtml += '<h3><strong>' + n.realName
								+ '</strong></h3>';
						addHtml += '<p>联系电话：' + n.mobile + '</p>';
						addHtml += '<p>当前单数：' + n.userCaseCount + '</p>';
						addHtml += '<p>本月接单：' + n.userCaseMonthCount + '</p>';
						addHtml += '<p>未过户单：' + n.userCaseUnTransCount + '</p>';
						addHtml += '</div><div class="clearfix"></div></a>';
						addHtml += '</div></div>';
					})
	$("#leading-modal-data-show").html(addHtml);

	$('.contact-box').each(function() {
		animationHover(this, 'pulse');
	});
	$('#leading-modal-form').modal("show");
}


/**
 * 变更合作对象
 */
function showChangeModal() {
	//查询机构交易顾问
	var url = "/case/changeCoope";
	var ctx = $("#ctx").val();
	var caseCode=$("#caseCode").val();
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
			ChangeModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

// 变更合作对象
function ChangeModal(data) {
	var addHtml = '';
	var aa=0;
	$.each(data.servitemList, function(index, value){
		addHtml+='<div class="row">';
		addHtml += '<div class="col-md-6 wd-31">';
		addHtml += '<div class="form-group mr0">';
			if(value.users !=""&&value.users.length!=0){
				addHtml += "<input type='hidden' name='caseCode' value='"+$('#caseCode').val()+"' />";
				addHtml += "<input type='hidden' name='srvCode' value='"+value.srvCode+"'/>";
			}
			addHtml += "<label class='col-md-3 control-label wd87'>合作项目</label>";
			addHtml += "<div class='col-md-9 wd-64'><p class='form-control-static'>"+value.srvName+"</p></div>"
		addHtml += '</div></div>';
		
		addHtml += '<div class="col-md-6 wd-72">';
			if(value.users !=""&&value.users.length!=0){
				addHtml += "<label class='col-md-2 control-label'>合作顾问</label>";
			}
		addHtml += "<div class=\"col-md-10\">";
		
		if(value.users !=""&&value.users.length!=0){
			addHtml += "<input type='hidden' name='orgId' id='org"+index+"' value='"+value.orgId+"'/>";
			addHtml += "<select class='form-control m-b' id='userChange"+index+"' name='myProcessorId'>";
			aa=index;
			$.each(value.users, function(index, value){
				// 让修改后的复选框默认被选中
				if(data.servitemList[aa].processorId==value.id){
					addHtml += "<option value='"+value.id+"' selected='selected'>"+value.realName+"("+value.orgName+")"+"</option>";
				}else{
					addHtml += "<option value='"+value.id+"'>"+value.realName+"("+value.orgName+")"+"</option>";
				}
			});
			addHtml += "<option value='-1'>---跨区选择---</option>";
			addHtml += "</select>";
			addHtml += "<input type='hidden'  id='processorId"+index+"' name='processorId' value=''/>";
			addHtml += "<input type='hidden' name='oldOrgId' id='oldOrg"+index+"' value='"+value.orgId+"'/>";
			
			$('#processorId'+index).val($('#userChange'+index).find(":selected").val());
			
		}else{
			
		}
		addHtml += "</div></div>";
		addHtml += '</div>';
		addHtml += '</div>';
		
	});
	
	$("#change-modal-data-show").html(addHtml);

	$('.change-box').each(function() {
		animationHover(this, 'pulse');
	});
	$('#change-modal-form').modal("show");
}


/*点击生成或清除合作顾问下拉框*/
$(document).on("change",'select[name="myProcessorId"]',function(){
	var pros=$('select[name="myProcessorId"]');
	$.each(pros,function(i,items){
		var parent = $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-10');
		var org = parent.children(':hidden:eq(0)');
		var oldOrg = parent.children(':hidden:eq(2)');
		var consult = parent.children(':hidden:eq(1)');
		if($('select[name="myProcessorId"]:eq('+i+')').find(":selected").val()=='-1'){
			org.val('');
			consult.val('');
			if($("#corss_area"+i).length==0){
				var corsstxt="";
				corsstxt += "<div class='col-md-12 wd445' id='corss_area"+i+"'>";
				corsstxt += "<select name='crossProcessorId' id='consult"+i+"'>";
				corsstxt += "<option value='0'>----人员----</option>";
				corsstxt += '</select>';
				corsstxt += "<select name='crossOrgId' id='org"+i+"'>";
				corsstxt += "<option value='0'>----组别----</option>";
				corsstxt += '</select>';				
				corsstxt += "<select id='district"+i+"'>";
				corsstxt += "<option value='0'>----部门----</option>";
				corsstxt += '</select></div>';
				parent.after(corsstxt);
				crossAreaCooperation(i);
			}
		}else{
			if(org.val()==''){
				org.val(oldOrg.val());
			}
			consult.val($('select[name="myProcessorId"]:eq('+i+')').find(":selected").val());
			if($("#corss_area"+i).length>0){
				removeCrossAreaCooperation(i);
			}					
		}	
	});
});

/*生成跨区合作选项框*/
function crossAreaCooperation(i){
	
	var ctx = $("#ctx").val();
	var url = ctx +"/task/firstFollow/getCrossAeraCooperationItems";
	
	$.ajax({
		cache : true,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		success : function(data) {
			
			/*三级联动*/
			var district = $('#district'+i);
			var org = $('#org'+i);
			var consult = $("#consult"+i);
			var districtStr="";
			
			$.each(data.cross,function(j,items){
				districtStr += "<option value='"+ items.districtId+"'>" + items.districtName+"</option>";
			});
			district.empty().append("<option value='0'>----部门----</option>"+districtStr);
			
			district.bind("change", function(){
				var orgStr="";
				var myIndex = district.find(":selected").index()-1;
				$.each(data.cross[myIndex].orgs, function(l, items){
					orgStr += "<option value='"+items.orgId+"'>"+items.orgName+"</option>";
				})
				org.empty().append("<option value='0' selected='selected'>----组别----</option>"+orgStr);
				var val1 = org.find(":selected").val();
				if(val1!='0'){
					changeConsult();
				}
			});
			
			org.bind("change", changeConsult);
			function changeConsult(){
				var consultStr="";
				var index1 = district.find(":selected").index()-1;
				var index2 = org.find(":selected").index()-1;
				$.each(data.cross[index1].orgs[index2].userItems, function(k,items) {
					consultStr += "<option value='"+items.id+"'>"+items.realName+"("+items.count+"件)</option>";
				});
				consult.empty().append("<option value='0'>----人员----</option>"+consultStr);
				if(consultStr == ""){
					consult.empty();
					consult.append("<option value='0'>----人员----</option>");
				}
				getVals();
			}
			
			consult.bind("change", getVals);
			/*改变隐藏框的值*/
			function getVals(){
				var guwen=consult.find(':selected').val();
				var zuzhi=org.find(':selected').val();
				
				if(guwen!='0'){
					 $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-10').children(':hidden:eq(0)').val(zuzhi);
					 $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-10').children(':hidden:eq(1)').val(guwen);
//					 alert($('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-10').children(':hidden:eq(0)').val()+""
//							 +$('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-10').children(':hidden:eq(1)').val());
				}
			}
			
		},
		error : function(errors) {
			alert("数据出错。");
		}
	});
}

/*删除跨区合作的DOM节点*/
function removeCrossAreaCooperation(i){
	$("#corss_area"+i).remove();
}

/*跨区合作表单校验*/
function check(){
	var crossAreas= $('.col-md-12 wd445');
	if(crossAreas.length==0){
		return true;
	}else if(crossAreas.length>0){
		$.each(crossAreas, function(i,items){
			 var crossProcessorId = items.children('select[name="crossProcessorId"]').find(':selected').val();
			 alert(crossProcessorId)
			if(crossProcessorId=='0'){
				alert("跨区合作交易顾问不能为空!");
				return false;
			}
		});
	}
	return true;
}

/**
 * 变更责任人
 * @param index
 */
function changeLeadingUser(index) {
	if (confirm("您是否确认进行责任人变更")) {
		var caseCode = $("#caseCode").val();
		var instCode = $("#instCode").val();
		var userId = $("#user_" + index).val();

		var url = "/case/changeLeadingUser";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var params = '&userId=' + userId + '&caseCode=' + caseCode+"&instCode="+instCode;

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
					alert("变更成功");
					location.reload();
				}else{
					alert(data.message);
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}
/**
 * 产调发起
 * 
 */
function startCasePrairses() {
	if (confirm("您是否确认进行产调发起？")) {

		var url = "/case/startCasePrairses";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var prItems = new Array;
		$("input[name='pr_item']:checked").each(function() {
			prItems.push(this.value);
		});
		if(prItems.length == 0){
			alert("请至少选择一项");
			return;
		}
		var params ='&caseCode=' + caseCode+ '&prItems=' + prItems;

		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
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
					alert("保存成功");
					$('#pr-modal-form').modal("hide");
				}else{
					alert(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}

//初始化服务项
function showSrvModal(){
	resetSrvModal();
	$('#srv-modal-form').modal("show");
}

var srvs;
//初始化服务项
function resetSrvModal(){
	$("input:checkbox[name='srvCode']").removeAttr("checked");
	srvs = $("#srvCodes").val();
	var reHtml= "";
	var srvDicts = $("input:checkbox[name='srvCode']").val();
	if(srvs!=null){
		$.each(srvs.split(","), function(k, v){
			$("input:checkbox[name='srvCode']").each(function() {
				if(this.value==v){
					$(this).prop("checked", true);
					var s = $(this).parent().parent().parent().text();
					reHtml+=s;
					reHtml+=" ";
				}
			});
	  });
	}
	$("#oldSrvs").html(reHtml);
	changeSrvs();
}

//变更服务项
function changeSrvs(){
	var reHtml= "";
	$("input:checkbox[name='srvCode']:checked").each(function() {
		var s = $(this).parent().parent().parent().text();
		reHtml+=s;
		reHtml+=" ";
	});
	$("#selectedSrvs").html(reHtml);
}
var resSrvs = [ '30004001', '30004002'];
var delSrv='30004010';
//保存服务项
function saveSrvItems(){

	if (confirm("您是否确认进行服务项变更？")) {
		var isDel = false;
		var delSrvCheck = $("input[name='srvCode'][value="+delSrv+"]").prop('checked');
		if(srvs.indexOf(delSrv)>-1 && !delSrvCheck){
			isDel=true;
		}else if(delSrvCheck && srvs.indexOf(delSrv)==-1){
			isDel=true;
		}

		var isRes = false;
		var resSrvCheck1 = $("input[name='srvCode'][value="+resSrvs[0]+"]").prop('checked');
		var resSrvCheck2 = $("input[name='srvCode'][value="+resSrvs[1]+"]").prop('checked');
		if(resSrvCheck1 && resSrvCheck2){
			alert("商贷/组合贷和纯公积金贷只允许存在一种！");
			return;
		}else if(resSrvCheck1 && srvs.indexOf(resSrvs[0])==-1){
			isRes = true;
		}else if(!resSrvCheck1 && srvs.indexOf(resSrvs[0])>0){
			isRes = true;
		}else if(resSrvCheck2 && srvs.indexOf(resSrvs[1])==-1){
			isRes = true;
		}else if(!resSrvCheck2 && srvs.indexOf(resSrvs[1])>0){
			isRes = true;
		}
		var url = "/case/saveSrvItems";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var prItems = new Array;
	
		$("input[name='srvCode']:checked").each(function() {
			prItems.push(this.value);
		});
		var params ='&caseCode=' + caseCode+ '&prItems=' + prItems+ '&isDel=' + isDel+ '&isRes=' + isRes+"&srvs="+srvs;
		var confirmStr = "您的选择会进行流程重启，是否继续？";
		if(isDel)confirmStr = "您的选择会进行案件爆单操作，是否继续？";

		
		if ((!isRes&&!isDel )|| confirm(confirmStr)){
			$.ajax({
				cache : false,
				async : true,
				type : "POST",
				url : url,
				dataType : "json",
				timeout : 10000,
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

						if(data.message!="变更成功！"){
							window.location.reload();
							window.location.href=ctx+"/task/ServiceChangeApply?&caseCode="+caseCode +"&taskId="+data.content;
						}else{
							alert("保存成功");
							window.location.reload();
							changeSrvsHidden();
							$('#srv-modal-form').modal("hide");
						}
					}else{
						alert(data.message);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	}
}
function changeSrvsHidden(){
	var url = "/case/getSrvsByCaseCode";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
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
				$("#srvCodes").val(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
//初始化交易计划
function showPlanModal(){
	resetPlanModal();
	$('#plan-modal-form').modal("show");
}
//重置交易计划
function resetPlanModal(){
	$("input[name='estPartTime']").val("");
	var url = "/case/getTransPlanByCaseCode";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data : params,
		success : function(data) {

			var inHtml = "";
			$.each(data, function(k, v){
				inHtml+='<div class="form-group"><div class="col-lg-2 control-label">';
				inHtml+= '预计'+v.partName+'时间';
				inHtml+='</div><div class="col-lg-4 control-label" style="text-align:left; margin-top:-10px;" >';
				inHtml+='<input type="hidden" id="pkId_'+k+'" name="estId" value="'+v.pkid+'" >';
				inHtml+='<input type="hidden" id="isChange_'+k+'" name="estFlag" value="false" >';
				inHtml+='<span style="position: relative; z-index: 9999;">';
				inHtml+='<div class="input-group date"><span class="input-group-addon">';
				inHtml+='<i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>';
				inHtml+='<input class="form-control" type="text" id="estPartTime_'+k+'" name="estPartTime" value="'+v.estPartTimeStr+'" onchange="javascript:changeEstTime('+k+')">';
				inHtml+='</div>	</span></div>';
				inHtml+='<div class="col-lg-1 control-label">';
				inHtml+= '变更理由';
				inHtml+='</div><div class="col-lg-3 control-label" style="text-align:left; margin-top:-10px;" >';
				inHtml+='<input class="form-control" type="text" id="whyChange_'+k+'" name="whyChange" value="">';
				inHtml+='</div>';
				inHtml+='</div>';

			});
			$("#plan-form").html(inHtml);
			 $('.input-group.date').datepicker({
				  todayBtn: "linked",
	                keyboardNavigation: false,
	                forceParse: false,
	                calendarWeeks: true,
	                autoclose: true
	            });
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}

function changeEstTime(index){
	$("#isChange_"+index).val("true");
}
//交易计划变更 - 保存
function savePlanItems(){
	var url = "/case/savePlanItems";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
	var isChanges = new Array;
	var estIds = new Array;
	var estTimes = new Array;
	var whyChanges = new Array;
	var msg = "";

	$("input:hidden[name='estFlag']").each(function(k) {
		if($(this).val() == 'true'){
			var whyChange = $("#whyChange_"+k).val();
			if(whyChange==""||whyChange.trim==""){
				msg = "请输入变更理由";
				return false;
			}
		}
		isChanges.push($(this).val());
	});

	$("input:text[name='estPartTime']").each(function(k) {
		if($(this).val()==""||$(this).val().trim==""){
			msg = "交易计划不允许为空";
			return false;
		}
		estTimes.push($(this).val());
	});
	$("input:hidden[name='estId']").each(function() {
		estIds.push($(this).val());
	});
	$("input:text[name='whyChange']").each(function() {
		whyChanges.push($(this).val());
	});
	if(msg!=""){
		alert(msg);
		return false;
	}
	params+="&isChanges="+isChanges+"&estIds="+estIds+"&estDates="+estTimes+"&whyChanges="+whyChanges;
	
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
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
				alert("保存成功");
				$('#plan-modal-form').modal("hide");
			}else{
				alert(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
//交易计划变更记录
function openTransHistory(){
	var url = "/task/transPlan/showTransPlanHistory?";
	var ctx = $("#ctx").val();
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
	url = ctx + url + params;
	window.location.href= url;
}
//案件挂起
function casePause(){
	var activityFlag = $("#activityFlag").val();
	var showStr = "";
	if(activityFlag == "30003003"){
		showStr = "您是否确认进行案件挂起操作？";
	}else if(activityFlag == "30003004"){
		showStr = "您是否确认进行案件恢复操作？";
	}
	if(confirm(showStr)){
		var url = "/case/casePause";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var params ='&caseCode=' + caseCode;
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
					alert("操作成功");
					$("#activityFlag").val(data.content);
					buttonActivity();
				}else{
					alert(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}
function buttonActivity(){
	var activityFlag = $("#activityFlag").val();
	if(activityFlag == "30003003"){
		$('.btn-activity').show();
		$('#casePause').text("案件挂起");
		$('#casePause').show();
		$('#caseResetes').text("案件重置");
		$('#caseResetes').show();
		$('#processRestart').text("流程重启");
		$('#processRestart').show();
		$('#buttonDiv').show();
	}else{
		if(activityFlag=="30003004"){
			$('#casePause').text("案件恢复");
			$('#casePause').show();
			$('.btn-activity').hide();
			$('#caseResetes').text("案件重置");
			$('#caseResetes').show();
			$('#processRestart').text("流程重启");
			$('#processRestart').show();
			$('#buttonDiv').show();
		}else{
			$('.btn-activity').hide();
			$('#casePause').hide();
			$('#caseResetes').text("案件重置");
			$('#caseResetes').show();
			$('#processRestart').text("流程重启");
			$('#processRestart').show();
		}
	}
}
//清空表单
function checkAllPRItem() {
	$("input[name='pr_item']").prop("checked", true);
}
//清空表单
function cleanPRItem() {
	$("input[name='pr_item']").removeAttr("checked");
}

function startSpvOutApplyProcess(caseCode){
	if(confirm("点击该按钮将会启动资金监管解除审批流程，您确定要启动该流程吗？")){
		$.ajax({
			url:ctx+"/spv/startSpvOutApplyProcess",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode},
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
			success:function(data){
                $.unblockUI();   
				if(!data.success){
					alert(data.message);
				}else{
					window.location.href=ctx+"/task/spvOutApply?taskId="+data.content.taskId+"&instCode="+data.content.processInstanceId+"&caseCode="+data.content.caseCode;
				}
			}
		});
	}
}
/**
 * 案件转组初始化
 
function caseChangeTeam(){
	var url = "/case/getOrgTeamList";
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
}
/**
 * 选择组别modal
 * @param data
 */
function showTeamModal(data){
	var inHtml = '';
	inHtml+='<div class="form-group"><label class="col-lg-3 control-label">';
	inHtml+= '请选择组别：';
	inHtml+='</label><div class="col-lg-9" style="text-align:left; margin-top:-10px;" >';
	$.each(data,function(i, n){
		inHtml+='<div class="checkbox i-checks"><label>';
		inHtml+='<input type="radio" name="teamRadio" value="'+n.id+'"/>  '+n.orgName+' </label></div>';
	})
	inHtml+='</div></div>';
	$("#team-form").html(inHtml);
	$('#team-modal-form').modal("show");
}
function showChangeFormModal(){
	$('#changeForm-modal-form').modal("show");
}
/**
 * 案件转组
 * @param index
 */
function changeCaseTeam(){
	var orgName =$('input[name="teamRadio"]:checked').parent().text();
	if(confirm("您是否确认分配给"+orgName+"?")){

    	var orgId =$('input[name="teamRadio"]:checked').val();
		var url = "/case/orgChange";
		var ctx = $("#ctx").val();
		url = ctx + url;
		
		var params='&orgId='+orgId+'&caseCode='+$("#caseCode").val();
		
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
					alert("转组成功");
					$('#team-modal-form').modal("hide");
				}else{
					alert(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
	}
}
function serviceRestart(){
	if(confirm('点击该按钮将会启动流程重启审批流程，您确定要启动该流程吗？')){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/service/restart",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode},
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		        }
		   } , success:function(data){
				if(!data.success){
					$.unblockUI();   
					alert(data.message);
				}else{
					window.location.href=ctx+"/task/serviceRestartApply?taskId="+data.content.activeTaskId+"&instCode="+data.content.id+"&caseCode="+caseCode;
				}
			}
		});
	}
}
function caseReset(){
	if(confirm('您的操作将恢复案件至未分单状态，是否确定要重置案件？')){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/case/reset",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode},
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		        }
		   } , success:function(data){
				if(!data.success){
					$.unblockUI();   
					alert(data.message);
				}else{
					alert("案件重置成功.");
					window.location.reload();
				}
			}
		});
	}
}

