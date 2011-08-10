var root = '/ht-callin';
var frame = top.document.getElementById('rightFrame');
var ocxFrame = top.frames['ocxFrame'];
var tabFrame = top.frames['tabFrame'];
var callFrame = top.frames['callFrame'];
var mainFrame = top.frames['mainFrame'];

requestHTML = '<div class="wrapper"><div class="area-w air"></div><div class="area-v"></div><div class="area"><div class="indicator"><table><tr><td>数据请求中，请稍侯...</td><td class="image">&nbsp;</td></tr></table></div></div><div class="area-v"></div><div class="area-w"></div></div>';

function onKeyDown()
{
	var keyCode = (event || mainFrame.frames['main'].event).keyCode;
	if (keyCode == 116 || keyCode == 122 || keyCode == 93 || (keyCode == 82 && window.event.ctrlKey))
	{
		event.keyCode = 0;
		event.returnValue = false;
		return false;
	}
}
function moreList()
{
	var child = window.open(root + '/popScreen/moreCust.action');
	while (!child.document.body);
	var table = child.G('dataList');
	var data = G('data' + findSelectedTab().eid);
	table.innerHTML = data.innerHTML;
	G(table.parentNode).css('height', (28 + (data.getChildren('div').length / 6 - 1) * 25 + 20) + 'px');
	child.initTable('dataList', 6);
}
function changeDisplay()
{
	var e = G('fr' + findSelectedTab().eid);
	if (e.css('display') == 'none')
	{
		e.show();
	}
	else
	{
		e.hide();
	}
}
function userSearch(page, total)
{
	page = page.toString().trim();
	if(!/^\d+$/.test(page) || page == 0){
		alert('请输入大于0的数字');
		return false;
	}
	page = parseInt(page);
	if (total !== undefined && page > total)
	{
		alert('请不要输入大于最大页数的数字');
		return false;
	}
	var eid = findSelectedTab().eid;
	var f = document['uf' + eid];
	var value = f.queryValue.value.trim();
	if (value)
	{
		new Ajax(function (html) {
			callFrame.G('ul' + eid).innerHTML = html.format(eid);
			callFrame.initTable('dlr' + eid, 8);
		}).send(root + '/popScreen/custList.action',
			'page.curPageNum=' + page
			+ '&queryName=' + f.queryName.value
			+ '&queryValue=' + encodeURIComponent(value),
			'post');
	}
	else
	{
		alert('请填写查询条件再提交');
	}
}
function showNote(flag)
{
	var eid = findSelectedTab().eid;
	G('yes' + eid).hide();
	G('no' + eid).hide();
	G('cont' + eid).hide();
	G('custUpdate' + eid).hide();
	G('auditBtn' + eid).hide();

    if (flag == 0) //下发
	{
		G('yes' + eid).show();
		G('custUpdate' + eid).show();
		G('auditBtn' + eid).show();
	}
	if (flag == 2) //后处理
	{
		G('cont' + eid).show();
	}
	else if (flag == 1) //废弃
	{
		G('no' + eid).show();
        G('noTitle' + eid).innerHTML = "废弃批注";
	}
	else if (flag == 3) //冲突客户处理
	{
		G('no' + eid).show();
        G('noTitle' + eid).innerHTML = "冲突客户处理批注";
	}
}
function changeMain(url)
{
	var rows = frame.rows.split(',');
	if (rows[3] != '*')
	{
		rows[2] = '0';
		rows[3] = '*';
		frame.rows = rows.join(',');
	}
	url += url.indexOf('?') > 0 ? '&' : '?';
	mainFrame.G('main').src = url + 'reqTime=' + new Date().getTime();
	tabFrame.changeForm(tabFrame.G('tabs').getChildren('div')[0]);
}
function showCallFrame()
{
	var rows = frame.rows.split(',');
	rows[1] = '40';
    rows[2] = '0';
	rows[3] = '*';
	frame.rows = rows.join(',');
    var now = new Date().getTime();
    /**
     * 确保取到高度，并且超过一秒未取到强制跳出
     */
    while (mainFrame.document.documentElement.offsetHeight == 0 
            && new Date().getTime() - now < 1000) {
    }
    mainFrame.autoSize();
	rows[2] = '*';
	rows[3] = '0';
	frame.rows = rows.join(',');
//	callFrame.G('callsInner').style.height = (callFrame.document.documentElement.offsetHeight || callFrame.document.html.offsetHeight) + 'px';
}
function hideCallFrame()
{
	var rows = frame.rows.split(',');
	rows[2] = '0';
	rows[3] = '*';
	frame.rows = rows.join(',');
}
function addTab(name)
{
	var parent = tabFrame.G('tabs');
	var node = tabFrame.document.createElement('div');
	node.innerHTML = '<span onclick="changeForm(this.parentNode);">'
		+ name.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
		+ '&nbsp;<span onclick="remove(this.parentNode.parentNode)">X</span></span>';
	parent.appendChild(node);
	return node;
}
function findSelectedTab()
{
	var children = tabFrame.G('tabs').getChildren('div');
	for (var i = 0, o; o = children[i]; i++)
	{
		if (o.className == 'tabSelect')
		{
			return o;
		}
	}
}
function remove(e)
{
	if (ocxFrame.calling == e || ocxFrame.waitCallTalk == e)
	{
		alert('不能关闭当前呼叫对应的弹出页面');
		return;
	}
	if (e.callId)
	{
		alert('还没有保存通话记录, 不能关闭弹出页面');
		return;
	}
	G(e).remove();
	if (e.className == 'tabSelect')
	{
		changeForm(tabFrame.G('sysTab'));
	}
	callFrame.G('call' + e.eid).remove();
	if (callFrame.G('calls').getChildren('div').length <= 1)
	{
		var rows = frame.rows.split(',');
		rows[1] = '0';
        var h = mainFrame.document.documentElement.offsetHeight;
		frame.rows = rows.join(',');
        var now = new Date().getTime();
        /**
         * 确保取到高度，并且超过一秒未取到强制跳出
         */
        while (mainFrame.document.documentElement.offsetHeight == h 
                && new Date().getTime() - now < 1000) {
        }
        mainFrame.autoSize();
	}
}
function changeForm(e)
{
	var children = tabFrame.G('tabs').getChildren('div');
	for (var i = 0, o; o = children[i]; i++)
	{
		if (o == e)
		{
			if (i)
			{
				showCallFrame();
				var list = callFrame.G('calls').getChildren('div');
				for (var j = 0, e; e = list[j]; j++)
				{
                    var suffix = e.id.substring(4), //去除call字符
                        afterCurePanel = callFrame.G('afterCurePanel' + suffix);
                    //如果某个tab下的后处理弹屏，处于显示状态则，隐藏它并做上标记
                    if (afterCurePanel && afterCurePanel.style.display != "none") {
                        afterCurePanel.setAttribute("data-opened", "true");
                        callFrame.Popup.hide(afterCurePanel);
                    }
					e.style.display = 'none';
				}
                //如果当前tab之前有打开过后处理弹屏，则显示
				e = callFrame.G('call' + o.eid);
				e && (e.style.display = '');
				afterCurePanel = callFrame.G('afterCurePanel' + o.eid);
		if (afterCurePanel && afterCurePanel.getAttribute("data-opened")) {
                    callFrame.Popup.show(afterCurePanel);
                    afterCurePanel.removeAttribute("data-opened");
                }
			}
			else
			{
				hideCallFrame();
			}
			o.className = 'tabSelect';
		}
		else
		{
			o.className = 'tab';
		}
	}
}
//如果存在SoftLocalLog构造函数，则初始化对象
if( typeof SoftLocalLog != "undefined")
{
  var log = new SoftLocalLog();
}
else
{
  var log = null;
}
function popCallIn(callId, callType, callingNo, calledNo)
{
	log && log.info('start popCallIn['+callId+']:' + callingNo);
	showCallFrame();
	var parent = callFrame.G('calls');
	log && log.info('create tab[' + callId + ']');
	var node = addTab('呼入(' + callingNo + ')');
	var eid = new Date().getTime();
	node.eid = eid;
	log && log.info('create call div[' + callId + ']-' + eid);
	var callNode = callFrame.document.createElement('div');
	callNode.id = 'call' + eid;
	callNode.innerHTML = requestHTML;
	callNode.style.height = callFrame.document.documentElement.offsetHeight + 'px';
	parent.appendChild(callNode);
	log && log.info('change tab[' + callId + ']-' + eid);
	changeForm(node);
	node.callingId = callId;
	node.callId = callId;
	node.callType = callType;
	node.callingNo = callingNo;
	node.calledNo = calledNo;
	node.call = '0';
	ocxFrame.calling = node;
	log && log.info('call ajax[' + callId + ']-' + eid);
	var requester = new Ajax(function (html) {
		log && log.info('ajax return[' + callId + ']-' + eid + ':' + html.length);
		callNode.innerHTML = html.format(eid);
		callNode.style.height = '';
		log && log.info('table init[' + callId + ']-' + eid);
    log && (log.callId = callId);
    callFrame.log = log;
		if (callFrame.G('dl' + eid))
		{
			callFrame.initTable('dl' + eid, 6);
		}
		log && log.info('GetCallData start[' + callId + ']-' + eid);
		var data = ocxFrame.agentBar.GetCallData();
		log && log.info('GetCallData end' + data + '[' + callId + ']-' + eid);
		if (data)
		{
			callFrame.G('clTime' + eid).innerHTML = data.split(',')[2];
		}
		if (node.arTime)
		{
			callFrame.G('arTime' + eid).innerHTML = node.arTime;
		}
		log && log.info('calendar init[' + callId + ']-' + eid);
		callFrame.RangeCalendar.init({
			'beginDate' : 'ed' + eid,
			'endDate'   : false,
			'future'	: true
		});
		callFrame.Cust.init('rd' + eid, eid);
		callFrame.initMultiSelect([{name:"tradeList1" + eid, url : "/ht-callout/cust/tradeList.action?tradeId="}, {name:"tradeList2" + eid}]);
		callFrame.initMultiSelect([{name:"custProv" + eid, url : "/ht-callout/cust/areaList.action?areaId="}, {name:"custCity" + eid, url : "/ht-callout/cust/areaList.action?areaId=", receive:cityChange}, {name:"custArea" + eid}]);
		callFrame.querySelChange({type : 'radio', selectId : 'cust.webSite', normal : 'webSiteTab1' + eid, maps : {"1" : "webSiteTab1" + eid, "2" : "webSiteTab2" + eid, "3" : "webSiteTab3" + eid}}, 'rd' + eid);
		log && log.info('SetCallData[' + callId + ']-' + eid);
		setCallData(node);
		if (node.party !== undefined)
		{
			log && log.info('hangup[' + callId + ']-' + eid);
			callDataSubmit(node, node.party);
		}
	});
  log && log.info('create XMLHttpRequest Success:' + callId);
  try {
    log && log.info('start sending data:' + root + '/popScreen/callIn.action?callId=' + callId + '&phoneNumber=' + callingNo + '&orgCalledNum=' + calledNo);
    requester.send(root + '/popScreen/callIn.action?callId=' + callId + '&phoneNumber=' + callingNo + '&orgCalledNum=' + calledNo, '', 'get');
    log && log.info('end sending data:' + callId);
  } catch (ex) {
    log && log.info('sending data fail, callId:' + callId + ';message:' + ex.message);
  }
}
function setCallData(node)
{
	var eid = node.eid;
  var callData = node.callId
		+ ','
		+ node.callingNo
		+ ','
		+ (node.startTime || callFrame.G('clTime' + eid).innerHTML)
		+ ','
		+ node.calledNo
		+ ','
		+ node.call;
  log && log.info('setCallData inner：' + callData);
	ocxFrame.agentBar.SetCallData(callData);
}
function answerRequest(callId, callType, callingNo, calledNo)
{
	if (callType == 1 || ocxFrame.agentBar.GetCallData() == '1')
	{
		// 内部呼叫/呼叫转移/咨询转移都不需要弹屏
		ocxFrame.calling = true;
	}
	else if (callType == 0 || callType == 3)
	{
		popCallIn(callId, callType, callingNo, calledNo);
	}
	else if (callType == 2)
	{
		var node = {};
		node.callId = callId;
		node.callingNo = callingNo;
		node.calledNo = calledNo;
		ocxFrame.calling = node;
	}
}
function answerSuccess()
{
	if (ocxFrame.calling !== true)
	{
		var node = ocxFrame.calling;
		new Ajax(function (html) {
			if (callFrame.G('arTime' + node.eid))
			{
				callFrame.G('arTime' + node.eid).innerHTML = html;
			}
			else
			{
				node.arTime = html;
			}
			node.date = new Date();
		}).send(root + '/popScreen/currTime.action', '', 'get');
	}
}
function hangupSuccess(party)
{
	var node;
	if (ocxFrame.waitCallTalk)
	{
		node = ocxFrame.waitCallTalk;
		ocxFrame.waitCallTalk = null;
	}
	else if (ocxFrame.keeping)
	{
		if (ocxFrame.agentBar.GetHoldCallID())
		{
			node = ocxFrame.calling;
			ocxFrame.calling = ocxFrame.keeping;
		}
		else
		{
			node = ocxFrame.keeping;
		}
		ocxFrame.keeping = null;
	}
	else
	{
		node = ocxFrame.calling;
		ocxFrame.calling = false;
	}
	if (!node || node === true)
	{
		return;
	}
	var eid = node.eid;
	node.date = null;
	if (!eid || callFrame.G('clTime' + eid))
	{
		callDataSubmit(node, party);
	}
	else
	{
		node.party = party;
	}
}
function callDataSubmit(node, party)
{
	if (node.callingId)
	{
		var eid = node.eid;
		new Ajax(function (json) {
			eval(json);
		}).send(
			root + '/calldata/save.action',
			'callData.callId=' + node.callingId
			+ '&callData.custPhone=' + node.callingNo
			+ '&callData.orgCalledNum=' + node.calledNo
			+ '&callData.hotLine=' + (node.call == '0' ? callFrame.G('hl' + eid).innerHTML : '')
			+ '&callData.numIdentify=' + (node.call == '0' ? callFrame.G('sn' + eid).innerHTML : '')
			+ '&callData.callProv=' + (eid ? callFrame.G('cp' + eid).value : '')
			+ '&callData.callCity=' + (eid ? callFrame.G('cc' + eid).value : '')
			+ '&callData.startTime=' + (eid ? callFrame.G('clTime' + eid).innerHTML : node.startTime)
			+ '&callData.answerTime=' + (eid ? callFrame.G('arTime' + eid).innerHTML : node.answerTime)
			+ '&callData.startType=' + node.callType
			+ '&callData.stopType=' + party
			+ '&callData.callType=' + node.call
			+ '&callData.audioName=' + encodeURIComponent(node.fileName || ''),
			'post'
		);
		node.fileName = '';
	}
}
function formatTime(time)
{
	switch (time.length)
	{
	case 1:
		return '0' + time + ':00';
	case 2:
		return time + ':00';
	case 3:
		return '0' + time.substring(0, 1) + ':0' + time.substring(2);
	case 4:
		if (time.indexOf(':') == 1)
		{
			return '0' + time;
		}
		else
		{
			return time.substring(0, 3) + '0' + time.substring(3);
		}
	case 5:
		return time;
	}
}
function callinSubmit()
{
	var tab = findSelectedTab();
	var eid = tab.eid;
	var f = document['rd' + eid];
	var turnCust;
	var params = [];
	each(f['callRecord.turnCust'], function () {
		if (this.checked)
		{
			turnCust = this.value;
		}
	});
	if (!turnCust)
	{
		alert('请选择本次处理结果');
		return false;
	}
	else if (turnCust == 2) {
		for (var i = 0, list = f['reasonIds'], value; value = list[i]; i++) {
			if (value.checked) {
				break;
			}
		}
        var continueFlag = true;
		if (!value) {
			FormInfo.show(f['errReasonIds'].parentNode, '请至少选择一个待后续原因');
            continueFlag = false;
		}
        else {
			FormInfo.remove(f['errReasonIds'].parentNode);
        }

		var toDoPostil = f['callRecord.toDoPostil'];
		value = toDoPostil.value = toDoPostil.value.trim();
		if (value.length > 200) {
			FormInfo.show(toDoPostil.parentNode, '待后续原因过长，不能大于200字符');
            continueFlag = false;
		}
		else {
			FormInfo.remove(toDoPostil.parentNode);
		}
        if(!continueFlag) return false;
	}
	else if (turnCust == 1)
	{
		if (f['callRecord.noCustReason'].value.trim().length == 0)
		{
			FormInfo.show(f['callRecord.noCustReason'].parentNode, '废弃批注必需填写');
			return false;
		} else {
			FormInfo.remove(f['callRecord.noCustReason'].parentNode);
		}
		if (f['callRecord.noCustReason'].value.length > 200)
		{
			FormInfo.show(f['callRecord.noCustReason'].parentNode, '废弃批注的长度不能大于200字符');
			return false;
		} else {
			FormInfo.remove(f['callRecord.noCustReason'].parentNode);
		}
		if (!confirm('真的要废弃吗'))
		{
			return false;
		}
	}
	else if (turnCust == 3)
	{
		if (f['callRecord.noCustReason'].value.trim().length == 0)
		{
			FormInfo.show(f['callRecord.noCustReason'].parentNode, '冲突客户处理批注必需填写');
			return false;
		} else {
			FormInfo.remove(f['callRecord.noCustReason'].parentNode);
		}
		if (f['callRecord.noCustReason'].value.length > 200)
		{
			FormInfo.show(f['callRecord.noCustReason'].parentNode, '冲突客户处理批注的长度不能大于200字符');
			return false;
		} else {
			FormInfo.remove(f['callRecord.noCustReason'].parentNode);
		}
	}
	else
	{
		if (f['callRecord.yesCustRemark'].value.length > 200)
		{
			FormInfo.show(f['callRecord.yesCustRemark'].parentNode, '下发备注的长度不能大于200字符');
			return false;
		} else {
			FormInfo.remove(f['callRecord.yesCustRemark'].parentNode);
		}
	}
	if (f['callRecord.question'].value.length > 200)
	{
		FormInfo.show(f['callRecord.question'].parentNode, '问题描述的长度不能大于200字符');
		return false;
	} else {
		FormInfo.remove(f['callRecord.question'].parentNode);
	}
	var startTime = f['callRecord.startTime'].value.trim();
	var endTime = f['callRecord.endTime'].value.trim();
	if (!/^(([01]?\d|2[0123])(:([012345]?\d))?)?$/.test(startTime)
		|| !/^(([01]?\d|2[0123])(:([012345]?\d))?)?$/.test(endTime))
	{
		FormInfo.show(f['callRecord.startTime'].parentNode, '日期格式不合法, 请输入"小时", 或者"小时：分钟"');
		return false;
	} else {
		FormInfo.remove(f['callRecord.startTime'].parentNode);
	}
	startTime = formatTime(startTime);
	endTime = formatTime(endTime);
	f['callRecord.startTime'].value = startTime;
	f['callRecord.endTime'].value = endTime;
	f['callRecord.callId'].value = tab.callId || tab.oldCallId || '';
	f['callRecord.callType'].value = tab.call;
	f['custPhone'].value = tab.callingNo;
	f['callTime'].value = callFrame.G('arTime' + tab.eid).innerHTML;
	if (tab.netOrderId) {
		f['netOrderId'].value = tab.netOrderId;
	}
	if(tab.callMessageId) {
	    f['callMessageId'].value = tab.callMessageId;
	}
	return confirm('您确定要进行本次处理吗？'); 
}

