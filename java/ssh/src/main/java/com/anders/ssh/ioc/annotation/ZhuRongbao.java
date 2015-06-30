package com.anders.ssh.ioc.annotation;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component(value = "zhuRongbao")
@Component
public class ZhuRongbao {
	@Autowired
	// @Qualifier(value = "zhuZhen")
	private IZhuZhen zhuZhen;
	@Autowired
	private HuangXiaoyan huangXiaoyan;
	@Resource
	private GuoLili guoLili;
	// @Resource(mappedName="")
	// @Resource
	@Autowired
	private com.anders.ssh.ioc.annotation.sub.GuoLili guoLili2;

	// public ZhuRongbao(ZhuZhen zhuZhen, HuangXiaoyan huangXiaoyan, GuoLili guoLili)
	// {
	// this.zhuZhen = zhuZhen;
	// this.huangXiaoyan = huangXiaoyan;
	// this.guoLili = guoLili;
	// }

	public ZhuRongbao() {
		super();
	}

	public IZhuZhen getZhuZhen() {
		return zhuZhen;
	}

	// @Autowired
	public void setZhuZhen(IZhuZhen zhuZhen) {
		this.zhuZhen = zhuZhen;
	}

	public HuangXiaoyan getHuangXiaoyan() {
		return huangXiaoyan;
	}

	// @Autowired
	public void setHuangXiaoyan(HuangXiaoyan huangXiaoyan) {
		this.huangXiaoyan = huangXiaoyan;
	}

	public GuoLili getGuoLili() {
		return guoLili;
	}

	// @Autowired
	public void setGuoLili(GuoLili guoLili) {
		this.guoLili = guoLili;
	}

	public com.anders.ssh.ioc.annotation.sub.GuoLili getGuoLili2() {
		return guoLili2;
	}

	public void setGuoLili2(com.anders.ssh.ioc.annotation.sub.GuoLili guoLili2) {
		this.guoLili2 = guoLili2;
	}

	@Override
	public String toString() {
		return "我是朱荣宝，老婆[" + huangXiaoyan.toString() + "]；儿子[" + zhuZhen.toString() + "]；媳妇[" + guoLili.toString() + "]";
	}
}
