package com.bio.cip.indexer.job;

import com.bio.cip.indexer.service.IndexerService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.indexer.util.RIDUtil;

/**
 * A scheduled job that invokes indexing based on context information
 */
@DisallowConcurrentExecution
public class IndexerJob implements Job {

	private static Logger log = LoggerFactory.getLogger(IndexerJob.class);

	public static final String RIDTYPE_KEY = "ridType";

	public void execute(JobExecutionContext jex) throws JobExecutionException {
		RIDUtil.RIDTYPE ridType = (RIDUtil.RIDTYPE) jex.getJobDetail()
				.getJobDataMap().get(RIDTYPE_KEY);

		log.info("Indexer job executed successfully @"
				+ System.currentTimeMillis() + " for entity type "
				+ ridType.name());
		IndexerService.doIndex(ridType);
	}
}
