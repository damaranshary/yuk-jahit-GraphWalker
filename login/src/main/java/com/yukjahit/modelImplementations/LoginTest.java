package com.yukjahit.modelImplementations;

import com.yukjahit.Login;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@GraphWalker()
public class LoginTest extends ExecutionContext implements Login {

    private WebDriver driver;

    private WebDriverWait driverWait;

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
        //WebElement Toast = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("toast-login-success-description"))));
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
        //WebElement Email = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("field-:r1:-label"))));
        //String EmailLabel = Email.getText();
        boolean EmailInput = driver.findElement(By.id("login-email-input")).isDisplayed();
        // WebElement Password = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("field-:r3:-label"))));
        //String PasswordLabel = Password.getText();
        boolean PasswordInput = driver.findElement(By.id("login-password-input")).isDisplayed();

        //Assert.assertEquals(EmailLabel, "Email*");
        Assert.assertTrue(EmailInput);
        //Assert.assertEquals(PasswordLabel, "Password*");
        Assert.assertTrue(PasswordInput);
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
    }

    @Override
    public void v_Home_NotLoggedIn() {
       driver.findElement(By.id("nav-login-button")).isDisplayed();
       // Assert.assertEquals(LoginButtonText, "Masuk");
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
        PasswordInput.sendKeys(Keys.ENTER);
        driver.findElement(By.id("login-submit-button")).click();

        EmailInput.clear();
        PasswordInput.clear();
    }

    @Override
    public void v_ProfilePage() {

    }
}
