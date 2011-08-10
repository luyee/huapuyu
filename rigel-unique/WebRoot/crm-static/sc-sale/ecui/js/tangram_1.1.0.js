var baidu = baidu || {
    version: "1-1-0",
    emptyFn: new Function()
};

baidu.browser = baidu.browser || {};

(function(){
	var ua = navigator.userAgent;

	baidu.browser.firefox = /firefox\/(\d+\.\d)/i.test(ua) ? parseFloat(RegExp['\x241']) : 0;

	baidu.browser.ie = /msie (\d+\.\d)/i.test(ua) ? parseFloat(RegExp['\x241']) : 0;

	baidu.browser.opera = /opera\/(\d+\.\d)/i.test(ua) ? parseFloat(RegExp['\x241']) : 0;

	baidu.browser.safari = (/(\d+\.\d)(\.\d)?\s+safari/i.test(ua) && !/chrome/i.test(ua)) ? parseFloat(RegExp['\x241']) : 0;

	try{
		baidu.browser.maxthon = /(\d+\.\d)/.test(external.max_version) ? parseFloat(RegExp['\x241']) : 0;
	}catch(e){
		baidu.browser.maxthon = 0;
	}

	baidu.browser.isGecko = /gecko/i.test(ua) && !/like gecko/i.test(ua);

	baidu.browser.isStrict = document.compatMode == "CSS1Compat";

	baidu.browser.isWebkit = /webkit/i.test(ua);
})();

baidu.dom = baidu.dom || {};

baidu.dom._matchNode = function (element, direction, start) {
    element = baidu.dom.g(element);

    for (var node = element[start]; node; node = node[direction]) {
        if (node.nodeType == 1) {
            return node;
        }
    }

    return null;
};

baidu.dom._styleFixer = baidu.dom._styleFixer || {};

baidu.dom._styleFixer["float"] = baidu.browser.ie ? "styleFloat" : "cssFloat";

baidu.dom._styleFixer.opacity = baidu.browser.ie ? {
    get: function (element) {
        var filter = element.style.filter;
        filter && filter.indexOf("opacity=") >= 0 ? (parseFloat(filter.match(/opacity=([^)]*)/)[1]) / 100) + "" : "1";
    },

    set: function (element, value) {
        var style = element.style;
        // 只能Quirks Mode下面生效??
        style.filter = (style.filter || "").replace(/alpha\([^\)]*\)/gi, "") + (value == 1 ? "" : "alpha(opacity=" + value * 100 + ")");
        // IE filters only apply to elements with "layout."
        style.zoom = 1;
    }
} : void(0);

baidu.dom._styleFixer.display = baidu.browser.ie && baidu.browser.ie < 7 ? {
    set: function (element, value) {
        element = element.style;
        if (value == 'inline-block') {
            element.display = 'inline';
            element.zoom = 1;
        } else {
            element.display = value;
        }
    }
} : baidu.browser.firefox && baidu.browser.firefox < 3 ? {
    set: function (element, value) {
        element.style.display = value == 'inline-block' ? '-moz-inline-box' : value;
    }
} : void(0);

