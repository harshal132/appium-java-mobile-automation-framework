package tests;

import com.aventstack.extentreports.MediaEntityBuilder;
import common.constants.*;
import common.listener.TestListener;
import config.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.DataLoader;
import utils.ExtentTestManager;
import java.lang.reflect.Method;

@Listeners(TestListener.class)
public class BaseTest {
    protected final String applicationData = (FilePath.REAL_APP_DATA_FILE_PATH);
    private static final ThreadLocal<AppiumDriver> threadLocalDriver = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> loggedInState = null;
    public static Environment testEnvType;
    public static Platform platformName;
    public static DriverType driverType;
    public static DeviceType deviceType;
    public static String wdaPortNumber;
    private static String appiumServerUrl;

    @Parameters({ "EnvType", "DriverType"})
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(@Optional("qa") String EnvType, @Optional("local") String testDriverType) {
            testEnvType = Environment.get(EnvType);
            driverType = DriverType.get(testDriverType);
            System.out.println("Suite running on environment: " + EnvType);
            System.out.println("Suite running on driver type: " + testDriverType);
    }

    @Parameters({ "Platform", "Emulator", "Port", "WdaPort" })
    @BeforeTest(alwaysRun = true)
    public void setUpAppium(@Optional("ios") String platform, @Optional("real") String testDeviceType, @Optional("4723") String portNumber, @Optional("8100") String wdaPort, Method method) throws Exception {
        platformName = Platform.get(platform);
        deviceType = DeviceType.get(testDeviceType);
        System.out.println("Test running on platform: " + platform);
        System.out.println("Test running on device type: " + testDeviceType);
        appiumServerUrl = "http://" + DataLoader.getAppData(applicationData, "appiumLocalIp") + ":" + portNumber + "/";
        wdaPortNumber = wdaPort;

        AppiumDriver driver = MobileDriverFactory.getAppiumDriver(testEnvType, driverType, platformName, deviceType, appiumServerUrl, wdaPortNumber);
        setDriver(driver);
    }

    public static void setDriver(AppiumDriver driver) {
        threadLocalDriver.set(driver);
    }

    public static AppiumDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void removeDriver() {
        threadLocalDriver.remove();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (getDriver() != null) {
            if(!result.isSuccess()) {
                try {
                    ExtentTestManager.getTest().fail(result.getName() + " Test Failed due to exception: " + result.getThrowable() + "<br>Final screenshot: ", MediaEntityBuilder.createScreenCaptureFromBase64String(TestListener.takeScreenShot()).build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result.getName() + " Test has failed");
                System.out.println("After Test: " + result.getName() + " with Thread ID: " + Thread.currentThread().getId());
            }
        }
    }

    @AfterTest(alwaysRun = true)
    public void stopAppium() {
        getDriver().quit();
        removeDriver();
    }

    public static boolean isQaTest() {
        try {
            boolean flag;
            flag = testEnvType.isQaEnv();
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get QA environment status");
            System.out.println(e.toString());
            throw e;
        }
    }

    public static boolean isProdTest() {
        try {
            boolean flag;
            flag = testEnvType.isProdEnv();
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get Prod environment status");
            System.out.println(e.toString());
            throw e;
        }
    }

    public static boolean isIosTest() {
        try {
            boolean flag;
            flag = platformName.isIos();
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get ios platform status");
            System.out.println(e.toString());
            throw e;
        }
    }

    public static boolean isAndroidTest() {
        try {
            boolean flag;
            flag = platformName.isAndroid();
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get ios platform status");
            System.out.println(e.toString());
            throw e;
        }
    }
}
