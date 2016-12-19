package com.petersmiley.counter;

import org.junit.Test;

import org.junit.Assert;

public class CounterMessageProcessorImplTest {

	@Test
	public void testProcessInvalidCounterMessage() {
		
		CounterCommandProcessorMock counterCommandProcesssor = new CounterCommandProcessorMock();
		CounterMessageProcessorImpl counterMessageProcessor = new CounterMessageProcessorImpl(counterCommandProcesssor);
		
		counterMessageProcessor.processCounterMessage(null);
		
		Assert.assertEquals("Should have been no modification of the count as a result of invalid message", 0L, counterCommandProcesssor.get());
		
		counterMessageProcessor.processCounterMessage("invalid command");
		
		Assert.assertEquals("Should have been no modification of the count as a result of invalid message", 0L, counterCommandProcesssor.get());
	}
	
	@Test
	public void testProcessCounterMessages() {
		
		CounterCommandProcessorMock counterCommandProcesssor = new CounterCommandProcessorMock();
		CounterMessageProcessorImpl counterMessageProcessor = new CounterMessageProcessorImpl(counterCommandProcesssor);
		
		counterMessageProcessor.processCounterMessage("incrementAndGet");
		
		Assert.assertEquals("Should have incremented the count", 1L, counterCommandProcesssor.get());
		
		counterMessageProcessor.processCounterMessage("decrementAndGet");
		
		Assert.assertEquals("Should have decremented the count", 0L, counterCommandProcesssor.get());
		
	}

}
