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
			  var random = 'hwT6tmrtX45NiDMepFnDw6nnsAzJRKD7';
			  
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
						bizCode : null,
						readonly:false,    //是否只读,true:只读,false:可以添加图片
						isNestTable:false, //是否嵌套表格 例子：产调那边上传图片已经在table里边,如果不做限制会影响页面美观
						tdWidth : 100,     //设置表格单元格宽度,该属性一般跟isNestTable共用
						exclude : null     //排除的附件编号
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
				        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
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
							   window.wxc.alert("请检查"+ ctx+url +"的路径是否正确!");
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
					initDeleteImgEvent(settings);
					initImgViewer(settings.maskId);
					
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
				                '<span class="cancel" id="'+ random +'">删除</span>' +
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
					    var preFileCode = $('#'+file.id).siblings().last().attr("id");
						$.each(res.files, function(index, item){
							var id = item.id;
							var fileCat = item.fileCat;
							var fileName = item.fileName;
							
							addAttachment(settings.caseCode,settings.partCode,id,preFileCode,fileName);
							
							var img = $('#'+file.id).find("img");
							img.attr("data",appCtx['aist-filesvr-web']+"/JQeryUpload/getfile?fileId="+id);
							$('#'+file.id).attr("id",id);
							
						});
					    initImgViewer(settings.maskId);
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
			  
			  initDeleteImgEvent = function (settings) {
				  	var fileUploadContainer = settings.fileUploadContainer;
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
			                	removeFile($li,settings.maskId);
			                    return ;

			                case 1:
			                    break;

			                case 2:
			                    break;
			            }
			        });
				  
			  };
			  
			  getRandomString = function _getRandomString(len) {  
				    len = len || 32;  
				    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1  
				    var maxPos = $chars.length;  
				    var pwd = '';  
				    for (i = 0; i < len; i++) {  
				        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));  
				    }  
				    return pwd;  
			  };
		      
		      queryAttachments = function(settings) {
		    	  var ctx = settings.ctx;
		    	  var caseCode = settings.caseCode;
		    	  var bizCode = settings.bizCode;
		    	  var partCode = settings.partCode;
		    	  var available = settings.available;
		    	  var preFileCode = settings.preFileCode;
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
						},{
							name : 'preFileCode',
							value : preFileCode
						}],
						dataType : "json",
						success : function(data) {
				    	if(settings.exclude){
						    //删除附件类型里面排除的类型
						    if(data && data.toAccesoryList){
						    	var accesorys = data.toAccesoryList;
						    	accesorys.forEach(function(e,i){
						    			settings.exclude.forEach(function(f){
						    				if(e.accessoryCode == f){
						    					accesorys.splice(i,1);
						    				}
						    			})
						    		})  
						    	}
						    //删除附件中排除的类型附件
						    if(data && data.attachmentList){
						    	var attachments = data.attachmentList;
						    	attachments.forEach(function(e,i){
						    			settings.exclude.forEach(function(f){
						    				if(e.preFileCode == f){
						    					attachments.splice(i,1);
						    				}
						    			})
						    		})  
						    	}
			    			}
							//如果没有案件编号，就将图片列表置为空
							if(caseCode == null || caseCode == ""){
								data.attachmentList = "";
							}
							
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
		        	  var templeteSource = '<table class="table table-bordered customerinfo">';
		        	  
		        	  if(!settings.isNestTable){
		        		  templeteSource += '<thead><tr><th style="width: 100px;">类型</th><th>附件</th></tr></thead>'
		        	  }
		        	  
		        	  templeteSource += '<tbody>' 
		        	  +'{{each toAccesoryList as item index}}'
		        	  +'<tr>' 
		        	  +'<td style="width: ' + settings.tdWidth + 'px;">' 
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
			        	  +'<img src=\"'+appCtx['shcl-image-web'] +'/image/{{item2.preFileAdress}}/400_300_f.jpg\" data=\"'+appCtx['shcl-filesvr-web'] +'/JQeryUpload/getfile?fileId={{item2.preFileAdress}}\" width=\"'+settings.thumbnailWidth+'\" height=\"'+settings.thumbnailHeight+'\"/>'
			        	  +'</p>';
		        	  
		        	  //如果readonly为false时，可以删除图片操作
		        	  if(!settings.readonly){
		        		  templeteSource += '<div class="file-panel" style="height: 0px;"><span class="cancel">删除</span></div>';
		        	  }
		        	  
		        	  templeteSource += '</li>{{/if}}{{/each}}';
                      
		        	  if(!settings.readonly){
		        		  templeteSource += "<span class='"+ settings.pick +" add-file' id=\"{{item.accessoryCode}}\" name=\"{{item.accessoryCode}}\"></span>";
		        	  }
                      
		        	  templeteSource += '</ul></td></tr>{{/each}}</tbody></table>';
		        		var render = template.compile(templeteSource);
		        	    fileuploadHtml = render(data);
		          } else {
		        	    fileuploadHtml = template(settings.templeteId , data);
		          }
		          return fileuploadHtml;
		      };
		      
		      removeFile = function(li,maskId) {
		    	  var $li = li;
		    	  
		    	  var id = $li.attr("id");
		    	  //li.off().end().find('.file-panel').off().end().remove();
		    	  deleteAttachment(id, $li,maskId);
		      };
		      
		      deleteAttachment = function (preFileAdress,li,maskId) {
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
		  					initImgViewer(maskId);
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
		      
		      initImgViewer = function (maskId) {
				  if(maskId){
					  $('#'+maskId).viewer('destroy');
	  				  $('#'+maskId).viewer({zIndex:16001,url:"data"});
				  }else{
					  $('.wrapper-content').viewer('destroy');
					  $('.wrapper-content').viewer({zIndex:15001,url:"data"});
				  }
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
		      
		      isCompletedUploadById = function(options) {
		    	  var container = options.container;
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
		    	  isCompletedUpload : isCompletedUpload,
		    	  isCompletedUploadById : isCompletedUploadById
		      }
		  
});
