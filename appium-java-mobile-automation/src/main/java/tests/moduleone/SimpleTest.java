package tests.moduleone;

import common.constants.FilePath;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import pages.BasePage;
import tests.BaseTest;
import utils.DataLoader;

import java.net.MalformedURLException;
import java.util.Map;

public class SimpleTest extends BaseTest {
    @Test
    public void executeSimpleTest() throws MalformedURLException {
        BasePage basePage = new BasePage(BaseTest.getDriver());
        System.out.println("Test Execution started");
        //BasePage.logTestStep("Test Execution Started");
        AppiumDriver browserDriver;
        if(BaseTest.isIosTest()){
            browserDriver = basePage.launchAnotherApp(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"chromeAppId.android"), DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"chromeAppPackage"));
        }else{
            browserDriver = basePage.launchAnotherApp(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"chromeAppId.ios"));
        }
        //
        // Perform Actions On Browser
        //
        // Relaunch Main Application
        basePage.launchApp();

    }
    @Test
    public void appBackgroundVerification() {
        BasePage basePage = new BasePage(BaseTest.getDriver());
        System.out.println("Test Execution started");

        basePage.hardWait(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"waitTime.vvhigh"));
        // basePage.runAppInBackgroundForTime(Duration.ofSeconds(10)); // -> closes and relaunches app after given duration
        basePage.minimizeCurrentMobileApp();
        // Relaunch Main Application
        basePage.launchApp();

    }
    @Test
    public void androidSettingsCheck() {
        BasePage basePage = new BasePage(BaseTest.getDriver());
        System.out.println("Test Execution started");
        basePage.openAndroidNotifications();
        basePage.toggleAndroidWifiNetwork();
        basePage.toggleAndroidMobileData();
        basePage.toggleAndroidAirPlaneMode();
        for(Map.Entry<String, String> entry: basePage.getAppStringMap().entrySet()){
            System.out.println(entry.getKey() +": "+entry.getValue());
        }
        basePage.uninstallApplication();
    }
}
