$(document).ready( function() {
//	debugger;
   $("#submitBtn").click(function(){
 	  if(!checkFormSubmit() && checkForm()){
 		  //return false;
		  window.wxc.confirm("确定提交任务吗？",{"wxcOk":function(){
			  submitNewRansom();
		  }}); 
 	  }
 });
   
   
   function submitNewRansom(){
	  var totalArr = [];
	  var caseCode = $('#caseCode').val();
	  
	  var borrowerName = $("#custName").val();//主贷人姓名
	  var signTime = $("#date-picker2").val();
	  var planTime = $("#date-picker1").val();
	  
	  var finOrgCode = "";
	  var mortgageType = "";
	  var diyaType = "";
	  var loanMoney = "";
	  var restMoney = "";
	  var length = $("select[name='finalOrg']").length;
	  var borroMoney = $("#allLoanMoney").val();
	  var jsonStr = new Array();
	  for(var i = 0; i < length;i++){
		  finOrgCode=$("#trId" + i + " td select[name='finalOrg']" ).val();
		  mortgageType=$("#trId" + i + " td select[name='finalType']" ).val();
		  diyaType=$("#trId" + i + " td select[name='diyaType']" ).val();
		  loanMoney=$("#trId" + i + " td input[name='loanMoney']" ).val();
		  restMoney=$("#trId" + i + " td input[name='restMoney']" ).val();
		  var resJson = {
			  caseCode:caseCode, //案件编号
			  signTime:signTime, //受理时间
			  planTime:planTime, //计划申请时间
			  finOrgCode:finOrgCode, //尾款机构
			  mortgageType:mortgageType, //尾款类型
			  diyaType:diyaType, //抵押类型
			  loanMoney:loanMoney, //贷款金额
			  restMoney:restMoney, //剩余金额
			  borroMoney:borroMoney, //总金额
			  borrowerName:borrowerName
		  };
		  jsonStr.push(resJson);
	  }
	  console.log(jsonStr.length);
   	  $.ajax({
   		url:ctx + "/ransomList/addRansom",
   		method:"post",
   		dataType:"json",
   		data:{
   			jsonStr:JSON.stringify(jsonStr)
   		},
		beforeSend:function(){  
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
         },
        complete: function() {
             $.unblockUI(); 
             if(status=='timeout'){ //超时,status还有success,error等值的情况
          	  Modal.alert(
			  {
			    msg:"抱歉，系统处理超时。"
			  }); 
            } 
        },   
       
		success : function(data) {
			
			if(data.status == "0"){
				window.wxc.success("任务提交成功，赎楼详情单已生成",{"wxcOk":function(){
					window.location.href = ctx+"/ransomList/ransomCase/myRansom_list";
				}});  	 
			}else{
				console.log(data);
				window.wxc.error("任务提交失败,请重新填写信息！",{"wxcOk":function(){
//					window.location.href = ctx+"/ransomList/ransomCase/addRansom";
				}});  	 
			}     
			 $.unblockUI();
		},		
		error : function(errors) {
			$.unblockUI();   
			window.wxc.error("数据保存出错！");
		}  
    });
   }
}); 
 
 function checkFormSubmit(){
 	var ds = $('.case_content').css('display');
	if(ds=='none'){
		window.wxc.alert("请选择关联案件！");
		return false;	
	}
 }

 function checkForm(){
		//主贷人姓名
	  if($("#custName").val() == "" || $("#custName").val() == null){
		  $.unblockUI();
		 window.wxc.alert("主贷人姓名不能为空！");
		 return false;
	  }
	  //手机号码
	  if($("#phone").val() == "" || $("#phone").val() == null){
		  $.unblockUI();
		 window.wxc.alert("手机号码不能为空！");
		 return false;
	  }
	  // 受理时间
	  if($("#date-picker2").val() == "" || $("#date-picker2").val() == null){
		  $.unblockUI();
		 window.wxc.alert("受理时间不能为空！");
		 return false;
	  }
	  
	  //申请时间
	  if($("#date-picker1").val() == "" || $("#date-picker1").val() == null){
		  $.unblockUI();
		  window.wxc.alert("计划申请时间不能为空！");
		  return false;
	  }
	  
	  //尾款机构
	  if($("#finalOrg").val() == "" || $("#finalOrg").val() == null){
		  $.unblockUI();
		  window.wxc.alert("尾款机构时间不能为空！");
		  return false;
	  }
	  //尾款类型
	  if($("#finalType").val() == "" || $("#finalType").val() == null){
		  $.unblockUI();
		  window.wxc.alert("尾款类型时间不能为空！");
		  return false;
	  }
	  //抵押类型
	  if($("#diyaType").val() == "" || $("#diyaType").val() == null){
		  $.unblockUI();
		  window.wxc.alert("抵押类型时间不能为空！");
		  return false;
	  }
	  //贷款金额
	  if($("#loanMoney").val() == "" || $("#loanMoney").val() == null){
		  $.unblockUI();
		  window.wxc.alert("贷款金额时间不能为空！");
		  return false;
	  }
	  //剩余金额
	  if($("#restMoney").val() == "" || $("#restMoney").val() == null){
		  $.unblockUI();
		  window.wxc.alert("剩余金额不能为空！");
		  return false;
	  }
	  return true;
}
 function changeRestMoney(){
	 var allLoanMoney = 0;
	 for(var i = 0; i < $("select[name='finalOrg']").length;i++){
		 allLoanMoney += parseInt($("#trId" + i + " td input[name='restMoney']").val() * 1000000);
	 }
	 
	$("#allLoanMoney").val(allLoanMoney / 1000000);
 }

 /**
  * 检查案件编号和赎楼编号是否唯一
  * @returns
  */
 function checkLink(){
	 var caseCode = $("#modal_caseCode0").html();
	 
	 $.ajax({
			url:ctx+ "/ransomList/queryRansomCode" ,
			method: "GET",
			dataType: "json",
			async: false,
			data: {caseCode:caseCode},
			success : function(data) {
				debugger;
				if(data.status == "0"){
					window.wxc.success("案件关联成功！"); 
					$('.case_content').show();
					$("#custName").val();
					$("#phone").val();
				}else{
					console.log(data);
					window.wxc.error("案件关联已被关联，请重新选择！");  
					$(".case_content").hide();
					$("#custName").val("");
					$("#phone").val("");
				}     
				 $.unblockUI();
			},		
			error : function(errors) {
				$.unblockUI();
				window.wxc.alert("操作失败！");
			} 
		});
 }
