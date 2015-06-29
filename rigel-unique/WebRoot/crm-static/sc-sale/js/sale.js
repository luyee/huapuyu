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
 * JS字符串过滤
 */
function encodeJSStr(str) {
    str = str.replace(/\\/g, '\\\\');
    str = str.replace(/\//g, '\\/');
    str = str.replace(/\t/g, '\\t');
    str = str.replace(/\n/g, '\\n');
    str = str.replace(/\r/g, '\\r');
    str = str.replace(/"/g, '\\"');
    return str;
}


function unDisabledFormItems(formId) {
    var formItems = getAllFormItems(formId || "custAddForm");
    for (var i = 0, length = formItems.length; i < length; i++) {
        var formItem = formItems[i];
        var disabledAttr = formItem.getAttribute("disabled");
        if (disabledAttr) {
            formItem.removeAttribute("disabled");
            formItem.setAttribute("disabledTemp", "true");
        }
    }
    Mask.show();
    return true;
}

function disabledFormItems(formId) {
    var formItems = getAllFormItems(formId || "custAddForm");
    for (var i = 0, length = formItems.length; i < length; i++) {
        var formItem = formItems[i];
        var disabledTempAttr = formItem.getAttribute("disabledTemp");
        if (disabledTempAttr) {
            formItem.removeAttribute("disabledTemp");
            formItem.setAttribute("disabled", "disabled");
        }
    }
    return true;
}

/* 
 * 将伪数组转化成数组
 * @param arrLikeObj - 伪数组 
 * @return {Array} - 真正的数组
 */
function toArray(arrLikeObj) {
    var returnArr = [];
    for (var i = 0, length = arrLikeObj.length; i < length; i++) {
	returnArr.push(arrLikeObj[i]);
    }
    return returnArr;
}

/* 
 * 获得某个表单下的所有input,select,textarea元素(全局方法)
 * @param formId - 表单的ID名
 * @return {Array} - 所有input,select,textarea框列表
 */
function getAllFormItems(formId) {
    var formEl = G(formId) || document.forms[formId];
    var formItems = [];

    var inputEls = formEl.getElementsByTagName("input");
    inputEls = toArray(inputEls);
    var selectEls = formEl.getElementsByTagName("select");
    selectEls = toArray(selectEls);
    var textareaEls = formEl.getElementsByTagName("textarea");
    textareaEls = toArray(textareaEls);
    var formItems = inputEls.concat(selectEls, textareaEls);

    return formItems;
}

/* 
 * 获得指定的表单项元素
 * @param formId - 表单的ID名
 * @param itemList - 表单项列表 eg: "contact.contactName,contact.disabled"
 * @return {Array} - 所有input,select,textarea框列表
 */
function getFormItems(formId, itemList) {
    var formEl = G(formId);
    var formItems = [];

    var itemListArr = itemList.split(",");
    for (var i = 0, length = itemListArr.length; i < length; i++) {
	var itemName = itemListArr[i];
	var item = formEl[itemName];
	if (item && !item.length) {
	    var tempArr = [item];
	    formItems = formItems.concat(tempArr);
	}
	else if (item && item.length > 0) {
		var tempArr;
		/*区分select与checkbox/radio*/
		if (item.tagName && item.tagName.toLowerCase() == 'select') {
			tempArr = [item]
		}
		else {
			tempArr = toArray(item);
		}
	    formItems = formItems.concat(tempArr);
	}
    }

    return formItems;
}

/* 
 * 获得指定或所有含有opType属性的A标签
 * opTypeList - 链接opType属性对应的值字符串，支持多个
 *          eg: "phone.create_op,contact.create_op"
 * @return {Array} - 返回指定或所有含有opType属性的A标签
 */
function getOpLinks(opTypeList) {
    var opLinks = [];

    var aEls = document.getElementsByTagName("a");
    for (var i = 0,length = aEls.length; i < length; i++) {
	var aEl = aEls[i];
	var opType = aEl.getAttribute("opType");
	if (opType && !opTypeList) {
	    opLinks.push(aEl);
	}
	else if (opType && opTypeList && opTypeList.indexOf(opType) > -1) {
	    opLinks.push(aEl);
	}
    }

    return opLinks;
};

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
function querySelChange(map) {
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
		each(document.getElementsByName(map.selectId), function (i, v) {
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
				//select.removeChild(select.options[select.length - 1]);
				select.options[select.length - 1] = null;
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
		'custName' : "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、'、*、/、阿拉伯数字等字符！",
		'trade1'   : '请选择一级行业',
		'trade2'   : '请选择二级行业',
		'siteName' : '请填写网站名称',
		'siteURL'  : '请填写网站URL',
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
		'fax'  : '传真',
        'disabledReason' : '无效原因'
	},

	//name与ID的映射，在补充非空验证时使用
	NAMEIDMAP : {
	    'cust.custProvId' : {'id' : 'custProv', 'text' : '省'},
	    'cust.custCityId' : {'id' : 'custCity', 'text' : '市'},
	    'cust.custDistrictId' : {'id' : 'custArea', 'text' : '区'},
	    'cust.custCardTypeId' : {'id' : 'cust.custCardTypeId', 'text' : '证件类型'},
	    'cust.custTrade1Id' : {'id' : 'tradeList1', 'text' : '一级行业'},
	    'cust.custTrade2Id' : {'id' : 'tradeList2', 'text' : '二级行业'},
	    'cust.custProv' : {'id' : 'custProv', 'text' : '省'},
	    'cust.custCity' : {'id' : 'custCity', 'text' : '市'},
	    'cust.custDistrict' : {'id' : 'custArea', 'text' : '区'},
	    'cust.custCardType' : {'id' : 'cust.custCardTypeId', 'text' : '证件类型'},
	    'cust.custTrade1' : {'id' : 'tradeList1', 'text' : '一级行业'},
	    'cust.custTrade2' : {'id' : 'tradeList2', 'text' : '二级行业'},
	    'cust.custAddr' : {'id' : 'cust.custAddr', 'text' : '公司地址'},
	    'cust.custMail' : {'id' : 'cust.custMail', 'text' : '开户邮箱'},
	    'cust.custZip' : {'id' : 'cust.custZip', 'text' : '客户邮编'},
	    'cust.custFax' : [{'id' : 'custFaxphAreaCode', 'text' : '传真'}, {'id' : 'custFaxphNum', 'text' : '传真'}, {'id' : 'custFaxphExtension', 'text' : '传真', 'pass' : true}]
	},

	// 座机国家代号
	COUNTRYCODE : {
	    'CHINA' : '86'
	},

	//联系人相关表单项
	contactFormItems : "contact.contactName," 
	    + "contact.disabled,"
	    + "contact.disabledReason,"
	    + "contact.phAreaCode,"
	    + "contact.phNum,"
	    + "contact.phExtension,"
	    + "contact.phDisabled,"
	    + "contact.phDisabledReason,"
	    + "contact.mobile,"
	    + "contact.mobileDisabled,"
	    + "contact.mobileDisabledReason,"
	    + "contact.companyMail,"
	    + "contact.personalMail,"
	    + "contact.contactDuty,"
	    + "contact.policyType,"
	    + "contact.contactSex,"
	    + "contact.contactRemark",

	contactOpLinks : "phone.create_op," 
	    + "phone.delete_op," 
	    + "contact.create_op," 
	    + "contact.delete_op",

    formatTime : function(time) {
        switch (time.length) {
            case 1:
                return '0' + time + ':00';
            case 2:
                return time + ':00';
            case 3:
                return '0' + time.substring(0, 1) + ':0' + time.substring(2);
            case 4:
                if (time.indexOf(':') == 1) {
                    return '0' + time;
                } else {
                    return time.substring(0, 3) + '0' + time.substring(3);
                }
            case 5:
                return time;
        }
    },

    /**
     * 手动验证公司名称
     * @param{String} checkUrl - 后端验证的URL
     */
    selfCheckCustName : function(checkUrl) {
        //获取名称相关值
        var custIdEl = G("custId"),
            custNameEl = G("cust.custName"),
            custName = custNameEl.value.trim(),
            custBranchNameEl = G("cust.custBranchName"),
            custBranchName = custBranchNameEl.value.trim();
        //传递给后端的参数
        var postData = custIdEl ? ("custId=" + custIdEl.value + "&") : "";
        //存储checkUrl,提供给custType函数
        Cust.selfCheckCustName.checkUrl = checkUrl;

        //验证公司名输入框是否符合要求
        if (!Cust.validateCustName(custNameEl)) {
            G("selfCheckCustNameTip").hide();
            return false;
        }
        
        //根据当前客户类型进行处理
        if (G('custType_0').checked) { //普通企业 - 不验证
            postData += "custName=" + encodeURIComponent(custName);
        }
        else if (G('custType_1').checked) { //特殊企业 - 两框合并
            postData += "custName=" + encodeURIComponent(custName) + "&custBranchName=" + encodeURIComponent(custBranchName);
        }
        else if (G('custType_2').checked) { //个人 - 不比较
            G("selfCheckCustNameTip").hide();
            return false;
        }
        else if (G('custType_3') && G('custType_3').checked) { //广告代理公司 - 后一个框
            postData += "custBranchName=" + encodeURIComponent(custBranchName);
        }

        var ajax = new Ajax(function (responseData) {
                responseData = responseData.trim();
                if (responseData != "") {
                    G("selfCheckCustNameTip").innerHTML = responseData;
                    G("selfCheckCustNameTip").show();
                }
        }, function (){
                G("selfCheckCustNameTip").hide();
        });
        ajax.post(checkUrl, postData);
    },

    /**
     * 点击后处理链接时的处理
     * @param {Element} afterCureEl - 后处理链接
     */
    afterCure : function(afterCureEl) {
	var params = {};
	params.afterCureAction = afterCureEl.getAttribute("afterCureAction");
	Cust.showAfterCure(params);
       
    },

    showAfterCure : function (params) {
        var custInputEls = G("custAddForm")["custInputId"]; //资料来源
        for (var i = 0, len = custInputEls.length; i < len; i++) {
            var custInputEl = custInputEls[i];
            if (custInputEl.checked) {
                G("afterCureSite").value = custInputEl.value;
                break;
            }
        }

        var afterCureFrame = G("afterCureFrame");
	G("afterCureUrl").value = G("custUrlName").value.trim();
        afterCureFrame.src = params.afterCureAction;
        Popup.show('afterCurePanel');
    },

    // 审核客户资料
    auditCust : function (formid, url) {
	var form = G(formid),
	    action = form.action;

	form.action = url;
	if (form.onsubmit) {
	    form.submit();
	}
	else {
	    Cust.chkForm(formid) && form.submit();
	}
	form.action = action;
    },
    
    /**
     * 点击有效无效按钮时的处理
     * @todo 将其拆分成单个小函数
     * @param {Element} me - 有效无效checkbox元素
     */
    ctrlDisabledClick : function(me) {
        /*区分是联系的无效，还是点哈的无效checkbox*/
        var role = me.getAttribute("role");
        /*联系的有效无效*/
        if ( role == "contactNameDisabled" ) {
            var meId = me.getAttribute("id");
            var meIdArr = meId.split("_");
            var contactEl = G(meIdArr[0] + "_" + meIdArr[1]);
            var inputEls = contactEl.getElementsByTagName("input");
            var length = inputEls.length;
            var disabledReasonEl = G(meIdArr[0] + "_" + meIdArr[1] + "_disabledReason");
            if (me.checked) {
                if (inputEls[0].value.trim().length < 1) {
                    me.checked = false;
                    return false;
                }
                for (var i = 0; i < length; i++) {
                    if ( inputEls[i] != me &&  inputEls[i] != disabledReasonEl ) { 
                        inputEls[i].setAttribute("disabled", true);
                    }
                }
                disabledReasonEl.show();
            }
            else {
                for (var i = 0; i < length; i++) {
                    if (
                            inputEls[i].getAttribute("canModify") != "false" && 
                            inputEls[i].getAttribute("canModifyTemp") != "false"
                       )
                    {
                        inputEls[i].removeAttribute("disabled");
                    }
                }
                disabledReasonEl.hide();
            }
            Cust.checkDisabledReason(disabledReasonEl);
        }
        /*电话的有效无效*/
        else if ( role == "phDisabled") {
            var meId = me.getAttribute("id");
            var meIdArr = meId.split("_");
            var idSuffix = meIdArr[0] + "_" + meIdArr[1] + "_";
            var phAreaCodeEl = G(idSuffix + "phAreaCode" + "_" + meIdArr[3]);
            var phNumEl = G(idSuffix + "phNum" + "_" + meIdArr[3]);
            var phExtensionEl = G(idSuffix + "phExtension" + "_" + meIdArr[3]);
            var disabledReasonEl = G(idSuffix + "phDisabledReason" + "_" + meIdArr[3]);
	    var phCountryCodeSel = G(idSuffix + "countryCodeSel" + "_" + meIdArr[3]);
            var phoneStr = 
                phAreaCodeEl.value.trim() + 
                "-" + 
                phNumEl.value.trim() + 
                "-" + 
                phExtensionEl.value.trim();
            if (me.checked) {
		var re = (phCountryCodeSel.vlaue == Cust.COUNTRYCODE.CHINA) ? /^(0\d{2,3})-(\d{7,8})-(\d{0,8})?$/ : /^(\d{0,4})-(\d{1,8})-(\d{0,8})?$/;
                if (!re.test(phoneStr)) {
                    me.checked = false;
                    return false;
		}
		else if (phCountryCodeSel.vlaue == Cust.COUNTRYCODE.CHINA && phoneStr == '--') {
		    me.checked = false;
                    return false;
		}
		phCountryCodeSel.setAttribute('disabled', true);
                phAreaCodeEl.setAttribute("disabled", true);
                phNumEl.setAttribute("disabled", true);
                phExtensionEl.setAttribute("disabled", true);

		phCountryCodeSel.setAttribute("canModifyTemp", "false");
                phAreaCodeEl.setAttribute("canModifyTemp", "false");
                phNumEl.setAttribute("canModifyTemp", "false");
                phExtensionEl.setAttribute("canModifyTemp", "false");
                
		disabledReasonEl.show();
            }
            else {
	       if (phCountryCodeSel.getAttribute("canModify") != "false") {
                   phCountryCodeSel.removeAttribute("disabled");
               }
               if (phAreaCodeEl.getAttribute("canModify") != "false") {
                   phAreaCodeEl.removeAttribute("disabled");
               }
               if (phNumEl.getAttribute("canModify") != "false") {
                   phNumEl.removeAttribute("disabled");
               }
               if (phExtensionEl.getAttribute("canModify") != "false") {
                   phExtensionEl.removeAttribute("disabled");
               }
                disabledReasonEl.hide();
		phCountryCodeSel.removeAttribute("canModifyTemp");
                phAreaCodeEl.removeAttribute("canModifyTemp");
                phNumEl.removeAttribute("canModifyTemp");
                phExtensionEl.removeAttribute("canModifyTemp");
            }
            Cust.checkDisabledReason(disabledReasonEl);
        }
        /*手机的有效无效*/
        else if ( role == "mbDisabled") {
            var meId = me.getAttribute("id");
            var meIdArr = meId.split("_");
            var idSuffix = meIdArr[0] + "_" + meIdArr[1] + "_";
            var mobileEl = G(idSuffix + "mobileNum" + "_" + meIdArr[3]);
            var disabledReasonEl = G(idSuffix + "mbDisabledReason" + "_" + meIdArr[3]);
            if (me.checked) {
		        var re = /^\d{11}$/;
                if (!re.test(mobileEl.value.trim())) {
                    me.checked = false;
                    return false;
                }
                mobileEl.setAttribute("disabled", true);
                mobileEl.setAttribute("canModifyTemp", "false");
                disabledReasonEl.show();
            }
            else {
                if (mobileEl.getAttribute("canModify") != "false") {
                    mobileEl.removeAttribute("disabled");
                }
                disabledReasonEl.hide();
                mobileEl.removeAttribute("canModifyTemp");
            }
            Cust.checkDisabledReason(disabledReasonEl);
        }
    },

	// 初始化
	init : function (frm, params) {
		// 行业改变时验证信息需要改变
		each([G('tradeList1'), G('tradeList2')], function (i, v) {
			Event.add(v, 'change', function () {
				if (v.options[0].value != v.value) {
					FormInfo.remove(v.parentNode);
					Cust.data(window, v.id, false);
				} else {
					var trade = v.id.indexOf('1') > -1 ? Cust.text.trade1 : Cust.text.trade2;
					FormInfo.show(v.parentNode, trade);
					Cust.data(window, v.id, trade);
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
					}
				}
			});
		});

		if(window.fe_multiChannel) {
		    var _mFrom = document.forms[frm]; 
		    var city = _mFrom['cust.custCityId'];
		    var prov = _mFrom['cust.custProvId'];
		    var dist = _mFrom['cust.custDistrictId'];
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
		}

		// 附加非空验证，为表单项添加blur事件判空
		// 此时只是显示错误信息 不保存错误信息到全局ERR中
		
		function exChkEmptyBlurHandler(el, items, index) {
		    var pass = false, str, err;
		    for (var i =0, item; item = items[i]; i++) {
			if (item.pass) {
			    continue;
			}
			pass = pass || Cust.chkEmpty(G(item.id));
		    }
		    if (el.nodeName.toLowerCase() != 'select') {
			str = '请填写' + items[index].text;
		    }
		    else {
		        str = '请选择' + items[index].text;
		    }
		    if (!pass) {
			
			FormInfo.show(G(items[0].id).parentNode, str);
			Cust.data(window, items[0].id, str);
		    }
		    else {
			//在没有其它错误的情况
			//或者错误提示信息是为空错误信息时去除为空错误提示
			err = Cust.getError(G(items[0].id).id);
			if (!err || err == str) {
			    FormInfo.remove(G(items[0].id).parentNode);
			    Cust.data(window, items[0].id, false);
			}
		    }
		}

		if (window.fe_chkEmptyField && window.fe_chkEmptyField.length != 0) {
		    for (var i = 0, field, item; field = window.fe_chkEmptyField[i]; i++) {
			item = Cust.NAMEIDMAP[field];	
			(!item.length) && (item = [item]);
			for (var j = 0, handler, o, el; o = item[j]; j++) {
			    el = G(o.id);
			    if (el.nodeName.toLowerCase() == 'select') {
				handler = 'change';
			    }
			    else {
				handler = 'blur';
			    }
			    Event.add(el, handler, (function (items, index, el) {
				return function () {
				    exChkEmptyBlurHandler(el, items, index);  
				};
			    })(item, j, el));
			}
		    }
		}
	    
	    // 处理params参数
	    if (!params) {
		return;
	    }

	    /* 
	     * 表示绝大多数的表单项不可以编辑,
	     * 后端给的editableList是可编辑表单项的列表
	     */
	    if (params.editableCtrl === true) {
	       Cust.disabledFlag = true;
	       Cust.canEditHandle(params.editableList); 
	    }
	    /* 
	     * 表示绝大多数的表单项可以编辑,
	     * 后端给的editableList是不可编辑表单项的列表
	     */
	    else if(params.editableCtrl === false) {
		Cust.disabledFlag = true;
		Cust.cantEditHandle(params.editableList);
	    }

	    // 如果需要验证修改次数，则保存需要验证的控件集合
	    (params.modifyCountList && params.modifyCountList.length > 0) && (Cust.modifyCountList = params.modifyCountList);

		// 行业修改验证
		if (params.chkTradeChange == true) {
			Cust.oTradeData = G('tradeList2').value;
		}
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
	 *  判断一个key是否是对应的错误
	 */

	isError : function (key) {
	    for (var i = 0 , err; err = Cust.ERR[i]; i++) {
		if (err.key == key) {
		    return true;
		}
	    }
	    return false;
	},

	/**
	 * 获得key对应的错误信息
	 */
	getError : function (key) {
	    for (var i = 0 , err; err = Cust.ERR[i]; i++) {
		if (err.key == key) {
		    return err.value;
		}
	    }
	    return false;
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
		// 当前项
		var item = o.parentNode;
		// 同类项所在的容器
		var keyElem = item.parentNode;

        /*当存在"联系人无效checkbox"并且勾选时联系电话不能进行操作*/
        var keyElemId = keyElem.getAttribute("id");
        if ( keyElemId && keyElemId.indexOf("phoneParent") > -1 ) {
            var keyElemIdArr = keyElemId.split("_");
            var disabledElId = "contact_" + keyElemIdArr[keyElemIdArr.length-1] + "_disabled";
            if (G(disabledElId) && G(disabledElId).checked) {
                return false;
            }
        }

		var id = this.data(keyElem, type) || 0;
		if (id == 0) {
			this.data(keyElem, type, 0);
		}
		// 模板
		var thisTPL = G(type + '_tpl').value;
		// 计数
		this.data(keyElem, type, ++id);
		Cust.itemIndex++;

		// 临时位置  保存临时信息, 因为IE的原因
		var tplTmpContainer = G('tplTmpContainer');

		if (type == 'telephone' || type == 'mobile') {
			var firstId = keyElem.id.split('_')[1];
			tplTmpContainer.innerHTML = thisTPL.format(firstId, Cust.itemIndex + '');
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

            /*当存在"联系人无效checkbox"并且勾选时联系电话不能进行操作*/
            var keyElemId = keyElem.getAttribute("id");
            if ( keyElemId && keyElemId.indexOf("phoneParent") > -1 ) {
                var keyElemIdArr = keyElemId.split("_");
                var disabledElId = "contact_" + keyElemIdArr[keyElemIdArr.length-1] + "_disabled";
                if (G(disabledElId) && G(disabledElId).checked) {
                    return false;
                }
            }

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
			
			// 清空数据
			// 输入框(type="text")在有默认值时恢复默认值否则清空value
			// select在有默认选项时恢复默认选项否则不做处理
			var s = item.getElementsByTagName('*');
			var inputs = item.getElementsByTagName('input');
			each(s, function (i, v) {
				if (v.nodeName.toLowerCase() == 'input' && v.type == 'text') {
					if (v.getAttribute("defValue")) {
					    v.value = v.getAttribute("defValue");
					}
					else {
					    v.value = '';
					}
				}
				else if (v.nodeName.toLowerCase() == 'select' && v.getAttribute('defIndex')) {
				    v.selectedIndex = parseInt(v.getAttribute('defIndex'));
				}
			});

			// 具体
			if (type == 'telephone' || type == 'mobile') {
				// 删除电话时同时删除相关的错误
				Cust.data(window, inputs[type == 'telephone' ? 1 : 0].id, false);
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

	/*
	 * 表单元素判空（只支持select input(text) textarea）
	 */
	chkEmpty : function (el) {
	    var type = el.nodeName.toLowerCase();
	    if (type == 'select') {
		if (el.value == '-1' && el.selectedIndex == 0) {
		    return false;
		}
		else {
		    return true;
		}
	    }
	    else {
		el.value = el.value.trim();
		if (el.value == '') {
		    return false;
		}
		else {
		    return true;
		}
	    }
	},

	/*
	 * 对表单进行附加非空验证
	 * 具体附加的检查项由全局fe_chkEmptyField数组提供(元素为表单项的name)
	 */
	exChkEmptyFields : function (form) {
	    if (!window.fe_chkEmptyField || window.fe_chkEmptyField.length == 0) {
		return;
	    }
	    for (var i = 0, pass, field, item, err; field = window.fe_chkEmptyField[i]; i++) {
		pass = false;
		item = Cust.NAMEIDMAP[field];	
		(!item.length) && (item = [item]); 
		for (var j = 0, el, o; o = item[j]; j++) {
		    el = G(o.id);
		    if (o.pass) {
			continue;
		    }
		    pass = pass || Cust.chkEmpty(el);
		}
		if (G(item[0].id).nodeName.toLowerCase() == 'select') {
		    str = '请选择' + item[0].text;
		}
		else {
		    str =  '请填写' + item[0].text;
		}
		if (!pass) {
		    Cust.data(window, item[0].id, str);
		}
		else {
		    err = Cust.getError(item[0].id);
		    if (err && err == str) {
			Cust.data(window, item[0].id, false);
			FormInfo.remove(item[0].parentNode);
		    }
		}
	    }
	},

	setBtnDisabled : function (stats) {
	    if (G('saveBtn')) {
		G('saveBtn').disabled = stats;
	    }
	    if (G('auditBtn')) {
		G('auditBtn').disabled = stats;
	    }
	    if (G('restBtn')) {
		G('restBtn').disabled = stats;
	    }
	},

	/**
	 * 表单提交前的验证
	 */
	chkForm : function (frm) {
		frm = document.forms[frm];

		//Cust.validateCustName(frm['cust.custName']);

		//Cust.checkURL(frm['custUrlName']);

		// 联系人
		var contactResult = Cust.contactCheck(frm);

		// 收件人
		var recipResult = Cust.recipCheck(frm);

		// 传真
		var phResult = Cust.checkPH(frm, ['custFaxphAreaCode', 'custFaxphNum', 'custFaxphExtension', 'fax']);

		var pass = false;

		//如果是多渠道客户资料录入则需要验证所属地域必填
		if(window.fe_multiChannel) {
		    var custProvId = frm['cust.custProvId']; // 所属地域 省
		    var custCityId = frm['cust.custCityId']; // 所属地域 市
		    FormInfo.remove(custProvId.parentNode);
		    if (custProvId.value == '-1') {
			//FormInfo.show(custProvId.parentNode, '请选择省份');
			Cust.data(window, 'custProv', '请选择省份');
		    } 
		    else {
			Cust.data(window, 'custProv', false);
		    }
		    if (custCityId.value == '-1') {
			//FormInfo.remove(custProvId.parentNode);
			//FormInfo.show(custCityId.parentNode, '请选择城市');
			Cust.data(window, 'custCity', '请选择城市');
		    }
		    else {
			Cust.data(window, 'custCity', false);
		    }
		}

		//对表单进行附加非空验证
		Cust.exChkEmptyFields(frm);

		// 错误处理
		var errs = Cust.ERR;
		if (errs.length == 0) {
		Cust.setBtnDisabled(true);
		    pass = true;
		} else {
			Cust.errorTips('topErr');
			each(errs, function (i, v) {
				window.scrollTo(0, 0);
				if (G(v['key'])) {
					FormInfo.show(G(v['key']).parentNode, v['value']);
				} else {
					var elem = document.getElementsByName(v['key'])[0];
					if (elem) {
						FormInfo.show(elem.parentNode, v['value']);
					}
				}
			});
		    pass = false;
		}
		
		//根据情况进行修改次数限制(同步)
		(pass && Cust.modifyCountList) && (pass = Cust.chkModifyCount(frm));

		//根据情况进行行业修改验证(同步)
		(pass && Cust.oTradeData !== undefined) && (pass = Cust.tradeChangeCheck(frm));

		//如果有编辑项被置灰需要恢复
		if (pass && Cust.disabledFlag) {
		    unDisabledFormItems();
		}
		return pass;
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

	/**
	 * 联系人验证
	 *
	 * @param {HTMLFormElement} frm Form元素
	 * @return {Boolean} 是否通过
	 */
	contactCheck : function (frm) {
		// 联系人
		var contacts = G('contacts').clearEmptyNode();
		contacts = contacts.childNodes;
		var contactData = '[';
		// 是否有错误
		var _flag = 0;

		for (var i = 0; i < contacts.length; i++) {
			var v = contacts[i];
			var vrel = v.getAttribute('rel');

			if (v.nodeName.toLowerCase() == 'div') {
				var fields = v.getElementsByTagName('input');
				var len = fields.length;
				var contactId = v.getAttribute('contactId') || '';

				// contactName
				var contactName = fields[0];
				contactName.value = contactName.value.trim();
				if (contactName.value == '') {
					Cust.data(window, contactName.id, Cust.text.required(Cust.infoText.contact));
					_flag = 1;
				} else if (contactName.value.length >20) {
					Cust.data(window, contactName.id, Cust.text.more(20));
					_flag = 1;
				} else {
					Cust.data(window, contactName.id, false);
				}
				contactName = contactName.value;

				// contactRemark , contactSex
                var contactSex = null;
				var contactRemark = fields[len - 1].value.trim();
				var contactSexArr = [];
                var contactSexName = fields[len - 2].name;
                for (var j = 2; fields[len - j].name == contactSexName; j++){
                        contactSexArr.push(fields[len - j]);
                }
				each(contactSexArr, function (sexi, sexv) {
					if (sexv.checked) {
						contactSex = sexv.value;
					}
				});
                if (contactSex === null) {
                    var contactSexRadio = contactSexArr[contactSexArr.length - 1];
                    contactSex = contactSexRadio.value;
                    contactSexRadio.checked = true;
                }

				// policyType
                var policyTypeId = null;
                var policyTypeArr = [];
                var policyTypeName = fields[len - 5].name;
                for (var j = 5; fields[len - j].name == policyTypeName; j++){
                        policyTypeArr.push(fields[len - j]);
                }
				
                each(policyTypeArr, function (sexi, sexv) {
					if (sexv.checked) {
						policyTypeId = sexv.value;
					}
				});
                if (policyTypeId === null) {
                    var policyTypeRadio = policyTypeArr[policyTypeArr.length - 1];
                    policyTypeId = policyTypeRadio.value;
                    policyTypeRadio.checked = true;
                }

				// contactDuty , personalMail , companyMail
				//var contactDuty = fields[len - 8].value.trim();
				//var custPersonalMail = fields[len - 9].value.trim();
				//var custCompanyMail = fields[len - 10].value.trim();
				var contactDuty = G('contact_'+ vrel +'_contactDuty').value.trim();
				var custPersonalMail = G('contact_'+ vrel +'_custPersonMail').value.trim();
				var custCompanyMail = G('contact_'+ vrel +'_custCompanyMail').value.trim();

                //disabled, disabledReason
                var disabledVal = fields[1].checked ? "1" : "0";
                var disabledReasonVal = fields[2].value.trim();

				if (i > 0) {
					contactData += ',';
				}
                if (fields[1].id.indexOf("disabled") > -1) {
				    contactData += '{"contactId":"'+ contactId +'", "contactName":"'+ encodeJSStr(contactName) +'", "contactRemark":"'+ encodeJSStr(contactRemark) +'", "contactSex":"'+ contactSex +'", "policyTypeId":"'+ policyTypeId +'", "contactDuty":"'+ encodeJSStr(contactDuty) +'", "custPersonalMail":"'+ custPersonalMail +'", "custCompanyMail":"'+ custCompanyMail + '", "disabled":"'+ disabledVal + '", "disabledReason":"'+ disabledReasonVal + '", "contactPhone":[';
                }
                else {
				    contactData += '{"contactId":"'+ contactId +'", "contactName":"'+ encodeJSStr(contactName) +'", "contactRemark":"'+ encodeJSStr(contactRemark) +'", "contactSex":"'+ contactSex +'", "policyTypeId":"'+ policyTypeId +'", "contactDuty":"'+ encodeJSStr(contactDuty) +'", "custPersonalMail":"'+ custPersonalMail +'", "custCompanyMail":"'+ custCompanyMail +'", "contactPhone":[';
                }

				var phsParent = Cust.getByClsName('phoneParent', v);
				phsParent = G(phsParent).clearEmptyNode();
				var phs = phsParent.childNodes;

				// 是否有电话
				var _hasPh = 0;
				each(phs, function (m, n) {
					if (n.nodeName.toLowerCase() == 'p') {
						var phId = n.getAttribute('phId') || '';
						var inputs = n.getElementsByTagName('input');
						var ilen = inputs.length;
						var regForPhone;

						//座机
						if (inputs[0].id.indexOf("phCountryCode") > -1) {
						    
							inputs[1].value = inputs[1].value.trim();
							inputs[2].value = inputs[2].value.trim();
							if (inputs[0].value == Cust.COUNTRYCODE.CHINA) {
							    regForPhone = [/^0\d{2,3}$/, /^\d{7,8}$/];
							}
							else {
							    regForPhone = [/^\d{0,4}$/, /^\d{1,8}$/];
							}
							if (!regForPhone[0].test(inputs[1].value) || !regForPhone[1].test(inputs[2].value)) {
							    if (inputs[1].value != '' || inputs[2].value != '') {  //座机填写错误
								_hasPh = 1;
								Cust.data(window, inputs[1].id, Cust.text.format('ph'));
							    }
							} else {
								_hasPh = 1;
								if (m > 0 && !contactData.endWith('[')) {
									contactData += ',';
								}
                                if (ilen <= 4) {
								    contactData += '{"phType":"0", "phId":"'+ phId +'", "phCountryCode":"'+ inputs[0].value +'", "phAreaCode":"'+ inputs[1].value +'", "phNum":"'+ inputs[2].value +'", "phExtension":"'+ inputs[3].value +'"}';
                                }
                                else {
                                    var disabledVal = inputs[4].checked ? "1" : "0"; 
								    contactData += 
                                        '{"phType":"0", "phId":"' 
                                        + phId 
					 + '", "phCountryCode":"'
                                        + inputs[0].value 
                                        + '", "phAreaCode":"'
                                        + inputs[1].value 
                                        + '", "phNum":"' 
                                        + inputs[2].value 
                                        + '","phExtension":"' 
                                        + inputs[3].value 
                                        + '", "disabled":"'
                                        + disabledVal 
                                        + '", "disabledReason":"'
                                        + inputs[5].value 
                                        + '"}';
                                }
							}
						} else {
							inputs[0].value = inputs[0].value.trim();
							if (!/^\d{11}$/.test(inputs[0].value)) {
							    if (inputs[0].value != '') {
								_hasPh = 1;
								Cust.data(window, inputs[0].id, Cust.text.format('phMB'));
							    }
							} else {
								_hasPh = 1;
								if (m > 0 && !contactData.endWith('[')) {
									contactData += ',';
								}
                                if (ilen <= 1) {
								    contactData += '{"phType":"1", "phId":"'+ phId +'", "phNum":"'+ inputs[0].value +'"}';
                                }
                                else {
                                    var disabledVal = inputs[1].checked ? "1" : "0"; 
								    contactData += 
                                        '{"phType":"1", "phId":"'
                                         + phId 
                                         +'", "phNum":"'
                                         + inputs[0].value 
                                         +'", "disabled":"' 
                                         + disabledVal 
                                         + '", "disabledReason":"'
                                         + inputs[2].value 
                                         +'"}';
                                }
							}
						}
					}
				});
				/*try {
                    var mobileEl = phs[1].getElementsByTagName('input')[0];
					var phsLastId = mobileEl.id;
		            FormInfo.remove(mobileEl.parentNode);
					if (_hasPh == 0) {
						Cust.data(window, phsLastId, Cust.text.required(Cust.infoText.ph));
					} else {
						Cust.data(window, phsLastId, false);
					}
				} catch (e) {
					//console.log(phsLastId);
				}*/
				var mobileEl = phs[1].getElementsByTagName('input')[0];
				(!mobileEl) && (mobileEl = phs[2].getElementsByTagName('input')[0]);//当第一个座机填写错误，需要重新取第一个手机的input
				if (_hasPh == 0) {
				    Cust.data(window, mobileEl.id, Cust.text.required(Cust.infoText.ph));
				}
				else {
				    var txt = Cust.getError(mobileEl.id);

				    //在有错误信息并且错误信息为空错时去除错误提示
				    if (txt && txt == Cust.text.required(Cust.infoText.ph)) {
					FormInfo.remove(mobileEl.parentNode);
					Cust.data(window, mobileEl.id, false);
				    }
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

	/* 举报验证
 	 *
 	 * @return {Object} 举报需要的信息，如果为null表示没有验证通过
 	 */
 	surveyCheck : function () {
 	    var ret = null;
 	    var custName = G('cust.custName');
        var custType = null;
        var custId = "";
 	    var custParent = custName.parentNode;
 	    
        // 客户资料修改时获得客户资料ID
        var custIdEl = G("custId");
        custIdEl && (custId = custIdEl.value);

        // 获得客户资料类型
        var formEl = document.forms["custAddForm"];
        var custTypeEls = formEl["cust.custTypeId"];
        for (var i = 0, len = custTypeEls.length; i < len; i++) {
            var custTypeEl = custTypeEls[i];
            if (custTypeEl.checked) {
                custType = custTypeEl.value;
                break;
            }
        }

 	    //判读公司名称是否合法
 	    if(custParent.nextSibling && custParent.nextSibling.nodeType == 1 && custParent.nextSibling.tagName.toLowerCase() == 'div' && custParent.nextSibling.className == 'fieldErr' || custName.value.trim() == '') {
 		    return;
 	    }

 	    custName = custName.value.trim();
 	    //公司类型为个人或者特殊企业时需补全公司名称
 	    if(G('cust.personalRemark') && G('cust.personalRemark').style.display != 'none') {
 		    custName += ' ' + G('cust.personalRemark').value.trim();
 		    custName = custName.trim();
 	    }
 	    else if(G('_custBranch') && G('_custBranch').style.display != 'none') {
 		    custName += '-' + G('cust.custBranchName').value.trim();
 	    }

 	    //判读URL是否合法
        var custUrlEl = G("custUrlName");
        var hasWebsite = !!custUrlEl.offsetWidth;
        var custUrl = custUrlEl.value.trim();
        var custUrlErrEl = custUrlEl.nextSibling;
 	    if(hasWebsite) {
            // URL不合法
            if ((custUrlErrEl
                && custUrlErrEl.nodeType == 1
                && custUrlErrEl.tagName.toLowerCase() == 'div'
                && custUrlErrEl.className == 'fieldErr')
                || custUrl == '') {
                return false;
            }
 	    }
        else {
            custUrl = '';
        }
 
 	    //验证联系人
 	    var contacts = G('contacts').clearEmptyNode();
 	    contacts = contacts.childNodes;
 	    for(var i = 0, v; v = contacts[i]; i++) {
 		    if(v.nodeName.toLowerCase() == 'div') {
 		        var inputs = v.getElementsByTagName('input');
 		        var contactName = inputs[0].value.trim();
 		        var hasPhone = null;
 		        if(contactName == '') {
 		            continue;
 		        }
 		        var phoneAreas = Cust.getByClsName('phoneParent', v).getElementsByTagName('p');
 		        for(var j = 0, phone; phone = phoneAreas[j]; j++) {
 		    	    var ins = phone.getElementsByTagName('input');
 		    	    if (ins[0].id.indexOf("phCountryCode") > -1) {
		    	        var re = null;
		    	        hasPhone = ins[1].value + '-' + ins[2].value + '-' + ins[3].value;
		    	        if (ins[0].value == Cust.COUNTRYCODE.CHINA) {
		    	    	    re = /^(0\d{2,3})-(\d{7,8})-(\d{0,8})?$/;
		    	        }
		    	        else {
		    	    	    re = /^(\d{0,4})-(\d{1,8})-(\d{0,8})?$/;
		    	        }
		    	        if (re.test(hasPhone)) {
		    	    	    break;
		    	        }
		    	        else {
		    	    	    hasPhone = null;
		    	        }
 		    	    }
 		    	    else {
 		    	        if(/^\d{11}$/.test(ins[0].value)) {
 		    	    	    hasPhone = ins[0].value;
 		    	    	    break;
 		    	        }
 		    	    }
 		       }
 		       if(hasPhone) {
                  ret = {
                      'custId' : custId,
                      'custType' : custType,
                      'custName' : custName,
                      'custUrl' : custUrl,
                      'contactName' : contactName,
                      'contactPhone' : hasPhone
                  };
 		          break;
 		       }
 		    }
 	    }
 	    return ret;
 	},
 
 	/**
 	 * 举报
 	 */
 	surveySubmit : function (ele) {
 	    var url = ele.parentNode.getAttribute('surveyAction');
        var surveyErrorEl = ele.parentNode.id == "surveyArea" ? G("surveyError") : G("surveyAfterSubmitError");
 	    var data = Cust.surveyCheck();
 	    if(!data) {
 		    surveyErrorEl.innerHTML = '请填写正确的公司名称，URL，联系人及其联系电话后再举报';
 		    return;
 	    }
        else {
 	        surveyErrorEl.innerHTML = '';
        }
        G('uploadFileForm').action = url;
        G('eCustId').value = data.custId;
        G('eCustType').value = data.custType;
        G('eCustUrl').value = data.custUrl;
        G('eCustName').value = data.custName;
        G('eContactName').value = data.contactName;
        G('eContactPhone').value = data.contactPhone;
        G("filesArea").innerHTML = G("filesAreaInit").innerHTML; //初始化图片上传内容
        Popup.show('attachFilePanel'); //实现图片上传浮动层
 	},

 	/**
 	 * 添加举报时附件上传框
 	 */
    addAttachInput : function () {
        var divEl = document.createElement("div");
        divEl.innerHTML = G("fileInputTemp").innerHTML;
        G("filesArea").appendChild(divEl);
    },

 	/**
 	 * 删除附件上传框
 	 */
    rmAttachInput : function (ele) {
        ele.parentNode.parentNode.removeChild(ele.parentNode);
    },

 	/**
 	 * 验证附件上传表单
 	 */
    validUploadAttachForm : function (formEl) {
        var uploadFileEls = formEl["uploadFile"];
        // 只有一个上传框
        if (!uploadFileEls.length) {
            uploadFileEls = [uploadFileEls];
        }
        
        var allPass = true;
        for (var i = 0, len = uploadFileEls.length; i < len; i++) {
            var uploadFileEl = uploadFileEls[i];
            var errEl = uploadFileEl.parentNode.getElementsByTagName("div")[0];
            var value = uploadFileEl.value.trim();
            if (/\.(bmp|png|gif|pjpeg|jpg|jpeg|zip|rar|doc|docx)$/i.test(value) || value == "") {
                G(errEl).hide();
            }
            else {
                G(errEl).show();
                allPass = false;
            }
        }
        if (!allPass || !confirm('确定进行举报吗？')) {
            return false;
        }
        else {
            return true;
        }
    },

 	/**
 	 * 举报提交后返回的处理
 	 */
    handleUpload : function () {
       var iframe = G("uploadIframe");
       var d = iframe.contentWindow.document;
       var content = d.body.innerHTML;
       if(content == "") {
           return false;
       }
       else {
           alert(content);
       }
    },

 	/**
 	 * 申领 
 	 */
 	obtainSubmit : function (ele) {
        if (!confirm("确定申领该客户资料？")) {
            return false;
        }
 	    var url = ele.parentNode.getAttribute('obtainAction');
 	    var xhr = new Ajax(function (data){
 		    alert(data);
 	    });
 	    xhr.post(url);
 	},

	/**
	 * 收件人验证
	 *
	 * @param {HTMLFormElement} frm Form元素
	 * @return {Boolean} 是否通过
	 */
	recipCheck : function (frm) {
		var recips = G('recips').clearEmptyNode();
		recips = recips.childNodes;
		var recipData = '[';

		var _recipData = '[';
		// 收件人
		each(recips, function (i, v) {
			if (v.nodeName.toLowerCase() == 'div') {
				var fields = v.getElementsByTagName('input');
				var len = fields.length;
				var recipId = v.getAttribute('recipId') || '';
				var recipName = fields[0].value.trim();
				var recipZip = fields[len - 1].value.trim();
				var recipAddr = fields[len - 2].value.trim();


				if (!_recipData.endWith('[')) {
					_recipData += ',';
				}
				_recipData += '{"recipId":"'+ recipId +'", "recipName":"'+ encodeJSStr(recipName) +'", "recipZip":"'+ recipZip +'", "recipAddr":"'+ encodeJSStr(recipAddr) +'", "recipPhone":';

				_recipData += '[';

				var phsParent = Cust.getByClsName('phoneParent', v);
				phsParent = G(phsParent).clearEmptyNode();
				var phs = phsParent.childNodes;
				var _hasPH = false;
				each(phs, function (m, n) {
					if (n.nodeName.toLowerCase() == 'p') {
						var phId = n.getAttribute('phId') || '';
						var inputs = n.getElementsByTagName('input');
						var ilen = inputs.length;
						if (ilen == 3) {
							if (!_recipData.endWith('[')) {
								_recipData += ',';
							}

							if (Cust.checkPH(frm, [inputs[0].id, inputs[1].id, inputs[2].id, 'ph']) && inputs[0].value != '' && inputs[1].value != '') {
								_hasPH = true;
								_recipData += '{"phType":"0", "phId":"'+ phId +'", "phAreaCode":"'+ inputs[0].value +'", "phNum":"'+ inputs[1].value +'", "phExtension":"'+ inputs[2].value +'"}';
							}
						} else {
							if (!_recipData.endWith('[')) {
								_recipData += ',';
							}
							if (/^\d{11}$/.test(inputs[0].value)) {
								_hasPH = true;
								_recipData += '{"phType":"1", "phId":"'+ phId +'", "phNum":"'+ inputs[0].value +'"}';
							}
						}
					}
				});

				if (_recipData.endWith(',')) {
					_recipData = _recipData.substr(0, _recipData.length - 1);
				}

				if (_recipData.endWith('[')) {
				    _recipData = _recipData.substring(0, _recipData.length - 1);
				    _recipData += '""';
				}
				else {
				    _recipData += ']';
				}
				_recipData += '}';
				if (recipName == "" && recipZip == "" && recipAddr == "" && !_hasPH) {
					var _ = _recipData.split(',{');
					_.length = _.length - 1;
					_recipData = _.join(',{');
				}
				recipData += _recipData;
			}
		});
		_recipData += ']';
		recipData = _recipData;
		recipData = recipData == ']' ? '' : recipData;
		//frm['recip'].value = recipData.replace("[]", '""');
		frm['recip'].value = recipData;
		return true;

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
		var code = G(params[0]).value = G(params[0]).value.trim();
		var num  = G(params[1]).value = G(params[1]).value.trim();
		var ext  = G(params[2]).value = G(params[2]).value.trim();
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
	 * 客户资料二次确认（6.0.3项目增加）
	 */
	custConfirm : function (txt) {
	    if(confirm(txt)) {
		(G('custConfirm')) && (G('custConfirm').value = 'true');
		document.forms['custAddForm'].submit();
	    }
	    else {
		(G('custConfirm')) && (G('custConfirm').value = 'false');
	    }
	},

	/**
	 * 验证客户名称
	 */
	validateCustName : function(el) {
	    var ret = true, val = el.value.trim();
	    el.value = val;
	    FormInfo.remove(el.parentNode);
	    if (G('custType_0').checked) {
		//添加单独字符个数验证，只针对普通企业
		ret = /^.{2,}$/.test(val);
		if (!ret) {
		    FormInfo.show(el.parentNode, "公司名字数不能低于2个字符");
		    Cust.data(window, el.id, "公司名字数不能低于2个字符");
		    return false;
		}
		//ret = /^[\u4E00-\u9FA5a-zA-Z @&(),\.<>《》•\d]{2,}$/.test(val);
		ret = /^[\u3040-\u9FFF\uAC00-\uD7AFa-zA-Z @&(),\.<>《》•'\*\/\d\-]{2,}$/.test(val);
		if (!ret) {
		    FormInfo.show(el.parentNode, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、-、@、&、()、<>、《》、• 、'、*、/、阿拉伯数字等字符！");
		    Cust.data(window, el.id, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、-、@、&、()、<>、《》、• 、'、*、/、阿拉伯数字等字符！");
		    return false;
		}
	    }
	    else if (G('custType_1').checked || (G('custType_3') && G('custType_3').checked)) {
		var custBranchName = G('cust.custBranchName');
		custBranchVal = custBranchName.value.trim();
		custBranchName.value = custBranchVal;

		/*ret =  /^[\u4E00-\u9FA5a-zA-Z @&(),\.<>《》•\d]{2,}$/.test(val)
		    && /^[\u4E00-\u9FA5a-zA-Z @&(),\.<>《》•\d]{2,}$/.test(custBranchVal);*/
		ret =  /^[\u3040-\u9FFF\uAC00-\uD7AFa-zA-Z @&(),\.<>《》•'\*\/\d]{2,}$/.test(val)
		    && /^[\u3040-\u9FFF\uAC00-\uD7AFa-zA-Z @&(),\.<>《》•'\*\/\d]{2,}$/.test(custBranchVal);
		if (!ret) {
		    FormInfo.show(el.parentNode, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、'、*、/、阿拉伯数字等字符！");
		    Cust.data(window, el.id, "公司名非法，只允许包含中文汉字、英文字母、日文、韩文、英文符号.,、空格、@、&、()、<>、《》、• 、'、*、/、阿拉伯数字等字符！");
		    return false;
		}
	    }
	    else if (G('custType_2').checked) {
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
	 * 验证客户希望联系时间
	 */
	validateExpectTime : function() {
        var startTimeEl = G("postil.startTime"),
            endTimeEl = G("postil.endTime"),
            startTime = startTimeEl.value.trim(),
            endTime = endTimeEl.value.trim();
            timePattern = /^(([01]?\d|2[0123])(:([012345]?\d))?)?$/;

		FormInfo.remove(startTimeEl.parentNode);

		if (timePattern.test(startTime) && timePattern.test(endTime)) {
            startTimeEl.value = Cust.formatTime(startTime);
            endTimeEl.value = Cust.formatTime(endTime);
		}
        else {
            FormInfo.show(startTimeEl.parentNode, '日期格式不合法, 请输入"小时", 或者"小时:分钟"');
		    Cust.data(window, startTimeEl.id, '日期格式不合法, 请输入"小时", 或者"小时:分钟"');
		    return false;
        }
        Cust.data(window, startTimeEl.id, false);
        return true;
	},

	/**
	 * 验证客户下发备注
	 */
	validateYesCustRemark : function(el) {
        var val = el.value.trim();
        el.value = val;
		
        FormInfo.remove(el.parentNode);

		if (val.length > 200) {
            FormInfo.show(el.parentNode, '下发备注的长度不能大于200字符');
		    Cust.data(window, el.id, '下发备注的长度不能大于200字符');
		    return false;
		}
        Cust.data(window, el.id, false);
        return true;
	},

	/**
	 * 验证失效原因
	 */
	checkDisabledReason : function(el) {
		var ret = true, val = el.value.trim();
		FormInfo.remove(el.parentNode);

        var disabledCheckEl = Cust.prev(el);
		if (val.length < 1 && disabledCheckEl.checked ) {
            ret = false;
		}
		if (!ret) {
            FormInfo.show(el.parentNode, "请填写失效原因");
			Cust.data(window, el.id, "请填写失效原因");
			return false;
		}
        Cust.data(window, el.id, false);
	},

    /*
     * 获得当前节点的下一个元素节点
     * @param {Element} el -- 当前元素
     * @return {Element|null} 下一个元素节点或者空节点
     */
    next : function(el) {
        if (!el) {
            return null;
        }
        el = el.nextSibling;
        while (el) {
            if (el.nodeType == 1) {
                break;
            }
            else {
                el = el.nextSibling;
            }
        }
        return el;
    },

    /*
     * 获得当前节点的上一个元素节点
     * @param {Element} el -- 当前元素
     * @return {Element|null} 上一个元素节点或者空节点
     */
    prev : function(el) {
        if (!el) {
            return null;
        }
        el = el.previousSibling;
        while (el) {
            if (el.nodeType == 1) {
                break;
            }
            else {
                el = el.previousSibling;
            }
        }
        return el;
    },
    /*
     * 获得元素el的特定标签的第一个祖先元素
     * @param {Element} el -- 需要获得父节点的元素
     * @param {String} ancestorTagNames -- 标签形式如: "p,div"
     * @return {Element|null} el特定标签的第一个祖先元素
     */
    getElementAncestor : function(el, ancestorTagNames) {
        if (!el) {
            return null;
        }
        var lastTags = "," + ancestorTagNames.toUpperCase() + ",";
        while (el) {
            if ( lastTags.indexOf("," + el.nodeName.toUpperCase() + ",") != -1) {
                return el;
            }
            el = el.parentNode;
        }
        return null;
    },
	/**
	 * 客户类型切换
	 */
	custType : function (n) {
		var _custName = G('_custName');
		var _custBranch = G('_custBranch');
		var _custAddr = G('_custAddr');
		var _custMail = G('_custMail');
		var _custScale = G('_custScale');
        var custName = G('cust.custName');
		var custBranchName = G('cust.custBranchName');
        var personalRemark = G('cust.personalRemark');
		Cust.validateCustName(custName);

        //如果存在检查手动检查，则进行相应处理
        if (G("selfCheckCustNameTip") && G("selfCheckCustNameTip").style.display != "none") {
            Cust.selfCheckCustName(Cust.selfCheckCustName.checkUrl);
        }

        var formEl = Cust.getElementAncestor(_custName, "form");
		if (n == 0) {
			_custName.innerHTML = Cust.infoText.corpName + '<b>*</b>';
			_custBranch.hide();
			_custAddr.show();
			_custMail.show();
			_custScale.show();
            personalRemark.hide();
		} else if (n == 1 || n == 3) {
			_custName.innerHTML = Cust.infoText.corpName + '<b>*</b>';
			_custBranch.show();
			_custAddr.show();
			_custMail.show();
			_custScale.show();
            personalRemark.hide();
		} else if (n == 2) {
			_custName.innerHTML = Cust.infoText.custName + '<b>*</b>';
			_custBranch.hide();
			_custAddr.show();
			_custMail.show(); /*紧急版本升级时最简单修改*/
			_custScale.hide();
            personalRemark.show();
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
		thsid = ths.getAttribute("id");
		var fval = ths.value.trim();
		var original = ths.getAttribute('original');

        //特殊企业中备份第二框的fval和original值
        var fvalBak = "", originalBak = "";

        //如果是客户名称或特殊企业则需要先验证
        if (data == 'custName' || data == 'custBranchName') {
            var result = Cust.validateCustName(G('cust.custName'));
            //验证不通过，不进行后端验证
            if (!result) {
                return false;
            }
            //个人客户，不进行后端验证
            if (G('custType_2').checked) {
                return false;
            }
			fvalBak = G('cust.custBranchName').value.trim();
			originalBak = G('cust.custBranchName').getAttribute('original');
        }
		if (data == 'custBranchName') {
			if (fval == '') {
				return false;
			}
			elem = elem.parentNode;
			if (!G('custType_3') || !G('custType_3').checked) {
			    data = 'custName';
			    thsid = 'cust.custName';
			}
			isOptional = false;
			fval = G('cust.custName').value.trim();
			original = G('cust.custName').getAttribute('original');
		}
		FormInfo.remove(elem);
		if (!thsid) {
			thsid = ths.name;
		}
		// 标志特殊地方的精确提示
		var flag = '';

		var thsIdArr = thsid.split('_') || [];
		if (thsIdArr.length == 3 && thsIdArr[0] == 'contact' && thsIdArr[2] == 'contactName') {
			flag = Cust.infoText.contact;
		}
		if (
                thsIdArr[0] == 'contact' &&
                (thsIdArr[2] == 'disabledReason' ||
                 thsIdArr[2] == 'phDisabledReason' ||
                 thsIdArr[2] == 'mbDisabledReason')
           )
        {
			flag = Cust.infoText.disabledReason;
		}

		// 数字
		if (type && type == 'number' && (isNaN(fval) || ((thsid.toLowerCase().indexOf('custzip') != -1 || thsid.toLowerCase().indexOf('recipzip')) != -1 && fval.indexOf('.') != -1))) {
			FormInfo.show(elem, Cust.text.format('number'));
			Cust.data(window, thsid, Cust.text.format('number'));
			return false;
		}
		Cust.data(window, thsid, false);

		// 是否是可选
		if (!isOptional && fval.trim() == '') {
			FormInfo.show(elem, Cust.text.required(flag, thsid));
			Cust.data(window, thsid, Cust.text.required(flag, thsid));
			return false;
		}
		Cust.data(window, thsid, false);

		// 字节限制
		if (sizeLimit !== '' && fval.trim().length > sizeLimit) {
			FormInfo.show(elem, Cust.text.more(sizeLimit));
			Cust.data(window, thsid, Cust.text.more(sizeLimit));
			return false;
		}
		Cust.data(window, thsid, false);

		// 是否自动审核
		if (autoCheck === true) {
			// 如果 含有original属性  并且 当前值与original值相等   则不提交
			if ((fval + fvalBak) != (original + originalBak)) {
				var sendData = data + '=' + encodeURIComponent(fval);
				var frm = document.forms['custAddForm'];
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
				// 广告代理公司只第二个框做后台验证
				if (custTypeId == '3' && (data == 'custBranchName' || data == 'custName' )) {
				    sendData = 'custName=' + encodeURIComponent(frm['cust.custBranchName'].value);
				}
				// 针对URL
		if ((data == 'urlName' || data =="custName") && frm['custId']) {
					sendData += '&custId=' + encodeURIComponent(frm['custId'].value);
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
							Cust.data(window, thsid, v.errors);
						}
					    if(data == 'urlName') {
						    // 如果返回信息含有url项(呼入后处理,呼出投诉)
						    var afterCure = G("afterCure");
						    var survey = G('surveyArea');
						    var obtain = G('obtainArea');
						    if (v.url) {
						        if (afterCure) { //是否为呼入的后处理 
						    	    afterCure.show();
						    	    afterCure.setAttribute("afterCureAction", v.url);
						        }
						        else if (survey){	//呼出举报
						    	    survey.show();
						    	    survey.setAttribute('surveyAction', v.url);
						        }
						    }
						    else {
						        afterCure && afterCure.hide();
						        survey && survey.hide();
					        }
                            // 如果存在申领链接
                            if (v.applyUrl) {
						        if (obtain) {
						    	    obtain.show();
						    	    obtain.setAttribute("obtainAction", v.applyUrl);
                                }
                            }
                            else {
                                obtain && obtain.hide();
                            }
                        }
					});
				ajax.post(url, sendData);
			}
		}
		// Cust.data(window, thsid, false);
		return true;
	},

	/**
	 * 电话验证
	 */
	checkPhone : function (ths, url, data, n, idSuffix, isOptional) {
		var elem = ths.parentNode;
		var phType = ths.getAttribute('rel');
		FormInfo.remove(elem);

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
		fval = ths.value = ths.value.trim();
		
		if (fval && !/^\d{11}$/.test(fval)) {
			FormInfo.show(elem, Cust.text.format('phMB'));
			Cust.data(window, ths.id, Cust.text.format('phMB'));
			return false;
		} else {
			Cust.data(window, ths.id, false);
		}
		if (url && fval) {
			if (fval != ths.getAttribute('original')) {
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
		(G('surveyArea')) && (G('surveyArea').hide());
		(G('obtainArea')) && (G('obtainArea').hide());
		if (!ths.id) {
			ths.id = ths.name;
		}
		var fval = ths.value.trim();
		var re = /^[^.。，]+(\.[^.，。]+)+$/i;
		if (!re.test(fval) && document.forms["custAddForm"]["cust.webSite"][0].checked) {
			FormInfo.show(elem, Cust.text.format('url'));
			Cust.data(window, ths.id, Cust.text.format('url'));
			return false;
		}
		Cust.data(window, ths.id, false);
		return this.autoCheck(ths, url, data, isOptional, sizeLimit, autoCheck);
	},

	/**
	 * 服务端信息处理
	 *
	 * @param {String} frm Form名称
	 * @param {Boolean} result 返回的结果
	 * @param {Object} errors 错误信息
	 */
	serverCheck : function (frm, result, errors, url, nextCleanoutUrl, cleanoutListUrl,passedMsg) {
		var errorMsg = [];
		Cust.setBtnDisabled(false);
		if (nextCleanoutUrl || cleanoutListUrl) {
			cleanoutNextShow(nextCleanoutUrl, cleanoutListUrl);
			return false;
		}

		frm = document.forms[frm];
		if (result == false) {
			if (errors.length == 0) {
				location.href = '/ht-callout/systembusy.html';
			}
			window.scrollTo(0, 0);
			each(errors, function (i, v) {
				var field = frm[v['name']];

				if (field) {
					Cust.errorTips('topErr');
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
					var id = field.id ? field.id : v['name'];
					if (id == 'cust.custTrade2Id') {
						id = 'tradeList2';
					}

					//只有在不是全局错误并且不是只显示错误信息的情况下才将错误添加到Cust.ERR中
					if (id != 'errorMessage' && !v['showOnly']) {
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
						parentElem = G('contacts').clearEmptyNode().childNodes[contactIndex];
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
            if (passedMsg && passedMsg.length > 0) {
                alert(passedMsg);
            }
            else {
			    alert('您的提交已成功保存');
            }
            // 如果是线索转化提交的客户资料
            if (G("saleHintFlag")) {
                parent.Popup.hide('transferHintPanel');
                parent.document.location = "/ht-callout/" + parent.G("afterTagUrl").value;
            }
            else {
			    location.href = url;
            }
		}
	},

	/*
	 * 禁用全部输入框，只开启editableList指定的元素
	 */
	canEditHandle : function(editableList) {
	var contactFormItems = Cust.contactFormItems,
	    contactOpLinks = Cust.contactOpLinks,
	    editableListStr = editableList.join(","),
	    hasContact = editableListStr.indexOf("contact.contactName") != -1;

        /*是否有联系人*/
        if (hasContact) {
            /*除联系人相关外的其它表单项置为不可编辑,其它操作隐藏*/
            var allFormItems = getAllFormItems("custAddForm");
            for (var i = 0, length = allFormItems.length; i < length; i++) {
                var formItem = allFormItems[i];
                formItemName = formItem.getAttribute("name") || "";
                /*决策类型和性别的特殊处理*/
                formItemName = formItemName.split("_")[0];
                if (contactFormItems.indexOf(formItemName) == -1) {
                    formItem.setAttribute("disabled", "disabled");
                }
            }

            var allOpLinks = getOpLinks();
            for (var i = 0, length = allOpLinks.length; i < length; i++) {
                var opLink = allOpLinks[i];
                var opType = opLink.getAttribute("opType");
                if (contactOpLinks.indexOf(opType) == -1) {
                    opLink.style.display = "none";
                }
            }
        }
        else {
            /*将所有的表单项置为不可编辑，所有的操作链接隐藏*/
            var allFormItems = getAllFormItems("custAddForm");
            for (var i = 0, length = allFormItems.length; i < length; i++) {
                allFormItems[i].setAttribute("disabled", "disabled");
            }

            var allOpLinks = getOpLinks();
            for (var i = 0, length = allOpLinks.length; i < length; i++) {
                allOpLinks[i].style.display = "none";
            }
        }


        /*取出editableList中的元素，进行可编辑或显示*/
        for (var i = 0, length = editableList.length; i < length; i++) {
            var editableItem = editableList[i];
            /*联系人相关*/
            if (editableItem == "contact.contactName") {
                continue;
            }
            /*传真表单项*/
            else if (editableItem == "cust.custFax") {
                var custFaxItems = getFormItems("custAddForm",
                    "custFaxphAreaCode,custFaxphNum,custFaxphExtension");
                for (var j = 0, len = custFaxItems.length; j < len; j++) {
                    custFaxItems[j].removeAttribute("disabled");
                }
            }
	    else if (editableItem == 'cust.custName') {
		var custNameItems = getFormItems('custAddForm', 'cust.custName,cust.personalRemark,cust.custBranchName');
		for (var j = 0, len = custNameItems.length; j < len; j++) {
                    custNameItems[j].removeAttribute("disabled");
                }
	    }
            /*操作链接*/
            else if (editableItem.indexOf("_op") > -1) {
                var opLinks = getOpLinks(editableItem);
                for (var j = 0, len = opLinks; j < len; j++) {
                    opLinks[j].style.display = "";
                }
            }
            /*普通表单项*/
            else {
                var formItems = getFormItems("custAddForm", editableItem);
                for (var j = 0, len = formItems.length; j < len; j++) {
                    formItems[j].removeAttribute("disabled");
                }
            }
        }

        /*取消提交、重置、返回按钮的disabled状态*/
        G("submitBtn").removeAttribute("disabled");
        G("resetBtn").removeAttribute("disabled");
        G("backBtn").removeAttribute("disabled");
    },

    /*
     * 禁用editableList指定的元素
     */
    cantEditHandle : function(editableList) {
	var contactFormItems = Cust.contactFormItems,
	    contactOpLinks = Cust.contactOpLinks;

        /*取出editableList中的元素，进行不可编辑或隐藏*/
        for (var i = 0, length = editableList.length; i < length; i++) {
            var editableItem = editableList[i];
            /*联系人相关*/
            if (editableItem == "contact.contactName") {
                var formItems = getFormItems("custAddForm", contactFormItems);
                for (var j = 0, len = formItems.length; j < len; j++) {
                    formItems[j].setAttribute("disabled", "disabled");
                }
                var opLinks = getOpLinks(contactOpLinks);
                for (var j = 0, len = opLinks; j < len; j++) {
                    opLinks[j].style.display = "none";
                }
            }
            /*传真表单项*/
            else if (editableItem == "cust.custFax") {
                var custFaxItems = getFormItems("custAddForm", 
                        "custFaxphAreaCode,custFaxphNum,custFaxphExtension");
                for (var j = 0, len = custFaxItems.length; j < len; j++) {
                    custFaxItems[j].setAttribute("disabled", "disabled");
                }
            }
	    else if (editableItem == 'cust.custName') {
		var custNameItems = getFormItems('custAddForm', 'cust.custName,cust.personalRemark,cust.custBranchName');
		for (var j = 0, len = custNameItems.length; j < len; j++) {
                    custNameItems[j].setAttribute("disabled", "disabled");
                }
	    }
            /*操作链接*/
            else if (editableItem.indexOf("_op") > -1) {
                var opLinks = getOpLinks(editableItem);
                for (var j = 0, len = opLinks; j < len; j++) {
                    opLinks[j].style.display = "none";
                }
            }
            /*普通表单项*/
            else {
                var formItems = getFormItems("custAddForm", editableItem);
                for (var j = 0, len = formItems.length; j < len; j++) {
                    formItems[j].setAttribute("disabled", "disabled");
                }
            }
        }
    },

    /*
     * 验证修改次数
     */
    chkModifyCount : function (frm) {
	var list = Cust.modifyCountList,
	    url = '/ht-callout/cust/checkUpdateNum.action',
	    params = [], xht, ret, res;
	
	params.push('custId=' + frm['custId'].value);
	if (!list || list.length <= 0) {
	    return true;
	}
	for (var i = 0, name; name = list[i]; i++) {
	    switch(name) {
		case 'custType' :
		    var inputs = frm['cust.custTypeId'];
		    for (var j = 0, item; item = inputs[j]; j++) {
			if (item.checked) {
			    params.push('cust.custType=' + item.value);
			    break;
			}
		    }
		    break;
		case 'custName':
		    params.push('cust.custName=' + encodeURIComponent(frm['cust.custName'].value.trim()));
		    params.push('cust.personalRemark=' + encodeURIComponent(frm['cust.personalRemark'].value.trim()));
		    params.push('cust.custBranchName=' + encodeURIComponent(frm['cust.custBranchName'].value.trim()));
		    break;
	    }
	}
	xht = new Ajax();
	xht.async = false;
	Mask.show();
	xht.post(url, params.join('&'));
	if (xht.xhr.status == 200) {
	    res = xht.xhr.responseText;
	    res = eval('(' + res + ')');
	    if (res.result) {
		ret = true;
		(res.msg) && (ret = confirm(res.msg));
	    }
	    else {
		alert(res.msg || '修改次数验证错误');
		ret = false;
	    }
	}
	else {
	    alert('通信错误，请稍候重试...');
	    ret = false;
	}
	Mask.hide();
	return ret;
    },

	//行业修改验证
	tradeChangeCheck : function (frm) {
		var url = '/ht-callout/cust/checkTradeChange.action', xht, res,
			params = [], 
			ret = true;
		if (Cust.oTradeData != G('tradeList2').value) {
			xht = new Ajax();
			xht.async = false;
			Mask.show();
			params.push('custId=' + frm['custId'].value);
			params.push('tradeId=' + encodeURIComponent(G('tradeList2').value.trim()));
			xht.post(url, params.join('&'));
			if (xht.xhr.status == 200) {
				res = xht.xhr.responseText;
				res = eval('(' + res.trim() + ')');
				if (res.result) {
					ret = true;
					(res.msg) && (ret = confirm(res.msg));
				}
				else {
					alert(res.msg || '行业修改验证');
					ret = false;
				}
			}
			else {
				alert('通信错误，请稍候重试...');
				ret = false;
			}
			Mask.hide();
		}
		return ret;
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
