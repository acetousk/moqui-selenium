import Selenium.Helper
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import spock.lang.Shared
import spock.lang.Specification

class SystemTests extends Specification{

  //if running these tests more than once, add 1 to the number below before running it
  @Shared String user =          "000potate"
  @Shared String pass =          "000Passw0rd!"
  @Shared String email =         "000test@moqui.org"
  @Shared String userGroup =     "000Test"
  @Shared String artifactGroup = "000TestArtifactGroup"
  @Shared String name =          "000name"
  @Shared String value =         "000value"
  @Shared String value2 =        "000value2"

  //if running these tests more than once, change to the next letter below before running it
  @Shared String mantleTestId =   "AABMantleTestId"
  @Shared String mantleTestName = "AABMantleTestName"
  @Shared String original =       "AABOriginal"
  @Shared String entityName =     "AABEntityName"
  @Shared String fieldName =      "AABFieldName"
  @Shared String pk =             "AABpk"
  @Shared String locale =         "AABUS"

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
    helper.clickElement(By.linkText("System"))
  }

  def cleanup(){
    helper.clickElement(By.className("navbar-brand"))
  }

  def "DataAdmin/L10n:Messages test"(){
    when:
    helper.clickElement(By.linkText("L10n: Messages"))
    helper.clickById("CreateMessageDialog-button")
    helper.sendKeys(By.id("CreateLocalizedMessage_original"),original)
    helper.clickById("CreateLocalizedMessage_submitButton")
    helper.clickById("UpdateLocalizedMessages_update")
    helper.sendKeys(By.id("UpdateLocalizedMessages_original"),original)
    helper.pause(2)
    helper.clickById("UpdateLocalizedMessages_delete")

    String isOriginal = helper.getTextBySelector("#UpdateLocalizedMessages_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > span:nth-child(1)")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Localization/Messages")
    isOriginal.contains(original)
  }

  def "DataAdmin/L10n:EntityFields test"(){
    when:
    helper.clickElement(By.linkText("L10n: Entity Fields"))
    helper.clickById("CreateEntityFieldDialog-button")
    helper.sendKeys(By.id("CreateLocalizedEntityField_entityName"),entityName)
    helper.sendKeys(By.id("CreateLocalizedEntityField_fieldName"),fieldName)
    helper.sendKeys(By.id("CreateLocalizedEntityField_pkValue"),pk)
    helper.sendKeys(By.id("CreateLocalizedEntityField_locale"),locale)
    helper.clickById("CreateLocalizedEntityField_submitButton")

    helper.sendKeys(By.id("UpdateLocalizedEntityFields_entityName"),entityName)
    //helper.pause(2)
    helper.clickById("UpdateLocalizedEntityFields_delete")

    String isEntityName = helper.getTextBySelector("#UpdateLocalizedEntityFields_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > span:nth-child(1)")
    System.println("isEntityName = " + isEntityName)
    String isFieldName = helper.getTextBySelector("#UpdateLocalizedEntityFields_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
    System.println("isFieldName = " + isFieldName)
    String isPk = helper.getTextBySelector("#UpdateLocalizedEntityFields_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(3) > div:nth-child(1) > span:nth-child(1)")
    System.println("isPk = " + isPk)
    String isLocale = helper.getTextBySelector("#UpdateLocalizedEntityFields_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(4) > div:nth-child(1) > span:nth-child(1)")
    System.println("isLocale = " + isLocale)

    helper.clickById("UpdateLocalizedEntityFields_delete_0_deleteLocalizedEntityField_button")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Localization/EntityFields")
    isEntityName.contains(entityName)
    isFieldName.contains(fieldName)
    isPk.contains(pk)
    isLocale.contains(locale)
  }

  def "DataAdmin/ResourceFinder test"(){
    when:
    helper.clickElement(By.linkText("Resource Finder"))
    //TODO: add tests to this later
    //it will take some work and may not be necessary

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Resource/ElFinder")
  }

  def "DataDocumentAndFeeds/Export test"(){
    when:
    helper.clickElement(By.linkText("Export"))

    helper.select(By.cssSelector(".select2-search__field"),3)
    helper.clickBySelector("#ExportDocuments_output_1 > input:nth-child(1)")
    helper.clickById("ExportDocuments_submitButton")
    String isError = helper.getTextBySelector(".text-inline")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("DataDocument/Export")
    isError.contains("Path is not a directory.")
  }

  //TODO: Start back here
  def "DataDocumentAndFeeds/FeedIndex test"(){
    when:
    helper.clickElement(By.linkText("Feed Index"))

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("DataDocument/Index")
  }

  def "DataDocumentAndFeeds/EditandReportBuilder test"(){
    when:
    helper.clickElement(By.linkText("Edit and Report Builder"))
    helper.clickById("CreateDialog-button")
    helper.sendKeys(By.id("CreateDataDocument_dataDocumentId"),mantleTestId)
    helper.clickById("CreateDataDocument_submitButton")
    helper.sendKeys(By.id("EditDataDocument_documentName"),mantleTestName)
    helper.clickById("EditDataDocument_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("DataDocument/Edit/EditDataDocument")
  }

  def "IntegrationAdmin/SystemMessages test"(){
    when:
    helper.clickElement(By.linkText("System Messages"))
    helper.clickById("SendDialog-button")
    helper.clickById("SendForm_submitButton")
    helper.clickById("ConsumeDialog-button")
    helper.clickById("ConsumeForm_submitButton")
    //helper.clickById("NewIncomingMessageDialog-button")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("SystemMessage/Message/SystemMessageList")
  }

  def "IntegrationAdmin/MessageRemotes test"(){
    when:
    helper.clickElement(By.linkText("Message Remotes"))
    helper.clickById("CreateRemoteDialog-button")
    helper.clickById("CreateMessageRemoteForm_submitButton")
    helper.clickById("SystemMessageRemoteList_systemMessageRemoteId_0_remoteDetail")
    helper.clickById("SystemMessageRemoteForm_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("SystemMessage/Remote/MessageRemoteDetail")
  }

  def "IntegrationAdmin/MessageTypes test"(){
    when:
    helper.clickElement(By.linkText("Message Types"))
    helper.clickById("CreateTypeDialog-button")
    helper.sendKeys(By.id("CreateMessageTypeForm_description"),"Description")
    helper.clickById("CreateMessageTypeForm_submitButton")

    helper.clickById("SystemMessageTypeList_systemMessageTypeId_4_typeDetail")
    helper.clickById("SystemMessageTypeForm_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("SystemMessage/Type/MessageTypeDetail")
  }

  def "IntegrationAdmin/EntityDataSync test"(){
    when:
    helper.clickElement(By.linkText("Entity Data Sync"))

    helper.clickById("EntitySyncList_find")
    helper.clickById("CreateEntitySyncDialog-button")
    helper.clickById("CreateEntitySync_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("EntitySync/EntitySyncList")
  }

  def "Security/Users test"(){
    when:
    helper.clickElement(By.linkText("Users"))
    helper.clickById("CreateUserAccountDialog-button")
    helper.sendKeys(By.id("CreateUserAccount_username"),user)
    helper.sendKeys(By.id("CreateUserAccount_newPassword"),pass)
    helper.sendKeys(By.id("CreateUserAccount_newPasswordVerify"),pass)
    helper.clickById("CreateUserAccount_submitButton")

    helper.sendKeys(By.id("UserAccountList_username"),user)
    helper.clickById("UserAccountList_find")

    helper.clickById("UserAccountList_username_0_userAccountDetail")

    helper.sendKeys(By.id("UserAccountForm_emailAddress"),email)
    helper.clickById("UserAccountForm_submitButton")

    helper.clickById("AddGroupMemberDialog-button")
    //helper.select(By.id("select2-CreateUserGroupMember_userGroupId-container"),"1")
    helper.clickById("CreateUserGroupMember_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Security/UserAccount/UserAccountDetail")
  }

  def "Security/UserGroups test"(){
    when:
    helper.clickElement(By.linkText("User Groups"))

    helper.clickById("CreateUserGroupDialog-button")

    helper.sendKeys(By.id("CreateUserGroup_userGroupId"),userGroup)
    helper.clickById("CreateUserGroup_submitButton")

    helper.sendKeys(By.id("UserGroupList_userGroupId"),userGroup)
    helper.clickById("UserGroupList_find")

    helper.clickById("UserGroupList_userGroupId_0_userGroupDetail")
    //TODO: add inputting data things here?

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Security/UserGroup/UserGroupDetail")
  }

  def "Security/ArtifactGroups test"(){
    when:
    helper.clickElement(By.linkText("Artifact Groups"))

    helper.clickById("CreateArtifactGroupDialog-button")
    helper.sendKeys(By.id("CreateArtifactGroup_artifactGroupId"),artifactGroup)
    helper.clickById("CreateArtifactGroup_submitButton")

    helper.sendKeys(By.id("ArtifactGroupList_artifactGroupId"),artifactGroup)
    helper.clickById("ArtifactGroupList_find")
    helper.clickById("ArtifactGroupList_artifactGroupId_0_artifactGroupDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Security/ArtifactGroup/ArtifactGroupDetail")
  }

  def "Security/ServiceJobs test"(){
    when:
    System.println("If this test fails, (this will often happen if this test is run more than once) manually delete the parameter here http://localhost:8080/vapps/ServiceJob/Jobs/ServiceJobDetail?jobName=run_EntitySyncAll_frequent ")

    helper.clickElement(By.linkText("Service Jobs"))
    helper.sendKeys(By.id("ServiceJobList_jobName"),"run_EntitySyncAll_frequent")
    helper.clickById("ServiceJobList_findButton")
    helper.clickById("ServiceJobList_jobName_0_serviceJobDetail")
    helper.clickBySelector("button.btn:nth-child(2)")//run jobs

    String alert1 = helper.getAlertTextAndAccept()

    //helper.clickById("ServiceJobForm_submitButton")

    helper.clickById("AddParameterDialog-button")
    helper.sendKeys(By.id("CreateJobParameter_parameterName"),name)
    helper.sendKeys(By.id("CreateJobParameter_parameterValue"),value)
    helper.clickById("CreateJobParameter_submitButton")

    helper.clearAndSendKeys(By.id("UpdateJobParameter_parameterValue_0"),value2)
    helper.clickById("UpdateJobParameter_submitButton_0")
    //helper.clickById("UpdateJobParameter_deleteLink_0_deleteJobParameter_button")
    //String alert2 = helper.getAlertTextAndAccept()

    helper.clickById("AddUserDialog-button")
    helper.sendKeys(By.id("CreateJobUser_userId_ac"),"User")
    helper.clickById("CreateJobUser_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    alert1.contains("Run job now with current parameters?")
    //alert2.contains("Delete parameter?")
    url.contains("ServiceJob/Jobs/ServiceJobDetail")
  }

  def "Security/JobsRuns test"(){
    when:
    helper.clickElement(By.linkText("Jobs Runs"))
    helper.clickById("JobRunList_hdialog_button")
    //helper.sendKeys(By.id("JobRunList_header_jobRunId"),"100170")
    helper.clickById("JobRunList_header_findButton")
    helper.clickById("JobRunList_jobName_0_serviceJobDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("ServiceJob/Jobs/ServiceJobDetail")
  }

  def "Security/ActiveUsers test"(){
    when:
    helper.clickElement(By.linkText("Active Users"))

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Security/ActiveUsers")
  }

  def "ServerAdmin/CacheMgmt test"(){
    when:
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

  def "ServerAdmin/InstanceMgmt test"(){
    when:
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

  def "ServerAdmin/Printers test"(){
    when:
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


  def "ServerAdmin/ServiceJobs test"(){
    when:
    System.println("If this test fails, (this will often happen if this test is run more than once) manually delete the parameter here http://localhost:8080/vapps/ServiceJob/Jobs/ServiceJobDetail?jobName=run_EntitySyncAll_frequent ")

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

  def "ServerAdmin/JobsRuns test"(){
    when:
    helper.clickElement(By.linkText("Jobs Runs"))
    helper.clickById("JobRunList_hdialog_button")
    helper.sendKeys(By.id("JobRunList_header_jobRunId"),"100170")
    helper.clickById("JobRunList_header_findButton")
    //helper.clickById("JobRunList_jobName_0_serviceJobDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("ServiceJob/JobRuns")
  }

  def "Usage/ArtifactHitSummary test"(){
    when:
    helper.clickElement(By.linkText("Artifact Hit Summary"))
    //TODO: add artifacts to make a summary worth it

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "Usage/ArtifactHitBins test"(){
    when:
    helper.clickElement(By.linkText("Artifact Hit Bins"))
    //TODO: add artifacts to make a hit bin worth it

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "Usage/AuditLog test"(){
    when:
    helper.clickElement(By.linkText("Audit Log"))
    //TODO: not much to do here change that

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "Usage/Visits test"(){
    when:
    helper.clickElement(By.linkText("Visits"))

    helper.clickById("VisitList_hdialog_button")

    helper.sendKeys(By.id("VisitList_header_visitId"),"100000")
    helper.clickById("VisitList_header_findButton")

    helper.clickById("VisitList_visitId_0_visitDetail")
    //helper.clickById("VisitArtifactHitList_hdialog_button")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Visit/VisitDetail?visitId=100000")
  }

  def "Usage/LogViewer test"(){
    when:
    helper.clickElement(By.linkText("Log Viewer"))
    helper.clickById("SearchOptions_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/LogViewer")
  }
}
