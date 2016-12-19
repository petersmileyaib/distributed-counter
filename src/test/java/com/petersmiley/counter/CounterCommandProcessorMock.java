package com.petersmiley.counter;

import java.io.IOException;

public class CounterCommandProcessorMock implements CounterCommandProcessor {

	private long counter;
	
	@Override
	public long incrementAndGet() throws IOException {

		return ++counter;
	}

	@Override
	public long get() {

		return counter;
	}

	@Override
	public long decrementAndGet() throws IOException {

		return --counter;
	}

}
