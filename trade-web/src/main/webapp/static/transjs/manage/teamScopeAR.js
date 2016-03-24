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
            colNames: ['序号', '片区编码','誉萃组别名称', '誉萃组别编码','片区名称','组别类型' ],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60,hidden:true},
                {name: 'AR_CODE', index: 'AR_CODE', width: 80, hidden:true},
                {name: 'YU_TEAM_NAME', index: 'YU_TEAM_NAME', width: 80},
                {name: 'YU_TEAM_CODE', index: 'YU_TEAM_CODE', width: 80, hidden:true},
                {name: 'AR_NAME', index: 'AR_NAME', width: 180},
                {name: 'IS_RESPONSE_TEAM', index: 'IS_RESPONSE_TEAM', width: 80,
                	formatter:function(value,options,rowData){
	                    if(value==0){
	                        return '后台组';
	                    }else{
	                         return '前台组';
	                    }
                	}
                }
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
            	queryId:"queryTeamScopeAR"
/*            	search_agentTeamName:$("#agentTeamName").val(),
            	search_teamName:$("#teamName").val(),
            	argu_oriGrpId : $("#oriGrpId").val(),
            	argu_yuCuiOriGrpId : $("#yuCuiOriGrpId").val()*/
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
				var rowData = $("#table_list_1").jqGrid('getRowData', rowid);
				$("#pkid").val(rowData['PKID']);
				//$("#yuAgentTeamCode").val(rowData['AR_CODE']);
				$("#arCode").val(rowData['AR_CODE']);
				$("#arName").val(rowData['AR_NAME']);
			}
            
        });
    }
    function saveTeamScopeAr(){
    	   var tsTeamPropertyList = new Array();
	       $('.form').each(function () {
	            var yuTeamCode = $(this).find("select[name='yuTeamCode']").val();
	            var yuTeamName = $(this).find("select[name='yuTeamCode'] option:selected").text();
	            var isResponseTeam = $(this).find("select[name='isResponseTeam']").val();
	            
	            var tsTeamScopeAr = {
            		yuTeamCode : yuTeamCode,
            		yuTeamName : yuTeamName,
            		isResponseTeam : isResponseTeam
	            }
	            
	       	    tsTeamPropertyList.push(tsTeamScopeAr);
	       });
	       //console.log(tsTeamPropertyList);
	       var tsTeamScopeArVO = {
	    		tsTeamPropertyList : tsTeamPropertyList,
	    		arCode : $("#arCode").val(),
	    		arName : $("#arName").val()
	       }
	       tsTeamScopeArVO = $.toJSON(tsTeamScopeArVO);
        	$.ajax({
        		url:ctx+"/setting/saveTsTeamScopeArVO",
        		method:"post",
        		dataType:"json",
        		data: tsTeamScopeArVO,
        		contentType: "application/json; charset=utf-8" ,
        		success:function(data){
        			//alert(data.message);
        			if(data.success){
        				$("#modal-addOrModifyForm").modal("hide");
        				
        				var data = {};
        		    	data.queryId="queryTeamScopeAR";
        		    	
        			 	$("#table_list_1").jqGrid('setGridParam',{
        		    		datatype:'json',
        		    		mtype:'post',
        		    		postData: data
        		    	}).trigger('reloadGrid'); 
        			} else {
        				alert(data.message);
        			}
        			//$("#pkid").val("");
        		}
        	});
    }
    function delTeamScopeAr(){
    	$.ajax({
    		url:ctx+"/setting/delTeamScopeAr",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
				alert(data.message);

				var data = {};
		    	data.queryId="queryTeamScopeAR";
		    	
			 	$("#table_list_1").jqGrid('setGridParam',{
		    		datatype:'json',
		    		mtype:'post',
		    		postData: data
		    	}).trigger('reloadGrid'); 
			 	
				$("#pkid").val("");
    		}
    	});
    }
    
    // 获取所有组别
    function getTeamScopeArInfo(){
       	$.ajax({
    		url:ctx+"/setting/getTeamScopeArListByArCode",
    		method:"post",
    		dataType:"json",
    		data:{arCode:$("#arCode").val()},
    		success:function(data){
    			if(data.success){
    				//console.log(data);
    		        var tempTeam= template('yuCuiTeamList', data); 
    		        $("#team").empty();
    		        $("#team").html(tempTeam);
    		        
    		        $("#modal-addOrModifyForm").modal("show");
    			}else{
    				alert(data.message);
    			}
    		}
    		
    	});
    }
    
    function getYuCuiTeamList(backId){
       	$.ajax({
    		url:ctx+"/setting/getYuCuiTeamList",
    		method:"post",
    		dataType:"json",
    		data:'',
    		success:function(data){
    			if(data.success){
    				//console.log(data);
    				
    		        var tempTeam= template('newYuCuiTeamList', data); 
    		        
    		        $("#"+backId).empty();
    		        $("#"+backId).html(tempTeam);
    			}else{
    				alert(data.message);
    			}
    		}
    		
    	});
    }

    $(function(){
    	getAgentOrgs();
    	
    	 $( "#arName").typeahead({
             source: agentOrgs,
             display: "orgName",   
             val: "orgCode",          
             items: 8,           
             itemSelected: function (item, val, text) { 
               $( "#arName").val(text);

               $( "#arCode").val(val);
             }
           });

    	$("input").each(function(){
    		$(this).blur(function(){
    			if($(this).val() != ""){
					$(this).css("border-color","#e5e6e7");
    			}
    		});
    	});

    	$("#searchButton").click(function(){
	    	var data = {};
	    	data.argu_oriGrpId =$.trim( $('#oriGrpId').val() );  
	    	data.search_agentTeamName =$.trim( $('#agentTeamName').val() ); 
	    	data.argu_yuCuiOriGrpId =$.trim( $('#yuCuiOriGrpId').val() );  
	    	data.search_teamName =$.trim( $('#teamName').val() ); 
	    	data.queryId="queryTeamScopeAR";
	    	
	    	$("#table_list_1").jqGrid('setGridParam',{
	    		datatype:'json',
	    		mtype:'post',
	    		postData:data
	    	}).trigger('reloadGrid'); 
    	});
    	$("#cleanButton").click(function(){
    		$("input").val("");
    	});
    	getTeamScopeList();
    	$("#addBtn").click(function(){
    		$("#modal-addOrModifyForm input").val("");
    		$("#modal-addOrModifyForm input[type='hidden']").val("");

    		$("#modal-addOrModifyForm").modal("show");
    		getTeamScopeArInfo();
    	});
    	$("#modifyBtn").click(function(){
    		if($("#pkid").val()==""){
    			alert("请选择要修改的记录！");
    			return;
    		}
    		getTeamScopeArInfo();

    	});
    	$("#delBtn").click(function(){
    		if($("#pkid").val() == ""){
    			alert("请选择要删除的记录！");
				return;
    		}
    		if(confirm("确定要删除该组别！")){
    			delTeamScopeAr();
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
    		saveTeamScopeAr();
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
    