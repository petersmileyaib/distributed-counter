package com.petersmiley.counter;

import java.io.IOException;

/**
 * used to persist the counter to a store
 * 
 * @author psmiley
 *
 */
public interface CounterLoader {

	/**
	 * Read the counter. 
	 * If none available will default to 0l
	 * @return counter
	 * @throws IOException 
	 */
	long readCount() throws IOException;
	
	
	/**
	 * Write the counter to the store
	 * @throws IOException 
	 */
	void writeCount(long counter) throws IOException;
}
