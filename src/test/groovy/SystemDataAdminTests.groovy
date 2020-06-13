import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

import Selenium.Helper

class SystemDataAdminTests extends Specification{

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

  def "system/DataAdmin/L10n:Messages test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("L10n: Messages"))
    helper.clickById("CreateMessageDialog-button")
    helper.sendKeys(By.id("CreateLocalizedMessage_original"),"Original")
    helper.clickById("CreateLocalizedMessage_submitButton")
    helper.clickById("UpdateLocalizedMessages_update")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/Localization/Messages")
  }

  def "system/DataAdmin/L10n:EntityFields test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("L10n: Entity Fields"))
    //helper.clickById("CreateEntityFieldDialog-button")
    //helper.sendKeys(By.id("CreateLocalizedEntityField_entityName"),"EntityName")
    //helper.sendKeys(By.id("CreateLocalizedEntityField_fieldName"),"FieldName")
    //helper.clickById("CreateLocalizedEntityField_submitButton")
    //helper.clickById("UpdateLocalizedEntityFields_delete_0_deleteLocalizedEntityField_button")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/Localization/EntityFields")
  }

  def "system/DataAdmin/ResourceFinder test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Resource Finder"))
    //TODO: add tests to this later

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Resource/ElFinder")
  }
}
