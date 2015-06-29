/**
 * Baidu UE JavaScript Library
 *
 * core.js
 * @author RigelFE
 */


(function () {
	var uniqueIdMap = {};

	/**
	 * @param <Number> len 生成uniqueId的长度
	 * @owner window
	 */
	getUniqueId = function (len) {
		var l = len || 8;
		var uid = '';
		while (l--) {
			uid += getRandomChar();
		}
		if (!uniqueIdMap[uid]) {
			uniqueIdMap[uid] = 1;
			return uid;
		} else {
			return getUniqueId(l);
		}
	}

	var getRandomChar = function () {
		var charMap = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
		var charMapLen = charMap.length;
		return charMap.charAt(Math.floor(Math.random() * charMapLen));
	}
})();
/**
 * Baidu UE JavaScript Library
 *
 * core.js
 * @author UTer
 */



/**
 * 将一个对象的属性成员复制到另一个
 *
 * @param {object} toObj 目标对象
 * @param {object} fromObj 源对象
 * @return {object} 目标对象
 */
function _extend(toObj, fromObj) {
    if (toObj && fromObj) {
        for (var prop in fromObj) {
            toObj[prop] = fromObj[prop];
        }
    }
    return toObj;
}

/**
 * 屏蔽异常的function执行
 *
 * @return {object} 目标函数返回值
 */
function _try() {
    for (var i = 0, l = arguments.length; i < l; i++) {
        try {
            return arguments[i]();
        } catch (e) {}
    }
    return null;
}
/**
 * Baidu UE JavaScript Library
 *
 * event.js
 * @author UTer
 */


/**
 * 事件包装类
 * @public
 * @param {event} event EventArgument
 */
function Event(event) {
    event = event || window.event;
    this.target = event.target || event.srcElement;
    _extend(this, event);
    this.keyCode = event.which ? event.which : event.keyCode;
    this.rightClick = (event.which == 3) || (event.button == 2);

	this.x = function () {
		return event.pageX || event.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft);
	}

	this.y = function () {
		return event.pageY || event.clientY + (document.documentElement.scrollTop || document.body.scrollTop);
	}

	this.isIn = function (o) {
		var o = G(o);
		var nodePos = o.getPosition();
		var x1 = nodePos.left;
		var y1 = nodePos.top;
		var x2 = x1 + o.offsetWidth;
		var y2 = y1 + o.offsetHeight;

		if(this.x() >= x1 && this.x() <= x2 && this.y() >= y1 && this.y() <= y2) {
			return true;
		}

		return false;
	}
}

/**
 * 为页面元素添加事件监听器
 * @param {HTMLElement} element 页面元素
 * @param {String} eventType 监听的事件类型
 * @param {Function} listener 监听器
 */
Event.add = function (element, eventType, listener) {
    if (window.addEventListener) {
        element.addEventListener(eventType, listener, false);
    } else {
        element.attachEvent('on' + eventType, listener);
    }
};

/**
 * 为页面元素移除事件监听器
 * @param {HTMLElement} element 页面元素
 * @param {String} eventType 监听的事件类型
 * @param {Function} listener 监听器
 */
Event.remove = function (element, eventType, listener) {
    if (window.removeEventListener) {
        element.removeEventListener(eventType, listener, false);
    } else {
        element.detachEvent('on' + eventType, listener);
    }
};
/**
 * Baidu UE JavaScript Library
 *
 * event-observer.js
 * @author UTer
 */


/**
 * 事件观察者类
 *
 * <pre>
 * 事件挂载解决方案：解决直接挂载事件容易被覆盖、添加监听器在不同浏览器下this指针不同的问题
 * </pre>
 *
 * @public
 * @param {HTMLElement} el 事件挂载的元素
 * @param {String} eventType 事件类型
 */
