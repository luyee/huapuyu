/* Copyright (c) 2010 Baidu */
/**
 * suggestion 
 * @namespace baidu.effect
 * @author  berg
 * @date 2010-02-03 
 **/

 /*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu.js
 * author: allstar, erik
 * version: 1.1.0
 * date: 2009/12/2
 */

/**
 * 声明baidu包
 */
var baidu = baidu || {version: "1-1-1", guid: "$BAIDU$"};

/**
 * meizz 2010/02/04
 * 顶级域名 baidu 有可能被闭包劫持，而需要页面级唯一信息时需要用到下面这个对象
 */

window[baidu.guid] = window[baidu.guid] || {};




baidu.ui = baidu.ui || {};


baidu.suggestion = baidu.ui.suggestion = baidu.ui.suggestion || {} ;

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/event/preventDefault.js
 * author: erik
 * version: 1.1.0
 * date: 2009/11/23
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/event.js
 * author: erik
 * version: 1.1.0
 * date: 2009/12/02
 */



/**
 * 声明baidu.event包
 */
baidu.event = baidu.event || {};


/**
 * 阻止事件的默认行为
 * 
 * @param {Event} event 事件对象
 */
baidu.event.preventDefault = function (event) {
   if (event.preventDefault) {
       event.preventDefault();
   } else {
       event.returnValue = false;
   }
};

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/g.js
 * author: allstar, erik
 * version: 1.1.0
 * date: 2009/11/17
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom.js
 * author: allstar, erik
 * version: 1.1.0
 * date: 2009/12/02
 */



/**
 * 声明baidu.dom包
 */
baidu.dom = baidu.dom || {};


/**
 * 从文档中获取指定的DOM元素
 * 
 * @param {string|HTMLElement} id 元素的id或DOM元素
 * @return {HTMLElement} DOM元素，如果不存在，返回null，如果参数不合法，直接返回参数
 */
baidu.dom.g = function (id) {
    if ('string' == typeof id || id instanceof String) {
        return document.getElementById(id);
    } else if (id && id.nodeName && (id.nodeType == 1 || id.nodeType == 9)) {
        return id;
    }
    return null;
};

// 声明快捷方法
baidu.g = baidu.G = baidu.dom.g;

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/event/on.js
 * author: erik
 * version: 1.1.0
 * date: 2009/12/16
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/event/_listeners.js
 * author: erik
 * version: 1.1.0
 * date: 2009/11/23
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/event/_unload.js
 * author: erik, berg
 * version: 1.1.0
 * date: 2009/12/16
 */



/**
 * 卸载所有事件监听器
 * @private
 */
baidu.event._unload = function () {
    var lis = baidu.event._listeners,
        len = lis.length,
        standard = !!window.removeEventListener,
        item, el;
        
    while (len--) {
        item = lis[len];
        //20100409 berg: 不解除unload的绑定，保证用户的事件一定会被执行
        if(item[1] == 'unload'){
            continue;
        }
        el = item[0];
        if (el.removeEventListener) {
            el.removeEventListener(item[1], item[3], false);
        } else if (el.detachEvent){
            el.detachEvent('on' + item[1], item[3]);
        }
    }
    
    if (standard) {
        window.removeEventListener('unload', baidu.event._unload, false);
    } else {
        window.detachEvent('onunload', baidu.event._unload);
    }
};

// 在页面卸载的时候，将所有事件监听器移除
if (window.attachEvent) {
    window.attachEvent('onunload', baidu.event._unload);
} else {
    window.addEventListener('unload', baidu.event._unload, false);
}


/**
 * 事件监听器的存储表
 * @private
 */
baidu.event._listeners = baidu.event._listeners || [];


/**
 * 为目标元素添加事件监听器
 * 
 * @param {HTMLElement|string|window} element  目标元素或目标元素id
 * @param {string}                    type     事件类型
 * @param {Function}                  listener 事件监听器
 * @return {HTMLElement} 目标元素
 */