baidu.dom._styleFixer.textOverflow = (function () {
    var fontSizeCache = {};

    function pop(list) {
        var o = list.length;
        if (o > 0) {
            o = list[o - 1];
            list.length--;
        } else {
            o = null;
        }
        return o;
    }

    function setText(element, text) {
        element[baidu.browser.firefox ? "textContent" : "innerText"] = text;
    }

    function count(element, width, ellipsis) {
        /* 计算cache的名称 */
        var o = baidu.browser.ie ? element.currentStyle || element.style : getComputedStyle(element, null),
            fontWeight = o.fontWeight,
            cacheName =
                "font-family:" + o.fontFamily + ";font-size:" + o.fontSize
                + ";word-spacing:" + o.wordSpacing + ";font-weight:" + ((parseInt(fontWeight) || 0) == 401 ? 700 : fontWeight)
                + ";font-style:" + o.fontStyle + ";font-variant:" + o.fontVariant,
            cache = fontSizeCache[cacheName];

        if (!cache) {
            o = element.appendChild(document.createElement("div"));

            o.style.cssText = "float:left;" + cacheName;
            cache = fontSizeCache[cacheName] = [];

            /* 计算ASCII字符的宽度cache */
            for (i = 0; i < 256; i++) {
                i == 32 ? (o.innerHTML = "&nbsp;") : setText(o, String.fromCharCode(i));
                cache[i] = o.offsetWidth;
            }

            /* 计算非ASCII字符的宽度、字符间距、省略号的宽度 */
            setText(o, "一");
            cache[256] = o.offsetWidth;
            setText(o, "一一");
            cache[257] = o.offsetWidth - cache[256] * 2;
            cache[258] = cache[".".charCodeAt(0)] * 3 + cache[257] * 3;

            element.removeChild(o);
        }

        for (
            /* wordWidth是每个字符或子节点计算之前的宽度序列 */
            var node = element.firstChild, charWidth = cache[256], wordSpacing = cache[257], ellipsisWidth = cache[258],
                wordWidth = [], ellipsis = ellipsis ? ellipsisWidth : 0;
            node;
            node = node.nextSibling
        ) {
            if (width < ellipsis) {
                element.removeChild(node);
            }
            else if (node.nodeType == 3) {
                for (var i = 0, text = node.nodeValue, length = text.length; i < length; i++) {
                    o = text.charCodeAt(i);
                    /* 计算增加字符后剩余的长度 */
                    wordWidth[wordWidth.length] = [width, node, i];
                    width -= (i ? wordSpacing : 0) + (o < 256 ? cache[o] : charWidth);
                    if (width < ellipsis) {
                        break;
                    }
                }
            }
            else {
                o = node.tagName;
                if (o == "IMG" || o == "TABLE") {
                    /* 特殊元素直接删除 */
                    o = node;
                    node = node.previousSibling;
                    element.removeChild(o);
                }
                else {
                    wordWidth[wordWidth.length] = [width, node];
                    width -= node.offsetWidth;
                }
            }
        }

        if (width < ellipsis) {
            /* 过滤直到能得到大于省略号宽度的位置 */
            while (o = pop(wordWidth)) {
                width = o[0];
                node = o[1];
                o = o[2];
                if (node.nodeType == 3) {
                    if (width >= ellipsisWidth) {
                        node.nodeValue = node.nodeValue.substring(0, o) + "...";
                        return true;
                    }
                    else if (!o) {
                        element.removeChild(node);
                    }
                }
                else if (count(node, width, true)) {
                    return true;
                }
                else {
                    element.removeChild(node);
                }
            }

            /* 能显示的宽度小于省略号的宽度，直接不显示 */
            element.innerHTML = "";
        }
    };

    return {
		get: function (element, style) {
            var browser = baidu.browser;
			return (browser.opera ? style.OTextOverflow : browser.firefox ? element._baiduOverflow: style.textOverflow) || "clip";
		},

		set: function (element, value) {
            var browser = baidu.browser;
			if (element.tagName == "TD" || element.tagName == "TH" || browser.firefox) {
				element._baiduHTML && (element.innerHTML = element._baiduHTML);

				if (value == "ellipsis") {
					element._baiduHTML = element.innerHTML;
					var o = document.createElement("div"), width = element.appendChild(o).offsetWidth;
					element.removeChild(o);
					count(element, width);
				}
				else {
					element._baiduHTML = "";
				}
			}

			o = element.style;
			browser.opera ? (o.OTextOverflow = value) : browser.firefox ? (element._baiduOverflow = value) : (o.textOverflow = value);
		}
    };
})();

baidu.dom.addClass = function (element, className) {
    element = baidu.dom.g(element);

    var classes = className.split(/\s+/), len = classes.length;
    className = element.className.split(/\s+/).join(" ");

    while (len--) {
        new RegExp("(^| )" + classes[len].replace(/\-/g, '\\-') + "( |\x24)").test(className) && classes.splice(len, 1);
    }

    element.className = className + " " + classes.join(" ");

    return element;
};

baidu.dom.children = function (element) {
    element = baidu.dom.g(element);

    for (var children = [], tmpEl = element.firstChild; tmpEl; tmpEl = tmpEl.nextSibling) {
        if (tmpEl.nodeType == 1) {
            children.push(tmpEl);
        }
    }
    
    return children;    
};

baidu.dom.first = function (element) {
    return baidu.dom._matchNode(element, 'nextSibling', 'firstChild');
};

baidu.dom.g = function (id) {
    return typeof id == 'string' ? document.getElementById(id) : id;
};

baidu.dom.getDocument = function (element) {
    element = baidu.dom.g(element);
    return element.nodeType == 9 ? element : element.ownerDocument || element.document;
};

