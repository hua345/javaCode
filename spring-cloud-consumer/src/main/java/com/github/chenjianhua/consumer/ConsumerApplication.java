package com.github.chenjianhua.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
/**
 * https://spring.io/guides/gs/service-registration-and-discovery/
 * https://spring.io/guides/gs/consuming-rest/
 * https://spring.io/guides/gs/client-side-load-balancing/
 * @EnableCircuitBreaker 不需要
 * 在配置中添加spring.cloud.circuit.breaker.enabled就可以开启
 */
@EnableHystrix
@SpringBootApplication
@SpringCloudApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
