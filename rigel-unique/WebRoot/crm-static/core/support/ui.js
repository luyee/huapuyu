/**
 * Baidu UE JavaScript Library - Web Control
 *
 * ui.js
 * @author RigelFE
 */


/**
 * @fileoverview 遮盖层控件
 *
 * <pre>
 * 由于js对当前页面宽度和高度取值的时候，获取不到margin的值
 * 而IE/Firefox/Safari浏览器会给页面body一个默认的margin值
 * 所以mask只能使用在body的margin为0的页面中
 * 为了便于布局以及屏蔽浏览器之间的差异性，通常页面都会设置margin为0
 * </pre>
 *
 * @static
 */
var Mask = {
	onrs : null,
	/**
	 * 显示遮盖层
	 * @public
	 */
	show: function () {
		var maskEl;

		var wid = Math.max(document.documentElement.clientWidth,
			Math.max(document.body.scrollWidth, document.documentElement.scrollWidth));
		var hei = Math.max(document.documentElement.clientHeight,
			Math.max(document.body.scrollHeight, document.documentElement.scrollHeight));

		if (!Mask.uniqueId) {
			Mask.uniqueId = getUniqueId() + "Mark";
			maskEl = new Element('div');
			maskEl.id = Mask.uniqueId;
			maskEl.className = "mask-layer";
			document.body.appendChild(maskEl);
		} else {
			maskEl = G(Mask.uniqueId);
		}

		maskEl.css({
			'left'			: 0,
			'top'			: 0,
			'position'		: 'absolute',
			'width'			: wid + 'px',
			'height'		: hei + 'px',
			'overflow'		: 'auto',
			'zIndex'		: 1500
		});


		//为body添加class，该class会隐藏所有的select框
		var classArr = document.body.className.split(/\s+/);
		classArr.push('body-masked');
		document.body.className = classArr.join(' ');
		var onrs = window.onresize || function() {};
		if (Mask.onrs == null) {
			Mask.onrs = onrs;
		}
		window.onresize = function() {
			onrs();
			Mask.show();
		}
	},

	/**
	 * 隐藏遮盖层
	 * @public
	 */
	hide: function () {
		var maskEl = G(Mask.uniqueId);
		if (!maskEl) {
			return;
		}

		maskEl.css({
			'left'			: '-30px',
			'top'			: '-30px',
			'position'		: 'absolute',
			'width'			: '1px',
			'height'		: '1px',
			'overflow'		: 'hidden'
		});


		//为body删除masked的class
		var classArr = document.body.className.split(/\s+/);
		var len = classArr.length;
		while (len--) {
			if (classArr[len] == 'body-masked') {
				classArr.splice(len, 1);
			}
		}
		document.body.className = classArr.join(' ');
		window.onresize = Mask.onrs;
	}
}



/**
 * 拖拽
 */
var Drag = {
	root : null,
	handle : null,
	offX : null,
	offY : null,
	init : function (root) {
		var root = G(root);
		this.handle = root.getElementsByTagName('div')[0];
		this.handle.root = root;
		this.handle.onmousedown = Drag.start;
		root.onDragStart = new Function();
		root.onDrag = new Function();
		root.onDragEnd = new Function();
	},
	start : function(e) {
		var root = Drag.root = Drag.handle.root;
		var e = new Event(e);
		var mouseX = e.x();
		var mouseY = e.y();
		var rootPos = G(root).getPosition();
		var x = rootPos.left, y = rootPos.top, w = root.offsetWidth;
		root.css({top : y + 'px', left : x + 'px', width : w + 'px', filter : 'alpha(opacity=75)', opacity : 0.75});
		var offX = Drag.offX = mouseX - x;
		var offY = Drag.offY = mouseY - y;
		root.onDragStart(x, y);
		document.onmousemove = Drag.drag;
		document.onmouseup = Drag.end;
		return false;
	},
	drag : function(e) {
		var e = new Event(e);
		var mouseX = e.x();
		var mouseY = e.y();
		var x = mouseX - Drag.offX;
		var y = mouseY - Drag.offY;
		Drag.root.css({top : y + 'px', left : x + 'px'});
		Drag.root.onDrag(x, y);
		return false;
	},
	end : function() {
		document.onmousemove = null;
		document.onmouseup = null;
		var handle = Drag.handle;
		var rootPos = (handle.root).getPosition();
		var x = rootPos.left, y = rootPos.top;
		handle.root.onDragEnd(x, y);
		Drag.root.css({filter : 'alpha(opacity=100)', opacity : 1});
		handle = null;
	}
};

