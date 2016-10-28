  var manager = null;  
$(function ()
            { 
    	 $.ajax({  
           	 type: "POST",
           	  async:false, 
                url: appCtx['aist-uam-web']+"/sys/showOrgTree.html",
                data:{todo:"jsonp"},
                dataType: "jsonp",
                jsonp: "jsonpCallbackOrgTree",
                crossDomain: true,         
                cache:false,
                success: callbackOrgTree,
                error: function (jqXHR, textStatus) {
               	 alert(textStatus);
               	 alert(jqXHR);
                    //handle error
                }
           	
           });
    	
            });
    

    function callbackOrgTree(result){  
    	
    	 
    	 $("#tree1").ligerTree(
    	            {data:result,
    	             	 checkbox: false,
    	              	treeLeafOnly:false,
    	                   idFieldName: 'id', 
    	                   slide: false,
    	                   onSelect: onSelect,});
    	            manager = $("#tree1").ligerGetTreeManager();
    	 
    	
    	// return data;
    	} 
    function onSelect(note)
    {
      //  alert('onSelect:' + note.data.id);
        $('#orgTreeId').attr("value",note.data.id); 
    }
    
    