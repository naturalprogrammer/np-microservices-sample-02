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

import com.naturalprogrammer.spring.lemonreactive.LemonReactiveAutoConfiguration;

@Configuration
@AutoConfigureBefore({LemonReactiveAutoConfiguration.class, ValidationAutoConfiguration.class})
@ComponentScan
public class Lib001AutoConfiguration {
	
	@Bean
	public MessageSource messageSource(
			@Value("${spring.cloud.config.uri}") String configUri,
			@Value("${git.branch}") String gitBranch) {
		
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages",
				configUri + "/appname/profile/" + gitBranch + "/messages/messages",
				configUri + "/appname/profile/develop/messages/messages",
				configUri + "/appname/profile/master/messages/messages");
		
		return messageSource;
	}
	
    @Bean
    public Validator validator(MessageSource messageSource) {

        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}
