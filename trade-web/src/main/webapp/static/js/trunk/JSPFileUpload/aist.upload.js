var AistUpload = function () {
	
	return {
		//houDelAdd 中图片上传
		initHouAddPicUpload:function (){
			$('#tapeInfo-fileupload').fileupload({   
	    	  	//还有其他属性   和方法重    请参考 jquery.fileuplod-ui
	    	  
	    	     //上传模板的ID：
				uploadTemplateId: 'template-upload-tapeInfo',
				//下载模板的ID：
	            downloadTemplateId: 'template-download-tapeInfo',
	            //文件列表的容器。如果未定义的，它是集
	            //一个元素类的“文件”里面的控件元素：
				filesContainer:$('#tapeInfo-Container')
	            
			});
		},
		//houDelAdd 中文件上传
		initHouTapeInfoUpload:function(){
			$('#pic-fileupload').fileupload(
					{    /* ... */
				uploadTemplateId: 'template-upload-pic',
				filesContainer:$('#pic-Container'),
	            // The ID of the download template:
	            downloadTemplateId: 'template-download-pic'
			});
		},
		
		_init1:function(picFileupload,picContainer,templateUpload,templateDownload,updFunction){
			$('#'+picFileupload).fileupload(
					{    /* ... */
				'uploadTemplateId': templateUpload,
				'filesContainer':$('#'+picContainer),
	            // The ID of the download template:
	            'downloadTemplateId': templateDownload,
	             'start': updFunction,
	             'autoUpload' : true
			});
		}, 
		_init2:function(picFileupload,picContainer,templateUpload,templateDownload,updFunction,acceptFileTypes){
			$('#'+picFileupload).fileupload(
					{    
				'uploadTemplateId': templateUpload,
				'filesContainer':$('#'+picContainer),
	            // The ID of the download template:
	            'downloadTemplateId': templateDownload,
	            'acceptFileTypes' : acceptFileTypes,
	             'start': updFunction
			});
		},
		//houDelAdd 中文件上传
		init:function(){
			var len= arguments.length; 
			if(len==6){
				this._init2(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
			}else{
				this._init1(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4]);
			}
		} /*, 
		
		init:function(picFileupload,picContainer,templateUpload,templateDownload,updFunction,acceptFileTypes){
			$('#'+picFileupload).fileupload(
					{     ... 
				'uploadTemplateId': templateUpload,
				'filesContainer':$('#'+picContainer),
	            // The ID of the download template:
	            'downloadTemplateId': templateDownload,
	            'acceptFileTypes' : acceptFileTypes,
	             'start': updFunction
			});
		}*/
	};
	
	
	
}();