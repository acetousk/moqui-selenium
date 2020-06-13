import Selenium.Helper
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

class ToolsGeneralTests extends Specification{

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

  def "tools/GeneralTools/AutoScreens test"(){
    when:
    helper.clickElement(By.linkText("Tools"))
    helper.clickElement(By.linkText("Auto Screens"))
    helper.clickBySelector("#select2-SelectEntity_aen-container")
    helper.select(By.cssSelector(".select2-selection"),10)
    helper.select(By.cssSelector(".select2-selection"),7)
    helper.clickById("EntityList_aen_82_find")//sales opportunity stage
    helper.clickById("FindValueDialog-button")
    helper.clickBySelector("#FindEntityValue > fieldset:nth-child(2) > div:nth-child(1) > div:nth-child(2) > span:nth-child(1) > span:nth-child(1) > input:nth-child(1)")
    helper.sendKeys(By.id("FindEntityValue_opportunityStageId"),"Stage Id")
    helper.sendKeys(By.id("FindEntityValue_description"),"description")
    helper.sendKeys(By.id("FindEntityValue_defaultProbability_from"),"0")
    helper.sendKeys(By.id("FindEntityValue_defaultProbability_thru"),"5")
    helper.sendKeys(By.id("FindEntityValue_sequenceNum_from"),"1")
    helper.sendKeys(By.id("FindEntityValue_sequenceNum_thru"),"3")
    helper.clickById("FindEntityValue_submitButton")
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("AutoScreen/AutoFind")
  }


  def "tools/GeneralTools/ArtifactStats test"(){
    when:
    helper.clickElement(By.linkText("Tools"))
    helper.clickElement(By.linkText("Artifact Stats"))
    helper.clickById("ServiceInfoList_name_13_serviceReference")//org.moqui
    //helper.clickById("SelectServiceInput_submitButton")//start moqui.org's service
    helper.clickBySelector("tr.form-list-nav-row:nth-child(1) > th:nth-child(1) > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1)")//page 2
    helper.clickById("ServiceList_detail_0_serviceDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Service/ServiceDetail")
  }

  def "tools/GeneralTools/Services test"(){
    when:
    helper.clickElement(By.linkText("Tools"))
    helper.clickElement(By.linkText("Services"))
    helper.clickById("ServiceList_run_22_serviceRun")//mantle.account.FinancialAccountServices.reverse#InvoiceTransactions


    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Service/ServiceRun")
  }
}
