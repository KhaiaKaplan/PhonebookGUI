package com.phonebook.tests;

import com.phonebook.core.TestBase;
import com.phonebook.models.User;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.phonebook.core.ApplicationManager.softAssert;

public class LoginTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (!app.getUser().isLoginLinkPresent()) {
            app.getUser().clickOnSignOutButton();
            //  System.out.println("***********************Before Method");
        }
    }

    @Parameters({"email","password"})
    @Test(priority = 1)
    public void loginRegisteredUserPositiveTest(String email, String password) {

        app.getUser().clickOnLoginLink();
        app.getUser().fillLoginRegisterForm(new User()
                .setEmail(email)
                .setPassword(password));
        app.getUser().clickOnLoginButton();
        Assert.assertTrue(app.getUser().isSignOutButtonPresent());
    }

    @Test(priority = 2,groups = "demo")
    public void loginRegisteredUserNegativeWithoutEmailTest() {
        app.getUser().clickOnLoginLink();
        app.getUser().fillLoginRegisterForm(new User()
                .setPassword("Manuel1234$"));
        app.getUser().clickOnLoginButton();
        softAssert.assertTrue(app.getUser().isAlertPresent());
        softAssert.assertTrue(app.getUser().isErrorMessagePresent());
        softAssert.assertAll();
    }

//    @BeforeClass
//    public void startBeforeClass() {
//        System.out.println("*************Before Class");
//    }
//
//    @AfterClass
//    public void stopAfterClass() {
//        System.out.println("*************After Class");
//    }
}
