package com.mar.meta;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class MartialArtsMetaverseApplication {
    private static final Logger logger = LogManager.getLogger(MartialArtsMetaverseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MartialArtsMetaverseApplication.class, args);
    }

}
