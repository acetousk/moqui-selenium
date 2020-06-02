import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class IsItAThing {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("http://localhost:8080/Login");
        driver.findElement(By.id("TestLoginLink_button")).click();
        driver.findElement(By.xpath("//div[@id='content']/div/div/div/div/a[5]/i")).click();
        driver.findElement(By.linkText("Data Import")).click();
        driver.findElement(By.name("dummyFks")).click();
        driver.findElement(By.name("useTryInsert")).click();
        driver.findElement(By.id("ImportData_timeout")).click();
        driver.findElement(By.id("ImportData_timeout")).clear();
        driver.findElement(By.id("ImportData_timeout")).sendKeys("30");
        driver.findElement(By.xpath("//form[@id='ImportData']/fieldset/div/div")).click();
        driver.findElement(By.xpath("//form[@id='ImportData']/fieldset")).click();
        driver.findElement(By.id("ImportData_types")).click();
        driver.findElement(By.id("ImportData_components")).click();
        driver.findElement(By.linkText("Resource Location")).click();
        driver.findElement(By.linkText("XML Text")).click();
        driver.findElement(By.linkText("JSON Text")).click();
        driver.findElement(By.linkText("CSV Text")).click();
        driver.findElement(By.id("ImportData_checkOnly")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Entity')])[2]")).click();
        driver.findElement(By.linkText("Speed Test")).click();
        driver.findElement(By.id("SelectBaseCalls_submitButton")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

/**
 open | http://localhost:8080/Login |
 click | id=TestLoginLink_button |
 click | //div[@id='content']/div/div/div/div/a[5]/i |
 click | link=Data Import |
 click | name=dummyFks |
 click | name=useTryInsert |
 click | id=ImportData_timeout |
 type | id=ImportData_timeout | 30
 click | //form[@id='ImportData']/fieldset/div/div |
 click | //form[@id='ImportData']/fieldset |
 click | id=ImportData_types |
 click | id=ImportData_components |
 click | link=Resource Location |
 click | link=XML Text |
 click | link=JSON Text |
 click | link=CSV Text |
 click | id=ImportData_checkOnly |
 click | xpath=(//button[@type='button'])[4] |
 click | xpath=(//a[contains(text(),'Entity')])[2] |
 click | link=Speed Test |
 click | id=SelectBaseCalls_submitButton |
*/