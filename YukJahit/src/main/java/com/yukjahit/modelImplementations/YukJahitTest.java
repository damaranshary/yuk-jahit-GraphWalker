package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.graphwalker.java.annotation.BeforeExecution;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Objects;

@GraphWalker
public class YukJahitTest extends ExecutionContext implements YukJahit  {

    private Integer indexCardInHome = 1;
    public static WebDriver driver;

    @Override
    public void v_Start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\damar\\Documents\\SKRIPSI\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://yukjahit.vercel.app/");
        driver.manage().window().setSize(new Dimension(974, 1080));
    }

    @Override
    public void v_CartPage_SHARED() {

    }

    @Override
    public void e_SearchProduct_IsNotFound() {
        driver.findElement(By.id("nav-search-input")).clear();
        driver.findElement(By.id("nav-search-input")).sendKeys("Kemeja polkadot kotak-kotak");
        driver.findElement(By.id("nav-search-input")).sendKeys(Keys.ENTER);

        SearchPageTest.expectedSearchResult = "notFound";
    }

    @Override
    public void v_HomePage_SHARED() {
//        boolean imageHome = driver.findElement(By.xpath("//img")).isDisplayed();
        boolean headingHome =  driver.findElement(By.xpath("//h2[contains(text(), 'Produk Terbaru')]")).isDisplayed();

//        Assert.assertTrue(imageHome);
        Assert.assertTrue(headingHome);
    }

    @Override
    public void v_DetailProductPage_SHARED() {

    }

    @Override
    public void e_SelectProduct() {
        if (indexCardInHome > 4) {
            indexCardInHome = 1;
        }

        DetailProductPageTest.expectedProductName = driver.findElement(By.id("product-name-".concat(indexCardInHome.toString()))).getText();
        driver.findElement(By.id("product-card-".concat(indexCardInHome.toString()))).click();

        indexCardInHome++;
    }

    @Override
    public void e_SearchProduct_IsFound() {
        driver.findElement(By.id("nav-search-input")).clear();
        driver.findElement(By.id("nav-search-input")).sendKeys("Kaos");
        driver.findElement(By.id("nav-search-input")).sendKeys(Keys.ENTER);

        SearchPageTest.expectedSearchResult = "found";
    }

    @Override
    public void e_Back_To_Home() {
        driver.findElement(By.id("logo-link")).click();
    }

    @Override
    public void v_OrderPage_SHARED() {

    }

    @Override
    public void v_ProfilePage_SHARED() {

    }

    @Override
    public void e_To_ProfilePage() {
        driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).click();
        driver.findElement(By.xpath("//a[@name='nav-profile-button']")).click();
    }

    @Override
    public void e_SearchProduct_IsEmpty() {
        driver.get("https://yukjahit.vercel.app/products");

        SearchPageTest.expectedSearchResult = "empty";
    }

    @Override
    public void e_To_CartPage() {
        driver.findElement(By.id("nav-cart-button")).click();
    }

    @Override
    public void e_Login() {
        driver.findElement(By.id("nav-login-button")).click();
        driver.findElement(By.id("login-email-input")).sendKeys("user@gmail.com");
        driver.findElement(By.id("login-password-input")).sendKeys("user1234");
        driver.findElement(By.id("login-submit-button")).click();
    }

    @Override
    public void e_To_OrderPage() {
        driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).click();
        driver.findElement(By.xpath("//a[@name='nav-order-button']")).click();
    }

    @Override
    public void v_SearchPage_SHARED() {

    }
}
