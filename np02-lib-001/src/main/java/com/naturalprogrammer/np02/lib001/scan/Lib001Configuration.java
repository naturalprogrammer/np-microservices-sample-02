package com.naturalprogrammer.np02.lib001.scan;

import javax.validation.Validator;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import feign.Logger;

@Configuration
@EnableFeignClients
public class Lib001Configuration {
	
    @Bean
    public Validator validator(MessageSource messageSource) {

        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
