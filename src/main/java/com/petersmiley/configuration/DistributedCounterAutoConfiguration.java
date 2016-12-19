package com.petersmiley.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.MessageChannel;

import com.petersmiley.counter.CounterMessageProcesssor;

/**
 * Spring auto-configuration class to setup inbound tcp channel with Spring Integration.
 * 
 * @author psmiley
 *
 */
@Configuration
public class DistributedCounterAutoConfiguration {
	
	private int port;
	
	private long requestTimeout;
	
	private CounterMessageProcesssor counterMessageProcesssor;
	
	@Autowired
	public DistributedCounterAutoConfiguration(
			@Value("${tcp.port:9999}") int port, 
			@Value("${request.timeout:10000}") long requestTimeout,
			CounterMessageProcesssor counterMessageProcesssor
		) {
		
		this.port = port;
		this.requestTimeout = requestTimeout;
		this.counterMessageProcesssor = counterMessageProcesssor;
	}

	@Bean
	public TcpNetServerConnectionFactory connectionFactory() {
		return new TcpNetServerConnectionFactory(port);
	}

	@Bean
	public MessageChannel requestChannel() {
		return new DirectChannel();
	}

	@Bean
	public ObjectToStringTransformer objectToStringTransformer() {
		return new ObjectToStringTransformer();
	}

	@Bean
	public TcpInboundGateway tcpInboundGateway() {

		TcpInboundGateway gateway = new TcpInboundGateway();
		gateway.setConnectionFactory(connectionFactory());
		gateway.setRequestTimeout(requestTimeout);
		gateway.setRequestChannel(requestChannel());

		return gateway;
	}

	@Bean
	public IntegrationFlow flow() {
		
		return IntegrationFlows
				// Bind this flow to the request channel
				.from(requestChannel())
				
				// transform the byte array input to a string
				.transform(objectToStringTransformer())
				
				// process the incoming message
				.handle((payload, headers) -> {
					
					return counterMessageProcesssor.processCounterMessage((String) payload);
				})
				
				.get();
	}
	
}
