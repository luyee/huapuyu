package com.anders.dp.结构模式.合成;

import java.util.ArrayList;
import java.util.List;

public class 文件夹 implements IFile {
	private List<IFile> fileList = new ArrayList<IFile>();

	@Override
	public IFile getFile() {
		return this;
	}

	@Override
	public void 显示文件信息(String msg) {
		System.out.println(msg + this.getClass().getName());

		for (IFile file : fileList) {
			file.显示文件信息(msg + "--");
		}
	}

	@Override
	public void add(IFile component) {
		fileList.add(component);
	}

	@Override
	public void remove(IFile component) {
		component.remove(component);
	}

}
