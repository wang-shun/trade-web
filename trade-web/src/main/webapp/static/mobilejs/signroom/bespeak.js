
$(function() {
	var ctx = $("#ctx").val();
	var tradeCenterId = $("#tradeCenterId").val();
	var agentCode = $("#agentCode").val();
	
	getPropertyAddress();
	
	//产证地址文本框失去焦点获取对应的caseCode
	$("#propertyAddress").blur(function(){
		$(".autocompleter").hide();
	});
	
	$("#propertyAddress").focus(function(){
		$(".autocompleter").show();
	});
	
  
	//点击切换效果
    $(".add-select input").click(function() {
        if($(this).hasClass("selected-mark")) {
            $(this).removeClass("selected-mark");
        }
        else {
            $(this).addClass("selected-mark");
        }
    });
    
    $("#btnBespoke").click(function(){
    	var propertyAddress = $("#propertyAddress").val();
    	var numberOfPeople = $("#numberOfPeople").val();
    	
    	if(propertyAddress == ""){
    		$(".zvalid-resultformat").html("请输入交易单地址");
    		$(".field-tooltipWrap").show(300).delay(1500).hide(300);
    		return false;
    	}
    	
    	if(numberOfPeople == ""){
    		$(".zvalid-resultformat").html("请输入参与人数");
    		$(".field-tooltipWrap").show(300).delay(1500).hide(300);
    		return false;
    	}
    	else {
    		numberOfPeople = Number(numberOfPeople);
    		
    		if(numberOfPeople <= 0){
    			$(".zvalid-resultformat").html("请输入合法的参与人数");
        		$(".field-tooltipWrap").show(300).delay(1500).hide(300);
        		return false;
    		}
    	}
    	
    	var transactItem = "";
    	var isSelectTransactItem = false;
    	$("input[name=transactItemCode]").each(function(){
    		if($(this).hasClass("selected-mark")){
    			isSelectTransactItem = true;
    			transactItem += $(this).attr("id") + ",";
    		}
    	});
    	
    	if(!isSelectTransactItem){
    		$(".zvalid-resultformat").html("请选择办理事项");
    		$(".field-tooltipWrap").show(300).delay(1500).hide(300); ;
    		return false;
    	}
    	
    	transactItem = transactItem.substring(0,transactItem.lastIndexOf(","));
    	
    	save(propertyAddress,numberOfPeople,transactItem);  //保存签约室预约信息
    });
    
    function save(propertyAddress,numberOfPeople,transactItem){
    	var caseCode = $("#caseCode").val();
    	var specialRequirement = $("#specialRequirement").val();
    	var tradeCenterId = $("#tradeCenterId").val();
    	var selDate = $("#selDate").val();
    	var bespeakTime = $("#bespeakTime").val();
    	var inputNumberOfPeople = Number($("#inputNumberOfPeople").val());
    	numberOfPeople = Number(numberOfPeople);
    	
    	console.log(inputNumberOfPeople + "-" + numberOfPeople);
    	
    	var actNumberOfPeople;
    	if(inputNumberOfPeople > numberOfPeople){
    		actNumberOfPeople = numberOfPeople;
    	}
    	else if(inputNumberOfPeople < numberOfPeople){
    		actNumberOfPeople = inputNumberOfPeople;
    	}
    	else if(inputNumberOfPeople = numberOfPeople){
    		actNumberOfPeople = numberOfPeople;
    	}
    	
    	console.log("最小值:" + actNumberOfPeople);
    	 
    	$.ajax({
    		cache:false,
    		async:false,
    		type:"POST",
    		dataType:"json",
    		url:ctx+"/weixin/signroom/save",
    		data: {resType:'0',actNumberOfPeople:actNumberOfPeople,resPersonId:agentCode,caseCode:caseCode,propertyAddress:propertyAddress,numberOfParticipants:numberOfPeople,transactItemCode:transactItem,specialRequirement:specialRequirement,tradeCenterId:tradeCenterId,selDate:selDate,bespeakTime:bespeakTime},
    		success:function(data){
    			if(data.isSuccess == "true"){
    				myOpenSuccess(data.resNo,data.numberOfPeople,data.selDate,data.bespeakTime);
    			}
    			else {
    				myOpenFail(data.isSuccess);
    			}
    		}
    	});
    }
    
    function myOpenFail(flag){
    	if(flag == "false"){
    		layer.open({
                type: 0,
                title: '',
                shadeClose: false,
                content: '<div class="dialog-user">'+
                            '<i class="iconfont iconfont70 mt20 falsegrey">&#xe60b;</i>'
                            + '<h2 class="dialog-head mt20 font18">预约失败！</h2>'
                            + '<div class="btn-box mt20">'
                            + '<a href="' + ctx + '/weixin/signroom/myReservationList"><div class="aui-btn aui-btn-primary aui-margin-r-10">我的预约</div></a>'
                            + '<a href="' + ctx + '/weixin/signroom/list"><div class="aui-btn aui-btn-grey aui-margin-l-10">继续预约</div></a>'
                            + '</div>'
            });
    	}
    	else if(flag == "noRoom"){
    		layer.open({
                type: 0,
                title: '',
                shadeClose: false,
                content: '<div class="dialog-user">'+
                            '<i class="iconfont iconfont70 mt20 falsegrey">&#xe60b;</i>'
                            + '<h2 class="dialog-head mt20 font18">未有闲置的房间！</h2>'
                            + '<div class="btn-box mt20">'
                            + '<a href="' + ctx + '/weixin/signroom/myReservationList"><div class="aui-btn aui-btn-primary aui-margin-r-10">我的预约</div></a>'
                            + '<a href="' + ctx + '/weixin/signroom/list"><div class="aui-btn aui-btn-grey aui-margin-l-10">继续预约</div></a>'
                            + '</div>'
            });
    	}
    }
    
    function myOpenSuccess(resNo,numberOfPeople,selDate,bespeakTime){
        layer.open({
            type: 0,
            title: '',
            shadeClose: false,
            content: '<div class="dialog-user">'+
                        '<i class="iconfont iconfont70 mt20 cyan">&#xe606;</i>'
                        + '<h2 class="dialog-head mt20 font18">恭喜你预约成功</h2>'
                        + '<div class="dialog-info">'
                        + '<p class="font14 mt20">预约编号<span class="yellow font18">' + resNo + '</span>（最大容纳' + numberOfPeople + '人）</p>'
                        + '<p class="mt5 font12">时间：' + selDate + '&nbsp;&nbsp;' + bespeakTime + '</p></div>'
                        + '<div class="btn-box mt20">'
                        + '<a href="' + ctx + '/weixin/signroom/myReservationList"><div class="aui-btn aui-btn-primary aui-btn-big">确定</div></a>'
                        + '</div>'
        });
    };
    
    //获取产证地址列表
    function getPropertyAddress(){
    	$.ajax({
			cache:false,
			async:false,
			type:"POST",
			dataType:"json",
			url: ctx + "/weixin/signroom/getPropertyAddressList",
			data: {agentCode : agentCode},
			success:function(data){
				if(data.length > 0){
					 $("#propertyAddress").autocompleter({
					       highlightMatches: true,
					       source: data,
					       hint: true,
					       empty: false
					   });
				}
			}
		});
    }
    
});


