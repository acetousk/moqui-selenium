
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration

class SeleniumHelper {
  static WebDriver driver = new FirefoxDriver()
  static Actions actions = new Actions(driver)
  static Select select
  static double defaultWaitTimeInSeconds = 1
/*
  //waits
  static WebElement wait(double time = defaultWaitTimeInSeconds ){
    try{
      return new WebDriverWait(driver, Duration.ofSeconds(time).getSeconds())
    }catch(Exception e){
      e.printStackTrace()
    }
  }

  WebElement waitUntil(ExpectedConditions conditions, double time = defaultWaitTimeInSeconds){
    try{
      return new WebDriverWait(driver, Duration.ofSeconds(time).getSeconds())
          .until(conditions)
    }catch(Exception e){
      e.printStackTrace()
    }
  }

  //action and wait
  //click
  def clickAndWait(By element){
    waitUntil(ExpectedConditions.elementToBeClickable(element)).click()
  }
  def click(By element){
    clickAndWait(element)
  }

  //double click
  def doubleClickAndWait(By element){
    actions.doubleClick(waitUntil(ExpectedConditions.elementToBeClickable(element))).perform()
  }
  def doubleClick(By element){
    doubleClickAndWait(element)
  }

  //select
  //TODO: check to see if this is actually works
  def selectAndWait(By element, String visibleText){
    select = new Select(waitUntil(ExpectedConditions.elementToBeSelected(element)))
    select.selectByVisibleText(visibleText)
  }
  def select(By element, String visibleText){
    selectAndWait(element, visibleText)
  }

  //sendKeys
  def sendKeysAndWait(By element, String keys){
    waitUntil(ExpectedConditions.elementToBeClickable(element)).sendKeys(keys)
  }
  def sendKeys(By element, String keys){
    sendKeysAndWait(element,keys)
  }

  //submit
  def submitAndWait(By element){
    waitUntil(ExpectedConditions.elementToBeClickable(element)).submit()
  }
  def submit(By element){
    submitAndWait(element)
  }

  //veryify / assert
  //text
  //TODO: does this work
  boolean verifyText(By element, String text){
    if(waitUntil(ExpectedConditions.invisibilityOfElementWithText(element,text)).getText().contains(text)){
      return true
    }else{
      //TODO: throw error
      return false
    }
  }
  def assertText(By element, String text){
    if(!verifyText(element,text)){
      //TODO: throw error
    }
  }

  //title
  boolean verifyTitle(String text){
    if(waitUntil(ExpectedConditions.titleContains(text))){
      return true
    }else{
      //TODO: throw error
      return false
    }
  }
  def assertTitle(String text){
    if(!verifyTitle(text)){
      //TODO: throw error
    }
  }

  //value
  boolean verifyValue(By element, String attribute, String text){
    if(waitUntil(ExpectedConditions.attributeContains(element,attribute,text))){
      return true
    }else{
      //TODO: throw error
      return false
    }
  }
  def assertValue(By element, String attribute, String text){
    if(!verifyValue(element,attribute,text)){
      //TODO: throw error
    }
  }

  //wait for
  //alert
  def waitForAlertPresent(By element){
    waitUntil(ExpectedConditions.alertIsPresent())
  }
  def waitForAlertNotPresent(By element){
    waitUntil(!ExpectedConditions.alertIsPresent())
  }

  //element
  def waitForElementPresent(By element){
    waitUntil(ExpectedConditions.presenceOfElementLocated(element))
  }
  def waitForElementNotPresent(By element){
    waitUntil(ExpectedConditions.stalenessOf(element))
  }

  //text
  def waitForTextPresent(By element, String text){
    waitUntil(ExpectedConditions.textToBePresentInElementLocated(element,text))
  }
  def waitForTextNotPresent(By element, String text){
    waitUntil(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(element,text)))
  }

  //value
  def waitForValue(By element, String text){
    waitUntil(ExpectedConditions.textToBePresentInElementValue(element,text))
  }
  def waitForNotValue(By element, String text){
    waitUntil(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(element,text)))
  }

  //visible
  def waitForVisible(By element){
    waitUntil(ExpectedConditions.visibilityOf(element))
  }
  def waitForNotVisible(By element){
    waitUntil(ExpectedConditions.invisibilityOf(element))
  }

  //random (self described)
  def pause(double time){
    wait(time)
  }

  def refresh(){
    driver.navigate().refresh()
  }

  def selectWindow(){
    int length = driver.getWindowHandles().size()
    driver.switchTo(driver.getWindowHandles()[length-1])
  }
  def selectFrame(By element){
    waitUntil(ExpectedConditions.visibilityOfElementLocated(element))
    driver.switchTo().frame(element) //wrong syntax?
  }

  def goBack(){
    driver.navigate().back()
  }

  def assertConfirmation(){
    driver.switchTo().alert().accept()
  }

 */
  def open(String target, String name="example name"){
    try{
      driver.get(target, name)
    }catch(Exception e){
      e.printStackTrace()
    }
  }

}
