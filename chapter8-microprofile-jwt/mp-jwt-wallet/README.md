# mp-jwt-wallet
Sample project for chapter 8 of the book The Definitive Guide to Jakarta EE Security

[Note:] this version has been updated to run against the 2.7.0.Final release of Thorntail.

# Updated Instructions for the 2.7.0.Final release of Thorntail
1. git clone https://github.com/Apress/definitive-guide-jakarta-ee-security
1. cd chapter8-microprofile-jwt/mp-jwt-wallet
1. Run `mvn test` from the directory to build and run the test example

## Sample
```bash
[mp-jwt-wallet]$ mvn clean test
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------< com.apress.jakartasecbook:mp-jwt-wallet >---------------
[INFO] Building MicroProfile JWT Wallet Example 1.0.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ mp-jwt-wallet ---
[INFO] Deleting ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ mp-jwt-wallet ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/src\main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ mp-jwt-wallet ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ mp-jwt-wallet ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 10 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ mp-jwt-wallet ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/target/test-classes
[WARNING] /C:/Users/Werner/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/src/test/java/org/eclipse/microprofile/test/jwt/MySecureWalletTest.java: ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/src\test\java\org\eclipse\microprofile\test\jwt\MySecureWalletTest.java uses or overrides a deprecated API.
[WARNING] /C:/Users/Werner/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/src/test/java/org/eclipse/microprofile/test/jwt/MySecureWalletTest.java: Recompile with -Xlint:deprecation for details.
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ mp-jwt-wallet ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.eclipse.microprofile.test.jwt.MySecureWalletTest
WebArchive: MySecureEndpoint.war:
/META-INF/
/META-INF/MP-JWT-SIGNER
/WEB-INF/
/WEB-INF/classes/
/WEB-INF/classes/org/
/WEB-INF/classes/org/eclipse/
/WEB-INF/classes/org/eclipse/microprofile/
/WEB-INF/classes/org/eclipse/microprofile/test/
/WEB-INF/classes/org/eclipse/microprofile/test/jwt/
/WEB-INF/classes/org/eclipse/microprofile/test/jwt/MySecureWallet.class
/WEB-INF/classes/org/eclipse/microprofile/test/jwt/MyRestApp.class
/WEB-INF/classes/project-defaults.yml
/WEB-INF/classes/jwt-roles.properties
/WEB-INF/beans.xml
/WEB-INF/web.xml
Failed downloading commons-io/commons-io/2.8-SNAPSHOT/maven-metadata.xml from https://repo1.maven.org/maven2/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.8-SNAPSHOT/maven-metadata.xml in central (https://repo1.maven.org/maven2)
Failed downloading commons-io/commons-io/2.8-SNAPSHOT/maven-metadata.xml from https://maven.repository.redhat.com/ga/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.8-SNAPSHOT/maven-metadata.xml in redhat-ga (https://maven.repository.redhat.com/ga/)
Failed downloading commons-io/commons-io/2.8-SNAPSHOT/maven-metadata.xml from https://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.8-SNAPSHOT/maven-metadata.xml in gradle (https://repo.gradle.org/gradle/libs-releases-local)
Failed downloading commons-io/commons-io/2.9.0-SNAPSHOT/maven-metadata.xml from https://repo1.maven.org/maven2/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.9.0-SNAPSHOT/maven-metadata.xml in central (https://repo1.maven.org/maven2)
Failed downloading commons-io/commons-io/2.9.0-SNAPSHOT/maven-metadata.xml from https://maven.repository.redhat.com/ga/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.9.0-SNAPSHOT/maven-metadata.xml in redhat-ga (https://maven.repository.redhat.com/ga/)
Failed downloading commons-io/commons-io/2.9.0-SNAPSHOT/maven-metadata.xml from https://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.9.0-SNAPSHOT/maven-metadata.xml in gradle (https://repo.gradle.org/gradle/libs-releases-local)
Failed downloading commons-io/commons-io/maven-metadata.xml from https://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io/maven-metadata.xml in gradle (https://repo.gradle.org/gradle/libs-releases-local)
Failed downloading commons-io/commons-io/2.7.1-SNAPSHOT/maven-metadata.xml from https://repo1.maven.org/maven2/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.7.1-SNAPSHOT/maven-metadata.xml in central (https://repo1.maven.org/maven2)
Failed downloading commons-io/commons-io/2.7.1-SNAPSHOT/maven-metadata.xml from https://maven.repository.redhat.com/ga/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.7.1-SNAPSHOT/maven-metadata.xml in redhat-ga (https://maven.repository.redhat.com/ga/)
Failed downloading commons-io/commons-io/2.7.1-SNAPSHOT/maven-metadata.xml from https://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.7.1-SNAPSHOT/maven-metadata.xml in gradle (https://repo.gradle.org/gradle/libs-releases-local)
Scanning for needed Thorntail fractions with mode: when_missing
Detected fractions: jaxrs-jsonp:2.7.0.Final, jsonp:2.7.0.Final, microprofile-jwt:2.7.0.Final
Adding fractions: jaxrs-jsonp:2.7.0.Final, jsonp:2.7.0.Final, microprofile-jwt:2.7.0.Final
Resolving 0 out of 558 artifacts
Sat Jan 09 04:28:33 CET 2021 INFO [org.wildfly.swarm.bootstrap] (main) Dependencies not bundled; resolving from local Maven repo.
2021-01-09 04:28:43,102 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:        CDI Configuration - STABLE          io.thorntail:cdi-config:2.7.0.Final
2021-01-09 04:28:43,123 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:             Transactions - STABLE          io.thorntail:transactions:2.7.0.Final
2021-01-09 04:28:43,123 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:          Bean Validation - STABLE          io.thorntail:bean-validation:2.7.0.Final
2021-01-09 04:28:43,125 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:                  Elytron - STABLE          io.thorntail:elytron:2.7.0.Final
2021-01-09 04:28:43,127 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:                      CDI - STABLE          io.thorntail:cdi:2.7.0.Final
2021-01-09 04:28:43,129 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:                 Undertow - STABLE          io.thorntail:undertow:2.7.0.Final
2021-01-09 04:28:43,130 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:               Management - STABLE          io.thorntail:management:2.7.0.Final
2021-01-09 04:28:43,131 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction: MicroProfile JWT RBAC Auth - STABLE          io.thorntail:microprofile-jwt:2.7.0.Final
2021-01-09 04:28:43,132 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:                  Logging - STABLE          io.thorntail:logging:2.7.0.Final
2021-01-09 04:28:43,132 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:      MicroProfile Config - STABLE          io.thorntail:microprofile-config:2.7.0.Final
2021-01-09 04:28:43,132 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:                   JAX-RS - STABLE          io.thorntail:jaxrs:2.7.0.Final
2021-01-09 04:28:43,133 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:               Arquillian - STABLE          io.thorntail:arquillian:2.7.0.Final
2021-01-09 04:28:43,133 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:       JAX-RS with JSON-P - STABLE          io.thorntail:jaxrs-jsonp:2.7.0.Final
2021-01-09 04:28:43,133 INFO  [org.wildfly.swarm] (main) THORN0013: Installed fraction:                   JSON-P - STABLE          io.thorntail:jsonp:2.7.0.Final
2021-01-09 04:28:46,328 INFO  [org.jboss.msc] (main) JBoss MSC version 1.4.11.Final
2021-01-09 04:28:46,337 INFO  [org.jboss.threads] (main) JBoss Threads version 2.3.3.Final
2021-01-09 04:28:46,628 INFO  [org.jboss.as] (MSC service thread 1-2) WFLYSRV0049: Thorntail 2.7.0.Final (WildFly Core 10.0.3.Final) starting
2021-01-09 04:28:46,688 INFO  [org.wildfly.swarm] (MSC service thread 1-2) THORN0019: Install MSC service for command line args: []
2021-01-09 04:28:47,679 INFO  [org.wildfly.security] (ServerService Thread Pool -- 11) ELY00001: WildFly Elytron version 1.10.4.Final
2021-01-09 04:28:47,950 INFO  [org.jboss.as.server] (Controller Boot Thread) WFLYSRV0039: Creating http management service using socket-binding (management-http)
2021-01-09 04:28:47,959 WARN  [org.jboss.as.server] (Controller Boot Thread) WFLYSRV0035: No security realm or http server authentication defined for http management service; all access will be unrestricted.
2021-01-09 04:28:47,969 INFO  [org.xnio] (MSC service thread 1-4) XNIO version 3.7.3.Final
2021-01-09 04:28:47,983 INFO  [org.xnio.nio] (MSC service thread 1-4) XNIO NIO Implementation Version 3.7.3.Final
2021-01-09 04:28:48,028 WARN  [org.jboss.as.txn] (ServerService Thread Pool -- 15) WFLYTX0013: The node-identifier attribute on the /subsystem=transactions is set to the default value. This is a danger for environments running multiple servers. Please make sure the attribute value is unique.
2021-01-09 04:28:48,056 INFO  [org.jboss.as.naming] (ServerService Thread Pool -- 18) WFLYNAM0001: Activating Naming Subsystem
2021-01-09 04:28:48,078 INFO  [org.wildfly.extension.microprofile.config._private] (ServerService Thread Pool -- 21) EMPCONF0001: Activating Eclipse MicroProfile Config Subsystem
2021-01-09 04:28:48,091 INFO  [org.jboss.as.jaxrs] (ServerService Thread Pool -- 17) WFLYRS0016: RESTEasy version 3.11.2.Final
2021-01-09 04:28:48,101 INFO  [org.wildfly.extension.io] (ServerService Thread Pool -- 19) WFLYIO001: Worker 'default' has auto-configured to 8 core threads with 64 task threads based on your 4 available processors
2021-01-09 04:28:48,109 INFO  [org.jboss.remoting] (MSC service thread 1-1) JBoss Remoting version 5.0.15.Final
2021-01-09 04:28:48,112 INFO  [org.jboss.as.security] (ServerService Thread Pool -- 22) WFLYSEC0002: Activating Security Subsystem
2021-01-09 04:28:48,127 INFO  [org.jboss.as.security] (MSC service thread 1-4) WFLYSEC0001: Current PicketBox version=5.0.3.Final
2021-01-09 04:28:48,152 INFO  [org.jboss.as.naming] (MSC service thread 1-7) WFLYNAM0003: Starting Naming Service
2021-01-09 04:28:48,195 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-5) WFLYUT0003: Undertow 2.1.3.Final starting
2021-01-09 04:28:48,810 INFO  [org.wildfly.swarm.arquillian.daemon.server.Server] (MSC service thread 1-2) Arquillian Daemon server started on 127.0.0.1:12345
2021-01-09 04:28:48,829 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-5) WFLYUT0012: Started server default-server.
2021-01-09 04:28:49,253 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-2) WFLYUT0006: Undertow HTTP listener default listening on 127.0.0.1:8080
2021-01-09 04:28:49,305 WARN  [org.jboss.as.remoting] (MSC service thread 1-1) ****** All authentication is ANONYMOUS for org.jboss.as.remoting.RemotingHttpUpgradeService
2021-01-09 04:28:49,464 INFO  [org.jboss.as.server] (Controller Boot Thread) WFLYSRV0212: Resuming server
2021-01-09 04:28:49,471 INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0060: Http management interface listening on http://127.0.0.1:9990/management
2021-01-09 04:28:49,472 INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0054: Admin console is not enabled
2021-01-09 04:28:49,473 INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0025: Thorntail 2.7.0.Final (WildFly Core 10.0.3.Final) started in 3197ms - Started 139 of 147 services (32 services are lazy, passive or on-demand)
2021-01-09 04:28:50,694 INFO  [org.wildfly.swarm.runtime.deployer] (main) deploying MySecureEndpoint.war
2021-01-09 04:28:50,732 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-4) WFLYSRV0027: Starting deployment of "MySecureEndpoint.war" (runtime-name: "MySecureEndpoint.war")
2021-01-09 04:28:51,973 WARN  [org.jboss.as.dependency.private] (MSC service thread 1-7) WFLYSRV0018: Deployment "deployment.MySecureEndpoint.war" is using a private module ("org.jboss.jts") which may be changed or removed in future versions without notice.
2021-01-09 04:28:51,996 INFO  [org.jboss.weld.deployer] (MSC service thread 1-3) WFLYWELD0003: Processing weld deployment MySecureEndpoint.war
2021-01-09 04:28:52,110 INFO  [org.hibernate.validator.internal.util.Version] (MSC service thread 1-3) HV000001: Hibernate Validator 6.0.18.Final
2021-01-09 04:28:52,443 INFO  [org.jboss.weld.Version] (MSC service thread 1-2) WELD-000900: 3.1.2 (Final)
2021-01-09 04:28:52,518 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-7) WFLYUT0018: Host default-host starting
2021-01-09 04:28:53,001 INFO  [org.jboss.weld.Bootstrap] (Weld Thread Pool -- 4) WELD-000119: Not generating any bean definitions from org.jboss.arquillian.testng.Arquillian$UpdateResultListener because of underlying class loading error: Type Failed to link org.jboss.arquillian.testng.Arquillian$UpdateResultListener (Module "deployment.MySecureEndpoint.war" from Service Module Loader): org.testng.IInvokedMethodListener not found.  If this is unexpected, enable DEBUG logging to see the full error.
2021-01-09 04:28:53,014 INFO  [org.jboss.weld.Bootstrap] (Weld Thread Pool -- 3) WELD-000119: Not generating any bean definitions from org.eclipse.microprofile.test.jwt.MySecureWalletTest because of underlying class loading error: Type org.testng.SkipException from [Module "deployment.MySecureEndpoint.war" from Service Module Loader] not found.  If this is unexpected, enable DEBUG logging to see the full error.
2021-01-09 04:28:53,852 INFO  [org.jboss.resteasy.resteasy_jaxrs.i18n] (ServerService Thread Pool -- 5) RESTEASY002225: Deploying javax.ws.rs.core.Application: class org.eclipse.microprofile.test.jwt.MyRestApp$Proxy$_$$_WeldClientProxy
2021-01-09 04:28:53,907 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 5) WFLYUT0021: Registered web context: '/' for server 'default-server'
2021-01-09 04:28:53,973 INFO  [org.jboss.as.server] (main) WFLYSRV0010: Deployed "MySecureEndpoint.war" (runtime-name : "MySecureEndpoint.war")
2021-01-09 04:28:53,996 INFO  [org.wildfly.swarm] (main) THORN99999: Thorntail is Ready
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
{"usd":100000.0000,"bitcoin":22.0361,"ethereum":304.8780}
{"usd":100000.0000,"bitcoin":22.0361,"ethereum":304.8780}
{"usd":100000.0000,"bitcoin":22.0361,"ethereum":304.8780}
{"usd":101500.0000,"bitcoin":22.3667,"ethereum":309.4512}
{"usd":101000.0000,"bitcoin":22.2565,"ethereum":307.9268}
{"usd":99500.0000,"bitcoin":21.9260,"ethereum":303.3537}
{"usd":98000.0000,"bitcoin":21.5954,"ethereum":298.7805}
{"usd":96500.0000,"bitcoin":21.2649,"ethereum":294.2073}
{"usd":95000.0000,"bitcoin":20.9343,"ethereum":289.6341}
{"usd":93500.0000,"bitcoin":20.6038,"ethereum":285.0610}
{"usd":92000.0000,"bitcoin":20.2732,"ethereum":280.4878}
{"usd":90500.0000,"bitcoin":19.9427,"ethereum":275.9146}
{"usd":89000.0000,"bitcoin":19.6122,"ethereum":271.3415,"warning":"balance is below warning limit: 90000"}
Saw warning
{"usd":87500.0000,"bitcoin":19.2816,"ethereum":266.7683}
{"usd":86000.0000,"bitcoin":18.9511,"ethereum":262.1951}
{"usd":84500.0000,"bitcoin":18.6205,"ethereum":257.6220}
{"usd":83000.0000,"bitcoin":18.2900,"ethereum":253.0488}
{"usd":81500.0000,"bitcoin":17.9595,"ethereum":248.4756}
{"usd":80000.0000,"bitcoin":17.6289,"ethereum":243.9024}
{"usd":78500.0000,"bitcoin":17.2984,"ethereum":239.3293}
{"usd":77000.0000,"bitcoin":16.9678,"ethereum":234.7561}
{"usd":75500.0000,"bitcoin":16.6373,"ethereum":230.1829}
{"usd":74000.0000,"bitcoin":16.3067,"ethereum":225.6098}
{"usd":72500.0000,"bitcoin":15.9762,"ethereum":221.0366}
{"usd":71000.0000,"bitcoin":15.6457,"ethereum":216.4634}
{"usd":69500.0000,"bitcoin":15.3151,"ethereum":211.8902}
{"usd":68000.0000,"bitcoin":14.9846,"ethereum":207.3171}
{"usd":66500.0000,"bitcoin":14.6540,"ethereum":202.7439}
{"usd":65000.0000,"bitcoin":14.3235,"ethereum":198.1707}
{"usd":63500.0000,"bitcoin":13.9929,"ethereum":193.5976}
{"usd":62000.0000,"bitcoin":13.6624,"ethereum":189.0244}
{"usd":60500.0000,"bitcoin":13.3319,"ethereum":184.4512}
{"usd":59000.0000,"bitcoin":13.0013,"ethereum":179.8780}
{"usd":57500.0000,"bitcoin":12.6708,"ethereum":175.3049}
{"usd":56000.0000,"bitcoin":12.3402,"ethereum":170.7317}
{"usd":54500.0000,"bitcoin":12.0097,"ethereum":166.1585}
{"usd":53000.0000,"bitcoin":11.6792,"ethereum":161.5854}
{"usd":51500.0000,"bitcoin":11.3486,"ethereum":157.0122}
{"usd":50000.0000,"bitcoin":11.0181,"ethereum":152.4390}
{"usd":48500.0000,"bitcoin":10.6875,"ethereum":147.8659,"warning":"balance is below warning limit: 50000"}
Saw warning
{"usd":47000.0000,"bitcoin":10.3570,"ethereum":143.2927,"warning":"balance is below warning limit: 90000"}
{"usd":47000.0000,"bitcoin":10.3570,"ethereum":143.2927}
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 40.936 s - in org.eclipse.microprofile.test.jwt.MySecureWalletTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  48.940 s
[INFO] Finished at: 2021-01-09T04:28:57+01:00
[INFO] ------------------------------------------------------------------------
```
