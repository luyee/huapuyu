/**
 * 我的客户-联系方式页面相关行为处理
 *
 * contact_manage.js
 * @author lim
 */

/*
 * @desc 采用统一的命名空间, pt是pageTools的缩写
 */
if (typeof(pt) == "undefined") {
    pt = {};
}

pt.COMMON = {
    'CHIANCODE' : 86
}

/*
 * 获得元素el的特定标签的第一个祖先元素
 * @param {Element} el -- 需要获得父节点的元素
 * @param {String} ancestorTagNames -- 标签形式如: "p,div"
 * @return {Element|null} el特定标签的第一个祖先元素
 */
pt.getElementAncestor = function(el, ancestorTagNames) {
    if (!el) {
        return null;
    }
    var lastTags = "," + ancestorTagNames.toUpperCase() + ",";
    while (el) {
        if ( lastTags.indexOf("," + el.nodeName.toUpperCase() + ",") != -1) {
            return el;
        }
        el = el.parentNode;
    }
    return null;
};

/**
 * 获得元素的在垂直方向的像素
 * @param {Element} el - 具体的元素节点
 */
pt.pageY = function(el) {
	return el.offsetParent ? el.offsetTop + arguments.callee(el.offsetParent) : el.offsetTop
};

/**
 * 获得当前节点的下一个元素节点
 * @param {Element} el -- 当前元素
 * @return {Element|null} 下一个元素节点或者空节点
 */
pt.next = function(el) {
    if (!el) {
        return null;
    }
    el = el.nextSibling;
    while (el) {
        if (el.nodeType == 1) {
            break;
        }
        else {
            el = el.nextSibling;
        }
    }
    return el;
};

/*
 * 获得当前节点的上一个元素节点
 * @param {Element} el -- 当前元素
 * @return {Element|null} 上一个元素节点或者空节点
 */
pt.prev = function(el) {
    if (!el) {
        return null;
    }
    el = el.previousSibling;
    while (el) {
        if (el.nodeType == 1) {
            break;
        }
        else {
            el = el.previousSibling;
        }
    }
    return el;
};

pt._errorCounter = 0; /*_errorCounter 出错计数器*/

/**
 * 业务逻辑部分
 */

/**
 * 显示具体项的出错信息
 * @param {Element} el - 需要验证的元素
 * @param {String} errorTip - 错误提示信息
 */
pt.showErrorTip = function(el, errorTip) {
    var elParent = el.parentNode;
    var errorTipEl = baidu.Q("fieldErr", elParent, "div")[0];
    if (!errorTipEl) {
        var tipEl = document.createElement("div");
        tipEl.className = "fieldErr";
        tipEl.innerHTML = errorTip;
        elParent.appendChild(tipEl);
    }
};

/**
 * 清除具体项的出错信息
 * @param {String}  el - 出错的表单项
 */
pt.clearErrorTip = function(el) {
    var elParent = el.parentNode;
    var errorTipEl = baidu.Q("fieldErr", elParent, "div")[0];
    if (errorTipEl) {
        errorTipEl.parentNode.removeChild(errorTipEl);
    }
};

/**
 * 当联系人失焦时,判断填写是否正确
 * @param {Element} el - 联系人input框
 */
pt.ctrlContactNameBlur = function(el) {
    var contactNameVal = baidu.trim(el.value);
    if (contactNameVal.length < 1) {
        pt.showErrorTip(el, "请填写联系人！");
        return false;
    }
    else {
        pt.clearErrorTip(el);
        return true;
    }
};

/**
 * 真正传为后端的生效失效的input框设置成对应的值
 * @param {Element} el - 失效checkbox元素 
 */
pt.changeDisabledStatus = function(el) {
    var disabledInputEl = pt.next(el);
    if (el.checked) {
        disabledInputEl.value = "1";
    }
    else {
        disabledInputEl.value = "0";
    }
};

/**
 * disable除联系人联系人失效原因,按钮外的表单元素
 */
