package common.listener;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common.constants.FilePath;
import io.appium.java_client.AppiumDriver;
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
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class TestListener extends BaseTest implements ITestListener {
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
        AppiumDriver driver = BaseTest.getDriver();
        if (driver != null) {
            try {
                failedScreenshotPath = takeScreenShot(driver, result);
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

    public static String takeScreenShot(AppiumDriver driver, ITestResult result) throws Exception {
        String encodedBase64 = null;
        FileInputStream fileInputStream = null;
        try {
            final String testMethodName = result.getName();
            final String screenShotName = testMethodName + ".png";
            final String screenshotsDirectoryPath = FilePath.REAL_TEST_SCREENSHOT_DIR;

//            File targetFileDir = new File(screenshotsDirectoryPath);
//            if (!targetFileDir.exists()) {
//                targetFileDir.mkdir();
//            }
            //File targetFile = new File(screenshotsDirectoryPath, screenShotName);
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //FileUtils.copyFile(screenshotFile, targetFile);

            fileInputStream = new FileInputStream(screenshotFile);
            byte[] bytes = new byte[(int) screenshotFile.length()];
            fileInputStream.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encode((bytes)));

        } catch (Exception e) {
            System.out.println("An exception occured while taking screenshot: " + e.getCause());
            throw e;
        }
        fileInputStream.close();
        return encodedBase64;

    }
    public static synchronized String takeScreenShot() throws Exception {
        String encodedBase64 = null;
        FileInputStream fileInputStream = null;
        WebDriver driver = BaseTest.getDriver();
        try {
            Date now = new Date();
            String screenshotName = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH).format(now);

            final String screenShotName = screenshotName + ".png";
            final String screenshotsDirectoryPath = FilePath.REAL_TEST_SCREENSHOT_DIR;

//            File targetFileDir = new File(screenshotsDirectoryPath);
//            if (!targetFileDir.exists()) {
//                targetFileDir.mkdir();
//            }
            // File targetFile = new File(screenshotsDirectoryPath, screenShotName);
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // FileUtils.copyFile(screenshotFile, targetFile);

            fileInputStream = new FileInputStream(screenshotFile);
            byte[] bytes = new byte[(int) screenshotFile.length()];
            fileInputStream.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encode((bytes)));

        } catch (Exception e) {
            System.out.println("An exception occured while taking screenshot: " + e.getCause());
            throw e;
        }
        fileInputStream.close();
        return encodedBase64;
    }
}