baidu.event.on = function (element, type, listener) {
    type = type.replace(/^on/i, '');
    if ('string' == typeof element) {
        element = baidu.dom.g(element);
    }

    var fn = function (ev) {
        // 这里不支持EventArgument
        // 原因是跨frame的时间挂载
        listener.call(element, ev);
    },
    lis = baidu.event._listeners;
    
    // 将监听器存储到数组中
    lis[lis.length] = [element, type, listener, fn];
    
    // 事件监听器挂载
    if (element.addEventListener) {
        element.addEventListener(type, fn, false);
    } else if (element.attachEvent) {
        element.attachEvent('on' + type, fn);
    } 
    
    return element;
};

// 声明快捷方法
baidu.on = baidu.event.on;


//监听input
//listen : start, pick_word, mousedown_item, confirm_item
//call : start, need_data, all_clear, key_enter, key_up, key_down, key_tab, key_esc,

baidu.ui.suggestion._InputWatcher = function(Event, inputElement, view){
    var Input = this;

    //请求计时器，轮询计时器
    var requestTimer,
        circleTimer = 0;

    //=====
    //每次轮询获得的value
    var oldValue = '';

    //pick选择之外的oldValue
    var defaultIptValue = "";

    //一打开页面就有的input value
    var keyValue = '';

    //使用pick方法上框的input value
    var pickValue;
    //=====

    var mousedownView = false,
        stopCircleTemporary = false,
        started = false;


    inputElement.setAttribute('autocomplete', 'off');

    baidu.on(inputElement, 'keydown', function (e){
		//第一次按键盘启动suggestion
		if(!started){
			Event.call('start');
			started = true;
		}
		
		e = e || window.event;

        var type;
        switch(e.keyCode){
            case 9:
                type="tab";
                break;
            case 27:
                type="esc";
                break;
            case 13: //按回车的表单提交事件
                type="enter";
                baidu.event.preventDefault(e);
                break;
            case 38://在FF下 按上会出现光标左移的现象
                type="up";
                baidu.event.preventDefault(e);
                break;
            case 40:
                type="down";
                break;
        }
        if(type){
            Event.call("key_"+type, defaultIptValue);
        }
	});

    baidu.on(inputElement, 'mousedown', function (){
		//第一次按鼠标启动suggestion
		if(!started){
			Event.call('start');
			started = true;
        }
        if(inputElement.value)
            Event.call('need_data', inputElement.value);
	});

    baidu.on(inputElement, 'beforedeactivate', function(){
		if(mousedownView){
            //和M$输入法打架的问题
            //在失去焦点的时候，如果是点击在了suggestion上面，那就取消其默认动作(默认动作会把字上屏)
			window.event.cancelBubble = true;
			window.event.returnValue = false;
		}
	});

    //
    
    Event.listen("start", function() {
        keyValue = inputElement.value;
		circleTimer = setInterval(function (){
            if(stopCircleTemporary){
                return ;
            }

            if(baidu.G(inputElement) == null){
                suggestion.dispose();
            }

            var nowValue = inputElement.value;
            if(
                nowValue == oldValue && 
                nowValue != '' && 
                nowValue != keyValue && 
                nowValue != pickValue
              ){
                if(requestTimer == 0){
                    requestTimer = setTimeout(function(){
                        Event.call('need_data', nowValue);
                    }, 100);
                }
            }else{
                clearTimeout(requestTimer);
                requestTimer = 0;
                if(nowValue == '' && oldValue != ''){
                    pickValue = "";
                    Event.call("all_clear");
                }
                oldValue = nowValue;
                if(nowValue != pickValue){
                    defaultIptValue  = nowValue;
                }
                if(keyValue != inputElement.value){
                    keyValue = '';
                }
            }
        }, 10);
    });

    Event.listen("pick_word", function(value) {
        //firefox2.0和搜狗输入法的冲突
        if(mousedownView)
            inputElement.blur();
        inputElement.value = pickValue = value;
        if(mousedownView)
            inputElement.focus();
    });

    Event.listen("mousedown_item", function(e) {
        mousedownView = true;
        //chrome和搜狗输入法冲突的问题
        //在chrome下面，输入到一半的字会进框，如果这个时候点击一下suggestion，就会清空里面的东西，导致suggestion重新被刷新
        stopCircleTemporary = true;
        setTimeout(function(){
            stopCircleTemporary = false;
			mousedownView = false;
        },500);
    });

    Event.listen("confirm_item", function(oq, rsp, wd, html) {
        stopCircleTemporary = false;
        //更新oldValue，否则circle的时候会再次出现suggestion
        defaultIptValue = oldValue = wd;
    });
	
    return {
        "getValue" : function(){
            return inputElement.value;
        },
        "dispose":function(){
            clearInterval(circleTimer);
        }
    };
};

