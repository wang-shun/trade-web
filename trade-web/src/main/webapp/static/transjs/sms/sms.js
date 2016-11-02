(function($) {
	var _PS;
	function ps(){return{
			_f:{ e:{},c:'',caseCode:'',serviceItem:''},
			_data:{},
			_content:{},
			init:function(f){
				  this._f=$.extend(this._f,f);
				  this.getSms();
				  _PS=this;
				  return this;
			},render:function(){
				$(this._f.e).empty();
				var mf=$("<div>").addClass("modal fade").attr('aria-hidden',true);
				var md=$("<div>").addClass('modal-dialog').attr('style','width:900px');
				var mc=$("<div>").addClass('modal-content');
				var mh=$('<div>').addClass('modal-header');
				var cb=$('<button>').addClass('close').attr('data-dismiss',"modal").attr('aria-hidden',true).text("×");
				var mb=$('<div>').addClass('modal-body');
				md.append(mc);
				mh.append(cb);
				mc.append(mh).append(mb);
				mf.append(md);
				
				var msTempleCode=$('<select>').addClass('msTempleCode form-control m-b').change(this.dataChange);
				msTempleCode.append($('<option>').val('').text("请选择").attr('selected',true));
				var msPhone=$("<input>").addClass('msPhone form-control  m-b');
				var msContent=$("<textarea>").addClass('msContent form-control  m-b').attr('style',"height: 220px;");
				var btnSend=$("<button>").addClass('btn btn-success btn-space').text('发送').click(this.sendSms);
				$(this._data).each(function(){
					msTempleCode.append($('<option>').val(this.userFlag).text(this.userName));
				});
				mb.append($('<div>').addClass('row').append($("<label>").addClass('col-sm-2 control-label').text('接收人')).append($("<div>").addClass('col-sm-10').append(msTempleCode)));
				mb.append($('<div>').addClass('row').append($("<label>").addClass('col-sm-2 control-label').text("接收电话")).append($("<div>").addClass('col-sm-10').append(msPhone)));
				mb.append($('<div>').addClass('row').append($("<label>").addClass('col-sm-2 control-label').text("短信内容")).append($("<div>").addClass('col-sm-10').append(msContent)));
				mb.append($('<div>').addClass('row').append($("<label>").addClass('col-sm-12 control-label').append(btnSend)));
				this._content=this._f.e;
				$(this._f.e).append(mf);
				mf.modal('show');
			},getSms:function(){
				_getSms(this._f.ctx,this,this._f.caseCode,this._f.serviceItem);
			},sendSms:function(){
				var p=$(_PS._content).find('.msPhone').val();
				var c=$(_PS._content).find('.msContent').val();
				_sendSms(c,p,_PS);
			},hide:function(){
				$(this._f.e).children(':first').modal('hide');
				return this;
			},show:function(){
				return this.render();
			},setData:function(d){
				this._data=d;
				this.render();
			},dataChange:function(){
				var refObj=_PS;
				var tc =$(refObj._content).find('.msTempleCode').val();
				var v={};
				$(refObj._data).each(function(){
					if(this.userFlag==tc){
						v=this;
					}
				});
				$(refObj._content).find('.msPhone').val(v.phone);
				$(refObj._content).find('.msContent').val(v.content);
			}
		};
	}
	function _getSms(ctx,refObj,caseCode,serviceItem){
		var t=refObj;
		var tData={};
		tData['caseCode']=caseCode;
		tData['serviceItem']=serviceItem;
		$.ajax({url : ctx + "/sms/getTradeSms",
				type : "post",
				dataType : "json",
				data:tData,
				success : function(data) {
					t.setData(data);

				}
		});
	}
	function _sendSms(content,phone,refObj){
		var t=refObj;
		$.ajax({url : ctx + "/sms/sendSms",
				type : "post",
				dataType : "json",
				data:{content:content,phone:phone},
				success : function(data) {
					if(data.success){
						t.hide();
					}else{
						alert('发送失败！');
					}
				}
		});
	}
	$.fn.extend({smsPlatFrom:function(f){
		f.e=$(this);
		return new ps().init(f);
	}});
})(jQuery);