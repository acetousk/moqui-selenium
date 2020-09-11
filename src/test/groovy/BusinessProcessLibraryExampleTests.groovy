
import org.openqa.selenium.*
import spock.lang.Shared
import spock.lang.Specification

import Selenium.Helper

class BusinessProcessLibraryExampleTests extends Specification{

    @Shared String apps = "vapps"

    @Shared Helper helper = new Helper()

    def setupSpec() {
        System.println("Start Framework Browser Tests")
        helper.setupSpec()
    }

    def cleanupSpec() {
        helper.cleanupSpec()
        System.println("Framework Browser Tests Done!")
    }

    def setup() {
        helper.driver.get("http://localhost:8080/" + apps)
    }

    def cleanup() {
        helper.clickElement(By.className("navbar-brand"))
    }

    def "Buyer Places Purchase Order"(){
        when:
        helper.clickElement(By.linkText("POPC ERP"))
        helper.clickElement(By.linkText("Purchase Orders"))
        helper.clickById("CreatePurchaseOrderDialog-button")
        helper.clickById("CreatePurchaseOrder_submitButton")

        helper.clickBySelector("#HeaderForm_placedDate > span:nth-child(2) > span:nth-child(1)")//placed
        helper.sendKeys(By.id("HeaderForm_orderName"),"BuyerPurchaseOrder")
        helper.clickById("HeaderForm_submitButton")

        helper.clickById("AddPartDialog-button")
        //helper.clickBySelector(".select2-container--focus > span:nth-child(1) > span:nth-child(1)")
        //helper.driver.findElement(By.cssSelector(".select2-container--below")).sendKeys(Keys.ENTER)
        //helper.clickBySelector("#AddPartForm_shipBeforeDate > span:nth-child(2) > span:nth-child(1)")
        //helper.clickBySelector(".select2-container--focus > span:nth-child(1) > span:nth-child(1)")
        helper.clickById("AddPartForm_submitButton")

        //helper.clickById("AddPaymentInfo_0-button")
        //helper.clearAndSendKeys(By.id("AddPaymentForm_0_amount_0"),"1000.00")
        //helper.clickById("AddPaymentForm_0_submitButton_0")


        String url = helper.driver.getCurrentUrl()

        then:
        url.contains("http")
    }
}
