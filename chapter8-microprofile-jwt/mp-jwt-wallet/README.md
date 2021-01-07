# mp-jwt-wallet
Sample project for chapter 8 of the book The Definitive Guide to Jakarta EE Security

[Note:] this version has been updated to run against the 2018.2.0.Final release of Wildfly-Swarm.

# Updated Instructions for the 2018.2.0.Final release of Wildfly-Swarm
1. git clone https://github.com/Apress/definitive-guide-jakarta-ee-security
1. cd chapter8-microprofile-jwt/mp-jwt-wallet
1. Run mvn test from the directory to build and run the test example

## Sample
```bash
[newsletter-example 517]$ mvn clean test
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO]  Building MicroProfile JWT Wallet Example 1.0.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ jwt-auth-example ---
[INFO] Deleting ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ jwt-auth-example ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ jwt-auth-example ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ jwt-auth-example ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 10 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ jwt-auth-example ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 3 source files to ~/git/definitive-guide-jakarta-ee-security/chapter8-microprofile-jwt/mp-jwt-wallet/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.20:test (default-test) @ jwt-auth-example ---
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
Failed downloading commons-io/commons-io/2.5-RC4-SNAPSHOT/maven-metadata.xml from http://repo1.maven.org/maven2/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.5-RC4-SNAPSHOT/maven-metadata.xml in central (http://repo1.maven.org/maven2)
Failed downloading commons-io/commons-io/2.5-RC4-SNAPSHOT/maven-metadata.xml from http://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.5-RC4-SNAPSHOT/maven-metadata.xml in gradle (http://repo.gradle.org/gradle/libs-releases-local)
Failed downloading commons-io/commons-io/2.7-SNAPSHOT/maven-metadata.xml from http://repo1.maven.org/maven2/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.7-SNAPSHOT/maven-metadata.xml in central (http://repo1.maven.org/maven2)
Failed downloading commons-io/commons-io/2.7-SNAPSHOT/maven-metadata.xml from http://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.7-SNAPSHOT/maven-metadata.xml in gradle (http://repo.gradle.org/gradle/libs-releases-local)
Failed downloading commons-io/commons-io/maven-metadata.xml from http://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io/maven-metadata.xml in gradle (http://repo.gradle.org/gradle/libs-releases-local)
Failed downloading commons-io/commons-io/2.5-RC5-SNAPSHOT/maven-metadata.xml from http://repo1.maven.org/maven2/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.5-RC5-SNAPSHOT/maven-metadata.xml in central (http://repo1.maven.org/maven2)
Failed downloading commons-io/commons-io/2.5-RC5-SNAPSHOT/maven-metadata.xml from http://repo.gradle.org/gradle/libs-releases-local/. Reason: 
org.eclipse.aether.transfer.MetadataNotFoundException: Could not find metadata commons-io:commons-io:2.5-RC5-SNAPSHOT/maven-metadata.xml in gradle (http://repo.gradle.org/gradle/libs-releases-local)
Scanning for needed WildFly Swarm fractions with mode: when_missing
Detected fractions: jaxrs-jsonp:2018.2.0.Final, jsonp:2018.2.0.Final, microprofile-jwt:2018.2.0.Final, undertow:2018.2.0.Final
Adding fractions: jaxrs-jsonp:2018.2.0.Final, jsonp:2018.2.0.Final, microprofile-jwt:2018.2.0.Final, undertow:2018.2.0.Final
Resolving 0 out of 598 artifacts
Sat Feb 10 11:41:46 PST 2018 INFO [org.wildfly.swarm.bootstrap] (main) Dependencies not bundled; resolving from M2REPO.
2018-02-10 11:41:51,405 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:               Arquillian - STABLE          org.wildfly.swarm:arquillian:2018.2.0.Final
2018-02-10 11:41:51,414 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:                  Logging - STABLE          org.wildfly.swarm:logging:2018.2.0.Final
2018-02-10 11:41:51,414 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:          Bean Validation - STABLE          org.wildfly.swarm:bean-validation:2018.2.0.Final
2018-02-10 11:41:51,414 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:        CDI Configuration - STABLE          org.wildfly.swarm:cdi-config:2018.2.0.Final
2018-02-10 11:41:51,414 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:             Transactions - STABLE          org.wildfly.swarm:transactions:2018.2.0.Final
2018-02-10 11:41:51,415 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:                      CDI - STABLE          org.wildfly.swarm:cdi:2018.2.0.Final
2018-02-10 11:41:51,415 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:                  Elytron - STABLE          org.wildfly.swarm:elytron:2018.2.0.Final
2018-02-10 11:41:51,415 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:       JAX-RS with JSON-P - STABLE          org.wildfly.swarm:jaxrs-jsonp:2018.2.0.Final
2018-02-10 11:41:51,416 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:                   JSON-P - STABLE          org.wildfly.swarm:jsonp:2018.2.0.Final
2018-02-10 11:41:51,416 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction: Eclipse MicroProfile Config - STABLE          org.wildfly.swarm:microprofile-config:2018.2.0.Final
2018-02-10 11:41:51,416 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:                   JAX-RS - STABLE          org.wildfly.swarm:jaxrs:2018.2.0.Final
2018-02-10 11:41:51,416 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction: MicroProfile JWT RBAC Auth Fraction - STABLE          org.wildfly.swarm:microprofile-jwt:2018.2.0.Final
2018-02-10 11:41:51,417 INFO  [org.wildfly.swarm] (main) WFSWARM0013: Installed fraction:                 Undertow - STABLE          org.wildfly.swarm:undertow:2018.2.0.Final
2018-02-10 11:41:51,451 WARNING [ServiceLoader] (main) Could not load service class org.wildfly.swarm.cdi.config.deployment.InjectConfigViewExtension
2018-02-10 11:41:51,452 WARNING [ServiceLoader] (main) Could not load service class org.wildfly.swarm.microprofile.jwtauth.deployment.auth.cdi.MPJWTExtension
2018-02-10 11:41:53,533 INFO  [org.jboss.msc] (main) JBoss MSC version 1.2.7.SP1
2018-02-10 11:41:53,649 INFO  [org.jboss.as] (MSC service thread 1-7) WFLYSRV0049: WildFly Swarm 2018.2.0.Final (WildFly Core 3.0.8.Final) starting
2018-02-10 11:41:53,783 INFO  [org.wildfly.swarm] (MSC service thread 1-7) WFSWARM0019: Install MSC service for command line args: []
2018-02-10 11:41:53,927 INFO  [org.wildfly.swarm.arquillian.daemon.server.Server] (MSC service thread 1-1) Arquillian Daemon server started on localhost:12345
2018-02-10 11:41:54,452 INFO  [org.wildfly.security] (ServerService Thread Pool -- 6) ELY00001: WildFly Elytron version 1.1.6.Final
2018-02-10 11:41:54,488 INFO  [org.jboss.as.jaxrs] (ServerService Thread Pool -- 16) WFLYRS0016: RESTEasy version 3.0.24.Final
2018-02-10 11:41:54,494 INFO  [org.wildfly.extension.microprofile.config] (ServerService Thread Pool -- 19) EMPCONF0001: Activating Eclipse MicroProfile Config Subsystem
2018-02-10 11:41:54,495 WARN  [org.jboss.as.txn] (ServerService Thread Pool -- 20) WFLYTX0013: The node-identifier attribute on the /subsystem=transactions is set to the default value. This is a danger for environments running multiple servers. Please make sure the attribute value is unique.
2018-02-10 11:41:54,518 INFO  [org.jboss.as.naming] (ServerService Thread Pool -- 22) WFLYNAM0001: Activating Naming Subsystem
2018-02-10 11:41:54,519 INFO  [org.jboss.as.security] (ServerService Thread Pool -- 21) WFLYSEC0002: Activating Security Subsystem
2018-02-10 11:41:54,524 INFO  [org.jboss.as.security] (MSC service thread 1-5) WFLYSEC0001: Current PicketBox version=5.0.2.Final
2018-02-10 11:41:54,542 INFO  [org.xnio] (ServerService Thread Pool -- 23) XNIO version 3.5.4.Final
2018-02-10 11:41:54,551 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-4) WFLYUT0003: Undertow 1.4.18.Final starting
2018-02-10 11:41:54,566 INFO  [org.xnio.nio] (ServerService Thread Pool -- 23) XNIO NIO Implementation Version 3.5.4.Final
2018-02-10 11:41:54,568 INFO  [org.jboss.as.naming] (MSC service thread 1-8) WFLYNAM0003: Starting Naming Service
2018-02-10 11:41:54,621 INFO  [org.wildfly.extension.io] (ServerService Thread Pool -- 23) WFLYIO001: Worker 'default' has auto-configured to 8 core threads with 64 task threads based on your 4 available processors
2018-02-10 11:41:54,938 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-6) WFLYUT0012: Started server default-server.
2018-02-10 11:41:54,983 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-8) WFLYUT0006: Undertow HTTP listener default listening on 127.0.0.1:8080
2018-02-10 11:41:55,040 INFO  [org.jboss.as.server] (Controller Boot Thread) WFLYSRV0212: Resuming server
2018-02-10 11:41:55,042 INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0025: WildFly Swarm 2018.2.0.Final (WildFly Core 3.0.8.Final) started in 1572ms - Started 126 of 145 services (31 services are lazy, passive or on-demand)
2018-02-10 11:41:55,536 INFO  [org.wildfly.swarm.runtime.deployer] (main) deploying MySecureEndpoint.war
2018-02-10 11:41:55,559 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-6) WFLYSRV0027: Starting deployment of "MySecureEndpoint.war" (runtime-name: "MySecureEndpoint.war")
2018-02-10 11:41:56,335 WARN  [org.jboss.as.dependency.private] (MSC service thread 1-1) WFLYSRV0018: Deployment "deployment.MySecureEndpoint.war" is using a private module ("org.jboss.jts") which may be changed or removed in future versions without notice.
2018-02-10 11:41:56,335 WARN  [org.jboss.as.dependency.private] (MSC service thread 1-1) WFLYSRV0018: Deployment "deployment.MySecureEndpoint.war" is using a private module ("org.jboss.jts") which may be changed or removed in future versions without notice.
2018-02-10 11:41:56,348 INFO  [org.jboss.weld.deployer] (MSC service thread 1-3) WFLYWELD0003: Processing weld deployment MySecureEndpoint.war
2018-02-10 11:41:56,393 INFO  [org.hibernate.validator.internal.util.Version] (MSC service thread 1-3) HV000001: Hibernate Validator 5.3.5.Final
2018-02-10 11:41:56,544 INFO  [org.jboss.weld.Version] (MSC service thread 1-7) WELD-000900: 2.4.3 (Final)
2018-02-10 11:41:56,582 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-6) WFLYUT0018: Host default-host starting
2018-02-10 11:41:56,782 INFO  [org.jboss.weld.Bootstrap] (Weld Thread Pool -- 2) WELD-000119: Not generating any bean definitions from org.eclipse.microprofile.test.jwt.MySecureWalletTest because of underlying class loading error: Type org.testng.SkipException from [Module "deployment.MySecureEndpoint.war" from Service Module Loader] not found.  If this is unexpected, enable DEBUG logging to see the full error.
2018-02-10 11:41:56,783 INFO  [org.jboss.weld.Bootstrap] (Weld Thread Pool -- 2) WELD-000119: Not generating any bean definitions from org.jboss.arquillian.testng.Arquillian$UpdateResultListener because of underlying class loading error: Type Failed to link org.jboss.arquillian.testng.Arquillian$UpdateResultListener (Module "deployment.MySecureEndpoint.war" from Service Module Loader): org.testng.IInvokedMethodListener not found.  If this is unexpected, enable DEBUG logging to see the full error.
2018-02-10 11:41:57,227 INFO  [org.jboss.resteasy.resteasy_jaxrs.i18n] (ServerService Thread Pool -- 3) RESTEASY002225: Deploying javax.ws.rs.core.Application: class org.eclipse.microprofile.test.jwt.MyRestApp$Proxy$_$$_WeldClientProxy
2018-02-10 11:41:57,252 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 3) WFLYUT0021: Registered web context: '/' for server 'default-server'
2018-02-10 11:41:57,275 INFO  [org.jboss.as.server] (main) WFLYSRV0010: Deployed "MySecureEndpoint.war" (runtime-name : "MySecureEndpoint.war")
2018-02-10 11:41:57,284 INFO  [org.wildfly.swarm] (main) WFSWARM99999: WildFly Swarm is Ready
{"usd":100000.0000,"bitcoin":22.0361,"ethereum":304.8780}
{"usd":100000.0000,"bitcoin":22.0361,"ethereum":304.8780}
{"usd":98500.0000,"bitcoin":21.7056,"ethereum":300.3049}
{"usd":98500.0000,"bitcoin":21.7056,"ethereum":300.3049}
{"usd":98500.0000,"bitcoin":21.7056,"ethereum":300.3049}
{"usd":100000.0000,"bitcoin":22.0361,"ethereum":304.8780}
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
2018-02-10 11:42:06,217 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-7) WFLYUT0008: Undertow HTTP listener default suspending
2018-02-10 11:42:06,218 INFO  [stdout] (MSC service thread 1-5) [Server] Requesting shutdown...
2018-02-10 11:42:06,219 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-7) WFLYUT0007: Undertow HTTP listener default stopped, was bound to 127.0.0.1:8080
2018-02-10 11:42:06,216 INFO  [null] (MSC service thread 1-5) Requesting shutdown...
2018-02-10 11:42:06,219 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 3) WFLYUT0022: Unregistered web context: '/' from server 'default-server'
2018-02-10 11:42:06,230 INFO  [stdout] (MSC service thread 1-5) [Server] Server shutdown.
2018-02-10 11:42:06,230 INFO  [null] (MSC service thread 1-5) Server shutdown.
2018-02-10 11:42:06,233 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-7) WFLYUT0019: Host default-host stopping
2018-02-10 11:42:06,234 INFO  [org.wildfly.extension.undertow] (MSC service thread 1-3) WFLYUT0004: Undertow 1.4.18.Final stopping
2018-02-10 11:42:06,332 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-7) WFLYSRV0028: Stopped deployment MySecureEndpoint.war (runtime-name: MySecureEndpoint.war) in 124ms
2018-02-10 11:42:06,348 INFO  [org.jboss.as] (MSC service thread 1-7) WFLYSRV0050: WildFly Swarm 2018.2.0.Final (WildFly Core 3.0.8.Final) stopped in 138ms
2018-02-10 11:42:06,380 INFO  [org.jboss.weld.Bootstrap] (pool-1-thread-1) WELD-ENV-002001: Weld SE container internal shut down

[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 28.804 s - in org.eclipse.microprofile.test.jwt.MySecureWalletTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 32.856 s
[INFO] Finished at: 2018-02-10T11:42:07-08:00
[INFO] Final Memory: 44M/502M
[INFO] ------------------------------------------------------------------------
```
