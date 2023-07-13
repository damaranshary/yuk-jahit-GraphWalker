package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.yukjahit.modelImplementations.YukJahitTest.*;

@GraphWalker
public class SearchPageTest extends ExecutionContext implements SearchPage {

    public static String expectedSearchResult; //type= "notFound, "found", "empty"

    @Override
    public void v_DetailProductPage_SHARED() {

    }

    @Override
    public void v_Verify_SearchResult() {
        String expectedSearchEmptyTitle = "Semua Produk | YukJahit";
        String expectedSearchTitle = "Pencarian produk | YukJahit";
        switch(expectedSearchResult){
            case "notFound":
                Assert.assertEquals(expectedSearchTitle, driver.getTitle());
                Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Produk tidak ditemukan')]")).isDisplayed());
                break;
            case "found":
                Assert.assertEquals(expectedSearchTitle, driver.getTitle());
                Assert.assertTrue(driver.findElement(By.id("product-card-1")).isDisplayed());;
                break;
            case "empty":
                Assert.assertEquals(expectedSearchEmptyTitle, driver.getTitle());
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
                boolean pageChanged = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.titleIs("Semua Produk | YukJahit"));
                if (pageChanged) {
                    DetailProductPageTest.expectedProductName = driver.findElement((By.id("product-name-4"))).getText();
                    driver.findElement(By.id("product-card-4")).click();
                }
                break;
            case "notFound": // we will still select a product even if the product not found, so the model won't stop
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