(function() {
    var Central = {};
    
    var centralService = function(target) {
        var listeners = {};

        target.listen = function(command, handler) {
            listeners[command] = listeners[command] || [];
            var i = 0;
            while (i < listeners[command].length && listeners[command][i] != handler) {
                i++;
            }
            if (i == listeners[command].length) {
                listeners[command].push(handler);
            }
            return target;
        };

        target.call = function(command) {
            //console.log(command);
            //console.log(Array.prototype.slice.call(arguments, 1));
            if (listeners[command]) {
                for (var i = 0; i < listeners[command].length; i++) {
                    //try {
                        listeners[command][i].apply(this, Array.prototype.slice.call(arguments, 1));
                    //} catch (error) {}
                }
            }
            return target;
        };
    };
    
    Central.extend = function(target) {
        new centralService(target);
        return target;
    };
    
    Central.extend(Central);
    baidu.suggestion._Central = Central;
})();

//这里可以扩展做本地缓存
//listen : response_data, need_data
//call : data_ready
baidu.ui.suggestion._Data = function(Event, getData){
    var Data = this,
	    dataObj = {};

    Event.listen("response_data", function(word, data) {
		dataObj[word] = data;
        Event.call("data_ready", word, data); 
    });

    Event.listen("need_data", function(word) {
		if(typeof dataObj[word] == 'undefined'){
            getData(word);
		}else{
            Event.call("data_ready", word, dataObj[word]); 
		}
    });
    Event.listen("clear_data", function (){
	dataObj = {};
    });
    return {};
};


/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/getStyle.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/18
 */


/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/_styleFixer.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/17
 */



/**
 * 提供给setStyle与getStyle使用
 */
baidu.dom._styleFixer = baidu.dom._styleFixer || {};

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/_styleFilter/filter.js
 * author: allstar, erik
 * version: 1.1.0
 * date: 2009/12/02
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/_styleFilters.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/12/02
 */



/**
 * 提供给setStyle与getStyle使用
 */
baidu.dom._styleFilter = baidu.dom._styleFilter || [];



/**
 * 为获取和设置样式的过滤器
 * @private
 */
baidu.dom._styleFilter.filter = function (key, value, method) {
    for (var i = 0, filters = baidu.dom._styleFilter, filter; filter = filters[i]; i++) {
        if (filter = filter[method]) {
            value = filter(key, value);
        }
    }

    return value;
};

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/string/toCamelCase.js
 * author: erik
 * version: 1.1.0
 * date: 2009/11/30
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/string.js
 * author: erik
 * version: 1.1.0
 * date: 2009/11/15
 */



/**
 * 声明baidu.string包
 */
baidu.string = baidu.string || {};


/**
 * 将目标字符串进行驼峰化处理
 * 
 * @param {string} source 目标字符串
 * @return {string} 驼峰化处理后的字符串
 */
baidu.string.toCamelCase = function (source) {
    return String(source).replace(/[-_]\D/g, function (match) {
                return match.charAt(1).toUpperCase();
            });
};


/**
 * 获取DOM元素的样式值
 * 
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @param {string}             key     要获取的样式名
 * @return {string} 要获取的样式值
 */
baidu.dom.getStyle = function (element, key) {
    var dom = baidu.dom;

    element = dom.g(element);
    key = baidu.string.toCamelCase(key);

    var value = element.style[key];
    
    // 在取不到值的时候，用fixer进行修正
    if (!value) {
        var fixer = dom._styleFixer[key],
        	/* 在IE下，Element没有在文档树上时，没有currentStyle属性 */
    	    style = element.currentStyle || (baidu.browser.ie ? element.style : getComputedStyle(element, null));
            
        if ('string' == typeof fixer) {
            value = style[fixer];
        } else if (fixer && fixer.get) {
            value = fixer.get(element, style);
        } else {
            value = style[key];
        }
    }
    
    /* 检查结果过滤器 */
    if (fixer = dom._styleFilter) {
        value = fixer.filter(key, value, 'get');
    }

    return value;
};