pt.disableAllItems = function() {
    var formEl = baidu.G("contactManageForm");
    var inputEls = formEl.getElementsByTagName("input");
    for (var i = 0, length = inputEls.length; i < length; i++) {
        var inputEl = inputEls[i];
        var elName = inputEl.getAttribute("name");
        switch (elName) {
            case "disabledTemp" :
            case "contact.disabled" :
            case "contact.disabledReason" :
            case "submitFormBtn" :
            case "resetFormBtn" :
                break;
            default :
                inputEl.setAttribute("disabled", true);
                break;
        }
    }
};

/**
 * enable 不含rel="cantModify"元素
 */
pt.enableItems = function() {
    var formEl = baidu.G("contactManageForm");
    var inputEls = formEl.getElementsByTagName("input");
    var selectEls = formEl.getElementsByTagName("select")
    for (var i = 0, length = inputEls.length; i < length; i++) {
        var inputEl = inputEls[i];
        if (inputEl.getAttribute("rel") != "cantModify") {
            inputEl.removeAttribute("disabled");
        }
        else {
            inputEl.setAttribute("disabled", true);
        }
    }
    for (var i = 0, length = selectEls.length; i < length; i++) {
        var inputEl = selectEls[i];
        if (inputEl.getAttribute("rel") != "cantModify") {
            inputEl.removeAttribute("disabled");
        }
        else {
            inputEl.setAttribute("disabled", true);
        }
    }
};

pt.changeCountryCode = function (el) {
    var input = pt.next(el);
    if (input.name == 'phCountryCode') {
	input.value = el.value;
    }
    input = pt.next(input);
    if (input.name == 'phArea') {
	pt.ctrlPhoneBlur(input);
    }
}

/**
 * 点击联系人失效checkbox时的处理
 * @param {Element} el - 失效checkbox元素
 */
pt.ctrlDisabledClick = function(el) {
    /*真正传为后端的生效失效的input框设置成对应的值*/
    pt.changeDisabledStatus(el);

    var disabledReasonEl = pt.next(pt.next(el));

    /*隐藏显示失效原因框*/
    if (el.checked){
        baidu.show(disabledReasonEl);
    }
    else {
        baidu.hide(disabledReasonEl);
    }

    /*是否需要出失效原因错误提示*/
    pt.ctrlDisabledReasonBlur(disabledReasonEl);
    
    var elName = el.getAttribute("name");
    switch (elName) {
        case "disabledTemp" :
            /*联系人失效checkbox*/
            if (el.checked) {
                pt.disableAllItems();
            }
            else {
                pt.enableItems();
            }
            break;
        default :
            break;
    }

};

/**
 * 当无效原因失焦，调用 
 * @param {Element} el - 无效原因输入框 
 */
pt.ctrlDisabledReasonBlur = function(el) {
    var elVal = baidu.trim(el.value);
    var disabledEl = pt.prev(el);
    if (
            el.style.display != "none" && 
            elVal.length < 1 &&
            disabledEl.value == "1"
        ) 
    {
        pt.showErrorTip(el, "请填写失效原因！");
        return false;
    }
    else {
        pt.clearErrorTip(el);
        return true;
    }
};

/**
 * 当座机区号、总机号、分机号失焦时，调用 
 * @param {Element} el - 区号or总机号or分机号输入框 
 */
pt.ctrlPhoneBlur = function(el) {
    var elName = el.getAttribute("name");
    var phAreaEl = null;
    var phNumEl = null;
    var phExtensionEl = null;
    var inputs = el.parentNode.getElementsByTagName('input');
    var isChina = true;
    for (var i = 0; i < inputs.length; i++) {
	if (inputs[i].name == 'phCountryCode') {
	    if (inputs[i].value != pt.COMMON.CHIANCODE) {
		isChina = false;
	    }
	    break;
	}
    }
    if (elName == "phArea") {
        phAreaEl = el;
        phNumEl = pt.next(el);
        phExtensionEl = pt.next(phNumEl);
    }
    else if (elName == "phNum") {
        phAreaEl = pt.prev(el);
        phNumEl = el;
        phExtensionEl = pt.next(el);
    }
    else if (elName == "phExtension") {
        phNumEl = pt.prev(el);
        phAreaEl = pt.prev(phNumEl);
        phExtensionEl = el;
    }
    var phoneStr = baidu.trim(phAreaEl.value) + 
        "-" +
        baidu.trim(phNumEl.value) +
        "-" +
        baidu.trim(phExtensionEl.value);

    /*将出错提示的ID存放在元素属性中*/
    if (phoneStr.length > 2 && !pt.validatePhone(phoneStr, isChina) ) {
        pt.showErrorTip(el, "座机号不符合要求！");
        return false;
    }
    else {
        pt.clearErrorTip(el);
        return true;
    }
};

