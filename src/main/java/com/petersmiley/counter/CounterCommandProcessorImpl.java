package com.petersmiley.counter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterCommandProcessorImpl implements CounterCommandProcessor {

	private CounterLoader counterLoader;
	
	private long counter;
	
	@Autowired
	public CounterCommandProcessorImpl(CounterLoader counterLoader) throws IOException {
		
		this.counterLoader = counterLoader;
		
		counter = counterLoader.readCount();
	}
	
	/* (non-Javadoc)
	 * @see com.petersmiley.counter.CounterCommandProcessor#incrementAndGet()
	 */
	@Override
	public synchronized long incrementAndGet() throws IOException {
		
		counter++;
		
		counterLoader.writeCount(counter);
		
		return counter;
	}
	
	/* (non-Javadoc)
	 * @see com.petersmiley.counter.CounterCommandProcessor#get()
	 */
	@Override
	public synchronized long get() {
		
		return counter;
	}
	
	/* (non-Javadoc)
	 * @see com.petersmiley.counter.CounterCommandProcessor#decrementAndGet()
	 */
	@Override
	public synchronized long decrementAndGet() throws IOException {
		
		counter--;
		
		counterLoader.writeCount(counter);
		
		return counter;
	}
}
