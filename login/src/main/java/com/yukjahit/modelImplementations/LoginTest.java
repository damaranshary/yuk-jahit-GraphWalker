package com.yukjahit.modelImplementations;

import com.yukjahit.Login;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@GraphWalker()
public class LoginTest extends ExecutionContext implements Login {

    private boolean failedLogin = false;

    private WebDriver driver;

    @BeforeExecution
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\damar\\Documents\\SKRIPSI\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(974, 1080));
    }

    @Override
    public void v_Home_LoggedIn() {
        driver.findElement(By.xpath("//*[contains(text(), 'Login Berhasil')]")).isDisplayed();

        String LoginButton = driver.findElement(By.id("nav-cart-button")).getText();
        String RegisterButton = driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).getText();

        Assert.assertEquals("Keranjang", LoginButton);
        Assert.assertEquals("Akun", RegisterButton);

        //String ToastLoginText = Toast.getText();
        // Assert.assertEquals(ToastLoginText, "Login Berhasil");
    }

    @Override
    public void e_Logout() {
        driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).click();
        driver.findElement(By.xpath("//button[@name='nav-logout-button']")).click();
    }

    @Override
    public void e_CheckProfile() {
        driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).click();
        driver.findElement(By.xpath("//a[@name='nav-profile-button']")).click();
    }

    @Override
    public void e_BackToHome() {
        driver.navigate().back();
    }

    @Override
    public void v_LoginPage() {
        boolean EmailLabel = driver.findElement(By.xpath("//label[contains(text(), 'Email')]")).isDisplayed();
        boolean EmailInput = driver.findElement(By.id("login-email-input")).isDisplayed();
        boolean PasswordLabel = driver.findElement(By.xpath("//label[contains(text(), 'Password')]")).isDisplayed();
        boolean PasswordInput = driver.findElement(By.id("login-password-input")).isDisplayed();

        Assert.assertTrue(EmailLabel);
        Assert.assertTrue(EmailInput);
        Assert.assertTrue(PasswordLabel);
        Assert.assertTrue(PasswordInput);

        if (failedLogin) {
            driver.findElement(By.xpath("//*[contains(text(), 'Login Gagal')]")).isDisplayed();
        }
    }

    @Override
    public void e_LoginWithValidCredentials() {
        WebElement EmailInput = driver.findElement(By.id("login-email-input"));
        WebElement PasswordInput = driver.findElement(By.id("login-password-input"));

        EmailInput.clear();
        PasswordInput.clear();

        EmailInput.sendKeys("user@gmail.com");
        PasswordInput.sendKeys("user1234");
        driver.findElement(By.id("login-submit-button")).click();

        failedLogin = false;
    }

    @Override
    public void v_Home_NotLoggedIn() {
       String LoginButton = driver.findElement(By.id("nav-login-button")).getText();
       String RegisterButton = driver.findElement(By.id("nav-register-button")).getText();

       Assert.assertEquals("Masuk", LoginButton);
       Assert.assertEquals("Daftar", RegisterButton);
    }

    @Override
    public void e_Back() {
        driver.navigate().back();
    }

    @Override
    public void e_ToLoginPage() {
        driver.findElement(By.id("nav-login-button")).click();
    }

    @Override
    public void e_LoginWithInvalidCredentials() {
        WebElement EmailInput = driver.findElement(By.id("login-email-input"));
        WebElement PasswordInput = driver.findElement(By.id("login-password-input"));

        EmailInput.sendKeys("user@gmail.com");
        PasswordInput.sendKeys("usersssss");

        driver.findElement(By.id("login-submit-button")).click();

        EmailInput.clear();
        PasswordInput.clear();

        failedLogin = true;
    }

    @Override
    public void v_ProfilePage() {
        driver.findElement(By.xpath("//h2[contains(text(), 'Profil Saya')]")).isDisplayed();
        String Username = driver.findElement(By.id("user-name")).getText();
        String Email = driver.findElement(By.id("user-email")).getText();

        Assert.assertEquals("Usery Tadd", Username);
        Assert.assertEquals("user@gmail.com", Email);
    }
}
