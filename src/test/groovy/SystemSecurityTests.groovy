import Selenium.Helper
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

class SystemSecurityTests extends Specification{

  //if running these tests more than once, add 1 to the number below before running it
  @Shared String user =          "0potate"
  @Shared String pass =          "0Passw0rd!"
  @Shared String email =         "0test@moqui.org"
  @Shared String userGroup =     "0Test"
  @Shared String artifactGroup = "0TestArtifactGroup"
  @Shared String name =          "0name"
  @Shared String value =         "0value"
  @Shared String value2 =        "0value2"

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



  def "system/ServerAdmin/Users test"(){
    when:
    helper.clickElement(By.linkText("System"))
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

  def "system/ServerAdmin/UserGroups test"(){
    when:
    helper.clickElement(By.linkText("System"))
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

  def "system/ServerAdmin/ArtifactGroups test"(){
    when:
    helper.clickElement(By.linkText("System"))
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

  def "system/ServerAdmin/JobsRuns test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Jobs Runs"))
    helper.clickById("JobRunList_hdialog_button")
    //helper.sendKeys(By.id("JobRunList_header_jobRunId"),"100170")
    helper.clickById("JobRunList_header_findButton")
    helper.clickById("JobRunList_jobName_0_serviceJobDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("ServiceJob/Jobs/ServiceJobDetail")
  }

  def "system/ServerAdmin/ActiveUsers test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Active Users"))

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Security/ActiveUsers")
  }

}
