import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Shared
import spock.lang.Specification

class ToolsGeneralTests extends Specification{

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

  def "tools/GeneralTools/AutoScreens test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Auto Screens"))
    clickBySelector("#select2-SelectEntity_aen-container")
    select(By.cssSelector(".select2-selection"),10)
    select(By.cssSelector(".select2-selection"),7)
    clickById("EntityList_aen_82_find")//sales opportunity stage
    clickById("FindValueDialog-button")
    clickBySelector("#FindEntityValue > fieldset:nth-child(2) > div:nth-child(1) > div:nth-child(2) > span:nth-child(1) > span:nth-child(1) > input:nth-child(1)")
    sendKeys(By.id("FindEntityValue_opportunityStageId"),"Stage Id")
    sendKeys(By.id("FindEntityValue_description"),"description")
    sendKeys(By.id("FindEntityValue_defaultProbability_from"),"0")
    sendKeys(By.id("FindEntityValue_defaultProbability_thru"),"5")
    sendKeys(By.id("FindEntityValue_sequenceNum_from"),"1")
    sendKeys(By.id("FindEntityValue_sequenceNum_thru"),"3")
    clickById("FindEntityValue_submitButton")
    String url = driver.getCurrentUrl()

    then:
    url.contains("AutoScreen/AutoFind")
  }


  def "tools/GeneralTools/ArtifactStats test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Artifact Stats"))
    clickById("ServiceInfoList_name_13_serviceReference")//org.moqui
    //clickById("SelectServiceInput_submitButton")//start moqui.org's service
    clickBySelector("tr.form-list-nav-row:nth-child(1) > th:nth-child(1) > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1)")//page 2
    clickById("ServiceList_detail_0_serviceDetail")

    String url = driver.getCurrentUrl()

    then:
    url.contains("Service/ServiceDetail")
  }

  def "tools/GeneralTools/Services test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Services"))
    clickById("ServiceList_run_22_serviceRun")//mantle.account.FinancialAccountServices.reverse#InvoiceTransactions


    String url = driver.getCurrentUrl()

    then:
    url.contains("Service/ServiceRun")
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
