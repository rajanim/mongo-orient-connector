package com.bio.cip.indexer;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.constants.ApplicationConstants;
import com.bio.cip.indexer.job.DataColnClearNtMatchKW;
import com.bio.cip.indexer.job.DataCollectionIndexJob;
import com.bio.cip.indexer.job.IndexerJob;
import com.bio.cip.indexer.util.AppConfig;
import com.bio.cip.indexer.util.DataCollectionType.DATACOLLECTIONTYPE;
import com.bio.cip.indexer.util.RIDUtil.RIDTYPE;

/**
 * Application start-up class - all initialization code goes here
 */
public class AppScheduler {

	private static Logger log = LoggerFactory.getLogger(AppScheduler.class);

	public static void main(String[] args) {
		scheduleEntityIndexers();
		scheduleDataCollectionIndexing();
		scheduleClearRecordsNotMatchingKW();
	}

	/**
	 * <p> to schedule Entity Indexing
	 * </p>
	 *
	 */
	private static void scheduleEntityIndexers() {
		int REPEAT_SECS = Integer.parseInt(AppConfig
				.get(ApplicationConstants.REP_INTERVAL_FOR_ENTITIES_INDEXING));
		try {
			Scheduler scheduler = getScheduler();

			for (RIDTYPE rid : RIDTYPE.values()) {
				scheduler.scheduleJob(getJobDetail(rid),
						getTrigger(REPEAT_SECS));
			}
			log.info("Successfully scheduled indexer jobs!");

		} catch (SchedulerException e) {
			log.error("Failed to initialize jobs!", e);
			throw new IllegalStateException("Job Init failed ", e);
		}
	}

	/**
	 * <p>to schedule DataCollection Indexing
	 * </p>
	 *
	 */
	private static void scheduleDataCollectionIndexing() {
		int REPEAT_SECS = Integer
				.parseInt(AppConfig
						.get(ApplicationConstants.REP_INTERVAL_FOR_DATA_COLLECTION_INDEXING));
		try {
			Scheduler scheduler = getScheduler();

			for (DATACOLLECTIONTYPE dcType : DATACOLLECTIONTYPE.values()) {
				scheduler.scheduleJob(getJobDetail(dcType),
						getTrigger(REPEAT_SECS));
			}
			log.info("Successfully scheduled data collection indexer jobs!");

		} catch (SchedulerException e) {
			log.error("Failed to initialize jobs!", e);
			throw new IllegalStateException("Job Init failed ", e);
		}
	}
	
	/**
	 * <p>to schedule Clear Records NotMatching KW
	 * </p>
	 *
	 */
	private static void scheduleClearRecordsNotMatchingKW() {
		int REPEAT_SECS = Integer
				.parseInt(AppConfig
						.get(ApplicationConstants.REP_INTERVAL_FOR_CLEAR_INDEX));
		try {
			Scheduler scheduler = getScheduler();

			for (DATACOLLECTIONTYPE dcType : DATACOLLECTIONTYPE.values()) {
				scheduler.scheduleJob(getJobDetailToClearIndex(dcType),
						getTrigger(REPEAT_SECS));
			}
			log.info("Successfully scheduled data collection indexer jobs!");

		} catch (SchedulerException e) {
			log.error("Failed to initialize jobs!", e);
			throw new IllegalStateException("Job Init failed ", e);
		}
	}


	private static JobDetail getJobDetail(RIDTYPE ridType) {
		JobDetail detail = JobBuilder.newJob(IndexerJob.class)
				.withIdentity(ridType.name()).build();
		detail.getJobDataMap().put(IndexerJob.RIDTYPE_KEY, ridType);
		return detail;
	}
	
	
	private static JobDetail getJobDetail(DATACOLLECTIONTYPE dcType) {
		JobDetail detail = JobBuilder.newJob(DataCollectionIndexJob.class)
				.withIdentity(dcType.name()).build();
		detail.getJobDataMap().put(DataCollectionIndexJob.DCTYPE_KEY, dcType);
		return detail;

	}
	
	private static JobDetail getJobDetailToClearIndex(DATACOLLECTIONTYPE dcType) {
		JobDetail detail = JobBuilder.newJob(DataColnClearNtMatchKW.class)
				.withIdentity(dcType.name()).build();
		detail.getJobDataMap().put(DataCollectionIndexJob.DCTYPE_KEY, dcType);
		return detail;

	}

	private static Trigger getTrigger(final int frequency) {
		return TriggerBuilder
				.newTrigger()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(frequency)
								.repeatForever()).build();
	}

	private static Scheduler getScheduler() throws SchedulerException {
		SchedulerFactory schFactory = new StdSchedulerFactory();
		Scheduler sch = schFactory.getScheduler();
		sch.start();
		return sch;
	}
}