/**
 * 
 */
package com.baidu.rigel.unique.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanBing
 * 
 */
public class AutoAuditRecord implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 4521424516409849774L;

	private Short autoAuditType;

	private List<AuditInfo> customer = new ArrayList<AuditInfo>();

	/**
	 * @return the autoAuditType
	 */
	public Short getAutoAuditType() {
		return autoAuditType;
	}

	/**
	 * @param autoAuditType
	 *            the autoAuditType to set
	 */
	public void setAutoAuditType(Short autoAuditType) {
		this.autoAuditType = autoAuditType;
	}

	/**
	 * @return the customer
	 */
	public List<AuditInfo> getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(List<AuditInfo> customer) {
		this.customer = customer;
	}

}
