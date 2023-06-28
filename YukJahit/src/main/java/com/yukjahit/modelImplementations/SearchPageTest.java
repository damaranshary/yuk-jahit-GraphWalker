package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;

import static com.yukjahit.modelImplementations.YukJahitTest.driver;

@GraphWalker
public class SearchPageTest extends ExecutionContext implements SearchPage {

    public static String expectedSearchResult; //type= "notFound, "found", "empty"

    @Override
    public void v_DetailProductPage_SHARED() {

    }

    @Override
    public void v_Verify_SearchResult() {
        switch(expectedSearchResult){
            case "notFound":
                Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Produk tidak ditemukan')]")).isDisplayed());
                break;
            case "found":
                Assert.assertTrue(driver.findElement(By.id("product-card-1")).isDisplayed());;
                break;
            case "empty":
                Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Semua Produk')]")).isDisplayed());
                Assert.assertTrue(driver.findElement(By.id("product-card-5")).isDisplayed());
                break;
            default:
        }
    }

    @Override
    public void e_NoAction() {
        //no action
    }

    @Override
    public void e_SelectProduct() {
        switch (expectedSearchResult) {
            case "found":
                DetailProductPageTest.expectedProductName = driver.findElement((By.id("product-name-1"))).getText();
                driver.findElement(By.id("product-card-1")).click();
                break;
            case "empty":
                DetailProductPageTest.expectedProductName = driver.findElement((By.id("product-name-4"))).getText();
                driver.findElement(By.id("product-card-4")).click();
                break;
            case "notFound":
                driver.findElement(By.id("nav-search-input")).clear();
                driver.findElement(By.id("nav-search-input")).sendKeys("Kaos Putih Polos");
                driver.findElement(By.id("nav-search-input")).sendKeys(Keys.ENTER);
                DetailProductPageTest.expectedProductName = driver.findElement((By.id("product-name-1"))).getText();
                driver.findElement(By.id("product-card-1")).click();
        }
    }

    @Override
    public void v_SearchPage_SHARED() {
        //no action because we will do assert on the search result instead
    }
}
