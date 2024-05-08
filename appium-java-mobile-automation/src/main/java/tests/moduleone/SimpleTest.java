package tests.moduleone;

import org.testng.annotations.Test;
import pages.BasePage;
import tests.BaseTest;

public class SimpleTest extends BaseTest {
    @Test
    public void executeSimpleTest(){
        System.out.println("Test Execution started");
        BasePage.logTestStep("Test Execution Started");
    }
}
