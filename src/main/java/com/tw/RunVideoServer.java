package com.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@EnableAsync                     //允许异步
@EnableScheduling              //启动定时任务使用这个注解
/*@MapperScan(value="com.tw.dao.*")*/
public class RunVideoServer {

	public static void main(String[] args) {
		SpringApplication.run(RunVideoServer.class, args);
	}

	/**
	 * request过滤器,校验token是否正确
	 * @return
	 */
//	@Bean
//	public FilterRegistrationBean jwtFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
//		registrationBean.setFilter(filter);
//		return registrationBean;
//	}

}
