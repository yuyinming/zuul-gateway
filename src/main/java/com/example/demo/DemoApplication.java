package com.example.demo;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableConfigurationProperties({FilterConfigration.class})
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

	/**
	 * 配置动态加载过滤器
	 * 加载路径
	 * 加载时间间隔
	 * @param filterConfigration
	 * @return
	 */
	@Bean
	public FilterLoader filterLoader(FilterConfigration filterConfigration){
		FilterLoader filterLoader = FilterLoader.getInstance();
		filterLoader.setCompiler(new GroovyCompiler());
		try {
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			FilterFileManager.init(filterConfigration.getInterval(),
					filterConfigration.getRoot() + "/pre",
					filterConfigration.getRoot() + "/route",
					filterConfigration.getRoot() + "/post",
					filterConfigration.getRoot() + "/error");
		} catch (Exception e) {
			throw  new RuntimeException(e);
		}
		return filterLoader;
	}

}
