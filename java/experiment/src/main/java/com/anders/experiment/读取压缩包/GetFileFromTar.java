package com.anders.experiment.读取压缩包;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class GetFileFromTar {

	public static void visitTARGZ(File targzFile) throws IOException {
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;
		GZIPInputStream gzipInputStream = null;
		TarArchiveInputStream tarArchiveInputStream = null;
		try {
			fileInputStream = new FileInputStream(targzFile);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			gzipInputStream = new GZIPInputStream(bufferedInputStream);
			tarArchiveInputStream = new TarArchiveInputStream(gzipInputStream);
			TarArchiveEntry entry = null;

			while ((entry = tarArchiveInputStream.getNextTarEntry()) != null) {
				if (entry.isDirectory())
					continue;
				// configure(taris, ((TarArchiveEntry) entry).getFile());
				// //process every entry in this tar file.

				// System.out.println(entry.getGroupId());
				// System.out.println(entry.getGroupName());
				System.out.println(entry.getName());
				File file = entry.getFile();
				// System.out.println(file.getName());
				System.out.println(entry.getSize());
				// FileInputStream fo = new FileInputStream(file);

				// byte[] b = new byte[(int) entry.getSize()];
				// // fo.read(b);
				// taris.read(b);
				// taris.read(b, 0, (int) entry.getSize());
				// System.out.println(b.length);
				// System.out.println(b);
			}
		} finally {
			tarArchiveInputStream.close();
			gzipInputStream.close();
			bufferedInputStream.close();
			fileInputStream.close();
		}
	}

	public static void main(String[] args) throws IOException {
		File file = new File("E:/zookeeper-3.4.6.tar.gz");
		visitTARGZ(file);
	}
}
