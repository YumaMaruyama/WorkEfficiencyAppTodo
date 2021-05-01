package com.example.demo;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WebConfig {

	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();

		//メッセージのプロパティファイル名（デフォルト）を指定します
		//下記ではmessages.properticeファイルがセットされます
		bean.setBasename("classpath:messages");
		bean.setDefaultEncoding("UTF-8");

		return bean;

	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {

		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource());

		return localValidatorFactoryBean;
	}
}
