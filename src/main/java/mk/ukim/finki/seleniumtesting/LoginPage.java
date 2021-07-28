package mk.ukim.finki.seleniumtesting;

import static org.testng.Assert.assertEquals;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LoginPage extends BasePage {
	
	private final String buttonHomePageLogin = "//a[@href='/login' and @class='btn__blue login']";
	private final String inputEmail = "//input[@id='email']";
	private final String inputPwd = "//input[@id='password']";
	private final String buttonLogin = "//button[@id='logIn']";
	private final String errMsgInvalidEmail = "//div[@class='login-error-container']";
	private final String linkNeedHelp = "//a[text()='Need help?']";
	private final String buttonReset = "//button[@id='resetBtn']";
	private final String inputForgotemail = "//button[@id='forgot-email']";
	private final String erMsgInvalidEmailFormat = "//div[@class='reset-error-container']";
	
	
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.hudl.com/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void isLoaded() throws InterruptedException {
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	Thread.sleep(5000);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav")));
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(buttonHomePageLogin)).click();
    	Thread.sleep(5000);
    }

    public void login(String user, String password) throws InterruptedException {
    	Thread.sleep(1000);
    	
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	Assert.assertEquals(true, driver.findElement(By.xpath(inputEmail)).isDisplayed());
    	driver.findElement(By.xpath(inputEmail)).clear();
    	driver.findElement(By.xpath(inputEmail)).sendKeys(user);
    	
    	Assert.assertEquals(true, driver.findElement(By.xpath(inputPwd)).isDisplayed());
    	driver.findElement(By.xpath(inputPwd)).clear();
    	driver.findElement(By.xpath(inputPwd)).sendKeys(password);
    	
    	Assert.assertEquals(true, driver.findElement(By.xpath(buttonLogin)).isDisplayed());
    	driver.findElement(By.xpath(buttonLogin)).click();
    	Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public String getErrMsgForLoginReset(String email) throws InterruptedException {
    	driver.findElement(By.xpath(inputForgotemail)).clear();
    	driver.findElement(By.xpath(inputForgotemail)).sendKeys(email);
    	driver.findElement(By.xpath(buttonReset)).click();
    	Thread.sleep(2000);
    	return driver.findElement(By.xpath(erMsgInvalidEmailFormat)).getText();
    }
    
    public void loginReset() throws InterruptedException{
    	Assert.assertEquals(false, driver.findElement(By.xpath(buttonLogin)).isEnabled());
    	driver.findElement(By.xpath(linkNeedHelp)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resetBtn")));
    	Assert.assertEquals(true, driver.findElement(By.xpath(buttonReset)).isEnabled());
    	
    }
    public String getErrorMessage() throws InterruptedException {
    	Thread.sleep(5000);
    	return driver.findElement(By.xpath(errMsgInvalidEmail)).getText();
    }
}
