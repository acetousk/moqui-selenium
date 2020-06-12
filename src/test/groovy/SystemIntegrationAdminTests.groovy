import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Shared
import spock.lang.Specification

class SystemIntegrationAdminTests extends Specification{

  @Shared WebDriver driver = new FirefoxDriver()
  @Shared WebDriverWait wait = new WebDriverWait(driver,10,10)

  //set this to true to make a browser not pop up for tests (it will minimize the browser)
  //boolean minimized = true

  def setupSpec(){
    System.println("Start Framework Browser Tests")

    System.println("Importing Gecko Driver (if this fails you may need to change the path in the code)")

    //for different installations the path needs to change
    System.setProperty("webdriver.gecko.driver", "/home/acetousk/dev/java/moqui-selenium-test/geckodriver")

    System.println("Connecting Selenium WebDriver to Moqui through http (this may also have to be changed to wherever Moqui is being ran)")

    driver.get("http://localhost:8080")
    clickElement(By.id("TestLoginLink_button"))

    driver.manage().window().setPosition(new Point(0, 0));
    driver.manage().window().setSize(new Dimension(1080, 720))
  }

  def cleanupSpec(){
    driver.get("http://localhost:8080")
    driver.findElement(By.cssSelector(".glyphicon-off")).click()
    System.println(getAlertTextAndAccept())

    driver.quit()

    System.println("Framework Browser Tests Done!")
  }

  def setup(){
    driver.get("http://localhost:8080")
  }

  def cleanup(){
    clickElement(By.className("navbar-brand"))
  }

  def "system/IntegrationAdmin/SystemMessages test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("System Messages"))
    clickById("SendDialog-button")
    clickById("SendForm_submitButton")
    clickById("ConsumeDialog-button")
    clickById("ConsumeForm_submitButton")
    //clickById("NewIncomingMessageDialog-button")

    String url = driver.getCurrentUrl()

    then:
    url.contains("SystemMessage/Message/SystemMessageList")
  }

  def "system/IntegrationAdmin/MessageRemotes test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Message Remotes"))
    clickById("CreateRemoteDialog-button")
    clickById("CreateMessageRemoteForm_submitButton")
    clickById("SystemMessageRemoteList_systemMessageRemoteId_0_remoteDetail")
    clickById("SystemMessageRemoteForm_submitButton")

    String url = driver.getCurrentUrl()

    then:
    url.contains("system/SystemMessage/Remote/MessageRemoteDetail")
  }

  def "system/IntegrationAdmin/MessageTypes test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Message Types"))
    clickById("CreateTypeDialog-button")
    sendKeys(By.id("CreateMessageTypeForm_description"),"Description")
    clickById("CreateMessageTypeForm_submitButton")

    clickById("SystemMessageTypeList_systemMessageTypeId_4_typeDetail")
    clickById("SystemMessageTypeForm_submitButton")

    String url = driver.getCurrentUrl()

    then:
    url.contains("system/SystemMessage/Type/MessageTypeDetail")
  }

  def "system/IntegrationAdmin/EntityDataSync test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Entity Data Sync"))

    clickById("EntitySyncList_find")
    clickById("CreateEntitySyncDialog-button")
    clickById("CreateEntitySync_submitButton")

    String url = driver.getCurrentUrl()

    then:
    url.contains("system/EntitySync/EntitySyncList")
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
