import Selenium.Helper
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import spock.lang.Shared
import spock.lang.Specification

class ToolsTests extends Specification{

  @Shared String entityName =  "AaaaEntityName"
  @Shared String packageName = "zaaapackagename"
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
    helper.clickElement(By.linkText("Tools"))
  }

  def cleanup(){
    helper.clickElement(By.className("navbar-brand"))
  }

  def "Entity/DataImport test"(){
    when:
    helper.clickElement(By.linkText("Data Import"))
    helper.clearAndSendKeys(By.cssSelector("#ImportData_timeout"),"30")
    helper.clickBySelector("#ImportData_dummyFks > input:nth-child(1)")
    helper.sendKeys(By.cssSelector("#ImportData_types"),"foo")
    helper.sendKeys(By.xpath("//*[@id=\"ImportData_components\"]"),"goo")
    helper.clickBySelector("#ImportData_accordion_heading2 > h5:nth-child(1) > a:nth-child(1)")
    helper.sendKeys(By.cssSelector("#ImportData_location"),"boo")
    helper.clickBySelector("#ImportData_accordion_heading3 > h5:nth-child(1) > a:nth-child(1)")
    helper.clearAndSendKeys(By.cssSelector("#ImportData_xmlText"),"<facade-xml></facade-xml>")
    helper.clickBySelector("#ImportData_accordion_heading4 > h5:nth-child(1) > a:nth-child(1)")
    helper.sendKeys(By.cssSelector("#ImportData_jsonText"),"JSON stuff")
    helper.clickBySelector("#ImportData_accordion_heading5 > h5:nth-child(1) > a:nth-child(1)")
    helper.sendKeys(By.cssSelector("#ImportData_csvText"),"CSV stuff")
    helper.clickBySelector("#ImportData_checkOnly")
    helper.clickBySelector("#ImportData_onlyCreate")
    String alert1 = helper.getAlertTextAndAccept()
    helper.clickBySelector("#ImportData_importButton")
    String alert2 = helper.getAlertTextAndAccept()
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Entity/DataImport")
    alert1 == "Are you sure you want to load data, only creating missing records?"
    alert2 == "Are you sure you want to load data, creating new and updating existing records? If in doubt, cancel this and double check."
  }

  def "Entity/DataExport test"(){
    when:
    helper.clickElement(By.linkText("Data Export"))
    helper.clearAndSendKeys(By.cssSelector("#ExportData_dependentLevels"),"5")
    helper.sendKeys(By.cssSelector("#ExportData_masterName"),"MasterName")
    helper.clearAndSendKeys(By.cssSelector("#ExportData_fromDate > input:nth-child(1)"),"2020-00-02 13:45")
    helper.clearAndSendKeys(By.cssSelector("#ExportData_thruDate_idate"),"2040-06-02 13:20")
    helper.clickBySelector("#ExportData_fileType_1 > input:nth-child(1)")
    helper.clickBySelector("#ExportData_output > input:nth-child(1)")
    helper.sendKeys(By.cssSelector("#ExportData_path"),"/")
    helper.clickById("ExportData_submitButton")
    String text1 = helper.driver.findElement(By.cssSelector(".text-inline")).getText()
    System.println(text1)
    helper.select(By.cssSelector(".select2-search__field"),2)
    helper.select(By.cssSelector(".select2-search__field"),50)
    helper.select(By.cssSelector(".select2-search__field"),5)
    helper.select(By.cssSelector(".select2-search__field"),10)
    helper.clickById("ExportData_submitButton")
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Entity/DataExport")
    text1 == "No entity names specified, not exporting anything."
  }


  def "Entity/DataSnapshot test"(){
    when:
    helper.clickElement(By.linkText("Data Snapshots"))
    helper.clickBySelector(".inner > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(6) > button:nth-child(1)")
    String alert2 = helper.getAlertTextAndAccept()

    helper.clickById("UploadDialog-button")
    helper.clickById("UploadSnapshot_submitButton")
    helper.clickBySelector("#UploadDialog > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")

    helper.clickById("ExportDialog-button")
    helper.clickBySelector(".select2-selection__rendered")
    helper.select(By.id("select2-ExportData_entitiesToSkip-results"),5)
    helper.clickById("ExportData_submitButton")

    //TODO: figure out why the following isn't waiting to click
//        clickById("ImportDialog_0-button")
//        clickBySelector("#ImportForm_0_dummyFks_0 > input:nth-child(1)")
//        clickBySelector("#ImportForm_0_useTryInsert_0 > input:nth-child(1)")
//        clearAndSendKeys(By.id("ImportForm_0_transactionTimeout_0"),"10000")
//        clickBySelector("ImportForm_0_submitButton_0")
//        clickBySelector("div.row:nth-child(8) > div:nth-child(5) > form:nth-child(1) > button:nth-child(2)")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Entity/DataSnapshot")
    alert2 == "Really create all missing foreign keys?"
  }

  def "Entity/Entities test"(){
    when:
    helper.clickElement(By.linkText("Entities"))

    helper.sendKeys(By.id("FilterForm_filterRegexp"),"BitcoinWallet")
    helper.clickById("FilterForm_submitButton")
    helper.clickById("FilterForm_submitButton")
    helper.clickById("EntityList_detail_0_detail")
    helper.clickById("RelatedEntities_link_0_find")

    helper.clickBySelector("#ListEntityValue_edit_0")
    helper.clearAndSendKeys(By.id("UpdateEntityValue_description"),"Vx Rbt ************914")
    helper.sendKeys(By.id("UpdateEntityValue_ledgerBalance"),"72832")
    helper.sendKeys(By.id("UpdateEntityValue_availableBalance"),"2131")
    helper.clearAndSendKeys(By.id("UpdateEntityValue_balanceDate_idate"),"2020-04-25 09:00")
    helper.sendKeys(By.id("UpdateEntityValue_imageUrl"),"https://youtu.be/5SoJIEwe3cA?t=1696")
    helper.sendKeys(By.id("UpdateEntityValue_paymentFraudEvidenceId"),"worlds 2020 was FAKE!")
    helper.clickById("UpdateEntityValue_submitButton")

    helper.clickById("RelatedEntities_link_0_edit")

    String text = helper.getTextBySelector("pre.text-inline")

    helper.clearAndSendKeys(By.id("LevelsForm_dependentLevels"),"5")
    helper.clickById("LevelsForm_submitButton")
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Entity")
    text.contains("PaymentMethodType")
  }

  def "Entity/DataViews test"(){
    when:
    helper.clickElement(By.linkText("Data Views"))
    helper.sendKeys(By.id("CreateDbViewEntity_dbViewEntityName"),entityName)
    helper.sendKeys(By.id("CreateDbViewEntity_packageName"),packageName)
    helper.clickById("CreateDbViewEntity_submitButton")

    helper.clickBySelector("#ListDbViewEntities_table > thead:nth-child(1) > tr:nth-child(2) > th:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1) > a:nth-child(2) > i:nth-child(1)")

    String isEntityName = helper.getTextBySelector("#ListDbViewEntities_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > span:nth-child(1)")
    System.println("isEntityName = " + isEntityName)
    String isPackageName = helper.getTextBySelector("#ListDbViewEntities_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
    System.println("isPackageName = " + isPackageName)

    helper.clickById("ListDbViewEntities_edit_0_edit")
    helper.clickBySelector("a.btn-primary:nth-child(1)")
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("DataView")
    //isEntityName.contains(entityName)
    //isPackageName.contains(packageName)
  }

  //TODO:start here
  def "Entity/SpeedTest test"(){
    when:
    helper.clickElement(By.linkText("Speed Test"))
    helper.clearAndSendKeys(By.id("SelectBaseCalls_baseCalls"),"100")
    helper.clickById("SelectBaseCalls_submitButton")
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Entity/SpeedTest")
  }

  def "Entity/QueryStats test"(){
    when:
    helper.clickElement(By.linkText("Query Stats"))
    helper.clickBySelector(".inner > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(1) > button:nth-child(1)")

    helper.sendKeys(By.id("FilterForm_entityFilter"),"Entity")
    helper.sendKeys(By.id("FilterForm_sqlFilter"),"sqlfilter")

    helper.clickById("FilterForm_submitButton")
    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Entity/QueryStats")
  }

  def "Generaltools/AutoScreens test"(){
    when:
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

  def "Generaltools/ArtifactStats test"(){
    when:
    helper.clickElement(By.linkText("Artifact Stats"))
    helper.clickById("ServiceInfoList_name_13_serviceReference")//org.moqui
    //helper.clickById("SelectServiceInput_submitButton")//start moqui.org's service
    helper.clickBySelector("tr.form-list-nav-row:nth-child(1) > th:nth-child(1) > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1)")//page 2
    helper.clickById("ServiceList_detail_0_serviceDetail")

    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Service/ServiceDetail")
  }

  def "Generaltools/Services test"(){
    when:
    helper.clickElement(By.linkText("Services"))
    helper.clickById("ServiceList_run_22_serviceRun")//mantle.account.FinancialAccountServices.reverse#InvoiceTransactions


    String url = helper.driver.getCurrentUrl()

    then:
    url.contains("Service/ServiceRun")
  }
}