function popCallOut(netOrderId, custName)
{
	if (!ocxFrame)
	{
		alert('请先登录呼叫中心');
		return;
	}
	new Ajax(function (html) {
		if (html.substring(0, 5) == 'alert')
		{
			eval(html);
			return;
		}
		showCallFrame();
		var parent = callFrame.G('calls');
		var node = addTab('呼出(' + custName + ')');
		node.call = '1';
		node.callId = '';
		node.netOrderId = netOrderId;
		var eid = new Date().getTime();
		node.eid = eid;

		var callNode = callFrame.document.createElement('div');
		callNode.id = 'call' + eid;
		callNode.innerHTML = requestHTML;
		parent.appendChild(callNode);

		changeForm(node);
		var list = parent.getChildren('div');
		callNode.innerHTML = html.format(eid);
		if (callFrame.G('dl' + eid))
		{
			callFrame.initTable('dl' + eid, 6);
		}
		callFrame.RangeCalendar.init({
			'beginDate' : 'ed' + eid,
			'endDate'   : false,
			'future'    : true
		});

		callFrame.Cust.init('rd' + eid, eid);
		callFrame.initMultiSelect([{name:"tradeList1" + eid, url : "/ht-callout/cust/tradeList.action?tradeId="}, {name:"tradeList2" + eid}]);
		callFrame.initMultiSelect([{name:"custProv" + eid, url : "/ht-callout/cust/areaList.action?areaId="}, {name:"custCity" + eid, url : "/ht-callout/cust/areaList.action?areaId=", receive:cityChange}, {name:"custArea" + eid}]);
		callFrame.querySelChange({type : 'radio', selectId : 'cust.webSite', normal : 'webSiteTab1' + eid, maps : {"1" : "webSiteTab1" + eid, "2" : "webSiteTab2" + eid, "3" : "webSiteTab3" + eid}}, 'rd' + eid);
	}).send(root + '/popScreen/callOut.action?netOrderId=' + netOrderId, '', 'get');
}

