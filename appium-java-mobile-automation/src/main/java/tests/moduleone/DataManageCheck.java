package tests.moduleone;

import common.constants.FilePath;
import org.testng.annotations.Test;
import pages.BasePage;
import tests.BaseTest;
import utils.DataLoader;
import utils.TestDataProvider;

import java.net.MalformedURLException;
import java.util.Map;

public class DataManageCheck extends BaseTest {
    @Test (dataProvider = "module1", dataProviderClass = TestDataProvider.class)
    public void executeDataManagementTest(Map<String, Object> testData) throws MalformedURLException {
        BasePage basePage = new BasePage(BaseTest.getDriver());
        System.out.println("Test Execution started");

        for(Map.Entry<String, Object> entry: testData.entrySet()){
            System.out.println(entry.getKey()+": "+entry.getValue());
        }
        System.out.println(DataLoader.getLocator(FilePath.REAL_LOCATORS_MODULE_ONE_PAGE_ONE,"loginButton"));
    }
}
