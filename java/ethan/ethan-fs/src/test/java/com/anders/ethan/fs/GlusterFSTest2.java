package com.anders.ethan.fs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GlusterFSTest2 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() throws IOException {
	}

	@Test
	public void testRead() throws IOException, URISyntaxException {
		String mountUri = "gluster://192.168.56.110:gfstest/";
		// Path mountPath = Paths.get(new URI(mountUri));
		FileSystem fileSystem = FileSystems.newFileSystem(new URI(mountUri), null);
		FileStore store = fileSystem.getFileStores().iterator().next();
		System.out.println("TOTAL SPACE: " + store.getTotalSpace());
		System.out.println("USABLE SPACE: " + store.getUsableSpace());
		System.out.println("UNALLOCATED SPACE: " + store.getUnallocatedSpace());
		System.out.println(fileSystem.toString());

	}

}
