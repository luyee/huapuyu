/**
 * 枚举函数
 */
function each(obj, fn) {
	var name, i = 0, len = obj.length;
	if (len === undefined) {
		for (name in obj) {
			if (fn.call(obj[name], name, obj[name]) === false) {
				break;
			}
		}
	} else {
		while (i < len) {
			if (fn.call(obj[i], i, obj[i++]) === false) {
				break;
			}
		}
	}
}
/**
 * 查询条件联动
 *
 * {
 *	type : 'select',
 *	selectId : 'querySelectId',
 *	normal : 'normalFieldId',
 *	maps : {
 *		sendTime : 'sendTime',
 *		sendStatus : 'sendStatus',
 *		sendUserName : false,
 *		sendContent : 'sendContent',
 *		custName : false,
 *		contactName : false,
 *		contactPhone : false
 *	}
 * }
 */
function querySelChange(map, frm) {
	var r = frm ? document.forms[frm][map.selectId] : document.getElementsByName(map.selectId);
	function hideOthers() {
		each(map.maps, function(i, v) {
			if (v) {
				try{
					G(v).style.display = 'none';
				}catch(e) {}
			}
		});
		G(map.normal).hide();
	}
	if (map.type && map.type == 'select') {
		G(map.selectId).onchange = function() {
			var has = map.maps[this.value];
			hideOthers();
			if (has) {
				var chgIt = G(has);
				chgIt.style.display = '';
			} else {
				G(map.normal).style.display = '';
			}
		}
	} else {
		each(r, function (i, v) {
			v.onclick = function () {
				hideOthers();
				var has = map.maps[this.getAttribute('rel')];
				if (has) {
					var chgIt = G(has);
					chgIt.style.display = '';
				} else {
					G(map.normal).style.display = '';
				}
			}
		});
	}
}

/**
 * 多级联动下拉列表的渲染和事件注册
 * @param {Object} config 配置对象
 * @param {Number} level 级别，为当前select以及后级select注册事件
 */
function initMultiSelect(config, level) {
	for (var i = level !== undefined ? level : 0, info; info = config[i]; i++)	{
		// 获得select的DOM对象
		var select = G(info.name);

		if (info.data)
		{
			// 清空select
			while (select.length > 0) {
				select.removeChild(select.options[select.length - 1]);
			}

			// 重新渲染select
			for (var j = 0; info.data[j]; j += 2) {
				select.options[j / 2] = new Option(info.data[j + 1], info.data[j]);
			}
		}
		else
		{
			info.data = [];
		}

		// 设置选中项
		if (info.value !== undefined) {
			select.value = info.value;
		}

		// 将一些值带入select作为附加属性
		select.level = i;
		select.config = config;

		// 事件注册
    if (level === undefined) {
      select._change = select.onchange;
      select.onchange = initMultiSelect.change;
    }
	}
}


initMultiSelect.change = function(force) {
	if (!this.config[this.level].url || (this._change && this._change() === false)) {
		return;
	}
	// 实例化Ajax对象
	var s = new Ajax();

	// 设置select对象到Ajax对象的select属性
	s.select = this;

	// 数据接收完毕的处理
	s.onSuccess = function(value) {
		eval("var config=" + value);
		var select = this.select;
		for (var i = 0, info; info = config[i]; i++) {
			var o = select.config[select.level + i + 1];
			o.data = info.data;
			if (info.value) {
				o.value = info.value;
			}
		}
		initMultiSelect(select.config, select.level + 1);
    info = select.config[select.level + 1];
    info.receive && info.receive();
		return false;
	}

	// 重置下级的事件和下拉选项值
	for (var i = this.level + 1, info; info = this.config[i]; i++) {
//		G(info.name).onchange = null;
		info.data = [];
	}

	// 发送请求
	s.get(this.config[this.level].url + this.value);
}

/**
 * 通用验证提示信息显示
 */
var FormInfo = {
	show : function (dom, text) {
		var has = this.hasInfo(dom);
		if (!has) {
			has = dom.nextSibling || null;
			var div = document.createElement('div');
			div.className = 'fieldErr';
			div.innerHTML = text;
			dom.parentNode.insertBefore(div, has);
		} else {
			has.innerHTML = text;
		}
	},

	remove : function(dom) {
		var has = this.hasInfo(dom);
		if (has) {
			has.parentNode.removeChild(has);
		}
	},
	clearAllInfo : function(prt) {
		var infoDiv = prt.getElementsByTagName('*');
		var arr = [];
		each(infoDiv, function (i, v) {arr.push(v);});
		each(arr, function(index, value) {
			if (value && value.className && value.className == 'fieldErr') {
				FormInfo.remove(value.previousSibling);
			}
		});
	},
	hasInfo : function(dom) {
		try{
			var has = dom.nextSibling;
		}catch(e) {
			return false;
		}
		if (has && has.nodeName.toLowerCase() == 'div' && has.className == 'fieldErr') {
			return has;
		}
		return false;
	}
};


/**
 * 客户资料处理
 */
