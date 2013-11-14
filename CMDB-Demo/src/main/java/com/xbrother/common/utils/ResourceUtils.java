package com.xbrother.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * Description:
 * 
 * @author Arian Zhang
 * @email hzhang@digitnexus.com
 * @Date 2013-6-14 上午9:40:40
 * @since v1.0.0
 */
public class ResourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    public static List<URL> getURLsSortByNear2Long(String resourceLocation) throws FileNotFoundException {
        checkResourceLocation(resourceLocation);
        try {
            Enumeration<URL> enumeration = ClassUtils.getDefaultClassLoader().getResources(resourceLocation.trim());
            return CollectionUtils.convert(enumeration);
        } catch (IOException e) {
            throw new FileNotFoundException("resource path [" + resourceLocation
                    + "] cannot be resolved to URL because it does not exist");
        }
    }
    
    private static void checkResourceLocation(String resourceLocation){
        if(StringUtils.isEmpty(resourceLocation)){
            throw new RuntimeException("Empty resource location is unaccepted!");
        }
    }

    public static List<InputStream> getResourcesAsStreamsSortByLong2Near(String resourceLocation) throws IOException {
        List<URL> urls = getURLsSortByNear2Long(resourceLocation);
        List<InputStream> ins = new ArrayList<InputStream>(urls.size());
        for (int i = urls.size() - 1; i >= 0; i--) {
            logger.debug(urls.get(i).getPath());
            ins.add(urls.get(i).openStream());
        }
        return ins;
    }

    public static URL getResource(String resourceLocation) {
        checkResourceLocation(resourceLocation);
        return Thread.currentThread().getContextClassLoader().getResource(resourceLocation);
    }

    public static URL getResource(String resourceLocation, Class<?> clazz) {
        checkResourceLocation(resourceLocation);
        URL url = clazz.getClassLoader().getResource(resourceLocation);
        if (url != null) {
            return url;
        }
        return Thread.currentThread().getContextClassLoader().getResource(resourceLocation);
    }

    public static InputStream getResourceAsStream(String resourceLocation) throws IOException {
        checkResourceLocation(resourceLocation);
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceLocation);
    }

    public static InputStream getResourceAsStream(String resourceLocation, Class<?> clazz) throws IOException {
        checkResourceLocation(resourceLocation);
        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(resourceLocation);
        if (inputStream == null) {
            inputStream = getResourceAsStream(resourceLocation);
        }
        return inputStream;
    }

    public static void closeResourceStreams(List<InputStream> inputStreams) {
        if (inputStreams == null || inputStreams.size() == 0) {
            return;
        }
        for (InputStream is : inputStreams) {
            StreamUtils.closeInputStream(is);
        }
    }

    public static void closeResourceStream(InputStream inputStream) {
        StreamUtils.closeInputStream(inputStream);
    }
}
