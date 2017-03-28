    $(function ()
            { 
                  
    	   // alert('adf');
    	   /*  $(function(){     
    	        $.ajax({  
    	        	 type: "POST",
    	        	  async:false, 
    	             url: appCtx['aist-uam-web']+"/sys/showMenuTree.html",
    	             data:{todo:"jsonp"},
    	             dataType: "jsonp",
    	             jsonp: "jsonpCallback",
    	             crossDomain: true,         
    	             cache:false,
    	             success: function (jsonResult) {
    	               alert("succ"+jsonResult);
    	             },
    	             error: function (jqXHR, textStatus) {
    	            	 alert(textStatus);
    	            	 alert(jqXHR);
    	                 //handle error
    	             }
    	        	
    	        });   
    	    });   */
       
    	 $.ajax({  
           	 type: "POST",
           	  async:false, 
                url: appCtx['aist-uam-web']+"/sys/showMenuTree.html",
                data:{todo:"jsonp"},
                dataType: "jsonp",
                jsonp: "jsonpCallback",
                crossDomain: true,         
                cache:false,
                success: callback,
                error: function (jqXHR, textStatus) {
               	 alert(textStatus);
               	 alert(jqXHR);
                    //handle error
                }
           	
           });
    	
            });
    

    function callback(result){  
    	 $("#menuTreeId").ligerComboBox({
             width: 180,
             selectBoxWidth: 200,
             selectBoxHeight: 200, valueField: 'id',textField: 'text',
             tree: {data:result,
             	 checkbox: true,
                  idFieldName: 'id', 
                  slide: false},
             onSelected:onSelected
         });
    	 
    	
    	// return data;
    	} 
    function onSelected(newvalue,text)
    {
      //alert('onSelect:' + newvalue+":---"+text);
      $('#menuTreeValue').attr("value",newvalue);
    }
    
    