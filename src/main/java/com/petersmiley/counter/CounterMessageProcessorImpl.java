package com.petersmiley.counter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterMessageProcessorImpl implements CounterMessageProcesssor {

	private static final Logger logger = LoggerFactory.getLogger(CounterMessageProcessorImpl.class);
	
	enum CounterCommandType {
		
		UNKNOWN,
		INCREMENT_AND_GET,
		GET,
		DECREMENT_AND_GET
	}
	
	private CounterCommandProcessor counterCommandProcesssor;
	
	@Autowired
	public CounterMessageProcessorImpl(CounterCommandProcessor counterCommandProcesssor) {
		
		this.counterCommandProcesssor = counterCommandProcesssor;
	}
	
	public String processCounterMessage(String counterPayload) {
		
		StringBuilder responseBuilder = new StringBuilder();
		
		// echo the input back to the client
		echoInput(responseBuilder, counterPayload);
		
		// Determine the counter command type
		CounterCommandType counterCommandType = determineCounterCommandType(counterPayload);
		
		// Process the counter command
		processCounterCommand(counterCommandType, responseBuilder);
		
		return responseBuilder.toString();
	}

	private void processCounterCommand(CounterCommandType counterCommandType, StringBuilder responseBuilder) {
		
		try {
			
			switch (counterCommandType) {
			
				case UNKNOWN:
					
					responseBuilder
						.append("Unknown command supplied. Please supply one of: \r\n")
						.append("\t1. \"incrementAndGet\" - increments the counter and returns the value after incrementing\r\n")
						.append("\t2. \"get\" - gets the current value of the counter\r\n")
						.append("\t3. \"decrementAndGet\" - decrements the counter and returns the value after decrementing\r\n");
					
					return;
					
				case INCREMENT_AND_GET:
					
					responseBuilder
						.append("incremented counter, current count: ")
						.append(counterCommandProcesssor.incrementAndGet());
					
					return;
					
				case GET:
					
					responseBuilder
						.append("current counter: ")
						.append(counterCommandProcesssor.get());
					
					return;
					
				case DECREMENT_AND_GET:
					
					responseBuilder
						.append("decremented counter, current count: ")
						.append(counterCommandProcesssor.decrementAndGet());
					
					return;
			} 
		
		} catch (IOException e) {
			
			String errorMessage = "Unable to access the current counter value";
			
			logger.error(errorMessage, e);
			
			responseBuilder.append(errorMessage);
		}
	}

	/**
	 * Determine which command type is being requested.
	 * 
	 * @param counterPayload
	 * @return counterCommandType
	 */
	private CounterCommandType determineCounterCommandType(String counterPayload) {
	
		if (StringUtils.isBlank(counterPayload)) {
			
			logger.warn("Blank string received cannot process command");
			
			return CounterCommandType.UNKNOWN;
		}
		
		switch (counterPayload) {
		
			case "incrementAndGet":
			
				return CounterCommandType.INCREMENT_AND_GET;
			
			case "get":
			
				return CounterCommandType.GET;
			
			case "decrementAndGet": 
			
				return CounterCommandType.DECREMENT_AND_GET;
		}
		
		logger.warn("Unknown counter command recieved: " + counterPayload);
		
		return CounterCommandType.UNKNOWN;
	}

	/**
	 * Echo the input command back to the client.
	 * 
	 * @param responseBuilder
	 * @param counterPayload
	 */
	private void echoInput(StringBuilder responseBuilder, String counterPayload) {
		
		responseBuilder
			.append("input: ")
			.append(counterPayload)
			.append("\r\n");
	}
}