function popMessage(callMessageId, custName)
{
	if (!ocxFrame)
	{
		alert('请先登录呼叫中心');
		return;
	}
	new Ajax(function (html) {
		if (html.substring(0, 5) == 'alert')
		{
			eval(html);
			return;
		}
		showCallFrame();
		var parent = callFrame.G('calls');
		var node = addTab('呼出(' + custName + ')');
		node.call = '2';
		node.callId = '';
		node.callMessageId = callMessageId;
		var eid = new Date().getTime();
		node.eid = eid;

		var callNode = callFrame.document.createElement('div');
		callNode.id = 'call' + eid;
		callNode.innerHTML = requestHTML;
		parent.appendChild(callNode);

		changeForm(node);
		var list = parent.getChildren('div');
		callNode.innerHTML = html.format(eid);
		if (callFrame.G('dl' + eid))
		{
			callFrame.initTable('dl' + eid, 6);
		}
		callFrame.RangeCalendar.init({
			'beginDate' : 'ed' + eid,
			'endDate'   : false,
			'future'    : true
		});

		callFrame.Cust.init('rd' + eid, eid);
		callFrame.initMultiSelect([{name:"tradeList1" + eid, url : "/ht-callout/cust/tradeList.action?tradeId="}, {name:"tradeList2" + eid}]);
		callFrame.initMultiSelect([{name:"custProv" + eid, url : "/ht-callout/cust/areaList.action?areaId="}, {name:"custCity" + eid, url : "/ht-callout/cust/areaList.action?areaId=", receive:cityChange}, {name:"custArea" + eid}]);
		callFrame.querySelChange({type : 'radio', selectId : 'cust.webSite', normal : 'webSiteTab1' + eid, maps : {"1" : "webSiteTab1" + eid, "2" : "webSiteTab2" + eid, "3" : "webSiteTab3" + eid}}, 'rd' + eid);
	}).send(root + '/popScreen/callMsgOut.action.action?callMessageId=' + callMessageId, '', 'get');
}