baidu.dom.getPosition = function (element) {
    var doc = baidu.dom.getDocument(element), browser = baidu.browser;

    element = baidu.dom.g(element);

    // Gecko browsers normally use getBoxObjectFor to calculate the position.
    // When invoked for an element with an implicit absolute position though it
    // can be off by one. Therefor the recursive implementation is used in those
    // (relatively rare) cases.
    var BUGGY_GECKO_BOX_OBJECT = browser.isGecko > 0 && 
                                 doc.getBoxObjectFor &&
                                 baidu.dom.getStyle(element, 'position') == 'absolute' &&
                                 (element.style.top === '' || element.style.left === '');

    // NOTE(arv): If element is hidden (display none or disconnected or any the
    // ancestors are hidden) we get (0,0) by default but we still do the
    // accumulation of scroll position.

    var pos = {"left":0,"top":0};

    var viewportElement = (browser.ie && !browser.isStrict) ? doc.body : doc.documentElement;
    
    if(element == viewportElement){
        // viewport is always at 0,0 as that defined the coordinate system for this
        // function - this avoids special case checks in the code below
        return pos;
    }

    var parent = null;
    var box;

    if(element.getBoundingClientRect){ // IE and Gecko 1.9+
        box = element.getBoundingClientRect();

        pos.left = Math.floor(box.left) + Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
        pos.top  = Math.floor(box.top)  + Math.max(doc.documentElement.scrollTop,  doc.body.scrollTop);
	        
        // IE adds the HTML element's border, by default it is medium which is 2px
        // IE 6 and 7 quirks mode the border width is overwritable by the following css html { border: 0; }
        // IE 7 standards mode, the border is always 2px
        // This border/offset is typically represented by the clientLeft and clientTop properties
        // However, in IE6 and 7 quirks mode the clientLeft and clientTop properties are not updated when overwriting it via CSS
        // Therefore this method will be off by 2px in IE while in quirksmode
        pos.left -= doc.documentElement.clientLeft;
        pos.top  -= doc.documentElement.clientTop;

        if(browser.ie && !browser.isStrict){
            pos.left -= 2;
            pos.top  -= 2;
        }
    }else if(doc.getBoxObjectFor && !BUGGY_GECKO_BOX_OBJECT/* && !goog.style.BUGGY_CAMINO_*/){ // gecko
        // Gecko ignores the scroll values for ancestors, up to 1.9.  See:
        // https://bugzilla.mozilla.org/show_bug.cgi?id=328881 and
        // https://bugzilla.mozilla.org/show_bug.cgi?id=330619

        box = doc.getBoxObjectFor(element);
        var vpBox = doc.getBoxObjectFor(viewportElement);
        pos.left = box.screenX - vpBox.screenX;
        pos.top  = box.screenY - vpBox.screenY;
    }else{ // safari/opera
        
        parent = element;

        do{
            pos.left += parent.offsetLeft;
            pos.top  += parent.offsetTop;
      
            // In Safari when hit a position fixed element the rest of the offsets
            // are not correct.
            if (browser.isWebkit > 0 && baidu.dom.getStyle(parent, 'position') == 'fixed') {
                pos.left += doc.body.scrollLeft;
                pos.top  += doc.body.scrollTop;
                break;
            }
            
            parent = parent.offsetParent;
        }while(parent && parent != element);

        // opera & (safari absolute) incorrectly account for body offsetTop
        if(browser.opera > 0 || (browser.isWebkit > 0 && baidu.dom.getStyle(element, 'position') == 'absolute')){
            pos.top  -= doc.body.offsetTop;
        }

        // accumulate the scroll positions for everything but the body element
        parent = element.offsetParent;
        while (parent && parent != doc.body) {
            pos.left -= parent.scrollLeft;
            // see https://bugs.opera.com/show_bug.cgi?id=249965
            if (!browser.opera || parent.tagName != 'TR') {
                pos.top -= parent.scrollTop;
            }
            parent = parent.offsetParent;
        }
    }

    return pos;
}

baidu.dom.getStyle = function (element, key) {
    element = baidu.dom.g(element);
    key = baidu.string.toCamelCase(key);

    var value = element.style[key];
    if (value) {
        return value;
    }

    var fixer = baidu.dom._styleFixer[key];

	/* 在IE下，Element没有在文档树上时，没有currentStyle属性 */
	var style = element.currentStyle || (baidu.browser.ie ? element.style : getComputedStyle(element, null));
	return 'object' == typeof fixer && fixer.get ? fixer.get(element, style) : style[fixer || key];
};

