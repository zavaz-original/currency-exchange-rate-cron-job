package com.sb_juhav.siili_exchange_rates_cronjob;

import java.util.Date;

/**
 * @author Juha Valimaki, Siili Candidate by Virnex 2022
 *
 */


/*
 Response:
 	Date
	from
	to
	toAmount
	exchangeRate
 */

public class ExchangeRateResponseDTO {

	private Date date;
	private String from;			// id of "from-currency":	EUR, SEK, USD, ...
	private String to;				// id of "to-currency":	EUR, SEK, USD, ...
	private int fromAmount;			// amount of "from-currency"
	private double toAmount;		// amount of "to-currency"
	private double exchangeRate;	// amount of "to-currency" for 1 unit of "from-currency"

	public ExchangeRateResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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
	 * @return the fromAmount
	 */
	public int getFromAmount() {
		return fromAmount;
	}

	/**
	 * @param fromAmount the fromAmount to set
	 */
	public void setFromAmount(int fromAmount) {
		this.fromAmount = fromAmount;
	}

	/**
	 * @return the toAmount
	 */
	public double getToAmount() {
		return toAmount;
	}

	/**
	 * @param toAmount the toAmount to set
	 */
	public void setToAmount(double toAmount) {
		this.toAmount = toAmount;
	}

	/**
	 * @return the exchangeRate
	 */
	public double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Override
	public String toString() {
		return "ExchangeRateResponseDTO [date=" + date + ", from=" + from + ", to=" + to + ", fromAmount=" + fromAmount + ", toAmount="
				+ toAmount + ", exchangeRate=" + exchangeRate + "]";
	}

	public String toLogString() {
		return "{date:" + "\"" + date + "\"" + ",from:" + from + ",to:" + to + ",fromAmount:" + fromAmount + ",toAmount:"
				+ toAmount + ",exchangeRate:" + exchangeRate + "}";
	}





}
