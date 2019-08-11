package com.tw;

import com.tw.common.ListenerAdaptor;
import com.tw.config.FtpConfig;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.TimeUnit;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@EnableAsync                     //允许异步
//@EnableScheduling              //启动定时任务使用这个注解
/*@MapperScan(value="com.tw.dao.*")*/
public class RunVideoServer {
	private static Logger log = Logger.getLogger(RunVideoServer.class);

	@Autowired
	private ListenerAdaptor listenerAdaptor;

	@Autowired
	private FtpConfig ftpConfig;


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


	/***
	 * 文件监听服务,配置在启动类中监听线程会自动启动  added  by  lushiqin
 	 */
	//被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次
	@PostConstruct
	public void executeListener(){

		log.info("====文件监听服务启动========");
		System.out.println("====ListenerServer文件监听服务启动========");

		// 设置监控目录,监听FTP服务器的视频存放路径
		//String monitorDir = "D:\\testVideo";//本机测试用
		String monitorDir = ftpConfig.getBasePath()+ftpConfig.getVideoPath();//正式环境

		// 设置扫描间隔
		long interval = TimeUnit.SECONDS.toMillis(5);
		// 创建过滤器
		// 匹配目录
		IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);

		// 匹配txt后缀文件
		IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
				FileFilterUtils.suffixFileFilter(".mp4"));

		// 过滤符合：对应可视目录 和 后缀为".mp4"的文件
		IOFileFilter filter = FileFilterUtils.or(directories, files);
		// 使用过滤器：装配过滤器，生成监听者
		//FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir), filter);
		// 不使用过滤器的情况
		FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir));

		// 向监听者添加监听器，并注入业务服务
		observer.addListener(listenerAdaptor);

		// 创建文件变化监听器
		FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(interval, observer);
		try {
			// 开启监听
			fileAlterationMonitor.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