/**
 * 当手机号失焦时，调用 
 * @param {Element} el - 手机号输入框 
 */
pt.ctrlMobileBlur = function(el) {
    var mobileStr = baidu.trim(el.value);

    if (mobileStr.length > 0 && !pt.validateMobile(mobileStr)) {
        pt.showErrorTip(el, "手机号不符合要求！");
        return false;
    }
    else {
        pt.clearErrorTip(el);
        return true;
    }
};

/**
 * 当邮箱失焦时，调用 
 * @param {Element} el - 邮箱输入框 
 */
pt.ctrlEmailBlur = function(el) {
    var emailStr = baidu.trim(el.value);

    if (emailStr.length > 0 && !pt.validateEmail(emailStr)) {
        pt.showErrorTip(el, "邮箱号不符合要求！");
        return false;
    }
    else {
        pt.clearErrorTip(el);
        return true;
    }
};

/**
 * 添加座机
 * @param {Element} el - 当前点击的添加按钮 
 */
pt.addPhoneTpl = function(el) {
    /*当前的联系人是否失效状态*/
    var contactDisabled = baidu.G("contactManageForm")["contact.disabled"];
    /*当前添加的座机的个数*/
    var phNumEls = baidu.G("contactManageForm")["phNum"];
    if (contactDisabled.value == "1" || phNumEls.length >= 10) {
        return false;
    }
    else {
        var phEl = pt.getElementAncestor(el, "div");
        baidu.insertHTML(phEl.parentNode, "beforeEnd", baidu.G("phoneTpl").innerHTML);
        return true;
    }
};

/**
 * 删除座机
 * @param {Element} el - 当前点击的删除按钮 
 */
pt.delPhone = function(el) {
    /*当前的联系人是否失效状态*/
    var contactDisabled = baidu.G("contactManageForm")["contact.disabled"];
    /*当前添加的座机的个数*/
    var phNumEls = baidu.G("contactManageForm")["phNum"];
    var length = phNumEls.length || 1;
    var cantModify = el.getAttribute("rel");
    var phEl = pt.getElementAncestor(el, "div");
    if (contactDisabled.value == "1" || length < 2 || cantModify == "cantModify") {
        return false;
    }
    else {
        phEl.parentNode.removeChild(phEl);
        return true;
    }
};

/**
 * 添加手机
 * @param {Element} el - 当前点击的添加按钮 
 */
pt.addMobileTpl = function(el) {
    /*当前的联系人是否失效状态*/
    var contactDisabled = baidu.G("contactManageForm")["contact.disabled"];
    /*当前添加的座机的个数*/
    var mbNumEls = baidu.G("contactManageForm")["mbNum"];
    if (contactDisabled.value == "1" || mbNumEls.length >= 10) {
        return false;
    }
    else {
        var phEl = pt.getElementAncestor(el, "div");
        baidu.insertHTML(phEl.parentNode, "beforeEnd", baidu.G("mobileTpl").innerHTML);
        return true;
    }
};

/**
 * 删除手机
 * @param {Element} el - 当前点击的删除按钮 
 */
pt.delMobile = function(el) {
    /*当前的联系人是否失效状态*/
    var contactDisabled = baidu.G("contactManageForm")["contact.disabled"];
    /*当前添加的手机的个数*/
    var mbNumEls = baidu.G("contactManageForm")["mbNum"];
    var length = mbNumEls.length || 1;
    var cantModify = el.getAttribute("rel");
    if (contactDisabled.value == "1" || length < 2 || cantModify == "cantModify") {
        return false;
    }
    else {
        var phEl = pt.getElementAncestor(el, "div");
        phEl.parentNode.removeChild(phEl);
        return true;
    }
};

/**
 * 提交前将所有输入框都置成有效
 */
