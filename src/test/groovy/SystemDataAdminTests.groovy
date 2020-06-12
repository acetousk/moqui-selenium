import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Shared
import spock.lang.Specification

class SystemDataAdminTests extends Specification{

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

  def "system/DataAdmin/L10n:Messages test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("L10n: Messages"))
    clickById("CreateMessageDialog-button")
    sendKeys(By.id("CreateLocalizedMessage_original"),"Original")
    clickById("CreateLocalizedMessage_submitButton")
    clickById("UpdateLocalizedMessages_update")

    String url = driver.getCurrentUrl()

    then:
    url.contains("system/Localization/Messages")
  }

  def "system/DataAdmin/L10n:EntityFields test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("L10n: Entity Fields"))
    //clickById("CreateEntityFieldDialog-button")
    //sendKeys(By.id("CreateLocalizedEntityField_entityName"),"EntityName")
    //sendKeys(By.id("CreateLocalizedEntityField_fieldName"),"FieldName")
    //clickById("CreateLocalizedEntityField_submitButton")
    //clickById("UpdateLocalizedEntityFields_delete_0_deleteLocalizedEntityField_button")

    String url = driver.getCurrentUrl()

    then:
    url.contains("system/Localization/EntityFields")
  }

  def "system/DataAdmin/ResourceFinder test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Resource Finder"))
    //TODO: add tests to this later

    String url = driver.getCurrentUrl()

    then:
    url.contains("Resource/ElFinder")
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
