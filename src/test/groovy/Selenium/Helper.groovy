package Selenium

import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

class Helper {

    public WebDriver driver
    public WebDriverWait wait

    Helper() {
        System.println("Importing Gecko Driver (if this fails you may need to change the path in the code)")

        //for different installations the path needs to change
        System.setProperty("webdriver.gecko.driver", "./geckodriver")

        System.println("Connecting Selenium WebDriver to Moqui through http (this may also have to be changed to wherever Moqui is being ran)")

        driver = new FirefoxDriver()
        wait = new WebDriverWait(driver,10,10)
    }

    String getText(By by){
        String text = driver.findElement(by).getText()
        System.println("getting text: \"" + text + "\" by: " + by.toString())
        return text
    }

    String getTextBySelector(String selectorString){
        getText(By.cssSelector(selectorString))
    }

    String getNotificationMessage(String apps, String qapps = "div.q-notification__message", String vapps = "span[data-notify='message']"){
        if(apps == "qapps"){
            return getTextBySelector(qapps)
        }else if(apps == "vapps"){
            return getTextBySelector(vapps)
        }else{
            return "in a getNotificationMessage apps is not vapps or qapps"
        }
    }

    def clickElement(By by){
        System.println("clicking element by: " + by.toString())
        wait.until(ExpectedConditions.elementToBeClickable(by))
        driver.findElement(by).click()
    }

    def clickByText(String text){
//        System.println("clicking by link text: " + text)
        clickElement(By.linkText(text))
    }

    def clickByPath(String text){
//        System.println("clicking by xpath text: " + text)
        clickElement(By.xpath(text))
    }

    def clickBySelector(String selector){
//        System.println("clicking by selector: " + selector)
        clickElement(By.cssSelector(selector))
    }

    def clickById(String id){
//        System.println("clicking by id: " + id)
        clickElement(By.id(id))
    }

    def sendKeys(By by, String keys){
        System.println("sending " + keys + " by: " + by.toString())
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
        driver.findElement(by)
            .sendKeys(keys)
    }

    def sendKeysBySelector(String selector, String keys){
        By by = By.cssSelector(selector)
        sendKeys(by,keys)
    }

    def sendKeys(By by, Keys keys){
        System.println("sending keys by: " + by.toString())
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
        driver.findElement(by)
            .sendKeys(keys)
    }

    def sendKeysBySelector(String selector, Keys keys){
        By by = By.cssSelector(selector)
        sendKeys(by,keys)
    }

    def clearAndSendKeys(By by, String keys){
        System.println("clearing and sending " + keys + " by: " + by.toString())
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
        WebElement element = driver.findElement(by)
        element.clear()
        element.sendKeys(keys)
    }

    def clearAndSendKeysBySelector(String selector, String keys){
        By by = By.cssSelector(selector)
        clearAndSendKeys(by,keys)
    }

    String getAlertTextAndAccept(){
        wait.until(ExpectedConditions.alertIsPresent())
        String alert = driver.switchTo().alert().getText()
        System.println("got alert and accepting: " + alert)
        driver.switchTo().alert().accept()
        return alert
    }

    def pause(long timeInSeconds){
        System.println("pausing for: " + timeInSeconds + " seconds")
        TimeUnit.SECONDS.sleep(timeInSeconds);
    }

    String prependString(String string){
        Random rand = new Random()
        String prepend = rand.nextInt(100)
        return "A" + prepend + string
    }

    String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";
    String appendString(String string){
        Random rand = new Random()
        return string + AlphaNumericString[rand.nextInt(25)] + AlphaNumericString[rand.nextInt(25)] + AlphaNumericString[rand.nextInt(25)]
    }

    def setupSpec(){
        System.println("Start Framework Browser Tests")

        // go to /apps to force login
        driver.get("http://localhost:8080/apps")
        clickById("TestLoginLink_button")

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1200, 1080))
    }

    def cleanupSpec(){
        driver.get("http://localhost:8080/Login/logout")
        driver.quit()
        System.println("Framework Browser Tests Done!")
    }

    def setup(String apps, String linkText){
        driver.get("http://localhost:8080/" + apps)
        clickByText(linkText)
    }

    def cleanup(String apps) {
        driver.get("http://localhost:8080/" + apps)
    }


    /***************************************************************/
    //below not going to be used much if at all
    def select(By by, int downs){
        clickElement(by)
        for(int i in 1..downs){
            sendKeys(by,Keys.ARROW_DOWN)
        }
        sendKeys(by,Keys.ENTER)
    }

    def selectVisibleText(By by, String text){
        Select select = new Select(driver.findElement(by))
        wait.until(ExpectedConditions.visibilityOfElementLocated(by))
        select.selectByVisibleText(text)
    }

}
