var descHash = {
	'true' : 'false',
	'false' : 'true'
};

/*设置表单查询条件*/
function setFormConditions(form) {
	var lineList = G('lineList');
	if(lineList){
		var options = lineList.options;
		for (var i = 0,len = options.length; i < len ; i ++ )
		{
			options[i].selected = true;
		}

		if(options.length == 0){
			var option = document.createElement('option');
			option.selected = true;
			option.value = '-1';
			lineList.appendChild(option);
		}
	}
	
	if( form.elements['id'] ){
		form.elements['id'].value = '-1';
	}
	return true;

}

/*预留接口*/
function queryBy(attr, value) {

	return true;
}

/*折叠面板*/
function toggle(elId) {
       var panel = G(elId);
 
       if( !panel) return false;
 
       panel.style.display = panel.style.display == 'none' ? 'block' : 'none';
       return false;
}

/*设置行业*/
function setItem(type){
	
	type == 'add' ? divertOption(selects[0],selects[1],'add') : divertOption(selects[1],selects[0],'remove');
}

/*移动option*/
function divertOption(srcSelect,targetSelect,type) {

	if (!(srcSelect && targetSelect)) {
		return false;
	}
	
	for(var i = srcSelect.options.length; i > 0; i --){
		var option = srcSelect.options[i-1];	
		if ( option.selected == true ){
			targetSelect.appendChild(option);
		}
	}	
}

/*清除日期*/
function clearDate(evt){
	evt = evt || window.event;
	
	if ( evt.keyCode == 8 ){
		this.value = '';
	}
	
	if (evt.preventDefault) {
		evt.preventDefault();
	} else {
		evt.returnValue = false;
	}

	return false;
}

