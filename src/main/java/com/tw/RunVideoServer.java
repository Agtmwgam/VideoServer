package com.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync                     //允许异步
//@EnableScheduling              //启动定时任务使用这个注解
/*@MapperScan(value="com.tw.dao.*")*/
public class RunVideoServer {

	public static void main(String[] args) {
		SpringApplication.run(RunVideoServer.class, args);
	}

}
