package com.anders.ssh.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AutoGc extends QuartzJobBean {

	private static Logger log = Logger.getLogger(AutoGc.class);

	private int timeout;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private void gc() throws Exception {
		Runtime rt = Runtime.getRuntime();
		long maxMemory = rt.maxMemory();
		long freeMemory = rt.freeMemory();
		log.info("auto gc [ free:" + freeMemory + ", max:" + maxMemory + ", percent:" + (double) freeMemory / maxMemory + " ]");
		System.out.println("auto gc [ free:" + freeMemory + ", max:" + maxMemory + ", percent:" + (double) freeMemory / maxMemory + " ]");
		if ((double) freeMemory / maxMemory < 0.5)
			rt.gc();
	}

	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.gc();
		}
		catch (Exception e) {
			log.error("gc failed");
		}
	}

	public static void main(String[] args) {
		AutoGc gc = new AutoGc();
		try {
			gc.gc();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("finish");
	}
}
