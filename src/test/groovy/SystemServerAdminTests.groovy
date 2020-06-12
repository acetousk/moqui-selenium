import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Shared
import spock.lang.Specification

class SystemServerAdminTests extends Specification{

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

  def "system/ServerAdmin/CacheMgmt test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Cache Mgmt"))
    //clickBySelector("div.row:nth-child(2) > div:nth-child(1) > form:nth-child(1) > button:nth-child(1)")//clear all
    //clickBySelector("div.row:nth-child(2) > div:nth-child(1) > form:nth-child(2) > button:nth-child(1)")//clear all artifacts
    //clickElement(By.linkText("Run GC"))

    //String alert1 = getAlertTextAndAccept()

    sendKeys(By.id("FilterForm_filterRegexp"),"security.UserGroupMember")
    clickById("FilterForm_submitButton")
    clickById("CacheList_clear_0_clearCache_button")

    sendKeys(By.id("FilterForm_filterRegexp"),"security.UserGroupMember")
    clickById("FilterForm_submitButton")
    clickById("CacheList_name_1_cacheElements")
    clickById("CacheElements_clear_0_clearCacheElement_button")

    String url = driver.getCurrentUrl()

    then:
    //alert1.contains("Run Java garbage collection? This may take some time and pause other running threads.")
    url.contains("Cache/CacheElements?cacheName=entity.record.list_ra.moqui.security.UserGroupMember")
  }

  def "system/ServerAdmin/InstanceMgmt test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Instance Mgmt"))

    clickById("CreateInstanceDialog-button")
    sendKeys(By.id("CreateInstance_instanceName"),"InstanceName")
    sendKeys(By.id("CreateInstance_hostName"),"HostName")
    clickById("CreateInstance_submitButton")

    clickById("InstanceList_instanceLinks_2_initAppInstance_button")

    String url = driver.getCurrentUrl()

    then:
    url.contains("Instance/InstanceList")
  }

  def "system/ServerAdmin/Printers test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Printers"))
    //clickById("GetDialog-button")
    //sendKeys(By.id("GetForm_serverHost"),"127.0.0.1")
    //clickById("GetForm_submitButton")
    //clickBySelector("#GetDialog > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")//exit button
    clickById("CreateDialog-button")
    sendKeys(By.id("CreateForm_serverHost"),"127.0.0.1")
    clickById("CreateForm_submitButton")
    clickById("PrintJobList_update_0")
    clickById("PrintJobList_jobs_0_printJobList")
    clickById("PrintJobList_find")

    String url = driver.getCurrentUrl()

    then:
    url.contains("Print/PrintJob/PrintJobList")
  }

  def clickElement(By by){
    wait.until(ExpectedConditions.elementToBeClickable(by))
    driver.findElement(by).click()
  }


  def "system/ServerAdmin/ServiceJobs test"(){
    when:
    System.println("If this test fails, (this will often happen if this test is run more than once) manually delete the parameter here http://localhost:8080/vapps/system/ServiceJob/Jobs/ServiceJobDetail?jobName=run_EntitySyncAll_frequent ")

    clickElement(By.linkText("System"))
    clickElement(By.linkText("Service Jobs"))
    sendKeys(By.id("ServiceJobList_jobName"),"run_EntitySyncAll_frequent")
    clickById("ServiceJobList_findButton")
    clickById("ServiceJobList_jobName_0_serviceJobDetail")
    clickBySelector("button.btn:nth-child(2)")//run jobs

    String alert1 = getAlertTextAndAccept()

    //clickById("ServiceJobForm_submitButton")

    clickById("AddParameterDialog-button")
    sendKeys(By.id("CreateJobParameter_parameterName"),"name")
    sendKeys(By.id("CreateJobParameter_parameterValue"),"value")
    clickById("CreateJobParameter_submitButton")

    clearAndSendKeys(By.id("UpdateJobParameter_parameterValue_0"),"value2")
    clickById("UpdateJobParameter_submitButton_0")
    //clickById("UpdateJobParameter_deleteLink_0_deleteJobParameter_button")
    //String alert2 = getAlertTextAndAccept()

    clickById("AddUserDialog-button")
    sendKeys(By.id("CreateJobUser_userId_ac"),"User")
    clickById("CreateJobUser_submitButton")

    String url = driver.getCurrentUrl()

    then:
    alert1.contains("Run job now with current parameters?")
    //alert2.contains("Delete parameter?")
    url.contains("ServiceJob/Jobs/ServiceJobDetail")
  }

  def "system/ServerAdmin/JobsRuns test"(){
    when:
    clickElement(By.linkText("System"))
    clickElement(By.linkText("Jobs Runs"))
    clickById("JobRunList_hdialog_button")
    sendKeys(By.id("JobRunList_header_jobRunId"),"100170")
    clickById("JobRunList_header_findButton")
    clickById("JobRunList_jobName_0_serviceJobDetail")

    String url = driver.getCurrentUrl()

    then:
    url.contains("ServiceJob/Jobs/ServiceJobDetail?jobName=autoApprove_OrdersDelayed")
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
