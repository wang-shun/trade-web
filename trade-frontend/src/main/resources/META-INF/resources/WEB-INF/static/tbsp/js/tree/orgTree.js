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
    	 $("#orgTreeId").ligerComboBox({
             width: 180,
             selectBoxWidth: 200,
             selectBoxHeight: 200, valueField: 'id',textField: 'text',
             tree: {data:result,
             	 checkbox: false,
             	treeLeafOnly:false,
                  idFieldName: 'id', 
                  slide: false},
             onSelected:onSelectedOrgTree
         });
    	 
    	
    	// return data;
    	} 
    function onSelectedOrgTree(newvalue,text)
    {
      //alert('onSelect:' + newvalue+":---"+text);
      $('#orgTreeValue').attr("value",newvalue);
    }
    
    