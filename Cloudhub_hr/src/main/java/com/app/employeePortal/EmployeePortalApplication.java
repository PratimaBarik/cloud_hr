package com.app.employeePortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

@Async
@EnableScheduling
@EnableCaching
@SpringBootApplication
@ComponentScan("com.app.employeePortal")
public class EmployeePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeePortalApplication.class, args);
		System.out.println("Hello world");
	}
	
//	@Override
//	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//      return application.sources(EmployeePortalApplication.class);
//  }
//	
	
	/*@Bean
	public MultipartConfigElement multipartConfigElement() {
	  MultipartConfigFactory factory = new MultipartConfigFactory();
	 // DataSize dataSize = new DataSize(0);
	  factory.setMaxFileSize(DataSize.ofBytes(52428800));
	  factory.setMaxRequestSize(DataSize.ofBytes(52428800));
	  return factory.createMultipartConfig();*/
//	}

}
