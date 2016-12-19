Distributed Counter Readme


1. Programming language
	Chose Java as the language as most familiar with it

2. Programming paradigms
	Primarily object oriented, with tiny usage of functional lambda's

3. Optimisations
	I didn't do anything to optimise the code, due to time constraints.
	Ideally would look at distributing the services and use some gossip like protocol or perhaps an in memory grid to replicate to a certain number of nodes.
	
4. Coding style
	Didn't apply a coding style
	
5. Test strategy
	Unit tested and acceptance tested the primary classes
	
Running/Verifying service
Can run the server with "mvn spring-boot:run"
Then can telnet or use some socket based connection to localhost 9999 and issue one of the following commands:
	1. incrementAndGet
	2. get
	3. decrementAndGet