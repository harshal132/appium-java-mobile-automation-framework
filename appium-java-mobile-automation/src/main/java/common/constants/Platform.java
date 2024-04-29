package common.constants;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
public enum Platform {
    IOS("ios"), ANDROID("android");

    private static final Map<String, Platform> PLATFORM_MAP;

    private String name;

    static {
        PLATFORM_MAP = Stream.of(Platform.values())
                .collect(Collectors.toMap(platform -> platform.name, Function.identity()));
    }

    Platform(String name) {
        this.name = name;
    }

    public static Platform get(String platform) {
        if (!StringUtils.isEmpty(platform) && PLATFORM_MAP.containsKey(platform.toLowerCase()))
            return PLATFORM_MAP.get(platform.toLowerCase());
        throw new RuntimeException("Platform cannot be null or Invalid platform name - " + platform);
    }

    public boolean isIos() {
        return IOS.equals(this);
    }

    public boolean isAndroid() {
        return ANDROID.equals(this);
    }
}
