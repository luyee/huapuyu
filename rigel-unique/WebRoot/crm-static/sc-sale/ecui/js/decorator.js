(function () {
    var core = ecui,
	ui = core.ui,
	UI_VSCROLL_CLASS = ui.VScroll.prototype,
	UI_HSCROLL_CLASS = ui.HScroll.prototype;

    UI_VSCROLL_CLASS.$create = function() {
	var me = this,
	block = this.$getSection('Block');
	new ecui.ext.TBDecorator(block, 'vsblockdtr');
	ecui.ui.Control.prototype.$create.call(this);
    };

    UI_HSCROLL_CLASS.$create = function() {
	var block = this.$getSection('Block');
	new ecui.ext.LRDecorator(block, 'hsblockdtr');
	ecui.ui.Control.prototype.$create.call(this);
    };
})()
