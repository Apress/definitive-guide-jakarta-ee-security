### How to run the code

Build
> mvn clean package

Run the authentication example
```
java -Djava.security.auth.login.config=src/main/resources/jaas.config \
      -classpath target/jaas-1.0.0-SNAPSHOT.jar com.apress.chapter6.jaas.SimpleJaasAuthentication
```

Run the authorization example
```
java -Djava.security.manager -Djava.security.policy=src/main/resources/jaas.policy \
      -Djava.security.auth.login.config=src/main/resources/jaas.config \
      -classpath target/jaas-1.0.0-SNAPSHOT.jar com.apress.chapter6.jaas.JaasAuthorization
```

Run the console menu authorization example
```
java -Djava.security.manager -Djava.security.policy=src/main/resources/jaas.consolemenu.policy \
    -Djava.security.auth.login.config=src/main/resources/jaas.config \
    -classpath target/jaas-1.0.0-SNAPSHOT.jar com.apress.chapter6.jaas.consolemenu.JaasAuthorization
```