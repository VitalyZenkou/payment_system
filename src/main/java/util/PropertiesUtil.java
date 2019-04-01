package util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream inputStream = PropertiesUtil.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can't load class PropertiesUtil");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
