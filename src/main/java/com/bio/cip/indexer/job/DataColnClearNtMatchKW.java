package com.bio.cip.indexer.job;

import com.bio.cip.indexer.service.DataColnClearRecordsService;
import com.bio.cip.indexer.util.DataCollectionType;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A scheduled job that invokes indexing based on context information
 */
@DisallowConcurrentExecution
public class DataColnClearNtMatchKW implements Job {

	private static Logger log = LoggerFactory.getLogger(DataColnClearNtMatchKW.class);

	public static final String DCTYPE_KEY = "dcType";

	public void execute(JobExecutionContext jex) throws JobExecutionException {
		DataCollectionType.DATACOLLECTIONTYPE dcType = (DataCollectionType.DATACOLLECTIONTYPE) jex.getJobDetail()
				.getJobDataMap().get(DCTYPE_KEY);

		log.info("Clear index job executed successfully @"
				+ System.currentTimeMillis() + " for entity type "
				+ dcType.name());
		DataColnClearRecordsService.clear(dcType);
	}
}
