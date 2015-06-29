var fe_auditDesc_len = 1000;  //备注长度
function changeTag(head,content,className,currentNum,totalNum){
	var tagHead = G(head+currentNum);
	var tagCont = G(content+currentNum);
	for(var i=1;i<=totalNum;i++){
		G(head+i).removeClass(className);
		G(content+i).hide();
	}
	tagHead.addClass(className);
	tagCont.show();
	return false;
}
function changeSumSpan(checkboxDiv,spanDes,valueFrom){
	//金额联动
	if(G(checkboxDiv) && G(checkboxDiv).checked){
		var tempSpan = "";
		if(G(spanDes)) {
			tempSpan = G(spanDes);
		} else {
			return false;
		}
		
		if(G(valueFrom).value.length <= 0) {
			return false;
		}
		//alert(parseFloat(G(valueFrom).value));
		var resultValue = ( isNaN(parseFloat(G(valueFrom).value)) ? 0 : parseFloat(G(valueFrom).value) );
		
		
		if(checkboxDiv == "productSearch" && spanDes.indexOf("productSearchAccountSum")==-1) {	//如果是搜索推广，合同金额和发票金额为：服务费+预存款
			resultValue += parseFloat(G("serviceFee").value.match(/^\d+(\.\d{1,2})?$/) ? G("serviceFee").value : 0)
		} else if(checkboxDiv == "productNetunion" && spanDes.indexOf("productNetunionAccountSum") == -1) {
			resultValue += parseFloat(G("netUnionServiceFee").value.match(/^\d+(\.\d{1,2})?$/) ? G("netUnionServiceFee").value : 0)
		}
		
		
		if(tempSpan.tagName.toUpperCase() == "INPUT")
			tempSpan.value = resultValue;
		else
			tempSpan.innerHTML = resultValue;
	}
}
function changePreSumInput(checkboxDiv,valueFrom){
	
	//修改预存款输入框
	changeSumSpan(checkboxDiv,'contractSumSpan',valueFrom);
	changeSumSpan(checkboxDiv,checkboxDiv+'AccountSumSpan',valueFrom);
	changeSumSpan(checkboxDiv,checkboxDiv+'AccountSumHidden',valueFrom);
	changeSumSpan(checkboxDiv,'financeSumSpan',valueFrom);
	changeSumSpan(checkboxDiv,"financeSumSpanHidden",valueFrom);
	
}

function showProductPanel(productId) {
	var productPanelIds = ["productSearchDiv","productHeatDiv","productNetUnionDiv"];
	
	for (var i = 0, len = productPanelIds.length; i < len; i++ ) {
		var panel = G(productPanelIds[i]);
		if (panel) {
			panel.hide();
		}
	}
	
	G(productId).show();
	
}


/**
 * 删除产品信息各项验证信息
 */
function delProductItemValidate() {
	var inputs = G('productListPanel').getElementsByTagName('input');
	
	for (var i = 0, len = inputs.length; i < len; i++) {
		var item = inputs[i];
		
		if (item.type == 'text') {
			item.removeAttribute('pattern');
			item.removeAttribute('minLen');
			item.removeAttribute('maxLen');
			item.removeAttribute('minValue');
		}
	}
}

/**
 * 添加搜索推广的相关验证
 * @return
 */
function addProductSearchValidate() {
	G("searchPre").setAttribute("pattern","\\d+(\\.\\d{1,2})?");
	G("buildWebFee").setAttribute("pattern","(\\d+(\\.\\d{1,2})?)?");
	G("serviceFee").setAttribute("pattern","(\\d+(\\.\\d{1,2})?)?");
	//G("favRate").setAttribute("pattern","(100|[1-9]?[0-9])?");
	G("favRate").setAttribute("pattern", "^((\\d{1,2})(\\.\\d{1,5})?)?$");
}



function productSearchClick(){
	//点击搜索推广的radio
	showProductPanel("productSearchDiv");
	
	changeSumSpan("productSearch","contractSumSpan","searchPre");//修改合同信息里面的合同金额
	changeSumSpan("productSearch","financeSumSpan","searchPre");//修改财务信息里面的发票金额
	changeSumSpan("productSearch","financeSumSpanHidden","searchPre");
	
	delProductItemValidate();
	
	//添加搜索推广的相关验证
	addProductSearchValidate();
}



function addDroductHeatValidate() {
	//添加火爆地带的相关验证
	G("heatPre").setAttribute("pattern","\\d+(\\.\\d{1,2})?");
	var coopInputsParent = G("productHeatDiv").getElementsByTagName("input");
	for(var i=0,tempLen=coopInputsParent.length;i<tempLen;i++){
		if(coopInputsParent[i].name == "orderInfoDto.heatAreaDto.popWord" || coopInputsParent[i].name == "orderInfoDto.heatAreaDto.popPosition"){
			coopInputsParent[i].setAttribute("minLen","1");
		}
		if(coopInputsParent[i].name == "orderInfoDto.heatAreaDto.coopTime"){
			coopInputsParent[i].setAttribute("pattern","\\d+");
			coopInputsParent[i].setAttribute("minValue","1");
		}
	}
	var coopTextAreaParent = G("productHeatDiv").getElementsByTagName("textarea");
	for(var i=0,tempLen=coopTextAreaParent.length;i<tempLen;i++){
		if(coopTextAreaParent[i].name == "orderInfoDto.heatAreaDto.popDesc"){
			coopTextAreaParent[i].setAttribute("minLen","1");
			coopTextAreaParent[i].setAttribute("maxLen","100");
		}
	}
	
}

function productHeatClick(){
	//点击火爆地带的radio
	showProductPanel("productHeatDiv");
	
	changeSumSpan("productHeat","contractSumSpan","heatPre");
	changeSumSpan("productHeat","financeSumSpan","heatPre");
	changeSumSpan("productHeat","financeSumSpanHidden","heatPre");
	
	delProductItemValidate();

	//添加火爆地带的相关验证
	addDroductHeatValidate();
	
}

function addNetUnionValidate() {
	G("netUnionPre").setAttribute("pattern","\\d+(\\.\\d{1,2})?");
	G("netUnionBuildType").setAttribute("pattern","(\\d+(\\.\\d{1,2})?)?");
	G("netUnionServiceFee").setAttribute("pattern","(\\d+(\\.\\d{1,2})?)?");
	//G("netUnionFavRate").setAttribute("pattern", "(100|[1-9]?[0-9])?");
	G("netUnionFavRate").setAttribute("pattern", "^((\\d{1,2})(\\.\\d{1,5})?)?$");
}

function productNetUnionClick(){
	showProductPanel("productNetUnionDiv");
	
	changeSumSpan("productNetunion","contractSumSpan","netUnionPre");
	changeSumSpan("productNetunion","financeSumSpan","netUnionPre");
	changeSumSpan("productNetunion","financeSumSpanHidden","netUnionPre");
	
	
	//删除产品相关验证
	delProductItemValidate();
	
	//添加网盟推广相关验证
	addNetUnionValidate();
}


function caculateFav(){
	//计算优惠金额，预存款*优惠比率
    if (G("searchPre").value.length <= 0 || G("favRate").value.length <= 0 
        || !/^((\d{1,2})(\.\d{1,5})?)?$/.test(G("favRate").value.trim())) {
		G("searchPopDtoorderProductInfoSearchPopfavSumSpan").innerHTML = "";
		G("searchPopDtoorderProductInfoSearchPopfavSum").value = "";
		return false;
	}
    var result = (parseFloat(G("searchPre").value) * parseFloat(G("favRate").value) / 100).toFixed(2);
	G("searchPopDtoorderProductInfoSearchPopfavSumSpan").innerHTML = result;
	G("searchPopDtoorderProductInfoSearchPopfavSum").value = result;
}

