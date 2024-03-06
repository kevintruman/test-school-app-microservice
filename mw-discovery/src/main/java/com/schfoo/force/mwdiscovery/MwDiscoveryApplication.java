package com.schfoo.force.mwdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MwDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MwDiscoveryApplication.class, args);
	}

}
