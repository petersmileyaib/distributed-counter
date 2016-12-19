package com.petersmiley;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedCounterAcceptanceTests {

	/**
	 * Delete the temp file prior to tests.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// Delete the counterFile

		File counterLoaderImplTestFile = new File("counterFile");

		if (counterLoaderImplTestFile.exists()) {

			counterLoaderImplTestFile.delete();
		}
	}

	@Test
	public void testIncrementGetAndDecrement() throws UnknownHostException, IOException {

		try (
				Socket socket = SocketFactory.getDefault().createSocket("localhost", 9999);
				InputStream inputStream = socket.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			) {

			socket.getOutputStream().write("incrementAndGet\r\n".getBytes());

			String response = bufferedReader.readLine();
			response = bufferedReader.readLine();

			Assert.assertEquals("incremented counter, current count: 1", response);
			
			socket.getOutputStream().write("get\r\n".getBytes());

			response = bufferedReader.readLine();
			response = bufferedReader.readLine();

			Assert.assertEquals("current counter: 1", response);
			
			socket.getOutputStream().write("decrementAndGet\r\n".getBytes());

			response = bufferedReader.readLine();
			response = bufferedReader.readLine();

			Assert.assertEquals("decremented counter, current count: 0", response);
		}
	}
	
	@Test
	public void testMultipleClients() throws UnknownHostException, IOException {

		try (
				Socket socketOne = SocketFactory.getDefault().createSocket("localhost", 9999);
				InputStream inputStreamOne = socketOne.getInputStream();
				BufferedReader bufferedReaderOne = new BufferedReader(new InputStreamReader(socketOne.getInputStream()));
				
				Socket socketTwo = SocketFactory.getDefault().createSocket("localhost", 9999);
				InputStream inputStreamTwo = socketTwo.getInputStream();
				BufferedReader bufferedReaderTwo = new BufferedReader(new InputStreamReader(socketTwo.getInputStream()));
			) {

			socketTwo.getOutputStream().write("incrementAndGet\r\n".getBytes());

			String response = bufferedReaderTwo.readLine();
			response = bufferedReaderTwo.readLine();

			Assert.assertEquals("incremented counter, current count: 1", response);
			
			socketOne.getOutputStream().write("get\r\n".getBytes());

			response = bufferedReaderOne.readLine();
			response = bufferedReaderOne.readLine();

			Assert.assertEquals("current counter: 1", response);
			
			socketTwo.getOutputStream().write("decrementAndGet\r\n".getBytes());

			response = bufferedReaderTwo.readLine();
			response = bufferedReaderTwo.readLine();

			Assert.assertEquals("decremented counter, current count: 0", response);
			
			socketOne.getOutputStream().write("get\r\n".getBytes());

			response = bufferedReaderOne.readLine();
			response = bufferedReaderOne.readLine();

			Assert.assertEquals("current counter: 0", response);
		}
	}

}