function caculateFavForNetUnion(){
	
	//计算优惠金额，预存款*优惠比率
	var netUnionPre		= G('netUnionPre').value.trim(),
		netUnionFavRate = G('netUnionFavRate').value.trim(),
		netUnionFavSumSpan = G('productNetunionFavSum'),
		netUnionFavSumInput = G('productNetunionFavSumHidden');

	if (netUnionPre.length <= 0 || netUnionFavRate.length <= 0
        || !/^((\d{1,2})(\.\d{1,5})?)?$/.test(netUnionFavRate)) {
		netUnionFavSumSpan.innerHTML = "";
		netUnionFavSumInput.value = "";
		return false;
	}

    var result = (parseFloat(netUnionPre) * parseFloat(netUnionFavRate) / 100).toFixed(2);

	netUnionFavSumSpan.innerHTML = result;
	netUnionFavSumInput.value = result;
}

function addHeatSubDiv(){
	//增加一条火爆地带的推广信息
	var randomId = new Date().getTime();
	var inputList = G("productHeatDiv").getElementsByTagName("input");
	var sourceDiv = "";
	for(var i=0,tempLen=inputList.length;i<tempLen;i++){
		if(inputList[i].name=="orderInfoDto.heatAreaDto.id"){
			sourceDiv = inputList[i].parentNode.parentNode.parentNode;
			break;
		}
	}
	var newDiv = document.createElement("div");
	newDiv.setAttribute("id","heatSubDiv"+randomId);
	newDiv.innerHTML = sourceDiv.innerHTML;
	G("productHeatDiv").appendChild(newDiv);
	G("heatSubDiv"+randomId).addClass("mess-query");
	G("heatSubDiv"+randomId).addClass("air");
	var newSelect = G("heatSubDiv"+randomId).getElementsByTagName("select");
	for(var i=0,tempLen=newSelect.length;i<tempLen;i++){
		newSelect[i].selectedIndex = 0;
		if(newSelect[i].name == "orderInfoDto.heatAreaDto.coopTimeType")
			newSelect[i].id = "coopTypeList" + randomId;
		newSelect[i].removeAttribute("wrapVersion");
	}
	var newInput = G("heatSubDiv"+randomId).getElementsByTagName("input");
	for(var i=0,tempLen=newInput.length;i<tempLen;i++){
		if(newInput[i].type=="text" || newInput[i].type=="hidden"){
			newInput[i].value = "";
		}
		if(newInput[i].name == "orderInfoDto.heatAreaDto.popWord")
			newInput[i].setAttribute("invalid","heatAreaDtopopWordErrSpan"+randomId);
		else if(newInput[i].name == "orderInfoDto.heatAreaDto.coopTime"){
			newInput[i].setAttribute("invalid","orderInfoDtoheatAreaDtocoopTimeErrSpan"+randomId);
			newInput[i].disabled = false;
			newInput[i].value = "";
		}else if(newInput[i].name == "orderInfoDto.heatAreaDto.popPosition")
			newInput[i].setAttribute("invalid","heatAreaDtopopPositionErrSpan"+randomId);
	}
	var newTextArea = G("heatSubDiv"+randomId).getElementsByTagName("textarea");
	for(var i=0,tempLen=newTextArea.length;i<tempLen;i++){
		newTextArea[i].value = "";
		if(newTextArea[i].name == "orderInfoDto.heatAreaDto.popDesc")
			newTextArea[i].setAttribute("invalid","heatAreaDtopopDescErrSpan"+randomId);
	}
	var newSpan = G("heatSubDiv"+randomId).getElementsByTagName("span");
	for(var i=0,tempLen=newSpan.length;i<tempLen;i++){
		if(newSpan[i].getAttribute("spanname")=="heatAreaDtopopWordErrSpan"){
			newSpan[i].setAttribute("id","heatAreaDtopopWordErrSpan"+randomId);
			newSpan[i].innerHTML = "";
		}else if(newSpan[i].getAttribute("spanname")=="orderInfoDtoheatAreaDtocoopTimeErrSpan"){
			newSpan[i].setAttribute("id","orderInfoDtoheatAreaDtocoopTimeErrSpan"+randomId);
			newSpan[i].innerHTML = "";
		}else if(newSpan[i].getAttribute("spanname")=="heatAreaDtopopPositionErrSpan"){
			newSpan[i].setAttribute("id","heatAreaDtopopPositionErrSpan"+randomId);
			newSpan[i].innerHTML = "";
		}else if(newSpan[i].getAttribute("spanname")=="heatAreaDtopopDescErrSpan"){
			newSpan[i].setAttribute("id","heatAreaDtopopDescErrSpan"+randomId);
			newSpan[i].innerHTML = "";
		}
	}
	var newALink = G("heatSubDiv"+randomId).getElementsByTagName("a");
	if(newALink.length>0){
		newALink[0].onclick = function(){
			removeHeatDiv(randomId);
			return false;
		}
	}
	return false;
}
function removeHeatDiv(myId){
	//删除火爆地带的一条
	var heatNum = G("productHeatDiv").getElementsByTagName("a").length - 1;
	if(heatNum<=1){
		alert("您不能删除最后一条记录");
	}else if(myId.length <= 0){
		alert("对不起，这条记录不能删除");
	}else
		G("heatSubDiv"+myId).remove();
	return false;
}
function payTypeComment(target,source){
	if(source.options.length == 0){
		target.innerHTML = "";
		return false;
	}
	target.innerHTML = source.options[source.selectedIndex].text;
	Event.add(source,"change",function(){
		target.innerHTML = source.options[source.selectedIndex].text;
	});
}
function contractProperty1Click(){
	//点击合同范本radio
	G("contractProperty2Reason").hide();
	G("contractProperty2Reason").removeAttribute("minLen");
	G("contractProperty2Reason").removeAttribute("maxLen");
	G("contractProperty2ReasonErrSpan").hide();
}
function contractProperty2Click(){
	//点击合同非范本radio
	G("contractProperty2Reason").show();
	G("contractProperty2Reason").setAttribute("minLen","1");
	G("contractProperty2Reason").setAttribute("maxLen","50");
	G("contractProperty2ReasonErrSpan").show();
}
function checkAplusB(){
	var Asum = G("paySumA").value.trim();
	var Bsum = G("paySumB").value.trim();
	var totalsum = G("financeSumSpan").innerHTML;
	if(Asum.length == 0 && Bsum.length == 0)
		return "";
	else if(!Bsum.match(/^\d+(\.\d{1,2})?$/))
		return "必须是数字（小数点后最多两位）";
	else if(parseFloat(Asum)+parseFloat(Bsum)!=parseFloat(totalsum))
		return "A、B单的金额总数必须等于发票金额";
	else
		return "";
}
function isNotABClick(){
	//点击非AB单radio时
	G("payTypeSingle").show();
	G("payTypeA").hide();
	G("payTypeB").hide();
	G("paySumA").removeAttribute("pattern");
	G("paySumB").removeAttribute("custom");
}
function isABClick(){
	//点击AB单radio时
	G("payTypeSingle").hide();
	G("payTypeA").show();
	G("payTypeB").show();
	G("paySumA").setAttribute("pattern","\\d*(\\.\\d{0,2})?");
	G("paySumB").setAttribute("custom","checkAplusB");
}
function SelectCheck(obj){
	//校验下拉框必须选择
	if(obj.options[obj.selectedIndex].value == "")
		return "必选";
	else
		return "";
}
function mustSelectContractArea(){
	//合同地域必选
	return SelectCheck(G("contractArea"));
}
function mustSelectOrderSource(){
	//订单来源必选
	return SelectCheck(G("orderSource"));
}

function mustSelectPopType(){
	//合同推广选项必选
	return SelectCheck(G("popType"));
}

