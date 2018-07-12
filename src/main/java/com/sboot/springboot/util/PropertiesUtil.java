package com.sboot.springboot.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Log4j2
public class PropertiesUtil {

    private static Properties prop;

    static {
        log.info("PropertiesUtil加载开始");
        prop = new Properties();
        try {
            prop.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("sboot.properties"), "utf-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常", e);
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }
}
