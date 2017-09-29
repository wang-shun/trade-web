$(document).ready( function() {
   $("#submitBtn").click(function(){
//	   debugger;
 	  if(!checkFormSubmit()){
 		  //return false;
 		  if(!checkForm()){
 			  window.wxc.confirm("确定提交任务吗？",{"wxcOk":function(){
 				  submitNewRansom();
 			  }}); 
 		  }
 	  }
// 	 submitNewRansom();
//	  if($("#handle").val() == null || $("#handle").val() == ''){
//		  window.wxc.confirm("确定提交并开启流程吗？",{"wxcOk":function(){
//			  submitNewRansom();
//		  }}); 	
//	  }else{
//		  window.wxc.confirm("确定提交任务吗？",{"wxcOk":function(){
//			  submitNewRansom();
//		  }}); 
//	  }
 });
   
 
   function checkForm(){
	   		//主贷人姓名
		  //var custName = $("#custName").val();
		  if($("#custName").val() == "" || $("#custName").val() == null){
			 window.wxc.success("主贷人姓名不能为空！");
//			 Modal.alert("主贷人姓名不能为空！"); 
		  }
		  //手机号码
		  //var phone = $("#phone").val();
		  if($("#phone").val() == "" || $("#phone").val() == null){
			 window.wxc.success("手机号码不能为空！");
//			 Modal.alert("手机号码不能为空！");
		  }
		  // 受理时间
		  //var dealTime = $("#date-picker2").val();
		  if($("#date-picker2").val() == "" || $("#date-picker2").val() == null){
			 window.wxc.success("受理时间不能为空！");
//			 Modal.alert("受理时间不能为空！");
		  }
		  
		  //申请时间
		  //var applyTime = $("#date-picker1").val(); 
		  if($("#date-picker1").val() == "" || $("#date-picker1").val() == null){
			  window.wxc.success("计划申请时间不能为空！");
//			  Modal.alert("计划申请时间不能为空！");
		  }
		  
		  //尾款机构
		  if($("#finalOrg").val() == "" || $("#finalOrg").val() == null){
			  window.wxc.success("尾款机构时间不能为空！");
//			  Modal.alert("尾款机构不能为空！");
		  }
		  //尾款类型
		  if($("#finalType").val() == "" || $("#finalType").val() == null){
			  window.wxc.success("尾款类型时间不能为空！");
//			  Modal.alert("尾款类型不能为空！");
		  }
		  //抵押类型
		  if($("#diyaType").val() == "" || $("#diyaType").val() == null){
			  window.wxc.success("抵押类型时间不能为空！");
//			  Modal.alert("抵押类型时间不能为空！");
		  }
		  //贷款金额
		  if($("#loanMoney").val() == "" || $("#loanMoney").val() == null){
			  window.wxc.success("贷款金额时间不能为空！");
//			  Modal.alert("贷款金额不能为空！");
		  }
		  //剩余金额
		  if($("#restMoney").val() == "" || $("#restMoney").val() == null){
			  window.wxc.success("剩余金额不能为空！");
//			  Modal.alert("剩余金额不能为空！");
		  }
		  return false;
   }
   
   
   function submitNewRansom(){
//	   debugger;
	  var totalArr = [];
//	  var path = ctx + "/ransomList/ransomCase/ransomDetail";
	  var path = ctx + "/ransomList/addRansom1";
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
   		url:path,
   		method:"post",
   		dataType:"json",
   		data:{
   			jsonStr:JSON.stringify(jsonStr)
   		},
//   		data:jsonStr,
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

 debugger;
 function changeRestMoney(){
	 var allLoanMoney = 0.0;
	 var sumMoney;
//	 for(var i = 0; i < $("select[name='finalOrg']").length;i++){
//		 allLoanMoney += parseInt($("#trId" + i + " td input[name='restMoney']").val() * 1000000);
//	 }
	 
	 if(addOrg()){
		 allLoanMoney += parseInt($("#trId1 td input[name='restMoney']").val() * 1000000);
	 }
	 
	 if(removeTr(1)){
		 allLoanMoney -= parseInt($("#trId1 td input[name='restMoney']").val() * 1000000);
	 }
	 
	 sumMoney = $("#allLoanMoney").val(allLoanMoney / 1000000);
	 
	 return sumMoney;
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
	txt +=$("#diyaType").html()
	txt+= '	/></td>';
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
}