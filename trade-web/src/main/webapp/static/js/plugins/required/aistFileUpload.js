/****
 *  
 *    上传组件
 *    
 *    @author : Astar
 * 
 */

define(["jquery","aistTemplate","viewer","aistWebuploader"],function($, template,viewer,WebUploader) {


			  webUploader = '';
			  container = '';
			  
			  init = function(options) {
				  
				  var settings = $.extend({
						ctx  :  window.ctx,
						fileUploadContainer : "fileUploadContainer",
						auto: false,
						duplicate: true,
						/*accept: {
						        title: 'Images',
						        extensions: 'gif,jpg,jpeg,bmp,png',
						        mimeTypes: 'image/*'
						},*/
						accept: {
					        title: 'Images',
					        extensions: 'jpg',
					        mimeTypes: '.jpg'
					    },
						thumbnailWidth : 64,
						thumbnailHeight : 64,
						pick : 'filePicker',
						server : appCtx['aist-filesvr-web']+'/webUploader/uploadPicture',
						available : null,
						bizCode : null
				  },options||{});
				  
				  container = settings.fileUploadContainer;
				  
				  updateAttachmentStatus(settings);
				  
				  if (typeof window.WebUploader == 'undefined') {
					  use(["/js/viewer/viewer.min.css","/js/viewer/viewer.min.js","/js/template.js","/js/plugins/webuploader/attachment-ui.css","/js/plugins/webuploader/webuploader.css"],settings);
			      }
			      else {
			    	  queryAttachments(settings);
			      }
				  
				  /*if(!window.WebUploader.Uploader.support()) {
				        window.wxc.alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
				        throw new Error( 'WebUploader does not support the browser you are using.' );
				  }*/
			  };
			  
			  updateAttachmentStatus = function(settings) {
				  $.ajax({
		    			cache : true,
		    			async : false,//false同步，true异步
		    			type : "POST",
		    			url : ctx+'/attachment/updateAvaliableAttachmentByProperty',
		    			dataType : "json",
		    			data : [{
		    				name : 'caseCode',
		    				value :  settings.caseCode
		    			},{
		    				name : 'partCode',
		    				value :  settings.partCode
		    			}],
		    			success : function(data) {
		    			},
		    			error : function(errors) {
		    				window.wxc.error("更新状态失败");
		    			}
		    		});
			  };
			  use = function(urls,settings) {
				  var ctx = settings.ctx;
				  var urls = typeof urls == "string" ? [urls] : urls;
				  for (var i = 0; i < urls.length; i++) {
					   var url = urls[i].replace(/\s/g, "");
					   var isCss = /\.css$/.test(url);
					   var tag = window.document.createElement(isCss ? "link": "script");
					   if(/\.css$/.test(url)){
						   $("<link>").attr({ rel: "stylesheet", type: "text/css", href: ctx+url }).appendTo("head");
					   }
					   if(/\.js$/.test(url)){
						   var script = ctx+url;
						   jQuery.getScript(ctx+url).done(function() {
						   }).fail(function() {
				                window.wxc.error("请检查"+ ctx+url +"的路径是否正确!");
				           });
					   }
				  }
				  queryAttachments(settings);
		      };
		      
			  initWebUploader = function(settings) {
				   
				    // 初始化Web Uploader
					var uploader = WebUploader.create({

					    // 选完文件后，是否自动上传。
					    auto: settings.auto,

					    // swf文件路径
					    //swf: BASE_URL + '/js/Uploader.swf',
		 				dataType: settings.dataType,//加入这个选项即可
		 				
		 				duplicate: settings.duplicate,
		 				
					    // 文件接收服务端。
					    server: settings.server,
					    //server: 'http://127.0.0.1:8083/servlet/jqueryFileUpload',

					    // 选择文件的按钮。可选。
					    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
					    pick: "."+settings.pick,

					    // 只允许选择图片文件。
					    accept: settings.accept,
					    
					    thumbnailWidth : settings.thumbnailWidth,
					    
					    thumbnailHeight : settings.thumbnailHeight
					});
					webUploader = uploader;
					
					//初始化事件
					initDeleteImgEvent(settings.fileUploadContainer);
					initImgViewer();
					
					$("#"+settings.fileUploadContainer).on("click",".webuploader-element-invisible", function() {
						var $this = $(this).parent().parent() ;
						// 去掉其他被选择的
						$("."+settings.pick).removeClass("checked");
						$this.addClass("checked");
					});
					
					// 当有文件添加进来的时候
					uploader.on('fileQueued', function(file) {
						var checkedBtn = $("."+settings.pick+".checked");
						var id = checkedBtn.attr("id");
					    
					    // $list为容器jQuery实例
					    var $ul = $("#"+id+"_pic_list");
					    var $li = $('<li id="'+file.id+'"><p class="imgWrap"><img></p></li>');
					    
					    var $span = $ul.find("."+settings.pick+".checked");
					    
					    var $btns = $('<div class="file-panel">' +
				                '<span class="cancel">删除</span>' +
				                '</div>').appendTo($li);
					    
					    //$ul.append($li);
					    $span.before($li);
					    
					    uploader.makeThumb(file, function(error,src) {
					        if(error) {
					        	$li.find("img").replaceWith('<span>不能预览</span>');
					            return;
					        }

					        $li.find("img").attr('src', src);
					    }, uploader.options.thumbnailWidth, uploader.options.thumbnailHeight );
					    	    
					    uploader.upload();
					    
					    var $li = $( '#'+file.id ),
				        $error = $li.find('div.error');
					    // 避免重复创建
					    if (!$error.length) {
					        $error = $('<div class="error"></div>').appendTo( $li );
					    }
					    $error.text('上传中...');
					});
					
					uploader.on('uploadSuccess', function(file,res) {
						var $li = $( '#'+file.id ),
				        $error = $li.find('div.error');
					    // 避免重复创建
					    if ($error.length) {
					    	$error.remove();
					    }
					    
						$.each(res.files, function(index, item){
							var id = item.id;
							var fileCat = item.fileCat;
							var fileName = item.fileName;
							
							//preFileCode
							var checkedBtn = $("."+settings.pick+".checked");
							var preFileCode = checkedBtn.attr("id");
							
							addAttachment(settings.caseCode,settings.partCode,id,preFileCode,fileName);
							
							var img = $('#'+file.id).find("img");
							img.attr("data",appCtx['aist-filesvr-web']+"/JQeryUpload/getfile?fileId="+id);
							$('#'+file.id).attr("id",id);
							
						});
					    initImgViewer();
					});

					// 文件上传失败，显示上传出错。
					uploader.on('uploadError', function(file) {
					    var $li = $( '#'+file.id ),
					    $error = $li.find('div.error');

					    // 避免重复创建
					    if (!$error.length) {
					        $error = $('<div class="error"></div>').appendTo( $li );
					    }

					    $error.text('上传失败');
					});

					// 完成上传完了，成功或者失败，先删除进度条。
					uploader.on( 'uploadComplete', function(file) {
						//isCompletedUpload();
					});
			  };
			  
			  initDeleteImgEvent = function (fileUploadContainer) {
				    var $li = $("#"+fileUploadContainer).find("li");
				    var $btns = $("#"+fileUploadContainer).find("div.file-panel");
				    
				    $("#"+fileUploadContainer).on('mouseenter', 'li', function() {
				    	$(this).find("div.file-panel").stop().animate({height: 30});
				    });
				    
				    $("#"+fileUploadContainer).on('mouseleave', 'li', function() {
				    	$(this).find("div.file-panel").stop().animate({height: 0});
				    });
				    
				    $("#"+fileUploadContainer).on('click', 'div.file-panel>span:nth-child(1)', function() {
			            var index = $(this).index(),
			                deg;

			            switch (index) {
			                case 0:
			                	var $li = $(this).parent().parent();
			                    removeFile($li);
			                    return ;

			                case 1:
			                    break;

			                case 2:
			                    break;
			            }
			        });
				  
			  };
		      
		      queryAttachments = function(settings) {
		    	  var ctx = settings.ctx;
		    	  var caseCode = settings.caseCode;
		    	  var bizCode = settings.bizCode;
		    	  var partCode = settings.partCode;
		    	  var available = settings.available;
		    	  if(available=='') {
		    		  available = null;
		    	  }
		    	  if(bizCode=='') {
		    		  bizCode = null;
		    	  }
		    	
		    	  var templeteId = settings.templeteId;
		    	  $.ajax({
						type : 'post',
						cache : false,
						async : true,//false同步，true异步
						dataType : 'json',
						url : ctx+'/attachment/queryNewAttachment',
						data : [{
							name : 'caseCode',
							value : caseCode
						},{
							name : 'bizCode',
							value : bizCode
						},{
							name : 'partCode',
							value : partCode
						},{
							name : 'available',
							value : available
						}],
						dataType : "json",
						success : function(data) {
						  
							var fileuploadHtml = createTempleteHtml(settings,data);
							$("#"+settings.fileUploadContainer).empty();
							$("#"+settings.fileUploadContainer).html(fileuploadHtml);
			                
			                if(webUploader!=null && webUploader!=''){
			                	webUploader.destroy();
			                }
			                initWebUploader(settings);
            
						},		
						error : function(errors) {
							window.wxc.error("产调加载失败");
							return false;
						}
					});
		      };
		      
		      createTempleteHtml = function(settings,data) {
		    	  settings.imgCentanet = appCtx['img-centanet'];
		    	  var fileuploadHtml ='';
		          if(typeof(settings.templeteId) == "undefined") {
		        	  var templeteSource = '<table class="table table-bordered customerinfo">'
	                  +'<thead>'
		        	  +'<tr>'
		        	  +'<th style="width: 100px;">类型</th>'
		        	  +'<th>附件</th>'
		        	  +'</tr>' 
		        	  +'</thead>' 
		        	  +'<tbody>' 
		        	  +'{{each toAccesoryList as item index}}'
		        	  +'<tr>' 
		        	  +'<td>' 
		        	  +'{{item.accessoryName}}'
		        	  +'</td>' 
		        	  +'<td>'
		        	  +'<ul id="{{item.accessoryCode}}_pic_list" class="filelist">'
		        	  +'{{each attachmentList as item2 index}}'
                      +'{{if item.accessoryCode==item2.preFileCode}}'
	                      +'<li id="{{item2.preFileAdress}}">'
			        	  +'<p class="title">'
			        	  +'{{item2.fileName}}'
			        	  +'</p>'
			        	  +'<p class="imgWrap">'
			        	  +'<img src=\"'+appCtx['img-centanet'] +'/salesweb/image/{{item2.preFileAdress}}/80_80_f.jpg\" data=\"'+appCtx['shcl-filesvr-web'] +'/JQeryUpload/getfile?fileId={{item2.preFileAdress}}\" width=\"'+settings.thumbnailWidth+'\" height=\"'+settings.thumbnailHeight+'\"/>'
			        	  +'</p>'
			        	  +'<div class="file-panel" style="height: 0px;">'
			        	  +'<span class="cancel">'
			        	  +'删除'
			        	  +'</span>'
			        	  +'</div>'
			        	  +'</li>'
		        	  +'{{/if}}'
                      +'{{/each}}'
		        	  + "<span class='"+ settings.pick +" add-file' id=\"{{item.accessoryCode}}\">"
		        	  +'</span>'
		        	  +'</ul>'
		        	  +'</td>'
		        	  +'</tr>'
		        	  +'{{/each}}'
		        	  +'</tbody>'
		        	  +'</table>'
		        		var render = template.compile(templeteSource);
		        	    fileuploadHtml = render(data);
		          } else {
		        	    fileuploadHtml = template(settings.templeteId , data);
		          }
		          return fileuploadHtml;
		      };
		      
		      removeFile = function(li) {
		    	  var $li = li;
		    	  
		    	  var id = $li.attr("id");
		    	  //li.off().end().find('.file-panel').off().end().remove();
		    	  deleteAttachment(id, $li);
		      };
		      
		      deleteAttachment = function (preFileAdress,li) {
		    	  $.ajax({
		  			type : 'post',
		  			cache : false,
		  			async : false,//false同步，true异步
		  			dataType : 'json',
		  			url : ctx+'/attachment/delAttachmentByFileAddress',
		  			data : {preFileAdress:preFileAdress},
		  			success : function(data) {
		  				if(data.success){
		  					li.off().find('.file-panel').off().end().remove();
		  				} else if(!data) {
		  					window.wxc.error(data.message);
		  				}
		  			}
		  		});
		      };
		      
		      addAttachment = function(caseCode,partCode,id,preFileCode,fileName) {
		    	  $.ajax({
		    			cache : true,
		    			async : false,//false同步，true异步
		    			type : "POST",
		    			url : ctx+'/attachment/saveAttachment',
		    			dataType : "json",
		    			data : [ {
		    				name : 'pictureNo',
		    				value : id
		    			}
		    			, {
		    				name : 'framePart',
		    				value : preFileCode
		    			},{
		    				name : 'picName',
		    				value :  fileName
		    			},{
		    				name : 'caseCode',
		    				value :  caseCode
		    			},{
		    				name : 'partCode',
		    				value :  partCode
		    			}],
		    			success : function(data) {
		    			},
		    			error : function(errors) {
		    				window.wxc.error("附件添加出错。");
		    			}
		    		});
		      };
		      
		      initImgViewer = function () {
		    	  
		    	  $('#fileUploadContainer').viewer('destroy');
				  $('#fileUploadContainer').viewer({zIndex:15001,url:"data"});
		      };
		      
		      isCompletedUpload = function() {
		    	  var $container = $( '#'+container);
			      var $error = $container.find('div.error');
		    	  if (!$error.length) {
				     return true;
				  } else {
					 return false;
				  }
		      };
		      
		      return  {
		    	  init : init,
		    	  isCompletedUpload : isCompletedUpload
		      }
		  
});
