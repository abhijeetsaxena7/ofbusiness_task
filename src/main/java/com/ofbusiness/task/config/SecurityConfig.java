package com.ofbusiness.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
    protected void configure(HttpSecurity security) throws Exception {
        //Disable Spring's basic security settings as they are not relevant for this project
        security.httpBasic().disable().csrf().disable().cors().disable();
    }
}
