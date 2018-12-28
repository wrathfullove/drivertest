package com.drivertest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan(basePackages = {"com.drivertest.dao"})
public class ReptileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}

}

