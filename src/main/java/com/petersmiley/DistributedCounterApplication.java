package com.petersmiley;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Primary context configuration class for the distributed counter appliation.
 * 
 * @author psmiley
 *
 */
@SpringBootApplication
public class DistributedCounterApplication  { 
	
	@Bean
	public CountDownLatch closeLatch() {
	    return new CountDownLatch(1);
	}

	public static void main(String... args) throws InterruptedException {
		
	    ApplicationContext ctx = SpringApplication.run(DistributedCounterApplication.class, args);  

	    // This is to prevent the application context from shutting down directly after startup
	    final CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
	    Runtime.getRuntime().addShutdownHook(new Thread() {
	        @Override
	        public void run() {
	            closeLatch.countDown();
	        }
	    });
	    closeLatch.await();
	}

}
