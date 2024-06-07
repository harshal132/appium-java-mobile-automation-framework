package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import common.constants.FilePath;
import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;
import tests.BaseTest;

public class DataLoader {
    private static Map<String, Object> readLocatorYaml(String filePath, String locatorName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        Map<String, Object> obj = null;
        try {
            inputStream = new FileInputStream(filePath);
            obj = yaml.load(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (Map<String, Object>) obj.get(locatorName);
    }

    public static String getAppData(String filePath, String keyName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        Map<String, Object> obj = null;
        try {
            inputStream = new FileInputStream(filePath);
            obj = yaml.load(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(obj == null){
            return null;
        }else{
            return getValueByKey(obj,keyName);
        }
    }

    private static String getValueByKey(Map<String, Object> yamlData, String keyName) {
        String[] keys = keyName.split("\\.");
        Map<String, Object> currentMap = yamlData;
        for (String k : keys) {
            Object obj = currentMap.get(k);
            if (obj instanceof Map) {
                currentMap = (Map<String, Object>) obj;
            } else {
                return obj.toString();
            }
        }
        return null; // Key not found
    }

    private static String getValueAfterReplacement(String locatorValue, String... replacement) {
        String finalLocatorString = "";
        String[] locatorsSplitArray;
        if(locatorValue.contains("|")){
            locatorsSplitArray = locatorValue.split("\\|");
            for(String oneLocator: locatorsSplitArray){
                if(oneLocator.equals("|")){
                    continue;
                }
                for(int i=0;i<replacement.length;i++){
                    oneLocator = oneLocator.replaceFirst("\\$\\{text\\}", replacement[i]);
                }
                if(finalLocatorString.length()==0){
                    finalLocatorString = oneLocator;
                }
                else{
                    finalLocatorString = finalLocatorString + " | "+ oneLocator;
                }
            }
        }else{
            for(int i=0;i<replacement.length;i++){
                finalLocatorString = locatorValue.replaceFirst("\\$\\{text\\}",replacement[i]);
            }
        }
        return finalLocatorString;
    }

    public static  By getLocator(String filePath, String locatorName, String ...replacement) {
        Map<String, Object> locatorMap = readLocatorYaml(filePath, locatorName);
        String value;
        if(BaseTest.isProdTest()){
            locatorMap = (Map<String, Object>)locatorMap.get("prod");
            if(BaseTest.isAndroidTest()){
                locatorMap = (Map<String, Object>)locatorMap.get("android");
            }else{
                locatorMap = (Map<String, Object>)locatorMap.get("ios");
            }
            if(replacement.length!= 0)
                value = getValueAfterReplacement(locatorMap.get("value").toString(), replacement);
            else
                value = locatorMap.get("value").toString();
        }
        else{
            locatorMap = (Map<String, Object>)locatorMap.get("qa");
            if(BaseTest.isAndroidTest()){
                locatorMap = (Map<String, Object>)locatorMap.get("android");
            }else{
                locatorMap = (Map<String, Object>)locatorMap.get("ios");
            }
            if(replacement.length!= 0)
                value = getValueAfterReplacement(locatorMap.get("value").toString(), replacement);
            else
                value = locatorMap.get("value").toString();
        }
        return getLocatorType(locatorMap.get("locatorType").toString(), value);
    }

    public static String getCommonData(String keyName){
        String filePath = FilePath.REAL_COMMON_DATA_FILE_PATH;
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        Map<String, Object> obj = null;
        try {
            inputStream = new FileInputStream(filePath);
            obj = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(obj == null){
            return null;
        }else{
            if(BaseTest.isProdTest()){
                return getValueByKey((Map<String, Object>) obj.get("prod"),keyName);
            }else{
                return getValueByKey((Map<String, Object>) obj.get("qa"),keyName);
            }
        }
    }

    private static By getLocatorType(String yamlLocatorType, String locatorValue) {
        By locator = switch (yamlLocatorType.toLowerCase()) {
            case "xpath" -> By.xpath(locatorValue);
            case "css" -> By.cssSelector(locatorValue);
            case "class" -> By.className(locatorValue);
            case "id" -> By.id(locatorValue);
            case "linktext" -> By.linkText(locatorValue);
            case "name" -> By.name(locatorValue);
            case "partiallinktext" -> By.partialLinkText(locatorValue);
            case "tagname" -> By.tagName(locatorValue);
            default -> By.xpath(locatorValue);
        };
        return locator;
    }
}