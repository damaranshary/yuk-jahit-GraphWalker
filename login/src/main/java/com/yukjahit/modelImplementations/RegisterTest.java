package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;

import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.*;
import org.junit.Assert;

import static com.yukjahit.modelImplementations.LoginTest.driver;

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
        registerMethod(driver, "Registered Email", "user@gmail.com", "usertest", "usertest",
                "08123994588", "Address of Registered Email");
    }

    @Override
    public void v_RegisterPage_SHARED() {
        driver.findElement(By.xpath("//h2[contains(text(), 'Daftar')]")).isDisplayed();
        boolean NameLabel = driver.findElement(By.xpath("//label[contains(text(), 'Nama')]")).isDisplayed();
        boolean NameInput = driver.findElement(By.id("register-name-input")).isDisplayed();

        boolean EmailLabel = driver.findElement(By.xpath("//label[contains(text(), 'Alamat Email')]")).isDisplayed();
        boolean EmailInput = driver.findElement(By.id("register-email-input")).isDisplayed();

        boolean PasswordLabel = driver.findElement(By.xpath("//label[contains(text(), 'Password')]")).isDisplayed();
        boolean PasswordInput = driver.findElement(By.id("register-password-input")).isDisplayed();

        boolean ConfirmPasswordLabel = driver.findElement(By.xpath("//label[contains(text(), 'Konfirmasi Password')]")).isDisplayed();
        boolean ConfirmPasswordInput = driver.findElement(By.id("register-confirm-password-input")).isDisplayed();

        boolean PhoneLabel = driver.findElement(By.xpath("//label[contains(text(), 'Nomor HP')]")).isDisplayed();
        boolean PhoneInput = driver.findElement(By.id("register-phone-input")).isDisplayed();

        boolean AddressLabel = driver.findElement(By.xpath("//label[contains(text(), 'Alamat Lengkap')]")).isDisplayed();
        boolean AddressInput = driver.findElement(By.id("register-address-input")).isDisplayed();

        Assert.assertTrue(NameLabel);
        Assert.assertTrue(NameInput);
        Assert.assertTrue(EmailLabel);
        Assert.assertTrue(EmailInput);
        Assert.assertTrue(PasswordLabel);
        Assert.assertTrue(PasswordInput);
        Assert.assertTrue(ConfirmPasswordLabel);
        Assert.assertTrue(ConfirmPasswordInput);
        Assert.assertTrue(PhoneLabel);
        Assert.assertTrue(PhoneInput);
        Assert.assertTrue(AddressLabel);
        Assert.assertTrue(AddressInput);
    }

    @Override
    public void e_NoAction() {

    }

    @Override
    public void e_Register_With_WrongPasswordType() {
        registerMethod(driver, "Wrong Password", "wrongpassword@gmail.com", "password", "password",
                "08123994588", "Address of Wrong Password");
    }

    @Override
    public void v_Home_LoggedIn_SHARED() {
        //the assertions are already on the LoginTest Class
    }

    @Override
    public void v_RegisterSuccess() {
        if (alreadyRegistered == 1) {
            driver.findElement(By.xpath("//*[contains(text(), 'Registrasi Berhasil')]")).isDisplayed();
        }
    }

    @Override
    public void e_Register_With_EmptyField() {
        driver.findElement(By.id("register-submit-button")).click();
    }

    @Override
    public void v_RegisterFailed() {
        driver.findElement(By.xpath("//*[contains(text(), 'Registrasi Gagal')]")).isDisplayed();
    }

    @Override
    public void e_Register_With_ValidData() {
        if (alreadyRegistered == 0) {
            registerMethod(driver, "YukJahit MBT (26)", "yukjahitmbt26@email.com", "yukjahit123", "yukjahit123",
                    "08123456789", "Address of YukJahit (Valid Data)");
        }
        alreadyRegistered++;
    }

    @Override
    public void e_Login_With_RegisteredData() {
        if (alreadyRegistered > 1) {
            driver.findElement(By.id("nav-login-button")).click();
        }
        LoginTest.loginMethod(driver,"yukjahitmbt5@email.com", "yukjahit123" );
        LoginTest.failedLogin = false;
        LoginTest.activeName = "YukJahit MBT (5)";
        LoginTest.activeEmail = "yukjahitmbt5@email.com";
        LoginTest.activePhone = "08123456789";
        LoginTest.activeAddress = "Address of YukJahit (Valid Data)";
    }
}
