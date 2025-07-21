package com.phonebook.core;

import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static ApplicationManager app = new ApplicationManager
            (System.getProperty("browser", Browser.CHROME.browserName()));

    //  @BeforeMethod
    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        System.err.close();
        app.init();
        //   System.out.println("Before Suite");
    }

    @BeforeGroups("demo")
    public void beforeGroups() {
        System.out.println("Before Groups");
    }

    //  @AfterMethod(enabled = true)
    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
        // System.out.println("AfterSuite");
    }

//    @BeforeTest
//    public void startBeforeTest() {
//        System.out.println("***Before Test");
//    }
//
//    @AfterTest
//    public void stopAfterTest() {
//        System.out.println("***After Test");
//    }

    @BeforeMethod
    public void startTest(Method method, Object[] p) {
        logger.info("Start test {} with data: {}",method.getName(), Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void stopTest(ITestResult result) {

        if (result.isSuccess()) {
            logger.info("PASSED: " + result.getMethod().getMethodName());
        } else {
            logger.error("FAILED: " + result.getMethod().getMethodName() + " Screenshot: " +
                    app.getUser().takeScreenShot());
        }
        logger.info("Stop test");
        logger.info("==========================================");
    }

}
