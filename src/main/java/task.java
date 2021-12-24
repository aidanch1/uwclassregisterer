import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class task implements Runnable{
    String netid;
    String password;
    public task(String a, String b) {
        netid = a;
        password = b;
    }

    public static void dowork(String netid, String password) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://myplan.uw.edu/");
        //can wait up to 5 seconds for stuff to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        //login to myplan
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-netid"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='form']")));
        driver.findElement(By.id("weblogin_netid")).sendKeys(netid);
        driver.findElement(By.id("weblogin_password")).sendKeys(password);
        driver.findElement(By.id("submit_button")).click();

        //This ID will change by quarter, I'm guessing. Right now it is for winter 2022 hence the affix 20221
        wait.until(ExpectedConditions.elementToBeClickable(By.id("registration-20221"))).click();
        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Send to Registration"))).click();

        //wait until request is processed
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set handles = driver.getWindowHandles();
        handles.remove(originalWindow);
        String nextWindow = String.valueOf(handles.iterator().next());
        driver.switchTo().window(nextWindow);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("doneDiv")));

        driver.switchTo().window(originalWindow);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//Button[contains(text(), 'Close and Refresh')]"))).click();

        //confirm that the classes have been registered
        String finished = "You do not have any course sections ready to send to Registration.";
        wait.until(webDriver -> driver.findElement(By.xpath("//div[@class='card-body']/p")).getText().equals(finished));

        driver.quit();
    }

    @Override
    public void run() {
        dowork(netid, password);
    }
}
