package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;

import java.util.List;

import static com.yukjahit.modelImplementations.YukJahitTest.driver;

@GraphWalker
public class OrderPageTest extends ExecutionContext implements OrderPage {

    public static boolean isOrderEmpty = false;
    public static String expectedStatusOrder = "Dibatalkan";
    public static String actualStatusOrder; //Selesai, Dibatalkan, Kedaluwarsa, Belum dibayar
    public static Integer totalOrderCards;

    @Override
    public void v_OrderPage_SHARED() {
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Daftar Transaksi')]")).isDisplayed());

        if (isOrderEmpty) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Belum ada transaksi')]")).isDisplayed());
        } else {
            List<WebElement> orderCards = driver.findElements(By.className("order-card"));
            totalOrderCards = orderCards.size();
            actualStatusOrder = driver.findElement(By.id("order-status-".concat(totalOrderCards.toString()))).getText();
            Assert.assertEquals(expectedStatusOrder, actualStatusOrder);
        }
    }

    @Override
    public void e_NoAction() {
        driver.navigate().refresh();
    }

    @Override
    public void e_Click_CancelOrder() {
        if (actualStatusOrder.equals("Belum dibayar") && !isOrderEmpty) {
            driver.findElement(By.id("cancel-order-button-".concat(totalOrderCards.toString()))).click();
        }
    }

    @Override
    public void e_Confirm_CancelOrder() {
        if (actualStatusOrder.equals("Belum dibayar") && !isOrderEmpty) {
            driver.findElement(By.id("cancel-order-confirmation-button")).click();
            expectedStatusOrder = "Dibatalkan";
        }
    }

    @Override
    public void v_Verify_CancelOrder_AlertDialog() {
        if (actualStatusOrder.equals("Belum dibayar") && !isOrderEmpty) {
            Assert.assertTrue(driver.findElement(By.id("chakra-modal-cancel-order-alert-dialog")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("cancel-order-confirmation-button")).isEnabled());
            Assert.assertTrue(driver.findElement(By.id("cancel-order-close-button")).isEnabled());
        }
    }

    @Override
    public void v_Verify_CancelOrder_Success() {
        if (actualStatusOrder.equals("Belum dibayar") && !isOrderEmpty) {
            Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Status akan diupdate dalam beberapa saat')]")).isDisplayed());
        }
    }

    @Override
    public void v_Verify_DetailOrder() {
        if (!isOrderEmpty) {
            Assert.assertTrue(driver.findElement(By.id("chakra-modal-order-modal")).isDisplayed());
        }
    }

    @Override
    public void e_Cancel_CancelOrder() {
        if (actualStatusOrder.equals("Belum dibayar") && !isOrderEmpty) {
            driver.findElement(By.id("cancel-order-close-button")).click();
        }
    }

    @Override
    public void e_Close_DetailOrder() {
        if (!isOrderEmpty) {
            driver.findElement(By.id("order-modal-header-close-button")).click();
        }
    }

    @Override
    public void e_Click_DetailOrder() {
        if (!isOrderEmpty) {
            driver.findElement(By.id("detail-order-button-".concat(totalOrderCards.toString()))).click();
        }
    }
}
