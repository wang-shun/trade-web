  function checkform(){
    	if($("#distCode").val() == ""){
    		$("#distCode").css("border-color","red");
    		return false;
    	}else if($("#distName").val() == ""){
    		$("#distName").css("border-color","red");
    		return false;
    	}

    	return true;
    }
    function getPrResearchMapList(){
    	$("#table_list_1").jqGrid("GridUnload");
    	$("#table_list_1").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 350,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['序号', '行政区域编码', '行政区域名称', '誉萃部门编码','誉萃部门名称'],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60},
                {name: 'DIST_CODE', index: 'FIN_ORG_CODE', width: 140},
                {name: 'DIST_NAME', index: 'FIN_ORG_NAME', width: 180},
                {name: 'YU_DIST_CODE', index: 'FA_FIN_ORG_CODE', width: 140},
                {name: 'YU_DIST_NAME', index: 'FA_FIN_ORG_NAME', width: 140}

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
            	queryId:"queryPrResearchMapPage",
            	search_orgCode:$("#orgCode").val(),
            	search_orgName:$("#orgName").val(),
            	search_yuOrgName:$("#yuOrgName").val()
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
				var rowData = $("#table_list_1").jqGrid('getRowData', rowid);
				$("#pkid").val(rowData['PKID']);
			}
            
        });
    }
    function savePrResearchMap(){
    	
    	if(checkform()){
        	$.ajax({
        		url:ctx+"/setting/saveTsPrResearchMap",
        		method:"post",
        		dataType:"json",
        		data:$("#addOrModifyForm").serialize(),
	   		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  
                    $.unblockUI();   
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
    				alert(data.message);

        			if(data.success){
        				$("#modal-addOrModifyForm").modal("hide");
        				getPrResearchMapList();
        			}
        			$("#pkid").val("");
        		}
        	});
    	}
    }
    function delPrResearchMap(){
    	$.ajax({
    		url:ctx+"/setting/delTsPrResearchMap",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
				alert(data.message);

    			getPrResearchMapList();
    			
				$("#pkid").val("");
    		}
    	});
    }
    function getPrResearchMap(){
       	$.ajax({
    		url:ctx+"/setting/getTsPrResearchMap",
    		method:"post",
    		dataType:"json",
    		data:{pkid:$("#pkid").val()},
    		success:function(data){
    			if(data.success){
    				$("#modal-addOrModifyForm").modal("show");
    				$("#distCode").val(data.content.distCode);
    				$("#distName").val(data.content.distName);
    				$("#yuDistCode").val(data.content.yuDistCode);
    				$("#yuDistName").val(data.content.yuDistName);

    			}else{
    				alert(data.message);
    			}
    		}
    	});
    }
    var dists = null;
    function getDistList(){
        $.ajax({
        	url:ctx+"/setting/queryDistList",
    		dataType:"json",
    		async:false,
    		data:{},
    		success:function(data){
    			dists = data;
    		}
         });
    	
    }
    var yuDists = null;
    function getYuDistList(){
    	$.ajax({
            url: ctx+"/setting/getYuDistList",
            dataType: "json",
    		async:false,
            data:{           
            },
            success: function( data ) {
            	yuDists = data;
            }
        });
    }

 
    $(function(){
    	getDistList();
       	$("#distName").typeahead({
            source: dists,
            display: "name",    
            val: "code",           
            items: 8,            
            itemSelected: function (item, val, text) {  
            	$("#distName").val(text);

            	$("#distCode").val(val);
            }
       	});
       	getYuDistList();
       	$("#yuDistName").typeahead({
            source: yuDists,
            display: "orgName",    
            val: "orgCode",           
            items: 8,            
            itemSelected: function (item, val, text) {  
            	$("#yuDistName").val(text);

            	$("#yuDistCode").val(val);
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
    		getPrResearchMapList();
    	});
    	getPrResearchMapList();
    	$("#addBtn").click(function(){
    		$("#modal-addOrModifyForm input[type='text']").val("");
    		$("#modal-addOrModifyForm input[type='hidden']").val("");
    		$("#distCode").attr("readonly",false);
    		$("#distName").attr("readonly",false);
    		$("pkid").val("");
    		$("#modal-addOrModifyForm").modal("show");
    	});
    	$("#modifyBtn").click(function(){
    		if($("#pkid").val()==""){
    			alert("请选择要修改的记录！");
    			return;
    		}
    		$("#distCode").attr("readonly","readonly");
    		$("#distName").attr("readonly","readonly");
	
    		getPrResearchMap();

    	});
    	$("#delBtn").click(function(){
    		if($("#pkid").val() == ""){
    			alert("请选择要删除的记录！");
				return;
    		}
    		if(confirm("确定要删除该记录！")){
    			delPrResearchMap();
    		}
    	});
    	$("#saveOrModifyBtn").click(function(){
    		savePrResearchMap();
    	});

    });
    