pt.enableAllItems = function() {
    var formEl = baidu.G("contactManageForm");
    var inputEls = formEl.getElementsByTagName("input");
    for (var i = 0, length = inputEls.length; i < length; i++) {
        inputEls[i].removeAttribute("disabled");
    }
};


/**
 * 表单验证
 */

/**
 * 座机验证
 * @param {Element} phoneStr - 座机号码
 * @return {Boolen} 是否通过验证
 */
pt.validatePhone = function(phoneStr, isChina) {
    var validResult = true,
	reg;
    if (isChina) {
	reg = /^\d{3,4}-\d{7,8}-\d{0,8}$/;
    }
    else {
	reg = /^\d{0,4}-\d{0,8}-\d{0,8}$/
    }
    validResult = reg.test(phoneStr);
    return validResult;
};

/**
 * 手机验证
 * @param {Element} mobileStr - 手机号码
 * @return {Boolen} 是否通过验证
 */
pt.validateMobile = function(mobileStr) {
    var validResult = true;
    validResult = /^\d{11}$/.test(mobileStr);
    return validResult;
};

/**
 * email验证
 * @param {Element} emailStr - email
 * @return {Boolen} 是否通过验证
 */
pt.validateEmail = function(emailStr) {
    var validResult = true;
    validResult = /^[a-zA-Z0-9_\-.]+@[a-zA-Z0-9\-]+\.[a-zA-Z\-.]+$/.test(emailStr);
    return validResult;
};

/**
 * 判断座机或手机至少填写一个
 * @param {Element} phNumEl - 总机输入框元素
 * @param {Element} mobileEl - 手机输入框元素
 * @return {Boolen} 是否通过验证
 */
pt.validateContact = function(phNumEl, mobileEl) {
    var validResult = true;

    var isPhoneEmpty = pt.isEmptyPhone(phNumEl);
    var isMoibleEmpty = baidu.trim(mobileEl.value).length < 1;
    if (isPhoneEmpty && isMoibleEmpty) {
        validResult = false;
    }

    return  validResult;
};

/**
 * 清除上次的出错提示
 */
pt.clearErrorTips = function() {
    /*根据class名来找出错提示*/
    var errorTipEls = baidu.Q("fieldErr", baidu.G("contactManageForm"), "div");
    for (var i = 0, length = errorTipEls.length; i < length; i++) {
        var errorTipEl = errorTipEls[i];
        errorTipEl.parentNode.removeChild(errorTipEl);
    }
};

/**
 * 验证所有的信息，并给出出错提示
 */
pt.validateAllItems = function() {
    var i = 0, length = 0;
    var passedPhone = 0; /*通过验证的联系电话个数*/
    var result = null; /*验证是否正确*/

    /*获得表单元素*/
    var formEl = baidu.G("contactManageForm");

    /*联系人验证*/
    var contactNameEl = formEl["contact.contactName"];
    pt.ctrlContactNameBlur(contactNameEl);

    /*座机验证*/
    var phoneEls = formEl["phNum"];
    if (!phoneEls) {
        phoneEls = [];
    }
    else if (phoneEls && !phoneEls.length) {
        phoneEls = [phoneEls];
    }
    for (i = 0, length = phoneEls.length; i < length; i++){
        result = pt.ctrlPhoneBlur(phoneEls[i]);
        if (result && baidu.trim(phoneEls[i].value).length > 0) {
            passedPhone++;
        }
    }

    /*手机验证*/
    var mobileEls = formEl["mbNum"];
    if (!mobileEls) {
        mobileEls = [];
    }
    else if (mobileEls && !mobileEls.length) {
        mobileEls = [mobileEls]
    }
    for (i = 0, length = mobileEls.length; i < length; i++){
        var result = pt.ctrlMobileBlur(mobileEls[i]);
        if (result && baidu.trim(mobileEls[i].value).length > 0) {
            passedPhone++;
        }
    }

    /*邮箱验证*/
    var companyMailEl = formEl["contact.companyMail"];
    var personalMailEl = formEl["contact.personalMail"];
    pt.ctrlEmailBlur(companyMailEl);
    pt.ctrlEmailBlur(personalMailEl);

    /*无效原因验证*/
    /*联系人无效原因*/
    var contactDisabledReasonEl = formEl["contact.disabledReason"];
    pt.ctrlDisabledReasonBlur(contactDisabledReasonEl);

    /*手机无效原因*/
    var phDisabledReasonEls = formEl["phDisabledReason"];
    if (!phDisabledReasonEls) {
        phDisabledReasonEls = [];
    }
    else if (phDisabledReasonEls && !phDisabledReasonEls.length) {
        phDisabledReasonEls = [phDisabledReasonEls]
    }
    for (i = 0, length = phDisabledReasonEls.length; i < length; i++){
        pt.ctrlDisabledReasonBlur(phDisabledReasonEls[i]);
    }

    /*座机无效原因*/
    var mbDisabledReasonEls = formEl["mbDisabledReason"];
    if (!mbDisabledReasonEls) {
        mbDisabledReasonEls = [];
    }
    else if (mbDisabledReasonEls && !mbDisabledReasonEls.length) {
        mbDisabledReasonEls = [mbDisabledReasonEls]
    }
    for (i = 0, length = mbDisabledReasonEls.length; i < length; i++){
        pt.ctrlDisabledReasonBlur(mbDisabledReasonEls[i]);
    }

    /*手机座机必填一项验证*/
    if (passedPhone < 1) {
        var phoneGlobalTipEl = baidu.G("phoneGlobalTip");
        pt.showErrorTip(phoneGlobalTipEl.lastChild, "请至少填写一个符合要求的联系电话！");
    }
};