baidu.dom.insertAfter = function (newElement, existElement) {
    var g = baidu.dom.g;
    newElement = g(newElement);
    existElement = g(existElement);

    g = existElement.parentNode;
    (existElement = existElement.nextSibling) ? g.insertBefore(newElement, existElement) : g.appendChild(newElement);

    return newElement;
};

baidu.dom.ready = function () {
    var isReady = false,
        readyBound = false,
        readyList = [];

    function ready() {
        if (!isReady) {
            isReady = true;
            for (var i = 0, j = readyList.length; i < j; i++) {
                try {
                    readyList[i]();
                } catch (e) {
                }
            }
        }
    }

    function bindReady() {
        if (readyBound) {
            return;
        }
        readyBound = true;

        var doc = document, w = window, opera = baidu.browser.opera;

        // Mozilla, Opera (see further below for it) and webkit nightlies currently support this event
        if (doc.addEventListener && !opera) {
            // Use the handy event callback
            doc.addEventListener("DOMContentLoaded", opera ? function () {
                if (isReady) {
                    return;
                }
                for (var i = 0; i < doc.styleSheets.length; i++) {
                    if (doc.styleSheets[i].disabled) {
                        setTimeout( arguments.callee, 0 );
                        return;
                    }
                }
                // and execute any waiting functions
                ready();
            } : ready, false);
        } else if (baidu.browser.ie && w == top) {
            // If IE is used and is not in a frame
            // Continually check to see if the doc is ready
            (function () {
                if (isReady) {
                    return;
                }

                try {
                    // If IE is used, use the trick by Diego Perini
                    // http://javascript.nwbox.com/IEContentLoaded/
                    doc.documentElement.doScroll("left");
                } catch (error) {
                    setTimeout(arguments.callee, 0);
                    return;
                }
                // and execute any waiting functions
                ready();
            })();
        } else if (baidu.browser.safari) {
            var numStyles;
            (function () {
                if (isReady) {
                    return;
                }
                if (doc.readyState != "loaded" && doc.readyState != "complete") {
                    setTimeout( arguments.callee, 0 );
                    return;
                }
                if (numStyles === undefined) {
                    numStyles = 0;
                    var s1 = doc.getElementsByTagName('style');
                    var s2 = doc.getElementsByTagName('link');
                    if (s1) {
                        numStyles += s1.length;
                    }
                    if (s2) {
                        for (var i = 0, j = s2.length; i < j; i ++) {
                            if (s2[i].getAttribute("rel") == "stylesheet") {
                                numStyles ++;
                            }
                        }
                    }
                }
                // numStyles = jQuery("style, link[rel=stylesheet]").length;
                if (doc.styleSheets.length != numStyles) {
                    setTimeout(arguments.callee, 0);
                    return;
                }
                // and execute any waiting functions
                ready();
            })();
        }

        // A fallback to window.onload, that will always work
        w.attachEvent ? w.attachEvent("onload", ready) : w.addEventListener("load", ready, false);
    }

    return function (callback) {
        bindReady();
        isReady ? callback() : (readyList[readyList.length] = callback);
    };
}();

baidu.dom.removeClass = function (element, className) {
    element = baidu.dom.g(element);

    element.className =
        element.className.split(/\s+/).join("  ").replace(new RegExp("(^| )(" + baidu.string.trim(className).replace(/\-/g, '\\-').split(/\s+/).join("|") + ")( |\x24)", "g"), " ");

    return element;
};

baidu.dom.setStyle = function (element, key, value) {
    // 放弃了对firefox 0.9的opacity的支持
    element = baidu.dom.g(element);
    key = baidu.string.toCamelCase(key);

    var fixer = baidu.dom._styleFixer[key];

    'object' == typeof fixer && fixer.set ? fixer.set(element, value) : (element.style[fixer || key] = value);

    return element;
};

baidu.dom.setStyles = function (element, styles) {
    element = baidu.dom.g(element);

    for (var key in styles) {
        baidu.dom.setStyle(element, key, styles[key]);
    }

    return element;
};

baidu.array = baidu.array || {};

