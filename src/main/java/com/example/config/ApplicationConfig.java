package com.example.config;

import com.example.DemoApplication;
import org.springframework.context.annotation.*;

/* * * @author Clint Atmosoerodjo #commander *  */

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackageClasses = {DemoApplication.class})
public class ApplicationConfig {

	@Bean
	public ApplicationContextProvider applicationContextProvider() throws Exception {
		return new ApplicationContextProvider();
	}

	@Configuration
	@Profile("dev")
	@PropertySource({"classpath:application-dev.properties"})
	static class DEV {
	}

	@Configuration
	@Profile("prod")
	@PropertySource({"classpath:application-prod.properties"})
	static class PRD {
	}

	@Configuration
	@Profile("test")
	@PropertySource({"classpath:application-test.properties"})
	static class TEST {
	}

	@Configuration
	@Profile("devmem")
	@PropertySource({"classpath:application-devmem.properties"})
	static class DEVMEM {
	}

	@Configuration
	@Profile("acc")
	@PropertySource({"classpath:application-acc.properties"})
	static class ACC {
	}

}