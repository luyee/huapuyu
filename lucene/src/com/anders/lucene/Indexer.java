package com.anders.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
	public static void main(String[] args) throws Exception {
		String indexDir = Indexer.class.getResource("index").getPath();
		String dataDir = Indexer.class.getResource("data").getPath();

		final FileFilter textFileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().endsWith(".txt");
			}
		};

		long start = System.currentTimeMillis();
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_34, new StandardAnalyzer(Version.LUCENE_34));
		IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);
		int numIndexed;

		try {
			File[] files = new File(dataDir).listFiles();
			for (File file : files) {
				if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && (textFileFilter == null || textFileFilter.accept(file))) {
					System.out.println("Indexing " + file.getCanonicalPath());
					Document doc = new Document();
					doc.add(new Field("contents", new FileReader(file)));
					doc.add(new Field("filename", file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
					doc.add(new Field("fullpath", file.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
					indexWriter.addDocument(doc);
				}
			}
			numIndexed = indexWriter.numDocs();
		} finally {
			indexWriter.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
	}
}