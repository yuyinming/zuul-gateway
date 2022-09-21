package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DemoApplication.class).web(true).run(args);
	}

	/**
	 * 注册Java编写的过滤器（这种过滤器的修改需要重启服务才能生效）
	 * @return
	 */
	//@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

}
