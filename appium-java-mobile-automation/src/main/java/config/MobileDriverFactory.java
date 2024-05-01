package config;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

import common.constants.*;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import utils.JsonFileReader;
import utils.capabilities.Emulator;
import utils.capabilities.Real;

import static utils.DataLoader.getAppData;

public class MobileDriverFactory {
    private static final String capabilitiesJson = (FilePath.REAL_CAPABILITIES_JSON_FILE_PATH);
    public static DesiredCapabilities capabilities;

    public static AppiumDriver getAppiumDriver(Environment env, DriverType driverType, Platform platformName, DeviceType deviceType, String serverUrl, String wdaPortNumber) throws Exception {

        AppiumDriver driver = null;
        capabilities = new DesiredCapabilities();
        if (driverType != null && platformName != null) {
            if (driverType.isRemoteDriver() || driverType.isLocalDriver()) {
                if (platformName.isAndroid()) {
                    capabilities = getAndroidCaps(deviceType, env);
                    driver = driverType.isRemoteDriver() ? getRemoteDriverInstance(capabilities, env)
                            : new AndroidDriver(new URL(serverUrl), capabilities);
                } else if (platformName.isIos()) {
                    capabilities = getIosCaps(deviceType, env, wdaPortNumber);
                    driver = driverType.isRemoteDriver() ? getRemoteDriverInstance(capabilities, env)
                            : new IOSDriver(new URL(serverUrl), capabilities);
                } else
                    throw new RuntimeException(
                            "Platform name is not ios | android: "
                                    + platformName);
            } else
                throw new RuntimeException("Driver type is not remote or local: " + driverType);
        } else if (driverType == null)
            throw new RuntimeException("Driver type is null");
        else if (platformName == null)
            throw new RuntimeException("Platform name is null");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Objects.requireNonNull(getAppData(FilePath.REAL_APP_DATA_FILE_PATH, "implicitWaitTime")))));
        return driver;
    }

    private static AppiumDriver getRemoteDriverInstance(Capabilities capabilities, Environment env) throws MalformedURLException {

        AppiumDriver driver = null;
        try {

            driver = (AppiumDriver) new RemoteWebDriver(new URL(Objects.requireNonNull(getAppData(FilePath.REAL_APP_DATA_FILE_PATH, "remoteHubUrl"))), capabilities);
            Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
            System.out.println("Suite running on remote driver platform: " + cap.getBrowserName().toLowerCase());
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw e;
        }
        return driver;
    }

    private static DesiredCapabilities getAndroidCaps(DeviceType deviceType, Environment env) {
        var capabilities = new DesiredCapabilities();
        Emulator emulatorMap = JsonFileReader.getCapabilitiesJson(capabilitiesJson).getAndroid().getEmulator();
        Real realMap = JsonFileReader.getCapabilitiesJson(capabilitiesJson).getAndroid().getReal();
        if(deviceType.isEmulatorDevice()) {
            capabilities.setCapability("appium:deviceName", emulatorMap.getDeviceName());
            capabilities.setCapability("platformName", emulatorMap.getPlatformName());
            if(env.isProdEnv()) {
                capabilities.setCapability("appium:appPackage", emulatorMap.getAppPackageProd());
                capabilities.setCapability("appium:appActivity", emulatorMap.getAppActivityProd());
            }
            else {
                capabilities.setCapability("appium:appPackage", emulatorMap.getAppPackage());
                capabilities.setCapability("appium:appActivity", emulatorMap.getAppActivity());
            }
            capabilities.setCapability("appium:automationName", emulatorMap.getAutomationName());
            capabilities.setCapability("appium:noReset", emulatorMap.getNoReset());
            capabilities.setCapability("appium:autoGrantPermissions", emulatorMap.getautoGrantPermissions());

        }
        else if(deviceType.isRealDevice()) {
            capabilities.setCapability("appium:udid", realMap.getUdid());
            capabilities.setCapability("platformName", realMap.getPlatformName());
            if(env.isProdEnv()) {
                capabilities.setCapability("appium:appPackage", realMap.getAppPackageProd());
                capabilities.setCapability("appium:appActivity", realMap.getAppActivityProd());
            }
            else {
                capabilities.setCapability("appium:appPackage", realMap.getAppPackage());
                capabilities.setCapability("appium:appActivity", realMap.getAppActivity());
            }
            capabilities.setCapability("appium:automationName", realMap.getAutomationName());
            capabilities.setCapability("appium:noReset", realMap.getNoReset());
            capabilities.setCapability("appium:autoGrantPermissions", realMap.getautoGrantPermissions());
        }
        return capabilities;

    }

    private static DesiredCapabilities getIosCaps(DeviceType deviceType, Environment env, String wdaPortNumber) {
        var capabilities = new DesiredCapabilities();
        Emulator emulatorMap = JsonFileReader.getCapabilitiesJson(capabilitiesJson).getIos().getEmulator();
        Real realMap = JsonFileReader.getCapabilitiesJson(capabilitiesJson).getIos().getReal();
        if(deviceType.isEmulatorDevice()) {
            capabilities.setCapability("appium:deviceName", emulatorMap.getDeviceName());
            capabilities.setCapability("platformName", emulatorMap.getPlatformName());

            if(env.isProdEnv()) {
                capabilities.setCapability("appium:bundleId", emulatorMap.getBundleIdProd());
            }
            else {
                capabilities.setCapability("appium:bundleId", emulatorMap.getBundleId());
            }
            capabilities.setCapability("appium:automationName", emulatorMap.getAutomationName());
            capabilities.setCapability("appium:noReset", emulatorMap.getNoReset());
            capabilities.setCapability("appium:wdaLocalPort", wdaPortNumber);
            capabilities.setCapability("appium:autoAcceptAlerts", emulatorMap.getautoGrantPermissions());

        }
        else if(deviceType.isRealDevice()) {
            capabilities.setCapability("platformName", realMap.getPlatformName());
            capabilities.setCapability("appium:udid", realMap.getUdid());
            if(env.isProdEnv()) {
                capabilities.setCapability("appium:bundleId", realMap.getBundleIdProd());
            }
            else {
                capabilities.setCapability("appium:bundleId", realMap.getBundleId());
            }
            capabilities.setCapability("appium:automationName", realMap.getAutomationName());
            capabilities.setCapability("appium:noReset", realMap.getNoReset());
            capabilities.setCapability("appium:wdaLocalPort", wdaPortNumber);
            capabilities.setCapability("appium:autoAcceptAlerts", realMap.getautoGrantPermissions());
        }
        return capabilities;

    }
}
