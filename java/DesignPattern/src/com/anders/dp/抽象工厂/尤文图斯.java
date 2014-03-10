package com.anders.dp.抽象工厂;

public class 尤文图斯 implements I俱乐部 {

	@Override
	public I前锋 factory前锋() {
		return new 皮耶罗();
	}

	@Override
	public I球场 factory球场() {
		return new 阿尔卑球场();
	}

}