function RadioCheck(){
	//radio必选
	for(var i=0,tempLen=arguments.length;i<tempLen;i++){
		if(arguments[i].checked){
			return "";
		}
	}
	return "必选";
}
function mustSelectContractType(){
	//合同类型必选
	return RadioCheck(G("contractType1"),G("contractType2"));
}
function mustSelectContractProperty(){
	//合同性质必选
	return RadioCheck(G("contractProperty1"),G("contractProperty2"));
}
function mustSelectIsNeedGift(){
	//是否申领礼品必选
	return RadioCheck(G("isNeedGift1"),G("isNeedGift0"),G("isNeedGift2"));
}
function mustSelectContractVersion(){
	//合同版本必选
	var radios = G("ContractVersionTd").getElementsByTagName("input");
	var tempLen = radios.length;
	for(var i=0;i<tempLen;i++){
		if(radios[i].checked){
			return "";
		}
	}
	return "必选";
}
function mustSelectContractGetMethod(){
	//合同领取方式必选
	return RadioCheck(G("contractgetMethod1"),G("contractgetMethod2"));
}
function mustSelectContractGetMethodDetail(){
	//合同领取方式，选快递的时候必须选一个收件人
	if(G("contractgetMethod2").checked){
		var inputs = G("dataList3").getElementsByTagName("input");
		var radios = [];
		for(var i=0,tempLen=inputs.length;i<tempLen;i++){
			if(inputs[i].type == "radio")
				radios[radios.length] = inputs[i];
		}
		for(var i=0,tempLen=radios.length;i<tempLen;i++){
			if(radios[i].checked){
				return "";
			}
		}
		return "必选一个收件人";
	}
	return "";
}
function mustSelectFinanceGetMethodDetail(){
	//财务信息，票据派出方式，选快递的时候必须选一个收件人
	if(G("financegetMethod2").checked){
		var inputs = G("dataList4").getElementsByTagName("input");
		var radios = [];
		for(var i=0,tempLen=inputs.length;i<tempLen;i++){
			if(inputs[i].type == "radio")
				radios[radios.length] = inputs[i];
		}
		for(var i=0,tempLen=radios.length;i<tempLen;i++){
			if(radios[i].checked){
				return "";
			}
		}
		return "必选一个收件人";
	}
	return "";
}
function mustSelectGiftGetMethodDetail(){
	//礼品信息，送出方式，选快递的时候必须选一个收件人
	if(G("giftgetMethod2").checked){
		var inputs = G("dataList7").getElementsByTagName("input");
		var radios = [];
		for(var i=0,tempLen=inputs.length;i<tempLen;i++){
			if(inputs[i].type == "radio")
				radios[radios.length] = inputs[i];
		}
		for(var i=0,tempLen=radios.length;i<tempLen;i++){
			if(radios[i].checked){
				return "";
			}
		}
		return "必选一个收件人";
	}
	return "";
}
function mustSelectReceiptType(){
	//收款类型必选
	return RadioCheck(G("isNeedReceipt0"),G("isNeedReceipt1"),G("isNeedReceipt2"));
}
function floatApplyThisGift(objInput){
	var thisId = objInput.value;
	var thisApplyInput = G("floatGiftApply"+thisId);
	if(objInput.checked)
		thisApplyInput.disabled = false;
	else{
		thisApplyInput.disabled = true;
		thisApplyInput.value = "0";
		thisApplyInput.style.backgroundColor = "#FFFFFF";
	}
}
function clearTable5(){
	//修改礼品申领地点，应当清空已经选择了的礼品列表
	G("dataList5").setData([]);
}

function getLastGiftApply(giftId){
	//将主页面的申领数带到浮动层
	var item = G("mainGiftApply"+giftId);
	if(item){
		return item.getAttribute('applyednum').trim();
	}else
		return "0";
}

function getGiftApply(giftId){
	//将主页面的申领数带到浮动层
	if(G("mainGiftApply"+giftId)){
		return G("mainGiftApply"+giftId).value
	}else
		return "0";
}
function getGiftCheckBox(giftId){
	//如果主页面存在申领数，则浮动层上选中
	if(giftId.length>0 && G("mainGiftApply"+giftId) && parseInt(G("mainGiftApply"+giftId).value,10)>0){
		return "checked";
	}else
		return " ";
}
function getGiftDisabled(giftId){
	if(giftId.length>0 && G("mainGiftApply"+giftId) && parseInt(G("mainGiftApply"+giftId).value,10)>0 ){
		return "";
	}else
		return "disabled";
}
function checkApplyNumber(giftId,applyObj){
	//申领数不能大于库存数
	var storageNum = parseInt(G("floatGiftStorage"+giftId).innerHTML,10);
	if(!applyObj.value.match(/^\d+$/ig)){
		alert("申领数必须是数字");
		applyObj.style.backgroundColor = "#FF0000";
		return false;
	} /*else if(parseInt(applyObj.value,10) > (storageNum + parseInt(applyObj.getAttribute('applyedNum'),10)) ){
		alert("库存数不够");
		applyObj.style.backgroundColor = "#FF0000";
		return false;
	} */
	
	else if(parseInt(applyObj.value,10)<=0){
		alert("申领数必须是正数");
		applyObj.style.backgroundColor = "#FF0000";
		return false;
	}else{
		applyObj.style.backgroundColor = "#FFFFFF";
		return true;
	}
}
function getGiftList(urlHead){
	var url = urlHead + "/order/gift/getGiftListByAddr.action";
	//var url = "gift/getGiftListByAddr.action";
	
	//首先判断是否已选择申领地点
	if(G('giftApplyAddress').value == ''){
		//alert("请先选择申领地点!");
		G('giftListDivTip').innerHTML = '请先选择申领地点';
		return false;
	}

	var postData = "ad="+ G('giftApplyAddress').options[G('giftApplyAddress').selectedIndex].value;
	var ajax = new Ajax(function(responseData){
		if(!responseData) return false;
		var jsonData = eval( "(" + responseData  + ")" );
		if(jsonData.flag!=0){
			alert(jsonData.error);
		}else{
			G("dataList6").setData([]);
			G("giftStorageListExt").show();
			var listData = jsonData.giftList;
			for(var i=0;i<listData.length;i++){
				var giftApplyed = getGiftApply(listData[i].giftId);
				var giftLastApplyed = getLastGiftApply(listData[i].giftId);
				//组装弹出层
				var item1 = '<input type="checkbox" value="'+listData[i].giftId+'" id="floatGiftCheckBox'+listData[i].giftId+'" onclick="floatApplyThisGift(this)" '+getGiftCheckBox(listData[i].giftId)+'>';
				var item2 = '<span id="floatGiftType'+listData[i].giftId+'">'+listData[i].giftType+'</span>';
				var item3 = '<span id="floatGiftName'+listData[i].giftId+'">'+listData[i].giftName+'</span>';
				var item4 = '<span id="floatGiftStorage'+listData[i].giftId+'">'+listData[i].giftStorage+'</span>';
				var item5 = '<input type="input" applyednum="'+ giftLastApplyed +'"value="'+giftApplyed+'" size="2" id="floatGiftApply'+listData[i].giftId+'" style="border:1px solid #7A7CED;" '+getGiftDisabled(listData[i].giftId)+'>';
				G("dataList6").add([item1,item2,item3,item4,item5]);
			}
			if(40 + 26*listData.length < 270)
				G("dataList6").parentNode.style.height = 40 + 26*listData.length + "px";
			else
				G("dataList6").parentNode.style.height = "270px";
			var mainList = G("dataList5").getElementsByTagName("input");
			for(var i=0,tempLen=mainList.length;i<tempLen;i++){
				//将原有列表的值反映到弹出层上
				if(mainList[i].name=="giftId"){
					var tempId = mainList[i].value;
					G("floatGiftCheckBox"+tempId).checked = true;
					G("floatGiftApply"+tempId).disabled = false;
					G("floatGiftApply"+tempId).value = G("mainGiftApply"+tempId).value;
				}
			}
		}
	},function(){
		alert("对不起，系统错误，请稍候再试！");
	});
	ajax.post(url,postData);
}
function confirmGiftApply(){
	//将选择的礼品由浮动层返回订单
	G("dataList5").setData([]);
	var applyList = G("dataList6").getElementsByTagName("input");
	var selectCount = 0;
	for(var i=0,tempLen=applyList.length;i<tempLen;i++){
		if(applyList[i].type=="checkbox" && applyList[i].checked){
			var tempId = applyList[i].value;
			
			
			if(!checkApplyNumber(tempId,G("floatGiftApply"+tempId)))
				return false;
			
			
			var item1 = '<input type="hidden" value="'+tempId+'" name="giftDto.giftApplyId" id="mainGiftId'+tempId+'">'+G("floatGiftType"+tempId).innerHTML;
			var item2 = G("floatGiftName"+tempId).innerHTML;
			var item3 = G("floatGiftStorage"+tempId).innerHTML;
			var item4 = '<input type="hidden" applyedNum="'+G("floatGiftApply"+tempId).getAttribute('applyednum')+'" value="'+G("floatGiftApply"+tempId).value+'" name="giftDto.giftApplySum" id="mainGiftApply'+tempId+'">'+G("floatGiftApply"+tempId).value;
			G("dataList5").add([item1,item2,item3,item4]);
			selectCount++;
		}
	}
	G("dataList5").parentNode.style.height = 48 + 26*selectCount + "px";
	G("giftStorageListExt").hide();
}

