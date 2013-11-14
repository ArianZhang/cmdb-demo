package com.xbrother.common.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.xbrother.common.constants.CacheSpace;

/**
 * 
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-25
 * @version 1.0
 */
@Configuration
@EnableCaching
@ComponentScan(basePackages = { AppConfig.BASE_PACKAGES })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({ TransactionConfig.class })
public class AppConfig {
	public final static String BASE_PACKAGES = "com.xbrother";

	@Bean(name = "validator")
	public Validator validator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Bean
	public CacheManager cacheManager() throws Exception {
		SimpleCacheManager scm = new SimpleCacheManager();
		Field[] fields = CacheSpace.class.getFields();
		List<Cache> caches = new ArrayList<Cache>(fields.length);
		caches.add(new ConcurrentMapCache(CacheSpace.MENUDTO));
		caches.add(new ConcurrentMapCache(CacheSpace.BUTTONDTO));
		scm.setCaches(caches);
		return scm;
	}
}
