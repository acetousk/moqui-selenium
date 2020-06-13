import Selenium.Helper
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

class SystemServerAdminTests extends Specification{

  @Shared Helper helper = Helper.get()

  def setupSpec(){
    System.println("Start Framework Browser Tests")

    helper.driver.get("http://localhost:8080")
    helper.clickElement(By.id("TestLoginLink_button"))

    helper.driver.manage().window().setPosition(new Point(0, 0));
    helper.driver.manage().window().setSize(new Dimension(1080, 720))
  }

  def cleanupSpec(){
    helper.driver.get("http://localhost:8080")
    helper.driver.findElement(By.cssSelector(".glyphicon-off")).click()
    System.println(helper.getAlertTextAndAccept())

    helper.driver.quit()

    System.println("Framework Browser Tests Done!")
  }

  def setup(){
    helper.driver.get("http://localhost:8080")
  }

  def cleanup(){
    helper.clickElement(By.className("navbar-brand"))
  }


  def "system/ServerAdmin/CacheMgmt test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Cache Mgmt"))
    //helper.clickBySelector("div.row:nth-child(2) > div:nth-child(1) > form:nth-child(1) > button:nth-child(1)")//clear all
    //helper.clickBySelector("div.row:nth-child(2) > div:nth-child(1) > form:nth-child(2) > button:nth-child(1)")//clear all artifacts
    //helper.clickElement(By.linkText("Run GC"))

    //String alert1 = helper.getAlertTextAndAccept()

    helper.sendKeys(By.id("FilterForm_filterRegexp"),"security.UserGroupMember")
    helper.clickById("FilterForm_submitButton")
    helper.clickById("CacheList_clear_0_clearCache_button")

    helper.sendKeys(By.id("FilterForm_filterRegexp"),"security.UserGroupMember")
    helper.clickById("FilterForm_submitButton")
    helper.clickById("CacheList_name_1_cacheElements")
    helper.clickById("CacheElements_clear_0_clearCacheElement_button")

    String url = helper.driver.getCurrentUrl()

    then:
    //alert1.contains("Run Java garbage collection? This may take some time and pause other running threads.")
    url.contains("Cache/CacheElements?cacheName=entity.record.list_ra.moqui.security.UserGroupMember")
  }

  def "system/ServerAdmin/InstanceMgmt test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Instance Mgmt"))
    helper.clickById("CreateInstanceDialog-button")
    helper.sendKeys(By.id("CreateInstance_instanceName"),"InstanceName")
    helper.sendKeys(By.id("CreateInstance_hostName"),"HostName")
    helper.clickById("CreateInstance_submitButton")

    //helper.clickById("InstanceList_instanceLinks_2_initAppInstance_button")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Instance/InstanceList")
  }

  def "system/ServerAdmin/Printers test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Printers"))
    //helper.clickById("GetDialog-button")
    //helper.sendKeys(By.id("GetForm_serverHost"),"127.0.0.1")
    //helper.clickById("GetForm_submitButton")
    //helper.clickBySelector("#GetDialog > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")//exit button
    helper.clickById("CreateDialog-button")
    helper.sendKeys(By.id("CreateForm_serverHost"),"127.0.0.1")
    helper.clickById("CreateForm_submitButton")
    helper.clickById("PrintJobList_update_0")
    helper.clickById("PrintJobList_jobs_0_printJobList")
    helper.clickById("PrintJobList_find")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Print/PrintJob/PrintJobList")
  }


  def "system/ServerAdmin/ServiceJobs test"(){
    when:
    System.println("If this test fails, (this will often happen if this test is run more than once) manually delete the parameter here http://localhost:8080/vapps/system/ServiceJob/Jobs/ServiceJobDetail?jobName=run_EntitySyncAll_frequent ")

    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Service Jobs"))
    helper.sendKeys(By.id("ServiceJobList_jobName"),"run_EntitySyncAll_frequent")
    helper.clickById("ServiceJobList_findButton")
    helper.clickById("ServiceJobList_jobName_0_serviceJobDetail")
    helper.clickBySelector("button.btn:nth-child(2)")//run jobs

    String alert1 = helper.getAlertTextAndAccept()

    //helper.clickById("ServiceJobForm_submitButton")

    //helper.clickById("AddParameterDialog-button")
    //helper.sendKeys(By.id("CreateJobParameter_parameterName"),"name")
    //helper.sendKeys(By.id("CreateJobParameter_parameterValue"),"value")
    //helper.clickById("CreateJobParameter_submitButton")

    //helper.clearAndSendKeys(By.id("UpdateJobParameter_parameterValue_0"),"value2")
    //helper.clickById("UpdateJobParameter_submitButton_0")
    //helper.clickById("UpdateJobParameter_deleteLink_0_deleteJobParameter_button")
    //helper.String alert2 = getAlertTextAndAccept()

    //helper.clickById("AddUserDialog-button")
    //helper.sendKeys(By.id("CreateJobUser_userId_ac"),"User")
    //helper.clickById("CreateJobUser_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    alert1.contains("Run job now with current parameters?")
    //alert2.contains("Delete parameter?")
    url.contains("ServiceJob/Jobs/ServiceJobDetail")
  }

  def "system/ServerAdmin/JobsRuns test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Jobs Runs"))
    helper.clickById("JobRunList_hdialog_button")
    helper.sendKeys(By.id("JobRunList_header_jobRunId"),"100170")
    helper.clickById("JobRunList_header_findButton")
    //helper.clickById("JobRunList_jobName_0_serviceJobDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("ServiceJob/JobRuns")
  }

}
