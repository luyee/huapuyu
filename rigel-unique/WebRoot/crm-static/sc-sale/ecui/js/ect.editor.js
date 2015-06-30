var ECTableEditor = (function () {
    var COMMON = {
	    'ATTR' : 'ected',
	    'ITEMID' : 'tid'
	},
	_validHandler,
	_curEditItems,
	_curOpItem;

    function parseParamStr(str) {
	var params = str.split(';'),
	    obj = {};
	for (var i = 0, p; p = params[i]; i++) {
	    p = p.split(':');
	    obj[p[0]] = p[1];
	}
	return obj;
    }

    function eachEditItem(fun) {
	var o;
	for (var name in _curEditItems) {
	    o = _curEditItems[name];
	    fun.call(null, o, name);
	}
    }

    function addEditItems(paramStr, node) {
	var params = parseParamStr(paramStr), o;

	o = _curEditItems[params.name] = params;
	o.nodeEle = node;
    }

    function createInput(value) {
	return '<input type="text" style="width:98%" value="'+ value +'"/>';
    }

    function createSelect(value, data) {
	var html = ['<select>'],
	    oTemplate = '<option value="{0}" "{2}">{1}</option>',
	    str;
	data = window[data] || [];
	for (var i = 0, len = data.length; i < len; i++) {
	    if (data[i].value == value || data[i].text == value) {
		str = 'selected';
	    }
	    else {
		str = '';
	    }
	    html.push(oTemplate.format(data[i].value, data[i].text, str));
	}
	html.push('</select>');
	return html.join('');
    }

    function renderInput(con, cancel) {
	var o, e;
	for (var name in _curEditItems) {
	    o = _curEditItems[name]; 
	    e = o.nodeEle;
	    if (con) {
		o.oValue = e.innerHTML.trim();
		switch(o.type) {
		    case 'text' : 
			e.innerHTML = createInput(o.oValue);
			break;
		    case 'select' :
			e.innerHTML = createSelect(o.oValue, o.datasource);
			break;
		}
	    }
	    else {
		e.innerHTML = cancel ? o.oValue : o.nText;
	    }
	}
    }

    function getItemValue() {
	var values = {}, ele, type;
	eachEditItem(function (obj, name) {
	    ele = obj.nodeEle.childNodes[0];
	    type = ele.nodeName.toLowerCase(); 
	    if (type == 'input') {
		obj.nText = obj.nValue = ele.value.trim();
	    }
	    else if(type == 'select') {
		obj.nValue = ele.value;
		obj.nText = ele.options[ele.selectedIndex].innerHTML;
	    }
	    else {
		obj.nValue = obj.nText = '';
	    }
	    values[name] = obj.nValue;
	});
	return values;
    }

    function renderOpt(el) {
	if (el) {
	    _curOpItem.nodeEle = el;
	    _curOpItem.oHTML = el.innerHTML;
	    el.innerHTML = '<a href="#" onclick="ECTableEditor.save();return false">保存</a>&nbsp;<a href="#" onclick="ECTableEditor.cancel();return false;">取消</a>';
	}
	else {
	    (o = _curOpItem.nodeEle) && (o.innerHTML = _curOpItem.oHTML);
	}
    }

    function getChildsByTagName(el, name) {
	var child = el.childNodes,
	    res = [];
	for (var i = 0; i < child.length; i++) {
	    if(child[i].nodeName.toLowerCase() == name) {
		res.push(child[i]);
	    }
	}
	return res;
    }

    function renderNewData() {
	renderInput(false, false);
	renderOpt(false);
    }

    return {
	init : function (validHanlder) {
	    _validHandler = validHanlder;
	},
	
	edit : function (el) {
	    var tr = baidu.dom.getAncestorByTag(el, "tr").getControl(),
		tds = tr.getCols(), i, spans, j, att;
	    
	    _curEditItems = {}; 
	    _curOpItem = {};
	    _curOpItem.paramId = el.getAttribute('tid');
	    this.cancel(true);
	    for (i = 0; td = tds[i]; i++) {
		td = td.getBase();
		td = getChildsByTagName(td, 'div')[0];
		spans = getChildsByTagName(td, 'span');
		for (j = 0; td = spans[j]; j++) {
		    if (td.className == COMMON.ATTR + '-opt') {
			renderOpt(td);
			continue;
		    }
		    att = td.getAttribute(COMMON.ATTR);
		    if (!att) {
			continue;
		    }
		    addEditItems(att, td);
		}
	    }
	    renderInput(true);
	},

	cancel : function (con) {
	    var o;
	    if (!con && !confirm('确定要取消修改吗？')) {
		return;
	    }
	    renderInput(false, true);
	    renderOpt(false);
	},

	save : function () {
	    var data = getItemValue();
	    _validHandler.call(null, data, _curOpItem.paramId, renderNewData);
	}
    };
})();
