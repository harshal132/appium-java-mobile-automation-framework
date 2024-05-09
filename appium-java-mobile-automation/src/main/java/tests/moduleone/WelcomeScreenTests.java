package tests.moduleone;

import common.listener.SoftAssertListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.moduleone.LoginScreen;
import pages.moduleone.SignupScreen;
import pages.moduleone.WelcomeScreen;
import tests.BaseTest;
import utils.TestDataProvider;

import java.util.Map;

public class WelcomeScreenTests extends BaseTest {

    @Test
    public void verifyWelcomeScreenComponents(){
        SoftAssertListener softAssert = new SoftAssertListener();
        WelcomeScreen welcomeScreen = new WelcomeScreen(BaseTest.getDriver());

        welcomeScreen.logTestStep("Navigated to Welcome screen");
        // Verify Components Visibility
        softAssert.assertTrue(welcomeScreen.isAppLogoDisplayed(),"Verify application logo is displayed");
        softAssert.assertTrue(welcomeScreen.isGetStartedLabelDisplayed(),"Verify get started label is displayed");
        softAssert.assertTrue(welcomeScreen.isGrowYourBusinessDisplayed(),"Verify grow your business is displayed");
        softAssert.assertTrue(welcomeScreen.isAppDescriptionDisplayed(),"Verify application description is displayed");
        softAssert.assertTrue(welcomeScreen.isLoginButtonDisplayed(),"Verify login button is displayed");
        softAssert.assertTrue(welcomeScreen.isSignupButtonDisplayed(),"Verify signup button is displayed");
        softAssert.assertAll();
    }


    @Test(dataProvider = "module1", dataProviderClass = TestDataProvider.class)
    public void verifyComponentLabels(Map<String,Object> testData){
        SoftAssertListener softAssert = new SoftAssertListener();
        WelcomeScreen welcomeScreen = new WelcomeScreen(BaseTest.getDriver());

        welcomeScreen.logTestStep("Navigated to Welcome screen");
        // Verify Components Visibility
        softAssert.assertEquals(welcomeScreen.getGetStartedLabel(),testData.get("getStarted"),"Verified get started label");
        softAssert.assertEquals(welcomeScreen.getGrowYourBusinessLabel(),testData.get("growYourBusiness"), "Verified grow your business label");
        softAssert.assertEquals(welcomeScreen.getAppDescriptionLabel(),testData.get("appDescription"), "Verified app description");
        softAssert.assertEquals(welcomeScreen.getLoginButtonLabel(),testData.get("login"), "Verified login button label");
        softAssert.assertEquals(welcomeScreen.getSignupButtonLabel(),testData.get("signup"),"Verified signup button label");
        softAssert.assertAll();
    }



//    @Test
//    public void verifyNavigationToLoginScreen(){
//        SoftAssertListener softAssert = new SoftAssertListener();
//        WelcomeScreen welcomeScreen = new WelcomeScreen(BaseTest.getDriver());
//        LoginScreen loginScreen = new LoginScreen(BaseTest.getDriver());
//
//        welcomeScreen.logTestStep("Navigated to Welcome screen");
//        // Tap on login button
//        welcomeScreen.tapOnLoginButton();
//        softAssert.assertTrue(loginScreen.isLoginLableDisplayed());
//        if(loginScreen.isLoginLableDisplayed()){
//            // Tap on back button
//            loginScreen.tapOnBackButton();
//        }
//    }

//    @Test
//    public void verifyNavigationToSignupScreen(){
//        SoftAssertListener softAssert = new SoftAssertListener();
//        WelcomeScreen welcomeScreen = new WelcomeScreen(BaseTest.getDriver());
//        SignupScreen signupScreen = new SignupScreen(BaseTest.getDriver());
//
//        welcomeScreen.logTestStep("Navigated to Welcome screen");
//        // Tap on login button
//        welcomeScreen.tapOnLoginButton();
//        softAssert.assertTrue(signupScreen.isCreateNewAccountLabelDisplayed());
//        if(signupScreen.isCreateNewAccountLabelDisplayed()){
//            // Tap on back button
//            signupScreen.tapOnBackButton();
//        }
//    }
}
