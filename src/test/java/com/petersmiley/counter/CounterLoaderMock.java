package com.petersmiley.counter;

import java.io.IOException;

public class CounterLoaderMock implements CounterLoader {

	private long counter;
	
	@Override
	public long readCount() throws IOException {
		
		return counter;
	}

	@Override
	public void writeCount(long counter) throws IOException {
		
		this.counter = counter;
	}

}
