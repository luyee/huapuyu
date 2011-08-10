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
	if(G(checkboxDiv).checked){
		var tempSpan = G(spanDes);
		if(tempSpan.tagName.toUpperCase() == "INPUT")
			tempSpan.value = G(valueFrom).value;
		else
			tempSpan.innerHTML = G(valueFrom).value;
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
function productSearchClick(){
	//点击搜索推广的radio
	G("productSearchDiv").show();
	G("productHeatDiv").hide();
	changeSumSpan("productSearch","contractSumSpan","searchPre");//修改合同信息里面的合同金额
	changeSumSpan("productSearch","financeSumSpan","searchPre");//修改财务信息里面的发票金额
	changeSumSpan("productSearch","financeSumSpanHidden","searchPre");
	
	//删除火爆地带的相关验证
	G("heatPre").removeAttribute("pattern");
	var coopInputsParent = G("productHeatDiv").getElementsByTagName("input");
	for(var i=0,tempLen=coopInputsParent.length;i<tempLen;i++){
		if(coopInputsParent[i].name == "orderInfoDto.heatAreaDto.popWord" || coopInputsParent[i].name == "orderInfoDto.heatAreaDto.popPosition"){
			coopInputsParent[i].removeAttribute("minLen");
		}
		if(coopInputsParent[i].name == "orderInfoDto.heatAreaDto.coopTime"){
			coopInputsParent[i].removeAttribute("pattern");
			coopInputsParent[i].removeAttribute("minValue");
		}
	}
	var coopTextAreaParent = G("productHeatDiv").getElementsByTagName("textarea");
	for(var i=0,tempLen=coopTextAreaParent.length;i<tempLen;i++){
		if(coopTextAreaParent[i].name == "orderInfoDto.heatAreaDto.popDesc"){
			coopTextAreaParent[i].removeAttribute("minLen");
			coopTextAreaParent[i].removeAttribute("maxLen");
		}
	}
	//添加搜索推广的相关验证
	G("searchPre").setAttribute("pattern","\\d+(\\.\\d{0,2})?");
	G("buildWebFee").setAttribute("pattern","(\\d+(\\.\\d{0,2})?)?");
	G("serviceFee").setAttribute("pattern","(\\d+(\\.\\d{0,2})?)?");
	G("favRate").setAttribute("pattern","(100|[1-9]?[0-9])?");
}
function productHeatClick(){
	//点击火爆地带的radio
	G("productSearchDiv").hide();
	G("productHeatDiv").show();
	changeSumSpan("productHeat","contractSumSpan","heatPre");
	changeSumSpan("productHeat","financeSumSpan","heatPre");
	changeSumSpan("productHeat","financeSumSpanHidden","heatPre");
	
	//删除搜索推广的相关验证
	G("searchPre").removeAttribute("pattern");
	G("buildWebFee").removeAttribute("pattern");
	G("serviceFee").removeAttribute("pattern");
	G("favRate").removeAttribute("pattern");
	//添加火爆地带的相关验证
	G("heatPre").setAttribute("pattern","\\d+(\\.\\d{0,2})?");
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
function caculateFav(){
	//计算优惠金额，预存款*优惠比率
	if(G("searchPre").value.length <= 0 || G("favRate").value.length <= 0){
		G("searchPopDtoorderProductInfoSearchPopfavSumSpan").innerHTML = "";
		G("searchPopDtoorderProductInfoSearchPopfavSum").value = "";
		return false;
	}
	var result = parseInt(G("searchPre").value,10) * parseInt(G("favRate").value,10)/100;
	G("searchPopDtoorderProductInfoSearchPopfavSumSpan").innerHTML = result;
	G("searchPopDtoorderProductInfoSearchPopfavSum").value = result;
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
			newInput[i].disabled = true;
			newInput[i].value = G("coopTypeList" + randomId).options[0].value;
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
	if(myId.length <= 0){
		alert("系统错误");
		return false;
	}
	//删除火爆地带的一条
	var heatNum = G("productHeatDiv").getElementsByTagName("a").length - 1;
	if(heatNum<=1){
		alert("您不能删除最后一条记录");
	}else
		G("heatSubDiv"+myId).remove();
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
	var Asum = G("paySumA").value;
	var Bsum = G("paySumB").value;
	var totalsum = G("financeSumSpan").innerHTML;
	if(Asum.length == 0 && Bsum.length == 0)
		return "";
	else if(!Bsum.match(/^\d+(\.\d{0,2})?$/))
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
function clearTable4(){
	G("dataList4").setData([]);
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
	}else if(parseInt(applyObj.value,10)>storageNum){
		alert("申领数不能大于库存数");
		applyObj.style.backgroundColor = "#FF0000";
		return false;
	}else if(parseInt(applyObj.value,10)<=0){
		alert("申领数必须是正数");
		applyObj.style.backgroundColor = "#FF0000";
		return false;
	}
	else{
		applyObj.style.backgroundColor = "#FFFFFF";
		return true;
	}
}
function getGiftList(urlHead){
	var url = urlHead + "/order/gift/getGiftListByAddr.action";
	//var url = "/order/gift/getGiftListByAddr.action";
	var postData = "ad="+ G('giftApplyAddress').options[G('giftApplyAddress').selectedIndex].value;
	var ajax = new Ajax(function(responseData){
		if(!responseData) return false;
		var jsonData = eval( "(" + responseData  + ")" );
		if(jsonData.flag!=0){
			alert(jsonData.error)
		}else{
			G("dataList6").setData([]);
			G("giftStorageListExt").show();
			var listData = jsonData.giftList;
			for(var i=0;i<listData.length;i++){
				//组装弹出层
				var item1 = '<input type="checkbox" value="'+listData[i].giftId+'" id="floatGiftCheckBox'+listData[i].giftId+'" onclick="floatApplyThisGift(this)" '+getGiftCheckBox(listData[i].giftId)+'>';
				var item2 = '<span id="floatGiftType'+listData[i].giftId+'">'+listData[i].giftType+'</span>';
				var item3 = '<span id="floatGiftName'+listData[i].giftId+'">'+listData[i].giftName+'</span>';
				var item4 = '<span id="floatGiftStorage'+listData[i].giftId+'">'+listData[i].giftStorage+'</span>';
				var item5 = '<input type="input" value="'+getGiftApply(listData[i].giftId)+'" size="2" id="floatGiftApply'+listData[i].giftId+'" style="border:1px solid #7A7CED;" '+getGiftDisabled(listData[i].giftId)+'>';
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
			var item4 = '<input type="hidden" value="'+G("floatGiftApply"+tempId).value+'" name="giftDto.giftApplySum" id="mainGiftApply'+tempId+'">'+G("floatGiftApply"+tempId).value;
			G("dataList5").add([item1,item2,item3,item4]);
			selectCount++;
		}
	}
	G("dataList5").parentNode.style.height = 48 + 26*selectCount + "px";
	G("giftStorageListExt").hide();
}

function bySelf(table){
	//自行取合同
	G(table + "IdDiv").hide();
	G(table + "AddDiv").innerHTML = "";
	G(table + "AddDivWrap").hide();
	G(table + "AddText").hide();
}
function makeReceiverList(table){
	if(!custListFlag){
		G(table + "QuestionDiv").show();
		G(table + "IdDiv").hide();
	}else{
		G(table + "IdDiv").show();
		G(table + "QuestionDiv").hide();
	}
	G(table + "AddText").show();
	G(table + "AddDivWrap").hide();
}
function getModifiedValue(modify,itemName,itemId){
	if(!modify)
		return "";
	else if(G(itemName+itemId))
		return G(itemName+itemId).innerHTML;
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
	createReceiverList(false,'',myNum);
	G(myTable+"AddDivWrap").show();
	G(myTable+"IdDiv").hide();
	G(myTable+"QuestionDiv").hide();
}
function modifiedAllBack(myTable,modify,urlHead){
	//将收件人信息的新增、修改返回到服务器，拉取新的列表
	var url = urlHead;
	if(!modify)
		url += "/order/custreceiver/newCustReceiver.action";
	else
		url += "/order/custreceiver/modCustReceiver.action";
	var postData = "";
	var sourceInput = G(myTable + "AddDiv").getElementsByTagName("input");
	for(var i=0,tempLen=sourceInput.length;i<tempLen;i++){
		if(sourceInput[i].type=="text" || sourceInput[i].type=="hidden"){
			if(i==0)
				postData += sourceInput[i].name + "=" + sourceInput[i].value;
			else
				postData += "&" + sourceInput[i].name + "=" + sourceInput[i].value;
		}
	}
	var ajax = new Ajax(function(responseData){
		if(!responseData) return false;
		var jsonData = eval( "(" + responseData  + ")" );
		if(jsonData.flag!=0){
			alert(jsonData.error)
		}else{
			var listData = jsonData.custRecipList;
			G("dataList3").setData([]);
			G("dataList4").setData([]);
			G("dataList7").setData([]);
			if(listData.length>0)
				custListFlag = true;
			else
				custListFlag = false;
			for(var i=0,tempLen1=listData.length;i<tempLen1;i++){
				//组装弹出层
				var item31 = '<input type="radio" value="'+listData[i].id+'" name="contractDto.contract.receiverId" id="contractReceiveId'+listData[i].id+'">';
				var item41 = '<input type="radio" value="'+listData[i].id+'" name="financeDto.receiptInfo.receiver" id="financeReceiveId'+listData[i].id+'">';
				var item71 = '<input type="radio" value="'+listData[i].id+'" name="giftDto.giftApplyInfo.receiverId" id="giftReceiveId'+listData[i].id+'">';
				var item32 = '<span id="receiverListName'+listData[i].id+'">'+listData[i].name+'</span>';
				var item42 = listData[i].name;
				var item33 = '';
				var item43 = '';
				var item34 = '';
				var item44 = '';
				var tempList = listData[i].phoneList;
				var tempCount1 = 0;
				var tempCount2 = 0;
				for(var j=0,tempLen2=tempList.length;j<tempLen2;j++){
					if(tempList[j].phoneType=="0"){
						item33 += '<input type="hidden" id="receiveListPhoneId'+listData[i].id+tempCount1+'" value="'+tempList[j].phoneId+'">';
						if(tempList[j].phone1.length>0){
							item33 += '<span id="receiverListPhone1'+listData[i].id+tempList[j].phoneId+'">'+tempList[j].phone1+'</span>-';
							item43 += tempList[j].phone1+'-';
						}
						item33 += '<span id="receiverListPhone2'+listData[i].id+tempList[j].phoneId+'">'+tempList[j].phone2+'</span>';
						item43 += tempList[j].phone2;
						if(tempList[j].phone3.length>0){
							item33 += '-<span id="receiverListPhone3'+listData[i].id+tempList[j].phoneId+'">'+tempList[j].phone3+'</span>';
							item43 += '-'+tempList[j].phone3;
						}
						item33 += ' ';
						item43 += ' ';
						tempCount1++;
					}else if(tempList[j].phoneType=="1"){
						item34 += '<input type="hidden" id="receiveListCellPhoneId'+listData[i].id+tempCount2+'" value="'+tempList[j].phoneId+'">';
						item34 += '<span id="receiverListCellPhone'+listData[i].id+tempList[j].phoneId+'">'+tempList[j].phone2+'</span> ';
						item44 += tempList[j].phone2 + ' ';
						tempCount2++;
					}
				}
				item33 += '<input type="hidden" value="'+tempCount1+'" id="receiverListPhoneCount'+listData[i].id+'">';
				item34 += '<input type="hidden" value="'+tempCount2+'" id="receiverListCellPhoneCount'+listData[i].id+'">';
				item35 = '<span id="receiverListAddress'+listData[i].id+'">'+listData[i].address+'</span>';
				item45 = listData[i].address;
				item36 = '<span id="receiverListPostNumber'+listData[i].id+'">'+listData[i].postNumber+'</span>';
				item46 = listData[i].postNumber;
				item37 = '<a href="#" onClick="createReceiverList(true,'+listData[i].id+',3);return false;" hidefocus>修改</a>';
				item47 = '<a href="#" onClick="createReceiverList(true,'+listData[i].id+',4);return false;" hidefocus>修改</a>';
				item77 = '<a href="#" onClick="createReceiverList(true,'+listData[i].id+',7);return false;" hidefocus>修改</a>';
				G("dataList3").add([item31,item32,item33,item34,item35,item36,item37]);
				G("dataList4").add([item41,item42,item43,item44,item45,item46,item47]);
				G("dataList7").add([item71,item42,item43,item44,item45,item46,item77]);
			}
			G("dataList3").parentNode.style.height = 46 + 26*listData.length + "px";
			G("dataList4").parentNode.style.height = 46 + 26*listData.length + "px";
			G("dataList7").parentNode.style.height = 46 + 26*listData.length + "px";
		}
	},function(){
		alert("对不起，系统错误，请稍候再试！");
	});
	ajax.post(url,postData);
	G(myTable + "IdDiv").show();
	G(myTable + "AddDivWrap").hide();
}
function changeDays(obj){
	var targetInput = obj.parentNode.getElementsByTagName("input")[0];
	targetInput.value = obj.options[obj.selectedIndex].value;
	if(obj.selectedIndex == (obj.options.length-1)){
		targetInput.disabled = false;
	}else
		targetInput.disabled = true;
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
	G("floatOrgListWrap").hide();
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
function createHiddenInput(sourceDiv,isHidden){
	var allInputs = [];
	var allInputs1 = changeToArray(G(sourceDiv).getElementsByTagName("input"));
	var allInputs2 = changeToArray(G(sourceDiv).getElementsByTagName("select"));
	var allInputs3 = changeToArray(G(sourceDiv).getElementsByTagName("textarea"));
	allInputs = allInputs.concat(allInputs1,allInputs2,allInputs3);
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		if(allInputs[i].type=="radio" && !allInputs[i].checked)
			continue;
		if(allInputs[i].type=="hidden" && !isHidden)
			continue;
		if(allInputs[i].name.length <= 0)
			continue;
		var tempInput = document.createElement("input");
		tempInput.type = "hidden";
		tempInput.name = allInputs[i].name;
		tempInput.value = allInputs[i].value;
		document.forms["hiddenForm"].appendChild(tempInput);
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
	for(var i=0,tempLen=allHiddens.length;i<tempLen;i++){
		if(allHiddens[i].type == "hidden")
			G(allHiddens[i]).remove();
	}
}
function submitForm(url){
	if(url=="")
		return false;
	document.forms["hiddenForm"].action = url;
	//设置时间框
	if(G("contractexpectedGetDateYYMMDD").value.length==0 && G("contractexpectedGetDateHHMMSS").value.length==0)
		G("contractexpectedGetDateHidden").value = "";
	else
		G("contractexpectedGetDateHidden").value = G("contractexpectedGetDateYYMMDD").value + " " + G("contractexpectedGetDateHHMMSS").value
	if(G("receiptInfoprecontractTimeYYMMDD").value.length==0 && G("receiptInfoprecontractTimeHHMMSS").value.length==0)
		G("receiptInfoprecontractTimeHidden").value = "";
	else
		G("receiptInfoprecontractTimeHidden").value = G("receiptInfoprecontractTimeYYMMDD").value + " " + G("receiptInfoprecontractTimeHHMMSS").value
	
	if(Validate.validElement(G('makeOrderMain'))){
		
		removeSubmitHidden();	//先删除所有的hidden，再逐一加入
		
		createHiddenInput("orderSourceDiv",true);
		createHiddenInput("orderInfoDtoproductSelectedP",true);
		createHiddenInput("productSearchDiv",true);
		createHiddenInput("productHeatDiv",true);
		
		createHiddenInput("contractDtoTable",true);
		createHiddenInput("dataList3",false);
		createHiddenInput("hiddenContractFile",true);
		
		createHiddenInput("financeDtoTable",true);
		createHiddenInput("dataList4",false);
		createHiddenInput("financeDtoTable2",true);
		
		if(G("isAB0").checked)
			createHiddenInput("payTypeSingle",true);
		else{
			createHiddenInput("payTypeA",true);
			createHiddenInput("payTypeB",true);
		}
		createHiddenInput("hiddenFinanceFile",true);
		
		createHiddenInput("giftDtoTable1",true);
		createHiddenInput("giftDtoTable2",true);
		createHiddenInput("giftListDiv",true);
		createHiddenInput("giftDtoTable3",true);
		createHiddenInput("dataList7",false);
		
		createHiddenInput("orderInfoDtoComment",true);
		
		document.forms["hiddenForm"].submit();
	}else
		return false;
}


function showModifiedInfo(objId){
	var obj = G(objId);
	if(obj.style.display == "none")
		obj.show();
	else
		obj.hide();
	return false;
}
function disableElement(targetDiv){
	//驳回订单，让元素不可用
	var allInputs = [];
	var allInputs1 = changeToArray(G(targetDiv).getElementsByTagName("input"));
	var allInputs2 = changeToArray(G(targetDiv).getElementsByTagName("select"));
	var allInputs3 = changeToArray(G(targetDiv).getElementsByTagName("textarea"));
	allInputs = allInputs.concat(allInputs1,allInputs2,allInputs3);
	for(var i=0,tempLen=allInputs.length;i<tempLen;i++){
		allInputs[i].disabled = true;
	}
	var allA = G(targetDiv).getElementsByTagName("a");
	for(var i=0,tempLen=allA.length;i<tempLen;i++){
		allA[i].onclick = function(){return false;}
	}
}

function setTargetId(checkBoxObj){
	G("custCompareQueryDetailButton").setAttribute("targetId",checkBoxObj.value);
}
function custQuery(urlHead){
	var url = urlHead + "/order/bloc.action";
	var postData = "";
	postData += "value=" + encodeURIComponent(G("custCompareQueryValue").value);
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
	if(custId.length <=0){
		alert("请选择一条信息进行详细对比");
		return false;
	}
	var url = urlHead + "/order/blocDetail.action";
	var postData = "customerId=" + custId;
	var ajax = new Ajax(function(responseData){
		if(!responseData) return false;
		G("compareTarget").innerHTML = responseData;
		G("comparePanel").show();
	},function(){
		alert("对不起，系统错误，请稍候再试！");
	});
	ajax.post(url,postData);
}