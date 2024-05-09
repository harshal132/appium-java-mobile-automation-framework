package tests.moduleone;

import common.constants.FilePath;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import pages.BasePage;
import tests.BaseTest;
import utils.DataLoader;

import java.net.MalformedURLException;

public class SimpleTest extends BaseTest {
    @Test
    public void executeSimpleTest() throws MalformedURLException {
        BasePage basePage = new BasePage(BaseTest.getDriver());
        System.out.println("Test Execution started");
        //BasePage.logTestStep("Test Execution Started");

        AppiumDriver browserDriver = basePage.launchAnotherApp(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"chromeAppId.android"), DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"chromeAppPackage"));

        //
        // Perform Actions On Browser
        //

        // Relaunch Main Application
        basePage.launchApp();

    }
}
