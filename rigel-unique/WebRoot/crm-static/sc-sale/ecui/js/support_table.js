/**
 *@file support_table.js
 *@description 支持模块使用ecui实现的列表
 */

/*
 * IE 6 背景图片缓存
 */
try {
	document.execCommand("BackgroundImageCache", false, true);
} catch(e){}

/*
 *滚动条装饰器
 */
ecui.ui.VScroll.prototype.$create = function() {
	var me = this,
	block = this.$getSection('Block');
	new ecui.ext.TBDecorator(block, 'vsblockdtr');
	ecui.ui.Control.prototype.$create.call(this);
};

ecui.ui.HScroll.prototype.$create = function() {
	var block = this.$getSection('Block');
	new ecui.ext.LRDecorator(block, 'hsblockdtr');
	ecui.ui.Control.prototype.$create.call(this);
};


function initEcuiTable() {
	var t = ecui.get('lst'),
	vs = t.$getSection('VScroll'),
	hs = t.$getSection('HScroll'),
	th = t.$getSection('Head'),
	cCount = t._aCol.length - 1,
	col = t.getCol(cCount),
	prevCol = t.getCol(cCount - 1);
	
	//此时重绘,防止火狐下高度异常
	t.paint();
	
	/*
	 * 展开/收起按钮
	 */
	//TODO 判断是否含有操作列
	if (col.getBase().parentNode != prevCol.getBase().parentNode) {
		//收缩按钮
		var ct = ecui.create('Control', {
			type: 'ec-col-toggler',
			parent: t.getBase()
		}),
		//竖条
		h = ecui.create('Control', {
			type: 'ec-v-toggler',
			parent: t.getBase()
		}),
		
		cw = col.getWidth(),
		pw = prevCol.getWidth(),
		hscrValue;

		h.getBody().innerHTML = '<div></div>'
		h.hide();
		h.onclick = function() {
			ct.click();
		};

		ct.onclick = function() {
			toggleStatus(this);
			vs && vs.paint();
			//col.paint();
		};
		ct.onpaint = function(){
			setHbarHeight(h);
			//alert("paint col toggler")
		}

		//根据是否有滚动条设置按钮位置
		if (vs && vs.isShow()) {
			ct.getBase().style.right = vs.getWidth() + "px";
		}
		/*
		 * 设置竖条高度
		 */
		var setHbarHeight = function(hbar) {
			if (hs) {//存在水平滚动条
				hs.setValue(hs.getTotal());
				hscrValue = hs.isShow() ? hs.getHeight() : 0;
			}
			else {
				hscrValue = 0;
			}
			hbar.setSize(undefined, t.getHeight() - th.getHeight() - 2 - hscrValue);
			var style = hbar.getOuter().style;
			if (vs && vs.isShow()) style.right = vs.getWidth() + "px";
			style.bottom = hscrValue + "px";
		}
		
		/*
		 * 点击'收起按钮'的回调函数
		 */
		var toggleStatus = function(ct) {

			//hack:避免第一次隐藏列后对不齐
			prevCol.setSize(pw);

			if (col.isShow()) {
				_autoSize(t, prevCol, pw);
				ct.alterClass('o');
				col.hide();
				setHbarHeight(h);
				h.show();
			} else {
				prevCol.setSize(pw);
				ct.alterClass('o', true);
				//console.log(hscrValue);
				col.show();
				h.hide();
				hs && hs.setValue(hs.getTotal());
			}
			
			col.paint();
		};

		/*
		 * 表格列自适应宽度
		 */
		var _autoSize = function(t, c, ow) {
			var tw = t.getWidth(),
			tcw = t.getBody().offsetWidth;
			if (tw > tcw) {
				c.setSize(tw - tcw + ow);
			}
		};
	}

	/*
	 * 垂直滚动条高度
	 */
	if (vs) {
		_setVSHeight = function(vs) {
			vs.setSize(undefined, vs.getHeight() - th.getHeight());
			vs.getBase().style.top = "";
			vs.getBase().style.bottom = vs.getWidth() + "px";
		}
		vs.onpaint = function() {
			this.$paint();
			_setVSHeight(this);
			document.body.className = document.body.className;
			return false;
		}
		_setVSHeight(vs);
	}

	/*
	 *	排序-记录水平位置
	 */
	var ectable_xpos = baidu.cookie.getRaw('ectable_xpos');
	if (ectable_xpos) {
		hs.setValue(ectable_xpos);
		baidu.cookie.remove('ectable_xpos');
	}
	
	/*
	 * 排序
	 */
	var old = null;
	var toggleSort = function(myCol) {
		var _sort = function(col, order/* true为 sort-up*/) {
			var _order = order || false;
			if (!_order) {
				//排序方法here
				col.setClass('sort-down');
				col.sorted = 'down';
			} else {
				//排序方法here
				col.setClass('sort-up');
				col.sorted = 'up';
			}
		}
		return function() {
			if (!old) {
				_sort(this);
			} else {
				if (old != this) {
					ecui.dom.removeClass(old.getBase(), 'sort-down');
					ecui.dom.removeClass(old.getBase(), 'sort-up');
					old.setClass('sort');
				}
				this.sorted == 'down' ? _sort(this) : _sort(this, true);
			}
			_defaultSort();
			old = myCol;
		}
	};
	
	var defaultSort = function() {
		window.sortClick(this.getBase().getAttribute('sort'));
		baidu.cookie.setRaw('ectable_xpos',hs.getValue());
	};
	
	for (var i = 0, cCount = t._aCol.length; i < cCount; i++) { 
		var col = t.getCol(i);
		if (col.getBase().className.match(/\s*sort\s*/)) {
			//ecui.dom.create('<div></div>', col.getBase());
			col.onclick = defaultSort;
		}
	}
	
	//hack for ie 8	
	document.body.className = document.body.className;
	
	/*
	 * checkbox处理
	 */
}
var ecTable = {
	/**
	 *找出table中被选中条目
	 *@param {EcTable} table
	 *@return {Array} 被选中行
	 */
	getChecked: function (table){
		var col = table.getCol(0), 
			l = ecui.getChildren(col)[0].getInferiors(), 
			ret = [];
		for(var i = 0, len = l.length; i<len; i++){
		    if(l[i].isChecked()){
		        ret.push(l[i].getParent());
		    }
		 }
		return ret;
	},
	/**
	 *获得table中有选中条目个数
	 *@param {EcTable} table
	 *@return {Number} 选中条目个数
	 */
	getCheckedCount: function(table){
		return ecTable.getChecked(table).length;
	}
};

ecui.dom.ready(initEcuiTable);
