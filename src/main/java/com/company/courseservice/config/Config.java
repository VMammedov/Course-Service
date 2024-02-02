package com.company.courseservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class Config {
    @Bean
    public ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }
}