// 声明快捷方法
baidu.getStyle = baidu.dom.getStyle;

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/object/extend.js
 * author: erik
 * version: 1.1.0
 * date: 2009/11/30
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/object.js
 * author: erik
 * version: 1.1.0
 * date: 2009/11/15
 */



/**
 * 声明baidu.object包
 */
baidu.object = baidu.object || {};


/**
 * 将源对象的所有属性拷贝到目标对象中
 * 
 * @param {Object} target 目标对象
 * @param {Object} source 源对象
 * @return {Object} 目标对象
 */
baidu.object.extend = function (target, source) {
    for (var p in source) {
        if (source.hasOwnProperty(p)) {
            target[p] = source[p];
        }
    }
    
    return target;
};

// 声明快捷方法
baidu.extend = baidu.object.extend;

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/browser/ie.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/23
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/browser.js
 * author: allstar, erik
 * version: 1.1.0
 * date: 2009/12/02
 */



/**
 * 声明baidu.browser包
 */
baidu.browser = baidu.browser || {};


/**
 * 判断是否为ie浏览器
 */
if (/msie (\d+\.\d)/i.test(navigator.userAgent)) {
    baidu.ie = baidu.browser.ie = parseFloat(RegExp['\x241']);
}


/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/getPosition.js
 * author: --
 * version: 1.0.0
 * date: 2009/--/--
 */

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/dom/getDocument.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/17
 */



/**
 * 获取目标元素所属的document对象
 *
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @return {HTMLDocument} element所属的document对象
 */
baidu.dom.getDocument = function (element) {
    element = baidu.dom.g(element);
    return element.nodeType == 9 ? element : element.ownerDocument || element.document;
};




/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/browser/opera.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/23
 */



/**
 * 判断是否为opera浏览器
 */
if (/opera\/(\d+\.\d)/i.test(navigator.userAgent)) {
    baidu.browser.opera = parseFloat(RegExp['\x241']);
}

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/browser/isWebkit.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/23
 */



/**
 * 判断是否为isWebkit
 */
baidu.browser.isWebkit = /webkit/i.test(navigator.userAgent);

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/browser/isGecko.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/23
 */



/**
 * 判断是否为isGecko
 */
baidu.browser.isGecko = /gecko/i.test(navigator.userAgent) && !/like gecko/i.test(navigator.userAgent);

/*
 * Tangram
 * Copyright 2009 Baidu Inc. All rights reserved.
 * 
 * path: baidu/browser/isStrict.js
 * author: allstar
 * version: 1.1.0
 * date: 2009/11/23
 */



/**
 * 判断是否为标准模式
 */
baidu.browser.isStrict = document.compatMode == "CSS1Compat";


/*
 * 获取目标元素元素相对于整个文档左上角的位置
 *
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @return {Object} 
 *   {
 *       left:xx,//{integer} 页面距离页面左上角的水平偏移量
 *       top:xx //{integer} 页面距离页面坐上角的垂直偏移量
 *   }
 */
baidu.dom.getPosition = function (element) {
    var doc = baidu.dom.getDocument(element), 
        browser = baidu.browser;

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
    } else if (doc.getBoxObjectFor && !BUGGY_GECKO_BOX_OBJECT/* && !goog.style.BUGGY_CAMINO_*/){ // gecko
        // Gecko ignores the scroll values for ancestors, up to 1.9.  See:
        // https://bugzilla.mozilla.org/show_bug.cgi?id=328881 and
        // https://bugzilla.mozilla.org/show_bug.cgi?id=330619

        box = doc.getBoxObjectFor(element);
        var vpBox = doc.getBoxObjectFor(viewportElement);
        pos.left = box.screenX - vpBox.screenX;
        pos.top  = box.screenY - vpBox.screenY;
    } else { // safari/opera
        parent = element;

        do {
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
        } while (parent && parent != element);

        // opera & (safari absolute) incorrectly account for body offsetTop
        if(browser.opera > 0 || (browser.isWebkit > 0 && baidu.dom.getStyle(element, 'position') == 'absolute')){
            pos.top  -= doc.body.offsetTop;
        }

        // accumulate the scroll positions for everything but the body element
        parent = element.offsetParent;
        while (parent && parent != doc.body) {
            pos.left -= parent.scrollLeft;
            // see https://bugs.opera.com/show_bug.cgi?id=249965
            if (!b.opera || parent.tagName != 'TR') {
                pos.top -= parent.scrollTop;
            }
            parent = parent.offsetParent;
        }
    }

    return pos;
};









