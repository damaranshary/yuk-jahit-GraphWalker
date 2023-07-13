package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;

import static com.yukjahit.modelImplementations.YukJahitTest.driver;
//import static com.yukjahit.modelImplementations.YukJahitTest.expectedProfileTitle;

@GraphWalker
public class ProfilePageTest extends ExecutionContext implements ProfilePage {

    public static String expectedUserName = "yukjahit";
    public static String expectedUserEmail = "yukjahit@email.com";
    public static String expectedUserPhone = "081234567890";
    public static String expectedUserAddress = "Alamat lengkap yukjahit";
    public static Integer numOfChangeProfile = 1;

    @Override
    public void v_ProfilePage_SHARED() {
        String expectedProfileTitle = "Profil | YukJahit";

        Assert.assertEquals(expectedProfileTitle, driver.getTitle());
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Profil Saya')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("edit-profile-button")).isEnabled());

        Assert.assertEquals(expectedUserName, driver.findElement(By.id("user-name")).getText());
        Assert.assertEquals(expectedUserEmail, driver.findElement(By.id("user-email")).getText());
        Assert.assertEquals(expectedUserPhone, driver.findElement(By.id("user-phone")).getText());
        Assert.assertEquals(expectedUserAddress, driver.findElement(By.id("user-address")).getText());
    }

    @Override
    public void e_EditProfile_With_ValidData() {
//        driver.findElement(By.id("edit-profile-modal-name-input")).clear();
//        driver.findElement(By.id("edit-profile-modal-name-input")).sendKeys("Changed Profile Number ".concat(numOfChangeProfile.toString()));
//        driver.findElement(By.id("edit-profile-modal-button")).click();
//
//        expectedUserName = "Changed Profile Number ".concat(numOfChangeProfile.toString());
//        numOfChangeProfile++;
        driver.findElement(By.id("edit-profile-modal-footer-close-button")).click();
    }

    @Override
    public void e_Close_EditProfile() {
        driver.findElement(By.id("edit-profile-modal-footer-close-button")).click();
    }

    @Override
    public void e_Click_EditProfile() {
        driver.findElement(By.id("edit-profile-button")).click();
    }

    @Override
    public void v_Verify_EditProfile_Modal() {
        Assert.assertTrue(driver.findElement(By.xpath("//header[contains(text(), 'Ubah Data Profil')]")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.id("edit-profile-modal-button")).isEnabled());
    }
}
