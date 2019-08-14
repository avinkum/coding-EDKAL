import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.System.setProperty;

public class TestBestSellerHeadphones {

     WebDriver driver = null;


    @BeforeClass
    public  void initializeBrowser()
    {
        setProperty("webdriver.chrome.driver", "./src/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
        WebDriverWait wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void goHomePage()
    {
       String PageTitle = driver.getTitle();
       //verify homepage Title
       //Assert.assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", PageTitle);

    }

    @Test
    public void SearchHeadphones()
    {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
        searchBox.click();
        searchBox.sendKeys("Headphones"+Keys.ENTER);
       //verify the result page
        Assert.assertEquals("Amazon.com: Headphones", driver.getTitle());
    }

    @Test
    public void TestAddBestSellerHeadPhoneToCart()
    {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Actions action = new Actions(driver);
        List<WebElement> bestSellers = driver.findElements(By.xpath("//span[text()='Best Seller']/ancestor::div[@class='sg-row']/following-sibling::div[@class='sg-row']/child::div[1]"));
        for(int i=1;i<=bestSellers.size();i++) {
            action.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Best Seller']/ancestor::div[@class='sg-row']/following-sibling::div[@class='sg-row']/child::div['"+i+"']")))).build().perform();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Best Seller']/ancestor::div[@class='sg-row']/following-sibling::div[@class='sg-row']/child::div['"+i+"']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.uss-o-close-icon.uss-o-close-icon-medium"))).click();
            driver.navigate().back();
            driver.navigate().refresh();
        }

        //verify the cart
        Assert.assertEquals(2, Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"nav-cart-count\"]")).getText()));
    }

    @AfterClass
    public  void disposeBrowser()
    {
       driver.quit();

    }

}
