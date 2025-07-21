package com.phonebook.tests;

import com.phonebook.core.TestBase;
import com.phonebook.models.Contact;
import com.phonebook.models.User;
import com.phonebook.utils.MyDataProviders;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddContactTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        if (!app.getUser().isLoginLinkPresent()) {
            app.getUser().clickOnSignOutButton();
        }
        app.getUser().clickOnLoginLink();
        app.getUser().fillLoginRegisterForm(new User()
                .setEmail("manuel@gm.com")
                .setPassword("Manuel1234$"));
        app.getUser().clickOnLoginButton();
    }

    @Test
    public void addContactPositiveTest() {
        app.getContact().clickOnAddLink();
        app.getContact().fillContactForm(new Contact().setName("Oliver")
                .setLastName("Kan")
                .setPhone("1234567890")
                .setEmail("kan@gm.com")
                .setAddress("Berlin")
                .setDescription("goalkeeper"));
        app.getContact().clickOnSaveButton();
        Assert.assertTrue(app.getContact().isContactCreatedByName("Oliver"));
    }

    @AfterMethod
    public void postCondition() {
        app.getContact().removeContact();
    }


    @Test(dataProvider = "addContact", dataProviderClass = MyDataProviders.class)
    public void addContactPositiveFromDataProviderTest(String name, String lastName, String phone,
                                                       String email, String address, String desc) {
        app.getContact().clickOnAddLink();
        app.getContact().fillContactForm(new Contact().setName(name)
                .setLastName(lastName)
                .setPhone(phone)
                .setEmail(email)
                .setAddress(address)
                .setDescription(desc));
        app.getContact().clickOnSaveButton();
        Assert.assertTrue(app.getContact().isContactCreatedByName(name));
    }

    @Test(dataProvider = "addContactFromCsv", dataProviderClass = MyDataProviders.class)
    public void addContactPositiveFromDataProviderWithCsvTest(Contact contact) {
        app.getContact().clickOnAddLink();
        app.getContact().fillContactForm(contact);
        app.getContact().clickOnSaveButton();
        Assert.assertTrue(app.getContact().isContactCreatedByPhone(contact.getPhone()));
    }

}
