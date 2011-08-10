/**
 * 订单为会议合同时，需要验证3项信息，合同编号，印刷编号，会议时间
 * 会议合同RD后端硬编码为:
 * orderInfoDto.order.orderMakeType = 1
 */
function validMeetingOrder() {
	var orderMkTypeList = G('orderMkTypeList');
	if (orderMkTypeList.value == 0) { /*普通订单不验证*/
		
		return true;
	}

	var serialNumber	= G('serialNumber'),
		printingSerialNumber = G('printingSerialNumber'),
		mettingTime_date	 = G('mettingTime_date'),
		mettingTime_time	 = G('mettingTime_time'),
		serialNumberTip		 = G('serialNumberTip'),
		mettingTimeTip		 = G('mettingTimeTip'),
		printingSerialNumberTip	= G('printingSerialNumberTip'),
		firstInvalidItem = null,
		valid = true;

	function setFirstInvalidItem(el) {
		if (!firstInvalidItem) {
			firstInvalidItem = el;
		}
	}
	
	function chkBlank(el,tip,msg) {
		var pass = true;
		if (el.value.trim() == '') {
			valid = false;
			tip.innerHTML = msg;
			setFirstInvalidItem(el);
			pass = false;
		} else {
			tip.innerHTML = '';
		}

		return pass;
	}	

	chkBlank(serialNumber,serialNumberTip,'请填写合同号码');
	chkBlank(printingSerialNumber,printingSerialNumberTip,'请填写印刷编号');

	//会议日期和会议时间非空验证存在逻辑，填写了日期时，才对时间进行验证。
	var timePadded = false,
		datePadded = chkBlank(mettingTime_date,mettingTimeTip,'请设定会议日期');
	if (datePadded) {
		timePadded = chkBlank(mettingTime_time,mettingTimeTip,'请设定会议时间');
	}

	var dateFormatOk = false,
		timeFormatOk = false;
	
	if (timePadded) {
		dateFormatOk = /^\d{4}-\d{2}-\d{2}$/i.test(mettingTime_date.value);
		if (!dateFormatOk) {
			mettingTimeTip.innerHTML = '错误的日期格式';
			setFirstInvalidItem(mettingTime_date);
			valid = false;
		} else {
			mettingTimeTip.innerHTML = '';

			/*进行时间验证*/
			timeFormatOk =  /^((2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9])?$/i.test(mettingTime_time.value);
			if (!timeFormatOk) {
				mettingTimeTip.innerHTML = '错误的时间格式';
				setFirstInvalidItem(mettingTime_time);
				valid = false;
			} else {
				mettingTimeTip.innerHTML = '';
			}
		}
	}

	if (firstInvalidItem) {
		firstInvalidItem.focus();
	}

	return valid;
}

