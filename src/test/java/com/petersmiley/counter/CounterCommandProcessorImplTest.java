package com.petersmiley.counter;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class CounterCommandProcessorImplTest {

	@Test
	public void testIncrementAndGet() throws IOException {
	
		
		CounterCommandProcessorImpl counterCommandProcessor = new CounterCommandProcessorImpl(new CounterLoaderMock());
		
		long counter = counterCommandProcessor.incrementAndGet();
		
		Assert.assertEquals("Counter should be incremented to 1", 1L, counter);
	}

	@Test
	public void testGet() throws IOException {
		
		CounterCommandProcessorImpl counterCommandProcessor = new CounterCommandProcessorImpl(new CounterLoaderMock());
		
		long counter = counterCommandProcessor.get();
		
		Assert.assertEquals("Counter should be defaulted to 0", 0L, counter);
		
		counterCommandProcessor.incrementAndGet();
		counter = counterCommandProcessor.get();
		
		Assert.assertEquals("Counter should have been incremented to 1", 1L, counter);
		
		counterCommandProcessor.decrementAndGet();
		counter = counterCommandProcessor.get();
		
		Assert.assertEquals("Counter should have been decremented to 0", 0L, counter);
	}

	@Test
	public void testDecrementAndGet() throws IOException {
		
		CounterCommandProcessorImpl counterCommandProcessor = new CounterCommandProcessorImpl(new CounterLoaderMock());
		
		long counter = counterCommandProcessor.decrementAndGet();
		
		Assert.assertEquals("Counter should be decremented to -1", -1L, counter);
	}

}