function EventObserver(el, eventType) {
    this.listeners = [];
    this.el = el;
    this.eventType = eventType;
    var me = this;
    el[eventType] = function (event) {
        var evt = new Event(event);
        me.notify.call(me, evt, this);
    };
}
EventObserver.prototype = {
    /**
     * 添加事件监听器
     * @public
     * @param {Function} listener 监听的function
     */
    add: function (listener) {
        this.listeners.push(listener);
    },

    /**
     * 移除事件监听器
     * @public
     * @param {Function} listener 监听的function
     */
    remove: function (listener) {
        var listeners = this.listeners;
        var len = listeners.length;
        while (len--) {
            if (listener === listeners[len]) {
                this.listeners.splice(len, 1);
            }
        }
    },

    /**
     * 清除事件观察者
     * @public
     */
    clear: function () {
        this.listeners.splice(0, this.listeners.length);
        this.el[this.eventType] = null;
        this.el = null;
    },

    /**
     * 事件触发执行入口
     * @private
     * @param {Event} event 事件信息
     * @param {HTMLElement} sender 触发事件的元素
     */
    notify: function (event, sender) {
        var listeners = this.listeners;
        for (var i = 0, l = listeners.length; i < l; i++) {
            var listener = listeners[i];
            if (listener instanceof Function) {
                listener.call(sender, event);
            }
        }
    }
};
/**
 * Baidu UE JavaScript Library
 *
 * ajax.js
 * @anthor UTer
 */


/**
 * Ajax类
 * @param {Function} onSuccess 请求成功时回调方法
 * @param {Function} onFail 请求失败时回调方法
 */