//渲染view
//listen : start, all_clear, data_ready, key_enter, key_up, key_down, key_tab, key_esc,
//call : confirm_item, mousedown_item, pick_word, need_data

baidu.ui.suggestion._Div = function(Event, /*READ FUCKING ONLY*/elementWatched, watcher, draw, options){
    var Div = this;

    var suggestion;
	var dataArray;
    var dataHtmlArray;
	var oq;
    var class_prefix = options['class_prefix'];
    var elementArray;


    //初始化

    //创建div和iframe
    var div = document.createElement('DIV');
    div.id = class_prefix + new Date().getTime();
    div.className = class_prefix +"wpr";
    div.style.display = 'none';

    var shadeIframe = document.createElement('IFRAME');
    shadeIframe.src = "javascript:void(0)";
    shadeIframe.className = class_prefix +"sd";
    shadeIframe.style.display = 'none';
    shadeIframe.style.position = 'absolute';
    shadeIframe.style.borderWidth  = "0px" ;

    //插入dom树
    if(options.apd_body){
        document.body.appendChild(div);
    }else{
        elementWatched.parentNode.appendChild(div);
    }
    div.parentNode.insertBefore(shadeIframe, div);


    Event.listen("start", function(){
        //监听外面的鼠标点击
        baidu.on(document, 'mousedown', function (e){
            e = e || window.event;
            var elm = e.target || e.srcElement;
            if(elm == elementWatched)
                return;
            while (elm = elm.parentNode){
                if (elm == div){
                    return;
                }
            }
            hide();
        });

        //窗口失去焦点就hide
        baidu.on(window, 'blur', hide);
    });

    Event.listen("key_enter", function (){
		var sel = getClassnameMO(),
            wd = sel[0] == -1 ? oq : sel[1];

        options.onconfirm(
            watcher.getValue(),
            sel[0],
            wd,
            sel[2]
        );
        hide();
	});


    //弹出层关闭时，按上下键显示弹出层，
    //弹出层显示时，按上下键选择关键词。
    function keyUpDown(up, defaultIptValue){
        if(div.style.display == 'none'){
            Event.call('need_data', watcher.getValue());
            return ;
        }
        var selected = getClassnameMO()[0];
        clearHighlight();
        if(up){
            //最上面再按上
            if(selected == 0){
                pick(defaultIptValue);
                selected--;
                return ;
            }
            if(selected == -1)
                selected = dataArray.length;
            selected--;
        }else{
            //最下面再按下
            if(selected == dataArray.length - 1){
                pick(defaultIptValue);
                selected = -1;
                return ;
            } 
            selected++;
        }
        highlight(selected);
        triggerHighlight();
        var valueBeforePick = watcher.getValue();
        pick(selected);
		var sel = getClassnameMO();
        options.onpick(valueBeforePick, sel[0], sel[1], sel[2]);
    }

    Event.listen("key_up", function(defaultIptValue){keyUpDown(1, defaultIptValue)});
    Event.listen("key_down", function(defaultIptValue){keyUpDown(0, defaultIptValue)});

    Event.listen("key_tab", hide);
    Event.listen("key_esc", hide);
    Event.listen("all_clear", hide);

    Event.listen("data_ready", function(word, data){
        oq = data;
        dataArray = [];
        dataHtmlArray = [];
        for(var i=0,l=data.length; i<l; i++){
            if(typeof data[i].input != 'undefined'){
                dataArray[i] = data[i].input;
                dataHtmlArray[i] = data[i].selection;
            }else{
                dataHtmlArray[i] = dataArray[i] = data[i];
            }
        }
        if(dataArray.length != 0){
            elementArray = draw(div, dataHtmlArray, Div);
            for(var i=0, l=elementArray.length; i<l; i++){
                baidu.on(elementArray[i], 'mouseover', function (){
                                                            clearHighlight();
                                                            this.className = class_prefix+'mo';
                                                            triggerHighlight();
                                                        });
                baidu.on(elementArray[i], 'mouseout', clearHighlight);
                baidu.on(elementArray[i], 'mousedown', function(e){
                                                            Event.call('mousedown_item');
                                                            if(!baidu.ie){
                                                                e.stopPropagation();
                                                                e.preventDefault();
                                                                return false;
                                                            }
                                                        });
                baidu.on(elementArray[i], 'click', click(i));
            }
        }else{
            hide();
        }
    });

	//清除suggestion中全部tr的蓝色背景样式
	function clearHighlight(){
		for(var i=0; i<elementArray.length; i++){
			elementArray[i].className = class_prefix+'ml';
		}
	}

	//获得当前鼠标悬停的词
	function getClassnameMO(){
		if(elementArray && div.style.display != 'none'){
			for(var i=0; i<elementArray.length; i++){
				if(elementArray[i].className == class_prefix+'mo')
					return [i, dataArray[i], dataHtmlArray[i]];
			}
		}
		return [-1, ''];
	}


    //触发onhighlight
    function triggerHighlight(){
		var sel = getClassnameMO();
        options.onhighlight(watcher.getValue(), sel[0], sel[1], sel[2]);
    }

    function highlight(index){
		clearHighlight();
        elementArray[index].className = class_prefix+'mo';
    }

    //pick某个word
    function pick(index){
        var word = dataArray && typeof index == 'number' && typeof dataArray[index] != 'undefined' ?  dataArray[index] : index;
        Event.call("pick_word", word);
    }

	//关闭suggestion
	function hide(){
        if(div.style.display == 'none')
            return null;
        shadeIframe.style.display = div.style.display = 'none';
        options.onhide();
	}
	
	
	function click(i){
		var j = i;
		return function(){
			Event.call(
                "confirm_item",
                watcher.getValue(),
                dataArray[j],
                j,
                dataHtmlArray[j]
            );
            var valueBeforePick = watcher.getValue(); 

            pick(dataArray[j]);
            options.onpick(
                valueBeforePick,
                j,
                dataArray[j],
                dataHtmlArray[j]
            );

            options.onconfirm(
                watcher.getValue(),
                j,
                dataArray[j],
                dataHtmlArray[j]
            );
            hide();
		}
	};
	

    return {
        "element" : div,
        "shade" : shadeIframe,
        "pick" : pick,
        "highlight" : highlight,
        "hide" : hide,
        "dispose": function(){
            div.parentNode.removeChild(div);
        }
    };
};


