package com.naturalprogrammer.np02.lib001.scan;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.naturalprogrammer.spring.lemon.commons.LemonCommonsAutoConfiguration;

@Configuration
@AutoConfigureBefore({LemonCommonsAutoConfiguration.class, ValidationAutoConfiguration.class})
@ComponentScan
public class Lib001AutoConfiguration {
	
    @Bean
    public Validator validator(MessageSource messageSource) {

        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}
