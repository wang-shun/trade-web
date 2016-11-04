  function checkform(){
    	if($("#yuAgentTeamCode").val() == ""){
    		$("#yuAgentTeamCode").css("border-color","red");
    		return false;
    	}else if($("#yuAgentTeamName").val() == ""){
    		$("#yuAgentTeamName").css("border-color","red");
    		return false;
    	}else if($("#yuTeamCode").val() == ""){
    		$("#yuTeamCode").css("border-color","red");
    		return false;
    	}else if($("#yuTeamName").val() == ""){
    		$("#yuTeamName").css("border-color","red");
    		return false;
    	}

    	return true;
    }
    function getTeamScopeList(){
    	$("#table_list_1").jqGrid("GridUnload");
    	$("#table_list_1").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 350,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['序号', '誉萃组别名称','业务组别名称','业务组别编码', '誉萃组别编码','区经','区经组织','区总','区总组织' ],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60,hidden:true},
                {name: 'YU_TEAM_NAME', index: 'YU_TEAM_NAME', width: 180},
                {name: 'YU_AGENT_TEAM_NAME', index: 'YU_AGENT_TEAM_NAME', width: 180},
                {name: 'YU_AGENT_TEAM_CODE', index: 'YU_AGENT_TEAM_CODE', width: 140},
                {name: 'YU_TEAM_CODE', index: 'YU_TEAM_CODE', width: 80,hidden:true},
                {name: 'QYJL', index: 'QYJL', width: 80},
                {name: 'QYJL_ORG', index: 'QYJL', width: 80},
                {name: 'QYZJ', index: 'QYZJ', width: 80},
                {name: 'QYZJ_ORG', index: 'QYZJ', width: 80}
            ], 
            add: true,
            addtext: 'Add',
            pager: "#pager_list_1",
            viewrecords: true,
            pagebuttions: true,
            hidegrid: false,
            recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
            pgtext : " {0} 共 {1} 页",
            search:false,
            postData:{
            	queryId:"queryTeamScopePage",
            	//search_agentTeamCode:$("#agentTeamCode").val(),
            	search_agentTeamName:$("#agentTeamName").val(),
            	//search_teamCode:$("#teamCode").val(),
            	search_teamName:$("#teamName").val(),
            	argu_oriGrpId : $("#oriGrpId").val(),
            	argu_yuCuiOriGrpId : $("#yuCuiOriGrpId").val()
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
				var rowData = $("#table_list_1").jqGrid('getRowData', rowid);
				$("#pkid").val(rowData['PKID']);
				$("#agenTeamCode").val(rowData['YU_AGENT_TEAM_CODE']);
				$("#agenTeamName").val(rowData['YU_AGENT_TEAM_NAME']);
			}
            
        });
    }
    function saveTeamScope(){
    	if(checkform()){
        	$.ajax({
        		url:ctx+"/setting/saveTeamScopeVO",
        		method:"post",
        		dataType:"json",
        		data:$("#addOrModifyForm").serialize(),
        		success:function(data){
    				alert(data.message);

        			if(data.success){
        				$("#modal-addOrModifyForm").modal("hide");
        				getTeamScopeList();
        			}
        			$("#pkid").val("");
        		}
        	});
    	}
    }
    function delTeamScope(){
    	$.ajax({
    		url:ctx+"/setting/delTeamScope",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
				alert(data.message);

		    	getTeamScopeList();
				$("#pkid").val("");
    		}
    	});
    }
    
    function getTeamScopeInfo(){
       	$.ajax({
    		url:ctx+"/setting/getTeamScopeListByCode",
    		method:"post",
    		dataType:"json",
    		data:{agentTeamCode:$("#agenTeamCode").val()},
    		success:function(data){
    			if(data.success){
    				$("#yuAgentTeamCode").val($("#agenTeamCode").val());
    				$("#yuAgentTeamName").val($("#agenTeamName").val());
    				$("#modal-addOrModifyForm").modal("show");
    				console.log(data);			
    				console.log(data);
    		        var tempFontTeam= template('yuCuiFontTeamList', data); 
    		        $("#fontTeam").empty();
    		        $("#fontTeam").html(tempFontTeam);
    		        
    		        var tempBackTeam= template('yuCuiBackTeamList', data); 
    		        $("#backTeam").empty();
    		        $("#backTeam").html(tempBackTeam);
    			}else{
    				alert(data.message);
    			}
    		}
    	});
       	
       	//getYuCuiTeamList();
    }
   /* function getTeamScopeInfo(){
       	$.ajax({
    		url:ctx+"/setting/getTeamScopeInfo",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
    			if(data.success){
    				loadUnSettingOrg();
    				$("#modal-addOrModifyForm").modal("show");
    				$("#yuAgentTeamCode").val(data.content.yuAgentTeamCode);
    				$("#yuAgentTeamName").val(data.content.yuAgentTeamName);
    				$("#yuTeamCode").val(data.content.yuTeamCode);
    				$("#yuTeamName").val(data.content.yuTeamName);
    			}else{
    				alert(data.message);
    			}
    		}
    	});
       	
       	getYuCuiTeamList();
    }*/
    
    function getYuCuiTeamList(backId){
    	$.ajax({
    		url:ctx+"/setting/getYuCuiTeamList",
    		method:"post",
    		dataType:"json",
    		data:"",
    		success:function(data){
    			if(data.success){
    				console.log(data);
/*    		        var tempFontTeam= template('yuCuiFontTeamList', data); 
    		        $("#fontTeam").empty();
    		        $("#fontTeam").html(tempFontTeam);*/
    		        
    		        var tempBackTeam= template('yuCuiOtherBackTeamList', data); 
    		        $("#"+backId).empty();
    		        $("#"+backId).html(tempBackTeam);
    			}else{
    				alert(data.message);
    			}
    		}
    	});
    }

    $(function(){
    	
    	$("input").each(function(){
    		$(this).blur(function(){
    			if($(this).val() != ""){
					$(this).css("border-color","#e5e6e7");
    			}
    		});
    	});

    	$("#searchButton").click(function(){
    		getTeamScopeList();
    	});
    	$("#cleanButton").click(function(){
    		$("input").val("");
    	});
    	getTeamScopeList();
    	$("#addBtn").click(function(){
    		$("#modal-addOrModifyForm input").val("");
    		$("#modal-addOrModifyForm input[type='hidden']").val("");

    		$("#modal-addOrModifyForm").modal("show");
    		getTeamScopeInfo();
    	});
    	$("#modifyBtn").click(function(){
    		if($("#pkid").val()==""){
    			alert("请选择要修改的记录！");
    			return;
    		}
    		getTeamScopeInfo();

    	});
    	$("#delBtn").click(function(){
    		if($("#pkid").val() == ""){
    			alert("请选择要删除的记录！");
				return;
    		}
    		if(confirm("确定要删除该组别！")){
    			delTeamScope();
    		}
    	});
    	$("#recoveryBtn").click(function(){
    		$.ajax({
        		url:ctx+"/setting/recoveryTeamScope",
        		method:"post",
        		dataType:"json",
        		data:'',
        		success:function(data){
        		   //alert(data.message);
        			//console.log(data);
    		        var recoveryCaseList= template('recoveryCaseList', data); 
    		        $(".modal-body").empty();
    		        $(".modal-body").html(recoveryCaseList);
    		        
    				Modal.alert();
        		}
    		})
    	});
    	$("#saveOrModifyBtn").click(function(){
    		saveTeamScope();
    	});

    });
    
    //选业务组织的回调函数
    function radioOrgSelectCallBack(array){
        if(array && array.length >0){
            $("#agentTeamCode").val(array[0].name);
    		$("#oriGrpId").val(array[0].id);
    		
    /*		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
    		$("#oldactiveName").attr("onclick",userSelect);*/
    	}else{
    		$("#agentTeamCode").val("");
    		$("#oriGrpId").val("");
    	}
    }
    
    //选业务组织的回调函数
    function radioYuCuiOrgSelectCallBack(array){
        if(array && array.length >0){
            $("#teamCode").val(array[0].name);
    		$("#yuCuiOriGrpId").val(array[0].id);
    		
    /*		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
    		$("#oldactiveName").attr("onclick",userSelect);*/
    	}else{
    		$("#teamCode").val("");
    		$("#yuCuiOriGrpId").val("");
    	}
    }
    