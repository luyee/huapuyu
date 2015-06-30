package com.anders.dp.结构模式.合成;

public class 文件 implements IFile {

	@Override
	public IFile getFile() {
		return this;
	}

	@Override
	public void 显示文件信息(String msg) {
		System.out.println(msg + this.getClass().getName());
	}

	@Override
	public void add(IFile file) {
		throw new RuntimeException("Can't invoke this method!");
	}

	@Override
	public void remove(IFile file) {
		throw new RuntimeException("Can't invoke this method!");
	}

}