/**
 * 浮动层
 */
var Popup = {
	show : function (el) {
		Drag.init(el);
		Mask.show();
		
		var popWin = G(el);
		popWin.css({"display" : "", "position" : "absolute"});

		/*元素要在显示的状态下才能计算offsetWidth或者scrollWidth*/
		var left = document.body.offsetWidth / 2 - popWin.offsetWidth/2;
        var top = document.documentElement.scrollTop + (window.innerHeight || document.documentElement.clientHeight) / 2 - popWin.offsetHeight / 2;

		top = top < 0 ? 0 : top;
		left = left < 0 ? 0 : left;

        popWin.style.left = left + 'px';
        popWin.style.top = top + 'px'; 
	},
	hide : function (el) {
		Mask.hide();
		G(el).css({"display" : "none"});
	}
};

/**
 * 验证表单元素的格式, 属性validate表示当前数据的名称；属性trim="yes"时会过滤数据两端的空白字符；属性pattern表示
 * 需要验证的正则表达式，会自动在当前正则表达式前后添加^与$表示行首行尾转义；属性maxValue,minValue表示数据允许的
 * 最大、最小数值；属性maxLen, minLen表示数据允许的最大、最小长度，此时，有一个属性character表示长度验证是否基于
 * 字符，否则表示基于字节；属性custom表示自定义的验证处理，这里填入的是验证函数的文件名，在调用函数时，第一个参
 * 数是需要验证的值，第二个参数是数据的名称；属性message表示验证失败时需要显示的错误信息。例如：
 * <input name="email" pattern="[A-Za-z][\w.]*@[A-Za-z][\w.]*" maxLen="64" trim="yes" validate="邮箱地址" />
 * <input name="year" minValue="1990" maxValue="2020" message="请填入1990-2020之间的年份" />
 * 如果未设置invalid属性, 验证失败时提示信息在输入框中生成, 如果设置了invalid属性, 则会在指定的Element中显示出错
 * 信息, 或者是调用指定的函数处理出错信息.
 * @public
 *
 * @param {Element} e 表单元素
 * @return {Boolean} 是/否为允许的格式
 */
function Validate(e)
{
	if (e._sValue !== undefined)
	{
		return false;
	}
	e = G(e);
	var o = Validate;
	var name = e.get('validate') || e.name;
	var value = e.get('trim') == 'yes' ? e.value.trim() : e.value;
	var msg =
		o._pattern(value, name, e.get('pattern'))
		|| o._number(value, name, e.get('maxValue'), e.get('minValue'))
		|| o._length(value, name, e.get('maxLen'), e.get('minLen'), e.get('character') !== false)
		|| o._custom(value, name, e.get('custom'));
	if (msg)
	{
		msg = e.get('message') || msg;
		if (!e.onInvalid)
		{
			var invalid = e.get('invalid');
			if (invalid)
			{
				o = G(invalid);
				if (o)
				{
					/* 如果有同名的Element, 则设置Element的innerHTML值 */
					e._eMsg = o;
					e.onInvalid = function (msg)
					{
						this._eMsg.innerHTML = msg;
					}
					e.onValid = function ()
					{
						this._eMsg.innerHTML = '';
					}
				}
				else
				{
					/* 如果没有同名的Element, 调用函数处理 */
					e.onInvalid = window[invalid];
					e.onValid = e.onInvalid.valid;
				}
			}
			else
			{
				e.onInvalid = function ()
				{
					this._sValue = this.value;
					this.addClass('invalid');
					this.value = msg;
					this.onfocus = function ()
					{
						this.removeClass('invalid');
						var v = this._sValue;
						/* 恢复原来的值 */
						if (v !== undefined)
						{
							this.value = v;
							this._sValue = undefined;
						}
					}
				}
			}
		}
		try
		{
			e.onInvalid(msg);
		}
		catch (e)
		{
		}
		return false;
	}
	try{
		e.value = value;
	}
	catch(e)
	{
	}
	e.onValid && e.onValid();
	return true;
}

