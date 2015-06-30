package com.anders.experiment.读取压缩包;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GetFileFromJar {

	public static void main(String[] args) throws IOException {
		JarFile jarFile = new JarFile("e:/data.jar");
		Enumeration<JarEntry> jarEntries = jarFile.entries();

		InputStream is = jarFile.getInputStream(jarFile
				.getEntry("target/test-classes/persistence-test.xml"));

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// System.out.println(jarFile.getEntry(
			// "target/test-classes/persistence-test.xml").getName());

			System.out.println(sb.toString());

			while (jarEntries.hasMoreElements()) {
				JarEntry jarEntry = jarEntries.nextElement();
				// System.out.println(jarEntry.getName());
			}

		}
	}
}
