/**
 * 
 */
package com.sb_juhav.siili_exchange_rates_cronjob;

// class com.sb_juhav.siili_exchange_rates_cronjob.ExchangeRateRequestDTO
/**
 * @author Juha Valimaki, Siili Candidate by Virnex 2022
 *
 */

public class ExchangeRateRequestDTO {

	/*
	Request:
		from
		to
		from_amount
	 */

	private String  from;			// id of from currency: EUR, SEK, USD, ...
	private String  to;				// id of to currency: EUR, SEK, USD, ...
	private int fromAmount;		// amount of from currency


	public ExchangeRateRequestDTO() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}


	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}


	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}


	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}


	/**
	 * @return the from_amount
	 */
	public int getFromAmount() {
		return fromAmount;
	}


	/**
	 * @param from_amount the from_amount to set
	 */
	public void setFromAmount(int fromAmount) {
		this.fromAmount = fromAmount;
	}


	@Override
	public String toString() {
		return "ExchangeRateRequestDTO [from=" + from + ", to=" + to + ", fromAmount=" + fromAmount + "]";
	}

}