var Cust = {
	expando : 'jsl' +  (+new Date()),
	uuid : 0,
	windowData : {},
	cache: {},
	hasErr : 0,
	ERR : [],
	text : {
		'custName' : '公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、阿拉伯数字等字符！',
		'trade1'   : '请选择一级行业',
		'trade2'   : '请选择二级行业',
		'siteName' : '请填写网站名称',
		'siteURL'  : '请填写正确的URL',
		'formReset': '请确认是否重置表单，确认后页面将恢复到初始未改动状态',
		'topErr'   : '页面中存在填写错误, 请修改后再次提交',
		'required' : function (f, id) {
			var _map = {
				'cust.custName' : Cust.infoText.corpName + '（或' + Cust.infoText.custName + '）',
				'custDomainName': Cust.infoText.siteName
			};
			if (id && (_map[id])) {
				f = _map[id];
			}
			return '请填写' + f;
		},
		'info'     : function (text) {
			return 'text';
		},
		'more'     : function (n) {
			return '请输入不超过' + n + '个字符';
		},
		'format'   : function (f) {
			var _map = {
				'mail'   : Cust.infoText.mail,
				'url'    : Cust.infoText.url,
				'phCode' : Cust.infoText.phCode,
				'phNum'  : Cust.infoText.phNum,
				'phExt'  : Cust.infoText.phExt,
				'phMB'   : Cust.infoText.phMB,
				'ph'   : Cust.infoText.ph,
				'fax'   : Cust.infoText.fax
			};
			if (f == 'number') {
				return '请填写数字';
			} else {
				return '请填写正确的' + _map[f];
			}
		}
	},
	infoText : {
		'contact' : '联系人',
		'telephone' : '联系人电话-座机',
		'mobile' : '联系人电话-手机',
		'recip' : '收件人',
		'phone' : '联系人电话',
		'corpName' : '公司名称',
		'custName' : '客户名称',
		'phCode' : '区号',
		'phNum' : '电话号码',
		'phExt' : '分机',
		'phMB' : '手机',
		'ph' : '电话',
		'siteName' : '网站名称',
		'mail' : '邮箱',
		'url'  : 'URL',
		'fax'  : '传真'
	},

	// 座机国家代号
	COUNTRYCODE : {
	    'CHINA' : '86'
	},

    /**
     * 点击后处理链接时的处理
     * @param {Element} afterCureEl - 后处理链接
     */
    afterCure : function(afterCureEl) {
        var suffix = afterCureEl.getAttribute("suffix"),
	    params = {};
	params.afterCureAction = afterCureEl.getAttribute("afterCureAction");
	params.suffix = suffix;
	Cust.showAfterCure(params);
    },

    showAfterCure : function (params) {
	var node = findSelectedTab(),
	    suffix = params.suffix,
	    callType = node.call - 0;

        if (callType == 0) { //呼入弹屏
            var callId = node.callId;
            G("afterCureCallId" + suffix).value = callId;
            G("afterCureSite" + suffix).value = "3";
        }
        else if (callType == 1) { //网单弹屏
            var netOrderId = node.netOrderId;
            G("afterCureNetOrderId" + suffix).value = netOrderId;
            G("afterCureSite" + suffix).value = "2";
        }
        else if (callType == 2) { //留言弹屏
            var callMessageId = node.callMessageId;
            G("afterCureMessageId" + suffix).value = callMessageId;
            G("afterCureSite" + suffix).value = "4";
        }

        var afterCureFrame = G("afterCureFrame" + suffix);
	G('afterCureUrl' + suffix).value = G("custUrlName" + suffix).value.trim();
        afterCureFrame.src = params.afterCureAction;
        Popup.show('afterCurePanel' + suffix);
    },
	
	/**
	 * 国家代码选择处理
	 * 联动设置国家代码输入框 并触再次触发座机验证
	 *
	 * @param (HTMLElement) 触发选择事件的select
	 * @param {String} id标示符，用于替换后得到相应input的id
	 */
	selectPhCountryCode : function (el, idSuffix) {
	    var id = idSuffix.replace(/@@@/g, 'phCountryCode'),
		fixChk = false,
		type = idSuffix.indexOf('Fax') >= 0 ? 'fax' : 'ph';

	    G(id).value = el.value;
	    if (el.value != Cust.COUNTRYCODE.CHINA) {
		fixChk = true;
	    }
	    FormInfo.remove(G(id).parentNode);
	    if (!Cust.checkPH(null, [
		idSuffix.replace(/@@@/g, 'phAreaCode'), 
		idSuffix.replace(/@@@/g, 'phNum'),
		idSuffix.replace(/@@@/g, 'phExtension'),
		type
	    ], fixChk)) {
		FormInfo.show(G(id).parentNode, Cust.text.format(type));
	    }
	},

    // 审核客户资料
	auditCust : function (formid, url) {
	    var form = G(formid),
		action = form.action;

	    form.action = url;
	    (Cust.chkForm(form.name, form.name.replace(/^rd/, ''), true)) && (form.submit());
	    form.action = action;
	},
    

	// 初始化
	init : function (frm, suffix) {
		var frm = document.forms[frm];
		var tradeList1 = frm['cust.custTrade1Id'];
		var tradeList2 = frm['cust.custTrade2Id'];
		var city = frm['cust.custCityId'];
		var prov = frm['cust.custProvId'];
		var dist = frm['cust.custDistrictId'];
		// 行业改变时验证信息需要改变
		var tradeArr = [tradeList1, tradeList2];
		each(tradeArr, function (i, v) {
			Event.add(v, 'change', function () {
				if (v.options[0].value != v.value) {
					FormInfo.remove(v.parentNode);
				} else {
					var info;
					if (tradeArr[0].value == '-1') {
						info = Cust.text.trade1;
					} else if (tradeArr[1].value == '-1') {
						info = Cust.text.trade2;
					}
					FormInfo.show(v.parentNode, info);
				}
			});
		});
		var pcdArr =[prov, city, dist];
		each(pcdArr, function (i, v) {
			Event.add(v, 'change', function () {
				if (v.options[0].value != v.value) {
					FormInfo.remove(v.parentNode);
				} else {
					var info;
					if (pcdArr[0].value == '-1') {
						info = '请选择省份';
					} else if (pcdArr[1].value == '-1') {
						info = '请选择城市';
					}
					FormInfo.show(v.parentNode, info);
				}
			});
		});
		// 建站情况改变时有些验证信息需要改变
		var siteArr = document.getElementsByName('cust.webSite');
		each(siteArr, function (i, v) {
			Event.add(v, 'click', function () {
				if (v.getAttribute('rel') > 1) {
					var _arr = [];
					each(Cust.ERR, function (m, n) {
						if (n.key != 'custDomainName' && n.key != 'custUrlName') {
							_arr.push(n);
						}
					});
					Cust.ERR = _arr;
				} else {
					var has = false;
					each(Cust.ERR, function (m, n) {
						if (n.key == 'custDomainName' || n.key == 'custUrlName') {
							has = true;
							Cust.ERR[m].value = Cust.data(window, n.key);
						}
					});
					if (!has) {
						Cust.ERR.push({ 'key' : 'custDomainName', 'value' : Cust.data(window, 'custDomainName')});
						Cust.ERR.push({ 'key' : 'custUrlName', 'value' : Cust.data(window, 'custUrlName')});
					}
				}
			});
		});
	},

	/**
	 * 数据缓存设置
	 *
	 * @private
	 * @param {HTMLElement} elem 元素
	 * @param {String} name key
	 * @param {Any} data 缓存的数据
	 * @return 返回指定name或id的值
	 */
	_data: function( elem, name, data ) {
		elem = elem == window ?	Cust.windowData : elem;
		var id = elem[ Cust.expando ];
		if ( id === undefined ) {
			id = elem[ Cust.expando ] = ++Cust.uuid;
		}
		if ( name && !Cust.cache[ id ] ) {
			Cust.cache[ id ] = {};
		}
		if ( data !== undefined ) {
			Cust.cache[ id ][ name ] = data;
		}
		return name ? Cust.cache[ id ][ name ] : id;
	},

	/**
	 * 数据缓存设置
	 */
	data : function (elem, key, value) {
		if ( value === undefined ) {
			var data = Cust._data(elem, key);
			return data === undefined ? null : data;
		} else {
			Cust._data( elem, key, value );
			if (window === elem) {
				Cust.ERR.push({ "key" : key, "value" : value});


			}
		}
		Cust.ERR = Cust.unique(Cust.ERR);
	},

	/**
	 * 清除ERR数组中相同key的元素, 清除非错误元素
	 *
	 * @public
	 *
	 * @param {Array} array 待处理的数组
	 */
	unique: function( array ) {
		var ret = [], done = {};

		for ( var i = array.length - 1; i >= 0; i-- ) {
			var id = array[i]['key'];
			if ( !done[ id ] && Cust.data(window, id) !== false ) {
				done[ id ] = true;
                //修改IE下getElementById问题
                var el = G(array[i]['key']);
				if (el && el.getAttribute("id")) {
					ret.push( array[ i ] );
				}
			}
		}

		return ret;

	},

	/**
	 * 设置可动态增加的最多个数
	 */
	config : {
		itemMaxNum : 10
	},

	// 增加该属性记录 item 的 id 信息   避免重复ID引起错误信息显示缺失
	itemIndex : 10,

	/**
	 * 动态增加
	 */
	add : function(o, type) {
		var suffix = o.getAttribute('suffix');
		// 当前项
		var item = o.parentNode;
		// 同类项所在的容器
		var keyElem = item.parentNode;
		var id = this.data(keyElem, type) || 0;
		if (id == 0) {
			this.data(keyElem, type, 0);
		}
		// 模板
		var thisTPL = G(type + '_tpl' + suffix).value;
		// 计数
		this.data(keyElem, type, ++id);
		Cust.itemIndex++;

		// 临时位置  保存临时信息, 因为IE的原因
		var tplTmpContainer = G('tplTmpContainer' + suffix);

		if (type == 'telephone' || type == 'mobile') {
			//var firstId = keyElem.id.split('_')[1];
			//tplTmpContainer.innerHTML = thisTPL.format(firstId, Cust.itemIndex + '');
			tplTmpContainer.innerHTML = thisTPL.replace(/[[\d+]]/g, Cust.itemIndex + '');
		} else {
			tplTmpContainer.innerHTML = thisTPL.format(Cust.itemIndex + '');
		}
		var cld = tplTmpContainer.firstChild;
		if (cld.nodeType == 3) {
			cld = cld.nextSibling;
		}
		// 将临时HTML附加到总容器  清空临时位置
		keyElem.appendChild(cld);
		tplTmpContainer.innerHTML = '';
		// 获取所有链接
		var as = item.getElementsByTagName('a');
		var asLen = as.length;
		if (as[asLen - 1].getAttribute('rel') == 'true') {
			as[asLen - 1].style.display = '';
			if (type == 'contact') {
				as[asLen - 1].style.right = '120px';
			}
		}

		// 针对电话的判断
		if (type == 'telephone' || type == 'mobile') {
			var phs = [], mbs = [];
			var allPhs = keyElem.getElementsByTagName('p');
			each(allPhs, function (i, v) {
				if (v.className.indexOf('tel') != -1) {
					phs.push(v);
				} else {
					mbs.push(v);
				}
			});
			if (type == 'telephone') {
				if (phs.length > 1) {
					if(as[1]) as[1].style.display = '';
				} else {
					if(as[1]) as[1].style.display = 'none';
				}
			}
			if (type == 'mobile') {
				if (mbs.length > 1) {
					if(as[1]) as[1].style.display = '';
				} else {
					if(as[1]) as[1].style.display = 'none';
				}
			}
		}

		// 判断是否超出总数
		if (id + 1 == this.config.itemMaxNum) {
			o.style.display = 'none';
			as[asLen - 1].style.right = '15px';
		}
	},

	hasDeled : {},

	/**
	 * 动态删除
	 *
	 * @param {HTMLElement} oo HTML元素
	 * @param {String} ttype
	 * @param {String} url 物理删除提交的Action, 存在url则一定会向后端发送请求来直接删除该数据
	 * @param {String} data 提交的操作参数
	 * @param {Boolean} ff 是否是特殊删除, 特殊删除会向后端发送请求, 直接删除该数据.  在前端表现上, 该部分HTML中的input的value会被清空, 相应的ID字段被置空, 但外层DOM不会被remove
	 */
	del : function(oo, ttype, url, data, ff) {
		// 如果url有值   异步删除之  并且处理在页面的展现
		if (url && !Cust.hasDeled[data]) {
			var ajax = new Ajax(function (val) {
				eval('var v = ' + val);
				if (v.result == true) {
					delItem(oo, ttype, ff);
					Cust.hasDeled[data] = true;
				} else {
					FormInfo.show(oo, v.errors);
				}
			});
			ajax.get(url, data);
		} else {
			delItem(oo, ttype, ff);
		}

		// 删除项
		function delItem(o, type, f) {
			// item 表示当前要删除的项
			var item = o.parentNode;
			// 所有同类该项所在的容器
			var keyElem = item.parentNode;
			FormInfo.remove(item);
			// 该容器下所有的项
			var allItems = G(keyElem).clearEmptyNode().childNodes;


			// 项的个数
			var id = Cust.data(keyElem, type);

			// 如果仅有1项  特殊删除
			if (allItems.length == 1) {
				specDel(o, type, f);
			} else {
				// 如果 存在特殊删除参数  特殊删除
				if (f) {
					if (type == 'contact' || type == 'recip') {
						// 元素移除  计数
						keyElem.removeChild(item);
						Cust.data(keyElem, type, --id);
					} else {
						specDel(o, type, f);
						return false;
					}
				} else {
					// 元素移除  计数
					keyElem.removeChild(item);
					Cust.data(keyElem, type, --id);
				}
				// 第一个容器下的所有链接
				var firstItemLinks = allItems[0].getElementsByTagName('a');
				var linkLen = firstItemLinks.length;

				if (type == 'contact' || type == 'recip') {
					// 添加链接  删除链接
					var linkAdd = firstItemLinks[linkLen - 2];
					var linkDel = firstItemLinks[linkLen - 1];
					linkAdd.style.display = '';
					if (type == 'contact') {
						if (allItems.length == 1) {
							linkDel.style.display = 'none';
						} else {
							// 修改页面不允许删除   清洗和重新提交页面可以删除   清洗页面删除时异步提交url  重新提交时删除但不提交
							if (linkDel.getAttribute('clean')) {
								linkDel.style.display = 'none';
							} else {
								linkDel.style.display = '';
								linkDel.style.right = '120px';
							}
						}
					} else if (type == 'recip') {
						linkDel.style.right = '120px';
					}
				} else if (type == 'telephone') {
					firstItemLinks[0].style.display = '';
				} else if (type == 'mobile') {
					var secondItemLinks = keyElem.getElementsByTagName('p')[1].getElementsByTagName('a');
					secondItemLinks[0].style.display = '';
				}
				// 针对电话的判断
				if (type == 'telephone' || type == 'mobile') {
					var as = firstItemLinks;
					var as2 = keyElem.getElementsByTagName('p')[1].getElementsByTagName('a');
					as[0].style.display = as2[0].style.display = '';
					var phs = [], mbs = [];
					var allPhs = keyElem.getElementsByTagName('p');
					each(allPhs, function (i, v) {
						if (v.className.indexOf('tel') != -1) {
							phs.push(v);
						} else {
							mbs.push(v);
						}
					});
					if (type == 'telephone') {
						if (phs.length > 1) {
							if(as[1]) as[1].style.display = '';
						} else {
							if(as[1]) as[1].style.display = 'none';
						}
					}
					if (type == 'mobile') {
						if (mbs.length > 1) {
							if(as2[1]) as2[1].style.display = '';
						} else {
							if(as2[1]) as2[1].style.display = 'none';
						}
					}
				}
			}
		}

		// 特殊删除, 只删除值, 不改变DOM结构 ( 但联系人处会删掉多于的电话(多于两条时)DOM结构 )
		function specDel(o, type, f) {
			// item 表示当前要删除的项
			var item = o.parentNode;
			// 所有同类该项所在的容器
			var keyElem = item.parentNode;
			// 该容器下所有的项
			var allItems = G(keyElem).clearEmptyNode().childNodes;

			// 如果容器下项的数量多于1个   需要删除item  并将剩余的项集合的第一项  显示添加链接
			if (allItems.length > 1 && !f) {
				keyElem.removeChild(item);
				Cust.data(keyElem, type, --id);
			}
			var s = item.getElementsByTagName('input');
			each(s, function (i, v) {
				if (v.type == 'text') {
					v.value = '';
				}
			});

			// 具体
			if (type == 'telephone' || type == 'mobile') {
				item.setAttribute('phId', '');
			} else if (type == 'contact' || type == 'recip') {
				item.setAttribute(type + 'Id', '');
				var phs = Cust.getByClsName('phoneParent', item).getElementsByTagName('p');
				while (phs.length > 2) {
					phs[phs.length - 1].parentNode.removeChild(phs[phs.length - 1]);
					phs = Cust.getByClsName('phoneParent', item).getElementsByTagName('p')
				}
				phs[0].setAttribute('phId', '');
				phs[1].setAttribute('phId', '');
			}
		}
	},

	/**
	 * 表单提交前的验证
	 */
	chkForm : function (frm, suffix, audit) {
		var f = frm = document.forms[frm];
		var turnCust = false;
		var hasErr = false;
		//审核客户资料不进行其它处理
		if (!audit) {
		    var c = callinSubmit();
		    if (c != true) {
			    return false;
		    }
		}
		each(f['callRecord.turnCust'], function () {
			if (this.checked) {
				turnCust = this.value;
			}
		});
		if(turnCust == 0) {
			var custName = frm['cust.custName']; // 公司名称
			var trade1 = frm['cust.custTrade1Id']; // 一级行业
			var trade2 = frm['cust.custTrade2Id']; // 二级行业
			var webSite = frm['cust.webSite']; // 建站情况
			var custDomainName = frm['custDomainName']; // 网站名称
			var custUrlName = frm['custUrlName']; // 网站URl
			var noWebSiteTypeId = frm['cust.noWebSiteTypeId']; // 暂无网站说明
			var custProvId = frm['cust.custProvId']; // 所属地域 省
			var custCityId = frm['cust.custCityId']; // 所属地域 市
			var custDistrictId = frm['cust.custDistrictId']; // 所属地域 区
			var globalMsg = frm['globalMsg']; // 全局错误信息
			FormInfo.remove(globalMsg.parentNode);
			if (custName.value.trim().length == 0) {
				FormInfo.show(custName.parentNode, Cust.text.custName);
				hasErr = true;
            }
            else if (!Cust.validateCustName(custName)){
                    hasErr = true;
			} 
            else {
				FormInfo.remove(custName.parentNode);
			}
			if (trade1.value == '-1') {
				FormInfo.show(trade1.parentNode, Cust.text.trade1);
				hasErr = true;
			} else {
				FormInfo.remove(trade1.parentNode);
			}
			if (trade1.value != '-1') {
				if (trade2.value == '-1') {
					FormInfo.show(trade2.parentNode, Cust.text.trade2);
					hasErr = true;
				} else {
					FormInfo.remove(trade2.parentNode);
				}
			}
			if (webSite[0].checked) {
				if (custDomainName.value.trim() == '') {
					FormInfo.show(custDomainName.parentNode, Cust.text.siteName);
					hasErr = true;
				} else {
					FormInfo.remove(custDomainName.parentNode);
				}
				if (custUrlName.value.trim() == '') {
					FormInfo.show(custUrlName.parentNode, Cust.text.siteURL);
					hasErr = true;
				} else {
					FormInfo.remove(custUrlName.parentNode);
				}
			} else {
				FormInfo.remove(custDomainName.parentNode);
				FormInfo.remove(custUrlName.parentNode);
			}
			if (custProvId.value == '-1') {
				FormInfo.show(custProvId.parentNode, '请选择省份');
				hasErr = true;
			} else if (custCityId.value == '-1') {
				FormInfo.remove(custProvId.parentNode);
				FormInfo.show(custCityId.parentNode, '请选择城市');
				hasErr = true;
			} else {
				FormInfo.remove(custProvId.parentNode);
				FormInfo.remove(custCityId.parentNode);
			}
			// 联系人
			var contactResult = Cust.contactCheck(frm, suffix);
			if (!contactResult || hasErr) {
				return false;
			}
		}

		//二次点击屏蔽
		Cust.setBtnDisabled(true, suffix);
		return true;
	},

    /**
	 * 验证客户名称
	 */
	validateCustName : function(el) {
		var ret = true, val = el.value.trim();
        el.value = val;
		var suffix = el.getAttribute('suffix');
		FormInfo.remove(el.parentNode);

		if (G('custType' + suffix + '_0').checked) {
			ret = /^[\u3040-\u9FFF\uAC00-\uD7AFa-zA-Z @&(),\.<>《》•\d]{2,}$/.test(val);
            if (!ret) {
                FormInfo.show(el.parentNode, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、阿拉伯数字等字符！");
			    Cust.data(window, el.id, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、阿拉伯数字等字符！");
			    return false;
            }
		}
        else if (G('custType' + suffix + '_1').checked) {
		    var custBranchName = G('cust.custBranchName' + suffix);
            custBranchVal = custBranchName.value.trim();
            custBranchName.value = custBranchVal;

			ret =  /^[\u3040-\u9FFF\uAC00-\uD7AFa-zA-Z @&(),\.<>《》•\d]{2,}$/.test(val)
		    && /^[\u3040-\u9FFF\uAC00-\uD7AFa-zA-Z @&(),\.<>《》•\d]{2,}$/.test(custBranchVal);
            if (!ret) {
                FormInfo.show(el.parentNode, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、阿拉伯数字等字符！");
			    Cust.data(window, el.id, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、阿拉伯数字等字符！");
			    return false;
            }
		}
		else if (G('custType' + suffix + '_2').checked) {
			ret = /^[\u4E00-\u9FA5a-zA-Z]{2,}$/.test(val);
            if (!ret) {
                FormInfo.show(el.parentNode, "姓名非法，只允许包含中文汉字、英文字母！");
			    Cust.data(window, el.id, "姓名非法，只允许包含中文汉字、英文字母！");
			    return false;
            }
		}
        Cust.data(window, el.id, false);
        return true;
	},

	/**
	 * 页面错误头部显示
	 *
	 * @param {String} id 要显示的位置
	 */
	errorTips : function (id) {
		id = G(id);
		id.setHTML(Cust.text.topErr).show('block');
		setTimeout(function () {
			id.setHTML('').hide();
		}, 3000);
	},

	/**
	 * 联系人验证
	 *
	 * @param {HTMLFormElement} frm Form元素
	 * @return {Boolean} 是否通过
	 */
	contactCheck : function (frm, suffix) {
		// 联系人
		var contacts = G('contacts' + suffix).clearEmptyNode();
		contacts = contacts.childNodes;
		var contactData = '[';
		// 是否有错误
		var _flag = 0;

		for (var i = 0; i < contacts.length; i++) {
			var v = contacts[i];
			var vrel = v.getAttribute('id');

			if (v && v.nodeName && v.nodeName.toLowerCase() == 'div') {
				var fields = v.getElementsByTagName('input');
				var len = fields.length;
				var contactId = v.getAttribute('contactId') || '';

				// contactName
				var contactName = fields[0];
				if (contactName.value == '') {
					FormInfo.show(G(contactName.id).parentNode, Cust.text.required(Cust.infoText.contact));
					_flag = 1;
				} else {
					FormInfo.remove(G(contactName.id).parentNode);
				}
				contactName = contactName.value.trim();

				// contactRemark , contactSex
				var contactRemark = fields[len - 1].value.trim();
				var contactSex = frm[fields[len - 2].name];
				each(contactSex, function (sexi, sexv) {
					if (sexv.checked) {
						contactSex = sexv.value;
					}
				});

				// policyType
				var policyTypeId = frm[fields[len - 5].name];
				each(policyTypeId, function (sexi, sexv) {
					if (sexv.checked) {
						policyTypeId = sexv.value;
					}
				});

				// contactDuty , personalMail , companyMail
				//var contactDuty = fields[len - 8].value.trim();
				//var custPersonalMail = fields[len - 9].value.trim();
				//var custCompanyMail = fields[len - 10].value.trim();
				var contactDuty = G(vrel + '_contactDuty').value.trim();
				var custPersonalMail = G(vrel + '_custPersonMail').value.trim();
				var custCompanyMail = G(vrel + '_custCompanyMail').value.trim();

				if (i > 0) {
					contactData += ',';
				}
				contactData += '{"contactId":"'+ contactId +'", "contactName":"'+ contactName +'", "contactRemark":"'+ contactRemark +'", "contactSex":"'+ contactSex +'", "policyTypeId":"'+ policyTypeId +'", "contactDuty":"'+ contactDuty +'", "custPersonalMail":"'+ custPersonalMail +'", "custCompanyMail":"'+ custCompanyMail +'", "contactPhone":[';

				var phsParent = Cust.getByClsName('phoneParent', v);
				phsParent = G(phsParent).clearEmptyNode();
				var phs = phsParent.childNodes;

				// 是否有电话
				var _hasPh = 0;
				each(phs, function (m, n) {
					if (n && n.nodeName && n.nodeName.toLowerCase() == 'p') {
						var phId = n.getAttribute('phId') || '';
						var inputs = n.getElementsByTagName('input');
						var ilen = inputs.length;
						var regForPhone = [];
						if (ilen == 4) {
							if (inputs[0].value == Cust.COUNTRYCODE.CHINA) {
							   regForPhone = [/^0\d{2,3}$/, /^\d{7,8}$/];
							}
							else {
							    regForPhone = [/^\d{0,4}$/, /^\d{1,8}$/];
							}
							if (!regForPhone[0].test(inputs[1].value) || !regForPhone[1].test(inputs[2].value)) {
								if (inputs[1].value != '' || inputs[2].value != '') {
									_flag = 1;
									FormInfo.show(inputs[1].parentNode, Cust.text.format('ph'));
								}
							} else {
								FormInfo.remove(inputs[1].parentNode);
								_hasPh = 1;
								if (m > 0 && !contactData.endWith('[')) {
									contactData += ',';
								}
								contactData += '{"phType":"0", "phId":"'+ phId +'", "phCountryCode":"'+ inputs[0].value +'", "phAreaCode":"'+ inputs[1].value +'", "phNum":"'+ inputs[2].value +'", "phExtension":"'+ inputs[3].value +'"}';
							}
						} else {
							var mbPh = inputs[0].value;
							if ((!/^\d{11,12}$/.test(mbPh) || (mbPh.length == 12 && !mbPh.startWith('0')) || (mbPh.length == 11 && !mbPh.startWith('1')))) {
								if (mbPh != '') {
									_flag = 1;
									FormInfo.show(inputs[0].parentNode, Cust.text.format('phMB'));
								}
							} else {
								if (mbPh.length == 12) {
									mbPh = mbPh.substr(1);
								}
								FormInfo.remove(inputs[0].parentNode);
								_hasPh = 1;
								if (m > 0 && !contactData.endWith('[')) {
									contactData += ',';
								}
								contactData += '{"phType":"1", "phId":"'+ phId +'", "phNum":"'+ mbPh +'"}';
							}
						}
					}
				});
				var phsLastId = phs[phs.length - 1];
				if (phsLastId.nodeName.toLowerCase() != 'p') {
					phsLastId = phs[phs.length - 2];
				}
				phsLastId = phsLastId.getElementsByTagName('input')[0].id;
				if (_hasPh == 0) {
					_flag = 1;
					FormInfo.show(G(phsLastId).parentNode, Cust.text.format('ph'));
				} else if (!_flag) {
					FormInfo.remove(G(phsLastId).parentNode);
				}
				contactData += ']}';
			}
		}
		if (_flag == 0) {
			contactData += ']';
			frm['contact'].value = contactData;
			return true;
		} else {
			return false;
		}
	},


	/**
	 * 座机与传真验证
	 *
	 * @param {HTMLFormElement} frm Form元素
	 * @param {Array} params 参数配置
	 * ["code", "num", "ext", "ph|fax"]
	 * @param {Boolean} 是否对座机进行非严格验证。（适用于外国电话）
	 * @return {Boolean} 是否通过
	 */
	checkPH : function (frm, params, fixChk) {
		var code = G(params[0]).value;
		var num  = G(params[1]).value;
		var ext  = G(params[2]).value;
		var phstr = code + '-' + num + '-' + ext;
		var re;
		if (fixChk) {
		    //对国外电话进行非严格验证
		    re = /^(\d{0,4})-(\d{1,8})-(\d{0,8})?$/;
		}
		else {
		    re = /^(0\d{2,3})-(\d{7,8})-(\d{0,8})?$/;
		}

		// 如果都为空  通过
		if (code == '' && num == '' && ext == '') {
			Cust.data(window, params[0], false);
			return true;
		}

		// 有一个不为空
		var match = phstr.match(re);
		if (match) {
			switch (params[3]) {
				case 'fax':
					if (frm) {
						frm['cust.custFax'].value = match[1] + '-' + match[2] + (!match[3] ? '' : '-' + match[3]);
					}
					break;
				case 'ph':
				default:
					break;
			}
			Cust.data(window, params[0], false);
			return true;
		}

		// 错误信息提示
		var str = '';
		switch (params[3]) {
			case 'fax':
				str = Cust.text.format('fax');
				break;
			case 'ph':
				str = Cust.text.format('ph');
				break;
			default:break;
		}
		Cust.data(window, params[0], str);
		return false;
	},

	/**
	 * 根据className获取HTML元素对象
	 *
	 * @param {String} clsName className
	 * @param {HTMLElement} parent 在parent元素内查找
	 * @return 返回符合条件的HTML元素集合
	 */
	getByClsName : function (clsName, parent, f) {
		var ds = parent.getElementsByTagName('*'), ret, reta = [];
		each(ds, function (i, v) {
			if (v.className == clsName) {
				ret = G(v);
				reta.push(ret);
			}
		});
		return !f ? ret : reta;
	},

	/**
	 * 表单重置
	 */
	resetForm : function () {
		if (confirm(Cust.text.formReset)) {
			location.reload();
		}
	},

	/**
	 * 客户类型切换
	 */
	custType : function (radio, n) {
		var suffix = radio.getAttribute('suffix');
		var _custName = G('_custName' + suffix);
        var personalRemark = G('cust.personalRemark' + suffix);
		var _custBranch = G('_custBranch' + suffix);
		Cust.validateCustName(G("cust.custName" + suffix) );
		if (n == 0) {
			_custName.innerHTML = Cust.infoText.corpName + '<b>*</b>';
            personalRemark.hide();
			_custBranch.hide();
		} else if (n == 1) {
			_custName.innerHTML = Cust.infoText.corpName + '<b>*</b>';
            personalRemark.hide();
			_custBranch.show();
		} else if (n == 2) {
			_custName.innerHTML = Cust.infoText.custName + '<b>*</b>';
            personalRemark.show();
			_custBranch.hide();
		}
	},
	/**
	 * 自动审核的检查
	 * ths this
	 * elem this.parentNode
	 * url
	 * data fieldName
	 * isOptional
	 * sizeLimit
	 * autoCheck
	 */
	autoCheck : function (ths, url, data, isOptional, sizeLimit, autoCheck, type) {
		var elem = ths.parentNode;
		var thsid = ths.id;
		if (data == 'custName') {
			thsid = ths.name;
		}
		if (thsid.startWith('custDomainName')) {
			thsid = 'custDomainName';
		}
		var suffix = ths.getAttribute('suffix');
		var fval = ths.value.trim();
		var original = ths.getAttribute('original');
        //特殊企业中备份第二框的fval和original值
        var fvalBak = "", originalBak = "";

        //如果是客户名称或特殊企业则需要先验证
        if (data == 'custName' || data == 'custBranchName') {
            var result = Cust.validateCustName(G('cust.custName' + suffix));
            //验证不通过，不进行后端验证
            if (!result) {
                return false;
            }
            //个人客户，不进行后端验证
            if (G('custType' + suffix + '_2').checked) {
                return false;
            }
			fvalBak = G('cust.custBranchName' + suffix).value.trim();
			originalBak = G('cust.custBranchName' + suffix).getAttribute('original');
        }
		if (data == 'custBranchName') {
			if (fval == '') {
				return false;
			}
			elem = elem.parentNode;
			data = 'custName';
			thsid = 'cust.custName';
			isOptional = false;
			fval = G('cust.custName' + suffix).value.trim();
			original = G('cust.custName' + suffix).getAttribute('original');
		}
		FormInfo.remove(elem);
		if (!thsid) {
			thsid = ths.name;
		}
		// 标志特殊地方的精确提示
		var flag = '';

		var thsIdArr = thsid.split('_') || [];
		if (thsIdArr.length == 3 && thsIdArr[0] == 'contact' + suffix && thsIdArr[2] == 'contactName') {
			flag = Cust.infoText.contact;
		}

		// 数字
		if (type && type == 'number' && (isNaN(fval) || ((thsid.toLowerCase().indexOf('custzip') != -1 || thsid.toLowerCase().indexOf('recipzip')) != -1 && fval.indexOf('.') != -1))) {
			FormInfo.show(elem, Cust.text.format('number'));
			return false;
		}

		// 是否是可选
		if (!isOptional && fval.trim() == '') {
			FormInfo.show(elem, Cust.text.required(flag, thsid));
			return false;
		}
		Cust.data(window, thsid, false);

		// 字节限制
		if (sizeLimit !== '' && fval.trim().length > sizeLimit) {
			FormInfo.show(elem, Cust.text.more(sizeLimit));
			return false;
		}
		Cust.data(window, thsid, false);

		// 是否自动审核
		if (autoCheck === true) {
			// 如果 含有original属性  并且 当前值与original值相等   则不提交
			if ((fval + fvalBak) != (original + originalBak)) {
				var sendData = data + '=' + encodeURIComponent(fval);
				var frm = document.forms['rd' + suffix];
				var custTypeIds = frm['cust.custTypeId'];
				var custTypeId = '';
				each(custTypeIds, function (i, v) {
					if (v.checked) {
						custTypeId = v.value;
					}
				});
				fval = encodeURIComponent(fval);
				// 针对公司名称
				if (data == 'custName' && custTypeId == '1') {
					sendData = 'custName=' + encodeURIComponent(frm['cust.custName'].value) + '&custBranchName=' + encodeURIComponent(frm['cust.custBranchName'].value);
				}
				var ajax = new Ajax(function (value) {
						eval('var v = ' + value);
						if (v.result == true) {
							Cust.data(window, thsid, false);
							if (v.errors != '') {
								FormInfo.show(elem, v.errors);
							}
						} else {
							FormInfo.show(elem, v.errors);
						}
					if(data == 'urlName') {
					    // 如果返回信息含有url项(呼入后处理,呼出投诉)
					    var afterCure = G("afterCure" + suffix);
					    if (v.url) {
						if (afterCure) {
						    afterCure.show();
						    afterCure.setAttribute("afterCureAction", v.url);
						}
					    }
					    else {
						if (afterCure) {
						    afterCure.hide();
						}
					    }
					}
				    });
				ajax.post(url, sendData);
			}
		}
		return true;
	},

	/**
	 * 电话验证
	 */
	checkPhone : function (ths, url, data, n, idSuffix, isOptional) {
		var elem = ths.parentNode;
		var phType = ths.getAttribute('rel');
		FormInfo.remove(elem);
		// 手机支持12位的情况   呼出弹屏
		var phSize = ths.getAttribute('phSize');

		var fval = ths.value;

		// 座机
		if (idSuffix) {
			var ph0 = idSuffix.replace(/@@@/g, 'phAreaCode');
			var ph1 = idSuffix.replace(/@@@/g, 'phNum');
			var ph2 = idSuffix.replace(/@@@/g, 'phExtension');
			var elCC = G(idSuffix.replace(/@@@/g, 'phCountryCode'));
			var _type = ph0.indexOf('Fax') != -1 ? 'fax' : 'ph';
			var fixChk = false;

			if (elCC && elCC.value != Cust.COUNTRYCODE.CHINA) {
			    fixChk = true;
			}

			var phResult = Cust.checkPH(null, [ph0, ph1, ph2, _type], fixChk);
			if (!phResult) {
				FormInfo.show(elem, Cust.text.format(_type));
				return false;
			}

			if (url && G(ph0).value && G(ph1).value && _type == 'ph') {
				if (fval != ths.getAttribute('original')) {
					var ajax = new Ajax(function (value) {
						eval('var v = ' + value);
						if (v.result == true) {
							if (v.errors && v.errors != '{}') {
								FormInfo.show(elem, v.errors);
							}
							Cust.data(window, ph0, false);
						} else {
							FormInfo.show(elem, v.errors);
							Cust.data(window, ph0, v.errors);
						}
					});
					ajax.get(url, 'phAreaCode=' + G(ph0).value + '&phNum=' + G(ph1).value);
				}
			}
			return true;
		}

		// 手机
		if (fval && (!/^\d{11,12}$/.test(fval) || (fval.length == 12 && !fval.startWith('0')) || (fval.length == 11 && !fval.startWith('1')))) {
			FormInfo.show(elem, Cust.text.format('phMB'));
			Cust.data(window, ths.id, Cust.text.format('phMB'));
			return false;
		} else {
			Cust.data(window, ths.id, false);
		}
		if (url && fval) {
			if (fval != ths.getAttribute('original')) {
				if (fval.length == 12) {
					fval = fval.substr(1);
				}
				var ajax = new Ajax(function (value) {
						eval('var v = ' + value);
						if (v.result) {
							if (v.errors && v.errors != '{}') {
								FormInfo.show(elem, v.errors);
							}
							Cust.data(window, ths.id, false);
						} else {
							FormInfo.show(elem, v.errors);
							Cust.data(window, ths.id, v.errors);
						}
					});
				ajax.get(url, 'phNum=' + fval);
				Cust.data(window, ths.id, false);
			}
		}
		return true;
	},

	/**
	 * Email验证
	 */
	checkMail : function (ths, url, data, isOptional, sizeLimit, autoCheck) {
		var elem = ths.parentNode;
		FormInfo.remove(elem);
		if (!ths.id) {
			ths.id = ths.name;
		}
		var fval = ths.value.trim();
		var re = /^[_\w-]+(\.[_\w-]+)*@([\w-])+(\.[\w-]+)*((\.[\w]{2,})|(\.[\w]{2,}\.[\w]{2,}))$/;
		if (fval.trim().length > 0 && !re.test(fval)) {
			FormInfo.show(elem, Cust.text.format('mail'));
			Cust.data(window, ths.id, Cust.text.format('mail'));
			return false;
		}
		Cust.data(window, ths.id, false);
		return this.autoCheck(ths, url, data, isOptional, sizeLimit, autoCheck);
	},

	/**
	 * URL验证
	 */
	checkURL : function (ths, url, data, isOptional, sizeLimit, autoCheck) {
		var elem = ths.parentNode;
		FormInfo.remove(elem);
		if (!ths.id) {
			ths.id = ths.name;
		}
		var fval = ths.value.trim();
		var re = /^[^.。，]+(\.[^.，。]+)+$/i;
		if (!re.test(fval)) {
			FormInfo.show(elem, Cust.text.format('url'));
			Cust.data(window, ths.id, Cust.text.format('url'));
			return false;
		}
		Cust.data(window, ths.id, false);
		return this.autoCheck(ths, url, data, isOptional, sizeLimit, autoCheck);
	},

	//设置表单按钮的disabled
	setBtnDisabled : function (stats, suffix) {
	    callFrame.G('save' + suffix).disabled = stats;
	    callFrame.G('auditBtn' + suffix).disabled = stats;
	},

	/**
	 * 服务端信息处理
	 *
	 * @param {String} frm Form名称
	 * @param {Boolean} result 返回的结果
	 * @param {Object} errors 错误信息
	 */
	serverCheck : function (frm, result, errors, suffix) {
		var node = findSelectedTab();
		var errorMsg = [];
		//解除二次点击屏蔽
		Cust.setBtnDisabled(false, suffix);
		frm = document.forms[frm];
		if (result == false) {
			each(errors, function (i, v) {
				var field = frm[v['name']];

				if (field) {
					Cust.errorTips('topErr' + suffix);
					// 如果是radio 或 checkbox
					if (field.length) {
						field = field[0];
					}
					// option 的处理
					if (field.tagName.toLowerCase() == 'option') {
						field = field.parentNode;
					}
					//FormInfo.show(field.parentNode, v['text']);
					errorMsg.push({'ele' : field.parentNode, 'txt' : v['text']});
					var id = v['name'];
					if (id == 'cust.custTrade2Id' + suffix) {
						id = 'tradeList2' + suffix;
					}
					if (id != 'errorMessage') {
						Cust.data(window, id, v['text']);
					}

				// 特殊错误的精确定位分析处理
				} else {
					var arr = v['name'].split('_');
					var type = arr[0];
					var contactIndex = arr[1];
					var phoneIndex = arr[2];
					var parentElem = false;

					// 如果是收件人的项
					if (arr[0].startWith('recip')) {
						parentElem = G('recips').clearEmptyNode().childNodes[contactIndex];

					// 如果是联系人项
					} else {
						parentElem = G('contacts' + suffix).clearEmptyNode().childNodes[contactIndex];
					}

					var id = parentElem.id;
					var elem = G(parentElem.id + '_' + type);
					if (type == 'contactPhone' || type == 'recipPhone') {
						elem = Cust.getByClsName('phoneParent', parentElem).clearEmptyNode().childNodes[phoneIndex];
						while (elem.nextSibling && elem.nodeName.toLowerCase() != 'p') {
							elem = elem.nextSibling;
						}
						elem = elem.getElementsByTagName('input');
						if (elem[0].id.indexOf('phCountryCode') >= 0) {
						    elem = elem[1];
						}
						else {
						    elem = elem[0];
						}
					}
					var infoElem = elem.parentNode;

					//FormInfo.show(infoElem, v['text']);
					errorMsg.push({'ele' : infoElem, 'txt' : v['text']});
					Cust.data(window, elem.id, v['text']);
				}

			});
			for (var i = 0, e; e = errorMsg[i]; i++) {
			    FormInfo.show(e.ele, e.txt);
			}
		} else if (result == true) {
			alert('您的提交已成功保存');
      if (node.callId)
      {
      	node.oldCallId = node.callId;
      	node.callId = '';
      }
			if (ocxFrame.calling != node) {
				remove(node);
			}
		}
	}
};

/**
 * 客户资料修改历史记录查看
 */
function custModiRecordView() {
	Popup.show('custModiRecordList');
}

/**
 * 简单的操作点击事件处理
 * @public
 *
 * @param {String} name id对应的输入框名称
 * @param {String} id id的值
 * @param {String} url 需要提交的地址
 */
function opClick(name, id, url) {
	var f = document.query;
	f.reset();
	f[name].value = id;
	f.action = url;
	f.submit();
}

/**
 * 验证checkbox至少选择一个
 *
 * @param {Array} name checkbox的name
 * @param {Array} id 出错信息显示位置
 * @param {Array} msg 出错信息的名字
 * @return {Boolean}
 */
function chkRequire(n, d, m) {
	// 单个验证函数
	function chkOne(name, id, msg) {
		var inputs = document.getElementsByTagName('input');
		var chks = [];

		each(inputs, function (i, v) {
			if (v.name == name && v.type == 'checkbox') {
				chks.push(v);
			}
		});

		var hasChked = false;
		each(chks, function (i, v) {
			if (v.checked) {
				hasChked = true;
			}
		});

		if (!hasChked) {
			G(id).innerHTML = '请至少选择一个' + msg;
			return false;
		}
		G(id).innerHTML = '';

		return true;
	}

	var len = n.length;
	var ret = [];
	each(new Array(len), function (i, v) {
		ret[i] = chkOne(n[i], d[i], m[i]);
	});

	var hasErr = true;
	each(ret, function (i, v) {
		if (v != true) {
			hasErr = false;
		}
	});
	return hasErr;
}
function changeSecond(time)
{
	var value = time % 60;
	var result = value ? value + '秒' : '';
	time = Math.floor(time / 60);
	value = time % 60;
	if (value)
	{
		result = value + '分' + result;
	}
	time = Math.floor(time / 60);
	value = time % 24;
	if (value)
	{
		result = value + '小时' + result;
	}
	time = Math.floor(time / 24);
	if (time)
	{
		result = time + '天' + result;
	}
	return result ? result : '0秒';
}
window.onerror = function(){return true;}
