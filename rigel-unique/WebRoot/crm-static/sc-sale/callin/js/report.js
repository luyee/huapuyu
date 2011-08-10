/**
 *@author:liguoxin@baidu.com
 *@function:呼入模块功能函数
 */

var AjaxLoader = {
    'get' : function(url,callback,errorHandler) {
	var xhr = new Ajax(callback,errorHandler || function(){} );
		
	xhr.send(url);
    }
};

/*双向移动select选项*/
function switchOptions(from, to) {
    if ( from.length == undefined || to.length == undefined ) {
	return false;
    }

    for (var i = from.length - 1; i >= 0; i --) {
	var option = from[i];

	if (option.selected == true) {
	    to.appendChild(option);
	}
    }

    return false;
}

function setPlayer(result) {
	var result = eval('(' + result + ')');
	var win;
	/*if(result.error.trim() != '') {
		G('audioPlayer').innerHTML = '<span style="color:red">' + result.error +'</span>';
	} else {
		G('audioPlayer').innerHTML = '<EMBED id="EMBED" SRC="'+ result.src+'" controls="console" height="40" width="200"></EMBED>';
	}

	G('audioPlayerWrapper').show();*/
	if(result.error.trim() != '') {
	    alert(result.error);
	}
	else {
	    win = window.open('/ht-callin/playAudio.html?'+ result.src,'','height=100, width=250, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	}
}
 
/*加载音频*/
function loadAudio(link){
	var href = link.getAttribute('href');

	AjaxLoader.get(href,setPlayer);

	return false;
}

/*==============================批处理下发二次确认==============================*/
/**获得表单中选中的checkbox值列表，
 * @public
 * @param {HtmlElement}表单元素
 * @param {String} checkbox name
 * @return {Array} 条目列表
 **/
function getSelectedItem(form,chkboxname){
	var custIds = form.elements[chkboxname],
		result = [];
		
	if(!custIds.length){
		custIds = [custIds]; /*一个radio不是数组*/
	}
	
	for(var i=0, len=custIds.length; i<len; i++){
		if (custIds[i].checked == true) {
			result.push(custIds[i].value)
		}
	}
	
	return result;
}

/**
 * 获得select选中option的值
 * @param {Object} select
 */
function getSelectedOptionText(select){
	var options = select.options;
	for (var i=0, len=options.length; i<len; i++) {
		if (options[i].selected == true) {
			return options[i].innerHTML;
		}
	}
	
	return '';
}

/**
 * 获取下发操作条目地域
 * @param {Object} custIds
 */	
function getDelItemRegion(custIds) {
	var regions = [];
	custIds = (typeof custIds.length === undefined) ? [custIds] : custIds;
	
	for (var i=0, len=custIds.length; i < len; i++) {
		regions.push(delegateItems[custIds[i]]['region']);
	}
	
	return regions.join(',');
}

/*下发 废弃操作二次确认*/
function delegateConfirm(form,isBatch){
	var handleType = form.elements['handleType'].value;
		
	if (handleType == 1) {
		var regions = [],
			selectCustIds = isBatch ? getSelectedItem(form,'custIds') : form.elements['custId'].value,
			targetRegion = getSelectedOptionText(form.elements["delegateTargetManagerId"]),
			msg = (isBatch ? ('您确认将来自:\n'+ getDelItemRegion(selectCustIds) +"\n的资料下发至 ") : "您确认将该资料下发至 ") + targetRegion +" 吗?";
	
		return confirm(msg);
	}
	else if(parseInt(handleType) === 0) {
	   return confirm('确定要废弃资料吗？'); 
	}
	
	else{
		return true;
	}
}
/*==============================批处理下发二次确认 END==========================*/
			
