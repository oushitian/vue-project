package com.interest.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

//资源服务器配置
@Configuration
@EnableResourceServer
public class ResourceServerConfigurerAdapterExt extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
		.antMatchers("/public/**").permitAll()
		.antMatchers("/authentication/github").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/**/*.jpg","/**/*.png","/**/*.jpeg").permitAll()
				//这里得hasRole不能带前缀，而数据库中插入的时候必须带ROLE_的前缀
		.antMatchers("/users/**","/menus/**","/roles/**","/admin/**").hasRole("ADMIN")
		.anyRequest()
		.authenticated();
	}

}
