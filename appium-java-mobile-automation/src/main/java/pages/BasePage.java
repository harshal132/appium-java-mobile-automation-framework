package pages;

import com.google.common.collect.ImmutableMap;
import common.constants.FilePath;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasePage {
    protected AppiumDriver driver;
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    protected final String applicationData = (FilePath.REAL_APP_DATA_FILE_PATH);

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void openDeepLink(String appScreenName) {
        try {
            driver.get(appScreenName);
            System.out.println("Naviagted to deep link");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not navigate to deep link: "+ e.getMessage());
            throw e;
        }
    }

    public void navigateBack() {
        try {
            driver.navigate().back();
            waitForScreenToLoad();
            System.out.println("Naviagted to back screen ");
        } catch (Exception e) {
            System.out.println("Exception reached: Could not navigate to back screen "+ e.getMessage());
            throw e;
        }
    }

    public void navigateForward() {
        try {
            driver.navigate().forward();;
            waitForScreenToLoad();
            logger.debug("Naviagted to forward screen ");
        } catch (Exception e) {
            logger.error("Exception reached: Could not navigate to forward screen", e);
            throw e;
        }
    }

    protected WebElement getElement(By locator) {
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            logger.debug("WebElement found");
        } catch (Exception e) {
            logger.error("Exception reached: Could not get Element", e);
            throw e;
        }
        return element;
    }

    protected List<WebElement> getElements(By locator) {
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(locator);
            logger.debug("WebElements found");
        } catch (Exception e) {
            logger.error("Exception reached: Could not get elements", e);
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
            logger.debug("WebElement text cleared");
        } catch (Exception e) {
            logger.error("Exception reached: Could not clear text", e);
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
            logger.debug("Keys entered on element");
        } catch (Exception e) {
            logger.error("Exception reached: Could not enter keys", e);
            throw e;
        }
    }

    protected void enterTextWithoutClear(String text, By locator) {
        enterTextWithoutClear(text, getElement(locator));
    }

    protected void enterTextWithoutClear(String text, WebElement element) {
        try {
            element.sendKeys(text);
            logger.debug("Text entered on element: " + text);
        } catch (Exception e) {
            logger.error("Exception reached: Could not enter text", e);
            throw e;
        }
    }


    protected void enterText(String text, WebElement element) {
        try {
            clearText(element);
            element.sendKeys(text);
            logger.debug("Text entered on element: " + text);
        } catch (Exception e) {
            logger.error("Exception reached: Could not enter text", e);
            throw e;
        }
    }

    protected void enterTextCharByChar(String text, By locator) {
        try {
            WebElement element = getElement(locator);
            clearText(element);
            text.chars().forEach(ch -> {
                element.sendKeys(Character.toString((char) ch));
                hardWait(20);
            });
            logger.debug("Text entered on element char by char: " + text);
        } catch (Exception e) {
            logger.error("Exception reached: Could not enter text char by char", e);
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
            logger.debug("Element text returned: " + text);
        } catch (Exception e) {
            logger.error("Exception reached: Could not get text by element", e);
        }
        return text;
    }

    protected void click(By locator) {
        click(getElement(locator));
    }

    protected void click(WebElement element) {
        try {
            element.click();
            logger.debug("Clicked on element");
        } catch (Exception e) {
            logger.error("Exception reached: Could not click by element", e);
            throw e;
        }
    }

    protected String getTagName(By locator) {
        return getTagName(getElement(locator));
    }

    protected String getTagName(WebElement element) {
        String tagName="";
        try {
            element.getTagName();
        } catch (Exception e) {
            logger.error("Exception reached: Could not get tagName", e);
            throw e;
        }
        return tagName;
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
            logger.error("Exception reached: Could not verify attribute's presence ", e);
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
            logger.debug("Got attribute: " + attributeName + " value: " + text);
        } catch (Exception e) {
            logger.error("Exception reached: Could not get attribute", e);
            throw e;
        }
        return text;
    }

    protected int getElementsCount(By locator) {
        int count = -1;
        try {
            count = getElements(locator).size();
            logger.debug("Got number of elments count: " + count);
        } catch (Exception e) {
            logger.error("Exception reached: Could not get elements count", e);
            throw e;
        }
        return count;
    }

    /**
     * checks if element is displayed
     *
     * @param shouldBeDisplayed
     * @param locator
     * @return isDisplayed
     */
    protected boolean isDisplayed(boolean shouldBeDisplayed, By locator) {
        try {

            if (getElementsCount(locator) > 0) {
                boolean isDisplayed = getElement(locator).isDisplayed();
                logger.debug("Got isDisplayed value as: " + isDisplayed);
                return !(shouldBeDisplayed ^ isDisplayed);
            } else {
                logger.debug("Got element count value as equal to 0");
                if (!shouldBeDisplayed)
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            logger.error("Exception reached: Could not get element is displayed status", e);
            throw e;

        }
    }

    /**
     * checks if element is displayed
     *
     * @param shouldBeDisplayed
     * @param locator
     * @return isDisplayed
     */
    protected boolean isDisplayedIfElementOnDom(boolean shouldBeDisplayed, WebElement element) {
        try {
            boolean isDisplayed = element.isDisplayed();
            logger.debug("Got isDisplayed value as: " + isDisplayed);
            return !(shouldBeDisplayed ^ isDisplayed);
        } catch (Exception e) {
            logger.error("Exception reached: Could not get element is displayed status", e);
            throw e;

        }
    }

    /**
     * waits by thread wait
     *
     * @param timeInMilliSeconds
     */
    public void hardWait(long timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);
        } catch (InterruptedException e) {
            logger.warn("Exception reached: Could not add hardwait", e);
        } catch (Exception e) {
            logger.warn("Exception reached: Could not add hardwait", e);
        }
    }

    /**
     * waits till element is clickable
     *
     * @param locator
     * @param timeInMilliSeconds
     * @return webelement
     */
    protected WebElement waitForElementToBeClickable(By locator, int timeInMilliSeconds) {
        WebElement element = null;
        try {
            element = new WebDriverWait(driver, Duration.ofMillis(timeInMilliSeconds))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            logger.debug("Waited for element to be clickable");
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for element to be clickable", e);
            throw e;
        }
        return element;
    }

    /**
     * waits till element is clickable
     *
     * @param locator
     * @param timeInMilliSeconds
     * @return webelement
     */
    protected WebElement waitForElementToBeClickable(WebElement element, int timeInMilliSeconds) {
        try {
            element = new WebDriverWait(driver, Duration.ofMillis(timeInMilliSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
            logger.debug("Waited for element to be clickable");
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for element to be clickable", e);
            throw e;
        }
        return element;
    }

    /**
     * waits till element is visible
     *
     * @param locator
     * @param timeInMilliSeconds
     * @return webelement
     */
    protected WebElement waitForElementToBeVisible(By locator, int timeInMilliSeconds) {
        WebElement element = null;
        try {
            element = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(timeInMilliSeconds))
                    .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for element to be visible", e);
            throw e;
        }
        return element;
    }

    /**
     * waits till elements are visible
     *
     * @param locator
     * @param timeInMilliSeconds
     * @return List<WebElement>
     */
    protected List<WebElement> waitForElementsToBeVisible(By locator, int timeInMilliSeconds) {
        List<WebElement> elements = null;
        try {
            elements = new WebDriverWait(driver, Duration.ofMillis(timeInMilliSeconds))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            logger.debug("Waited for element to be clickable");
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for element to be clickable", e);
            throw e;
        }
        return elements;
    }

    /**
     * waits till element is invisible
     *
     * @param locator
     * @param timeInMilliSeconds
     */
    protected boolean waitForElementToBeInVisible(By locator, int timeInMilliSeconds) {
        boolean elementInvisible = false;
        try {
            elementInvisible = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(timeInMilliSeconds))
                    .pollingEvery(Duration.ofMillis(200)).ignoring(TimeoutException.class)
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for element to be invisible", e);
            throw e;
        }
        return elementInvisible;
    }

    /**
     * waits till element is not stale
     *
     * @param locator
     * @return webelement
     */
    protected WebElement waitForElementToBecomeUnstale(By locator) {
        WebElement element = null;
        try {
            element = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(10000))
                    .pollingEvery(Duration.ofMillis(500)).ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class)
                    .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for element to be unstale", e);
            throw e;
        }
        return element;
    }

    /**
     * waits for screen to load
     */
    public void waitForScreenToLoad() {
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(10000));
            logger.debug("Waited for screen to load");
        } catch (Exception e) {
            logger.warn("Exception reached: Could not wait for screen to be loaded", e);
        }
    }

    /**
     * get build version appium server details
     *
     * @param locator
     */
    protected Map <String, Object> getServerStatus() {
        Map <String, Object> serverVersionDetails= null;
        try {
            logger.debug("got server status");
            serverVersionDetails =  driver.getStatus();
        } catch (Exception e) {
            logger.error("Exception reached: Could not get server status", e);
            throw e;
        }
        return serverVersionDetails;
    }

    /**
     * get app id in map
     *
     */
    public ImmutableMap<String, String> getAppId() {
        if(BaseTest.isAndroidTest())
            return ImmutableMap.of("appId", (String) DriverFactory.capabilities.getCapability(AndroidMobileCapabilityType.APP_PACKAGE));
        else
            return ImmutableMap.of("bundleId", (String) DriverFactory.capabilities.getCapability(IOSMobileCapabilityType.BUNDLE_ID));
    }

    /**
     * resets location permission on ios
     *
     */
    public void jsIosResetLocationPermission() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: resetPermission", ImmutableMap.of("service", "location"));
            logger.debug("app terminated and cleared cache");
        } catch (Exception e) {
            logger.error("Exception reached: Could not terminate app", e);
            throw e;
        }
    }

    /**
     * terminates app and clears cache
     *
     */
    public void jsTerminateAppClearingCache() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: terminateApp", getAppId());
            logger.debug("app terminated and cleared cache");
        } catch (Exception e) {
            logger.error("Exception reached: Could not terminate app", e);
            throw e;
        }
    }

    /**
     * puts app into background
     *
     */
    public void jsPutAppInBackground() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: backgroundApp");
            logger.debug("app put in background");
        } catch (Exception e) {
            logger.error("Exception reached: Could not put app in background", e);
            throw e;
        };
    }

    /**
     * activates app from background without clearing cache
     *
     */
    public void jsActivateAppithoutClearingCache() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: activateApp", getAppId());
            logger.debug("activated app without clearing cache");
        } catch (Exception e) {
            logger.error("Exception reached: Could not activate app", e);
            throw e;
        }
    }

    /**
     * activates app after closing and clearing cache
     *
     */
    public void jsCloseAndlaunchAppClearingCache() {
        try {
            jsTerminateAppClearingCache();
            jsActivateAppithoutClearingCache();
            logger.debug("clse and launch app after clearing cache");
        } catch (Exception e) {
            logger.error("Exception reached: Could not close and launch app", e);
            throw e;
        }
    }

    /**
     * launch app without clearing cache
     *
     */
    public void jsLaunchAppWithoutClearingCache() {
        try {
            hardWait(2000);
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: launchApp", getAppId());
            hardWait(2000);
            logger.debug("launched app without clearing cache");
        } catch (Exception e) {
            logger.error("Exception reached: Could not launch app", e);
            throw e;
        }
    }

    /**
     * check if app is installed
     *
     */
    public boolean jsIsAppInstalled() {
        boolean isInstalled = false;
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            isInstalled = (boolean) jsDriver.executeScript("mobile: isAppInstalled", getAppId());
            logger.debug("got is app installed status");
        } catch (Exception e) {
            logger.error("Exception reached: Could not check if app is installed", e);
            throw e;
        }
        return isInstalled;
    }

    /**
     * hide keyboard
     *
     */
    public void jsHideKeyboard() {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            jsDriver.executeScript("mobile: hideKeyboard");
            logger.debug("hidden keyboard");
        } catch (Exception e) {
            logger.error("Exception reached: Could not hide keyboard", e);
            throw e;
        };
    }

    /**
     * check if keyboard displayed
     *
     */
    public boolean jsIsKeybordShown() {
        boolean isKeyboardShown;
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            isKeyboardShown = (boolean)jsDriver.executeScript("mobile: isKeyboardShown");
            logger.debug("got value if keyboard displayed");
        } catch (Exception e) {
            logger.error("Exception reached: Could not check if keyborad displayed", e);
            throw e;
        };
        return isKeyboardShown;
    }

    /**
     * Enum for direction
     *
     */
    public enum Direction {
        RIGHT,
        LEFT,
        DOWN,
        UP
    }

    /**
     * perform javascript scroll
     *
     */
    public void jsIosScrollTillEnd(int numberOfTimesToScroll, Direction directionToReach) {
        try {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
            int counter = 1;
            while(counter <= numberOfTimesToScroll) {
                jsDriver.executeScript("mobile: scroll", ImmutableMap.of("direction", directionToReach.toString().toLowerCase()));
                counter++;
            }
            logger.debug("performed scroll");
        } catch (Exception e) {
            logger.error("Exception reached: Could not scroll", e);
            throw e;
        }
    }

    /**
     * Perform scroll with given coordinates
     * @param startx int
     * @param starty int
     * @param endx int
     * @param endy int
     *
     */
    public void scrollByCoordinates(int startx, int starty, int endx, int endy) {
        try {
            Sequence scroll = new Sequence(finger, 0);
            scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startx, starty));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), endx, endy));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(scroll));
            hardWait(2000);
            logger.debug("performed scroll/swipe by coordinates");
        } catch(Exception e) {
            logger.error("Exception reached: Could not scroll/swipe by coordinates", e);
            throw e;
        }
    }

    /**
     * get screen coordinates for full scrolling
     * @param direction Enum
     *
     */
    private HashMap<String, Integer> getScreenScrollPositions(Direction directionToReach) {
        HashMap<String, Integer> coordMap = new HashMap<>();
        int screenWidth, screenHeight;
        screenWidth = driver.manage().window().getSize().getWidth();
        screenHeight = driver.manage().window().getSize().getHeight();
        switch (directionToReach) {
            case UP:
                coordMap.put("startx", screenWidth / 2);
                coordMap.put("starty", (int) (screenHeight * 0.12));
                coordMap.put("endx", screenWidth / 2);
                coordMap.put("endy", (int) (screenHeight * 0.85));
                break;
            case DOWN:
                coordMap.put("startx", screenWidth / 2);
                coordMap.put("starty", (int) (screenHeight * 0.85));
                coordMap.put("endx", screenWidth / 2);
                coordMap.put("endy", (int) (screenHeight * 0.1));
                break;
            case LEFT:
                coordMap.put("startx", (int) (screenWidth * 0.2));
                coordMap.put("starty", screenHeight / 2);
                coordMap.put("endx", (int) (screenWidth * 0.8));
                coordMap.put("endy", screenHeight / 2);
                break;
            case RIGHT:
                coordMap.put("startx", (int) (screenWidth * 0.8));
                coordMap.put("starty", screenHeight / 2);
                coordMap.put("endx", (int) (screenWidth * 0.2));
                coordMap.put("endy", screenHeight / 2);
                break;
            default:
                throw new IllegalArgumentException("Invalid swipe direction: " + directionToReach);
        }
        return coordMap;
    }

    /**
     * Perform scroll till end of screen in specified direction
     * @param direction Enum
     * @param element WebElement
     *
     */
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
            logger.debug("performed scroll for element into view");
        } catch (Exception e) {
            logger.error("Exception reached: Could not scroll for element", e);
            throw e;
        }
    }

    /**
     * Perform scroll till element in view in given direction
     * @param direction Enum
     * @param element WebElement
     *
     */
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
            logger.debug("performed scroll for element into view");
        } catch (Exception e) {
            logger.error("Exception reached: Could not scroll for element", e);
            throw e;
        }
    }

    /**
     * Perform swipe on an element in given direction
     * @param direction Enum
     * @param element WebElement
     *
     */
    public void swipeOnElement(Direction directionToReach, WebElement element) {
        try {
            int startx, starty, endx, endy;
            int elementX = element.getRect().x;
            int elementY = element.getRect().y;
            int elementHeight = element.getSize().height;
            int elementWidth = element.getSize().width;

            switch (directionToReach) {
                case LEFT:
                    startx = elementX + (elementWidth / 5);
                    starty = elementY + (elementHeight / 2);
                    endx = elementX + (elementWidth * 3 / 4);
                    endy = starty;
                    break;
                case RIGHT:
                    startx = elementX + (elementWidth * 3 / 4);
                    starty = elementY + (elementHeight / 2);
                    endx = elementX + (elementWidth / 5);
                    endy = starty;
                    break;
                case UP:
                    startx = elementX + (elementWidth / 2);
                    starty = elementY + (elementHeight / 4);
                    endx = startx;
                    endy = elementY + (elementHeight * 3 / 4);
                    break;
                case DOWN:
                    startx = elementX + (elementWidth / 2);
                    starty = elementY + (elementHeight * 3 / 4);
                    endx = startx;
                    endy = elementY + (elementHeight / 4);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid swipe direction: " + directionToReach);
            }
            scrollByCoordinates(startx, starty, endx, endy);
            logger.debug("performed swipe on element");
        } catch(Exception e) {
            logger.error("Exception reached: Could not perform swipe on element", e);
            throw e;
        }
    }

    /**
     * swipe on element with locator and direction
     *
     * @param locator
     */
    protected void swipeOnElement(Direction directionToReach, By locator) {
        swipeOnElement(directionToReach, getElement(locator));
    }

    /**
     * Perform tap with given coordinates
     * @param xcoordinate int
     * @param ycoordinate int
     *
     */
    public void tapByCoordinates(int x, int y) {
        try {
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(tap));
            logger.debug("performed tap by coordinates");
        } catch(Exception e) {
            logger.error("Exception reached: Could not tap by coordinates", e);
            throw e;
        }
    }

    /**
     * Perform tap at middle of the screen
     * @param xcoordinate int
     * @param ycoordinate int
     *
     */
    public void tapOnMidOfScreen() {
        try {
            int x = driver.manage().window().getSize().getWidth() / 2;
            int y = driver.manage().window().getSize().getHeight() / 2;
            tapByCoordinates(x, y);
            logger.debug("performed tap on screen");
        } catch(Exception e) {
            logger.error("Exception reached: Could not tap on screen", e);
            throw e;
        }
    }

    /**
     * Perform tap on an element
     * @param element WebElement
     *
     */
    public void tapOnElement(WebElement element) {
        try {
            Point sourceLocation = element.getLocation();
            Dimension sourceSize = element.getSize();
            int centerX = sourceLocation.getX() + sourceSize.getWidth() / 2;
            int centerY = sourceLocation.getY() + sourceSize.getHeight() / 2;
            tapByCoordinates(centerX, centerY);
            logger.debug("performed tap on element");
        } catch(Exception e) {
            logger.error("Exception reached: Could not tap on element", e);
            throw e;
        }
    }

    /**
     * tap on element with locator
     *
     * @param locator
     */
    protected void tapOnElement(By locator) {
        tapOnElement(getElement(locator));
    }

    /**
     * Perform double tap on an element
     * @param element WebElement
     *
     */
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
            logger.debug("performed double tap on element");
        } catch(Exception e) {
            logger.error("Exception reached: Could not perform double tap on element", e);
            throw e;
        }
    }

    /**
     * double tap on element with locator
     *
     * @param locator
     */
    protected void doubleTapOnElement(By locator) {
        doubleTapOnElement(getElement(locator));
    }

    /**
     * drags an element from source to destination
     * @param sourceElement [WebElement] : WebElement information for the element to be dragged as source
     * @param destinationElement [WebElement] : WebElement information to the destination where the element is dropped
     */
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
            logger.debug("Dragged source element to destination element");
        } catch (Exception e) {
            logger.error("Exception reached: Could not drag source element to destination element", e);
            throw e;
        }
    }

    /**
     * drags an element from source to destination
     * @param sourceLocator [By] : Locator information for the element to be dragged as source
     * @param destinationLocator [By] : Locator information to the destination where the element is dropped
     */
    protected void dragAndDrop(By sourceLocator, By destinationLocator) {
        dragAndDrop(getElement(sourceLocator), getElement(destinationLocator));
    }

    /**
     * gets driver page source
     *
     * @param locator
     * @return text
     */
    public void getPageSource() {
        try {
            System.out.println(driver.getPageSource());
            hardWait(2000);
        } catch(Exception e) {
            logger.error("Exception reached: Could not get page source", e);
            throw e;
        }
    }

    /**
     * hover on element
     *
     * @param element
     */
    protected void hoverOnElement(WebElement element) {
        try {
            new Actions(driver).moveToElement(element).build().perform();
            logger.debug("hovered on element");
        } catch (Exception e) {
            logger.error("Exception reached: Could not hover on element", e);
            throw e;
        }
    }

    /**
     * hover on element
     *
     * @param locator
     */
    protected void hoverOnElement(By locator) {
        hoverOnElement(getElement(locator));
    }

    /**
     * accepts alert
     */
    public void acceptAlertIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofMillis(8000)).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (TimeoutException e) {
            logger.warn("Timed out, alert not present", e);
        } catch (NoAlertPresentException e) {
            logger.warn("No Alert present", e);
        } catch (Exception e) {
            logger.error("Exception reached: Could not confirm on alert", e);
            throw e;
        }
    }

    /**
     * dismisses alert
     */
    public void dismissAlertIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofMillis(8000)).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss();;
        } catch (TimeoutException e) {
            logger.warn("Timed out, alert not present", e);
        } catch (NoAlertPresentException e) {
            logger.warn("No Alert present", e);
        } catch (Exception e) {
            logger.error("Exception reached: Could not confirm on alert", e);
            throw e;
        }
    }

    /**
     * get https response code after sending request
     *
     * @param urlString
     * @param method
     *
     * @return responseCode
     */
    public int getHttpResponseCode(String urlString, String method) {
        URL url;
        HttpURLConnection request;
        int responseCode = 0;
        try {
            url = new URL(urlString);
            request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod(method);
            request.connect();
            responseCode = request.getResponseCode();
        } catch (IOException e) {
            logger.error("Exception reached: Could not get url response", e);
        }
        return responseCode;
    }

    /**
     * emulates hitting device home
     *
     */
    public void tapDeviceHomeButton() {
        try {
            if(BaseTest.isAndroidTest())
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
            else {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                jsDriver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
            }
            logger.debug("tapped on device home");
        } catch (Exception e) {
            logger.error("Exception reached: Could not hit home", e);
            throw e;
        }
    }

    /**
     * emulates pressing button
     *
     */
    public void pressButton() {
        try {
            if(BaseTest.isAndroidTest())
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
            else {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                jsDriver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
            }
            logger.debug("tapped on device home");
        } catch (Exception e) {
            logger.error("Exception reached: Could not hit home", e);
            throw e;
        }
    }

    public List<String> getStringIntoList(String stringToChange, String delimeter) {
        List<String> list;
        try {
            list = Stream.of(stringToChange.split(delimeter, -1)).collect(Collectors.toList());
        } catch(Exception e) {
            logger.error("Exception reached: Could not split string to list " + stringToChange + " with delimeter " + delimeter, e);
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
            logger.error("Exception reached: Could not split list to map " + listToChange + " with delimeter " + delimeter, e);
            throw e;
        }
        return map;
    }

    // Math operations

    /**
     * to truncate the double value to a particular decimal point
     * @author Arzoo Hingorani
     * @param value [double] : The double value you want to truncate
     * @param decimalpoint [int] :  Upto what decimal point you want it to truncate
     * @return double truncated value
     */
    public double truncateUptoParticularDecimalPoint(double value, int decimalpoint)
    {
        double updatedValue=0.0;
        try {
            updatedValue = value * Math.pow(10, decimalpoint);
            updatedValue = Math.floor(updatedValue);
            updatedValue = updatedValue / Math.pow(10, decimalpoint);

        } catch (Exception e) {
            logger.error("Exception reached: Could not truncate value upto decimal point as expected due to - ", e);
        }
        return updatedValue;
    }

    // Regular expression operations

    /**
     * retrieve the one matching pattern using Regular Expression
     * @author Arzoo Hingorani
     * @param patternToFindMatch [String] : Regex pattern
     * @param textToBeMatched [String] : The text in which a pattern is to be found
     * @return String value of the matching text as per the pattern
     */
    public String getTextMatchingWithPatternUsingRegex(String patternToFindMatch, String textToBeMatched) {
        try {
            Pattern pattern = Pattern.compile(patternToFindMatch);
            Matcher matcher = pattern.matcher(textToBeMatched);
            boolean matchFound = matcher.find();
            if(matchFound)
                return matcher.group(1);

        } catch (Exception e) {
            logger.debug("Failed to get text matching the given pattern due to exception " + e.getMessage());
        }
        return null;
    }

    /**
     * validates whether the text is matching with the provided pattern using Regular Expression
     * @author Arzoo Hingorani
     * @param patternToFindMatch [String] : Regex pattern
     * @param textToBeMatched [String] : The text in which a pattern is to be found
     * @return boolean true if a matching text as per the pattern is found successfully
     */
    public boolean validateTextIsMatchingWithPatternUsingRegex(String patternToFindMatch, String textToBeMatched) {
        try {
            Pattern pattern = Pattern.compile(patternToFindMatch);
            Matcher matcher = pattern.matcher(textToBeMatched);
            boolean matchFound = matcher.find();
            if(matchFound)
                return true;

        } catch (Exception e) {
            logger.debug("Failed to validate whether the text is matching the given pattern or not, due to exception " + e.getMessage());
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

    public void relaunchAppFromBackground(){
        if(BaseTest.isIosTest()){
            ((IOSDriver) driver).activateApp(getAppId().get("bundleId"));
        }
        else{
            ((AndroidDriver) driver).activateApp(getAppId().get("appId"));
        }
    }
}
