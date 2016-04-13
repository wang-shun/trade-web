  function checkform(){
    	if($("#finOrgCode").val() == ""){
    		$("#finOrgCode").css("border-color","red");
    		return false;
    	}else if($("#finOrgName").val() == ""){
    		$("#finOrgName").css("border-color","red");
    		return false;
    	}

    	return true;
    }
    function getFinOrgList(){
    	$("#table_list_1").jqGrid("GridUnload");
    	$("#table_list_1").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 350,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['序号', '金融机构编号', '金融机构名称', '父机构编码','父机构名称','别名'],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60},
                {name: 'FIN_ORG_CODE', index: 'FIN_ORG_CODE', width: 140},
                {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME', width: 180},
                {name: 'FA_FIN_ORG_CODE', index: 'FA_FIN_ORG_CODE', width: 140},
                {name: 'FA_FIN_ORG_NAME', index: 'FA_FIN_ORG_NAME', width: 140},
                {name: 'FIN_ORG_NAME_YC', index: 'FIN_ORG_NAME_YC', width: 140}

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
            	queryId:"queryFinOrgPage",
            	search_orgCode:$("#orgCode").val(),
            	search_orgName:$("#orgName").val()
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
				var rowData = $("#table_list_1").jqGrid('getRowData', rowid);
				$("#pkid").val(rowData['PKID']);
			}
            
        });
    }
    function saveFinOrg(){
    	
    	if(checkform()){
        	$.ajax({
        		url:ctx+"/setting/saveFinOrg",
        		method:"post",
        		dataType:"json",
        		data:$("#addOrModifyForm").serialize(),
        		success:function(data){
    				alert(data.message);

        			if(data.success){
        				$("#modal-addOrModifyForm").modal("hide");
        		    	getFinOrgList();
        			}
        			$("#pkid").val("");
        		}
        	});
    	}
    }
    function delFinOrg(){
    	$.ajax({
    		url:ctx+"/setting/delFinOrg",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
				alert(data.message);

		    	getFinOrgList();
				$("#pkid").val("");
    		}
    	});
    }
    function getFinOrgInfo(){
       	$.ajax({
    		url:ctx+"/setting/getFinOrgInfo",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
    			if(data.success){
    				$("#modal-addOrModifyForm").modal("show");
    				$("#finOrgCode").val(data.content.finOrgCode);
    				$("#finOrgName").val(data.content.finOrgName);
    				$("#faFinOrgCode").val(data.content.faFinOrgCode);
    				$("#faFinOrgName").val(data.content.faFinOrgName);
    				$("#faFinOrgNameYc").val(data.content.faFinOrgNameYc);
    				
    			}else{
    				alert(data.message);
    			}
    		}
    	});
    }
    var finOrgs = null;
    function getFinOrgs(){
    	$.ajax({
            url: ctx+"/manage/queryAllFinOrg",
            dataType: "json",
    		async:false,
            data:{           
            },
            success: function( data ) {
            	finOrgs = data;
            }
        });
    }
  /*  this.setSelectedFinOrg = function (finOrg) {
        $("#faFinOrgCode").val(finOrg.finOrgCode);
    };*/
 
    $(function(){
       	$("#faFinOrgName").typeahead({
       	  ajax: {
              url: ctx+"/manage/queryFinOrgNameLike",
              timeout: 300,                   // 延时
              method: 'post',
              triggerLength: 3,    // 输入几个字符之后，开始请求
              loadingClass: null,             //
              preDispatch: function (query) {
                  var para = {datatype: "json"};
                  para.finOrgName = query;
                  return para;
              },
              preProcess: function (result) {
                  return result;
              }
          }, display: "finOrgName",    
            val: "finOrgCode",           
            items: 8,            
            itemSelected: function (item, val, text) {  
            	$("#faFinOrgName").val(text);

            	$("#faFinOrgCode").val(val);
            }
       	});
 /*   	$("#faFinOrgName").typeahead({
            source: function(query, process ) {
           	  var results = $.map(finOrgs, function (finOrg) {
                    return finOrg.finOrgName + "";
                }); 
                process(results);
            },
	       	updater: function (id) {
	       		var finOrg = null;
	       		for(var i = 0;i<finOrgs.length;i++){
	       			if(id == finOrgs[i].finOrgName){
	       				finOrg = finOrgs[i];
	       				break;
	       			}
	       		}
	       		setSelectedFinOrg(finOrg);	
	            return finOrg.finOrgName;
	        }
   	});*/
    	$("input").each(function(){
    		$(this).blur(function(){
    			if($(this).val() != ""){
					$(this).css("border-color","#e5e6e7");
    			}
    		});
    	});

    	$("#searchButton").click(function(){
    		getFinOrgList();
    	});
    	getFinOrgList();
    	$("#addBtn").click(function(){
    		$("#modal-addOrModifyForm input[type='text']").val("");
    		$("#modal-addOrModifyForm input[type='hidden']").val("");

    		$("pkid").val("");
    		$("#modal-addOrModifyForm").modal("show");
    	});
    	$("#modifyBtn").click(function(){
    		if($("#pkid").val()==""){
    			alert("请选择要修改的记录！");
    			return;
    		}
    		$("#finOrgCode").attr("readonly","readonly");
    		getFinOrgInfo();

    	});
    	$("#delBtn").click(function(){
    		if($("#pkid").val() == ""){
    			alert("请选择要删除的记录！");
				return;
    		}
    		if(confirm("确定要删除该金融机构！")){
    			delFinOrg();
    		}
    	});
    	$("#saveOrModifyBtn").click(function(){
    		saveFinOrg();
    	});

    });
    