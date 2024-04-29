package tests;

import com.aventstack.extentreports.MediaEntityBuilder;
import common.constants.*;
import common.listener.TestListner;
import config.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.DataLoader;
import utils.ExtentTestManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseTest {
    protected final String applicationData = (FilePath.REAL_APP_DATA_FILE_PATH);
    protected final String serverData = (FilePath.SERVER_DATA);
    private static final ThreadLocal<AppiumDriver> threadLocalDriver = new ThreadLocal<>();
    public static Environment testEnvType;
    public static Platform platformName;
    public static DriverType driverType;
    public static DeviceType deviceType;
    public static String wdaPortNumber;
    private static String appiumServerUrl;

    /**
     * Will be run before suite to clear reports and logs folder
     * @param EnvType String
     * @param testDriverType String
     */
    @Parameters({ "EnvType", "DriverType"})
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(@Optional("qa") String EnvType, @Optional("local") String testDriverType) {
        try {
            final String reportsPath = FilePath.REAL_REPORTS_FILE_PATH;
            if (Files.exists(Paths.get(reportsPath))) {
                FileUtils.cleanDirectory(new File(reportsPath));
            }
            testEnvType = Environment.get(EnvType);
            driverType = DriverType.get(testDriverType);
            System.out.println("Suite running on environment: " + EnvType);
            System.out.println("Suite running on driver type: " + testDriverType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Will be run before every test in xml to start appium server
     *
     * @param platform String
     * @param testDeviceType String
     * @param portNumber String
     */
    @Parameters({ "Platform", "Emulator", "Port", "WdaPort" })
    @BeforeTest(alwaysRun = true)
    public void setUpAppium(@Optional("ios") String platform, @Optional("real") String testDeviceType, @Optional("4723") String portNumber, @Optional("8100") String wdaPort) throws Exception {
        platformName = Platform.get(platform);
        deviceType = DeviceType.get(testDeviceType);
        System.out.println("Test running on platform: " + platform);
        System.out.println("Test running on device type: " + testDeviceType);
        appiumServerUrl = "http://" + DataLoader.getAppData(applicationData, "appiumLocalIp") + ":" + portNumber + "/";
        wdaPortNumber = wdaPort;
    }

    /**
     * Will be run before every method to initiate appium driver
     *
     */
    @BeforeMethod(alwaysRun = true)
    public void setUpDriver(Method method) throws Exception {
        AppiumDriver driver = MobileDriverFactory.getAppiumDriver(testEnvType, driverType, platformName, deviceType, appiumServerUrl, wdaPortNumber);
        setDriver(driver);
        System.out.println("Before Test: " + method.getName() + " with Thread ID: " + Thread.currentThread().getId());
    }

    /**
     * set thread-safe driver
     */
    public static void setDriver(AppiumDriver driver) {
        threadLocalDriver.set(driver);
    }

    /**
     * get thread-safe driver
     */
    public static AppiumDriver getDriver() {
        return threadLocalDriver.get();
    }

    /**
     * remove thread-safe driver
     */
    public static void removeDriver() {
        threadLocalDriver.remove();
    }

    /**
     * Will be run after every method in xml to generate failed screenshots after every failed test and close driver
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (getDriver() != null) {
            if(!result.isSuccess()) {
                try {
                    ExtentTestManager.getTest().fail(result.getName() + " Test Failed due to exception: " + result.getThrowable() + "<br>Final screenshot: ", MediaEntityBuilder.createScreenCaptureFromBase64String(TestListner.takeScreenShot()).build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result.getName() + " Test has failed");
            }
            getDriver().quit();
            System.out.println("After Test: " + result.getName() + " with Thread ID: " + Thread.currentThread().getId());
            removeDriver();
        }
        //		}
    }

    /**
     * Will be run after every test in xml to stop appium server
     */
    @AfterTest(alwaysRun = true)
    public void stopAppium() {
        //		AppiumServerManager.stopAppiumServer();
        //		AppiumServerProcessManager.stopServerProcess();
    }

    /**
     * returns boolean as per test is running on Qa environment
     *
     */
    public static boolean isQaTest() {
        try {
            boolean flag;
            flag = (testEnvType.isQaEnv()) ? true : false;
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get QA environment status");
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * returns boolean as per test is running on Prod environment
     *
     */
    public static boolean isProdTest() {
        try {
            boolean flag;
            flag = (testEnvType.isProdEnv()) ? true : false;
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get Prod environment status");
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * returns boolean as per test is running on ios platform
     *
     */
    public static boolean isIosTest() {
        try {
            boolean flag;
            flag = (platformName.isIos()) ? true : false;
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get ios platform status");
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * returns boolean as per test is running on android platform
     */
    public static boolean isAndroidTest() {
        try {
            boolean flag;
            flag = (platformName.isAndroid()) ? true : false;
            return flag;
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get ios platform status");
            System.out.println(e.toString());
            throw e;
        }
    }
}
