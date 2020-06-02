import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.*

import java.time.Duration

class BasicTest extends Specification{

    @Shared WebDriver driver = new FirefoxDriver()
    @Shared WebDriverWait wait = new WebDriverWait(driver,5,10)

    //set this to true to make a browser not pop up for tests (it will minimize the browser)
    //boolean minimized = true

    def setupSpec(){
        System.println("Start Framework Browser Tests")

        System.println("Importing Gecko Driver (if this fails you may need to change the path in the code)")

        //for different installations the path needs to change
        System.setProperty("webdriver.gecko.driver", "/home/acetousk/dev/java/moqui-selenium-test/geckodriver")

        System.println("Connecting Selenium WebDriver to Moqui through http (this may also have to be changed to wherever Moqui is being ran)")
        driver.get("https://demo.moqui.org")

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1080, 720))
    }

    def cleanupSpec(){
        System.println("Framework Browser Tests Done!")

        driver.findElement(By.cssSelector(".glyphicon-off")).click();
        driver.quit()
    }

    def setup(){
        driver.get("https://demo.moqui.org")
        clickElement(By.id("TestLoginLink_button"))
    }

    def cleanup(){
        clickElement(By.className("navbar-brand"))
    }

    def "tools/Entity/DataImport test"(){
        when:
        clickElement(By.linkText("Tools"))
        clickByXPath("/html/body/div[1]/div[2]/div/div/div/div/div/div[2]/div/div[2]/div/div[1]/a[1]")
        clearAndSendKeys(By.cssSelector("#ImportData_timeout"),"30")
        clickBySelector("#ImportData_dummyFks > input:nth-child(1)")
        clickByXPath("/html/body/div[1]/div[2]/div/div/div/div/div/form/fieldset/div[2]/div/div[2]/div/span/input")
        sendKeys(By.cssSelector("#ImportData_types"),"foo")
        sendKeys(By.xpath("//*[@id=\"ImportData_components\"]"),"goo")
        clickBySelector("#ImportData_accordion_heading2 > h5:nth-child(1) > a:nth-child(1)")
        sendKeys(By.cssSelector("#ImportData_location"),"boo")
        clickBySelector("#ImportData_accordion_heading3 > h5:nth-child(1) > a:nth-child(1)")
        clearAndSendKeys(By.cssSelector("#ImportData_xmlText"),"<facade-xml></facade-xml>")
        clickBySelector("#ImportData_accordion_heading4 > h5:nth-child(1) > a:nth-child(1)")
        sendKeys(By.cssSelector("#ImportData_jsonText"),"JSON stuff")
        clickBySelector("#ImportData_accordion_heading5 > h5:nth-child(1) > a:nth-child(1)")
        sendKeys(By.cssSelector("#ImportData_csvText"),"CSV stuff")
        clickBySelector("#ImportData_checkOnly")
        clickBySelector("#ImportData_onlyCreate")
        String alert1 = getAlertTextAndAccept()
        clickBySelector("#ImportData_importButton")
        String alert2 = getAlertTextAndAccept()

        then:
        "https://demo.moqui.org/vapps/tools/Entity/DataImport" == driver.getCurrentUrl()
        alert1 == "Are you sure you want to load data, only creating missing records?"
        alert2 == "Are you sure you want to load data, creating new and updating existing records? If in doubt, cancel this and double check."
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

    def sendKeys(By by, String keys){
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
        String alert1 = driver.switchTo().alert().getText()
        print(alert1)
        driver.switchTo().alert().accept()
        return alert1
    }
}
