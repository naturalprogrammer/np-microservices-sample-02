//package com.naturalprogrammer.np02.auth.config;
//
//import javax.validation.Validator;
//
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//@Configuration
//public class AuthConfig {
//
//    @Bean
//    public Validator validator(MessageSource messageSource) {
//
//        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//        localValidatorFactoryBean.setValidationMessageSource(messageSource);
//        return localValidatorFactoryBean;
//    }
//
//}
