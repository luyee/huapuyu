var Receipter = (function(){

		var receiptList   = G('receiptList'),
		   	isReceiptTypeEq1 = false,								/*默认为开发票而不是收款证明*/
			receiptNum	  = 1,										/*初始化为1张发票		*/
			receiptNOTip  = G('receiptNOTip'),
			maxReceiptNum = 100,									/*最多50张发票			*/
			inputs		  = null,									/*保存所有的输入项		*/
			receiptReg	  = /^[0-9a-zA-Z]{20}$/i,					/*发票号					*/
			moneyReg	  = /^(?:0|[1-9][0-9]*)(?:\.[0-9]+)?$/,		/*金额验证				*/
			numbersList	  = [],										/*保存所有发票号		*/
			receiptSun	  = 0; 	/*发票金额*/
	
		
		/*获得参数值*/
		var scripts = document.getElementsByTagName('script'),
			len = scripts.length,
			curScript = scripts[len-1],								/*当前脚本				*/
			src	= curScript.getAttribute('src'),
			eqPos = src.lastIndexOf('=');
		
		receiptSun = parseFloat(src.substring(eqPos+1));


		function checkInit() {
			
			inputs = receiptList.getElementsByTagName('input');
			numbersList = [];
			
			isReceiptTypeEq1 = document.getElementById('certificationNO') ? true : false;
			
		}
		
		function showMsg(msg) {
			receiptNOTip.innerHTML = msg || '';
		}
		
		/*检测非空*/
		function chkBlank() {			
			
			for (var i = 0,len = inputs.length; i < len; i++) {
				if (inputs[i].value.trim() == '') {
					
					if (isReceiptTypeEq1) {
						showMsg('尚未填写收款证明');
					} else {
						showMsg('尚有发票号或者金额未填写');
					}
					inputs[i].focus();
					return false;
				}
			}
			
			return true;
		}
		
		
		/*检测是否存在重复发票号*/
		function hasRepeatReceiptCode(number) {
			for (var i = 0, len = numbersList.length; i < len; i++) {
				if (numbersList[i] == number) {
					return true;
				}
			}
			
			return false;
		}
		
		/*检测发票号是否合法*/
		function chkReceiptNum() {
			for (var i = 0, len = inputs.length; i < len; i++) {
				var item = inputs[i],numbers = item.value.trim();
				if (item.getAttribute('name') == 'receiptNO'  ) {
					if ( !receiptReg.test(numbers) ) {
						showMsg('发票必须为20位!');
						item.focus();
						return false;
					} else {
						/*检测是否有相同的发票号*/
						if (!hasRepeatReceiptCode(numbers)) {
							numbersList.push(numbers);
						} else {
							showMsg('存在相同的发票号码，请检查!');
							item.focus();
							return false;
						}
					}
				}
			}
			
			return true;
		}
		
		/*检测金额是否合法			*/
		function chkReceiptMoney() {
			for (var i = 0, len = inputs.length; i < len; i++) {
				var item = inputs[i];
				
				if (item.getAttribute('name') == 'receiptSum' ) {
					var money = item.value.trim();
					
					/*首先验证格式*/
					if (!moneyReg.test(money)) {
						showMsg('错误的金额格式!');
						item.focus();
						return false;
					}
					
					/*然后验证是否金额大于0*/
					if (parseFloat(money) <= 0 ) {
						showMsg('发票金额必须大于0');
						item.focus();
						return false;
					}
					
				}
			}
			
			return true;
		}
		
		/*检测金额数目是否合法*/
		function chkSum() {
			var sum = 0,needCheckSum = false;
			for (var i = 0, len = inputs.length; i < len; i++) {
				var item = inputs[i];
				
				if (item.getAttribute('name') == 'receiptSum') {
					needCheckSum = true;
					sum += parseFloat(item.value.trim());
				}
			}
			
			if (!needCheckSum) {
				return true;
			}
			
			if (receiptSun != sum) {
				showMsg('发票金额与实际金额数目不相等,发票金额：<b>' + sum + '</b>&nbsp;&nbsp;实际金额：<b>' + receiptSun + '</b>.');
				return false;
			}
			
			return true;
		}
		
		
		return {
			/**
			 * 添加一张发票
			 */
			add : function() {
				receiptNOTip.innerHTML = '';
				
				if (receiptNum <= maxReceiptNum) {
					var li = document.createElement('li');
					
					li.innerHTML = receiptList.getElementsByTagName('li')[0].innerHTML;
					li.style.marginBottom = '10px';
					receiptList.appendChild(li);
					
					receiptNum++;
				} else {
					showMsg('最多只能填写100张发票');
				}
				return false;						
			},
			
			/**
			 * 删除一张发票
			 * @param {Object} link
			 */
			del : function(link) {
				receiptNOTip.innerHTML = '';
				
				if (receiptNum > 1) {
					receiptList.removeChild(link.parentNode);
					receiptNum--;
				} else {
					showMsg('至少需要一张发票!');
				}
				
				return false;
            },

            /*
             * 通过Ajax验证发票号是否重复
             * @public
             *
             * @param {HTMLElemnt} txt 发票号文本框
             */
            checkDupInvoice: function(txt,reqUrl) {
                if (txt.value.trim() == "")
                    return;
                var url = reqUrl + (reqUrl.match(/\?/) ? "&" : "?") + "invoiceNum=" + txt.value,
                    parent = txt.parentNode,
                    req = new Ajax(
                        //SUCCESS
                        function (result) {
                            if (result == "")
                                return;
                            var div = document.createElement("div");
                            div.style.color = "red";
                            div.innerHTML = result;
                            parent.appendChild(div);
                        }
                    );
                req.get(url);
            },

            checkDupInvoice4Batch: function (txt,num,btn,form,reqUrl) {
                if (txt.value.trim() == "")
                    return;
                var url = reqUrl + "?invoiceNum=" + txt.value + "&numSize=" + num;
                btn.disable = true;
                var req = new Ajax(
                        //SUCCESS
                        function (result) {
                            btn.disable = false;
                            if (result == ""){
                                form.submit();
                                return;
                            }
                            var div = document.createElement("div");
                            div.style.color = "red";
                            div.innerHTML = result;
                            baidu.dom.insertAfter(div, txt);
                        },
                        //error
                        function (result) {
                            alert("网络错误，请稍后重试");
                            btn.disable = false;
                        }
                    );
                req.get(url);
            },
			
			/**
			 * 提交表单时验证
			 * @param {Object} form
			 */
			check : function(form) {
				/**
				 * 验证规则:
				 * 1.发票号和金额非空 
				 * 2.发票号限定为20位字符，并且必须为20位字符，输入的各发票金额总和需等于订单金额。
				 * 3.金额为正确格式(数目>0)
				 * 4.不应该存在相同的发票号
				 */
				var pass = true,
					isReceiptTypeEq1 = false;
				
				try {
					checkInit();
				} catch(e){
					/*如果checkInit出错，则说明是开收款证明
					 *$financeDto.receiptInfo.receiptType.intValue() = 1
					 *它位于文件/order/receiptManage.vm中
					 */
					isReceiptTypeEq1 = true;
				}
												
				
				/**
				 * 如果选择驳回，则不进行上面的校验,但是需要检验备注
				 */
				 var rejectBtn = G('rejectBtn'),auditDescTextarea = G('auditDescTextarea');
				 var auditDesc = auditDescTextarea.value.trim();
				 
				 if (auditDesc.length > fe_auditDesc_len) {
					pass = false;
					showMsg('备注不能大于'+fe_auditDesc_len+'字符');
				 }
				
				 
				 if (rejectBtn && rejectBtn.checked) {
					//pass = true;
					if (auditDesc == '') {
						pass = false;
						showMsg('请填写备注');
					} 
			
				 } else {
					 if (isReceiptTypeEq1) {
						var certificationNO = G('certificationNO').value.trim();
						
						if (certificationNO == '') {
							showMsg('请填写收款证明编号');
							pass = false;
						}

					 } else {
						 /*发票增减验证*/
						var allPass =	    chkBlank() 					
								 		&&	chkReceiptNum()				
								 		&&	chkReceiptMoney()			
								 		&&	chkSum();	
						if (!allPass) {
							pass = false;
						}
					 }
				 }
				 	
				return pass;
			}
		};
	}
)();

/*
 * 通过Ajax验证发票号是否重复
 * @public
 *
 * @param {HTMLElemnt} txt 发票号文本框
 */
function checkDupInvoice(txt) {
    if (txt.value.trim() == "")
        return;
    var url = "validationInvoiceNum.action?invoiceNum=" + txt.value,
        parent = txt.parentNode,
        req = new Ajax(
            //SUCCESS
            function (result) {
                var div = document.createElement("div");
                div.innerHTML = result;
                parent.appendChild(div);
            }
        );
    req.get(url);
}