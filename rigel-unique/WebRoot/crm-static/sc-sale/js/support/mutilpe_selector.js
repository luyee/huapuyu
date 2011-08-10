function MutilpeSelector(wrap,maxSelectedNum) {
	this.wrap = wrap;
	this.listeners = [];
	this.maxSelectedNum = maxSelectedNum || Number.MAX_VALUE;
	this.init();
}

MutilpeSelector.layer = null;
MutilpeSelector.mask  = null;
MutilpeSelector.instances = {};
MutilpeSelector.disable = function(id) {
	if( this.instances[id] ) {
		this.instances[id].disable();
	}
}

MutilpeSelector.enable = function(id) {	
	if( this.instances[id] ) {
		this.instances[id].enable();
	}
}

MutilpeSelector.prototype = {
	
	'showLayer' : function() {
		var doc = document,
			db = doc.body,
			dd = doc.documentElement,
			client = doc.compatMode == 'BackCompat' ? body : doc.documentElement,
		    w = Math.max(db.scrollWidth,dd.scrollWidth,client.clientWidth),
		    h = Math.max(db.scrollHeight,dd.scrollHeight,client.clientHeight),
		    lStyle = MutilpeSelector.layer.style,
		    mStyle = MutilpeSelector.mask.style;
		    
			lStyle.width   = mStyle.width  = w + 'px';
			lStyle.height  = mStyle.height = h + 'px';
			lStyle.display = mStyle.display = 'block';
	},

	'closeLayer' : function() {
		MutilpeSelector.layer.style.display = 'none';
		MutilpeSelector.mask.style.display = 'none';
	},

	'disable' : function() {
		this.wrap.className = 'custom-selector disabled-status';
		this.wrap.disabled = true;
		
		this.disableChkbox(true);
	},

	'enable' : function(){
		this.wrap.className = 'custom-selector';
		this.wrap.disabled = false;
		this.disableChkbox(false);
	},
	
	'disableChkbox' : function(disabled) {
		var chkbox = this.wrap.getElementsByTagName('input');
		
		for (var i = 0, len = chkbox.length; i < len; i++) {
			chkbox[i].disabled = disabled;
		}
	},
	
	'show' : function() {
		var style = this.panel.style;
		
		style.visibility = 'hidden';
		style.display = 'block';
		
		//修复ie高度，最高280px;
		if (/*@cc_on !@*/false) {
			
			if(this.panel.offsetHeight >= 280) {
				style.height = '280px';
			}
		}
		style.visibility = 'visible';
		this.showLayer();
	},
	'hide' : function() {
		this.panel.style.display = 'none';
		this.closeLayer();
	},

	'hideAll' : function() {
		for (var i in MutilpeSelector.instances ) {
			MutilpeSelector.instances[i].hide();
		}
	},

	'init' : function() {
		this.panel  = this.wrap.getElementsByTagName('div')[0];
		this.bar    = this.wrap.getElementsByTagName('p')[0];
		this.tipper = this.bar.getElementsByTagName('span')[0];
		
		this.panel.style.display = 'none';
		this.bar.onclick = this._bindEvent(this,this.clickBarHandler);
		this.panel.onclick = this._bindEvent(this,this.clickPanelHandler);
		
		if ( !(MutilpeSelector.layer && MutilpeSelector.mask) ) {
			MutilpeSelector.layer = document.createElement('div');
			MutilpeSelector.layer.className = 'selector-layer';
			document.body.appendChild(MutilpeSelector.layer);

			var mask = document.createElement('iframe');
			mask.className = 'selector-mask';
			document.body.appendChild(mask);

			MutilpeSelector.mask = mask;
		}

		this.setTipper(this.getSelectedItemsInfo()['labels']);
		if(this.wrap.id) {
			MutilpeSelector.instances[this.wrap.id] = this;
		}
	
		
		if(this.wrap.getAttribute('disabled')) {
			this.disable();
		}
		
		var relation = this.wrap.getAttribute('relation');
	
		if (relation) {
			var cr = G(relation);
			if (cr.checked == true) {
				this.enable();
			} else {
				this.disable();
			}
		}
	},
	
	'_bindEvent' : function(obj,fn){
		return function(evt) {
			fn.call(obj,evt);
		}
	},

	'clickBarHandler' : function(evt)  {
		//检查是否禁止
		if (this.wrap.disabled) {
			return false;
		}
		
		if (this.panel.style.display == 'none') {
			this.hideAll();
			this.show();
		} else {
			this.hide();
		}
	},
	
	'getSelectedItemsInfo' : function() {
		var chkboxes = this.panel.getElementsByTagName('input'),
			labels = [],
			values = [];
	
		for (var i = 0, len = chkboxes.length; i < len; i++) {
			var item = chkboxes[i];
			if (item.checked) {
				labels.push(item.parentNode.getElementsByTagName('label')[0].innerHTML);
				values.push(item.value);
			}
		}
		
		return {'labels' : labels,'values' : values};
	},
	
	'clickPanelHandler' : function(evt) {
		var evt = evt || window.event,
			el  = evt.target || evt.srcElement;

		if (el.type == 'checkbox') {
			var info = this.getSelectedItemsInfo();
			
			if (info['values'].length > this.maxSelectedNum) {
				alert("最多选择" + this.maxSelectedNum + "项!");
				el.checked = false;
				return false
			}
			
			this.dispathEvent(info);//同时派发事件
			this.setTipper(info['labels']);
		}
	},
	

	'addListener' : function(fn) {
		this.listeners.push(fn);
	},
	
	'dispathEvent' : function(info) {
		for (var i = 0, len = this.listeners.length; i < len; i++) {
			var fn = this.listeners[i];
			if (typeof fn == 'function') {
				fn.call(null,info);
			}
		}
	},

	'setTipper' : function(labels) {
		this.tipper.innerHTML = (labels.length == 0) ? '请选择' : labels.join(',');
	}
};



/**
 * 自定义多选框初始化
 */
(function(){
	var divs,docElement,mutilpeSelectors = [];
	var limitCount = getSelectedCountLimit();
	
	function getSelectedCountLimit() {
		/*获得参数值*/
		var scripts = document.getElementsByTagName('script'),
			len = scripts.length,
			curScript = scripts[len-1],								/*当前脚本				*/
			src	= curScript.getAttribute('src'),
			eqPos = src.lastIndexOf('=');
		
		return parseInt(src.substring(eqPos+1));
	}
	
	
	
	function init() {
		divs = document.getElementsByTagName('div');
		docElement = document.documentElement;
		
		for (var i = 0, len = divs.length; i < len; i++) {
			var item = divs[i];
			if (item.className == 'custom-selector')  {
				mutilpeSelectors.push(new MutilpeSelector(item,limitCount));
			}
		}

		Event.add(MutilpeSelector.layer ,'click',function(evt) {
			closeAllSelector();
		});
		
	}
			
	function closeAllSelector() {
		for (var i = 0,len = mutilpeSelectors.length; i < len; i++) {
			var item = mutilpeSelectors[i];
			item.hide();
	    }
	}
	
	//Event.add(window,'load',init);
	init();
})();

	