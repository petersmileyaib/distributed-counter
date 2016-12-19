package com.petersmiley.counter;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.petersmiley.counter.CounterLoader;
import com.petersmiley.counter.CounterLoaderImpl;

public class CounterLoaderImplTest {

	private static final String counterLoaderImplTestFilePath = "counterLoaderImplTestFile";
	
	/**
	 * Delete the temp file prior to tests.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		
		// Delete the counterFile
		
		File counterLoaderImplTestFile = new File(counterLoaderImplTestFilePath);
		
		if (counterLoaderImplTestFile.exists()) {
			
			counterLoaderImplTestFile.delete();
		}
	}

	@Test
	public void testReadCount() throws IOException {
		
		CounterLoader counterLoader = new CounterLoaderImpl(counterLoaderImplTestFilePath);
		
		long counter = counterLoader.readCount();
		
		Assert.assertEquals("The default value from a non existent file should be 0", 0L, counter);
		
		counterLoader.writeCount(1L);
		counter = counterLoader.readCount();
		
		Assert.assertEquals("The counter should have been incremented to 1", 1L, counter);
	}

	@Test
	public void testWriteCount() throws IOException {
		
		CounterLoader counterLoader = new CounterLoaderImpl(counterLoaderImplTestFilePath);
		
		counterLoader.writeCount(1L);
		long counter = counterLoader.readCount();
		
		Assert.assertEquals("The counter should have been set to 1", 1L, counter);
	}

}
