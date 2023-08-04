package com.yukjahit.modelImplementations;

import com.yukjahit.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@GraphWalker
public class AdminTest extends ExecutionContext implements Admin  {

    public static WebDriver driver;
    private boolean isProductEmpty = false;
    private boolean isOrderEmpty = false;
    private Integer totalProducts;
    private Integer totalOrders;

    @Override
    public void v_Start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\damar\\Documents\\SKRIPSI\\Tools\\chromedriver_v114.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Override
    public void e_Login() {
        driver.findElement(By.id("email-login")).click();
        driver.findElement(By.id("email-login")).sendKeys("yukjahit@gmail.com");
        driver.findElement(By.id("password-login")).sendKeys("yukjahit123");
        driver.findElement(By.id("password-login")).sendKeys(Keys.ENTER);
    }

    @Override
    public void e_To_Dashboard() {
        driver.findElement(By.id("dashboard-navigation")).click();
    }

    @Override
    public void v_DashboardPage() {
       Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'SELAMAT DATANG ADMIN')]")).isDisplayed());
    }

    @Override
    public void e_To_ProductPage() {
        driver.findElement(By.id("produk-navigation")).click();
    }

    @Override
    public void v_ProductPage() {
        Assert.assertEquals("Semua Produk | YukJahit admin", driver.getTitle());
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Semua Produk')]")).isDisplayed());

        if (isProductEmpty) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Belum ada produk')]")).isDisplayed());
        } else {
            List<WebElement> productCard = driver.findElements(By.className("product-card"));
            totalProducts = productCard.size();
        }
    }

    private static void updateProductMethod(WebDriver driver, String name, String price, String description, String imageURL) {
        driver.findElement(By.id("nama-produk")).clear();
        driver.findElement(By.id("nama-produk")).sendKeys(name);

        driver.findElement(By.id("harga-produk")).clear();
        driver.findElement(By.id("harga-produk")).sendKeys(price);


        driver.findElement(By.id("kategori-produk")).click();
        driver.findElement(By.id("kategori-produk")).findElement(By.xpath("//option[. = 'Kaos']")).click();

        driver.findElement(By.id("harga-produk")).clear();
        driver.findElement(By.id("deskripsi-produk")).sendKeys(description);

        driver.findElement(By.id("gambar-produk")).sendKeys(imageURL);
    }

    @Override
    public void e_AddProduct() {
        driver.findElement(By.id("ke-tambah-produk-button")).click();

        updateProductMethod(driver,"Kaos Biru Polos (Tambah)",
                "250000", "Kaos Biru Polos (hasil tambah GraphWalekr)",
                "C:\\Users\\damar\\Documents\\SKRIPSI\\YukJahit\\kaos biru polos.jpg");
    }

    @Override
    public void e_UpdateProduct() {
        if (!isProductEmpty) {
            driver.findElement(By.id("ubah-produk-button-".concat(Integer.toString(totalProducts)))).click();

            updateProductMethod(driver, "(ubah)",
                    "350000", "(hasil ubah GraphWalker)",
                    "C:\\Users\\damar\\Documents\\SKRIPSI\\YukJahit\\kaos abu polos.jpeg" );
        }
    }

    @Override
    public void e_DeleteProduct() {
        if (!isProductEmpty) {
            if (totalProducts == 1) {
                driver.findElement(By.id("hapus-produk-button-".concat(Integer.toString(totalProducts)))).click();
                isProductEmpty = true;
            } else {
                driver.findElement(By.id("hapus-produk-button-".concat(Integer.toString(totalProducts)))).click();
            }
        }
    }

    @Override
    public void v_Verify_AddProduct() {
        assertionForProductForm();

        driver.findElement(By.id("tambah-produk-submit-button")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Produk berhasil ditambahkan')]")).isDisplayed());

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'Produk berhasil ditambahkan')]"))));
    }

    @Override
    public void v_Verify_UpdateProduct() {
        assertionForProductForm();

        driver.findElement(By.id("submit-ubah-button")).click();

        //Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Data produk berhasil diubah')]")).isDisplayed());
    }

    private void assertionForProductForm() {
        Assert.assertEquals("Nama*", driver.findElement(By.id("label-nama")).getText());
        Assert.assertEquals("Harga*", driver.findElement(By.id("label-harga")).getText());
        Assert.assertEquals("Kategori*", driver.findElement(By.id("label-kategori")).getText());
        Assert.assertEquals("Deskripsi*", driver.findElement(By.id("label-deskripsi")).getText());
    }

    @Override
    public void v_Verify_DeleteProduct() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Produk berhasil dihapus')]")).isDisplayed());

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'Produk berhasil dihapus')]"))));
    }

    @Override
    public void e_To_OrderPage() {
        driver.findElement(By.id("transaksi-navigation")).click();
    }

    @Override
    public void v_OrderPage() {
        Assert.assertEquals("Semua Transaksi | YukJahit admin", driver.getTitle());
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Semua Transaksi')]")).isDisplayed());

        if (isOrderEmpty) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Belum ada transaksi')]")).isDisplayed());
        } else {
            List<WebElement> orderCards = driver.findElements(By.className("order-card"));
            totalOrders = orderCards.size();
        }
    }

    @Override
    public void e_Click_DetailOrder() {
        driver.findElement(By.id("detail-order-button-".concat(totalOrders.toString()))).click();
    }

    @Override
    public void e_Click_DeleteOrder() {
        if (!isOrderEmpty) {
            List<WebElement> orderCard = driver.findElements(By.className("order-card"));

            int newestOrder = orderCard.size();

            if (newestOrder == 1) {
                driver.findElement(By.id("hapus-transaksi-button-".concat(Integer.toString(newestOrder)))).click();
                isOrderEmpty = true;
            } else {
                driver.findElement(By.id("hapus-transaksi-button-".concat(Integer.toString(newestOrder)))).click();
            }
        }
    }

    @Override
    public void v_Verify_DetailOrder() {
        Assert.assertTrue(driver.findElement(By.id("chakra-modal-order-modal")).isDisplayed());
    }

    @Override
    public void v_Verify_DeleteOrder() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Transaksi berhasil dihapus')]")).isDisplayed());
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'Transaksi berhasil dihapus')]"))));
    }

    @Override
    public void e_Close_DetailOrder() {
        driver.findElement(By.id("order-modal-header-close-button")).click();
    }

    @Override
    public void e_NoAction() {

    }

}
