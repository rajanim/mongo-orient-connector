package com.bio.cip.indexer.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexTest {

	private static final String INDX_DIR = "target/test-index-data";
	private static final String TESTDATA_RESOURCE_PATH = "testdata/index/content.txt";

	private static final String INDEX_FIELD_UNIQID = "UNIQ_ID";
	private static final String INDEX_FIELD_CONTENT = "CONTENT";

	private static final String SEARCH_TERM = "Al Jazeera";

	private static final int PAGE_NO = 3;
	private static final int PAGE_SIZE = 5;

	private static final Version LUCENE_VERSION = org.apache.lucene.util.Version.LUCENE_4_9;

	private static File _indexDirectory;
	private static File _dataDirectory;

	private static Logger log = LoggerFactory.getLogger(IndexTest.class);

	@BeforeClass
	public static void beforeAll() throws IOException, URISyntaxException {
		_indexDirectory = new File(INDX_DIR);
		deleteIndexDirIfExists();

		_dataDirectory = new File(ClassLoader.getSystemResource(
				TESTDATA_RESOURCE_PATH).toURI());
	}

	@AfterClass
	public static void afterAll() throws IOException {
		deleteIndexDirIfExists();
	}

	private static void deleteIndexDirIfExists() throws IOException {
		if (_indexDirectory.exists()) {
			FileUtils.deleteDirectory(_indexDirectory);
		}
	}

	@Test
	public void testIndex() throws Exception {
		Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);
		IndexWriter indexWriter = new IndexWriter(
				FSDirectory.open(_indexDirectory), new IndexWriterConfig(
						LUCENE_VERSION, analyzer));

		List<String> allLines = FileUtils.readLines(_dataDirectory,
				StandardCharsets.UTF_8);

		for (String line : allLines) {
			Document document = new Document();
			document.add(new LongField(INDEX_FIELD_UNIQID, System
					.currentTimeMillis(), Field.Store.YES));
			document.add(new TextField(INDEX_FIELD_CONTENT, line,
					Field.Store.YES));
			indexWriter.addDocument(document);
		}

		int countIndexed = indexWriter.maxDoc();
		indexWriter.close();
		Assert.assertEquals(allLines.size(), countIndexed);
	}

	@Test
	public void testSearch() throws IOException, ParseException {

		IndexReader reader = DirectoryReader.open(FSDirectory
				.open(_indexDirectory));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);
		QueryParser queryParser = new QueryParser(LUCENE_VERSION,
				INDEX_FIELD_CONTENT, analyzer);
		Query query = queryParser.parse(SEARCH_TERM);

		TopDocs results = searcher.search(query, PAGE_NO * PAGE_SIZE, new Sort(
				new SortField(INDEX_FIELD_UNIQID, SortField.Type.LONG)));
		ScoreDoc[] hits = results.scoreDocs;
		int hitCount = results.totalHits;

		int startIndex;
		int endIndex;

		if (hitCount == 0) {
			startIndex = endIndex = 0;
		}
		startIndex = ((PAGE_NO - 1) * PAGE_SIZE);
		endIndex = Math.min((PAGE_NO * PAGE_SIZE) - 1, (hitCount - 1));

		if (startIndex > endIndex) {
			startIndex = Math.max(0, ((endIndex - PAGE_SIZE) + 1));
		}

		for (int indx = startIndex; indx <= endIndex; indx++) {
			Document doc = searcher.doc(hits[indx].doc);
			log.info(doc.toString());
		}
		reader.close();
	}
}