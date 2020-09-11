import Selenium.Helper
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import spock.lang.Shared
import spock.lang.Specification

class DataModelResourceBookTests extends Specification{

    @Shared String apps = "vapps"

    @Shared Helper helper = new Helper()

    def setupSpec(){ helper.setupSpec() }
    def cleanupSpec(){ helper.cleanupSpec() }
    def setup(){ helper.setup(apps,"POPC ERP") }
    def cleanup(){ helper.cleanup(apps) }

    /**
     * What are the attributes or characteristics of the people and organizations that are involved in the course of conducting business?
     */
    def "Chapter2"(){
        when:
        helper.clickByText("Suppliers")

        String supplierUrl = helper.driver.getCurrentUrl()

        helper.clickByText("Amazon Web Services")
//        helper.clickByText("Add Identification")
//        helper.sendKeysBySelector("input[name='idValue']","1080904")



        then:
        supplierUrl.contains("Supplier/FindSupplier")
    }

    /**
     * What relationships exist between various people, between various organizations, and between people and organizations?
     * What are the addresses, phone numbers, and other contact mechanisms of people and organizations, and how can they be contacted?
     * What types of communication or contacts have occurred between various parties, and what is necessary to effectively follow up on these communications?
     */

}
