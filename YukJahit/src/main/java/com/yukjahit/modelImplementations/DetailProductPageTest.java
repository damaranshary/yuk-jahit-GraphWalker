package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;

import static com.yukjahit.modelImplementations.YukJahitTest.driver;

@GraphWalker
public class DetailProductPageTest extends ExecutionContext implements DetailProductPage {

    public static String expectedProductName;

    @Override
    public void e_Back_To_DefaultQuantity() {
        driver.findElement(By.id("product-quantity-input")).clear();
        driver.findElement(By.id("product-quantity-input")).sendKeys("1");
    }

    @Override
    public void v_CartPage_SHARED() {

    }

    @Override
    public void v_Verify_AddProduct_Success() {
       // Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Produk berhasil ditambahkan ke keranjang')]")).isDisplayed());
    }

    @Override
    public void v_Verify_ZeroQuantity() {
        Assert.assertFalse(driver.findElement(By.id("add-to-cart-button")).isEnabled());
    }

    @Override
    public void e_Add_To_Cart() {
        driver.findElement(By.id("add-to-cart-button")).click();

        CartPageTest.expectedProductInCart = driver.findElement(By.id("product-name")).getText();
        CartPageTest.isCartEmpty = false;
    }

    @Override
    public void e_SearchProduct() {
        driver.findElement(By.id("nav-search-input")).clear();
        driver.findElement(By.id("nav-search-input")).sendKeys("Kaos");
        driver.findElement(By.id("nav-search-input")).sendKeys(Keys.ENTER);

        SearchPageTest.expectedSearchResult = "found";
    }

    @Override
    public void v_Verify_ChangeQuantity() {
        Assert.assertTrue(driver.findElement(By.id("add-to-cart-button")).isEnabled());
    }

    @Override
    public void e_To_Cart_Page() {
        driver.findElement(By.id("nav-cart-button")).click();
    }

    @Override
    public void e_ChangeQuantity() {
        driver.findElement(By.id("product-quantity-input")).clear();
        driver.findElement(By.id("product-quantity-input")).sendKeys("5");
    }

    @Override
    public void e_ChangeQuantity_Zero() {
        driver.findElement(By.id("product-quantity-input")).clear();
        driver.findElement(By.id("product-quantity-input-decrement")).click();
    }

    @Override
    public void v_DetailProductPage_SHARED() {
        String productNameOnDetailProduct = driver.findElement(By.id("product-name")).getText();

        Assert.assertEquals(expectedProductName, productNameOnDetailProduct);
//        Assert.assertTrue(driver.findElement(By.xpath("//img")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("product-price")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("product-description")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("product-quantity-input")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("add-to-cart-button")).isDisplayed());
    }

    @Override
    public void v_SearchPage_SHARED() {

    }
}