function bySelf(table){
	//自行取合同
	G(table + "WrapPanel").hide();
}
function makeReceiverList(panel){
	G(panel + "WrapPanel").show();
}
function getModifiedValue(modify,itemName,itemId){
	if(!modify)
		return "";
	else if(G(itemName+itemId))
		return G(itemName+itemId).innerHTML.replace(/(&quot;|")/g, "&#34;");
	else
		return "";
}
function getModifiedPhoneValue(modify,itemName,itemId,detailId){
	if(!modify)
		return "";
	else if(G(itemName+itemId+detailId))
		return G(itemName+itemId+detailId).innerHTML;
	else
		return "";
}
function newContactText(myTable,myNum){
	//点击“新增收件人”链接
	if(custListCount>=10){
		alert("最多只能10个收件人");
		return false;
	}
	createReceiverList(false,'',myNum);
	G(myTable+"AddDivWrap").show();
	G(myTable+"IdDiv").hide();
	G(myTable+"QuestionDiv").hide();
	return false;
}


function changeDays(obj){
	//火爆地带，合作期限联动
	var targetInput = obj.parentNode.getElementsByTagName("input")[0];
	targetInput.value = obj.options[obj.selectedIndex].value;
	if(obj.options[obj.selectedIndex].value == "-1"){
		targetInput.value = "";
		targetInput.removeAttribute('readOnly');
		
	}else {
		targetInput.setAttribute('readOnly','readOnly');
	}
}

function editItem(listId){
	var item1 = G("listUserAccount"+listId).innerHTML + '<input type="hidden" name="id" value="'+G("listCheckBox"+listId).value+'"><input type="hidden" name="userId" value="'+G("listUserId"+listId).value+'">';
	var item2 = G("listUserName"+listId).innerHTML + '<input type="hidden" name="roleTag" value="'+G("listRoleTag"+listId).value+'">';
	var item3 = G("listAuditDepts"+listId).innerHTML;
	G("dataList2").setData([]);
	G("dataList2").add([item1,item2,item3]);
}
function batchEditItem(){
	G("dataList2").setData([]);
	var sourceList = G("dataList").getElementsByTagName("input");
	var checkCount = 0;
	for(var i=0,tempLen=sourceList.length;i<tempLen;i++){
		if(sourceList[i].type=="checkbox" && sourceList[i].checked){
			checkCount++;
			var tempUserIdRoleTag = sourceList[i].id.substr(12,sourceList[i].id.length-12);
			var tempId = sourceList[i].value;
			var item1 = G("listUserAccount"+tempUserIdRoleTag).innerHTML + '<input type="hidden" name="id" value="'+tempId+'"><input type="hidden" name="userId" value="'+G("listUserId"+tempUserIdRoleTag).value+'">';
			var item2 = G("listUserName"+tempUserIdRoleTag).innerHTML + '<input type="hidden" name="roleTag" value="'+G("listRoleTag"+tempUserIdRoleTag).value+'">';
			var item3 = G("listAuditDepts"+tempUserIdRoleTag).innerHTML;
			G("dataList2").add([item1,item2,item3]);
		}
	}	
	G("dataList2").parentNode.style.height = 46 + 26*checkCount + "px";
}
function unCheckParent(obj){
	if(obj.parentNode == G("floatOrgList") || obj.parentNode.parentNode == G("floatOrgList"))
		return false;
	var parentInput = obj.parentNode.parentNode.getElementsByTagName("input")[0];
	parentInput.checked = false;
	unCheckParent(parentInput);
}
function orgCheck(orgId,obj){
	var inputList = G("orgId"+orgId).getElementsByTagName("input");
	var isCheck = obj.checked;
	if(!isCheck)
		unCheckParent(obj);
	for(var i=0,tempLen=inputList.length;i<tempLen;i++){
		inputList[i].checked = isCheck;
	}
}
var resultList = [];
function getTargetDiv(img){	
	if(img.nextSibling!=null){
		var tempNode = img.nextSibling;
		if(tempNode.tagName && tempNode.tagName.toUpperCase() == "DIV")
			resultList[resultList.length] = tempNode;
		getTargetDiv(tempNode);
	}
}
function changeListShow(img){
	resultList = [];
	getTargetDiv(img);
	for(var i=0,tempLen=resultList.length;i<tempLen;i++){
		if(resultList[i].style.display != "none"){
			resultList[i].style.display = "none";
			img.src = addPic;
		}else{
			resultList[i].style.display = "block";
			img.src = removePic;
		}
	}
}
function resetOrgList(){
	var allInput = G("floatOrgList").getElementsByTagName("input");
	for(var i=0,tempLen=allInput.length;i<tempLen;i++){
		if(allInput[i].type == "checkbox")
			allInput[i].checked = false;
	}
}
function isLeaf(objCheckBox){
	if(objCheckBox.parentNode.getElementsByTagName("input").length <= 1)
		return true;
	else
		return false;
}
function confirmOrgList(){
	G("dataList3").setData([]);
	var allInput = G("floatOrgList").getElementsByTagName("input");
	var orgCount = 0;
	for(var i=0,tempLen=allInput.length;i<tempLen;i++){
		if(allInput[i].type == "checkbox" && allInput[i].checked && isLeaf(allInput[i])){
			orgCount++;
			var tempOrgId = allInput[i].value;
			var item1 = G("orgName"+tempOrgId).innerHTML;
			G("dataList3").add([item1]);
		}
	}
	G("dataList3").parentNode.style.height = 46 + 26*orgCount + "px";
	Popup.hide(G("floatOrgListWrap"));
}
function changeToArray(list){
	if(list==null)
		return [];
	var tempArray = [];
	for(var i=0,tempLen=list.length;i<tempLen;i++){
		tempArray[tempArray.length] = list[i];
	}
	return tempArray;
}

function encodeHTML(me){
    return me.replace(/&/g,'&amp;')
             .replace(/</g,'&lt;')
             .replace(/>/g,'&gt;')
             .replace(/"/g, "&quot;");
}


function createHiddenInput(sourceDiv,isHidden){
	var sourceDiv = G(sourceDiv);
	if(!sourceDiv)
		return false;
	var allInputs = [];
	var allInputs1 = changeToArray(sourceDiv.getElementsByTagName("input"));
	var allInputs2 = changeToArray(sourceDiv.getElementsByTagName("select"));
	var allInputs3 = changeToArray(sourceDiv.getElementsByTagName("textarea"));
	allInputs = allInputs.concat(allInputs1,allInputs2,allInputs3);
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		var item = allInputs[i];
		if(item.type=="radio" && !item.checked) {
			continue;
		}
		if(item.type=="hidden" && !isHidden) {
			continue;
		}
		if(item.name.length <= 0) {
			continue;
		}
	
		var tempSpan = document.createElement('span');
		tempSpan.innerHTML = '<input type="hidden" name="' + item.name + '" value="'+ encodeHTML(item.value) +'" />';
		
		/* ie下创建input会产生bug
		var tempInput = document.createElement("input");
		tempInput.type = "hidden";
		tempInput.name = allInputs[i].name;
		tempInput.value = allInputs[i].value;
		*/
		
		document.forms["hiddenForm"].appendChild(tempSpan);
	}
}

function getRadioValue(myRadio){
	for(var i=0,tempLen=myRadio.length;i<tempLen;i++){
		if(myRadio[i].checked)
			return myRadio[i].value;
	}
}
function removeSubmitHidden(){
	var allHiddens = document.forms["hiddenForm"].getElementsByTagName("input");
	for(var i=0,tempLen=allHiddens.length; i<tempLen; i++){
		if(allHiddens[i].type == "hidden") {
			G(allHiddens[i]).remove();
		}
	}
}


function allowFinance(targetDiv){
	//选择提前开发票、提前开收款证明，财务信息可填
	var allInputs = [];
	var allInputs1 = changeToArray(G(targetDiv).getElementsByTagName("input"));
	var allInputs2 = changeToArray(G(targetDiv).getElementsByTagName("select"));
	var allInputs3 = changeToArray(G(targetDiv).getElementsByTagName("textarea"));
	allInputs = allInputs.concat(allInputs1,allInputs2,allInputs3);
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		allInputs[i].disabled = false;
	}
	
}
function receiptAfter(targetDiv){
	//点击后开发票
	var allInputs = [];
	var allInputs1 = changeToArray(G(targetDiv).getElementsByTagName("input"));
	var allInputs2 = changeToArray(G(targetDiv).getElementsByTagName("select"));
	var allInputs3 = changeToArray(G(targetDiv).getElementsByTagName("textarea"));
	allInputs = allInputs.concat(allInputs1,allInputs2,allInputs3);
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		var item = allInputs[i];
		
		/*收款类型和发票抬头不能disabled*/
		if(item.name == "financeDto.receiptInfo.receiptType" || item.name == "financeDto.receiptInfo.receiptTitle"){
			continue;
		}
		
		if(item.type == "text" || item.type == "hidden") {
			//allInputs[i].value = "";
		} else if(item.type == "select-one") {
			item.selectedIndex = 0;
		} else if(item.name == "financeDto.receiptInfo.isAb"){
			item.checked = false;
		}
		
		isNotABClick();	//是否AB单不再选择，所以执行一下选否的操作
		item.disabled = true;
	}
	//以下处理联动下拉框，清空
	G("payTypeSelectedSingle").innerHTML = "";
	G("payTypeSelectedA").innerHTML = "";
	G("payTypeSelectedB").innerHTML = "";
	G("payTypeCommentSingle").innerHTML = "";
	G("payTypeCommentA").innerHTML = "";
	G("payTypeCommentB").innerHTML = "";
	
	
	G("financegetMethod2").checked = true; //只能选快递
	makeReceiverList("finance");		//点击快递时执行的函数，这里需要执行一下
/*
	if(!custListFlag) {
		newContactText("finance",4);	//如果没有收件人，则不用选择是否，直接添加收件人
	}
*/
	var allInputs4 = G("financeRecieverTabWrap").getElementsByTagName("input");
	for(var i=0,tempLen=allInputs4.length;i<tempLen;i++){
		allInputs4[i].disabled = false;
	}
	
	//激活财务收件人编辑框
	var fefm = new FormMgr(G('financeEditPanel'));
	fefm.enableAll();
}
function submitForm(url,customValidFn,clearFn){
	/*自定义验证函数*/
	var customValidFn = customValidFn || function() {return true};
	var clearFn = clearFn || function() {return true;};
	
	if(url=="")
		return false;
	document.forms["hiddenForm"].action = url;
	//设置时间框
	try{
		if(G("contractexpectedGetDateYYMMDD").value.length==0 && G("contractexpectedGetDateHHMMSS").value.length==0)
			G("contractexpectedGetDateHidden").value = "";
		else
			G("contractexpectedGetDateHidden").value = G("contractexpectedGetDateYYMMDD").value + " " + G("contractexpectedGetDateHHMMSS").value;
		if(G("receiptInfoprecontractTimeYYMMDD").value.length==0 && G("receiptInfoprecontractTimeHHMMSS").value.length==0)
			G("receiptInfoprecontractTimeHidden").value = "";
		else
			G("receiptInfoprecontractTimeHidden").value = G("receiptInfoprecontractTimeYYMMDD").value + " " + G("receiptInfoprecontractTimeHHMMSS").value;
	}catch(e){
		//do nothing
	}
	
	G('mettingTime').value = G('mettingTime_date').value.trim() +" " + G('mettingTime_time').value.trim();

	if(Validate.validElement(G('makeOrderMain')) && customValidFn()){
		
		//caculateFav();	//再计算一次优惠金额，这样比较保险，因为valid函数先trim过了
		removeSubmitHidden();	//先删除所有的hidden，再逐一加入
		
		createHiddenInput("orderSourceDiv",true);
		createHiddenInput("orderInfoDtoproductSelectedP",true);
		createHiddenInput("productSearchDiv",true);
		createHiddenInput("productHeatDiv",true);
		createHiddenInput("productNetUnionDiv",true);
		
		
		createHiddenInput("contractDtoTable",true);
		createHiddenInput("receiveRecieverTabWrap",false);
		createHiddenInput("hiddenContractFile",true);
		
		createHiddenInput("financeDtoTable",true);
		createHiddenInput("financeDtoTablePart2",true);
		
		//如果选择提前开收款证明，需要发收件人
		if(G('isNeedReceipt1').checked){
			createHiddenInput("anotherReceiverRecieverTabWrap",true);
		}
		
		createHiddenInput("financeDtoTable2",true);
		createHiddenInput("financeRecieverTabWrap",false);
		
		if(G("isAB0").checked)
			createHiddenInput("payTypeSingle",true);
		else {
			createHiddenInput("payTypeA",true);
			createHiddenInput("payTypeB",true);
		}
		createHiddenInput("hiddenFinanceFile",true);
		createHiddenInput("financeAuditDiv",true);
		
		
		createHiddenInput("giftDtoTable1",true);
		createHiddenInput("giftDtoTable2",true);
		createHiddenInput("giftListDiv",true);
		createHiddenInput("giftDtoTable3",true);
		createHiddenInput("giftRecieverTabWrap",false);
		
		//开户信息
		if(G('accountInfoModifiedDiv')) {
			createHiddenInput("accountInfoModifiedDiv",true);
		}
		
		createHiddenInput("orderInfoDtoComment",true);
		
		clearFn(); //运行清理函数
		
		
		
		document.forms["hiddenForm"].submit();
		
	}else
		return false;
}


