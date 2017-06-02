(function () {
     function openParamsDialog(editor) {
     	var elements = [];
        var paramsscope = "";
        if($("#scope") && $("#scope").val()){paramsscope = $("#scope").val();}
        console.log("paramsscope:"+paramsscope);
     	$.ajax({
 		   type: "POST",
 		   url: appCtx['aist-uam-web']+"/templateVariable/findVariableListByScope.json",
 		   async: false,
 		   data: "scope="+paramsscope,
 		   success: function(data){
 			   if(data && data.length>0){
 				   for(var i=0;i<data.length;i++){
 					  var paramObj = data[i];
 					  var obj = {
                          id: paramObj.name,
                          type: 'checkbox',
                          label: paramObj.name+"("+paramObj.desceiption+")",
                          'default': '',
                          commit: function () {
                               if(this.getValue()){editor.insertHtml("${"+$(this).attr('id')+"}")};
                          }
                      }
 					  elements.push([obj]);
 				   }
 			   }
 		   }
     	});
         return {
             title: '选择参数',
             minWidth: 350,
             minHeight: 140,
             buttons: [
             CKEDITOR.dialog.okButton,
             CKEDITOR.dialog.cancelButton],
             contents:[{
	               id: 'info',
	               label: '',
	               title: '',
	               elements:elements
             }],
             onOk: function () {
                 this.commitContent();
             },
             resizable: CKEDITOR.DIALOG_RESIZE_HEIGHT
         };
     }
  
     CKEDITOR.dialog.add('insertparams', function (a) {
         return openParamsDialog(a);
     });
 })();

/*(function() {
    CKEDITOR.dialog.add("insertparams", 
    function(a) {
        var elements = [];
        var paramsscope = "";
        if($("#scope") && $("#scope").val()){paramsscope = $("#scope").val();}
        console.log("paramsscope:"+paramsscope);
    	$.ajax({
		   type: "POST",
		   url: appCtx['aist-uam-web']+"/templateVariable/findVariableListByScope.json",
		   async: false,
		   data: "scope="+paramsscope,
		   success: function(data){
			   if(data && data.length>0){
				   for(var i=0;i<data.length;i++){
					   var paramObj = data[i];
					   var obj = {
							   id: paramObj.id,
							   type: 'checkbox',
							   label: paramObj.name+"("+paramObj.desceiption+")",
							   'default': '',
							   value:paramObj.id,
							   name:'paramCheckbox1',
							   commit: function (a) {
	                                 var text = 'Hello'+this.getValue();
	                                 alert(text+this.id);
	                           }
					   };
					   elements.push(obj);
				   }
			   }
		   }
    	});
        return {
            title: "选择参数"
            ,minWidth:350
    		,minHeight:140
            ,contents: [{
                id: "info",
                label: "",
                title: "",
                width: "350px",
                height: "500px",
                padding: 0,
                elements: elements
            }],
            onOk: function() {
            	var str = "---test---";
            	$("input[class='cke_dialog_ui_checkbox_input']:checked").each(function(i){
            		str+="${"+$(this).val()+"}";
            	});
                //点击确定按钮后的操作
                a.insertHtml();
            }
        }
    })
})();*/
/*
 Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license

CKEDITOR.dialog.add("insertparams",function(d){
	
	return{
		 title:"选择参数"
		,minWidth:350
		,minHeight:140
	    ,contents:[{
		    id:"info"
		   ,label:""
		   ,title:""
		   ,elements:[{
	    	   type:"checkbox"
	    	  ,label:$("#paramscontainer").val()
	    	  ,"default":""
	    	  ,accessKey:"S"
	    	  ,value:"checked"
		   }]
	   }],
       onOk: function() {
           //点击确定按钮后的操作
           d.insertHtml(str);
       }
	  }});*/