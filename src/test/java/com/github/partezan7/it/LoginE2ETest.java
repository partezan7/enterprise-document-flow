package com.github.partezan7.it;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.vaadin.testbench.BrowserTestBase;

//@RunLocally(Browser.FIREFOX)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class LoginE2ETest extends BrowserTestBase {
public class LoginE2ETest {

    @Autowired
    Environment environment;

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

//    @BeforeEach
//    void openBrowser() {
//        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":"
//                + environment.getProperty("local.server.port") + "/");
//    }

//    @Test // Vaadin Pro (Can be rewritten in Selenium WebDriver)
//    public void loginAsValidUserSucceeds() {
//        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
//        assertTrue(loginView.login("user", "password"));
//    }

    //    @BrowserTest // Vaadin Pro (Can be rewritten in Selenium WebDriver)
//    public void loginAsInvalidUserFails() {
//        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
//        assertFalse(loginView.login("user", "invalid"));
//    }

}
