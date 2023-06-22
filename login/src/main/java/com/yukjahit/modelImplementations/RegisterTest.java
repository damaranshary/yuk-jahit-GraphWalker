package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;

import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.*;
//import org.junit.Assert;

@GraphWalker
public class RegisterTest extends ExecutionContext implements Register {
    private int alreadyRegistered = 0;

    public static void registerMethod(WebDriver driver, String name, String email, String password, String confirmPassword, String phone, String address) {
        driver.findElement(By.id("register-name-input")).sendKeys(name);
        driver.findElement(By.id("register-email-input")).sendKeys(email);
        driver.findElement(By.id("register-password-input")).sendKeys(password);
        driver.findElement(By.id("register-confirm-password-input")).sendKeys(confirmPassword);
        driver.findElement(By.id("register-phone-input")).sendKeys(phone);
        driver.findElement(By.id("register-address-input")).sendKeys(address);
        driver.findElement(By.id("register-submit-button")).click();
    }

    @Override
    public void e_Register_With_RegisteredEmail() {
        registerMethod(LoginTest.driver, "Registered Email", "user@gmail.com", "usertest", "usertest",
                "08123994588", "Address of Registered Email");
    }

    @Override
    public void v_RegisterPage_SHARED() {
        LoginTest.driver.findElement(By.xpath("//h2[contains(text(), 'Daftar')]")).isDisplayed();
        LoginTest.driver.findElement(By.xpath("//label[contains(text(), 'Nama')]")).isDisplayed();
        LoginTest.driver.findElement(By.id("register-name-input")).isDisplayed();

        LoginTest.driver.findElement(By.xpath("//label[contains(text(), 'Alamat Email')]")).isDisplayed();
        LoginTest.driver.findElement(By.id("register-email-input")).isDisplayed();

        LoginTest.driver.findElement(By.xpath("//label[contains(text(), 'Password')]")).isDisplayed();
        LoginTest.driver.findElement(By.id("register-password-input")).isDisplayed();

        LoginTest.driver.findElement(By.xpath("//label[contains(text(), 'Konfirmasi Password')]")).isDisplayed();
        LoginTest.driver.findElement(By.id("register-confirm-password-input")).isDisplayed();

        LoginTest.driver.findElement(By.xpath("//label[contains(text(), 'Nomor HP')]")).isDisplayed();
        LoginTest.driver.findElement(By.id("register-phone-input")).isDisplayed();

        LoginTest.driver.findElement(By.xpath("//label[contains(text(), 'Alamat Lengkap')]")).isDisplayed();
        LoginTest.driver.findElement(By.id("register-address-input")).isDisplayed();

//        Assert.assertTrue(NameLabel);
//        Assert.assertTrue(NameInput);
//        Assert.assertTrue(EmailLabel);
//        Assert.assertTrue(EmailInput);
//        Assert.assertTrue(PasswordLabel);
//        Assert.assertTrue(PasswordInput);
//        Assert.assertTrue(ConfirmPasswordLabel);
//        Assert.assertTrue(ConfirmPasswordInput);
//        Assert.assertTrue(PhoneLabel);
//        Assert.assertTrue(PhoneInput);
//        Assert.assertTrue(AddressLabel);
//        Assert.assertTrue(AddressInput);
    }

    @Override
    public void e_NoAction() {

    }

    @Override
    public void e_Register_With_WrongPasswordType() {
        registerMethod(LoginTest.driver, "Wrong Password", "wrongpassword@gmail.com", "password", "password",
                "08123994588", "Address of Wrong Password");
    }

    @Override
    public void v_Home_LoggedIn_SHARED() {
        //the assertions are already on the LoginTest Class
    }

    @Override
    public void v_RegisterSuccess() {
        if (alreadyRegistered == 1) {
            LoginTest.driver.findElement(By.xpath("//*[contains(text(), 'Registrasi Berhasil')]")).isDisplayed();
        }
    }

    @Override
    public void e_Register_With_EmptyField() {
        LoginTest.driver.findElement(By.id("register-submit-button")).click();
    }

    @Override
    public void v_RegisterFailed() {
        LoginTest.driver.findElement(By.xpath("//*[contains(text(), 'Registrasi Gagal')]")).isDisplayed();
    }

    @Override
    public void e_Register_With_ValidData() {
        if (alreadyRegistered == 0) {
            registerMethod(LoginTest.driver, "YukJahit MBT (14)", "yukjahit14@email.com", "yukjahitmbt", "yukjahitmbt",
                    "08123456789", "Address of YukJahit (Valid Data)");
        }
        alreadyRegistered++;
    }

    @Override
    public void e_Login_With_RegisteredData() {
        if (alreadyRegistered > 1) {
            LoginTest.driver.findElement(By.id("nav-login-button")).click();
        }
        LoginTest.loginMethod(LoginTest.driver,"yukjahit14@email.com", "yukjahitmbt" );
        LoginTest.failedLogin = false;
        LoginTest.activeName = "YukJahit MBT (14)";
        LoginTest.activeEmail = "yukjahit14@email.com";
        LoginTest.activePhone = "08123456789";
        LoginTest.activeAddress = "Address of YukJahit (Valid Data)";
    }
}
