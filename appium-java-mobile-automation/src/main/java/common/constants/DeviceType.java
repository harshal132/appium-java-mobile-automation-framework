package common.constants;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public enum DeviceType {
    REAL("real"), EMULATOR("emulator");
    private static final Map<String, DeviceType> TYPE_MAP;

    private String name;

    static {
        TYPE_MAP = Stream.of(DeviceType.values())
                .collect(Collectors.toMap(type -> type.name, Function.identity()));
    }

    DeviceType(String name) {
        this.name = name;
    }

    public static DeviceType get(String deviceType) {
        if (!StringUtils.isEmpty(deviceType) && TYPE_MAP.containsKey(deviceType.toLowerCase()))
            return TYPE_MAP.get(deviceType.toLowerCase());
        throw new RuntimeException("Device type cannot be null or invalid - " + deviceType);
    }

    public boolean isRealDevice() {
        return (REAL.equals(this));
    }

    public boolean isEmulatorDevice() {
        return (EMULATOR.equals(this));
    }
}
