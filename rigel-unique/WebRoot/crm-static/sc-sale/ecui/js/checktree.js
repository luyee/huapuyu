(function () {
    
    var core = ecui,
	dom = core.dom,
	ui = core.ui,
	inherits = core.util.inherits,
	$connect = core.$connect,

	UI_TREE = ui.Tree,
	UI_TREE_CLASS = UI_TREE.prototype,
	UI_CHECKBOX_CLASS = ui.Checkbox.prototype;
    

    var UI_CHECKTREE = ui.Checktree = function (el, params) {
	var me = this;
	
	me._oCheckbox = core.create('Checkbox', {
	    'base' : params.base + '-checkbox',
	    'name' : params.name || el.getAttribute('name'),
	    'value' : params.value || (params.value === 0 ? '0' : el.value),
	    'checked' : params.checked
	});
	
	(params.disabled) && (me._oCheckbox.setEnabled(false));
	(params.text) && (el.innerHTML = params.text);

	me._sID = params.id;

	UI_TREE.call(this, el, params);
	me._oCheckbox.setParent(el);
	el.insertBefore(me._oCheckbox.getOuter(), el.firstChild);
	(params.superior) && ($connect(this, this.setSuperior, params.superior));
    };
    
    var UI_CHECKTREE_CLASS = inherits(UI_CHECKTREE, UI_TREE);

    UI_CHECKTREE_CLASS.$createChild = function (params) {
	return core.create('Checktree', params);
    };
    
    UI_CHECKTREE_CLASS.$append = function (item) {
	if(item instanceof UI_CHECKTREE) {
	    item._oCheckbox.setChecked(this._oCheckbox.isChecked()); 
	    item._oCheckbox.setSuperior(this._oCheckbox);
	    if(this.getChildTrees().length == 0) {
		this.alterClass('parent');
	    }
	}
    };

    UI_CHECKTREE_CLASS.$remove = function (item) {
	if(item instanceof UI_CHECKTREE) {
	    item._oCheckbox.setSuperior();
	    if(this.getTrees().length <= 0) {
		this.alterClass('parent', false);
	    }
	}
    };
    
    UI_CHECKTREE_CLASS.add = function (_item, index) {
	var item = UI_TREE_CLASS.add.call(this, _item, index);
	if(typeof _item == 'string') {
	    item.getBase().insertBefore(item._oCheckbox.getOuter(), item.getBase().firstChild);
	}
	return item;
    };

    UI_CHECKTREE_CLASS.setName = function (name) {
	this._oCheckbox.setName(name);
    };

    UI_CHECKTREE_CLASS.setValue = function (value) {
	this._oCheckbox.setValue(value);
    };
    
    UI_CHECKTREE_CLASS.isChecked = function () {
	return this._oCheckbox.isChecked();
    };

    UI_CHECKTREE_CLASS.getCheckedNum = function () {
	var childs = this.getChildTrees();
	var num = this.isChecked() ? 1 : 0;
	for(var i =0, item; item = childs[i]; i++) {
	    num += item.getCheckedNum();
	}
	return num;
    };

    UI_CHECKTREE_CLASS.getCheckedNodes = function () {
        var childs = this.getChildTrees();
        var res = this.isChecked() ? [this] : [];
        for (var i = 0, item; item = childs[i]; i++) {
            res = res.concat(item.getCheckedNodes());
        }
        return res;
    };

    UI_CHECKTREE_CLASS.getID = function () {
        return this._sID;
    };

    UI_CHECKTREE_CLASS.setSuperior = function (superior) {
	superior.add(this);
    };
})()