baidu.array.indexOf = function (source, condition, position) {
    var len = source.length,
        iterator = condition;
        
    // 参考ecma262的String.prototype.indexOf实现
    // 为undefined时归0，否则进行ToInteger(参见ecma262 3rd 9.4)
    position = Number(position) || 0;
    position = position < 0 ? Math.ceil(position) : Math.floor(position); 
    position = Math.min(Math.max(position, 0), len);
    
    if ('function' != typeof condition) {
        iterator = function (item) {
            return condition === item;
        };
    }
    
    for ( ; position < len; position++) {
        if (true === iterator.call(source, source[position], position)) {
            return position;
        }
    }
    
    return -1;
};

baidu.array.remove = function (source, condition) {
    var len = source.length,
        iterator = condition;
    
    if ('function' != typeof condition) {
        iterator = function (item) {
            return condition === item;
        };
    }
    
    while (len--) {
        if (true === iterator.call(source, source[len], len)) {
            source.splice(len, 1);
        }
    }
};

baidu.string = baidu.string || {};

baidu.string.toCamelCase = function (source) {
    return String(source).replace(/-\D/g, function (match) {
        return match.charAt(1).toUpperCase();
    });
};

baidu.string.trim = function (source) {
    return String(source)
            .replace(new RegExp("(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g"), "");
};

baidu.object = baidu.object || {};

baidu.object.extend = function (target, source) {
    for (var p in source) {
        if (source.hasOwnProperty(p)) {
            target[p] = source[p];
        }
    }
};

baidu.lang = baidu.lang || {};

baidu.lang.inherits = function(childClass, parentClass, className) { 
    var i, p, op = childClass.prototype, C = function(){};
    C.prototype = parentClass.prototype;
    p = childClass.prototype = new C();

    if (typeof className == "string"){
        p._className = className;
    }

    for (i in op){
        p[i]=op[i];
    }
    childClass.prototype.constructor = childClass;
    childClass.superClass = parentClass.prototype;
    op = C = null;
    return p;
};


/**
 * \u63d0\u4f9b\u4e86\u4e00\u4e9b\u5bf9Cookie\u5e38\u7528\u64cd\u4f5c\u7684\u65b9\u6cd5
 * @name baidu.cookie
 * @namespace
 */
baidu.cookie = baidu.cookie || {
    isValidKey: function (key) {
    	// http://www.w3.org/Protocols/rfc2109/rfc2109
    	// Syntax:  General
		// The two state management headers, Set-Cookie and Cookie, have common
		// syntactic properties involving attribute-value pairs.  The following
		// grammar uses the notation, and tokens DIGIT (decimal digits) and
		// token (informally, a sequence of non-special, non-white space
		// characters) from the HTTP/1.1 specification [RFC 2068] to describe
		// their syntax.
        // av-pairs   = av-pair *(";" av-pair)
        // av-pair    = attr ["=" value] ; optional value
        // attr       = token
        // value      = word
        // word       = token | quoted-string
        
        // http://www.ietf.org/rfc/rfc2068.txt
        // token      = 1*<any CHAR except CTLs or tspecials>
        // CHAR       = <any US-ASCII character (octets 0 - 127)>
        // CTL        = <any US-ASCII control character
        //              (octets 0 - 31) and DEL (127)>
        // tspecials  = "(" | ")" | "<" | ">" | "@"
        //              | "," | ";" | ":" | "\" | <">
        //              | "/" | "[" | "]" | "?" | "="
        //              | "{" | "}" | SP | HT
        // SP         = <US-ASCII SP, space (32)>
        // HT         = <US-ASCII HT, horizontal-tab (9)>
        
        return (new RegExp("^[^\\x00-\\x20\\x7f\\(\\)<>@,;:\\\\\\\"\\[\\]\\?=\\{\\}\\/\\u0080-\\uffff]+\x24")).test(key);
    }
};


baidu.isString = function(e){
    return (typeof e  == "object" && e && e.constructor == String )  || typeof e == "string";
};



/**
 * \u83b7\u53d6\u7684Cookie\u503c\u3002\u6ca1\u6709\u8be5cookie\u65f6\u8fd4\u56denull\u3002
 * 
 * @param {String} key \u8981\u83b7\u53d6\u7684Cookie\u7684key
 * @return {String} \u83b7\u53d6\u7684Cookie\u503c
 */
baidu.cookie.getRaw = function (key) {
    if (!baidu.cookie.isValidKey(key)) {
        return null;
    }
    
    var reg = new RegExp("(^| )" + key + "=([^;]*)(;|\x24)");
    var result = reg.exec(document.cookie);
    if (result) {
        return result[2] || null;
    }
    return null;
};


