
		/** 关于上传的图片  */
		var ctx = $("#ctx").val();
		//var preFileCode;//图片的ID
		//var fileName;//图片文件名
		
		//获取上传成功的图片的信息
		function getAddFilesIsOk() {
		//图片的ID
		var pics=[];
		var spans = $("div[name='names']");
	    $.each(spans, function(i, item) {
	    	var pic={
	    			'preFileAdress':spans[i].className,
	    			'fileName':spans[i].title
	    	}
	    	pics.push(pic);
	    });
	   
	    //必须上传图片
	    if(pics.length<=0){
	    	 alert("请先上传图片成功后再提交！");
	    	 return null;
	    }
	    var picDiv=$("div[name='allPicDiv']");
	    //所选图片和上传的图片的数目要相同
	    if(picDiv.length!==pics.length){
	    	 alert("请成功上传所有的图片后再提交！");
	    	 return null;
	    }
	    return pics;
	}
	
		 //提交
		function commitFiles(){
			//交易编号
			var prCode = $("#prCode").val();
			//判断是否上传了附件
			var pics=getAddFilesIsOk();
			if(pics==null){
				return;
			};
			var params={
					'prCode' : prCode,//交易单编号
					'pic':pics
			};
			
			params=$.toJSON(params);
			
			 $.ajax({
				async: true,
				cache : false,
				type : "POST",
				url : ctx+'/property/saveFiles',
				dataType: 'json',
				contentType: "application/json; charset=utf-8",
				data:params,
			    success:function(data) {
					alert(data.message);
					if(data.success){
						parent.$.fancybox.close(); 
					}
				},
				
				error: function(errors) {
					alert("上传附件失败");
				}
			}); 
		}
		
	    //关闭
		function closeThisWin(){
			 parent.$.fancybox.close(); 
		}
		/*删除图片*/
		function delPic(pkid){
			$.ajax({
				async: true,
				cache : false,
				type : "GET",
				url : ctx+'/property/delFiles?pkid='+pkid,
				dataType: 'json',
				contentType: "application/json; charset=utf-8",
			    success:function(data) {
					alert(data.message);
					if(data.success){
						$("#"+pkid).remove();
					}
				},
				
				error: function(errors) {
					alert("删除失败");
				}
			}); 
		}
		
		
		