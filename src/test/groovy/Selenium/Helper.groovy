package Selenium

import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class Helper {

    private static Helper instance = null

    private WebDriver driver
    private WebDriverWait wait

    private Helper(){
        System.println("Importing Gecko Driver (if this fails you may need to change the path in the code)")

        //for different installations the path needs to change
        System.setProperty("webdriver.gecko.driver", "/home/acetousk/dev/java/moqui-selenium-test/geckodriver")

        System.println("Connecting Selenium WebDriver to Moqui through http (this may also have to be changed to wherever Moqui is being ran)")

        driver = new FirefoxDriver()
        wait = new WebDriverWait(driver,10,10)
    }

    public static get(){
        if(instance == null){
            instance = new Helper()
        }
        return instance
    }

    def clickElement(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by))
        driver.findElement(by).click()
    }

    def clickBySelector(String selector){
        clickElement(By.cssSelector(selector))
    }

    def clickByXPath(String xpath){
        clickElement(By.xpath(xpath))
    }

    def clickById(String id){
        clickElement(By.id(id))
    }

    def sendKeys(By by, String keys){
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
        driver.findElement(by)
                .sendKeys(keys)
    }

    def sendKeys(By by, Keys keys){
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
        driver.findElement(by)
                .sendKeys(keys)
    }

    def clearAndSendKeys(By by, String keys){
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
        WebElement element = driver.findElement(by)
        element.clear()
        element.sendKeys(keys)
    }

    String getAlertTextAndAccept(){
        wait.until(ExpectedConditions.alertIsPresent())
        String alert = driver.switchTo().alert().getText()
        System.println(alert)
        driver.switchTo().alert().accept()
        return alert
    }

    def select(By by, int downs){
        clickElement(by)
        for(int i in 1..downs){
            sendKeys(by,Keys.ARROW_DOWN)
        }
        sendKeys(by,Keys.ENTER)
    }
}
