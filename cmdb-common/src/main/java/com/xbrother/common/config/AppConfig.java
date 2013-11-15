package com.xbrother.common.config;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * 
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-25
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = { AppConfig.BASE_PACKAGES })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({ TransactionConfig.class })
public class AppConfig {
	public final static String BASE_PACKAGES = "com.xbrother";

	@Bean(name = "validator")
	public Validator validator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}
}
