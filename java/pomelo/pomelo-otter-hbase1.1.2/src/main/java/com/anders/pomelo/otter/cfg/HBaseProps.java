package com.anders.pomelo.otter.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hbase")
public class HBaseProps {

	@Autowired
	private HBaseZkProps hBaseZkProps;

	public HBaseZkProps getZkProps() {
		return hBaseZkProps;
	}

	public void setZkProps(HBaseZkProps hBaseZkProps) {
		this.hBaseZkProps = hBaseZkProps;
	}

}
