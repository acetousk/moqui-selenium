import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import spock.lang.*

import java.time.Duration

class BasicTest extends Specification{

    @Shared WebDriver driver = new FirefoxDriver()

    //set this to true to make a browser not pop up for tests (it will minimize the browser)
    //boolean minimized = true

    def setupSpec(){
        System.println("Start Framework Browser Tests")

        System.println("Importing Gecko Driver (if this fails you may need to change the path in the code)")

        //for different installations the path needs to change
        System.setProperty("webdriver.gecko.driver", "/home/acetousk/dev/java/moqui-selenium-test/geckodriver")

        System.println("Connecting Selenium WebDriver to Moqui through http (this may also have to be changed to wherever Moqui is being ran)")
        driver.get("http://localhost:8080")

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1080, 720))

        driver.findElement(By.id("TestLoginLink_button")).click();
    }

    def cleanupSpec(){
        System.println("Framework Browser Tests Done!")

        driver.findElement(By.cssSelector(".glyphicon-off")).click();
        driver.quit()
    }

    def setup(){
        driver.get("http://localhost:8080/vapps")
    }

    def cleanup(){
        driver.findElement(By.className("navbar-brand")).click()
    }

    def "tool test"(){
        when:
        WebElement toolsButton = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds())
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Tools")))
        toolsButton.click()

        then:
        "http://localhost:8080/vapps/tools/dashboard" == driver.getCurrentUrl()
    }
}
