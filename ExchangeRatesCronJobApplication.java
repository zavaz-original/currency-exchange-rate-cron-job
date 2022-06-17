package com.sb_juhav.siili_exchange_rates_cronjob;

/**
 * @author Juha Valimaki, Siili Candidate by Virnex 2022
 *
 */

import java.util.Collection;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ExchangeRatesCronJobApplication { 

	/* This apikey is a dummy String at the moment on 2022-06-14.
	 * Quartz does not accept a job class where the constructor
	 * has a parameter(s).
	 * This App needs to be rewritten to use a key/value map to pass
	 * the apikey to the job class.
	 * I had no time to rewrite this App accordingly yet.
	 */

	private String apikey = "HZOwa1dYHjD4MEEA0KNPsOiOEwqhQcvt"; // dummy

	private StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();

	public ExchangeRatesCronJobApplication(String apikey)
			throws SchedulerException {

		this.apikey = apikey;

		// the Job Class Name as argument
		JobDetail job = JobBuilder.newJob(ExchangeRatesFetchJob.class) 
				.build();


		// String cronExpression5min   = "0 0/5 * * * ?";	// every 5 min"
		// String cronExpression1 = "0/1 * * * * ?";		// every second"		
		// String cronExpression7 = "0/7 * * * * ?";		// every 7 seconds"
		// String cronExpressionX = "0/30 0/30 0/2 * * ?";	// every 2 hours, 30 min, 30 sec
		//String cronExpression1m   = "0 0/1 * * * ?";		// every 1 min"

		String cronExpression1h   = "0 0 0/1 * * ?";		// every 1 hour"
		

		//create schedule builder
		
		// !!! CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule( cronExpression1h );

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule( cronExpression1h );

		//create trigger which the schedule Builder
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(scheduleBuilder)
				.build();

		//create scheduler 
		Scheduler scheduler = stdSchedulerFactory.getScheduler();

		// start your scheduler
		scheduler.start();

		// triggeredCount++;

		// let the scheduler call the Job using trigger
		scheduler.scheduleJob(job, trigger);
	}

	// no idea if stop() works, never called from anywhere!
	public void stop() 
			throws Exception {
		try {
			Collection<Scheduler> allSchedulers = this.stdSchedulerFactory.getAllSchedulers();
			for (Scheduler scheduler : allSchedulers) {
				scheduler.shutdown(true);
			}
		} catch (SchedulerException e) {
			throw new Exception( "Exception in ExchangeRatesCronJobApplication.stop() " + e.toString() );
		}
	}

	// String apikey = "sB0NIeQ0Tr5srJ6lvwKMlBpvnXzIoyRY";     // New account & new apikey

	// !!! apikey in main has no effect. !!!
	// the apikey is fixed in ExchangeRatesFetchJob.java,
	// because Quartz did not accept a constructor with
	// an apikey as an argument.
	public static void main(String[] args) {

		// old apikey, free account limit of requests exceeded at API server web site
		String apikey = "HZOwa1dYHjD4MEEA0KNPsOiOEwqhQcvt";  // dummy

		if ( args != null ) {
			if ( args.length == 1 )
				apikey = args[0];		
		}

		ExchangeRatesCronJobApplication exchangeRatesCronJobApplication;
		try {
			exchangeRatesCronJobApplication = new ExchangeRatesCronJobApplication( apikey );
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
