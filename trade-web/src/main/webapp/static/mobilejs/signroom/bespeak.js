
$(function() {
	var ctx = $("#ctx").val();
	var tradeCenterId = $("#tradeCenterId").val();
	
	getPropertyAddress();
	
	//产证地址文本框失去焦点获取对应的caseCode
	$("#propertyAddress").blur(function(){
		var propertyAddress = this.value;
		
		if(propertyAddress != ""){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				dataType:"text",
				url:ctx+"/mobile/reservation/getCaseCodeByPropertyAddr",
				data: {propertyAddress:propertyAddress},
				success:function(data){
					$("#caseCode").val(data);
				}
			});
		}
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
    	
    	$.ajax({
    		cache:false,
    		async:false,
    		type:"POST",
    		dataType:"json",
    		url:ctx+"/mobile/reservation/save",
    		data: {resType:'0',resPersonId:agentCode,caseCode:caseCode,propertyAddress:propertyAddress,numberOfParticipants:numberOfPeople,transactItemCode:transactItem,specialRequirement:specialRequirement,tradeCenterId:tradeCenterId,selDate:selDate,bespeakTime:bespeakTime},
    		success:function(data){
    			if(data.isSuccess == "true"){
    				myOpenSuccess(data.roomNo,data.numberOfPeople,data.selDate,data.bespeakTime);
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
                content: '<div class="dialog-user">'+
                            '<i class="iconfont iconfont70 mt20 falsegrey">&#xe60b;</i>'
                            + '<h2 class="dialog-head mt20 font18">预约失败！</h2>'
                            + '<div class="btn-box mt20">'
                            + '<a href="' + ctx + '/mobile/reservation/myReservationList"><div class="aui-btn aui-btn-primary aui-margin-r-10">我的预约</div></a>'
                            + '<a href="' + ctx + '/mobile/reservation/list"><div class="aui-btn aui-btn-grey aui-margin-l-10">继续预约</div></a>'
                            + '</div>'
            });
    	}
    	else if(flag == "noRoom"){
    		layer.open({
                type: 0,
                title: '',
                content: '<div class="dialog-user">'+
                            '<i class="iconfont iconfont70 mt20 falsegrey">&#xe60b;</i>'
                            + '<h2 class="dialog-head mt20 font18">未有闲置的房间！</h2>'
                            + '<div class="btn-box mt20">'
                            + '<a href="' + ctx + '/mobile/reservation/myReservationList"><div class="aui-btn aui-btn-primary aui-margin-r-10">我的预约</div></a>'
                            + '<a href="' + ctx + '/mobile/reservation/list"><div class="aui-btn aui-btn-grey aui-margin-l-10">继续预约</div></a>'
                            + '</div>'
            });
    	}
    }
    
    function myOpenSuccess(roomNo,numberOfPeople,selDate,bespeakTime){
        layer.open({
            type: 0,
            title: '',
            content: '<div class="dialog-user">'+
                        '<i class="iconfont iconfont70 mt20 cyan">&#xe606;</i>'
                        + '<h2 class="dialog-head mt20 font18">恭喜你预约成功</h2>'
                        + '<div class="dialog-info">'
                        + '<p class="font14 mt20">房间号<span class="yellow font18">' + roomNo + '</span>（最大容纳' + numberOfPeople + '人）</p>'
                        + '<p class="mt5 font12">时间：' + selDate + '&nbsp;&nbsp;' + bespeakTime + '</p></div>'
                        + '<div class="btn-box mt20">'
                        + '<a href="' + ctx + '/mobile/reservation/myReservationList"><div class="aui-btn aui-btn-primary aui-margin-r-10">我的预约</div></a>'
                        + '<a href="' + ctx + '/mobile/reservation/list"><div class="aui-btn aui-btn-grey aui-margin-l-10">继续预约</div></a>'
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
			url: ctx + "/mobile/reservation/getPropertyAddressList",
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