function showModifiedInfo(objId,trigger){
	var obj = G(objId);
	var tip = trigger && trigger.getElementsByTagName('span')[0];
	
	if(obj.style.display == "none" || !obj.style.display) {
		obj.show('block');
		if (tip) {
			tip.className = 'unfold';
			tip.title = '点击折叠';
		}
	} else {
		obj.hide('none');
		if (tip) {
			tip.className = 'fold';
			tip.title = '点击展开';
		}
	}
	return false;
}
function disableElement(targetDiv){
	var targetDiv = G(targetDiv);
	if (!targetDiv) {
		return;
	}
	//驳回订单，让元素不可用
	var allInputs = [];
	var allInputs1 = changeToArray(targetDiv.getElementsByTagName("input"));
	var allInputs2 = changeToArray(targetDiv.getElementsByTagName("select"));
	var allInputs3 = changeToArray(targetDiv.getElementsByTagName("textarea"));
	allInputs = allInputs.concat(allInputs1,allInputs2,allInputs3);
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		allInputs[i].disabled = true;
	}
	var allA = targetDiv.getElementsByTagName("a");
	for(var i=0,tempLen=allA.length;i<tempLen;i++){
		allA[i].onclick = function(){return false;}
	}
}
function setTargetId(checkBoxObj){
	//黑名单、老客户对比的时候，设置传给rd的id
	G("custCompareQueryDetailButton").setAttribute("targetId",checkBoxObj.value);
}
function custCompareChange(obj){
	//黑名单、老客户对比的时候，如果是固定电话，则分成两个框
	if(obj.options[obj.selectedIndex].value == "2"){
		G("custCompareSpan1").hide();
		G("custCompareSpan2").show();
	}else{
		G("custCompareSpan1").show();
		G("custCompareSpan2").hide();
	}
	//三个输入框都清空
	G("custCompareQueryValue").value = "";
	G("custCompareQueryPhone1").value = "";
	G("custCompareQueryPhone2").value = "";
}
function custQuery(urlHead){
	
	G("custCompareQueryDetailButton").setAttribute("targetId","");	//点查询的时候清空targetId
	
	var url = urlHead + "/order/bloc.action";
	var postData = "";
	
	if(G("custCompareQueryName").options[G("custCompareQueryName").selectedIndex].value == "2"){
		//如果是固定电话，则拼装传给rd
		var phone1 = G("custCompareQueryPhone1");
		var phone2 = G("custCompareQueryPhone2");
		if(phone1.value.length<=0){
			alert("电话区号必填");
			return;
		}else if(!phone1.value.match(/^0\d{2,3}$/ig)){
			alert("电话区号长度只能3位或者4位，并且以0开头");
			return;
		}else if(phone2.value.length<=0){
			alert("电话号码必填");
			return;
		}else if(!phone2.value.match(/^\d{7,8}$/ig)){
			alert("电话号码只能是7位或者8位的数字");
			return;
		}
		G("custCompareQueryValue").value = phone1.value + "-" + phone2.value
	}
	
	if(G("custCompareQueryValue").value.trim().length<=0){
		alert("请输入查询内容");
		G("custQueryResultList").hide();
		G("dataList3").setData([]);
		return false;
	}
	
	postData += "value=" + encodeURIComponent(G("custCompareQueryValue").value.trim());
	postData += "&type=" + G("custCompareQueryName").options[G("custCompareQueryName").selectedIndex].value;

	var ajax = new Ajax(function(responseData){
		if(!responseData) return false;
		var jsonData = eval( "(" + responseData  + ")" );
		if(jsonData.flag!=0){
			alert(jsonData.error)
		}else{
			var listData = jsonData.searchResult;
			G("dataList3").setData([]);
			if(listData.length>0){
				for(var i=0,tempLen3=listData.length;i<tempLen3;i++){
					var item1 = "";
					var item2 = "";
					if(listData[i].source == "0"){
						item1 = "&nbsp;"
						item2 = "黑名单";
					}else if(listData[i].source == "1"){
						item1 = '<input type="radio" value="'+listData[i].custId+'" onclick="setTargetId(this)" name="custQueryCheckBox">';
						item2 = "老客户";
					}else if(listData[i].source == "2"){
						item1 = "&nbsp;"
						item2 = "老客户";
					}else if(listData[i].source == "3"){
						item1 = '<input type="radio" value="'+listData[i].custId+'" onclick="setTargetId(this)" name="custQueryCheckBox">';
						item2 = "黑名单";
					}
					var item3 = listData[i].custTypeName;
					var item4 = listData[i].custName;
					var item5 = listData[i].contactName;
					var item6 = "";
					var item7 = "";
					var tempList = listData[i].phoneList;
					var tempCount1 = 0;
					var tempCount2 = 0;
					for(var j=0,tempLen4=tempList.length;j<tempLen4;j++){
						if(tempList[j].phoneType=="0"){
							if(tempList[j].phone1.length>0){
								item6 += tempList[j].phone1+'-';
							}
							item6 += tempList[j].phone2;
							if(tempList[j].phone3.length>0){
								item6 += '-'+tempList[j].phone3;
							}
							item6 += ' ';
							tempCount1++;
						}else if(tempList[j].phoneType=="1"){
							item7 += tempList[j].phone2 + ' ';
							tempCount2++;
						}
					}
					G("dataList3").add([item1,item2,item3,item4,item5,item6,item7]);
				}
				G("custQueryResultList").show();
				G("dataList3").parentNode.style.height = 46 + 26*listData.length + "px";
			}else{
				G("custQueryResultList").hide();
				alert("没有查到任何结果");
			}
		}
	},function(){
		alert("对不起，系统错误，请稍候再试！");
	});
	ajax.post(url,postData);
}
function custCompare(custId,urlHead){
	if(custId.length<=0){
		alert("请选择一条信息进行详细对比");
		return false;
	}
	var url = urlHead + "/order/blocDetail.action";
	var postData = "customerId=" + custId;
	var ajax = new Ajax(function(responseData){
		if(!responseData) return false;
		G("compareTarget").innerHTML = responseData;
		G("comparePanel").show();
		G("custCompareQueryName").style.visibility = "hidden";
		G("reasonSelect").style.visibility = 'hidden';
	},function(){
		alert("对不起，系统错误，请稍候再试！");
	});
	ajax.post(url,postData);
}

