package com.suiveg.utils.properties;

import com.suiveg.utils.string.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Simple utility that helps loading properties.
 * These options loading a properties-file is supported in this class:
 * -ClassLoader
 * -ResourceBundle
 *
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @version see system.properties
 * @since 0.2-SNAPSHOT
 */
public final class PropertiesUtils {

    private static final String DEFAULT_SYSTEM_PROPERTIES = "system.properties";
    private static final String NA = "na";

    private static PropertiesUtils utils;
    private static Properties properties;

    static {
        properties = new Properties();
    }

    private PropertiesUtils() {}

    public static PropertiesUtils getInstance() {
        if(utils==null) {
            utils = new PropertiesUtils();
        }
        return utils;
    }

    public String getProperty(final String key) {
        Properties p = getSystemProperties();
        if(p!=null) {
            return String.valueOf(p.get(key));
        }
        return "";
    }

    public Properties getSystemProperties() {
        return getSystemProperties(NA);
    }

    public Properties getSystemProperties(final String name) {
        if(properties==null||properties.size()==0) {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
                    (StringUtils.isBlank(DEFAULT_SYSTEM_PROPERTIES) ? DEFAULT_SYSTEM_PROPERTIES : name)
            );
            if(is!=null) {
                try {
                    properties.load(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public String getPathToFile(final String fileName) {
        if(StringUtils.isNotBlank(fileName)) {
            URL u = this.getClass().getClassLoader().getResource(fileName);
            if(u!=null && StringUtils.isNotBlank(u.getFile())) {
                return u.getFile();
            }
        }
        return fileName;
    }

}