/**
 * 表单验证入口
 */
pt.validSubmitForm = function(event) {
    event = event || window.event;
    /*清除上次的出错信息*/
    pt.clearErrorTips();
    /*验证所有信息*/
    pt.validateAllItems();
    /*通过还是阻止提交*/
    var errorTipEls = baidu.Q("fieldErr", baidu.G("contactManageForm"), "div");
    if (errorTipEls.length > 0) {
        pt.showErrorTip(baidu.G("submitFormBtn"), "您填写的内容存在错误，请返回修改！");
        if (baidu.isIE) {
            event.returnValue = false;
        }
        else {
            event.preventDefault();
        }
    }
    else {
        pt.enableAllItems();
        baidu.G("submitFormBtn").setAttribute("disabled", true);
        setTimeout("pt.ctrlDisabledClick(baidu.G('contactManageForm')['disabledTemp'])", 0);
    }
};

/**
 * 处理表单提交返回的数据
 * @param {Element} 表单提交到的iframe页面
 */
pt.getSubmitResult = function(iframe){
    pt.getSubmitResult.counter++;
    if (pt.getSubmitResult.counter == 0 ) {
        return false;
    }
    var d = iframe.contentWindow.document;
    var submitResult = d.body.innerHTML;
    if (submitResult) {
        baidu.G("submitFormBtn").removeAttribute("disabled");
        alert(submitResult);
    }
    else {
        //pt.refreshPage();
        document.location.reload(true);
    }
};
pt.getSubmitResult.counter = -1;

/**
 * 刷新页面
 */
pt.refreshPage = function() {
    var url = "../cust/contactList.action?custId=" + baidu.G("contactManageCustId").value;
    function onSuccess(responseData){
        baidu.G("contactContainer").innerHTML = responseData;
    }
    function onFail(){
      alert("对不起，操作出错了，请稍候再试！");
    }
    var ajax = new Ajax(onSuccess,onFail);
    ajax.post(url);
}

/*进入页面时调用绘制页面*/
pt.refreshPage();

/**
 * 修改联系人请求
 */
pt.modifyContactInfo = function(contactId){
    if (!contactId) {
        return false;
    }
    var url = "../cust/contactView.action?contactId=" + contactId;
    function onSuccess(responseData){
        baidu.G("contactContainer").innerHTML = responseData;
        if (
                baidu.G("toggleUserInfoBtn") 
                && !baidu.G("toggleUserInfoBtn").getAttribute("disabled")
           )
        {
            toggleUserInfo();
        }
        var pageY = pt.pageY(G("contactManageTab"));
        window.scrollTo(0, pageY);
    }
    function onFail(){
      alert("对不起，操作出错了，请稍候再试！");
    }
    var ajax = new Ajax(onSuccess,onFail);
    ajax.post(url);
    return true;
};
