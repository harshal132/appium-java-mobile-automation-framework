package utils.capabilities;

public class Emulator {
    private String platformName;
    private String platformVersion;
    private String app;
    private String appPackage;
    private String appActivity;
    private String appPackageProd;
    private String appActivityProd;
    private boolean noReset;
    private String automationName;
    private String deviceName;
    private String bundleId;
    private String bundleIdProd;
    private String orientation;
    private String autoGrantPermissions;
    private String udid;
    private boolean fullReset;

    public String getPlatformName() {
        return platformName;
    }
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
    public String getUdid() {
        return udid;
    }
    public void setUdid(String udid) {
        this.udid = udid;
    }
    public boolean getFullReset() {
        return fullReset;
    }
    public void setFullReset(boolean fullReset) {
        this.fullReset = fullReset;
    }
    public String getPlatformVersion() {
        return platformVersion;
    }
    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }
    public String getApp() {
        return app;
    }
    public void setApp(String app) {
        this.app = app;
    }
    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }
    public String getAppPackage() {
        return appPackage;
    }
    public String getAppActivity() {
        return appActivity;
    }
    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }
    public void setAppPackageProd(String appPackageProd) {
        this.appPackageProd = appPackageProd;
    }
    public String getAppPackageProd() {
        return appPackageProd;
    }
    public String getAppActivityProd() {
        return appActivityProd;
    }
    public void setAppActivityProd(String appActivityProd) {
        this.appActivityProd = appActivityProd;
    }
    public boolean getNoReset() {
        return noReset;
    }
    public void setNoReset(boolean noReset) {
        this.noReset = noReset;
    }
    public String getAutomationName() {
        return automationName;
    }
    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getBundleId() {
        return bundleId;
    }
    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
    public String getBundleIdProd() {
        return bundleIdProd;
    }
    public void setBundleIdProd(String bundleIdProd) {
        this.bundleIdProd = bundleIdProd;
    }
    public String getOrientation() {
        return orientation;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
    public String getautoGrantPermissions() {
        return autoGrantPermissions;
    }
    public void setautoGrantPermissions(String autoGrantPermissions) {
        this.autoGrantPermissions = autoGrantPermissions;
    }
}