function Ajax(onSuccess, onFail) {
    this.onSuccess = onSuccess;
    if (onFail) {
        this.onFail = onFail;
    }

    //初始化XMLHttpRequest
    if (window.XMLHttpRequest) {
        this.xhr = new XMLHttpRequest();
    } else {
        try {
            this.xhr = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            this.xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }

    this.async = true;
    this.dataType = "text";
}

Ajax.prototype = {
    /**
     * 设置获取的数据类型为XML
     * @public
     */
    setXML: function () {
        this.dataType = "xml";
    },

    /**
     * 向服务器端发送数据
     * @public
     * @param {String} url 请求地址
     * @param {String} data 发送的数据，格式参照a=aaa&b=bbb
     * @param {String} method 请求方式，post|get
     * @param {String} encoding 发送数据的编码方式，仅post时有效
     */
    send: function (url, data, method, encoding) {
        var meth = "get";
        var contentType = "application/x-www-form-urlencoded";

        //发送类型
        if (method && method == "post") {
            meth = "post";
        }

        //根据发送类型get/post，解析url和发送头
        if (meth == "post") {
            contentType += (encoding ? (';charset=' + encoding) : '');
        } else {
            if (url.indexOf('?') < 0) {
                url = url + '?';
            }

            var lastIndex = url.length - 1;
            if (url.lastIndexOf('&') != lastIndex &&
                url.lastIndexOf('?') != lastIndex) {
                url = url + '&';
            }
            if (data) {
                url = url + data + '&';
            }
            url  = url + "reqTime=" + new Date().getTime();
            data = null;
        }

        //发送数据
        var me = this;
        this.xhr.open(meth.toUpperCase(), url, this.async);
        this.xhr.onreadystatechange = function () {
            me.onStateChg.call(me);
        };
        if (meth == "post") {
            this.xhr.setRequestHeader("Content-Type", contentType);
        }
        this.xhr.send(data);
    },

    /**
     * 通过post方式向服务器端发送数据
     * @public
     * @param {String} url 请求地址
     * @param {String} data 发送的数据，格式参照a=aaa&b=bbb
     * @param {String} encoding 发送数据的编码方式
     */
    post: function (url, data, encoding) {
        this.send(url, data, "post", encoding);
    },

    /**
     * 通过get方式向服务器端发送数据
     * @public
     * @param {String} url 请求地址
     * @param {String} data 发送的数据，格式参照a=aaa&b=bbb
     */
    get: function (url, data) {
        this.send(url, data);
    },

    /**
     * 在send中调用，当readyState发生改变时触发
     * @private
     */
    onStateChg: function () {
        //请求状态未就绪时不做任何事
        if (this.xhr.readyState != 4) {
            return;
        }

        //根据状态码触发请求成功/失败的function
        if (this.xhr.status >= 200 && this.xhr.status < 300) {
            var data;
            if (this.dataType == "xml") {
                data = this.xhr.responseXML;
            } else {
                data = this.xhr.responseText;
            }

            this.onSuccess.call(this, data);
        } else if (this.onFail) {
            this.onFail();
        }
    }
};
/**
 * Baidu UE JavaScript Library
 *
 * cookie.js
 * @author UTer
 */

/**
 * Cookie 操作类
 * 优化说明，重视多次读取效率。写入次之
 *
 * @param {String} name cookie名称
 * @param {String} path cookie路径
 * @param {String} expires cookie时间
 * @param {String} domain cookie域
 * @param {String} secure cookie私有
 */
function Cookie(name, path, expires, domain, secure) {
    this.name = encodeURIComponent(name);
    this.path = path;
    this.expires = expires;
    this.domain = domain;
    this.secure = secure;

    this._getExp = new RegExp("^(?:.*" + this.name + "=([^;]*))?.*");
}

Cookie.prototype = {
    /**
     * 设置cookie的值
     * @public
     *
     * @param {String} value cookie的值
     */
    set: function (value) {
        document.cookie = this.name + "=" + encodeURIComponent(value)
            + this._buildCookieString(this.path, this.expires, this.domain, this.secure);
    },

    /**
     * 清除cookie
     * @public
     */
    remove: function () {
        document.cookie = this.name + "="
            + this._buildCookieString(this.path, new Date(0), this.domain, this.secure);
    },

    /**
     * 获取cookie的值
     * @public
     *
     * @return {String} cookie的值
     */
    get: function () {
        var value = document.cookie.replace(this._getExp, '$1');
        return value && decodeURIComponent(value);
    },

    /**
     * 构造用于写入的cookie字符串
     * @private
     *
     * @param {String} path cookie路径
     * @param {String} expires cookie时间
     * @param {String} domain cookie域
     * @param {String} secure cookie私有
     * @return {String} cookie字符串
     */
    _buildCookieString: function (path, expires, domain, secure) {
        return (path ? "; path=" + path : "") +
            (expires ? "; expires=" + expires.toGMTString() : "") +
            (domain ? "; domain=" + domain : "") +
            (secure ? "; secure=" : '');
    }
};

/**
 * Baidu UE JavaScript Library
 *
 * format-number.js
 * @author UTer
 */


/**
 * 将数字格式化成设定的格式.
 * <h3>数字模式说明</h3>
 * 首先以“.”分割成整数部分和小数部分
 * 能后，可以对不同部分做分组，一般是三个数字一组（老外的习惯，中国好像是四个字）.','是分组位置。
 * 分组大小，整数和小数部分各有自己的设置，以离小数点最近位置的一个表示为准（javaFormat的做法）
 *
 * 截断与补零：尽量不改变数值量，或少改动数值量
 * 整数部分向前补零，小数部分向后补零
 * 整数部分永远不截断，小数部分向后截断
 * <pre>
 * 符号说明：
 * 0 表示补0 的数字占位
 * . 表示小数点
 * , 数字分组符号 如123,456.123
 * # 表示不补0 的数字占位
 *
 * Number                  Pattern     Result
 * 10000000000001124       #,###.###   10,000,000,000,001,124.000
 * 123.125                 ##,#.#,#    1,2,3.1,3
 * 123.125                 ###.#       123.1
 * 123.125                 00000       00123
 * 123.125                 .000        .125
 * 0.125                   0.0000      0.1250
 * 0.125                   00.0000     00.1250
 *
 * 使用代码:
 * var numberText = format("##.#",123.456)//output 123.45
 * </pre>
 * @public
 * @param {String} pattern 格式化模式字符串(参考上面说明)
 * @param {Number} data 需要格式化的数据
 * @author jindw 2008-07
 */
function formatNumber(pattern, data) {
    //hack:purePattern as floatPurePattern
    function trim(data, pattern, purePattern) {
        if (pattern) {
            if (purePattern) {
                if (purePattern.charAt() == '0') {
                    data = data + purePattern.substr(data.length);
                }
                if (purePattern != pattern) {
                    pattern = new RegExp("(\\d{" + pattern.search(/[^\d#]/) + "})(\\d)");
                    while (data.length < (data = data.replace(pattern, '$1,$2')).length) {
                    }
                }
                data = '.' + data;
            } else {
                purePattern = pattern.replace(/[^\d#]/g, '');
                if (purePattern.charAt() == '0') {
                    data = purePattern.substr(data.length) + data;
                }
                if (purePattern != pattern) {
                    pattern = new RegExp(
                        "(\\d)(\\d{" +
                        (pattern.length - pattern.search(/[^\d#]/) - 1) +
                        "})\\b");
                    while (data.length < (data = data.replace(pattern, '$1,$2')).length) {
                    }
                }
            }
            return data;
        } else {
            return '';
        }
    }
    return pattern.replace(/([#0,]*)?(?:\.([#0,]+))?/, function (param, intPattern, floatPattern) {
        var floatPurePattern = floatPattern.replace(/[^\d#]/g, '');
        data = data.toFixed(floatPurePattern.length).split('.');
        return trim(data[0], intPattern) + trim(data[1] || '', floatPattern, floatPurePattern);
    });
}
/**
 * Baidu UE JavaScript Library
 *
 * format-date.js
 * 部分程序参考 trydofor.com 日期格式化程序 LGPL May.2007
 * @author UTer
 */


/**
 * 将日期格式化成设定的格式.
 * <h3>日期模式说明</h3>
 * <pre>
 * like the ISO 8895
 * also see Java's SimpleDateFormat.
 *
 * Letter  Date or Time Component  Presentation    Examples          UserDic
 * Y       Year                    Year            1996; 96
 * M       Month in year           Month           July; Jul; 07     *
 * D       Day in month            Number          10
 * w       Day in week             Text            Tuesday; Tue; 2   *
 * h       Hour in day (0-23)      Number          0
 * m       Minute in hour          Number          30
 * s       Second in minute        Number          55
 *
 *
 *
 * Pattern                       Sample
 * YYYY-MM-DD hh:mm:ss           2001-07-04 12:08:56
 * YYYY-MM-DDThh:mm:ss           2001-07-04T12:08:56
 * YYYY/MM/DDThh:mm:ss           2001/07/04T12:08:56
 * YYYY年MM月DD日,周w              2008年12月12日,周3
 * hh:mm                         12:08
 *
 * 使用代码:
 * var dateText = format("YYYY-MM-DD",date)
 * </pre>
 *
 * @public
 * @param {String} pattern 格式化模式字符串(参考上面说明)
 * @param {Date | Number} data 需要格式化的数据
 * @author a9text May.2007
 * @author jindw 2008-07
 * @author erik168 2008-11
 */

function formatDate(pattern, data) {
    function dl(data, format) {
        format = format.length;
        data = data || 0;
        var d = String(Math.pow(10, format) + data);
        return format == 1 ? data : d.substr(d.length - format);
    }
    return pattern.replace(/([YMDhsmw])\1*/g, function (format) {
        switch (format.charAt()) {
        case 'Y' :
            return dl(data.getFullYear(), format);
        case 'M' :
            return dl(data.getMonth() + 1, format);
        case 'D' :
            return dl(data.getDate(), format);
        case 'w' :
            return data.getDay();
        case 'h' :
            return dl(data.getHours(), format);
        case 'm' :
            return dl(data.getMinutes(), format);
        case 's' :
            return dl(data.getSeconds(), format);
        }
    });
}
/**
 * Baidu UE JavaScript Library
 *
 * class.js
 * @author UTer
 */


/**
 * 元类
 * @public
 * @param {object} props 类成员
 * @param {Class} superClass 父类
 * @return {Class} 创建的类
 */
function Class(props, superClass) {
	var con = props.constructor == Object ? undefined : props.constructor;
	if (superClass) {
		var superConstructor = function () {
		    superClass.call(this);
		};
	}
	var clazz = con || superConstructor || new Function();
	var s_ = new Function();
	if (superClass) {
		s_.prototype = superClass.prototype;
		clazz.prototype = new s_();
	}
	for (var k in props) {
		clazz.prototype[k] = props[k];
	}
	clazz.constructor = clazz;
	return clazz;
}
/**
 * Baidu UE JavaScript Library
 *
 * string.js
 * @author UTer
 */


/**
 * 删除字符串中的首尾空白字符
 *
 * @public
 * @return {String} 处理过的字符串
 */
String.prototype.trim = function () {
    return this.replace(/(^[\s\u3000\xa0]+|[\s\u3000\xa0]+$)/g, '');
};

/**
 * 编码字符串中的html敏感字符
 *
 * @public
 * @return {String} 处理过的字符串
 */
String.prototype.escapeHTML = function () {
    return this.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
};

/**
 * 反编码字符串中的html敏感字符
 *
 * @public
 * @return {String} 处理过的字符串
 */
String.prototype.unescapeHTML = function () {
    return this.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&');
};

/**
 * 简单的字符串格式化
 *
 * @public
 * @return {String} 格式化后的字符串
 */
String.prototype.format = function () {
    var argus = Array.prototype.slice.call(arguments);
    var reStr = this.replace(/\{([0-9]+)\}/g, function ($0, num) {
        var str = argus[parseInt(num, 10)];
        return  typeof(str) == 'undefined' ? '' : str;
    });
    return reStr;
};

/**
 * 判断字符串是否以某个字符串开始
 *
 * @public
 * @param {String} str 开始的字符串
 * @return {Boolean} 是否以param string开始
 */
String.prototype.startWith = function (str) {
    return this.indexOf(str) === 0;
};

/**
 * 判断字符串是否以某个字符串结尾
 *
 * @public
 * @param {String} str 开始的字符串
 * @return {Boolean} 是否以param string结尾
 */
String.prototype.endWith = function (str) {
    return this.lastIndexOf(str) == (this.length - str.length);
};

/**
 * 将字符串转换为数值
 *
 * @public
 * @return 字符串表示的数值
 */
String.prototype.toNumber = function () {
	var i = parseInt(this);
	return isNaN(i) ? 0 : i;
};

/**
 * Baidu UE JavaScript Library
 *
 * element.js
 * @author UTer
 */

/**
 * Dom对象的扩展类
 * @public
 */
var Element = function (el) {
    if (!el) {
        return;
    }

    if (el.constructor == String) {
        el = document.createElement(el);
    }
    return G(el);
}

Element.prototype = {
    /**
     * 包装器版本信息
     * @private
     */
    wrapVersion: 1,

    /**
     * Element的样式操作
     * <pre>
     * <b>设置css：</b>
     * .css('width', '500px')
     * .css({width : '500px', backgroundColor : '#ccc'})
     * .css('width', '500px', 'backgroundColor', '#ccc')
     * <b>获取css：</b>
     * .css('width')
     * </pre>
     * @public
     *
     * @return {Element} 当前元素
     */
    css: function (arg) {
        var argLen = arguments.length;
        if (argLen > 1 && argLen % 2 === 0) {
            for (var i = 0; i < argLen; i += 2) {
                var a = arguments[i], a2 = arguments[i + 1];
                if (typeof a == 'string' && typeof a2 == 'string') {
                    this.style[a] = a2;
                }
            }
        } else if (argLen == 1) {
            if (typeof arg == 'string') {
                var sty = this.currentStyle || document.defaultView.getComputedStyle(this, null);
                return sty[arg];
            } else {
                for (var k in arg) {
                    this.style[k] = arg[k];
                }
            }
        }
        return this;
    },

    /**
     * 设置Element可见
     * @public
     *
     * @param {String} display 新的显示样式:block|inline....,默认为""(有些情况下,style.display=''还不能显示元素,原因待查)
     * @return {Element} 当前元素
     */
    show: function (display) {
        this.style.display = display || '';
        return this;
    },

    /**
     * 设置Element隐藏
     * @public
     *
     * @return {Element} 当前元素
     */
    hide: function () {
        this.style.display = "none";
        return this;
    },

    /**
     * 为Element添加class
     * @public
     *
     * @param {String} name class的名称
     * @return {Element} 当前元素
     */
    addClass: function (name) {
        var classArr = this.className.split(/\s+/);
        classArr.push(name);
        this.className = classArr.join(' ');
        return this;
    },

    /**
     * 判断Element是否包含class
     * @public
     *
     * @param {String} name class的名称
     * @return 是/否包含class
     */
    containClass: function (name) {
        var classArr = this.className.split(/\s+/);
        var len = classArr.length;
        while (len--) {
            if (classArr[len] == name) {
                return true;
            }
        }
        return false;
    },

    /**
     * 删除Element的class
     * @public
     *
     * @param {String} name class的名称
     * @return {Element} 当前元素
     */
    removeClass: function (name) {
        var classArr = this.className.split(/\s+/);
        var len = classArr.length;
        while (len--) {
            if (classArr[len] == name) {
                classArr.splice(len, 1);
            }
        }
        this.className = classArr.join(' ');
        return this;
    },

    /**
     * 添加子Element
     * @public
     *
     * @param {HTMLElement} el 要添加的元素
     * @return {Element} 当前元素
     */
    append: function (el) {
        this.appendChild(el);
        return this;
    },

    /**
     * 设置Element的innerHTML
     * @public
     *
     * @param {String} html html字符串
     * @return {Element} 当前元素
     */
    setHTML: function (html) {
        this.innerHTML = html;
        return this;
    },

    /**
     * 从页面中移除Element
     * @public
     *
     * @return {Element} 当前元素
     */
    remove: function () {
        if (this.parentNode) {
            this.parentNode.removeChild(this);
        }
        return this;
    },

    /**
     * 移除Element内部的文本节点
     * @public
     *
     * @return {Element} 当前元素
     */
    clearEmptyNode: function () {
        var nodes = this.childNodes;
        var i = nodes.length;
        while (i--) {
            var node = nodes[i];
            if (node.nodeType != 1 &&
                (node.nodeType != 3 || node.nodeValue.trim().length === 0)) {
                this.removeChild(node);
            }
        }
        return this;
    },

	/**
	 * 获得Element中指定的属性
	 * @public
	 *
	 * @param {String} name Element属性名称
	 * @return {Object} Element属性值
	 */
	get: function (name) {
		return this.getAttribute(name);
	},

    /**
     * 获得Element的绝对位置
     * @public
     *
     * @return {K,V} 位置信息
     */
    getPosition : function () {
        var el = this;
        if (/MSIE/.test(navigator.userAgent)) {
            var bound = this.getBoundingClientRect();
            var html = document.documentElement;
            var body = document.body;
            return {
                'left': bound.left + (html.scrollLeft || body.scrollLeft),
                'top': bound.top + (html.scrollTop || body.scrollTop)
            };
        }
        var left = el.offsetLeft, top = el.offsetTop;

        while ((el = el.offsetParent)) {
            var tag = el.tagName;
            if (tag == 'HTML' || tag == 'BODY') {
                break;
            }
            left += (el.offsetLeft - el.scrollLeft) || 0;
            top += (el.offsetTop - el.scrollTop) || 0;
        }
        return  {'left': left, 'top': top};
    },

    /**
     * 设置Element的透明度
     * @public
     *
     * @param {Number} opacity 透明度，0-1
     * @return {Element} 当前元素
     */
    setOpacity: function (opacity) {
        this.style.visibility = opacity < 0.001 ? "hidden" : "visible";
        if (!this.currentStyle || !this.currentStyle.hasLayout) {
            this.style.zoom = 1;
        }

        if (window.ActiveXObject) {
            this.style.filter = (opacity == 1) ? '' : "alpha(opacity=" + opacity * 100 + ")";
        }

        this.style.opacity = opacity;
        return this;
    },

	getChildren: function (name) {
		if (name) {
			name = name.toUpperCase();
		}
		var result = new Array();
		for (var o = this.firstChild; o; o = o.nextSibling) {
			if (!name || o.tagName == name) {
				result.push(o);
			}
		}
		return result;
	}
};

/**
 * 获得dom对象的包装
 *
 * @param {string||HTMLElement} el element或element的id
 * @return {Element} dom元素
 */
function G(el) {
    if (typeof(el) == 'string') {
        el = document.getElementById(el);
    }
    if (!el) {
        return null;
    }
    var p = Element.prototype;
    if (el.wrapVersion == p.wrapVersion) {
        return el;
    }
    for (var n in p) {
        el[n] = p[n];
    }
    return el;
}

/**
 * 获得标准事件对象
 *
 * @param event 事件对象, 如果是IE为undefined
 * @return {Event} 事件对象
 */
function getEvent(event)
{
	if (!event)
	{
		event = window.event;
		event.pageX = document.body.scrollLeft + event.clientX - document.body.clientLeft;
		event.pageY = document.body.scrollTop + event.clientY - document.body.clientTop;
		event.target = event.srcElement;
		event.which = event.keyCode;
	}
	return event;
}
