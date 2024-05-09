package pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;
import common.constants.FilePath;
import config.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;
import utils.DataLoader;
import utils.ExtentTestManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasePage {
    protected AppiumDriver driver;
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }

    protected WebElement getElement(By locator) {
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            System.out.println("WebElement found");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get Element"+ e);
            throw e;
        }
        return element;
    }

    protected List<WebElement> getElements(By locator) {
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(locator);
            System.out.println("WebElements found");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get elements"+ e);
            throw e;
        }
        return elements;
    }

    protected void clearText(By locator) {
        clearText(getElement(locator));
    }

    protected void clearText(WebElement element) {
        try {
            element.clear();
            System.out.println("WebElement text cleared");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not clear text"+ e);
            throw e;
        }
    }

    protected void enterText(String text, By locator) {
        enterText(text,getElement(locator));
    }

    protected void enterKeybordKeys(By locator, CharSequence... keysToSend) {
        enterKeybordKeys(getElement(locator), keysToSend);
    }

    protected void enterKeybordKeys(WebElement element, CharSequence... keysToSend) {
        try {
            element.sendKeys(keysToSend);
            System.out.println("Keys entered on element");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not enter keys"+ e);
            throw e;
        }
    }

    protected void enterTextWithoutClear(String text, By locator) {
        enterTextWithoutClear(text, getElement(locator));
    }

    protected void enterTextWithoutClear(String text, WebElement element) {
        try {
            element.sendKeys(text);
            System.out.println("Text entered on element: " + text);
        } catch (Exception e) {
            System.out.println("Exception reached: Could not enter text"+ e);
            throw e;
        }
    }

    protected void enterText(String text, WebElement element) {
        try {
            clearText(element);
            element.sendKeys(text);
            System.out.println("Text entered on element: " + text);
        } catch (Exception e) {
            System.out.println("Exception reached: Could not enter text"+ e);
            throw e;
        }
    }

    protected void enterTextCharByChar(String text, By locator) {
        try {
            WebElement element = getElement(locator);
            clearText(element);
            text.chars().forEach(ch -> {
                element.sendKeys(Character.toString((char) ch));
                hardWait(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"waitTime.vvsmall"));
            });
            System.out.println("Text entered on element char by char: " + text);
        } catch (Exception e) {
            System.out.println("Exception reached: Could not enter text char by char"+ e);
            throw e;
        }
    }

    protected String getText(By locator) {
        return getText(getElement(locator));
    }

    protected String getText(WebElement element) {
        String text = "";
        try {
            text = element.getText();
            System.out.println("Element text returned: " + text);
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get text by element"+ e);
        }
        return text;
    }

    protected void click(By locator) {
        click(getElement(locator));
    }

    protected void click(WebElement element) {
        try {
            element.click();
            System.out.println("Clicked on element");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not click by element"+ e);
            throw e;
        }
    }

    protected boolean isAttributePresent(String attributeName, By locator) {
        return isAttributePresent(attributeName, getElement(locator));
    }

    protected boolean isAttributePresent(String attributeName, WebElement element) {
        Boolean result = false;
        try {
            String value = getAttributeValue(attributeName, element);
            if (value != null){
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Exception reached: Could not verify attribute's presence "+ e);
            throw e;
        }
        return result;
    }

    protected String getAttributeValue(String attributeName, By locator) {
        return getAttributeValue(attributeName, getElement(locator));
    }

    protected String getAttributeValue(String attributeName, WebElement element) {
        String text = "";
        try {
            text = element.getAttribute(attributeName);
            System.out.println("Got attribute: " + attributeName + " value: " + text);
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get attribute"+ e);
            throw e;
        }
        return text;
    }

    protected int getElementsCount(By locator) {
        int count = -1;
        try {
            count = getElements(locator).size();
            System.out.println("Got number of elments count: " + count);
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get elements count"+ e);
            throw e;
        }
        return count;
    }

    protected boolean isDisplayed(boolean shouldBeDisplayed, By locator) {
        try {

            if (getElementsCount(locator) > 0) {
                boolean isDisplayed = getElement(locator).isDisplayed();
                System.out.println("Got isDisplayed value as: " + isDisplayed);
                return shouldBeDisplayed == isDisplayed;
            } else {
                System.out.println("Got element count value as equal to 0");
                if (!shouldBeDisplayed)
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get element is displayed status"+ e);
            throw e;

        }
    }

    protected WebElement waitForElementToBeClickable(By locator, int timeInMilliSeconds) {
        WebElement element = null;
        try {
            element = new WebDriverWait(driver, Duration.ofMillis(timeInMilliSeconds))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            System.out.println("Waited for element to be clickable");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for element to be clickable"+ e);
            throw e;
        }
        return element;
    }

    protected WebElement waitForElementToBeClickable(WebElement element, int timeInMilliSeconds) {
        try {
            element = new WebDriverWait(driver, Duration.ofMillis(timeInMilliSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
            System.out.println("Waited for element to be clickable");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for element to be clickable"+ e);
            throw e;
        }
        return element;
    }

    protected WebElement waitForElementToBeVisible(By locator, int timeInMilliSeconds) {
        WebElement element = null;
        try {
            element = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(timeInMilliSeconds))
                    .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for element to be visible"+ e);
            throw e;
        }
        return element;
    }

    protected List<WebElement> waitForElementsToBeVisible(By locator, int timeInMilliSeconds) {
        List<WebElement> elements = null;
        try {
            elements = new WebDriverWait(driver, Duration.ofMillis(timeInMilliSeconds))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            System.out.println("Waited for element to be clickable");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for element to be clickable"+ e);
            throw e;
        }
        return elements;
    }

    protected boolean waitForElementToBeInVisible(By locator, int timeInMilliSeconds) {
        boolean elementInvisible = false;
        try {
            elementInvisible = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(timeInMilliSeconds))
                    .pollingEvery(Duration.ofMillis(200)).ignoring(TimeoutException.class)
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for element to be invisible"+ e);
            throw e;
        }
        return elementInvisible;
    }

    protected WebElement waitForElementToBecomeUnstale(By locator) {
        WebElement element = null;
        try {
            element = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(10000))
                    .pollingEvery(Duration.ofMillis(500)).ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class)
                    .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for element to be stale"+ e);
            throw e;
        }
        return element;
    }

    public void waitForScreenToLoad() {
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(10000));
            System.out.println("Waited for screen to load");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not wait for screen to be loaded"+ e);
        }
    }

    protected Map <String, Object> getServerStatus() {
        Map <String, Object> serverVersionDetails= null;
        try {
            System.out.println("got server status");
            serverVersionDetails =  driver.getStatus();
        } catch (Exception e) {
            System.out.println("Exception reached: Could not get server status"+ e);
            throw e;
        }
        return serverVersionDetails;
    }

    public ImmutableMap<String, String> getAppId() {
        if(BaseTest.isAndroidTest())
            return ImmutableMap.of("appId", (String) MobileDriverFactory.capabilities.getCapability("appium:appPackage"));
        else
            return ImmutableMap.of("bundleId", (String) MobileDriverFactory.capabilities.getCapability("appium:bundleId"));
    }

    public void jsIosResetLocationPermission() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: resetPermission", ImmutableMap.of("service", "location"));
            System.out.println("app terminated and cleared cache");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not terminate app"+ e);
            throw e;
        }
    }

    public void jsTerminateAppClearingCache() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: terminateApp", getAppId());
            System.out.println("app terminated and cleared cache");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not terminate app"+ e);
            throw e;
        }
    }

    public void jsPutAppInBackground() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: backgroundApp");
            System.out.println("app put in background");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not put app in background"+ e);
            throw e;
        };
    }

    public void jsActivateAppithoutClearingCache() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: activateApp", getAppId());
            System.out.println("activated app without clearing cache");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not activate app"+ e);
            throw e;
        }
    }

    public void jsCloseAndlaunchAppClearingCache() {
        try {
            jsTerminateAppClearingCache();
            jsActivateAppithoutClearingCache();
            System.out.println("clse and launch app after clearing cache");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not close and launch app"+ e);
            throw e;
        }
    }

    public void hardWait(String waitTime){
        try {
            Thread.sleep(Integer.parseInt(waitTime));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void jsLaunchAppWithoutClearingCache() {
        try {
            hardWait(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"waitTime.small"));
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: launchApp", getAppId());
            hardWait(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"waitTime.small"));
            System.out.println("launched app without clearing cache");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not launch app"+ e);
            throw e;
        }
    }

    public boolean jsIsAppInstalled() {
        boolean isInstalled = false;
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            isInstalled = (boolean) jsDriver.executeScript("mobile: isAppInstalled", getAppId());
            System.out.println("got is app installed status");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not check if app is installed"+ e);
            throw e;
        }
        return isInstalled;
    }

    public void jsHideKeyboard() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: hideKeyboard");
            System.out.println("hidden keyboard");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not hide keyboard"+ e);
            throw e;
        };
    }

    public boolean jsIsKeybordShown() {
        boolean isKeyboardShown;
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            isKeyboardShown = (boolean)jsDriver.executeScript("mobile: isKeyboardShown");
            System.out.println("got value if keyboard displayed");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not check if keyborad displayed"+ e);
            throw e;
        };
        return isKeyboardShown;
    }

    public enum Direction {
        RIGHT,
        LEFT,
        DOWN,
        UP
    }

    public void jsIosScrollTillEnd(int numberOfTimesToScroll, Direction directionToReach) {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            int counter = 1;
            while(counter <= numberOfTimesToScroll) {
                jsDriver.executeScript("mobile: scroll", ImmutableMap.of("direction", directionToReach.toString().toLowerCase()));
                counter++;
            }
            System.out.println("performed scroll");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not scroll"+ e);
            throw e;
        }
    }

    public void scrollByCoordinates(int startx, int starty, int endx, int endy) {
        try {
            Sequence scroll = new Sequence(finger, 0);
            scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startx, starty));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), endx, endy));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(scroll));
            hardWait(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"waitTime.small"));
            System.out.println("performed scroll/swipe by coordinates");
        } catch(Exception e) {
            System.out.println("Exception reached: Could not scroll/swipe by coordinates"+ e);
            throw e;
        }
    }

    private HashMap<String, Integer> getScreenScrollPositions(Direction directionToReach) {
        HashMap<String, Integer> coordMap = new HashMap<>();
        int screenWidth, screenHeight;
        screenWidth = driver.manage().window().getSize().getWidth();
        screenHeight = driver.manage().window().getSize().getHeight();
        switch (directionToReach) {
            case UP -> {
                coordMap.put("startx", screenWidth / 2);
                coordMap.put("starty", (int) (screenHeight * 0.12));
                coordMap.put("endx", screenWidth / 2);
                coordMap.put("endy", (int) (screenHeight * 0.85));
            }
            case DOWN -> {
                coordMap.put("startx", screenWidth / 2);
                coordMap.put("starty", (int) (screenHeight * 0.85));
                coordMap.put("endx", screenWidth / 2);
                coordMap.put("endy", (int) (screenHeight * 0.1));
            }
            case LEFT -> {
                coordMap.put("startx", (int) (screenWidth * 0.2));
                coordMap.put("starty", screenHeight / 2);
                coordMap.put("endx", (int) (screenWidth * 0.8));
                coordMap.put("endy", screenHeight / 2);
            }
            case RIGHT -> {
                coordMap.put("startx", (int) (screenWidth * 0.8));
                coordMap.put("starty", screenHeight / 2);
                coordMap.put("endx", (int) (screenWidth * 0.2));
                coordMap.put("endy", screenHeight / 2);
            }
            default -> throw new IllegalArgumentException("Invalid swipe direction: " + directionToReach);
        }
        return coordMap;
    }

    public void scrollTillEnd(int numberOfTimesToScroll, Direction directionToReach) {
        int startx, endx, starty, endy;
        try {
            startx = getScreenScrollPositions(directionToReach).get("startx");
            starty = getScreenScrollPositions(directionToReach).get("starty");
            endx = getScreenScrollPositions(directionToReach).get("endx");
            endy = getScreenScrollPositions(directionToReach).get("endy");
            int timeout = 1;
            while (timeout <= numberOfTimesToScroll) {
                scrollByCoordinates(startx, starty, endx, endy);
                timeout++;
            }
            System.out.println("performed scroll for element into view");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not scroll for element"+ e);
            throw e;
        }
    }

    public void scrollTillElementIntoView(By locator, int numberOfTimesToScroll, Direction directionToReach) {
        int startx, endx, starty, endy;
        startx = getScreenScrollPositions(directionToReach).get("startx");
        starty = getScreenScrollPositions(directionToReach).get("starty");
        endx = getScreenScrollPositions(directionToReach).get("endx");
        endy = getScreenScrollPositions(directionToReach).get("endy");
        try {
            int timeout = 1;
            while (timeout <= numberOfTimesToScroll  && isDisplayed(false, locator)) {
                scrollByCoordinates(startx, starty, endx, endy);
                timeout++;
            }
            System.out.println("performed scroll for element into view");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not scroll for element"+ e);
            throw e;
        }
    }

    public void swipeOnElement(Direction directionToReach, WebElement element) {
        try {
            int startx, starty, endx, endy;
            int elementX = element.getRect().x;
            int elementY = element.getRect().y;
            int elementHeight = element.getSize().height;
            int elementWidth = element.getSize().width;

            switch (directionToReach) {
                case LEFT -> {
                    startx = elementX + (elementWidth / 5);
                    starty = elementY + (elementHeight / 2);
                    endx = elementX + (elementWidth * 3 / 4);
                    endy = starty;
                }
                case RIGHT -> {
                    startx = elementX + (elementWidth * 3 / 4);
                    starty = elementY + (elementHeight / 2);
                    endx = elementX + (elementWidth / 5);
                    endy = starty;
                }
                case UP -> {
                    startx = elementX + (elementWidth / 2);
                    starty = elementY + (elementHeight / 4);
                    endx = startx;
                    endy = elementY + (elementHeight * 3 / 4);
                }
                case DOWN -> {
                    startx = elementX + (elementWidth / 2);
                    starty = elementY + (elementHeight * 3 / 4);
                    endx = startx;
                    endy = elementY + (elementHeight / 4);
                }
                default -> throw new IllegalArgumentException("Invalid swipe direction: " + directionToReach);
            }
            scrollByCoordinates(startx, starty, endx, endy);
            System.out.println("performed swipe on element");
        } catch(Exception e) {
            System.out.println("Exception reached: Could not perform swipe on element"+ e);
            throw e;
        }
    }

    protected void swipeOnElement(Direction directionToReach, By locator) {
        swipeOnElement(directionToReach, getElement(locator));
    }

    public void tapByCoordinates(int x, int y) {
        try {
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(tap));
            System.out.println("performed tap by coordinates");
        } catch(Exception e) {
            System.out.println("Exception reached: Could not tap by coordinates"+ e);
            throw e;
        }
    }

    public void tapOnMidOfScreen() {
        try {
            int x = driver.manage().window().getSize().getWidth() / 2;
            int y = driver.manage().window().getSize().getHeight() / 2;
            tapByCoordinates(x, y);
            System.out.println("performed tap on screen");
        } catch(Exception e) {
            System.out.println("Exception reached: Could not tap on screen"+ e);
            throw e;
        }
    }

    public void tapOnElement(WebElement element) {
        try {
            Point sourceLocation = element.getLocation();
            Dimension sourceSize = element.getSize();
            int centerX = sourceLocation.getX() + sourceSize.getWidth() / 2;
            int centerY = sourceLocation.getY() + sourceSize.getHeight() / 2;
            tapByCoordinates(centerX, centerY);
            System.out.println("performed tap on element");
        } catch(Exception e) {
            System.out.println("Exception reached: Could not tap on element"+ e);
            throw e;
        }
    }

    protected void tapOnElement(By locator) {
        tapOnElement(getElement(locator));
    }

    public void doubleTapOnElement(WebElement element) {
        try {
            Point sourceLocation = element.getLocation();
            Dimension sourceSize = element.getSize();
            int centerX = sourceLocation.getX() + sourceSize.getWidth() / 2;
            int centerY = sourceLocation.getY() + sourceSize.getHeight() / 2;

            Sequence doubleTap = new Sequence(finger, 0);
            doubleTap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
            doubleTap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            doubleTap.addAction(new Pause(finger, Duration.ofMillis(100)));
            doubleTap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            doubleTap.addAction(new Pause(finger, Duration.ofMillis(50)));
            doubleTap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            doubleTap.addAction(new Pause(finger, Duration.ofMillis(100)));
            doubleTap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(doubleTap));
            System.out.println("performed double tap on element");
        } catch(Exception e) {
            System.out.println("Exception reached: Could not perform double tap on element"+ e);
            throw e;
        }
    }

    protected void doubleTapOnElement(By locator) {
        doubleTapOnElement(getElement(locator));
    }


    protected void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
        try {
            Point sourceLocation = sourceElement.getLocation();
            Dimension sourceSize = sourceElement.getSize();
            int centerX = sourceLocation.getX() + sourceSize.getWidth() / 2;
            int centerY = sourceLocation.getY() + sourceSize.getHeight() / 2;

            Point targetLocation = destinationElement.getLocation();
            Dimension targetSize = destinationElement.getSize();
            int centerX2 = targetLocation.getX() + targetSize.getWidth() / 2;
            int centerY2 = targetLocation.getY() + targetSize.getHeight() / 2;

            Sequence dragNDrop = new Sequence(finger, 1);
            dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), centerX, centerY));
            dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(700),
                    PointerInput.Origin.viewport(),centerX2, centerY2));
            dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(dragNDrop));
            System.out.println("Dragged source element to destination element");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not drag source element to destination element"+ e);
            throw e;
        }
    }

    protected void dragAndDrop(By sourceLocator, By destinationLocator) {
        dragAndDrop(getElement(sourceLocator), getElement(destinationLocator));
    }

    public void getPageSource() {
        try {
            System.out.println(driver.getPageSource());
            hardWait(DataLoader.getAppData(FilePath.REAL_APP_DATA_FILE_PATH,"waitTime.small"));
        } catch(Exception e) {
            System.out.println("Exception reached: Could not get page source"+ e);
            throw e;
        }
    }

    public void tapDeviceHomeButton() {
        try {
            if(BaseTest.isAndroidTest())
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
            else {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                jsDriver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
            }
            System.out.println("tapped on device home");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not hit home"+ e);
            throw e;
        }
    }

    public void pressButton() {
        try {
            if(BaseTest.isAndroidTest())
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
            else {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                jsDriver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
            }
            System.out.println("tapped on device home");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not hit home"+ e);
            throw e;
        }
    }

    public List<String> getStringIntoList(String stringToChange, String delimeter) {
        List<String> list;
        try {
            list = Stream.of(stringToChange.split(delimeter, -1)).collect(Collectors.toList());
        } catch(Exception e) {
            System.out.println("Exception reached: Could not split string to list " + stringToChange + " with delimeter " + delimeter + e.getMessage());
            throw e;
        }
        return list;
    }

    public Map<String, String> getListIntoMap(List<String> listToChange, String delimeter) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            for (String string : listToChange)
                map.put(string.split(delimeter)[0], string.split(delimeter)[1]);
        } catch(Exception e) {
            System.out.println("Exception reached: Could not split list to map " + listToChange + " with delimeter " + delimeter +" "+ e);
            throw e;
        }
        return map;
    }

    // Math operations

    public double truncateUptoParticularDecimalPoint(double value, int decimalpoint)
    {
        double updatedValue=0.0;
        try {
            updatedValue = value * Math.pow(10, decimalpoint);
            updatedValue = Math.floor(updatedValue);
            updatedValue = updatedValue / Math.pow(10, decimalpoint);

        } catch (Exception e) {
            System.out.println("Exception reached: Could not truncate value upto decimal point as expected due to - "+ e);
        }
        return updatedValue;
    }

    // Regular expression operations
    public String getTextMatchingWithPatternUsingRegex(String patternToFindMatch, String textToBeMatched) {
        try {
            Pattern pattern = Pattern.compile(patternToFindMatch);
            Matcher matcher = pattern.matcher(textToBeMatched);
            boolean matchFound = matcher.find();
            if(matchFound)
                return matcher.group(1);

        } catch (Exception e) {
            System.out.println("Failed to get text matching the given pattern due to exception " + e.getMessage());
        }
        return null;
    }

    public boolean validateTextIsMatchingWithPatternUsingRegex(String patternToFindMatch, String textToBeMatched) {
        try {
            Pattern pattern = Pattern.compile(patternToFindMatch);
            Matcher matcher = pattern.matcher(textToBeMatched);
            boolean matchFound = matcher.find();
            if(matchFound)
                return true;

        } catch (Exception e) {
            System.out.println("Failed to validate whether the text is matching the given pattern or not, due to exception " + e.getMessage());
        }
        return false;
    }

    public void swipeScreenVerticallyByPercentage(int startPercentage, int endPercentage){
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * 0.5);
        int startPoint = (int) (size.height * (startPercentage*0.01));
        int endPoint = (int) (size.height * (endPercentage*0.01));
        scrollByCoordinates(anchor,startPoint,anchor,endPoint);
    }

    public void swipeScreenHorizontallyByPercentage(int startPercentage, int endPercentage){
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.height * 0.5);
        int startPoint = (int) (size.width * (startPercentage*0.01));
        int endPoint = (int) (size.width * (endPercentage*0.01));
        scrollByCoordinates(anchor,startPoint,anchor,endPoint);
    }

    public void launchApp(){
        if(BaseTest.isIosTest()){
            ((IOSDriver) driver).activateApp(getAppId().get("bundleId"));
        }
        else{
            ((AndroidDriver) driver).activateApp(getAppId().get("appId"));
        }
    }

    public static void logTestStep(String description) {
        try{
            ExtentTestManager.getTest().log(Status.INFO,description, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot()).build());
        }catch(IOException e){
            System.out.println("Failed to capture screenshot"+e.getMessage());
        }
    }

    public static void logTestStep(String description, By locator) {
        try{
            ExtentTestManager.getTest().log(Status.INFO,description, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshotOfElement(locator)).build());
        }catch(IOException e){
            System.out.println("Failed to capture screenshot"+e.getMessage());
        }
    }

    public static String takeScreenshot() throws IOException {
        File screenshotFile = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.FILE);
        FileInputStream fileInputStream = new FileInputStream(screenshotFile);
        byte[] bytes = new byte[(int) screenshotFile.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        return new String(Base64.getEncoder().encode((bytes)));
    }

    public static String takeScreenshotOfElement(By locator) throws IOException {
        File screenshotFile = ((TakesScreenshot) BaseTest.getDriver().findElement(locator)).getScreenshotAs(OutputType.FILE);
        FileInputStream fileInputStream = new FileInputStream(screenshotFile);
        byte[] bytes = new byte[(int) screenshotFile.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        return new String(Base64.getEncoder().encode((bytes)));
    }

    public AppiumDriver launchAnotherApp(String packageName, String... activityName) throws MalformedURLException {
        AppiumDriver newAppDriver = MobileDriverFactory.getBrowserDriver(BaseTest.getDriver().getCapabilities(), packageName, activityName);
        if(BaseTest.isIosTest()){
            ((IOSDriver) newAppDriver).activateApp(newAppDriver.getCapabilities().getCapability("bundleId").toString());
        }
        else{
            ((AndroidDriver) newAppDriver).activateApp(newAppDriver.getCapabilities().getCapability("appPackage").toString());
        }
        return newAppDriver;
    }
}
