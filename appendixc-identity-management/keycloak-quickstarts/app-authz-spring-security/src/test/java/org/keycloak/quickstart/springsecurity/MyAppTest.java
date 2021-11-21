/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.keycloak.quickstart.springsecurity;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.quickstart.springsecurity.web.MyApplication;
import org.keycloak.test.FluentTestsHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {MyApplication.class})
public class MyAppTest {

    public static final String KEYCLOAK_URL = "http://localhost:8180/auth";

    private WebClient webClient = new WebClient(BrowserVersion.CHROME);
    private static FluentTestsHelper testsHelper;

    @BeforeClass
    public static void setup() throws IOException {
        testsHelper = new FluentTestsHelper(KEYCLOAK_URL,
                "admin", "admin",
                FluentTestsHelper.DEFAULT_ADMIN_REALM,
                FluentTestsHelper.DEFAULT_ADMIN_CLIENT,
                FluentTestsHelper.DEFAULT_TEST_REALM)
                .init();

        testsHelper.importTestRealm("/quickstart-realm.json");
    }

    @AfterClass
    public static void cleanUp() {
        testsHelper.deleteTestRealm();

        if (testsHelper != null) {
            testsHelper.close();
        }
    }

    @Test
    public void testRedirect() throws IOException {
        HtmlPage page = this.webClient.getPage("http://localhost:8080");
        assertTrue(page.getBody().getTextContent().contains("Username or email"));
    }

    @Test
    public void testLogin() throws IOException {
        HtmlPage page = login("arjan", "arjan");
        assertTrue(page.getTitleText().contains("Home Page"));
        assertTrue(page.getBody().getTextContent().contains("Default Resource"));
        assertTrue(page.getBody().getTextContent().contains("Alice Resource"));
        assertTrue(page.getBody().getTextContent().contains("Protected Resource"));
        logout(page);
        page = login("werner", "werner");
        assertTrue(page.getBody().getTextContent().contains("Default Resource"));
        assertTrue(page.getBody().getTextContent().contains("Protected Resource"));
        assertTrue(page.getBody().getTextContent().contains("Premium Resource"));
    }

    @Test
    public void testLogout() throws IOException {
        HtmlPage page = login("arjan", "arjan");
        page = logout(page);
        assertTrue(page.getBody().getTextContent().contains("Username or email"));
    }

    @Test
    public void testProtectedResource() throws IOException {
        HtmlPage page = login("arjan", "arjan");
        page = page.getElementById("protected-resource").click();
        assertTrue(page.getBody().getTextContent().contains("\"Protected Resource\""));
        logout(page);
        page = login("werner", "werner");
        page = page.getElementById("protected-resource").click();
        assertTrue(page.getBody().getTextContent().contains("\"Protected Resource\""));
    }

    @Test
    public void testPremiumResource() throws IOException {
        HtmlPage page = login("arjan", "arjan");
        page = page.getElementById("premium-resource").click();
        assertTrue(page.getBody().getTextContent().contains("lack permission"));
        logout(page);
        page = login("werner", "werner");
        page = page.getElementById("premium-resource").click();
        assertTrue(page.getBody().getTextContent().contains("\"Premium Resource\""));
    }

    @Test
    public void testAliceResource() throws IOException {
        HtmlPage page = login("arjan", "arjan");
        page = page.getElementById("arjan-resource").click();
        assertTrue(page.getBody().getTextContent().contains("Only Alice"));
        logout(page);
        page = login("werner", "werner");
        assertNull(page.getElementById("arjan-resource"));
    }

    private HtmlPage login(String username, String password) throws IOException {
        HtmlPage page = this.webClient.getPage("http://localhost:8080");
        ((HtmlInput)page.getElementById("username")).setValueAttribute(username);
        ((HtmlInput)page.getElementById("password")).setValueAttribute(password);
        return page.getElementByName("login").click();
    }

    private HtmlPage logout(HtmlPage page) throws IOException {
        return page.getElementById("logout").click();
    }
}
