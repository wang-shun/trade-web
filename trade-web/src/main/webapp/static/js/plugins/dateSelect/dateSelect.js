!function($, window) {
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()
		// millisecond
		}

		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}

		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
	var DateSelect = function(el, opts) {
		this._el=el;
		this._opts = {
			cust_date : new Date(),
			formate : 'yyyy/MM月',
			stepExp : 'MM'
		};
		this._opts = $.extend({}, this._opts, opts);
		this._lable_date = $(el).find(".month");
		this._max = this._opts.max;
		this._min = this._opts.min;
		this._stepExp = this._opts.stepExp;
		this._formate = this._opts.formate;
		this._move_btn = $(el).find('input[type=button]');
		this._move_btn_l = $(this._move_btn[0]);
		this._move_btn_r = $(this._move_btn[1]);
		this._move_btn_l.attr('ms-btn', 'l');
		this._move_btn_r.attr('ms-btn', 'r');
		this._cust_date = this._opts.cust_date;
		this._move_btn.click($.proxy(this,'_move'));
		this._render(this.getDate());
	};
	DateSelect.prototype = {
		constructor : DateSelect,
		_move : function(e) {
			var rl = $(e.target).attr('ms-btn') == 'l' ? -1 : 1;
			var cDate = this.getDate();
			switch (this._stepExp) {
			case 'yyyy':
				cDate.setYear(rl);
				break;
			case 'MM':
				cDate.setMonth(cDate.getMonth()+rl);
				break;
			case 'dd':
			default:
				cDate.setMonth(cDate.getMonth()+rl);
				break;
			}
			this._setDate(cDate);
			this._render(cDate);

		},
		getDate : function() {
			return this._cust_date;
		},
		_setDate : function(d) {
			this._cust_date = d;
		},
		_render : function(d) {
			this._lable_date.text(d.format(this._formate));
			if (this._max) {
				this._renderGoBtn(d, this._max, this._move_btn_r);
			}
			if (this._min) {
				this._renderGoBtn(d, this._min, this._move_btn_l);
			}
		},
		_renderGoBtn : function(cd, mmd, e) {
			e.removeAttr('disabled');
			e.removeClass('disable');
			if (typeof mmd == 'string') {
				if (cd.format(this._formate) == mmd) {
					e.attr('disabled', 'disabled');
					e.addClass('disable');
				}
			} else if (typeof mmd == 'object') {
				if (typeof mmd.format == 'function') {
					if (mmd.format(this._formate) == cd.format(this._formate)) {
						e.attr('disabled', 'disabled');
						e.addClass('disable');
					}
				}
			}
		}

	};
	window.DateSelect = DateSelect;
}(jQuery, window);