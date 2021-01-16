package org.eclipse.microprofile.test.jwt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.tck.util.TokenUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * Tests illustrating the MP-JWT functionality
 */
public class MySecureWalletTest extends Arquillian {

    /**
     * The base URL for the container under test
     */
    @ArquillianResource
    private URL baseURL;

    /**
     * Create a CDI aware Jakarta REST application archive with our endpoints and
     * @return the Jakarta REST application archive
     * @throws IOException - on resource failure
     */
    @Deployment(testable=true)
    public static WebArchive createDeployment() throws IOException {
        // Various system properties you can set to enable debug logging, debugging
        //System.setProperty("swarm.resolver.offline", "true");
        //System.setProperty("swarm.logging", "DEBUG");
        //System.setProperty("swarm.debug.port", "8888");
    	// TODO check if those are still relevant in Thorntail or beyond?
    	
        // Get the public key of the token signer
        URL publicKey = MySecureWalletTest.class.getResource("/publicKey.pem");
        WebArchive webArchive = ShrinkWrap
                .create(WebArchive.class, "MySecureEndpoint.war")
                // Place the public key in the war as /MP-JWT-SIGNER - Wildfly/Thorntail specific
                .addAsManifestResource(publicKey, "/MP-JWT-SIGNER")
                .addClass(MySecureWallet.class)
                .addClass(MyRestApp.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Add Thorntail specific configuration of the security domain
                .addAsResource("project-defaults.yml", "/project-defaults.yml")
                .addAsWebInfResource("jwt-roles.properties", "classes/jwt-roles.properties")
                .setWebXML("WEB-INF/web.xml")
                ;
        System.out.printf("WebArchive: %s\n", webArchive.toString(true));
        return webArchive;
    }

    @RunAsClient
    @Test(description = "Verify that jdoe can view balance using Token1.json")
    public void checkBalance() throws Exception {
        Reporter.log("Begin checkBalance");
        final String token = TokenUtils.signClaims("/Token1.json");

        String uri = baseURL.toExternalForm() + "/wallet/balance";
        WebTarget target = ClientBuilder.newClient()
                .target(uri);
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject reply = response.readEntity(JsonObject.class);
        Reporter.log(reply.toString());
        System.out.println(reply.toString());
    }
    @RunAsClient
    @Test(description = "Verify that jdoe can debit balance using Token1.json")
    public void debitBalance() throws Exception {
        Reporter.log("Begin debitBalance");
        final String token = TokenUtils.signClaims("/Token1.json");

        String uri = baseURL.toExternalForm() + "/wallet/debit";
        WebTarget target = ClientBuilder.newClient()
                .target(uri)
                .queryParam("amount", "500");
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject reply = response.readEntity(JsonObject.class);
        Reporter.log(reply.toString());
        System.out.println(reply.toString());
    }

    @RunAsClient
    @Test(description = "Verify that jdoe can issue debit > $1000 using Token1.json")
    public void whaleDebitBalance() throws Exception {
        Reporter.log("Begin WhaleDebitBalance");
        final String token = TokenUtils.signClaims("/Token1.json");

        String uri = baseURL.toExternalForm() + "/wallet/debit";
        WebTarget target = ClientBuilder.newClient()
                .target(uri)
                .queryParam("amount", "1500");
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject reply = response.readEntity(JsonObject.class);
        Reporter.log(reply.toString());
        System.out.println(reply.toString());
    }

    @RunAsClient
    @Test(description = "Verify that jdoe can credit balance using Token1.json")
    public void creditBalance() throws Exception {
        Reporter.log("Begin creditBalance");
        String token = TokenUtils.generateTokenString("/Token1.json");

        String uri = baseURL.toExternalForm() + "/wallet/credit";
        WebTarget target = ClientBuilder.newClient()
                .target(uri)
                .queryParam("amount", "1500");
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject reply = response.readEntity(JsonObject.class);
        Reporter.log(reply.toString());
        System.out.println(reply.toString());
    }

    @RunAsClient
    @Test(description = "Verify that jdoe can debit balance and see a warning about Token1.json warningLimit")
    public void debitTillWarningLimit() throws Exception {
        Reporter.log("Begin debitTillWarningLimit");
        String token = TokenUtils.generateTokenString("/Token1.json");

        String uri = baseURL.toExternalForm() + "/wallet/debit";
        WebTarget target = ClientBuilder.newClient()
                .target(uri)
                .queryParam("amount", "1500");
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject reply = response.readEntity(JsonObject.class);
        Reporter.log(reply.toString());
        System.out.println(reply.toString());
        while(!reply.containsKey("warning")) {
            response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
            reply = response.readEntity(JsonObject.class);
            System.out.println(reply.toString());
        }
        System.out.printf("Saw warning\n");

        // Update the token with a new lower warningLimit claim, and continue debiting until this level is breached
        token = TokenUtils.generateTokenString("/Token1-50000-limit.json");
        response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        reply = response.readEntity(JsonObject.class);
        System.out.println(reply.toString());
        // There should no longer be a warning as the new warningLimit claim should have been seen
        Assert.assertTrue(!reply.containsKey("warning"), "warning should be cleared");
        // Now continue debiting
        while(!reply.containsKey("warning")) {
            response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
            reply = response.readEntity(JsonObject.class);
            System.out.println(reply.toString());
        }
        System.out.printf("Saw warning\n");
    }

    @RunAsClient
    @Test(description = "Verify that jdoe cannot debit amount about the $2500 spendingLimit of Token1.json")
    public void bigDebitBalanceFail() throws Exception {
        Reporter.log("Begin bigDebitBalanceFail");
        final String token = TokenUtils.signClaims("/Token1.json");

        // First get the current balance
        String uri = baseURL.toExternalForm() + "/wallet/balance";
        WebTarget target = ClientBuilder.newClient()
                .target(uri);
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        JsonObject origBalance = response.readEntity(JsonObject.class);
        Assert.assertTrue(origBalance.containsKey("usd"));
        System.out.println(origBalance.toString());

        // Now try a big debit that is above the $2500 spendingLimit claim
        uri = baseURL.toExternalForm() + "/wallet/debit";
        target = ClientBuilder.newClient()
                .target(uri)
                .queryParam("amount", "3000");
        response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_BAD_REQUEST);

        // Now retrieve the balance again to make sure it has not changed
        uri = baseURL.toExternalForm() + "/wallet/balance";
        target = ClientBuilder.newClient()
                .target(uri);
        response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject newBalance = response.readEntity(JsonObject.class);
        Reporter.log(newBalance.toString());
        System.out.println(newBalance.toString());
        Assert.assertEquals(origBalance.getJsonNumber("usd"), newBalance.getJsonNumber("usd"));
    }

