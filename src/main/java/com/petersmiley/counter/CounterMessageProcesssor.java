package com.petersmiley.counter;

/**
 * Process the incoming message with the counter command.
 * 
 * @author psmiley
 *
 */
public interface CounterMessageProcesssor {

	/**
	 * Processs the incoming message with the counter payload
	 * @param counterPayload
	 * @return responseMessage
	 */
	String processCounterMessage(String counterPayload);
	
}
