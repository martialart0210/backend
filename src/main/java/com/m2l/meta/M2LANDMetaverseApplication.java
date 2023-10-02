package com.m2l.meta;

import com.m2l.meta.config.AppProperties;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.TimeZone;


//@OpenAPIDefinition(
//        servers = {
//                @Server(url = "http://54.254.42.188:8182/api/v1", description = "GAME BE API")
//        }
//)
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableConfigurationProperties(AppProperties.class)
public class M2LANDMetaverseApplication {
    private static final Logger logger = LogManager.getLogger(M2LANDMetaverseApplication.class);

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(M2LANDMetaverseApplication.class, args);
    }

}
