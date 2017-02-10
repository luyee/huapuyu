package com.anders.experiment.读取压缩包;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GetFileFromWar {

	public static void main(String[] args) throws IOException {
		JarFile jarFile = new JarFile(
				"e:/webapp.war");
		Enumeration<JarEntry> jarEntries = jarFile.entries();

		while (jarEntries.hasMoreElements()) {
			System.out.println(jarEntries.nextElement().getName());
		}
	}
}
