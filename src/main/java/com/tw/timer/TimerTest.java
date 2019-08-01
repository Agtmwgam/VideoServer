package com.tw.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
* @author liutianwen   
* @Description: 定时任务--------->系统计时器
* @date 2018年8月29日
* @demandNO	1080  
* @branch
 */
@Component
public class TimerTest {
	
	private int sec=0;
	
	@Scheduled(fixedRate=1000)
	public void test(){
		sec++;
		System.out.println("系统已运行"+sec+"秒");
	}
}
