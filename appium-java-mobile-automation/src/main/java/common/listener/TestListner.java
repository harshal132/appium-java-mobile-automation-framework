package common.listener;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common.constants.FilePath;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.ExtentManager;
import utils.ExtentTestManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class TestListner implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        String testClassName = result.getTestClass().getRealClass().getSimpleName();
        ExtentTest test = ExtentTestManager
                .startTest(String.format("%s - %s ( %s )", testClassName, result.getName(), BaseTest.platformName));
        test.assignDevice(BaseTest.platformName.toString());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, result.getName() + " Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String failedScreenshotPath;
        WebDriver driver = BaseTest.getDriver();
        if (driver != null) {
            try {
                failedScreenshotPath = takeScreenShot();
                ExtentTestManager.getTest().fail("Failed At Screenshot: ",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(failedScreenshotPath).build());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ExtentTestManager.getTest().log(Status.FAIL,
                    result.getName() + " Test Failed due to exception: " + result.getThrowable());
        } else
            System.out.println("Driver already closed, is null");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.wasRetried())
            ExtentTestManager.getTest().log(Status.WARNING, result.getName() + "Test failed and will retry");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    public static synchronized String takeScreenShot() throws Exception {
        String encodedBase64 = null;
        FileInputStream fileInputStream = null;
        WebDriver driver = BaseTest.getDriver();
        try {
            if (driver != null) {
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                fileInputStream = new FileInputStream(screenshotFile);
                byte[] bytes = new byte[(int) screenshotFile.length()];
                fileInputStream.read(bytes);
                encodedBase64 = new String(Base64.getEncoder().encode((bytes)));
            }
        } catch (Exception e) {
            System.out.println("An exception occured while taking screenshot: " + e.getCause());
            throw e;
        }
        fileInputStream.close();
        return encodedBase64;

    }
}
