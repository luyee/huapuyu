package com.anders.dp.合成;

public interface IFile {
	public IFile getFile();

	public void 显示文件信息(String msg);

	public void add(IFile file);

	public void remove(IFile file);
}