function listStatus(target,obj){
	//控制下拉列表是否能选
	G(target).disabled = !obj.checked;
}

function enableornot(target,obj){
	//控制下拉列表是否能选
	if (obj.checked) {
		MutilpeSelector.enable(target);
	} else {
		MutilpeSelector.disable(target);
	}

}
function setItem(items,obj){
	//设置关联item的显示、隐藏
	var itemsList = items.split(",");
	if(obj.checked){
		for(var i=0,tempLen=itemsList.length;i<tempLen;i++){
			if(G("itemSpan"+itemsList[i]))
				G("itemSpan"+itemsList[i]).show();
		}
	}else{
		for(var i=0,tempLen=itemsList.length;i<tempLen;i++){
			if(G("itemSpan"+itemsList[i])){
				G("itemSpan"+itemsList[i]).hide();
				G("itemInput"+itemsList[i]).checked = false;
			}
		}
	}
}
function selectAllItem(obj,notControl){
	//报表全选checkbox
	var allCheckbox = G("itemListDiv").getElementsByTagName("input");
	var notControlItem = notControl.split(",");
	for(var i=0,tempLen=allCheckbox.length;i<tempLen;i++){
		if(allCheckbox[i].type == "checkbox"){
			if (allCheckbox[i].parentNode.className.indexOf('mutilpe-selector-item') != '-1') {
				continue; //忽略多选下拉框里的checkbox
			}
			
			var setFlag = true;
			for(var j=0; j<notControlItem.length; j++){
				if("itemInput" + notControlItem[j].toString() == allCheckbox[i].id 
				   && G("itemSpan"+notControlItem[j].toString()).style.display == "none" 
				) {
					setFlag = false;
				}
			}
			if(setFlag) {
				allCheckbox[i].checked = obj.checked;
			}
		}
	}
	if (G("contractStatusList")) {
		enableornot("contractStatusList",obj);	//最后控制下拉列表的状态
		enableornot('contractDeductStatusList',obj);
	}
	
	if(G("financeStatusList"))
		enableornot("financeStatusList",obj);
	if(G("giftStatusList"))
		enableornot("giftStatusList",obj);
	if(G("orderStatusList"))
		enableornot("orderStatusList",obj);
}

function getMutilpeSelectorValue(item) {
	var str = [],
		relativeItem = G(item.getAttribute("relation"));
		
		str.push(
			"&item=",item.getAttribute("itemId")
		);
	
	//如果relativeItem是select
	if (relativeItem.tagName.toLowerCase() == 'select') {
		str.push(
			"&condi=",item.value,	
			'&condiValue=',relativeItem.value,'&valueNum=1'
		);
		return dataStr.join('');
	}
	
	 var inputs = relativeItem.getElementsByTagName('input'),
	 	 valueNum = 0,
	 	 oneItemSelected = false,
	 	 selectedAll = false;

	 for (var i = 0, len = inputs.length; i < len; i++) {
		 var chkbox = inputs[i];
		 if (chkbox.type == 'checkbox') {
			 if (chkbox.checked) {
				 oneItemSelected = true;
				 if (chkbox.value == '-1') {
					 selectedAll = true;
					 break;
				 } else {
					 str.push("&condiValue="+chkbox.value);
					 valueNum += 1;
				 }
				 
			 }
		 }
	 }
	 
	 //至少选择了1项且不是全部
	 if (oneItemSelected && !selectedAll) {
		 str.push(
			 "&condi=",item.value || item.getAttribute('condi'),
			 '&valueNum=',valueNum
		 );
	 } 
	 
	 return str.join('');
	
}