    @RunAsClient
    @Test(description = "Verify that jdoe2 cannot debit > 1000 using Token2.json")
    public void whaleDebitBalanceFail() throws Exception {
        Reporter.log("Begin WhaleDebitBalanceFail");
        final String token = TokenUtils.signClaims("/Token2.json");

        String uri = baseURL.toExternalForm() + "/wallet/debit";
        WebTarget target = ClientBuilder.newClient()
                .target(uri)
                .queryParam("amount", "1500");
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_FORBIDDEN);

        uri = baseURL.toExternalForm() + "/wallet/balance";
        target = ClientBuilder.newClient()
                .target(uri);
        response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_OK);
        JsonObject reply = response.readEntity(JsonObject.class);
        Reporter.log(reply.toString());
        System.out.println(reply.toString());
    }

    @RunAsClient
    @Test(description = "Verify that jdoe3 has no access via Token-noaccess.json")
    public void checkBalanceNoAccess() throws Exception {
        Reporter.log("Begin checkBalanceNoAccess");
        String token = TokenUtils.generateTokenString("/Token-noaccess.json");

        String uri = baseURL.toExternalForm() + "/wallet/balance";
        WebTarget target = ClientBuilder.newClient()
                .target(uri);
        Response response = target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_FORBIDDEN);
    }

    @RunAsClient
    @Test(description = "Verify that attempting to access the balance without a token fails")
    public void checkBalanceNoAuth() throws Exception {
        Reporter.log("Begin checkBalanceNoAuth");

        String uri = baseURL.toExternalForm() + "/wallet/balance";
        WebTarget target = ClientBuilder.newClient()
                .target(uri);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(response.getStatus(), HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
