package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.yukjahit.modelImplementations.YukJahitTest.driver;
//import static com.yukjahit.modelImplementations.YukJahitTest.expectedCartTitle;

@GraphWalker
public class CartPageTest extends ExecutionContext implements CartPage{

    private String originalWindow;
    private boolean canCheckout = false;
    public static String expectedProductInCart;
    public static boolean isCartEmpty = true;
    public static boolean checkoutIsCancelled = true;

    @Override
    public void e_ConfirmCheckout() {
        if (canCheckout) {
            driver.findElement(By.id("checkout-confirmation-button")).click();
            OrderPageTest.isOrderEmpty = false;
            OrderPageTest.expectedStatusOrder = "Belum dibayar";
        }
    }

    @Override
    public void v_Verify_Checkout_AlertDialog() {
        if (canCheckout) {
            Assert.assertTrue(driver.findElement(By.id("chakra-modal-checkout-alert-dialog")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("checkout-cancel-button")).isEnabled());
            Assert.assertTrue(driver.findElement(By.id("checkout-confirmation-button")).isEnabled());
        }
    }

    @Override
    public void v_CartPage_SHARED() {
//        String expectedCartTitle = "Keranjang | YukJahit";
//        Assert.assertEquals(expectedCartTitle, driver.getTitle());
//        driver.navigate().refresh();

        if (isCartEmpty) {
            Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Keranjangmu kosong')]")).isDisplayed());
            canCheckout = false;
        } else {
            WebElement productInCartIsVisible = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-product-card-1")));
            Assert.assertTrue(productInCartIsVisible.isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("cart-summary")).isDisplayed());
            Assert.assertEquals(expectedProductInCart, driver.findElement(By.linkText(expectedProductInCart)).getText());

            canCheckout = true;
        }
    }

    @Override
    public void v_OrderPage_SHARED() {

    }

    @Override
    public void e_NoAction() {

    }

    @Override
    public void e_CancelCheckout() {
        if (canCheckout) {
            driver.findElement(By.id("checkout-cancel-button")).click();
        }
    }

    @Override
    public void v_Verify_ProductDeleted() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Keranjangmu kosong')]")).isDisplayed());
    }

    @Override
    public void v_Verify_CheckoutContent() {
        if (canCheckout) {
            Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Selesaikan pembayaran sebelum')]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("payment-link")).isDisplayed());
        }
        isCartEmpty = true;
    }

    @Override
    public void e_ClickCheckout() {
        if(canCheckout) {
            driver.findElement(By.id("notes-textarea")).clear();
            driver.findElement(By.id("notes-textarea")).sendKeys("Ukuran XXL");
            driver.findElement(By.id("checkout-button")).click();
        }
    }

    @Override
    public void e_DeleteProduct() {
        if (!isCartEmpty) {
            List<WebElement> productCardInCart = driver.findElements(By.className("cart-product-card"));

            for (int i = productCardInCart.size(); i > 0; i--) {
                WebElement lastResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(By.id("delete-cart-button-".concat(Integer.toString(i)))));
                lastResult.click();
            }
        }
        isCartEmpty = true;
    }

    @Override
    public void e_To_OrderPage() {
        driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).click();
        driver.findElement(By.xpath("//a[@name='nav-order-button']")).click();

        driver.navigate().refresh();

        canCheckout = false;
    }

    @Override
    public void e_DoPayment() {
        if (!checkoutIsCancelled && canCheckout) {
            driver.findElement(By.id("pin")).sendKeys("654321");
            driver.findElement(By.cssSelector(".btn")).click();
            driver.close();
            driver.switchTo().window(originalWindow);
            OrderPageTest.expectedStatusOrder = "Selesai";
        }
    }

    @Override
    public void e_Click_PaymentLink() {
        if (canCheckout) {
            originalWindow = driver.getWindowHandle();
            assert driver.getWindowHandles().size() == 1;

            driver.findElement(By.id("payment-link")).click();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        }

        checkoutIsCancelled = false;
    }

    @Override
    public void e_NoClick_PaymentLink() {
        checkoutIsCancelled = true;
        driver.findElement(By.xpath("//button[@name='nav-account-dropdown']")).click();
        driver.findElement(By.xpath("//a[@name='nav-order-button']")).click();
    }

    @Override
    public void v_Verify_MidtransPage() {
        if (!checkoutIsCancelled && canCheckout) {
            Assert.assertEquals("Midtrans Mock Payment Provider", driver.getTitle());
            Assert.assertTrue(driver.findElement(By.id("pin")).isDisplayed());
        }
    }

    @Override
    public void v_Verify_CheckoutSuccess() {
        if (!checkoutIsCancelled && canCheckout) {
            Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'Transaksi Berhasil')]")).isDisplayed());
            driver.navigate().refresh();
        }
    }
}
