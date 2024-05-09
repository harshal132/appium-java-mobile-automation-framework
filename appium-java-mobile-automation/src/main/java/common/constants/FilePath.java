package common.constants;

import java.io.File;

public class FilePath {
    // Resources Directory
    private static final String REAL_RESOURCES_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator;

    // Main Directories
    public static final String REAL_DATA_DIRECTORY = REAL_RESOURCES_PATH + "data" + File.separator;
    public static final String REAL_LOCATORS_DIRECTORY = REAL_RESOURCES_PATH + "locators" + File.separator;
    public static final String REAL_REPORTS_DIRECTORY = REAL_RESOURCES_PATH + "reports" + File.separator;
    public static final String REAL_SUITES_DIRECTORY = REAL_RESOURCES_PATH + "suites" + File.separator;


    // Sub directories under Data directory
    public static final String REAL_APPLICATION_DIRECTORY = REAL_DATA_DIRECTORY + "application" + File.separator;
    public static final String REAL_APP_DATA_FILE_PATH = REAL_APPLICATION_DIRECTORY + "app-data.yml";
    public static final String REAL_CAPABILITIES_JSON_FILE_PATH = REAL_APPLICATION_DIRECTORY + "capabilities.json";
    public static final String REAL_COMMON_DATA_FILE_PATH = REAL_APPLICATION_DIRECTORY + "common-data.yml";
    public static final String REAL_MODULE_ONE_DATA_DIRECTORY = REAL_DATA_DIRECTORY + "moduleone" + File.separator;
    public static final String TEST_DATA_MODULE_ONE = REAL_MODULE_ONE_DATA_DIRECTORY + "moduleone.yml";
    public static final String REAL_MODULE_TWO_DATA_DIRECTORY = REAL_DATA_DIRECTORY + "moduletwo" + File.separator;
    public static final String TEST_DATA_MODULE_TWO = REAL_MODULE_TWO_DATA_DIRECTORY + "moduletwo.yml";


    // Sub directories under Locators directory
    public static final String REAL_LOCATORS_MODULE_ONE_DIRECTORY = REAL_LOCATORS_DIRECTORY + "moduleone"+ File.separator;
    public static final String REAL_LOCATORS_MODULE_ONE_PAGE_ONE = REAL_LOCATORS_MODULE_ONE_DIRECTORY + "moduleonepageone.yml";
    public static final String REAL_LOCATORS_MODULE_TWO_DIRECTORY = REAL_LOCATORS_DIRECTORY + "moduletwo"+ File.separator;
    public static final String REAL_LOCATORS_MODULE_TWO_PAGE_ONE = REAL_LOCATORS_MODULE_TWO_DIRECTORY + "moduletwopageone.yml";


    // Framework support files
    public static final String REAL_TEST_SCREENSHOT_DIR = REAL_REPORTS_DIRECTORY + "screenshots" + File.separator;
    public FilePath() {

    }
}
