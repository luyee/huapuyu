/**
 * 
 */
package com.anders.ethan.log.dapper.client.common;

import java.text.DecimalFormat;

/**
 * @author mandy
 * 
 */
public class ExceptionUtil {

	/**
	 * 
	 * 
	 * @param Excetipn
	 *            type
	 * @return String type
	 */
	public static String getExceptionDetail(Throwable e) {
		StringBuffer msg = new StringBuffer("null");
		if (e != null) {
			msg = new StringBuffer("");
			String message = e.toString();
			int length = e.getStackTrace().length;
			if (length > 0) {
				msg.append(message + "\n");
				for (int i = 0; i < length; i++) {
					msg.append("\t" + e.getStackTrace()[i] + "\n");
				}
			} else {
				msg.append(message);
			}
		}
		return msg.toString();
	}

	public static void main(String[] args) {
		Double aaDouble = null;
		Double bbDouble = null;
		// double val = aaDouble*2.0/(1*10.0);
		double aa = 0;
		DecimalFormat format = new DecimalFormat("0.00");
		System.out.println(format.format(aa));
	}
}
