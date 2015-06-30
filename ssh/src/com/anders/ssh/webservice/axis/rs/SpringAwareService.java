package com.anders.ssh.webservice.axis.rs;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;

public class SpringAwareService {
	private MyBean myBean = null;

	public void setMyBean(MyBean myBean) {
		this.myBean = myBean;
	}

	public OMElement getValue(OMElement ignore) {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace payloadNs = factory.createOMNamespace("http://springExample.org/example1", "example1");
		OMElement payload = factory.createOMElement("string", payloadNs);
		OMText response = factory.createOMText(this.myBean.emerge());
		payload.addChild(response);
		return payload;
	}
}