//call response_data
baidu.ui.suggestion._Suggestion = function(inputElement, options){ 
    var suggestion = this,
        MessageDispatcher = baidu.ui.suggestion._MessageDispatcher;

    suggestion.options = {
        "onpick" : function(){},
        "onconfirm"  : function(){},
        "onhighlight"  : function(){},
        "onhide"  : function(){},

        "view" : null,
        "getData" : false,
        "prepend_html" : "",
        "append_html" : "",
        "class_prefix" : "tangram_sug_",
        "apd_body" : false
    }

    baidu.extend(suggestion.options, options);

    if(!(inputElement = baidu.G(inputElement)))
        return null;

    suggestion.inputElement = inputElement;

    if(inputElement.id){
        suggestion.options._inputId = inputElement.id;
    }else{
        inputElement.id = suggestion.options._inputId = suggestion.options.class_prefix + 'ipt' + new Date().getTime();
    }

    suggestion._adjustPos = function(onlyAdjustShown){
        var div = view.element,
            shadeIframe = view.shade,
            doc = document,
            client = doc.compatMode == 'BackCompat'  ? doc.body : doc.documentElement,
            cH = client.clientHeight,
            cW = client.clientWidth;
        if(div.style.display == 'none' && onlyAdjustShown){
            return ;
        }

        //该死的傲游等
        /*if(baidu.browser.ie){*/
        /*ieShellZoomRate = (function (){*/
        /*var div = document.createElement("DIV");*/
        /*div.style.position = "absolute";*/
        /*div.style.top = "100px";*/
        /*document.body.appendChild(div);*/
        /*var divBox = (div.getBoundingClientRect()).top;*/
        /*if(!baidu.browser.isStrict){*/
        /*divBox -= 2;*/
        /*}*/
        /*document.body.removeChild(div);*/
        /*return parseFloat(divBox/100);*/
        /*})();*/
        /*}*/

        var inputPosition = baidu.dom.getPosition(inputElement),
            pos = [
                    (inputPosition.top + inputElement.offsetHeight - 1),
                    inputPosition.left,
                    (inputElement.offsetWidth)
                ];
        pos =  typeof suggestion.options.view == "function" ? suggestion.options.view(pos) : pos;
        div.style.display = shadeIframe.style.display = 'block';
        shadeIframe.style.top = pos[0] + "px";
        shadeIframe.style.left = pos[1] + "px";
        shadeIframe.style.width = pos[2] + "px";

        //计算margin对于top left的偏差
        var mt = parseFloat(baidu.getStyle(div, "marginTop")) || 0;
        var ml = parseFloat(baidu.getStyle(div, "marginLeft")) || 0;
        div.style.top = (pos[0] - mt) + "px";
        div.style.left = (pos[1] - ml) + "px";


        if(baidu.ie && document.compatMode == 'BackCompat'){
            div.style.width = pos[2] + 'px';
        } else {
            var bl = parseFloat(baidu.getStyle(div, "borderLeftWidth")) || 0;
            var br = parseFloat(baidu.getStyle(div, "borderRightWidth")) || 0;
            var mr = parseFloat(baidu.getStyle(div, "marginRight")) || 0;

            div.style.width = (pos[2] - bl - br - ml - mr ) + "px";
        }
        shadeIframe.style.height = div.offsetHeight + 'px';

        //如果调整改变了滚动条 （在页面被滚动缩放的时候可能出现）
        if(cH != client.clientHeight || cW != client.clientWidth){
            suggestion._adjustPos();
        }
    };

	suggestion._draw = function(div, data){
        var elementArray = [];
		var tb = document.createElement("TABLE");
        tb.cellPadding = 2;
        tb.cellSpacing = 0;
		//创建tbody
		var tbd = document.createElement('TBODY');
		tb.appendChild(tbd);

		//循环画出suggestion的表格
		for(var i=0, l=data.length; i<l; i++){
			var tr = tbd.insertRow(-1);
            elementArray.push(tr);
			var td = tr.insertCell(-1);
			td.innerHTML = data[i];
		}

		var prependDiv = document.createElement("DIV");
        prependDiv.className = suggestion.options.class_prefix+'pre';
        prependDiv.innerHTML = suggestion.options.prepend_html;

		var appendDiv = document.createElement("DIV");
        appendDiv.className = suggestion.options.class_prefix+'app';
        appendDiv.innerHTML = suggestion.options.append_html;

		div.innerHTML = '';
		div.appendChild(prependDiv);
		div.appendChild(tb);
		div.appendChild(appendDiv);
		
        suggestion._adjustPos();
        return elementArray;
	};

    var Event = baidu.suggestion._Central.extend(suggestion),
        Data  =  new baidu.ui.suggestion._Data(Event, suggestion.options.getData),
        watcher = new baidu.ui.suggestion._InputWatcher(Event, inputElement, view),
        view = new baidu.ui.suggestion._Div(Event, inputElement, watcher, suggestion._draw, suggestion.options);


    Event.listen("start", function(){
        //开始监听搜索框与suggestion弹出层的宽度是否一致。
        setInterval(function (){
            var div = view.element;
            if(div.offsetWidth !=0 && inputElement.offsetWidth != div.offsetWidth){
                suggestion._adjustPos();
            }
        }, 100);
        baidu.on(window, "resize", function(){suggestion._adjustPos(true);});
    });

    return {
        "pick" : view.pick,
        "hide" : view.hide,
        "highlight" : view.highlight,

        "getElement": function(){
            return view.element;
        },
        "getData" : suggestion.options.getData,
        "giveData" : function(word, data){
            Event.call("response_data", word, data); 
        },
        "dispose" : function(){
            view.dispose();
            watcher.dispose();
        },
	"clearCache" : function () {
	    Event.call("clear_data");
	}
    };
};



 


baidu.ui.suggestion.create = function(inputElement, options){ 
    return (new baidu.ui.suggestion._Suggestion(inputElement, options));
};