//添加机构
var index = 1;
var count = $('#loanLostFinOrgName option:last').index();
function addOrg() {
	
	var txt='<tr id="trId' + index + '"><td><select name="finalOrg"';
	txt +='	id="finalOrg" class="select_control yuanwid ">';
	txt += $("#finalOrg").html()
    txt += '</select></td>';
	txt +='<td ><select type="text" name="finalType" id="finalType" class="select_control yuanwid ">'
	txt +=$("#finalType").html()
	txt+= '	/></td>';
	txt +='<td ><select type="text" name="diyaType" id="diyaType" class="select_control yuanwid ">'
	txt +='<option>请选择</option><option value="710150002">二抵</option></select></td>'	
//	txt +=$("#diyaType").html()
//	txt+= '	/></td>';
	txt +='<td><input type="text" name="loanMoney" id="loanMoney" class="form-control input-one" placeholder="贷款金额(单位：万元)"  /></td>';
	txt +='<td><input type="text" name="restMoney" id="restMoney" class="form-control input-one" placeholder="剩余金额(单位：万元)"  onchange="changeRestMoney()" /></td>';
	txt +='<td><span><a href="javascript:removeTr('+ index + ');"><font>删除</font></a></span></td></tr></tbody>';

	$("#addInput").before(txt);
	$("#addMoneyLine").css("display","none");
//	index++;
}

function removeTr(index) {
	$("#trId" + index).remove();
	$("#addMoneyLine").css("display","block");
	
	setMoney();
}

function setMoney(){
	var restMoney0 = parseInt($("#restMoney").val());
	var allLoanMoney = $("#allLoanMoney").val(restMoney0);
}


