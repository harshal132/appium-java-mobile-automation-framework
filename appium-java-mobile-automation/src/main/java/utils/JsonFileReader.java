package utils;

import utils.capabilities.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
public class JsonFileReader {

    static ObjectMapper mapper = new ObjectMapper();

    public static Device getCapabilitiesJson(String jsonFilePath) {
        Device root = null;
        try {
            root  = mapper.readValue(new File(jsonFilePath), Device.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
