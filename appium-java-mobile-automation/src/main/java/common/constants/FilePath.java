package common.constants;

import java.io.File;

public class FilePath {

    public static final String SERVER_DATA = "";
    //////////////////////////// REAL PATH FOR MY TEST FRAMEWORK ////////////////////////////
    private static final String REAL_RESOURCES_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator;
    public static final String REAL_DATA_FILE_PATH = REAL_RESOURCES_PATH + "data" + File.separator;

    public static final String REAL_APP_DATA_FILE_PATH = REAL_DATA_FILE_PATH + "application" + File.separator + "app-data.yml";
    public static final String REAL_CAPABILITIES_JSON_FILE_PATH = REAL_DATA_FILE_PATH + "application" + File.separator + "capabilities.json";

    public static final String REAL_REPORTS_FILE_PATH = REAL_RESOURCES_PATH + "reports" + File.separator;


    public static final String REAL_TEST_SCREENSHOT_DIR = REAL_REPORTS_FILE_PATH + "screenshots" + File.separator;

    public static final String TEST_DATA_MODULE_ONE = REAL_DATA_FILE_PATH + "moduleone" + File.separator + "moduleone.yml";
    public static final String TEST_DATA_MODULE_TWO = REAL_DATA_FILE_PATH + "moduletwo" + File.separator + "moduletwo.yml";

    public static final String REAL_LOCATORS_DIRECTORY = REAL_RESOURCES_PATH + "locators" + File.separator;
    public static final String REAL_LOCATORS_MODULE_ONE_DIRECTORY = REAL_LOCATORS_DIRECTORY + "moduleone"+ File.separator;

    public static final String REAL_LOCATORS_MODULE_ONE_PAGE_ONE = REAL_LOCATORS_MODULE_ONE_DIRECTORY + "moduleonepageone.yml";
    public FilePath() {

    }
}