Validate.ERR_SUBMIT = "数据无法向服务器提交";
Validate.AFFIRM_REMOVE = "真的要删除它吗？";
Validate.ERR_REQUIRED = "{0}不能为空";
Validate.ERR_LEAST_LETTER = "{0}至少需要包含{1}字节";
Validate.ERR_MOST_LETTER = "{0}最多只能包含{1}字节";
Validate.ERR_LEAST_CHAR = "{0}至少需要包含{1}字符";
Validate.ERR_MOST_CHAR = "{0}最多只能包含{1}字符";
Validate.ERR_MINIMUL = "{0}必须大于{1}";
Validate.ERR_MAXIMUL = "{0}必须小于{1}";
Validate.ERR_INVAILD = "{0}无效";

/**
 * 验证整个区域内的元素
 * @public
 *
 * @param {Element} el Element元素
 * @return {Boolean} 是/否验证通过
 */
Validate.validElement = function (el,needFocus)
{	
	needFocus = (typeof needFocus == 'undefined') ? true : needFocus;
	var list = el.getElementsByTagName('*');
	var r = true;
	var firstErrItem = null;
	for (var i = 0, o; o = list[i]; i++)
	{
		var tagName = o.tagName.toLowerCase();
		if ((tagName == 'input' || tagName == 'select' || tagName == 'textarea') && (o.offsetWidth || o.type=='hidden') && !Validate(o))
		{
			if (!firstErrItem) {
				firstErrItem = o;
			}
			r = false;
		}
	}

	if (firstErrItem && needFocus) {
		try {
			firstErrItem.focus(); //ie下对隐藏的元素聚焦会产生错误
		}catch(e){}
	}
	
	return r;
}

/**
 * 验证整个表单
 * @public
 *
 * @param {FormElement} form 表单元素
 * @return {Boolean} 是/否验证通过
 */
Validate.validForm = function (form)
{
	var list = form.elements;
	var r = true;
	for (var i = 0, o; o = list[i]; i++)
	{
		if ((o.offsetWidth || o.type=='hidden') && !Validate(o))
		{
			r = false;
		}
	}
	return r;
}

/**
 * 对数据进行正则表达式验证
 * @public
 *
 * @param {String} value 要验证的字符串
 * @param {String} name 字符串名称
 * @param {String} regexp 正则表达式字符串
 * @return {String} 如果有值返回的是出错提示信息
 */
Validate._pattern = function (value, name, regexp)
{
	if (regexp && !value.match(new RegExp('^' + regexp + '$')))
	{
		return Validate.ERR_INVAILD.format(name);
	}
}

/**
 * 对数据进行数值表达式验证
 * @public
 *
 * @param {String} value 要验证的字符串
 * @param {String} name 字符串名称
 * @param {String} max 允许的最大数值, 如果为空表示没有最大值限制
 * @param {String} min 允许的最小数值, 如果为空表示没有最小值限制
 * @return {String} 如果有值返回的是出错提示信息
 */
Validate._number = function (value, name, max, min)
{
	if (max || min)
	{
		if (!value.match(/^[0-9]+(\.[0-9]+)?$/))
		{
			return Validate.ERR_INVAILD.format(name);
		}
		else
		{
			value = value.toNumber();
			if (max !== null && value > max.toNumber())
			{
				return Validate.ERR_MAXIMUL.format(name, max);
			}
			if (min !== null && value < min.toNumber())
			{
				return Validate.ERR_MINIMUL.format(name, min);
			}
		}
	}
}

/**
 * 对数据进行长度验证
 * @public
 *
 * @param {String} value 要验证的字符串
 * @param {String} name 字符串名称
 * @param {String} max 允许的最大长度, 如果为空表示没有最大长度限制
 * @param {String} min 允许的最小长度, 如果为空表示没有最小长度限制
 * @return {String} 如果有值返回的是出错提示信息
 */
Validate._length = function (value, name, max, min, character)
{
	if (max || min)
	{
		var length = character ? value.length : value.count();
		if (max && length > max.toNumber())
		{
			return character ? Validate.ERR_MOST_CHAR.format(name, max) : Validate.ERR_MOST_LETTER.format(name, max);
		}
		if (min && length < min.toNumber())
		{
			return length ? (character ? Validate.ERR_LEAST_CHAR.format(name, min) : Validate.ERR_LEAST_LETTER.format(name, min)) : Validate.ERR_REQUIRED.format(name);
		}
	}
}

/**
 * 自定义验证调用
 * @public
 *
 * @param {String} value 要验证的字符串
 * @param {String} name 字符串名称
 * @param {String} func 自定义验证函数名
 * @return {String} 如果有值返回的是出错提示信息
 */
Validate._custom = function (value, name, func)
{
	if (func)
	{
		return window[func](value, name);
	}
}
