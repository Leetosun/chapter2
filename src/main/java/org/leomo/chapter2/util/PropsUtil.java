package org.leomo.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by LeeToSun on 2017/5/18
 */
public class PropsUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties prop = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (null == is) {
                throw new FileNotFoundException(fileName + "" + "file is not found");
            }
            prop = new Properties();
            prop.load(is);
        } catch (Exception e) {
            LOGGER.error("load properties is failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return prop;
    }

}
