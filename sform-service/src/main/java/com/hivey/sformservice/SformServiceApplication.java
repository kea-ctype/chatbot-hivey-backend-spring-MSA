package com.hivey.sformservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 여기서 @EnableJpaAuditing은 BaseTime 클래스처럼 JPA auditing 기능을 활성화하기 위한 어노테이션이다.
 */
@EnableJpaAuditing
@EnableDiscoveryClient //eureka 등록
@SpringBootApplication
public class SformServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SformServiceApplication.class, args);
    }

}
