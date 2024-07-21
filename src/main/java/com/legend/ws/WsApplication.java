package com.legend.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//exclude = SecurityAutoConfiguration.class -> Securty'nin default davranışını devre dışı bırakırız.dolayısı ile hiçbir endpoint reject edilmeyecek
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

}
