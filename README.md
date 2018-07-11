# nomenclature

Install maven wrapper

    mvn -N io.takari:maven:wrapper
    
Use provider instead of inject directly for bean with scope prototype

Perfer @Resource than @Qualifier for filtering bean by name

Use atomikos with spring boot for transcation manangement (like: two phase commit)

Refactor lamda exception http://www.baeldung.com/java-lambda-exceptions
    
In order to test ignite cluste mode:
Should launch an instance with active profile: inmemory (in order to activate ignite), and with default port 8080
![Alt text](./image/instance1.png?raw=true "Title")
The same for instance2, need to activate profile: inmemory, and need to override port also. So here, I override parameter server.port = 9090
![Alt text](./image/instance2.png?raw=true "Title")