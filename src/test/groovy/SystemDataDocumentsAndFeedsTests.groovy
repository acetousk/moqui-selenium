import Selenium.Helper
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

class SystemDataDocumentsAndFeedsTests extends Specification{

  @Shared String mantleTestId =   "AMantleTestId"
  @Shared String mantleTestName = "AMantleTestName"
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

  def "system/DataDocumentAndFeeds/Search test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Search"))

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/DataDocument/Search")
  }

  def "system/DataDocumentAndFeeds/Export test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Export"))

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/DataDocument/Export")
  }

  def "system/DataDocumentAndFeeds/FeedIndex test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Feed Index"))

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system/DataDocument/Index")
  }

  def "system/DataDocumentAndFeeds/EditandReportBuilder test"(){
    when:
    helper.clickElement(By.linkText("System"))
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
}
