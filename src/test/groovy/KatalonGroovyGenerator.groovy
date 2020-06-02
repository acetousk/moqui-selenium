import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.*

import spock.lang.*

import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.function.Function

class KatalonGroovyGenerator extends Specification{
  @Shared WebDriver driver = new FirefoxDriver()
  @Shared Actions actions = new Actions(driver)
  @Shared WebDriverWait wait = new WebDriverWait(driver, 10,10)

  @Shared Select select

  //runs only before the first test
  def setupSpec(){
    System.println("Start Framework Browser Tests")
    System.println("\tImporting Gecko Driver (if this fails you may need to change the path in the code)")

    //for different installations the path needs to change
    System.setProperty("webdriver.gecko.driver", "/home/acetousk/dev/java/moqui-selenium-test/geckodriver")
  }
  //runs only after the last test
  def cleanupSpec(){
    System.println("Browser Tests Done!")
    driver.quit()
  }

  //run before each test
  def setup(){}

  //run after each test
  def cleanup(){}

  def "first test"(){
    when:
    open("https://demo.moqui.org")

    actions.click(
        wait.until(new Function<WebDriver,WebElement>(){
          public WebElement apply(WebDriver driver){
            return driver.findElement(By.xpath("//*[@id=\"TestLoginLink_button\"]"))
          }
        })
    )



    System.print("finished")

    then:
    true == true
  }
  //waitUntil
  //def waitUntil(By by){
    //return wait.until(new Function<WebDriver,WebElement>(){
    //  public WebElement apply(WebDriver driver){
    //    return driver.findElement(by)
    //  }
    //})
  //}

  //def waitUntil(ExpectedConditions conditions){
    //WebDriverWait driverWait = new WebDriverWait(driver,defaultWaitTimeInSeconds)
    //wait.until(conditions)
  //}

  //action and wait
  //click
//  def click(By element){
    //waitUntil(ExpectedConditions.elementToBeClickable(element)).click()
    //}

  //sendKeys
//  def sendKeys(By element, String keys){
    //waitUntil(element).sendKeys(keys)
    //}

  //wait for
  //alert
//  def waitForAlertPresent(By element){
    //waitUntil(ExpectedConditions.alertIsPresent())
    //}
//  def waitForAlertNotPresent(By element){
//    waitUntil(!ExpectedConditions.alertIsPresent())
    //}

  //element
//  def waitForElementPresent(By element){
//    waitUntil(ExpectedConditions.presenceOfElementLocated(element))
    //}
//  def waitForElementNotPresent(By element){
//    waitUntil(ExpectedConditions.stalenessOf(element))
    //}

  //text
//  def waitForTextPresent(By element, String text){
//    waitUntil(ExpectedConditions.textToBePresentInElementLocated(element,text))
    //}
//  def waitForTextNotPresent(By element, String text){
//    waitUntil(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(element,text)))
    //}

  //value
//  def waitForValue(By element, String text){
//    waitUntil(ExpectedConditions.textToBePresentInElementValue(element,text))
    //}
//  def waitForNotValue(By element, String text){
//    waitUntil(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(element,text)))
    //}

  //visible
//  def waitForVisible(By element){
//    waitUntil(ExpectedConditions.visibilityOf(element))
    //}
//  def waitForNotVisible(By element){
//    waitUntil(ExpectedConditions.invisibilityOf(element))
    //}

  //random (self described... kinda)
  def open(String target){
    try{
      driver.get(target)
    }catch(Exception e){
      e.printStackTrace()
    }
  }

//  def pause(long time){
    //try{
//      new WebDriverWait(driver, Duration.ofSeconds(time).getSeconds())
    //}catch(Exception e){
//      e.printStackTrace()
    //}
    //}

//  def refresh(){
//    driver.navigate().refresh()
    //}

//  def selectWindow(){
    //int length = driver.getWindowHandles().size()
//    driver.switchTo(driver.getWindowHandles()[length-1])
    //}

//  def selectFrame(By element){
//    waitUntil(ExpectedConditions.visibilityOfElementLocated(element))
//    driver.switchTo().frame(element) //wrong syntax?
    //}

//  def goBack(){
//    driver.navigate().back()
    //}

//  def assertConfirmation(){
//    driver.switchTo().alert().accept()
    //}
}

/**
 open | https://www.moqui.org/ |
 click | xpath=(//a[contains(text(),'Demo')])[2] |
 doubleClickAndWait | xpath=(//a[contains(text(),'HiveMind PM')])[2] |
 doubleClick | //img[@alt='Home'] |
 click | xpath=(//a[contains(text(),'Tools')])[2] |
 click | link=Applications |
 click | link=HH Warehouse |
 click | xpath=(//a[contains(text(),'HH Warehouse')])[2] |
 click | link=Applications |
 click | link=POPC ERP |
 click | xpath=(//a[contains(text(),'Wiki/Content')])[2] |
 click | id=NewSpaceContainer-button |
 click | id=NewSpaceContainer |
 click | id=WikiSpaceList_blogPostsLink_0___/WikiBlogs?wikiSpaceId=${wikiSpaceId} |
 click | id=AddBlogTextDialog-button |
 click | id=AddBlogText_title |
 type | id=AddBlogText_title | Foo
 click | id=AddBlogText_author |
 type | id=AddBlogText_author | Goo
 click | id=AddBlogText_summary |
 type | id=AddBlogText_summary | foo goo
 click | id=select2-AddBlogText_wikiSpaceId-container |
 selectFrame | index=0 |
 click | //html |
 click | //html |
 editContent | //body | <p>hello<br></p><p><br></p>
 */