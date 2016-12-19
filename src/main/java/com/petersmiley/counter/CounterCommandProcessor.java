package com.petersmiley.counter;

import java.io.IOException;

/**
 * Counter command processor. Supported counter operations methods, increment, get and decrement
 * 
 * @author psmiley
 *
 */
public interface CounterCommandProcessor {

	/***
	 * Increment the counter and return the count
	 * @return counter
	 * @throws IOException
	 */
	long incrementAndGet() throws IOException;

	/**
	 * Get the current counter value
	 * @return counter
	 */
	long get();

	/**
	 * Decrement the counter and return the count
	 * @return counter
	 * @throws IOException
	 */
	long decrementAndGet() throws IOException;

}