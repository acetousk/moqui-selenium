import Selenium.Helper
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

class SystemUsageTests extends Specification{

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

  def "system/Usage/ArtifactHitSummary test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Artifact Hit Summary"))
    //TODO: add artifacts to make a summary worth it

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "system/Usage/ArtifactHitBins test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Artifact Hit Bins"))
    //TODO: add artifacts to make a hit bin worth it

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "system/Usage/AuditLog test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Audit Log"))
    //TODO: not much to do here change that

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("system")
  }

  def "system/Usage/Visits test"(){
    when:
    helper.clickElement(By.linkText("System"))
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

  def "system/Usage/LogViewer test"(){
    when:
    helper.clickElement(By.linkText("System"))
    helper.clickElement(By.linkText("Log Viewer"))
    helper.clickById("LogMessageDocuments_user_id_0_userAccountDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Security/UserAccount/UserAccountDetail")
  }
}