function createReport(cur_page_num,btn){
	//生成报表
	if(!cur_page_num.toString().trim().match(/^[1-9]+\d*$/ig)){
		alert("请输入正整数");
		return false;
	}
	
	var postData = "pageData.cur_page_num=" + cur_page_num.toString().trim();
	var url = G("postURL").value;
	
	var hasChecked = false;	//是否有选项被选中
	
	//如果有设置产品范围
	if(G("conditionListDiv")){
		var allInputs = G("conditionListDiv").getElementsByTagName("input");
		var hasSelected = 0;
		for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
			if(allInputs[i].type=="checkbox" && allInputs[i].checked && allInputs[i].name=="conditionList"){
				hasSelected++;
				postData += "&condiValue=" + allInputs[i].value;
			}
		}
		if(hasSelected>0){
			var productTypeHidden = G('productTypeHidden');
			postData += [
			             	"&condi=",productTypeHidden.getAttribute("condi"),
			             	"&item=",productTypeHidden.getAttribute("itemId"),
			             	"&valueNum=",hasSelected
			             ].join('');
			hasChecked = true;
		}
	}
	//遍历列表项
	var allInputs = G("itemListDiv").getElementsByTagName("input");
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		var item = allInputs[i];
		
		if(item.type=="checkbox" && item.checked){
			if(item.name=="itemList"){
				postData += "&item=" + item.value;
				hasChecked = true;
			}else if(item.name=="conditionList"){
				//如果选择的项目绑定了下拉框，单独处理
				postData += getMutilpeSelectorValue(item);
				hasChecked = true;
			}
		}
	}
	
	//时间处理
	var timeList = G('timeList');
	if (timeList) {
		var inputs = timeList.getElementsByTagName('input'),
			timeData = [];
		
		for (var i = 0, len = inputs.length; i < len - 1; i += 2) {
			var startTime = inputs[i],
				endTime   = inputs[i+1],
				filledStartTime = startTime.value.trim().length > 0,
				filledEndTime   = endTime.value.trim().length > 0;
			
			if (startTime.getAttribute('condi') == '' || startTime.getAttribute('itemId') == '') {
				continue;
			}
			
			//两个时间，只要有任意一个有值就传
			if ( filledStartTime|| filledEndTime ) {
				timeData.push("&condi=" + startTime.getAttribute('condi') + '&item=' + startTime.getAttribute('itemId') + '&valueNum=2');
				timeData.push('&condiValue=' + (filledStartTime ? (startTime.value + ' 00:00:00') : '') );
				timeData.push('&condiValue=' + (filledEndTime   ? (endTime.value + ' 23:59:59') : '') );
				
				hasChecked = true;
			}
		}

		if (timeData.length > 0) {
			postData += timeData.join('');
		}
	}
	
	if(!hasChecked){
		alert("请选择列表项");
		return false;
	}
	
	
	/*3期新增:财务状态下拉选项*/
	var finDelayStatsList = G('finDelayStatsList');
	if (finDelayStatsList) {
		var financeStatusBox = G('financeStatusBox');

		if (!financeStatusBox.disabled)  {
			/*
			postData += "&item=" + finDelayStatsList.getAttribute('item')+"&valueNum=1&condi=" + finDelayStatsList.getAttribute('condi') + '&condiValue='+ finDelayStatsList.value ;
			*/
			//postData += "&item=" + finDelayStatsList.getAttribute('item') + "&condi=" + finDelayStatsList.getAttribute('condi');
			finDelayStatsList.setAttribute('relation','finDelayStatsList');
			postData += getMutilpeSelectorValue(finDelayStatsList);
		}
	}
	try{
		console.log(postData);
	}catch(e){}
	
	var loadingTip = G('loadingTip');
	loadingTip.innerHTML = '&nbsp;&nbsp;报表生成中...';
	if (btn) {
		btn.disabled = true;
	}
	var ajax = new Ajax(function(responseData){
		if (btn) {
			btn.disabled = false;
		}
		loadingTip.innerHTML = '';
		
		if(!responseData) return false;
		G("resultTableWrap").show();
		G("resultTable").innerHTML = responseData;
		if(G("resultListTable"))
			initTable("resultListTable",G("resultListTable").getAttribute("tableSize"));
	},function(){
		if (btn) {
			btn.disabled = false;
		}
		loadingTip.innerHTML = '';
		alert("对不起，系统错误，请稍候再试！");
	});
	
	ajax.post(url,postData);

	/*保存查询条件,和downloadData函数有很大关系*/
	window.lastPostData = postData;
	
	try{
		G('downloadDataLink').style.display = '';
	}catch(e){}
}

/**
 *下载报表数据
 *实现:根据上次的查询条件postData，生成表单项，然后提交表单至隐藏的iframe
 */

function downloadData(){
	var downloadForm = document.forms['download'];
	var postData = '';
	
	if (!downloadForm) {
		alert("页面不存在命名为download的Form表单!");
		return false;
	}
	
	try{
		/*把上次的查询数据重新转换成表单元素hidden，要注意下面两个replace是有顺序的
		 * "v1=a&v2=b" -> <input type="hidden" name="v1" value="a" /><input type="hidden" name="v2" value="b" />
		 */
		postData = lastPostData.replace(/=/g,'\" value=\"');
		postData = postData.replace(/&/g,'\" /><input type=\"hidden\" name=\"');
		postData = '<input type="hidden" name="' + postData + '" />';
		
		downloadForm.innerHTML = postData;
	}catch(e){}
	

	downloadForm.submit();
	G('downloadDataLink').style.display = 'none';
		
	
	return false;
}

function postData(formName,action,validate){
	var form = document.forms[formName] || G(formName);
	//form.action = "${request.getContextPath()}" + action;
	
	var validate = typeof validate == "function" ? validate : function(){return true;}
	
	if(validate(form)){
		form.submit();
	}
}

/**
 * 设置元素的显示状态
 * @param display
 * @return
 */
function makeElsDisplay(display) {
	return function() {
		for (var i = 0,len = arguments.length; i < len; i++) {
			if (arguments[i].style) {
				arguments[i].style.display = display || '';
			}
		}
	}
}

/**
 *获得className元素
 */
function getElementsByClassName(clsName,parent,tag) {
	var parent = parent || document.body,
		tag    = tag || '*',
		els = parent.getElementsByTagName(tag),
		result = [],
		reg = new RegExp("(^|\\s+)" + clsName + "(\\s+|$)");
	
	for (var i = 0, len = els.length; i < len; i++) {
		var cn = els[i].className;

		if (cn && reg.test(cn) ) {
			result.push(els[i]);
		}
	}

	return result;
}

/**
 * 移除表单元素
 * @usage removeFormElement(f,['name1','name2'])
 * @param form {HTMLDomElement}
 * @param eles {array}
 * @return
 */
function removeFormElement(form,eles) {
	
	for (var i = 0, len = eles.length; i < len; i++) {
		var item = form[eles[i]];
		
		if ( item ) {
			var itemLen = item.length;
			if (!itemLen ) {
				item.disabled = true;
			} else {
				for (var j = 0; j < itemLen; j++) {
					try {
						item[j].disabled = true;
					} catch(e){}
				}
			}
		}
	}

}

function ErrorReport(tip) {
	this.tip = tip;
	this.tip.innerHTML = '<p><b>发生以下错误:</b></p>';
	this.ul = document.createElement('ul');
	this.tip.appendChild(this.ul);
	this.index = 1;
}

ErrorReport.prototype = {
	clear : function() {
		this.tip.ul.innerHTML = '';
		this.index = 1;
	},
	
	show : function() {
		this.tip.style.display = '';
	},
	
	hide : function() {
		this.tip.style.display = 'none';
	},
	
	add : function(msg) {
		var li = document.createElement('li');
		li.innerHTML = (this.index++) + '.&nbsp;' + msg;
		this.ul.appendChild(li);
	}
};

