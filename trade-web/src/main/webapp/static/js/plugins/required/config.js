require.config({
    	baseUrl: ctx+"/",
    	shim: {
    		'blockUI': ['jquery']
    	},
    	paths: {
    		"config" : "js/plugins/required/config",
    		"aistMath": "js/plugins/required/math",
    		"jquery" : "js/jquery-2.1.1",
    		"blockUI": "static/tbsp/js/jquery-blockUI/jquery.blockUI",
    		"aistTemplate" : "js/template",
    		"viewer" : "js/viewer/viewer.min",
    		"aistWebuploader" : "js/plugins/webuploader/webuploader.min",
    		"aistFileUpload" : "js/plugins/aist/aist.fileupload"
    	}
    });


/*require(['aistFileUpload'], function (aistFileUpload){
	  aistFileUpload.initFileUpload({
  		caseCode : "ZY-SH-201609-0035",
  		partCode : "SpvApplyApprove",
  		fileUploadcontainer : "fileUploadcontainer",
  		accept: {
		        title: 'Images',
		        extensions: 'jpg',
		        mimeTypes: '.jpg'
		    }
  	}); 
 });

require(['aistMath'], function (aistMath){
	alert(aistMath.add(1,1));
}); */

require(['aistFileUpload'], function (aistFileUpload){
	  aistFileUpload.initFileUpload({
		caseCode : "ZY-SH-201609-0035",
		partCode : "SpvApplyApprove",
		fileUploadcontainer : "fileUploadcontainer",
		accept: {
		        title: 'Images',
		        extensions: 'jpg',
		        mimeTypes: '.jpg'
		    }
	}); 
});