/**
 * \u83b7\u53d6Cookie\u503c\u3002\u6ca1\u6709\u8be5cookie\u65f6\u8fd4\u56denull\u3002
 * 
 * <pre>
 * \u8be5\u65b9\u6cd5\u4f1a\u5bf9cookie\u503c\u8fdb\u884cdecodeURIComponent\u89e3\u7801
 * \u5982\u679c\u60f3\u83b7\u5f97cookie\u6e90\u5b57\u7b26\u4e32\uff0c\u8bf7\u4f7f\u7528getRaw\u65b9\u6cd5
 * </pre>
 * 
 * @param {String} key \u8981\u83b7\u53d6\u7684Cookie\u7684key
 * @return {String} \u83b7\u53d6\u7684Cookie\u503c
 */
baidu.cookie.get = function (key) {
    var value = baidu.cookie.getRaw(key);
    if (baidu.isString(value)) {
        value = decodeURIComponent(value);
        return value;
    }
    return null;
};
 
/**
 * \u8bbe\u7f6eCookie\u503c
 * 
 * @param {String} key \u8981\u8bbe\u7f6e\u7684Cookie\u7684key
 * @param {String} value \u8bbe\u7f6e\u7684Cookie\u503c
 * @param {Object} options \u8981\u8bbe\u7f6eCookie\u7684\u5176\u4ed6\u53ef\u9009\u53c2\u6570
 * @config {String} path cookie\u8def\u5f84
 * @config {Date|Number} expires cookie\u8fc7\u671f\u65f6\u95f4. \u5982\u679c\u7c7b\u578b\u662f\u6570\u5b57\u7684\u8bdd, \u5355\u4f4d\u662f\u6beb\u79d2
 * @config {String} domain cookie\u57df\u540d
 * @config {String} secure cookie\u662f\u5426\u5b89\u5168\u4f20\u8f93
 */
baidu.cookie.setRaw = function (key, value, options) {
    if (!baidu.cookie.isValidKey(key)) {
        return false;
    }
    
	options = options || {};
	
	var expires;
	switch (typeof(options.expires)) {
    case 'number':
        expires = new Date();
        expires.setTime(expires.getTime() + options.expires);
        break;
    case 'object':
        if (options.expires instanceof Date) {
            expires = options.expires;
        }
	}
	
    document.cookie =
        key + "=" + value
        + (options.path ? "; path=" + options.path : "")
        + (expires ? "; expires=" + expires.toGMTString() : "")
        + (options.domain ? "; domain=" + options.domain : "")
        + (options.secure ? "; secure" : ''); 
    
    return true;
};


/**
 * \u5220\u9664Cookie
 * 
 * @param {String} key \u8981\u5220\u9664Cookie\u7684key
 */
baidu.cookie.remove = function (key) {
    return baidu.cookie.setRaw(key, '', {'expires': new Date(0)});
};
 

/**
 * \u8bbe\u7f6eCookie\u503c
 * 
 * <pre>
 * \u8be5\u65b9\u6cd5\u4f1a\u5bf9cookie\u503c\u8fdb\u884cencodeURIComponent\u7f16\u7801
 * \u4e0d\u5e0c\u671b\u5bf9value\u8fdb\u884c\u7f16\u7801\uff0c\u8bf7\u4f7f\u7528setRaw\u65b9\u6cd5
 * </pre>
 * 
 * @param {String} key \u8981\u8bbe\u7f6e\u7684Cookie\u7684key
 * @param {String} value \u8bbe\u7f6e\u7684Cookie\u503c
 * @param {Object} options \u8981\u8bbe\u7f6eCookie\u7684\u5176\u4ed6\u53ef\u9009\u53c2\u6570
 * @config {String} path cookie\u8def\u5f84
 * @config {Date|Number} expires cookie\u8fc7\u671f\u65f6\u95f4. \u5982\u679c\u7c7b\u578b\u662f\u6570\u5b57\u7684\u8bdd, \u5355\u4f4d\u662f\u6beb\u79d2
 * @config {String} domain cookie\u57df\u540d
 * @config {String} secure cookie\u662f\u5426\u5b89\u5168\u4f20\u8f93
 */
baidu.cookie.set = function (key, value, options) {
    return baidu.cookie.setRaw(key, encodeURIComponent(value), options);
};

