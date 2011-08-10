function getEvent(event)
{
	if (!event)
	{
		event = window.event;
		event.pageX = document.documentElement.scrollLeft + event.clientX - document.body.clientLeft;
		event.pageY = document.documentElement.scrollTop + event.clientY - document.body.clientTop;
		event.target = event.srcElement;
		event.which = event.keyCode;
	}
	return event;
}

var exTip = {
    
    _TIPCLASS : 'exTip',
    
    _SOURCECLASS : 'exTipSource',
    
    _STIMELIMIT : 500,

    _HTIMELIMIT : 200,

    stime : null,

    htime : null,
    
    tip : null,
    
    init : function (el) {
	var spans = el.getElementsByTagName('span'),
	    span, that = this;
	for (var i = 0; span = spans[i]; i++) {
	    if (span.className == this._SOURCECLASS) {
		this.attachTip(span);
	    }
	}

	span = this.tip = G(document.createElement('div'));
	span.className = this._TIPCLASS;
	span.hide();
	span.onmouseover = function () {
	    clearTimeout(that.htime);
	    that.htime = null;
	}
	span.onmouseout = function () {
	    that.hide();
	}
	document.body.appendChild(span);
    },

    attachTip : function (el) {
	var html = el.innerHTML.trim(),
	    el = G(el.parentNode),
	    that = this, time = that.stime;
	el.onmouseover = el.onmousemove = function (event) {
	    event = getEvent(event);
	    if (this.x == event.pageX && this.y == event.pageY) {
		return;
	    }
	    this.x = event.pageX;
	    this.y = event.pageY;
	    that.hide();
	    that.show({'left' : event.pageX, 'top' : event.pageY}, html);
	};

	el.onmouseout = function () {
	   (that.stime) && (clearTimeout(that.stime));
	   that.stime = null;
	   this.x = this.y = null;
	   that.hide();
	}
    },

    show : function (pos, html) {
	var tip = this.tip;
	
	(this.stime) && (clearTimeout(this.stime));
	this.stime = setTimeout(function () {
	    tip.show();
	    if (pos.top + tip.offsetHeight + 20 > document.documentElement.clientHeight + document.documentElement.scrollTop) {
		tip.style.top = (pos.top - tip.offsetHeight) + 'px';
	    }
	    else {
		tip.style.top = (pos.top + 20) + 'px';
	    }
	    if (pos.left + tip.offsetWidth > document.documentElement.clientWidth + document.documentElement.scrollLeft) {
		tip.style.left = (pos.left - tip.offsetWidth) + 'px';
	    }
	    else {
		tip.style.left = pos.left + 'px';
	    }
	    tip.innerHTML = html;
	}, this._STIMELIMIT);
    },

    hide : function () {
	var that = this;

	if (!that.htime) {
	    that.htime = setTimeout(function () {
		that.tip.hide();
		that.htime = null;
	    }, that._HTIMELIMIT);
	}
    }
}
