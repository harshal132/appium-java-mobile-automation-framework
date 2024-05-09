package tests.moduleone;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import pages.BasePage;
import tests.BaseTest;

import java.net.MalformedURLException;

public class SimpleTest extends BaseTest {
    @Test
    public void executeSimpleTest() throws MalformedURLException {
        BasePage basePage = new BasePage(BaseTest.getDriver());
        System.out.println("Test Execution started");
        BasePage.logTestStep("Test Execution Started");

        AppiumDriver browserDriver = basePage.launchAnotherApp("com.android.chrome", "com.google.android.apps.chrome.Main");

        //
        // Perform Actions On Browser
        //

        // Relaunch Main Application
        basePage.launchApp();

    }
}
