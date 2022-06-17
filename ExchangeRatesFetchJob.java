package com.sb_juhav.siili_exchange_rates_cronjob;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Juha Valimaki, Siili Candidate by Virnex 2022
 *
 */

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/* I have to rewrite this App later. Quartz cannot accept a constructor
 * with a parameter. In a hurry I wrote this App with the apikey fixed
 * in the code. This App works ok like that. Then I tried to make the 
 * apikey an input parameter to the constructor. Quartz rejected the 
 * constructor with a parameter.
 * 
 * Later I found from web an example App, where parameters can be sent
 * to Quartz in a key/value parameter map. The structure of the example
 * code is very different. I had no time to rewrite this App accordingly.
 * 
 * Therefore the apikey has a value fixed in the source code of this version!!! 
 */

public class ExchangeRatesFetchJob implements Job {

	public static final String fullClassName = "com.sb_juhav.siili_exchange_rates_cronjob.ExchangeRatesFetchJob";

	private static final String apikey1		= "HZOwa1dYHjD4MEEA0KNPsOiOEwqhQcvt";
	private static final String apikey2		= "sB0NIeQ0Tr5srJ6lvwKMlBpvnXzIoyRY"; // N
	private static final String dummyApikey	= "dummy apikey value";

	private static final List<String> fromList	= List.of("USD", "USD", "EUR", "EUR", "SEK", "SEK");
	private static final List<String> toList 	= List.of("EUR", "SEK", "USD", "SEK", "USD", "EUR");
	private static final int size = fromList.size();

	private static final ExchangeRateRequestDTO exchangeRateRequestDTO   = new ExchangeRateRequestDTO();

	private static final String dummyString = "DUMMY";

	private static final String apikeyFileName = "apikey.txt";

	public static String apikeyFileNameWithPathWithPath = dummyString;

	private static String apikey = dummyString;

	// Spent more than a day to get reading a text file working.
	// The paths to the file are mapped to different locations in Eclipse
	// with some unexpected way which is against my logic.
	// Got File Not Found again and again trying different hints from web. 
	private static final synchronized String getApikeyFromFile() {

		if ( apikey.equals(dummyString) == false) {
			return apikey;
		}

		Class cls = null;
		try {
			cls = Class.forName(fullClassName);

			// returns the ClassLoader object associated with this Class
			ClassLoader classLoader = cls.getClassLoader();

			// System.out.println(classLoader.getClass());

			URL url = classLoader.getResource(apikeyFileName);

			/*
			System.out.println("Value11 = " + url.toString());
			System.out.println("Value12 = " + url.getFile());
			System.out.println("Value13 = " + url.getPath());
			System.out.println("Value14 = " + url.toExternalForm());
			System.out.println("Value15 = " + url.getContent().toString());


			// this maps to a file under a wrong class
			URL url2 = cLoader.getResource("input_files_dir/apikey.txt");
			System.out.println("Value21 = " + url2.toString());
			System.out.println("Value22 = " + url2.getFile());
			System.out.println("Value23 = " + url2.getPath());
			System.out.println("Value24 = " + url2.toExternalForm());
			System.out.println("Value25 = " + url2.getContent().toString());
			 */

			String tmpApikeyFileNameWithPath = url.getFile();
			int startPos = tmpApikeyFileNameWithPath.indexOf(":") + 1;

			apikeyFileNameWithPathWithPath = tmpApikeyFileNameWithPath.substring(startPos);

			System.out.println("INFO: apikey filename with path: " + apikeyFileNameWithPathWithPath);

			String apikeyFileContent = Files.readString(Paths.get(apikeyFileNameWithPathWithPath));

			System.out.println("INFO: apikey file content: " + apikeyFileContent);

			int startIndex = apikeyFileContent.indexOf("=") + 1;
			if (startIndex >= 1) {
				String apikeyCandidate = apikeyFileContent.substring(startIndex, startIndex + apikey1.length());
				System.out.println("apiKeyCandidate: " + apikeyCandidate);

				if ( apikeyCandidate.length() == apikey1.length() ) {
					System.out.println("INFO: apikey was read from the file, apikey: " + apikeyCandidate);
					return apikeyCandidate;

				} else {
					System.out.println("--- : Failed updating apikey from file " + apikeyFileNameWithPathWithPath);
					System.out.println("--- : Invalid value & format is returned as apikey: " + dummyString);
				}
			}
		} catch (Exception e) {
			System.out.println("--- : Exception in ExchangeRatesFetchJob.getApikeyFromFile(" + apikeyFileNameWithPathWithPath + ") " + e.toString());
		}

		return dummyString;
	}


	// To get the Exchange Rate the amount of from-currency can always be set to to 1 for clarity.
	// This is not necessary as the rate is a different independent field.
	// So the "to-amount" field will be equal to the exchangeRate field.

	// Later on in a rewritten version apikey will be passed in a key/value map in
	// a special Quartz way as constructor parameters are not allowed.
	// Due to lack of time the apikey is read from a text file for the moment.

	public ExchangeRatesFetchJob() {
		apikey = getApikeyFromFile();
		exchangeRateRequestDTO.setFromAmount(1);
	}

	/*  
	 	The below constructor with apikey as parameter would have no problem,
	    but constructor parameters not allowed by Quartz Job.
	 */
	// !!! unused as Job does not allow any parameter in the constructor !!!
	public ExchangeRatesFetchJob( String apikey) {
		ExchangeRatesFetchJob.apikey = apikey;   // setting a static class variable
		exchangeRateRequestDTO.setFromAmount(1);
	}

	@Override
	public void execute(JobExecutionContext context) 
			throws JobExecutionException {

		System.out.println("INFO: Quartz Scheduler triggered start of a run");

		for (int i = 0; i < size ; i++ ) {
			exchangeRateRequestDTO.setFrom( fromList.get(i) );
			exchangeRateRequestDTO.setTo( toList.get(i) );	

			try {

				// call a static method in the ExchangeRateDao class
				ExchangeRateDao.fetch( exchangeRateRequestDTO, apikey);

			} catch (Exception e) {
				System.out.println("--- Exception in fetch: " + e);
				e.printStackTrace();
			} 
		}

		System.out.println("INFO: Quartz Scheduler finished the run");
	}
}
