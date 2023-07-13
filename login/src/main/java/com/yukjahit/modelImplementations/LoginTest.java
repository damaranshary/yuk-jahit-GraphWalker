package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@GraphWalker()
public class LoginTest extends ExecutionContext implements Login {

    public static boolean failedLogin = false;
    public static String activeName, activeEmail, activePhone, activeAddress;
    public static WebDriver driver;

    public static void loginMethod (WebDriver driver, String email, String password) {
        WebElement emailInput = driver.findElement(By.id("login-email-input"));
        WebElement passwordInput = driver.findElement(By.id("login-password-input"));

        emailInput.clear();
        passwordInput.clear();

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        driver.findElement(By.id("login-submit-button")).click();
    }

    @BeforeExecution
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\damar\\Documents\\SKRIPSI\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://yukjahit.vercel.app/");
        driver.manage().window().setSize(new Dimension(974, 1080));
    }

    @Override
    public void v_Home_LoggedIn_SHARED() {
        String loginButton = driver.findElement(By.id("nav-cart-button")).getText();
        String registerButton = driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).getText();

        Assert.assertEquals("Keranjang", loginButton);
        Assert.assertEquals("Akun", registerButton);
    }

    @Override
    public void v_Notification_LoggedIn() {
        driver.findElement(By.xpath("//*[contains(text(), 'Kamu sudah login')]")).isDisplayed();
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
    public void e_To_HomePage() {
        driver.findElement(By.id("logo-link")).click();
    }

    @Override
    public void e_To_ProtectedPages() {
        driver.get("https://yukjahit.vercel.app/profile");
        driver.findElement(By.xpath("//*[contains(text(), 'Silahkan login terlebih dahulu')]")).isDisplayed();
    }

    @Override
    public void v_LoginPage() {
        boolean emailLabel = driver.findElement(By.xpath("//label[contains(text(), 'Email')]")).isDisplayed();
        boolean emailInput = driver.findElement(By.id("login-email-input")).isDisplayed();
        boolean passwordLabel = driver.findElement(By.xpath("//label[contains(text(), 'Password')]")).isDisplayed();
        boolean passwordInput = driver.findElement(By.id("login-password-input")).isDisplayed();

        Assert.assertTrue(emailLabel);
        Assert.assertTrue(emailInput);
        Assert.assertTrue(passwordLabel);
        Assert.assertTrue(passwordInput);

        if (failedLogin) {
            driver.findElement(By.xpath("//*[contains(text(), 'Login Gagal')]")).isDisplayed();
        }
    }

    @Override
    public void e_To_RegisterPage() {
        driver.findElement(By.id("nav-register-button")).click();
    }

    @Override
    public void e_To_LoginPage_LoggedIn() {
        driver.get("https://yukjahit.vercel.app/login");
        driver.findElement(By.xpath("//*[contains(text(), 'Kamu sudah login')]")).isDisplayed();
    }

    @Override
    public void e_Login_With_ValidCredentials() {
        loginMethod(driver, "user@gmail.com", "user1234");
        failedLogin = false;
        activeName = "Usery Tadd";
        activeEmail = "user@gmail.com";
        activePhone = "0813448988";
        activeAddress = "Kp. Sejahtera Nusa Bangsa Satu Indonesia No. 23 Rt.03/Rw.09, Padalarang Tengah, Bandung Barat, Jawa Barat,";
    }

    @Override
    public void v_Home_NotLoggedIn() {
       String loginButton = driver.findElement(By.id("nav-login-button")).getText();
       String registerButton = driver.findElement(By.id("nav-register-button")).getText();

       Assert.assertEquals("Masuk", loginButton);
       Assert.assertEquals("Daftar", registerButton);
    }

    @Override
    public void e_NoAction() {
        //no action, because well, you don't need to do anything here
    }

    @Override
    public void v_RegisterPage_SHARED() {
        // the assertions are already on the RegisterTest Class
    }

    @Override
    public void e_To_LoginPage() {
        failedLogin = false;

        driver.findElement(By.id("nav-login-button")).click();
    }

    @Override
    public void e_Login_With_InvalidCredentials() {
        loginMethod(driver, "user@gmail.com", "user2345");
        failedLogin = true;
    }

    @Override
    public void v_ProfilePage() {
        driver.findElement(By.xpath("//h2[contains(text(), 'Profil Saya')]")).isDisplayed();
        WebElement nameIsVisible = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElement((By.id("user-name")))));
        Assert.assertTrue(nameIsVisible.isDisplayed());

        String userName = driver.findElement(By.id("user-name")).getText();
        String userEmail = driver.findElement(By.id("user-email")).getText();
        String userPhone = driver.findElement(By.id("user-phone")).getText();
        String userAddress = driver.findElement(By.id("user-address")).getText();

        Assert.assertEquals(activeName, userName);
        Assert.assertEquals(activeEmail, userEmail);
        Assert.assertEquals(activePhone, userPhone);
        Assert.assertEquals(activeAddress, userAddress);
    }
}