function makeCallSuccess(callId, callType, callingNo, calledNo)
{
	if (callingNo.charAt(0) == '0')
	{
		callingNo = callingNo.substring(1);
		var node = findSelectedTab();
		if (!node || ocxFrame.calling || (node.call != '1' && node.call != '2'))
		//if (!node || ocxFrame.calling || node.call != '1')
		{
			node = {};
			node.call = '1';
		}
		node.callingId = callId;
		node.callId = node.callId ? node.callId + ',' + callId : callId;
		node.callType = callType;
		node.callingNo = callingNo;
		node.calledNo = calledNo;
		ocxFrame.waitCallTalk = node;
		if (node.eid)
		{
			new Ajax(function (html) {
				callFrame.G('clTime' + node.eid).innerHTML = html;
				callFrame.G('arTime' + node.eid).innerHTML = '';
				callFrame.G('asTime' + node.eid).innerHTML = '';
				callFrame.G('clNo' + node.eid).innerHTML = callingNo;
			}).send(root + '/popScreen/currTime.action', '', 'get');
			new Ajax(function (json) {
				var result;
				eval("result=" + json);
				callFrame.G('area' + node.eid).innerHTML = result.phoneAreaName;
				callFrame.G('cp' + node.eid).value = result.callProv;
				callFrame.G('cc' + node.eid).value = result.callCity;
			}).send(root + '/popScreen/phoneArea.action?phoneNumber=' + callingNo, '', 'get');
		}
		else
		{
			node.startTime = '';
			node.answerTime = '';
			new Ajax(function (html) {
				node.startTime = html;
			}).send(root + '/popScreen/currTime.action', '', 'get');
		}
	}
	else
	{
		ocxFrame.waitCallTalk = true;
	}
}
function makeCallTalkSuccess()
{
	var node = ocxFrame.waitCallTalk;
	ocxFrame.waitCallTalk = null;
	ocxFrame.keeping = ocxFrame.calling;
	ocxFrame.calling = node;
	if (node !== true)
	{
		if (!node.eid)
		{
			node.answerTime = '';
		}
		new Ajax(function (html) {
			if (node.eid)
			{
				callFrame.G('arTime' + node.eid).innerHTML = html;
				node.date = new Date();
			}
			else
			{
				node.answerTime = html;
			}
		}).send(root + '/popScreen/currTime.action', '', 'get');
		if (ocxFrame.DTMFDigits)
		{
			ocxFrame.agentBar.SendDTMF(ocxFrame.DTMFDigits);
			ocxFrame.DTMFDigits = null;
		}
		setCallData(node);
	}
	else
	{
		// 表示内部呼叫
		ocxFrame.agentBar.SetCallData('1');
	}
}
function cityChange(suffix){try{G('custCity' + suffix).onchange(true);}catch(e){}}
function custPageCallout(el, phType, phFlag) {
	if (phType === undefined || (phType != 0 && phType != 1)) {
		return false;
	}
	var inputs = el.getElementsByTagName('input');
	var phoneNumber = '';

	// 座机
	if (phType == 0) {
		//如果不是国内电话 不外呼
		if (inputs[0].value && inputs[0].value != Cust.COUNTRYCODE.CHINA) {
		    alert('国外电话暂不提供外呼服务');
		    return;
		}
		var phCode = inputs[1].value.trim();
		var phNum = inputs[2].value.trim();
		var phExt = inputs[3].value.trim();
		phoneNumber = phCode + '-' + phNum + (phExt ? '-' + phExt : '');
		if (!/^\d{3,4}-\d{7,8}(-\d{1,8})?$/.test(phoneNumber)) {
			FormInfo.show(el, '请检查电话号码是否正确');
			return false;
		} else {
			if (phFlag == phCode)
			{
				pageCallout(phNum, phExt);
			} else {
				pageCallout(phCode + '' + phNum, phExt);
			}
		}
	} else if (phType == 1) {
		var phNum = inputs[0].value.trim();
		if (!/^\d{11,12}$/.test(phNum) || (phNum.length == 12 && !phNum.startWith('0')) || (phNum.length == 11 && !phNum.startWith('1'))) {
			FormInfo.show(el, '请检查电话号码是否正确');
			return false;
		} else {
			pageCallout(phNum);
		}
	}
}
function pageCallout(phoneNumber, DTMFDigits)
{
	ocxFrame.agentBar.MakeCall('0' + phoneNumber, ocxFrame.agentBar.DN, 0, 5);
	ocxFrame.DTMFDigits = DTMFDigits;
}
function consultSuccess(callId)
{
}
function internalHelpArrived()
{
	var list = ocxFrame.agentBar.GetCallData().split(',');
  log && log.info( "internalHelpArrived inner" + ocxFrame.agentBar.GetCallData() );
	var node = ocxFrame.calling;
	popCallIn(node.callId, 2, list[1], node.calledNo);
	ocxFrame.calling.fileName = node.fileName;
	answerSuccess();
}
