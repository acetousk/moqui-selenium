import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Shared
import spock.lang.Specification

class SystemUsageTests extends Specification{

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

  def "system/Usage/ArtifactHitSummary test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Artifact Hit Summary"))
    //TODO: add artifacts to make a summary worth it

    String url = driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "system/Usage/ArtifactHitBins test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Artifact Hit Bins"))
    //TODO: add artifacts to make a hit bin worth it

    String url = driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "system/Usage/AuditLog test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Audit Log"))
    //TODO: not much to do here change that

    String url = driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "system/Usage/Visits test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Visits"))

    clickById("VisitList_hdialog_button")

    sendKeys(By.id("VisitList_header_visitId"),"100000")
    clickById("VisitList_header_findButton")

    clickById("VisitList_visitId_0_visitDetail")
    //clickById("VisitArtifactHitList_hdialog_button")

    String url = driver.getCurrentUrl()

    then:
    url.contains("Visit/VisitDetail?visitId=100000")
  }

  def "system/Usage/LogViewer test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Log Viewer"))
    clickById("LogMessageDocuments_user_id_0_userAccountDetail")

    String url = driver.getCurrentUrl()

    then:
    url.contains("Security/UserAccount/UserAccountDetail")
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
