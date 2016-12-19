package com.petersmiley.counter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Used for reading/writing to a file with the counter value.
 * 
 * @author psmiley
 *
 */
@Component
public class CounterLoaderImpl implements CounterLoader {

	private Path counterFilePath;
	
	private File counterFile;
	
	@Autowired
	public CounterLoaderImpl(@Value("${counter.file.path}") String counterFileSystemPath) throws IOException {
		
		counterFilePath = Paths.get(counterFileSystemPath);
		
		counterFile = counterFilePath.toFile();
	}
	
	@Override
	public long readCount() throws IOException {
		
		if (counterFile.exists()) {
			
			byte[] counterFileBytes = Files.readAllBytes(counterFilePath);
			
			return bytesToLong(counterFileBytes);
		}
		
		long defaultReturnCounter = 0l;
		
		return defaultReturnCounter;
	}

	@Override
	public void writeCount(long counter) throws IOException {
		
		Files.write(counterFilePath, longToBytes(counter));
	}
	
	private long bytesToLong(byte[] bytes) {
		
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip(); // need flip 
	    
	    return buffer.getLong();
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}

}
