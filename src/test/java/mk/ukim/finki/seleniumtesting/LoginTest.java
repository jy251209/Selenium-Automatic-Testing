package mk.ukim.finki.seleniumtesting;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class LoginTest {
    private WebDriver driver;
    public List<Dimension> screenDimensionsList;

    //Constants
	private final String invalidEmailFormat = "invalidEmail";
	private final String emailNotExist = "invalidEmail@email.com";
	private final String validEmail = "invalidEmail@email.com";
	private final String pwd = "wrong_password_test";
	
    @BeforeTest
    public void setup() {
        driver = getDriver();
    }

/* TC-1 Load the application */
    @Test
    public void launchApp() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        System.out.println("----"+driver.getTitle());
        loginPage.isLoaded();
    }

/* TC-2 Verify text boxes link button are present */
    @Test
    public void invalidCredentials() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.isLoaded();
        loginPage.login(validEmail, pwd);
    }
    
/* TC-3 Invalid credentials - combinations */
    @Test
    public void canNotLoginWithoutUserName() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.isLoaded();
        
        loginPage.login(validEmail, pwd);
        String errorMessage = loginPage.getErrorMessage();
        System.out.println("errorMessage "+errorMessage);
        assertEquals(errorMessage, "We didn't recognize that email and/or password. Need help?");
        
        loginPage.login(validEmail, "");
        assertEquals(loginPage.getErrorMessage(), "We didn't recognize that email and/or password. Need help?");
        
        loginPage.login("", pwd);
        assertEquals(loginPage.getErrorMessage(), "We didn't recognize that email and/or password. Need help?");
        
        loginPage.login("", "");
        assertEquals(loginPage.getErrorMessage(), "We didn't recognize that email and/or password. Need help?");               
    }
    
/* TC-4 Login Reset - Combinations */
    @Test
    public void shouldLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.isLoaded();
        loginPage.loginReset();
        loginPage.getErrMsgForLoginReset(invalidEmailFormat);
        loginPage.getErrMsgForLoginReset(emailNotExist);
        loginPage.getErrMsgForLoginReset(validEmail);
    }

    /* TC-5 Login functionality in responsive design */
    @Test
    public void screenResponsiveness() throws InterruptedException {
        screenDimensionsList = new ArrayList<Dimension>();

        screenDimensionsList.add(new Dimension(1600,800));
        screenDimensionsList.add(new Dimension(1200,800));
        screenDimensionsList.add(new Dimension(480,800));
        screenDimensionsList.add(new Dimension(360,800));
        
        for(Dimension d: screenDimensionsList){
            driver.manage().window().setSize(d);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open();
            loginPage.isLoaded();
            loginPage.login("gabdimitrievski111@gmail.com", "");
            try{
                Thread.sleep(2000);
            }catch(Exception e){
                e.printStackTrace();
           }
       }
    }
    
    private WebDriver getDriver() {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jyothish\\Documents\\FullStack\\chromedriver.exe");
        return new ChromeDriver();
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
