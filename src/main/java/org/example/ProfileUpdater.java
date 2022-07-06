package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ProfileUpdater {
    public WebDriver driver;
    public String monthsOfExp;

    @SuppressWarnings("deprecation")
    void openUrl() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("start-fullscreen");
        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.get("https://www.naukri.com/");
    }

    void login() {
        driver.findElement(By.xpath("//div[text()='Login']")).click();
        WebElement emailTextbox = driver
                .findElement(By.xpath("//input[@placeholder='Enter your active Email ID / Username']"));
        emailTextbox.clear();
        // emailTextbox.sendKeys("adityasharmaa1008@gmail.com");
        emailTextbox.sendKeys("srishti3lohani@gmail.com");
        WebElement pwdTextbox = driver.findElement(By.xpath("//input[@placeholder='Enter your password']"));
        pwdTextbox.clear();
        // pwdTextbox.sendKeys("$u99y@Naukri");
        pwdTextbox.sendKeys("$rishti@103");
        pwdTextbox.submit();
        TakesScreenshot ss = ((TakesScreenshot) driver);
        File ssFile = ss.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(
                "C:\\Users\\Hp\\eclipse-workspace\\RelianceTechnicalRoundAutomation\\src\\main\\resources\\screenshot.png");
        try {
            FileUtils.copyFile(ssFile, DestFile);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    void editProfile() throws InterruptedException {
        // removing the chatbox popup
        WebElement chatbotPopup = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // if(driver.findElement(By.id("just to justify this")).isDisplayed()){
        // driver.findElement(By.xpath("//div[@class='crossIcon chatBot
        // chatBot-ic-cross']")).click();
        // clicking on profile picture
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='user-name roboto-bold-text']"))).click();
//        driver.findElement(By.xpath("//div[@class='user-name roboto-bold-text']")).click();
        driver.findElement(By.xpath("//em[text()='Edit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exp-months-droope"))).click();
//        driver.findElement(By.id("exp-months-droope")).click();
        monthsOfExp = driver.findElement(By.xpath("//*[@id='ul_exp-months-droope']/ul/li[@class='pickVal active']")).getText();
        System.out.println("Current experience in months is " + monthsOfExp + " & it's being changed");
        TimeUnit.SECONDS.sleep(2);
        if (monthsOfExp.equals("4 Months"))
            driver.findElement(By.xpath("//*[@id='ul_exp-months-droope']/ul/li[6]")).click();
        else if (monthsOfExp.equals("5 Months"))
            driver.findElement(By.xpath("//*[@id='ul_exp-months-droope']/ul/li[5]")).click();
        Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(By.id("saveBasicDetailsBtn")));
        driver.findElement(By.id("saveBasicDetailsBtn")).click();
        driver.quit();
    }

    @Test
    public void test() throws InterruptedException {
        // TODO Auto-generated method stub
        ProfileUpdater udp = new ProfileUpdater();
        udp.openUrl();
        udp.login();
        udp.editProfile();
    }
}