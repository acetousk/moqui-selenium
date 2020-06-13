import Selenium.Helper
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

class SystemIntegrationAdminTests extends Specification{

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

  def "system/IntegrationAdmin/SystemMessages test"(){
    when:
    helper.clickElement(By.linkText("System"))
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

  def "system/IntegrationAdmin/MessageRemotes test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Message Remotes"))
    helper.clickById("CreateRemoteDialog-button")
    helper.clickById("CreateMessageRemoteForm_submitButton")
    helper.clickById("SystemMessageRemoteList_systemMessageRemoteId_0_remoteDetail")
    helper.clickById("SystemMessageRemoteForm_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/SystemMessage/Remote/MessageRemoteDetail")
  }

  def "system/IntegrationAdmin/MessageTypes test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Message Types"))
    helper.clickById("CreateTypeDialog-button")
    helper.sendKeys(By.id("CreateMessageTypeForm_description"),"Description")
    helper.clickById("CreateMessageTypeForm_submitButton")

    helper.clickById("SystemMessageTypeList_systemMessageTypeId_4_typeDetail")
    helper.clickById("SystemMessageTypeForm_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/SystemMessage/Type/MessageTypeDetail")
  }

  def "system/IntegrationAdmin/EntityDataSync test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Entity Data Sync"))

    helper.clickById("EntitySyncList_find")
    helper.clickById("CreateEntitySyncDialog-button")
    helper.clickById("CreateEntitySync_submitButton")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/EntitySync/EntitySyncList")
  }

}