var Request = {
		'get' : function(config) {
			
			var req = new Ajax(function(result){
				config.ok(result);
			},function(result){
				config.bad && config.bad(result);
			});
			
			req.get(config.url,config.params || '');
		},
		
		'post' : function(config) {
			var req = new Ajax(function(result){
				config.ok(result);
			},function(result){
				config.bad && config.bad(result);
			});
			
			req.post(config.url,config.data || '');
		}
};

function FormMgr(wrap) {
	this.wrap = (typeof wrap == 'string' ? G(wrap) : wrap) || document.body;
	this.formItems = [];
	this.hash = {};
	
	this.getFormItems();
	this.mkNameHash();
}

FormMgr.prototype = {
	/**
	 * 参数:表单项name属性
	 */
	"disable" : function() {
		for (var i = 0,len = arguments.length; i < len; i++) {
			var name = arguments[i];
			if (typeof name == 'string') {
				this.disableItems(name);
			} else {
				try { //DOM元素
					name.disabled = true;
				}catch(e){}
			}
		}
	},
	
	"disableItems" : function(name) {
		if (this.hash[name]) {
			for (var i = 0, len = this.hash[name].length; i < len; i++) {
				var item = this.hash[name][i];
				item.disabled = true;
			}
		}
	},
	
	"toArray" : function(arrayLike) {
		var arr = [];
		for (var i = 0, len = arrayLike.length; i < len; i++) {
			arr.push(arrayLike[i]);
		}
		return arr;
	},
	
	"getFormItems" : function() {
		var inputs = this.wrap.getElementsByTagName('input'),
			selects = this.wrap.getElementsByTagName('select'),
			texts   = this.wrap.getElementsByTagName('textarea');
		
		this.formItems = [].concat(this.toArray(inputs),this.toArray(selects),this.toArray(texts));
	},
	
	"mkNameHash" : function() {
		for (var i = 0, len = this.formItems.length; i < len; i++) {
			var item = this.formItems[i],
				name = item.name;
			
			if (!this.hash[name]) {
				this.hash[name] = [];
			}
			this.hash[name].push(item);
		}
	},
	
	"disableAll" : function() {
		for (var i = 0,len = this.formItems.length; i < len; i++) {
			this.formItems[i].disabled = true;
		}
	},
	
	"enableAll" : function() {
		for (var i = 0,len = this.formItems.length; i < len; i++) {
			this.formItems[i].disabled = false;
		}
	},
	
	"serializeStr" : function() {
		var html = [];
		for (var i = 0,len = arguments.length; i < len; i++) {
			var name = arguments[i];
			if (typeof name == 'string') {
				html.push(this.serializeItem(name,'ajax'));
			} 
		}
		
		return html.join('&');
	},
	
	"serializeStrAll" : function() {
		var str = [];
		
		for (var i = 0, len = this.formItems.length; i < len; i ++) {
			var itemStr = this.serializeItem(this.formItems[i],'ajax');
			if (itemStr) {
				str.push(itemStr);
			}
		}
		
		return str.join('&');
	},
	
	"serializeHtml" : function() {
		var str = [],argLen = arguments.length;
		
		if (argLen > 0) { //序列化指定数据
			for (var i = 0,len = argLen; i < len; i++) {
				var name = arguments[i];
				if (typeof name == 'string') {
					str.push(this.serializeByName(name));
				} 
			}
		} else { //全部序列化
			for (var i = 0, len = this.formItems.length; i < len; i ++) {
				str.push(this.serializeItem(this.formItems[i],'html'));
			}
		}
		
		var htmlStr = str.join('&'),
			html = [];
		
		var parts = htmlStr.split('&');
		for (var i = 0, len = parts.length; i < len; i++) {
			var part = parts[i];
			var item = part.split('=');
			var name = item[0],
				value = part.substring(part.indexOf('=')+1);
			
			if (name != '') {
				html.push('<input type="hidden" name="'+name+'" value="'+encodeHTML(value)+'" />');	
			}
		}
		
		return html.join('');
	},
	
	"serializeItem" : function(item,type) {
		if (item.disabled == true) {
			return '';
		}
		
		if (item.type == 'checkbox' || item.type == 'radio') {
			if (!item.checked) {
				return '';
			}
		}
		
		if (item.name != '') {
			if (type == 'ajax') {
				return item.name + '=' + encodeURIComponent(item.value);
			} else if (type == 'html') {
				return item.name + '=' + item.value;
			}
		}
		
		return '';
	},
	
	"serializeByName" : function(name) {
		var html = [];
		if (this.hash[name]) {
			for (var i = 0, len = this.hash[name].length; i < len; i++) {
				var item = this.hash[name][i];
				html.push( this.serializeItem(item,'html') );
			}
		}
		
		return html.join('&');
	}
};


/**
 * 模块验证函数收集器，既可以单例使用，也可以实例化后再使用
 * 如果单例使用，则全局只有1个验证器，不能用于多余1个表单的验证
 * 建议总是使用多例方式:-)
 * Usage:
 * 单例
 * ModuleValidator.add(function(){});
 * if( ModuleValidator.pass() ) {doSomething()}
 * 多例
 * var mv1 = new ModuleValidator();
 * mv1.add(function(){});
 * if(mv1.pass()) {doSomething()}
 */
function ModuleValidator() {
	this.fns = [];
}

ModuleValidator.fns = [];
ModuleValidator.add = function() {
	for (var i = 0,len = arguments.length; i < len; i++) {
		this.fns.push(arguments[i]);
	}
	
	return this;
};
ModuleValidator.pass = function() {
	var result = true;
	for (var i = 0, len = this.fns.length; i < len; i++) {
		var fn = this.fns[i];
		if (typeof fn == 'function') {
			var ok = fn();
			if (!ok) {
				result = false;
			}
		}
	}
	
	return result;
};

ModuleValidator.prototype = {
		add : ModuleValidator.add,
		pass : ModuleValidator.pass
};


/*表单提交数据收集器*/
var SubmitDataMgr = function(){
	this.data = [];
};

SubmitDataMgr.prototype.add = function(html){
	this.data.push(html);
	return this;
};

SubmitDataMgr.prototype.getHTML = function(){
	return this.data.join('');
};


function popTypeControl(){
	/**
	 * 如果合同，财务，礼品任意一个选择了快递,单独快递取款选项锁定为“否”(值为0),且不可编辑
	 * 'financegetMethod2','contractgetMethod2','giftgetMethod2'
	 */
	 var financegetMethod2 	= G('financegetMethod2'),
	 	financegetMethod1	= G('financegetMethod1'),
	 	contractgetMethod2 	= G('contractgetMethod2'),
	 	contractgetMethod1 	= G('contractgetMethod1'),
	 	giftgetMethod2		= G('giftgetMethod2'),
	 	giftgetMethod1		= G('giftgetMethod1'),
	 	sepeExpGetmoneyList = G('sepeExpGetmoneyList');

	 Event.add(financegetMethod1,	'click',enablePopType);
	 Event.add(contractgetMethod1,	'click',enablePopType);
	 Event.add(giftgetMethod1,		'click',enablePopType);
		
	 Event.add(financegetMethod2,	'click',disabledPopType);
	 Event.add(contractgetMethod2,	'click',disabledPopType);
	 Event.add(giftgetMethod2,		'click',disabledPopType);


	function disabledPopType() {
		
		var options = sepeExpGetmoneyList.options;
		for (var i = 0, len = options.length; i < len; i++ ) {
			if (options[i].value == 0) {
				sepeExpGetmoneyList.selectedIndex = i;
				sepeExpGetmoneyList.disabled = true;
				break;
			}
		}
		
	}

	function enablePopType() {
		if ( financegetMethod1.checked 
			&& contractgetMethod1.checked 
			&& giftgetMethod1.checked ) {
			sepeExpGetmoneyList.disabled = false;
		}

	}
	

 }

