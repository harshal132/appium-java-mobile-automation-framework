package pages.moduleone;

import common.constants.FilePath;
import io.appium.java_client.AppiumDriver;
import pages.BasePage;
import utils.DataLoader;

import java.util.Objects;

public class WelcomeScreen extends BasePage {
    private final String welcomeScreenLocators = FilePath.REAL_LOCATORS_MODULE_ONE_WELCOME_SCREEN;
    private final String appDataFilePath = FilePath.REAL_APP_DATA_FILE_PATH;
    public WelcomeScreen(AppiumDriver driver) {
        super(driver);
    }

    public boolean isAppLogoDisplayed(){
        waitForElementToBeVisible(DataLoader.getLocator(welcomeScreenLocators,"appLogo"), Integer.parseInt(Objects.requireNonNull(DataLoader.getAppData(appDataFilePath, "waitTime.medium"))));
        return isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"appLogo"));
    }

    public boolean isGetStartedLabelDisplayed(){
        waitForElementToBeVisible(DataLoader.getLocator(welcomeScreenLocators,"getStartedLabel"), Integer.parseInt(Objects.requireNonNull(DataLoader.getAppData(appDataFilePath, "waitTime.medium"))));
        return isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"getStartedLabel"));
    }

    public String getGetStartedLabel(){
        waitForElementToBeVisible(DataLoader.getLocator(welcomeScreenLocators,"getStartedLabel"), Integer.parseInt(Objects.requireNonNull(DataLoader.getAppData(appDataFilePath, "waitTime.medium"))));
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"getStartedLabel"))){
            return getElement(DataLoader.getLocator(welcomeScreenLocators,"getStartedLabel")).getText();
        }
        return null;
    }

    public boolean isGrowYourBusinessDisplayed(){
        waitForElementToBeVisible(DataLoader.getLocator(welcomeScreenLocators,"growYourBusinessLabel"), Integer.parseInt(Objects.requireNonNull(DataLoader.getAppData(appDataFilePath, "waitTime.medium"))));
        return isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"growYourBusinessLabel"));
    }

    public String getGrowYourBusinessLabel(){
        waitForElementToBeVisible(DataLoader.getLocator(welcomeScreenLocators,"growYourBusinessLabel"), Integer.parseInt(Objects.requireNonNull(DataLoader.getAppData(appDataFilePath, "waitTime.medium"))));
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"growYourBusinessLabel"))){
            return getElement(DataLoader.getLocator(welcomeScreenLocators,"growYourBusinessLabel")).getText();
        }
        return null;
    }

    public boolean isAppDescriptionDisplayed(){
        return isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"appDescription"));
    }

    public String getAppDescriptionLabel(){
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"appDescription"))){
            return getElement(DataLoader.getLocator(welcomeScreenLocators,"appDescription")).getText();
        }
        return null;
    }

    public boolean isLoginButtonDisplayed(){
        return isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"loginButton"));
    }

    public String getLoginButtonLabel(){
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"loginButton"))){
            return getElement(DataLoader.getLocator(welcomeScreenLocators,"loginButton")).getText();
        }
        return null;
    }

    public void tapOnLoginButton(){
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"loginButton"))){
            tapOnElement(DataLoader.getLocator(welcomeScreenLocators,"loginButton"));
        }
    }

    public boolean isSignupButtonDisplayed(){
        return isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"signupButton"));
    }

    public String getSignupButtonLabel(){
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"signupButton"))){
            return getElement(DataLoader.getLocator(welcomeScreenLocators,"signupButton")).getText();
        }
        return null;
    }

    public void tapOnSignupButton(){
        if(isElementDisplayed(DataLoader.getLocator(welcomeScreenLocators,"signupButton"))){
            tapOnElement(DataLoader.getLocator(welcomeScreenLocators,"signupButton"));
        }
    }

}
