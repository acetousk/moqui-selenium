import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.Select
import spock.lang.*

import java.time.Duration


class ToolsEntityTests extends Specification{

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

  def "tools/Entity/DataImport test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Data Import"))
    clearAndSendKeys(By.cssSelector("#ImportData_timeout"),"30")
    clickBySelector("#ImportData_dummyFks > input:nth-child(1)")
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
    String url = driver.getCurrentUrl()

    then:
    url.contains("tools/Entity/DataImport")
    alert1 == "Are you sure you want to load data, only creating missing records?"
    alert2 == "Are you sure you want to load data, creating new and updating existing records? If in doubt, cancel this and double check."
  }

  def "tools/Entity/DataExport test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Data Export"))
    clearAndSendKeys(By.cssSelector("#ExportData_dependentLevels"),"5")
    sendKeys(By.cssSelector("#ExportData_masterName"),"MasterName")
    clearAndSendKeys(By.cssSelector("#ExportData_fromDate > input:nth-child(1)"),"2020-00-02 13:45")
    clearAndSendKeys(By.cssSelector("#ExportData_thruDate_idate"),"2040-06-02 13:20")
    clickBySelector("#ExportData_fileType_1 > input:nth-child(1)")
    clickBySelector("#ExportData_output > input:nth-child(1)")
    sendKeys(By.cssSelector("#ExportData_path"),"/")
    clickById("ExportData_submitButton")
    String text1 = driver.findElement(By.cssSelector(".text-inline")).getText()
    System.println(text1)
    select(By.cssSelector(".select2-search__field"),2)
    select(By.cssSelector(".select2-search__field"),50)
    select(By.cssSelector(".select2-search__field"),5)
    select(By.cssSelector(".select2-search__field"),10)
    clickById("ExportData_submitButton")
    String url = driver.getCurrentUrl()

    then:
    url.contains("tools/Entity/DataExport")
    text1 == "No entity names specified, not exporting anything."
  }


  def "tools/Entity/DataSnapshot test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Data Snapshots"))
    clickBySelector(".inner > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(6) > button:nth-child(1)")
    String alert2 = getAlertTextAndAccept()

    clickById("UploadDialog-button")
    clickById("UploadSnapshot_submitButton")
    clickBySelector("#UploadDialog > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")

    clickById("ExportDialog-button")
    clickBySelector(".select2-selection__rendered")
    select(By.id("select2-ExportData_entitiesToSkip-results"),5)
    clickById("ExportData_submitButton")

    //TODO: figure out why the following isn't waiting to click
//        clickById("ImportDialog_0-button")
//        clickBySelector("#ImportForm_0_dummyFks_0 > input:nth-child(1)")
//        clickBySelector("#ImportForm_0_useTryInsert_0 > input:nth-child(1)")
//        clearAndSendKeys(By.id("ImportForm_0_transactionTimeout_0"),"10000")
//        clickBySelector("ImportForm_0_submitButton_0")
//        clickBySelector("div.row:nth-child(8) > div:nth-child(5) > form:nth-child(1) > button:nth-child(2)")

    String url = driver.getCurrentUrl()

    then:
    url.contains("tools/Entity/DataSnapshot")
    alert2 == "Really create all missing foreign keys?"
  }

  def "tools/Entity/SQLRunner test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("SQL Runner"))
    sendKeys(By.id("SqlOptions_sql"),"BAD SQL!!!")
    clearAndSendKeys(By.id("SqlOptions_limit"),"100")
    clickById("SqlOptions_submitButton")
    String url = driver.getCurrentUrl()

    then:
    url.contains("submitButton")
  }

  def "tools/Entity/Entities test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Entities"))

    sendKeys(By.id("FilterForm_filterRegexp"),"BitcoinWallet")
    clickById("FilterForm_submitButton")
    clickById("FilterForm_submitButton")
    clickById("EntityList_detail_0_detail")
    clickById("RelatedEntities_link_0_find")

    clickBySelector("#ListEntityValue_edit_0")
    clearAndSendKeys(By.id("UpdateEntityValue_description"),"Vx Rbt ************914")
    sendKeys(By.id("UpdateEntityValue_ledgerBalance"),"72832")
    sendKeys(By.id("UpdateEntityValue_availableBalance"),"2131")
    clearAndSendKeys(By.id("UpdateEntityValue_balanceDate_idate"),"2020-04-25 09:00")
    sendKeys(By.id("UpdateEntityValue_imageUrl"),"https://youtu.be/5SoJIEwe3cA?t=1696")
    sendKeys(By.id("UpdateEntityValue_paymentFraudEvidenceId"),"worlds 2020 was FAKE!")
    clickById("UpdateEntityValue_submitButton")
    clearAndSendKeys(By.id("LevelsForm_dependentLevels"),"5")
    clickById("LevelsForm_submitButton")
    String url = driver.getCurrentUrl()

    then:
    url.contains("tools/Entity")
  }

  def "tools/Entity/DataViews test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Data Views"))
    sendKeys(By.id("CreateDbViewEntity_dbViewEntityName"),"Entityname")
    sendKeys(By.id("CreateDbViewEntity_packageName"),"packagename")
    clickById("CreateDbViewEntity_submitButton")
    clickById("ListDbViewEntities_edit_0_edit")
    clickBySelector("a.btn-primary:nth-child(1)")
    String url = driver.getCurrentUrl()

    then:
    url.contains("Entityname")
  }

  def "tools/Entity/SpeedTest test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Speed Test"))
    clearAndSendKeys(By.id("SelectBaseCalls_baseCalls"),"100")
    clickById("SelectBaseCalls_submitButton")
    String url = driver.getCurrentUrl()

    then:
    url.contains("tools/Entity/SpeedTest")
  }

  def "tools/Entity/QueryStats test"(){
    when:
    clickElement(By.linkText("Tools"))
    clickElement(By.linkText("Query Stats"))
    clickBySelector(".inner > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(1) > button:nth-child(1)")

    sendKeys(By.id("FilterForm_entityFilter"),"Entity")
    sendKeys(By.id("FilterForm_sqlFilter"),"sqlfilter")

    clickById("FilterForm_submitButton")
    String url = driver.getCurrentUrl()

    then:
    url.contains("tools/Entity/QueryStats")
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
